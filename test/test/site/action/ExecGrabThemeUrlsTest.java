package test.site.action;

import ims.crawler.exec.ExecGrabThemeUrls;
import ims.site.model.Site;
import ims.site.service.SiteService;

import java.util.Map;
import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecGrabThemeUrlsTest {

	@Test
	public void testEexcGrabMethod() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		ExecGrabThemeUrls execGrabThemeUrls = (ExecGrabThemeUrls) appContext
				.getBean("execGrabThemeUrls");

		Site site = siteService.loadById(new Scanner(System.in).nextInt());

		Map<String, Object> resMap = execGrabThemeUrls.execGrabMethod(site);

		System.out.println(resMap);
	}
}
