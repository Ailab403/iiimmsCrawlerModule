package ims.crawler.util;

import ims.site.model.ExtraParame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BsLoginJsoupDocumentUtil {

	private static String cookieVal = null;

	public static String login(ExtraParame extraParame, String enCode) {

		// 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
		String surl = extraParame.getLoginUrl();

		HttpURLConnection connection = null;

		try {
			/*
			 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
			 * java.net.URL and //java.net.URLConnection
			 */
			URL url = new URL(surl);
			connection = (HttpURLConnection) url.openConnection();

			// 设置链接时限
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			/*
			 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
			 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
			 */
			connection.setDoOutput(true);

			/*
			 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
			 */
			OutputStreamWriter out = new OutputStreamWriter(connection
					.getOutputStream(), enCode);
			// 其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
			out.write("username=" + extraParame.getLoginName() + "&password="
					+ extraParame.getLoginPwd()); // post的关键所在！
			// remember to clean up
			out.flush();
			out.close();

			// 取得cookie，相当于记录了身份，供下次访问时使用
			cookieVal = connection.getHeaderField("Set-Cookie");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cookieVal = "domain=.baidu.com;";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cookieVal = "domain=.baidu.com;";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cookieVal = "domain=.baidu.com;";
		}

		// 断开连接
		connection.disconnect();
		return cookieVal;
	}

	// 需要线程同步，防止过分登陆
	public synchronized static Document getDocument(String url,
			ExtraParame extraParame, String baseUri, String enCode) {

		// 发现cookie不存在，才进行登陆操作
		if (cookieVal == null) {
			cookieVal = login(extraParame, enCode);
			System.out.println(cookieVal);
		}

		Document document = null;
		try {
			// 重新打开一个连接
			URL redicUrl = new URL(url);
			HttpURLConnection resumeConnection = (HttpURLConnection) redicUrl
					.openConnection();
			// 设置超时限制
			resumeConnection.setConnectTimeout(120000);
			resumeConnection.setReadTimeout(120000);

			if (cookieVal != null) {
				// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
				resumeConnection.setRequestProperty("Cookie", cookieVal);
			}
			resumeConnection.connect();
			InputStream urlStream = resumeConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlStream));
			String ss = null;
			String total = "";
			while ((ss = bufferedReader.readLine()) != null) {
				total += ss.replaceAll("<!--", "").replaceAll("-->", "");
			}
			bufferedReader.close();

			// 断开链接
			resumeConnection.disconnect();
			document = Jsoup.parse(total, baseUri);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return document;
	}
}
