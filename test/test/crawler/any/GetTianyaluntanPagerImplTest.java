package test.crawler.any;

import ims.crawler.fetch.util.pagerImpl.GetBaidutiebaPagerImpl;
import ims.crawler.fetch.util.pagerImpl.GetTianyaluntanPagerImpl;
import ims.site.service.FetchParameService;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetTianyaluntanPagerImplTest {

	@Test
	public void testGetPagerUrls() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		FetchParameService fetchParameService = (FetchParameService) appContext
				.getBean("fetchParameService");

		String urlPost = new Scanner(System.in).next();

		String pagerQuery = fetchParameService.loadBySiteId(2).getPagerQuery();

		GetTianyaluntanPagerImpl getTianyaluntanPagerImpl = new GetTianyaluntanPagerImpl();
		List<String> listPagerUrl = getTianyaluntanPagerImpl.getPagerUrls(
				urlPost, pagerQuery);

		for (String string : listPagerUrl) {
			System.out.println(string);
		}
	}
}
