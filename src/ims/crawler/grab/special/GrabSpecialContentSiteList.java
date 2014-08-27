package ims.crawler.grab.special;

import ims.site.model.Site;

import java.util.HashSet;
import java.util.Set;

public class GrabSpecialContentSiteList {

	// �����ʱ��ƥ���ʽվ���б�
	public static Set<String> specialTimeSiteNickNames = new HashSet<String>();
	static {
		specialTimeSiteNickNames.add("qqluntan");
		specialTimeSiteNickNames.add("dayangshequ");

	}

	// ��������ƥ���ʽվ���б�
	public static Set<String> specialNumSiteNickNames = new HashSet<String>();
	static {
		// TODO �����������ƥ���վ��nickName
	}

	/**
	 * �ж�վ���Ƿ����������ʱ��ƥ���ʽ������
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
	 * �ж�վ���Ƿ���������������ƥ���ʽ������
	 * 
	 * @param site
	 * @return
	 */
	public static boolean isInSpecialNumSiteList(Site site) {

		return false;
	}
}
