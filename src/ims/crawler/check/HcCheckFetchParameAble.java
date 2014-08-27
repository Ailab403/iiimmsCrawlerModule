package ims.crawler.check;

import ims.crawler.util.HcJsoupDocumentUtil;
import ims.site.model.FetchParame;
import ims.site.model.Post;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author superhy
 * 
 */
public class HcCheckFetchParameAble {

	public Map<String, Object> execCheck(Post testPost,
			FetchParame testFetchParame) {
		Document docTest = HcJsoupDocumentUtil.getDocument(testPost
				.getPostUrl(), testPost.getPostUrl(), "UTF-8");
		// 准备检查结果返回值
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// 默认检查无异常
		int checkRes = 1;

		// 准备错误描述
		String errorDescribe = "";

		if (!testFetchParame.getPostDivQuery().equals("")
				&& testFetchParame.getPostDivQuery() != null
				&& docTest.select(testFetchParame.getPostDivQuery()).size() == 0) {
			errorDescribe += "postDivQuery参数错误！，";
			System.err.println(errorDescribe);
		} else {
			Element elePostDiv = docTest.select(
					testFetchParame.getPostDivQuery()).first();

			if (!testFetchParame.getPostContentQuery().equals("")
					&& testFetchParame.getPostContentQuery() != null
					&& elePostDiv.select(testFetchParame.getPostContentQuery())
							.size() == 0) {
				errorDescribe += "postContentQuery参数错误！，";
				System.err.println(errorDescribe);
			}
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// 返回检查结果
		return checkResMap;
	}
}
