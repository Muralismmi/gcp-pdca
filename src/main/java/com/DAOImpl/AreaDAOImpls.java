package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.AreaDAO;
import com.entity.Area;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class AreaDAOImpls implements AreaDAO{
	private static final 	Logger log 	= 		Logger.getLogger(AreaDAOImpls.class.getName());

	@Override
	public void saveArea(Area objArea) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objArea);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objArea,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("value");
		  props.add("plant");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "AREA", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveArea  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<Area> fetchAll() {
		PersistenceManager pm = null;
		 List<Area>  AreaList  =new ArrayList<Area>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Area.class.getName()+ " WHERE active =="+true);
			   AreaList   =  (List<Area>) querystr.execute();
			/* if(AreaList != null && AreaList.size()> 0) {
				 AreaObj = AreaList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in fetchAll  ................."+e.getMessage()+"Exception : "+e);
		}
		return AreaList;
	}

	@Override
	public Area getAreaByName(String name) {
		PersistenceManager pm = null;
		Area  AreaObj = null;
		try {
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Area.class.getName()+ " WHERE value == '"+name.trim()+"' & active =="+true);
			 List<Area>  AreaList   =  (List<Area>) querystr.execute();
			 if(AreaList != null && AreaList.size()> 0) {
				 AreaObj = AreaList.get(0);
			 }
		} catch(Exception e) {
			e.printStackTrace();
			log.info("Error in getAreaByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return AreaObj;
	}
	@Override
	public Area getAreaByNameandPlant(String name,String plant) {
		PersistenceManager pm = null;
		Area  AreaObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Area.class.getName()+ " WHERE value == '"+name.trim()+"' & plant == '"+plant+"' & active =="+true);
			 List<Area>  AreaList   =  (List<Area>) querystr.execute();
			 if(AreaList != null && AreaList.size()> 0) {
				 AreaObj = AreaList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getAreaByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return AreaObj;
	}
	@Override
	public Area fetchAreaById(Long id) {
		PersistenceManager pm = null;
		Area  AreaObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Area.class.getName()+ " WHERE id =="+id);
			 List<Area>  AreaList   =  (List<Area>) querystr.execute();
			 if(AreaList != null && AreaList.size()> 0) {
				 AreaObj = AreaList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getAreaByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return AreaObj;
	}

	@Override
	public String deleteArea(long id) {
		PersistenceManager pm = null;
		Area AreaObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Area.class.getName()+" WHERE id =="+id);
			List<Area> AreaList = (List<Area>) queryStr.execute();
			
			if(AreaList != null && AreaList.size() > 0) {
				AreaObj=AreaList.get(0);
				AreaObj.setActive(false);
				
				pm.makePersistent(AreaObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(AreaObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("value");
				  props.add("plant");
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "AREA", props, "id", textFields);
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
	public Long countArea() {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Area.class.getName()+" WHERE active =="+true);
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
