package ims.site.model;

import java.sql.Timestamp;

public class Theme {

	// 主题信息表
	private int themeId;

	private int siteId;

	private String themeName;
	private String themeUrl;
	private String themeUrlMD5;
	private int themeGrabable;
	private int themeHotNum;
	private Timestamp refreshTime;
	private String themeExp;

	private Site site; // 网站Id外键对应表关联

	public Theme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Theme(int siteId, String themeName, String themeUrl,
			String themeUrlMD5, int themeGrabable, int themeHotNum,
			Timestamp refreshTime, String themeExp, Site site) {
		super();
		this.siteId = siteId;
		this.themeName = themeName;
		this.themeUrl = themeUrl;
		this.themeUrlMD5 = themeUrlMD5;
		this.themeGrabable = themeGrabable;
		this.themeHotNum = themeHotNum;
		this.refreshTime = refreshTime;
		this.themeExp = themeExp;
		this.site = site;
	}

	public Theme(int themeId, int siteId, String themeName, String themeUrl,
			String themeUrlMD5, int themeGrabable, int themeHotNum,
			Timestamp refreshTime, String themeExp, Site site) {
		super();
		this.themeId = themeId;
		this.siteId = siteId;
		this.themeName = themeName;
		this.themeUrl = themeUrl;
		this.themeUrlMD5 = themeUrlMD5;
		this.themeGrabable = themeGrabable;
		this.themeHotNum = themeHotNum;
		this.refreshTime = refreshTime;
		this.themeExp = themeExp;
		this.site = site;
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

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeUrl() {
		return themeUrl;
	}

	public void setThemeUrl(String themeUrl) {
		this.themeUrl = themeUrl;
	}

	public String getThemeUrlMD5() {
		return themeUrlMD5;
	}

	public void setThemeUrlMD5(String themeUrlMD5) {
		this.themeUrlMD5 = themeUrlMD5;
	}

	public int getThemeGrabable() {
		return themeGrabable;
	}

	public void setThemeGrabable(int themeGrabable) {
		this.themeGrabable = themeGrabable;
	}

	public int getThemeHotNum() {
		return themeHotNum;
	}

	public void setThemeHotNum(int themeHotNum) {
		this.themeHotNum = themeHotNum;
	}

	public Timestamp getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Timestamp refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getThemeExp() {
		return themeExp;
	}

	public void setThemeExp(String themeExp) {
		this.themeExp = themeExp;
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
				+ ((themeUrlMD5 == null) ? 0 : themeUrlMD5.hashCode());
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
		Theme other = (Theme) obj;
		if (themeUrlMD5 == null) {
			if (other.themeUrlMD5 != null)
				return false;
		} else if (!themeUrlMD5.equals(other.themeUrlMD5))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Theme [refreshTime=" + refreshTime + ", site=" + site
				+ ", siteId=" + siteId + ", themeExp=" + themeExp
				+ ", themeGrabable=" + themeGrabable + ", themeHotNum="
				+ themeHotNum + ", themeId=" + themeId + ", themeName="
				+ themeName + ", themeUrl=" + themeUrl + ", themeUrlMD5="
				+ themeUrlMD5 + "]";
	}

}
