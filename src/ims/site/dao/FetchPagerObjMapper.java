package ims.site.dao;

import java.util.Map;
import java.util.Set;

import ims.site.model.FetchPagerObj;

public interface FetchPagerObjMapper {

	void add(FetchPagerObj fetchPagerObj);

	void deleteById(int fetchPagerObjId);

	void update(FetchPagerObj fetchPagerObj);

	FetchPagerObj loadById(int fetchPagerObjId);

	Set<FetchPagerObj> listByGeneralableAndUsed(
			Map<String, Object> GeneralableAndUsedMaps);

	Set<FetchPagerObj> listAll();
}
