package ims.crawler.fetch.util.pagerImpl;

import java.util.ArrayList;
import java.util.List;

import ims.crawler.fetch.util.GetPostPager;

public class GetSinglePagerImpl implements GetPostPager {

	public List<String> getPagerUrls(String urlPost, String pagerQuery) {
		// 容器存放分页链接
		List<String> listPagerUrl = new ArrayList<String>();

		// 根据总的链接数得出每个分页的链接，第一页就是当前页
		listPagerUrl.add(urlPost);

		// 百度百家只有一页

		return listPagerUrl;
	}
}
