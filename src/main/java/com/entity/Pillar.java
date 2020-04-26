package com.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class Pillar implements Serializable{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String pillarType;
	@Persistent
	private String name;
	/*@Persistent
	private String location;
	@Persistent
	private String region;*/
	@Persistent
	private boolean active;
	@Persistent
	private	String createdBy;
	@Persistent
	private	String lastUpdatedBy;
	@Persistent
	private	long createdOn;
	@Persistent
	private	long lastUpdatedOn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPillarType() {
		return pillarType;
	}
	public void setPillarType(String pillarType) {
		this.pillarType = pillarType;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
