package com.DAOImpl;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.DAO.WorkPermitTypeDAO;
import com.entity.WorkPermitType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helper.PersistenceManagerUtil;

public class WorkPermitTypeDAOImpls implements WorkPermitTypeDAO {

	private static final Logger log = Logger.getLogger(WorkPermitTypeDAOImpls.class.getName());

	@Override
	public List<WorkPermitType> fetchActiveFromFirestore() {
		PersistenceManager pm = null;
		List<WorkPermitType> plantList = null;

		try {

			pm = PersistenceManagerUtil.get().getPersistenceManager();

			Query queryStr = pm.newQuery("SELECT FROM " + WorkPermitType.class.getName() + " WHERE activeStatus == "
					+ true + " && uploadStatus == 'Success' ORDER BY workPermitType ASC");
			plantList = (List<WorkPermitType>) queryStr.execute();

		} catch (Exception e) {

			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
		} finally {
			if (pm != null)
				pm.close();
		}
		return plantList;
	}

	@Override
	public WorkPermitType fetchbyNameFromFirestore(String uniqueId) {
		PersistenceManager pm = null;
		WorkPermitType workPermitTypeObj = null;

		try {

			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm
					.newQuery("SELECT FROM " + WorkPermitType.class.getName() + " WHERE workPermitType == '" + uniqueId+"'");
			List<WorkPermitType> plantList = (List<WorkPermitType>) queryStr.execute();

			if (plantList != null && plantList.size() > 0) {

				workPermitTypeObj = plantList.get(0);
			}
		} catch (Exception e) {

		} finally {
			if (pm != null)
				pm.close();
		}
		return workPermitTypeObj;
	}

	@Override
	public WorkPermitType fetchWorkPermitTypeObjByTypeNameAndId(String permitType, String permitTypeUniqueId) {
		WorkPermitType responseObj = null;
		PersistenceManager pm = null;
		try {
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query query = pm.newQuery("SELECT FROM " + WorkPermitType.class.getName() + " WHERE workPermitType ==\""
					+ permitType + "\" && activeStatus==true");
			List<WorkPermitType> responseList = (List<WorkPermitType>) query.execute();
			if (responseList != null && responseList.size() > 0) {
				responseObj = responseList.get(0);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
		} finally {
			if (pm != null)
				pm.close();
		}
		return responseObj;

	}

	@Override
	public WorkPermitType updatetoFirestore(WorkPermitType inputObj) {
		PersistenceManager pm = null;
		// WorkPermitTypeService service = new WorkPermitTypeServiceImpls();
		try {
			pm = PersistenceManagerUtil.get().getPersistenceManager();
			log.info("inputObj " + new ObjectMapper().writeValueAsString(inputObj));
			pm.makePersistent(inputObj);
			// service.constructWorkPermitTypeoIndex(new ObjectMapper().readValue(new
			// ObjectMapper().writeValueAsString(inputObj), new
			// TypeReference<HashMap<String,Object>>() {}));
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error  updatetoFirestore === > WorkPermitTypeDAOImpls : " + e);
			e.printStackTrace();
		} finally {
			if (pm != null)
				pm.close();
		}
		return inputObj;
	}

	@Override
	public WorkPermitType fetchbyIdFromFirestore(String uniqueId) {
		PersistenceManager pm = null;
		WorkPermitType workPermitTypeObj = null;

		try {

			pm = PersistenceManagerUtil.get().getPersistenceManager();
			Query queryStr = pm
					.newQuery("SELECT FROM " + WorkPermitType.class.getName() + " WHERE uniqueId ==" + uniqueId);
			List<WorkPermitType> plantList = (List<WorkPermitType>) queryStr.execute();

			if (plantList != null && plantList.size() > 0) {

				workPermitTypeObj = plantList.get(0);
			}
		} catch (Exception e) {

		} finally {
			if (pm != null)
				pm.close();
		}
		return workPermitTypeObj;
	}
}
