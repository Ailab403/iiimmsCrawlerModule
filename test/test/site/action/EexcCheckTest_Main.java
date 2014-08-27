package test.site.action;

import java.util.Scanner;

import ims.crawler.exec.ExecCheck;
import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.SiteLog;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.SiteLogService;
import ims.site.model.Site;
import ims.site.service.SiteService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EexcCheckTest_Main {

	@Test
	public void testExecCheckMethod() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ExecCheck execCheck = (ExecCheck) appContext.getBean("execCheck");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		SiteLogService siteLogService = (SiteLogService) appContext
				.getBean("siteLogService");
		HandleLogResult handleLogResult = (HandleLogResult) appContext
				.getBean("handleLogResult");

		int siteId = new Scanner(System.in).nextInt();
		Site site = siteService.loadById(siteId);

		// 随便创建一个测试的siteLog，记得清空
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskLogId("20140504094926");
		handleLogResult.setTaskLog(taskLog);
		SiteLog siteLog = handleLogResult.createNewSiteLog(site);

		execCheck.execCheckMethod(site, siteLog);
	}
}
