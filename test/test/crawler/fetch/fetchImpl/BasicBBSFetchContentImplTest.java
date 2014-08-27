package test.crawler.fetch.fetchImpl;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.fetch.fetchImpl.BasicBBSFetchContentThread;
import ims.crawler.util.BasicJsoupDocumentUtil;
import ims.crawlerLog.model.TaskLog;
import ims.crawlerLog.service.TaskLogService;
import ims.site.model.FetchPagerObj;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.FetchPagerObjService;
import ims.site.service.FetchParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.PostService;
import ims.site.service.SiteService;
import ims.site.service.ThemeService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class BasicBBSFetchContentImplTest {

	private BasicBBSFetchContentThread basicBBSFetchContentThread;

	@Before
	public void loadThreadObj() {
		ApplicationContext appContext = ApplicationContextFactory.appContext;
		FetchParameService fetchParameService = (FetchParameService) appContext
				.getBean("fetchParameService");
		SiteService siteService = (SiteService) appContext
				.getBean("siteService");
		GrabUserParameService grabUserParameService = (GrabUserParameService) appContext
				.getBean("grabUserParameService");
		FetchPagerObjService fetchPagerObjService = (FetchPagerObjService) appContext
				.getBean("fetchPagerObjService");
		ThemeService themeService = (ThemeService) appContext
				.getBean("themeService");
		PostService postService = (PostService) appContext
				.getBean("postService");
		TaskLogService taskLogService = (TaskLogService) appContext
				.getBean("taskLogService");

		FetchParame fetchParame = fetchParameService.loadBySiteId(3);
		Site site = siteService.loadById(3);
		GrabUserParame grabUserParame = grabUserParameService.loadById(3);
		FetchPagerObj fetchPagerObj = fetchPagerObjService.loadById(3);
		TaskLog taskLog = taskLogService.loadById("20140603164733");
		Theme theme = themeService.listBySiteId(3).iterator().next();
		Set<Post> posts = postService.listByThemeId(theme.getThemeId());

		BasicBBSFetchContentThread basicBBSFetchContentThread = new BasicBBSFetchContentThread(
				fetchParame, grabUserParame, fetchPagerObj, site, taskLog,
				theme, posts);

		setBasicBBSFetchContentThread(basicBBSFetchContentThread);
	}

	@Test
	public void testGetReplyEachArticle() {
		String url = new Scanner(System.in).next();
		Document document = new BasicJsoupDocumentUtil().getDocument(url);

		Element eleArticle = document.select("div.tzbdP").first();

		List<Map<String, Object>> replyMaps = this.basicBBSFetchContentThread
				.getReplyEachArticle(eleArticle);
		for (Map<String, Object> map : replyMaps) {
			System.out.println(map.toString());
		}
	}

	@Test
	public void testGetArticleAllPage() {

		System.out.println(this.basicBBSFetchContentThread.getTheme());

		Set<Post> posts = this.basicBBSFetchContentThread.getPosts();

		Iterator<Post> iterator = posts.iterator();
		while (iterator.hasNext()) {
			Post post = iterator.next();

			List<Map<String, Object>> articleMaps = this.basicBBSFetchContentThread
					.getArticleAllPage(this.basicBBSFetchContentThread
							.getFetchPagerObj(), post);
			for (Map<String, Object> map : articleMaps) {
				System.out.println(map.toString());
			}
		}

	}

	@Test
	public void testCall() throws Exception {
		this.basicBBSFetchContentThread.call();
	}

	public BasicBBSFetchContentThread getBasicBBSFetchContentThread() {
		return basicBBSFetchContentThread;
	}

	public void setBasicBBSFetchContentThread(
			BasicBBSFetchContentThread basicBBSFetchContentThread) {
		this.basicBBSFetchContentThread = basicBBSFetchContentThread;
	}

}
