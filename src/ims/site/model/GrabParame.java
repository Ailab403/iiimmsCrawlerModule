package ims.site.model;

public class GrabParame {

	// 爬虫固化技术参数表

	private int grabParameId; // 参数编号(primary key)

	private int siteId;
	private int categoryId;

	private String themeListDivQuery; // 主题列表div块识别Query
	private String themeDivQuery; // 每个主题div识别Query
	private String themeNameQuery; // 每个主题名称识别Query
	private String themeUrlQuery; // 每个主题链接识别Query
	private String postListPagerQuery; // 第二层节点列表分页区识别Query
	private String postListNextQuery; // 第二层分页节点列表分页下一页按钮识别Query
	private String postListSpDivQuery; // 单页节点列表div识别Query
	private String postDivQuery; // 每个节点div识别Query
	private String postNameQuery; // 每个节点名称识别Query
	private String postUrlQuery; // 每个节点链接识别Query
	private String postClickNumQuery; // 每个节点点击量识别Query
	private String postReplyNumQuery; // 每个节点回复量识别Query
	private String postForwardNumQuery; // 每个节点转发量识别参数
	private String postTimeQuery; // 每个节点最近回复时间识别Query

	private Site site; // 网站Id外键对应表关联
	private SiteCategory siteCategory;

