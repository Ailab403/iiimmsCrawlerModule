package ims.site.dao;

import ims.site.model.Site;

import java.util.Set;

public interface SiteMapper {

	void add(Site site);

	void deleteById(int siteId);

	void update(Site site);

	Site loadById(int siteId);

	Set<Site> listAll();

	Set<Site> listBySiteGrabable(int siteGrabable);

	Set<Site> listByCategoryId(int categoryId);
}
