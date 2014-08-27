package ims.site.service;

import ims.site.model.ExtraParame;

public interface ExtraParameService {

	public void add(ExtraParame extraParame);

	public void deleteById(int extraParameId);

	public void update(ExtraParame extraParame);

	public ExtraParame loadBySiteId(int siteId);

	public ExtraParame loadById(int extraParameId);
}
