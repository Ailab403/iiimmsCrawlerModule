package ims.crawler.exec;

import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.SiteLog;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

import java.util.Map;

/**
 * 
 * @author superhy
 * 
 */
public class ExecSiteMission {

	// 从spring注入执行实体
	private ExecCheck execCheck;
	private ExecGrabThemeUrls execGrabThemeUrls;
	private ExecGrabPostUrls execGrabPostUrls;
	private ExecFetchContent execFetchContent;
	// 从spring中获得处理日志结果的类实体
	private HandleLogResult handleLogResult;

	/**
	 * 这个方法用作将所有以站点为单位的执行操作全部打包，形成一整套任务，便于多线程方法调用
	 */
	public void execMissionMethod(Site site, TaskLog taskLog) {
		// 首先需传入总任务的实体和所服务的站点的实体

		// 设置处理日志结果类实体的总任务成员
		handleLogResult.setTaskLog(taskLog);

		// 站点分任务的日志实体
		SiteLog siteLog;

		/*
		 * 状态位说明： 任务尚未开始，状态位为0； 任务仅执行完检测操作，状态位为0； 任务仅执行完主题抓取操作，状态位为1；
		 * 任务仅执行完节点信息抓取操作，状态位为2；
		 * 所有任务都已完成，状态位最终为3；只有任务状态位>=3是，才算任务结束，任务未结束，手动放弃，任务状态位为4；
		 */

		// 判断总任务状态
		if (taskLog.getTaskStatus() < 1) {
			// 总任务显示总任务是新任务，"尚未开始"

			// 创建站点分任务的日志信息
			siteLog = handleLogResult.createNewSiteLog(site);

		} else if (taskLog.getTaskStatus() >= 1 && taskLog.getTaskStatus() < 3) {
			// 总任务状态为"暂停"，说明总任务执行到一半，说明这个站点的分任务也有可能只执行到一半，需要检查站点分任务的执行状态

			// 查询出站点执行的日志，为了查看执行状态
			siteLog = handleLogResult.loadOldSiteLog(site);

			// 如果站点分任务日志尚未创建，说明这个站点分任务尚未开始，就当没有开始执行处理
			if (siteLog == null) {
				siteLog = handleLogResult.createNewSiteLog(site);
			}
		} else {
			// 总任务已经结束或是取消或是执行失败，什么都不用做，直接跳出

			return;
		}

		// 如果尚未完成主题爬取
		if (siteLog.getSiteStatus() < 1) {
			// 执行预处理检查
			Map<String, Object> checkFeedBackMap = this.execCheck
					.execCheckMethod(site, siteLog);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				siteLog.setSiteLogExp((String) checkFeedBackMap
						.get("errorDescribe"));
				// 修改站点分任务日志的描述
				handleLogResult.updateSiteLog(siteLog);

				// 检查未通过，提前结束分任务，分任务状态停留在0
				System.out.println(site.getSiteId() + "号站点未通过检查");
				return;
			} else {
				siteLog.setSiteLogExp((String) checkFeedBackMap
						.get("errorDescribe"));
				// 修改站点分任务日志描述
				handleLogResult.updateSiteLog(siteLog);
			}

			// 执行抓取刷新主题
			Map<String, Object> themeFeedBackMap = this.execGrabThemeUrls
					.execGrabMethod(site);
			if (themeFeedBackMap == null) {
				siteLog.setSiteLogExp("执行抓取主题任务失败");
				// 修改站点分任务日志描述
				handleLogResult.updateSiteLog(siteLog);

				// 提前结束任务，分任务状态停留在0
				return;
			} else {
				siteLog.setSiteStatus(1);
				siteLog.setSiteThemeNum((Integer) themeFeedBackMap
						.get("siteUpdateThemeNum"));
				siteLog.setSiteLogExp((String) themeFeedBackMap
						.get("grabThemeExp"));

				// 修改站点分任务日志信息
				handleLogResult.updateSiteLog(siteLog);
			}
		}

		// 如果尚未完成节点爬取
		if (siteLog.getSiteStatus() < 2) {
			// 执行抓取加载节点信息
			Map<String, Object> postFeedBackMap = this.execGrabPostUrls
					.execGrabMethod(site, handleLogResult);
			if (postFeedBackMap == null) {
				siteLog.setSiteLogExp("执行加载节点信息失败");
				// 修改站点分任务日志描述
				handleLogResult.updateSiteLog(siteLog);

				// 提前结束任务，分任务状态停留在1
				return;
			} else {
				siteLog.setSiteStatus(2);
				siteLog.setSiteNewPostNum((Integer) postFeedBackMap
						.get("grabNewPostNum"));
				siteLog.setSiteUpdatePostNum((Integer) postFeedBackMap
						.get("grabUpdatePostNum"));
				siteLog.setSiteFixPostNum((Integer) postFeedBackMap
						.get("grabFixPostNum"));
				siteLog.setGrabCostTime((String) postFeedBackMap
						.get("grabCostTime"));
				siteLog.setSiteLogExp((String) postFeedBackMap
						.get("grabPostExp"));

				// 修改站点分任务日志信息
				handleLogResult.updateSiteLog(siteLog);
			}
		}

		// 如果尚未完成全部解析工作
		if (siteLog.getSiteStatus() < 3) {
			// 执行节点内容解析
			Map<String, Object> fetchFeedBackMap = this.execFetchContent
					.execFetchMethod(site, handleLogResult);
			if (fetchFeedBackMap == null) {
				siteLog.setSiteLogExp("执行解析节点内容失败");
				// 修改站点分任务日志描述
				handleLogResult.updateSiteLog(siteLog);

				// 提前结束任务，分任务状态停留在2
				return;
			} else {
				siteLog.setSiteStatus(3);
				siteLog.setSiteFetchNum((Integer) fetchFeedBackMap
						.get("fetchNum"));
				siteLog.setSiteFetchSuccNum((Integer) fetchFeedBackMap
						.get("fetchSuccNum"));
				siteLog.setFetchCostTime((String) fetchFeedBackMap
						.get("fetchCostTime"));
				siteLog
						.setSiteLogExp((String) fetchFeedBackMap
								.get("fetchExp"));

				// 修改站点分任务日志信息
				handleLogResult.updateSiteLog(siteLog);
			}
		}
	}

	public ExecCheck getExecCheck() {
		return execCheck;
	}

	public void setExecCheck(ExecCheck execCheck) {
		this.execCheck = execCheck;
	}

	public ExecGrabThemeUrls getExecGrabThemeUrls() {
		return execGrabThemeUrls;
	}

	public void setExecGrabThemeUrls(ExecGrabThemeUrls execGrabThemeUrls) {
		this.execGrabThemeUrls = execGrabThemeUrls;
	}

	public ExecGrabPostUrls getExecGrabPostUrls() {
		return execGrabPostUrls;
	}

	public void setExecGrabPostUrls(ExecGrabPostUrls execGrabPostUrls) {
		this.execGrabPostUrls = execGrabPostUrls;
	}

	public ExecFetchContent getExecFetchContent() {
		return execFetchContent;
	}

	public void setExecFetchContent(ExecFetchContent execFetchContent) {
		this.execFetchContent = execFetchContent;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

}
