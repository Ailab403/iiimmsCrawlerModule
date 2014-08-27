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
	// ����̻���������ʵ��
	private GrabParame grabParame;
	// �û��Զ����������ʵ��
	private GrabUserParame grabUserParame;

	// ��վ��Ϣ��ʵ��
	private Site site;
	// ����ȡ������ʵ�弯��
	private Set<Theme> themeSet;
	// ��Ҫ�����ض�����־�����������־��������Ψһ��taskLog��
	private HandleLogResult handleLogResult;

	// �����������¼��Ϣ
	private ExtraParame extraParame;

	public Map<String, Set<Post>> execGrabPostUrlsThread() {

		// ׼�����ص�����
		Set<Post> allAddPosts = new HashSet<Post>();
		Set<Post> allUpdatePosts = new HashSet<Post>();
		Set<Post> allFixPosts = new HashSet<Post>();
		Map<String, Set<Post>> allPostResultMap = new HashMap<String, Set<Post>>();

		// ��ʼ�������
		SingletonBufferPool singletonBufferPool = SingletonBufferPool
				.getSingletonBufferPool();
		singletonBufferPool.getPostBufferPool();

		// �����̳߳�
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Map<String, Object>>> setThreads = new HashSet<Future<Map<String, Object>>>();
		for (Theme theme : getThemeSet()) {
			// �����߳�����
			BsLoginBaGrabPostUrlsThread taskThread = new BsLoginBaGrabPostUrlsThread(
					getGrabParame(), getGrabUserParame(), getSite(), theme,
					getExtraParame());

			// �ж��������̱߳��ֶ�����������ǣ������ύ�µ�����
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			// �ύ�߳�����
			setThreads.add(exes.submit(taskThread));
		}

		// ִ�ж��߳�
		for (Future<Map<String, Object>> future : setThreads) {
			// ����������Ϣ���Ͻ����߳����н��
			Map<String, Object> eachThemePostResultMap = new HashMap<String, Object>();

			// �ж��������̱߳��ֶ�����������ǣ����ٵȴ����������
			if (ThreadEndFlag.isThreadEndFlag()) {
				return null;
			}

			eachThemePostResultMap = null;
			try {
				eachThemePostResultMap = future.get();
			} catch (InterruptedException e) {
				// TODO д�������쳣��־
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO д�������쳣��־
				e.printStackTrace();
			}

			// �����̵߳��ô���������ֱ��������һ��ѭ��
			if (eachThemePostResultMap == null) {
				continue;
			}

			// ��ÿ����������ӽ�������뵽�����ӽ����

			int addNum = 0;
			int updateNum = 0;
			int fixNum = 0;

			// ����ӽڵ�ʵ������жϣ��������ʱ�䷶Χ�������ڣ������뼯��
			for (Post post : (Set<Post>) eachThemePostResultMap.get("add")) {
				// TODO �����жϼ��϶�ʱ�䷶Χ�������ڵ��жϣ���ֲ��fetch��reply�����׶�
				allAddPosts.add(post);
				addNum++;
			}
			// ��ˢ�½ڵ㼯�Ͻ����жϣ����������Χ�������ڣ������뼯��
			for (Post post : (Set<Post>) eachThemePostResultMap.get("update")) {
				// TODO �����жϼ��϶�ʱ�䷶Χ�������ڵ��жϣ���ֲ��fetch��reply�����׶�
				allUpdatePosts.add(post);
				updateNum++;
			}
			// ���޸������޸Ľڵ㼯�Ͻ����жϣ����������Χ�������ڣ������뼯��
			for (Post post : (Set<Post>) eachThemePostResultMap.get("fix")) {
				// TODO �����жϼ��϶�ʱ�䷶Χ�������ڵ��жϣ���ֲ��fetch��reply�����׶�
				allFixPosts.add(post);
				fixNum++;
			}

			// ��ӳ�伯���в����������϶�Ӧ��������
			eachThemePostResultMap.put("addNum", addNum);
			eachThemePostResultMap.put("updateNum", updateNum);
			eachThemePostResultMap.put("fixNum", fixNum);

			// �����ⷴ����־���м���ͳ������
			this.handleLogResult.addNewThemeLogRealTime(eachThemePostResultMap);
		}

		// �ж��������̱߳��ֶ�������
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		// ���»����
		for (Post post : allAddPosts) {
			singletonBufferPool.addNewPostParames(post.getPostUrlMD5(), post);
		}
		for (Post post : allUpdatePosts) {
			singletonBufferPool.replacePostParames(post.getPostUrlMD5(), post);
		}
		for (Post post : allFixPosts) {
			singletonBufferPool.replacePostParames(post.getPostUrlMD5(), post);
		}

		// ������
		if (allAddPosts.size() != 0 || allUpdatePosts.size() != 0
				|| allFixPosts.size() != 0) {

			// ��������������Ҫ��һ����Ϊ�ղŽ��в���
			allPostResultMap.put("add", allAddPosts);
			allPostResultMap.put("update", allUpdatePosts);
			allPostResultMap.put("fix", allFixPosts);
		}

		return allPostResultMap;
	}

	// ������ȡ�����ӿڣ�Ϊ���ô���Ŀ��Ʋ㷽���ṩ
	public Map<String, Set<Post>> execGrabPost(GrabParame grabParame,
			GrabUserParame grabUserParame, Site site, Set<Theme> themes,
			HandleLogResult handleLogResult) {

		// ��ʼ������
		setGrabParame(grabParame);
		setGrabUserParame(grabUserParame);
		setSite(site);
		setThemeSet(themes);
		setHandleLogResult(handleLogResult);

		ExtraParameService extraParameService = (ExtraParameService) ApplicationContextFactory.appContext
				.getBean("extraParameService");
		setExtraParame(extraParameService.loadBySiteId(site.getSiteId()));

		// �����ȡ���
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
