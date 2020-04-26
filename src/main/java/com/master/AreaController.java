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

import com.DAO.AreaDAO;
import com.DAO.UserDAO;
import com.DAOImpl.AreaDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Area;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class AreaController {
	
	private static final Logger log = Logger.getLogger(AreaController.class.getName());

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	UserDAO userDao = new UserDAOImpls();
	@RequestMapping(value = "/savearea", method = RequestMethod.POST)
	public @ResponseBody String saveArea(HttpServletRequest request, @RequestParam("AreaObj") String AreaObj) throws JsonGenerationException, JsonMappingException, IOException {
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		
		AreaDAO AreaDAO = new AreaDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("AreaObj: "+AreaObj);
			Area AreaJdoObj = new ObjectMapper().readValue(AreaObj.toString(), new TypeReference<Area>() {});
			id = AreaJdoObj.getId();
			
			if( id != null && id!=0 ) {
				Area fromDB = AreaDAO.getAreaByName(AreaJdoObj.getValue());
				System.out.println(id+ "  "+fromDB.getId());
					if(fromDB == null || fromDB.getId().equals(id)) {
						AreaJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						AreaJdoObj.setLastUpdatedOn(new Date().getTime());
						AreaJdoObj.setCreatedBy(fromDB.getCreatedBy());
						AreaJdoObj.setCreatedOn(fromDB.getCreatedOn());
						AreaDAO.saveArea(AreaJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Area Succesfully");
						responseMap.put("data", AreaJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Area with this name already Exist");
				}
				
			} else {
				Area fromDB = AreaDAO.getAreaByName(AreaJdoObj.getValue());
				if(fromDB==null) {
					log.info("new Area");
					AreaJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					AreaJdoObj.setCreatedOn(new Date().getTime());
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Area Succesfully");
					responseMap.put("data", AreaJdoObj);
					AreaDAO.saveArea(AreaJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Area Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in AreaController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deletearea/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteAreaByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		AreaDAO AreaDAO = new AreaDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,AreaDAO.deleteArea(Long.parseLong(id)));
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
