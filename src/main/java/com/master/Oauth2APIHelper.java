package com.master;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.OauthCredentialDAO;
import com.DAOImpl.OauthCredentialDAOImpls;
import com.entity.OauthCredentialJDO;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.SecurityUtils;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.helper.AppHelper;
import com.helper.UserUtil;




@Controller
@RequestMapping(value="/oauthAPIController")
public class Oauth2APIHelper{
	
	private static final 	Logger 	log  = 	Logger.getLogger(Oauth2APIHelper.class.getName()); 
		
	 @RequestMapping(value={"/authenticateTechnicalAccount"}, method={RequestMethod.GET})
	 public String authenticateTechnicalAccount(HttpServletResponse response, HttpServletRequest request) throws Exception 
	 {
		 	String url = null;
	        try 
	        {
	                HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	                JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	                List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/drive.file",SheetsScopes.SPREADSHEETS_READONLY,SheetsScopes.SPREADSHEETS,DriveScopes.DRIVE,DriveScopes.DRIVE_READONLY,StorageScopes.DEVSTORAGE_FULL_CONTROL,StorageScopes.CLOUD_PLATFORM,
	                									StorageScopes.CLOUD_PLATFORM_READ_ONLY,StorageScopes.DEVSTORAGE_READ_ONLY,StorageScopes.DEVSTORAGE_READ_WRITE,"https://www.googleapis.com/auth/userinfo.email");
	                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                		HTTP_TRANSPORT, JSON_FACTORY,/*"877010652422-ago43e6bccmm5mlkede1fnneee899kfc.apps.googleusercontent.com"*/ AppHelper.getSpreadsheetClientID(),/*"FkKpvvxfqjXqNCzS5A5Xgg5c"*/ AppHelper.getSpreadsheetClientSecret(), SCOPES)
	        		.setAccessType("offline")
	        		.setApprovalPrompt("force").build();
	        		 url = flow.newAuthorizationUrl().setRedirectUri(AppHelper.getApplicationURL()/*"http://localhost:8080/"*/+"/oauthAPIController/oauth2callback").build();
	        }
	        catch (Exception e) 
	        {
	            log.info("Exception authenticateUser :: " + e.getMessage());
	            log.log(Level.SEVERE,e.getStackTrace()+" error : "+e);
	        }
	        return "redirect:"+url;
	 }
	 public static  Storage getService() throws IOException,GeneralSecurityException 
		{
			Storage storageService;
			HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		    JsonFactory JSON_FACTORY = new JacksonFactory();
			List<String> scopes = new ArrayList<>();
			scopes.add("https://www.googleapis.com/auth/devstorage.full_control");
			log.warning("Before privatekey"+AppHelper.getCertificate());
			PrivateKey privateKey = SecurityUtils.loadPrivateKeyFromKeyStore(
					SecurityUtils.getPkcs12KeyStore(),
					Oauth2APIHelper.class.getClassLoader().getResourceAsStream(AppHelper.getCertificate()),
					"notasecret", "privatekey", "notasecret");
			log.warning("After privatekey");
			GoogleCredential credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(AppHelper.getServiceAccountId())
					.setServiceAccountScopes(StorageScopes.all())
					.setServiceAccountPrivateKey(privateKey).build();
			credential.refreshToken();
			log.warning("After credential " + credential.getAccessToken() + " -- "
					+ credential.getServiceAccountId());
			storageService = new Storage.Builder(httpTransport, JSON_FACTORY,credential).setApplicationName(AppHelper.getApplication_Name())
					.build();
			return storageService;
		}
	 @RequestMapping(value={"/oauth2callback"}, method={RequestMethod.GET})
	 public void oauth2callback(HttpServletResponse response, HttpServletRequest request) throws Exception 
	 {
	        String code = null;
	        OauthCredentialDAO oauthCredentialDao = new OauthCredentialDAOImpls();
	        try 
	        {
	            log.info("comes here :: wwwwwwwww"+request);
	            if (request.getParameter("code") != null) 
	            {
	            	 code = request.getParameter("code");
	            	System.out.println("code :: "+code);
	            	HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	                JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	                List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/drive.file",SheetsScopes.SPREADSHEETS_READONLY,SheetsScopes.SPREADSHEETS,DriveScopes.DRIVE,DriveScopes.DRIVE_READONLY,StorageScopes.DEVSTORAGE_FULL_CONTROL,StorageScopes.CLOUD_PLATFORM,
							StorageScopes.CLOUD_PLATFORM_READ_ONLY,StorageScopes.DEVSTORAGE_READ_ONLY,StorageScopes.DEVSTORAGE_READ_WRITE);
	                
	            	 GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		                		HTTP_TRANSPORT, JSON_FACTORY,/*"877010652422-ago43e6bccmm5mlkede1fnneee899kfc.apps.googleusercontent.com"*/ AppHelper.getSpreadsheetClientID(),/*"FkKpvvxfqjXqNCzS5A5Xgg5c"*/ AppHelper.getSpreadsheetClientSecret(), SCOPES)
		        		.setAccessType("offline")
		        		.setApprovalPrompt("force").build();
	            	 
	        		GoogleTokenResponse responses = flow.newTokenRequest(code).setRedirectUri(AppHelper.getApplicationURL()/*"http://localhost:8080/"*/+"/oauthAPIController/oauth2callback").execute();
	        		
	        		GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
	        		                .setJsonFactory(JSON_FACTORY)
	        		                .setClientSecrets(/*"877010652422-ago43e6bccmm5mlkede1fnneee899kfc.apps.googleusercontent.com"*/AppHelper.getSpreadsheetClientID(),/*"FkKpvvxfqjXqNCzS5A5Xgg5c"*/AppHelper.getSpreadsheetClientSecret())
	        		                .build()
	        		                .setFromTokenResponse(responses);
	        		
	        		String accessToken1 = credential.getAccessToken();
	        		String refreshToken1 = credential.getRefreshToken();
	        		
	        		String currentUser	=	UserUtil.getCurrentUser();
	        		
	        		HashMap<String, Object> oauthCredential	=	(HashMap<String, Object>) oauthCredentialDao.fetchByTechnicalEmail(currentUser);
	        		if(oauthCredential!=null)
	        		{
	        			log.info("Update oauthCredential");
	        			oauthCredential.put("technicalEmail",currentUser);
	        			oauthCredential.put("obtainedOn",new Date().getTime());
	        			oauthCredential.put("accessToken",accessToken1);
		        		if(refreshToken1!=null)
		        		{
		        			oauthCredential.put("refreshToken",refreshToken1);
		        		}
		        		oauthCredential.put("expired",false);
		        		oauthCredential.put("scopesAuthorized",SCOPES);
		        		oauthCredential.put("uniqueId",oauthCredential.get("uniqueId"));
		        		oauthCredentialDao.save(new ObjectMapper().convertValue(oauthCredential, OauthCredentialJDO.class));
	        		}
	        		else
	        		{
	        			log.info("Save oauthCredential");
	        			oauthCredential = new HashMap<String, Object>();
	        			oauthCredential.put("technicalEmail",currentUser);
	        			oauthCredential.put("obtainedOn",new Date().getTime());
	        			oauthCredential.put("accessToken",accessToken1);
		        		if(refreshToken1!=null)
		        		{
		        			oauthCredential.put("refreshToken",refreshToken1);
		        		}
		        		oauthCredential.put("expired",false);
		        		oauthCredential.put("scopesAuthorized",SCOPES);
		        		oauthCredentialDao.save(new ObjectMapper().convertValue(oauthCredential, OauthCredentialJDO.class));
	        		}
	        		log.info("accessToken1 :: "+accessToken1 +"refreshToken1 :: "+refreshToken1);
	            }else {
	            	log.info("Coming inside no code");
	            }
	            	
	        }
	        catch (Exception e) 
	        {
	        	 log.info("Exception authenticateUser :: " + e.getMessage());
	        	 log.log(Level.SEVERE,e.getStackTrace()+" error : "+e);
	        }
	    }
	 
}