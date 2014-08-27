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
	private String unselectSiteId;// ǰ̨��ѡ��ûѡ�е�ֵ
	private String selectSiteId;// ǰ̨��ѡ��ѡ�е�ֵ
	private HashMap<String, Set<Site>> siteNameMap;// categoryName�����Ӧ��վ������
	private List<Integer> siteIdChoose;// ����ǰ̨siteIdҪѡ��ֵ
	private List<Integer> siteIdUnChoose;// ����ǰ̨siteId��Ҫѡ��ֵ
	private String selectCrawlTime;// �����б��ص�ֵ
	private Timestamp beginTime;// ʱ��εĿ�ʼʱ��
	private Timestamp endTime;// ʱ��εĽ���ʱ��

	public String listAllInfo() {
		String result = "error";
		siteNameMap = new HashMap<String, Set<Site>>();
		siteIdChoose = new ArrayList<Integer>();
		siteIdUnChoose = new ArrayList<Integer>();
		try {
			// ������ݿ������е�site��¼
			sites = siteService.listAll();
			// ������ݿ������е�category��¼
			categories = siteCategoryService.listAll();
			  ActionContext.getContext().getSession().put("categories",
			  categories);

			// ������һ��taskLog����Ϣ
			TaskLog lastTaskLog = taskLogService.loadByIdOnMax();
			String showStartTime = "????";
			String showEndTime = "????";

			// �������ʷ����������־��¼�Żط�ӳ�����һ�ε���Ϣ
			if (lastTaskLog != null) {
				showStartTime = lastTaskLog.getStartTime().toString();
				showEndTime = lastTaskLog.getEndTime().toString();
			}
			// �����һ�ε���������־ʱ����Ϣ����ǰ̨
			ActionContext.getContext().getSession().put("taskStartTime",
					showStartTime);
			ActionContext.getContext().getSession().put("taskEndTime",
					showEndTime);
			
			
			Map<Integer, List<Integer>> categoryOfSiteIdMap=new HashMap<Integer, List<Integer>>();
			
			
			// ��categoryName������Ӧ��site�ŵ�һ��map����
			for (SiteCategory category : categories) {
				sitesInEachCategory = siteService.listByCategoryId(category
						.getCategoryId());
				String name = category.getCategoryName();
				siteNameMap.put(name, sitesInEachCategory);
				setSiteNameMap(siteNameMap);
				List<Integer> categoryOfSiteIds = new ArrayList<Integer>();
				
				//��categoryId����֮��Ӧ��siteId�ŵ�categoryOfSiteIdMap���� 
				for(Site site:sitesInEachCategory){
					int categoryOfSiteId=site.getSiteId();
					categoryOfSiteIds.add(categoryOfSiteId);
				}
				
				categoryOfSiteIdMap.put(category.getCategoryId(), categoryOfSiteIds);
			}
			
			//��categoryOfSiteIdMapת��Ϊjson��ʽ ����ǰ̨
			Gson gson = new Gson();
			String categoryOfSiteIdJson=gson.toJson(categoryOfSiteIdMap).toString();
			
			ActionContext.getContext().getSession().put("categoryOfSiteIdJson",
					categoryOfSiteIdJson);
			ActionContext.getContext().getSession().put("siteNameMap",
					siteNameMap);
		
			// �������ݿ��ж����Ƿ���Ҫץȡ�����벻ͬ��ǰ̨����
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
			this.setTips("ϵͳ��������");
		}
		return result;
	}

	public String saveAllInfo() {
		String result = "error";
		try {
			TransUtil transUtil = new TransUtil();
			// ��ǰ̨ҳ��������ѡ�кͷ�ѡ�е�Id���
			List<Integer> unSelectIdList = transUtil.transSiteId(unselectSiteId);
			List<Integer> selectIdList = transUtil.transSiteId(selectSiteId);

			// ��û��ѡ�е�վ����Ϣ����Ϊ����ץȡ
			if (unSelectIdList != null) {
				for (int eachUnSelectId : unSelectIdList) {
					Site unSelectSite = siteService.loadById(eachUnSelectId);
					unSelectSite.setSiteGrabable(0);
					siteService.update(unSelectSite);
				}
			}
			// ��ѡ�е�վ����Ϣ����Ϊ��ץȡ
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

			// ��ѡ�е�վ��Ŀ�ʼ����ʱ�����
			// �����˵�ʱ���ת��Ϊ��׼ʱ���ʽ
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

			// �õ����ص�ʱ��ѡ��
			// ActionContext.getContext().getSession().put("selectCrawlTime",
			// selectCrawlTime);

			//�����ɼ����߳�����

			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			this.setTips("ϵͳ��������");
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
