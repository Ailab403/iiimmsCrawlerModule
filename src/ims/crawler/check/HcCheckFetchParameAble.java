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
		// ׼�����������ֵ
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		// Ĭ�ϼ�����쳣
		int checkRes = 1;

		// ׼����������
		String errorDescribe = "";

		if (!testFetchParame.getPostDivQuery().equals("")
				&& testFetchParame.getPostDivQuery() != null
				&& docTest.select(testFetchParame.getPostDivQuery()).size() == 0) {
			errorDescribe += "postDivQuery�������󣡣�";
			System.err.println(errorDescribe);
		} else {
			Element elePostDiv = docTest.select(
					testFetchParame.getPostDivQuery()).first();

			if (!testFetchParame.getPostContentQuery().equals("")
					&& testFetchParame.getPostContentQuery() != null
					&& elePostDiv.select(testFetchParame.getPostContentQuery())
							.size() == 0) {
				errorDescribe += "postContentQuery�������󣡣�";
				System.err.println(errorDescribe);
			}
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// ���ؼ����
		return checkResMap;
	}
}
