package ims.crawler.cache;

public class ThreadEndFlag {

	// �߳̽���״̬λ
	private volatile static boolean threadEndFlag = false; // ��ֵΪtrue��ʾ��ǰû���������߳�������

	public static boolean isThreadEndFlag() {
		return threadEndFlag;
	}

	public static void setThreadEndFlag(boolean threadEndFlag) {
		ThreadEndFlag.threadEndFlag = threadEndFlag;
	}

}
