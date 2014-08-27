package ims.site.dao;

import ims.site.model.GrabUserParame;

public interface GrabUserParameMapper {

	void add(GrabUserParame grabUserParame);

	void deleteById(int grabUserParameId);

	void update(GrabUserParame grabUserParame);

	GrabUserParame loadBySiteId(int siteId);

	GrabUserParame loadById(int grabUserParameId);
}
