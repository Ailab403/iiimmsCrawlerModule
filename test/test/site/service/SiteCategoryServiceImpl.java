package test.site.service;

import java.util.Set;

import ims.site.model.SiteCategory;
import ims.site.service.SiteCategoryService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SiteCategoryServiceImpl {

	@Test
	public void testListAll() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteCategoryService siteCategoryService = (SiteCategoryService) appContext
				.getBean("siteCategoryService");

		Set<SiteCategory> siteCategories = siteCategoryService.listAll();

		for (SiteCategory siteCategory : siteCategories) {
			System.out.println(siteCategory);
		}
	}
}
