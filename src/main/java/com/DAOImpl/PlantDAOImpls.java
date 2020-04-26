package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.PlantDAO;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class PlantDAOImpls implements PlantDAO {
	private static final 	Logger log 	= 		Logger.getLogger(PlantDAOImpls.class.getName());
	@Override
	public void savePlant(Plant plantJDO) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(plantJDO);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(plantJDO,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("code");
		  props.add("name");
		  props.add("location");
		  props.add("region");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "PLANT", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save savePlant  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public Plant getPlantbyName(String plantName) {

		PersistenceManager pm = null;
		Plant  plantObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Plant.class.getName()+ " WHERE name == '"+plantName+"' & active =="+true);
			 List<Plant>  plantList   =  (List<Plant>) querystr.execute();
			 if(plantList != null && plantList.size()> 0) {
				 plantObj = plantList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getPlantbyName  ................."+e.getMessage()+"Exception : "+e);
		}
		return plantObj;
	}

	@Override
	public Plant fetchPlantById(Long id) {
		PersistenceManager pm = null;
		Plant plantObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Plant.class.getName()+ " WHERE id =="+id+" & active =="+true );
			List<Plant> plantList = (List<Plant>) queryStr.execute();
			
			if(plantList != null && plantList.size() > 0) {
				
				plantObj = plantList.get(0);
			}
		} catch(Exception e) {
			
			
		}
		return plantObj;
	}

	@Override
	public List<Plant> fetchPlantList() {
		List<Plant> plantList = null;
		PersistenceManager pm = null;
		try {
			
			plantList = new ArrayList<>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			
			Query queryStr=pm.newQuery("SELECT FROM "+Plant.class.getName());
			plantList = (List<Plant>)queryStr.execute();
			log.info("plantList ................."+plantList);
			if(plantList.size()>0) {
				return plantList;
			} else {
				return null;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return plantList;
	}

	@Override
	public String deletePlant(long id) {
		PersistenceManager pm = null;
		Plant plantObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Plant.class.getName()+" WHERE id =="+id);
			List<Plant> plantList = (List<Plant>) queryStr.execute();
			
			if(plantList != null && plantList.size() > 0) {
				plantObj=plantList.get(0);
				plantObj.setActive(false);
				
				pm.makePersistent(plantObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(plantObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("code");
				  props.add("name");
				  props.add("location");
				  props.add("region");
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "PLANT", props, "id", textFields);
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
	public Long countPlant() {

		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Plant.class.getName()+" WHERE active =="+true);
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
