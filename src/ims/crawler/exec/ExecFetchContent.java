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

	// ��spring��ע���serviceʵ��
	private FetchParameService fetchParameService;
	private GrabUserParameService grabUserParameService;
	private SiteService siteService;
	private ThemeService themeService;
	private ToolService toolService;

	// ����������Ҫ�Ĳ���ʵ��
	private FetchParame fetchParame;
	// ��Ҫ�õ��û��Զ������ʵ��
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

		// ��ȡ���ص���������
		Map<String, Object> contentMap = new HashMap<String, Object>();

		// ��ʼ����������
		initFetchParames(site, handleLogResult);

		// TODO delete print
		for (Theme theme : getThemes()) {
			System.out.println(theme.toString());
		}

		// ׼�����صķ�������
		Map<String, Object> fetchFeedBackMap = new HashMap<String, Object>();
		try {
			// ��¼���еĿ�ʼʱ��
			long startTime = System.currentTimeMillis();

			// ʹ�÷������ִ�н�������
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

			// ˢ�����ݿ�Ĳ������߳����Ѿ�������

			// TODO delete print
			System.out.println("done!");

			// ������������õ�ʱ��
			long endTime = System.currentTimeMillis();
			String timeCost = AnalyzerTime.formatDuring(endTime - startTime);

			// �������еķ�������
			fetchFeedBackMap.put("fetchNum", contentMap.get("fetchNum"));
			fetchFeedBackMap
					.put("fetchSuccNum", contentMap.get("fetchSuccNum"));
			fetchFeedBackMap.put("fetchCostTime", timeCost);
			fetchFeedBackMap.put("fetchExp", "�ɹ������ڵ�����");
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			// �����쳣ʱ����������Ϊ��
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
