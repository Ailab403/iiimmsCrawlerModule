package ims.crawler.grab.grabImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.GrabThemeUrls;
import ims.crawler.grab.util.TransMD5;
import ims.crawler.util.HcLoginJsoupDocumentUtil;
import ims.site.model.ExtraParame;
import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.ExtraParameService;

/**
 * 
 * @author superhy
 * 
 */
public class HcWeiboGrabThemeUrlsImpl implements GrabThemeUrls {

	// 种子站点实体
	private Site site;
	// 爬虫固化技术参数实体
	private GrabParame grabParame;
	// 额外参数，登录信息
	private ExtraParame extraParame;

	// MD5转换工具类
	private static TransMD5 transMD5Obj = new TransMD5();

	/*
	 * 各分部方法传参规范，先传需调用方法的参数，再传固化技术参数， 最后传用户自定义配置参数，按照实体类前后顺序一次传参
	 */

	// 组装生产主题对象
	public Theme prodTheme(String themeName, String themeUrl, String themeUrlMD5) {

		// 设置需要爬取默认为1
		int grabable = 1;
		// 设置不良信息出现热度初始值为0
		int hotNum = 0;
		// 设置更新时间为当时计算机时间
		String refeshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		String themeExp = this.site.getSiteName() + "中的" + themeName + "主题";

		return new Theme(this.site.getSiteId(), themeName, themeUrl,
				themeUrlMD5, grabable, hotNum, Timestamp.valueOf(refeshTime),
				themeExp, site);
	}

	/**
	 * 第一层：从种子页面获取网站待爬取所有主题URL，为进入下一层做准备
	 */
	public Set<Theme> getThemeUrlSet() {

		// 准备要返回的数据
		Set<Theme> themeSet = new HashSet<Theme>();

		String themeName = "weibo";
		String themeUrl = this.site.getSeedUrl();
		String themeUrlMD5 = transMD5Obj.getMD5Code(themeUrl);
		// 微博只将热门微博一项作为唯一的主题，即种子页面
		if (this.site.getSeedUrl().contains("weibo")) {
			// 获得新浪微博的“热门微博”字段
			Document docSeed = HcLoginJsoupDocumentUtil.getDocument(
					this.site.getSeedUrl(), extraParame, "http://weibo.cn");
			themeName = docSeed.select("div.tip").first().ownText();
		}
		Theme onlyTheme = this.prodTheme(themeName, themeUrl, themeUrlMD5);
		themeSet.add(onlyTheme);

		return themeSet;
	}

	// 主题爬取方法接口，为调用此类的控制层方法提供
	public Set<Theme> execGrabTheme(Site site, GrabParame grabParame) {

		// 初始化数据
		setSite(site);
		setGrabParame(grabParame);

		// 在执行任务之前，首先检查任务结束状态位的情况，如果为true，提前结束，不执行任务
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		ExtraParameService extraParameService = (ExtraParameService) ApplicationContextFactory.appContext
				.getBean("extraParameService");
		setExtraParame(extraParameService.loadBySiteId(site.getSiteId()));

		Set<Theme> themes = this.getThemeUrlSet();
		if (themes == null) {
			// TODO 写入任务异常日志
		}

		// 获得爬取结果
		return themes;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
	}

	public ExtraParame getExtraParame() {
		return extraParame;
	}

	public void setExtraParame(ExtraParame extraParame) {
		this.extraParame = extraParame;
	}

}
