package test.site.action;

import ims.crawler.exec.ExecGrabPostUrls;
import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;
import ims.site.service.SiteService;

import java.util.Map;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecGrabPostUrlsTest_Main {

	public static void main(String[] args) {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		ExecGrabPostUrls execGrabPostUrls = (ExecGrabPostUrls) appContext
				.getBean("execGrabPostUrls");
		HandleLogResult handleLogResult = (HandleLogResult) appContext
				.getBean("handleLogResult");

		Site site = siteService.loadById(new Scanner(System.in).nextInt());

		// ����һ��handleLogResult����
		TaskLog taskLog = new TaskLog();
		taskLog.setTaskLogId("20140504094926");
		handleLogResult.setTaskLog(taskLog);

		// ����һ��siteLogΪ�˲����ã��ǵ����siteLog
		handleLogResult.createNewSiteLog(site);

		Map<String, Object> postFeedBackMap = execGrabPostUrls.execGrabMethod(
				site, handleLogResult);

		// ��÷�������
		if (postFeedBackMap == null) {
			System.out.println("ִ�к��������쳣");
			return;
		} else {
			int grabNewPostNum = (Integer) postFeedBackMap
					.get("grabNewPostNum");
			int grabUpdatePostNum = (Integer) postFeedBackMap
					.get("grabUpdatePostNum");
			int grabFixPostNum = (Integer) postFeedBackMap
					.get("grabFixPostNum");
			String grabCostTime = (String) postFeedBackMap.get("grabCostTime");

			System.out.println("�����ӣ�" + grabNewPostNum + " ���ظ������ӣ�"
					+ grabUpdatePostNum + " ��������ӣ�" + grabFixPostNum + " �ķ�ʱ�䣺"
					+ grabCostTime);
		}

	}
}
