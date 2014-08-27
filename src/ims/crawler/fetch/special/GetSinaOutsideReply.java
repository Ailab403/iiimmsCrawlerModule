package ims.crawler.fetch.special;

import ims.crawler.grab.util.AnalyzerTime;
import ims.site.model.GrabUserParame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class GetSinaOutsideReply {

	/**
	 * 得到回复隐藏页的内容集合
	 * 
	 * @param articlePageUrl
	 * @return
	 */
	public List<Map<String, Object>> getReplyPageContents(
			String articlePageUrl, GrabUserParame grabUserParame) {

		// 准备要返回的数据
		List<Map<String, Object>> eachArticleReplyMaps = new ArrayList<Map<String, Object>>();

		// 获取回复隐藏页面的url模版
		String replyPageUrlModel = articlePageUrl.replaceAll("blog", "comment");
		replyPageUrlModel = replyPageUrlModel.replaceFirst("comment", "blog");
		replyPageUrlModel = replyPageUrlModel.substring(0, replyPageUrlModel
				.lastIndexOf("."));

		System.out.println(replyPageUrlModel);

		// 最多解析前10页的回复内容
		// 判断是否回复全部为空
		boolean flagReplyNull = true;
		for (int i = 1; i <= 10; i++) {
			String testReplyPageUrl = replyPageUrlModel + "_" + i + ".html";

			// 尝试获得真实的回复页面html代码
			String testRealReplyHtml = this.getRealReplyHtml(testReplyPageUrl);

			// 判断回复页面的html代码的有效性，如果无效及时退出
			if (testRealReplyHtml.contains("noCommdate")
					|| testRealReplyHtml.equals("")) {
				break;
			} else {
				Document docAllReply = Jsoup.parse(testRealReplyHtml);

				// 获取每条回复的div元素集合
				Elements elesReplyDiv = docAllReply
						.select("div.SG_revert_Cont");

				for (Element eleReplyDiv : elesReplyDiv) {

					// 获取回复作者信息
					Element eleReplyAuthorDiv = eleReplyDiv.select(
							"span[id^=nick_cmt]").first();
					String strReplyAuthor = eleReplyAuthorDiv != null ? eleReplyAuthorDiv
							.text()
							: "";

					// 获取回复内容信息
					Element eleReplyContentDiv = eleReplyDiv.select(
							"div[id^=body_cmt]").first();
					String strReplyContent = eleReplyContentDiv != null ? eleReplyContentDiv
							.text()
							: "";

					// TODO delete print
					System.out.println(strReplyContent);

					// 获取回复时间信息
					Element eleReplyTimeDiv = eleReplyDiv.select(
							"span.SG_revert_Time").first();
					String strReplyTime = eleReplyTimeDiv != null ? eleReplyTimeDiv
							.text()
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date());
					strReplyTime = AnalyzerTime
							.transStandardTimeFormat(strReplyTime);

					// 将分析出来的各部分内容组合
					Map<String, Object> replyMap = new HashMap<String, Object>();
					// 判断是否出现非空的情况
					if (!strReplyAuthor.equals("")
							|| !strReplyContent.equals("")
							|| !strReplyTime.equals("")) {

						// 判断如果时间不再截至范围之内就不加入情报库映射对象中
						if (!AnalyzerTime
								.compTimeLimit(grabUserParame
										.getPostEndTimeLimit().toString(),
										strReplyTime)) {
							continue;
						}

						// 一旦有回复映射对象存入，标记回复映射对象不为空
						flagReplyNull = false;

						if (!strReplyAuthor.equals("")) {
							replyMap.put("replyAuthor", strReplyAuthor);
						}
						if (!strReplyContent.equals("")) {
							replyMap.put("replyContent", strReplyContent);
						}
						if (!strReplyTime.equals("")) {
							replyMap.put("replyTime", strReplyTime);
						}

						eachArticleReplyMaps.add(replyMap);

					} else {
						continue;
					}
				}
			}
		}

		// 如果没有发现一条非空的回复信息，说明根本没有回复信息，那么就不设置回复信息
		if (flagReplyNull) {
			eachArticleReplyMaps = null;
		}

		return eachArticleReplyMaps;
	}

	/**
	 * 得到单个隐藏页的回复部分html代码
	 * 
	 * @param replyPageUrl
	 * @return
	 */
	public String getRealReplyHtml(String replyPageUrl) {

		String htmlStr = "";

		try {
			URL url;
			url = new URL(replyPageUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("加载javascript外部内容成功");
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String strCurrentLine = "";
				String strTotalString = "";
				while ((strCurrentLine = bufferedReader.readLine()) != null) {
					strTotalString += strCurrentLine;
				}
				JSONObject jsonObject = JSONObject.fromObject(strTotalString);

				htmlStr = jsonObject.get("data").toString();
			} else {
				System.err.println("加载javascript外部内容失败");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return htmlStr;
	}
}
