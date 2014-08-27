package ims.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.GetPostPager;
import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetBaidutiebaPagerImpl implements GetPostPager {

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

			// ����ֻ��һҳ��û�з�ҳ��Ϣ�����
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
