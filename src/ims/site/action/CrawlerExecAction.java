package ims.site.action;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.TaskLogService;
import ims.site.action.util.TransUtil;
import ims.site.model.SiteCategory;
import ims.site.model.GrabUserParame;
import ims.site.model.Site;
import ims.site.service.SiteCategoryService;
import ims.site.service.GrabUserParameService;
import ims.site.service.SiteService;

@SuppressWarnings("serial")
public class CrawlerExecAction extends ActionSupport {
	private SiteService siteService;
	private SiteCategoryService siteCategoryService;
	private GrabUserParameService grabUserParameService;
	private TaskLogService taskLogService;
	private int siteId;
	private int categoryId;
	private Site site;
	private String tips;
	private Set<Site> sites;
	private Set<SiteCategory> categories;
	private Set<Site> sitesInEachCategory;
	private List<TaskLog> taskLogs;
	private String unselectSiteId;// 前台复选框没选中的值
	private String selectSiteId;// 前台复选框选中的值
	private HashMap<String, Set<Site>> siteNameMap;// categoryName与其对应的站点名字
	private List<Integer> siteIdChoose;// 传到前台siteId要选的值
	private List<Integer> siteIdUnChoose;// 传到前台siteId不要选的值
	private String selectCrawlTime;// 下拉列表返回的值
	private Timestamp beginTime;// 时间段的开始时间
	private Timestamp endTime;// 时间段的结束时间

	public String listAllInfo() {
		String result = "error";
		siteNameMap = new HashMap<String, Set<Site>>();
		siteIdChoose = new ArrayList<Integer>();
		siteIdUnChoose = new ArrayList<Integer>();
		try {
			// 获得数据库中所有的site记录
			sites = siteService.listAll();
			// 获得数据库中所有的category记录
			categories = siteCategoryService.listAll();
			  ActionContext.getContext().getSession().put("categories",
			  categories);

			// 获得最近一次taskLog的信息
			TaskLog lastTaskLog = taskLogService.loadByIdOnMax();
			String showStartTime = "????";
			String showEndTime = "????";

			// 如果有历史的总任务日志记录才回反映出最近一次的信息
			if (lastTaskLog != null) {
				showStartTime = lastTaskLog.getStartTime().toString();
				showEndTime = lastTaskLog.getEndTime().toString();
			}
			// 将最近一次的总任务日志时间信息传到前台
			ActionContext.getContext().getSession().put("taskStartTime",
					showStartTime);
			ActionContext.getContext().getSession().put("taskEndTime",
					showEndTime);
			
			
			Map<Integer, List<Integer>> categoryOfSiteIdMap=new HashMap<Integer, List<Integer>>();
			
			
			// 把categoryName和它对应的site放到一个map里面
			for (SiteCategory category : categories) {
				sitesInEachCategory = siteService.listByCategoryId(category
						.getCategoryId());
				String name = category.getCategoryName();
				siteNameMap.put(name, sitesInEachCategory);
				setSiteNameMap(siteNameMap);
				List<Integer> categoryOfSiteIds = new ArrayList<Integer>();
				
				//把categoryId和与之对应的siteId放到categoryOfSiteIdMap里面 
				for(Site site:sitesInEachCategory){
					int categoryOfSiteId=site.getSiteId();
					categoryOfSiteIds.add(categoryOfSiteId);
				}
				
				categoryOfSiteIdMap.put(category.getCategoryId(), categoryOfSiteIds);
			}
			
			//把categoryOfSiteIdMap转换为json格式 传到前台
			Gson gson = new Gson();
			String categoryOfSiteIdJson=gson.toJson(categoryOfSiteIdMap).toString();
			
			ActionContext.getContext().getSession().put("categoryOfSiteIdJson",
					categoryOfSiteIdJson);
			ActionContext.getContext().getSession().put("siteNameMap",
					siteNameMap);
		
			// 根据数据库中读出是否需要抓取，传入不同的前台集合
			for (Site site : sites) {
				int s = site.getSiteGrabable();
				int a = site.getSiteId();
				if (s == 1) {
					siteIdChoose.add(a);
				} else {
					siteIdUnChoose.add(a);
				}

			}
			ActionContext.getContext().getSession().put("siteIdChoose",
					siteIdChoose);
			ActionContext.getContext().getSession().put("siteIdUnChoose",
					siteIdUnChoose);
			result = "listAllInfo";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("系统出现问题");
		}
		return result;
	}

