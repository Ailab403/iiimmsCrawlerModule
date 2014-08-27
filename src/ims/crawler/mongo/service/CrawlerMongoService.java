package ims.crawler.mongo.service;

import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

import java.util.Map;

public interface CrawlerMongoService {

	/**
	 * 添加入新的记录，传入的记录用map映射
	 * 
	 * @param eachPostContentResultMap
	 * @param site
	 */
	public void addSinglePostContent(
			Map<String, Object> eachPostContentResultMap, Site site);

	/**
	 * 根据postUrlMD5删除记录
	 * 
	 * @param postUrlMD5
	 * @param site
	 */
	public void deleteContentByPostUrlMD5(String postUrlMD5, Site site);

	/**
	 * 根据siteId删除记录
	 * 
	 * @param site
	 */
	public void deleteContentBySiteId(Site site);

	/**
	 * 根据taskLogId删除记录
	 * 
	 * @param taskLog
	 * @param site
	 */
	public void deleteContentByTaskLogId(TaskLog taskLog, Site site);

	/**
	 * 查找对应集合中的记录数，其实集合名称就是site的nickName
	 * 
	 * @param collectionName
	 * @return
	 */
	public int countInCollection(String collectionName);

	/**
	 * 查找一个集合中对应任务的记录数
	 * @param collectionName
	 * @param taskLogId
	 * @return
	 */
	public int countByTaskLogIdInCollection(String collectionName,
			String taskLogId);
}
