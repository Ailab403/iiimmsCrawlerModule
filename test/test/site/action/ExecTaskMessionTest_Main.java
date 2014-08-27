package test.site.action;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ims.crawler.exec.ExecTaskMessionExecutor;

public class ExecTaskMessionTest_Main {

	public static void main(String[] args) {

		// ����ExecTaskMessionExecutor�Ѿ���ע����spring���ˣ����Ա��뱻��spring�е���
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ExecTaskMessionExecutor execTaskMessionExecutor = (ExecTaskMessionExecutor) appContext
				.getBean("execTaskMessionExecutor");

		boolean flagTaskMessionBegin = execTaskMessionExecutor.beginTaskMession();
		if (flagTaskMessionBegin == true) {
			System.out.println("����������ɹ�");
		}
		
		execTaskMessionExecutor.waitTaskMessionEnd();
	}
}