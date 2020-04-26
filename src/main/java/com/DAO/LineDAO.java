package com.DAO;

import java.util.List;

import com.entity.Line;
import com.entity.LossType;


public interface LineDAO {
	public void saveLine(Line objLine);
	public List<Line> fetchAll();
	public Line getLineByName(String  name);
	public Line getLineByNameandPlant(String name,String plant);
	public Line fetchLineById(Long id);
	public String deleteLine(long id);
	public Long countLine();
}
