package ims.crawler.fetch.util;

import java.util.List;

public interface GetPostPager {

	/**
	 * ��ȡ������վ���ӷ�ҳ�Ľӿ�ʵ�ַ���
	 * 
	 * @param urlPost
	 * @param pagerQuery
	 * @return
	 */
	public List<String> getPagerUrls(String urlPost, String pagerQuery);
}
