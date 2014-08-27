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

		// ���ӵ�ַ��ͨ���Ķ�htmlԴ�����ã���Ϊ��½���ύ��URL��
		String surl = extraParame.getLoginUrl();

		HttpURLConnection connection = null;

		try {
			/*
			 * ����Ҫ��URL�µ�URLConnection�Ի��� URLConnection���Ժ����׵Ĵ�URL�õ������磺 // Using
			 * java.net.URL and //java.net.URLConnection
			 */
			URL url = new URL(surl);
			connection = (HttpURLConnection) url.openConnection();

			// ��������ʱ��
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			/*
			 * Ȼ���������Ϊ���ģʽ��URLConnectionͨ����Ϊ������ʹ�ã���������һ��Webҳ��
			 * ͨ����URLConnection��Ϊ���������԰����������Webҳ���͡��������������
			 */
			connection.setDoOutput(true);

			/*
			 * ���Ϊ�˵õ�OutputStream�������������Լ����Writer���ҷ���POST��Ϣ�У����磺 ...
			 */
			OutputStreamWriter out = new OutputStreamWriter(connection
					.getOutputStream(), enCode);
			// ���е�memberName��passwordҲ���Ķ�html�����֪�ģ���Ϊ���ж�Ӧ�Ĳ�������
			out.write("username=" + extraParame.getLoginName() + "&password="
					+ extraParame.getLoginPwd()); // post�Ĺؼ����ڣ�
			// remember to clean up
			out.flush();
			out.close();

			// ȡ��cookie���൱�ڼ�¼����ݣ����´η���ʱʹ��
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

		// �Ͽ�����
		connection.disconnect();
		return cookieVal;
	}

	// ��Ҫ�߳�ͬ������ֹ���ֵ�½
	public synchronized static Document getDocument(String url,
			ExtraParame extraParame, String baseUri, String enCode) {

		// ����cookie�����ڣ��Ž��е�½����
		if (cookieVal == null) {
			cookieVal = login(extraParame, enCode);
			System.out.println(cookieVal);
		}

		Document document = null;
		try {
			// ���´�һ������
			URL redicUrl = new URL(url);
			HttpURLConnection resumeConnection = (HttpURLConnection) redicUrl
					.openConnection();
			// ���ó�ʱ����
			resumeConnection.setConnectTimeout(120000);
			resumeConnection.setReadTimeout(120000);

			if (cookieVal != null) {
				// ����cookie��Ϣ��ȥ���Ա����Լ�����ݣ�����ᱻ��Ϊû��Ȩ��
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

			// �Ͽ�����
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