	public String saveAllInfo() {
		String result = "error";
		try {
			TransUtil transUtil = new TransUtil();
			// 从前台页面获得所有选中和非选中的Id情况
			List<Integer> unSelectIdList = transUtil.transSiteId(unselectSiteId);
			List<Integer> selectIdList = transUtil.transSiteId(selectSiteId);

			// 把没有选中的站点信息都改为不可抓取
			if (unSelectIdList != null) {
				for (int eachUnSelectId : unSelectIdList) {
					Site unSelectSite = siteService.loadById(eachUnSelectId);
					unSelectSite.setSiteGrabable(0);
					siteService.update(unSelectSite);
				}
			}
			// 把选中的站点信息都改为可抓取
			if (selectIdList != null) {
				for (int eachSelectId : selectIdList) {
					Site selectSite = siteService.loadById(eachSelectId);
					selectSite.setSiteGrabable(1);
					siteService.update(selectSite);
				}
			}

			// ActionContext.getContext().getSession().put("beginTime",
			// beginTime);
			// ActionContext.getContext().getSession().put("endTime", endTime);

			// 被选中的站点的开始结束时间更改
			// 将回退的时间段转化为标准时间格式
			String timeRangeLimit = transUtil
					.transCrawlTimestamp(selectCrawlTime);

			// ActionContext.getContext().getSession().put("timeRangeLimit",
			// timeRangeLimit);

			for (int eachSelectId : selectIdList) {
				GrabUserParame gParame = grabUserParameService
						.loadBySiteId(eachSelectId);

				if (beginTime == null || endTime == null
						|| beginTime.equals("") || endTime.equals("")) {
					gParame.setPostTimeRangeLimit(timeRangeLimit);
					
				} else {
					gParame.setPostStartTimeLimit(beginTime);
					gParame.setPostEndTimeLimit(endTime);
				}
				grabUserParameService.update(gParame);

			}

			// 得到返回的时间选择
			// ActionContext.getContext().getSession().put("selectCrawlTime",
			// selectCrawlTime);

			//触发采集器线程任务

			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("系统出现问题");
		}
		return result;

	}

	public CrawlerExecAction() {
		super();
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Set<Site> getSiteList() {
		return sites;
	}

	public void setSiteList(Set<Site> siteList) {
		this.sites = siteList;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public Set<SiteCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<SiteCategory> categories) {
		this.categories = categories;
	}

	public Set<Site> getSites() {
		return sitesInEachCategory;
	}

	public void setSites(Set<Site> sites) {
		this.sitesInEachCategory = sites;
	}

	public HashMap<String, Set<Site>> getSiteNameMap() {
		return siteNameMap;
	}

	public void setSiteNameMap(HashMap<String, Set<Site>> siteNameMap) {
		this.siteNameMap = siteNameMap;
	}

	public java.util.List<Integer> getSiteIdChoose() {
		return siteIdChoose;
	}

	public void setSiteIdChoose(java.util.List<Integer> siteIdChoose) {
		this.siteIdChoose = siteIdChoose;
	}

	public java.util.List<Integer> getSiteIdUnChoose() {
		return siteIdUnChoose;
	}

	public void setSiteIdUnChoose(java.util.List<Integer> siteIdUnChoose) {
		this.siteIdUnChoose = siteIdUnChoose;
	}

	public String getSelectCrawlTime() {
		return selectCrawlTime;
	}

	public void setSelectCrawlTime(String selectCrawlTime) {
		this.selectCrawlTime = selectCrawlTime;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public GrabUserParameService getGrabUserParameService() {
		return grabUserParameService;
	}

	public void setGrabUserParameService(
			GrabUserParameService grabUserParameService) {
		this.grabUserParameService = grabUserParameService;
	}

	public TaskLogService getTaskLogService() {
		return taskLogService;
	}

	public void setTaskLogService(TaskLogService taskLogService) {
		this.taskLogService = taskLogService;
	}

	public List<TaskLog> getTaskLogs() {
		return taskLogs;
	}

	public void setTaskLogs(List<TaskLog> taskLogs) {
		this.taskLogs = taskLogs;
	}

	public SiteCategoryService getSiteCategoryService() {
		return siteCategoryService;
	}

	public void setSiteCategoryService(SiteCategoryService siteCategoryService) {
		this.siteCategoryService = siteCategoryService;
	}

	public Set<Site> getSitesInEachCategory() {
		return sitesInEachCategory;
	}

	public void setSitesInEachCategory(Set<Site> sitesInEachCategory) {
		this.sitesInEachCategory = sitesInEachCategory;
	}

	public String getUnselectSiteId() {
		return unselectSiteId;
	}

	public void setUnselectSiteId(String unselectSiteId) {
		this.unselectSiteId = unselectSiteId;
	}

	public String getSelectSiteId() {
		return selectSiteId;
	}

	public void setSelectSiteId(String selectSiteId) {
		this.selectSiteId = selectSiteId;
	}

}
