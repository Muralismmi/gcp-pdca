package com.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class Line implements Serializable{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String plant;
	@Persistent
	private String plantId;
	@Persistent
	private String value;
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
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
