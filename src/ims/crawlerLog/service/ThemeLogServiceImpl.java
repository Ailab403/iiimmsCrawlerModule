package ims.crawlerLog.service;

import ims.crawlerLog.dao.ThemeLogMapper;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.model.ThemeLog;

import java.util.Set;

public class ThemeLogServiceImpl implements ThemeLogService {

	private ThemeLogMapper themeLogMapper;

	public ThemeLogMapper getThemeLogMapper() {
		return themeLogMapper;
	}

	public void setThemeLogMapper(ThemeLogMapper themeLogMapper) {
		this.themeLogMapper = themeLogMapper;
	}

	public void add(ThemeLog themeLog) {
		this.themeLogMapper.add(themeLog);
	}

	public void deleteById(String themeLogId) {
		this.themeLogMapper.deleteById(themeLogId);
	}

	public void deleteByTaskLogId(String taskLogId) {
		this.themeLogMapper.deleteByTaskLogId(taskLogId);
	}

	public Set<ThemeLog> listAll() {
		return this.themeLogMapper.listAll();
	}

	public ThemeLog loadById(String themeLogId) {
		return this.themeLogMapper.loadById(themeLogId);
	}

	public void update(ThemeLog themeLog) {
		this.themeLogMapper.update(themeLog);
	}

	public Set<ThemeLog> listByTaskLogId(String taskLogId) {
		return this.themeLogMapper.listByTaskLogId(taskLogId);
	}
}
