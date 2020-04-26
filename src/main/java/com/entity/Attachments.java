package com.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class Attachments implements Serializable{
	
	@PrimaryKey	
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	public Long attachmentId;
	@Persistent	
	public String attachmentType;  //drivelink or fileupload
	@Persistent	
	public String referenceId; //workpermit ref Id
	@Persistent	
	public String url;
	@Persistent	
	public String fileName;
	@Persistent	
	public boolean active;
	@Persistent	
	public String createdBy;
	@Persistent	
	public Long createdDate;
	@Persistent	
	public String createdMode;
	@Persistent	
	public Long attachmentUploadedDate;
	@Persistent
	public String type; // defines attachment area (workpermit, helpinstruction etc...)
	
	
	public Long getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedMode() {
		return createdMode;
	}
	public void setCreatedMode(String createdMode) {
		this.createdMode = createdMode;
	}
	public Long getAttachmentUploadedDate() {
		return attachmentUploadedDate;
	}
	public void setAttachmentUploadedDate(Long attachmentUploadedDate) {
		this.attachmentUploadedDate = attachmentUploadedDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
