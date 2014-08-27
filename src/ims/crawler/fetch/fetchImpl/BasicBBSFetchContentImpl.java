package ims.crawler.fetch.fetchImpl;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.util.HandleLogResult;
import ims.site.model.FetchParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.PostService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BasicBBSFetchContentImpl {

	private static PostService postService = (PostService) ApplicationContextFactory.appContext
			.getBean("postService");

	// �������̻�����ʵ��
	private FetchParame fetchParame;
	// ��Ҫ�õ��û��Զ������ʵ��
	private GrabUserParame grabUserParame;
	private Site site;
	private Set<Theme> themes;
	private HandleLogResult handleLogResult;

	public Map<String, Object> execFetchContentThread() {

		// ׼�������ķ��ط���ֵ
		int fetchNum = 0;
		int fetchSuccNum = 0;

		// �����̳߳�
		ExecutorService exes = Executors.newSingleThreadExecutor();
		Set<Future<Map<String, Object>>> setThreads = new HashSet<Future<Map<String, Object>>>();

		for (Theme theme : getThemes()) {
			// ׼��Ҫ�����Ľڵ㼯�ϣ�������Ϊ��λ��ÿ���̸߳���һ�����⣩
			Map<String, Object> themeIdAndFetchableMaps = new HashMap<String, Object>();
			themeIdAndFetchableMaps.put("themeId", theme.getThemeId());
			themeIdAndFetchableMaps.put("fetchable", 1);
			Set<Post> posts = postService
					.listByThemeIdAndFetchable(themeIdAndFetchableMaps);

			// �����߳�����
			BasicBBSFetchContentThread taskThread = new BasicBBSFetchContentThread(
					getFetchParame(), getGrabUserParame(), getFetchParame()
							.getFetchPagerObj(), getSite(),
					getHandleLogResult().getTaskLog(), theme, posts);

			// �ж��������̱߳��ֶ�����������ǣ������ύ�µ�����
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// �ύ�߳�����
			setThreads.add(exes.submit(taskThread));
		}

		// ִ�ж��߳���������ÿ��վ������нڵ�����̫�󣬱�����ʱ�������ݿ��У�
		for (Future<Map<String, Object>> future : setThreads) {
			// ������������ӳ�����������߳����н��
			Map<String, Object> contentPartResultMap = new HashMap<String, Object>();

			// �ж��������̱߳��ֶ�����������ǣ����ٵȴ����������
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			try {
				// ����˵�����������ӳ��
				contentPartResultMap = future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// �������ֵ��null��ֱ��������һ��ѭ��
			if (contentPartResultMap == null) {
				continue;
			}

			// ͳ��վ�����������������
			fetchNum += (Integer) contentPartResultMap.get("fetchNum");
			fetchSuccNum += (Integer) contentPartResultMap.get("fetchSuccNum");

			// ��themeLog���ݿ���ʵʱд�����������ɹ��Ľڵ���
			this.handleLogResult.refreshThemeLogRealTime(contentPartResultMap);
			// ��siteLog���ݿ���ʵʱд���վ������Ľڵ������ͳɹ���
			this.handleLogResult
					.refreshSiteLogFetchRealTime(contentPartResultMap);
		}

		// �ж��������̱߳��ֶ�������
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// ���ؽ����ĳɹ�
		Map<String, Object> contentResultMap = new HashMap<String, Object>();
		contentResultMap.put("fetchNum", fetchNum);
		contentResultMap.put("fetchSuccNum", fetchSuccNum);

		return contentResultMap;
	}

	public Map<String, Object> execFetchContent(FetchParame fetchParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult) {

		// ��ʼ������
		setFetchParame(fetchParame);
		setGrabUserParame(grabUserParame);
		setSite(site);
		setThemes(themes);
		setHandleLogResult(handleLogResult);

		return this.execFetchContentThread();
	}

	public FetchParame getFetchParame() {
		return fetchParame;
	}

	public void setFetchParame(FetchParame fetchParame) {
		this.fetchParame = fetchParame;
	}

	public GrabUserParame getGrabUserParame() {
		return grabUserParame;
	}

	public void setGrabUserParame(GrabUserParame grabUserParame) {
		this.grabUserParame = grabUserParame;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Set<Theme> getThemes() {
		return themes;
	}

	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

}
