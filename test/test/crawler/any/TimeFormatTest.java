package test.crawler.any;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TimeFormatTest {

	@Test
	public void testTimeFormat() {
		String newTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());

		System.out.println(newTime);
	}
}
