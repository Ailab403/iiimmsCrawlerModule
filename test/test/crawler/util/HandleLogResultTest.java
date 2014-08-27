package test.crawler.util;

import java.util.Set;

import ims.crawler.util.HandleLogResult;
import ims.site.model.Site;
import ims.site.service.SiteService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HandleLogResultTest {

	@Test
	public void testCreateNewTaskLog() {

		// ��spring�л�ȡ����
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		HandleLogResult handleLogResult = (HandleLogResult) appContext
				.getBean("handleLogResult");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		Set<Site> sites = siteService.listBySiteGrabable(1);

		handleLogResult.createNewTaskLog(sites);
	}
}
