package ims.crawler.grab.special;

public class GrabSpecialThemeUrl {

	public static String getSpecialThemeUrl(String themeUrl) {
		String specialThemeUrl = themeUrl;

		// 处理猫扑贴贴themeUrl特殊情况
		if (themeUrl.contains("mop")) {
			specialThemeUrl = themeUrl + "?replytime=1";
		}

		return specialThemeUrl;
	}
}
