package ims.crawlerLog.model;

import ims.site.model.Site;

/**
 * 
 * @author superhy
 * 
 */
public class SiteLog {
	// 站点对应分任务日志表
	private String siteLogId;

	private String taskLogId;
	private int siteId;

	private int siteStatus;
	private int siteThemeNum;
	private int siteNewPostNum;
	private int siteUpdatePostNum;
	private int siteFixPostNum;
	private String grabCostTime;
	private int grabCostTimeNum; // 爬取消耗时间秒数
	private int siteFetchNum;
	private int siteFetchSuccNum;
	private String fetchCostTime;
	private int fetchCostTimeNum; // 解析消耗时间描述
	private String siteLogExp;

	private TaskLog taskLog;
	private Site site;

	public SiteLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiteLog(String siteLogId, String taskLogId, int siteId,
			int siteStatus, int siteThemeNum, int siteNewPostNum,
			int siteUpdatePostNum, int siteFixPostNum, String grabCostTime,
			int grabCostTimeNum, int siteFetchNum, int siteFetchSuccNum,
			String fetchCostTime, int fetchCostTimeNum, String siteLogExp,
			TaskLog taskLog, Site site) {
		super();
		this.siteLogId = siteLogId;
		this.taskLogId = taskLogId;
		this.siteId = siteId;
		this.siteStatus = siteStatus;
		this.siteThemeNum = siteThemeNum;
		this.siteNewPostNum = siteNewPostNum;
		this.siteUpdatePostNum = siteUpdatePostNum;
		this.siteFixPostNum = siteFixPostNum;
		this.grabCostTime = grabCostTime;
		this.grabCostTimeNum = grabCostTimeNum;
		this.siteFetchNum = siteFetchNum;
		this.siteFetchSuccNum = siteFetchSuccNum;
		this.fetchCostTime = fetchCostTime;
		this.fetchCostTimeNum = fetchCostTimeNum;
		this.siteLogExp = siteLogExp;
		this.taskLog = taskLog;
		this.site = site;
	}

	public String getSiteLogId() {
		return siteLogId;
	}

	public void setSiteLogId(String siteLogId) {
		this.siteLogId = siteLogId;
	}

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(int siteStatus) {
		this.siteStatus = siteStatus;
	}

	public int getSiteThemeNum() {
		return siteThemeNum;
	}

	public void setSiteThemeNum(int siteThemeNum) {
		this.siteThemeNum = siteThemeNum;
	}

	public int getSiteNewPostNum() {
		return siteNewPostNum;
	}

	public void setSiteNewPostNum(int siteNewPostNum) {
		this.siteNewPostNum = siteNewPostNum;
	}

	public int getSiteUpdatePostNum() {
		return siteUpdatePostNum;
	}

	public void setSiteUpdatePostNum(int siteUpdatePostNum) {
		this.siteUpdatePostNum = siteUpdatePostNum;
	}

	public int getSiteFixPostNum() {
		return siteFixPostNum;
	}

	public void setSiteFixPostNum(int siteFixPostNum) {
		this.siteFixPostNum = siteFixPostNum;
	}

	public String getGrabCostTime() {
		return grabCostTime;
	}

	public void setGrabCostTime(String grabCostTime) {
		this.grabCostTime = grabCostTime;
	}

	public int getGrabCostTimeNum() {
		return grabCostTimeNum;
	}

	public void setGrabCostTimeNum(int grabCostTimeNum) {
		this.grabCostTimeNum = grabCostTimeNum;
	}

	public int getSiteFetchNum() {
		return siteFetchNum;
	}

	public void setSiteFetchNum(int siteFetchNum) {
		this.siteFetchNum = siteFetchNum;
	}

	public int getSiteFetchSuccNum() {
		return siteFetchSuccNum;
	}

	public void setSiteFetchSuccNum(int siteFetchSuccNum) {
		this.siteFetchSuccNum = siteFetchSuccNum;
	}

	public String getFetchCostTime() {
		return fetchCostTime;
	}

	public void setFetchCostTime(String fetchCostTime) {
		this.fetchCostTime = fetchCostTime;
	}

	public int getFetchCostTimeNum() {
		return fetchCostTimeNum;
	}

	public void setFetchCostTimeNum(int fetchCostTimeNum) {
		this.fetchCostTimeNum = fetchCostTimeNum;
	}

	public String getSiteLogExp() {
		return siteLogExp;
	}

	public void setSiteLogExp(String siteLogExp) {
		this.siteLogExp = siteLogExp;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((siteLogId == null) ? 0 : siteLogId.hashCode());
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
		SiteLog other = (SiteLog) obj;
		if (siteLogId == null) {
			if (other.siteLogId != null)
				return false;
		} else if (!siteLogId.equals(other.siteLogId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SiteLog [siteLogId=" + siteLogId + ", taskLogId=" + taskLogId
				+ ", siteId=" + siteId + ", siteStatus=" + siteStatus
				+ ", siteThemeNum=" + siteThemeNum + ", siteNewPostNum="
				+ siteNewPostNum + ", siteUpdatePostNum=" + siteUpdatePostNum
				+ ", siteFixPostNum=" + siteFixPostNum + ", grabCostTime="
				+ grabCostTime + ", grabCostTimeNum=" + grabCostTimeNum
				+ ", siteFetchNum=" + siteFetchNum + ", siteFetchSuccNum="
				+ siteFetchSuccNum + ", fetchCostTime=" + fetchCostTime
				+ ", fetchCostTimeNum=" + fetchCostTimeNum + ", siteLogExp="
				+ siteLogExp + ", taskLog=" + taskLog + ", site=" + site + "]";
	}

}
