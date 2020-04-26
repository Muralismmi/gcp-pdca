package com.DAO;

import java.util.List;

import com.entity.Plant;

public interface PlantDAO {
	public void savePlant(Plant adminJdo);

	public Plant getPlantbyName(String plantName);

	public Plant fetchPlantById(Long id);

	public List<Plant> fetchPlantList();
	public String deletePlant(long id);
	
	public Long countPlant();
}
