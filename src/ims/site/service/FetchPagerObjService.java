package ims.site.service;

import ims.site.model.FetchPagerObj;

import java.util.Map;
import java.util.Set;

public interface FetchPagerObjService {

	public void add(FetchPagerObj fetchPagerObj);

	public void deleteById(int fetchPagerObjId);

	public void update(FetchPagerObj fetchPagerObj);

	public FetchPagerObj loadById(int fetchPagerObjId);

	public Set<FetchPagerObj> listByGeneralableAndUsed(
			Map<String, Object> GeneralableAndUsedMaps);

	public Set<FetchPagerObj> listAll();
}
