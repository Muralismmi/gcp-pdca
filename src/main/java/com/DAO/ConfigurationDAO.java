package com.DAO;

import java.util.List;

import com.entity.Configuration;


public interface ConfigurationDAO {
	public void saveConfiguration(Configuration objConfiguration);

	public List<Configuration> getConfigurationByType(String type);
	
	public Configuration getConfigurationByName(String  name);
	public Configuration getConfigurationByNameandPlant(String name,String plant,String type);
	public Configuration fetchConfigurationById(Long id);

	public String deleteConfiguration(long id);
	
	public Long countConfiguration(String type);
}
