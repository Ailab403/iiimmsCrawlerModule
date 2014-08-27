package ims.crawler.exec;

import ims.crawler.cache.SingletonBufferPool;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.util.HandleLogResult;
import ims.crawlerLog.model.TaskLog;
import ims.site.model.Site;
import ims.site.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * 
 * @author superhy
 * 
 */
public class ExecTaskMessionExecutor {

	// ��spring��ע��service����
	private SiteService siteService;
	// ���ִ�е�������ʵ��
	private TaskLog taskLog;
	// ��spring�еõ���־��Ϣ���������
	private HandleLogResult handleLogResult;

	// �������̶߳���
	private List<Future<Integer>> threadList = new ArrayList<Future<Integer>>();

	/*
	 * ���߳��в��ù̶���С���̳߳� �������̳߳�(�̶��������̳߳أ���ֹ�����ع��أ�Ĭ���̳߳ش�СΪ5)
	 */
	private static int threadPoolSize = 5; // �̳߳ش�СĬ��Ϊ5;
	private ExecutorService exes;

	// ����µ���������־ʵ��
	public TaskLog getNewTaskLog(Set<Site> sites) {
		// ����´�������������־
		TaskLog newTaskLog = this.handleLogResult.createNewTaskLog(sites);

		return newTaskLog;
	}

	// �������н�������������־�����ݿ��Ӧ����
	public void updateFinishedTaskLog(int taskStatus) {
		// ������������־�������������־��Ա����Ҳ���óɵ�ǰ���е�������ʵ��
		this.handleLogResult.setTaskLog(getTaskLog());

		// �����µ���������־�޸������ݿ�
		this.handleLogResult.refreshTaskLogRealTime();
		// �޸���������־��ִ��״̬
		this.handleLogResult.updateTaskLogStatus(taskStatus);
	}

	// ���ڵ�ǰִ�е����������������ʵʱ�ĳɹ�ͳ��
	public void realTimeTaskMessionFeedBack() {
		// ������������־�������������־��Ա����Ҳ���óɵ�ǰ���е�������ʵ��
		this.handleLogResult.setTaskLog(getTaskLog());

		this.handleLogResult.refreshTaskLogRealTime();
	}

