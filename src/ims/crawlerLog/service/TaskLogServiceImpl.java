package ims.crawlerLog.service;

import ims.crawlerLog.dao.TaskLogMapper;
import ims.crawlerLog.model.TaskLog;

import java.util.Set;

public class TaskLogServiceImpl implements TaskLogService {
	private TaskLogMapper taskLogMapper;

	public TaskLogMapper getTaskLogMapper() {
		return taskLogMapper;
	}

	public void setTaskLogMapper(TaskLogMapper taskLogMapper) {
		this.taskLogMapper = taskLogMapper;
	}

	public void add(TaskLog taskLog) {
		this.taskLogMapper.add(taskLog);
	}

	public void deleteById(String taskLogId) {
		this.taskLogMapper.deleteById(taskLogId);
	}

	public Set<TaskLog> listAll() {
		return this.taskLogMapper.listAll();
	}

	public TaskLog loadById(String taskLogId) {
		return this.taskLogMapper.loadById(taskLogId);
	}

	public void update(TaskLog taskLog) {
		this.taskLogMapper.update(taskLog);
	}

	public void updateTaskStatusById(TaskLog taskLog) {
		this.taskLogMapper.updateTaskStatusById(taskLog);
	}

	public TaskLog loadByIdOnMax() {
		return this.taskLogMapper.loadByIdOnMax();
	}
}
