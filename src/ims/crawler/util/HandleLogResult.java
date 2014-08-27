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

	// 注入需要的service方法
	private TaskLogService taskLogService;
	private SiteLogService siteLogService;
	private ThemeLogService themeLogService;

	// 注入mongodb的service方法
	private CrawlerMongoService crawlerMongoService;

	// 在本次任务周期中生存的总任务日志实体
	private TaskLog taskLog;

	public TaskLog createNewTaskLog(Set<Site> sites) {

		// 初始化Id是当前时间戳
		String taskLogId = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 状态位任务尚未开始
		int taskStatus = 0;
		// 开始时间为当前时间（估算）
		Timestamp startTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Timestamp activeTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		Timestamp endTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 任务消耗时间初始为0
		String costTime = "00:00:00";
		int costTimeNum = 0;
		// 其余参数初始都为0或为空串
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

		// 创建新的taskLog
		TaskLog newTaskLog = new TaskLog(taskLogId, taskStatus, startTime,
				activeTime, endTime, costTime, costTimeNum, grabSiteNum,
				grabSiteSuccNum, totalThemeNum, totalGrabNewPostNum,
				totalGrabUpdatePostNum, totalFetchPostNum,
				totalFetchSuccPostNum, taskPauseTimes, taskInfoExp, taskLogExp);
		// 向数据库中写入新的任务日志信息
		this.taskLogService.add(newTaskLog);

		return newTaskLog;
	}

	public SiteLog createNewSiteLog(Site site) {

		// 初始化Id是当前taskLogId后缀所属站点编号
		String siteLogId = this.taskLog.getTaskLogId()
				+ Integer.toString(site.getSiteId());
		// 其余参数暂时都以"0"为初始值
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
		String siteLogExp = "站点分任务日志被创建";

		// 创建新的站点分任务日志实体
		SiteLog siteLog = new SiteLog(siteLogId, this.taskLog.getTaskLogId(),
				site.getSiteId(), siteStatus, siteUpdateThemeNum,
				siteNewPostNum, siteUpdatePostNum, siteFixPostNum,
				grabCostTime, grabCostTimeNum, siteFetchNum, siteFetchSuccNum,
				fetchCostTime, fetchCostTimeNum, siteLogExp, this.taskLog, site);
		// 在数据库中存入新的站点分任务日志记录
		this.siteLogService.add(siteLog);

		// 向执行方法返回日志实体
		return siteLog;
	}

	public void addNewThemeLogRealTime(
			Map<String, Object> eachThemePostResultMap) {
		// 获得所对应的主题对象和所属站点对象（外键）
		Theme theme = (Theme) eachThemePostResultMap.get("theme");
		Site site = (Site) eachThemePostResultMap.get("site");

		// 初始化Id是当前taskLogId后缀所属siteId再后缀主题编号
		String themeLogId = this.taskLog.getTaskLogId() + site.getSiteId()
				+ Integer.toString(theme.getThemeId());
		// 爬取节点数以实际返回的映射对象参数为准
		int themeNewPostNum = (Integer) eachThemePostResultMap.get("addNum");
		int themeUpdatePostNum = (Integer) eachThemePostResultMap
				.get("updateNum");
		String themeLogExp = "该主题所有节点已加载成功，主题反馈日志创建";

		// 其余参数暂时为0
		int themeFetchNum = 0;
		int themeFetchSuccNum = 0;

		// 创建新的主题反馈数据日志实体
		ThemeLog themeLog = new ThemeLog(themeLogId, this.taskLog
				.getTaskLogId(), theme.getThemeId(), site.getSiteId(),
				themeNewPostNum, themeUpdatePostNum, themeFetchNum,
				themeFetchSuccNum, themeLogExp, this.taskLog, theme, site);
		// 在数据库中存入对应主题的反馈参数日志记录
		this.themeLogService.add(themeLog);
	}

	// 删除废弃任务的站点分任务日志记录
	public void deleteAbandonedSiteLog(TaskLog taskLog) {
		this.siteLogService.deleteByTaskLogId(taskLog.getTaskLogId());
	}

	// 删除废弃任务的主题反馈日志记录
	public void deleteAbandonedThemeLog(TaskLog taskLog) {
		this.themeLogService.deleteByTaskLogId(taskLog.getTaskLogId());
	}

	// 刷新当前总任务日志的成果参数
	public void refreshTaskLogRealTime() {
		// 查询出属于这次任务的所有siteLog和themeLog
		Set<SiteLog> siteLogs = this.siteLogService.listByTaskLogId(taskLog
				.getTaskLogId());
		Set<ThemeLog> themeLogs = this.themeLogService.listByTaskLogId(taskLog
				.getTaskLogId());

		// 这里的taskLog是初始创建的taskLog，数据不完善，需要计算统计，完善之后，修改入数据库中

		// 将总任务运行的状态改为正在运行
		// if (this.taskLog.getTaskStatus() == 0
		// || this.taskLog.getTaskStatus() == 2) {
		// this.taskLog.setTaskStatus(1);
		// }

		// 得出当前时间
		Timestamp nowTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));

		// 得出花费的时间
		String costTime = AnalyzerTime.anayTimeRange(taskLog.getStartTime(),
				nowTime);
		this.taskLog.setCostTime(costTime);

		// 计算解析成功的站点数量
		int grabSiteSuccNum = 0;
		for (SiteLog siteLog : siteLogs) {
			if (siteLog.getSiteStatus() == 3) {
				grabSiteSuccNum++;
			}
		}
		this.taskLog.setGrabSiteSuccNum(grabSiteSuccNum);

		// 计算本次任务刷新的主题数量
		int totalThemeNum = themeLogs.size();
		this.taskLog.setTotalThemeNum(totalThemeNum);

		// 计算爬取的新节点数量
		int totalGrabNewPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalGrabNewPostNum += siteLog.getSiteNewPostNum();
		}
		this.taskLog.setTotalGrabNewPostNum(totalGrabNewPostNum);

		// 计算爬取刷新的的节点数量
		int totalGrabUpdatePostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalGrabUpdatePostNum += siteLog.getSiteUpdatePostNum();
		}
		this.taskLog.setTotalGrabUpdatePostNum(totalGrabUpdatePostNum);

		// 计算解析的节点数量
		int totalFetchPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalFetchPostNum += siteLog.getSiteFetchNum();
		}
		this.taskLog.setTotalFetchPostNum(totalFetchPostNum);

		// 计算解析成功的节点数量
		int totalFetchSuccPostNum = 0;
		for (SiteLog siteLog : siteLogs) {
			totalFetchSuccPostNum += siteLog.getSiteFetchSuccNum();
		}
		this.taskLog.setTotalFetchSuccPostNum(totalFetchSuccPostNum);

		// 将更新好的解析实体修改入数据库
		this.taskLogService.update(this.taskLog);
	}

	// 在总任务线程死亡的情况下，修改总任务任务日志表，传入对应的总任务以及结束情况（暂停，取消，终止，顺利）
	public void updateTaskLogStatus(int taskStatus) {

		// 得出结束时间
		Timestamp endTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		this.taskLog.setEndTime(endTime);

		// 得出花费的时间
		String costTime = AnalyzerTime.anayTimeRange(taskLog.getStartTime(),
				endTime);
		this.taskLog.setCostTime(costTime);

		// 得出花费时间的秒数
		int costTimeNum = AnalyzerTime.formatToSecTimeSecs(costTime);
		this.taskLog.setCostTimeNum(costTimeNum);

		// 设置总任务状态位
		if (taskStatus == 3) {
			// 如果总任务线程是自然死亡的（没有暂停，没有终止，没有取消），则需要判断一下总任务结束时，任务执行是否完美（是否所有站点都正常解析成功）
			if (this.taskLog.getGrabSiteNum() == this.taskLog
					.getGrabSiteSuccNum()) {
				// 任务执行完美
				this.taskLog.setTaskStatus(3);
			} else {
				this.taskLog.setTaskStatus(4);
			}
		} else if (taskStatus == 5) {
			// 如果总任务是被取消的，需要销毁对应的siteLog和ThemeLog
			this.deleteAbandonedSiteLog(taskLog);
			this.deleteAbandonedThemeLog(taskLog);

			this.taskLog.setTaskStatus(taskStatus);
		} else {
			// 总任务线程不是自然死亡的，也没有被取消，则状态位根据传入的参数而定
			this.taskLog.setTaskStatus(taskStatus);
		}

		// 得出解析结果的描述
		String taskLogExp = "{result:"
				+ TaskStatusExp.taskStatusExp[this.taskLog.getTaskStatus()]
				+ "}";
		this.taskLog.setTaskLogExp(taskLogExp);

		// 将更新好的解析实体修改入数据库
		this.taskLogService.update(this.taskLog);
	}

	public void updateSiteLog(SiteLog siteLog) {
		// 更改的数值数据不能比表中已有的数值数据更小
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
		// tbl_siteLog中的siteFetchSuccNum字段值最终以mongodb中的记录数量为准
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());
		/*
		 * tbl_siteLog中的siteFetchNum需要特殊处理；
		 * 如果最终返回的数值不准确，比mongodb中的记录数还小，那么补救措施为mongodb中的数值为准
		 */
		int siteFetchNum = siteLogBef.getSiteFetchNum() > siteLog
				.getSiteFetchNum() ? siteLogBef.getSiteFetchNum() : siteLog
				.getSiteFetchNum();
		siteFetchNum = siteFetchNum > siteFetchSuccNum ? siteFetchNum
				: siteFetchSuccNum;
		siteLog.setSiteFetchNum(siteFetchNum);
		siteLog.setSiteFetchSuccNum(siteFetchSuccNum);

		// 算出grab所耗费的秒数和fetch所耗费的秒数
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
		// 获得对应的siteId和themeId
		Site site = (Site) eachThemePostResultMap.get("site");

		// 拼接出siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// 根据拼接出的Id查询出对应的siteLog实体
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// 修改一个站点实时的爬取增加节点数，更新节点数，微调节点数
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
			System.out.println("没有成功加载出siteLog");
		}
	}

	public void refreshSiteLogFetchRealTime(
			Map<String, Object> contentPartResultMap) {
		// 获得对应的siteId和themeId
		Site site = (Site) contentPartResultMap.get("site");

		// 拼接出siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// 根据拼接出的Id查询出对应的siteLog实体
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// tbl_siteLog中的siteFetchSuccNum字段值最终以mongodb中的记录数量为准
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());

		// 修改一个站点实时的解析节点数和解析成功数
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
			System.out.println("没有成功加载出siteLog");
		}
	}

	// 在线程的粒度上刷新tbl_siteLog表
	public void refreshSiteLogFetchRealTimeInThread(Site site) {
		// 拼接出siteLogId
		String siteLogId = this.taskLog.getTaskLogId() + site.getSiteId();
		// 根据拼接出的Id查询出对应的siteLog实体
		SiteLog siteLog = this.siteLogService.loadById(siteLogId);

		// tbl_siteLog中的siteFetchSuccNum字段值最终以mongodb中的记录数量为准
		int siteFetchSuccNum = crawlerMongoService
				.countByTaskLogIdInCollection(siteLog.getSite().getNickName(),
						this.taskLog.getTaskLogId());
		/*
		 * tbl_siteLog中的siteFetchNum需要特殊处理；
		 * 如果最终返回的数值不准确，比mongodb中的记录数还小，那么补救措施为mongodb中的数值为准
		 */
		int siteFetchNum = siteFetchSuccNum;
		siteLog.setSiteFetchNum(siteFetchNum);
		siteLog.setSiteFetchSuccNum(siteFetchSuccNum);
	}

	public void refreshThemeLogRealTime(Map<String, Object> contentPartResultMap) {
		// 获得对应的siteId和themeId
		Site site = (Site) contentPartResultMap.get("site");
		Theme theme = (Theme) contentPartResultMap.get("theme");

		// 拼接出themeLogId
		String themeLogId = this.taskLog.getTaskLogId() + site.getSiteId()
				+ theme.getThemeId();
		// 根据拼接出的Id查询出对应的themeLog实体
		ThemeLog themeLog = this.themeLogService.loadById(themeLogId);

		if (themeLog != null) {
			// 修改解析成功的节点数
			themeLog.setThemeFetchNum((Integer) contentPartResultMap
					.get("fetchNum"));
			themeLog.setThemeFetchSuccNum((Integer) contentPartResultMap
					.get("fetchSuccNum"));
			this.themeLogService.update(themeLog);
		} else {
			System.out.println("没有成功加载出themeLog");
		}
	}

	public SiteLog loadOldSiteLog(Site site) {
		// 根据siteLogId查询出对应的siteLog
		SiteLog siteLog;

		try {
			// 根据taskLogId和siteId拼接出siteLogId
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
