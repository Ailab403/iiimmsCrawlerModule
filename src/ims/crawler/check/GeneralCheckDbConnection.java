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

		// ׼����������
		String errorDescribe = "";

		try {
			Site site = siteService.loadById(testSiteId);
			if (site == null) {
				errorDescribe += "û�гɹ��ҵ�վ��";
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
				errorDescribe += "û�гɹ���������̻�������";
			}
			if (grabUserParame == null) {
				errorDescribe += "û�гɹ������û��Զ��������";
			}
			if (fetchParame == null) {
				errorDescribe += "û�гɹ����ؽ������̻�������";
			}
			if (tool == null) {
				errorDescribe += "û�гɹ�����վ�����׹�����Ϣ";
			}

			if (site.getSiteCategory().getReqLogin() == 1) {
				ExtraParame extraParame = this.extraParameService
						.loadBySiteId(site.getSiteId());
				if (extraParame == null) {
					errorDescribe += "û�гɹ����ظ��Ӳ���";
				}
			}
		} catch (Exception e) {
			errorDescribe += "���ݿ����Ӵ���";
			return errorDescribe;
		}

		// �������ֵ�ǿ��ַ�����˵�����ݿ���������
		return errorDescribe;
	}

	public String checkMongoDb() {

		String errorDescribe = "";
		try {
			MongoCorpusDbBean mongoDbBean = MongoCorpusDbBean.getMongoDbBean();
		} catch (Exception e) {
			errorDescribe = "MongoDB���Ӵ���";
		}

		// �������ֵ�ǿ��ַ�����˵�����ݿ���������
		return errorDescribe;
	}

	public Map<String, Object> execCheck(Integer testSiteId,
			SiteService siteService, GrabParameService grabParameService,
			GrabUserParameService grabUserParameService,
			FetchParameService fetchParameService,
			ExtraParameService extraParameService, ToolService toolService) {

		// ׼�����������ֵ
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// Ĭ�ϼ�����쳣
		int checkRes = 1;

		// ��ʼ������
		setSiteService(siteService);
		setGrabParameService(grabParameService);
		setGrabUserParameService(grabUserParameService);
		setFetchParameService(fetchParameService);
		setExtraParameService(extraParameService);
		setToolService(toolService);

		// ���mysql���ݿ�����
		String errorMysqlDb = checkMysqlDb(testSiteId);
		// ���mongodb���ݿ�����
		String errorMongoDb = checkMongoDb();

		if (errorMongoDb.equals("") && errorMysqlDb.equals("")) {
			String errorDescribe = "���ݿ���������";
			checkResMap.put("checkRes", checkRes);
			checkResMap.put("errorDescribe", errorDescribe);
		} else {
			String errorDescribe = (errorMysqlDb + errorMongoDb);
			checkRes = 0;// ����״̬λ��ʾ��������
			checkResMap.put("checkRes", checkRes);
			checkResMap.put("errorDescribe", errorDescribe);
		}

		// ���ؼ����
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
