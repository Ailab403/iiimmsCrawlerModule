package ims.crawler.grab.grabImpl;

import ims.crawler.cache.ApplicationContextFactory;
import ims.crawler.cache.ThreadEndFlag;
import ims.crawler.grab.GrabThemeUrls;
import ims.crawler.grab.special.GrabSpecialThemeUrl;
import ims.crawler.grab.util.TransMD5;
import ims.crawler.util.BsLoginJsoupDocumentUtil;
import ims.site.model.ExtraParame;
import ims.site.model.GrabParame;
import ims.site.model.Site;
import ims.site.model.Theme;
import ims.site.service.ExtraParameService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class BsLoginBaGrabThemeUrlsImpl implements GrabThemeUrls {

	// �������Ӳ���ʵ��
	private Site site;
	// ����̻���������ʵ��
	private GrabParame grabParame;
	// �����������¼��Ϣ
	private ExtraParame extraParame;

	// MD5ת��������
	private static TransMD5 transMD5Obj = new TransMD5();

	/*
	 * ���ֲ��������ι淶���ȴ�����÷����Ĳ������ٴ��̻����������� ����û��Զ������ò���������ʵ����ǰ��˳��һ�δ���
	 */

	// ��װ�����������
	public Theme prodTheme(String themeName, String themeUrl, String themeUrlMD5) {

		// ������Ҫ��ȡĬ��Ϊ1
		int grabable = 1;
		// ���ò�����Ϣ�����ȶȳ�ʼֵΪ0
		int hotNum = 0;
		// ���ø���ʱ��Ϊ��ʱ�����ʱ��
		String refeshTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());

		String themeExp = this.site.getSiteName() + "�е�" + themeName + "����";

		return new Theme(this.site.getSiteId(), themeName, themeUrl,
				themeUrlMD5, grabable, hotNum, Timestamp.valueOf(refeshTime),
				themeExp, site);
	}

	/**
	 * ��һ�㣺������ҳ���ȡ��վ����ȡ��������URL��Ϊ������һ����׼��
	 */
	public Set<Theme> getThemeUrlSet() {

		/*
		 * ��������쳣���������ҳ��Ϊ��ֵ����ô�������ӻ�ȡ���ɹ������ؿ�ֵ
		 */
		if ((site.getSeedUrl() == null || site.getSeedUrl().equals(""))) {
			return null;
		}

		// ��������URL�ļ��ϣ����ݽṹΪhash������������MD5��֤����дequel��hash��������ֹ�����ظ�
		Set<Theme> themeSet = new HashSet<Theme>();
		// ������ҳ��ʹ��HttpClient��ʽ����html����document
		Document docSeedPage = BsLoginJsoupDocumentUtil.getDocument(site
				.getSeedUrl(), getExtraParame(), site.getSeedUrl(), site
				.getEnCode());
		if (docSeedPage == null) {
			return null;
		}

		// ��ȡ����divԪ��
		Element eleThemeListDiv = docSeedPage.select(
				grabParame.getThemeListDivQuery()).first();
		// ��������divԪ�ػ�ȡʧ�ܵ����
		if (eleThemeListDiv == null) {
			return null;
		}

		// ��ȡÿ����������Ԫ�صļ���
		Elements elesSingleTheme = eleThemeListDiv.select(grabParame
				.getThemeDivQuery());
		// ������������Ԫ�ػ�ȡʧ�ܵ����
		if (elesSingleTheme.size() == 0) {
			return null;
		}

		for (Element eleEachTheme : elesSingleTheme) {
			// ��ȡÿ����������
			String themeName = eleEachTheme.select(grabParame
					.getThemeNameQuery()) != null ? eleEachTheme.select(
					grabParame.getThemeNameQuery()).first().text() : "unknow";
			// ��ȡÿ�����������
			String themeUrl = eleEachTheme
					.select(grabParame.getThemeUrlQuery()) != null ? eleEachTheme
					.select(grabParame.getThemeUrlQuery()).first().attr(
							"abs:href")
					: "unknow";
			if (themeUrl.equals("unknow")) {
				continue;
			}
			// ��������themeUrl
			themeUrl = GrabSpecialThemeUrl.getSpecialThemeUrl(themeUrl);

			// Ϊÿ����������Ψһ��MD5��֤��
			String themeUrlMD5 = transMD5Obj.getMD5Code(themeUrl);

			// ����themeʵ��
			Theme theme = prodTheme(themeName, themeUrl, themeUrlMD5);

			// TODO delete print
			System.out.println(theme.toString());

			themeSet.add(theme);
		}

		return themeSet;
	}

	// ������ȡ�����ӿڣ�Ϊ���ô���Ŀ��Ʋ㷽���ṩ
	public Set<Theme> execGrabTheme(Site site, GrabParame grabParame) {

		// ��ʼ������
		setSite(site);
		setGrabParame(grabParame);

		// ��ִ������֮ǰ�����ȼ���������״̬λ����������Ϊtrue����ǰ��������ִ������
		if (ThreadEndFlag.isThreadEndFlag()) {
			return null;
		}

		ExtraParameService extraParameService = (ExtraParameService) ApplicationContextFactory.appContext
				.getBean("extraParameService");
		setExtraParame(extraParameService.loadBySiteId(site.getSiteId()));

		Set<Theme> themes = this.getThemeUrlSet();
		if (themes == null) {
			// TODO д�������쳣��־
		}

		// �����ȡ���
		return themes;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public GrabParame getGrabParame() {
		return grabParame;
	}

	public void setGrabParame(GrabParame grabParame) {
		this.grabParame = grabParame;
	}

	public ExtraParame getExtraParame() {
		return extraParame;
	}

	public void setExtraParame(ExtraParame extraParame) {
		this.extraParame = extraParame;
	}

}