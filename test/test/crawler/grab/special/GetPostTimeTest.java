package test.crawler.grab.special;

import ims.crawler.grab.special.GrabSpecialContentSiteList;
import ims.crawler.grab.special.GrabSpecialTimeContent;
import ims.crawler.util.HcJsoupDocumentUtil;
import ims.site.model.Site;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class GetPostTimeTest {

	@Test
	public void testGetPostTime() {
		Document document = HcJsoupDocumentUtil.getDocument(
				"http://club.dayoo.com/bbs-gzcs-1.html",
				"http://club.dayoo.com/", "utf-8");

		Element element = document.select("form#moderate").first();
		Element element2 = element.select("tbody[id*=thread_]").first();
		Element element3 = element2.select("td.by>em>a>span").first();

		Site site = new Site();
		site.setNickName("dayangshequ");
		site.setSeedUrl("http://club.dayoo.com/");

		boolean flag = GrabSpecialContentSiteList.isInSpecialTimeSiteList(site);
		System.out.println(flag);

		String postTimeStr = GrabSpecialTimeContent.getSpecialTimeContent(
				element3, site);
		System.out.println(postTimeStr);
	}
}
