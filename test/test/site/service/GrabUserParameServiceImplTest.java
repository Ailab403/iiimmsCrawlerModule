package test.site.service;

import ims.site.model.GrabUserParame;
import ims.site.service.GrabUserParameService;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class GrabUserParameServiceImplTest {

	@Test
	public void testLoadBySiteId() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		GrabUserParameService grabUserParameService = (GrabUserParameService) appContext
				.getBean("grabUserParameService");

		int siteId = new Scanner(System.in).nextInt();
		GrabUserParame grabUserParame = grabUserParameService
				.loadBySiteId(siteId);

		System.out.println(grabUserParame != null ? grabUserParame.toString()
				: "null");
	}
}
