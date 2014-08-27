package ims.site.dao;

import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;


public interface ThemeMapper {

	void add(Theme theme);

	void deleteById(int themeId);

	void update(Theme theme);

	Theme loadById(int themeId);
	
	Theme loadByThemeUrlMD5(String themeUrlMD5);

	Set<Theme> listBySiteId(int siteId);

	Set<Theme> listBySiteIdAndGrabable(
			Map<String, Object> siteIdAndGrabableMaps);

	Set<Theme> listAll();
}
