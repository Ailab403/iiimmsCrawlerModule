package ims.crawler.grab;

import ims.crawler.util.HandleLogResult;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

/**
 * ץȡ�ڵ���Ϣͨ�ýӿ�
 * 
 * @author superhy
 * 
 */
public interface GrabPostUrls {

	/**
	 * ִ�ж��߳�����ץȡÿ����������нڵ�
	 * 
	 * @return
	 */
	public Map<String, Set<Post>> execGrabPostUrlsThread();

	/**
	 * ������ȡ�����ӿڣ�Ϊ���ô���Ŀ��Ʋ㷽���ṩ
	 * 
	 * @param grabParame
	 * @param grabUserParame
	 * @param site
	 * @param themes
	 * @param handleLogResult
	 * @return
	 */
	public Map<String, Set<Post>> execGrabPost(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult);
}
