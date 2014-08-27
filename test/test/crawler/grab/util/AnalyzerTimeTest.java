package test.crawler.grab.util;

import ims.crawler.grab.util.AnalyzerTime;

import org.junit.Test;

public class AnalyzerTimeTest {

	@Test
	public void testGetStandardTimeFormat() {
		String formatTimeStr = AnalyzerTime
				.transStandardTimeFormat("08月23日 16:19?来自iPhone 5s");
		System.out.println(formatTimeStr);
	}

	@Test
	public void testCompTimeLimit() {
		String compTime = "2013-04-05 09:12:30";
		String limitTime = "2014-04-06 00:00:00";

		System.out.println(AnalyzerTime.compTimeLimit(compTime, limitTime));
	}

	@Test
	public void testFormatToTimeMillis() {

		String timeRange = "1-2-3";

		System.out.println(AnalyzerTime.formatToDayTimeMillis(timeRange));
	}

	@Test
	public void testBackToGoalTime() {

		String timeRange = "1-0-0";

		System.out.println(AnalyzerTime.backToGoalTime(timeRange));
	}
}
