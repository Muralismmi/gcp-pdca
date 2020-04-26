package com.DAOImpl;


import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.DAO.AttachmentsDAO;
import com.entity.Attachments;
import com.entity.Plant;
import com.helper.PersistenceManagerUtil;


public class AttachmentsDAOImpls implements AttachmentsDAO {

	private static Logger log = Logger
			.getLogger(AttachmentsDAOImpls.class.getPackage().getName());
	
	
	@Override
	public Attachments saveToFirestore(HashMap<String,Object> attachmentObj) 
	{
		
		PersistenceManager pm	= null;
		Attachments obj =null;
		try {
			obj= new ObjectMapper().convertValue(attachmentObj, Attachments.class);
			 pm	= PersistenceManagerUtil.get().getPersistenceManager();
			  pm.makePersistent(obj);
		}
	catch(Exception e)
	{
		//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
	log.info("Error in save savePlant  ................."+e.getMessage()+"Exception : "+e);
	e.printStackTrace();
	}
   finally
   {
	   if(pm!=null)
		   pm.close();
   }
		
		return obj;
	}
	

	@Override
	public Attachments fetchById(String id) 
	{
		Attachments obj = null;
		PersistenceManager pm = null;
		
		try {
			
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm.newQuery("SELECT FROM "+Attachments.class.getName()+ " WHERE attachmentId =="+id+" & active =="+true );
			List<Attachments> plantList = (List<Attachments>) queryStr.execute();
			
			if(plantList != null && plantList.size() > 0) {
				
				obj = plantList.get(0);
			}
		} catch(Exception e)
		{
			//SendGridUtil.notifyDev(RolesDAO.class.getName()+" (Method - RolesDAO) : "+e, Arrays.toString(e.getStackTrace()));
		log.info("Error in save savePlant  ................."+e.getMessage()+"Exception : "+e);
		}
	   finally
	   {
		   if(pm!=null)
			   pm.close();
	   }
			
			return obj;
		
	}

}
