package ims.site.service;

import ims.site.dao.ToolMapper;
import ims.site.model.Tool;

import java.util.Set;

public class ToolServiceImpl implements ToolService {

	private ToolMapper toolMapper;

	public ToolMapper getToolMapper() {
		return toolMapper;
	}

	public void setToolMapper(ToolMapper toolMapper) {
		this.toolMapper = toolMapper;
	}

	public void add(Tool tool) {
		this.toolMapper.add(tool);
	}

	public void deleteById(int toolId) {
		this.toolMapper.deleteById(toolId);
	}

	public Set<Tool> listAll() {
		return this.toolMapper.listAll();
	}

	public Tool loadById(int toolId) {
		return this.toolMapper.loadById(toolId);
	}

	public void update(Tool tool) {
		this.toolMapper.update(tool);
	}

}
