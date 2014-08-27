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

	// 从spring中注入service对象
	private SiteService siteService;
	// 这次执行的总任务实体
	private TaskLog taskLog;
	// 从spring中得到日志信息处理类对象
	private HandleLogResult handleLogResult;

	// 总任务线程队列
	private List<Future<Integer>> threadList = new ArrayList<Future<Integer>>();

	/*
	 * 大线程中采用固定大小的线程池 ，建立线程池(固定容量的线程池，防止任务负载过重，默认线程池大小为5)
	 */
	private static int threadPoolSize = 5; // 线程池大小默认为5;
	private ExecutorService exes;

	// 获得新的总任务日志实体
	public TaskLog getNewTaskLog(Set<Site> sites) {
		// 获得新创建的总任务日志
		TaskLog newTaskLog = this.handleLogResult.createNewTaskLog(sites);

		return newTaskLog;
	}

	// 处理运行结束后总任务日志的数据库对应操作
	public void updateFinishedTaskLog(int taskStatus) {
		// 将处理任务日志对象的总任务日志成员变量也设置成当前运行的总任务实体
		this.handleLogResult.setTaskLog(getTaskLog());

		// 将最新的总任务日志修改入数据库
		this.handleLogResult.refreshTaskLogRealTime();
		// 修改总任务日志的执行状态
		this.handleLogResult.updateTaskLogStatus(taskStatus);
	}

	// 对于当前执行的这个总任务结果进行实时的成果统计
	public void realTimeTaskMessionFeedBack() {
		// 将处理任务日志对象的总任务日志成员变量也设置成当前运行的总任务实体
		this.handleLogResult.setTaskLog(getTaskLog());

		this.handleLogResult.refreshTaskLogRealTime();
	}

	/**
	 * 检查这个执行器中有无存活的线程
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
	 * 开始一个新的总任务（任务从头开始和恢复暂停的任务都是这个方法）
	 */
	public boolean beginTaskMession() {

		try {
			// 首先清空节点缓存池
			SingletonBufferPool.getSingletonBufferPool().clearBufferPool();

			// 查询出所有激活的站点装入执行队列
			Set<Site> sites = siteService.listBySiteGrabable(1);

			/*
			 * 如果总任务实体为空，说明没有还没有开启任务，初始化任务日志，如果不为空，说明只是恢复一个任务，不需要这一步
			 */
			if (getTaskLog() == null || getTaskLog().getTaskStatus() >= 3) {
				// 获取总任务日志的实体，设置到本类的全局变量中
				setTaskLog(this.getNewTaskLog(sites));
			}

			// 清空线程队列中的旧线程
			if (!this.threadList.isEmpty()) {
				this.threadList.clear();
			}
			// 以当先设定的线程池大小建立新的线程池
			setExes(Executors.newFixedThreadPool(getThreadPoolSize()));
			// 判断待执行站点栈容器是否为空
			if (sites.isEmpty()) {
				System.out.println("线程队列为空，请检查");

				return false;
			} else {
				// 执行任务线程程序块
				// 从站点执行堆栈中逐一取出站点对象新建线程任务
				for (Site site : sites) {
					ExecTaskMissionThread execTaskMissionThread = new ExecTaskMissionThread(
							site, this.taskLog);

					// 提交线程任务，线程开始运行
					this.threadList
							.add(this.exes.submit(execTaskMissionThread));
				}
				ThreadEndFlag.setThreadEndFlag(false); // 设置线程结束状态位初始值为false

				this.updateFinishedTaskLog(1); // 刷新一下总任务日志，将总任务运行的状态改为正在运行

				// 返回没有异常的回复值
				return true;
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// 返回有异常的回复值
			return false;
		}
	}

	/*
	 * 等待线程任务的自然结束
	 */
	public void waitTaskMessionEnd() {

		try {
			if (!this.threadList.isEmpty()) {
				// 等待所有线程结束（线程同步），获得线程结束后返回的对应站点编号
				for (Future<Integer> future : threadList) {
					// 检查线程任务是否仍然存活
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "号站点的采集任务线程已死亡");
				}

				// 关闭线程池
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 判断线程是否已经提前被其他方法结束掉了
		if (ThreadEndFlag.isThreadEndFlag()) {
			return;
		}

		// 线程运行结束后，检查总任务运行及各项分任务运行状态，将结果更新入数据库
		this.updateFinishedTaskLog(3);

		// 线程结束，将全局状态位调整为true，表示现在总任务线程已死亡
		ThreadEndFlag.setThreadEndFlag(true);
	}

	/**
	 * 取消当前执行的总任务
	 */
	public void cancelTaskMession() {

		// 首先判断是否任务已经处于暂停的状态
		if (ThreadEndFlag.isThreadEndFlag()) {
			// 设置总任务日志的执行状态为被取消
			this.updateFinishedTaskLog(5);

			return;
		}

		// 设置线程结束状态位全局变量为true，迫使正在运行的任务提前结束
		ThreadEndFlag.setThreadEndFlag(true);

		// 等待所有线程都结束（状态位设好之后，会有一定的延迟）
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// 检查线程任务是否仍然存活
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "号站点的采集任务线程已死亡");
				}

				// 关闭线程池
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置总任务日志的执行状态为被取消
		this.updateFinishedTaskLog(5);
	}

	/**
	 * 暂停当前执行的总任务
	 */
	public void pauseTaskMession() {
		// 设置线程结束状态位全局变量为true，迫使正在运行的任务提前结束

		// 等待所有线程都结束（状态位设好之后，会有一定的延迟）
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// 检查线程任务是否仍然存活
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "号站点的采集任务线程已死亡");
				}

				// 关闭线程池
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置总任务日志的执行状态位暂停
		this.updateFinishedTaskLog(2);
	}

	/**
	 * 终止当前的任务
	 */
	public void terminationTaskMession() {
		// 设置线程结束状态位全局变量为true，迫使正在运行的任务提前结束
		ThreadEndFlag.setThreadEndFlag(true);

		// 等待所有线程都结束（状态位设好之后，会有一定的延迟）
		try {
			if (!this.threadList.isEmpty()) {
				for (Future<Integer> future : threadList) {
					// 检查线程任务是否仍然存活
					if (future.isCancelled() || future.isDone()) {
						continue;
					}
					int siteId = future.get();
					// TODO delete print
					System.out.println(siteId + "号站点的采集任务线程已死亡");
				}

				// 关闭线程池
				if (!exes.isShutdown() && !exes.isTerminated()) {
					exes.shutdownNow();
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置总任务日志的执行状态位暂停
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
	 * 可以设定线程池的大小，方法是改变线程池的容量大小，下一次开启任务时（包括恢复的任务）就采用新的大小的线程池
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
