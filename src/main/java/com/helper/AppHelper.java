package com.helper;

import static com.google.appengine.api.utils.SystemProperty.environment;

import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;
import com.google.apphosting.api.ApiProxy;

public class AppHelper 
{
	private static final Logger logger = Logger.getLogger(AppHelper.class.getName());
	private static SystemProperty.Environment.Value env = environment.value();
	private static String appId = ApiProxy.getCurrentEnvironment().getAppId();
	private static	String defaultBucketName="";
	private static String certificate = "" ;
	private static String serviceAccountId = "";
	private static String application_Name = "";
	private static String technicalMailID = "";
	private static String applicationURL ="";
	private static String spreadsheetClientID= "";
	private static String spreadsheetClientSecret= "";
	private static String domainName = "";
	private static String fromEmailID="";
	private static String host="";
	private static String mailUser="";
	private static String mailpwd="";
	private static String mailport="";
	
	
	static
	{
		
		String[] myStringArray = appId.split("~");
		if(myStringArray.length>1)
		{
			appId = myStringArray[1];
		}
		System.out.println(myStringArray.length);
		logger.warning("the app name is"+appId);
		if(appId.equals("pdca-test-1"))
		{
			defaultBucketName = "pdca-test-1.appspot.com";
			certificate ="pdca-test-1-9d5b09bead7c.p12";
			serviceAccountId = "pdca-test-1@appspot.gserviceaccount.com";
			application_Name = appId ;
			applicationURL = "https://pdca-test-1.appspot.com";
			spreadsheetClientID = "99935715402-9nn90o0d07qtn7jci5eupifglql9nsrj.apps.googleusercontent.com";
			spreadsheetClientSecret = "043CHAccNEBYCylYA-d6mLeJ";
			domainName = "gmail.com";
			technicalMailID="techacntgcp@gmail.com";
			 fromEmailID ="no-reply@pdca-test-1.appspotmail.com";
			
		}
		else
		{
			logger.info("Inside DEV Mode");
			defaultBucketName = "pdca-test-1.appspot.com";
			certificate ="pdca-test-1-9d5b09bead7c.p12";
			serviceAccountId = "pdca-test-1@appspot.gserviceaccount.com";
			application_Name = appId ;
			applicationURL = "http://localhost:8888/";
			spreadsheetClientID = "99935715402-9nn90o0d07qtn7jci5eupifglql9nsrj.apps.googleusercontent.com";
			spreadsheetClientSecret = "043CHAccNEBYCylYA-d6mLeJ";
			domainName = "gmail.com";
			 technicalMailID="techacntgcp@gmail.com";
			 fromEmailID ="no-reply@pdca-test-1.appspotmail.com";
			 host="";
			mailUser="";
			mailpwd="";
			mailport="";
		}
	}
	
	
	

	public static String getFromEmailID() {
		return fromEmailID;
	}

	public static void setFromEmailID(String fromEmailID) {
		AppHelper.fromEmailID = fromEmailID;
	}

	public static String getTechnicalMailID() {
		return technicalMailID;
	}

	public static void setTechnicalMailID(String technicalMailID) {
		AppHelper.technicalMailID = technicalMailID;
	}

	public static String getDefaultBucketName() {
		return defaultBucketName;
	}

	public static void setDefaultBucketName(String defaultBucketName) {
		AppHelper.defaultBucketName = defaultBucketName;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static String getCertificate() {
		return certificate;
	}

	public static void setCertificate(String certificate) {
		AppHelper.certificate = certificate;
	}

	public static String getServiceAccountId() {
		return serviceAccountId;
	}

	public static void setServiceAccountId(String serviceAccountId) {
		AppHelper.serviceAccountId = serviceAccountId;
	}

	public static String getApplication_Name() {
		return application_Name;
	}

	public static void setApplication_Name(String application_Name) {
		AppHelper.application_Name = application_Name;
	}

	

	public static String getApplicationURL() {
		return applicationURL;
	}

	public static void setApplicationURL(String applicationURL) {
		AppHelper.applicationURL = applicationURL;
	}

	public static String getSpreadsheetClientID() {
		return spreadsheetClientID;
	}

	public static void setSpreadsheetClientID(String spreadsheetClientID) {
		AppHelper.spreadsheetClientID = spreadsheetClientID;
	}

	public static String getSpreadsheetClientSecret() {
		return spreadsheetClientSecret;
	}

	public static void setSpreadsheetClientSecret(String spreadsheetClientSecret) {
		AppHelper.spreadsheetClientSecret = spreadsheetClientSecret;
	}

	public static String getDomainName() {
		return domainName;
	}

	
	public static void setDomainName(String domainName) {
		AppHelper.domainName = domainName;
	}


	public static String getAppId() {
		return appId;
	}

	public static void setAppId(String appId) {
		AppHelper.appId = appId;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		AppHelper.host = host;
	}

	public static String getMailUser() {
		return mailUser;
	}

	public static void setMailUser(String mailUser) {
		AppHelper.mailUser = mailUser;
	}

	public static String getMailpwd() {
		return mailpwd;
	}

	public static void setMailpwd(String mailpwd) {
		AppHelper.mailpwd = mailpwd;
	}

	public static String getMailport() {
		return mailport;
	}

	public static void setMailport(String mailport) {
		AppHelper.mailport = mailport;
	}


}

