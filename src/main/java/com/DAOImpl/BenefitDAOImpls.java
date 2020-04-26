package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.BenefitDAO;
import com.entity.Benefit;
import com.entity.Plant;
import com.entity.User;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class BenefitDAOImpls implements BenefitDAO{
	private static final 	Logger log 	= 		Logger.getLogger(BenefitDAOImpls.class.getName());

	@Override
	public void saveBenefit(Benefit objBenefit) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objBenefit);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objBenefit,HashMap.class));
		  List<String> textFields = new ArrayList<String>();
		  List<String> props = new ArrayList<String>();
		  props.add("id");
		  props.add("value");
		  props.add("active");
		  props.add("createdBy");
		  props.add("lastUpdatedBy");
		  props.add("createdOn");
		  props.add("lastUpdatedOn");
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "BENEFIT", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveBenefit  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<Benefit> fetchAll() {
		PersistenceManager pm = null;
		 List<Benefit>  BenefitList  =new ArrayList<Benefit>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Benefit.class.getName()+ " WHERE active =="+true);
			   BenefitList   =  (List<Benefit>) querystr.execute();
			/* if(BenefitList != null && BenefitList.size()> 0) {
				 BenefitObj = BenefitList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in fetchAll  ................."+e.getMessage()+"Exception : "+e);
		}
		return BenefitList;
	}

	@Override
	public Benefit getBenefitByName(String name) {
		PersistenceManager pm = null;
		Benefit  BenefitObj = null;
		try {
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Benefit.class.getName()+ " WHERE value == '"+name.trim()+"' & active =="+true);
			 List<Benefit>  BenefitList   =  (List<Benefit>) querystr.execute();
			 if(BenefitList != null && BenefitList.size()> 0) {
				 BenefitObj = BenefitList.get(0);
			 }
		} catch(Exception e) {
			e.printStackTrace();
			log.info("Error in getBenefitByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return BenefitObj;
	}
	@Override
	public Benefit getBenefitByNameandPlant(String name) {
		PersistenceManager pm = null;
		Benefit  BenefitObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Benefit.class.getName()+ " WHERE value == '"+name.trim()+" & active =="+true);
			 List<Benefit>  BenefitList   =  (List<Benefit>) querystr.execute();
			 if(BenefitList != null && BenefitList.size()> 0) {
				 BenefitObj = BenefitList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getBenefitByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return BenefitObj;
	}
	@Override
	public Benefit fetchBenefitById(Long id) {
		PersistenceManager pm = null;
		Benefit  BenefitObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Benefit.class.getName()+ " WHERE id =="+id);
			 List<Benefit>  BenefitList   =  (List<Benefit>) querystr.execute();
			 if(BenefitList != null && BenefitList.size()> 0) {
				 BenefitObj = BenefitList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getBenefitByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return BenefitObj;
	}

	@Override
	public String deleteBenefit(long id) {
		PersistenceManager pm = null;
		Benefit BenefitObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Benefit.class.getName()+" WHERE id =="+id);
			List<Benefit> BenefitList = (List<Benefit>) queryStr.execute();
			
			if(BenefitList != null && BenefitList.size() > 0) {
				BenefitObj=BenefitList.get(0);
				BenefitObj.setActive(false);
				
				pm.makePersistent(BenefitObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(BenefitObj,HashMap.class));
				  List<String> textFields = new ArrayList<String>();
				  List<String> props = new ArrayList<String>();
				  props.add("id");
				  props.add("value");
				  props.add("active");
				  props.add("createdBy");
				  props.add("lastUpdatedBy");
				  props.add("createdOn");
				  props.add("lastUpdatedOn");
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "BENEFIT", props, "id", textFields);
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
	public Long countBenefit() {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Benefit.class.getName()+" WHERE active =="+true);
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
