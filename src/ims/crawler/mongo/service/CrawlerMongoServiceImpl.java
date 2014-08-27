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

		// ת����mongo���ݶ���ÿ�㶼�ж�Ӧ
		DBObject postDbs = new BasicDBObject();

		// ��map������ȡ������
		int siteId = (Integer) eachPostContentResultMap.get("siteId"); // ����վ��Id��������
		String taskLogId = (String) eachPostContentResultMap.get("taskLogId"); // ��������Id��������
		int postId = (Integer) eachPostContentResultMap.get("postId"); // �����ڵ�Id��������
		String postUrlMD5 = (String) eachPostContentResultMap.get("postUrlMD5"); // �����ڵ��ӦMD5ֵ
		String contentRefreshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()); // ������¼�����µ����ʱ��
		String lastEditTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()); // ������¼���༭�����ʱ��
		int identifyState = 0;
		String baName = (String) eachPostContentResultMap.get("baName"); // �������
		String title = (String) eachPostContentResultMap.get("title"); // �ڵ����
		int readNum = (Integer) eachPostContentResultMap.get("readNum"); // �Ķ���
		int commentNum = (Integer) eachPostContentResultMap.get("commentNum"); // ������
		int forwardNum = (Integer) eachPostContentResultMap.get("forwardNum"); // ת����

		List<Map<String, Object>> articleMaps = (List<Map<String, Object>>) eachPostContentResultMap
				.get("article");
		List<DBObject> articleDbsList = new ArrayList<DBObject>(); // ������Ƕ�����б����飩

		// Ψһ��ʶÿ��article��articleID
		int articleNum = 0;
		for (Map<String, Object> articleMap : articleMaps) {
			DBObject articleDbs = new BasicDBObject();// ������Ƕ���󣨵�����

			String articleAuthor = (String) articleMap.get("articleAuthor");
			String articleContent = (String) articleMap.get("articleContent");
			String articleTime = (String) articleMap.get("articleTime");

			// Ψһ��ʶÿ��article
			String articleId = postUrlMD5 + "-"
					+ Integer.toString(articleNum++);
			articleDbs.put("articleId", articleId);
			articleDbs.put("articleAuthor", articleAuthor);
			articleDbs.put("articleContent", articleContent);
			articleDbs.put("articleTime", articleTime);

			List<Map<String, Object>> replyMaps = (List<Map<String, Object>>) articleMap
					.get("articleReply");
			if (replyMaps == null) {
				articleDbsList.add(articleDbs); // �������м���ֵ
				continue; // ���û�лظ�����������һ��
			}
			List<DBObject> replyDbsList = new ArrayList<DBObject>(); // �ظ���Ƕ�������б����飩

			// Ψһ��ʶ��ÿ��reply��replyID
			int replyNum = 0;
			for (Map<String, Object> replyMap : replyMaps) {
				DBObject replyDbs = new BasicDBObject(); // �ظ���Ƕ���󣨵�����

				String replyAuthor = (String) replyMap.get("replyAuthor");
				String replyContent = (String) replyMap.get("replyContent");
				String replyTime = (String) replyMap.get("replyTime");

				// Ψһ��ʶ��ÿ��reply
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

				replyDbsList.add(replyDbs); // �������м���ֵ
			}
			articleDbs.put("articleReply", replyDbsList);

			articleDbsList.add(articleDbs); // �������м���ֵ
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

		// Ϊɾ�����ò�ѯ���������mongo���ݶ���
		DBObject postDbs = new BasicDBObject();
		// ����ɾ������
		postDbs.put("postUrlMD5", postUrlMD5);

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.deleteByDbs(postDbs, site.getNickName());
	}

	public void deleteContentBySiteId(Site site) {

		// Ϊɾ�����ò�ѯ���������mongo���ݶ���
		DBObject postDbs = new BasicDBObject();
		// ����ɾ������
		postDbs.put("siteId", site.getSiteId());

		MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		mongoDbBean.deleteByDbs(postDbs, site.getNickName());
	}

	public void deleteContentByTaskLogId(TaskLog taskLog, Site site) {

		// Ϊɾ�����ò�ѯ���������mongo���ݶ���
		DBObject postDbs = new BasicDBObject();
		// ����ɾ������
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
