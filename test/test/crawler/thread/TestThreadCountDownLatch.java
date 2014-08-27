package test.crawler.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author superhy
 * 
 */
public class TestThreadCountDownLatch implements Runnable {

	// 开始和结束的信号
	private CountDownLatch begin;
	private CountDownLatch end;

	public TestThreadCountDownLatch(CountDownLatch begin, CountDownLatch end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public void run() {

		try {
			// 线程等待开始
			this.begin.await();

			System.out.println(Thread.currentThread().getName());
			Thread.sleep(3000);

			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");

			// 等待线程结束
			this.end.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
