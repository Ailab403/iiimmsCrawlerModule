package ims.crawler.check;

import ims.crawler.util.HcJsoupDocumentUtil;

import java.util.HashMap;
import java.util.Map;

public class HcCheckNetConnection {
	public Map<String, Object> execCheck(String testUrl) {

		// ׼�����������ֵ
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		int checkRes = 1; // ���״̬��Ĭ��Ϊ1��ʾ����
		String errorDescribe = "��������";

		if (HcJsoupDocumentUtil.getDocument(testUrl, testUrl, "UTF-8") == null) {
			checkRes = 0; // �������󣬼��״̬Ϊ0��ʾ������
			errorDescribe = "�������Ӳ�����";
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// ���ؼ����
		return checkResMap;
	}
}
