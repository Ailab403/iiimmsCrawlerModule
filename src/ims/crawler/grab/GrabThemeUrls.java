package ims.crawler.grab;

import java.util.Set;

import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;

/**
 * ��ȡ������Ϣͨ�ýӿ�
 * 
 * @author superhy
 * 
 */
public interface GrabThemeUrls {

	/**
	 * ��װ�����������
	 * 
	 * @param themeName
	 * @param themeUrl
	 * @param themeUrlMD5
	 * @return
	 */
	public Theme prodTheme(String themeName, String themeUrl, String themeUrlMD5);

	/**
	 * ������ҳ���ȡ��վ����ȡ��������URL��Ҫʵ�ַ���
	 * 
	 * @return
	 */
	public Set<Theme> getThemeUrlSet();

	/**
	 * ������ȡ�����ӿڣ�Ϊ���ô���Ŀ��Ʋ㷽���ṩ
	 * 
	 * @param site
	 * @param grabParame
	 * @return
	 */
	public Set<Theme> execGrabTheme(Site site, GrabParame grabParame);

}
