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

	// ע����Ҫ��service����
	private PostService postService;

	// ע��MongoService����
	private CrawlerMongoService crawlerMongoService;

	// ����ʧЧ��ҳ�棨404������fetchable��Ϊ-1����ʾ��ҳ���ѳ���ʧЧ
	public void fixFailurePost(Post post) {
		post.setFetchable(-1);
		this.postService.updateFetchableByPostUrlMD5(post);
	}

	// �����Ѿ���������ҳ�棨��û���ٴα�ˢ�£�����fetchable��Ϊ0
	public void handleFetchedPost(Post post) {
		post.setFetchable(0);
		this.postService.updateFetchableByPostUrlMD5(post);
	}

	// ��mongo���ݿ��и��½���������
	public void refreshMongoContent(
			Map<String, Object> eachPostContentResultMap, Site site) {

		System.out.println("MongoDB refresh!");

		// ɾ��������
		String postUrlMD5 = (String) eachPostContentResultMap.get("postUrlMD5");
		crawlerMongoService.deleteContentByPostUrlMD5(postUrlMD5, site);
		// ����MongoDB����������ˢ��
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
