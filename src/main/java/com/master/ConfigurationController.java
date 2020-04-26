package com.master;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.DAO.ConfigurationDAO;
import com.DAO.PlantDAO;
import com.DAOImpl.ConfigurationDAOImpls;
import com.DAOImpl.PlantDAOImpls;
import com.entity.Configuration;
import com.entity.Plant;
import com.helper.UserUtil;

@RestController
public class ConfigurationController {
	private static final Logger log = Logger.getLogger(ConfigurationController.class.getName());

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/saveConfiguration", method = RequestMethod.POST)
	public @ResponseBody String saveConfiguration(HttpServletRequest request, @RequestParam("configurationObj") String configurationObj) throws JsonGenerationException, JsonMappingException, IOException {
		
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		ConfigurationDAO configurationDAO = new ConfigurationDAOImpls();
		
		Long id = 0L;
		try {
			log.info("configurationObj: "+configurationObj);
			Configuration objConfiguration = new ObjectMapper().readValue(configurationObj.toString(), new TypeReference<Configuration>() {});
			id = objConfiguration.getId();
			log.info("ID: "+id);
			Configuration fromDB = configurationDAO.getConfigurationByNameandPlant(objConfiguration.getValue(),objConfiguration.getPlant(),objConfiguration.getType());
			if(id != null) {
				log.info("existing Configuration");
				if(fromDB !=null   ) {
					if(fromDB.getId() == id) {
						objConfiguration.setLastUpdatedBy(UserUtil.getCurrentUser());
						objConfiguration.setLastUpdatedOn(new Date().getTime());
						configurationDAO.saveConfiguration(objConfiguration);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Configuration Succesfully");
						responseMap.put("data", objConfiguration);
					}else {
						responseMap.put("STATUS", "FAILURE");
						responseMap.put("MESSAGE", objConfiguration.getType()+" for this plant with this value already exist");
					}
				}
				
			} else {
				if(fromDB !=null   ) {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", objConfiguration.getType()+" for this plant with this name already exist");
				}
				else {
					log.info("new user");
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Plant Succesfully");
					responseMap.put("data", objConfiguration);
					configurationDAO.saveConfiguration(objConfiguration);
				}
				//status = "Save";
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in Configuration Controller while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	@RequestMapping(value="/deleteconfiguration/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deletePlantByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		ConfigurationDAO configurationDAO = new ConfigurationDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,configurationDAO.deleteConfiguration(Long.parseLong(id)));
			}
			responseMap.put("data",failedList);
			responseMap.put("Status", "SUCCESS");
			responseMap.put("Message", "Deleted Succesfully");
		}
		catch(Exception e)
		{
			
			responseMap.put("Status", "Failure");
			responseMap.put("Message", "Exception Occured,Please Contact Admin");
		}
		return new ObjectMapper().writeValueAsString(responseMap);

		//return new ObjectMapper().writeValueAsString(resultMap);
	}
}
