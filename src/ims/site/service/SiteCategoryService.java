package ims.site.service;

import ims.site.model.SiteCategory;

import java.util.Set;

public interface SiteCategoryService {

	public void add(SiteCategory siteCategory);

	public void deleteById(int categoryId);

	public void update(SiteCategory siteCategory);

	public SiteCategory loadById(int categoryId);

	public Set<SiteCategory> listAll();
}
