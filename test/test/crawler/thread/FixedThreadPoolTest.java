package test.crawler.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPoolTest {

	public void beginThread() {

		ExecutorService exes = Executors.newFixedThreadPool(5);
		Set<Future<Integer>> setThreads = new HashSet<Future<Integer>>();

		for (int i = 0; i < 20; i++) {
			BigDataThreadEntity bigDataThreadEntity = new BigDataThreadEntity(i);

			// 提交线程任务
			setThreads.add(exes.submit(bigDataThreadEntity));
			System.out.println("线程开启");
		}

		Set<Integer> flags = new HashSet<Integer>();
		for (Future<Integer> future : setThreads) {
			int flag = -1;
			try {
				flag = future.get();
				System.out.println("线程结束" + flag);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flags.add(flag);
		}

		System.out.println(setThreads.size());
		System.out.println(flags.size());

		for (Integer flag : flags) {
			System.out.println("任务结束，执行情况：" + flag);
		}

		exes.shutdownNow();
	}

	public static void main(String[] args) {
		new FixedThreadPoolTest().beginThread();
	}
}
