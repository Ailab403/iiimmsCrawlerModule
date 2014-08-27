package test.crawler.any;

import java.util.Scanner;

import ims.crawler.util.HcJsoupDocumentUtil;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class DouBanUrlTest {

	@Test
	public void testDouban() {

		String url = new Scanner(System.in).next();

		// baseUri ʹ�õ�ǰurl���ɣ�getBaseUri������
		Document docTest = HcJsoupDocumentUtil.getDocument(url, url, "UTF-8");
		String absUrl = docTest.select("div.ba_info>a").first()
				.attr("abs:href");

		System.out.println(absUrl);
	}
}
