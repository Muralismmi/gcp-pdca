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

import com.DAO.ToolDAO;
import com.DAO.UserDAO;
import com.DAOImpl.ToolDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Tool;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class ToolController {
	
	private static final Logger log = Logger.getLogger(ToolController.class.getName());
	private static final UserDAO userDao = new UserDAOImpls();

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/savetool", method = RequestMethod.POST)
	public @ResponseBody String saveTool(HttpServletRequest request, @RequestParam("ToolObj") String ToolObj) throws JsonGenerationException, JsonMappingException, IOException {
		

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		ToolDAO ToolDAO = new ToolDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("ToolObj: "+ToolObj);
			Tool ToolJdoObj = new ObjectMapper().readValue(ToolObj.toString(), new TypeReference<Tool>() {});
			id = ToolJdoObj.getId();
			
			if( id != null && id!=0 ) {
				Tool fromDB = ToolDAO.getToolByName(ToolJdoObj.getValue());
				System.out.println(id+ "  "+fromDB.getId());
					if(fromDB == null || fromDB.getId().equals(id)) {
						ToolJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						ToolJdoObj.setLastUpdatedOn(new Date().getTime());
						ToolJdoObj.setCreatedBy(fromDB.getCreatedBy());
						ToolJdoObj.setCreatedOn(fromDB.getCreatedOn());
						ToolDAO.saveTool(ToolJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Tool Succesfully");
						responseMap.put("data", ToolJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Tool with this name already Exist");
				}
				
			} else {
				Tool fromDB = ToolDAO.getToolByName(ToolJdoObj.getValue());
				if(fromDB==null) {
					log.info("new Tool");
					ToolJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					ToolJdoObj.setCreatedOn(new Date().getTime());
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Tool Succesfully");
					responseMap.put("data", ToolJdoObj);
					ToolDAO.saveTool(ToolJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Tool Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in ToolController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deletetool/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteToolByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{

		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		ToolDAO ToolDAO = new ToolDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,ToolDAO.deleteTool(Long.parseLong(id)));
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
