package ims.site.service;

import ims.site.dao.FetchParameMapper;
import ims.site.model.FetchParame;


public class FetchParameServiceImpl implements FetchParameService {

	private FetchParameMapper fetchParameMapper;

	public FetchParameMapper getFetchParameMapper() {
		return fetchParameMapper;
	}

	public void setFetchParameMapper(FetchParameMapper fetchParameMapper) {
		this.fetchParameMapper = fetchParameMapper;
	}

	public void add(FetchParame fetchParame) {

		this.fetchParameMapper.add(fetchParame);
	}

	public void deleteById(int fetchParameId) {

		this.fetchParameMapper.deleteById(fetchParameId);
	}

	public void update(FetchParame fetchParame) {

		this.fetchParameMapper.update(fetchParame);
	}

	public FetchParame loadBySiteId(int siteId) {

		return this.fetchParameMapper.loadBySiteId(siteId);
	}

	public FetchParame loadById(int fetchParameId) {

		return this.fetchParameMapper.loadById(fetchParameId);
	}

}