	/**
	 * ������ִ���������޴����߳�
	 * 
	 * @return
	 */
	public boolean checkAliveThread() {

		boolean flag = false;

		if (threadList.size() != 0 && threadList != null) {
			for (Future<Integer> future : threadList) {
				if (!(future.isCancelled() || future.isDone())) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	/**
	 * ��ʼһ���µ������������ͷ��ʼ�ͻָ���ͣ�����������������
	 */
	public boolean beginTaskMession() {

		try {
			// ������սڵ㻺���
			SingletonBufferPool.getSingletonBufferPool().clearBufferPool();

			// ��ѯ�����м����վ��װ��ִ�ж���
			Set<Site> sites = siteService.listBySiteGrabable(1);

			/*
			 * ���������ʵ��Ϊ�գ�˵��û�л�û�п������񣬳�ʼ��������־�������Ϊ�գ�˵��ֻ�ǻָ�һ�����񣬲���Ҫ��һ��
			 */
			if (getTaskLog() == null || getTaskLog().getTaskStatus() >= 3) {
				// ��ȡ��������־��ʵ�壬���õ������ȫ�ֱ�����
				setTaskLog(this.getNewTaskLog(sites));
			}

			// ����̶߳����еľ��߳�
			if (!this.threadList.isEmpty()) {
				this.threadList.clear();
			}
			// �Ե����趨���̳߳ش�С�����µ��̳߳�
			setExes(Executors.newFixedThreadPool(getThreadPoolSize()));
			// �жϴ�ִ��վ��ջ�����Ƿ�Ϊ��
			if (sites.isEmpty()) {
				System.out.println("�̶߳���Ϊ�գ�����");

				return false;
			} else {
				// ִ�������̳߳����
				// ��վ��ִ�ж�ջ����һȡ��վ������½��߳�����
				for (Site site : sites) {
					ExecTaskMissionThread execTaskMissionThread = new ExecTaskMissionThread(
							site, this.taskLog);

					// �ύ�߳������߳̿�ʼ����
					this.threadList
							.add(this.exes.submit(execTaskMissionThread));
				}
				ThreadEndFlag.setThreadEndFlag(false); // �����߳̽���״̬λ��ʼֵΪfalse

				this.updateFinishedTaskLog(1); // ˢ��һ����������־�������������е�״̬��Ϊ��������

				// ����û���쳣�Ļظ�ֵ
				return true;
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// �������쳣�Ļظ�ֵ
			return false;
		}
	}

	/*
	 * �ȴ��߳��������Ȼ����
	 */
	public void waitTaskMessionEnd() {

		try {
			if (!this.threadList.isEmpty()) {
				// �ȴ������߳̽������߳�ͬ����������߳̽����󷵻صĶ�Ӧվ����
				for (Future<Integer> future : threadList) {
					// ����߳������Ƿ���Ȼ���
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "��վ��Ĳɼ������߳�������");
				}

				// �ر��̳߳�
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �ж��߳��Ƿ��Ѿ���ǰ������������������
		if (ThreadEndFlag.isThreadEndFlag()) {
			return;
		}

		// �߳����н����󣬼�����������м��������������״̬����������������ݿ�
		this.updateFinishedTaskLog(3);

		// �߳̽�������ȫ��״̬λ����Ϊtrue����ʾ�����������߳�������
		ThreadEndFlag.setThreadEndFlag(true);
	}

	/**
	 * ȡ����ǰִ�е�������
	 */
	public void cancelTaskMession() {

		// �����ж��Ƿ������Ѿ�������ͣ��״̬
		if (ThreadEndFlag.isThreadEndFlag()) {
			// ������������־��ִ��״̬Ϊ��ȡ��
			this.updateFinishedTaskLog(5);

			return;
		}

		// �����߳̽���״̬λȫ�ֱ���Ϊtrue����ʹ�������е�������ǰ����
		ThreadEndFlag.setThreadEndFlag(true);

		// �ȴ������̶߳�������״̬λ���֮�󣬻���һ�����ӳ٣�
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// ����߳������Ƿ���Ȼ���
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "��վ��Ĳɼ������߳�������");
				}

				// �ر��̳߳�
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ������������־��ִ��״̬Ϊ��ȡ��
		this.updateFinishedTaskLog(5);
	}

	/**
	 * ��ͣ��ǰִ�е�������
	 */
	public void pauseTaskMession() {
		// �����߳̽���״̬λȫ�ֱ���Ϊtrue����ʹ�������е�������ǰ����

		// �ȴ������̶߳�������״̬λ���֮�󣬻���һ�����ӳ٣�
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// ����߳������Ƿ���Ȼ���
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "��վ��Ĳɼ������߳�������");
				}

				// �ر��̳߳�
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ������������־��ִ��״̬λ��ͣ
		this.updateFinishedTaskLog(2);
	}

	/**
	 * ��ֹ��ǰ������
	 */
	public void terminationTaskMession() {
		// �����߳̽���״̬λȫ�ֱ���Ϊtrue����ʹ�������е�������ǰ����
		ThreadEndFlag.setThreadEndFlag(true);

		// �ȴ������̶߳�������״̬λ���֮�󣬻���һ�����ӳ٣�
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// ����߳������Ƿ���Ȼ���
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "��վ��Ĳɼ������߳�������");
				}

				// �ر��̳߳�
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ������������־��ִ��״̬λ��ͣ
		this.updateFinishedTaskLog(4);
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public TaskLog getTaskLog() {
		return taskLog;
	}

	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

	/*
	 * �����趨�̳߳صĴ�С�������Ǹı��̳߳ص�������С����һ�ο�������ʱ�������ָ������񣩾Ͳ����µĴ�С���̳߳�
	 */
	public ExecutorService getExes() {
		return exes;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setExes(ExecutorService exes) {
		this.exes = exes;
	}

}
