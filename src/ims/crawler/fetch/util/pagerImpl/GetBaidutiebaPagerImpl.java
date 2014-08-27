package ims.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetBaidutiebaPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// 容器存放分页链接
		List<String> listPagerUrl = new ArrayList<String>();

		Document docPager = BasicJsoupDocumentUtil.getDocument(urlPost);

		// 根据总的链接数得出每个分页的链接，第一页就是当前页
		listPagerUrl.add(urlPost);

		// 防止突然断网的情况
		if (docPager == null) {
			return listPagerUrl;
		}

		try {
			// 假如只有一页，没有分页信息的情况
			if (docPager.select(pagerQuery).size() == 0) {

				return listPagerUrl;
			}
			
			Element elePagerDiv = docPager.select(pagerQuery).first();

			// 假如只有一页，没有分页信息的情况
			if (elePagerDiv.select("a[href]").size() == 0) {

				return listPagerUrl;
			}
			String strPagerLast = elePagerDiv.select("a[href]").last().attr(
					"abs:href");
			int numPager = Integer.parseInt(strPagerLast.substring(strPagerLast
					.indexOf('=') + 1));
			for (int i = 2; i <= numPager; i++) {
				String strPagerEach = strPagerLast.substring(0, strPagerLast
						.indexOf('=') + 1)
						+ Integer.toString(i);
				listPagerUrl.add(strPagerEach);

				/* System.out.println(strPagerEach); */
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return listPagerUrl;
		}

		return listPagerUrl;
	}
}
