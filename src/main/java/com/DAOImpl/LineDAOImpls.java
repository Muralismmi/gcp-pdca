package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;
import com.DAO.LineDAO;
import com.entity.Line;
import com.helper.PersistenceManagerUtil;
import com.helper.SearchDocumentHelper;

public class LineDAOImpls implements LineDAO{
	private static final 	Logger log 	= 		Logger.getLogger(LineDAOImpls.class.getName());

	@Override
	public void saveLine(Line objLine) {
		PersistenceManager pm	= null;
		try
		{
		  pm	= PersistenceManagerUtil.get().getPersistenceManager();
		  pm.makePersistent(objLine);
		  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
		  forSearchIndex.add(new ObjectMapper().convertValue(objLine,HashMap.class));
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
		  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "LINE", props, "id", textFields);
		}
		catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save saveLine  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
		
	}

	@Override
	public List<Line> fetchAll() {
		PersistenceManager pm = null;
		 List<Line>  LineList  =new ArrayList<Line>();
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Line.class.getName()+ " WHERE active =="+true);
			   LineList   =  (List<Line>) querystr.execute();
			/* if(LineList != null && LineList.size()> 0) {
				 LineObj = LineList.get(0);
			 }*/
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in fetchAll  ................."+e.getMessage()+"Exception : "+e);
		}
		return LineList;
	}

	@Override
	public Line getLineByName(String name) {
		PersistenceManager pm = null;
		Line  LineObj = null;
		try {
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Line.class.getName()+ " WHERE value == '"+name.trim()+"' & active =="+true);
			 List<Line>  LineList   =  (List<Line>) querystr.execute();
			 if(LineList != null && LineList.size()> 0) {
				 LineObj = LineList.get(0);
			 }
		} catch(Exception e) {
			e.printStackTrace();
			log.info("Error in getLineByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LineObj;
	}
	@Override
	public Line getLineByNameandPlant(String name,String plant) {
		PersistenceManager pm = null;
		Line  LineObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Line.class.getName()+ " WHERE value == '"+name.trim()+"' & plant == '"+plant+"' & active =="+true);
			 List<Line>  LineList   =  (List<Line>) querystr.execute();
			 if(LineList != null && LineList.size()> 0) {
				 LineObj = LineList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getLineByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LineObj;
	}
	@Override
	public Line fetchLineById(Long id) {
		PersistenceManager pm = null;
		Line  LineObj = null;
		try {
			
			//User = new ArrayList<User>();
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			 Query querystr = pm.newQuery("SELECT FROM "+Line.class.getName()+ " WHERE id =="+id);
			 List<Line>  LineList   =  (List<Line>) querystr.execute();
			 if(LineList != null && LineList.size()> 0) {
				 LineObj = LineList.get(0);
			 }
		} catch(Exception e) {
			
			e.printStackTrace();
			log.info("Error in getLineByName  ................."+e.getMessage()+"Exception : "+e);
		}
		return LineObj;
	}

	@Override
	public String deleteLine(long id) {
		PersistenceManager pm = null;
		Line LineObj = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Line.class.getName()+" WHERE id =="+id);
			List<Line> LineList = (List<Line>) queryStr.execute();
			
			if(LineList != null && LineList.size() > 0) {
				LineObj=LineList.get(0);
				LineObj.setActive(false);
				
				pm.makePersistent(LineObj);
				  List<HashMap<String,Object>> forSearchIndex = new ArrayList<HashMap<String,Object>>();
				  forSearchIndex.add(new ObjectMapper().convertValue(LineObj,HashMap.class));
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
				  SearchDocumentHelper.createFullSearchDocument(forSearchIndex, "LINE", props, "id", textFields);
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
	public Long countLine() {
		Long count=0L;
		PersistenceManager pm = null;
		try
		{
			pm=PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr=pm.newQuery("SELECT count(id) FROM "+Line.class.getName()+" WHERE active =="+true);
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
