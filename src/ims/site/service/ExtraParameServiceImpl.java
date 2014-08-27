package ims.site.service;

import ims.site.dao.ExtraParameMapper;
import ims.site.model.ExtraParame;

public class ExtraParameServiceImpl implements ExtraParameService {

	private ExtraParameMapper extraParameMapper;

	public ExtraParameMapper getExtraParameMapper() {
		return extraParameMapper;
	}

	public void setExtraParameMapper(ExtraParameMapper extraParameMapper) {
		this.extraParameMapper = extraParameMapper;
	}

	public void add(ExtraParame extraParame) {
		this.extraParameMapper.add(extraParame);
	}

	public void deleteById(int extraParameId) {
		this.extraParameMapper.deleteById(extraParameId);
	}

	public ExtraParame loadById(int extraParameId) {
		return this.extraParameMapper.loadById(extraParameId);
	}

	public ExtraParame loadBySiteId(int siteId) {
		return this.extraParameMapper.loadBySiteId(siteId);
	}

	public void update(ExtraParame extraParame) {
		this.extraParameMapper.update(extraParame);
	}

}
