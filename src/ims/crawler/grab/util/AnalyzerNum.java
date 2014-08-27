package ims.crawler.grab.util;

public class AnalyzerNum {

	public static boolean isdigit(char c) {

		return c >= '0' && c <= '9';
	}

	public static String getClickNum(String numStr) {

		// 先做预处理
		numStr = numStr.replaceAll(",", "");

		StringBuffer clickStr = new StringBuffer();

		String clickNum;
		try {
			int i;
			for (i = 0; i < numStr.length(); i++) {
				if (isdigit(numStr.charAt(i))) {
					break;
				}
			}
			for (; i < numStr.length(); i++) {
				if (!isdigit(numStr.charAt(i))) {
					break;
				}
				clickStr.append(numStr.charAt(i));
			}

			clickNum = clickStr.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			clickNum = "0";
		}

		if (!clickNum.matches("^[1-9]\\d*$")) {
			clickNum = "0";
		}

		return clickNum;
	}

	public static String getReplyNum(String numStr) {

		// 先做预处理
		numStr = numStr.replaceAll(",", "");

		StringBuffer replyStr = new StringBuffer();

		String replyNum;
		try {
			char[] c = new char[20];

			int i;
			for (i = numStr.length() - 1; i >= 0; i--) {
				if (isdigit(numStr.charAt(i))) {
					break;
				}
			}
			int j = 0;
			for (; i >= 0; i--) {
				if (!isdigit(numStr.charAt(i))) {
					break;
				}
				c[j++] = numStr.charAt(i);
			}
			for (int k = j - 1; k >= 0; k--) {
				replyStr.append(c[k]);
			}

			replyNum = replyStr.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			replyNum = "0";
		}

		if (!replyNum.matches("^[1-9]\\d*$")) {
			replyNum = "0";
		}

		return replyNum;
	}

	public static String ThransNum(String numStr) {
		StringBuffer transNumStrBuf = new StringBuffer();

		String transNumStr;
		try {
			int i;
			for (i = 0; i < numStr.length(); i++) {
				if (isdigit(numStr.charAt(i))) {
					break;
				}
			}
			for (; i < numStr.length(); i++) {
				if (!isdigit(numStr.charAt(i))) {
					break;
				}
				transNumStrBuf.append(numStr.charAt(i));
			}

			transNumStr = transNumStrBuf.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			transNumStr = "0";
		}

		if (!transNumStr.matches("^[1-9]\\d*$")) {
			transNumStr = "0";
		}

		return transNumStr;
	}
}
