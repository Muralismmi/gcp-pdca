package com.entity;
import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class User implements Serializable{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private	String userEmail;
	@Persistent
	private	String title;
	@Persistent
	private	String firstName;
	@Persistent
	private	String lastName;
	@Persistent
	private	String userName;
	@Persistent
	private	long plantId;
	@Persistent
	private	String plantName;
	@Persistent
	private	boolean pillarLead;
	@Persistent
	private	long pillarId;
	@Persistent
	private	String pillarName;
	@Persistent
	private	boolean projectLead;
	@Persistent
	private	boolean mentor;
	@Persistent
	private	boolean editor;
	@Persistent
	private	boolean active;
	@Persistent
	private	List<String> roles;
	@Persistent
	private	String createdBy;
	@Persistent
	private	String lastUpdatedBy;
	@Persistent
	private	long createdOn;
	@Persistent
	private	long lastUpdatedOn;
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	
	public boolean isPillarLead() {
		return pillarLead;
	}
	public void setPillarLead(boolean pillarLead) {
		this.pillarLead = pillarLead;
	}
	public String getPillarName() {
		return pillarName;
	}
	public long getPlantId() {
		return plantId;
	}
	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}
	public long getPillarId() {
		return pillarId;
	}
	public void setPillarId(long pillarId) {
		this.pillarId = pillarId;
	}
	public void setPillarName(String pillarName) {
		this.pillarName = pillarName;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isProjectLead() {
		return projectLead;
	}
	public void setProjectLead(boolean projectLead) {
		this.projectLead = projectLead;
	}
	public boolean isMentor() {
		return mentor;
	}
	public void setMentor(boolean mentor) {
		this.mentor = mentor;
	}
	public boolean isEditor() {
		return editor;
	}
	public void setEditor(boolean editor) {
		this.editor = editor;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public long getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
	public long getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(long lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
	
}
