package com.master;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.DAO.AreaDAO;
import com.DAO.AttachmentsDAO;
import com.DAO.BenefitDAO;
import com.DAO.ConfigurationDAO;
import com.DAO.LineDAO;
import com.DAO.LossTypeDAO;
import com.DAO.PillarDAO;
import com.DAO.RequestDAO;
import com.DAO.ToolDAO;
import com.DAO.UserDAO;
import com.DAOImpl.AreaDAOImpls;
import com.DAOImpl.AttachmentsDAOImpls;
import com.DAOImpl.BenefitDAOImpls;
import com.DAOImpl.ConfigurationDAOImpls;
import com.DAOImpl.LineDAOImpls;
import com.DAOImpl.LossTypeDAOImpls;
import com.DAOImpl.PillarDAOImpls;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.ToolDAOImpls;
import com.DAOImpl.UserDAOImpls;
import com.entity.Area;
import com.entity.Attachments;
import com.entity.Benefit;
import com.entity.Configuration;
import com.entity.Line;
import com.entity.LossType;
import com.entity.Pillar;
import com.entity.Tool;
import com.entity.User;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.Storage.Objects.Copy;
import com.google.api.services.storage.Storage.Objects.Delete;
import com.google.api.services.storage.model.StorageObject;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.helper.AppHelper;
import com.helper.Helper;
import com.helper.UserUtil;
import com.service.AttachmentsService;
import com.serviceImpl.AttachmentsServiceImpls;


