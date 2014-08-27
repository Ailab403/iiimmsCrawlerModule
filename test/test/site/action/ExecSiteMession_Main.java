package test.site.action;

import java.util.Scanner;

import ims.crawler.exec.ExecSiteMission;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;
import ims.site.service.SiteService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecSiteMession_Main {

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ExecSiteMission execSiteMission = (ExecSiteMission) appContext
				.getBean("execSiteMission");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		Site site = siteService.loadById(new Scanner(System.in).nextInt());
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskLogId("20140504094926");

		execSiteMission.execMissionMethod(site, taskLog);
	}
}
