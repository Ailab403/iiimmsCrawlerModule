package ims.site.dao;

import ims.site.model.GrabParame;

public interface GrabParameMapper {

	void add(GrabParame grabParame);

	void deleteById(int grabParameId);

	void update(GrabParame grabParame);

	GrabParame loadBySiteId(int siteId);

	GrabParame loadById(int grabParameId);
}
