package ims.site.dao;

import java.util.Set;

import ims.site.model.SiteCategory;

public interface SiteCategoryMapper {

	void add(SiteCategory siteCategory);

	void deleteById(int categoryId);

	void update(SiteCategory siteCategory);

	SiteCategory loadById(int categoryId);

	Set<SiteCategory> listAll();

}
