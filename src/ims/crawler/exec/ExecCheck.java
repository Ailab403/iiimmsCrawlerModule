package ims.crawler.exec;

import ims.crawlerLog.model.SiteLog;
import ims.site.model.FetchParame;
import ims.site.model.GrabParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Tool;
import ims.site.service.ExtraParameService;
import ims.site.service.FetchParameService;
import ims.site.service.GrabParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.PostService;
import ims.site.service.SiteService;
import ims.site.service.ToolService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author superhy
 * 
 */
public class ExecCheck {

	/*
	 * 汇总预警类方法（反射机制） 依次顺序： 1.检查网络连接 2.检查数据库连接 3.检查爬虫参数 4.检查解析器参数
	 * 5.汇总检查结果，返回给任务体系
	 */

	// 从spring注入的service方法
	private SiteService siteService;
	private GrabParameService grabParameService;
	private GrabUserParameService grabUserParameService;
	private FetchParameService fetchParameService;
	private ExtraParameService extraParameService;
	private PostService postService;
	private ToolService toolService;

	// 检查执行类需要的参数
	private Set<Post> posts;
	private GrabParame grabParame;
	private FetchParame fetchParame;
	private Tool tool;

	public void initCheckParames(Site site) {
		setGrabParame(this.grabParameService.loadBySiteId(site.getSiteId()));
		setFetchParame(this.fetchParameService.loadBySiteId(site.getSiteId()));
		setPosts(this.postService.listBySiteId(site.getSiteId()));
		setTool(site.getTool());
	}

	/**
	 * 反射执行网络连接检查
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkNetConnection(Site site) {
		// 准备检查的返回值
		Map<String, Object> checkNetResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckNet = Class.forName("ims.crawler.check."
					+ this.tool.getCheckNetObj());
			Method methodCheckNet = classCheckNet.getMethod("execCheck",
					String.class);
			checkNetResMap = (Map<String, Object>) methodCheckNet.invoke(
					classCheckNet.newInstance(), site.getSeedUrl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checkNetResMap;
	}

	/**
	 * 反射执行数据库连接检查
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkDbConnection(Site site) {
		// 准备检查的返回值
		Map<String, Object> checkDbResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckDb = Class.forName("ims.crawler.check."
					+ this.tool.getCheckDatabaseObj());
			Method methodCheckDb = classCheckDb.getMethod("execCheck",
					Integer.class, SiteService.class, GrabParameService.class,
					GrabUserParameService.class, FetchParameService.class,
					ExtraParameService.class, ToolService.class);
			checkDbResMap = (Map<String, Object>) methodCheckDb.invoke(
					classCheckDb.newInstance(), site.getSiteId(),
					this.siteService, this.grabParameService,
					this.grabUserParameService, this.fetchParameService,
					this.extraParameService, this.toolService);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkDbResMap;
	}

	/**
	 * 反射执行爬虫固化参数检查
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkGrabParame(Site site) {
		// 准备检查的返回值
		Map<String, Object> checkGrabParameResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckGrabParame = Class.forName("ims.crawler.check."
					+ this.tool.getCheckGrabParameObj());
			Method methodCheckGrabParame = classCheckGrabParame.getMethod(
					"execCheck", Site.class, GrabParame.class);

			checkGrabParameResMap = (Map<String, Object>) methodCheckGrabParame
					.invoke(classCheckGrabParame.newInstance(), site,
							this.grabParame);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkGrabParameResMap;
	}

	/**
	 * 反射执行解析固化参数检查
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkFetchParame(Site site) {
		// 准备检查的返回值
		Map<String, Object> checkFetchParameResMap = new HashMap<String, Object>();
		// 判断数据库中有无对应的post节点可以供解析，如果没有，返回错误信息，提示先进行预爬取工作
		if (this.posts == null) {
			checkFetchParameResMap.put("checkRes", 0);
			checkFetchParameResMap.put("errorDescribe", "没有可解析的节点");
			return checkFetchParameResMap;
		}

		try {
			// 取得一个testPost
			Iterator<Post> iterator = posts.iterator();
			Post testPost = iterator.next();

			Class<?> classCheckFetchParame = Class.forName("ims.crawler.check."
					+ this.tool.getCheckFetchParameObj());
			Method methodCheckFetchParame = classCheckFetchParame.getMethod(
					"execCheck", Post.class, FetchParame.class);
			checkFetchParameResMap = (Map<String, Object>) methodCheckFetchParame
					.invoke(classCheckFetchParame.newInstance(), posts,
							this.fetchParame);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkFetchParameResMap;
	}

	public synchronized Map<String, Object> execCheckMethod(Site site, SiteLog siteLog) {

		/*
		 * 如果是fetch任务，检查是否已经加载好对应的post节点，否则无法进行，传入tasklog实体，
		 * taskLog表加上当前执行状态status字段 ，0表示错误，1表示正常
		 */

		// 初始化检查参数
		initCheckParames(site);

		// 准备返回的反馈参数
		Map<String, Object> checkFeedBackMap = new HashMap<String, Object>();
		checkFeedBackMap.put("checkRes", 1);
		checkFeedBackMap.put("errorDescribe", "尚未开始检查");
		try {
			// 根据站点分任务执行状态进行必要的检查
			int siteStatus = siteLog.getSiteStatus();

			// 执行网络连接检查
			checkFeedBackMap = checkNetConnection(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("完成网络检查");

			// 执行数据库连接检查
			checkFeedBackMap = checkDbConnection(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("完成数据库检查");

			// 执行爬取固化参数检查
			checkFeedBackMap = checkGrabParame(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("完成爬虫固话参数检查");

			if (siteStatus == 2) {
				checkFeedBackMap = checkFetchParame(site);
				if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
					return checkFeedBackMap;
				}
			}
			// TODO delete print
			System.out.println("完成解析器固化参数检查");
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		if ((Integer) checkFeedBackMap.get("checkRes") == 1) {
			checkFeedBackMap.remove("errorDescribe");
			checkFeedBackMap.put("errorDescribe", "检测未发现错误");

			return checkFeedBackMap;
		} else {
			checkFeedBackMap.remove("errorDescribe");
			checkFeedBackMap.put("errorDescribe", "发生未知错误");

			return checkFeedBackMap;
		}
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public GrabParameService getGrabParameService() {
		return grabParameService;
	}

	public void setGrabParameService(GrabParameService grabParameService) {
		this.grabParameService = grabParameService;
	}

	public GrabUserParameService getGrabUserParameService() {
		return grabUserParameService;
	}

	public void setGrabUserParameService(
			GrabUserParameService grabUserParameService) {
		this.grabUserParameService = grabUserParameService;
	}

	public FetchParameService getFetchParameService() {
		return fetchParameService;
	}

	public void setFetchParameService(FetchParameService fetchParameService) {
		this.fetchParameService = fetchParameService;
	}

	public ExtraParameService getExtraParameService() {
		return extraParameService;
	}

	public void setExtraParameService(ExtraParameService extraParameService) {
		this.extraParameService = extraParameService;
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public ToolService getToolService() {
		return toolService;
	}

	public void setToolService(ToolService toolService) {
		this.toolService = toolService;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
	}

	public FetchParame getFetchParame() {
		return fetchParame;
	}

	public void setFetchParame(FetchParame fetchParame) {
		this.fetchParame = fetchParame;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

}
