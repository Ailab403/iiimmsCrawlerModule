package ims.crawler.grab.grabImpl;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.cache.SingletonBufferPool;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.GrabPostUrls;
import ims.crawler.util.HandleLogResult;
import ims.site.model.ExtraParame;
import ims.site.model.GrabParame;
import ims.site.model.GrabUserParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.ExtraParameService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author superhy
 * 
 */
public class BsLoginBaGrabPostUrlsImpl implements GrabPostUrls {
	// 爬虫固化技术参数实体
	private GrabParame grabParame;
	// 用户自定义爬虫参数实体
	private GrabUserParame grabUserParame;

	// 网站信息类实体
	private Site site;
	// 需爬取的主题实体集合
	private Set<Theme> themeSet;
	// 需要传入特定的日志处理类进行日志处理（传入唯一的taskLog）
	private HandleLogResult handleLogResult;

	// 额外参数，登录信息
	private ExtraParame extraParame;

	public Map<String, Set<Post>> execGrabPostUrlsThread() {

		// 准备返回的数据
		Set<Post> allAddPosts = new HashSet<Post>();
		Set<Post> allUpdatePosts = new HashSet<Post>();
		Set<Post> allFixPosts = new HashSet<Post>();
		Map<String, Set<Post>> allPostResultMap = new HashMap<String, Set<Post>>();

		// 初始化缓存池
		SingletonBufferPool singletonBufferPool = SingletonBufferPool
				.getSingletonBufferPool();
		singletonBufferPool.getPostBufferPool();

		// 创建线程池
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Map<String, Object>>> setThreads = new HashSet<Future<Map<String, Object>>>();
		for (Theme theme : getThemeSet()) {
			// 创建线程任务
			BsLoginBaGrabPostUrlsThread taskThread = new BsLoginBaGrabPostUrlsThread(
					getGrabParame(), getGrabUserParame(), getSite(), theme,
					getExtraParame());

			// 判断是由于线程被手动结束否，如果是，则不在提交新的任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// 提交线程任务
			setThreads.add(exes.submit(taskThread));
		}

		// 执行多线程
		for (Future<Map<String, Object>> future : setThreads) {
			// 建立帖子信息集合接受线程运行结果
			Map<String, Object> eachThemePostResultMap = new HashMap<String, Object>();

			// 判断是由于线程被手动结束否，如果是，则不再等待后面的任务
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			eachThemePostResultMap = null;
			try {
				eachThemePostResultMap = future.get();
			} catch (InterruptedException e) {
				// TODO 写入任务异常日志
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO 写入任务异常日志
				e.printStackTrace();
			}

			// 处理线程调用错误的情况，直接跳入下一层循环
			if (eachThemePostResultMap == null) {
				continue;
			}

			// 将每个主题的帖子结果集加入到总帖子结果集

			int addNum = 0;
			int updateNum = 0;
			int fixNum = 0;

			// 对添加节点实体进行判断，如果超过时间范围结束日期，不加入集合
			for (Post post : (Set<Post>) eachThemePostResultMap.get("add")) {
				// TODO 上面判断加上对时间范围结束日期的判断，移植到fetch的reply解析阶段
				allAddPosts.add(post);
				addNum++;
			}
			// 对刷新节点集合进行判断，如果超过范围结束日期，不加入集合
			for (Post post : (Set<Post>) eachThemePostResultMap.get("update")) {
				// TODO 上面判断加上对时间范围结束日期的判断，移植到fetch的reply解析阶段
				allUpdatePosts.add(post);
				updateNum++;
			}
			// 对修复部分修改节点集合进行判断，如果超过范围结束日期，不加入集合
			for (Post post : (Set<Post>) eachThemePostResultMap.get("fix")) {
				// TODO 上面判断加上对时间范围结束日期的判断，移植到fetch的reply解析阶段
				allFixPosts.add(post);
				fixNum++;
			}

			// 向映射集合中插入三个集合对应的数据量
			eachThemePostResultMap.put("addNum", addNum);
			eachThemePostResultMap.put("updateNum", updateNum);
			eachThemePostResultMap.put("fixNum", fixNum);

			// 向主题反馈日志表中加入统计数据
			this.handleLogResult.addNewThemeLogRealTime(eachThemePostResultMap);
		}

		// 判断是由于线程被手动结束否
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// 更新缓存池
		for (Post post : allAddPosts) {
			singletonBufferPool.addNewPostParames(post.getPostUrlMD5(), post);
		}
		for (Post post : allUpdatePosts) {
			singletonBufferPool.replacePostParames(post.getPostUrlMD5(), post);
		}
		for (Post post : allFixPosts) {
			singletonBufferPool.replacePostParames(post.getPostUrlMD5(), post);
		}

		// 载入结果
		if (allAddPosts.size() != 0 || allUpdatePosts.size() != 0
				|| allFixPosts.size() != 0) {

			// 三个集合中至少要有一个不为空才进行插入
			allPostResultMap.put("add", allAddPosts);
			allPostResultMap.put("update", allUpdatePosts);
			allPostResultMap.put("fix", allFixPosts);
		}

		return allPostResultMap;
	}

	// 帖子爬取方法接口，为调用此类的控制层方法提供
	public Map<String, Set<Post>> execGrabPost(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult) {

		// 初始化数据
		setGrabParame(grabParame);
		setGrabUserParame(grabUserParame);
		setSite(site);
		setThemeSet(themes);
		setHandleLogResult(handleLogResult);

		ExtraParameService extraParameService = (ExtraParameService) ApplicationContextFactory.appContext
				.getBean("extraParameService");
		setExtraParame(extraParameService.loadBySiteId(site.getSiteId()));

		// 获得爬取结果
		return this.execGrabPostUrlsThread();
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
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

	public Set<Theme> getThemeSet() {
		return themeSet;
	}

	public void setThemeSet(Set<Theme> themeSet) {
		this.themeSet = themeSet;
	}

	public ExtraParame getExtraParame() {
		return extraParame;
	}

	public void setExtraParame(ExtraParame extraParame) {
		this.extraParame = extraParame;
	}

	public HandleLogResult getHandleLogResult() {
		return handleLogResult;
	}

	public void setHandleLogResult(HandleLogResult handleLogResult) {
		this.handleLogResult = handleLogResult;
	}

}
