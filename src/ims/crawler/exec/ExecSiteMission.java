package ims.crawler.exec;

import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.SiteLog;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;

import java.util.Map;

/**
 * 
 * @author superhy
 * 
 */
public class ExecSiteMission {

	// ��springע��ִ��ʵ��
	private ExecCheck execCheck;
	private ExecGrabThemeUrls execGrabThemeUrls;
	private ExecGrabPostUrls execGrabPostUrls;
	private ExecFetchContent execFetchContent;
	// ��spring�л�ô�����־�������ʵ��
	private HandleLogResult handleLogResult;

	/**
	 * �������������������վ��Ϊ��λ��ִ�в���ȫ��������γ�һ�������񣬱��ڶ��̷߳�������
	 */
	public void execMissionMethod(Site site, TaskLog taskLog) {
		// �����贫���������ʵ����������վ���ʵ��

		// ���ô�����־�����ʵ����������Ա
		handleLogResult.setTaskLog(taskLog);

		// վ����������־ʵ��
		SiteLog siteLog;

		/*
		 * ״̬λ˵���� ������δ��ʼ��״̬λΪ0�� �����ִ�����������״̬λΪ0�� �����ִ��������ץȡ������״̬λΪ1��
		 * �����ִ����ڵ���Ϣץȡ������״̬λΪ2��
		 * ������������ɣ�״̬λ����Ϊ3��ֻ������״̬λ>=3�ǣ������������������δ�������ֶ�����������״̬λΪ4��
		 */

		// �ж�������״̬
		if (taskLog.getTaskStatus() < 1) {
			// ��������ʾ��������������"��δ��ʼ"

			// ����վ����������־��Ϣ
			siteLog = handleLogResult.createNewSiteLog(site);

		} else if (taskLog.getTaskStatus() >= 1 && taskLog.getTaskStatus() < 3) {
			// ������״̬Ϊ"��ͣ"��˵��������ִ�е�һ�룬˵�����վ��ķ�����Ҳ�п���ִֻ�е�һ�룬��Ҫ���վ��������ִ��״̬

			// ��ѯ��վ��ִ�е���־��Ϊ�˲鿴ִ��״̬
			siteLog = handleLogResult.loadOldSiteLog(site);

			// ���վ���������־��δ������˵�����վ���������δ��ʼ���͵�û�п�ʼִ�д���
			if (siteLog == null) {
				siteLog = handleLogResult.createNewSiteLog(site);
			}
		} else {
			// �������Ѿ���������ȡ������ִ��ʧ�ܣ�ʲô����������ֱ������

			return;
		}

		// �����δ���������ȡ
		if (siteLog.getSiteStatus() < 1) {
			// ִ��Ԥ������
			Map<String, Object> checkFeedBackMap = this.execCheck
					.execCheckMethod(site, siteLog);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				siteLog.setSiteLogExp((String) checkFeedBackMap
						.get("errorDescribe"));
				// �޸�վ���������־������
				handleLogResult.updateSiteLog(siteLog);

				// ���δͨ������ǰ���������񣬷�����״̬ͣ����0
				System.out.println(site.getSiteId() + "��վ��δͨ�����");
				return;
			} else {
				siteLog.setSiteLogExp((String) checkFeedBackMap
						.get("errorDescribe"));
				// �޸�վ���������־����
				handleLogResult.updateSiteLog(siteLog);
			}

			// ִ��ץȡˢ������
			Map<String, Object> themeFeedBackMap = this.execGrabThemeUrls
					.execGrabMethod(site);
			if (themeFeedBackMap == null) {
				siteLog.setSiteLogExp("ִ��ץȡ��������ʧ��");
				// �޸�վ���������־����
				handleLogResult.updateSiteLog(siteLog);

				// ��ǰ�������񣬷�����״̬ͣ����0
				return;
			} else {
				siteLog.setSiteStatus(1);
				siteLog.setSiteThemeNum((Integer) themeFeedBackMap
						.get("siteUpdateThemeNum"));
				siteLog.setSiteLogExp((String) themeFeedBackMap
						.get("grabThemeExp"));

				// �޸�վ���������־��Ϣ
				handleLogResult.updateSiteLog(siteLog);
			}
		}

		// �����δ��ɽڵ���ȡ
		if (siteLog.getSiteStatus() < 2) {
			// ִ��ץȡ���ؽڵ���Ϣ
			Map<String, Object> postFeedBackMap = this.execGrabPostUrls
					.execGrabMethod(site, handleLogResult);
			if (postFeedBackMap == null) {
				siteLog.setSiteLogExp("ִ�м��ؽڵ���Ϣʧ��");
				// �޸�վ���������־����
				handleLogResult.updateSiteLog(siteLog);

				// ��ǰ�������񣬷�����״̬ͣ����1
				return;
			} else {
				siteLog.setSiteStatus(2);
				siteLog.setSiteNewPostNum((Integer) postFeedBackMap
						.get("grabNewPostNum"));
				siteLog.setSiteUpdatePostNum((Integer) postFeedBackMap
						.get("grabUpdatePostNum"));
				siteLog.setSiteFixPostNum((Integer) postFeedBackMap
						.get("grabFixPostNum"));
				siteLog.setGrabCostTime((String) postFeedBackMap
						.get("grabCostTime"));
				siteLog.setSiteLogExp((String) postFeedBackMap
						.get("grabPostExp"));

				// �޸�վ���������־��Ϣ
				handleLogResult.updateSiteLog(siteLog);
			}
		}

		// �����δ���ȫ����������
		if (siteLog.getSiteStatus() < 3) {
			// ִ�нڵ����ݽ���
			Map<String, Object> fetchFeedBackMap = this.execFetchContent
					.execFetchMethod(site, handleLogResult);
			if (fetchFeedBackMap == null) {
				siteLog.setSiteLogExp("ִ�н����ڵ�����ʧ��");
				// �޸�վ���������־����
				handleLogResult.updateSiteLog(siteLog);

				// ��ǰ�������񣬷�����״̬ͣ����2
				return;
			} else {
				siteLog.setSiteStatus(3);
				siteLog.setSiteFetchNum((Integer) fetchFeedBackMap
						.get("fetchNum"));
				siteLog.setSiteFetchSuccNum((Integer) fetchFeedBackMap
						.get("fetchSuccNum"));
				siteLog.setFetchCostTime((String) fetchFeedBackMap
						.get("fetchCostTime"));
				siteLog
						.setSiteLogExp((String) fetchFeedBackMap
								.get("fetchExp"));

				// �޸�վ���������־��Ϣ
				handleLogResult.updateSiteLog(siteLog);
			}
		}
	}

	public ExecCheck getExecCheck() {
		return execCheck;
	}

	public void setExecCheck(ExecCheck execCheck) {
		this.execCheck = execCheck;
	}

	public ExecGrabThemeUrls getExecGrabThemeUrls() {
		return execGrabThemeUrls;
	}

	public void setExecGrabThemeUrls(ExecGrabThemeUrls execGrabThemeUrls) {
		this.execGrabThemeUrls = execGrabThemeUrls;
	}

	public ExecGrabPostUrls getExecGrabPostUrls() {
		return execGrabPostUrls;
	}

	public void setExecGrabPostUrls(ExecGrabPostUrls execGrabPostUrls) {
		this.execGrabPostUrls = execGrabPostUrls;
	}

	public ExecFetchContent getExecFetchContent() {
		return execFetchContent;
	}

	public void setExecFetchContent(ExecFetchContent execFetchContent) {
		this.execFetchContent = execFetchContent;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

}
