package com.serviceImpl;

import com.entity.WorkPermitTypeField;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.helper.AppHelper;
import com.helper.PDFBuilder;
import com.helper.TemplateBuilder;
import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.master.HomeController;
import com.service.ReportService;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ReportServiceImpls implements ReportService{
	private static final 	Logger logger 	= 		Logger.getLogger(ReportServiceImpls.class.getName());
	@Override
	public Object getQueryStringForReports(HashMap<String, String> inputMap) 
	{
		List<String> paramsList = null;
		try
		{
			paramsList = new ArrayList<>();
			for (Map.Entry<String, String> entry : inputMap.entrySet()) 
			{
				if(inputMap.get(entry.getKey())!=null)
				{
					
					if(!entry.getKey().equals("F004_Date"))
					{
						List<String> valueList = new ObjectMapper().readValue(inputMap.get(entry.getKey()), new TypeReference<List<String>>(){});
						String query	=	StringUtils.collectionToDelimitedString(valueList," OR ",""+entry.getKey()+"=\"","\"");
						if(query!=null&& !query.equalsIgnoreCase(""))
						{
							paramsList.add("("+query+")");
						}
					}
					else
					{
						String dateRange = entry.getValue();
						if(dateRange!=null && !dateRange.equals("")&&!dateRange.equals("0"))
						{
							String fromDateStr = dateRange.split("-")[0].trim();
							Long fromDate = Long.parseLong(fromDateStr);
							/*String fromDateDay = fromDateStr.split("/")[0].trim();
							String fromDateMonth = fromDateStr.split("/")[1].trim();
							String fromDateYr = fromDateStr.split("/")[2].trim();*/
							
							String toDateStr = dateRange.split("-")[1].trim();
							Long toDate = Long.parseLong(toDateStr);
							/*String toDateDay = toDateStr.split("/")[0].trim();
							String toDateMonth = toDateStr.split("/")[1].trim();
							String toDateYr = toDateStr.split("/")[2].trim();*/
							
							/*String fromDate_serchindex_format = fromDateYr+"-"+fromDateMonth+"-"+fromDateDay;
							String toDate_serchindex_format = toDateYr+"-"+toDateMonth+"-"+toDateDay;*/
							String fromDate_serchindex_format = convertMillisecondToDateString(fromDate, "UTC","yyyy-MM-dd");
							String toDate_serchindex_format = convertMillisecondToDateString(toDate, "UTC","yyyy-MM-dd");
							System.out.println("dateRange " + dateRange +" fromDate_serchindex_format "+ fromDate_serchindex_format +" toDate_serchindex_format "+ toDate_serchindex_format);
							paramsList.add("("+ entry.getKey()+">="+fromDate_serchindex_format.trim() + " AND "+ entry.getKey()+"<="+toDate_serchindex_format.trim() +")");
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured getQueryStringForReports : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return paramsList;
	}
	public String convertMillisecondToDateString(Long milliseconds,String timeZone,String format) {
		String date = "";
		try {
		if(milliseconds!=null && milliseconds!=0L) {
			if(timeZone==null || (timeZone!=null && timeZone.equals(""))) {
				timeZone = "Europe/Paris";
			}
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			date	=	formatter.format(new Date(milliseconds));
		}	
		}
		catch(Exception e) {
			logger.log(Level.SEVERE,"Error : "+e);
			e.printStackTrace();
		}
		return date;
	}
	@Override
	public Object buildIndividualSectionHeaderRow(PdfPCell cell,PdfPTable table,String sectionHeaderName) 
	{
		logger.info("Coming inside buildIndividualSectionHeaderRow");
		try
		{
			cell = new PdfPCell();
			Font fontText = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			fontText.setColor(BaseColor.BLACK);
			fontText.setSize(10);
			cell = new PdfPCell(new Phrase(sectionHeaderName.toUpperCase(), fontText));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
			cell.setBorderColor(WebColors.getRGBColor("#d5dcdf"));
			cell.setBackgroundColor(WebColors.getRGBColor("#e6eaed"));
			table.addCell(cell); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured buildIndividualSectionHeaderRow : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return table;
	}
	@Override
	public Object buildIndividualSectionContentRows(PdfPTable table,List<WorkPermitTypeField> fieldsUnderSectionList,HashMap<String,Object> workPermitObj) 
	{
		logger.info("Coming inside buildIndividualSectionContentRows -->");
		TemplateBuilder buildTemplate = new TemplateBuilder();
		try
		{
			//List<WorkPermitBean> workPermitListObj 	 = 	  new ArrayList<WorkPermitBean>();
			table = (PdfPTable) buildTemplate.buildContent(table,fieldsUnderSectionList,workPermitObj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured buildIndividualSectionContentRows : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return table;
	}
	@Override
	public Object constructCellFieldValueBasedOnFieldType(PdfPTable table,String fieldType,String fieldName ,WorkPermitTypeField fieldObj,String fieldName_config) 
	{
		try
		{
			Font fontText = null;
			fontText = FontFactory.getFont(FontFactory.TIMES_BOLD);
			fontText.setColor(BaseColor.BLACK);
			fontText.setSize(10);
			PdfPCell cell = new PdfPCell();
			// this define key column cell in table
			cell = new PdfPCell(new Phrase(fieldName_config, fontText));
			cell.setColspan(1);
			cell.setBorderColor(WebColors.getRGBColor("#d5dcdf"));
			cell.setBackgroundColor(BaseColor.WHITE);
			table.addCell(cell);
			
			Font fontText1 = null;
			fontText1 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			fontText1.setColor(BaseColor.BLACK);
			fontText1.setSize(10);
			PdfPCell cell1 = new PdfPCell();
			cell1.setColspan(1);
			//cell1.setBorderColor(WebColors.getRGBColor("#d5dcdf"));
			cell1.setBackgroundColor(BaseColor.WHITE);
			
			
			if(fieldType != null && fieldObj.getFieldName() != null)
			{
				Object fieldVal = fieldObj.getFieldName();
				System.out.println("coming inside IFF  "+fieldType + " fieldVal "+fieldVal);
				if(fieldVal != null && !fieldVal.equals("") && ! String.valueOf(fieldVal).equals("{}"))
				{
					if(fieldType.equals("TEXT") || fieldType.equals("AUTOSUGGEST") || fieldType.equals("NUMBER") 
							|| fieldType.equals("RADIO") || fieldType.equals("BIG TEXT") || fieldType.equals("TIME") || fieldType.equals("LISTBOX"))
					{
						// this define value column cell in table
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(String.valueOf(fieldVal)),fontText1));
					}
					else if(fieldType.equals("DATE"))
					{
						// this define value column cell in table
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(TemplateBuilder.convertDateToTimeZone(Long.parseLong(String.valueOf(fieldVal)),Calendar.getInstance().getTimeZone().getDisplayName())), fontText1));
					}
					else if(fieldType.equals("CHECKBOX"))
					{
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(removeSquarebracketsFromListValues(String.valueOf(fieldVal))), fontText1));
					}
					else if(fieldType.equals("FILE"))
					{
							HashMap<String,Object> attachmentMap = (HashMap<String, Object>) fieldVal;
							if(attachmentMap!=null)
							{
								List<String> fileNameList = new ArrayList<>();
								List<HashMap<String,Object>> fileObjList = (List<HashMap<String, Object>>) attachmentMap.get("file");
								List<HashMap<String,Object>> LinkObjList = (List<HashMap<String, Object>>) attachmentMap.get("link");
								if(fileObjList!=null && fileObjList.size() >0 )
								{
									for(HashMap<String,Object> fileObj : fileObjList) {
										fileNameList.add(String.valueOf(fileObj.get("fileName")));
									}
								}
								if(LinkObjList!=null && LinkObjList.size() >0 )
								{
									for(HashMap<String,Object> linkObj : LinkObjList) {
										fileNameList.add(String.valueOf(linkObj.get("fileName")));
									}
								}
								logger.info("fileNameList == >FILE "+ fileNameList);
								cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(String.valueOf(PDFBuilder.writeListvaluesintoNewLine(fileNameList))), fontText1));
							}
					}
					else if(fieldType.equals("CAPTURE"))
					{
						
					}
					else if(fieldType.equals("SIGNATURE"))
					{
						logger.info("fieldType is matched with SIGNATURE ");
						String uploadUrl = String.valueOf(fieldVal);
						String splitByFireBase = uploadUrl.split("/firebasestorage.googleapis.com/v0/b/")[1];
						String folderAndFileNameDecodedStr = URLDecoder.decode(splitByFireBase.split("/")[2], "UTF-8");
						String gcsFolderName = folderAndFileNameDecodedStr.split("/")[0];
						String gcsFileName = folderAndFileNameDecodedStr.split("/")[1].split("\\?")[0]; //1556256850223_jaya-priya.mohan-apps-test.valeo.com_Signature.jpg?alt=media&token=769305eb-c699-4bdd-8c48-edf435feb983
						
						logger.info("gcsFileName "+gcsFileName);
						logger.info("gcsFolderName "+gcsFolderName);
						
						GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),gcsFolderName+"/"+gcsFileName);
						GcsService service = GcsServiceFactory.createGcsService();
						ReadableByteChannel rbc = service.openReadChannel(gcs_filename, 0);
						InputStream stream = Channels.newInputStream(rbc);
						Image img = Image.getInstance(IOUtils.toByteArray(stream));
						cell1 = new PdfPCell(img, true);
					}
					else
					{
						logger.info("fieldType is not matched with any condition "+fieldType);
					}
					table.addCell(cell1);
				}
				else
				{
					cell1 = new PdfPCell(new Phrase("",fontText1));
					table.addCell(cell1); 
				}
			}
			else
			{
				cell1 = new PdfPCell(new Phrase("",fontText1));
				table.addCell(cell1); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured getFieldValueBasedOnFieldType : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return table;
	}
	
	public String removeSquarebracketsFromListValues(String val)
	{
		 String resultString = val.replaceAll("[\\[\\]\\(\\)]", "");
		 return resultString;
	 }
	

}
