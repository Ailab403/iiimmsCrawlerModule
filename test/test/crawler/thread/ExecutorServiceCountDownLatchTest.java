package test.crawler.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author superhy
 * 
 */
public class ExecutorServiceCountDownLatchTest {

	public static Boolean endFlag = new Boolean(false);

	public static void main(String[] args) {
		try {
			// 设置线程池容量
			int poolSize = 10;

			// 所有线程统一开始（0）
			CountDownLatch begin = new CountDownLatch(1);
			// 计算所有线程都结束（poolSize）
			CountDownLatch end = new CountDownLatch(poolSize);
			// 结束状态位

			// 建立线程池
			ExecutorService threadsPool = Executors
					.newFixedThreadPool(poolSize);
			List<TestThreadCountDownLatch> threadList = new ArrayList<TestThreadCountDownLatch>();
			// 逐一向线程池中加入线程
			for (int i = 0; i < poolSize; i++) {
				TestThreadCountDownLatch thread = new TestThreadCountDownLatch(begin, end);
				threadsPool.submit(thread);
				threadList.add(thread);
			}

			// int control = new Scanner(System.in).nextInt();

			// 开始启动线程
			begin.countDown();

			System.out.println("begin!");

			for (TestThreadCountDownLatch testThread : threadList) {
				Thread thread = new Thread(testThread);
				thread.join();
			}

			// 等待所有线程结束
			end.await();

			System.out.println("all thread has over!");

			// 关闭线程池
			threadsPool.shutdownNow();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
