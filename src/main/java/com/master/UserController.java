package com.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.DAO.AreaDAO;
import com.DAO.BenefitDAO;
import com.DAO.ConfigurationDAO;
import com.DAO.LineDAO;
import com.DAO.LossTypeDAO;
import com.DAO.PillarDAO;
import com.DAO.PlantDAO;
import com.DAO.RequestDAO;
import com.DAO.ToolDAO;
import com.DAO.UserDAO;
import com.DAOImpl.AreaDAOImpls;
import com.DAOImpl.BenefitDAOImpls;
import com.DAOImpl.ConfigurationDAOImpls;
import com.DAOImpl.LineDAOImpls;
import com.DAOImpl.LossTypeDAOImpls;
import com.DAOImpl.PillarDAOImpls;
import com.DAOImpl.PlantDAOImpls;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.ToolDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.User;
import com.helper.SearchDocumentHelper;
import com.helper.UserUtil;
import com.itextpdf.text.log.SysoLogger;


@RestController
public class UserController {
	
	private static final Logger log = Logger.getLogger(UserController.class.getName());
	private static final UserDAO userDao = new UserDAOImpls();

	
	/**
	 * @param request
	 * @param userObj
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody String saveUser(HttpServletRequest request, @RequestParam("userObj") String userObj) throws JsonGenerationException, JsonMappingException, IOException {
		
		HashMap<String,Object>  responseMap= new HashMap<String,Object>();
		String loggedInUserEmail = UserUtil.getCurrentUser();
		User loggedInUser = userDao.getUserByEmail(loggedInUserEmail);
		if(loggedInUser == null || !(loggedInUser.getRoles().contains("SUPERADMIN"))){
			responseMap.put("STATUS", "FAILURE");
			responseMap.put("MESSAGE", "You do not have permissions to perform this operation");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		UserDAO userDao = new UserDAOImpls();
		
		//UserJDO userJdoObj = new UserJDO();
		Long id = 0L;
		try {
			log.info("userObj: "+userObj);
			User userJdoObj = new ObjectMapper().readValue(userObj.toString(), new TypeReference<User>() {});
			id = userJdoObj.getId();
			log.info("ID: "+id);
			System.out.println("ID: "+id);
			userJdoObj.setUserEmail(userJdoObj.getUserEmail().toLowerCase());
			if(id != null) {
				System.out.println("existing user");
				log.info("existing user");
				userJdoObj.setLastUpdatedBy(UserUtil.getCurrentUser());
				userJdoObj.setLastUpdatedOn(new Date().getTime());
				userDao.saveUser(userJdoObj);
				responseMap.put("STATUS", "SUCCESS");
				responseMap.put("MESSAGE", "Updated User Succesfully");
				responseMap.put("data", userJdoObj);
			} else {
				User user = userDao.getUserByEmail(userJdoObj.getUserEmail().toLowerCase().trim());
				if(user!=null) {
					log.info("new user");
					responseMap.put("STATUS", "FAILURE");
					responseMap.put("MESSAGE", "User Already Exist");
					responseMap.put("data", userJdoObj);
				}
				else {
					log.info("new user");
					responseMap.put("STATUS", "SUCCESS");
					responseMap.put("MESSAGE", "Created User Succesfully");
					responseMap.put("data", userJdoObj);
					userDao.saveUser(userJdoObj);
				}
				//status = "Save";
				
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in UserController while saving " + e.getMessage());
			return "error";
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	/**
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value ="/fetchUserList", method = RequestMethod.GET)
	public @ResponseBody String fetchUserList(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		UserDAO userDao = new UserDAOImpls();
		User userJdo = null;
		List<User> userJdoList = null;
		HashMap<String, Object> resultMap = null;
		Long count = 0L;
		try {
			
			resultMap = new HashMap<>();
			userJdoList = new ArrayList<>();
			userJdo = new User();
			count = userDao.countUsers();
			if(count != 0) {
				
				userJdoList = userDao.fetchUserList();
				
				if(userJdoList.size()>0) {
					
					resultMap.put("data", userJdoList);
					resultMap.put("recordsTotal", count);
					resultMap.put("recordsFiltered", "");
				}
			}else {
				
				resultMap.put("data", "");
				resultMap.put("recordsTotal", count);
				resultMap.put("recordsFiltered", "");
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return new ObjectMapper().writeValueAsString(resultMap);
	}
	
	@RequestMapping(value="/delete/{ids}",method=RequestMethod.POST)
	public @ResponseBody String deleteUserByIds(@PathVariable("ids") List<String> idListStr) throws Exception
	{
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		UserDAO userDao = new UserDAOImpls();
		String resultMap = null;
		try
		{
			HashMap<String,String> failedList = new HashMap<String,String>();
			for(String id:idListStr) {
				failedList.put(id,userDao.deleteUser(Long.parseLong(id)));
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
	
	@RequestMapping(value = "/listadmindetails/{indexname}/{sortfield}/{filterfield}/{filtervalue}", method = RequestMethod.GET)
	public @ResponseBody String listadmindetails(@PathVariable("indexname") String searchIndex,@PathVariable("sortfield") String sortfield,@PathVariable("filterfield") String filterfield,@PathVariable("filtervalue") String filtervalue,HttpServletRequest req,
			@RequestParam("start") int start,
			@RequestParam("length") int length,
			@RequestParam("search[value]") String searchStr,
			@RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderOptions) throws JsonParseException, JsonMappingException, IOException 
	{
		String masterType="";
		if(req.getParameter("configuration") ==null || req.getParameter("configuration").equals(""))
			masterType="";
		else
			masterType=  req.getParameter("configuration");
		String userEmail = UserUtil.getCurrentUser();
		UserDAO objUserDAO = new UserDAOImpls();
		User currentUser = objUserDAO.getUserByEmail(userEmail.trim());
		PlantDAO objPlantDAO = new PlantDAOImpls();
		PillarDAO objPillarDAO = new PillarDAOImpls();
		AreaDAO objAreaDAO = new AreaDAOImpls();
		LineDAO objLineDAO = new LineDAOImpls();
		BenefitDAO objBenefitDAO = new BenefitDAOImpls();
		LossTypeDAO objLossTypeDAO = new LossTypeDAOImpls();
		ToolDAO objToolDAO = new ToolDAOImpls();
		RequestDAO objRequestDAO = new RequestDAOImpl();
		ConfigurationDAO objConfigurationDAO = new ConfigurationDAOImpls();
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		System.out.println(filterfield+" --- "+filtervalue);
		String[] columns = sortfield.split(",");
		String[] filedsArray = Arrays.copyOfRange(columns, 1, columns.length-1);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();
		Long resultSize = 0L;
		long count=0L;
		System.out.println("******"+orderColumn+"******");
		if(searchIndex.equals("REQUEST") && orderColumn>=15 && orderColumn<=18)
			orderColumn=orderColumn;
		else if(searchIndex.equals("REQUEST") && orderColumn>=19 )
			orderColumn=orderColumn-1;
		else
		orderColumn=orderColumn+1;
		boolean isPlantManagerOnly=false;
		try
		{
			String order = "true";
			if(orderOptions.equals("desc"))
				order="false";
			
			log.info("columns  : "+Arrays.toString(columns)+" filedsArray "+Arrays.toString(filedsArray));
			System.out.println(orderColumn+"  order column -- searchindex name  "+searchIndex+"  orderOptions  -- "+orderOptions +"order "+orderOptions);
			if(searchIndex != null && !searchIndex.equals(""))
			{
				System.out.println("IndexName here ---> "+searchIndex);
				if(searchIndex.equals("USER")) {
					
					if(currentUser.getRoles().contains("SUPERADMIN") || currentUser.getRoles().contains("PLANTMANAGER")) {
						count =objUserDAO.countUsers() ;
						if(!currentUser.getRoles().contains("SUPERADMIN")) {
							System.out.println("here true");
							isPlantManagerOnly=true;
						}
					}else {
						count=0;
					}
				}
				else if(searchIndex.equals("PLANT"))
					count = objPlantDAO.countPlant();
				else if(searchIndex.equals("PILLAR"))
					count = objPillarDAO.countPillar();
				else if(searchIndex.equals("AREA"))
					count = objAreaDAO.countArea();
				else if(searchIndex.equals("LINE"))
					count = objLineDAO.countLine();
				else if(searchIndex.equals("BENEFIT"))
					count = objBenefitDAO.countBenefit();
				else if(searchIndex.equals("TOOL"))
					count = objToolDAO.countTool();
				else if(searchIndex.equals("LOSSTYPE"))
					count = objLossTypeDAO.countLossType();
				else if(searchIndex.equals("REQUEST")) {
					count=objRequestDAO.countRequest();
					if(currentUser.getRoles().contains("SUPERADMIN")) {
						  filterfield ="";filtervalue="";
					}
				}
				else {
					count = objConfigurationDAO.countConfiguration(masterType);
				}
					
				
				log.info("COunt HERE --- "+count);
			}
			
			System.out.println(currentUser.getRoles().contains("SUPERADMIN") +"searchfield and value "+filterfield+" "+filtervalue);
			if(count > 0)
			{
					if(!searchStr.isEmpty()){
						List<String > queryString = new ArrayList<String>();
						for(String str :filedsArray){
							queryString.add(str+" = \""+searchStr+"\"");
						}
						List<String > queryString1 = new ArrayList<String>();
						for(String str :filedsArray){
							queryString1.add(str+"_1 = \""+searchStr+"\"");
						}
						/*if(isPlantManagerOnly) {
							queryString1.add
						}*/
							
