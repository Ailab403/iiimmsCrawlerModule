package ims.crawler.cache;

public class TaskStatusExp {

	// 总任务对应状态位的说明，编写成静态对象，持久化
	public static String taskStatusExp[] = new String[10]; // 初始多设几个状态位

	static {
		taskStatusExp[0] = "总任务尚未开始";
		taskStatusExp[1] = "总任务正在运行";
		taskStatusExp[2] = "总任务已经暂停";
		taskStatusExp[3] = "总任务顺利结束";
		taskStatusExp[4] = "总任务不完美结束（包含异常退出和提前终止）";
		taskStatusExp[5] = "总任务已被取消";
	}
}
