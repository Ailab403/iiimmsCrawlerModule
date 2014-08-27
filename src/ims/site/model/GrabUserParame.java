package ims.site.model;

import java.sql.Timestamp;

public class GrabUserParame {

	// 爬虫用户自定义配置参数表

	private int grabUserParameId; // 参数编号(primary key)

	private int siteId;
	private int categoryId;

	private int themePageNumLimit; // 每个主题（板块）获取帖子列表页数限制
	private Timestamp postStartTimeLimit; // 主题列表页时间限制开始时间（标准时间格式）
	private Timestamp postEndTimeLimit; // 主题列表页时间限制截止时间（标准时间格式）
	private String postTimeRangeLimit; // 时间回退间隔限制
	private int timeDeterminer; // 时间限制决定因素
	private int postClickNumLimit; // 节点热度点击量限制
	private int postReplyNumLimit; // 节点热度回复限制
	private int postForwardNumLimit; // 节点转载量限制

	// 网站Id外键对应表关联
	private Site site;
	private SiteCategory siteCategory;

	public GrabUserParame() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrabUserParame(int siteId, int categoryId, int themePageNumLimit,
			Timestamp postStartTimeLimit, Timestamp postEndTimeLimit,
			String postTimeRangeLimit, int timeDeterminer,
			int postClickNumLimit, int postReplyNumLimit,
			int postForwardNumLimit, Site site, SiteCategory siteCategory) {
		super();
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themePageNumLimit = themePageNumLimit;
		this.postStartTimeLimit = postStartTimeLimit;
		this.postEndTimeLimit = postEndTimeLimit;
		this.postTimeRangeLimit = postTimeRangeLimit;
		this.timeDeterminer = timeDeterminer;
		this.postClickNumLimit = postClickNumLimit;
		this.postReplyNumLimit = postReplyNumLimit;
		this.postForwardNumLimit = postForwardNumLimit;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public GrabUserParame(int grabUserParameId, int siteId, int categoryId,
			int themePageNumLimit, Timestamp postStartTimeLimit,
			Timestamp postEndTimeLimit, String postTimeRangeLimit,
			int timeDeterminer, int postClickNumLimit, int postReplyNumLimit,
			int postForwardNumLimit, Site site, SiteCategory siteCategory) {
		super();
		this.grabUserParameId = grabUserParameId;
		this.siteId = siteId;
		this.categoryId = categoryId;
		this.themePageNumLimit = themePageNumLimit;
		this.postStartTimeLimit = postStartTimeLimit;
		this.postEndTimeLimit = postEndTimeLimit;
		this.postTimeRangeLimit = postTimeRangeLimit;
		this.timeDeterminer = timeDeterminer;
		this.postClickNumLimit = postClickNumLimit;
		this.postReplyNumLimit = postReplyNumLimit;
		this.postForwardNumLimit = postForwardNumLimit;
		this.site = site;
		this.siteCategory = siteCategory;
	}

	public int getGrabUserParameId() {
		return grabUserParameId;
	}

	public void setGrabUserParameId(int grabUserParameId) {
		this.grabUserParameId = grabUserParameId;
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

	public int getThemePageNumLimit() {
		return themePageNumLimit;
	}

	public void setThemePageNumLimit(int themePageNumLimit) {
		this.themePageNumLimit = themePageNumLimit;
	}

	public Timestamp getPostStartTimeLimit() {
		return postStartTimeLimit;
	}

	public void setPostStartTimeLimit(Timestamp postStartTimeLimit) {
		this.postStartTimeLimit = postStartTimeLimit;
	}

	public Timestamp getPostEndTimeLimit() {
		return postEndTimeLimit;
	}

	public void setPostEndTimeLimit(Timestamp postEndTimeLimit) {
		this.postEndTimeLimit = postEndTimeLimit;
	}

	public String getPostTimeRangeLimit() {
		return postTimeRangeLimit;
	}

	public void setPostTimeRangeLimit(String postTimeRangeLimit) {
		this.postTimeRangeLimit = postTimeRangeLimit;
	}

	public int getTimeDeterminer() {
		return timeDeterminer;
	}

	public void setTimeDeterminer(int timeDeterminer) {
		this.timeDeterminer = timeDeterminer;
	}

	public int getPostClickNumLimit() {
		return postClickNumLimit;
	}

	public void setPostClickNumLimit(int postClickNumLimit) {
		this.postClickNumLimit = postClickNumLimit;
	}

	public int getPostReplyNumLimit() {
		return postReplyNumLimit;
	}

	public void setPostReplyNumLimit(int postReplyNumLimit) {
		this.postReplyNumLimit = postReplyNumLimit;
	}

	public int getPostForwardNumLimit() {
		return postForwardNumLimit;
	}

	public void setPostForwardNumLimit(int postForwardNumLimit) {
		this.postForwardNumLimit = postForwardNumLimit;
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
		return "GrabUserParame [grabUserParameId=" + grabUserParameId
				+ ", postClickNumLimit=" + postClickNumLimit
				+ ", postEndTimeLimit=" + postEndTimeLimit
				+ ", postForwardNumLimit=" + postForwardNumLimit
				+ ", postReplyNumLimit=" + postReplyNumLimit
				+ ", postStartTimeLimit=" + postStartTimeLimit
				+ ", postTimeRangeLimit=" + postTimeRangeLimit + ", site="
				+ site + ", siteCategory=" + siteCategory + ", categoryId="
				+ categoryId + ", siteId=" + siteId + ", themePageNumLimit="
				+ themePageNumLimit + ", timeDeterminer=" + timeDeterminer
				+ "]";
	}

}
