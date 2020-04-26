package com.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.DAO.PlantDAO;
import com.DAOImpl.PlantDAOImpls;
import com.service.PlantAccessService;

public class PlantAccessServiceImpls implements PlantAccessService{
	private static final 	Logger log 		= 	Logger.getLogger(PlantAccessServiceImpls.class.getName());
	@Override
	public Object checkAndCreatePlant(String plantName, String loggedinUser) {
		
		HashMap<String,Object> responseMap = null;
		PlantDAO plantDAO = new PlantDAOImpls();
		HashMap<String,Object> plantObj = null;
		try
		{
			responseMap = new HashMap<String,Object>();
			
		
			if(plantObj!=null)
			{
				log.info("Updating a plant");
				plantObj.put("updatedBy",loggedinUser);
				plantObj.put("updatedOn",new Date().getTime());
				//userObj.put("userName",Helper.FormFirstandLastname(userEmail));
				responseMap.put("data", plantObj);
				responseMap.put("Status", "Success");
				responseMap.put("Message","Plant Updated Successfully");
			}
			else
			{
				log.info("Creating a user");
				plantObj = new HashMap<String,Object>();
				
			//	userDao.save(userObj);
				responseMap.put("data", plantObj);
				responseMap.put("Status", "Success");
				responseMap.put("Message","Plant Created Successfully");
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			responseMap.put("Status","Failure");
			responseMap.put("Message", "Something Went wrong.Please Contact Administrator");
			responseMap.put("data",null);
		}
		return responseMap;
	
	}

	@Override
	public void constructPlantToIndex(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
