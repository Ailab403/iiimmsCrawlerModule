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

		// �����ܵ��������ó�ÿ����ҳ�����ӣ���һҳ���ǵ�ǰҳ
		listPagerUrl.add(urlPost);

		if (docPager == null) {
			return listPagerUrl;
		}

		try {
			// ����ֻ��һҳ��û�з�ҳ��Ϣ�����
			if (docPager.select(pagerQuery).size() == 0) {

				return listPagerUrl;
			}

			Element elePagerDiv = docPager.select(pagerQuery).first();
			String strBoolenNext = elePagerDiv.select("a[href^=dispbbs]")
					.last().text();
			if (strBoolenNext.equals("��һҳ")) {
				while (strBoolenNext.equals("��һҳ")) {
					String strNextPage = "http://club.kdnet.net/"
							+ elePagerDiv.select("a[href^=dispbbs]").last()
									.attr("href");
					listPagerUrl.add(strNextPage);

					docPager = HcJsoupDocumentUtil.getDocument(strNextPage,
							strNextPage, "GB2312");
					// ��ֹͻȻ���������
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
