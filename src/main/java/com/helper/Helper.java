package com.helper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.DAO.OauthCredentialDAO;
import com.DAO.WorkPermitTypeDAO;
import com.DAO.WorkPermitTypeFieldDAO;
import com.DAOImpl.OauthCredentialDAOImpls;
import com.DAOImpl.WorkPermitTypeDAOImpls;
import com.DAOImpl.WorkPermitTypeFieldDAOImpls;
import com.entity.OauthCredentialJDO;
import com.entity.WorkPermitType;
import com.entity.WorkPermitTypeField;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.appengine.api.datastore.Text;




public class Helper {
	
	private static final 	Logger log 		= 	Logger.getLogger(Helper.class.getName());
	public static String FormFirstandLastname(String email){

		if(email != null && email != "")
		{
		   	String Emailwithoutdoamin = email.split("@")[0];
		   	String firstname = Emailwithoutdoamin.split("[@._]")[0];
		   	firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
		   	String Lastname = Emailwithoutdoamin.split("[@._]")[1];
		   	Lastname = Lastname.substring(0,1).toUpperCase() + Lastname.substring(1).toLowerCase();
		   	Lastname = Lastname.toUpperCase();
		   	return firstname + " " + Lastname;
		}
		else
		{
		return "";
		}
  }
	
	public static String getDateString_timestamp(Long dateStamp)                    //1487203200000 to 2017-02-16 (Eg)
	{ 
		 	String datout=null;
			try
			{
				Date newDate= new Date(dateStamp);
				System.out.println("Date for timestamp :::"+newDate);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				datout=sdf.format(dateStamp);
		        System.out.println("Final Date String : "+datout);
			}
			catch(Exception e)
			{
				System.out.println("Error in getDateString_timestamp Method::::"+e.getMessage());
			}
			return datout;
	}
	
	public static String checkEmptyToaddFieldinIndex(String fieldValue)
	{
		String result = null;
		try
		{
				if(fieldValue!=null && !fieldValue.equals(""))
				{
					result = fieldValue;
				}
				else
				{
					result= "";
				}
		}
		catch(Exception e)
		{
			log.info("Exception occured checkEmptyToaddFieldinIndex");
			log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
		}
		return result ;
	}

