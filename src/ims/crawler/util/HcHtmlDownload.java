package ims.crawler.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

public class HcHtmlDownload {
	// 返回指定URL地址的页面内容
	public static String downLoadDepthMarketPage(String loginURL, String enCode)
			throws Exception {

		try {
			// 设置各种连接池参数
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
					.getSocketFactory()));
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
					.getSocketFactory()));
			ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
					schemeRegistry);
			cm.setDefaultMaxPerRoute(20);
			HttpHost localhost = new HttpHost("locahost", 80);
			cm.setMaxForRoute(new HttpRoute(localhost), 50);
			HttpClient httpClient = new DefaultHttpClient(cm);
			// 设置连接超时和读数超时
			httpClient.getParams().setParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 120000);
			httpClient.getParams().setParameter(
					HttpConnectionParams.SO_TIMEOUT, 120000);
			// 设置忽略过期页面
			httpClient.getParams().setParameter(
					HttpConnectionParams.STALE_CONNECTION_CHECK, false);
			// 设置中文编码格式
			httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, enCode);

			// 使用模拟浏览器下载页面实体
			HttpGet getMethod = new HttpGet(loginURL);
			// getMethod.setHeader("Content-type", "text/html; charset=" +
			// enCode);
			getMethod
					.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();

			// 将页面实体转化成字符流
			InputStream in = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			br.close();
			String wholePageStr = changeCharset(sb.toString(), enCode);
			// 关闭链接
			httpClient.getConnectionManager().shutdown();
			cm.shutdown();

			// System.out.println(wholePageStr);

			return wholePageStr;
		} catch (Exception ex) {
			System.out.println(ex);

			return null;
		}

	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换编码的字符串
	 * @param newCharset
	 *            目标编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String changeCharset(String str, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			// 用默认字符编码解码字符串。
			byte[] bs = str.getBytes();
			// 用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}
}
