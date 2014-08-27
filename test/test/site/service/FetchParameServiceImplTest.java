package test.site.service;

import ims.site.model.FetchParame;
import ims.site.service.FetchParameService;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FetchParameServiceImplTest {

	@Test
	public void TestLoadBySiteId() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		FetchParameService fetchParameService = (FetchParameService) appContext
				.getBean("fetchParameService");

		int siteId = new Scanner(System.in).nextInt();
		FetchParame fetchParame = fetchParameService.loadBySiteId(siteId);

		System.out.println(fetchParame != null ? fetchParame.toString()
				: "null");
	}
}
