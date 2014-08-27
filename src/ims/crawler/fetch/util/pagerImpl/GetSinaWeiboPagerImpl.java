package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

public class GetSinaWeiboPagerImpl {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// ������ŷ�ҳ����
		List<String> listPagerUrl = new ArrayList<String>();

		String pageUrlModel = urlPost.substring(0, urlPost.indexOf("#") - 1)
				+ "&page=";
		for (int i = 1; i <= 50; i++) {
			listPagerUrl.add(pageUrlModel + String.valueOf(i));
		}

		return listPagerUrl;
	}
}
