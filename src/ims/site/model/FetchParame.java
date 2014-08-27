package ims.site.model;

public class FetchParame {

	// 采集器固化技术参数表

	private int fetchParameId; // 参数编号(primary key)

	private int siteId;
	private int categoryId;

	private String baNameQuery; // 贴吧名识别Query
	private String titleQuery; // 贴吧标题识别Query
	private String readNumQuery; // 文章阅读量识别参数
	private String commentNumQuery; // 文章评论量识别参数
	private String forwardNumQuery; // 文章转载量识别参数
	private String pagerQuery; // 分页信息识别Query

	private FetchPagerObj fetchPagerObj; // 关联分页实现类信息表

	private String postDivQuery; // 帖子主体div识别Query
	private String postContentQuery; // 帖子内容识别Query
	private String postAuthorQuery; // 发帖作者识别Query
	private String postTimeQuery; // 发帖时间识别Query
	private String replyDivQuery; // 回复主体div识别Query
	private String replyContentQuery; // 回复信息识别Query
	private String replyAuthorQuery; // 回复作者识别Query
	private String replyTimeQuery; // 回复时间识别Query

	private Site site; // 网站Id外键对应表关联
	private SiteCategory siteCategory;

	public FetchParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FetchParame(int siteId, int categoryId, String baNameQuery,
			String titleQuery, String readNumQuery, String commentNumQuery,
			String forawardNumQuery, String pagerQuery,
			FetchPagerObj fetchPagerObj, String postDivQuery,
			String postContentQuery, String postAuthorQuery,
			String postTimeQuery, String replyDivQuery,
			String replyContentQuery, String replyAuthorQuery,
			String replyTimeQuery, Site site, SiteCategory siteCategory) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.baNameQuery = baNameQuery;
		this.titleQuery = titleQuery;
		this.readNumQuery = readNumQuery;
		this.commentNumQuery = commentNumQuery;
		this.forwardNumQuery = forawardNumQuery;
		this.pagerQuery = pagerQuery;
		this.fetchPagerObj = fetchPagerObj;
		this.postDivQuery = postDivQuery;
		this.postContentQuery = postContentQuery;
		this.postAuthorQuery = postAuthorQuery;
		this.postTimeQuery = postTimeQuery;
		this.replyDivQuery = replyDivQuery;
		this.replyContentQuery = replyContentQuery;
		this.replyAuthorQuery = replyAuthorQuery;
		this.replyTimeQuery = replyTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public FetchParame(int fetchParameId, int siteId, int categoryId,
			String baNameQuery, String titleQuery, String readNumQuery,
			String commentNumQuery, String forawardNumQuery, String pagerQuery,
			FetchPagerObj fetchPagerObj, String postDivQuery,
			String postContentQuery, String postAuthorQuery,
			String postTimeQuery, String replyDivQuery,
			String replyContentQuery, String replyAuthorQuery,
			String replyTimeQuery, Site site, SiteCategory siteCategory) {
		super();
		this.fetchParameId = fetchParameId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.baNameQuery = baNameQuery;
		this.titleQuery = titleQuery;
		this.readNumQuery = readNumQuery;
		this.commentNumQuery = commentNumQuery;
		this.forwardNumQuery = forawardNumQuery;
		this.pagerQuery = pagerQuery;
		this.fetchPagerObj = fetchPagerObj;
		this.postDivQuery = postDivQuery;
		this.postContentQuery = postContentQuery;
		this.postAuthorQuery = postAuthorQuery;
		this.postTimeQuery = postTimeQuery;
		this.replyDivQuery = replyDivQuery;
		this.replyContentQuery = replyContentQuery;
		this.replyAuthorQuery = replyAuthorQuery;
		this.replyTimeQuery = replyTimeQuery;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public int getFetchParameId() {
		return fetchParameId;
	}

	public void setFetchParameId(int fetchParameId) {
		this.fetchParameId = fetchParameId;
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

	public String getBaNameQuery() {
		return baNameQuery;
	}

	public void setBaNameQuery(String baNameQuery) {
		this.baNameQuery = baNameQuery;
	}

	public String getTitleQuery() {
		return titleQuery;
	}

	public void setTitleQuery(String titleQuery) {
		this.titleQuery = titleQuery;
	}

	public String getReadNumQuery() {
		return readNumQuery;
	}

	public void setReadNumQuery(String readNumQuery) {
		this.readNumQuery = readNumQuery;
	}

	public String getCommentNumQuery() {
		return commentNumQuery;
	}

	public void setCommentNumQuery(String commentNumQuery) {
		this.commentNumQuery = commentNumQuery;
	}

	public String getForwardNumQuery() {
		return forwardNumQuery;
	}

	public void setForwardNumQuery(String forwardNumQuery) {
		this.forwardNumQuery = forwardNumQuery;
	}

	public String getPagerQuery() {
		return pagerQuery;
	}

	public void setPagerQuery(String pagerQuery) {
		this.pagerQuery = pagerQuery;
	}

	public FetchPagerObj getFetchPagerObj() {
		return fetchPagerObj;
	}

	public void setFetchPagerObj(FetchPagerObj fetchPagerObj) {
		this.fetchPagerObj = fetchPagerObj;
	}

	public String getPostDivQuery() {
		return postDivQuery;
	}

	public void setPostDivQuery(String postDivQuery) {
		this.postDivQuery = postDivQuery;
	}

	public String getPostContentQuery() {
		return postContentQuery;
	}

	public void setPostContentQuery(String postContentQuery) {
		this.postContentQuery = postContentQuery;
	}

	public String getPostAuthorQuery() {
		return postAuthorQuery;
	}

	public void setPostAuthorQuery(String postAuthorQuery) {
		this.postAuthorQuery = postAuthorQuery;
	}

	public String getPostTimeQuery() {
		return postTimeQuery;
	}

	public void setPostTimeQuery(String postTimeQuery) {
		this.postTimeQuery = postTimeQuery;
	}

	public String getReplyDivQuery() {
		return replyDivQuery;
	}

	public void setReplyDivQuery(String replyDivQuery) {
		this.replyDivQuery = replyDivQuery;
	}

	public String getReplyContentQuery() {
		return replyContentQuery;
	}

	public void setReplyContentQuery(String replyContentQuery) {
		this.replyContentQuery = replyContentQuery;
	}

	public String getReplyAuthorQuery() {
		return replyAuthorQuery;
	}

	public void setReplyAuthorQuery(String replyAuthorQuery) {
		this.replyAuthorQuery = replyAuthorQuery;
	}

	public String getReplyTimeQuery() {
		return replyTimeQuery;
	}

	public void setReplyTimeQuery(String replyTimeQuery) {
		this.replyTimeQuery = replyTimeQuery;
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
		return "FetchParame [baNameQuery=" + baNameQuery + ", commentNumQuery="
				+ commentNumQuery + ", fetchPagerObj=" + fetchPagerObj
				+ ", fetchParameId=" + fetchParameId + ", forawardNumQuery="
				+ forwardNumQuery + ", pagerQuery=" + pagerQuery
				+ ", postAuthorQuery=" + postAuthorQuery
				+ ", postContentQuery=" + postContentQuery + ", postDivQuery="
				+ postDivQuery + ", postTimeQuery=" + postTimeQuery
				+ ", readNumQuery=" + readNumQuery + ", replyAuthorQuery="
				+ replyAuthorQuery + ", replyContentQuery=" + replyContentQuery
				+ ", replyDivQuery=" + replyDivQuery + ", replyTimeQuery="
				+ replyTimeQuery + ", site=" + site + ", siteCategory="
				+ siteCategory + ", categoryId=" + categoryId + ", siteId="
				+ siteId + ", titleQuery=" + titleQuery + "]";
	}

}