						resultMap = SearchDocumentHelper.getListFromSearchIndex(searchIndex, StringUtils.collectionToDelimitedString(queryString, " OR "),StringUtils.collectionToDelimitedString(queryString1, " OR "),columns[orderColumn], order,length,start,filterfield,filtervalue,isPlantManagerOnly,currentUser,masterType);
					}else{
						resultMap = SearchDocumentHelper.getListFromSearchIndex(searchIndex, searchStr,"",columns[orderColumn], order,length,start,filterfield,filtervalue,isPlantManagerOnly,currentUser,masterType);
					}
						
					dataList =(List<HashMap<String,Object>>)resultMap.get("data");
				 	resultSize = (Long) resultMap.get("recordsFiltered");
					responseMap.put("data", dataList);
					responseMap.put("recordsFiltered", resultSize);
					responseMap.put("recordsTotal", count);
					if(dataList.size() == 0)
					{
						responseMap.put("data","");
						responseMap.put("recordsTotal",count);
						responseMap.put("recordsFiltered",count);
					}
			}else{
				
				responseMap.put("data","");
				responseMap.put("recordsTotal",0);
				responseMap.put("recordsFiltered",0);
			}
			
			
			//responseMap.put("data", dataList);
			
			System.out.println(new ObjectMapper().writeValueAsString(responseMap));
			return new ObjectMapper().writeValueAsString(responseMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(Level.SEVERE,e.getStackTrace()+" Exception occurred - SaveXCn : "+e);
			responseMap.put("data","");
			responseMap.put("STATUS", "FAILED");
			responseMap.put("MESSAGE","Something Went Wrong, Please contact Administrator");
			return new ObjectMapper().writeValueAsString(responseMap);
		}
	}
	
}
