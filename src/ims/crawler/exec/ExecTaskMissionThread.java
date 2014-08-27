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

	// ����Ĳ���ʵ��
	private Site site;
	private TaskLog taskLog;

	public ExecTaskMissionThread(Site site, TaskLog taskLog) {
		super();
		this.site = site;
		this.taskLog = taskLog;
	}

	public Integer call() {

		try {
			// ��spring�л�ȡվ�������ִ�ж���
			ExecSiteMission execSiteMission = (ExecSiteMission) ApplicationContextFactory.appContext
					.getBean("execSiteMission");

			System.out.println(this.site.getSiteId() + "�������߳���������");

			execSiteMission.execMissionMethod(this.site, this.taskLog);

			System.out.println(this.site.getSiteId() + "�������߳�ִ���ѽ���");

			return this.site.getSiteId();
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			System.out.println(this.site.getSiteId() + "�������߳�ִ��ʧ��");

			return -1;
		}
	}
}
