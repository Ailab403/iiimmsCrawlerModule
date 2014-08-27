package test.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.pagerImpl.GetDoubanxiaozuPagerImpl;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class GetDoubanxiaozuPagerImplTest {

	@Test
	public void testGetPagerUrls() {
		String urlPost = new Scanner(System.in).next();
		String pagerQuery = "div.paginator";

		List<String> listPagerUrl = new GetDoubanxiaozuPagerImpl()
				.getPagerUrls(urlPost, pagerQuery);
		
		for (String string : listPagerUrl) {
			System.out.println(string);
		}
	}
}
