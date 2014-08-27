package test.crawler.util;

import ims.crawler.util.HcJsoupDocumentUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class HttpClientJsoupDocumentUtilTest {

	@Test
	public void testGetDocument() {

		String testUrl = new Scanner(System.in).next();
		
		Document document = HcJsoupDocumentUtil.getDocument(testUrl,
				testUrl, "utf-8");

		System.out.println(document.text());
		
		String strResult = document.toString();

		try {
			File fileResult = new File("./file/html/testHtml.txt");
			if (!fileResult.exists()) {
				fileResult.createNewFile();
			}

			FileWriter fw = new FileWriter(fileResult);
			fw.write(strResult);

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(strResult);
	}
}
