package ims.crawler.util;

import ims.crawler.util.special.LoginSinaWeiboMobile;
import ims.site.model.ExtraParame;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HcLoginJsoupDocumentUtil {

	public static DefaultHttpClient client = new DefaultHttpClient(
			new ThreadSafeClientConnManager());

	/**
	 * 登录新浪微博
	 * 
	 * @param extraParame
	 */
	public static void loginWeibo(ExtraParame extraParame) {
		// client = LoginSinaWeibo.loginSina(extraParame.getLoginName(),
		// extraParame.getLoginPwd());
		
		client = LoginSinaWeiboMobile.loginMobile(extraParame.getLoginName(),
				extraParame.getLoginPwd());
	}

	/**
	 * 获取已登录的html代码字符串
	 * 
	 * @param url
	 */
	public static String getLoginedHtmlStr(ExtraParame extraParame, String url) {

		String requestHtmlStr = null;

		try {
			// 判断client有没有存储cookie来指导是否已经登陆
			if (client.getCookieStore().getCookies().size() == 0) {
				loginWeibo(extraParame);
			}

			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = client.execute(getMethod);
			requestHtmlStr = EntityUtils.toString(response.getEntity());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return requestHtmlStr;
	}

	/**
	 * 获取Jsoup的document
	 * 
	 * @param url
	 * @param extraParame
	 * @param baseUri
	 * @param enCode
	 * @return
	 */
	public synchronized static Document getDocument(String url,
			ExtraParame extraParame, String baseUri) {

		String responseHtmlStr = getLoginedHtmlStr(extraParame, url);
		Document document = Jsoup.parse(responseHtmlStr, baseUri);

		return document;
	}
}