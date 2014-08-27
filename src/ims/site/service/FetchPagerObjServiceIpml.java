package ims.site.service;

import ims.site.dao.FetchPagerObjMapper;
import ims.site.model.FetchPagerObj;

import java.util.Map;
import java.util.Set;

public class FetchPagerObjServiceIpml implements FetchPagerObjService {

	private FetchPagerObjMapper fetchPagerObjMapper;

	public FetchPagerObjMapper getFetchPagerObjMapper() {
		return fetchPagerObjMapper;
	}

	public void setFetchPagerObjMapper(FetchPagerObjMapper fetchPagerObjMapper) {
		this.fetchPagerObjMapper = fetchPagerObjMapper;
	}

	public void add(FetchPagerObj fetchPagerObj) {
		this.fetchPagerObjMapper.add(fetchPagerObj);
	}

	public void deleteById(int fetchPagerObjId) {
		this.fetchPagerObjMapper.deleteById(fetchPagerObjId);
	}

	public Set<FetchPagerObj> listAll() {
		return this.fetchPagerObjMapper.listAll();
	}

	public Set<FetchPagerObj> listByGeneralableAndUsed(
			Map<String, Object> GeneralableAndUsedMaps) {
		return this.fetchPagerObjMapper
				.listByGeneralableAndUsed(GeneralableAndUsedMaps);
	}

	public FetchPagerObj loadById(int fetchPagerObjId) {
		return this.fetchPagerObjMapper.loadById(fetchPagerObjId);
	}

	public void update(FetchPagerObj fetchPagerObj) {
		this.fetchPagerObjMapper.update(fetchPagerObj);
	}

}
