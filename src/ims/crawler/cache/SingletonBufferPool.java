package ims.crawler.cache;

import ims.site.model.Post;
import ims.site.service.PostService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author fxp
 * 
 */
public class SingletonBufferPool {

	private static PostService postService = (PostService) ApplicationContextFactory.appContext
			.getBean("postService");

	private static SingletonBufferPool singletonBufferPool; // 类变量
	private Map<String, Map<String, Object>> postBufferPool; // 缓冲池(私有变量)

	// 私有构造
	private SingletonBufferPool() {
		super();
	}

	// 获取类变量
	public static SingletonBufferPool getSingletonBufferPool() {
		if (singletonBufferPool == null) {
			singletonBufferPool = new SingletonBufferPool();
		}
		return singletonBufferPool;
	}

	// 获取缓冲池
	public Map<String, Map<String, Object>> getPostBufferPool() {
		if (postBufferPool == null)
			postBufferPool = new HashMap<String, Map<String, Object>>();
		if (postBufferPool.isEmpty()) {
			constructBuffer(); // 初始化缓冲池
		}
		return postBufferPool;
	}

	// 初始化缓冲池 --- 线程安全，同一时刻只能有一个线程可以访问该方法
	public synchronized boolean constructBuffer() {
		try {
			// 从数据库获取所有post信息
			System.out.println("正在获得所有节点信息，请稍等...");
			Set<Post> posts = postService.listAll();

			// 将post信息设置入类变量
			for (Post post : posts) {

				// TODO delete print
				System.out.println("正在初始化缓存池：" + post.toString());

				Map<String, Object> postParames = new HashMap<String, Object>();
				postParames.put("clickNum", post.getClickNum());
				postParames.put("replyNum", post.getReplyNum());
				postParames.put("forwardNum", post.getForwardNum());
				postParames.put("lastReplyTime", post.getLastReplyTime());
				postBufferPool.put(post.getPostUrlMD5(), postParames);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 检查是否在缓冲池中
	public boolean checkInBuffer(String postUrlMD5) {
		if (getPostBufferPool().containsKey(postUrlMD5))
			return true;
		return false;
	}

	// 获取缓冲池中指定对象
	public Map<String, Object> getPostParames(String postUrlMD5) {
		return getPostBufferPool().get(postUrlMD5);
	}

	// 替换缓冲池中指定对象
	public void replacePostParames(String keyMD5, Post post) {

		// 删除原有对象
		postBufferPool.remove(keyMD5);

		Map<String, Object> postParames = new HashMap<String, Object>();
		postParames.put("clickNum", post.getClickNum());
		postParames.put("replyNum", post.getReplyNum());
		postParames.put("forwardNum", post.getForwardNum());
		postParames.put("lastReplyTime", post.getLastReplyTime());

		postBufferPool.put(keyMD5, postParames);
	}

	// 想缓冲池中加入新对象
	public void addNewPostParames(String keyMD5, Post post) {

		Map<String, Object> postParames = new HashMap<String, Object>();
		postParames.put("clickNum", post.getClickNum());
		postParames.put("replyNum", post.getReplyNum());
		postParames.put("forwardNum", post.getForwardNum());
		postParames.put("lastReplyTime", post.getLastReplyTime());

		postBufferPool.put(keyMD5, postParames);
	}

	// 清空缓存池
	public synchronized void clearBufferPool() {
		if (this.postBufferPool != null) {
			this.postBufferPool.clear();
		}
	}
}
