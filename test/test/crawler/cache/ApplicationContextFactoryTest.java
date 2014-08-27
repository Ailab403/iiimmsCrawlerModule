package test.crawler.cache;

import ims.crawler.cache.ApplicationContextFactory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class ApplicationContextFactoryTest {

	@Test
	public void testGetApplicationContext() {
		ApplicationContext appContext = ApplicationContextFactory.appContext;
		ApplicationContext appContext2 = ApplicationContextFactory.appContext;

		System.out.println(appContext.toString() + "\n");
		System.out.println(appContext2.toString());
	}
}
