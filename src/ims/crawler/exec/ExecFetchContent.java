package ims.crawler.exec;

import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.util.HandleLogResult;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.model.Tool;
import ims.site.service.FetchParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.SiteService;
import ims.site.service.ThemeService;
import ims.site.service.ToolService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExecFetchContent {

	// 从spring中注入的service实体
	private FetchParameService fetchParameService;
	private GrabUserParameService grabUserParameService;
	private SiteService siteService;
	private ThemeService themeService;
	private ToolService toolService;

	// 爬虫运行需要的参数实体
	private FetchParame fetchParame;
	// 需要用到用户自定义参数实体
	private GrabUserParame grabUserParame;
	private Set<Theme> themes;
	private Tool tool;
	private HandleLogResult handleLogResult;

	public void initFetchParames(Site site, HandleLogResult handleLogResult) {
		setFetchParame(this.fetchParameService.loadBySiteId(site.getSiteId()));
		setGrabUserParame(this.grabUserParameService.loadBySiteId(site
				.getSiteId()));
		setThemes(this.themeService.listBySiteId(site.getSiteId()));
		setTool(site.getTool());
		setHandleLogResult(handleLogResult);
	}

	public Map<String, Object> execFetchMethod(Site site,
			HandleLogResult handleLogResult) {

		// 获取返回的数据容器
		Map<String, Object> contentMap = new HashMap<String, Object>();

		// 初始化解析参数
		initFetchParames(site, handleLogResult);

		// TODO delete print
		for (Theme theme : getThemes()) {
			System.out.println(theme.toString());
		}

		// 准备返回的反馈参数
		Map<String, Object> fetchFeedBackMap = new HashMap<String, Object>();
		try {
			// 记录运行的开始时间
			long startTime = System.currentTimeMillis();

			// 使用反射机制执行解析程序
			Class<?> classFetchContent = Class
					.forName("ims.crawler.fetch.fetchImpl."
							+ this.tool.getFetchContentObj());

			Method methodExecFetchContent = classFetchContent.getMethod(
					"execFetchContent", FetchParame.class,
					GrabUserParame.class, Site.class, Set.class,
					HandleLogResult.class);
			contentMap = (Map<String, Object>) methodExecFetchContent.invoke(
					classFetchContent.newInstance(), this.fetchParame,
					this.grabUserParame, site, this.themes,
					this.handleLogResult);

			if (contentMap == null || contentMap.isEmpty()) {
				return null;
			}

			// 刷新数据库的操作在线程中已经运行了

			// TODO delete print
			System.out.println("done!");

			// 计算出运行所用的时间
			long endTime = System.currentTimeMillis();
			String timeCost = AnalyzerTime.formatDuring(endTime - startTime);

			// 返回运行的反馈参数
			fetchFeedBackMap.put("fetchNum", contentMap.get("fetchNum"));
			fetchFeedBackMap
					.put("fetchSuccNum", contentMap.get("fetchSuccNum"));
			fetchFeedBackMap.put("fetchCostTime", timeCost);
			fetchFeedBackMap.put("fetchExp", "成功解析节点内容");
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			// 发生异常时，反馈参数为空
			fetchFeedBackMap = null;
		}

		return fetchFeedBackMap;
	}

	public FetchParameService getFetchParameService() {
		return fetchParameService;
	}

	public void setFetchParameService(FetchParameService fetchParameService) {
		this.fetchParameService = fetchParameService;
	}

	public GrabUserParameService getGrabUserParameService() {
		return grabUserParameService;
	}

	public void setGrabUserParameService(
			GrabUserParameService grabUserParameService) {
		this.grabUserParameService = grabUserParameService;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
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

}
