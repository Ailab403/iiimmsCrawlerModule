package ims.site.service;

import ims.site.model.Tool;

import java.util.Set;

public interface ToolService {

	public void add(Tool tool);

	public void deleteById(int toolId);

	public void update(Tool tool);

	public Tool loadById(int toolId);

	public Set<Tool> listAll();
}
