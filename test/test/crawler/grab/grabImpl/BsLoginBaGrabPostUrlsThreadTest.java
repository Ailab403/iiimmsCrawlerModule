package test.crawler.grab.grabImpl;

import java.util.Map;
import java.util.Set;

import ims.crawler.grab.grabImpl.BsLoginBaGrabPostUrlsThread;
import ims.crawler.grab.grabImpl.HcWeiboGrabPostUrlsThread;
import ims.site.model.ExactUrl;
import ims.site.model.ExtraParame;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.ExtraParameService;
import ims.site.service.GrabParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.SiteService;
import ims.site.service.ThemeService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BsLoginBaGrabPostUrlsThreadTest {

	@Test
	public void testGetThemePageUrlSet() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		GrabUserParameService grabUserParameService = (GrabUserParameService) appContext
				.getBean("grabUserParameService");
		GrabParameService grabParameService = (GrabParameService) appContext
				.getBean("grabParameService");
		ThemeService themeService = (ThemeService) appContext
				.getBean("themeService");
		ExtraParameService extraParameService = (ExtraParameService) appContext
				.getBean("extraParameService");

		Site site = siteService.loadById(32);
		Theme theme = themeService.loadById(175);
		GrabParame grabParame = grabParameService
				.loadBySiteId(site.getSiteId());
		GrabUserParame grabUserParame = grabUserParameService.loadBySiteId(site
				.getSiteId());
		ExtraParame extraParame = extraParameService.loadBySiteId(site
				.getSiteId());

		HcWeiboGrabPostUrlsThread hcWeiboGrabPostUrlsThread = new HcWeiboGrabPostUrlsThread(
				grabParame, grabUserParame, site, theme, extraParame);

		Set<ExactUrl> urls = hcWeiboGrabPostUrlsThread.getThemePageUrlSet();
		for (ExactUrl exactUrl : urls) {
			System.out.println(exactUrl.getUrl());
		}
	}

	@Test
	public void testGetAllPagePostSet() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		GrabUserParameService grabUserParameService = (GrabUserParameService) appContext
				.getBean("grabUserParameService");
		GrabParameService grabParameService = (GrabParameService) appContext
				.getBean("grabParameService");
		ThemeService themeService = (ThemeService) appContext
				.getBean("themeService");
		ExtraParameService extraParameService = (ExtraParameService) appContext
				.getBean("extraParameService");

		Site site = siteService.loadById(1);
		Theme theme = themeService.loadById(34);
		GrabParame grabParame = grabParameService
				.loadBySiteId(site.getSiteId());
		GrabUserParame grabUserParame = grabUserParameService.loadBySiteId(site
				.getSiteId());
		ExtraParame extraParame = extraParameService.loadBySiteId(site
				.getSiteId());

		BsLoginBaGrabPostUrlsThread bsLoginBaGrabPostUrlsThread = new BsLoginBaGrabPostUrlsThread(
				grabParame, grabUserParame, site, theme, extraParame);

		// Set<ExactUrl> urls =
		// bsLoginBaGrabPostUrlsThread.getThemePageUrlSet();

		// for (ExactUrl exactUrl : urls) {
		// System.out.println(exactUrl.getUrl());
		// }

		Map<String, Set<Post>> resMap = bsLoginBaGrabPostUrlsThread
				.getSpPostSet("http://tieba.baidu.com/f?kw=%CD%F8%B1%AC&pn=200");

		Set<Post> posts = resMap.get("add");
		System.out.println(posts.size());
		for (Post post : posts) {
			System.out.println(post.getPostName());
		}
	}
}
