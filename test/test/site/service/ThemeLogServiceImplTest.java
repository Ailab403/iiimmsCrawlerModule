package test.site.service;

import ims.crawlerLog.model.ThemeLog;
import ims.crawlerLog.service.ThemeLogService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ThemeLogServiceImplTest {

	@Test
	public void testUpdate() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ThemeLogService themeLogService = (ThemeLogService) appContext
				.getBean("themeLogService");

		ThemeLog themeLog = themeLogService.loadById("20140425113125332");
		themeLog.setThemeFetchNum(1);
		themeLog.setThemeFetchSuccNum(1);
		themeLog.setThemeLogExp("ÐÞ¸Ä³É¹¦");

		themeLogService.update(themeLog);
	}
}
