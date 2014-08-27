package ims.crawler.fetch;

import ims.crawler.util.HandleLogResult;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

/**
 * 解析节点信息通用接口
 * 
 * @author superhy
 * 
 */
public interface FetchContent {

	/**
	 * 执行多线程任务解析每个主题的所有节点
	 * 
	 * @return
	 */
	public Map<String, Object> execFetchContentThread();

	/**
	 * 帖子解析方法接口，为调用此类的控制层方法提供
	 * 
	 * @param fetchParame
	 * @param grabUserParame
	 * @param site
	 * @param themes
	 * @param handleLogResult
	 * @return
	 */
	public Map<String, Object> execFetchContent(FetchParame fetchParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult);
}
