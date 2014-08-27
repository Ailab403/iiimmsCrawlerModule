package ims.crawler.fetch.special;

import net.sf.json.JSONObject;

import org.jsoup.nodes.Element;

public class FetchJsonPostTime_BaiduTieBa {

	public static String getBaiduTieBaPostTime(Element eleBaiduTieBaPostDiv) {
		String strDataField = eleBaiduTieBaPostDiv.select(
				"div[data-field*=date]").first().attr("data-field");

		// 使用java对json的解析方法解析出date元素
		JSONObject jsonObject = JSONObject.fromObject(strDataField)
				.getJSONObject("content");
		String strDate = jsonObject.getString("date");

		System.out.println(strDataField);
		System.out.println(strDate);

		return strDate;
	}
}
