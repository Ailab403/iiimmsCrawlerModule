package test.crawler.grab.util;

import ims.crawler.grab.util.AnalyzerNum;

import java.util.Scanner;

import org.junit.Test;

public class AnalyzerNumTest {

	@Test
	public void testGetNum() {

		String clickStr = new Scanner(System.in).next();
		String replyStr = new Scanner(System.in).next();

		String clickNum = AnalyzerNum.getClickNum(clickStr);
		String replyNum = AnalyzerNum.getReplyNum(replyStr);

		System.out.println(clickNum + "\n" + replyNum);
	}
}
