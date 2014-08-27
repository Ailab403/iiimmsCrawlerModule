package ims.crawler.exec;

import ims.crawlerLog.model.SiteLog;
import ims.site.model.FetchParame;
import ims.site.model.GrabParame;
import ims.site.model.Post;
import ims.site.model.Site;
import ims.site.model.Tool;
import ims.site.service.ExtraParameService;
import ims.site.service.FetchParameService;
import ims.site.service.GrabParameService;
import ims.site.service.GrabUserParameService;
import ims.site.service.PostService;
import ims.site.service.SiteService;
import ims.site.service.ToolService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author superhy
 * 
 */
public class ExecCheck {

	/*
	 * ����Ԥ���෽����������ƣ� ����˳�� 1.����������� 2.������ݿ����� 3.���������� 4.������������
	 * 5.���ܼ���������ظ�������ϵ
	 */

	// ��springע���service����
	private SiteService siteService;
	private GrabParameService grabParameService;
	private GrabUserParameService grabUserParameService;
	private FetchParameService fetchParameService;
	private ExtraParameService extraParameService;
	private PostService postService;
	private ToolService toolService;

	// ���ִ������Ҫ�Ĳ���
	private Set<Post> posts;
	private GrabParame grabParame;
	private FetchParame fetchParame;
	private Tool tool;

	public void initCheckParames(Site site) {
		setGrabParame(this.grabParameService.loadBySiteId(site.getSiteId()));
		setFetchParame(this.fetchParameService.loadBySiteId(site.getSiteId()));
		setPosts(this.postService.listBySiteId(site.getSiteId()));
		setTool(site.getTool());
	}

