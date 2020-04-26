package com.master;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.DAO.RequestDAO;
import com.DAOImpl.RequestDAOImpl;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.helper.PartialTextSearch;
import com.helper.SpreadSheetHelper;
import com.helper.UserUtil;
import com.service.ReportService;
import com.serviceImpl.ReportServiceImpls;



@RestController
@RequestMapping(value="/reports")
public class ReportsController 
{
	private static final 	Logger logger 	= 		Logger.getLogger(ReportsController.class.getName());
	@RequestMapping (value="/runWorkPermitFilterReports",method=RequestMethod.GET)
	public @ResponseBody String runWorkPermitFilterReports(HttpServletRequest req,HttpServletResponse res)
	{
		String status ="";
		String reportStatus = "";
		String reportType  = "";
		try
		{
			reportStatus = req.getParameter("reportStatus");
			reportType = req.getParameter("reportType");
			User user=UserServiceFactory.getUserService().getCurrentUser();
			Queue queue = QueueFactory.getQueue("filterreportQueue");
		    TaskOptions options = TaskOptions.Builder.withUrl("/reports/createFilteredPermitReports");
		    options.param("usermail", user.getEmail());
		    options.param("reportStatus", reportStatus);
		    options.param("reportType", reportType);
		    options.param("filterList",req.getParameter("input"));
		    queue.add(options);
		    status ="success";
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception ocuured runWorkPermitFilterReports : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());	
			status = "failure";
		}
		return status;
	}
	
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping (value="/createFilteredPermitReports",method=RequestMethod.POST)
	public void createFilteredPermitReports(HttpServletRequest req,HttpServletResponse res)
	{
		List<String> params = null;
		HashMap<String,String> map = new HashMap<String,String>();
		Drive DriveService 	 = null;
		String reportType = req.getParameter("reportStatus");
		String userSelectedType = req.getParameter("reportType");
		String userMail = req.getParameter("usermail");
		File copiedFile= null;
		String folderName=new Date().toString();
		copiedFile	=	new File();
		Cursor cursor =Cursor.newBuilder().build();
		int rowCount=2;
		int recordsTotal = 0;
		ReportService reportServiceObj = new ReportServiceImpls();
		try
		{
			logger.info("userSelectedType "+ userSelectedType +" Visiting createFilteredPermitReports ---- >"+userMail);
			params = new ArrayList<String>(); 
			if(userSelectedType.equalsIgnoreCase("FilterReport"))
			{
			map = new ObjectMapper().readValue(req.getParameter("filterList"), new TypeReference<HashMap<String,String>>() {});
				logger.info("Input Map "+new ObjectMapper().writeValueAsString(map));
			params = (List<String>) reportServiceObj.getQueryStringForReports(map);
				logger.info("Query here 406 ---- >"+params);
			}
			SpreadsheetService sheetService = new SpreadsheetService(AppHelper.getApplication_Name());
			while(cursor!=null)
			{
				List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
				HashMap<String, Object> searchResult = new HashMap<String, Object>();
				List<String> textFieldsList = new ArrayList<String>();
				if(reportType != null && !reportType.equals(""))
				{
					logger.info("reportStatus here ----- >"+reportType);
					if(userSelectedType.equalsIgnoreCase("FilterReport") && reportType.equalsIgnoreCase("WorkPermitFilterReport"))
					{
						searchResult = PartialTextSearch.getReportData(cursor,"WORKPERMIT_INDEX",StringUtils.collectionToDelimitedString(params, " AND ")+" AND isActive = true","",100,textFieldsList);
					}
					else
					{
						searchResult = PartialTextSearch.getReportData(cursor,"WORKPERMIT_INDEX","isActive = true","",100,textFieldsList);
					}
						logger.info("searchResult size"+searchResult.size());
					cursor=(Cursor) searchResult.get("cursor");
					results.addAll((List)searchResult.get("result"));
					searchResult.clear();
					logger.info("results Size here "+results.size());
					if (results != null && results.size() > 0 && userMail!=null && !userMail.equals("")) 
					{
						copiedFile=new SpreadSheetHelper().createSheetForAllReport(results,userMail,rowCount,folderName,copiedFile,reportType,sheetService);
					} 
					else
					{
						logger.info("result is empty"+results.size());
					}
					rowCount+=results.size();
					recordsTotal+=results.size();
					logger.warning("rowCount : "+rowCount);
					results.clear();
				}
				else
				{
					logger.info("reportType is empty here ");
				}
			}
			System.out.println("copiedFile "+copiedFile);
			if(recordsTotal>0 && copiedFile!=null)
			{
				   DriveService = new SpreadSheetHelper().getDriveService();	
				   Permission newPermission = new Permission();
				   newPermission.setType("user");
				   newPermission.setRole("writer");
				   newPermission.setEmailAddress(userMail);
				   DriveService.permissions().create(copiedFile.getId(), newPermission).execute();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured createFilteredPermitReports : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
	}
	*/
	
	 @RequestMapping(value = "/generateReportPDF", method = RequestMethod.GET)
	 public ModelAndView generateReportPDF(HttpServletRequest request,HttpServletResponse resp) throws GeneralSecurityException,IOException 
	 {
		 ModelAndView mv = new ModelAndView();
		 String workPermitId = null;
		 RequestDAO workPermitDao = new RequestDAOImpl();
		 try 
		 {
			 workPermitId = request.getParameter("workPermitId");
			 if(workPermitId != null && !workPermitId.equals(""))
			 {
				 Object workPermitObj = workPermitDao.fetchRequestById(Long.parseLong(workPermitId));
				// System.out.println("workPermitObj "+ new ObjectMapper().writeValueAsString(workPermitObj));
			     mv.setViewName("pdfView");
			     mv.addObject("WorkpermitObj", workPermitObj);
			     mv.addObject("WorkPermitId", workPermitId);
			 }
			 else{
				 System.out.println("workPermitId  is Empty");
			 }
		  } 
		 catch (Exception e) 
		 {
		    e.printStackTrace();
			logger.info("Exception ocuured generateReportPDF : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		 }
		 return mv;
	 }
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/generateReport", method = RequestMethod.GET)
		public @ResponseBody String generateReport(HttpServletRequest req, HttpServletResponse res) throws Exception {
			HashMap<String, Object> responseMap = new HashMap<String, Object>();
			try {
			
			SpreadSheetHelper objS = new SpreadSheetHelper();
			String type=req.getParameter("type");
			String configType="";
			if(req.getParameter("configtype")!=null)
				configType=req.getParameter("configtype");
			objS.createReport(type,configType);
			/*List<Object>
			switch(type) {
			case: "REQUEST":
				
			}
			objS.writeSomething(objI.getAll(), UserUtil.getCurrentUser());*/
			responseMap.put("STATUS","SUCCESS");
			responseMap.put("MESSAGE","Google Sheet will be shared with you shortly");
			}catch(Exception e) {
				responseMap.put("STATUS","FAILED");
				responseMap.put("MESSAGE","Please Contact Administrator");
			}
			return new ObjectMapper().writeValueAsString(responseMap);
			
		}
}
