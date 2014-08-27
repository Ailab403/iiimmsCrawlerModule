package ims.site.model;

public class ExactUrl {

	// 精确URL类，每个URL对应一个MD5值，重写equel和hash方法，确保无重复URL值
	// 仅作识别一般URL变量使用，不作数据库映射

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
	 * 重写hashCode()方法，以url的MD5指纹为标准
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlMD5 == null) ? 0 : urlMD5.hashCode());
		return result;
	}

	/**
	 * 重写equals()方法，以url的MD5指纹为标准
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
