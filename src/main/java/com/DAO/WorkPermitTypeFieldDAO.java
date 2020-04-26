package com.DAO;

import java.util.HashMap;
import java.util.List;

import com.entity.WorkPermitTypeField;

public interface WorkPermitTypeFieldDAO {

	Object savetoFirestore(WorkPermitTypeField inputObj);
	Object updatetoFirestore(WorkPermitTypeField inputObj);
	void deleteFromFirestore(WorkPermitTypeField inputObj);
	List<WorkPermitTypeField> fetchByWorkPermitRefId1(Long id);
	List<WorkPermitTypeField> fetchByWorkPermitRefId(String id);
}