	public GrabParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrabParame(int siteId, int categoryId, String themeListDivQuery,
			String themeDivQuery, String themeNameQuery, String themeUrlQuery,
			String postListPagerQuery, String postListNextQuery,
			String postListSpDivQuery, String postDivQuery,
			String postNameQuery, String postUrlQuery,
			String postClickNumQuery, String postReplyNumQuery,
			String postForwardNumQuery, String postTimeQuery, Site site,
			SiteCategory siteCategory) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themeListDivQuery = themeListDivQuery;
		this.themeDivQuery = themeDivQuery;
		this.themeNameQuery = themeNameQuery;
		this.themeUrlQuery = themeUrlQuery;
		this.postListPagerQuery = postListPagerQuery;
		this.postListNextQuery = postListNextQuery;
		this.postListSpDivQuery = postListSpDivQuery;
		this.postDivQuery = postDivQuery;
		this.postNameQuery = postNameQuery;
		this.postUrlQuery = postUrlQuery;
		this.postClickNumQuery = postClickNumQuery;
		this.postReplyNumQuery = postReplyNumQuery;
		this.postForwardNumQuery = postForwardNumQuery;
		this.postTimeQuery = postTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public GrabParame(int grabParameId, int siteId, int categoryId,
			String themeListDivQuery, String themeDivQuery,
			String themeNameQuery, String themeUrlQuery,
			String postListPagerQuery, String postListNextQuery,
			String postListSpDivQuery, String postDivQuery,
			String postNameQuery, String postUrlQuery,
			String postClickNumQuery, String postReplyNumQuery,
			String postForwardNumQuery, String postTimeQuery, Site site,
			SiteCategory siteCategory) {
		super();
		this.grabParameId = grabParameId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themeListDivQuery = themeListDivQuery;
		this.themeDivQuery = themeDivQuery;
		this.themeNameQuery = themeNameQuery;
		this.themeUrlQuery = themeUrlQuery;
		this.postListPagerQuery = postListPagerQuery;
		this.postListNextQuery = postListNextQuery;
		this.postListSpDivQuery = postListSpDivQuery;
		this.postDivQuery = postDivQuery;
		this.postNameQuery = postNameQuery;
		this.postUrlQuery = postUrlQuery;
		this.postClickNumQuery = postClickNumQuery;
		this.postReplyNumQuery = postReplyNumQuery;
		this.postForwardNumQuery = postForwardNumQuery;
		this.postTimeQuery = postTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public int getGrabParameId() {
		return grabParameId;
	}

	public void setGrabParameId(int grabParameId) {
		this.grabParameId = grabParameId;
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

	public String getThemeListDivQuery() {
		return themeListDivQuery;
	}

	public void setThemeListDivQuery(String themeListDivQuery) {
		this.themeListDivQuery = themeListDivQuery;
	}

	public String getThemeDivQuery() {
		return themeDivQuery;
	}

	public void setThemeDivQuery(String themeDivQuery) {
		this.themeDivQuery = themeDivQuery;
	}

	public String getThemeNameQuery() {
		return themeNameQuery;
	}

	public void setThemeNameQuery(String themeNameQuery) {
		this.themeNameQuery = themeNameQuery;
	}

	public String getThemeUrlQuery() {
		return themeUrlQuery;
	}

	public void setThemeUrlQuery(String themeUrlQuery) {
		this.themeUrlQuery = themeUrlQuery;
	}

	public String getPostListPagerQuery() {
		return postListPagerQuery;
	}

	public void setPostListPagerQuery(String postListPagerQuery) {
		this.postListPagerQuery = postListPagerQuery;
	}

	public String getPostListNextQuery() {
		return postListNextQuery;
	}

	public void setPostListNextQuery(String postListNextQuery) {
		this.postListNextQuery = postListNextQuery;
	}

	public String getPostListSpDivQuery() {
		return postListSpDivQuery;
	}

	public void setPostListSpDivQuery(String postListSpDivQuery) {
		this.postListSpDivQuery = postListSpDivQuery;
	}

	public String getPostDivQuery() {
		return postDivQuery;
	}

	public void setPostDivQuery(String postDivQuery) {
		this.postDivQuery = postDivQuery;
	}

	public String getPostNameQuery() {
		return postNameQuery;
	}

	public void setPostNameQuery(String postNameQuery) {
		this.postNameQuery = postNameQuery;
	}

	public String getPostUrlQuery() {
		return postUrlQuery;
	}

	public void setPostUrlQuery(String postUrlQuery) {
		this.postUrlQuery = postUrlQuery;
	}

	public String getPostClickNumQuery() {
		return postClickNumQuery;
	}

	public void setPostClickNumQuery(String postClickNumQuery) {
		this.postClickNumQuery = postClickNumQuery;
	}

	public String getPostReplyNumQuery() {
		return postReplyNumQuery;
	}

	public void setPostReplyNumQuery(String postReplyNumQuery) {
		this.postReplyNumQuery = postReplyNumQuery;
	}

	public String getPostForwardNumQuery() {
		return postForwardNumQuery;
	}

	public void setPostForwardNumQuery(String postForwardNumQuery) {
		this.postForwardNumQuery = postForwardNumQuery;
	}

	public String getPostTimeQuery() {
		return postTimeQuery;
	}

	public void setPostTimeQuery(String postTimeQuery) {
		this.postTimeQuery = postTimeQuery;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public SiteCategory getSiteCategory() {
		return siteCategory;
	}

	public void setSiteCategory(SiteCategory siteCategory) {
		this.siteCategory = siteCategory;
	}

	@Override
	public String toString() {
		return "GrabParame [grabParameId=" + grabParameId
				+ ", postClickNumQuery=" + postClickNumQuery
				+ ", postDivQuery=" + postDivQuery + ", postForwardNumQuery="
				+ postForwardNumQuery + ", postListNextQuery="
				+ postListNextQuery + ", postListPagerQuery="
				+ postListPagerQuery + ", postListSpDivQuery="
				+ postListSpDivQuery + ", postNameQuery=" + postNameQuery
				+ ", postReplyNumQuery=" + postReplyNumQuery
				+ ", postTimeQuery=" + postTimeQuery + ", postUrlQuery="
				+ postUrlQuery + ", site=" + site + ", siteCategory="
				+ siteCategory + ", categoryId=" + categoryId
				+ ", siteId=" + siteId + ", themeDivQuery=" + themeDivQuery
				+ ", themeListDivQuery=" + themeListDivQuery
				+ ", themeNameQuery=" + themeNameQuery + ", themeUrlQuery="
				+ themeUrlQuery + "]";
	}

}
