package ims.crawler.check;

import ims.crawler.util.HcJsoupDocumentUtil;

import java.util.HashMap;
import java.util.Map;

public class HcCheckNetConnection {
	public Map<String, Object> execCheck(String testUrl) {

		// 准备检查结果返回值
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		int checkRes = 1; // 检查状态，默认为1表示正常
		String errorDescribe = "网络正常";

		if (HcJsoupDocumentUtil.getDocument(testUrl, testUrl, "UTF-8") == null) {
			checkRes = 0; // 发生错误，检查状态为0表示不正常
			errorDescribe = "网络连接不正常";
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// 返回检查结果
		return checkResMap;
	}
}
