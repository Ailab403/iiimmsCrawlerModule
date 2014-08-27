package ims.crawlerLog.dao;

import java.util.Set;

import ims.crawlerLog.model.ThemeLog;

public interface ThemeLogMapper {

	void add(ThemeLog themeLog);

	void deleteById(String themeLogId);

	void deleteByTaskLogId(String taskLogId);

	void update(ThemeLog themeLog);

	ThemeLog loadById(String themeLogId);

	Set<ThemeLog> listAll();
	
	Set<ThemeLog> listByTaskLogId(String taskLogId);
}
