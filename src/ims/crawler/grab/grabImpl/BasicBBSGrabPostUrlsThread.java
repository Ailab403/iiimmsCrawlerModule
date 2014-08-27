package ims.crawler.grab.grabImpl;

import ims.crawler.cache.SingletonBufferPool;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.special.GrabSpecialContentSiteList;
import ims.crawler.grab.special.GrabSpecialTimeContent;
import ims.crawler.grab.util.AnalyzerNum;
import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.grab.util.TransMD5;
import ims.crawler.util.BasicJsoupDocumentUtil;
import ims.site.model.ExactUrl;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class BasicBBSGrabPostUrlsThread implements
		Callable<Map<String, Object>> {

	// ����̻���������ʵ��
	private GrabParame grabParame;
	// �û��Զ����������ʵ��
	private GrabUserParame grabUserParame;

	// ��վ��Ϣ��ʵ��
	private Site site;
	// ������Ϣ��ʵ��
	private Theme theme;

	// TODO ���������־ʵ���ʹ������ʵ��

	public BasicBBSGrabPostUrlsThread(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Theme theme) {
		super();
		this.grabParame = grabParame;
		this.grabUserParame = grabUserParame;
		this.site = site;
		this.theme = theme;
	}

	// MD5ת��������
	private static TransMD5 transMD5Obj = new TransMD5();

	// TODO �������Ԫ�صĴ�������ͼ��������Ƿ�Ϊ�գ���ǿ��׳��

	// ��õ�������������б��ҳ��ҳURL
	public Set<ExactUrl> getThemePageUrlSet() {

		// ���������ҳURL����
		Set<ExactUrl> themePageUrlSet = new HashSet<ExactUrl>();
		// ��ҳ�ĵ�һҳ������URL����
		themePageUrlSet.add(new ExactUrl(theme.getThemeUrl(), theme
				.getThemeUrlMD5()));

		// ��������ڷ�ҳ��������û��ҳ������Ҫ�󣨴󲿷��ǳ��ִ�������������ôֻѡȡ��һҳ
		if ((grabParame.getPostListPagerQuery() == null || grabParame
				.getPostListPagerQuery().equals(""))
				|| (grabParame.getPostListNextQuery() == null || grabParame
						.getPostListNextQuery().equals(""))
				|| (grabUserParame.getThemePageNumLimit() == 0)) {

			return themePageUrlSet;
		}

		// �ж�ʱ�����������ǻ���ʱ�仹�ǹ�ȥʱ��㣬��ͳһ����ɹ�ȥʱ���
		String postTimeLimit = null;
		if (grabUserParame.getTimeDeterminer() == 1) {
			if (grabUserParame.getPostTimeRangeLimit() == null
					|| grabUserParame.getPostTimeRangeLimit().equals("")) {
				postTimeLimit = null;
			} else {
				postTimeLimit = AnalyzerTime.backToGoalTime(grabUserParame
						.getPostTimeRangeLimit());
			}
		} else {
			postTimeLimit = grabUserParame.getPostStartTimeLimit() != null ? grabUserParame
					.getPostStartTimeLimit().toString() : null;
		}

		// �������û��ʱ��ʶ��Query����û������ظ�ʱ�����ƣ�����Ϊû������ظ�ʱ������
		if ((grabParame.getPostTimeQuery() == null || grabParame
				.getPostTimeQuery().equals("")) || (postTimeLimit == null)) {
			postTimeLimit = "0";
		}

		// �������ҳ��doc
		Document docEachTheme = BasicJsoupDocumentUtil.getDocument(theme
				.getThemeUrl());
		// ��ֹ����������ԭ����ֵ�ҳ�����ز��ɹ����
		if (docEachTheme == null) {
			return themePageUrlSet;
		}

		// ��ö�Ӧ����ҳ��ķ�ҳdivԪ��
		// ��ȡ��ҳԪ��ʧ�ܣ���ֻ��������ĵ�һҳ
		if (docEachTheme.select(grabParame.getPostListPagerQuery()).size() == 0) {
			return themePageUrlSet;
		}
		Element elePostListPagerDiv = docEachTheme.select(
				grabParame.getPostListPagerQuery()).first();

		// ��¼����ȡ��ҳ��
		int pageNum = 1;
		// ֻҪҳ��û�г������ƣ��ͼ���ץȡ
		while (pageNum <= grabUserParame.getThemePageNumLimit()) {
			// �ж���û����һҳ��ť��֪���Ƿ��ѵ����һҳ
			if (elePostListPagerDiv.select(grabParame.getPostListNextQuery())
					.size() == 0) {
				break;
			}

			// �ж���������ظ�ʱ������
			if (!postTimeLimit.equals("0")) {

				// ��ȡ��ǰҳ���һ�����ӵ�����ظ�ʱ��
				String postTimeThisPage = this.getPostTime(docEachTheme, null);
				// �ж�ҳ��ʱЧ�Ƿ����
				boolean flagTimeFresh = AnalyzerTime.compTimeLimit(
						postTimeThisPage, postTimeLimit);
				if (!flagTimeFresh) {
					break;
				}
			}

			// ���λ�ȡ��һҳ
			Element elePostListNextDiv = elePostListPagerDiv.select(
					grabParame.getPostListNextQuery()).first();
			String postListNextUrl = elePostListNextDiv.attr("abs:href");
			// ΪÿһҳURL����Ψһ��MD5��֤��
			String postListNextUrlMD5 = transMD5Obj.getMD5Code(postListNextUrl);
			themePageUrlSet.add(new ExactUrl(postListNextUrl,
					postListNextUrlMD5));

			// ת����һҳ���ӵ�ץȡ
			pageNum++;
			docEachTheme = BasicJsoupDocumentUtil.getDocument(postListNextUrl);
			// ��ֹ����������ԭ����ֵ�ҳ�����ز��ɹ����
			if (docEachTheme == null) {
				break;
			}
			elePostListPagerDiv = docEachTheme.select(
					grabParame.getPostListPagerQuery()).first();

		}

		return themePageUrlSet;
	}

	// ��õ�������һҳ���е�����������Ϣ����
	public Map<String, Set<Post>> getSpPostSet(String pageUrl) {

		// ׼�����ص�����
		Set<Post> postAddSet = new HashSet<Post>();
		Set<Post> postUpdateSet = new HashSet<Post>();
		Set<Post> postFixSet = new HashSet<Post>();
		Map<String, Set<Post>> postResultMap = new HashMap<String, Set<Post>>();

		// ��ø�ҳdocument
		Document docPage = BasicJsoupDocumentUtil.getDocument(pageUrl);
		// ��ֹ����������ԭ����ֵ�ҳ�����ز��ɹ����
		if (docPage == null) {
			return null;
		}

		// ��������б�divԪ��
		Element elePostListSpDiv = docPage.select(
				grabParame.getPostListSpDivQuery()).first();

		// �������divԪ�ؼ���
		// ���������б�divԪ�ػ�ȡʧ�ܵ����
		if (elePostListSpDiv == null) {
			return null;
		}
		Elements elesPostDiv = elePostListSpDiv.select(grabParame
				.getPostDivQuery());

		// ������div����������ȡ����������Ϣ
		for (Element eleEachPostDiv : elesPostDiv) {
			// �����������
			String postName = eleEachPostDiv.select(
					grabParame.getPostNameQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostNameQuery()).first().text()
					: "unknown";
			// ��ֹ�������ƹ���
			postName = postName.length() >= 50 ? (postName.substring(0, 50) + "......")
					: postName;

			// �����������
			String postUrl = eleEachPostDiv
					.select(grabParame.getPostUrlQuery()).first()
					.attr("abs:href");

			// ������������MD5
			String postUrlMD5 = new TransMD5().getMD5Code(postUrl);

			// ������ӵ����
			String clickNumStr = eleEachPostDiv.select(
					grabParame.getPostClickNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostClickNumQuery()).first().text()
					: "0";
			clickNumStr = AnalyzerNum.getClickNum(clickNumStr);
			// ���������ǩ�Ƿ������֣�������ʽƥ���������ַ�����
			int clickNum = clickNumStr.matches("^\\d+$") ? Integer
					.parseInt(clickNumStr) : 0;

			// ������ӻظ�����
			String replyNumStr = eleEachPostDiv.select(
					grabParame.getPostReplyNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostReplyNumQuery()).first().text()
					: "0";
			replyNumStr = AnalyzerNum.getReplyNum(replyNumStr);
			// ��������ǩͬ����
			int replyNum = replyNumStr.matches("^\\d+$") ? Integer
					.parseInt(replyNumStr) : 0;

			// ��ֹ������ͻظ�����������������ݣ������һ�����ڵ��ڻظ�����
			if (replyNum > clickNum) {
				int temp = replyNum;
				replyNum = clickNum;
				clickNum = temp;
			}

			// BBSû��ת������
			int forwardNum = 0;

			// �����������ظ�ʱ�䣬�Ҳ���ʱ���ǩ�ģ����ã�����ǰʱ�����
			String lastReplyTime = this.getPostTime(null, eleEachPostDiv);

			// ȡ�õ�ǰ����ʱ����Ϊ����ʱ��
			String refreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());

			// �������������ж��Ƿ�Ҫ����post���¼��ϣ�����жϲ����룬ֱ��������ִ����һ��ѭ��
			boolean flagClickNumQualified = (clickNum >= grabUserParame
					.getPostClickNumLimit());
			boolean flagReplyNumQualified = (replyNum >= grabUserParame
					.getPostReplyNumLimit());
			// BBSû��ת������������Ҫ�ж�
			boolean flagForwardNumQualified = true;
			String postTimeLimit = grabUserParame.getTimeDeterminer() == 1 ? AnalyzerTime
					.backToGoalTime(grabUserParame.getPostTimeRangeLimit())
					: grabUserParame.getPostStartTimeLimit().toString();
			boolean flagLastReplyTimeQualified = (AnalyzerTime.compTimeLimit(
					lastReplyTime, postTimeLimit));
			if (!flagClickNumQualified || !flagReplyNumQualified
					|| !flagLastReplyTimeQualified || !flagForwardNumQualified) {
				continue;
			}

			// �����������post����Ĭ����Ҫ����
			Post postPending = new Post(this.site.getSiteId(),
					this.theme.getThemeId(), postName, postUrl, postUrlMD5,
					clickNum, replyNum, forwardNum,
					Timestamp.valueOf(lastReplyTime), 1,
					Timestamp.valueOf(refreshTime), this.theme, this.site);

			// ����������ȡ�ж�
			SingletonBufferPool singletonBufferPool = SingletonBufferPool
					.getSingletonBufferPool();
			if (singletonBufferPool.getPostBufferPool().isEmpty()) {
				postAddSet.add(postPending);

				// TODO delete print
				System.out.println("����Ϊ�գ�����ӽڵ㣺" + postPending.getPostName());
			} else if (singletonBufferPool.checkInBuffer(postPending
					.getPostUrlMD5())) {
				Map<String, Object> compPostParames = singletonBufferPool
						.getPostParames(postPending.getPostUrlMD5());

				// ����������ֵ�Ƿ����ı䣨��equals��Ϊ�ı䣩
				boolean flagClickNumChanged = !((Integer) compPostParames
						.get("clickNum")).equals(postPending.getClickNum());
				boolean flagReplyNumChanged = !((Integer) compPostParames
						.get("replyNum")).equals(postPending.getReplyNum());
				// BBSû��ת������������Ҫ�ж�
				boolean flagForwardNumChanged = false;
				boolean flagLastReplyTimeChanged = !compPostParames.get(
						"lastReplyTime").equals(postPending.getLastReplyTime());

				// ����ظ������ͻظ�ʱ�������һ�һ��������updateSet
				if (flagReplyNumChanged || flagLastReplyTimeChanged) {
					postUpdateSet.add(postPending);

					// TODO delete print
					System.out.println("���½ڵ㣺" + postPending.getPostName());
				} else if (flagClickNumChanged || flagForwardNumChanged) {
					// ����ظ������ͻظ�ʱ�䶼û�䣬����ת�������ߵ�������ˣ�˵������û�䣬��Ǳ���
					postFixSet.add(postPending);

					// TODO delete print
					System.out.println("�޸Ľڵ㣺" + postPending.getPostName());
				} else {

					// TODO delete print
					System.out.println("�ڵ��������µģ�" + postPending.getPostName());
				}

			} else {
				postAddSet.add(postPending);

				// TODO delete print
				System.out.println("���治Ϊ�գ�����ӽڵ㣺" + postPending.getPostName());
			}
		}

		// ���������ӳ�伯��
		postResultMap.put("add", postAddSet);
		postResultMap.put("update", postUpdateSet);
		postResultMap.put("fix", postFixSet);

		return postResultMap;
	}

	/**
	 * д�ɶ��ھ�̬�����վ���飬ȡ��ʱ���ר�÷���������doc����ele�����û�д����ֵ
	 * 
	 * @param document
	 * @param element
	 * @return
	 */
	public String getPostTime(Document document, Element element) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		if (document != null && element == null) {

			// �������ʱ���divԪ��
			Element elePostTime = document.select(
					this.grabParame.getPostTimeQuery()).size() != 0 ? document
					.select(this.grabParame.getPostTimeQuery()).first() : null;

			if (elePostTime != null) {
				// ��̳����Դ��ж�������
				if (GrabSpecialContentSiteList
						.isInSpecialTimeSiteList(this.site)) {
					postTimeStr = GrabSpecialTimeContent.getSpecialTimeContent(
							elePostTime, this.site);
				} else {
					postTimeStr = elePostTime.text();
				}
			}
		} else if (document == null && element != null) {

			// �������ʱ���divԪ��
			Element elePostTime = element.select(
					this.grabParame.getPostTimeQuery()).size() != 0 ? element
					.select(this.grabParame.getPostTimeQuery()).first() : null;

			if (elePostTime != null) {
				// qq��̳����Դ�
				if (GrabSpecialContentSiteList
						.isInSpecialTimeSiteList(this.site)) {
					postTimeStr = GrabSpecialTimeContent.getSpecialTimeContent(
							elePostTime, this.site);
				} else {
					postTimeStr = elePostTime.text();
				}
			}
		}

		// ��ʱ���ַ���ת��Ϊ��׼��ʱ���ʽ
		postTimeStr = AnalyzerTime.transStandardTimeFormat(postTimeStr);

		// ����ʱ��
		return postTimeStr;
	}

	public Map<String, Object> call() throws Exception {

		// ׼�����ص�����
		Set<Post> themePostAddSet = new HashSet<Post>();
		Set<Post> themePostUpdateSet = new HashSet<Post>();
		Set<Post> themePostFixSet = new HashSet<Post>();
		Map<String, Object> themePostResultMap = new HashMap<String, Object>();

		// ��ִ������֮ǰ�����ȼ���������״̬λ����������Ϊtrue����ǰ��������ִ������
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// ��ȡÿҳ������
		Set<ExactUrl> themePageUrlSet = this.getThemePageUrlSet();
		if (themePageUrlSet == null) {
			// TODO д�������쳣��־
			return null;
		}

		// ����ÿҳ�����ӻ�ȡ�ڵ�ӳ�����
		for (ExactUrl eachPageUrl : themePageUrlSet) {

			// ÿ��ѭ��֮ǰ�����ȼ���������״̬λ����������Ϊtrue����ǰ��������ִ������
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// ͳһ��ý���������ظ�����
			Map<String, Set<Post>> pageResultMap = this
					.getSpPostSet(eachPageUrl.getUrl());

			if (pageResultMap == null) {
				continue;
			}

			Set<Post> pagePostAddSet = pageResultMap.get("add");
			Set<Post> pagePostUpdateSet = pageResultMap.get("update");
			Set<Post> pagePostFixSet = pageResultMap.get("updateNum");
			if (pagePostAddSet != null) {
				themePostAddSet.addAll(pagePostAddSet);
			}
			if (pagePostUpdateSet != null) {
				themePostUpdateSet.addAll(pagePostUpdateSet);
			}
			if (pagePostFixSet != null) {
				themePostFixSet.addAll(pagePostFixSet);
			}
		}

		themePostResultMap.put("add", themePostAddSet);
		themePostResultMap.put("update", themePostUpdateSet);
		themePostResultMap.put("fix", themePostFixSet);
		themePostResultMap.put("theme", this.theme);
		themePostResultMap.put("site", this.site);

		return themePostResultMap;
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
	}

	public GrabUserParame getGrabUserParame() {
		return grabUserParame;
	}

	public void setGrabUserParame(GrabUserParame grabUserParame) {
		this.grabUserParame = grabUserParame;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}
