package ims.site.model;

public class CrawlerConfig {

	private int configId;
	private String configName;
	private String proxyHost;
	private int refreshHour;
	private int refreshMin;
	private int refreshSec;
	private String configExp;

	// 没有外键关联

	public CrawlerConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CrawlerConfig(String configName, String proxyHost, int refreshHour,
			int refreshMin, int refreshSec, String configExp) {
		super();
		this.configName = configName;
		this.proxyHost = proxyHost;
		this.refreshHour = refreshHour;
		this.refreshMin = refreshMin;
		this.refreshSec = refreshSec;
		this.configExp = configExp;
	}

	public CrawlerConfig(int configId, String configName, String proxyHost,
			int refreshHour, int refreshMin, int refreshSec, String configExp) {
		super();
		this.configId = configId;
		this.configName = configName;
		this.proxyHost = proxyHost;
		this.refreshHour = refreshHour;
		this.refreshMin = refreshMin;
		this.refreshSec = refreshSec;
		this.configExp = configExp;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getRefreshHour() {
		return refreshHour;
	}

	public void setRefreshHour(int refreshHour) {
		this.refreshHour = refreshHour;
	}

	public int getRefreshMin() {
		return refreshMin;
	}

	public void setRefreshMin(int refreshMin) {
		this.refreshMin = refreshMin;
	}

	public int getRefreshSec() {
		return refreshSec;
	}

	public void setRefreshSec(int refreshSec) {
		this.refreshSec = refreshSec;
	}

	public String getConfigExp() {
		return configExp;
	}

	public void setConfigExp(String configExp) {
		this.configExp = configExp;
	}

	@Override
	public String toString() {
		return "CrawlerConfig [configExp=" + configExp + ", configId="
				+ configId + ", configName=" + configName + ", proxyHost="
				+ proxyHost + ", refreshHour=" + refreshHour + ", refreshMin="
				+ refreshMin + ", refreshSec=" + refreshSec + "]";
	}

}
