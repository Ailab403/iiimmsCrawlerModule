package ims.crawler.fetch.special;

import ims.site.model.Site;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author superhy
 * 
 */
public class FetchSpecialContentSiteList {

	// 特殊的时间匹配格式站点列表
	public static Set<String> specialTimeSiteNickNames = new HashSet<String>();
	static {
		specialTimeSiteNickNames.add("qqluntan");
		specialTimeSiteNickNames.add("aoyiluntan");
	}

	// 特殊数字匹配格式站点列表
	public static Set<String> specialNumSiteNickNames = new HashSet<String>();
	static {
		// TODO 添加数字特殊匹配的站点nickName
	}

	/**
	 * 判断站点是否存在于特殊时间匹配格式集合中
	 * 
	 * @param site
	 * @return
	 */
	public static boolean isInSpecialTimeSiteList(Site site) {

		String siteNickName = site.getNickName();
		boolean flagIn = specialTimeSiteNickNames.contains(siteNickName);

		return flagIn;
	}

	/**
	 * 判断站点是否存在于特殊的数字匹配格式集合中
	 * 
	 * @param site
	 * @return
	 */
	public static boolean isInSpecialNumSiteList(Site site) {

		return false;
	}
}
