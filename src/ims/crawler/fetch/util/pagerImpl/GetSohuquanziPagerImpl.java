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
