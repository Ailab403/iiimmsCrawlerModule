package test.crawler.fetch.special;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ims.crawler.fetch.special.FetchSpecialContentSiteList;
import ims.site.model.Site;
import ims.site.service.SiteService;

public class FetchSpecialContentSiteListTest {

	@Test
	public void testIsInSpecialTimeSiteList() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		// TODO delete set
		Site testSite = new Site();
		// testSite.setNickName("tianyaluntan");
		testSite.setNickName("qqluntan");

		boolean flag = FetchSpecialContentSiteList
				.isInSpecialTimeSiteList(testSite);

		System.out.println(flag);
	}
}
