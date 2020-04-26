package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.DAO.WorkPermitTypeFieldDAO;
import com.entity.WorkPermitTypeField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helper.PersistenceManagerUtil;



public class WorkPermitTypeFieldDAOImpls implements WorkPermitTypeFieldDAO {
	
	private static final 	Logger log 		= 	Logger.getLogger(WorkPermitTypeFieldDAOImpls.class.getName());

	@Override
	public Object savetoFirestore(WorkPermitTypeField inputObj) {
		PersistenceManager pm	=	null;
		try
		{
			log.info("inputObj In savetoFirestore "+new ObjectMapper().writeValueAsString(inputObj));
			pm	=	PersistenceManagerUtil.get().getPersistenceManager();
			inputObj = pm.makePersistent(inputObj);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}  finally
		   {
			   if(pm!=null)
				   pm.close();
		   }
		return inputObj;
	}

	@Override
	public Object updatetoFirestore(WorkPermitTypeField inputObj) {
		PersistenceManager pm	=	null;
		try
		{
			pm	=	PersistenceManagerUtil.get().getPersistenceManager();
			inputObj = pm.makePersistent(inputObj);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}  finally
		   {
			   if(pm!=null)
				   pm.close();
		   }
		return inputObj;
	}

	@Override
	public void deleteFromFirestore(WorkPermitTypeField inputObj) {
		PersistenceManager pm	=	null;
		try
		{
			pm	=	PersistenceManagerUtil.get().getPersistenceManager();
			pm.deletePersistent(inputObj);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}  finally
		   {
			   if(pm!=null)
				   pm.close();
		   }
	}

	@Override
	public List<WorkPermitTypeField> fetchByWorkPermitRefId(String id) {

		PersistenceManager pm	=	null;
		List<WorkPermitTypeField>	responseList = null;
		try
		{
			responseList	=	new ArrayList<WorkPermitTypeField>();
			pm	=	PersistenceManagerUtil.get().getPersistenceManager();
			Query query = pm.newQuery("SELECT FROM "+WorkPermitTypeField.class.getName()+" WHERE workpermitTypeRefId =="+id);
			query.setOrdering("fieldSequence asc");
			System.out.println(query.toString());
			responseList = (List<WorkPermitTypeField>) query.execute();
			System.out.println(responseList.size());
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}  finally
		   {
			   if(pm!=null)
				   pm.close();
		   }
		return responseList;
	
	}
	
	@Override
	public List<WorkPermitTypeField> fetchByWorkPermitRefId1(Long id) {

		PersistenceManager pm	=	null;
		List<WorkPermitTypeField>	responseList = null;
		try
		{
			responseList	=	new ArrayList<WorkPermitTypeField>();
			pm	=	PersistenceManagerUtil.get().getPersistenceManager();
			Query query = pm.newQuery("SELECT FROM "+WorkPermitTypeField.class.getName()+" WHERE workpermitTypeRefId =="+id);
			query.setOrdering("fieldSequence asc");
			System.out.println(query.toString());
			responseList = (List<WorkPermitTypeField>) query.execute();
			System.out.println(responseList.size());
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}  finally
		   {
			   if(pm!=null)
				   pm.close();
		   }
		return responseList;
	
	}

}
