package ims.site.dao;

import ims.site.model.ExtraParame;

public interface ExtraParameMapper {

	void add(ExtraParame extraParame);

	void deleteById(int extraParameId);

	void update(ExtraParame extraParame);

	ExtraParame loadBySiteId(int siteId);

	ExtraParame loadById(int extraParameId);
}
