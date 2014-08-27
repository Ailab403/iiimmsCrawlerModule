package test.crawler.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author superhy
 * 
 */
public class TestThreadCountDownLatch implements Runnable {

	// ��ʼ�ͽ������ź�
	private CountDownLatch begin;
	private CountDownLatch end;

	public TestThreadCountDownLatch(CountDownLatch begin, CountDownLatch end) {
		super();
		this.begin = begin;
		this.end = end;
	}

	public void run() {

		try {
			// �̵߳ȴ���ʼ
			this.begin.await();

			System.out.println(Thread.currentThread().getName());
			Thread.sleep(3000);

			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");
			System.out.println("ing...");

			// �ȴ��߳̽���
			this.end.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
