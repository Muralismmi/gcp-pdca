package com.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable
public class Request implements Serializable{
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String formId;
	@Persistent
	private String formType;
	@Persistent
	private String plant;
	@Persistent
	private String status;
	@Persistent
	private String line;
	@Persistent
	private String area;
	@Persistent
	private String station;
	@Persistent
	private String projectLead;
	@Persistent
	private long projectLeadId;
	@Persistent
	private String projectLeadName;
	
	@Persistent
	private Text teamMembers;
	/*@Persistent
	private List<Long> teamMembersID;
	@Persistent
	private List<String> teamMembersName;*/
	@Persistent
	private String mentor;
	@Persistent
	private long mentorId;
	@Persistent
	private String mentorName;
	
	@Persistent
	private String primaryPillar;
	@Persistent
	private String secondaryPillar;
	@Persistent
	private List<String> lossType;
	@Persistent
	private float lossValue ;
	@Persistent
	private Text problemStatement;
	@Persistent
	private	long startDate;
	@Persistent
	private	long targetDate;
	@Persistent
	private List<String> tools ;
	@Persistent
	private List<String> benefitType;
	@Persistent
	private float benefitValue;
	@Persistent
	private float cost;
	@Persistent
	private	long actualCompletionDate;
	
	@Persistent
	private Text variableFieldData;
		
	@Persistent
	private	String createdBy;
	@Persistent
	private	String lastUpdatedBy;
	@Persistent
	private	long createdOn;
	@Persistent
	private	long lastUpdatedOn;
	@Persistent
	private Text rejectComments;
	@Persistent
	private Text approver1Comments;
	@Persistent
	private Text approver2Comments;
	@Persistent
	private Text approver3Comments;
	@Persistent
	private	String approver1;
	@Persistent
	private	String approver2;
	@Persistent
	private	String approver3;
	@Persistent
	private String rejectedBy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFormType() {
		return formType;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getStatus() {
		return status;
	}
	
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getProjectLead() {
		return projectLead;
	}
	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}
	
	public Text getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(Text teamMembers) {
		this.teamMembers = teamMembers;
	}
	public String getMentor() {
		return mentor;
	}
	public Text getRejectComments() {
		return rejectComments;
	}
	public void setRejectComments(Text rejectComments) {
		this.rejectComments = rejectComments;
	}
	
	public Text getApprover1Comments() {
		return approver1Comments;
	}
	public void setApprover1Comments(Text approver1Comments) {
		this.approver1Comments = approver1Comments;
	}
	public Text getApprover2Comments() {
		return approver2Comments;
	}
	public void setApprover2Comments(Text approver2Comments) {
		this.approver2Comments = approver2Comments;
	}
	public Text getApprover3Comments() {
		return approver3Comments;
	}
	public void setApprover3Comments(Text approver3Comments) {
		this.approver3Comments = approver3Comments;
	}
	public String getApprover1() {
		return approver1;
	}
	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}
	public String getApprover2() {
		return approver2;
	}
	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}
	public String getApprover3() {
		return approver3;
	}
	public void setApprover3(String approver3) {
		this.approver3 = approver3;
	}
	public void setMentor(String mentor) {
		this.mentor = mentor;
	}
	public String getPrimaryPillar() {
		return primaryPillar;
	}
	public void setPrimaryPillar(String primaryPillar) {
		this.primaryPillar = primaryPillar;
	}
	public String getSecondaryPillar() {
		return secondaryPillar;
	}
	public void setSecondaryPillar(String secondaryPillar) {
		this.secondaryPillar = secondaryPillar;
	}
	public List<String> getLossType() {
		return lossType;
	}
	public void setLossType(List<String> lossType) {
		this.lossType = lossType;
	}
	public float getLossValue() {
		return lossValue;
	}
	public void setLossValue(float lossValue) {
		this.lossValue = lossValue;
	}
	public Text getProblemStatement() {
		return problemStatement;
	}
	public void setProblemStatement(Text problemStatement) {
		this.problemStatement = problemStatement;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(long targetDate) {
		this.targetDate = targetDate;
	}
	public List<String> getTools() {
		return tools;
	}
	public void setTools(List<String> tools) {
		this.tools = tools;
	}
	public List<String> getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(List<String> benefitType) {
		this.benefitType = benefitType;
	}
	public float getBenefitValue() {
		return benefitValue;
	}
	public void setBenefitValue(float benefitValue) {
		this.benefitValue = benefitValue;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public long getActualCompletionDate() {
		return actualCompletionDate;
	}
	public void setActualCompletionDate(long actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}
	public Text getVariableFieldData() {
		return variableFieldData;
	}
	public void setVariableFieldData(Text variableFieldData) {
		this.variableFieldData = variableFieldData;
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
	
	
	public long getProjectLeadId() {
		return projectLeadId;
	}
	public void setProjectLeadId(long projectLeadId) {
		this.projectLeadId = projectLeadId;
	}
	public String getProjectLeadName() {
		return projectLeadName;
	}
	public void setProjectLeadName(String projectLeadName) {
		this.projectLeadName = projectLeadName;
	}
	
	public long getMentorId() {
		return mentorId;
	}
	public void setMentorId(long mentorId) {
		this.mentorId = mentorId;
	}
	public String getMentorName() {
		return mentorName;
	}
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	
}
