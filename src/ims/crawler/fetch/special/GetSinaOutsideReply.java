package ims.crawler.fetch.special;

import ims.crawler.grab.util.AnalyzerTime;
import ims.site.model.GrabUserParame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author superhy
 * 
 */
public class GetSinaOutsideReply {

	/**
	 * �õ��ظ�����ҳ�����ݼ���
	 * 
	 * @param articlePageUrl
	 * @return
	 */
	public List<Map<String, Object>> getReplyPageContents(
			String articlePageUrl, GrabUserParame grabUserParame) {

		// ׼��Ҫ���ص�����
		List<Map<String, Object>> eachArticleReplyMaps = new ArrayList<Map<String, Object>>();

		// ��ȡ�ظ�����ҳ���urlģ��
		String replyPageUrlModel = articlePageUrl.replaceAll("blog", "comment");
		replyPageUrlModel = replyPageUrlModel.replaceFirst("comment", "blog");
		replyPageUrlModel = replyPageUrlModel.substring(0, replyPageUrlModel
				.lastIndexOf("."));

		System.out.println(replyPageUrlModel);

		// ������ǰ10ҳ�Ļظ�����
		// �ж��Ƿ�ظ�ȫ��Ϊ��
		boolean flagReplyNull = true;
		for (int i = 1; i <= 10; i++) {
			String testReplyPageUrl = replyPageUrlModel + "_" + i + ".html";

			// ���Ի����ʵ�Ļظ�ҳ��html����
			String testRealReplyHtml = this.getRealReplyHtml(testReplyPageUrl);

			// �жϻظ�ҳ���html�������Ч�ԣ������Ч��ʱ�˳�
			if (testRealReplyHtml.contains("noCommdate")
					|| testRealReplyHtml.equals("")) {
				break;
			} else {
				Document docAllReply = Jsoup.parse(testRealReplyHtml);

				// ��ȡÿ���ظ���divԪ�ؼ���
				Elements elesReplyDiv = docAllReply
						.select("div.SG_revert_Cont");

				for (Element eleReplyDiv : elesReplyDiv) {

					// ��ȡ�ظ�������Ϣ
					Element eleReplyAuthorDiv = eleReplyDiv.select(
							"span[id^=nick_cmt]").first();
					String strReplyAuthor = eleReplyAuthorDiv != null ? eleReplyAuthorDiv
							.text()
							: "";

					// ��ȡ�ظ�������Ϣ
					Element eleReplyContentDiv = eleReplyDiv.select(
							"div[id^=body_cmt]").first();
					String strReplyContent = eleReplyContentDiv != null ? eleReplyContentDiv
							.text()
							: "";

					// TODO delete print
					System.out.println(strReplyContent);

					// ��ȡ�ظ�ʱ����Ϣ
					Element eleReplyTimeDiv = eleReplyDiv.select(
							"span.SG_revert_Time").first();
					String strReplyTime = eleReplyTimeDiv != null ? eleReplyTimeDiv
							.text()
							: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date());
					strReplyTime = AnalyzerTime
							.transStandardTimeFormat(strReplyTime);

					// �����������ĸ������������
					Map<String, Object> replyMap = new HashMap<String, Object>();
					// �ж��Ƿ���ַǿյ����
					if (!strReplyAuthor.equals("")
							|| !strReplyContent.equals("")
							|| !strReplyTime.equals("")) {

						// �ж����ʱ�䲻�ٽ�����Χ֮�ھͲ������鱨��ӳ�������
						if (!AnalyzerTime
								.compTimeLimit(grabUserParame
										.getPostEndTimeLimit().toString(),
										strReplyTime)) {
							continue;
						}

						// һ���лظ�ӳ�������룬��ǻظ�ӳ�����Ϊ��
						flagReplyNull = false;

						if (!strReplyAuthor.equals("")) {
							replyMap.put("replyAuthor", strReplyAuthor);
						}
						if (!strReplyContent.equals("")) {
							replyMap.put("replyContent", strReplyContent);
						}
						if (!strReplyTime.equals("")) {
							replyMap.put("replyTime", strReplyTime);
						}

						eachArticleReplyMaps.add(replyMap);

					} else {
						continue;
					}
				}
			}
		}

		// ���û�з���һ���ǿյĻظ���Ϣ��˵������û�лظ���Ϣ����ô�Ͳ����ûظ���Ϣ
		if (flagReplyNull) {
			eachArticleReplyMaps = null;
		}

		return eachArticleReplyMaps;
	}

	/**
	 * �õ���������ҳ�Ļظ�����html����
	 * 
	 * @param replyPageUrl
	 * @return
	 */
	public String getRealReplyHtml(String replyPageUrl) {

		String htmlStr = "";

		try {
			URL url;
			url = new URL(replyPageUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("����javascript�ⲿ���ݳɹ�");
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String strCurrentLine = "";
				String strTotalString = "";
				while ((strCurrentLine = bufferedReader.readLine()) != null) {
					strTotalString += strCurrentLine;
				}
				JSONObject jsonObject = JSONObject.fromObject(strTotalString);

				htmlStr = jsonObject.get("data").toString();
			} else {
				System.err.println("����javascript�ⲿ����ʧ��");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return htmlStr;
	}
}
