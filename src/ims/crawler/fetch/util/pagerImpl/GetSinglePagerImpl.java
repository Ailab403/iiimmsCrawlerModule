package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

import ims.crawler.fetch.util.GetPostPager;

public class GetSinglePagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// ������ŷ�ҳ����
		List<String> listPagerUrl = new ArrayList<String>();

		// �����ܵ��������ó�ÿ����ҳ�����ӣ���һҳ���ǵ�ǰҳ
		listPagerUrl.add(urlPost);

		// �ٶȰټ�ֻ��һҳ

		return listPagerUrl;
	}
}
