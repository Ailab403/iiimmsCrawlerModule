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
			// �����̳߳�����
			int poolSize = 10;

			// �����̶߳���
			List<Thread> threadList = new ArrayList<Thread>();
			// �����߳̽���״̬λ��ʼֵΪfalse
			ThreadEndFlag.setThreadEndFlag(false);

			System.out.println("begin!");

			// ��һ���̳߳��м����߳�
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

			// �ر��̳߳�
			// threadsPool.shutdownNow();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
