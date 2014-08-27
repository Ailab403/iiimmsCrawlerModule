package ims.crawlerLog.service;

import ims.crawlerLog.model.TaskLog;

import java.util.Set;

public interface TaskLogService {
	public void add(TaskLog taskLog);

	public void deleteById(String taskLogId);

	public void update(TaskLog taskLog);

	public void updateTaskStatusById(TaskLog taskLog);

	public TaskLog loadById(String taskLogId);

	public Set<TaskLog> listAll();

	public TaskLog loadByIdOnMax();
}
