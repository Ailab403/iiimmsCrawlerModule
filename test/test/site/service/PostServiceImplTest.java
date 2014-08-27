package test.site.service;

import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.PostService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PostServiceImplTest {

	@Test
	public void testAdd() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		PostService postService = (PostService) appContext
				.getBean("postService");

		int themeId = 1;
		int siteId = 1;
		String postName = "缉拿凶手  寻找目击证人";
		String postUrl = "http://tieba.baidu.com/p/2856147964";
		String postUrlMD5 = "130BEDF59B749C4F5B9DD0A44FA15243";
		int clickNum = 0;
		int replyNum = 114;
		int forwardNum = 0;
		Timestamp lastReplyTime = Timestamp.valueOf("2014-03-10 12:09:00");
		Timestamp refreshTime = Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date()));
		Theme theme = new Theme();
		theme.setThemeId(themeId);
		Site site = new Site();
		site.setSiteId(siteId);
		Post post = new Post(siteId, themeId, postName, postUrl, postUrlMD5,
				clickNum, replyNum, forwardNum, lastReplyTime, 1, refreshTime,
				theme, site);

		postService.add(post);

	}

	@Test
	public void testClear() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		PostService postService = (PostService) appContext
				.getBean("postService");

		postService.clear();
	}

	@Test
	public void testListByThemeId() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		PostService postService = (PostService) appContext
				.getBean("postService");

		int themeId = new Scanner(System.in).nextInt();
		Set<Post> posts = postService.listByThemeId(themeId);

		for (Post post : posts) {
			System.out.println(post.toString());
		}
	}
}
