package ims.crawler.grab.grabImpl;

import ims.crawler.cache.SingletonBufferPool;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.util.AnalyzerNum;
import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.grab.util.TransMD5;
import ims.crawler.util.HcLoginJsoupDocumentUtil;
import ims.site.model.ExactUrl;
import ims.site.model.ExtraParame;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HcWeiboGrabPostUrlsThread implements Callable<Map<String, Object>> {

	// ����̻���������ʵ��
	private GrabParame grabParame;
	// �û��Զ����������ʵ��
	private GrabUserParame grabUserParame;

	// ��վ��Ϣ��ʵ��
	private Site site;
	// ������Ϣ��ʵ��
	private Theme theme;

	// �����������¼��Ϣ
	private ExtraParame extraParame;

	public HcWeiboGrabPostUrlsThread(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Theme theme,
			ExtraParame extraParame) {
		super();
		this.grabParame = grabParame;
		this.grabUserParame = grabUserParame;
		this.site = site;
		this.theme = theme;
		this.extraParame = extraParame;
	}

	// MD5ת��������
	private static TransMD5 transMD5Obj = new TransMD5();

	// ��õ�������������б���ҳ��ҳURL
	public Set<ExactUrl> getThemePageUrlSet() {

		// ���������ҳURL����
		Set<ExactUrl> themePageUrlSet = new HashSet<ExactUrl>();
		// ��ҳ�ĵ�һҳ������URL����
		themePageUrlSet.add(new ExactUrl(theme.getThemeUrl(), theme
				.getThemeUrlMD5()));

		// ����΢��ȫ������΢��ҳ�涼ץȡ

		// ΢���ɼ�����ʱ���ҳ������
		List<String> restUrls = new ArrayList<String>();
		if (theme.getThemeUrl().contains("weibo")) {
			restUrls = this.getSinaWeiboPageUrls();
		}
		for (String url : restUrls) {
			String urlMD5 = transMD5Obj.getMD5Code(url);
			themePageUrlSet.add(new ExactUrl(url, urlMD5));
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

		// ��ø���ҳdocument
		Document docPage = HcLoginJsoupDocumentUtil.getDocument(pageUrl,
				this.extraParame, pageUrl);
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
			// ���΢������
			String postName = eleEachPostDiv.select(
					grabParame.getPostNameQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostNameQuery()).first().text()
					: "unknown";
			// ��ֹ΢�����ƹ���
			postName = postName.length() >= 20 ? (postName.substring(0, 20) + "...")
					: postName;

			// ���΢������
			String postUrl = eleEachPostDiv
					.select(grabParame.getPostUrlQuery()).first()
					.attr("abs:href");

			// ����΢������MD5
			String postUrlMD5 = new TransMD5().getMD5Code(postUrl);

			// ΢��û�е������
			int clickNum = 0;

			// ���΢���ظ�����
			String replyNumStr = eleEachPostDiv.select(
					grabParame.getPostReplyNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostReplyNumQuery()).first().text()
					: "0";
			replyNumStr = AnalyzerNum.getReplyNum(replyNumStr);
			// ���ظ�����ǩ�Ƿ������֣��������ʽƥ���������ַ�����
			int replyNum = replyNumStr.matches("^\\d+$") ? Integer
					.parseInt(replyNumStr) : 0;

			// ���΢��ת������
			String forwardNumStr = eleEachPostDiv.select(
					grabParame.getPostForwardNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostForwardNumQuery()).first().text()
					: "0";
			int forwardNum = forwardNumStr.matches("^\\d+$") ? Integer
					.parseInt(forwardNumStr) : 0;

			// �����������ظ�ʱ�䣬�Ҳ���ʱ���ǩ�ģ����ã�����ǰʱ�����
			// TODO time special
			String lastReplyTime = this.getPostTime(null, eleEachPostDiv);

			// ȡ�õ�ǰ����ʱ����Ϊ����ʱ��
			String refreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());

			// �������������ж��Ƿ�Ҫ����post���¼��ϣ�����жϲ����룬ֱ��������ִ����һ��ѭ��
			// ΢��û�е������������Ҫ�ж�
			boolean flagClickNumQualified = true;
			boolean flagReplyNumQualified = (replyNum >= grabUserParame
					.getPostReplyNumLimit());
			boolean flagForwardNumQualified = (forwardNum >= grabUserParame
					.getPostForwardNumLimit());
			String postTimeLimit = grabUserParame.getTimeDeterminer() == 1 ? AnalyzerTime
					.backToGoalTime(grabUserParame.getPostTimeRangeLimit())
					: grabUserParame.getPostStartTimeLimit().toString();
			boolean flagLastReplyTimeQualified = (AnalyzerTime.compTimeLimit(
					lastReplyTime, postTimeLimit));
			if (!flagClickNumQualified || !flagReplyNumQualified
					|| !flagLastReplyTimeQualified || !flagForwardNumQualified) {
				continue;
			}

			// ������������post����Ĭ����Ҫ����
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
				System.out.println("����Ϊ�գ������ӽڵ㣺" + postPending.getPostName());
			} else if (singletonBufferPool.checkInBuffer(postPending
					.getPostUrlMD5())) {
				Map<String, Object> compPostParames = singletonBufferPool
						.getPostParames(postPending.getPostUrlMD5());

				// ����������ֵ�Ƿ����ı䣨��equals��Ϊ�ı䣩
				// ΢��û�е����������Ҫ�ж�
				boolean flagClickNumChanged = false;
				boolean flagReplyNumChanged = !((Integer) compPostParames
						.get("replyNum")).equals(postPending.getReplyNum());
				boolean flagForwardNumChanged = !((Integer) compPostParames
						.get("forwardNum")).equals(postPending.getForwardNum());
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
				System.out.println("���治Ϊ�գ������ӽڵ㣺" + postPending.getPostName());
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

			postTimeStr = elePostTime.text();
		} else if (document == null && element != null) {

			// �������ʱ���divԪ��
			Element elePostTime = element.select(
					this.grabParame.getPostTimeQuery()).size() != 0 ? element
					.select(this.grabParame.getPostTimeQuery()).first() : null;

			postTimeStr = elePostTime.ownText();
		}

		// ��ʱ���ַ���ת��Ϊ��׼��ʱ���ʽ
		System.out.println(postTimeStr);
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

			// ͳһ��ý���������ظ�ʹ��
			Map<String, Set<Post>> pageResultMap = this
					.getSpPostSet(eachPageUrl.getUrl());
			if (pageResultMap == null) {
				continue;
			}

			Set<Post> pagePostAddSet = pageResultMap.get("add");
			Set<Post> pagePostUpdateSet = pageResultMap.get("update");
			Set<Post> pagePostFixSet = pageResultMap.get("fix");
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

	// ���sina΢������΢������ҳ���url
	public List<String> getSinaWeiboPageUrls() {

		List<String> urls = new ArrayList<String>();

		String urlModel = "http://weibo.cn/pub/topmblog?page=";
		int pageNum = 3;
		for (int i = 2; i <= pageNum; i++) {
			String url = urlModel + String.valueOf(i);
			urls.add(url);
		}

		return urls;
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

	public ExtraParame getExtraParame() {
		return extraParame;
	}

	public void setExtraParame(ExtraParame extraParame) {
		this.extraParame = extraParame;
	}

}