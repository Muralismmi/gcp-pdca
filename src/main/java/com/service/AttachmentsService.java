package com.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Attachments;


public interface AttachmentsService 
{

	Object delete(Attachments attachObj);
	Object deleteById(Long uniqueId);
	Object fetchById(String uniqueId);
	Object fetchByType(String type);
	Object saveAttachmentObj(Attachments attachObj);
	String dispatchUploadForm(HttpServletRequest req,HttpServletResponse resp,String errormsg);
	String dispatchUploadForm1(HttpServletRequest req,HttpServletResponse resp,String errormsg);
}
