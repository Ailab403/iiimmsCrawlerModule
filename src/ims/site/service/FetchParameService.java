package ims.site.service;

import ims.site.model.FetchParame;

public interface FetchParameService {

	public void add(FetchParame fetchParame);

	public void deleteById(int fetchParameId);

	public void update(FetchParame fetchParame);

	public FetchParame loadBySiteId(int siteId);

	public FetchParame loadById(int fetchParameId);
}
