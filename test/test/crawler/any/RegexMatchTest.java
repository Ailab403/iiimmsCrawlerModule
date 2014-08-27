package test.crawler.any;

import java.util.Scanner;

import org.junit.Test;

public class RegexMatchTest {

	@Test
	public void testNumRegex() {
		String testStr = new Scanner(System.in).next();
		
		boolean flag = testStr.matches("^[1-9]\\d*$");
		
		System.out.println(flag);
	}
}
