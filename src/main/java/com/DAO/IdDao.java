package com.DAO;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.entity.IdJdo;
import com.helper.PersistenceManagerUtil;



public class IdDao {
	private static Logger logger = Logger.getLogger(IdDao.class.getPackage().getName());
	public IdJdo addIdtoDataStore(IdJdo id) {
		logger.warning("IN     IdDao --> addIdtoDataStore");
		PersistenceManager pm = null;
		//List userList = null;
	

		try {
			pm = PersistenceManagerUtil.getPersistenceManager();
             pm.makePersistent(id);
				
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			pm.close();
		}
		logger.warning("OUT     IdDao --> addIdtoDataStore");
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public int getNextIdfromDatastore(String idname) {
		logger.warning("IN     IdDao --> getNextIdfromDatastore");
		PersistenceManager pm = null;
		List<IdJdo> idList ;
        IdJdo obj = new IdJdo() ;
        int nextnum = 0 ;
		try {
			logger.warning("The jdo detail are");
			pm = PersistenceManagerUtil.getPersistenceManager();

			String strQry2Execute = "";

			strQry2Execute = "SELECT FROM " + IdJdo.class.getName()+" where id_name == '"+idname+"'" ;

			logger.warning("strQry2Execute = " + strQry2Execute);

			Query query = pm.newQuery(strQry2Execute);
			idList = (List<IdJdo>) query.execute() ;
			obj = idList.get(0);
			nextnum = obj.getNext_id();
			obj.setNext_id(nextnum + 1);
			pm.makePersistent(obj);
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			pm.close();
		}
		logger.warning("OUT  IdDao --> getNextIdfromDatastore");
		return nextnum;
	}
	@SuppressWarnings("unchecked")
	public IdJdo getIdEntryfromDatastore(String idname) {
		logger.warning("IN     IdDao --> getNextIdfromDatastore");
		PersistenceManager pm = null;
		List<IdJdo> idList ;
        IdJdo obj = null ;
        int nextnum = 0 ;
		try {
			logger.warning("The jdo detail are");
			pm = PersistenceManagerUtil.getPersistenceManager();

			String strQry2Execute = "";

			strQry2Execute = "SELECT FROM " + IdJdo.class.getName()+" where id_name == '"+idname+"'" ;

			logger.warning("strQry2Execute = " + strQry2Execute);

			Query query = pm.newQuery(strQry2Execute);
			idList = (List<IdJdo>) query.execute() ;
			if(idList!=null && idList.size()>0)
			obj = idList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			pm.close();
		}
		logger.warning("OUT  IdDao --> getNextIdfromDatastore");
		return obj;
	}
}
