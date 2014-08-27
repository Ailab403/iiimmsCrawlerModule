package test.site.action;

import java.util.Map;
import java.util.Scanner;

import ims.crawler.exec.ExecFetchContent;
import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.TaskLogService;
import ims.site.model.Site;
import ims.site.service.SiteService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecFetchContentTest_Main {

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		ExecFetchContent execFetchContent = (ExecFetchContent) appContext
				.getBean("execFetchContent");
		HandleLogResult handleLogResult = (HandleLogResult) appContext
				.getBean("handleLogResult");
		TaskLogService taskLogService = (TaskLogService) appContext
				.getBean("taskLogService");

		Site site = siteService.loadById(new Scanner(System.in).nextInt());

		// ����һ��handleLogResult����
		TaskLog taskLog = taskLogService.loadById("20140504094926");
		handleLogResult.setTaskLog(taskLog);
		Map<String, Object> fetchFeedBackMap = execFetchContent
				.execFetchMethod(site, handleLogResult);

		// ��÷�������
		if (fetchFeedBackMap == null) {
			System.out.println("ִ�к��������쳣");
			return;
		} else {
			int fetchNum = (Integer) fetchFeedBackMap.get("fetchNum");
			int fetchSuccNum = (Integer) fetchFeedBackMap.get("fetchSuccNum");
			String fetchTimeCost = (String) fetchFeedBackMap
					.get("fetchCostTime");

			System.out.println("�����ڵ�����" + fetchNum + "�����ɹ��ڵ��������ܲ�׼����"
					+ fetchSuccNum + "�ķ�ʱ�䣺" + fetchTimeCost);
		}

	}
}
