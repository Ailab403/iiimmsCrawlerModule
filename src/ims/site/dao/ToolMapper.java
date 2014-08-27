package ims.site.dao;

import java.util.Set;

import ims.site.model.Tool;

public interface ToolMapper {

	void add(Tool tool);

	void deleteById(int toolId);

	void update(Tool tool);

	Tool loadById(int toolId);

	Set<Tool> listAll();

}
