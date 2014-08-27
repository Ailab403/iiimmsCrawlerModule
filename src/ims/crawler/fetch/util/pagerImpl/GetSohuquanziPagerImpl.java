package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

public class GetSohuquanziPagerImpl implements GetPostPager {

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

			String lastPageUrl = elePagerDiv.select("a:last-of-type").last()
					.attr("abs:href");
			String pageUrlModel = lastPageUrl.substring(0,
					lastPageUrl.indexOf("=") + 1);
			int pageNum = Integer.parseInt(lastPageUrl.substring(
					lastPageUrl.indexOf("=") + 1, lastPageUrl.length()));

			for (int i = 2; i <= pageNum; i++) {
				listPagerUrl.add(pageUrlModel + i);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return listPagerUrl;
		}

		return listPagerUrl;
	}

}
