package ims.crawler.fetch;

import ims.crawler.util.HandleLogResult;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

/**
 * �����ڵ���Ϣͨ�ýӿ�
 * 
 * @author superhy
 * 
 */
public interface FetchContent {

	/**
	 * ִ�ж��߳��������ÿ����������нڵ�
	 * 
	 * @return
	 */
	public Map<String, Object> execFetchContentThread();

	/**
	 * ���ӽ��������ӿڣ�Ϊ���ô���Ŀ��Ʋ㷽���ṩ
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
