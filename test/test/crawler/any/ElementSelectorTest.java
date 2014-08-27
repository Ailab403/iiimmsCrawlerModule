package test.crawler.any;

import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class ElementSelectorTest {

	@Test
	public void testSelector() {

		String url = new Scanner(System.in).next();

		Document docTest = BasicJsoupDocumentUtil.getDocument(url);
		
		Element root = docTest.select("div.tzbdP").first();
		
		// System.out.println(root);

		Element element = root.select("div.box2.js-reply").first();

		// Element element2 = element.select("div.h_nr.js-reply-body").first();
		
		System.out.println(element.text());
	}
}
