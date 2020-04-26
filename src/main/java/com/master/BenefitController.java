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

import com.DAO.BenefitDAO;
import com.DAO.UserDAO;
import com.DAOImpl.BenefitDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Benefit;
import com.entity.User;
import com.helper.UserUtil;

@RestController
public class BenefitController {
	private static final UserDAO userDao = new UserDAOImpls();
	private static final Logger log = Logger.getLogger(BenefitController.class.getName());

	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/savebenefit", method = RequestMethod.POST)
	public @ResponseBody String saveBenefit(HttpServletRequest request, @RequestParam("BenefitObj") String BenefitObj) throws JsonGenerationException, JsonMappingException, IOException {
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		BenefitDAO BenefitDAO = new BenefitDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("BenefitObj: "+BenefitObj);
			Benefit BenefitJdoObj = new ObjectMapper().readValue(BenefitObj.toString(), new TypeReference<Benefit>() {});
			id = BenefitJdoObj.getId();
			
			if( id != null && id!=0 ) {
				Benefit fromDB = BenefitDAO.getBenefitByName(BenefitJdoObj.getValue());
				System.out.println(id+ "  "+fromDB.getId());
					if(fromDB == null || fromDB.getId().equals(id)) {
						BenefitJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
						BenefitJdoObj.setLastUpdatedOn(new Date().getTime());
						BenefitJdoObj.setCreatedBy(fromDB.getCreatedBy());
						BenefitJdoObj.setCreatedOn(fromDB.getCreatedOn());
						BenefitDAO.saveBenefit(BenefitJdoObj);
						responseMap.put("STATUS", "SUCCESS");
						responseMap.put("MESSAGE", "Updated Benefit Succesfully");
						responseMap.put("data", BenefitJdoObj);
				}
				else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Benefit with this name already Exist");
				}
				
			} else {
				Benefit fromDB = BenefitDAO.getBenefitByName(BenefitJdoObj.getValue());
				if(fromDB==null) {
					log.info("new Benefit");
					BenefitJdoObj.setCreatedBy(UserUtil.getCurrentUser());
					BenefitJdoObj.setCreatedOn(new Date().getTime());
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created Benefit Succesfully");
					responseMap.put("data", BenefitJdoObj);
					BenefitDAO.saveBenefit(BenefitJdoObj);
				}else {
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "Benefit Already Exist");
				}
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in BenefitController while saving " + e.getMessage());
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "Something Went Wrong, Please contact Administrator");
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	@RequestMapping(value="/deletebenefit/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteBenefitByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		BenefitDAO BenefitDAO = new BenefitDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,BenefitDAO.deleteBenefit(Long.parseLong(id)));
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
