package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.ConfigurationDAO;
import com.entity.Configuration;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class ConfigurationDAOImpls implements ConfigurationDAO{
	private static final 	Logger log 	= 		Logger.getLogger(ConfigurationDAOImpls.class.getName());

	@Override
	public void saveConfiguration(Configuration objConfiguration) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objConfiguration);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objConfiguration,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("type");
		  props.add("value");
		  props.add("plant");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "CONFIGURATION", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveConfiguration  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<Configuration> getConfigurationByType(String type) {
		PersistenceManager pm = null;
		Configuration  configurationObj = null;
		 List<Configuration>  configurationList  =new ArrayList<Configuration>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Configuration.class.getName()+ " WHERE type == '"+type.trim()+"'");
			   configurationList   =  (List<Configuration>) querystr.execute();
			/* if(configurationList != null && configurationList.size()> 0) {
				 configurationObj = configurationList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getConfigurationByType  ................."+e.getMessage()+"Exception : "+e);
		}
		return configurationList;
	}

	@Override
	public Configuration getConfigurationByName(String name) {
		PersistenceManager pm = null;
		Configuration  configurationObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Configuration.class.getName()+ " WHERE value == '"+name.trim()+"'");
			 List<Configuration>  configurationList   =  (List<Configuration>) querystr.execute();
			 if(configurationList != null && configurationList.size()> 0) {
				 configurationObj = configurationList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getConfigurationByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return configurationObj;
	}
	@Override
	public Configuration getConfigurationByNameandPlant(String name,String plant,String type) {
		PersistenceManager pm = null;
		Configuration  configurationObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Configuration.class.getName()+ " WHERE value == '"+name.trim()+"' & plant == '"+plant+"' & type == '"+type+"' & active =="+true);
			 List<Configuration>  configurationList   =  (List<Configuration>) querystr.execute();
			 if(configurationList != null && configurationList.size()> 0) {
				 configurationObj = configurationList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getConfigurationByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return configurationObj;
	}
	@Override
	public Configuration fetchConfigurationById(Long id) {
		PersistenceManager pm = null;
		Configuration  configurationObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Configuration.class.getName()+ " WHERE id =="+id);
			 List<Configuration>  configurationList   =  (List<Configuration>) querystr.execute();
			 if(configurationList != null && configurationList.size()> 0) {
				 configurationObj = configurationList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getConfigurationByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return configurationObj;
	}

	@Override
	public String deleteConfiguration(long id) {
		PersistenceManager pm = null;
		Configuration configurationObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Configuration.class.getName()+" WHERE id =="+id);
			List<Configuration> configurationList = (List<Configuration>) queryStr.execute();
			
			if(configurationList != null && configurationList.size() > 0) {
				configurationObj=configurationList.get(0);
				configurationObj.setActive(false);
				
				pm.makePersistent(configurationObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(configurationObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("type");
				  props.add("value");
				  props.add("active");
				  props.add("plant");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "CONFIGURATION", props, "id", textFields);
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
	public Long countConfiguration(String type) {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Configuration.class.getName()+" WHERE type =='"+type+"' & active =="+true);
			System.out.println(queryStr);
			count=(Long)queryStr.execute();
			System.out.println("count from here "+count);
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