	/**
	 * ����ִ���������Ӽ��
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkNetConnection(Site site) {
		// ׼�����ķ���ֵ
		Map<String, Object> checkNetResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckNet = Class.forName("ims.crawler.check."
					+ this.tool.getCheckNetObj());
			Method methodCheckNet = classCheckNet.getMethod("execCheck",
					String.class);
			checkNetResMap = (Map<String, Object>) methodCheckNet.invoke(
					classCheckNet.newInstance(), site.getSeedUrl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checkNetResMap;
	}

	/**
	 * ����ִ�����ݿ����Ӽ��
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkDbConnection(Site site) {
		// ׼�����ķ���ֵ
		Map<String, Object> checkDbResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckDb = Class.forName("ims.crawler.check."
					+ this.tool.getCheckDatabaseObj());
			Method methodCheckDb = classCheckDb.getMethod("execCheck",
					Integer.class, SiteService.class, GrabParameService.class,
					GrabUserParameService.class, FetchParameService.class,
					ExtraParameService.class, ToolService.class);
			checkDbResMap = (Map<String, Object>) methodCheckDb.invoke(
					classCheckDb.newInstance(), site.getSiteId(),
					this.siteService, this.grabParameService,
					this.grabUserParameService, this.fetchParameService,
					this.extraParameService, this.toolService);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkDbResMap;
	}

	/**
	 * ����ִ������̻��������
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkGrabParame(Site site) {
		// ׼�����ķ���ֵ
		Map<String, Object> checkGrabParameResMap = new HashMap<String, Object>();
		try {
			Class<?> classCheckGrabParame = Class.forName("ims.crawler.check."
					+ this.tool.getCheckGrabParameObj());
			Method methodCheckGrabParame = classCheckGrabParame.getMethod(
					"execCheck", Site.class, GrabParame.class);

			checkGrabParameResMap = (Map<String, Object>) methodCheckGrabParame
					.invoke(classCheckGrabParame.newInstance(), site,
							this.grabParame);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkGrabParameResMap;
	}

	/**
	 * ����ִ�н����̻��������
	 * 
	 * @param site
	 * @return
	 */
	public Map<String, Object> checkFetchParame(Site site) {
		// ׼�����ķ���ֵ
		Map<String, Object> checkFetchParameResMap = new HashMap<String, Object>();
		// �ж����ݿ������޶�Ӧ��post�ڵ���Թ����������û�У����ش�����Ϣ����ʾ�Ƚ���Ԥ��ȡ����
		if (this.posts == null) {
			checkFetchParameResMap.put("checkRes", 0);
			checkFetchParameResMap.put("errorDescribe", "û�пɽ����Ľڵ�");
			return checkFetchParameResMap;
		}

		try {
			// ȡ��һ��testPost
			Iterator<Post> iterator = posts.iterator();
			Post testPost = iterator.next();

			Class<?> classCheckFetchParame = Class.forName("ims.crawler.check."
					+ this.tool.getCheckFetchParameObj());
			Method methodCheckFetchParame = classCheckFetchParame.getMethod(
					"execCheck", Post.class, FetchParame.class);
			checkFetchParameResMap = (Map<String, Object>) methodCheckFetchParame
					.invoke(classCheckFetchParame.newInstance(), posts,
							this.fetchParame);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return checkFetchParameResMap;
	}

	public synchronized Map<String, Object> execCheckMethod(Site site, SiteLog siteLog) {

		/*
		 * �����fetch���񣬼���Ƿ��Ѿ����غö�Ӧ��post�ڵ㣬�����޷����У�����tasklogʵ�壬
		 * taskLog����ϵ�ǰִ��״̬status�ֶ� ��0��ʾ����1��ʾ����
		 */

		// ��ʼ��������
		initCheckParames(site);

		// ׼�����صķ�������
		Map<String, Object> checkFeedBackMap = new HashMap<String, Object>();
		checkFeedBackMap.put("checkRes", 1);
		checkFeedBackMap.put("errorDescribe", "��δ��ʼ���");
		try {
			// ����վ�������ִ��״̬���б�Ҫ�ļ��
			int siteStatus = siteLog.getSiteStatus();

			// ִ���������Ӽ��
			checkFeedBackMap = checkNetConnection(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("���������");

			// ִ�����ݿ����Ӽ��
			checkFeedBackMap = checkDbConnection(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("������ݿ���");

			// ִ����ȡ�̻��������
			checkFeedBackMap = checkGrabParame(site);
			if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
				return checkFeedBackMap;
			}
			// TODO delete print
			System.out.println("�������̻��������");

			if (siteStatus == 2) {
				checkFeedBackMap = checkFetchParame(site);
				if ((Integer) checkFeedBackMap.get("checkRes") == 0) {
					return checkFeedBackMap;
				}
			}
			// TODO delete print
			System.out.println("��ɽ������̻��������");
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		if ((Integer) checkFeedBackMap.get("checkRes") == 1) {
			checkFeedBackMap.remove("errorDescribe");
			checkFeedBackMap.put("errorDescribe", "���δ���ִ���");

			return checkFeedBackMap;
		} else {
			checkFeedBackMap.remove("errorDescribe");
			checkFeedBackMap.put("errorDescribe", "����δ֪����");

			return checkFeedBackMap;
		}
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public GrabParameService getGrabParameService() {
		return grabParameService;
	}

	public void setGrabParameService(GrabParameService grabParameService) {
		this.grabParameService = grabParameService;
	}

	public GrabUserParameService getGrabUserParameService() {
		return grabUserParameService;
	}

	public void setGrabUserParameService(
			GrabUserParameService grabUserParameService) {
		this.grabUserParameService = grabUserParameService;
	}

	public FetchParameService getFetchParameService() {
		return fetchParameService;
	}

	public void setFetchParameService(FetchParameService fetchParameService) {
		this.fetchParameService = fetchParameService;
	}

	public ExtraParameService getExtraParameService() {
		return extraParameService;
	}

	public void setExtraParameService(ExtraParameService extraParameService) {
		this.extraParameService = extraParameService;
	}

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public ToolService getToolService() {
		return toolService;
	}

	public void setToolService(ToolService toolService) {
		this.toolService = toolService;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
	}

	public FetchParame getFetchParame() {
		return fetchParame;
	}

	public void setFetchParame(FetchParame fetchParame) {
		this.fetchParame = fetchParame;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

}
