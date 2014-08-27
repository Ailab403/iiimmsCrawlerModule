package ims.crawler.grab;

import java.util.Set;

import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;

/**
 * 爬取主题信息通用接口
 * 
 * @author superhy
 * 
 */
public interface GrabThemeUrls {

	/**
	 * 组装生产主题对象
	 * 
	 * @param themeName
	 * @param themeUrl
	 * @param themeUrlMD5
	 * @return
	 */
	public Theme prodTheme(String themeName, String themeUrl, String themeUrlMD5);

	/**
	 * 从种子页面获取网站待爬取所有主题URL主要实现方法
	 * 
	 * @return
	 */
	public Set<Theme> getThemeUrlSet();

	/**
	 * 主题爬取方法接口，为调用此类的控制层方法提供
	 * 
	 * @param site
	 * @param grabParame
	 * @return
	 */
	public Set<Theme> execGrabTheme(Site site, GrabParame grabParame);

}
