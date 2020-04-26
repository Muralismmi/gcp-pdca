package com.helper;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.master.RequestController;


public class MailHelper {
	private static final Logger log = Logger.getLogger(RequestController.class.getName());
	public void sendMail(Set<String> toList,Set<String> ccList,Set<String> BccList,String subject,StringBuffer mailContent) {
			String host=AppHelper.getHost();  
			final String user=AppHelper.getMailUser();//change accordingly  
			final String password=AppHelper.getMailpwd();//change accordingly  
		    
		  
		   //Get the session object  
		   Properties props = new Properties();  
		   props.put("mail.smtp.host",host);  
		   props.put("mail.smtp.port",AppHelper.getMailport());  
		   props.put("mail.smtp.auth", "true");  
		     
		   Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {  
			   	protected PasswordAuthentication getPasswordAuthentication() {  
		    	  return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session); 
		     
		     message.setFrom(new InternetAddress(AppHelper.getFromEmailID())); 
		    for(String str:toList) {
		    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(str));
		    }
		       for(String str:ccList) {
		    		message.addRecipient(Message.RecipientType.CC,new InternetAddress(str));
		       }
		       for(String str:BccList) {
		    		message.addRecipient(Message.RecipientType.BCC,new InternetAddress(str));
		       }
		   //  message.addRecipient(Message.RecipientType.TO,new InternetAddress("nickjosegcp@gmail.com"));  
		     message.setSubject(subject);  
		     System.out.println(mailContent.toString());
		     message.setText(mailContent.toString());  
		       
		    //send the message  
		     Transport.send(message);  
		     log.info(message.toString());
		     System.out.println("message sent successfully...");  
		   
		     } catch (MessagingException e) {e.printStackTrace();}  
		 }  
	
}
