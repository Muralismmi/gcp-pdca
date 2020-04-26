package com.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.DAO.UserDAO;
import com.DAOImpl.UserDAOImpls;
import com.service.UserAccessService;



public class UserAccessServiceImpls implements UserAccessService{
	private static final 	Logger log 		= 	Logger.getLogger(UserAccessServiceImpls.class.getName());

	@Override
	public Object checkAndCreateUser(String userEmail,String loggedInUser) {
		
		HashMap<String,Object> responseMap = null;
		UserDAO userDao = new UserDAOImpls();
		HashMap<String,Object> userObj = null;
		try
		{
			responseMap = new HashMap<String,Object>();
			
		
			if(userObj!=null)
			{
				log.info("Updating a user");
				userObj.put("updatedBy",loggedInUser);
				userObj.put("updatedOn",new Date().getTime());
				//userObj.put("userName",Helper.FormFirstandLastname(userEmail));
				userObj.put("UserEmailId",userEmail);
				responseMap.put("data", userObj);
				responseMap.put("Status", "Success");
				responseMap.put("Message","User Updated Successfully");
			}
			else
			{
				log.info("Creating a user");
				userObj = new HashMap<String,Object>();
				
			//	userDao.save(userObj);
				responseMap.put("data", userObj);
				responseMap.put("Status", "Success");
				responseMap.put("Message","User Created Successfully");
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
	public void constructUserToIndex(HashMap<String, Object> map) 
	{
		List<String> keyList	=	null;
		try
		{
			keyList	=	new ArrayList<String>();
			List<HashMap<String,Object>> completeCardsMap = new ArrayList<HashMap<String,Object>>();
			HashMap<String, Object> objmap = new ObjectMapper().convertValue(map, HashMap.class);
			objmap.put("id",objmap.get("userEmailUri"));
			completeCardsMap.add(objmap);
			keyList.addAll(objmap.keySet());
			List<String> textFields = new ArrayList<String>();
			List<String> dateFields = new ArrayList<String>();
			dateFields.add("createdDate");
			dateFields.add("updatedDate");
			//PartialTextSearch.createFullSearchDocument(completeCardsMap, "USER_INDEX", keyList, "id",textFields,dateFields);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}
	}



	@Override
	public Object fetchUserRoles(String emailUri) {
		// TODO Auto-generated method stub
		return null;
	}



	
}