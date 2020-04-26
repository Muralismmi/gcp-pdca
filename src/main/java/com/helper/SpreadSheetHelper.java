package com.helper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.DAO.AreaDAO;
import com.DAO.BenefitDAO;
import com.DAO.ConfigurationDAO;
import com.DAO.LineDAO;
import com.DAO.LossTypeDAO;
import com.DAO.PillarDAO;
import com.DAO.PlantDAO;
import com.DAO.RequestDAO;
import com.DAO.ToolDAO;
import com.DAO.UserDAO;
import com.DAOImpl.AreaDAOImpls;
import com.DAOImpl.BenefitDAOImpls;
import com.DAOImpl.ConfigurationDAOImpls;
import com.DAOImpl.LineDAOImpls;
import com.DAOImpl.LossTypeDAOImpls;
import com.DAOImpl.PillarDAOImpls;
import com.DAOImpl.PlantDAOImpls;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.ToolDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Configuration;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendDimensionRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.appengine.api.datastore.Text;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.Link;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.master.HomeController;


public class  SpreadSheetHelper
{
	private static final 	Logger 	log  = 	Logger.getLogger(SpreadSheetHelper.class.getName()); 
	

	

	public List<LinkedHashMap<String,Object>> readSpreadhsheetAndUpdateContent(String templateId,List<String> keyList,int count) throws Exception
	{
		List<LinkedHashMap<String,Object>> resultList	=	new ArrayList<LinkedHashMap<String,Object>>();
		try
		{
			
			Sheets service	=	Helper.getSheetsService();
			log.warning("templateId : "+templateId);
			ValueRange valueRange	=	service.spreadsheets().values().get(templateId, "A1:L").execute();
			if(valueRange!=null && valueRange.getValues().size()>0)
			{
				log.warning("valueRange.getValues().size() : "+valueRange.getValues().size());
				for(int i=0;i<valueRange.getValues().size();i++)
				{
					List<Object>	row	=	valueRange.getValues().get(i);
					LinkedHashMap<String,Object> resultMap	=	new LinkedHashMap<String,Object>();
					log.warning("i: "+i);
					log.warning("row: "+row);
					if(i!=0)
					{
						for(int index=0;index<row.size();index++)
						{
							resultMap.put(keyList.get(index),row.get(index));
						}
						resultList.add(resultMap);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info("Exception occured in readSpreadhsheetAndUpdateContent");
			//log.log(Level.SEVERE+" Error : "+e);
			if(count<6)
			{
				count+=1;
				System.out.println("count : "+count);
				readSpreadhsheetAndUpdateContent(templateId,keyList,count);
			}
		}
		return resultList;
	}
	
		public WorksheetEntry getWorkSheet(SpreadsheetService sheetService,int count,String fileId) throws Exception
		{
		   	WorksheetEntry worksheet	= null;
		   	System.out.println("count : : : "+count);
		   	try
		   	{
		   	  URL workSheetURL = new URL(String.format(Locale.US, "https://spreadsheets.google.com/feeds/worksheets/%s/private/full", fileId));
		   	  sheetService.setConnectTimeout(180000);
		   	  WorksheetFeed worksheetFeed = sheetService.getFeed(workSheetURL, WorksheetFeed.class);
		   	  List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		   	  worksheet = worksheets.get(0);
		   	}
		   	catch(Exception e)
		   	{
		   		log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			   	if(count<5)
			   	{
			   	count=count+1;
			   	worksheet	= getWorkSheet(sheetService,count,fileId);
			   	}
			   }
		   		return worksheet;
		   }
		
		
		
		

	//================== creating folder in drive  ==========================================
	public static File createFolder(Drive service,String folderName,String parentId) throws Exception
	{
		 File body = new File();
		    body.setName(folderName);
		    body.setMimeType("application/vnd.google-apps.folder");
		    if(parentId!=null && !parentId.equalsIgnoreCase(""))
		    {
		    	body.setParents(Arrays.asList(parentId));
		    }
		    try 
		    {
				return service.files().create(body).execute();
			} 
		    catch (IOException e) 
		    {
		    	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			}
			return null;
	 }
	
	private static File copyFileInsideFolder(Drive service, String originFileId,String copyTitle,String parentId) throws Exception 
	{
	    File copiedFile = new File();
	    copiedFile.setName(copyTitle);
	    copiedFile.setParents(Arrays.asList(parentId));
	    try 
	    {
	      return service.files().copy(originFileId, copiedFile).execute();
	    } 
	    catch (IOException e) 
	    {
	    	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
	    }
	    return null;
	  }
  //============================ fetch all drive files =============================================================
	private static List<File> retrieveAllFiles(Drive service) throws Exception 
	{
	    List<File> result = new ArrayList<File>();
	    Files.List request = service.files().list();

	    do {
	      try 
	      {
	        FileList files = request.execute();

	        result.addAll(files.getFiles());
	        request.setPageToken(files.getNextPageToken());
	      } 
	      catch (Exception e) 
	      {
	    	  log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
	        request.setPageToken(null);
	      }
	    } 
	    while (request.getPageToken() != null &&
	             request.getPageToken().length() > 0);

	    return result;
	  }	

/*//================================== add permission to admin for spread sheet ============================================
		 @RequestMapping(value = "/addPermissionforUploadedTemplate", method = RequestMethod.GET)
	     public void addPermissionforUploadedTemplate(String templateId) throws Exception 
	     {
			GoogleCredential gc = null;
			try
			{
				gc = getGoogleCredential();
				Drive service = getDriveService();
				System.out.println("success:::::::::::"+service+"credential"+gc);
				SpreadsheetService sheetService = new SpreadsheetService(AppHelper.getApplication_Name());
			    sheetService.setOAuth2Credentials(gc);
			    Permission permission	=	new Permission();
			    permission.setType("domain");
			    permission.setValue(AppHelper.getDomainName());
				permission.setRole("reader");
				service.permissions().insert(templateId,permission).setSendNotificationEmails(false).execute();
		    }
	     	catch(Exception e)
	     	{
	     		log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
	     	}
	     }*/
	
   //================================ renaming the spread sheet ===================================================
   /*private static File renameFile(Drive service, String fileId, String newTitle) throws Exception 
   {
	    try {
	      File file = new File();
	      file.setTitle(newTitle);

	      // Rename the file.
	      Files.Patch patchRequest = service.files().patch(fileId, file);
	      patchRequest.setFields("title");

	      File updatedFile = patchRequest.execute();
	      return updatedFile;
	    } catch (Exception e) 
	    {
	    	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
	      System.out.println("An error occurred: " + e);
	      return null;
	    }
	  }*/
//update spreadsheet as batch
	public void batchUpdateToSheet(SpreadsheetService sheetService,URL cellFeedUrl,List<CellAddress> cellAddrs) throws Exception
	{
		Map<String, CellEntry> cellEntries;
		Link batchLink ;
		CellFeed batchRequest = null;
		 try
		  {
			 sheetService.setConnectTimeout(180000);
			 CellFeed cellFeed = sheetService.getFeed(cellFeedUrl, CellFeed.class);
			  //log.warning("cellFeedUrl : "+cellFeedUrl);
			  cellEntries = getCellEntryMap(sheetService, cellFeedUrl, cellAddrs);
			  log.warning("prints after cellentries");
			  batchRequest = new CellFeed();
			  for (CellAddress cellAddr : cellAddrs) 
			  {
		  	        CellEntry batchEntry = new CellEntry(cellEntries.get(cellAddr.idString));
		  	        batchEntry.changeInputValueLocal(cellAddr.value);
		  	        BatchUtils.setBatchId(batchEntry, cellAddr.idString);
		  	        BatchUtils.setBatchOperationType(batchEntry, BatchOperationType.UPDATE);
		  	      
		  	        batchRequest.getEntries().add(batchEntry);
			  }
			  cellEntries = null;
			  cellEntries = new HashMap<String, CellEntry>();
			  batchLink = cellFeed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM);
			  /*CellFeed batchResponse = */sheetService.batch(new URL(batchLink.getHref()), batchRequest);	  
		  }
		  catch(Exception e)
		  {
			  log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			  CellFeed cellFeed = sheetService.getFeed(cellFeedUrl, CellFeed.class);
			  cellEntries = getCellEntryMap(sheetService, cellFeedUrl, cellAddrs);
			  log.warning("prints after cellentries");
			  batchRequest = new CellFeed();
			  for (CellAddress cellAddr : cellAddrs) 
			  {
		  	        CellEntry batchEntry = new CellEntry(cellEntries.get(cellAddr.idString));
		  	        batchEntry.changeInputValueLocal(cellAddr.value);
		  	        BatchUtils.setBatchId(batchEntry, cellAddr.idString);
		  	        BatchUtils.setBatchOperationType(batchEntry, BatchOperationType.UPDATE);
		  	      
		  	        batchRequest.getEntries().add(batchEntry);
			  }
			  batchLink = cellFeed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM);
			 /* CellFeed batchResponse = */sheetService.batch(new URL(batchLink.getHref()), batchRequest);	
		  }
	}
	public static Map<String, CellEntry> getCellEntryMap(SpreadsheetService ssSvc, URL cellFeedUrl, List<CellAddress> cellAddrs)throws Exception 
	{
			log.warning("calls for writing");
			ssSvc.setConnectTimeout(120000);
			Map<String, CellEntry> cellEntryMap = null; 
			try
			{
				CellFeed batchRequest = new CellFeed();
			    for (CellAddress cellId : cellAddrs) 
			    {
			      CellEntry batchEntry = new CellEntry(cellId.row, cellId.col, cellId.idString);
			      batchEntry.setId(String.format("%s/%s", cellFeedUrl.toString(), cellId.idString));
			      BatchUtils.setBatchId(batchEntry, cellId.idString);
			      BatchUtils.setBatchOperationType(batchEntry, BatchOperationType.QUERY);
			      batchRequest.getEntries().add(batchEntry);
			    }
			    CellFeed cellFeed;
			    try
			    {
			    	 cellFeed = ssSvc.getFeed(cellFeedUrl, CellFeed.class);
			    }
			    catch(Exception e)
			    {
			    	log.warning("Checking secondtime");
			    	 cellFeed = ssSvc.getFeed(cellFeedUrl, CellFeed.class);
			    }
			    CellFeed queryBatchResponse =
			      ssSvc.batch(new URL(cellFeed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM).getHref()), batchRequest);

			    cellEntryMap = new HashMap<String, CellEntry>(cellAddrs.size());
			    for (CellEntry entry : queryBatchResponse.getEntries()) 
			    {
			      cellEntryMap.put(BatchUtils.getBatchId(entry), entry);
			     /* System.out.printf("batch %s {CellEntry: id=%s editLink=%s inputValue=%s\n",
		          BatchUtils.getBatchId(entry), entry.getId(), entry.getEditLink().getHref(),
		          entry.getCell().getInputValue());*/
			    }
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			}
			return cellEntryMap;
	}
	public class CellAddress 
	{
	    public  int row;
	    public  int col;
	    public  String idString;
	    public  String value;
	    
