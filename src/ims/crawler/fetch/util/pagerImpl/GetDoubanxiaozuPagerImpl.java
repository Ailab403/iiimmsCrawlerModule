package ims.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetDoubanxiaozuPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// ������ŷ�ҳ����
		List<String> listPagerUrl = new ArrayList<String>();

		Document docPager = BasicJsoupDocumentUtil.getDocument(urlPost);

		// �����ܵ��������ó�ÿ����ҳ�����ӣ���һҳ���ǵ�ǰҳ
		listPagerUrl.add(urlPost);

		// ��ֹͻȻ���������
		if (docPager == null) {
			return listPagerUrl;
		}

		try {
			// ����ֻ��һҳ��û�з�ҳ��Ϣ�����
			if (docPager.select(pagerQuery).size() == 0) {
				return listPagerUrl;
			}
			Element elePagerDiv = docPager.select(pagerQuery).first();
			
			while (elePagerDiv.select("span.next>a").size() != 0) {
				String strNextPage = elePagerDiv.select("span.next>a").first()
						.attr("abs:href");

				listPagerUrl.add(strNextPage);

				docPager = BasicJsoupDocumentUtil.getDocument(strNextPage);
				// ��ֹͻȻ���������
				if (docPager == null) {
					break;
				}
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
