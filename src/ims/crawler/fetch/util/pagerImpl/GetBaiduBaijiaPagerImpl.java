package ims.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.GetPostPager;

import java.util.ArrayList;
import java.util.List;

/**
 * �ٶȰټ�fetch��ҳֻȡ��һҳ����ͨ�û�ȡ��ҳ��ʵ����ȡ��
 * 
 * @author superhy
 * 
 */
@Deprecated
public class GetBaiduBaijiaPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// ������ŷ�ҳ����
		List<String> listPagerUrl = new ArrayList<String>();

		// �����ܵ��������ó�ÿ����ҳ�����ӣ���һҳ���ǵ�ǰҳ
		listPagerUrl.add(urlPost);

		// �ٶȰټ�ֻ��һҳ

		return listPagerUrl;
	}
}
