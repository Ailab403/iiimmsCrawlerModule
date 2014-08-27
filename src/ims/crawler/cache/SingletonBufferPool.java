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

	private static SingletonBufferPool singletonBufferPool; // �����
	private Map<String, Map<String, Object>> postBufferPool; // �����(˽�б���)

	// ˽�й���
	private SingletonBufferPool() {
		super();
	}

	// ��ȡ�����
	public static SingletonBufferPool getSingletonBufferPool() {
		if (singletonBufferPool == null) {
			singletonBufferPool = new SingletonBufferPool();
		}
		return singletonBufferPool;
	}

	// ��ȡ�����
	public Map<String, Map<String, Object>> getPostBufferPool() {
		if (postBufferPool == null)
			postBufferPool = new HashMap<String, Map<String, Object>>();
		if (postBufferPool.isEmpty()) {
			constructBuffer(); // ��ʼ�������
		}
		return postBufferPool;
	}

	// ��ʼ������� --- �̰߳�ȫ��ͬһʱ��ֻ����һ���߳̿��Է��ʸ÷���
	public synchronized boolean constructBuffer() {
		try {
			// �����ݿ��ȡ����post��Ϣ
			System.out.println("���ڻ�����нڵ���Ϣ�����Ե�...");
			Set<Post> posts = postService.listAll();

			// ��post��Ϣ�����������
			for (Post post : posts) {

				// TODO delete print
				System.out.println("���ڳ�ʼ������أ�" + post.toString());

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

	// ����Ƿ��ڻ������
	public boolean checkInBuffer(String postUrlMD5) {
		if (getPostBufferPool().containsKey(postUrlMD5))
			return true;
		return false;
	}

	// ��ȡ�������ָ������
	public Map<String, Object> getPostParames(String postUrlMD5) {
		return getPostBufferPool().get(postUrlMD5);
	}

	// �滻�������ָ������
	public void replacePostParames(String keyMD5, Post post) {

		// ɾ��ԭ�ж���
		postBufferPool.remove(keyMD5);

		Map<String, Object> postParames = new HashMap<String, Object>();
		postParames.put("clickNum", post.getClickNum());
		postParames.put("replyNum", post.getReplyNum());
		postParames.put("forwardNum", post.getForwardNum());
		postParames.put("lastReplyTime", post.getLastReplyTime());

		postBufferPool.put(keyMD5, postParames);
	}

	// �뻺����м����¶���
	public void addNewPostParames(String keyMD5, Post post) {

		Map<String, Object> postParames = new HashMap<String, Object>();
		postParames.put("clickNum", post.getClickNum());
		postParames.put("replyNum", post.getReplyNum());
		postParames.put("forwardNum", post.getForwardNum());
		postParames.put("lastReplyTime", post.getLastReplyTime());

		postBufferPool.put(keyMD5, postParames);
	}

	// ��ջ����
	public synchronized void clearBufferPool() {
		if (this.postBufferPool != null) {
			this.postBufferPool.clear();
		}
	}
}
