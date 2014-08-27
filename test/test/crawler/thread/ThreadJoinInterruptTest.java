package test.crawler.thread;

import ims.crawler.cache.ThreadEndFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author superhy
 * 
 */
public class ThreadJoinInterruptTest {

	public static Boolean endFlag = new Boolean(false);

	public static void main(String[] args) {
		try {
			// 设置线程池容量
			int poolSize = 10;

			// 建立线程队列
			List<Thread> threadList = new ArrayList<Thread>();
			// 设置线程结束状态位初始值为false
			ThreadEndFlag.setThreadEndFlag(false);

			System.out.println("begin!");

			// 逐一向线程池中加入线程
			for (int i = 0; i < poolSize; i++) {
				TestThreadJoin testThread = new TestThreadJoin();
				Thread thread = new Thread(testThread);
				thread.start();
				// threadsPool.submit(thread);
				threadList.add(thread);
			}

			// int control = new Scanner(System.in).nextInt();

			// ThreadEndFlag.setThreadEndFlag(true);

			for (Thread thread : threadList) {
				thread.interrupt();
			}

			for (Thread thread : threadList) {
				thread.join();
			}

			System.out.println("all thread has over!");

			// 关闭线程池
			// threadsPool.shutdownNow();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
