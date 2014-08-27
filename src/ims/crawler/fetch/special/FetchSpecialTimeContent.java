package ims.crawler.fetch.special;

import java.text.SimpleDateFormat;
import java.util.Date;

import ims.site.model.Site;

import org.jsoup.nodes.Element;

public class FetchSpecialTimeContent {

	// ��������ʱ��ƥ��Ԫ�صķ���
	public static String getSpecialTimeContent(Element element, Site site) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		// ��qq��̳�����⴦��
		if (site.getSeedUrl().contains("qq")) {
			postTimeStr = element.attr("title");
			if (!postTimeStr.startsWith("20")) {
				postTimeStr = "1999-01-01 00:00:00";
			}
		}

		// ��һ��̳����Դ�
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

	// ������������ƥ��Ԫ�صķ���
	public static String getSpecialNumContent(Element element, Site site) {

		return null;
	}
}
