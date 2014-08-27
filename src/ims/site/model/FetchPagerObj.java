package ims.site.model;

public class FetchPagerObj {

	private int fetchPagerObjId;
	private String fetchPagerObjName;
	private int generalable;
	private int used;
	private String objExp;

	public FetchPagerObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FetchPagerObj(String fetchPagerObjName, int generalable, int used,
			String objExp) {
		super();
		this.fetchPagerObjName = fetchPagerObjName;
		this.generalable = generalable;
		this.used = used;
		this.objExp = objExp;
	}

	public FetchPagerObj(int fetchPagerObjId, String fetchPagerObjName,
			int generalable, int used, String objExp) {
		super();
		this.fetchPagerObjId = fetchPagerObjId;
		this.fetchPagerObjName = fetchPagerObjName;
		this.generalable = generalable;
		this.used = used;
		this.objExp = objExp;
	}

	public int getFetchPagerObjId() {
		return fetchPagerObjId;
	}

	public void setFetchPagerObjId(int fetchPagerObjId) {
		this.fetchPagerObjId = fetchPagerObjId;
	}

	public String getFetchPagerObjName() {
		return fetchPagerObjName;
	}

	public void setFetchPagerObjName(String fetchPagerObjName) {
		this.fetchPagerObjName = fetchPagerObjName;
	}

	public int getGeberalable() {
		return generalable;
	}

	public void setGeberalable(int generalable) {
		this.generalable = generalable;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public String getObjExp() {
		return objExp;
	}

	public void setObjExp(String objExp) {
		this.objExp = objExp;
	}

	@Override
	public String toString() {
		return "FetchPagerObj [fetchPagerObj=" + fetchPagerObjName
				+ ", fetchPagerObjId=" + fetchPagerObjId + ", generalable="
				+ generalable + ", objExp=" + objExp + ", used=" + used + "]";
	}

}
