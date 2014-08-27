package ims.site.dao;

import ims.site.model.FetchParame;

public interface FetchParameMapper {

	void add(FetchParame fetchParame);

	void deleteById(int fetchParameId);

	void update(FetchParame fetchParame);

	FetchParame loadBySiteId(int siteId);

	FetchParame loadById(int fetchParameId);
}
