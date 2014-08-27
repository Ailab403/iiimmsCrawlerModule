package test.crawler.cache;

import ims.crawler.cache.SiteStatusExp;
import ims.crawler.cache.TaskStatusExp;

import org.junit.Test;

public class StatusExpTest {

	@Test
	public void testTaskStatus() {
		for (int i = 0; i < TaskStatusExp.taskStatusExp.length; i++) {
			System.out.println(TaskStatusExp.taskStatusExp[i]);
		}
	}

	@Test
	public void testSiteStatus() {
		for (int i = 0; i < SiteStatusExp.siteStatusExp.length; i++) {
			System.out.println(SiteStatusExp.siteStatusExp[i]);
		}
	}
}
