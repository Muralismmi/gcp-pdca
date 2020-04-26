package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.LossTypeDAO;
import com.entity.LossType;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class LossTypeDAOImpls implements LossTypeDAO{
	private static final 	Logger log 	= 		Logger.getLogger(LossTypeDAOImpls.class.getName());

	@Override
	public void saveLossType(LossType objLossType) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objLossType);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objLossType,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("value");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "LOSSTYPE", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveLossType  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<LossType> fetchAll() {
		PersistenceManager pm = null;
		 List<LossType>  LossTypeList  =new ArrayList<LossType>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+LossType.class.getName()+ " WHERE active =="+true);
			   LossTypeList   =  (List<LossType>) querystr.execute();
			/* if(LossTypeList != null && LossTypeList.size()> 0) {
				 LossTypeObj = LossTypeList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in fetchAll  ................."+e.getMessage()+"Exception : "+e);
		}
		return LossTypeList;
	}

	@Override
	public LossType getLossTypeByName(String name) {
		PersistenceManager pm = null;
		LossType  LossTypeObj = null;
		try {
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+LossType.class.getName()+ " WHERE value == '"+name.trim()+"' & active =="+true);
			 List<LossType>  LossTypeList   =  (List<LossType>) querystr.execute();
			 if(LossTypeList != null && LossTypeList.size()> 0) {
				 LossTypeObj = LossTypeList.get(0);
			 }
		} catch(Exception e) {
			e.printStackTrace();
			log.info("Error in getLossTypeByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LossTypeObj;
	}
	@Override
	public LossType getLossTypeByNameandPlant(String name) {
		PersistenceManager pm = null;
		LossType  LossTypeObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+LossType.class.getName()+ " WHERE value == '"+name.trim()+" & active =="+true);
			 List<LossType>  LossTypeList   =  (List<LossType>) querystr.execute();
			 if(LossTypeList != null && LossTypeList.size()> 0) {
				 LossTypeObj = LossTypeList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getLossTypeByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LossTypeObj;
	}
	@Override
	public LossType fetchLossTypeById(Long id) {
		PersistenceManager pm = null;
		LossType  LossTypeObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+LossType.class.getName()+ " WHERE id =="+id);
			 List<LossType>  LossTypeList   =  (List<LossType>) querystr.execute();
			 if(LossTypeList != null && LossTypeList.size()> 0) {
				 LossTypeObj = LossTypeList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getLossTypeByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LossTypeObj;
	}

	@Override
	public String deleteLossType(long id) {
		PersistenceManager pm = null;
		LossType LossTypeObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+LossType.class.getName()+" WHERE id =="+id);
			List<LossType> LossTypeList = (List<LossType>) queryStr.execute();
			
			if(LossTypeList != null && LossTypeList.size() > 0) {
				LossTypeObj=LossTypeList.get(0);
				LossTypeObj.setActive(false);
				
				pm.makePersistent(LossTypeObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(LossTypeObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("value");
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "LOSSTYPE", props, "id", textFields);
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
	public Long countLossType() {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+LossType.class.getName()+" WHERE active =="+true);
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
