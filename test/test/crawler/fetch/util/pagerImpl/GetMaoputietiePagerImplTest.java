package test.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.pagerImpl.GetMaoputietiePagerImpl;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class GetMaoputietiePagerImplTest {

	@Test
	public void testGetPagerUrls() {
		String urlPost = new Scanner(System.in).next();
		String pagerQuery = "div.page";

		List<String> listPagerUrl = new GetMaoputietiePagerImpl()
				.getPagerUrls(urlPost, pagerQuery);

		for (String string : listPagerUrl) {
			System.out.println(string);
		}
	}
}
