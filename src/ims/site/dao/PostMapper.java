package ims.site.dao;

import ims.site.model.Post;

import java.util.Map;
import java.util.Set;

public interface PostMapper {

	void add(Post post);

	void deleteByThemeId(int themeId);

	void deleteBySiteId(int siteId);

	void deleteByFetchable(int fetchable);

	void update(Post post);

	void updateNumByPostUrlMD5(Post post);

	void updateByPostUrlMD5(Post post);

	void updateFetchableByPostUrlMD5(Post post);

	void clear();

	Set<Post> listByThemeId(int themeId);

	Set<Post> listByThemeIdAndFetchable(
			Map<String, Object> themeIdAndFetchableMaps);

	Set<Post> listBySiteId(int siteId);

	Set<Post> listBySiteIdAndFetchable(
			Map<String, Object> siteIdAndFetchableMaps);

	Set<Post> listAll();
}
