package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

public class GetTianyaluntanPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
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
			while (elePagerDiv.select("a.js-keyboard-next").size() != 0) {
				String strNextPage = elePagerDiv.select("a.js-keyboard-next")
						.first().attr("abs:href");

				listPagerUrl.add(strNextPage);

				docPager = BasicJsoupDocumentUtil.getDocument(strNextPage);
				elePagerDiv = docPager.select(pagerQuery).first();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return listPagerUrl;
		}

		return listPagerUrl;
	}

}
