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

	// 解析器固话参数实体
	private FetchParame fetchParame;
	// 需要用到用户自定义参数实体
	private GrabUserParame grabUserParame;
	private Site site;
	private Set<Theme> themes;
	private HandleLogResult handleLogResult;

	public Map<String, Object> execFetchContentThread() {

		// 准备解析的返回反馈值
		int fetchNum = 0;
		int fetchSuccNum = 0;

		// 创建线程池
		ExecutorService exes = Executors.newSingleThreadExecutor();
		Set<Future<Map<String, Object>>> setThreads = new HashSet<Future<Map<String, Object>>>();

		for (Theme theme : getThemes()) {
			// 准备要解析的节点集合（以主题为单位，每个线程负责一个主题）
			Map<String, Object> themeIdAndFetchableMaps = new HashMap<String, Object>();
			themeIdAndFetchableMaps.put("themeId", theme.getThemeId());
			themeIdAndFetchableMaps.put("fetchable", 1);
			Set<Post> posts = postService
					.listByThemeIdAndFetchable(themeIdAndFetchableMaps);

			// 创建线程任务
			BasicBBSFetchContentThread taskThread = new BasicBBSFetchContentThread(
					getFetchParame(), getGrabUserParame(), getFetchParame()
							.getFetchPagerObj(), getSite(),
					getHandleLogResult().getTaskLog(), theme, posts);

			// 判断是由于线程被手动结束否，如果是，则不在提交新的任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// 提交线程任务
			setThreads.add(exes.submit(taskThread));
		}

		// 执行多线程任务（由于每个站点的所有节点内容太大，必须随时存入数据库中）
		for (Future<Map<String, Object>> future : setThreads) {
			// 建立帖子内容映射容器接受线程运行结果
			Map<String, Object> contentPartResultMap = new HashMap<String, Object>();

			// 判断是由于线程被手动结束否，如果是，则不再等待后面的任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			try {
				// 获得了单个帖子内容映射
				contentPartResultMap = future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 如果返回值是null，直接跳入下一次循环
			if (contentPartResultMap == null) {
				continue;
			}

			// 统计站点解析任务结果的总数
			fetchNum += (Integer) contentPartResultMap.get("fetchNum");
			fetchSuccNum += (Integer) contentPartResultMap.get("fetchSuccNum");

			// 向themeLog数据库中实时写入该主题解析成功的节点数
			this.handleLogResult.refreshThemeLogRealTime(contentPartResultMap);
			// 向siteLog数据库中实时写入该站点解析的节点总数和成功数
			this.handleLogResult
					.refreshSiteLogFetchRealTime(contentPartResultMap);
		}

		// 判断是由于线程被手动结束否
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// 返回解析的成果
		Map<String, Object> contentResultMap = new HashMap<String, Object>();
		contentResultMap.put("fetchNum", fetchNum);
		contentResultMap.put("fetchSuccNum", fetchSuccNum);

		return contentResultMap;
	}

	public Map<String, Object> execFetchContent(FetchParame fetchParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult) {

		// 初始化数据
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
