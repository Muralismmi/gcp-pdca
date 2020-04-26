package com.DAO;

import java.util.HashMap;

import com.entity.Attachments;


public interface AttachmentsDAO {
	
	Attachments fetchById(String uniqueId);
	
	Attachments saveToFirestore(HashMap<String,Object> attachmentsObj);
}
