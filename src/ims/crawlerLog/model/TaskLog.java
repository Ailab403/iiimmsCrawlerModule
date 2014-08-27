package ims.crawlerLog.model;

import java.sql.Timestamp;

/**
 * 
 * @author superhy
 * 
 */
public class TaskLog {
	// 总任务日志表
	private String taskLogId;
	private int taskStatus;
	private Timestamp startTime;
	private Timestamp activeTime;
	private Timestamp endTime;
	private String costTime;
	private int costTimeNum;
	private int grabSiteNum;
	private int grabSiteSuccNum;
	private int totalThemeNum;
	private int totalGrabNewPostNum;
	private int totalGrabUpdatePostNum;
	private int totalFetchPostNum;
	private int totalFetchSuccPostNum;
	private int taskPauseTimes;
	private String taskInfoExp;
	private String taskLogExp;

	public TaskLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskLog(String taskLogId, int taskStatus, Timestamp startTime,
			Timestamp activeTime, Timestamp endTime, String costTime,
			int costTimeNum, int grabSiteNum, int grabSiteSuccNum,
			int totalThemeNum, int totalGrabNewPostNum,
			int totalGrabUpdatePostNum, int totalFetchPostNum,
			int totalFetchSuccPostNum, int taskPauseTimes, String taskInfoExp,
			String taskLogExp) {
		super();
		this.taskLogId = taskLogId;
		this.taskStatus = taskStatus;
		this.startTime = startTime;
		this.activeTime = activeTime;
		this.endTime = endTime;
		this.costTime = costTime;
		this.costTimeNum = costTimeNum;
		this.grabSiteNum = grabSiteNum;
		this.grabSiteSuccNum = grabSiteSuccNum;
		this.totalThemeNum = totalThemeNum;
		this.totalGrabNewPostNum = totalGrabNewPostNum;
		this.totalGrabUpdatePostNum = totalGrabUpdatePostNum;
		this.totalFetchPostNum = totalFetchPostNum;
		this.totalFetchSuccPostNum = totalFetchSuccPostNum;
		this.taskPauseTimes = taskPauseTimes;
		this.taskInfoExp = taskInfoExp;
		this.taskLogExp = taskLogExp;
	}

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public Timestamp getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Timestamp activeTime) {
		this.activeTime = activeTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public int getCostTimeNum() {
		return costTimeNum;
	}

	public void setCostTimeNum(int costTimeNum) {
		this.costTimeNum = costTimeNum;
	}

	public int getGrabSiteNum() {
		return grabSiteNum;
	}

	public void setGrabSiteNum(int grabSiteNum) {
		this.grabSiteNum = grabSiteNum;
	}

	public int getGrabSiteSuccNum() {
		return grabSiteSuccNum;
	}

	public void setGrabSiteSuccNum(int grabSiteSuccNum) {
		this.grabSiteSuccNum = grabSiteSuccNum;
	}

	public int getTotalThemeNum() {
		return totalThemeNum;
	}

	public void setTotalThemeNum(int totalThemeNum) {
		this.totalThemeNum = totalThemeNum;
	}

	public int getTotalGrabNewPostNum() {
		return totalGrabNewPostNum;
	}

	public void setTotalGrabNewPostNum(int totalGrabNewPostNum) {
		this.totalGrabNewPostNum = totalGrabNewPostNum;
	}

	public int getTotalGrabUpdatePostNum() {
		return totalGrabUpdatePostNum;
	}

	public void setTotalGrabUpdatePostNum(int totalGrabUpdatePostNum) {
		this.totalGrabUpdatePostNum = totalGrabUpdatePostNum;
	}

	public int getTotalFetchPostNum() {
		return totalFetchPostNum;
	}

	public void setTotalFetchPostNum(int totalFetchPostNum) {
		this.totalFetchPostNum = totalFetchPostNum;
	}

	public int getTotalFetchSuccPostNum() {
		return totalFetchSuccPostNum;
	}

	public void setTotalFetchSuccPostNum(int totalFetchSuccPostNum) {
		this.totalFetchSuccPostNum = totalFetchSuccPostNum;
	}

	public int getTaskPauseTimes() {
		return taskPauseTimes;
	}

	public void setTaskPauseTimes(int taskPauseTimes) {
		this.taskPauseTimes = taskPauseTimes;
	}

	public String getTaskInfoExp() {
		return taskInfoExp;
	}

	public void setTaskInfoExp(String taskInfoExp) {
		this.taskInfoExp = taskInfoExp;
	}

	public String getTaskLogExp() {
		return taskLogExp;
	}

	public void setTaskLogExp(String taskLogExp) {
		this.taskLogExp = taskLogExp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((taskLogId == null) ? 0 : taskLogId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskLog other = (TaskLog) obj;
		if (taskLogId == null) {
			if (other.taskLogId != null)
				return false;
		} else if (!taskLogId.equals(other.taskLogId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskLog [taskLogId=" + taskLogId + ", taskStatus=" + taskStatus
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", costTime=" + costTime + ", costTimeNum=" + costTimeNum
				+ ", grabSiteNum=" + grabSiteNum + ", grabSiteSuccNum="
				+ grabSiteSuccNum + ", totalThemeNum=" + totalThemeNum
				+ ", totalGrabNewPostNum=" + totalGrabNewPostNum
				+ ", totalGrabUpdatePostNum=" + totalGrabUpdatePostNum
				+ ", totalFetchPostNum=" + totalFetchPostNum
				+ ", totalFetchSuccPostNum=" + totalFetchSuccPostNum
				+ ", taskPauseTimes=" + taskPauseTimes + ", taskInfoExp="
				+ taskInfoExp + ", taskLogExp=" + taskLogExp + "]";
	}

}
