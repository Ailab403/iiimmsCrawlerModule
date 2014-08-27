package ims.crawlerLog.service;

import ims.crawlerLog.model.SiteLog;

import java.util.Set;

public interface SiteLogService {

	public void add(SiteLog siteLog);

	public void deleteById(String siteLogId);

	public void deleteByTaskLogId(String taskLogId);

	public void update(SiteLog siteLog);

	public void updateSiteStatusById(SiteLog siteLog);

	public SiteLog loadById(String siteLogId);

	public Set<SiteLog> listAll();

	public Set<SiteLog> listByTaskLogId(String taskLogId);
}
