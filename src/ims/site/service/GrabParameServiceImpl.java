package ims.site.service;

import ims.site.dao.GrabParameMapper;
import ims.site.model.GrabParame;


public class GrabParameServiceImpl implements GrabParameService {

	// ×¢Èëmapper·½·¨
	private GrabParameMapper grabParameMapper;

	public GrabParameMapper getGrabParameMapper() {
		return grabParameMapper;
	}

	public void setGrabParameMapper(GrabParameMapper grabParameMapper) {
		this.grabParameMapper = grabParameMapper;
	}

	public void add(GrabParame grabParame) {

		this.grabParameMapper.add(grabParame);
	}

	public void deleteById(int grabParameId) {

		this.grabParameMapper.deleteById(grabParameId);
	}

	public void update(GrabParame grabParame) {

		this.grabParameMapper.update(grabParame);
	}

	public GrabParame loadBySiteId(int siteId) {

		return this.grabParameMapper.loadBySiteId(siteId);
	}

	public GrabParame loadById(int grabParameId) {

		return this.grabParameMapper.loadById(grabParameId);
	}

}
