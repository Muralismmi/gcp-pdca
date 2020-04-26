package com.service;

import java.util.HashMap;

public interface PlantAccessService {
	Object checkAndCreatePlant(String plantName,String loggedinUser);
	void constructPlantToIndex(HashMap<String,Object> map);
}
