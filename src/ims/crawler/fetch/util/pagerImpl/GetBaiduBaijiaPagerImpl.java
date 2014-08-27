package ims.crawler.fetch.util.pagerImpl;

import ims.crawler.fetch.util.GetPostPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 百度百家fetch分页只取第一页，用通用获取首页的实现类取代
 * 
 * @author superhy
 * 
 */
@Deprecated
public class GetBaiduBaijiaPagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// 容器存放分页链接
		List<String> listPagerUrl = new ArrayList<String>();

		// 根据总的链接数得出每个分页的链接，第一页就是当前页
		listPagerUrl.add(urlPost);

		// 百度百家只有一页

		return listPagerUrl;
	}
}
