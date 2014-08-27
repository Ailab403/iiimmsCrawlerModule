package ims.crawlerLog.service;

import ims.crawlerLog.dao.SiteLogMapper;
import ims.crawlerLog.model.SiteLog;

import java.util.Set;

public class SiteLogServiceImpl implements SiteLogService {

	private SiteLogMapper siteLogMapper;

	public SiteLogMapper getSiteLogMapper() {
		return siteLogMapper;
	}

	public void setSiteLogMapper(SiteLogMapper siteLogMapper) {
		this.siteLogMapper = siteLogMapper;
	}

	public void add(SiteLog siteLog) {
		this.siteLogMapper.add(siteLog);
	}

	public Set<SiteLog> listAll() {
		return this.siteLogMapper.listAll();
	}

	public SiteLog loadById(String siteLogId) {
		return this.siteLogMapper.loadById(siteLogId);
	}

	public void update(SiteLog siteLog) {
		this.siteLogMapper.update(siteLog);
	}

	public void updateSiteStatusById(SiteLog siteLog) {
		this.siteLogMapper.updateSiteStatusById(siteLog);
	}

	public void deleteById(String siteLogId) {
		this.siteLogMapper.deleteById(siteLogId);
	}

	public void deleteByTaskLogId(String taskLogId) {
		this.siteLogMapper.deleteByTaskLogId(taskLogId);
	}

	public Set<SiteLog> listByTaskLogId(String taskLogId) {
		return this.siteLogMapper.listByTaskLogId(taskLogId);
	}

}
