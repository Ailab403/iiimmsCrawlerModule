package test.crawler.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ims.crawler.util.BsLoginJsoupDocumentUtil;
import ims.site.model.ExtraParame;
import ims.site.model.Site;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class BsLoginJsoupDocumentUtilTest {

	@Test
	public void testGetDocument() throws Exception {
		ExtraParame extraParame = new ExtraParame(
				1,
				"https://passport.baidu.com/v2/?login&tpl=mn&u=http%3A%2F%2Fwww.baidu.com%2F",
				"superhy199148", "qdhy199148", new Site());

		Document testDoc = BsLoginJsoupDocumentUtil.getDocument(
				"http://tieba.baidu.com/f?kw=%CD%F8%B1%AC&pn=200", extraParame,
				"www.baidu.com", "GBK");

		FileWriter fw = new FileWriter(new File("./file/html/baiduHtml.txt"));
		fw.write(testDoc.toString());
		// System.out.println(testDoc);
	}
}
