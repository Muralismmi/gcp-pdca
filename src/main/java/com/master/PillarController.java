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

import com.DAO.PillarDAO;
import com.DAO.UserDAO;
import com.DAOImpl.PillarDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Pillar;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class PillarController {
	private static final UserDAO userDao = new UserDAOImpls();
	private static final Logger log = Logger.getLogger(PillarController.class.getName());

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/savePillar", method = RequestMethod.POST)
	public @ResponseBody String savePillar(HttpServletRequest request, @RequestParam("pillarObj") String PillarObj) throws JsonGenerationException, JsonMappingException, IOException {
		

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		PillarDAO PillarDAO = new PillarDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("PillarObj: "+PillarObj);
			Pillar PillarJdoObj = new ObjectMapper().readValue(PillarObj.toString(), new TypeReference<Pillar>() {});
			id = PillarJdoObj.getId();
			
			if( id != null && id!=0 ) {
				Pillar fromDB = PillarDAO.getPillarbyName(PillarJdoObj.getName());
					if(fromDB == null || fromDB.getId().equals(id)) {
						PillarJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						PillarJdoObj.setLastUpdatedOn(new Date().getTime());
						PillarDAO.savePillar(PillarJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Pillar Succesfully");
						responseMap.put("data", PillarJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Pillar with this name already Exist");
				}
				
			} else {
				Pillar fromDB = PillarDAO.getPillarbyName(PillarJdoObj.getName());
				if(fromDB==null) {
					log.info("new Pillar");
					PillarJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					PillarJdoObj.setCreatedOn(new Date().getTime());
					PillarDAO.savePillar(PillarJdoObj);
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Pillar Succesfully");
					responseMap.put("data", PillarJdoObj);
					//PillarDAO.savePillar(PillarJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Pillar Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in PillarController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deletepillar/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deletePillarByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		PillarDAO PillarDAO = new PillarDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,PillarDAO.deletePillar(Long.parseLong(id)));
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
