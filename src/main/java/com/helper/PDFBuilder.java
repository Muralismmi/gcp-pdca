package com.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.DAO.RequestDAO;
import com.DAO.WorkPermitTypeDAO;
import com.DAO.WorkPermitTypeFieldDAO;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.WorkPermitTypeDAOImpls;
import com.DAOImpl.WorkPermitTypeFieldDAOImpls;
import com.entity.Request;
import com.entity.WorkPermitType;
import com.entity.WorkPermitTypeField;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.service.ReportService;
import com.serviceImpl.ReportServiceImpls;


/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
	private static final 	Logger logger 	= 		Logger.getLogger(PDFBuilder.class.getName());
	
	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map<String, Object> model, Document doc,PdfWriter writer, HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		String workPermitId		 	 =   (String) model.get("WorkPermitId");
		ObjectMapper mapper 	 = 	  new ObjectMapper();
		HashMap<String,Object> workPermitObj = 	 mapper.readValue(new ObjectMapper().writeValueAsString(model.get("WorkpermitObj")), new TypeReference<HashMap<String,Object> >(){});
		logger.info("Entering into buildPdfDocument " +mapper.writeValueAsString(workPermitObj));
		HashMap<String,Object> variableFieldText = (HashMap<String, Object>) workPermitObj.get("variableFieldData");
		HashMap<String,Object> variableFieldData = new ObjectMapper().readValue( variableFieldText.get("value").toString(), new TypeReference<Map>() {});
		workPermitObj.putAll(variableFieldData);
		Set<String> sectionHearderArr = null;
		LinkedHashMap<String,Object> sectionWiseMap = null;
	//	List<LinkedHashMap<String,Object>> variableFieldsConfigList = null;
		ReportService reportServiceObj = new ReportServiceImpls();
		 try
		 {
			    sectionWiseMap = new LinkedHashMap<String,Object>();
			   // variableFieldsConfigList = new ArrayList<LinkedHashMap<String,Object>>();
			    
				PdfPTable table = new PdfPTable(2);
				PdfPCell cell = new PdfPCell(); 
				table.setWidthPercentage(100.0f);
				table.setWidths(new float[] {30f,70f});
				table.setSpacingBefore(15);
				
				
				HashMap<String,Object> responseMap = (HashMap<String, Object>)buildSectionWiseWorkPermitObject(workPermitObj);
				if(responseMap!=null)
				{
					sectionHearderArr =  (Set<String>) responseMap.get("sectionHearderArr");
					sectionWiseMap = (LinkedHashMap<String, Object>) responseMap.get("sectionWiseMap");
					//variableFieldsConfigList = (List<LinkedHashMap<String, Object>>) responseMap.get("variableFieldConfigurations");
				}
				Font fontText = FontFactory.getFont(FontFactory.TIMES_BOLD); // define Whole table header row
				fontText.setColor(BaseColor.WHITE);
				fontText.setSize(12);
				cell = new PdfPCell(new Phrase("PDCA Request Details - "+workPermitId,fontText));
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
				cell.setBorderColor(WebColors.getRGBColor("#d5dcdf"));
				cell.setBackgroundColor(WebColors.getRGBColor("#587687"));
				table.addCell(cell);
				//doc.add(table);
				if(sectionHearderArr!=null && sectionHearderArr.size() > 0)
				{
					for(String sectionHeader : sectionHearderArr)  //define sectionwise table header and content
					{
						String sectionHeaderName = sectionHeader;
						List<WorkPermitTypeField> fieldsUnderSectionList = (List<WorkPermitTypeField>) sectionWiseMap.get(sectionHeader);
						logger.info("sectionHeaderName "+sectionHeaderName);
						logger.info("fieldsUnderSectionList Size" + fieldsUnderSectionList.size());
						table = (PdfPTable) reportServiceObj.buildIndividualSectionHeaderRow(cell,table,sectionHeaderName);
						logger.info("table header 1"+table);
						table = (PdfPTable) reportServiceObj.buildIndividualSectionContentRows(table,fieldsUnderSectionList,workPermitObj);
					}
					doc.add(table);
					
					
				}
		 }
		 catch (Exception e) 
		 {
		    e.printStackTrace();
			logger.info("Exception ocuured buildPdfDocument : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		 }
	}

	public static StringBuffer writeListvaluesintoNewLine (List<String> listValues)
	{
		StringBuffer resultString = new StringBuffer();
		try
		{
			if(listValues.size() > 0){
				for(String str:listValues){
			 		resultString.append(str+"\n");
				 }
			}
			else
			{
				
			}
		 	logger.info("returned list here "+resultString);
		}
		catch(Exception e)
	     {
	    	 logger.info(" Exception in  writeListvaluesintoNewLine ()----"+e.getMessage());
			 logger.log(Level.SEVERE,e.getStackTrace()+" Exception occurred while writeListvaluesintoNewLine: "+e);
	     }
		return resultString;
	}
	
	public Object buildSectionWiseWorkPermitObject(HashMap<String, Object> workPermitObj) 
	{
		logger.info("Inside buildSectionWiseWorkPermitObject ");
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		LinkedHashMap<String,Object> sectionWiseMap = new LinkedHashMap<String,Object>();
		/*List<LinkedHashMap<String,Object>> variableFieldsConfigList = null;
		List<LinkedHashMap<String,Object>> fieldConfigList = null;*/
		try
		{
			List<WorkPermitTypeField> resultList = new ArrayList<WorkPermitTypeField>();
			WorkPermitTypeFieldDAO objWorkPermitTypeDAO = new WorkPermitTypeFieldDAOImpls();
			WorkPermitTypeDAO objDAO = new WorkPermitTypeDAOImpls();
			WorkPermitType objworkpermitType = objDAO.fetchbyNameFromFirestore(workPermitObj.get("formType").toString());
                if(objworkpermitType != null) {
                	System.out.println("null objworkpermitType "+objworkpermitType.getUniqueId());
                	resultList = objWorkPermitTypeDAO.fetchByWorkPermitRefId1(objworkpermitType.getUniqueId());
                	for(WorkPermitTypeField objField : resultList) {
                		if(sectionWiseMap.get(objField.getHeaderName())==null)
                			{
                			List<WorkPermitTypeField> obj = new ArrayList<WorkPermitTypeField>();
                			obj.add(objField);
                			sectionWiseMap.put(objField.getHeaderName(), obj);
                			}
                		else {
                			List<WorkPermitTypeField> obj = (List<WorkPermitTypeField>) sectionWiseMap.get(objField.getHeaderName());
                			obj.add(objField);
                			sectionWiseMap.put(objField.getHeaderName(), obj);
                			}
                	}
                }
		/*	responseMap = new HashMap<String,Object>();
			variableFieldsConfigList = new ArrayList<LinkedHashMap<String,Object>>();
			if(workPermitObj!=null)
			{
				variableFieldsConfigList = (List<LinkedHashMap<String, Object>>) workPermitObj.get("variableFieldConfigurations");
				fieldConfigList = (List<LinkedHashMap<String, Object>>) workPermitObj.get("fieldConfigurations");
				if(fieldConfigList!=null && fieldConfigList.size() > 0)
				{
					LinkedHashMap<String,Object> sectionWiseMap = new LinkedHashMap<String,Object>();
					List<String> sectionHearderArr = new ArrayList<String>();
					for( LinkedHashMap<String, Object> fieldConfigData: fieldConfigList)
					{
						List<Object> fieldConfigArr = new ArrayList<Object>();
						if(sectionWiseMap.get(fieldConfigData.get("HeaderName"))!=null && !sectionWiseMap.get(fieldConfigData.get("HeaderName")).equals(""))
						{
							fieldConfigArr = (List<Object>) sectionWiseMap.get(fieldConfigData.get("HeaderName"));
						}
						fieldConfigArr.add(fieldConfigData);
						sectionWiseMap.put(String.valueOf(fieldConfigData.get("HeaderName")), fieldConfigArr);
						if(!sectionHearderArr.contains(fieldConfigData.get("HeaderName")))
						{
							sectionHearderArr.add(String.valueOf(fieldConfigData.get("HeaderName")));
						}
					}*/
				//	sectionWiseMap.remove("Variable Part");
				//	sectionWiseMap.put("Variable Part",variableFieldsConfigList);
					//log.info("sectionWiseMap "+new ObjectMapper().writeValueAsString(sectionWiseMap));
				//	logger.info("variableFieldConfigurations "+variableFieldsConfigList);
					//log.info("data "+workPermitObj);
					System.out.println("From here"+sectionWiseMap.toString());
					responseMap.put("sectionHearderArr",sectionWiseMap.keySet());
					responseMap.put("sectionWiseMap", sectionWiseMap);
				//	responseMap.put("variableFieldConfigurations", variableFieldsConfigList);
					responseMap.put("data", workPermitObj);
 				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exceptio occured in buildSectionWiseWorkPermitObject  ---> : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getStackTrace()+" Exception occurred : "+e);
		}
		return responseMap;
	}
	
	/*public Object buildSectionWiseWorkPermitObject(HashMap<String, Object> workPermitObj) 
	{
		logger.info("Inside buildSectionWiseWorkPermitObject ");
		HashMap<String,Object> responseMap = null;
		List<LinkedHashMap<String,Object>> variableFieldsConfigList = null;
		List<LinkedHashMap<String,Object>> fieldConfigList = null;
		try
		{
			responseMap = new HashMap<String,Object>();
			variableFieldsConfigList = new ArrayList<LinkedHashMap<String,Object>>();
			if(workPermitObj!=null)
			{
				variableFieldsConfigList = (List<LinkedHashMap<String, Object>>) workPermitObj.get("variableFieldConfigurations");
				fieldConfigList = (List<LinkedHashMap<String, Object>>) workPermitObj.get("fieldConfigurations");
				if(fieldConfigList!=null && fieldConfigList.size() > 0)
				{
					LinkedHashMap<String,Object> sectionWiseMap = new LinkedHashMap<String,Object>();
					List<String> sectionHearderArr = new ArrayList<String>();
					for( LinkedHashMap<String, Object> fieldConfigData: fieldConfigList)
					{
						List<Object> fieldConfigArr = new ArrayList<Object>();
						if(sectionWiseMap.get(fieldConfigData.get("HeaderName"))!=null && !sectionWiseMap.get(fieldConfigData.get("HeaderName")).equals(""))
						{
							fieldConfigArr = (List<Object>) sectionWiseMap.get(fieldConfigData.get("HeaderName"));
						}
						fieldConfigArr.add(fieldConfigData);
						sectionWiseMap.put(String.valueOf(fieldConfigData.get("HeaderName")), fieldConfigArr);
						if(!sectionHearderArr.contains(fieldConfigData.get("HeaderName")))
						{
							sectionHearderArr.add(String.valueOf(fieldConfigData.get("HeaderName")));
						}
					}
					sectionWiseMap.remove("Variable Part");
					sectionWiseMap.put("Variable Part",variableFieldsConfigList);
					logger.info("sectionHearderArr "+sectionHearderArr);
					//log.info("sectionWiseMap "+new ObjectMapper().writeValueAsString(sectionWiseMap));
					logger.info("variableFieldConfigurations "+variableFieldsConfigList);
					//log.info("data "+workPermitObj);
					
					responseMap.put("sectionHearderArr",sectionHearderArr);
					responseMap.put("sectionWiseMap", sectionWiseMap);
					responseMap.put("variableFieldConfigurations", variableFieldsConfigList);
					responseMap.put("data", workPermitObj);
 				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exceptio occured in buildSectionWiseWorkPermitObject  ---> : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getStackTrace()+" Exception occurred : "+e);
		}
		return responseMap;
	}*/
}