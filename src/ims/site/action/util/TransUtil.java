package ims.site.action.util;

import java.util.ArrayList;
import java.util.List;

public class TransUtil {

	public List<Integer> transSiteId(String siteIdListStr) {
		// 如果字符串是null，返回空值
		if (siteIdListStr == null || siteIdListStr.equals("")) {
			return null;
		}

		List<Integer> list = new ArrayList<Integer>();
		String[] stringArr = siteIdListStr.split(",");
		for (int i = 0; i < stringArr.length; i++) {
			int a = Integer.parseInt(stringArr[i]);
			list.add(a);
		}
		return list;
	}

	public String transCrawlTimestamp(String time) {
		String crawlTime = null;
		int t = Integer.parseInt(time);
		switch (t) {
		case 1:
			crawlTime = "00-00-03";
			break;
		case 2:
			crawlTime = "00-00-07";
			break;
		case 3:
			crawlTime = "00-03-00";
			break;
		case 4:
			crawlTime = "00-06-00";
			break;
		case 5:
			crawlTime = "01-00-00";
			break;
		}
		return crawlTime;
	}

}
