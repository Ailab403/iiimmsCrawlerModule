package ims.crawler.util;

import java.util.Map;

import ims.crawler.mongo.service.CrawlerMongoService;
import ims.crawler.mongo.service.CrawlerMongoServiceImpl;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.service.PostService;

/**
 * 
 * @author superhy
 * 
 */
public class HandleFetchResult {

	// 注入需要的service方法
	private PostService postService;

	// 注入MongoService方法
	private CrawlerMongoService crawlerMongoService;

	// 处理失效的页面（404），将fetchable改为-1，表示此页面已彻底失效
	public void fixFailurePost(Post post) {
		post.setFetchable(-1);
		this.postService.updateFetchableByPostUrlMD5(post);
	}

	// 处理已经解析过的页面（且没有再次被刷新），将fetchable改为0
	public void handleFetchedPost(Post post) {
		post.setFetchable(0);
		this.postService.updateFetchableByPostUrlMD5(post);
	}

	// 在mongo数据库中更新解析的内容
	public void refreshMongoContent(
			Map<String, Object> eachPostContentResultMap, Site site) {

		System.out.println("MongoDB refresh!");

		// 删除旧内容
		String postUrlMD5 = (String) eachPostContentResultMap.get("postUrlMD5");
		crawlerMongoService.deleteContentByPostUrlMD5(postUrlMD5, site);
		// 存入MongoDB，用新内容刷新
		crawlerMongoService
				.addSinglePostContent(eachPostContentResultMap, site);
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public CrawlerMongoService getCrawlerMongoService() {
		return crawlerMongoService;
	}

	public void setCrawlerMongoService(CrawlerMongoService crawlerMongoService) {
		this.crawlerMongoService = crawlerMongoService;
	}

}
