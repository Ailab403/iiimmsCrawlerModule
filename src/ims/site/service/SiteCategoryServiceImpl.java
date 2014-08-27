package ims.site.service;

import ims.site.dao.SiteCategoryMapper;
import ims.site.model.SiteCategory;

import java.util.Set;

public class SiteCategoryServiceImpl implements SiteCategoryService {

	private SiteCategoryMapper siteCategoryMapper;

	public SiteCategoryMapper getSiteCategoryMapper() {
		return siteCategoryMapper;
	}

	public void setSiteCategoryMapper(SiteCategoryMapper siteCategoryMapper) {
		this.siteCategoryMapper = siteCategoryMapper;
	}

	public void add(SiteCategory siteCategory) {
		this.siteCategoryMapper.add(siteCategory);
	}

	public void deleteById(int categoryId) {
		this.siteCategoryMapper.deleteById(categoryId);
	}

	public Set<SiteCategory> listAll() {
		return this.siteCategoryMapper.listAll();
	}

	public SiteCategory loadById(int categoryId) {
		return this.siteCategoryMapper.loadById(categoryId);
	}

	public void update(SiteCategory siteCategory) {
		this.siteCategoryMapper.update(siteCategory);
	}

}
