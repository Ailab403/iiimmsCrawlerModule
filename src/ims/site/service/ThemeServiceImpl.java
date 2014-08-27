package ims.site.service;

import ims.site.dao.ThemeMapper;
import ims.site.model.Theme;

import java.util.Map;
import java.util.Set;

public class ThemeServiceImpl implements ThemeService {

	private ThemeMapper themeMapper;

	public ThemeMapper getThemeMapper() {
		return themeMapper;
	}

	public void setThemeMapper(ThemeMapper themeMapper) {
		this.themeMapper = themeMapper;
	}

	public void add(Theme theme) {

		this.themeMapper.add(theme);
	}

	public void deleteById(int themeId) {

		this.themeMapper.deleteById(themeId);
	}

	public Set<Theme> listAll() {

		return this.themeMapper.listAll();
	}

	public Set<Theme> listBySiteId(int siteId) {

		return this.themeMapper.listBySiteId(siteId);
	}

	public Set<Theme> listBySiteIdAndGrabable(
			Map<String, Object> siteIdAndGrabableMaps) {

		return this.themeMapper.listBySiteIdAndGrabable(siteIdAndGrabableMaps);
	}

	public Theme loadById(int themeId) {

		return this.themeMapper.loadById(themeId);
	}

	public void update(Theme theme) {

		this.themeMapper.update(theme);
	}

	public Theme loadByThemeUrlMD5(String themeUrlMD5) {
		return this.themeMapper.loadByThemeUrlMD5(themeUrlMD5);
	}

}
