package test.crawler.any;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class SinaBlogParseTest {
	public static void main(String[] args) {
		// String urlStr =
		// "http://blog.sina.com.cn/s/blog_4ffe69630102ei9p.html?tj=1";
		String urlStr = "http://blog.sina.com.cn/s/comment_5054769e0102efuk_1.html";
		// String urlStr =
		// "http://blog.sina.com.cn/s/comment_4120db8b0102ecia_5.html";
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.err.println("³É¹¦");
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String sCurrentLine = "";
				String sTotalString = "";
				while ((sCurrentLine = bufferedReader.readLine()) != null) {
					sTotalString += sCurrentLine;
				}
				JSONObject jsonObject = JSONObject.fromObject(sTotalString);
				String htmlStr = jsonObject.get("data").toString();

				// TODO delete print
				System.out.println(htmlStr);

				try {
					File fileResult = new File(
							"./file/html/testSinaBlogHtml.txt");
					if (!fileResult.exists()) {
						fileResult.createNewFile();
					}

					FileWriter fw = new FileWriter(fileResult);
					fw.write(htmlStr);

					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (sTotalString.equals("OK")) {
				} else {
				}
			} else {
				System.err.println("Ê§°Ü");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
