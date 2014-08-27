package ims.site.model;

import java.sql.Timestamp;

public class Post {

	// 文章链接信息表

	private int postId;

	private int siteId;
	private int themeId;

	private String postName;
	private String postUrl;
	private String postUrlMD5;
	private int clickNum;
	private int replyNum;
	private int forwardNum;
	private Timestamp lastReplyTime;
	private int fetchable;
	private Timestamp refreshTime;

	// 主题Id外键对应表关联
	private Theme theme;
	private Site site;

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int siteId, int themeId, String postName, String postUrl,
			String postUrlMD5, int clickNum, int replyNum, int forwardNum,
			Timestamp lastReplyTime, int fetchable, Timestamp refreshTime,
			Theme theme, Site site) {
		super();
		this.siteId = siteId;
		this.themeId = themeId;
		this.postName = postName;
		this.postUrl = postUrl;
		this.postUrlMD5 = postUrlMD5;
		this.clickNum = clickNum;
		this.replyNum = replyNum;
		this.forwardNum = forwardNum;
		this.lastReplyTime = lastReplyTime;
		this.fetchable = fetchable;
		this.refreshTime = refreshTime;
		this.theme = theme;
		this.site = site;
	}

	public Post(int postId, int siteId, int themeId, String postName,
			String postUrl, String postUrlMD5, int clickNum, int replyNum,
			int forwardNum, Timestamp lastReplyTime, int fetchable,
			Timestamp refreshTime, Theme theme, Site site) {
		super();
		this.postId = postId;
		this.siteId = siteId;
		this.themeId = themeId;
		this.postName = postName;
		this.postUrl = postUrl;
		this.postUrlMD5 = postUrlMD5;
		this.clickNum = clickNum;
		this.replyNum = replyNum;
		this.forwardNum = forwardNum;
		this.lastReplyTime = lastReplyTime;
		this.fetchable = fetchable;
		this.refreshTime = refreshTime;
		this.theme = theme;
		this.site = site;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getPostUrlMD5() {
		return postUrlMD5;
	}

	public void setPostUrlMD5(String postUrlMD5) {
		this.postUrlMD5 = postUrlMD5;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getForwardNum() {
		return forwardNum;
	}

	public void setForwardNum(int forwardNum) {
		this.forwardNum = forwardNum;
	}

	public Timestamp getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Timestamp lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public int getFetchable() {
		return fetchable;
	}

	public void setFetchable(int fetchable) {
		this.fetchable = fetchable;
	}

	public Timestamp getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Timestamp refreshTime) {
		this.refreshTime = refreshTime;
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
				+ ((postUrlMD5 == null) ? 0 : postUrlMD5.hashCode());
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
		Post other = (Post) obj;
		if (postUrlMD5 == null) {
			if (other.postUrlMD5 != null)
				return false;
		} else if (!postUrlMD5.equals(other.postUrlMD5))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [clickNum=" + clickNum + ", fetchable=" + fetchable
				+ ", forwardNum=" + forwardNum + ", lastReplyTime="
				+ lastReplyTime + ", postId=" + postId + ", postName="
				+ postName + ", postUrl=" + postUrl + ", postUrlMD5="
				+ postUrlMD5 + ", refreshTime=" + refreshTime + ", replyNum="
				+ replyNum + ", site=" + site + ", siteId=" + siteId
				+ ", theme=" + theme + ", themeId=" + themeId + "]";
	}

}
