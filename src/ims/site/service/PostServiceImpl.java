package ims.site.service;

import ims.site.dao.PostMapper;
import ims.site.model.Post;

import java.util.Map;
import java.util.Set;

public class PostServiceImpl implements PostService {

	private PostMapper postMapper;

	public PostMapper getPostMapper() {
		return postMapper;
	}

	public void setPostMapper(PostMapper postMapper) {
		this.postMapper = postMapper;
	}

	public void add(Post post) {

		this.postMapper.add(post);
	}

	public void update(Post post) {
		this.postMapper.update(post);
	}

	public void clear() {

		this.postMapper.clear();
	}

	public void deleteByThemeId(int themeId) {

		this.postMapper.deleteByThemeId(themeId);
	}

	public Set<Post> listByThemeId(int themeId) {

		return this.postMapper.listByThemeId(themeId);
	}

	public Set<Post> listBySiteId(int siteId) {
		return this.postMapper.listBySiteId(siteId);
	}

	public Set<Post> listAll() {
		return this.postMapper.listAll();
	}

	public void updateByPostUrlMD5(Post post) {
		this.postMapper.updateByPostUrlMD5(post);
	}

	public void updateNumByPostUrlMD5(Post post) {
		this.postMapper.updateNumByPostUrlMD5(post);
	}

	public void updateFetchableByPostUrlMD5(Post post) {
		this.postMapper.updateFetchableByPostUrlMD5(post);
	}

	public Set<Post> listBySiteIdAndFetchable(
			Map<String, Object> siteIdAndFetchableMaps) {

		return this.postMapper.listBySiteIdAndFetchable(siteIdAndFetchableMaps);
	}

	public void deleteByFetchable(int fetchable) {
		this.postMapper.deleteByFetchable(fetchable);
	}

	public void deleteBySiteId(int siteId) {
		this.postMapper.deleteBySiteId(siteId);
	}

	public Set<Post> listByThemeIdAndFetchable(
			Map<String, Object> themeIdAndFetchableMaps) {

		return this.postMapper
				.listByThemeIdAndFetchable(themeIdAndFetchableMaps);
	}
}
