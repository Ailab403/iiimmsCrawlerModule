package ims.crawler.exec;

import java.util.concurrent.Callable;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

/**
 * 
 * @author superhy
 * 
 */
public class ExecTaskMissionThread implements Callable<Integer> {

	// 传入的参数实体
	private Site site;
	private TaskLog taskLog;

	public ExecTaskMissionThread(Site site, TaskLog taskLog) {
		super();
		this.site = site;
		this.taskLog = taskLog;
	}

	public Integer call() {

		try {
			// 从spring中获取站点分任务执行对象
			ExecSiteMission execSiteMission = (ExecSiteMission) ApplicationContextFactory.appContext
					.getBean("execSiteMission");

			System.out.println(this.site.getSiteId() + "号任务线程正在运行");

			execSiteMission.execMissionMethod(this.site, this.taskLog);

			System.out.println(this.site.getSiteId() + "号任务线程执行已结束");

			return this.site.getSiteId();
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			System.out.println(this.site.getSiteId() + "号任务线程执行失败");

			return -1;
		}
	}
}
