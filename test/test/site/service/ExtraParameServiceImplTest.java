package test.site.service;

import java.util.Scanner;

import ims.site.model.ExtraParame;
import ims.site.service.ExtraParameService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExtraParameServiceImplTest {

	@Test
	public void testLoadBySiteId() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ExtraParameService extraParameService = (ExtraParameService) appContext
				.getBean("extraParameService");

		int siteId = new Scanner(System.in).nextInt();

		ExtraParame extraParame = extraParameService.loadBySiteId(siteId);

		System.out.println(extraParame.toString());
	}
}
