package ims.crawler.fetch.util;

import java.util.List;

public interface GetPostPager {

	/**
	 * 获取各种网站帖子分页的接口实现方法
	 * 
	 * @param urlPost
	 * @param pagerQuery
	 * @return
	 */
	public List<String> getPagerUrls(String urlPost, String pagerQuery);
}
