package test.site.service;

import ims.site.model.SiteCategory;
import ims.site.model.Site;
import ims.site.model.Tool;
import ims.site.service.SiteService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SiteServiceImplTest {

	@Test
	public void testAdd() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		Site site = new Site();
		site.setSiteName("天涯论坛");
		site.setNickName("tianyaluntan");
		String seedUrl = "http://bbs.tianya.cn/";
		site.setSeedUrl(seedUrl);
		site.setRefreshTime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		siteService.add(site);
	}

	@Test
	public void testDeleteById() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		int siteId = new Scanner(System.in).nextInt();

		siteService.deleteById(siteId);
	}

	@Test
	public void testUpdate() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		Site site = new Site();
		site.setSiteId(1);
		SiteCategory siteCategory = new SiteCategory();
		siteCategory.setCategoryId(2);
		site.setSiteCategory(siteCategory);
		Tool tool = new Tool();
		tool.setToolId(3);
		site.setTool(tool);
		site.setSiteName("百度贴吧");
		String seedUrl = "http://tieba.baidu.com/f/index/forumpark?cn=&ci=0&pcn=%C9%E7%BB%E1&pci=0&ct=1&st=popular";
		site.setNickName("baidutieba");
		site.setSeedUrl(seedUrl);
		site.setEnCode("utf-8");
		site.setSiteHotNum(0);
		site.setSiteGrabable(1);
		site.setRefreshTime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));
		site.setSiteExp("");

		siteService.update(site);
	}

	@Test
	public void testLoadById() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		int siteId = new Scanner(System.in).nextInt();
		Site site = siteService.loadById(siteId);

		System.out.println(site != null ? site.toString() : "null");
	}

	@Test
	public void testListAll() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		SiteService siteService = (SiteService) appContext
				.getBean("siteService");

		Set<Site> siteList = siteService.listAll();

		for (Site site : siteList) {
			System.out.println(site.toString());
		}
	}
}
