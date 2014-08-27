package test.crawler.mongo;

import ims.crawler.mongo.service.CrawlerMongoService;
import ims.crawler.mongo.service.CrawlerMongoServiceImpl;
import ims.site.model.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class MongoServiceTest {

	@Test
	public void testAddSinglePostContent() {
		Map<String, Object> postMap = new HashMap<String, Object>();

		List<Map<String, Object>> articleList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 3; i++) {
			List<Map<String, Object>> replyList = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < 3; j++) {
				Map<String, Object> replyMap = new HashMap<String, Object>();
				replyMap.put("replyAuthor", "daidan" + j);
				replyMap.put("replyContent", "ceshireply" + j);
				replyMap.put("replyTime", "2013-12-12 12:12:15");

				replyList.add(replyMap);
			}

			Map<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("articleAuthor", "huyang" + i);
			articleMap.put("articleContent", "ceshiArticle" + i);
			articleMap.put("articleTime", "2013-12-12 12:12:12");
			articleMap.put("articleReply", replyList);

			articleList.add(articleMap);
		}

		postMap.put("siteId", 3);
		postMap.put("postId", 123);
		postMap.put("postUrlMD5", "07CA82028334741BD2258EA9869F168C");
		postMap.put("baName", "test");
		postMap.put("title", "Œ“ «∫˙—Ó");
		postMap.put("readNum", 1234);
		postMap.put("commentNum", 321);
		postMap.put("forwardNum", 0);
		postMap.put("article", articleList);

		Site site = new Site();
		site.setNickName("test");

		CrawlerMongoService mongoService = new CrawlerMongoServiceImpl();
		mongoService.addSinglePostContent(postMap, site);
	}

	@Test
	public void testDeletePostContentByMD5() {

		String postUrlMD5 = new Scanner(System.in).next();
		CrawlerMongoService mongoService = new CrawlerMongoServiceImpl();

		Site site = new Site();
		site.setNickName("maoputietie");

		mongoService.deleteContentByPostUrlMD5(postUrlMD5, site);
	}
	
	@Test
	public void testCountByTaskLogIdInCollection() {
		String taskLogId = "20140504094926";
		String collectionName = "doubangroup";
		
		CrawlerMongoService mongoService = new CrawlerMongoServiceImpl();
		int countNum = mongoService.countByTaskLogIdInCollection(collectionName, taskLogId);
		
		System.out.println(countNum);
	}
}
