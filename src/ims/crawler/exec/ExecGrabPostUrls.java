package ims.crawler.exec;

import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.util.HandleGrabResult;
import ims.crawler.util.HandleLogResult;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.model.Tool;
import ims.site.service.GrabParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.ThemeService;
import ims.site.service.ToolService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author superhy
 * 
 */
public class ExecGrabPostUrls {

	// 从spring中注入的service实体
	private GrabParameService grabParameService;
	private GrabUserParameService grabUserParameService;
	private ThemeService themeService;
	private ToolService toolService;
	// 从spring中获取爬取结果处理对象
	private HandleGrabResult handleGrabResult;

	// 爬虫运行需要的参数实体
	private GrabParame grabParame;
	private GrabUserParame grabUserParame;
	private Set<Theme> themes;
	private Tool tool;
	private HandleLogResult handleLogResult;

	// 初始化执行爬虫的参数
	public void initCrawlerParames(Site site, HandleLogResult handleLogResult) {

		setGrabParame(this.grabParameService.loadBySiteId(site.getSiteId()));
		setGrabUserParame(this.grabUserParameService.loadBySiteId(site
				.getSiteId()));
		Map<String, Object> siteIdAndGrabableMaps = new HashMap<String, Object>();
		siteIdAndGrabableMaps.put("siteId", site.getSiteId());
		siteIdAndGrabableMaps.put("themeGrabable", 1);
		setThemes(this.themeService
				.listBySiteIdAndGrabable(siteIdAndGrabableMaps));
		setTool(site.getTool());
		setHandleLogResult(handleLogResult);
	}

	public Map<String, Object> execGrabMethod(Site site,
			HandleLogResult handleLogResult) {

		// 获取返回的数据容器
		Map<String, Object> postsMap = new HashMap<String, Object>();

		// 初始化爬行参数
		initCrawlerParames(site, handleLogResult);

		// 准备返回的反馈参数
		Map<String, Object> postFeedBackMap = new HashMap<String, Object>();
		try {

			// 记录运行时间，开始时间
			long startTime = System.currentTimeMillis();

			// 使用反射机制执行爬虫
			Class<?> classGrabPostUrls = Class
					.forName("ims.crawler.grab.grabImpl."
							+ this.tool.getGrabPostObj());

			Method methodExecGrabPost = classGrabPostUrls.getMethod(
					"execGrabPost", GrabParame.class, GrabUserParame.class,
					Site.class, Set.class, HandleLogResult.class);
			postsMap = (Map<String, Object>) methodExecGrabPost.invoke(
					classGrabPostUrls.newInstance(), this.grabParame,
					this.grabUserParame, site, this.themes,
					this.handleLogResult);

			System.out.println(postsMap.size());
			if (postsMap == null || postsMap.isEmpty()) {
				return null;
			}

			// 调用统一方法刷新数据库
			handleGrabResult.refreshPostTbl(postsMap);

			// 将已抓取主题激活状态改为未激活（表示已经抓取完成）
			for (Theme theme : this.themes) {
				theme.setThemeGrabable(0);
			}
			handleGrabResult.refreshThemeTbl(this.themes);

			System.out.println("done!");

			// 计算出程序运行所用的时间
			long endTime = System.currentTimeMillis();
			String timeCost = AnalyzerTime.formatDuring(endTime - startTime);

			// 返回运行的反馈参数
			postFeedBackMap.put("grabNewPostNum", ((Set<Post>) postsMap
					.get("add")).size());
			postFeedBackMap.put("grabUpdatePostNum", ((Set<Post>) postsMap
					.get("update")).size());
			postFeedBackMap.put("grabFixPostNum", ((Set<Post>) postsMap
					.get("fix")).size());
			postFeedBackMap.put("grabCostTime", timeCost);
			postFeedBackMap.put("grabPostExp", "成功加载节点信息");
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

			// 发生异常时，反馈参数为空
			postFeedBackMap = null;
		}

		return postFeedBackMap;
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

	public ThemeService getThemeService() {
		return themeService;
	}

	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	public ToolService getToolService() {
		return toolService;
	}

	public void setToolService(ToolService toolService) {
		this.toolService = toolService;
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

	public Set<Theme> getThemes() {
		return themes;
	}

	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

	public HandleGrabResult getHandleGrabResult() {
		return handleGrabResult;
	}

	public void setHandleGrabResult(HandleGrabResult handleGrabResult) {
		this.handleGrabResult = handleGrabResult;
	}

}
