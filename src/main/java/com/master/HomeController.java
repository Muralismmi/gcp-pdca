package com.master;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.DAO.RequestDAO;
import com.DAO.UserDAO;
import com.DAO.WorkPermitTypeDAO;
import com.DAO.WorkPermitTypeFieldDAO;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.UserDAOImpls;
import com.DAOImpl.WorkPermitTypeDAOImpls;
import com.DAOImpl.WorkPermitTypeFieldDAOImpls;
import com.entity.Request;
import com.entity.User;
import com.entity.WorkPermitType;
import com.entity.WorkPermitTypeField;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserServiceFactory;
import com.helper.Helper;
import com.helper.SearchDocumentHelper;
import com.helper.UserUtil;

@Controller
public class HomeController {
	public final BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
	private static final Logger log = Logger.getLogger(HomeController.class.getName());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req, HttpServletResponse resp) {
		try {
			System.out.println("Inside home controller");
			String email = UserUtil.getCurrentUser();
			if (email != null && !email.trim().isEmpty()) {
				UserDAO objUserDAO = new UserDAOImpls();
				User objUser = objUserDAO.getUserByEmail(email.trim());
				if (objUser == null) {
					return "errorpage";
				}
				req.setAttribute("signouturl", UserServiceFactory.getUserService().createLogoutURL("/logout"));
				req.setAttribute("loggedinusersmailid", email);
				req.setAttribute("user", objUser);
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				resp.setHeader("Pragma", "no-cache");
				resp.setDateHeader("Expires", 0);
			} else {
				log.warning("NO SESSION FOUND");
			}

		} catch (Exception e) {
			log.info("errorpage");
			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
		}
		return "index1";
		// return "redirect:authenticateUser";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		try {
			System.out.println("Inside logout controller");
			return "logout";

		} catch (Exception e) {
			log.info("errorpage");
			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
		}
		return "logout";
		// return "redirect:authenticateUser";
	}
	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public String home1(HttpServletRequest req, HttpServletResponse resp) {
		try {
			System.out.println("Inside home controller");
			String email = UserUtil.getCurrentUser();
			if (email != null && !email.trim().isEmpty()) {
				UserDAO objUserDAO = new UserDAOImpls();
				User objUser = objUserDAO.getUserByEmail(email.trim());
				if (objUser == null) {
					return "errorpage";
				}

				req.setAttribute("loggedinusersmailid", email);
				req.setAttribute("user", objUser);
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				resp.setHeader("Pragma", "no-cache");
				resp.setDateHeader("Expires", 0);
			} else {
				log.warning("NO SESSION FOUND");
			}

		} catch (Exception e) {
			log.info("errorpage");
			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
		}
		return "index";
		// return "redirect:authenticateUser";
	}

	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFormTypes", method = RequestMethod.GET)
	public @ResponseBody String getFormTypes(HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<WorkPermitType> resultList = new ArrayList<WorkPermitType>();
		WorkPermitTypeDAO objWorkPermitTypeDAO = new WorkPermitTypeDAOImpls();

		resultList = objWorkPermitTypeDAO.fetchActiveFromFirestore();
		if (resultList != null && resultList.size() > 0)
			return new ObjectMapper().writeValueAsString(resultList);
		else
			return new ObjectMapper().writeValueAsString(new ArrayList<WorkPermitType>());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFieldDetailsSectionWise", method = RequestMethod.GET)
	public @ResponseBody String getFieldDetailsSectionWise(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try {
			System.out.println("from here ");
			String workPermitTypeID = req.getParameter("WorkPermitTypeID");
			List<WorkPermitTypeField> resultList = new ArrayList<WorkPermitTypeField>();
			WorkPermitTypeFieldDAO objWorkPermitTypeDAO = new WorkPermitTypeFieldDAOImpls();

			resultList = objWorkPermitTypeDAO.fetchByWorkPermitRefId(workPermitTypeID);

			if (resultList != null && resultList.size() > 0)
				return new ObjectMapper().writeValueAsString(resultList);
			else
				return new ObjectMapper().writeValueAsString(new ArrayList<WorkPermitType>());
		} catch (Exception e) {
			e.printStackTrace();
			return new ObjectMapper().writeValueAsString(new ArrayList<WorkPermitType>());
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFieldDetailsSectionWise1", method = RequestMethod.GET)
	public @ResponseBody String getFieldDetailsSectionWise1(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		try {
			System.out.println("from here ");
			String workPermitTypeID = req.getParameter("WorkPermitTypeID");
			List<WorkPermitTypeField> resultList = new ArrayList<WorkPermitTypeField>();
			WorkPermitTypeFieldDAO objWorkPermitTypeDAO = new WorkPermitTypeFieldDAOImpls();
			WorkPermitTypeDAO objDAO = new WorkPermitTypeDAOImpls();
			RequestDAO objRequestDAO = new RequestDAOImpl();
			Request fromDAO = objRequestDAO.fetchRequestById(Long.parseLong(workPermitTypeID));
			if (fromDAO != null) {
				WorkPermitType objworkpermitType = objDAO.fetchbyNameFromFirestore(fromDAO.getFormType());
                System.out.println("Form Dao " +fromDAO.getFormType());
                if(objworkpermitType == null) {
                	System.out.println("null objworkpermitType "+objworkpermitType.getUniqueId());
                }else {
                	System.out.println("not null objworkpermitType "+objworkpermitType);
                }
				resultList = objWorkPermitTypeDAO.fetchByWorkPermitRefId1(objworkpermitType.getUniqueId());
				if (resultList != null && resultList.size() > 0)
					return new ObjectMapper().writeValueAsString(resultList);
				else
					return new ObjectMapper().writeValueAsString(new ArrayList<WorkPermitType>());
			} else {
				responseMap.put("STATUS", "FAILURE");
				responseMap.put("MESSAGE", "Request can only be updated in DRAFT State");
				return new ObjectMapper().writeValueAsString(resultList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ObjectMapper().writeValueAsString(new ArrayList<WorkPermitType>());
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fetchdataforautosuggest", method = RequestMethod.GET)
	public @ResponseBody String fetchDataForAutoSuggest(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		List<String> resultList = new ArrayList<String>();
		String fieldName = req.getParameter("fieldName");
		String queryString = req.getParameter("qstr");
		String indexName = req.getParameter("indexName");

		String email = UserUtil.getCurrentUser();
		if (email != null && !email.trim().isEmpty()) {
			UserDAO objUserDAO = new UserDAOImpls();
			User objUser = objUserDAO.getUserByEmail(email.trim());
			if (objUser != null) {
				try {
					log.info("Inside fetchdataforautosuggest --- >");
					if (fieldName != null && !fieldName.equalsIgnoreCase("") && queryString != null) {
						if (objUser.getRoles().contains("SUPERADMIN")) {
							resultList = SearchDocumentHelper.searchDatabyField(queryString, indexName, fieldName,
									objUser);
							log.info("resultList in dataforautosuggest"
									+ new ObjectMapper().writeValueAsString(resultList));
						} else if (objUser.getRoles().contains("PLANTMANAGER")) {
							resultList.add(objUser.getPlantName() + "&&&&" + objUser.getPlantId());
						}

					} else
						System.out.println("something is null");
				} catch (Exception e) {
					log.info("fetchdataforautosuggest----> : " + e.getStackTrace());
					log.log(Level.SEVERE, e.getStackTrace() + " Exception occurred : " + e);
				}
			}
		}
		return new ObjectMapper().writeValueAsString(resultList);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fetchdataforautosuggestfromConfiguration", method = RequestMethod.GET)
	public @ResponseBody String fetchdataforautosuggestfromConfiguration(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		List<String> resultList = null;
		String fieldName = req.getParameter("fieldName");
		String queryString = req.getParameter("qstr");
		String indexName = req.getParameter("indexName");
	//	String configuration = req.getParameter("configuration");
		try {
			log.info("Inside fetchdataforautosuggest --- >");
			if (fieldName != null && !fieldName.equalsIgnoreCase("") && queryString != null) {
				resultList = SearchDocumentHelper.searchDatabyFieldfromConfiguration(queryString, indexName, fieldName);
				log.info("resultList in dataforautosuggest" + new ObjectMapper().writeValueAsString(resultList));
			} else
				System.out.println("something is null");
		} catch (Exception e) {
			log.info("fetchdataforautosuggest----> : " + e.getStackTrace());
			log.log(Level.SEVERE, e.getStackTrace() + " Exception occurred : " + e);
			return "error";
		}
		return new ObjectMapper().writeValueAsString(resultList);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fetchdataforautosuggestfromConfigurationwithQuery", method = RequestMethod.GET)
	public @ResponseBody String fetchdataforautosuggestfromConfigurationwithQuery(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		List<Object> resultList = null;
		String fieldName = req.getParameter("fieldName");
		String queryString = req.getParameter("qstr");
		String indexName = req.getParameter("indexName");
		String configuration = req.getParameter("configuration");
		try {
			log.info("Inside fetchdataforautosuggest --- >");
			if (fieldName != null && !fieldName.equalsIgnoreCase("") && queryString != null) {
				resultList = SearchDocumentHelper.searchDatabyFieldfromConfigurationwithQuery(queryString, indexName,
						fieldName, configuration);
				log.info("resultList in dataforautosuggest" + new ObjectMapper().writeValueAsString(resultList));
			} else
				System.out.println("something is null");
		} catch (Exception e) {
			log.info("fetchdataforautosuggest----> : " + e.getStackTrace());
			log.log(Level.SEVERE, e.getStackTrace() + " Exception occurred : " + e);
			return "error";
		}
		return new ObjectMapper().writeValueAsString(resultList);
	}

	
	@RequestMapping(value = "/readContentFromSheet", method = RequestMethod.GET)
	public @ResponseBody String readContentFromSheet(@RequestParam("SheetID") String sheetID,
			@RequestParam("workPermitTypeName") String workPermitTypeName,
			@RequestParam("workPermitTypeId") String workPermitTypeId, HttpServletRequest req,
			HttpServletResponse res) {
		String status = "";
		try {
			System.out.println("SheetID in readContentFromSheet " + sheetID);
			if (sheetID != null && !sheetID.equals("")) {
				log.info("TaskQueue Initiated");
				Queue queue = QueueFactory.getQueue("readSheetQueue");
				TaskOptions options = TaskOptions.Builder.withUrl("/readContentFromSheetQueue");
				options.param("usermail", UserUtil.getCurrentUser());
				options.param("spreadSheetUrl", sheetID);
				options.param("workPermitTypeName", workPermitTypeName);
				options.param("workPermitTypeId", workPermitTypeId);
				queue.add(options);
				
				
				status = "Success";
			} else {
				log.info("Inside Else ---- sheetID is null");
				status = "Failure";
			}
		} catch (Exception e) {
			log.info("Exception ocuured during Create form readContentFromSheet : " + e.getStackTrace());
			log.log(Level.SEVERE, e.getMessage());
			status = "Error";
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/readContentFromSheetQueue", method = RequestMethod.POST)
	public @ResponseBody void readContentFromSheetQueue(HttpServletRequest req) {
		HashMap<String, Object> responseMap = null;
		Helper service = new Helper();
		try {
			log.info("spreadSheetUrl ===========>  " + req.getParameter("spreadSheetUrl") + " workPermitTypeName "
					+ req.getParameter("workPermitTypeName") + " workPermitTypeId "
					+ req.getParameter("workPermitTypeId") + " --->  USER NAME" + req.getParameter("usermail"));
			responseMap = (HashMap<String, Object>) service.readPermitTypeFieldsFromSheet(
					req.getParameter("spreadSheetUrl"), req.getParameter("workPermitTypeName"),
					req.getParameter("workPermitTypeId"), req.getParameter("usermail"));
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error : " + e);
			e.printStackTrace();
			responseMap.put("data", null);
			responseMap.put("Status", "Failure");
			responseMap.put("Message", "Something went wrong readContentFromSheetQueue . Please contact Administrator");
		}
	}

	public static String validateEmptyValues(String value) {
		String resultString = null;
		try {
			if (value != null && !value.equals("")) {
				resultString = value;
			} else {
				resultString = "-";
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getStackTrace() + " Error : " + e);
			log.info("Exception occured while validateEmptyValues");
		}
		return resultString;
	}
	
	
	
	// Below are Configuration Related Controllers
	
	@RequestMapping(value = "/setLocalPermission")
	public String setLocalPermission(UriComponentsBuilder ucBuilder)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.info("In setLocal Permission method - --------->");

		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		UserUtil serviceObj = new UserUtil();
		try {
			responseMap = (HashMap<String, Object>) serviceObj.setLocalPermission();
		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			return "failure";
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}

	@RequestMapping(value = "/setplantinitial")
	public String setPlantInitial(UriComponentsBuilder ucBuilder)
			throws JsonGenerationException, JsonMappingException, IOException {
		log.info("In setLocal Permission method - --------->");

		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		UserUtil serviceObj = new UserUtil();
		try {
			responseMap = (HashMap<String, Object>) serviceObj.setPlantInitial();
			serviceObj.setPillarInitial();
		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			return "failure";
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}

	@RequestMapping(value = "/createForm")
	public String createForm(UriComponentsBuilder ucBuilder)
			throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Coming here ");
		log.info("*******************-****************** --------->");

		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		UserUtil serviceObj = new UserUtil();
		try {
			responseMap = (HashMap<String, Object>) serviceObj.createForm();
		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			return "failure";
		}
		return new ObjectMapper().writeValueAsString(responseMap);
	}
	
	
	
	// Index to delete data from search index
	@RequestMapping(value = "/removeindex")
	public @ResponseBody String removeindex(HttpServletRequest request) throws GeneralSecurityException, IOException

	{
		String indexname = request.getParameter("indexname");
		Queue queue = QueueFactory.getQueue("removeQueue");
		TaskOptions options = TaskOptions.Builder.withUrl("/removeindexqueue");
		options.param("indexname", indexname);
		queue.add(options);
		return "success";
	}

	@RequestMapping(value = "/removeindexqueue", method = RequestMethod.POST)
	public @ResponseBody String removeindexqueue(HttpServletRequest req, HttpServletResponse resp)
			throws GeneralSecurityException, IOException, ServletException, ParserConfigurationException, SAXException,
			DOMException, JSONException {
		String indexname = req.getParameter("indexname");
		SearchDocumentHelper searchob = new SearchDocumentHelper();
		searchob.deletedocsFromSearchIndex(indexname);
		return "Success";
	}

}
