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

	// 爬虫固化技术参数实体
	private GrabParame grabParame;
	// 用户自定义爬虫参数实体
	private GrabUserParame grabUserParame;

	// 网站信息类实体
	private Site site;
	// 主题信息类实体
	private Theme theme;

	// TODO 添加任务日志实例和错误类别实例

	public BasicBBSGrabPostUrlsThread(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Theme theme) {
		super();
		this.grabParame = grabParame;
		this.grabUserParame = grabUserParame;
		this.site = site;
		this.theme = theme;
	}

	// MD5转换工具类
	private static TransMD5 transMD5Obj = new TransMD5();

	// TODO 检查所有元素的存在情况和技术参数是否为空，增强健壮性

	// 获得单个主题的帖子列表分页各页URL
	public Set<ExactUrl> getThemePageUrlSet() {

		// 设置主题分页URL集合
		Set<ExactUrl> themePageUrlSet = new HashSet<ExactUrl>();
		// 分页的第一页是主题URL本身
		themePageUrlSet.add(new ExactUrl(theme.getThemeUrl(), theme
				.getThemeUrlMD5()));

		// 如果不存在分页条件或者没有页数限制要求（大部分是出现错误的情况），那么只选取第一页
		if ((grabParame.getPostListPagerQuery() == null || grabParame
				.getPostListPagerQuery().equals(""))
				|| (grabParame.getPostListNextQuery() == null || grabParame
						.getPostListNextQuery().equals(""))
				|| (grabUserParame.getThemePageNumLimit() == 0)) {

			return themePageUrlSet;
		}

		// 判断时间限制条件是回退时间还是过去时间点，并统一计算成过去时间点
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

		// 如果存在没有时间识别Query或者没有最近回复时间限制，均视为没有最近回复时间限制
		if ((grabParame.getPostTimeQuery() == null || grabParame
				.getPostTimeQuery().equals("")) || (postTimeLimit == null)) {
			postTimeLimit = "0";
		}

		// 获得主题页面doc
		Document docEachTheme = BasicJsoupDocumentUtil.getDocument(theme
				.getThemeUrl());
		// 防止出现因网络原因出现的页面下载不成功情况
		if (docEachTheme == null) {
			return themePageUrlSet;
		}

		// 获得对应主题页面的分页div元素
		// 获取分页元素失败，就只返回主题的第一页
		if (docEachTheme.select(grabParame.getPostListPagerQuery()).size() == 0) {
			return themePageUrlSet;
		}
		Element elePostListPagerDiv = docEachTheme.select(
				grabParame.getPostListPagerQuery()).first();

		// 记录已爬取的页数
		int pageNum = 1;
		// 只要页数没有超过限制，就继续抓取
		while (pageNum <= grabUserParame.getThemePageNumLimit()) {
			// 判断有没有下一页按钮，知道是否已到最后一页
			if (elePostListPagerDiv.select(grabParame.getPostListNextQuery())
					.size() == 0) {
				break;
			}

			// 判断有无最近回复时间限制
			if (!postTimeLimit.equals("0")) {

				// 获取当前页面第一条帖子的最近回复时间
				String postTimeThisPage = this.getPostTime(docEachTheme, null);
				// 判断页面时效是否过旧
				boolean flagTimeFresh = AnalyzerTime.compTimeLimit(
						postTimeThisPage, postTimeLimit);
				if (!flagTimeFresh) {
					break;
				}
			}

			// 依次获取下一页
			Element elePostListNextDiv = elePostListPagerDiv.select(
					grabParame.getPostListNextQuery()).first();
			String postListNextUrl = elePostListNextDiv.attr("abs:href");
			// 为每一页URL制作唯一的MD5验证码
			String postListNextUrlMD5 = transMD5Obj.getMD5Code(postListNextUrl);
			themePageUrlSet.add(new ExactUrl(postListNextUrl,
					postListNextUrlMD5));

			// 转入下一页链接的抓取
			pageNum++;
			docEachTheme = BasicJsoupDocumentUtil.getDocument(postListNextUrl);
			// 防止出现因网络原因出现的页面下载不成功情况
			if (docEachTheme == null) {
				break;
			}
			elePostListPagerDiv = docEachTheme.select(
					grabParame.getPostListPagerQuery()).first();

		}

		return themePageUrlSet;
	}

	// 获得单个主题一页当中的帖子链接信息集合
	public Map<String, Set<Post>> getSpPostSet(String pageUrl) {

		// 准备返回的数据
		Set<Post> postAddSet = new HashSet<Post>();
		Set<Post> postUpdateSet = new HashSet<Post>();
		Set<Post> postFixSet = new HashSet<Post>();
		Map<String, Set<Post>> postResultMap = new HashMap<String, Set<Post>>();

		// 获得该页document
		Document docPage = BasicJsoupDocumentUtil.getDocument(pageUrl);
		// 防止出现因网络原因出现的页面下载不成功情况
		if (docPage == null) {
			return null;
		}

		// 获得帖子列表div元素
		Element elePostListSpDiv = docPage.select(
				grabParame.getPostListSpDivQuery()).first();

		// 获得帖子div元素集合
		// 处理帖子列表div元素获取失败的情况
		if (elePostListSpDiv == null) {
			return null;
		}
		Elements elesPostDiv = elePostListSpDiv.select(grabParame
				.getPostDivQuery());

		// 按帖子div集合逐条爬取单个帖子信息
		for (Element eleEachPostDiv : elesPostDiv) {
			// 获得帖子名称
			String postName = eleEachPostDiv.select(
					grabParame.getPostNameQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostNameQuery()).first().text()
					: "unknown";
			// 防止帖子名称过长
			postName = postName.length() >= 50 ? (postName.substring(0, 50) + "......")
					: postName;

			// 获得帖子链接
			String postUrl = eleEachPostDiv
					.select(grabParame.getPostUrlQuery()).first()
					.attr("abs:href");

			// 制作帖子链接MD5
			String postUrlMD5 = new TransMD5().getMD5Code(postUrl);

			// 获得帖子点击量
			String clickNumStr = eleEachPostDiv.select(
					grabParame.getPostClickNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostClickNumQuery()).first().text()
					: "0";
			clickNumStr = AnalyzerNum.getClickNum(clickNumStr);
			// 检查点击量标签是否是数字（正则表达式匹配正整数字符串）
			int clickNum = clickNumStr.matches("^\\d+$") ? Integer
					.parseInt(clickNumStr) : 0;

			// 获得帖子回复数量
			String replyNumStr = eleEachPostDiv.select(
					grabParame.getPostReplyNumQuery()).size() > 0 ? eleEachPostDiv
					.select(grabParame.getPostReplyNumQuery()).first().text()
					: "0";
			replyNumStr = AnalyzerNum.getReplyNum(replyNumStr);
			// 与点击量标签同理检查
			int replyNum = replyNumStr.matches("^\\d+$") ? Integer
					.parseInt(replyNumStr) : 0;

			// 防止点击量和回复量互换的情况（依据：点击量一定大于等于回复量）
			if (replyNum > clickNum) {
				int temp = replyNum;
				replyNum = clickNum;
				clickNum = temp;
			}

			// BBS没有转发数量
			int forwardNum = 0;

			// 获得帖子最近回复时间，找不到时间标签的（顶置）按当前时间计算
			String lastReplyTime = this.getPostTime(null, eleEachPostDiv);

			// 取得当前电脑时间作为更新时间
			String refreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());

			// 根据限制条件判断是否要加入post更新集合，如果判断不加入，直接跳出，执行下一次循环
			boolean flagClickNumQualified = (clickNum >= grabUserParame
					.getPostClickNumLimit());
			boolean flagReplyNumQualified = (replyNum >= grabUserParame
					.getPostReplyNumLimit());
			// BBS没有转发数量，不需要判断
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

			// 制作待处理的post对象，默认需要解析
			Post postPending = new Post(this.site.getSiteId(),
					this.theme.getThemeId(), postName, postUrl, postUrlMD5,
					clickNum, replyNum, forwardNum,
					Timestamp.valueOf(lastReplyTime), 1,
					Timestamp.valueOf(refreshTime), this.theme, this.site);

			// 进行增量爬取判断
			SingletonBufferPool singletonBufferPool = SingletonBufferPool
					.getSingletonBufferPool();
			if (singletonBufferPool.getPostBufferPool().isEmpty()) {
				postAddSet.add(postPending);

				// TODO delete print
				System.out.println("缓存为空，新添加节点：" + postPending.getPostName());
			} else if (singletonBufferPool.checkInBuffer(postPending
					.getPostUrlMD5())) {
				Map<String, Object> compPostParames = singletonBufferPool
						.getPostParames(postPending.getPostUrlMD5());

				// 检查各种特征值是否发生改变（非equals作为改变）
				boolean flagClickNumChanged = !((Integer) compPostParames
						.get("clickNum")).equals(postPending.getClickNum());
				boolean flagReplyNumChanged = !((Integer) compPostParames
						.get("replyNum")).equals(postPending.getReplyNum());
				// BBS没有转发数量，不需要判断
				boolean flagForwardNumChanged = false;
				boolean flagLastReplyTimeChanged = !compPostParames.get(
						"lastReplyTime").equals(postPending.getLastReplyTime());

				// 如果回复数量和回复时间出现有一项不一样，加入updateSet
				if (flagReplyNumChanged || flagLastReplyTimeChanged) {
					postUpdateSet.add(postPending);

					// TODO delete print
					System.out.println("更新节点：" + postPending.getPostName());
				} else if (flagClickNumChanged || flagForwardNumChanged) {
					// 如果回复数量和回复时间都没变，但是转发量或者点击量变了，说明内容没变，标记变了
					postFixSet.add(postPending);

					// TODO delete print
					System.out.println("修改节点：" + postPending.getPostName());
				} else {

					// TODO delete print
					System.out.println("节点已是最新的：" + postPending.getPostName());
				}

			} else {
				postAddSet.add(postPending);

				// TODO delete print
				System.out.println("缓存不为空，新添加节点：" + postPending.getPostName());
			}
		}

		// 将结果加入映射集合
		postResultMap.put("add", postAddSet);
		postResultMap.put("update", postUpdateSet);
		postResultMap.put("fix", postFixSet);

		return postResultMap;
	}

	/**
	 * 写成对于静态数组的站点检查，取得时间的专用方法，传入doc或者ele，如果没有传入空值
	 * 
	 * @param document
	 * @param element
	 * @return
	 */
	public String getPostTime(Document document, Element element) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		if (document != null && element == null) {

			// 获得帖子时间的div元素
			Element elePostTime = document.select(
					this.grabParame.getPostTimeQuery()).size() != 0 ? document
					.select(this.grabParame.getPostTimeQuery()).first() : null;

			if (elePostTime != null) {
				// 论坛特殊对待判定并处理
				if (GrabSpecialContentSiteList
						.isInSpecialTimeSiteList(this.site)) {
					postTimeStr = GrabSpecialTimeContent.getSpecialTimeContent(
							elePostTime, this.site);
				} else {
					postTimeStr = elePostTime.text();
				}
			}
		} else if (document == null && element != null) {

			// 获得帖子时间的div元素
			Element elePostTime = element.select(
					this.grabParame.getPostTimeQuery()).size() != 0 ? element
					.select(this.grabParame.getPostTimeQuery()).first() : null;

			if (elePostTime != null) {
				// qq论坛特殊对待
				if (GrabSpecialContentSiteList
						.isInSpecialTimeSiteList(this.site)) {
					postTimeStr = GrabSpecialTimeContent.getSpecialTimeContent(
							elePostTime, this.site);
				} else {
					postTimeStr = elePostTime.text();
				}
			}
		}

		// 将时间字符串转换为标准的时间格式
		postTimeStr = AnalyzerTime.transStandardTimeFormat(postTimeStr);

		// 返回时间
		return postTimeStr;
	}

	public Map<String, Object> call() throws Exception {

		// 准备返回的数据
		Set<Post> themePostAddSet = new HashSet<Post>();
		Set<Post> themePostUpdateSet = new HashSet<Post>();
		Set<Post> themePostFixSet = new HashSet<Post>();
		Map<String, Object> themePostResultMap = new HashMap<String, Object>();

		// 在执行任务之前，首先检查任务结束状态位的情况，如果为true，提前结束，不执行任务
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// 获取每页的链接
		Set<ExactUrl> themePageUrlSet = this.getThemePageUrlSet();
		if (themePageUrlSet == null) {
			// TODO 写入任务异常日志
			return null;
		}

		// 根据每页的链接获取节点映射对象
		for (ExactUrl eachPageUrl : themePageUrlSet) {

			// 每次循环之前，首先检查任务结束状态位的情况，如果为true，提前结束，不执行任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// 统一获得结果，不得重复调用
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
