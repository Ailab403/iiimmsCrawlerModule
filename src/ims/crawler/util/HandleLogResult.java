package ims.crawler.util;

import ims.crawler.cache.TaskStatusExp;
import ims.crawler.grab.util.AnalyzerTime;
import ims.crawler.mongo.service.CrawlerMongoService;
import ims.crawlerLog.model.SiteLog;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.model.ThemeLog;
import ims.crawlerLog.service.SiteLogService;
import ims.crawlerLog.service.TaskLogService;
import ims.crawlerLog.service.ThemeLogService;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.sun.jmx.snmp.tasks.Task;

/**
 * 
 * @author superhy
 * 
 */
public class HandleLogResult {

	// ע����Ҫ��service����
	private TaskLogService taskLogService;
	private SiteLogService siteLogService;
	private ThemeLogService themeLogService;

	// ע��mongodb��service����
	private CrawlerMongoService crawlerMongoService;

	// �ڱ��������������������������־ʵ��
	private TaskLog taskLog;

	public TaskLog createNewTaskLog(Set<Site> sites) {

		// ��ʼ��Id�ǵ�ǰʱ���
		String taskLogId = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// ״̬λ������δ��ʼ
		int taskStatus = 0;
		// ��ʼʱ��Ϊ��ǰʱ�䣨���㣩
		Timestamp startTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Timestamp activeTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Timestamp endTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		// ��������ʱ���ʼΪ0
		String costTime = "00:00:00";
		int costTimeNum = 0;
		// ���������ʼ��Ϊ0��Ϊ�մ�
		int grabSiteNum = sites.size();
		int grabSiteSuccNum = 0;
		int totalThemeNum = 0;
		int totalGrabNewPostNum = 0;
		int totalGrabUpdatePostNum = 0;
		int totalFetchPostNum = 0;
		int totalFetchSuccPostNum = 0;
		int taskPauseTimes = 0;
		String taskInfoExp = "{}";
		String taskLogExp = "{}";

		// �����µ�taskLog
		TaskLog newTaskLog = new TaskLog(taskLogId, taskStatus, startTime,
				activeTime, endTime, costTime, costTimeNum, grabSiteNum,
				grabSiteSuccNum, totalThemeNum, totalGrabNewPostNum,
				totalGrabUpdatePostNum, totalFetchPostNum,
				totalFetchSuccPostNum, taskPauseTimes, taskInfoExp, taskLogExp);
		// �����ݿ���д���µ�������־��Ϣ
		this.taskLogService.add(newTaskLog);

		return newTaskLog;
	}

	public SiteLog createNewSiteLog(Site site) {

		// ��ʼ��Id�ǵ�ǰtaskLogId��׺����վ����
		String siteLogId = this.taskLog.getTaskLogId()
				+ Integer.toString(site.getSiteId());
		// ���������ʱ����"0"Ϊ��ʼֵ
		int siteStatus = 0;
		int siteUpdateThemeNum = 0;
		int siteNewPostNum = 0;
		int siteUpdatePostNum = 0;
		int siteFixPostNum = 0;
		String grabCostTime = "00:00:00";
		int grabCostTimeNum = 0;
		int siteFetchNum = 0;
		int siteFetchSuccNum = 0;
		String fetchCostTime = "00:00:00";
		int fetchCostTimeNum = 0;
		String siteLogExp = "վ���������־������";

		// �����µ�վ���������־ʵ��
		SiteLog siteLog = new SiteLog(siteLogId, this.taskLog.getTaskLogId(),
				site.getSiteId(), siteStatus, siteUpdateThemeNum,
				siteNewPostNum, siteUpdatePostNum, siteFixPostNum,
				grabCostTime, grabCostTimeNum, siteFetchNum, siteFetchSuccNum,
				fetchCostTime, fetchCostTimeNum, siteLogExp, this.taskLog, site);
		// �����ݿ��д����µ�վ���������־��¼
		this.siteLogService.add(siteLog);

		// ��ִ�з���������־ʵ��
		return siteLog;
	}

