package test.crawler.fetch.special;

import ims.crawler.fetch.special.FetchJsonPostTime_BaiduTieBa;
import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;


public class FetchBaiduTieBaPostTimeTest {

	@Test
	public void testGetBaiduTieBaPostTime() {
		FetchJsonPostTime_BaiduTieBa testObj = new FetchJsonPostTime_BaiduTieBa();

		Scanner cin = new Scanner(System.in);
		String strPostLink = cin.next();

		Document docBaiduTieBaPost = BasicJsoupDocumentUtil.getDocument(strPostLink);
		Element eleBaiduTieBaPostDiv = docBaiduTieBaPost.select(
				"div[class*=l_post]").first();
		
		testObj.getBaiduTieBaPostTime(eleBaiduTieBaPostDiv);
	}
}
