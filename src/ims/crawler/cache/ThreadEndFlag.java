package ims.crawler.cache;

public class ThreadEndFlag {

	// 线程结束状态位
	private volatile static boolean threadEndFlag = false; // 初值为true表示当前没有总任务线程在运行

	public static boolean isThreadEndFlag() {
		return threadEndFlag;
	}

	public static void setThreadEndFlag(boolean threadEndFlag) {
		ThreadEndFlag.threadEndFlag = threadEndFlag;
	}

}
