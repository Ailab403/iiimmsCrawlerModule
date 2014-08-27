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
			// �����̳߳�����
			int poolSize = 10;

			// �����߳�ͳһ��ʼ��0��
			CountDownLatch begin = new CountDownLatch(1);
			// ���������̶߳�������poolSize��
			CountDownLatch end = new CountDownLatch(poolSize);
			// ����״̬λ

			// �����̳߳�
			ExecutorService threadsPool = Executors
					.newFixedThreadPool(poolSize);
			List<TestThreadCountDownLatch> threadList = new ArrayList<TestThreadCountDownLatch>();
			// ��һ���̳߳��м����߳�
			for (int i = 0; i < poolSize; i++) {
				TestThreadCountDownLatch thread = new TestThreadCountDownLatch(begin, end);
				threadsPool.submit(thread);
				threadList.add(thread);
			}

			// int control = new Scanner(System.in).nextInt();

			// ��ʼ�����߳�
			begin.countDown();

			System.out.println("begin!");

			for (TestThreadCountDownLatch testThread : threadList) {
				Thread thread = new Thread(testThread);
				thread.join();
			}

			// �ȴ������߳̽���
			end.await();

			System.out.println("all thread has over!");

			// �ر��̳߳�
			threadsPool.shutdownNow();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
