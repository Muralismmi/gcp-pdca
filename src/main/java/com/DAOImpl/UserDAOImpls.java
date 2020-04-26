package com.DAOImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;
import com.DAO.UserDAO;
import com.entity.User;

import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;



public class UserDAOImpls implements UserDAO{
	
	private static final 	Logger log 	= 		Logger.getLogger(UserDAOImpls.class.getName());

	@Override
	public void saveUser(User adminJdo) {

		PersistenceManager pm	= null;
		try
		{
		  log.info("RolesJDO:: --- ");
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  adminJdo.setUserEmail(adminJdo.getUserEmail().toLowerCase());
		  pm.makePersistent(adminJdo);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(adminJdo,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("userEmail");
		  props.add("title");
		  props.add("firstName");
		  props.add("lastName");
		  props.add("userName");
		  props.add("plantId");
		  props.add("plantName");
		  props.add("pillarLead");
		  props.add("pillarId");
		  props.add("pillarName");
		  props.add("projectLead");
		  props.add("mentor");
		  props.add("editor");
		  props.add("active");
		  props.add("roles");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "USER", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save RolesJDO  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByEmail(String userMail) {

		PersistenceManager pm = null;
		//List<User> User = null;
		User  UserObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+User.class.getName()+ " WHERE userEmail == '"+userMail+"' & active =="+true);
			 log.info("querystr in getNextStepname Method 121313 ---->"+querystr);
			 List<User>  User   =  (List<User>) querystr.execute();

			 if(User != null && User.size()> 0) {
				 log.info("test1 ");
				 UserObj = User.get(0);
				 log.info("test3");
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getUserByEmail  ................."+e.getMessage()+"Exception : "+e);
		}
		return UserObj;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getuserWithRoleandPlant(String roleName, String PlantName) {

		PersistenceManager pm = null;
		List<String> approverlist =new ArrayList<String>();
		//List<User> User = null;
		try {
			SearchDocumentHelper objSearchDocumentHelper= new SearchDocumentHelper();
			 approverlist = objSearchDocumentHelper.getUserWithRoleforPlant(roleName,PlantName);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getuserWithRoleandPlant  ................."+e.getMessage()+"Exception : "+e);
		}
		return approverlist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User getPillarLeadForPlant(String pillarName, String PlantName) {

		PersistenceManager pm = null;
		//List<User> User = null;
		User  UserObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+User.class.getName()+ " WHERE plantName == '"+PlantName+"' & pillarName == '"+pillarName+"' & active =="+true);
			 log.info("querystr in getNextStepname Method 121313 ---->"+querystr);
			 List<User>  User   =  (List<User>) querystr.execute();

			 if(User != null && User.size()> 0) {
				 log.info("test1 ");
				 UserObj = User.get(0);
				 log.info("test3");
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getUserByEmail  ................."+e.getMessage()+"Exception : "+e);
		}
		return UserObj;
	}
	@Override
	public User fetchUserById(Long id) {
		
		PersistenceManager pm = null;
		User User = new User();
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+User.class.getName()+ " WHERE id =="+id);
			List<User> userObj = (List<User>) queryStr.execute();
			
			if(userObj != null && userObj.size() > 0) {
				
				User = userObj.get(0);
			}
		} catch(Exception e) {
			
			
		}
		return User;
	}
	
	@Override
	public String deleteUser(long id) {
		
		PersistenceManager pm = null;
		User User = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+User.class.getName()+" WHERE id =="+id);
			List<User> userObj = (List<User>) queryStr.execute();
			
			if(userObj != null && userObj.size() > 0) {
				User obj=userObj.get(0);
				obj.setActive(false);
				
				pm.makePersistent(obj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(obj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("userEmail");
				  props.add("title");
				  props.add("firstName");
				  props.add("lastName");
				  props.add("userName");
				  props.add("plantId");
				  props.add("plantName");
				  props.add("pillarLead");
				  props.add("pillarId");
				  props.add("pillarName");
				  props.add("projectLead");
				  props.add("mentor");
				  props.add("editor");
				  props.add("active");
				  props.add("roles");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "USER", props, "id", textFields);
				return "Deleted Succesfully";
			}
			else {
				return "No Record Exist with this ID, please contact Admin";
			}
		} catch(Exception e) {
			
			return "Something Went Wrong Please contact Admin";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> fetchUserList() {
		
		List<User> UserList = null;
		PersistenceManager pm = null;
		try {
			
			UserList = new ArrayList<>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			
			Query queryStr=pm.newQuery("SELECT FROM "+User.class.getName());
			UserList = (List<User>)queryStr.execute();
			log.info("userlist ................."+UserList);
			if(UserList.size()>0) {
				return UserList;
			} else {
				return null;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return UserList;
	}

	@Override
	public Long countUsers(){
		
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+User.class.getName()+" WHERE active =="+true);
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
