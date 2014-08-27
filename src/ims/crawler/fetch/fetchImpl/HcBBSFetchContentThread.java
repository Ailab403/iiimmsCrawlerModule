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

	// 解析器固话参数实体
	private FetchParame fetchParame;
	// 需要用到用户自定义参数实体
	private GrabUserParame grabUserParame;
	// 分页分析方法实体
	private FetchPagerObj fetchPagerObj;
	// 所属网站实体
	private Site site;
	// 所属任务实体
	private TaskLog taskLog;
	// 所属主题实体
	private Theme theme;
	// 解析对应节点实体
	private Set<Post> posts;
	// 页面文档加载项
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
	 * 以下方法注释，（*）标志表示直接获取内容，无此标志表示此方法是作为其他方法的准备
	 */

	/**
	 * 获取节点所属板块（*）
	 * 
	 * @param baNameQuery
	 * @return
	 */
	public String getBaName(String baNameQuery) {
		String strBaName = "unknown";

		// 应对主题名缺失的情况
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
	 * 获取节点标题（*）
	 * 
	 * @param titleQuery
	 * @return
	 */
	public String getTitle(String titleQuery) {
		String strTitle = "unknown";

		// 应对贴子标题缺失的情况
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
	 * 获取节点阅读（点击）数量（*）
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

		// 转化成标准数字格式
		strReadNum = AnalyzerNum.ThransNum(strReadNum);
		int readNum = Integer.parseInt(strReadNum);
		return readNum;
	}

	/**
	 * 获取节点评论（回复）数量（*）
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

		// 转化成标准数字格式
		strCommentNum = AnalyzerNum.ThransNum(strCommentNum);
		int commentNum = Integer.parseInt(strCommentNum);
		return commentNum;
	}

	/**
	 * 获得节点转发量（*）
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

		// 转化成标准数字格式
		strForwardNum = AnalyzerNum.ThransNum(strForwardNum);
		int forwardNum = Integer.parseInt(strForwardNum);
		return forwardNum;
	}

	/**
	 * 获取帖子各分页链接（集合），反射机制
	 * 
	 * @param fetchPagerObj
	 * @param pagerQuery
	 * @return
	 */
	public List<String> getPagerUrl(String fetchPagerObj, String postUrl,
			String pagerQuery) {
		List<String> listPagerUrl = new ArrayList<String>();
		// 先加上自生的页面链接，防止分页分析出错的情况
		listPagerUrl.add(postUrl);

		Class<?> classFetchPager = null;

		// 反射机制获取类对象
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
	 * 获取时间字符串的抽象方法，传入ele元素对象和query识别参数
	 * 
	 * @param document
	 * @param element
	 * @return
	 */
	public String getContentTime(Element element) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		if (element != null) {
			// 特殊对待的站点判断并处理
			if (FetchSpecialContentSiteList.isInSpecialTimeSiteList(this.site)) {
				postTimeStr = FetchSpecialTimeContent.getSpecialTimeContent(
						element, this.site);
			} else {
				postTimeStr = element.text();
			}
		}

		// 将时间字符串转换为标准的时间格式
		postTimeStr = AnalyzerTime.transStandardTimeFormat(postTimeStr);

		// 返回时间
		return postTimeStr;
	}

	/**
	 * 获取每个跟帖的回复信息
	 * 
	 * @param elePostDiv
	 * @param replyDivQuery
	 * @param replyContentQuery
	 * @param replyAuthorQuery
	 * @param replyTimeQuery
	 * @return
	 */
	public List<Map<String, Object>> getReplyEachArticle(Element elePostDiv) {
		// 准备post各篇article的回复映射信息
		List<Map<String, Object>> eachArticleReplyMaps = new ArrayList<Map<String, Object>>();

		// 应对平行回复的情况（无叶子回复）
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

		// 获取单个回复元素
		Elements elesReplyDiv = elePostDiv.select(fetchParame
				.getReplyDivQuery());
		if (elesReplyDiv == null) {
			return null;
		}

		// 判断是否回复全部为空
		boolean flagReplyNull = true;
		for (Element eleReplyDiv : elesReplyDiv) {

			// 获取回复作者信息
			Element eleReplyAuthorDiv = eleReplyDiv.select(
					fetchParame.getReplyAuthorQuery()).first();
			String strReplyAuthor = eleReplyAuthorDiv != null ? eleReplyAuthorDiv
					.ownText()
					: "";

			// 获取回复内容信息
			Element eleReplyContentDiv = eleReplyDiv.select(
					fetchParame.getReplyContentQuery()).first();
			String strReplyContent = eleReplyContentDiv != null ? eleReplyContentDiv
					.text()
					: "";

			// TODO delete print
			System.out.println(strReplyContent);

			// 获取回复时间信息
			Element eleReplyTimeDiv = eleReplyDiv.select(
					fetchParame.getReplyTimeQuery()).first();
			String strReplyTime = this.getContentTime(eleReplyTimeDiv);
			strReplyTime = AnalyzerTime.transStandardTimeFormat(strReplyTime);

			// 将分析出来的各部分内容组合
			Map<String, Object> replyMap = new HashMap<String, Object>();
			// 判断是否出现非空的情况
			if (!strReplyAuthor.equals("") || !strReplyContent.equals("")
					|| !strReplyTime.equals("")) {

				// 判断如果时间不再截至范围之内就不加入情报库映射对象中
				if (!AnalyzerTime.compTimeLimit(grabUserParame
						.getPostEndTimeLimit().toString(), strReplyTime)) {
					continue;
				}

				// 一旦有回复映射对象存入，标记回复映射对象不为空
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

		// 如果没有发现一条非空的回复信息，说明根本没有回复信息，那么就不设置回复信息
		if (flagReplyNull) {
			eachArticleReplyMaps = null;
		}

		return eachArticleReplyMaps;
	}

	/**
	 * 获取每个分页的帖子内容
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
		// 准备文章对象映射信息列表
		List<Map<String, Object>> eachPageArticleMaps = new ArrayList<Map<String, Object>>();

		// 防止出现异常情况
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

		// 获取单独每页的文档信息
		Document docEachPage = HcJsoupDocumentUtil.getDocument(pageUrl,
				this.site.getSeedUrl(), this.site.getEnCode());
		// 获取每个帖子div的元素集合
		Elements elesArticleDiv = (docEachPage == null ? null : docEachPage
				.select(fetchParame.getPostDivQuery()));
		if (elesArticleDiv == null) {
			return null;
		}

		for (Element eleArticleDiv : elesArticleDiv) {

			// 获取跟帖作者信息
			Element eleArticleAuthorDiv = eleArticleDiv.select(
					fetchParame.getPostAuthorQuery()).first();
			String strArticleAuthor = eleArticleAuthorDiv != null ? eleArticleAuthorDiv
					.ownText()
					: "";

			// 获取帖子内容信息
			Element eleArticleContentDiv = eleArticleDiv.select(
					fetchParame.getPostContentQuery()).first();
			String strArticleContent = eleArticleContentDiv != null ? eleArticleContentDiv
					.text()
					: "";

			// TODO delete print
			System.out.println(strArticleContent);

			// 获取发帖时间及楼层信息
			Element eleArticleTimeDiv = eleArticleDiv.select(
					fetchParame.getPostTimeQuery()).first();
			String strArticleTime = this.getContentTime(eleArticleTimeDiv);
			// 百度的发帖时间在javascript中显示，需要单独编写解析json的方法
			if (pageUrl.indexOf("baidu") != -1) {
				strArticleTime = (FetchJsonPostTime_BaiduTieBa
						.getBaiduTieBaPostTime(eleArticleDiv));
			}
			// 通过静态方法转化成标准格式
			strArticleTime = AnalyzerTime
					.transStandardTimeFormat(strArticleTime);

			// 获取回复模块（通过调用单独的方法实现）
			List<Map<String, Object>> eachArticleReplyMaps = getReplyEachArticle(eleArticleDiv);

			// 将分析出来的各部分内容组合
			Map<String, Object> articleMap = new HashMap<String, Object>();
			// 判断帖子内容是否
			if (!strArticleAuthor.equals("") || !strArticleContent.equals("")
					|| !strArticleTime.equals("")) {
				if (!strArticleAuthor.equals("")) {
					articleMap.put("articleAuthor", strArticleAuthor);
				}
				if (!strArticleContent.equals("")) {
					articleMap.put("articleContent", strArticleContent);
				} else {
					// 内容为空，假定是图片
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
	 * 获取所有分页的帖子内容（*）
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
		// 准备所有页面的文章映射列表信息
		List<Map<String, Object>> allArticleMaps = new ArrayList<Map<String, Object>>();

		// 获取某一帖子所有页面的链接列表
		List<String> pageUrlList = getPagerUrl(fetchPagerObj
				.getFetchPagerObjName(), post.getPostUrl(), fetchParame
				.getPagerQuery());

		// 按照链接列表逐一分析页面
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

		// 从spring中获取fetch结果处理对象和log结果处理对象
		HandleFetchResult handleFetchResult = (HandleFetchResult) ApplicationContextFactory.appContext
				.getBean("handleFetchResult");
		HandleLogResult handleLogResult = (HandleLogResult) ApplicationContextFactory.appContext
				.getBean("handleLogResult");

		int fetchNum = 0;
		int fetchSuccNum = 0;

		for (Post post : getPosts()) {

			// 每次循环之前，首先检查任务结束状态位的情况，如果为true，提前结束，不执行任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// 初始化第一页的document
			this.docPostFirstPage = HcJsoupDocumentUtil.getDocument(post
					.getPostUrl(), this.site.getSeedUrl(), this.site
					.getEnCode());

			// 准备节点所有的映射数据
			Map<String, Object> postAllContentMap = new HashMap<String, Object>();

			// 调用各方法获得节点对应的内容
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
				// 处理失效的页面
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

			// 处理已经解析过的页面（除非再次被刷新），避免重复解析
			handleFetchResult.handleFetchedPost(post);

			fetchNum++;
			if (postAllContentMap != null) {

				try {
					handleFetchResult.refreshMongoContent(postAllContentMap,
							this.site);
					// 更频繁地刷新日志反馈数据
					handleLogResult
							.refreshSiteLogFetchRealTimeInThread(this.site);

					fetchSuccNum++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 准备返回的解析成果数据
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
