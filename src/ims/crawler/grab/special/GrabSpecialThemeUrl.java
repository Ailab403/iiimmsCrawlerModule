package ims.crawler.grab.special;

public class GrabSpecialThemeUrl {

	public static String getSpecialThemeUrl(String themeUrl) {
		String specialThemeUrl = themeUrl;

		// ����è������themeUrl�������
		if (themeUrl.contains("mop")) {
			specialThemeUrl = themeUrl + "?replytime=1";
		}

		return specialThemeUrl;
	}
}
