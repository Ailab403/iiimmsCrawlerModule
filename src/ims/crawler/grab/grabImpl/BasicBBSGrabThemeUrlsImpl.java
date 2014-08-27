package ims.crawler.grab.grabImpl;

import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.GrabThemeUrls;
import ims.crawler.grab.special.GrabSpecialThemeUrl;
import ims.crawler.grab.util.TransMD5;
import ims.crawler.util.BasicJsoupDocumentUtil;
import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class BasicBBSGrabThemeUrlsImpl implements GrabThemeUrls {

	// 种子站点实体
	private Site site;
	// 爬虫固化技术参数实体
	private GrabParame grabParame;

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

		/*
		 * 如果出现异常情况，种子页面为空值，那么主题链接获取不成功，返回空值
		 */
		if ((site.getSeedUrl() == null || site.getSeedUrl().equals(""))) {
			return null;
		}

		// 设置主题URL的集合，数据结构为hash表，单独配有MD5验证，重写equel和hash方法，防止发生重复
		Set<Theme> themeSet = new HashSet<Theme>();
		// 从种子页面载入html代码document
		Document docSeedPage = BasicJsoupDocumentUtil.getDocument(site
				.getSeedUrl());
		if (docSeedPage == null) {
			return null;
		}

		// 获取主题div元素
		Element eleThemeListDiv = docSeedPage.select(
				grabParame.getThemeListDivQuery()).first();
		// 处理主题div元素获取失败的情况
		if (eleThemeListDiv == null) {
			return null;
		}

		// 获取每个主题连接元素的集合
		Elements elesSingleTheme = eleThemeListDiv.select(grabParame
				.getThemeDivQuery());
		// 处理主题连接元素获取失败的情况
		if (elesSingleTheme.size() == 0) {
			return null;
		}

		for (Element eleEachTheme : elesSingleTheme) {
			// 获取每个主题名称
			String themeName = eleEachTheme.select(grabParame
					.getThemeNameQuery()) != null ? eleEachTheme.select(
					grabParame.getThemeNameQuery()).first().text() : "unknow";
			// 获取每个主题的链接
			String themeUrl = eleEachTheme
					.select(grabParame.getThemeUrlQuery()) != null ? eleEachTheme
					.select(grabParame.getThemeUrlQuery()).first().attr(
							"abs:href")
					: "unknow";
			if (themeUrl.equals("unknow")) {
				continue;
			}
			// 处理特殊themeUrl
			themeUrl = GrabSpecialThemeUrl.getSpecialThemeUrl(themeUrl);

			// 为每个主题制作唯一的MD5验证码
			String themeUrlMD5 = transMD5Obj.getMD5Code(themeUrl);

			// 制作theme实体
			Theme theme = prodTheme(themeName, themeUrl, themeUrlMD5);

			// TODO delete print
			System.out.println(theme.toString());

			themeSet.add(theme);
		}

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
}
