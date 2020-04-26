package com.entity;


import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable
public class WorkPermitType implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long uniqueId;
	@Persistent
	String workPermitType;
	@Persistent
	String workPermitIdPrefix;
	@Persistent
	String uploadStatus;
	@Persistent
	String updatedBy;
	@Persistent
	String sheetId;
	@Persistent
	int noOfFields;
	@Persistent
	String createdMode;
	@Persistent
	String createdBy;
	@Persistent
	Long createdDate;
	@Persistent
	Long updatedDate;
	@Persistent
	boolean activeStatus;
	@Persistent
	Text FieldJson;
	public Long getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getWorkPermitType() {
		return workPermitType;
	}
	public void setWorkPermitType(String workPermitType) {
		this.workPermitType = workPermitType;
	}
	public String getWorkPermitIdPrefix() {
		return workPermitIdPrefix;
	}
	public void setWorkPermitIdPrefix(String workPermitIdPrefix) {
		this.workPermitIdPrefix = workPermitIdPrefix;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public int getNoOfFields() {
		return noOfFields;
	}
	public void setNoOfFields(int noOfFields) {
		this.noOfFields = noOfFields;
	}
	public String getCreatedMode() {
		return createdMode;
	}
	public void setCreatedMode(String createdMode) {
		this.createdMode = createdMode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Text getFieldJson() {
		return FieldJson;
	}
	public void setFieldJson(Text fieldJson) {
		FieldJson = fieldJson;
	}
	

}
