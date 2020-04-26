package com.helper;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.ConfigurationDAO;
import com.DAO.PlantDAO;
import com.DAO.UserDAO;
import com.DAO.WorkPermitTypeDAO;
import com.DAOImpl.ConfigurationDAOImpls;
import com.DAOImpl.PlantDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.DAOImpl.WorkPermitTypeDAOImpls;
import com.entity.Configuration;
import com.entity.Plant;
import com.entity.User;
import com.entity.WorkPermitType;
import com.fasterxml.jackson.core.JsonParser;
import com.google.api.client.json.Json;
import com.google.appengine.api.users.UserServiceFactory;
import com.itextpdf.text.log.SysoLogger;



public class UserUtil{
	
	private static final 	Logger 	log  = 	Logger.getLogger(UserUtil.class.getName()); 

	/*========================== retrieve current logged in user ===========================================*/
	public static String getCurrentUser()
	{
		String userMailId	=	"";
		try
		{
			com.google.appengine.api.users.User user = UserServiceFactory.getUserService().getCurrentUser();
		    if(user.getEmail()!=null && !"".equals(user.getEmail()))
		    {
		    	log.info("session is there");
		    	userMailId = user.getEmail().toLowerCase().trim();
		    }
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
		}
		return userMailId;
	}
	
	public static String getDate(Long dateStamp)                    //1487203200000 to 16-02-2017 (Eg)
	{ 
		 	String datout=null;
			try
			{
				Date newDate= new Date(dateStamp);
				System.out.println("Date for timestamp :::"+newDate);
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				datout=sdf.format(dateStamp);
				System.out.println("here"+datout);
			}
			catch(Exception e)
			{
				System.out.println("Error in getDate()"+e.getMessage());
				log.log(Level.SEVERE,e.getMessage());
			}
			return datout;
	}
	
	public static String getDateyyyy(Long dateStamp)                    //1487203200000 to 16-02-2017 (Eg)
	{ 
		 	String datout=null;
			try
			{
				Date newDate= new Date(dateStamp);
				System.out.println("Date for timestamp :::"+newDate);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				datout=sdf.format(dateStamp);
				System.out.println("here"+datout);
			}
			catch(Exception e)
			{
				System.out.println("Error in getDate()"+e.getMessage());
				log.log(Level.SEVERE,e.getMessage());
			}
			return datout;
	}
	
	public static String getMonthandYear()                    //1487203200000 to 16-02-2017 (Eg)
	{ 
		String month = "";
		String year = "";
		Date date = new Date();
	    SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("EEEE");
	    SimpleDateFormat simpleDateFormatmonth = new SimpleDateFormat("EEEE");
	    simpleDateFormatmonth = new SimpleDateFormat("MMMM");
	    month = simpleDateFormatmonth.format(date).toUpperCase();
	    simpleDateFormatYear = new SimpleDateFormat("YYYY");
	    year = simpleDateFormatYear.format(date).toUpperCase();
		return month + " - "+year;
	}
	
	public static double getamountwithComma(double d){
		DecimalFormat decimalFormat = new DecimalFormat("#.##"); 
		decimalFormat.setGroupingUsed(true); 
		decimalFormat.setGroupingSize(3);
		decimalFormat.format(d);
		return d;
	}
	
	public static boolean checkmail(String mail)
	{
		
		String EMAIL_REGEX = 
		        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Boolean b = mail.matches(EMAIL_REGEX);
		return b;
	}
	
