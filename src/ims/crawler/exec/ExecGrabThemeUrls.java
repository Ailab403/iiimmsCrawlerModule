package ims.crawler.exec;

import ims.crawler.util.HandleGrabResult;
import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.model.Tool;
import ims.site.service.GrabParameService;
import ims.site.service.ToolService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author superhy
 * 
 */
public class ExecGrabThemeUrls {

	// 从spring中注入的service实体
	private GrabParameService grabParameService;
	private ToolService toolService;
	// 从spring中获得结果处理实体
	private HandleGrabResult handleGrabResult;

	// 爬虫运行需要的参数实体
	private GrabParame grabParame;
	private Tool tool;

	// 初始化执行爬虫的参数
	public void initCrawlerParames(Site site) {

		setGrabParame(this.grabParameService.loadBySiteId(site.getSiteId()));
		setTool(site.getTool());
	}

	// 一键式全套操作完成，返回刷新的主题数目
	public Map<String, Object> execGrabMethod(Site site) {

		Set<Theme> themes = new HashSet<Theme>();

		initCrawlerParames(site);

		// 准备返回的反馈参数
		Map<String, Object> themeFeedBackMap = new HashMap<String, Object>();
		try {

			// 通过反射机制获得执行主题爬取方法的结果
			Class<?> classGrabThemeUrls = Class
					.forName("ims.crawler.grab.grabImpl."
							+ this.tool.getGrabThemeObj());
			Method methodExecGrabTheme = classGrabThemeUrls.getMethod(
					"execGrabTheme", Site.class, GrabParame.class);
			themes = (Set<Theme>) methodExecGrabTheme.invoke(classGrabThemeUrls
					.newInstance(), site, this.grabParame);

			if (themes == null || themes.isEmpty()) {
				return null;
			}

			// 调用统一方法刷新数据库
			handleGrabResult.refreshThemeTbl(themes);

			themeFeedBackMap.put("siteUpdateThemeNum", themes.size());
			themeFeedBackMap.put("grabThemeExp", "成功刷新主题");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			themeFeedBackMap = null;
		}

		return themeFeedBackMap;
	}

	public GrabParameService getGrabParameService() {
		return grabParameService;
	}

	public void setGrabParameService(GrabParameService grabParameService) {
		this.grabParameService = grabParameService;
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

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public HandleGrabResult getHandleGrabResult() {
		return handleGrabResult;
	}

	public void setHandleGrabResult(HandleGrabResult handleGrabResult) {
		this.handleGrabResult = handleGrabResult;
	}

}
