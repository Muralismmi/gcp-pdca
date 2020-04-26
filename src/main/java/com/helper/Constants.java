package com.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Constants 
{
	public static final String WORK_PERMIT_TYPE_MASTERDATA_TYPE	=	"WorkPermitType";
	public static final String Collective_MASTERDATA_TYPE	=	"CollectiveAndPersonalProtection";
	public static final String FIELD_TYPE_MASTERDATA_TYPE	=	"FielType";
	public static final List<String> EMAIL_FIELDS =	Arrays.asList("createdBy","updatedBy","addedBy","emailUri","siteOwnerEmailUri","who_monitored","reqestedEmail","workResposibleEmail","personToInformEmail","userEmail"); // To fetch email id by querying email uri from search index
	public static final List<String> DATE_FIELDS =	Arrays.asList("createdDate","updatedDate","createdOn","updatedOn","addedOn","actionperformedOn","validityFromDate","validityToDate","monitoredDate","reqestedDate","plannedStartDate","plannedEndDate","singedDate","attachmentUploadedDate","F004_Date","carriedOn","autoCompletionUpdateOn");
	public static final List<String> VARIABLE_PART_CONFIGURATION_TABLE_COLUMNS	=	Arrays.asList("","siteName","siteCode","createdMode","createdBy","createdDate","updatedBy","updatedDate","maxNoFields","authorizedFieldType");
	public static final List<String> WORKPERMITTYPEFIELDTEMPLATEHEADERS	=	Arrays.asList("iD","headerName","fieldName","fieldType","options","masterDataReference","defaultValue","isMandatory","byDefaultEditable","showWhen","helpText","type");
	public static final List<String> MANDATORYCOLUMNVALUESBYDEFAULT	=	Arrays.asList("iD","headerName","fieldName","fieldType","type");
	public static final List<String> FieldType_VALUES  = Arrays.asList("TEXT","AUTOSUGGEST","NUMBER","RADIO","FILE","BIG TEXT","DATE","CHECKBOX","CAPTURE","SIGNATURE","TIME","VARIABLE","LISTBOX","LISTBOX1");
	public static final List<String> IsMandatory_ByDefaultEditable_VALUES  = Arrays.asList("YES","NO");
	public static final List<String> MANDATORYFIELDS	=	Arrays.asList("Req ID","Status","Date","Reference");
}