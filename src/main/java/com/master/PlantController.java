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

import com.DAO.PlantDAO;
import com.DAO.UserDAO;
import com.DAOImpl.PlantDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Plant;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class PlantController {
	
	private static final Logger log = Logger.getLogger(PlantController.class.getName());
	private static final UserDAO userDao = new UserDAOImpls();
	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/savePlant", method = RequestMethod.POST)
	public @ResponseBody String savePlant(HttpServletRequest request, @RequestParam("plantObj") String plantObj) throws JsonGenerationException, JsonMappingException, IOException {
		

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		PlantDAO plantDAO = new PlantDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("plantObj: "+plantObj);
			Plant plantJdoObj = new ObjectMapper().readValue(plantObj.toString(), new TypeReference<Plant>() {});
			id = plantJdoObj.getId();
			log.info("ID: "+id);
			
			if(id != null) {
				Plant fromDBbyId = plantDAO.fetchPlantById(id);
				Plant fromDB = plantDAO.getPlantbyName(plantJdoObj.getName());
				
				if(fromDBbyId!=null) {
					if(fromDBbyId.getName().equals(plantJdoObj.getName().trim())  ||  fromDB==null) {
						plantJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						plantJdoObj.setLastUpdatedOn(new Date().getTime());
						plantDAO.savePlant(plantJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Plant Succesfully");
						responseMap.put("data", plantJdoObj);
					}else {
						responseMap.put("STATUS", "FAILURE");
						responseMap.put("MESSAGE", "Plant with this name already Exist");
					}
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "System Could not find Plant With ID specified");
				}
				
			} else {
				Plant fromDB = plantDAO.getPlantbyName(plantJdoObj.getName());
				if(fromDB==null) {
					log.info("new Plant");
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Plant Succesfully");
					responseMap.put("data", plantJdoObj);
					plantDAO.savePlant(plantJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Plant with this name already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in PlantController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deleteplant/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deletePlantByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		PlantDAO plantDAO = new PlantDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,plantDAO.deletePlant(Long.parseLong(id)));
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
