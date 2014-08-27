package ims.site.service;

import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

public interface ThemeService {

	public void add(Theme theme);

	public void deleteById(int themeId);

	public void update(Theme theme);

	public Theme loadById(int themeId);

	public Theme loadByThemeUrlMD5(String themeUrlMD5);

	public Set<Theme> listBySiteId(int siteId);

	public Set<Theme> listBySiteIdAndGrabable(
			Map<String, Object> siteIdAndGrabableMaps);

	public Set<Theme> listAll();
}
