package ims.crawler.mongo.service;

import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

import java.util.Map;

public interface CrawlerMongoService {

	/**
	 * ������µļ�¼������ļ�¼��mapӳ��
	 * 
	 * @param eachPostContentResultMap
	 * @param site
	 */
	public void addSinglePostContent(
			Map<String, Object> eachPostContentResultMap, Site site);

	/**
	 * ����postUrlMD5ɾ����¼
	 * 
	 * @param postUrlMD5
	 * @param site
	 */
	public void deleteContentByPostUrlMD5(String postUrlMD5, Site site);

	/**
	 * ����siteIdɾ����¼
	 * 
	 * @param site
	 */
	public void deleteContentBySiteId(Site site);

	/**
	 * ����taskLogIdɾ����¼
	 * 
	 * @param taskLog
	 * @param site
	 */
	public void deleteContentByTaskLogId(TaskLog taskLog, Site site);

	/**
	 * ���Ҷ�Ӧ�����еļ�¼������ʵ�������ƾ���site��nickName
	 * 
	 * @param collectionName
	 * @return
	 */
	public int countInCollection(String collectionName);

	/**
	 * ����һ�������ж�Ӧ����ļ�¼��
	 * @param collectionName
	 * @param taskLogId
	 * @return
	 */
	public int countByTaskLogIdInCollection(String collectionName,
			String taskLogId);
}
