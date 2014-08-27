package test.crawler.grab.util;

import ims.crawler.grab.util.TransMD5;

import java.util.Scanner;

import org.junit.Test;


public class TransMD5Test {

	@Test
	public void testGetMD5Code() {
		Scanner cin = new Scanner(System.in);
		String testUrl = cin.next();
		
		System.out.println(new TransMD5().getMD5Code(testUrl));
	}

}
