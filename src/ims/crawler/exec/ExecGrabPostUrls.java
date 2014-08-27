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

	// ��spring��ע���serviceʵ��
	private GrabParameService grabParameService;
	private GrabUserParameService grabUserParameService;
	private ThemeService themeService;
	private ToolService toolService;
	// ��spring�л�ȡ��ȡ����������
	private HandleGrabResult handleGrabResult;

	// ����������Ҫ�Ĳ���ʵ��
	private GrabParame grabParame;
	private GrabUserParame grabUserParame;
	private Set<Theme> themes;
	private Tool tool;
	private HandleLogResult handleLogResult;

	// ��ʼ��ִ������Ĳ���
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

		// ��ȡ���ص���������
		Map<String, Object> postsMap = new HashMap<String, Object>();

		// ��ʼ�����в���
		initCrawlerParames(site, handleLogResult);

		// ׼�����صķ�������
		Map<String, Object> postFeedBackMap = new HashMap<String, Object>();
		try {

			// ��¼����ʱ�䣬��ʼʱ��
			long startTime = System.currentTimeMillis();

			// ʹ�÷������ִ������
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

			// ����ͳһ����ˢ�����ݿ�
			handleGrabResult.refreshPostTbl(postsMap);

			// ����ץȡ���⼤��״̬��Ϊδ�����ʾ�Ѿ�ץȡ��ɣ�
			for (Theme theme : this.themes) {
				theme.setThemeGrabable(0);
			}
			handleGrabResult.refreshThemeTbl(this.themes);

			System.out.println("done!");

			// ����������������õ�ʱ��
			long endTime = System.currentTimeMillis();
			String timeCost = AnalyzerTime.formatDuring(endTime - startTime);

			// �������еķ�������
			postFeedBackMap.put("grabNewPostNum", ((Set<Post>) postsMap
					.get("add")).size());
			postFeedBackMap.put("grabUpdatePostNum", ((Set<Post>) postsMap
					.get("update")).size());
			postFeedBackMap.put("grabFixPostNum", ((Set<Post>) postsMap
					.get("fix")).size());
			postFeedBackMap.put("grabCostTime", timeCost);
			postFeedBackMap.put("grabPostExp", "�ɹ����ؽڵ���Ϣ");
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

			// �����쳣ʱ����������Ϊ��
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