@RestController
@RequestMapping(value="/attachments")
public class AttachmentsController
{
	private static final 	Logger log		= 	Logger.getLogger(AttachmentsController.class.getName());
	public final BlobstoreService blobstore=BlobstoreServiceFactory.getBlobstoreService();
	 public final DatastoreService datastore=DatastoreServiceFactory.getDatastoreService();
	 
	 
	 @RequestMapping (value="/getuploadurlforattachment",method=RequestMethod.POST)
		public @ResponseBody String getuploadurlforattachment(HttpServletRequest req,HttpServletResponse resp) throws Exception
		{
			String uploadUrl = null;
			AttachmentsService service = new AttachmentsServiceImpls();
			try
			{
				log.info("Inside getuploadurlforattachment");
				uploadUrl = service.dispatchUploadForm(req, resp, null);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return uploadUrl;
		}
	 @RequestMapping (value="/getuploadurlforattachment1",method=RequestMethod.POST)
		public @ResponseBody String getuploadurlforattachment1(HttpServletRequest req,HttpServletResponse resp) throws Exception
		{
			String uploadUrl = null;
			AttachmentsService service = new AttachmentsServiceImpls();
			try
			{
				log.info("Inside getuploadurlforattachment");
				uploadUrl = service.dispatchUploadForm1(req, resp, null);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return uploadUrl;
		}///attachments/configupload
	 
	 @RequestMapping(value = "/configupload", method = RequestMethod.POST)
		public @ResponseBody String configupload(HttpServletRequest req, HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException 
	    {
		 
		 
				 HashMap<String,Object> responseMap = new HashMap<String,Object>();
				 String fileName = null;
				 String typeoffile = "";
				 String refId = null;
			   	 HttpHeaders responseHeaders=new HttpHeaders();
		 	     HashMap<String,Object> attachmentObj = new HashMap<String,Object>();
		   		try
		   		{
		   			responseHeaders.add("Content-Type", "text/plain; charset=utf-8");
		   			Map<String,List<FileInfo>> uploads = blobstore.getFileInfos(req);
		   			log.info("file info : "+String.valueOf(uploads));
			        List<FileInfo> fileInfos = uploads.get("files");
			        refId = req.getParameter("RefId");
			        typeoffile = req.getParameter("typeofFile");
			        fileName = req.getParameter("fileName");
			        int i = 0;
			        
			        for (FileInfo fileInfo : fileInfos) 
			        {
			        	if(!fileInfo.getFilename().isEmpty() && fileInfo.getFilename() != "" && fileInfo.getFilename() != null)
			        	{
			        		long attachmentID = System.currentTimeMillis();
			        		log.info("file info : "+fileInfo.getFilename());
			        		log.info("fileName : "+fileName);
			        		log.info("typeoffile "+typeoffile +" req.getParameter(\"typeofFile\") "+req.getParameter("typeofFile"));
			        		String stmedialink="";
				        	//Comment for local
									        			String encodedurl[]=fileInfo.getGsObjectName().split("/");
											            Storage client= Oauth2APIHelper.getService();
											            StorageObject storageobject= new StorageObject();
									        			Copy objstorage=client.objects().copy(AppHelper.getDefaultBucketName(), encodedurl[encodedurl.length-1],AppHelper.getDefaultBucketName(),"RequestAttachments/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"),storageobject);
					    					            objstorage.setDestinationPredefinedAcl("publicread");
					    					            StorageObject st1 = objstorage.execute();
					    					            System.out.println("st1 "+st1);
					    					            Delete objDelete=client.objects().delete(AppHelper.getDefaultBucketName(), encodedurl[encodedurl.length-1]);
					    					            objDelete.execute();
					    					           stmedialink = st1.getMediaLink();
					    					            
					    					           
					    				//Comment For local
					    					            String url = "";
					    					            switch(typeoffile) {
					    					            case "BULKUPLOADCONFIG":
					    					            	url="/attachments/masterconfigbulkupload";
					    					            	break;
					    					            case "PILLARBULKUPLOADCONFIG":
					    					            	url="/attachments/pillarmasterconfigbulkupload";
					    					            	break;
					    					            case "LINEBULKUPLOADCONFIG":
					    					            	url="/attachments/linemasterconfigbulkupload";
					    					            	break;
					    					            case "AREABULKUPLOADCONFIG":
					    					            	url="/attachments/areamasterconfigbulkupload";
					    					            	break;
					    					            case "TOOLBULKUPLOADCONFIG":
					    					            	url="/attachments/toolmasterconfigbulkupload";
					    					            	break;
					    					            case "LOSSTYPEBULKUPLOADCONFIG":
					    					            	url="/attachments/losstypemasterconfigbulkupload";
					    					            	break;
					    					            case "BENEFITBULKUPLOADCONFIG":
					    					            	url="/attachments/benefitmasterconfigbulkupload";
					    					            	break;
					    					            case "USERBULKUPLOADCONFIG":
					    					            	url="/attachments/usermasterbulkupload";
				    					            		break;
				    					            	
					    					            }
					    					           
					    					            Queue queue = QueueFactory.getQueue("readSheetQueue");
					    								TaskOptions options = TaskOptions.Builder.withUrl(url);
					    								options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link",stmedialink);
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
					    								queue.add(options);
					    					            System.out.println("attachmentObj "+ new ObjectMapper().writeValueAsString(attachmentObj));
											            responseMap.put("Status", "Success"); 
											   			responseMap.put("Message", "Processing Bulk upload");
				        	 
			        	}
			        }
		   		} 
		   		catch(Exception e)
		   		{
		   			responseMap.put("Status", "Failure"); 
		   			responseMap.put("Message", "Something went wrong while file Upload,Please Contact Administrator"); 
		   			e.printStackTrace();
		   			log.info("Exception occured during createfileattachment : "+e);
					log.log(Level.SEVERE,e.getMessage());
		   		}
		        return new ObjectMapper().writeValueAsString(responseMap);
	    }//pillarmasterconfigbulkupload
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/losstypemasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueuelosstypemasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				LossTypeDAO objLossTypeDAO = new LossTypeDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<LossType> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				LossType objLossType = new LossType();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 2) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objLossType.setValue(cellValue);
						}else if (cell.getColumnIndex() == 1) {
							objLossType.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objLossType.setCreatedBy(usermail);
					objLossType.setCreatedOn(new Date().getTime());
					configList.add(objLossType);
					
					LossType fromDB = objLossTypeDAO.getLossTypeByName(objLossType.getValue());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objLossTypeDAO.saveLossType(objLossType);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/benefitmasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueuebenefitmasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				BenefitDAO objBenefitDAO = new BenefitDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Benefit> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Benefit objBenefit = new Benefit();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 2) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objBenefit.setValue(cellValue);
						}else if (cell.getColumnIndex() == 1) {
							objBenefit.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objBenefit.setCreatedBy(usermail);
					objBenefit.setCreatedOn(new Date().getTime());
					configList.add(objBenefit);
					
					Benefit fromDB = objBenefitDAO.getBenefitByName(objBenefit.getValue());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objBenefitDAO.saveBenefit(objBenefit);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/toolmasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueuetoolmasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				ToolDAO objToolDAO = new ToolDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Tool> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Tool objTool = new Tool();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 2) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objTool.setValue(cellValue);
						}else if (cell.getColumnIndex() == 1) {
							objTool.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objTool.setCreatedBy(usermail);
					objTool.setCreatedOn(new Date().getTime());
					configList.add(objTool);
					
					Tool fromDB = objToolDAO.getToolByName(objTool.getValue());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objToolDAO.saveTool(objTool);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/areamasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueueareamasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				AreaDAO objAreaDAO = new AreaDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Area> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Area objArea = new Area();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 3) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objArea.setValue(cellValue);
						} else if (cell.getColumnIndex() == 1) {
							objArea.setPlant(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							objArea.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objArea.setCreatedBy(usermail);
					objArea.setCreatedOn(new Date().getTime());
					configList.add(objArea);
					
					Area fromDB = objAreaDAO.getAreaByName(objArea.getValue());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objAreaDAO.saveArea(objArea);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/linemasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueuelinemasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				LineDAO objLineDAO = new LineDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Line> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Line objLine = new Line();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 3) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objLine.setValue(cellValue);
						} else if (cell.getColumnIndex() == 1) {
							objLine.setPlant(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							objLine.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objLine.setCreatedBy(usermail);
					objLine.setCreatedOn(new Date().getTime());
					configList.add(objLine);
					
					Line fromDB = objLineDAO.getLineByName(objLine.getValue());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objLineDAO.saveLine(objLine);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @SuppressWarnings("unchecked")
		@RequestMapping(value = "/pillarmasterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueuepillarmasterconfigbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				PillarDAO objPillarDAO = new PillarDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Pillar> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Pillar objPillar = new Pillar();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 3) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objPillar.setName(cellValue);
						} else if (cell.getColumnIndex() == 1) {
							objPillar.setPillarType(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							objPillar.setActive(Boolean.parseBoolean(cellValue));
						} 

					}
					objPillar.setCreatedBy(usermail);
					objPillar.setCreatedOn(new Date().getTime());
					configList.add(objPillar);
					
					Pillar fromDB = objPillarDAO.getPillarbyName(objPillar.getName());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objPillarDAO.savePillar(objPillar);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/masterconfigbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueue(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				ConfigurationDAO configurationDAO = new ConfigurationDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
				/*InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-Master1.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<Configuration> configList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			while (rowIterator.hasNext()) {
				Configuration objConfiguration = new Configuration();
				Row row = rowIterator.next();
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 3) {
					Iterator<Cell> cellIterator = row.cellIterator();
					System.out.println(row.getPhysicalNumberOfCells());
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objConfiguration.setValue(cellValue);
						} else if (cell.getColumnIndex() == 1) {
							objConfiguration.setPlant(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							objConfiguration.setType(cellValue);
						} 

						System.out.print(cellValue + "\t");
					}
					objConfiguration.setCreatedBy(usermail);
					objConfiguration.setActive(true);
					// objConfiguration.setCreatedOn(new Date().getTime());
					configList.add(objConfiguration);
					
					Configuration fromDB = configurationDAO.getConfigurationByNameandPlant(objConfiguration.getValue(),objConfiguration.getPlant(), objConfiguration.getType());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								configurationDAO.saveConfiguration(objConfiguration);
								successList.add(row.getRowNum());
							}
					System.out.println();
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			 System.out.println("configList. size -->"+configList.size());
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/usermasterbulkupload", method = RequestMethod.POST)
		public @ResponseBody void readContentFromSheetQueueusermasterbulkupload(HttpServletRequest req) {
			HashMap<String, Object> responseMap = null;
			Helper service = new Helper();
			/*
			 options.param("usermail", UserUtil.getCurrentUser());
					    								options.param("link", st1.getMediaLink());
					    								options.param("attachmentID", String.valueOf(attachmentID));
					    								options.param("fileName", fileName);
			 */
			try {
				log.info(" link "+ req.getParameter("link") +  " --->  USER NAME" + req.getParameter("usermail") +  " --->  attachmentID" + req.getParameter("attachmentID") +  " --->  fileName" + req.getParameter("fileName"));
				String attachmentID =  req.getParameter("attachmentID") ;
				String fileName =  req.getParameter("fileName") ;
				String usermail= req.getParameter("usermail");
				UserDAO objUserDAO = new UserDAOImpls();
				
				//Commented for Local
				GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"));
				GcsService service1 = GcsServiceFactory.createGcsService();
				ReadableByteChannel rbc = service1.openReadChannel(gcs_filename, 0);
				InputStream stream = Channels.newInputStream(rbc);
				Workbook wb = WorkbookFactory.create(stream);
				
				//comment for Deployment
			/*	InputStream serviceAccount = UserUtil.class.getResourceAsStream("/Template-UserBulkUpload.xlsx");
				Workbook wb = WorkbookFactory.create(serviceAccount);*/
				
				Sheet sheet = wb.getSheetAt(0);
				 DataFormatter dataFormatter = new DataFormatter();
	            // Get the first cell.
				 System.out.println("sheet name"+sheet.getSheetName());
				 List<Integer> columnErrorList = new ArrayList<Integer>();
				 List<Integer> columnValuesErrorList = new ArrayList<Integer>();
				 List<Integer> successList = new ArrayList<Integer>();
				 List<User> userList = new ArrayList<>();
				 Iterator<Row> rowIterator = sheet.rowIterator();
				 System.out.println("sheet rows "+sheet.getPhysicalNumberOfRows());
			
			while (rowIterator.hasNext()) {
				User objUser = new User();
				List<String> rolesList = new ArrayList<String>();
				Row row = rowIterator.next();
				 System.out.println("row columns "+row.getPhysicalNumberOfCells());
				if (row.getRowNum()!=0 && row.getPhysicalNumberOfCells() == 18) {
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						if (cell.getColumnIndex() == 0) {
							objUser.setTitle(cellValue);
						} else if (cell.getColumnIndex() == 1) {
							objUser.setUserEmail(cellValue);
						} else if (cell.getColumnIndex() == 2) {
							objUser.setUserName(cellValue);
						} else if (cell.getColumnIndex() == 3) {
							objUser.setFirstName(cellValue);
						} 
						else if (cell.getColumnIndex() == 4) {
							objUser.setLastName(cellValue);
						} 

						else if (cell.getColumnIndex() == 5) {
							objUser.setPlantName(cellValue);
						} 

						else if (cell.getColumnIndex() == 6) {
							objUser.setPillarName(cellValue);
						} 

						else if (cell.getColumnIndex() == 7) {
							objUser.setProjectLead(Boolean.getBoolean(cellValue));
						} 

						else if (cell.getColumnIndex() == 8) {
							objUser.setPillarLead(Boolean.getBoolean(cellValue));
						} 

						else if (cell.getColumnIndex() == 9) {
							objUser.setMentor(Boolean.getBoolean(cellValue));
						} 

						else if (cell.getColumnIndex() == 10) {
							objUser.setEditor(Boolean.getBoolean(cellValue));
						} 

						else if (cell.getColumnIndex() == 11) {
							objUser.setActive(Boolean.getBoolean(cellValue));
						} 
						
						else if (cell.getColumnIndex() == 12) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("SUPERADMIN");
						} 

						else if (cell.getColumnIndex() == 13) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("CREATOR");
						} 
						else if (cell.getColumnIndex() == 14) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("APPROVER1");
						} 
						else if (cell.getColumnIndex() == 15) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("APPROVER2");
						} 
						else if (cell.getColumnIndex() == 16) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("APPROVER3");
						} 
						else if (cell.getColumnIndex() == 17) {
							if(Boolean.getBoolean(cellValue))
								rolesList.add("PLANTMANAGER");
						} 
						
						System.out.print(cellValue + "\t");
					}
					objUser.setRoles(rolesList);
					objUser.setCreatedBy(usermail);
					objUser.setActive(true);
					objUser.setCreatedOn(new Date().getTime());
					userList.add(objUser);
					
					User fromDB = objUserDAO.getUserByEmail(objUser.getUserEmail());
							if(fromDB!=null) {
								columnValuesErrorList.add(row.getRowNum());
								continue;
							}else {
								objUserDAO.saveUser(objUser);
								successList.add(row.getRowNum());
							}
				} else {
					columnErrorList.add(row.getRowNum());
					continue;
				}
			}
			 System.out.println("USer master. size -->"+columnErrorList.size()+"     "+successList.size());
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error : " + e);
				e.printStackTrace();
				responseMap.put("data", null);
				responseMap.put("Status", "Failure");
				responseMap.put("Message", "Something went wrong masterconfigbulkupload . Please contact Administrator");
			}
		}
	 @RequestMapping(value = "/createfileattachment", method = RequestMethod.POST)
		public @ResponseBody String createfileattachment(HttpServletRequest req, HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException 
	    {
		 
		 
				 HashMap<String,Object> responseMap = new HashMap<String,Object>();
				 String fileName = null;
				 String typeoffile = "";
				 String refId = null;
			   	 HttpHeaders responseHeaders=new HttpHeaders();
			   	 AttachmentsDAO objApplicationAttachmentsDAO = new AttachmentsDAOImpls();
		         Attachments objAttachmentJDO = new Attachments();
		 	     HashMap<String,Object> attachmentObj = new HashMap<String,Object>();
		   		try
		   		{
		   			responseHeaders.add("Content-Type", "text/plain; charset=utf-8");
		   			Map<String,List<FileInfo>> uploads = blobstore.getFileInfos(req);
		   			log.info("file info : "+String.valueOf(uploads));
			        List<FileInfo> fileInfos = uploads.get("files");
			        refId = req.getParameter("RefId");
			        typeoffile = req.getParameter("typeofFile");
			        fileName = req.getParameter("fileName");
			        int i = 0;
			        
			        for (FileInfo fileInfo : fileInfos) 
			        {
			        	if(!fileInfo.getFilename().isEmpty() && fileInfo.getFilename() != "" && fileInfo.getFilename() != null)
			        	{
			        		long attachmentID = System.currentTimeMillis();
			        		log.info("file info : "+fileInfo.getFilename());
			        		log.info("fileName : "+fileName);
			        		log.info("typeoffile "+typeoffile +" req.getParameter(\"typeofFile\") "+req.getParameter("typeofFile"));
				        	
									        			String encodedurl[]=fileInfo.getGsObjectName().split("/");
											            Storage client= Oauth2APIHelper.getService();
											            StorageObject storageobject= new StorageObject();
									        			Copy objstorage=client.objects().copy(AppHelper.getDefaultBucketName(), encodedurl[encodedurl.length-1],AppHelper.getDefaultBucketName(),"RequestAttachments/"+attachmentID+"_"+URLEncoder.encode(fileName, "UTF-8"),storageobject);
					    					            objstorage.setDestinationPredefinedAcl("publicread");
					    					            StorageObject st1 = objstorage.execute();
					    					            System.out.println("st1 "+st1);
					    					            Delete objDelete=client.objects().delete(AppHelper.getDefaultBucketName(), encodedurl[encodedurl.length-1]);
					    					            objDelete.execute();
					    					            attachmentObj.put("attachmentId",attachmentID);
					    					            attachmentObj.put("fileName", fileName);
					    					            attachmentObj.put("attachmentUploadedDate",new Date().getTime());
					    					            attachmentObj.put("active",true);
					    					            attachmentObj.put("url", st1.getMediaLink());
					    					            attachmentObj.put("createdBy",UserUtil.getCurrentUser());
					    					            attachmentObj.put("type",typeoffile);
					    					            attachmentObj.put("referenceId",refId);
					    					            attachmentObj.put("attachmentType", "FILEUPLOAD");
					    					           Attachments objAttachment1= objApplicationAttachmentsDAO.saveToFirestore(attachmentObj);
					    					            System.out.println("attachmentObj "+ new ObjectMapper().writeValueAsString(attachmentObj));
											            responseMap.put("Status", "Success"); 
											   			responseMap.put("Message", "Uploaded Succesfully");
											   		    responseMap.put("data", objAttachment1);
				        	 
			        	}
			        }
		   		} 
		   		catch(Exception e)
		   		{
		   			responseMap.put("Status", "Failure"); 
		   			responseMap.put("Message", "Something went wrong while file Upload,Please Contact Administrator"); 
		   			e.printStackTrace();
		   			log.info("Exception occured during createfileattachment : "+e);
					log.log(Level.SEVERE,e.getMessage());
		   		}
		        return new ObjectMapper().writeValueAsString(responseMap);
	    }
	 
	 @RequestMapping(value = "/FileDownload", method = RequestMethod.GET)
	  public @ResponseBody String FileDownload(@RequestParam("type") String type,
			  @RequestParam("attchemnetID") String attchmentID,
			  @RequestParam("filename") String filename,
			  @RequestParam("refID") String refId,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException
	  {
		    log.info("Inside FileDownload");
		    Attachments attachmentObj = null;
		    AttachmentsDAO attachmentDao = new AttachmentsDAOImpls();
		    HashMap<String, Object> resultMap = null;
		    BufferedOutputStream outs = null;
		    InputStream stream = null;
	        try
	        {
	        	resultMap = new  HashMap<String, Object>();
	        	switch(type)
	        	{
	        		case "HelpInstructionAttachments" : 
	        			 attachmentObj =  attachmentDao.fetchById(attchmentID);
	        			break;
	        		case "WorkPermitAttachments"	:
	        			attachmentObj	=	attachmentDao.fetchById(attchmentID);
	        			break;
	        	}
	        	if(attachmentObj!=null)
	        	{
	        		log.info("FileDownload filename : "+filename);
					GcsFilename gcs_filename = new GcsFilename(AppHelper.getDefaultBucketName(),"RequestAttachments"+"/"+attchmentID+"_"+URLEncoder.encode(filename, "UTF-8"));
					GcsService service = GcsServiceFactory.createGcsService();
					ReadableByteChannel rbc = service.openReadChannel(gcs_filename, 0);
					stream = Channels.newInputStream(rbc);
					log.info("stream from storage"+stream);
					 resp.setHeader("Content-disposition", "attachments;filename="+filename);
				     MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			         String mimeType = mimeTypesMap.getContentType(filename);
			         resp.setContentType(mimeType);
			         System.out.println(mimeType);
					 outs = new BufferedOutputStream(resp.getOutputStream());
				       int len;
				       byte[] buf = new byte[1024];
				       while ((len = stream.read(buf)) > 0 ) 
				       {
				           outs.write(buf, 0, len);
				       }
				       outs.close();
				       resultMap.put("data", attachmentObj);
	 				   resultMap.put("Status", "Success");
	 				   resultMap.put("Message", "File downloaded successfully");
	        	}
	        	else
 				{
 					resultMap.put("data", null);
 					resultMap.put("Status", "Failure");
 					resultMap.put("Message", "Something went wrong No such ID exixts. Please Contact Administrator");
 				}
	        }
	        catch(Exception e)
	        {
	        	resultMap.put("Status","Failure");
				resultMap.put("Message", "Something went wrong while Fetching. Please Contact Administrator");
				resultMap.put("data",null);
	        	log.info("Exception occured during FileDownload : "+e);
				log.log(Level.SEVERE,e.getMessage());
	        }
	        finally {
	        	if(stream!=null)
	        		stream.close();
	        	if(outs!=null)
	        		outs.close();
	        }
	        return new ObjectMapper().writeValueAsString(resultMap);
		}
	 
	 
	 @RequestMapping(value = "/fetchAttachObjById/{uniqueId}", method = RequestMethod.POST)
		public @ResponseBody String fetchAttachObjById(@PathVariable("uniqueId") String uniqueId,HttpServletRequest req, HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException 
	    {
				 HashMap<String,Object> responseMap = new HashMap<String,Object>();
				 AttachmentsService service	=	new AttachmentsServiceImpls();
		   		try
		   		{
		   			responseMap = (HashMap<String, Object>) service.fetchById(uniqueId);
		   		} 
		   		catch(Exception e)
		   		{
		   			responseMap.put("Status", "Failure"); 
		   			responseMap.put("Message", "Something went wrong while file Upload,Please Contact Administrator"); 
		   			e.printStackTrace();
		   			log.info("Exception occured during createfileattachment : "+e);
					log.log(Level.SEVERE,e.getMessage());
		   		}
		        return new ObjectMapper().writeValueAsString(responseMap);
	    }
	  
}
