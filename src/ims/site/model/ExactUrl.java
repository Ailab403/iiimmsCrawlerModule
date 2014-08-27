package ims.site.model;

public class ExactUrl {

	// ��ȷURL�࣬ÿ��URL��Ӧһ��MD5ֵ����дequel��hash������ȷ�����ظ�URLֵ
	// ����ʶ��һ��URL����ʹ�ã��������ݿ�ӳ��

	private String url;
	private String urlMD5;

	public ExactUrl() {
	}

	public ExactUrl(String url, String urlMD5) {
		super();
		this.url = url;
		this.urlMD5 = urlMD5;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlMD5() {
		return urlMD5;
	}

	public void setUrlMD5(String urlMD5) {
		this.urlMD5 = urlMD5;
	}

	/**
	 * ��дhashCode()��������url��MD5ָ��Ϊ��׼
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlMD5 == null) ? 0 : urlMD5.hashCode());
		return result;
	}

	/**
	 * ��дequals()��������url��MD5ָ��Ϊ��׼
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExactUrl other = (ExactUrl) obj;
		if (urlMD5 == null) {
			if (other.urlMD5 != null)
				return false;
		} else if (!urlMD5.equals(other.urlMD5))
			return false;
		return true;
	}
}