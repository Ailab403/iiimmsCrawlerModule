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

	// ��spring��ע���serviceʵ��
	private GrabParameService grabParameService;
	private ToolService toolService;
	// ��spring�л�ý������ʵ��
	private HandleGrabResult handleGrabResult;

	// ����������Ҫ�Ĳ���ʵ��
	private GrabParame grabParame;
	private Tool tool;

	// ��ʼ��ִ������Ĳ���
	public void initCrawlerParames(Site site) {

		setGrabParame(this.grabParameService.loadBySiteId(site.getSiteId()));
		setTool(site.getTool());
	}

	// һ��ʽȫ�ײ�����ɣ�����ˢ�µ�������Ŀ
	public Map<String, Object> execGrabMethod(Site site) {

		Set<Theme> themes = new HashSet<Theme>();

		initCrawlerParames(site);

		// ׼�����صķ�������
		Map<String, Object> themeFeedBackMap = new HashMap<String, Object>();
		try {

			// ͨ��������ƻ��ִ��������ȡ�����Ľ��
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

			// ����ͳһ����ˢ�����ݿ�
			handleGrabResult.refreshThemeTbl(themes);

			themeFeedBackMap.put("siteUpdateThemeNum", themes.size());
			themeFeedBackMap.put("grabThemeExp", "�ɹ�ˢ������");

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
