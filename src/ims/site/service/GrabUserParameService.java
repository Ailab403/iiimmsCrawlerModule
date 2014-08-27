package ims.site.service;

import ims.site.model.GrabUserParame;

public interface GrabUserParameService {

	public void add(GrabUserParame grabUserParame);

	public void deleteById(int grabUserParameId);

	public void update(GrabUserParame grabUserParame);

	public GrabUserParame loadBySiteId(int siteId);

	public GrabUserParame loadById(int grabUserParameId);
}
