package ims.site.service;

import ims.site.model.GrabParame;

public interface GrabParameService {

	public void add(GrabParame grabParame);

	public void deleteById(int grabParameId);

	public void update(GrabParame grabParame);

	public GrabParame loadBySiteId(int siteId);

	public GrabParame loadById(int grabParameId);
}
