package test.crawler.thread;

import ims.crawler.cache.ThreadEndFlag;

/**
 * 
 * @author superhy
 * 
 */
public class TestThreadJoinInterrupt implements Runnable {

	public void run() {

		try {

			System.out.println(Thread.currentThread().getName());
			// Thread.sleep(2);

			while (!Thread.currentThread().isInterrupted()) {
				for (int i = 0; i < 100000; i++) {

					if (ThreadEndFlag.isThreadEndFlag()) {
						return;
					}

					int num = i % 3;
					System.out.println(num);
				}

				System.out.println("ing...");
				System.out.println("ing...");
				System.out.println("ing...");
				System.out.println("ing...");
				System.out.println("ing...");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
