package ims.crawler.grab.special;

import java.text.SimpleDateFormat;
import java.util.Date;

import ims.site.model.Site;

import org.jsoup.nodes.Element;

public class GrabSpecialTimeContent {

	// ��������ʱ��ƥ��Ԫ�صķ���
	public static String getSpecialTimeContent(Element element, Site site) {

		String postTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		// qq��̳����Դ�
		if (site.getSeedUrl().contains("qq")) {
			postTimeStr = element.attr("title");
			if (!postTimeStr.startsWith("20")) {
				postTimeStr = "1999-01-01 00:00:00";
			}
		}

		// ������������Դ�
		if (site.getSeedUrl().contains("dayoo")) {
			postTimeStr = element.attr("title");
			if (!postTimeStr.startsWith("20")) {
				postTimeStr = "1999-01-01 00:00:00";
			}
		}

		return postTimeStr;
	}

	// ������������ƥ��Ԫ�صķ���
	public static String getSpecialNumContent(Element element, Site site) {

		return null;
	}
}
