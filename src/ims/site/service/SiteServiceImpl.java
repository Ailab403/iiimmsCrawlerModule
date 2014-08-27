package ims.site.service;

import ims.site.dao.SiteMapper;
import ims.site.model.Site;

import java.util.Set;

public class SiteServiceImpl implements SiteService {

	// ×¢Èëmapper·½·¨
	private SiteMapper siteMapper;

	public SiteMapper getSiteMapper() {
		return siteMapper;
	}

	public void setSiteMapper(SiteMapper siteMapper) {
		this.siteMapper = siteMapper;
	}

	public void add(Site site) {

		this.siteMapper.add(site);
	}

	public void deleteById(int siteId) {

		this.siteMapper.deleteById(siteId);
	}

	public void update(Site site) {

		this.siteMapper.update(site);
	}

	public Site loadById(int siteId) {

		return this.siteMapper.loadById(siteId);
	}

	public Set<Site> listAll() {

		return this.siteMapper.listAll();
	}

	public Set<Site> listByCategoryId(int categoryId) {
		return this.siteMapper.listByCategoryId(categoryId);
	}

	public Set<Site> listBySiteGrabable(int siteGrabable) {
		return this.siteMapper.listBySiteGrabable(siteGrabable);
	}

}
