package test.site.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ims.crawler.exec.ExecTaskMessionExecutor;

public class ExecTaskMessionTest_Main {

	public static void main(String[] args) {

		// 由于ExecTaskMessionExecutor已经被注册在spring中了，所以必须被从spring中调用
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ExecTaskMessionExecutor execTaskMessionExecutor = (ExecTaskMessionExecutor) appContext
				.getBean("execTaskMessionExecutor");

		boolean flagTaskMessionBegin = execTaskMessionExecutor.beginTaskMession();
		if (flagTaskMessionBegin == true) {
			System.out.println("开启总任务成功");
		}
		
		execTaskMessionExecutor.waitTaskMessionEnd();
	}
}