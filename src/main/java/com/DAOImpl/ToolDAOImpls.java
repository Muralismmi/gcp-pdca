package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.ToolDAO;
import com.entity.Tool;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class ToolDAOImpls implements ToolDAO{
	private static final 	Logger log 	= 		Logger.getLogger(ToolDAOImpls.class.getName());

	@Override
	public void saveTool(Tool objTool) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objTool);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objTool,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("value");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "TOOL", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveTool  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<Tool> fetchAll() {
		PersistenceManager pm = null;
		 List<Tool>  ToolList  =new ArrayList<Tool>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Tool.class.getName()+ " WHERE active =="+true);
			   ToolList   =  (List<Tool>) querystr.execute();
			/* if(ToolList != null && ToolList.size()> 0) {
				 ToolObj = ToolList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in fetchAll  ................."+e.getMessage()+"Exception : "+e);
		}
		return ToolList;
	}

	@Override
	public Tool getToolByName(String name) {
		PersistenceManager pm = null;
		Tool  ToolObj = null;
		try {
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Tool.class.getName()+ " WHERE value == '"+name.trim()+"' & active =="+true);
			 List<Tool>  ToolList   =  (List<Tool>) querystr.execute();
			 if(ToolList != null && ToolList.size()> 0) {
				 ToolObj = ToolList.get(0);
			 }
		} catch(Exception e) {
			e.printStackTrace();
			log.info("Error in getToolByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return ToolObj;
	}
	@Override
	public Tool getToolByNameandPlant(String name) {
		PersistenceManager pm = null;
		Tool  ToolObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Tool.class.getName()+ " WHERE value == '"+name.trim()+" & active =="+true);
			 List<Tool>  ToolList   =  (List<Tool>) querystr.execute();
			 if(ToolList != null && ToolList.size()> 0) {
				 ToolObj = ToolList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getToolByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return ToolObj;
	}
	@Override
	public Tool fetchToolById(Long id) {
		PersistenceManager pm = null;
		Tool  ToolObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Tool.class.getName()+ " WHERE id =="+id);
			 List<Tool>  ToolList   =  (List<Tool>) querystr.execute();
			 if(ToolList != null && ToolList.size()> 0) {
				 ToolObj = ToolList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getToolByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return ToolObj;
	}

	@Override
	public String deleteTool(long id) {
		PersistenceManager pm = null;
		Tool ToolObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Tool.class.getName()+" WHERE id =="+id);
			List<Tool> ToolList = (List<Tool>) queryStr.execute();
			
			if(ToolList != null && ToolList.size() > 0) {
				ToolObj=ToolList.get(0);
				ToolObj.setActive(false);
				
				pm.makePersistent(ToolObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(ToolObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("value");
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "TOOL", props, "id", textFields);
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
	public Long countTool() {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Tool.class.getName()+" WHERE active =="+true);
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
