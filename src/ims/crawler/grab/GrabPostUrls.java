package ims.crawler.grab;

import ims.crawler.util.HandleLogResult;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

/**
 * 抓取节点信息通用接口
 * 
 * @author superhy
 * 
 */
public interface GrabPostUrls {

	/**
	 * 执行多线程任务抓取每个主题的所有节点
	 * 
	 * @return
	 */
	public Map<String, Set<Post>> execGrabPostUrlsThread();

	/**
	 * 帖子爬取方法接口，为调用此类的控制层方法提供
	 * 
	 * @param grabParame
	 * @param grabUserParame
	 * @param site
	 * @param themes
	 * @param handleLogResult
	 * @return
	 */
	public Map<String, Set<Post>> execGrabPost(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult);
}
