package com.DAO;

import java.util.List;

import com.entity.Area;
import com.entity.LossType;


public interface AreaDAO {
	public void saveArea(Area objArea);
	public List<Area> fetchAll();
	public Area getAreaByName(String  name);
	public Area getAreaByNameandPlant(String name,String plant);
	public Area fetchAreaById(Long id);
	public String deleteArea(long id);
	public Long countArea();
	
}
