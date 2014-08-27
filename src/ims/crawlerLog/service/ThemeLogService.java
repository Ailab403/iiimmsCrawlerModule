package ims.crawlerLog.service;

import ims.crawlerLog.model.ThemeLog;

import java.util.Set;

public interface ThemeLogService {

	public void add(ThemeLog themeLog);

	public void deleteById(String themeLogId);

	public void deleteByTaskLogId(String taskLogId);

	public void update(ThemeLog themeLog);

	public ThemeLog loadById(String themeLogId);

	public Set<ThemeLog> listAll();

	public Set<ThemeLog> listByTaskLogId(String taskLogId);
}
