package com.service;

import java.util.HashMap;

public interface UserAccessService {
	Object checkAndCreateUser(String userEmail,String loggedinUser);
	Object fetchUserRoles(String emailUri);
	void constructUserToIndex(HashMap<String,Object> map);

}