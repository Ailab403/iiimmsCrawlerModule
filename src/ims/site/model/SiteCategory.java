package ims.site.model;

public class SiteCategory {

	private int categoryId;
	private String categoryName;
	private int reqLogin;
	private String categoryExp;

	public SiteCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiteCategory(String categoryName, int reqLogin, String categoryExp) {
		super();
		this.categoryName = categoryName;
		this.reqLogin = reqLogin;
		this.categoryExp = categoryExp;
	}

	public SiteCategory(int categoryId, String categoryName, int reqLogin,
			String categoryExp) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.reqLogin = reqLogin;
		this.categoryExp = categoryExp;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getReqLogin() {
		return reqLogin;
	}

	public void setReqLogin(int reqLogin) {
		this.reqLogin = reqLogin;
	}

	public String getCategoryExp() {
		return categoryExp;
	}

	public void setCategoryExp(String categoryExp) {
		this.categoryExp = categoryExp;
	}

	@Override
	public String toString() {
		return "Category [categoryExp=" + categoryExp + ", categoryId="
				+ categoryId + ", categoryName=" + categoryName + ", reqLogin="
				+ reqLogin + "]";
	}

}