	public void addNewThemeLogRealTime(
			Map<String, Object> eachThemePostResultMap) {
		// �������Ӧ��������������վ����������
		Theme theme = (Theme) eachThemePostResultMap.get("theme");
		Site site = (Site) eachThemePostResultMap.get("site");

		// ��ʼ��Id�ǵ�ǰtaskLogId��׺����siteId�ٺ�׺������
		String themeLogId = this.taskLog.getTaskLogId() + site.getSiteId()
				+ Integer.toString(theme.getThemeId());
		// ��ȡ�ڵ�����ʵ�ʷ��ص�ӳ��������Ϊ׼
		int themeNewPostNum = (Integer) eachThemePostResultMap.get("addNum");
		int themeUpdatePostNum = (Integer) eachThemePostResultMap
				.get("updateNum");
		String themeLogExp = "���������нڵ��Ѽ��سɹ������ⷴ����־����";

		// ���������ʱΪ0
		int themeFetchNum = 0;
		int themeFetchSuccNum = 0;

		// �����µ����ⷴ��������־ʵ��
		ThemeLog themeLog = new ThemeLog(themeLogId, this.taskLog
				.getTaskLogId(), theme.getThemeId(), site.getSiteId(),
				themeNewPostNum, themeUpdatePostNum, themeFetchNum,
				themeFetchSuccNum, themeLogExp, this.taskLog, theme, site);
		// �����ݿ��д����Ӧ����ķ���������־��¼
		this.themeLogService.add(themeLog);
	}

	// ɾ�����������վ���������־��¼
	public void deleteAbandonedSiteLog(TaskLog taskLog) {
		this.siteLogService.deleteByTaskLogId(taskLog.getTaskLogId());
	}

	// ɾ��������������ⷴ����־��¼
	public void deleteAbandonedThemeLog(TaskLog taskLog) {
		this.themeLogService.deleteByTaskLogId(taskLog.getTaskLogId());
	}

