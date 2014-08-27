package test.crawler.fetch.special;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.fetch.special.GetSinaOutsideReply;
import ims.site.model.GrabUserParame;
import ims.site.service.GrabUserParameService;

import org.junit.Test;

public class GetSinaOutsideReplyTest {

	@Test
	public void testGetReplyPageContents() {
		GetSinaOutsideReply getSinaOutsideReply = new GetSinaOutsideReply();

		int siteId = 21;

		GrabUserParameService grabUserParameService = (GrabUserParameService) ApplicationContextFactory.appContext
				.getBean("grabUserParameService");
		GrabUserParame grabUserParame = grabUserParameService
				.loadBySiteId(siteId);

		String articlePageUrl = new Scanner(System.in).next();
		List<Map<String, Object>> resList = getSinaOutsideReply
				.getReplyPageContents(articlePageUrl, grabUserParame);

		if (resList == null) {
			System.out.println(resList);
		} else {
			for (Map<String, Object> map : resList) {
				System.out.println(map.toString());
			}
		}
	}
}
