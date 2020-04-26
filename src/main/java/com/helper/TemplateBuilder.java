package com.helper;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.mortbay.log.Log;
import org.springframework.util.StringUtils;

import com.entity.WorkPermitTypeField;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.master.HomeController;




public class TemplateBuilder {
	private static final 	Logger logger 	= 		Logger.getLogger(TemplateBuilder.class.getName());
	public Document buildPdfDocument(Document doc,/* XCN014JDO redcard,*/String xcnId) throws MalformedURLException, IOException, DocumentException
	{
		Image img = Image.getInstance("images/pdcalogo.png.png");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(BaseColor.BLACK);
		font.setSize(12);
		PdfPCell cell = new PdfPCell(); 
		cell.setBorderColor(WebColors.getRGBColor("#d5dcdf"));
		cell.setBackgroundColor(WebColors.getRGBColor("#587687"));
		
		doc.add(new Paragraph("\n"));
		doc.add(new Paragraph("XCN014 DATABASE - "+xcnId+"                   "+"Print Date : "+new Date(),font));
		font.setSize(14);
		return doc;
	}
	public PdfPTable buildContent(PdfPTable table,List<WorkPermitTypeField> fieldsUnderSectionList,HashMap<String,Object> workPermitObj) throws MalformedURLException, IOException, DocumentException
	{
		try
		{
			System.out.println(new ObjectMapper().writeValueAsString(workPermitObj));
			for( WorkPermitTypeField fieldMap: fieldsUnderSectionList)
			{
			//	System.out.println("fieldMap "+ new ObjectMapper().writeValueAsString(fieldMap));
				String fieldType = (String) fieldMap.getFieldType();
				String fieldName_config = (String) fieldMap.getFieldName();
				String fieldId_config = (String) fieldMap.getiD();
				String fieldName = fieldId_config+"_"+fieldName_config.replaceAll("[^a-zA-Z0-9]+","");
			//	System.out.println("fieldMap "+ new ObjectMapper().writeValueAsString(fieldMap));
			//	System.out.println("fieldType "+fieldType);
				
				if(!fieldType.equalsIgnoreCase("VARIABLE"))
				{
					table =  (PdfPTable)constructCellFieldValueBasedOnFieldType(table,fieldType,fieldName,workPermitObj,fieldName_config,fieldMap.getType());
				}
			}
			//System.out.println("table LAST "+table);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured buildContent : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return table;
	}
	
	public Object constructCellFieldValueBasedOnFieldType(PdfPTable table,String fieldType,String fieldName ,HashMap<String,Object> fieldObj,String fieldName_config,String type) 
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
			fieldName=(fieldName.split("_")[1]).substring(0, 1).toLowerCase() + (fieldName.split("_")[1]).substring(1);
			//System.out.println(new ObjectMapper().writeValueAsString(fieldObj));
			System.out.println("here "+fieldType+"    "+fieldName+"    "+fieldObj.get(fieldName));
			if(fieldType != null && fieldObj.get(fieldName) != null)
			{
				Object fieldVal = fieldObj.get(fieldName);
				System.out.println("coming inside IFF  "+fieldType + " fieldVal "+fieldVal);
				if(fieldVal != null && !fieldVal.equals("") && ! String.valueOf(fieldVal).equals("{}"))
				{
					if(fieldType.equals("TEXT") || fieldType.equals("AUTOSUGGEST") || fieldType.equals("NUMBER") 
							|| fieldType.equals("RADIO") || fieldType.equals("BIG TEXT") || fieldType.equals("TIME") || fieldType.equals("LISTBOX") ||fieldType.equals("LISTBOX1"))
					{
						if(fieldType.equals("BIG TEXT")&& !type.equals("VARIABLE")) {
							System.out.println("********************************");
							HashMap<String,Object> x = (HashMap<String, Object>)fieldObj.get(fieldName);
							fieldVal=x.get("value");
							System.out.println(fieldVal);
						}
						if(fieldType.equals("LISTBOX1")&& !type.equals("VARIABLE")) {
							System.out.println("********************************");
							List<String> x = (List<String>)fieldObj.get(fieldName);
							 fieldVal=StringUtils.collectionToDelimitedString(x, " , ");
							System.out.println(fieldVal);
						}
						// this define value column cell in table
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(String.valueOf(fieldVal)),fontText1));
					}
					else if(fieldType.equals("DATE"))
					{
						// this define value column cell in table
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(TemplateBuilder.convertDateToTimeZone(Long.parseLong(String.valueOf(fieldVal)),String.valueOf(fieldObj.get("requesterTimezone")))), fontText1));
					}
					else if(fieldType.equals("CHECKBOX"))
					{
						cell1 = new PdfPCell(new Phrase(HomeController.validateEmptyValues(removeSquarebracketsFromListValues(String.valueOf(fieldVal))), fontText1));
					}
					else if(fieldType.equals("FILE"))
					{
							List<HashMap<String,Object>> attachmentMap = (List<HashMap<String, Object>>) fieldVal;
							
							for(HashMap<String,Object> filedetails:attachmentMap) {
								String fileName=String.valueOf(filedetails.get("fileName"));
								String attachmentID = String.valueOf(filedetails.get("attachmentId"));
								GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
								GcsService service = GcsServiceFactory.createGcsService();
								ReadableByteChannel rbc = service.openReadChannel(gcs_filename, 0);
								InputStream stream = Channels.newInputStream(rbc);
								Image img = Image.getInstance(IOUtils.toByteArray(stream));
								cell1.addElement(img);
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
	public static String convertDateToTimeZone(Long date,String timeZone)
	{
		String dateStr	=	"";
		try
		{
			if(date!=null && date!=0)
			{
				Calendar	cal	=	Calendar.getInstance();
				cal.setTime(new Date(date));
				SimpleDateFormat format	=	new SimpleDateFormat("dd/MM/YYYY");
				//System.out.println("XCNobj.getTimeZoneName() : "+timeZone);
				format.setTimeZone(TimeZone.getTimeZone(timeZone));
				dateStr	=	format.format(cal.getTime());
				System.out.println("dateStr builder --- >"+dateStr);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.getStackTrace();
			return dateStr;
		}
		return dateStr;
	}
	
	
	public static String convertmillsectoDate(Long date)
	{
		String dateStr	=	"";
		try
		{
			if(date!=null && date!=0)
			{
				Calendar	cal	=	Calendar.getInstance();
				cal.setTime(new Date(date));
				SimpleDateFormat format	=	new SimpleDateFormat("dd/MM/YYYY");
				dateStr	=	format.format(cal.getTime());
				System.out.println("dateStr builder --- >"+dateStr);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.getStackTrace();
			return dateStr;
		}
		return dateStr;
	}
}