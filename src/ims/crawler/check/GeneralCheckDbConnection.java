package ims.crawler.check;

import java.util.HashMap;
import java.util.Map;

import ims.crawler.mongo.MongoCorpusDbBean;
import ims.site.model.ExtraParame;
import ims.site.model.FetchParame;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Site;
import ims.site.model.Tool;
import ims.site.service.ExtraParameService;
import ims.site.service.FetchParameService;
import ims.site.service.GrabParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.SiteService;
import ims.site.service.ToolService;

/**
 * 
 * @author superhy
 * 
 */
public class GeneralCheckDbConnection {

	private SiteService siteService;
	private GrabParameService grabParameService;
	private GrabUserParameService grabUserParameService;
	private FetchParameService fetchParameService;
	private ExtraParameService extraParameService;
	private ToolService toolService;

	public String checkMysqlDb(int testSiteId) {

		// 准备错误描述
		String errorDescribe = "";

		try {
			Site site = siteService.loadById(testSiteId);
			if (site == null) {
				errorDescribe += "没有成功找到站点";
				return errorDescribe;
			}

			GrabParame grabParame = this.grabParameService.loadBySiteId(site
					.getSiteId());
			GrabUserParame grabUserParame = this.grabUserParameService
					.loadBySiteId(site.getSiteId());
			FetchParame fetchParame = this.fetchParameService.loadBySiteId(site
					.getSiteId());
			Tool tool = this.toolService.loadById(site.getTool().getToolId());

			if (grabParame == null) {
				errorDescribe += "没有成功加载爬虫固化参数，";
			}
			if (grabUserParame == null) {
				errorDescribe += "没有成功加载用户自定义参数，";
			}
			if (fetchParame == null) {
				errorDescribe += "没有成功加载解析器固化参数，";
			}
			if (tool == null) {
				errorDescribe += "没有成功加载站点配套工具信息";
			}

			if (site.getSiteCategory().getReqLogin() == 1) {
				ExtraParame extraParame = this.extraParameService
						.loadBySiteId(site.getSiteId());
				if (extraParame == null) {
					errorDescribe += "没有成功加载附加参数";
				}
			}
		} catch (Exception e) {
			errorDescribe += "数据库链接错误";
			return errorDescribe;
		}

		// 如果返回值是空字符串，说明数据库连接正常
		return errorDescribe;
	}

	public String checkMongoDb() {

		String errorDescribe = "";
		try {
			MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		} catch (Exception e) {
			errorDescribe = "MongoDB链接错误";
		}

		// 如果返回值是空字符串，说明数据库连接正常
		return errorDescribe;
	}

	public Map<String, Object> execCheck(Integer testSiteId,
			SiteService siteService, GrabParameService grabParameService,
			GrabUserParameService grabUserParameService,
			FetchParameService fetchParameService,
			ExtraParameService extraParameService, ToolService toolService) {

		// 准备检查结果返回值
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// 默认检查无异常
		int checkRes = 1;

		// 初始化参数
		setSiteService(siteService);
		setGrabParameService(grabParameService);
		setGrabUserParameService(grabUserParameService);
		setFetchParameService(fetchParameService);
		setExtraParameService(extraParameService);
		setToolService(toolService);

		// 获得mysql数据库检查结果
		String errorMysqlDb = checkMysqlDb(testSiteId);
		// 获得mongodb数据库检查结果
		String errorMongoDb = checkMongoDb();

		if (errorMongoDb.equals("") && errorMysqlDb.equals("")) {
			String errorDescribe = "数据库链接正常";
			checkResMap.put("checkRes", checkRes);
			checkResMap.put("errorDescribe", errorDescribe);
		} else {
			String errorDescribe = (errorMysqlDb + errorMongoDb);
			checkRes = 0;// 错误状态位表示发生错误
			checkResMap.put("checkRes", checkRes);
			checkResMap.put("errorDescribe", errorDescribe);
		}

		// 返回检查结果
		return checkResMap;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public GrabParameService getGrabParameService() {
		return grabParameService;
	}

	public void setGrabParameService(GrabParameService grabParameService) {
		this.grabParameService = grabParameService;
	}

	public GrabUserParameService getGrabUserParameService() {
		return grabUserParameService;
	}

	public void setGrabUserParameService(
			GrabUserParameService grabUserParameService) {
		this.grabUserParameService = grabUserParameService;
	}

	public FetchParameService getFetchParameService() {
		return fetchParameService;
	}

	public void setFetchParameService(FetchParameService fetchParameService) {
		this.fetchParameService = fetchParameService;
	}

	public ExtraParameService getExtraParameService() {
		return extraParameService;
	}

	public void setExtraParameService(ExtraParameService extraParameService) {
		this.extraParameService = extraParameService;
	}

	public ToolService getToolService() {
		return toolService;
	}

	public void setToolService(ToolService toolService) {
		this.toolService = toolService;
	}
}
