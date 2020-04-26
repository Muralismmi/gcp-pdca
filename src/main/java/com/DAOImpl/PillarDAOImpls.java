package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.PillarDAO;
import com.entity.Pillar;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class PillarDAOImpls implements PillarDAO {
	private static final 	Logger log 	= 		Logger.getLogger(PlantDAOImpls.class.getName());
	@Override
	public void savePillar(Pillar pillarJDO) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(pillarJDO);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(pillarJDO,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("pillarType");
		  props.add("name");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "PILLAR", props, "id", textFields);
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
	public Pillar getPillarbyName(String pillarName) {

		PersistenceManager pm = null;
		Pillar  plantObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Pillar.class.getName()+ " WHERE name == '"+pillarName+"' & active =="+true);
			 List<Pillar>  pillarList   =  (List<Pillar>) querystr.execute();
			 if(pillarList != null && pillarList.size()> 0) {
				 plantObj = pillarList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getPlantbyName  ................."+e.getMessage()+"Exception : "+e);
		}
		return plantObj;
	}

	@Override
	public Pillar fetchPillarById(Long id) {
		PersistenceManager pm = null;
		Pillar pillarObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Pillar.class.getName()+ " WHERE id =="+id+" & active =="+true );
			List<Pillar> pillarList = (List<Pillar>) queryStr.execute();
			
			if(pillarList != null && pillarList.size() > 0) {
				
				pillarObj = pillarList.get(0);
			}
		} catch(Exception e) {
			
			
		}
		return pillarObj;
	}

	@Override
	public List<Pillar> fetchPillarList() {
		List<Pillar> pillarList = null;
		PersistenceManager pm = null;
		try {
			
			pillarList = new ArrayList<>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			
			Query queryStr=pm.newQuery("SELECT FROM "+Pillar.class.getName());
			pillarList = (List<Pillar>)queryStr.execute();
			log.info("plantList ................."+pillarList);
			if(pillarList.size()>0) {
				return pillarList;
			} else {
				return null;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		return pillarList;
	}

	@Override
	public String deletePillar(long id) {
		PersistenceManager pm = null;
		Pillar plantObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Pillar.class.getName()+" WHERE id =="+id);
			List<Pillar> plantList = (List<Pillar>) queryStr.execute();
			
			if(plantList != null && plantList.size() > 0) {
				plantObj=plantList.get(0);
				plantObj.setActive(false);
				
				pm.makePersistent(plantObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(plantObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("pillarType");
				  props.add("name");
				  /*props.add("location");
				  props.add("region");*/
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "PILLAR", props, "id", textFields);
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
	public Long countPillar() {

		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Pillar.class.getName()+" WHERE active =="+true);
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
