package com.helper;

/*
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class UrlFetch {
    private static final Logger log = Logger.getLogger(UrlFetch.class.getName());

    public JSONObject httpGET(String urlAndParam, int count) throws Exception 
    {
        URL url = null;
        JSONObject responseObject = null;
        try {
            String inputLine;
            url = new URL(urlAndParam);
            String inputToJson = "";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader readers = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = readers.readLine()) != null) {
                inputToJson = String.valueOf(inputToJson) + inputLine;
            }
            System.out.println("inputToJson ::" + inputToJson);
            try {
                JSONObject googleUserDetails = new JSONObject(inputToJson);
                responseObject = new JSONObject();
                //String emailId = googleUserDetails.getString("email");
                String firstName = googleUserDetails.getString("name");
                String picture = googleUserDetails.getString("picture");
                //responseObject.put("email", (Object)emailId);
                responseObject.put("name", (Object)firstName);
                responseObject.put("picture", (Object)picture);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        catch (Exception e) 
        {
        	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
            e.printStackTrace();
        }
        return responseObject;
    }
    
 /*   
    public String fetchContactInfo(String requestUrl) throws Exception 
	 {
   	String res	=	"";
   	try 
   	{
   		final URL url = new URL(requestUrl);
   		final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
   		BasicAuthFilter.addBasicAuth(urlConnection, "valeoService","dio\u0027vd4vs!po*$dsd76qdq");
   		if (urlConnection.getResponseCode() == HttpServletResponse.SC_OK) 
   		{
   			try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) 
   			{
   				String line;
   				while ((line = reader.readLine()) != null) 
   				{
   					res+=line;
   				}
   			}
   		}
   	} 
   	catch (Exception e) 
   	{
   		log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
           e.printStackTrace();
   	}
   	return res;
	 }*/
    
    public String httpGet(String urlAndParam, int count) throws Exception 
    {
        URL url = null;
        String response = null;
        try {
            String inputLine;
            url = new URL(urlAndParam);
            String inputToJson = "";
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader readers = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = readers.readLine()) != null) {
                inputToJson = String.valueOf(inputToJson) + inputLine;
            }
            System.out.println("inputToJson ::" + inputToJson);
            conn.disconnect();
            response	=	inputToJson;
        }
        catch (Exception e) 
        {
        	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
            e.printStackTrace();
        }
        return response;
    }

    public String httpPost(String content, String urlAndParam, String type) throws Exception 
    {
        String line = null;
        StringBuffer line1 = null;
        URL url = null;
        BufferedReader reader = null;
        String response = null;
        log.info("Inside  httpPost method :" + content);
        try {
            line1 = new StringBuffer();
            url = new URL(urlAndParam);
            log.info("Url to call :: " + url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            con.setRequestProperty("Host", "accounts.google.com");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            log.info("Content is  : " + content);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(content);
            out.close();
            log.info("Response Code : " + con.getResponseCode());
            if (con.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                log.info("The tostring of reader is  :  " + reader);
                while ((line = reader.readLine()) != null) {
                    line1.append(line);
                }
                response = line1.toString();
            }
            log.info("The response from the service is  : " + response);
        }
        catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        catch (Exception e) 
        {
        	log.log(Level.SEVERE,e.getStackTrace()+" Error : "+e);
			StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
        }
        return response;
    }
}
