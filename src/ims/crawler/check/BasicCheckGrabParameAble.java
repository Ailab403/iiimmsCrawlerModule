package ims.crawler.check;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ims.crawler.util.BasicJsoupDocumentUtil;
import ims.site.model.GrabParame;
import ims.site.model.Site;

/**
 * 
 * @author superhy
 * 
 */
public class BasicCheckGrabParameAble {

	public Map<String, Object> execCheck(Site testSite,
			GrabParame testGrabParame) {
		Document docTest = BasicJsoupDocumentUtil.getDocument(testSite
				.getSeedUrl());
		// 准备检查结果返回值
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// 默认检查无异常
		int checkRes = 1;

		// 准备错误描述
		String errorDescribe = "";

		if (docTest.select(testGrabParame.getThemeListDivQuery()).size() == 0) {
			errorDescribe += "themeListDivQuery参数错误！，";
			System.err.println(errorDescribe);
		} else {
			Element eleThemeListDiv = docTest.select(
					testGrabParame.getThemeListDivQuery()).first();

			if (eleThemeListDiv.select(testGrabParame.getThemeDivQuery())
					.size() == 0) {
				errorDescribe += "themeDivQuery参数错误！，";
				System.err.println(errorDescribe);
			} else {
				if (eleThemeListDiv.select(testGrabParame.getThemeUrlQuery())
						.size() == 0) {
					errorDescribe += "themeUrlQuery参数错误！，";
					System.err.println(errorDescribe);
				} else {
					Document docPostPage = BasicJsoupDocumentUtil
							.getDocument(eleThemeListDiv.select(
									testGrabParame.getThemeUrlQuery()).first()
									.attr("abs:href"));

					if (docPostPage.select(
							testGrabParame.getPostListSpDivQuery()).size() == 0) {
						errorDescribe += "postListSpDivQuery参数错误！，";
						System.err.println(errorDescribe);
					} else {
						Element elePostListSpDiv = docPostPage.select(
								testGrabParame.getPostListSpDivQuery()).first();

						if (elePostListSpDiv.select(
								testGrabParame.getPostDivQuery()).size() == 0) {
							errorDescribe += "postDivQuery参数错误！";
							System.err.println(errorDescribe);
						}
					}
				}
			}
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// 返回检查结果
		return checkResMap;
	}
}
