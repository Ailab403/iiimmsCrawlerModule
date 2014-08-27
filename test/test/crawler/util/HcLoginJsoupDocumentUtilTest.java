package test.crawler.util;

import ims.crawler.util.HcLoginJsoupDocumentUtil;
import ims.site.model.ExtraParame;

import java.io.File;
import java.io.FileWriter;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class HcLoginJsoupDocumentUtilTest {

	@Test
	public void testLoginWeiboCookie() {
		DefaultHttpClient testClient = HcLoginJsoupDocumentUtil.client;

		ExtraParame extraParame = new ExtraParame();
		extraParame.setLoginName("superhy199148@gmail.com");
		extraParame.setLoginPwd("232323");

		System.out.println(testClient.getCookieStore().getCookies().size());

		HcLoginJsoupDocumentUtil.loginWeibo(extraParame);
		testClient = HcLoginJsoupDocumentUtil.client;

		System.out.println(testClient.getCookieStore().getCookies().size());
		if (testClient.getCookieStore().getCookies().size() != 0) {
			for (Cookie cookie : testClient.getCookieStore().getCookies()) {
				System.out.println(cookie.toString());
			}
		}
	}

	@Test
	public void testLoginWeiboAccess() throws Exception {

		ExtraParame extraParame = new ExtraParame();
		extraParame.setLoginName("superhy199148@gmail.com");
		extraParame.setLoginPwd("232323");

		String strResult = HcLoginJsoupDocumentUtil.getLoginedHtmlStr(
				extraParame, "http://weibo.cn/pub/");

		File fileResult = new File("./file/html/testHtml2.txt");
		if (!fileResult.exists()) {
			fileResult.createNewFile();
		}
		FileWriter fw = new FileWriter(fileResult);
		fw.write(strResult);
		fw.close();
	}

	@Test
	public void testLoginWeiboDocument() {

		ExtraParame extraParame = new ExtraParame();
		extraParame.setLoginName("superhy199148@gmail.com");
		extraParame.setLoginPwd("232323");

		Document document = HcLoginJsoupDocumentUtil.getDocument(
				"http://weibo.cn/pub/", extraParame, "http://weibo.cn");
		String nickName = document.select("div.ut").first().ownText();

		System.out.println(nickName);
	}
}
