package ims.crawler.fetch.fetchImpl;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.fetch.special.FetchJsonPostTime_BaiduTieBa;
import ims.crawler.fetch.special.FetchSpecialContentSiteList;
import ims.crawler.fetch.special.FetchSpecialTimeContent;
import ims.crawler.grab.util.AnalyzerNum;
import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.util.HandleFetchResult;
import ims.crawler.util.HandleLogResult;
import ims.crawler.util.HcJsoupDocumentUtil;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.FetchPagerObj;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HcBBSFetchContentThread implements Callable<Map<String, Object>> {

	// �������̻�����ʵ��
	private FetchParame fetchParame;
	// ��Ҫ�õ��û��Զ������ʵ��
	private GrabUserParame grabUserParame;
	// ��ҳ��������ʵ��
	private FetchPagerObj fetchPagerObj;
	// ������վʵ��
	private Site site;
	// ��������ʵ��
	private TaskLog taskLog;
	// ��������ʵ��
	private Theme theme;
	// ������Ӧ�ڵ�ʵ��
	private Set<Post> posts;
	// ҳ���ĵ�������
	private Document docPostFirstPage;

	public HcBBSFetchContentThread(FetchParame fetchParame,
			GrabUserParame grabUserParame, FetchPagerObj fetchPagerObj,
			Site site, TaskLog taskLog, Theme theme, Set<Post> posts) {
		super();
		this.fetchParame = fetchParame;
		this.grabUserParame = grabUserParame;
		this.fetchPagerObj = fetchPagerObj;
		this.site = site;
		this.taskLog = taskLog;
		this.theme = theme;
		this.posts = posts;
	}

	/*
	 * ���·���ע�ͣ���*����־��ʾֱ�ӻ�ȡ���ݣ��޴˱�־��ʾ�˷�������Ϊ����������׼��
	 */

	/**
	 * ��ȡ�ڵ�������飨*��
	 * 
	 * @param baNameQuery
	 * @return
	 */
	public String getBaName(String baNameQuery) {
		String strBaName = "unknown";

		// Ӧ��������ȱʧ�����
		if (baNameQuery == null || baNameQuery.equals("")) {
			strBaName = "\r\n";

			return strBaName;
		}

		try {
			Element eleBaName = this.docPostFirstPage.select(baNameQuery)
					.first();
			strBaName = eleBaName != null ? eleBaName.text() : strBaName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			strBaName = "unknown";
		}

		// TODO delete print
		System.out.println(strBaName);

		return strBaName;
	}

	/**
	 * ��ȡ�ڵ���⣨*��
	 * 
	 * @param titleQuery
	 * @return
	 */
	public String getTitle(String titleQuery) {
		String strTitle = "unknown";

		// Ӧ�����ӱ���ȱʧ�����
		if (titleQuery == null || titleQuery.equals("")) {
			strTitle = "\r\n";

			return strTitle;
		}

		try {
			Element eleTitle = this.docPostFirstPage.select(titleQuery).first();
			strTitle = eleTitle != null ? eleTitle.text() : strTitle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			strTitle = "unknown";
		}

		// TODO delete print
		System.out.println(strTitle);

		return strTitle;
	}

	/**
	 * ��ȡ�ڵ��Ķ��������������*��
	 * 
	 * @param readNumQuery
	 * @return
	 */
	public int getReadNum(String readNumQuery) {
		String strReadNum = "0";

		if (readNumQuery == null || readNumQuery.equals("")) {
			return 0;
		}

		try {
			Element eleReadNum = this.docPostFirstPage.select(readNumQuery)
					.first();
			strReadNum = eleReadNum != null ? eleReadNum.text() : strReadNum;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			strReadNum = "0";
		}

		// ת���ɱ�׼���ָ�ʽ
		strReadNum = AnalyzerNum.ThransNum(strReadNum);
		int readNum = Integer.parseInt(strReadNum);
		return readNum;
	}

	/**
	 * ��ȡ�ڵ����ۣ��ظ���������*��
	 * 
	 * @param commentNumQuery
	 * @return
	 */
	public int getCommentNum(String commentNumQuery) {
		String strCommentNum = "0";

		if (commentNumQuery == null || commentNumQuery.equals("")) {
			return 0;
		}

		try {
			Element eleCommentNum = this.docPostFirstPage.select(
					commentNumQuery).first();
			strCommentNum = eleCommentNum != null ? eleCommentNum.text()
					: strCommentNum;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			strCommentNum = "0";
		}

		// ת���ɱ�׼���ָ�ʽ
		strCommentNum = AnalyzerNum.ThransNum(strCommentNum);
		int commentNum = Integer.parseInt(strCommentNum);
		return commentNum;
	}

	/**
	 * ��ýڵ�ת������*��
	 * 
	 * @param forwardNumQuery
	 * @return
	 */
	public int getForwardNum(String forwardNumQuery) {
		String strForwardNum = "0";

		if (forwardNumQuery == null || forwardNumQuery.equals("")) {
			return 0;
		}

		try {
			Element eleForwardNum = this.docPostFirstPage.select(
					forwardNumQuery).first();
			strForwardNum = eleForwardNum != null ? eleForwardNum.text()
					: strForwardNum;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			strForwardNum = "0";
		}

		// ת���ɱ�׼���ָ�ʽ
		strForwardNum = AnalyzerNum.ThransNum(strForwardNum);
		int forwardNum = Integer.parseInt(strForwardNum);
		return forwardNum;
	}

	/**
	 * ��ȡ���Ӹ���ҳ���ӣ����ϣ����������
	 * 
	 * @param fetchPagerObj
	 * @param pagerQuery
	 * @return
	 */
	public List<String> getPagerUrl(String fetchPagerObj, String postUrl,
			String pagerQuery) {
		List<String> listPagerUrl = new ArrayList<String>();
		// �ȼ���������ҳ�����ӣ���ֹ��ҳ������������
		listPagerUrl.add(postUrl);

		Class<?> classFetchPager = null;

		// ������ƻ�ȡ�����
		try {
			classFetchPager = Class.forName("ims.crawler.fetch.util.pagerImpl."
					+ fetchPagerObj);

			Method methodGetPagerUrls = classFetchPager.getMethod(
					"getPagerUrls", String.class, String.class);
			listPagerUrl = (List<String>) methodGetPagerUrls.invoke(
					classFetchPager.newInstance(), postUrl, pagerQuery);

		} catch (Exception e) {
			e.printStackTrace();

			return listPagerUrl;
		}

		// TODO delete print
		for (String string : listPagerUrl) {
			System.out.println(string);
		}

		return listPagerUrl;
	}

	/**
	 * ��ȡʱ���ַ����ĳ��󷽷�������eleԪ�ض����queryʶ�����
	 * 
	 * @param document
	 * @param element
	 * @return
	 */
	public String getContentTime(Element element) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		if (element != null) {
			// ����Դ���վ���жϲ�����
			if (FetchSpecialContentSiteList.isInSpecialTimeSiteList(this.site)) {
				postTimeStr = FetchSpecialTimeContent.getSpecialTimeContent(
						element, this.site);
			} else {
				postTimeStr = element.text();
			}
		}

		// ��ʱ���ַ���ת��Ϊ��׼��ʱ���ʽ
		postTimeStr = AnalyzerTime.transStandardTimeFormat(postTimeStr);

		// ����ʱ��
		return postTimeStr;
	}

	/**
	 * ��ȡÿ�������Ļظ���Ϣ
	 * 
	 * @param elePostDiv
	 * @param replyDivQuery
	 * @param replyContentQuery
	 * @param replyAuthorQuery
	 * @param replyTimeQuery
	 * @return
	 */
	public List<Map<String, Object>> getReplyEachArticle(Element elePostDiv) {
		// ׼��post��ƪarticle�Ļظ�ӳ����Ϣ
		List<Map<String, Object>> eachArticleReplyMaps = new ArrayList<Map<String, Object>>();

		// Ӧ��ƽ�лظ����������Ҷ�ӻظ���
		if ((fetchParame.getReplyDivQuery() == null || fetchParame
				.getReplyDivQuery().equals(""))
				&& (fetchParame.getReplyContentQuery() == null || fetchParame
						.getReplyContentQuery().equals(""))
				&& (fetchParame.getReplyAuthorQuery() == null || fetchParame
						.getReplyAuthorQuery().equals(""))
				&& (fetchParame.getReplyTimeQuery() == null || fetchParame
						.getReplyTimeQuery().equals(""))) {

			return null;
		}

		// ��ȡ�����ظ�Ԫ��
		Elements elesReplyDiv = elePostDiv.select(fetchParame
				.getReplyDivQuery());
		if (elesReplyDiv == null) {
			return null;
		}

		// �ж��Ƿ�ظ�ȫ��Ϊ��
		boolean flagReplyNull = true;
		for (Element eleReplyDiv : elesReplyDiv) {

			// ��ȡ�ظ�������Ϣ
			Element eleReplyAuthorDiv = eleReplyDiv.select(
					fetchParame.getReplyAuthorQuery()).first();
			String strReplyAuthor = eleReplyAuthorDiv != null ? eleReplyAuthorDiv
					.ownText()
					: "";

			// ��ȡ�ظ�������Ϣ
			Element eleReplyContentDiv = eleReplyDiv.select(
					fetchParame.getReplyContentQuery()).first();
			String strReplyContent = eleReplyContentDiv != null ? eleReplyContentDiv
					.text()
					: "";

			// TODO delete print
			System.out.println(strReplyContent);

			// ��ȡ�ظ�ʱ����Ϣ
			Element eleReplyTimeDiv = eleReplyDiv.select(
					fetchParame.getReplyTimeQuery()).first();
			String strReplyTime = this.getContentTime(eleReplyTimeDiv);
			strReplyTime = AnalyzerTime.transStandardTimeFormat(strReplyTime);

			// �����������ĸ������������
			Map<String, Object> replyMap = new HashMap<String, Object>();
			// �ж��Ƿ���ַǿյ����
			if (!strReplyAuthor.equals("") || !strReplyContent.equals("")
					|| !strReplyTime.equals("")) {

				// �ж����ʱ�䲻�ٽ�����Χ֮�ھͲ������鱨��ӳ�������
				if (!AnalyzerTime.compTimeLimit(grabUserParame
						.getPostEndTimeLimit().toString(), strReplyTime)) {
					continue;
				}

				// һ���лظ�ӳ�������룬��ǻظ�ӳ�����Ϊ��
				flagReplyNull = false;

				if (!strReplyAuthor.equals("")) {
					replyMap.put("replyAuthor", strReplyAuthor);
				}
				if (!strReplyContent.equals("")) {
					replyMap.put("replyContent", strReplyContent);
				}
				if (!strReplyTime.equals("")) {
					replyMap.put("replyTime", strReplyTime);
				}

				eachArticleReplyMaps.add(replyMap);

			} else {
				continue;
			}
		}

		// ���û�з���һ���ǿյĻظ���Ϣ��˵������û�лظ���Ϣ����ô�Ͳ����ûظ���Ϣ
		if (flagReplyNull) {
			eachArticleReplyMaps = null;
		}

		return eachArticleReplyMaps;
	}

	/**
	 * ��ȡÿ����ҳ����������
	 * 
	 * @param pageUrl
	 * @param postDivQuery
	 * @param postContentQuery
	 * @param postAuthorQuery
	 * @param postTimeQuery
	 * @param replyDivQuery
	 * @param replyContentQuery
	 * @param replyAuthorQuery
	 * @param replyTimeQuery
	 * @return
	 */
	public List<Map<String, Object>> getArticleEachPage(String pageUrl) {
		// ׼�����¶���ӳ����Ϣ�б�
		List<Map<String, Object>> eachPageArticleMaps = new ArrayList<Map<String, Object>>();

		// ��ֹ�����쳣���
		if ((fetchParame.getPostDivQuery() == null || fetchParame
				.getPostDivQuery().equals(""))
				&& (fetchParame.getPostContentQuery() == null || fetchParame
						.getPostContentQuery().equals(""))
				&& (fetchParame.getPostAuthorQuery() == null || fetchParame
						.getPostAuthorQuery().equals(""))
				&& (fetchParame.getPostTimeQuery() == null || fetchParame
						.getPostTimeQuery().equals(""))) {

			return null;
		}

		// ��ȡ����ÿҳ���ĵ���Ϣ
		Document docEachPage = HcJsoupDocumentUtil.getDocument(pageUrl,
				this.site.getSeedUrl(), this.site.getEnCode());
		// ��ȡÿ������div��Ԫ�ؼ���
		Elements elesArticleDiv = (docEachPage == null ? null : docEachPage
				.select(fetchParame.getPostDivQuery()));
		if (elesArticleDiv == null) {
			return null;
		}

		for (Element eleArticleDiv : elesArticleDiv) {

			// ��ȡ����������Ϣ
			Element eleArticleAuthorDiv = eleArticleDiv.select(
					fetchParame.getPostAuthorQuery()).first();
			String strArticleAuthor = eleArticleAuthorDiv != null ? eleArticleAuthorDiv
					.ownText()
					: "";

			// ��ȡ����������Ϣ
			Element eleArticleContentDiv = eleArticleDiv.select(
					fetchParame.getPostContentQuery()).first();
			String strArticleContent = eleArticleContentDiv != null ? eleArticleContentDiv
					.text()
					: "";

			// TODO delete print
			System.out.println(strArticleContent);

			// ��ȡ����ʱ�估¥����Ϣ
			Element eleArticleTimeDiv = eleArticleDiv.select(
					fetchParame.getPostTimeQuery()).first();
			String strArticleTime = this.getContentTime(eleArticleTimeDiv);
			// �ٶȵķ���ʱ����javascript����ʾ����Ҫ������д����json�ķ���
			if (pageUrl.indexOf("baidu") != -1) {
				strArticleTime = (FetchJsonPostTime_BaiduTieBa
						.getBaiduTieBaPostTime(eleArticleDiv));
			}
			// ͨ����̬����ת���ɱ�׼��ʽ
			strArticleTime = AnalyzerTime
					.transStandardTimeFormat(strArticleTime);

			// ��ȡ�ظ�ģ�飨ͨ�����õ����ķ���ʵ�֣�
			List<Map<String, Object>> eachArticleReplyMaps = getReplyEachArticle(eleArticleDiv);

			// �����������ĸ������������
			Map<String, Object> articleMap = new HashMap<String, Object>();
			// �ж����������Ƿ�
			if (!strArticleAuthor.equals("") || !strArticleContent.equals("")
					|| !strArticleTime.equals("")) {
				if (!strArticleAuthor.equals("")) {
					articleMap.put("articleAuthor", strArticleAuthor);
				}
				if (!strArticleContent.equals("")) {
					articleMap.put("articleContent", strArticleContent);
				} else {
					// ����Ϊ�գ��ٶ���ͼƬ
					strArticleContent = "image";
					articleMap.put("articleContent", strArticleContent);
				}
				if (!strArticleTime.equals("")) {
					articleMap.put("articleTime", strArticleTime);
				}
				if (eachArticleReplyMaps != null) {
					articleMap.put("articleReply", eachArticleReplyMaps);
				}

				eachPageArticleMaps.add(articleMap);
			} else {
				continue;
			}

		}

		return eachPageArticleMaps;
	}

	/**
	 * ��ȡ���з�ҳ���������ݣ�*��
	 * 
	 * @param fetchPagerMethod
	 * @param pagerQuery
	 * @param postDivQuery
	 * @param postContentQuery
	 * @param postAuthorQuery
	 * @param postTimeQuery
	 * @param replyDivQuery
	 * @param replyContentQuery
	 * @param replyAuthorQuery
	 * @param replyTimeQuery
	 * @return
	 */
	public List<Map<String, Object>> getArticleAllPage(
			FetchPagerObj fetchPagerObj, Post post) {
		// ׼������ҳ�������ӳ���б���Ϣ
		List<Map<String, Object>> allArticleMaps = new ArrayList<Map<String, Object>>();

		// ��ȡĳһ��������ҳ��������б�
		List<String> pageUrlList = getPagerUrl(fetchPagerObj
				.getFetchPagerObjName(), post.getPostUrl(), fetchParame
				.getPagerQuery());

		// ���������б���һ����ҳ��
		for (String eachPageUrl : pageUrlList) {
			List<Map<String, Object>> eachPageArticleMaps = getArticleEachPage(eachPageUrl);

			if (eachPageArticleMaps != null) {
				allArticleMaps.addAll(eachPageArticleMaps);
			}
		}

		if (allArticleMaps.size() == 0) {
			return null;
		}

		return allArticleMaps;
	}

	public Map<String, Object> call() throws Exception {
		// TODO hcJsoupDocument

		// ��spring�л�ȡfetch�����������log����������
		HandleFetchResult handleFetchResult = (HandleFetchResult) ApplicationContextFactory.appContext
				.getBean("handleFetchResult");
		HandleLogResult handleLogResult = (HandleLogResult) ApplicationContextFactory.appContext
				.getBean("handleLogResult");

		int fetchNum = 0;
		int fetchSuccNum = 0;

		for (Post post : getPosts()) {

			// ÿ��ѭ��֮ǰ�����ȼ���������״̬λ����������Ϊtrue����ǰ��������ִ������
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// ��ʼ����һҳ��document
			this.docPostFirstPage = HcJsoupDocumentUtil.getDocument(post
					.getPostUrl(), this.site.getSeedUrl(), this.site
					.getEnCode());

			// ׼���ڵ����е�ӳ������
			Map<String, Object> postAllContentMap = new HashMap<String, Object>();

			// ���ø�������ýڵ��Ӧ������
			int siteId = site.getSiteId();
			String taskLogId = taskLog.getTaskLogId();
			int postId = post.getPostId();
			String postUrlMD5 = post.getPostUrlMD5();
			String baName = getBaName(fetchParame.getBaNameQuery());
			String title = getTitle(fetchParame.getTitleQuery());
			int readNum = getReadNum(fetchParame.getReadNumQuery());
			int commentNum = getCommentNum(fetchParame.getCommentNumQuery());
			int forwardNum = getForwardNum(fetchParame.getForwardNumQuery());

			List<Map<String, Object>> articleMaps = getArticleAllPage(
					fetchParame.getFetchPagerObj(), post);

			if (articleMaps == null || articleMaps.size() == 0) {
				// ����ʧЧ��ҳ��
				handleFetchResult.fixFailurePost(post);
				continue;
			}

			postAllContentMap.put("siteId", siteId);
			postAllContentMap.put("taskLogId", taskLogId);
			postAllContentMap.put("postId", postId);
			postAllContentMap.put("postUrlMD5", postUrlMD5);
			postAllContentMap.put("baName", baName);
			postAllContentMap.put("title", title);
			postAllContentMap.put("readNum", readNum);
			postAllContentMap.put("commentNum", commentNum);
			postAllContentMap.put("forwardNum", forwardNum);
			postAllContentMap.put("article", articleMaps);

			// �����Ѿ���������ҳ�棨�����ٴα�ˢ�£��������ظ�����
			handleFetchResult.handleFetchedPost(post);

			fetchNum++;
			if (postAllContentMap != null) {

				try {
					handleFetchResult.refreshMongoContent(postAllContentMap,
							this.site);
					// ��Ƶ����ˢ����־��������
					handleLogResult
							.refreshSiteLogFetchRealTimeInThread(this.site);

					fetchSuccNum++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// ׼�����صĽ����ɹ�����
		Map<String, Object> contentResultMap = new HashMap<String, Object>();
		contentResultMap.put("fetchNum", fetchNum);
		contentResultMap.put("fetchSuccNum", fetchSuccNum);
		contentResultMap.put("site", this.site);
		contentResultMap.put("theme", this.theme);

		return contentResultMap;
	}

	public FetchParame getFetchParame() {
		return fetchParame;
	}

	public void setFetchParame(FetchParame fetchParame) {
		this.fetchParame = fetchParame;
	}

	public GrabUserParame getGrabUserParame() {
		return grabUserParame;
	}

	public void setGrabUserParame(GrabUserParame grabUserParame) {
		this.grabUserParame = grabUserParame;
	}

	public FetchPagerObj getFetchPagerObj() {
		return fetchPagerObj;
	}

	public void setFetchPagerObj(FetchPagerObj fetchPagerObj) {
		this.fetchPagerObj = fetchPagerObj;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

}
