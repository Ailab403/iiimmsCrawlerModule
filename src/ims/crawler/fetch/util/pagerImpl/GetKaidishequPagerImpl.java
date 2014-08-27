package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.HcJsoupDocumentUtil;

public class GetKaidishequPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		List<String> listPagerUrl = new ArrayList<String>();

		Document docPager = HcJsoupDocumentUtil.getDocument(urlPost, urlPost,
				"GB2312");

		// 根据总的链接数得出每个分页的链接，第一页就是当前页
		listPagerUrl.add(urlPost);

		if (docPager == null) {
			return listPagerUrl;
		}

		try {
			// 假如只有一页，没有分页信息的情况
			if (docPager.select(pagerQuery).size() == 0) {

				return listPagerUrl;
			}

			Element elePagerDiv = docPager.select(pagerQuery).first();
			String strBoolenNext = elePagerDiv.select("a[href^=dispbbs]")
					.last().text();
			if (strBoolenNext.equals("下一页")) {
				while (strBoolenNext.equals("下一页")) {
					String strNextPage = "http://club.kdnet.net/"
							+ elePagerDiv.select("a[href^=dispbbs]").last()
									.attr("href");
					listPagerUrl.add(strNextPage);

					docPager = HcJsoupDocumentUtil.getDocument(strNextPage,
							strNextPage, "GB2312");
					// 防止突然断网的情况
					if (docPager == null) {
						break;
					}
					elePagerDiv = docPager.select(pagerQuery).first();

					strBoolenNext = elePagerDiv.select("a[href^=dispbbs]")
							.last().text();
				}
			} else {
				Elements elesPageList = elePagerDiv.select("a");
				for (Element eleEachPage : elesPageList) {
					listPagerUrl.add("http://club.kdnet.net/"
							+ eleEachPage.attr("href"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return listPagerUrl;
		}

		return listPagerUrl;
	}

}
