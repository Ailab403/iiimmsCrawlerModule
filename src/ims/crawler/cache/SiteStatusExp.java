package ims.crawler.cache;

public class SiteStatusExp {

	// 分任务日志对应各个状态位的说明，编写成静态成员，持久化
	public static String siteStatusExp[] = new String[5];

	static {
		siteStatusExp[0] = "分任务尚未开始";
		siteStatusExp[1] = "分任务仅完成主题爬取";
		siteStatusExp[2] = "分任务仅完成节点爬取";
		siteStatusExp[3] = "分任务完成全部解析";
	}
}
