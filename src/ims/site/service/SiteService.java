package ims.site.service;

import ims.site.model.Site;

import java.util.Set;

public interface SiteService {

	public void add(Site site);

	public void deleteById(int siteId);

	public void update(Site site);

	public Site loadById(int siteId);

	public Set<Site> listAll();

	public Set<Site> listBySiteGrabable(int siteGrabable);

	public Set<Site> listByCategoryId(int categoryId);
}
