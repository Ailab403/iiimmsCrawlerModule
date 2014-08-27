package ims.crawler.check;

import ims.crawler.util.HcJsoupDocumentUtil;
import ims.site.model.GrabParame;
import ims.site.model.Site;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author superhy
 * 
 */
public class HcCheckGrabParameAble {

	public Map<String, Object> execCheck(Site testSite,
			GrabParame testGrabParame) {
		Document docTest = HcJsoupDocumentUtil.getDocument(testSite
				.getSeedUrl(), testSite.getSeedUrl(), "UTF-8");
		// ׼�����������ֵ
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// Ĭ�ϼ�����쳣
		int checkRes = 1;

		// ׼����������
		String errorDescribe = "";

		if (docTest.select(testGrabParame.getThemeListDivQuery()).size() == 0) {
			errorDescribe += "themeListDivQuery�������󣡣�";
			System.err.println(errorDescribe);
		} else {
			Element eleThemeListDiv = docTest.select(
					testGrabParame.getThemeListDivQuery()).first();

			if (eleThemeListDiv.select(testGrabParame.getThemeDivQuery())
					.size() == 0) {
				errorDescribe += "themeDivQuery�������󣡣�";
				System.err.println(errorDescribe);
			} else {
				if (eleThemeListDiv.select(testGrabParame.getThemeUrlQuery())
						.size() == 0) {
					errorDescribe += "themeUrlQuery�������󣡣�";
					System.err.println(errorDescribe);
				} else {
					Document docPostPage = HcJsoupDocumentUtil.getDocument(
							eleThemeListDiv.select(
									testGrabParame.getThemeUrlQuery()).first()
									.attr("abs:href"), eleThemeListDiv.select(
									testGrabParame.getThemeUrlQuery()).first()
									.attr("abs:href"), "UTF-8");

					if (docPostPage.select(
							testGrabParame.getPostListSpDivQuery()).size() == 0) {
						errorDescribe += "postListSpDivQuery�������󣡣�";
						System.err.println(errorDescribe);
					} else {
						Element elePostListSpDiv = docPostPage.select(
								testGrabParame.getPostListSpDivQuery()).first();

						if (elePostListSpDiv.select(
								testGrabParame.getPostDivQuery()).size() == 0) {
							errorDescribe += "postDivQuery��������";
							System.err.println(errorDescribe);
						}
					}
				}
			}
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// ���ؼ����
		return checkResMap;
	}
}
