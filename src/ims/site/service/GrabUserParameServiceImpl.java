package ims.site.service;

import ims.site.dao.GrabUserParameMapper;
import ims.site.model.GrabUserParame;


public class GrabUserParameServiceImpl implements GrabUserParameService {

	// ×¢Èëmapper·½·¨
	private GrabUserParameMapper grabUserParameMapper;

	public GrabUserParameMapper getGrabUserParameMapper() {
		return grabUserParameMapper;
	}

	public void setGrabUserParameMapper(
			GrabUserParameMapper grabUserParameMapper) {
		this.grabUserParameMapper = grabUserParameMapper;
	}

	public void add(GrabUserParame grabUserParame) {

		this.grabUserParameMapper.add(grabUserParame);
	}

	public void deleteById(int grabUserParameId) {

		this.grabUserParameMapper.deleteById(grabUserParameId);
	}

	public GrabUserParame loadById(int grabUserParameId) {

		return this.grabUserParameMapper.loadById(grabUserParameId);
	}

	public GrabUserParame loadBySiteId(int siteId) {

		return this.grabUserParameMapper.loadBySiteId(siteId);
	}

	public void update(GrabUserParame grabUserParame) {

		this.grabUserParameMapper.update(grabUserParame);
	}

}