   /* public static boolean isJSONValid(String test)
    {
        try 
        {
            new JSONObject(test);
        } 
        catch (JSONException ex) 
        {
            try 
            {
              new JSONArray(test);
            } catch (JSONException ex1) 
            {
                return false;
            }
        }
        return true;
    }
 
    
    public String constructOauthData(String code, String client_id, String remoteHost, String client_secret, String grant_type) 
    {
        String data = null;
        try 
        {
            data = "code=" + code + "&client_id=" + client_id + "&redirect_uri=" + remoteHost + "/oauth2callback" + "&client_secret=" + client_secret + "&grant_type=" + grant_type;
        }
        catch (Exception e) 
        {
            log.info("Exception constructOauthData ::" + e);
        }
        log.info("the data retunred is ::" + data);
        return data;
    }

    public String getAccessToken(String data, String URL2) 
    {
        String tokenResponse = null;
        String accessToken = null;
        Map result = null;
        ObjectMapper objectMapper = null;
        try 
        {
            log.info("data :: " + data);
            log.info("URL :: " + URL2);
            result = new HashMap();
            objectMapper = new ObjectMapper();
            tokenResponse = new UrlFetch().httpPost(data, URL2, "POST");
            result = (Map)objectMapper.readValue(tokenResponse, (Class)HashMap.class);
            log.info("Result ::" + result);
            accessToken = result.get("access_token").toString();
        }
        catch (Exception e) 
        {
            log.info("Exception getAccessToken :: " + e.getMessage());
        }
        return accessToken;
    }

    public JSONObject callGoogleAPI(String callGoogleAPIURL, String accessToken, String APIkey) 
    {
        JSONObject getresponse = null;
        try 
        {
            getresponse = new UrlFetch().httpGET(String.valueOf(callGoogleAPIURL) + accessToken + "&key=" + APIkey, 0);
        }
        catch (Exception e) 
        {
            log.info("Exception callGoogleAPI :: " + e.getMessage());
        }
        return getresponse;
    }
    

    public String constructRedirectURL(String state, String authenticateURL, String remoteHost, String response_type, String client_id, String approval_prompt) 
    {
        String oAuthURL = null;
        try 
        {
            oAuthURL = String.valueOf(authenticateURL) + state + "&redirect_uri=" + remoteHost + "/oauth2callback" + "&response_type=" + response_type + "&client_id=" + client_id + "&approval_prompt=" + approval_prompt;
        }
        catch (Exception e) 
        {
            log.info("Exception constructRedirectURL :: " + e.getMessage());
        }
        return oAuthURL;
    }
    
    public static String FormFirstandLastname(String email){
		
   	 if(email != null && email != "")
   	 {
       	String Emailwithoutdoamin = email.split("@")[0];
       	log.info(""+email);
       	String firstname = Emailwithoutdoamin.split("[@._]")[0];
       	firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
       	String Lastname = Emailwithoutdoamin.split("[@._]")[1];
       	Lastname = Lastname.substring(0,1).toUpperCase() + Lastname.substring(1).toLowerCase();
       	
       	return firstname + " " + Lastname;
   	 }
   	 else
   	 {
   		 return "";
   	 }
       	
       }*/
	
	
	public Object setLocalPermission() throws JsonProcessingException, IOException 
	{//superadmindetails.json
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		try
		{
				InputStream serviceAccount = UserUtil.class.getResourceAsStream("/superadmindetails.json");
				ObjectMapper objectMapper = new ObjectMapper();
				List<HashMap> userlist = objectMapper.readValue(serviceAccount, ArrayList.class);
				UserDAO userDao = new UserDAOImpls();
				for(HashMap user:userlist) {
					User objuser = objectMapper.convertValue(user, User.class);
					Object userfromDB = userDao.getUserByEmail(objuser.getUserEmail().trim());
					if(userfromDB !=null) {
						System.out.println("User Already Exist -- > {UserEmail}"+objuser.getUserEmail());
					}
					else {
						objuser.setCreatedBy(UserUtil.getCurrentUser());
						//objuser.setCreatedOn(new Date().getTime());
						objuser.setLastUpdatedBy(UserUtil.getCurrentUser());
						//objuser.setLastUpdatedOn(new Date().getTime());
						userDao.saveUser(objuser);
						System.out.println("Creating user --> {UserEmail}"+objuser.getUserEmail());
					}
					}
					responseMap.put("data", null);
					responseMap.put("Status", "Success");
					responseMap.put("Message", "SuperAdmin Permissions Added successfully");
			}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(Level.SEVERE,e.getStackTrace()+" Exception occurred setLocalPermission : "+e);
			responseMap.put("data",null);
			responseMap.put("Status", "Failure");
			responseMap.put("Message","Something Went Wrong, Please contact Administrator");
		}
		return responseMap;
	}
	
	public Object setPlantInitial() throws JsonProcessingException, IOException 
	{//superadmindetails.json
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		try
		{
				InputStream serviceAccount = UserUtil.class.getResourceAsStream("/plantmasterdata.json");
				ObjectMapper objectMapper = new ObjectMapper();
				List<HashMap> userlist = objectMapper.readValue(serviceAccount, ArrayList.class);
				PlantDAO userDao = new PlantDAOImpls();
				for(HashMap user:userlist) {
					Plant objuser = objectMapper.convertValue(user, Plant.class);
					Object userfromDB = userDao.getPlantbyName(objuser.getName().trim());
					if(userfromDB !=null) {
						System.out.println("User Already Exist -- > {UserEmail}"+objuser.getName());
					}
					else {
						objuser.setCreatedBy(UserUtil.getCurrentUser());
						//objuser.setCreatedOn(new Date().getTime());
						objuser.setLastUpdatedBy(UserUtil.getCurrentUser());
						//objuser.setLastUpdatedOn(new Date().getTime());
						userDao.savePlant(objuser);
						System.out.println("Creating Plant --> {UserEmail}"+objuser.getName());
					}
					}
					responseMap.put("data", null);
					responseMap.put("Status", "Success");
					responseMap.put("Message", "SuperAdmin Permissions Added successfully");
			}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(Level.SEVERE,e.getStackTrace()+" Exception occurred setLocalPermission : "+e);
			responseMap.put("data",null);
			responseMap.put("Status", "Failure");
			responseMap.put("Message","Something Went Wrong, Please contact Administrator");
		}
		return responseMap;
	}

	public Object setPillarInitial() throws JsonProcessingException, IOException 
	{//superadmindetails.json
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		try
		{
				InputStream serviceAccount = UserUtil.class.getResourceAsStream("/pillarmasterdetails.json");
				ObjectMapper objectMapper = new ObjectMapper();
				List<HashMap> userlist = objectMapper.readValue(serviceAccount, ArrayList.class);
				ConfigurationDAO userDao = new ConfigurationDAOImpls();
				for(HashMap user:userlist) {
					Configuration objuser = objectMapper.convertValue(user, Configuration.class);
					Object userfromDB = userDao.getConfigurationByName(objuser.getValue().trim());
					if(userfromDB !=null) {
						System.out.println("User Already Exist -- > {UserEmail}"+objuser.getValue());
					}
					else {
						objuser.setCreatedBy(UserUtil.getCurrentUser());
						//objuser.setCreatedOn(new Date().getTime());
						objuser.setLastUpdatedBy(UserUtil.getCurrentUser());
						//objuser.setLastUpdatedOn(new Date().getTime());
						userDao.saveConfiguration(objuser);
						System.out.println("Creating Plant --> {UserEmail}"+objuser.getValue());
					}
					}
					responseMap.put("data", null);
					responseMap.put("Status", "Success");
					responseMap.put("Message", "SuperAdmin Permissions Added successfully");
			}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(Level.SEVERE,e.getStackTrace()+" Exception occurred setLocalPermission : "+e);
			responseMap.put("data",null);
			responseMap.put("Status", "Failure");
			responseMap.put("Message","Something Went Wrong, Please contact Administrator");
		}
		return responseMap;
	}
	public Object createForm() throws JsonProcessingException, IOException 
	{//superadmindetails.json
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		try
		{
				InputStream serviceAccount = UserUtil.class.getResourceAsStream("/FormDetails.json");
				ObjectMapper objectMapper = new ObjectMapper();
				List<HashMap> userlist = objectMapper.readValue(serviceAccount, ArrayList.class);
				WorkPermitTypeDAO userDao = new WorkPermitTypeDAOImpls();
				for(HashMap user:userlist) {
					WorkPermitType objuser = objectMapper.convertValue(user, WorkPermitType.class);
					
						objuser.setCreatedBy(UserUtil.getCurrentUser());
						objuser.setCreatedDate(new Date().getTime());
						userDao.updatetoFirestore(objuser);
					
					}
					responseMap.put("data", null);
					responseMap.put("Status", "Success");
					responseMap.put("Message", "SuperAdmin Permissions Added successfully");
			}		
		catch(Exception e)
		{
			e.printStackTrace();
			log.log(Level.SEVERE,e.getStackTrace()+" Exception occurred setLocalPermission : "+e);
			responseMap.put("data",null);
			responseMap.put("Status", "Failure");
			responseMap.put("Message","Something Went Wrong, Please contact Administrator");
		}
		return responseMap;
	}
 
}