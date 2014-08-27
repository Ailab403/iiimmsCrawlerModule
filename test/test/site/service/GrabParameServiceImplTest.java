package test.site.service;

import ims.site.model.GrabParame;
import ims.site.service.GrabParameService;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class GrabParameServiceImplTest {

	@Test
	public void testLoadBySiteId() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		GrabParameService grabParameService = (GrabParameService) appContext
				.getBean("grabParameService");

		int siteId = new Scanner(System.in).nextInt();
		GrabParame grabParame = grabParameService.loadBySiteId(siteId);

		System.out.println(grabParame != null ? grabParame.toString() : "null");
		
	}
}
