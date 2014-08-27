package ims.crawlerLog.model;

import ims.site.model.Site;
import ims.site.model.Theme;

/**
 * 
 * @author superhy
 * 
 */
public class ThemeLog {
	// 每次爬取所得的主题更新数据日志表
	private String themeLogId;

	private String taskLogId;
	private int themeId;
	private int siteId;

	private int themeNewPostNum;
	private int themeUpdatePostNum;
	private int themeFetchNum;
	private int themeFetchSuccNum;
	private String themeLogExp;

	// 外键对应表关联
	private TaskLog taskLog;
	private Theme theme;
	private Site site;

	public ThemeLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThemeLog(String themeLogId, String taskLogId, int themeId,
			int siteId, int themeNewPostNum, int themeUpdatePostNum,
			int themeFetchNum, int themeFetchSuccNum, String themeLogExp,
			TaskLog taskLog, Theme theme, Site site) {
		super();
		this.themeLogId = themeLogId;
		this.taskLogId = taskLogId;
		this.themeId = themeId;
		this.siteId = siteId;
		this.themeNewPostNum = themeNewPostNum;
		this.themeUpdatePostNum = themeUpdatePostNum;
		this.themeFetchNum = themeFetchNum;
		this.themeFetchSuccNum = themeFetchSuccNum;
		this.themeLogExp = themeLogExp;
		this.taskLog = taskLog;
		this.theme = theme;
		this.site = site;
	}

	public String getThemeLogId() {
		return themeLogId;
	}

	public void setThemeLogId(String themeLogId) {
		this.themeLogId = themeLogId;
	}

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getThemeNewPostNum() {
		return themeNewPostNum;
	}

	public void setThemeNewPostNum(int themeNewPostNum) {
		this.themeNewPostNum = themeNewPostNum;
	}

	public int getThemeUpdatePostNum() {
		return themeUpdatePostNum;
	}

	public void setThemeUpdatePostNum(int themeUpdatePostNum) {
		this.themeUpdatePostNum = themeUpdatePostNum;
	}

	public int getThemeFetchNum() {
		return themeFetchNum;
	}

	public void setThemeFetchNum(int themeFetchNum) {
		this.themeFetchNum = themeFetchNum;
	}

	public int getThemeFetchSuccNum() {
		return themeFetchSuccNum;
	}

	public void setThemeFetchSuccNum(int themeFetchSuccNum) {
		this.themeFetchSuccNum = themeFetchSuccNum;
	}

	public String getThemeLogExp() {
		return themeLogExp;
	}

	public void setThemeLogExp(String themeLogExp) {
		this.themeLogExp = themeLogExp;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
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
				+ ((themeLogId == null) ? 0 : themeLogId.hashCode());
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
		ThemeLog other = (ThemeLog) obj;
		if (themeLogId == null) {
			if (other.themeLogId != null)
				return false;
		} else if (!themeLogId.equals(other.themeLogId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ThemeLog [site=" + site + ", siteId=" + siteId + ", taskLog="
				+ taskLog + ", taskLogId=" + taskLogId + ", theme=" + theme
				+ ", themeFetchNum=" + themeFetchNum + ", themeFetchSuccNum="
				+ themeFetchSuccNum + ", themeId=" + themeId + ", themeLogExp="
				+ themeLogExp + ", themeLogId=" + themeLogId
				+ ", themeNewPostNum=" + themeNewPostNum
				+ ", themeUpdatePostNum=" + themeUpdatePostNum + "]";
	}

}
