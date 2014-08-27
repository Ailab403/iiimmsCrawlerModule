package ims.crawler.util.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author superhy
 * 
 */
public class LoginSinaWeiboMobile {

	/**
	 * �ӵ�½�����л��һЩ�̶��Ĳ���
	 * 
	 * @param loginUrl
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> grabFormValue(String loginUrl)
			throws Exception {

		// ׼��Ҫ���ص�����
		Map<String, String> formParams = new HashMap<String, String>();

		HttpClient preClient = new DefaultHttpClient();
		HttpGet getMethod = new HttpGet(loginUrl);
		HttpResponse response = preClient.execute(getMethod);
		String loginHtml = EntityUtils.toString(response.getEntity());
		Document docLogin = Jsoup.parse(loginHtml);

		// ��ȡҳ��̶�����
		Element eleForm = docLogin.select("form").first();
		String passwordName = eleForm.select("input[name^=password]").first()
				.attr("name");
		String backTitle = eleForm.select("input[name=backTitle]").first()
				.attr("value");
		String tryCount = eleForm.select("input[name=tryCount]").first().attr(
				"value");
		String vk = eleForm.select("input[name=vk]").first().attr("value");
		String action = eleForm.select("form[method=post]").first().attr(
				"action");
		String submit = eleForm.select("input[name=submit]").first().attr(
				"value");

		formParams.put("passwordName", passwordName);
		formParams.put("backTitle", backTitle);
		formParams.put("tryCount", tryCount);
		formParams.put("vk", vk);
		formParams.put("action", action);
		formParams.put("submit", submit);

		// TODO delete print
		System.out.println("sina login params:" + formParams.toString());

		return formParams;
	}

	/**
	 * ִ�е�½����
	 * 
	 * @param u
	 * @param p
	 * @param loginUrl
	 * @return
	 */
	public static DefaultHttpClient loginMobile(String u, String p) {

		// ���ù̶���¼����
		String loginUrl = "http://login.weibo.cn/login/";
		// ��Ҫ�̰߳�ȫʽ���ʿͻ���
		DefaultHttpClient loginClient = new DefaultHttpClient(
				new ThreadSafeClientConnManager());

		try {

			HttpPost postMethod = new HttpPost(loginUrl);

			// ������Ϣͷ
			postMethod
					.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");
			postMethod
					.setHeader("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			postMethod.setHeader("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			postMethod.setHeader("Host", "login.weibo.cn");
			postMethod.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			// ���������Ͳ���
			Map<String, String> formParams = grabFormValue(loginUrl);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("mobile", u));
			nvps.add(new BasicNameValuePair(formParams.get("passwordName"), p));
			nvps
					.add(new BasicNameValuePair("backURL",
							"http%3A%2F%2Fweibo.cn"));
			nvps.add(new BasicNameValuePair("backTitle", formParams
					.get("backTitle")));
			nvps.add(new BasicNameValuePair("tryCount", formParams
					.get("tryCount")));
			nvps
					.add(new BasicNameValuePair("submit", formParams
							.get("submit")));
			nvps.add(new BasicNameValuePair("remenber", "off"));
			nvps.add(new BasicNameValuePair("vk", formParams.get("vk")));

			// ����������ύ�ĵ�ַ
			nvps.add(new BasicNameValuePair("url", "http://weibo.cn/"
					+ formParams.get("action")));

			// �ύ�����
			postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = loginClient.execute(postMethod);

			// TODO delete print
			System.out.println("login response:" + response.toString());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return loginClient;
	}
}
