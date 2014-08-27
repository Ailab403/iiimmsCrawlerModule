package ims.crawler.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Jsoup自带方法，速度较快
 */
public class BasicJsoupDocumentUtil {

	// 线程同步
	public synchronized static Document getDocument(String url) {
		try {

			// 设置连接超时和读数超时
			// 设置忽略过期页面
			return Jsoup.connect(url).timeout(120000).ignoreHttpErrors(true)
					.ignoreContentType(true).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}
}
