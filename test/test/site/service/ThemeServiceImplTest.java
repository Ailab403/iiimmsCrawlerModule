package test.site.service;

import ims.site.model.Theme;
import ims.site.service.ThemeService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ThemeServiceImplTest {

	@Test
	public void testLoadBySiteIdAndGrabable() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ThemeService themeService = (ThemeService) appContext
				.getBean("themeService");

		int siteId = new Scanner(System.in).nextInt();
		int themeGrabable = 1;
		Map<String, Object> siteIdAndGrabableMaps = new HashMap<String, Object>();
		siteIdAndGrabableMaps.put("siteId", siteId);
		siteIdAndGrabableMaps.put("themeGrabable", themeGrabable);
		Set<Theme> themes = themeService
				.listBySiteIdAndGrabable(siteIdAndGrabableMaps);

		for (Theme theme : themes) {
			System.out.println(theme.toString());
		}
	}

	@Test
	public void testLoadByThemeUrlMD5() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ThemeService themeService = (ThemeService) appContext
				.getBean("themeService");

		String themeUrlMD5 = new Scanner(System.in).next();
//		Theme theme = new Theme();
//		theme.setThemeUrlMD5(themeUrlMD5);
		System.out.println(themeService.loadByThemeUrlMD5(themeUrlMD5).toString());
	}
}
