package ims.crawler.util;

import java.util.Map;
import java.util.Set;

import ims.site.model.Post;
import ims.site.model.Theme;
import ims.site.service.PostService;
import ims.site.service.SiteService;
import ims.site.service.ThemeService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author superhy
 * 
 */
public class HandleGrabResult {

	// ע����Ҫ��service����
	private SiteService siteService;
	private ThemeService themeService;
	private PostService postService;

	public void refreshThemeTbl(Set<Theme> themes) {
		// ������Ҳʵ��������ȡ�����ԭ���ʹ��ڣ���ִ�и��²�����ԭ�������ڣ�ִ�в������
		for (Theme theme : themes) {
			Theme themeHad = this.themeService.loadByThemeUrlMD5(theme
					.getThemeUrlMD5());
			if (themeHad != null) {
				theme.setThemeId(themeHad.getThemeId());
				this.themeService.update(theme);
			} else {
				this.themeService.add(theme);
			}
		}
	}

	public void refreshPostTbl(Map<String, Object> postsMap) {
		try {
			Set<Post> postsAdd = (Set<Post>) postsMap.get("add");
			Set<Post> postsUpdate = (Set<Post>) postsMap.get("update");
			Set<Post> postUpdateNum = (Set<Post>) postsMap.get("updateNum");

			if (postsAdd != null && postsAdd.size() != 0) {
				for (Post post : postsAdd) {
					// TODO delete print
					System.out.println("��ӣ�" + post.toString());

					this.postService.add(post);
				}
			}
			if (postsUpdate != null && postsUpdate.size() != 0) {
				for (Post post : postsUpdate) {
					// TODO delete print
					System.out.println("���£�" + post.toString());

					this.postService.updateByPostUrlMD5(post);
				}
			}
			if (postUpdateNum != null && postUpdateNum.size() != 0) {
				for (Post post : postUpdateNum) {
					System.out.println("���ָ��£�" + post.toString());

					this.postService.updateNumByPostUrlMD5(post);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public ThemeService getThemeService() {
		return themeService;
	}

	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

}
