package ims.crawler.mongo.service;

import ims.crawlerLog.model.TaskLog;
import ims.crawler.mongo.MongoCorpusDbBean;
import ims.site.model.Site;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CrawlerMongoServiceImpl implements CrawlerMongoService {

	public void addSinglePostContent(
			Map<String, Object> eachPostContentResultMap, Site site) {

		// 转换成mongo数据对象，每层都有对应
		DBObject postDbs = new BasicDBObject();

		// 从map容器中取出数据
		int siteId = (Integer) eachPostContentResultMap.get("siteId"); // 所属站点Id，必须有
		String taskLogId = (String) eachPostContentResultMap.get("taskLogId"); // 所属任务Id，必须有
		int postId = (Integer) eachPostContentResultMap.get("postId"); // 所属节点Id，必须有
		String postUrlMD5 = (String) eachPostContentResultMap.get("postUrlMD5"); // 所属节点对应MD5值
		String contentRefreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()); // 这条纪录被更新的最后时间
		String lastEditTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()); // 这条纪录被编辑的最后时间
		int identifyState = 0;
		String baName = (String) eachPostContentResultMap.get("baName"); // 板块名称
		String title = (String) eachPostContentResultMap.get("title"); // 节点标题
		int readNum = (Integer) eachPostContentResultMap.get("readNum"); // 阅读量
		int commentNum = (Integer) eachPostContentResultMap.get("commentNum"); // 评论量
		int forwardNum = (Integer) eachPostContentResultMap.get("forwardNum"); // 转发量

		List<Map<String, Object>> articleMaps = (List<Map<String, Object>>) eachPostContentResultMap
				.get("article");
		List<DBObject> articleDbsList = new ArrayList<DBObject>(); // 文章内嵌对象列表（数组）

		// 唯一标识每个article的articleID
		int articleNum = 0;
		for (Map<String, Object> articleMap : articleMaps) {
			DBObject articleDbs = new BasicDBObject();// 文章内嵌对象（单个）

			String articleAuthor = (String) articleMap.get("articleAuthor");
			String articleContent = (String) articleMap.get("articleContent");
			String articleTime = (String) articleMap.get("articleTime");

			// 唯一标识每个article
			String articleId = postUrlMD5 + "-"
					+ Integer.toString(articleNum++);
			articleDbs.put("articleId", articleId);
			articleDbs.put("articleAuthor", articleAuthor);
			articleDbs.put("articleContent", articleContent);
			articleDbs.put("articleTime", articleTime);

			List<Map<String, Object>> replyMaps = (List<Map<String, Object>>) articleMap
					.get("articleReply");
			if (replyMaps == null) {
				articleDbsList.add(articleDbs); // 向数组中加入值
				continue; // 如果没有回复，则不设置这一项
			}
			List<DBObject> replyDbsList = new ArrayList<DBObject>(); // 回复内嵌对象劣列表（数组）

			// 唯一标识到每个reply的replyID
			int replyNum = 0;
			for (Map<String, Object> replyMap : replyMaps) {
				DBObject replyDbs = new BasicDBObject(); // 回复内嵌对象（单个）

				String replyAuthor = (String) replyMap.get("replyAuthor");
				String replyContent = (String) replyMap.get("replyContent");
				String replyTime = (String) replyMap.get("replyTime");

				// 唯一标识到每个reply
				String replyId = articleId + "-" + Integer.toString(replyNum++);
				replyDbs.put("replyId", replyId);
				if (replyAuthor != null) {
					replyDbs.put("replyAuthor", replyAuthor);
				}
				if (replyContent != null) {
					replyDbs.put("replyContent", replyContent);
				}
				if (replyTime != null) {
					replyDbs.put("replyTime", replyTime);
				}

				replyDbsList.add(replyDbs); // 向数组中加入值
			}
			articleDbs.put("articleReply", replyDbsList);

			articleDbsList.add(articleDbs); // 向数组中加入值
		}

		postDbs.put("siteId", siteId);
		postDbs.put("taskLogId", taskLogId);
		postDbs.put("postId", postId);
		postDbs.put("postUrlMD5", postUrlMD5);
		postDbs.put("contentRefreshTime", contentRefreshTime);
		postDbs.put("lastEditTime", lastEditTime);
		postDbs.put("identifyState", identifyState);
		postDbs.put("baName", baName);
		postDbs.put("title", title);
		postDbs.put("readNum", readNum);
		postDbs.put("commentNum", commentNum);
		postDbs.put("forwardNum", forwardNum);
		postDbs.put("article", articleDbsList);

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.insert(postDbs, site.getNickName());
	}

	public void deleteContentByPostUrlMD5(String postUrlMD5, Site site) {

		// 为删除设置查询条件，获得mongo数据对象
		DBObject postDbs = new BasicDBObject();
		// 设置删除条件
		postDbs.put("postUrlMD5", postUrlMD5);

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.deleteByDbs(postDbs, site.getNickName());
	}

	public void deleteContentBySiteId(Site site) {

		// 为删除设置查询条件，获得mongo数据对象
		DBObject postDbs = new BasicDBObject();
		// 设置删除条件
		postDbs.put("siteId", site.getSiteId());

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.deleteByDbs(postDbs, site.getNickName());
	}

	public void deleteContentByTaskLogId(TaskLog taskLog, Site site) {

		// 为删除设置查询条件，获得mongo数据对象
		DBObject postDbs = new BasicDBObject();
		// 设置删除条件
		postDbs.put("taskLogId", taskLog.getTaskLogId());

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.deleteByDbs(postDbs, site.getNickName());
	}

	public int countInCollection(String collectionName) {

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		int countNum = mongoDbBean.countInColl(collectionName);

		return countNum;
	}

	public int countByTaskLogIdInCollection(String collectionName,
			String taskLogId) {
		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();

		DBObject ref = new BasicDBObject();
		ref.put("taskLogId", taskLogId);
		int countNum = mongoDbBean.countByKeyAndRefInColl(ref, null,
				collectionName);

		return countNum;
	}
}
