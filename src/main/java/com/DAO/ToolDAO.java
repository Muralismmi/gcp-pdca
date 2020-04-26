package com.DAO;

import java.util.List;

import com.entity.Tool;


public interface ToolDAO {
	public void saveTool(Tool objTool);
	public List<Tool> fetchAll();
	public Tool getToolByName(String  name);
	public Tool getToolByNameandPlant(String name);
	public Tool fetchToolById(Long id);
	public String deleteTool(long id);
	public Long countTool();
}