	    public CellAddress(int row, int col) 
	    {
	        this.row = row;
	        this.col = col;
	    }
	    public CellAddress(int row, int col,String value) 
	    {
	      this.row = row;
	      this.col = col;
	      this.value=value;
	      this.idString = String.format("R%sC%s", row, col,value);
	    }
	  }
	
	//====================== write error data of risk upload to sheet=================================//
	
	
	
	public void writeIntoWorksheet(SpreadsheetService spreadsheetService,URL cellFeedUrl,List<CellAddress> cellAddrs,CellFeed cellFeed)
	{
		 Map<String, CellEntry> cellEntries;
		 CellFeed batchRequest = null;
		 Link batchLink ;
		try
		{
			 cellEntries=getCellEntryMap(spreadsheetService, cellFeedUrl, cellAddrs);
			  batchRequest = new CellFeed();
			  new CellFeed();
			  for (CellAddress cellAddr : cellAddrs)
		      {
		  	        CellEntry batchEntry = new CellEntry(cellEntries.get(cellAddr.idString));
		  	        batchEntry.changeInputValueLocal(cellAddr.value);
		  	        BatchUtils.setBatchId(batchEntry, cellAddr.idString);
		  	        BatchUtils.setBatchOperationType(batchEntry, BatchOperationType.UPDATE);
		  	        batchRequest.getEntries().add(batchEntry);
		      }
		      batchLink = cellFeed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM);
		      spreadsheetService.batch(new URL(batchLink.getHref()), batchRequest);
		}
		catch(Exception e)
		{
			log.warning("writeIntoWorksheet --- >Error : "+Arrays.asList(e.getStackTrace()));
			log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
		}
	}
	
	public WorksheetEntry getWorkSheet(SpreadsheetService sheetService,int retryCount,String fileId,int worksheetNo) throws Exception
	{
	   	WorksheetEntry worksheet	= null;
	   	try
	   	{
	   	  URL workSheetURL = new URL(String.format(Locale.US, "https://spreadsheets.google.com/feeds/worksheets/%s/private/full", fileId));
	   	  sheetService.setConnectTimeout(180000);
	   	  WorksheetFeed worksheetFeed = sheetService.getFeed(workSheetURL, WorksheetFeed.class);
	   	  List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	   	  worksheet = worksheets.get(worksheetNo); // 0 or 1 or 2 etc..
	   	}
	   	catch(Exception e)
	   	{
	   		log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
		   	if(retryCount<5)
		   	{
		   		retryCount=retryCount+1;
		   	   worksheet	= getWorkSheet(sheetService,retryCount,fileId,worksheetNo);
		   	}
		   }
	   		return worksheet;
	   }
	

	
	public List<Object> validateWorkPermitTypeFieldSheet(HashMap<String,Object> rowMap)
	{
		log.info("inside validateWorkPermitTypeFieldSheet ===> SpreadSheetHelper");
		List<String> fieldTypeValList = null;
		List<String> isMandatory_ByDefaultEditable_VALUES = null;
		List<String> mandatoryList = null;
		List<Object> errorDetails =  new ArrayList<Object>();
		List<String> headerList = null;
		List<String> mandatoryFieldsInPermit = null;
		String errorMsgStr= "";
		try
		{
			fieldTypeValList = Constants.FieldType_VALUES;
			isMandatory_ByDefaultEditable_VALUES = Constants.IsMandatory_ByDefaultEditable_VALUES;
			mandatoryList = Constants.MANDATORYCOLUMNVALUESBYDEFAULT;
			headerList = Constants.WORKPERMITTYPEFIELDTEMPLATEHEADERS;
			mandatoryFieldsInPermit = Constants.MANDATORYFIELDS;
			System.out.println("headerList "+ headerList);
			for (String headerName : headerList) 
			{
				//log.info("headerName "+headerName);
				String sheetFieldVal = (String) rowMap.get(headerName);
				//log.info("sheetFieldVal 1"+sheetFieldVal);
				
				    if(mandatoryList.contains(headerName))
				    {
				    	if(sheetFieldVal!=null && !sheetFieldVal.equals(""))
				    	{
				    		log.info("Mandatory Column value is present");
				    	}
				    	else
						{
				    		errorMsgStr =errorMsgStr+headerName+" Coulmn Value Missing - It is Mandatory"+"\n";
							//errorDetails.add(headerName+" Coulmn Value Missing - It is Mandatory");
						}
				    }
					if(headerName.equalsIgnoreCase("fieldType"))
						{
							if(fieldTypeValList.contains(rowMap.get(headerName)))
							{
								if(sheetFieldVal!=null && !sheetFieldVal.equals("") && sheetFieldVal.equalsIgnoreCase("CHECKBOX") || sheetFieldVal.equalsIgnoreCase("DROPDOWN") 
										|| sheetFieldVal.equalsIgnoreCase("RADIO") || sheetFieldVal.equalsIgnoreCase("AUTOSUGGEST"))
								{
									String optionFieldVal = (String) rowMap.get("options");
									String MasterDataReferenceFieldVal = (String) rowMap.get("masterDataReference");
									log.info("MasterDataReferenceVal here "+ MasterDataReferenceFieldVal);
									if(optionFieldVal!=null && !optionFieldVal.equals("") && !sheetFieldVal.equalsIgnoreCase("AUTOSUGGEST"))
									{
										// validate option as ,
										log.info("Inside IF optionFieldVal "+optionFieldVal);
										if(optionFieldVal.contains(","))
										{
											log.info("Inside If -- > optionFieldVal has comma" + optionFieldVal);
										}
										else
										{
											errorMsgStr+="Option Column Value should be comma seperated Values"+"\n";
											//errorDetails.add("Option Column Value should be comma seperated Values");
										}
									}
									else if(MasterDataReferenceFieldVal!=null && !MasterDataReferenceFieldVal.equals(""))
									{
										if(sheetFieldVal!=null && !sheetFieldVal.equals("") && sheetFieldVal.equalsIgnoreCase("AUTOSUGGEST"))
										{
											String defaultVal = (String) rowMap.get("defaultValue");
											log.info("defaultVal ==> "+defaultVal);
											if(MasterDataReferenceFieldVal.equalsIgnoreCase("EDCONTACTSEARCH"))
											{
												if(defaultVal!=null && !defaultVal.equals(""))
												{
													log.info("checking "+!(defaultVal.equalsIgnoreCase("LOGGEDINUSERNAME") || defaultVal.equalsIgnoreCase("LOGGEDINUSEREMAIL")));
													if(!(defaultVal.equalsIgnoreCase("LOGGEDINUSERNAME") || defaultVal.equalsIgnoreCase("LOGGEDINUSEREMAIL"))) 
													{
														//errorDetails.add("Default Value for the EDCONTACTSEARCH should be either LOGGEDINUSERNAME (or) LOGGEDINUSEREMAIL");
														errorMsgStr+="Default Value for the EDCONTACTSEARCH should be either LOGGEDINUSERNAME (or) LOGGEDINUSEREMAIL"+"\n";
													}
												}
											}
											else 
											{
												if(MasterDataReferenceFieldVal.equalsIgnoreCase("EDSITESEARCH")) {
													if(defaultVal!=null && !defaultVal.equals(""))
													{
														if(!defaultVal.equalsIgnoreCase("LOGGEDINUSERSITE")) 
														{
															//errorDetails.add("Default Value for the EDSITESEARCH should be LOGGEDINUSERSITE");
															errorMsgStr+="Default Value for the EDSITESEARCH should be LOGGEDINUSERSITE"+"\n";
														}
													}
												}
											}
										}
									}
									else
									{
										log.info("ELSE +++++++++++++  ==> ");
										//errorDetails.add("Option (or) MasterDataReference Column Value is Mandatory If FieldType value is "+sheetFieldVal);
										errorMsgStr+="Option (or) MasterDataReference Column Value is Mandatory If FieldType value is "+sheetFieldVal+"\n";
									}
								}
							}
							else
							{
								//errorDetails.add(headerName+" Coulmn Value MisMatch with Std Values for this column");
								errorMsgStr+=headerName+" Coulmn Value MisMatch with Std Values for this column"+"\n";
							}
						}
						else
						{
							if(headerName.equalsIgnoreCase("isMandatory") || headerName.equalsIgnoreCase("byDefaultEditable"))
							{
								if(sheetFieldVal!=null && !sheetFieldVal.equals("") && !isMandatory_ByDefaultEditable_VALUES.contains(sheetFieldVal.toUpperCase()))
								{
									//errorDetails.add(headerName+" Coulmn value should be yes or no");
									errorMsgStr+=headerName+" Coulmn value should be yes or no"+"\n";
								}
							}
						}
			}
			log.info("errorMsgStr INSIDE  "+errorMsgStr);
			errorDetails.add(errorMsgStr);
			log.info("errorDetails   "+errorDetails );
		}
		catch(Exception e)
		{
			log.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			log.log(Level.SEVERE,e.getStackTrace()+" Error validateWorkPermitTypeFieldSheet : "+e);
		}
		return errorDetails;
	}
	
	
	public void writeIntoSpreadSheet(String spreadSheetId,List<List<Object>> commonList,int gridId) throws Exception
 	{
		 List<Request> reqs	=	new ArrayList<Request>();
		 //int sheetId	=	0;
		try
		{
			    log.info(" "+ new ObjectMapper().writeValueAsString(commonList));
 				Sheets	sheetsService = Helper.getSheetsService();
 				//sheetId	=	new SpreadSheetHelper().getSheetIdBySheetName(sheetsService, spreadSheetId,Constants.QUESTIONNAIRE_SHEET_NAME);*/
  			    String range = "M2:M"; // TODO: Update placeholder value.
 		 	    String valueInputOption = "RAW"; // TODO: Update placeholder value.
 		 	    ValueRange requestBody = new ValueRange();
 		 	    requestBody.setValues(commonList);
 		 	    reqs.add(new Request().setAppendDimension(new AppendDimensionRequest().setSheetId(gridId).setDimension("COLUMNS").setLength(1)));
		 	    BatchUpdateSpreadsheetRequest batchReq	=	new BatchUpdateSpreadsheetRequest().setRequests(reqs);
		 	    sheetsService.spreadsheets().batchUpdate(spreadSheetId,batchReq).execute();
 		 	    Sheets.Spreadsheets.Values.Update request = sheetsService.spreadsheets().values().update(spreadSheetId, range, requestBody);
 		 	    request.setValueInputOption(valueInputOption);
 		 	    UpdateValuesResponse response = request.execute();
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			log.log(Level.SEVERE,e.getStackTrace()+" Error writeIntoSpreadSheet : "+e);
 			 log.log(Level.SEVERE,e.getMessage());
 		}
 	}
	
	 public int generateWorkPermitFieldsForsheet(int rowNumber,int cellIndex,List<CellAddress> cellAddrs,HashMap<String, String> workPermitObj) throws JsonGenerationException, JsonMappingException, IOException
	 {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 try
		 {
			 if(workPermitObj.get("F001_ReqID") != null && !workPermitObj.get("F001_ReqID").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("F001_ReqID")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F014_SiteLocation") != null && !workPermitObj.get("F014_SiteLocation").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("F014_SiteLocation")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F006_EmailRequester") != null && !workPermitObj.get("F006_EmailRequester").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("F006_EmailRequester")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("workPermitType") != null && !workPermitObj.get("workPermitType").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("workPermitType")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F018_PlannedStartDate") != null && !workPermitObj.get("F018_PlannedStartDate").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,HomeController.validateEmptyValues(TemplateBuilder.convertDateToTimeZone(Long.parseLong(workPermitObj.get("F018_PlannedStartDate")),workPermitObj.get("requesterTimezone")))));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F019_Provisionalendofwork") != null && !workPermitObj.get("F019_Provisionalendofwork").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,HomeController.validateEmptyValues(TemplateBuilder.convertDateToTimeZone(Long.parseLong(workPermitObj.get("F019_Provisionalendofwork")),workPermitObj.get("requesterTimezone")))));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F012_ContractorEmail") != null && !workPermitObj.get("F012_ContractorEmail").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("F012_ContractorEmail")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F002_Status") != null && !workPermitObj.get("F002_Status").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("F002_Status")));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 if(workPermitObj.get("F004_Date") != null && !workPermitObj.get("F004_Date").equals(""))
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,HomeController.validateEmptyValues(TemplateBuilder.convertDateToTimeZone(Long.parseLong(workPermitObj.get("F004_Date")),workPermitObj.get("requesterTimezone")))));
			 }
			 else
			 {
				 cellAddrs.add(new CellAddress(rowNumber, cellIndex++,workPermitObj.get("")));
			 }
			 rowNumber++;
		 }
		 catch(Exception e){
			 log.info("Exception occured in generateWorkPermitFieldsForsheet "+e);
			 log.log(Level.SEVERE,e.getMessage());
			 e.printStackTrace();
		 }
		 return rowNumber;
	 }
	 public void createReport(String type,String configType) {
		 List dataObjList = new ArrayList<>();
		 List<Object> headerRow = new ArrayList<Object>();
		 List<String> keyList = new ArrayList<String>();
		 switch(type) {
		 case  "REQUEST":
			 RequestDAO objRequestDAO = new RequestDAOImpl();
			 dataObjList =objRequestDAO.fetchRequestList();
			 headerRow.add("ID");
			 headerRow.add("Form Type");
			 headerRow.add("Line");
			 headerRow.add("Station");
			 headerRow.add("Project Lead");
			 headerRow.add("Team Members");
			 headerRow.add("Mentor");
			 headerRow.add("Pillar");
			 headerRow.add("Loss");
			 headerRow.add("Problem Statement");
			 headerRow.add("Start Date");
			 headerRow.add("Target Date");
			 headerRow.add("Benefit Type");
			 headerRow.add("Benefit [INR]");
			 headerRow.add("Cost [INR]");
			 headerRow.add("Benefit/Cost");
			 headerRow.add("Tools Used");
			 headerRow.add("Completion Date");
			 headerRow.add("Duration");
			 headerRow.add("Stage");
			 headerRow.add("created By");
			 headerRow.add("created On");
			 keyList.add("formId");
			 keyList.add("formType");
			 keyList.add("line");
			 keyList.add("station");
			 keyList.add("projectLeadName");
			 keyList.add("teamMembers");
			 keyList.add("mentorName");
			 keyList.add("primaryPillar");
			 keyList.add("lossType");
			 keyList.add("problemStatement");
			 keyList.add("startDate");
			 keyList.add("targetDate");
			 keyList.add("benefitType");
			 keyList.add("benefitValue");
			 keyList.add("cost");
			 keyList.add("Benefit/Cost");
			 keyList.add("tools");
			 keyList.add("actualCompletionDate");
			 keyList.add("Duration");
			 keyList.add("status");
			 keyList.add("createdBy");
			 keyList.add("createdOn");
			 break;
		 case "PLANT":
				 PlantDAO objPlantDAO = new PlantDAOImpls();
				 dataObjList =objPlantDAO.fetchPlantList();
				 headerRow.add("Code");
				 headerRow.add("Name");
				 headerRow.add("Location");
				 headerRow.add("Region");
				 headerRow.add("isActive");
				 headerRow.add("Created By");
				 keyList.add("code");
				 keyList.add("name");
				 keyList.add("location");
				 keyList.add("region");
				 keyList.add("active");
				 keyList.add("createdBy");
				 break;
		 case "PILLAR":
			 PillarDAO objPillarDAO = new PillarDAOImpls();
			 dataObjList =objPillarDAO.fetchPillarList();
			 headerRow.add("Pillar Type");
			 headerRow.add("Name");
			 headerRow.add("Created By");
			 keyList.add("pillarType");
			 keyList.add("name");
			 keyList.add("active");
			 keyList.add("createdBy");
			 break;
		 case "AREA":
			 AreaDAO objAreaDAO = new AreaDAOImpls();
			 dataObjList =objAreaDAO.fetchAll();
			 headerRow.add("Name");
			 headerRow.add("Plant");
			 headerRow.add("isActive");
			 headerRow.add("Created By");
			 keyList.add("value");
			 keyList.add("plant");
			 keyList.add("active");
			 keyList.add("createdBy");
			 break;
		 case "LINE":
			LineDAO objLineDAO = new LineDAOImpls();
			 dataObjList =objLineDAO.fetchAll();
			 headerRow.add("Name");
			 headerRow.add("Plant");
			 headerRow.add("isActive");
			 headerRow.add("Created By");
			 keyList.add("value");
			 keyList.add("plant");
			 keyList.add("active");
			 keyList.add("createdBy");
			 break;
		 case "LOSSTYPE":
				LossTypeDAO objLossTypeDAO = new LossTypeDAOImpls();
				 dataObjList =objLossTypeDAO.fetchAll();
				 headerRow.add("Name");
				 headerRow.add("isActive");
				 headerRow.add("Created By");
				 keyList.add("value");
				 keyList.add("active");
				 keyList.add("createdBy");
				 break;
		 case "BENEFITTYPE":
				BenefitDAO objBenefitTypeDAO = new BenefitDAOImpls();
				 dataObjList =objBenefitTypeDAO.fetchAll();
				 headerRow.add("Name");
				 headerRow.add("isActive");
				 headerRow.add("Created By");
				 keyList.add("value");
				 keyList.add("active");
				 keyList.add("createdBy");
				 break;
		 case "TOOL":
				ToolDAO objToolDAO = new ToolDAOImpls();
				 dataObjList =objToolDAO.fetchAll();
				 headerRow.add("Name");
				 headerRow.add("isActive");
				 headerRow.add("Created By");
				 keyList.add("value");
				 keyList.add("active");
				 keyList.add("createdBy");
				 break;
		 case "USER":
			 UserDAO objUsernDAO = new UserDAOImpls();
			 dataObjList =objUsernDAO.fetchUserList();
			 headerRow.add("Title");
			 headerRow.add("FirstName");
			 headerRow.add("LastName");
			 headerRow.add("UserName");
			 headerRow.add("UserEmail");
			 headerRow.add("PlantName");
			 headerRow.add("PillarLead");
			 headerRow.add("PillarName");
			 headerRow.add("ProjectLead");
			 headerRow.add("Mentor");
			 headerRow.add("Editor");
			 headerRow.add("Roles");
			 headerRow.add("CreatedBy");
			 keyList.add("title");
			 keyList.add("firstName");
			 keyList.add("lastName");
			 keyList.add("userName");
			 keyList.add("userEmail");
			 keyList.add("plantName");
			 keyList.add("pillarLead");
			 keyList.add("pillarName");
			 keyList.add("projectLead");
			 keyList.add("mentor");
			 keyList.add("editor");
			 keyList.add("roles");
			 keyList.add("createdBy");
			 break;
		default :
			
		 }
		writeSomething(dataObjList, UserUtil.getCurrentUser(),headerRow,keyList,type,configType);
	 }
	 public void writeSomething(List<Object> invoiceData,String emailaddress,List<Object> headerRow,List<String> keyList,String type,String configType) {

			
		    try {
		    System.out.println("test cmoing here" );
		    	 Sheets service	=	Helper.getSheetsService();
		    	 String fileName ="";
		    	if(configType==null ||configType.trim().isEmpty())
		    		fileName= "Report_"+type+"_"+java.time.LocalDate.now()+java.time.LocalTime.now();
		    	else
		    		fileName="Report_"+configType+"_"+java.time.LocalDate.now()+java.time.LocalTime.now();
		    	 Spreadsheet spreadsheet = new Spreadsheet()
		    		        .setProperties(new SpreadsheetProperties()
		    		                .setTitle(fileName));
		    		spreadsheet = service.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
		    		System.out.println("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
		    		
		    		String id =  spreadsheet.getSpreadsheetId();
		    		Drive driveService = Helper.getDriveService1();
		    		BatchRequest batch = driveService.batch();
					
		    	Permission userPermission = new Permission().setType("user").setRole("writer").setEmailAddress(emailaddress);
		    	driveService.permissions().create(id,userPermission).setSendNotificationEmail(true).execute();
		    	//driveService.permissions().create(id, userPermission).setFields("id").queue(batch, callback);
		     
		        String writeRange = "Sheet1!A1";
		        List<List<Object>> writeData = new ArrayList<>();
		   
	            writeData.add(headerRow);
		        for(Object invoice : invoiceData) {
		        	
		        	 List<Object> dataRow = new ArrayList<>();
		        	 HashMap<String,Object> data = new ObjectMapper().convertValue(invoice, HashMap.class);
		        	 	for(String key:keyList) {
		        	 		System.out.println(key);
		        	 		switch(key) {
		        	 		
		        	 		case "Benefit/Cost":
			        	 			if(data.get("benefitValue")!=null && data.get("cost")!=null) {
			        	 				double cost = (double) data.get("cost");
				        	 			double benefitValue = (double) data.get("benefitValue");
				        	 			 if(cost!=0 && benefitValue!=0) {
				     				        double calcValue =benefitValue/cost;
				     				        			dataRow.add(Math.round(calcValue));
				     				        			System.out.println("cost ==0");
				     				        }else {
				     				        	dataRow.add("");
				     				        	System.out.println("cost ==0");
				     				        }
			        	 			}
			        	 			else {
			        	 				System.out.println("benefitValue ==0");
			        	 				dataRow.add("");
			        	 			}
		        	 		
		        	 			break;
		        	 		case "teamMembers" :
			        	 			LinkedHashMap<String,Object> teammembersText = (LinkedHashMap<String, Object>) data.get("teamMembers");
			        	 			if( data.get("teamMembers")!=null) {
			        	 				List<HashMap<String,Object>> teammembers = new ObjectMapper().readValue(teammembersText.get("value").toString(), new TypeReference<List<HashMap<String,Object>>>() {});
				        	 			 List<String> sbMembers=new ArrayList();
				        			        for(HashMap<String,Object> members:teammembers) {
				        			        	sbMembers.add(members.get("name").toString());
				        			        }
				        			        dataRow.add(sbMembers.stream().collect(Collectors.joining(", ")));
			        	 			}
			        	 			else {
			        	 				dataRow.add("");
			        	 			}
		        	 			break;
		        	 		case "lossType":

		        	 			if( data.get("lossType")!=null) {
		        	 				List<String> losstypelist =  (List<String>) data.get("lossType");
			        	 			 List<String> losstype=new ArrayList();
			        			        for(String members:losstype) {
			        			        	losstypelist.add(members);
			        			        }
			        			        dataRow.add(losstypelist.stream().collect(Collectors.joining(", ")));
		        	 			}
		        	 			else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "problemStatement":
		        	 			if( data.get("problemStatement")!=null) {
		        	 				LinkedHashMap<String,Object> problemStatementText = (LinkedHashMap<String, Object>) data.get("problemStatement");
		        			        dataRow.add(problemStatementText.get("value"));
		        	 			}
		        	 			else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "startDate":
		        	 			 Long startDate = (Long)data.get("startDate");
		        	 			if(startDate!=0) {
			        	 			 DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
			    				     dataRow.add(f.format(startDate));
		        	 			}else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "targetDate":
		        	 			 Long targetDate = (Long)data.get("targetDate");
		 		        	 			if(targetDate!=0) {
		 		        	 				 DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
					    				     dataRow.add(f.format(targetDate));
		 		        	 			}else {
		 		        	 				dataRow.add("");
		 		        	 			}
			        	 		break;
		        	 		case "actualCompletionDate":
		        	 			 Long actualCompletionDate = (Long)data.get("actualCompletionDate");
			 		        	 			if(actualCompletionDate!=0) {
			 		        	 				 DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
						    				     dataRow.add(f.format(actualCompletionDate));
			 		        	 			}else {
			 		        	 				dataRow.add("");
			 		        	 			}
		        	 			break;
		        	 		case "createdOn":
		        	 			 Long createdOn = (Long)data.get("createdOn");
	 		        	 			if(createdOn!=0) {
	 		        	 				 DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
				    				     dataRow.add(f.format(createdOn));
	 		        	 			}else {
	 		        	 				dataRow.add("");
	 		        	 			}
		        	 			break;
		        	 		case "benefitType":
		        	 			if(data.get("benefitType")!=null) {
		        	 				List<String> benefitType =  (List<String>) data.get("benefitType");
			        	 			 List<String> benefitTypelist=new ArrayList();
			        			        for(String members:benefitType) {
			        			        	benefitTypelist.add(members);
			        			        }
			        			        dataRow.add(benefitTypelist.stream().collect(Collectors.joining(", ")));
		        	 			}else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "tools":
		        	 			if(data.get("tools")!=null) {
		        	 				List<String> tools =  (List<String>) data.get("tools");
			        	 			 List<String> toolslist=new ArrayList();
			        			        for(String members:tools) {
			        			        	toolslist.add(members);
			        			        }
			        			        dataRow.add(toolslist.stream().collect(Collectors.joining(", ")));
		        	 			}else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "roles":
		        	 			if(data.get("roles")!=null) {
		        	 				List<String> roles =  (List<String>) data.get("roles");
			        	 			 List<String> roleslist=new ArrayList();
			        			        for(String members:roles) {
			        			        	roleslist.add(members);
			        			        }
			        			        dataRow.add(roleslist.stream().collect(Collectors.joining(", ")));
		        	 			}else {
		        	 				dataRow.add("");
		        	 			}
		        	 			break;
		        	 		case "Duration":

		        	 			long targetDate1 = (long) data.get("targetDate") ;
		        	 			long startDate1 = (long) data.get("startDate") ;
		        	 			if(targetDate1 !=0 && startDate1!=0) {
		        	 				long diff = (new Date(targetDate1).getTime() - new Date(startDate1).getTime());
/*
		        	 				long diffSeconds = diff / 1000 % 60;
		        	 				long diffMinutes = diff / (60 * 1000) % 60;
		        	 				long diffHours = diff / (60 * 60 * 1000) % 24;*/
		        	 				long diffDays = diff / (24 * 60 * 60 * 1000);
		        	 				dataRow.add(diffDays+" Days");
		        	 			}
		        	 			else {
		        	 			  	dataRow.add("");
		        	 			}
		        	 		break;
		        	 		case "code":
		        	 			System.out.println("key  "+key+"   "+data.get("code"));
		        	 			dataRow.add(data.get("code"));
		        	 		break;
		        	 		case "name":
		        	 			System.out.println("key  "+key+"   "+data.get("name"));
		        	 			dataRow.add(data.get("name"));
		        	 			break;
		        	 		case "location":
		        	 			System.out.println("key  "+key+"   "+data.get("location"));
		        	 			dataRow.add(data.get("location"));
		        	 			break;
		        	 		case "region":
		        	 			System.out.println("key  "+key+"   "+data.get("region"));
		        	 			dataRow.add(data.get("region"));
		        	 			break;
		        	 		case "pillarType":
		        	 			System.out.println("key  "+key+"   "+data.get("pillarType"));
		        	 			dataRow.add(data.get("pillarType"));
		        	 			break;
		        	 		default:
		        	 			if(data.get(key)!=null)
		        	 			dataRow.add(data.get(key));
		        	 			else
		        	 				dataRow.add("");
		        	 		}
		        	 	}
		        	 	//System.out.println(dataRow);
			            writeData.add(dataRow);
		        }
		        ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
		        service.spreadsheets().values()
		                .update(id, writeRange, vr)
		                .setValueInputOption("RAW")
		                .execute();
		       
		    } catch (Exception e) {
		    	e.printStackTrace();
		        // handle exception
		    }
		}
		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
			  @Override
			  public void onFailure(GoogleJsonError e,
			                        HttpHeaders responseHeaders)
			      throws IOException {
			    // Handle error
			    System.err.println(e.getMessage());
			  }

			  @Override
			  public void onSuccess(Permission permission,
			                        HttpHeaders responseHeaders)
			      throws IOException {
			    System.out.println("Permission ID: " + permission.getId());
			  }
			};
}

