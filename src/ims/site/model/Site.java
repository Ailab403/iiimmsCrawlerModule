package ims.site.model;

import java.sql.Timestamp;

public class Site {

	// 网站信息表

	private int siteId;

	private int categoryId;
	private int toolId;

	private String siteName;
	private String nickName;
	private String seedUrl;
	private String enCode;
	private int siteHotNum;
	private int siteGrabable;
	private Timestamp refreshTime;
	private String siteExp;

	// 对应外键表关联
	private SiteCategory siteCategory;
	private Tool tool;

	public Site() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Site(int categoryId, int toolId, String siteName, String nickName,
			String seedUrl, String enCode, int siteHotNum, int siteGrabable,
			Timestamp refreshTime, String siteExp, SiteCategory siteCategory,
			Tool tool) {
		super();
		this.categoryId = categoryId;
		this.toolId = toolId;
		this.siteName = siteName;
		this.nickName = nickName;
		this.seedUrl = seedUrl;
		this.enCode = enCode;
		this.siteHotNum = siteHotNum;
		this.siteGrabable = siteGrabable;
		this.refreshTime = refreshTime;
		this.siteExp = siteExp;
		this.siteCategory = siteCategory;
		this.tool = tool;
	}

	public Site(int siteId, int categoryId, int toolId, String siteName,
			String nickName, String seedUrl, String enCode, int siteHotNum,
			int siteGrabable, Timestamp refreshTime, String siteExp,
			SiteCategory siteCategory, Tool tool) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.toolId = toolId;
		this.siteName = siteName;
		this.nickName = nickName;
		this.seedUrl = seedUrl;
		this.enCode = enCode;
		this.siteHotNum = siteHotNum;
		this.siteGrabable = siteGrabable;
		this.refreshTime = refreshTime;
		this.siteExp = siteExp;
		this.siteCategory = siteCategory;
		this.tool = tool;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public String getEnCode() {
		return enCode;
	}

	public void setEnCode(String enCode) {
		this.enCode = enCode;
	}

	public int getSiteHotNum() {
		return siteHotNum;
	}

	public void setSiteHotNum(int siteHotNum) {
		this.siteHotNum = siteHotNum;
	}

	public int getSiteGrabable() {
		return siteGrabable;
	}

	public void setSiteGrabable(int siteGrabable) {
		this.siteGrabable = siteGrabable;
	}

	public Timestamp getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Timestamp refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getSiteExp() {
		return siteExp;
	}

	public void setSiteExp(String siteExp) {
		this.siteExp = siteExp;
	}

	public SiteCategory getSiteCategory() {
		return siteCategory;
	}

	public void setSiteCategory(SiteCategory siteCategory) {
		this.siteCategory = siteCategory;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	@Override
	public String toString() {
		return "Site [enCode=" + enCode + ", nickName=" + nickName
				+ ", refreshTime=" + refreshTime + ", seedUrl=" + seedUrl
				+ ", siteCategory=" + siteCategory + ", categoryId="
				+ categoryId + ", siteExp=" + siteExp + ", siteGrabable="
				+ siteGrabable + ", siteHotNum=" + siteHotNum + ", siteId="
				+ siteId + ", siteName=" + siteName + ", tool=" + tool
				+ ", toolId=" + toolId + "]";
	}

}
