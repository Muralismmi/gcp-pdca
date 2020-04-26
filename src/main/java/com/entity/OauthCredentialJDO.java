package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class OauthCredentialJDO  implements Serializable {	
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long uniqueId;
	@Persistent
	private String technicalEmail;
	@Persistent
	private String accessToken;
	@Persistent
	private String refreshToken;
	@Persistent
	private List<String> scopesAuthorized	=new ArrayList<String>();
	@Persistent
	private boolean expired;
	@Persistent
	private Long obtainedOn;
	public Long getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getTechnicalEmail() {
		return technicalEmail;
	}
	public void setTechnicalEmail(String technicalEmail) {
		this.technicalEmail = technicalEmail;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public List<String> getScopesAuthorized() {
		return scopesAuthorized;
	}
	public void setScopesAuthorized(List<String> scopesAuthorized) {
		this.scopesAuthorized = scopesAuthorized;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public Long getObtainedOn() {
		return obtainedOn;
	}
	public void setObtainedOn(Long obtainedOn) {
		this.obtainedOn = obtainedOn;
	}
	
	
	
	
}