	// ˢ�µ�ǰ��������־�ĳɹ�����
	public void refreshTaskLogRealTime() {
		// ��ѯ������������������siteLog��themeLog
		Set<SiteLog> siteLogs = this.siteLogService.listByTaskLogId(taskLog
				.getTaskLogId());
		Set<ThemeLog> themeLogs = this.themeLogService.listByTaskLogId(taskLog
				.getTaskLogId());

		// �����taskLog�ǳ�ʼ������taskLog�����ݲ����ƣ���Ҫ����ͳ�ƣ�����֮���޸������ݿ���

		// �����������е�״̬��Ϊ��������
		// if (this.taskLog.getTaskStatus() == 0
		// || this.taskLog.getTaskStatus() == 2) {
		// this.taskLog.setTaskStatus(1);
		// }

		// �ó���ǰʱ��
		Timestamp nowTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));

		// �ó����ѵ�ʱ��
		String costTime = AnalyzerTime.anayTimeRange(taskLog.getStartTime(),
				nowTime);
		this.taskLog.setCostTime(costTime);

		// ��������ɹ���վ������
		int grabSiteSuccNum = 0;
		for (SiteLog siteLog : siteLogs) {
			if (siteLog.getSiteStatus() == 3) {
				grabSiteSuccNum++;
			}
		}
		this.taskLog.setGrabSiteSuccNum(grabSiteSuccNum);

		// ���㱾������ˢ�µ���������
		int totalThemeNum = themeLogs.size();
		this.taskLog.setTotalThemeNum(totalThemeNum);

		// ������ȡ���½ڵ�����
		int totalGrabNewPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalGrabNewPostNum += siteLog.getSiteNewPostNum();
		}
		this.taskLog.setTotalGrabNewPostNum(totalGrabNewPostNum);

		// ������ȡˢ�µĵĽڵ�����
		int totalGrabUpdatePostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalGrabUpdatePostNum += siteLog.getSiteUpdatePostNum();
		}
		this.taskLog.setTotalGrabUpdatePostNum(totalGrabUpdatePostNum);

		// ��������Ľڵ�����
		int totalFetchPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalFetchPostNum += siteLog.getSiteFetchNum();
		}
		this.taskLog.setTotalFetchPostNum(totalFetchPostNum);

		// ��������ɹ��Ľڵ�����
		int totalFetchSuccPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalFetchSuccPostNum += siteLog.getSiteFetchSuccNum();
		}
		this.taskLog.setTotalFetchSuccPostNum(totalFetchSuccPostNum);

		// �����ºõĽ���ʵ���޸������ݿ�
		this.taskLogService.update(this.taskLog);
	}

	// ���������߳�����������£��޸�������������־�������Ӧ���������Լ������������ͣ��ȡ������ֹ��˳����
	public void updateTaskLogStatus(int taskStatus) {

		// �ó�����ʱ��
		Timestamp endTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		this.taskLog.setEndTime(endTime);

		// �ó����ѵ�ʱ��
		String costTime = AnalyzerTime.anayTimeRange(taskLog.getStartTime(),
				endTime);
		this.taskLog.setCostTime(costTime);

		// �ó�����ʱ�������
		int costTimeNum = AnalyzerTime.formatToSecTimeSecs(costTime);
		this.taskLog.setCostTimeNum(costTimeNum);

		// ����������״̬λ
		if (taskStatus == 3) {
			// ����������߳�����Ȼ�����ģ�û����ͣ��û����ֹ��û��ȡ����������Ҫ�ж�һ�����������ʱ������ִ���Ƿ��������Ƿ�����վ�㶼���������ɹ���
			if (this.taskLog.getGrabSiteNum() == this.taskLog
					.getGrabSiteSuccNum()) {
				// ����ִ������
				this.taskLog.setTaskStatus(3);
			} else {
				this.taskLog.setTaskStatus(4);
			}
		} else if (taskStatus == 5) {
			// ����������Ǳ�ȡ���ģ���Ҫ���ٶ�Ӧ��siteLog��ThemeLog
			this.deleteAbandonedSiteLog(taskLog);
			this.deleteAbandonedThemeLog(taskLog);

			this.taskLog.setTaskStatus(taskStatus);
		} else {
			// �������̲߳�����Ȼ�����ģ�Ҳû�б�ȡ������״̬λ���ݴ���Ĳ�������
			this.taskLog.setTaskStatus(taskStatus);
		}

		// �ó��������������
		String taskLogExp = "{result:"
				+ TaskStatusExp.taskStatusExp[this.taskLog.getTaskStatus()]
				+ "}";
		this.taskLog.setTaskLogExp(taskLogExp);

		// �����ºõĽ���ʵ���޸������ݿ�
		this.taskLogService.update(this.taskLog);
	}

	public void updateSiteLog(SiteLog siteLog) {
		// ���ĵ���ֵ���ݲ��ܱȱ������е���ֵ���ݸ�С
		SiteLog siteLogBef = this.siteLogService.loadById(siteLog
				.getSiteLogId());
		siteLog.setSiteThemeNum(siteLogBef.getSiteThemeNum() > siteLog
				.getSiteThemeNum() ? siteLogBef.getSiteThemeNum() : siteLog
				.getSiteThemeNum());
		siteLog.setSiteNewPostNum(siteLogBef.getSiteNewPostNum() > siteLog
				.getSiteNewPostNum() ? siteLogBef.getSiteNewPostNum() : siteLog
				.getSiteNewPostNum());
		siteLog
				.setSiteUpdatePostNum(siteLogBef.getSiteUpdatePostNum() > siteLog
						.getSiteUpdatePostNum() ? siteLogBef
						.getSiteUpdatePostNum() : siteLog
						.getSiteUpdatePostNum());
		siteLog.setSiteFixPostNum(siteLogBef.getSiteFixPostNum() > siteLog
				.getSiteFixPostNum() ? siteLogBef.getSiteFixPostNum() : siteLog
				.getSiteFixPostNum());
		// tbl_siteLog�е�siteFetchSuccNum�ֶ�ֵ������mongodb�еļ�¼����Ϊ׼
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());
		/*
		 * tbl_siteLog�е�siteFetchNum��Ҫ���⴦��
		 * ������շ��ص���ֵ��׼ȷ����mongodb�еļ�¼����С����ô���ȴ�ʩΪmongodb�е���ֵΪ׼
		 */
		int siteFetchNum = siteLogBef.getSiteFetchNum() > siteLog
				.getSiteFetchNum() ? siteLogBef.getSiteFetchNum() : siteLog
				.getSiteFetchNum();
		siteFetchNum = siteFetchNum > siteFetchSuccNum ? siteFetchNum
				: siteFetchSuccNum;
		siteLog.setSiteFetchNum(siteFetchNum);
		siteLog.setSiteFetchSuccNum(siteFetchSuccNum);

		// ���grab���ķѵ�������fetch���ķѵ�����
		int grabCostTimeNum = AnalyzerTime.formatToSecTimeSecs(siteLog
				.getGrabCostTime());
		int fetchCostTimeNum = AnalyzerTime.formatToSecTimeSecs(siteLog
				.getFetchCostTime());
		siteLog.setGrabCostTimeNum(grabCostTimeNum);
		siteLog.setFetchCostTimeNum(fetchCostTimeNum);

		this.siteLogService.update(siteLog);
	}

	public void refreshSiteLogGrabRealTime(
			Map<String, Object> eachThemePostResultMap) {
		// ��ö�Ӧ��siteId��themeId
		Site site = (Site) eachThemePostResultMap.get("site");

		// ƴ�ӳ�siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// ����ƴ�ӳ���Id��ѯ����Ӧ��siteLogʵ��
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// �޸�һ��վ��ʵʱ����ȡ���ӽڵ��������½ڵ�����΢���ڵ���
		int siteNewPostNum = siteLog.getSiteNewPostNum()
				+ (Integer) eachThemePostResultMap.get("addNum");
		int siteUpdatePostNum = siteLog.getSiteUpdatePostNum()
				+ (Integer) eachThemePostResultMap.get("updateNum");
		int siteFixPostNum = siteLog.getSiteFixPostNum()
				+ (Integer) eachThemePostResultMap.get("fixNum");

		if (siteLog != null) {
			siteLog.setSiteNewPostNum(siteNewPostNum);
			siteLog.setSiteUpdatePostNum(siteUpdatePostNum);
			siteLog.setSiteFixPostNum(siteFixPostNum);
			this.siteLogService.update(siteLog);
		} else {
			System.out.println("û�гɹ����س�siteLog");
		}
	}

	public void refreshSiteLogFetchRealTime(
			Map<String, Object> contentPartResultMap) {
		// ��ö�Ӧ��siteId��themeId
		Site site = (Site) contentPartResultMap.get("site");

		// ƴ�ӳ�siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// ����ƴ�ӳ���Id��ѯ����Ӧ��siteLogʵ��
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// tbl_siteLog�е�siteFetchSuccNum�ֶ�ֵ������mongodb�еļ�¼����Ϊ׼
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());

		// �޸�һ��վ��ʵʱ�Ľ����ڵ����ͽ����ɹ���
		int siteFetchNum = (siteLog.getSiteFetchNum() + (Integer) contentPartResultMap
				.get("fetchNum")) > siteFetchSuccNum ? (siteLog
				.getSiteFetchNum() + (Integer) contentPartResultMap
				.get("fetchNum")) : siteFetchSuccNum;
		siteFetchSuccNum = (siteLog.getSiteFetchSuccNum() + (Integer) contentPartResultMap
				.get("fetchSuccNum")) > siteFetchSuccNum ? (siteLog
				.getSiteFetchSuccNum() + (Integer) contentPartResultMap
				.get("fetchSuccNum")) : siteFetchSuccNum;

		if (siteLog != null) {
			siteLog.setSiteFetchNum(siteFetchNum);
			siteLog.setSiteFetchSuccNum(siteFetchSuccNum);
			this.siteLogService.update(siteLog);
		} else {
			System.out.println("û�гɹ����س�siteLog");
		}
	}

	// ���̵߳�������ˢ��tbl_siteLog��
	public void refreshSiteLogFetchRealTimeInThread(Site site) {
		// ƴ�ӳ�siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// ����ƴ�ӳ���Id��ѯ����Ӧ��siteLogʵ��
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// tbl_siteLog�е�siteFetchSuccNum�ֶ�ֵ������mongodb�еļ�¼����Ϊ׼
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());
		/*
		 * tbl_siteLog�е�siteFetchNum��Ҫ���⴦��
		 * ������շ��ص���ֵ��׼ȷ����mongodb�еļ�¼����С����ô���ȴ�ʩΪmongodb�е���ֵΪ׼
		 */
		int siteFetchNum = siteFetchSuccNum;
		siteLog.setSiteFetchNum(siteFetchNum);
		siteLog.setSiteFetchSuccNum(siteFetchSuccNum);
	}

	public void refreshThemeLogRealTime(Map<String, Object> contentPartResultMap) {
		// ��ö�Ӧ��siteId��themeId
		Site site = (Site) contentPartResultMap.get("site");
		Theme theme = (Theme) contentPartResultMap.get("theme");

		// ƴ�ӳ�themeLogId
		String themeLogId = this.taskLog.getTaskLogId() + site.getSiteId()
				+ theme.getThemeId();
		// ����ƴ�ӳ���Id��ѯ����Ӧ��themeLogʵ��
		ThemeLog themeLog = this.themeLogService.loadById(themeLogId);

		if (themeLog != null) {
			// �޸Ľ����ɹ��Ľڵ���
			themeLog.setThemeFetchNum((Integer) contentPartResultMap
					.get("fetchNum"));
			themeLog.setThemeFetchSuccNum((Integer) contentPartResultMap
					.get("fetchSuccNum"));
			this.themeLogService.update(themeLog);
		} else {
			System.out.println("û�гɹ����س�themeLog");
		}
	}

	public SiteLog loadOldSiteLog(Site site) {
		// ����siteLogId��ѯ����Ӧ��siteLog
		SiteLog siteLog;

		try {
			// ����taskLogId��siteIdƴ�ӳ�siteLogId
			String siteLogId = this.taskLog.getTaskLogId()
					+ Integer.toString(site.getSiteId());

			siteLog = siteLogService.loadById(siteLogId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			siteLog = null;
		}

		return siteLog;
	}

	/******************************************/

	public TaskLogService getTaskLogService() {
		return taskLogService;
	}

	public void setTaskLogService(TaskLogService taskLogService) {
		this.taskLogService = taskLogService;
	}

	public SiteLogService getSiteLogService() {
		return siteLogService;
	}

	public void setSiteLogService(SiteLogService siteLogService) {
		this.siteLogService = siteLogService;
	}

	public ThemeLogService getThemeLogService() {
		return themeLogService;
	}

	public void setThemeLogService(ThemeLogService themeLogService) {
		this.themeLogService = themeLogService;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public CrawlerMongoService getCrawlerMongoService() {
		return crawlerMongoService;
	}

	public void setCrawlerMongoService(CrawlerMongoService crawlerMongoService) {
		this.crawlerMongoService = crawlerMongoService;
	}

}