	public static List<String> getCoulmnValuesOfFieldNameColumn(List<LinkedHashMap<String, Object>> resultList)
	{
		log.info(" inside getCoulmnValuesOfFieldNameColumn");
		List<String> fieldNames = new ArrayList<String>();
		try
		{
			if(resultList!=null && resultList.size()>0)
			{
				for(HashMap<String,Object> map:resultList)
				{
					if(map.get("FieldName")!=null) {
						fieldNames.add(String.valueOf(map.get("FieldName")));
					}
				}
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error  getCoulmnValuesOfFieldNameColumn : "+e);
			e.printStackTrace();
		}
		return fieldNames;	
	}
	
	
	
	public Object readPermitTypeFieldsFromSheet(String spreadSheetUrl,String workPermitTypeName, String workPermitTypeRefId, String loggedInUserEmail) 
	{
		HashMap<String,Object> responseMap = null;
		String sheetID = null;
		List<String> templateHeaderList = null;
		HashMap<String,Object> workPermitTypeFieldObj = null;
		int maxNoOfFields = 0;
		WorkPermitTypeDAO workPermitTypeDao = new WorkPermitTypeDAOImpls();
		WorkPermitTypeFieldDAO workPermitTypeFieldDao	=	new WorkPermitTypeFieldDAOImpls();
		WorkPermitType resultObj = null;
		List<WorkPermitTypeField> workPermitTypeFieldObjList = null;
		List<List<Object>> errorMsgList = new ArrayList<List<Object>>();
		List<Object> errorStr = null;
		int gridId = 0;
		try
		{
			responseMap = new HashMap<String ,Object>();
			templateHeaderList = new ArrayList<>();
			resultObj = new WorkPermitType();
			templateHeaderList = Constants.WORKPERMITTYPEFIELDTEMPLATEHEADERS;
			sheetID =	spreadSheetUrl.split("d/")[1].split("/")[0];
			String sheetGridId= spreadSheetUrl.split("d/")[1].split("/")[1];
			gridId = Integer.parseInt(sheetGridId.split("gid=")[1]);
			System.out.println("gridId "+ gridId);
			boolean isErrorDetailsPresent  = false;
			if(sheetID!=null)
			{
				System.out.println("sheetID  "+sheetID);
				List<LinkedHashMap<String,Object>>  resultList	= new SpreadSheetHelper().readSpreadhsheetAndUpdateContent(sheetID,templateHeaderList,1);
				log.warning("resultList.size() : "+resultList.size());
				if(resultList!=null && resultList.size()>0)
				{
					workPermitTypeFieldObjList = new ArrayList<WorkPermitTypeField>();
					for(HashMap<String,Object> map:resultList)
					{
						workPermitTypeFieldObj = new HashMap<String,Object>();
						errorStr = new ArrayList<Object>();
						log.info("map here 497"+ map);
						errorStr = new SpreadSheetHelper().validateWorkPermitTypeFieldSheet(map);
						log.info("errorStr "+errorStr);
						if(errorStr!=null && errorStr.size()>0 && !errorStr.contains(""))
						{
							if(!errorStr.contains(""))
							{
								isErrorDetailsPresent = true;
							}
							errorMsgList.add(errorStr);
						}
						else
						{
							for(String header : templateHeaderList)
							{
								if(map.containsKey(header))
								{
									workPermitTypeFieldObj.put(header,Helper.checkEmptyToaddFieldinIndex(String.valueOf(map.get(header))));
								}
								else
								{
									workPermitTypeFieldObj.put(header,"");
								}
							}
							errorMsgList.add(Arrays.asList(""));
							//workPermitTypeFieldObj.put("type", "FIXED");
							workPermitTypeFieldObj.put("workpermitTypeName", workPermitTypeName);
							workPermitTypeFieldObj.put("workpermitTypeRefId", workPermitTypeRefId);
							workPermitTypeFieldObj.put("fieldSequence", maxNoOfFields);
							if(workPermitTypeFieldObj.get("options")!=null  && !workPermitTypeFieldObj.get("options").equals(""))
							{
								String[] optionsStr = String.valueOf(workPermitTypeFieldObj.get("options")).split(",");
								//List<String> optionsArr = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(workPermitTypeFieldObj.get("options")),new TypeReference<List<String>>() {});
								workPermitTypeFieldObj.put("options", Arrays.asList(String.valueOf(workPermitTypeFieldObj.get("options")).split(",")));
							}
							else
							{
								workPermitTypeFieldObj.put("options", new ArrayList<String>());
							}
							log.info("workPermitTypeFieldObj "+new ObjectMapper().writeValueAsString(workPermitTypeFieldObj));
							workPermitTypeFieldObjList.add(new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(workPermitTypeFieldObj),new TypeReference<WorkPermitTypeField>() {}));
						}
						maxNoOfFields++;
					}
					resultObj  = workPermitTypeDao.fetchWorkPermitTypeObjByTypeNameAndId(workPermitTypeName, workPermitTypeRefId);
					if(resultObj!=null) 
					{
						
							if(!isErrorDetailsPresent)
							{
								log.info("workPermitTypeFieldObjList Size "+workPermitTypeFieldObjList.size());
								
								for(WorkPermitTypeField obj : workPermitTypeFieldObjList) {
									workPermitTypeFieldDao.savetoFirestore(obj);
								}
								resultObj.setNoOfFields(maxNoOfFields);
								resultObj.setUploadStatus("Success");
								resultObj.setFieldJson(new Text(new ObjectMapper().writeValueAsString(resultList)));
								log.info("Data pushed to firestore and updated in workpermit type Obj "+isErrorDetailsPresent);
							}
							else
							{
								//send error mail to the user
								log.info("Inside Error === > Else "+isErrorDetailsPresent );
								resultObj.setUploadStatus("Error");
								new SpreadSheetHelper().writeIntoSpreadSheet(sheetID,errorMsgList,gridId);
							}
							workPermitTypeDao.updatetoFirestore(resultObj);
							ArrayList<String> tolist = new ArrayList<String>();
							ArrayList<String> cclist = new ArrayList<String>();
							ArrayList<String> bcclist = new ArrayList<String>();
							tolist.add(loggedInUserEmail);
				    }
				}
				responseMap.put("data",null);
				responseMap.put("Status","Success");
				responseMap.put("Message","Data has been read from the sheet successfully");
			}
			else
			{
				responseMap.put("data",null);
				responseMap.put("Status","Failure");
				responseMap.put("Message","SheetID is Empty");
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
			responseMap.put("data",null);
			responseMap.put("Status","Failure");
			responseMap.put("Message","Something went wrong in readPermitTypeFieldsFromSheet. Please contact Administrator");
		}
		return responseMap;
	}
	@SuppressWarnings("unchecked")
	public static Sheets getSheetsService() throws Exception
	 {
		Sheets sheetService	=	null;
		String technicalEmail = null;
		HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        OauthCredentialDAO oauthCredentailDao	=	new OauthCredentialDAOImpls();
		try
		{
			//technicalEmail	=	ModeUtil.getTechnicalMail();
			technicalEmail = AppHelper.getTechnicalMailID();
			HashMap<String, Object> oauth	=	oauthCredentailDao.fetchByTechnicalEmail(technicalEmail);
			OauthCredentialJDO oauthObj = new ObjectMapper().convertValue(oauth,OauthCredentialJDO.class);
			if(oauthObj!=null)
			{
				System.out.println("Before credential builder - checking ---");
				
				GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(JSON_FACTORY)
	   			     .setTransport(HTTP_TRANSPORT).setClientSecrets(AppHelper.getSpreadsheetClientID(),AppHelper.getSpreadsheetClientSecret()).build();
				credential.setAccessToken(String.valueOf(oauthObj.getAccessToken()));
				credential.setRefreshToken(String.valueOf(oauthObj.getRefreshToken()));
				log.warning("credential : "+credential.getRefreshToken());
				credential.refreshToken();
				sheetService= new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(AppHelper.getApplication_Name())
                .build();
				log.warning("sheetService 332: "+sheetService);
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getStackTrace()+" Error getSheetsService : "+e);
			return sheetService;
		}
		return sheetService;
    }
	@SuppressWarnings("unchecked")
	public static Drive getDriveService1() throws Exception
	 {
		Drive driveService	=	null;
		String technicalEmail = null;
		HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        OauthCredentialDAO oauthCredentailDao	=	new OauthCredentialDAOImpls();
		try
		{
			//technicalEmail	=	ModeUtil.getTechnicalMail();
			technicalEmail = AppHelper.getTechnicalMailID();
			HashMap<String, Object> oauth	=	oauthCredentailDao.fetchByTechnicalEmail(technicalEmail);
			OauthCredentialJDO oauthObj = new ObjectMapper().convertValue(oauth,OauthCredentialJDO.class);
			if(oauthObj!=null)
			{
				System.out.println("Before credential builder - checking ---");
				
				GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(JSON_FACTORY)
	   			     .setTransport(HTTP_TRANSPORT).setClientSecrets(AppHelper.getSpreadsheetClientID(),AppHelper.getSpreadsheetClientSecret()).build();
				credential.setAccessToken(String.valueOf(oauthObj.getAccessToken()));
				credential.setRefreshToken(String.valueOf(oauthObj.getRefreshToken()));
				log.warning("credential : "+credential.getRefreshToken());
				credential.refreshToken();
				driveService= new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(AppHelper.getApplication_Name())
                .build();
				
			
				log.warning("sheetService 332: "+driveService);
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getStackTrace()+" Error getSheetsService : "+e);
			return driveService;
		}
		return driveService;
    }
}
