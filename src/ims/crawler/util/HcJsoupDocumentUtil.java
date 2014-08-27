package ims.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * HttpClient连接方法，速度较慢，但可以添加代理扩展
 */
public class HcJsoupDocumentUtil {

	// 线程同步
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
