package ims.crawler.util.special;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class LoginSinaWeibo {

	/**
	 * ����URL,get��ҳ
	 * 
	 * @param url
	 * @throws IOException
	 */
	private static String get(String url, DefaultHttpClient client)
			throws IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		String result = dump(entity);
		get.abort();
		return result;
	}

	/**
	 * ����΢��Ԥ��¼����ȡ������ܹ�Կ
	 * 
	 * @param unameBase64
	 * @return ���شӽ����ȡ�Ĳ����Ĺ�ϣ��
	 * @throws IOException
	 */
	private static HashMap<String, String> preLogin(String unameBase64,
			DefaultHttpClient client) throws IOException {
		String url = "http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&rsakt=mod&client=ssologin.js(v1.4.5)&_="
				+ "_=" + new Date().getTime();
		return getParaFromResult(get(url, client));
	}

	/**
	 * �����˷��صĽ���ַ����л�ò���
	 * 
	 * @param result
	 * @return
	 */
	private static HashMap<String, String> getParaFromResult(String result) {
		HashMap<String, String> hm = new HashMap<String, String>();
		result = result.substring(result.indexOf("{") + 1, result.indexOf("}"));
		String[] r = result.split(",");
		String[] temp;
		for (int i = 0; i < r.length; i++) {
			temp = r[i].split(":");
			for (int j = 0; j < 2; j++) {
				if (temp[j].contains("\""))
					temp[j] = temp[j].substring(1, temp[j].length() - 1);
			}
			hm.put(temp[0], temp[1]);
		}
		return hm;
	}

	/**
	 * ��ӡҳ��
	 * 
	 * @param entity
	 * @throws IOException
	 */
	private static String dump(HttpEntity entity) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(entity
				.getContent(), "utf8"));
		return IOUtils.toString(br);
	}

	private static String encodeAccount(String account) {
		String userName = "";
		try {
			userName = Base64.encodeBase64String(URLEncoder.encode(account,
					"UTF-8").getBytes());
			// userName =
			// BASE64Encoder.encode(URLEncoder.encode(account,"UTF-8").getBytes());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return userName;
	}

	private static String makeNonce(int len) {
		String x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < len; i++) {
			str += x.charAt((int) (Math.ceil(Math.random() * 1000000) % x
					.length()));
		}
		return str;
	}

	private static String getServerTime() {
		// long servertime = new Date().getTime() / 1000;
		// return String.valueOf(servertime);

		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static DefaultHttpClient loginSina(String username, String password) {

		// ��Ҫ�̰߳�ȫʽ���ʿͻ���
		DefaultHttpClient client = new DefaultHttpClient(
				new ThreadSafeClientConnManager());

		try {
			// ���rsaPubkey,rsakv,servertime�Ȳ���ֵ
			HashMap<String, String> params = preLogin(encodeAccount(username),
					client);

			// Mobile
			// HttpPost post = new HttpPost(
			// "https://passport.sina.cn/signin/signin?entry=wapsso.js(v1.4.5)");

			HttpPost post = new HttpPost(
					"http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.2)");

			post
					.setHeader("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			post
					.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 5.1; rv:9.0.1) Gecko/20100101 Firefox/9.0.1");

			post.setHeader("Accept-Language", "zh-cn,zh;q=0.8");
			post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
			post.setHeader("Referer",
					"http://weibo.com/?c=spr_web_sq_firefox_weibo_t001");
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");

			String nonce = makeNonce(6);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("encoding", "UTF-8"));
			nvps.add(new BasicNameValuePair("entry", "weibo"));
			nvps.add(new BasicNameValuePair("from", ""));
			nvps.add(new BasicNameValuePair("gateway", "1"));
			nvps.add(new BasicNameValuePair("nonce", nonce));
			nvps.add(new BasicNameValuePair("pagerefer",
					"http://i.firefoxchina.cn/old/"));
			nvps.add(new BasicNameValuePair("prelt", "111"));
			nvps.add(new BasicNameValuePair("pwencode", "rsa2"));
			nvps.add(new BasicNameValuePair("returntype", "META"));
			nvps.add(new BasicNameValuePair("rsakv", params.get("rsakv")));
			nvps.add(new BasicNameValuePair("savestate", "0"));
			nvps.add(new BasicNameValuePair("servertime", params
					.get("servertime")));

			nvps.add(new BasicNameValuePair("service", "miniblog"));
			// nvps.add(new BasicNameValuePair("sp", new
			// SinaSSOEncoder().encode(p, data, nonce)));

			/******************** *�������� ***************************/
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine se = sem.getEngineByName("javascript");
			// FileReader f = new FileReader("d://sso.js");
			se.eval(SinaSSOEncoder.getJs());
			String pass = "";

			if (se instanceof Invocable) {
				Invocable invoke = (Invocable) se;
				// ����preprocess���������������������������֤��

				pass = invoke.invokeFunction("getpass", password,
						params.get("servertime"), nonce, params.get("pubkey"))
						.toString();

				System.out.println("c = " + pass);
			}

			nvps.add(new BasicNameValuePair("sp", pass));
			nvps.add(new BasicNameValuePair("su", encodeAccount(username)));
			nvps
					.add(new BasicNameValuePair(
							"url",
							"http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));

			nvps.add(new BasicNameValuePair("useticket", "1"));
			// nvps.add(new BasicNameValuePair("ssosimplelogin", "1"));
			nvps.add(new BasicNameValuePair("vsnf", "1"));

			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = client.execute(post);

			String entity = EntityUtils.toString(response.getEntity());

			// System.out.println(entity.toString());

			if (entity.replace("\"", "").indexOf("retcode=0") > -1) {

				// fix code
				String url = entity.substring(entity
						.indexOf("http://passport.weibo.com/wbsso/login?"),
						entity.indexOf("code=0") + 6);

				String strScr = ""; // ��ҳ�û�script��ʽ����
				String nickname = "����"; // �ǳ�

				// ��ȡ��ʵ��url��������
				HttpGet getMethod = new HttpGet(url);
				response = client.execute(getMethod);
				entity = EntityUtils.toString(response.getEntity());

				// System.out.println(entity);

				nickname = entity.substring(entity.indexOf("displayname") + 14,
						entity.lastIndexOf("userdomain") - 3).trim();

				url = entity.substring(entity.indexOf("userdomain") + 13,
						entity.lastIndexOf("\""));
				getMethod = new HttpGet("http://weibo.com/" + url);
				response = client.execute(getMethod);
				entity = EntityUtils.toString(response.getEntity());

				// System.out.println(entity.toString());

				Document doc = Jsoup.parse(entity);
				Elements els = doc.select("script");

				if (els != null && els.size() > 0) {
					for (int i = 0, leg = els.size(); i < leg; i++) {

						if (els.get(i).html().indexOf("$CONFIG") > -1) {
							strScr = els.get(i).html();
							break;
						}
					}
				}

				if (!strScr.equals("")) {
					ScriptEngineManager manager = new ScriptEngineManager();
					ScriptEngine engine = manager.getEngineByName("javascript");

					engine.eval("function getMsg(){" + strScr
							+ "return $CONFIG['onick'];}");
					if (engine instanceof Invocable) {
						Invocable invoke = (Invocable) engine;
						// ����preprocess���������������������������֤��

						nickname = invoke.invokeFunction("getMsg", null)
								.toString();

					}
				}

				System.out.println(nickname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;
	}
}
