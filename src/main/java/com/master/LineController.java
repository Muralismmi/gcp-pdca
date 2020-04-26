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

import com.DAO.LineDAO;
import com.DAO.UserDAO;
import com.DAOImpl.LineDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Line;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class LineController {
	
	private static final Logger log = Logger.getLogger(LineController.class.getName());
	private static final UserDAO userDao = new UserDAOImpls();
	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/saveline", method = RequestMethod.POST)
	public @ResponseBody String saveLine(HttpServletRequest request, @RequestParam("LineObj") String LineObj) throws JsonGenerationException, JsonMappingException, IOException {
		
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		LineDAO LineDAO = new LineDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("LineObj: "+LineObj);
			Line LineJdoObj = new ObjectMapper().readValue(LineObj.toString(), new TypeReference<Line>() {});
			id = LineJdoObj.getId();
			
			if( id != null && id!=0 ) {
				Line fromDB = LineDAO.getLineByName(LineJdoObj.getValue());
				System.out.println(id+ "  "+fromDB.getId());
					if(fromDB == null || fromDB.getId().equals(id)) {
						LineJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						LineJdoObj.setLastUpdatedOn(new Date().getTime());
						LineJdoObj.setCreatedBy(fromDB.getCreatedBy());
						LineJdoObj.setCreatedOn(fromDB.getCreatedOn());
						LineDAO.saveLine(LineJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Line Succesfully");
						responseMap.put("data", LineJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Line with this name already Exist");
				}
				
			} else {
				Line fromDB = LineDAO.getLineByName(LineJdoObj.getValue());
				if(fromDB==null) {
					log.info("new Line");
					LineJdoObj.setCreatedOn(new Date().getTime());
					LineJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Line Succesfully");
					responseMap.put("data", LineJdoObj);
					LineDAO.saveLine(LineJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Line Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in LineController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deleteline/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteLineByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		LineDAO LineDAO = new LineDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,LineDAO.deleteLine(Long.parseLong(id)));
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
