package com.DAO;

import java.util.HashMap;
import java.util.List;

import com.entity.WorkPermitType;

public interface WorkPermitTypeDAO {

	List<WorkPermitType> fetchActiveFromFirestore();
	WorkPermitType fetchWorkPermitTypeObjByTypeNameAndId(String permitType, String permitTypeUniqueId);
	WorkPermitType updatetoFirestore(WorkPermitType inputObj); 
	WorkPermitType fetchbyIdFromFirestore(String uniqueId);
	WorkPermitType fetchbyNameFromFirestore(String name);
}
