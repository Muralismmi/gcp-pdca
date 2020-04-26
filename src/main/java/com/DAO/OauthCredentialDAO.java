package com.DAO;

import java.util.HashMap;

import com.entity.OauthCredentialJDO;

public interface OauthCredentialDAO 
{
	OauthCredentialJDO save(OauthCredentialJDO oauthCredentialObj) throws Exception;
	HashMap<String, Object> fetchByTechnicalEmail(String email) throws Exception;
}
