package ims.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * HttpClient���ӷ������ٶȽ�������������Ӵ�����չ
 */
public class HcJsoupDocumentUtil {

	// �߳�ͬ��
	public synchronized static Document getDocument(String url, String baseUri,
			String enCode) {
		try {

			System.out.println(url);

			String strResponseHtml = HcHtmlDownload.downLoadDepthMarketPage(url,
					enCode).toString();

			return Jsoup.parse(strResponseHtml, baseUri);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}
}
