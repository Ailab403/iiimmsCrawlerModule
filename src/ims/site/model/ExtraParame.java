package ims.site.model;

public class ExtraParame {

	private int extraParameId;
	private int siteId;
	private String loginUrl;
	private String loginName;
	private String loginPwd;

	// 外键关联实体
	private Site site;

	public ExtraParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExtraParame(int siteId, String loginUrl, String loginName,
			String loginPwd, Site site) {
		super();
		this.siteId = siteId;
		this.loginUrl = loginUrl;
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.site = site;
	}

	public ExtraParame(int extraParameId, int siteId, String loginUrl,
			String loginName, String loginPwd, Site site) {
		super();
		this.extraParameId = extraParameId;
		this.siteId = siteId;
		this.loginUrl = loginUrl;
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.site = site;
	}

	public int getExtraParameId() {
		return extraParameId;
	}

	public void setExtraParameId(int extraParameId) {
		this.extraParameId = extraParameId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String toString() {
		return "ExtraParame [extraParameId=" + extraParameId + ", loginName="
				+ loginName + ", loginPwd=" + loginPwd + ", loginUrl="
				+ loginUrl + ", site=" + site + ", siteId=" + siteId + "]";
	}

}
