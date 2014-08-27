package ims.crawlerLog.dao;

import java.util.Set;

import ims.crawlerLog.model.SiteLog;

public interface SiteLogMapper {

	void add(SiteLog siteLog);

	void deleteById(String siteLogId);

	void deleteByTaskLogId(String taskLogId);

	void update(SiteLog siteLog);

	void updateSiteStatusById(SiteLog siteLog);

	SiteLog loadById(String siteLogId);

	Set<SiteLog> listAll();
	
	Set<SiteLog> listByTaskLogId(String taskLogId);
}
