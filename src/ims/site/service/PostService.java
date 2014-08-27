package ims.site.service;

import ims.site.model.Post;

import java.util.Map;
import java.util.Set;

public interface PostService {

	public void add(Post post);

	public void deleteByThemeId(int themeId);

	public void deleteBySiteId(int siteId);

	public void deleteByFetchable(int fetchable);

	public void update(Post post);

	public void updateNumByPostUrlMD5(Post post);

	public void updateByPostUrlMD5(Post post);

	public void updateFetchableByPostUrlMD5(Post post);

	public void clear();

	public Set<Post> listByThemeId(int themeId);

	public Set<Post> listByThemeIdAndFetchable(
			Map<String, Object> themeIdAndFetchableMaps);

	public Set<Post> listBySiteId(int siteId);

	public Set<Post> listBySiteIdAndFetchable(
			Map<String, Object> siteIdAndFetchableMaps);

	public Set<Post> listAll();
}
