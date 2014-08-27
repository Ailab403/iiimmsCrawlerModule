package ims.crawler.fetch.special;

import ims.site.model.GrabUserParame;
import ims.site.model.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchOutsideReplyContent {

	public List<Map<String, Object>> getOutsideReplyContent(Site site,
			String articlePageUrl, GrabUserParame grabUserParame) {

		List<Map<String, Object>> replyContentRes = new ArrayList<Map<String, Object>>();

		// 处理sina博客特殊情况
		if (site.getSeedUrl().contains("sina")) {
			GetSinaOutsideReply getSinaOutsideReply = new GetSinaOutsideReply();

			replyContentRes = getSinaOutsideReply.getReplyPageContents(
					articlePageUrl, grabUserParame);
		}

		return replyContentRes;
	}
}
