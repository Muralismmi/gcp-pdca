package com.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@SuppressWarnings("serial")
@PersistenceCapable
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkPermitTypeField implements Serializable
{
	@PrimaryKey
	 @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long uniqueId;
	
	@Persistent
	String byDefaultEditable;
	@Persistent
	String defaultValue;
	@Persistent
	String fieldName;
	@Persistent
	int fieldSequence;
	@Persistent
	String fieldType;
	@Persistent
	String headerName;
	@Persistent
	String helpText;
	@Persistent
	String iD;
	@Persistent
	String isMandatory;
	@Persistent
	String masterDataReference;
	@Persistent
	List<String> options;
	@Persistent
	String showWhen;
	@Persistent
	String type;
	@Persistent
	String workpermitTypeName;
	@Persistent
	long workpermitTypeRefId;
	public Long getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getByDefaultEditable() {
		return byDefaultEditable;
	}
	public void setByDefaultEditable(String byDefaultEditable) {
		this.byDefaultEditable = byDefaultEditable;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getFieldSequence() {
		return fieldSequence;
	}
	public void setFieldSequence(int fieldSequence) {
		this.fieldSequence = fieldSequence;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public String getiD() {
		return iD;
	}
	public void setiD(String iD) {
		this.iD = iD;
	}
	public String getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getMasterDataReference() {
		return masterDataReference;
	}
	public void setMasterDataReference(String masterDataReference) {
		this.masterDataReference = masterDataReference;
	}
	public String getShowWhen() {
		return showWhen;
	}
	public void setShowWhen(String showWhen) {
		this.showWhen = showWhen;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWorkpermitTypeName() {
		return workpermitTypeName;
	}
	public void setWorkpermitTypeName(String workpermitTypeName) {
		this.workpermitTypeName = workpermitTypeName;
	}
	public long getWorkpermitTypeRefId() {
		return workpermitTypeRefId;
	}
	public void setWorkpermitTypeRefId(long workpermitTypeRefId) {
		this.workpermitTypeRefId = workpermitTypeRefId;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
}
