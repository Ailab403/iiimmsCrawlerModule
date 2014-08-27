package ims.site.model;

public class Tool {

	private int toolId;
	private String toolName;
	private String grabThemeObj;
	private String grabPostObj;
	private String fetchContentObj;
	private String checkNetObj;
	private String checkDatabaseObj;
	private String checkGrabParameObj;
	private String checkFetchParameObj;
	private String toolExp;

	public Tool() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tool(String toolName, String grabThemeObj, String grabPostObj,
			String fetchContentObj, String checkNetObj,
			String checkDatabaseObj, String checkGrabParameObj,
			String checkFetchParameObj, String toolExp) {
		super();
		this.toolName = toolName;
		this.grabThemeObj = grabThemeObj;
		this.grabPostObj = grabPostObj;
		this.fetchContentObj = fetchContentObj;
		this.checkNetObj = checkNetObj;
		this.checkDatabaseObj = checkDatabaseObj;
		this.checkGrabParameObj = checkGrabParameObj;
		this.checkFetchParameObj = checkFetchParameObj;
		this.toolExp = toolExp;
	}

	public Tool(int toolId, String toolName, String grabThemeObj,
			String grabPostObj, String fetchContentObj, String checkNetObj,
			String checkDatabaseObj, String checkGrabParameObj,
			String checkFetchParameObj, String toolExp) {
		super();
		this.toolId = toolId;
		this.toolName = toolName;
		this.grabThemeObj = grabThemeObj;
		this.grabPostObj = grabPostObj;
		this.fetchContentObj = fetchContentObj;
		this.checkNetObj = checkNetObj;
		this.checkDatabaseObj = checkDatabaseObj;
		this.checkGrabParameObj = checkGrabParameObj;
		this.checkFetchParameObj = checkFetchParameObj;
		this.toolExp = toolExp;
	}

	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getGrabThemeObj() {
		return grabThemeObj;
	}

	public void setGrabThemeObj(String grabThemeObj) {
		this.grabThemeObj = grabThemeObj;
	}

	public String getGrabPostObj() {
		return grabPostObj;
	}

	public void setGrabPostObj(String grabPostObj) {
		this.grabPostObj = grabPostObj;
	}

	public String getFetchContentObj() {
		return fetchContentObj;
	}

	public void setFetchContentObj(String fetchContentObj) {
		this.fetchContentObj = fetchContentObj;
	}

	public String getCheckNetObj() {
		return checkNetObj;
	}

	public void setCheckNetObj(String checkNetObj) {
		this.checkNetObj = checkNetObj;
	}

	public String getCheckDatabaseObj() {
		return checkDatabaseObj;
	}

	public void setCheckDatabaseObj(String checkDatabaseObj) {
		this.checkDatabaseObj = checkDatabaseObj;
	}

	public String getCheckGrabParameObj() {
		return checkGrabParameObj;
	}

	public void setCheckGrabParameObj(String checkGrabParameObj) {
		this.checkGrabParameObj = checkGrabParameObj;
	}

	public String getCheckFetchParameObj() {
		return checkFetchParameObj;
	}

	public void setCheckFetchParameObj(String checkFetchParameObj) {
		this.checkFetchParameObj = checkFetchParameObj;
	}

	public String getToolExp() {
		return toolExp;
	}

	public void setToolExp(String toolExp) {
		this.toolExp = toolExp;
	}

	@Override
	public String toString() {
		return "Tool [checkDatabaseObj=" + checkDatabaseObj
				+ ", checkFetchParameObj=" + checkFetchParameObj
				+ ", checkGrabParameObj=" + checkGrabParameObj
				+ ", checkNetObj=" + checkNetObj + ", fetchContentObj="
				+ fetchContentObj + ", grabPostObj=" + grabPostObj
				+ ", grabThemeObj=" + grabThemeObj + ", toolExp=" + toolExp
				+ ", toolId=" + toolId + ", toolName=" + toolName + "]";
	}

}
