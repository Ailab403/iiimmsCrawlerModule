package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

public class GetFenghuangluntanPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		List<String> listPagerUrl = new ArrayList<String>();

		Document docPager = BasicJsoupDocumentUtil.getDocument(urlPost);

		// �����ܵ��������ó�ÿ����ҳ�����ӣ���һҳ���ǵ�ǰҳ
		listPagerUrl.add(urlPost);

		// ��ֹͻȻ����
		if (docPager == null) {
			return listPagerUrl;
		}

		try {
			// ����ֻ��һҳ��û�з�ҳ��Ϣ�����
			if (docPager.select(pagerQuery).size() == 0) {

				return listPagerUrl;
			}
			Element elePagerDiv = docPager.select(pagerQuery).first();
			String strLastPage = elePagerDiv.select("a[href*=viewthread]")
					.last().attr("abs:href");

			// System.out.println(strLastPage);

			String strPageModel = strLastPage.substring(0, strLastPage
					.lastIndexOf("=") + 1);
			int numPages = Integer.valueOf(strLastPage.substring(strLastPage
					.lastIndexOf("=") + 1, strLastPage.length()));

			for (int i = 2; i <= numPages; i++) {
				listPagerUrl.add(strPageModel + i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return listPagerUrl;
		}

		return listPagerUrl;
	}

}
