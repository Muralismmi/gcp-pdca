package com.serviceImpl;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.AttachmentsDAO;
import com.DAOImpl.AttachmentsDAOImpls;
import com.entity.Attachments;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.helper.AppHelper;
import com.service.AttachmentsService;


public class AttachmentsServiceImpls implements AttachmentsService {

	private static final 	Logger log 		= 	Logger.getLogger(AttachmentsServiceImpls.class.getName());
	public final BlobstoreService blobstore=BlobstoreServiceFactory.getBlobstoreService();
	
	/*@Override
	public Object createOrUpdate(String input, String loggedinUser) 
	{
		HashMap<String,Object> responseMap	=	null;
		AttachmentsDAO	masterDataDao	=	null;
		CountService countService	=	null;
		HistoryService historyService	=	null;
		HashMap<String,Object> oldObj	=	null;
		HashMap<String,Object> newObj	=	null;
		Attachments inputObj	=	null;
		String loggedInUserEmailUri	=	null;
		UserAccessService userService	=	null;
		try
		{
			responseMap	=	new HashMap<String,Object>();
			masterDataDao	=	new AttachmentsDAOImpls();
			countService	=	new CountServiceImpls();
			historyService	=	new HistoryServiceImpls();
			userService	=	new UserAccessServiceImpls();
			if(input!=null && !input.equalsIgnoreCase(""))
			{
				loggedInUserEmailUri	=	userService.fetchEmailUriByUserEmail(loggedinUser);
				Attachments masterDataObj	=	new ObjectMapper().readValue(input,new TypeReference<MasterData>() {});
				inputObj	=	masterDataObj;	
				log.info("Input Attachement Obj  "+masterDataObj);
				//create or update
				if(masterDataObj!=null && masterDataObj.getAttachmentId()!=null && !masterDataObj.getAttachmentId().equals(""))
				{
					responseMap.put("data",masterDataObj);
					responseMap.put("Status","Success");
					responseMap.put("Message","Record added successfully");
				}
				else
				{
					log.info("Create");
					masterDataDao.save(masterDataObj);
					countService.saveCountDetails("MasterData",null,"Save");
					newObj	=	new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(masterDataObj),new TypeReference<HashMap<String,Object>>(){});
					historyService.createHistoryObject(loggedInUserEmailUri,"MasterData",newObj,"Created",new ObjectMapper().writeValueAsString(inputObj),null);
					responseMap.put("data",masterDataObj);
					responseMap.put("Status","Success");
					responseMap.put("Message","Record added successfully");
				}
			}
			else
			{
				responseMap.put("data",null);
				responseMap.put("Status","Failure");
				responseMap.put("Message","No Data to save");
			}
			
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
			responseMap.put("data",null);
			responseMap.put("Status","Failure");
			responseMap.put("Message","Something went wrong. Please contact Administrator");
		}
		return responseMap;
	}*/
	
	
	public String dispatchUploadForm(HttpServletRequest req,HttpServletResponse resp,String errormsg)
	{
		String uploadurl = null;
		try
		{
			
			UploadOptions opts=UploadOptions.Builder.withGoogleStorageBucketName(AppHelper.getDefaultBucketName());
		    log.info(opts.toString());
			uploadurl=blobstore.createUploadUrl("/attachments/createfileattachment",opts);
			log.warning("the generated url before is --- dispatchUploadForm "+ uploadurl);
			 if (errormsg != null) 
	         {
	             uploadurl="error";
	         }
			return uploadurl;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error in dispatchUploadForm : "+e);
			e.printStackTrace();
		}
		return uploadurl;
	}
    
	public String dispatchUploadForm1(HttpServletRequest req,HttpServletResponse resp,String errormsg)
	{
		String uploadurl = null;
		try
		{
			
			UploadOptions opts=UploadOptions.Builder.withGoogleStorageBucketName(AppHelper.getDefaultBucketName());
		    log.info(opts.toString());
			uploadurl=blobstore.createUploadUrl("/attachments/configupload",opts);
			log.warning("the generated url before is --- dispatchUploadForm "+ uploadurl);
			 if (errormsg != null) 
	         {
	             uploadurl="error";
	         }
			return uploadurl;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error in dispatchUploadForm : "+e);
			e.printStackTrace();
		}
		return uploadurl;
	}
    
	@Override
	public Object delete(Attachments attachObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteById(Long uniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fetchById(String uniqueId) 
	{
		HashMap<String,Object> responseMap = null;
		AttachmentsDAO attachmentDao = new AttachmentsDAOImpls();
		try
		{
			responseMap = new HashMap<String,Object>();
			Attachments resultObj = attachmentDao.fetchById(uniqueId);
			if(resultObj!=null)
			responseMap.put("data",resultObj);
			responseMap.put("Status","Success");
			responseMap.put("Message","Attachment fetched successfully");
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
			responseMap.put("data",null);
			responseMap.put("Status","Failure");
			responseMap.put("Message","Something went wrong. Please contact Administrator");
		}
		return responseMap;
	}

	@Override
	public Object fetchByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object saveAttachmentObj(Attachments attachObj) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
