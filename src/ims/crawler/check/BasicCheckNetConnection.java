package ims.crawler.check;

import ims.crawler.util.BasicJsoupDocumentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author superhy
 * 
 */
public class BasicCheckNetConnection {

	public Map<String, Object> execCheck(String testUrl) {
		// ׼�����������ֵ
		Map<String, Object> checkResMap = new HashMap<String, Object>();

		int checkRes = 1; // ���״̬��Ĭ��Ϊ1��ʾ����
		String errorDescribe = "��������";

		if (BasicJsoupDocumentUtil.getDocument(testUrl) == null) {
			checkRes = 0; // �������󣬼��״̬Ϊ0��ʾ������
			errorDescribe = "�������Ӳ�����";
		}

		checkResMap.put("checkRes", checkRes);
		checkResMap.put("errorDescribe", errorDescribe);

		// ���ؼ����
		return checkResMap;
	}
}
