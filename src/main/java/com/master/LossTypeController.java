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

import com.DAO.LossTypeDAO;
import com.DAO.UserDAO;
import com.DAOImpl.LossTypeDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.LossType;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class LossTypeController {
	private static final UserDAO userDao = new UserDAOImpls();
	private static final Logger log = Logger.getLogger(LossTypeController.class.getName());

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/savelosstype", method = RequestMethod.POST)
	public @ResponseBody String saveLossType(HttpServletRequest request, @RequestParam("LossTypeObj") String LossTypeObj) throws JsonGenerationException, JsonMappingException, IOException {
		

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		LossTypeDAO LossTypeDAO = new LossTypeDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("LossTypeObj: "+LossTypeObj);
			LossType LossTypeJdoObj = new ObjectMapper().readValue(LossTypeObj.toString(), new TypeReference<LossType>() {});
			id = LossTypeJdoObj.getId();
			
			if( id != null && id!=0 ) {
				LossType fromDB = LossTypeDAO.getLossTypeByName(LossTypeJdoObj.getValue());
				System.out.println(id+ "  "+fromDB.getId());
					if(fromDB == null || fromDB.getId().equals(id)) {
						LossTypeJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						LossTypeJdoObj.setLastUpdatedOn(new Date().getTime());
						LossTypeJdoObj.setCreatedBy(fromDB.getCreatedBy());
						LossTypeJdoObj.setCreatedOn(fromDB.getCreatedOn());
						LossTypeDAO.saveLossType(LossTypeJdoObj);
						
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated LossType Succesfully");
						responseMap.put("data", LossTypeJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "LossType with this name already Exist");
				}
				
			} else {
				LossType fromDB = LossTypeDAO.getLossTypeByName(LossTypeJdoObj.getValue());
				if(fromDB==null) {
					log.info("new LossType");
					LossTypeJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					LossTypeJdoObj.setCreatedOn(new Date().getTime());
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created LossType Succesfully");
					responseMap.put("data", LossTypeJdoObj);
					LossTypeDAO.saveLossType(LossTypeJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "LossType Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in LossTypeController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deletelosstype/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteLossTypeByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		LossTypeDAO LossTypeDAO = new LossTypeDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,LossTypeDAO.deleteLossType(Long.parseLong(id)));
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
