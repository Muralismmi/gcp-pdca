package com.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.DAO.OauthCredentialDAO;
import com.entity.OauthCredentialJDO;
import com.helper.PersistenceManagerUtil;


public class OauthCredentialDAOImpls implements OauthCredentialDAO
{
	private static final 	Logger 	log  = 	Logger.getLogger(OauthCredentialDAO.class.getName());
	//======================== save user =====================================================================
	 public OauthCredentialJDO save(OauthCredentialJDO oauthCredentialObj) throws Exception
	   {
		   PersistenceManager pm = null; 
	       try
	       {
	    	   pm = PersistenceManagerUtil.getPersistenceManager();
	    	   oauthCredentialObj =  pm.makePersistent(oauthCredentialObj);
	    	   log.info("IN OauthCredentialJDO -------> save method ");
	    	  /* ofy.save().entities(oauthCredentialObj).now();*/
	       }
	       catch(Exception e)
	       {
	    	   log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
	       }
	       log.info("OUT OauthCredentialJDO -------> save method ");
	        return oauthCredentialObj;
	   }
	 
	 public HashMap<String, Object> fetchByTechnicalEmail(String email) throws Exception 
	   {
		 
		    PersistenceManager pm = null;
		    OauthCredentialJDO obj = new OauthCredentialJDO();
			List<OauthCredentialJDO> roleslist = new ArrayList<OauthCredentialJDO>();
			try 
			{
				pm = PersistenceManagerUtil.getPersistenceManager();
				String strQry2Execute = "";
				strQry2Execute = "SELECT FROM " + OauthCredentialJDO.class.getName() + " WHERE technicalEmail == '"+email+"'";
				log.info("strQry2Execute "+strQry2Execute);
				Query query_contact = pm.newQuery(strQry2Execute);
				roleslist = (List<OauthCredentialJDO>) query_contact.execute();
				log.info("roleslist :: "+roleslist.size());
				if(roleslist.size() > 0)
				{
					obj = roleslist.get(0);
				}
			} 
			catch (Exception e) {
				log.warning("JDO EXCEPTION");
				log.log(Level.SEVERE, e.getMessage(), e);
			} 
			finally {
				pm.close();
			}
			return new ObjectMapper().convertValue(obj,HashMap.class);
	   }


}
