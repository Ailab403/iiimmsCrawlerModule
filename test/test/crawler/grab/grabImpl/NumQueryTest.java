package test.crawler.grab.grabImpl;

import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class NumQueryTest {
	
	@Test
	public void testNumQuery() throws Exception {
		Document docTest = Jsoup.parse(new File("./file/html/testHtml.txt"), "UTF-8");
		
		Element eleTest = docTest.select("tr:has(td[class^=td])").first();
		System.out.println(eleTest);
		
		Elements elesTest = eleTest.select("td:nth-child(4)");
		System.out.println(elesTest);
		
		System.out.println(docTest.select("td:matchesOwn(^\\d+$)").first());
	}
}
