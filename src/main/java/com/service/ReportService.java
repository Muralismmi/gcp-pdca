package com.service;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.entity.Request;
import com.entity.WorkPermitTypeField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public interface ReportService {
		Object	getQueryStringForReports(HashMap<String,String> inputMap);
		Object buildIndividualSectionHeaderRow(PdfPCell cell,PdfPTable table,String sectionHeaderName);
		Object buildIndividualSectionContentRows(PdfPTable table,List<WorkPermitTypeField> fieldsUnderSectionList,HashMap<String,Object> workPermitObj);
		Object constructCellFieldValueBasedOnFieldType(PdfPTable table,String fieldType,String fieldName,WorkPermitTypeField fieldObj,String fieldName_config);
}
