package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import com.DAO.IdDao;
import com.DAO.RequestDAO;
import com.entity.Request;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class RequestDAOImpl implements RequestDAO {
	private static final 	Logger log 	= 		Logger.getLogger(RequestDAOImpl.class.getName());
	@Override
	public Request saveRequest(Request requestJDO) {
		PersistenceManager pm	= null;
		Request obj = null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  obj= pm.makePersistent(requestJDO);
		  
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  HashMap<String,Object> objToMap = new ObjectMapper().convertValue(requestJDO,HashMap.class);
		  objToMap.remove("variableFieldData");
		  forSearchIndex.add(objToMap);
		  HashMap<String,Object> variableFieldMap = new ObjectMapper().readValue(requestJDO.getVariableFieldData().getValue(), new TypeReference<HashMap>() {});
		  objToMap.putAll(variableFieldMap);
		  
		  List<String> props = new ArrayList(objToMap.keySet());
		  List<String> textFields = new ArrayList<String>();
		  textFields.add("problemStatement");
		  textFields.add("rejectComments");
		  textFields.add("approveComments");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "REQUEST", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
			log.info("Error in save saveRequest  ................."+e.getMessage()+"Exception : "+e);
			e.printStackTrace();
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
		  // pm.currentTransaction().commit();
	   }
		return obj;
	}
	public String IDGenerator(String formType,Request objRequest) {
		// long unixTime = System.currentTimeMillis() / 1000L;
		/*
		 * Calendar calendar = Calendar.getInstance(); String s =
		 * String.valueOf(calendar.getTimeInMillis()); String id = "RED-"+s ;
		 * return id.trim() ;
		 */
		IdDao obj = new IdDao();
		int nextnum = obj.getNextIdfromDatastore(formType);
		String nextnumString = String.format("%05d", nextnum);
		log.info("the next num is" + nextnumString);

		String red_Id = objRequest.getPlant()+"_"+objRequest.getPrimaryPillar()+"_"+formType+"_"+ nextnumString;
		return red_Id.trim();

	}
	/*public static Request updateUserDetailsinRequestObject(Request objRequest) {
		
		//setProjectLead
		if(isValid())
		return objRequest;
	}*/
	 static boolean isValid(String email) {
	      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
	   }
/*	@Override
	public Request getRequestbyName(String requestName) {

		PersistenceManager pm = null;
		Request  requestObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Request.class.getName()+ " WHERE name == '"+requestName+"'");
			 List<Request>  requestList   =  (List<Request>) querystr.execute();
			 if(requestList != null && requestList.size()> 0) {
				 requestObj = requestList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getRequestbyName  ................."+e.getMessage()+"Exception : "+e);
		}
		return requestObj;
	}*/

	@Override
	public Request fetchRequestById(Long id) {
		PersistenceManager pm = null;
		Request requestObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Request.class.getName()+ " WHERE id =="+id);
			List<Request> requestList = (List<Request>) queryStr.execute();
			
			if(requestList != null && requestList.size() > 0) {
				
				requestObj = requestList.get(0);
			}
		} catch(Exception e) {
			
			
		}
		return requestObj;
	}

	@Override
	public List<Request> fetchRequestList() {
		List<Request> requestList = null;
		PersistenceManager pm = null;
		try {
			
			requestList = new ArrayList<>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			
			Query queryStr=pm.newQuery("SELECT FROM "+Request.class.getName());
			requestList = (List<Request>)queryStr.execute();
			log.info("requestList ................."+requestList);
			if(requestList.size()>0) {
				return requestList;
			} else {
				return null;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return requestList;
	}

	@Override
	public String deleteRequest(long id) {
		PersistenceManager pm = null;
		Request requestObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Request.class.getName()+" WHERE id =="+id);
			List<Request> requestList = (List<Request>) queryStr.execute();
			
			if(requestList != null && requestList.size() > 0) {
				requestObj=requestList.get(0);
				requestObj.setStatus("DELETED");
				
				pm.makePersistent(requestObj);
				List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  HashMap<String,Object> objToMap = new ObjectMapper().convertValue(requestObj,HashMap.class);
				  objToMap.remove("variableFieldData");
				  forSearchIndex.add(objToMap);
				  objToMap.remove("variableFieldData");
				  HashMap<String,Object> variableFieldMap = new ObjectMapper().readValue(requestObj.getVariableFieldData().toString(), new TypeReference<HashMap>() {});
				  objToMap.putAll(variableFieldMap);
				  
				  List<String> props = new ArrayList(objToMap.keySet());
				  List<String> textFields = new ArrayList<String>();
				  textFields.add("problemStatement");
				  textFields.add("rejectComments");
				  textFields.add("approveComments");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "REQUEST", props, "id", textFields);
				return "Deleted Succesfully";
			}
			else {
				return "No Record Exist with this ID, please contact Admin";
			}
		} catch(Exception e) {
			
			return "Something Went Wrong Please contact Admin";
		}
	}

	@Override
	public Long countRequest() {

		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Request.class.getName()+" WHERE status != 'DELETED'");
			count=(Long)queryStr.execute();
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(CountryDAO.class.getName()+" (Method - UserDAO) : "+e, Arrays.toString(e.getStackTrace()));
			log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
		}
		finally
		{
			pm.close();
		}
		return count;
	}

}
