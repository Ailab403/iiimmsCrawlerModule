package ims.crawler.fetch.special;

import java.text.SimpleDateFormat;
import java.util.Date;

import ims.site.model.Site;

import org.jsoup.nodes.Element;

public class FetchSpecialTimeContent {

	// 解析特殊时间匹配元素的方法
	public static String getSpecialTimeContent(Element element, Site site) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		// 对qq论坛的特殊处理
		if (site.getSeedUrl().contains("qq")) {
			postTimeStr = element.attr("title");
			if (!postTimeStr.startsWith("20")) {
				postTimeStr = "1999-01-01 00:00:00";
			}
		}

		// 奥一论坛特殊对待
		if (site.getSeedUrl().contains("oeeee")) {
			if (element.select("span").size() != 0) {
				postTimeStr = element.select("span").first().attr("title");
				if (!postTimeStr.contains("20")) {
					postTimeStr = "1999-01-01 00:00:00";
				}
			} else {
				postTimeStr = element.text();
			}
		}

		return postTimeStr;
	}

	// 解析特殊数字匹配元素的方法
	public static String getSpecialNumContent(Element element, Site site) {

		return null;
	}
}
