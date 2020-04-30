package com.master;

import com.DAO.IdDao;
import com.DAO.RequestDAO;
import com.DAO.UserDAO;
import com.DAO.WorkPermitTypeDAO;
import com.DAOImpl.RequestDAOImpl;
import com.DAOImpl.UserDAOImpls;
import com.DAOImpl.WorkPermitTypeDAOImpls;
import com.entity.IdJdo;
import com.entity.Request;
import com.entity.User;
import com.entity.WorkPermitType;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.helper.AppHelper;
import com.helper.MailHelper;
import com.helper.TemplateBuilder;
import com.helper.UserUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class RequestController {
    private static final Logger log = Logger.getLogger(RequestController.class.getName());

    /**
     * @param request
     * @param userObj
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/saverequest", method = RequestMethod.POST)
    public @ResponseBody
    String saveRequest(HttpServletRequest request, @RequestParam("requestobj") String requestobj, @RequestParam("variableconfigdata") String variableconfigdata) throws JsonGenerationException, JsonMappingException, IOException {

        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        UserDAO userDao = new UserDAOImpls();
        log.info("requestobj: " + requestobj);
        //UserJDO userJdoObj = new UserJDO();
        Long id = 0L;
        try {
            Request objRequest = new ObjectMapper().readValue(requestobj.toString(), new TypeReference<Request>() {
            });
            objRequest.setVariableFieldData(new Text(variableconfigdata));
            RequestDAO objRequestDAO = new RequestDAOImpl();
            User userJdoObj = userDao.getUserByEmail(UserUtil.getCurrentUser());
            if (userJdoObj != null) {
                if (objRequest.getId() == null || (objRequest.getId() == 0) || objRequest.getId().equals("")) {
                    log.info("New Request ");
                    // Validated for save
                    objRequest.setPlant(userJdoObj.getPlantName());
                    objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                    objRequest.setLastUpdatedOn(new Date().getTime());
                    objRequest.setCreatedBy(UserUtil.getCurrentUser());
                    objRequest.setCreatedOn(new Date().getTime());
                    objRequest.setStatus("DRAFT");
                    objRequest.setFormId(IDGenerator(objRequest.getFormType(), objRequest));
                    Request obj = objRequestDAO.saveRequest(objRequest);
                    responseMap.put("STATUS", "SUCCESS");
                    responseMap.put("MESSAGE", "Created Request succesfully");
                    responseMap.put("data", obj);
                } else {
                    log.info("Update Existing " + objRequest.getId());
                    if (objRequest.getStatus().equals("DRAFT")) {
                        Request fromDB = objRequestDAO.fetchRequestById(objRequest.getId());
                        if (fromDB == null) {
                            responseMap.put("STATUS", "FAILURE");
                            responseMap.put("MESSAGE", "No record with this id exist in database");
                        } else {
                            if (objRequest.getCreatedBy().trim().equals(userJdoObj.getUserEmail()) || userJdoObj.getRoles().contains("SUPERADMIN")) {
                                objRequest.setFormId(fromDB.getFormId());
                                objRequest.setPlant(fromDB.getPlant());
                                objRequest.setCreatedOn(fromDB.getCreatedOn());
                                objRequest.setCreatedBy(fromDB.getCreatedBy());
                                objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                                objRequest.setLastUpdatedOn(new Date().getTime());
                                Request obj = objRequestDAO.saveRequest(objRequest);
                                responseMap.put("STATUS", "SUCCESS");
                                responseMap.put("MESSAGE", "Updated Request succesfully");
                                responseMap.put("data", obj);
                            } else {
                                responseMap.put("STATUS", "FAILURE");
                                responseMap.put("MESSAGE", "Sorry, You do not have permission to update this Request");
                            }
                        }
                        // Validated for update

                    } else {
                        responseMap.put("STATUS", "FAILURE");
                        responseMap.put("MESSAGE", "Request can only be updated in DRAFT State");
                    }
                }
                //}
            } else {
                responseMap.put("STATUS", "FAILURE");
                responseMap.put("MESSAGE", "Sorry, You do not have permission to create Request");
            }
        } catch (Exception e) {

            e.printStackTrace();
            log.info("Error in RequestController while saving " + e.getMessage());
            responseMap.put("STATUS", "FAILURE");
            responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
        }
        return new ObjectMapper().writeValueAsString(responseMap);
    }
    //sendnotificationtonextapprover

	  /*Queue queue = QueueFactory.getQueue("readSheetQueue");
		TaskOptions options = TaskOptions.Builder.withUrl(url);
		options.param("usermail", UserUtil.getCurrentUser());
		options.param("link",stmedialink);
		options.param("attachmentID", String.valueOf(attachmentID));
		options.param("fileName", fileName);
		queue.add(options);*/

    public String IDGenerator(String formType, Request objRequest) {
        // long unixTime = System.currentTimeMillis() / 1000L;
        /*
         * Calendar calendar = Calendar.getInstance(); String s =
         * String.valueOf(calendar.getTimeInMillis()); String id = "RED-"+s ;
         * return id.trim() ;
         */
        IdDao obj = new IdDao();
        int nextnum = obj.getNextIdfromDatastore(formType);
        String nextnumString = String.format("%05d", nextnum);
        log.info("the next num is" + nextnumString);

        String red_Id = objRequest.getPlant() + "_" + objRequest.getPrimaryPillar() + "_" + formType + "_" + nextnumString;
        return red_Id.trim();

    }

    @RequestMapping(value="/getAllRequest")
    public ResponseEntity<?> getAllRequest(){

        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        RequestDAO objRequestDAO = new RequestDAOImpl();

        List<Request> requests = objRequestDAO.fetchRequestList();
        log.warning("Requests ARE " + requests.toString());
        responseMap.put("data", requests);
        responseMap.put("recordsFiltered", requests.size());
        responseMap.put("recordsTotal", requests.size());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/setid")
    public @ResponseBody
    String setid(HttpServletRequest request)
            throws GeneralSecurityException, IOException {

        WorkPermitTypeDAO objFormDAO = new WorkPermitTypeDAOImpls();
        List<WorkPermitType> type = objFormDAO.fetchActiveFromFirestore();
        IdDao objIdDao = new IdDao();
        for (WorkPermitType wptype : type) {
            //if(objIdDao.getIdEntryfromDatastore(wptype.getWorkPermitType()) == null) {
            IdJdo obj = new IdJdo();
            obj.setId_name(wptype.getWorkPermitType());
            obj.setNext_id(1);
            IdDao obj1 = new IdDao();
            obj1.addIdtoDataStore(obj);
            //}
        }

        return "success";

    }

    /**
     * @param request
     * @param userObj
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/submitRequest", method = RequestMethod.POST)
    public @ResponseBody
    String updateRequest(HttpServletRequest request, @RequestParam("requestobj") String requestobj, @RequestParam("variableconfigdata") String variableconfigdata) throws JsonGenerationException, JsonMappingException, IOException {

        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        UserDAO userDao = new UserDAOImpls();
        log.info("requestobj: " + requestobj);
        //UserJDO userJdoObj = new UserJDO();
        Long id = 0L;
        try {
            Request objRequest = new ObjectMapper().readValue(requestobj.toString(), new TypeReference<Request>() {
            });
            objRequest.setVariableFieldData(new Text(variableconfigdata));
            RequestDAO objRequestDAO = new RequestDAOImpl();
            User userJdoObj = userDao.getUserByEmail(UserUtil.getCurrentUser());
            if (userJdoObj != null /* && (userJdoObj.getRoles().contains("CREATOR") ||  userJdoObj.getRoles().contains("SUPERADMIN") ||  userJdoObj.getRoles().contains("PLANTMANAGER"))*/) {
                if (objRequest.getPrimaryPillar().trim().equals(objRequest.getSecondaryPillar())) {
                    responseMap.put("STATUS", "FAILURE");
                    responseMap.put("MESSAGE", "Primary Pillar and Secondary pillar can not be same time");
                } else {

                    if (objRequest.getId() == null || (objRequest.getId() == 0) || objRequest.getId().equals("")) {
                        log.info("New Request ");
                        // Validated for save
                        responseMap.put("STATUS", "FAILURE");
                        responseMap.put("MESSAGE", "Request ID not found");
                    } else {
                        log.info("Update Existing " + objRequest.getId());
                        Request fromDAO = objRequestDAO.fetchRequestById(objRequest.getId());
                        if (fromDAO != null && fromDAO.getStatus().equals("DRAFT")) {
                            objRequest.setFormId(fromDAO.getFormId());
                            objRequest.setPlant(fromDAO.getPlant());
                            objRequest.setCreatedOn(fromDAO.getCreatedOn());
                            objRequest.setCreatedBy(fromDAO.getCreatedBy());
                            if (fromDAO.getCreatedBy().equals(UserUtil.getCurrentUser()) || userJdoObj.getRoles().contains("SUPERADMIN")) {
                                objRequest.setStatus("WAITING FOR APPROVAL1");
                                objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                                objRequest.setLastUpdatedOn(new Date().getTime());
                                Request objsaved = objRequestDAO.saveRequest(objRequest);
                                responseMap.put("STATUS", "SUCCESS");
                                responseMap.put("MESSAGE", "Succesfully Submitted for Validation");
                                responseMap.put("data", objsaved);
                                Queue queue = QueueFactory.getQueue("readSheetQueue");
                                TaskOptions options = TaskOptions.Builder.withUrl("/sendnotificationtonextapprover");
                                options.param("REQUESTID", objsaved.getId().toString());
                                queue.add(options);
                            } else {
                                if (fromDAO.getPlant().equals(userJdoObj.getPlantName()) && (userJdoObj.getRoles().contains("PLANTMANAGER") || userJdoObj.getRoles().contains("EDITOR"))) {
                                    objRequest.setStatus("WAITING FOR APPROVAL1");
                                    objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                                    objRequest.setLastUpdatedOn(new Date().getTime());
                                    Request objsaved = objRequestDAO.saveRequest(objRequest);
                                    responseMap.put("STATUS", "SUCCESS");
                                    responseMap.put("MESSAGE", "Succesfully Submitted for Validation");
                                    responseMap.put("data", objsaved);
                                    Queue queue = QueueFactory.getQueue("readSheetQueue");
                                    TaskOptions options = TaskOptions.Builder.withUrl("/sendnotificationtonextapprover");
                                    options.param("REQUESTID", objsaved.getId().toString());
                                    queue.add(options);
                                } else {
                                    responseMap.put("STATUS", "FAILURE");
                                    responseMap.put("MESSAGE", "Sorry, You do not have permission to create Request");
                                }
                            }

                        } else {
                            responseMap.put("STATUS", "FAILURE");
                            responseMap.put("MESSAGE", "Request can only be updated in DRAFT State");
                        }
                    }
                }
            } else {
                responseMap.put("STATUS", "FAILURE");
                responseMap.put("MESSAGE", "Sorry, You do not have permission to create Request");
            }
        } catch (Exception e) {

            e.printStackTrace();
            log.info("Error in RequestController while saving " + e.getMessage());
            responseMap.put("STATUS", "FAILURE");
            responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
        }
        return new ObjectMapper().writeValueAsString(responseMap);
    }

    /**
     * @param request
     * @param userObj
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/validaterequest", method = RequestMethod.POST)
    public @ResponseBody
    String validate(HttpServletRequest request, @RequestParam("requestobj") String requestobj, @RequestParam("variableconfigdata") String variableconfigdata, @RequestParam("actionperformed") String actionperformed, @RequestParam("rejectcomments") String rejeccomments, @RequestParam("approvecomments") String approvecomments) throws JsonGenerationException, JsonMappingException, IOException {

        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        UserDAO userDao = new UserDAOImpls();

        log.info("requestobj: " + requestobj);
        //UserJDO userJdoObj = new UserJDO();
        try {
            Request objRequest = new ObjectMapper().readValue(requestobj.toString(), new TypeReference<Request>() {
            });
            objRequest.setVariableFieldData(new Text(variableconfigdata));
            RequestDAO objRequestDAO = new RequestDAOImpl();
            User userJdoObj = userDao.getUserByEmail(UserUtil.getCurrentUser());
            if (userJdoObj != null /* && (userJdoObj.getRoles().contains("CREATOR") ||  userJdoObj.getRoles().contains("SUPERADMIN") ||  userJdoObj.getRoles().contains("PLANTMANAGER"))*/) {
                if (objRequest.getPrimaryPillar().trim().equals(objRequest.getSecondaryPillar())) {
                    responseMap.put("STATUS", "FAILURE");
                    responseMap.put("MESSAGE", "Primary Pillar and Secondary pillar can not be same time");
                } else {

                    if (objRequest.getId() == null || (objRequest.getId() == 0) || objRequest.getId().equals("")) {
                        log.info("New Request ");
                        // Validated for save
                        responseMap.put("STATUS", "FAILURE");
                        responseMap.put("MESSAGE", "Request ID not found");
                    } else {
                        log.info("Approval Flow" + objRequest.getId());
                        Request fromDAO = objRequestDAO.fetchRequestById(objRequest.getId());
                        if (fromDAO != null && fromDAO.getStatus().contains("WAITING FOR APPROVAL")) {
                            // Validated for Approval
                            String nextStatus = getNextStatus(fromDAO.getStatus().trim());
                            if (nextStatus.trim().isEmpty()) {
                                responseMap.put("STATUS", "FAILURE");
                                responseMap.put("MESSAGE", "Invalid State, Please contact Administrator");
                            } else {

                                String approverRole = getApproverRole(fromDAO.getStatus().trim()).trim();
                                String plant = fromDAO.getPlant().trim();
                                if (userJdoObj.getRoles().contains("SUPERADMIN") || (userJdoObj.getPlantName().equals(plant) && userJdoObj.getRoles().contains(approverRole))) {
                                    objRequest.setFormId(fromDAO.getFormId());
                                    objRequest.setPlant(fromDAO.getPlant());
                                    objRequest.setCreatedOn(fromDAO.getCreatedOn());
                                    objRequest.setCreatedBy(fromDAO.getCreatedBy());
                                    if (actionperformed.equals("APPROVE")) {
                                        objRequest = setApprovedBy(fromDAO.getStatus().trim(), new Text(approvecomments), userJdoObj, objRequest);
                                        objRequest.setStatus(nextStatus.trim());
                                        objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                                        objRequest.setLastUpdatedOn(new Date().getTime());
                                        // objRequest.setApproveComments(new Text(approvecomments));
                                        objRequestDAO.saveRequest(objRequest);
                                        responseMap.put("STATUS", "SUCCESS");
                                        responseMap.put("MESSAGE", "Request Status Changed to " + nextStatus);
                                        responseMap.put("data", objRequest);
                                        Queue queue = QueueFactory.getQueue("readSheetQueue");
                                        TaskOptions options = TaskOptions.Builder.withUrl("/sendnotificationtonextapprover");
                                        options.param("REQUESTID", objRequest.getId().toString());
                                        queue.add(options);
                                    } else {
                                        objRequest.setStatus("REJECTED");
                                        objRequest.setRejectedBy(userJdoObj.getUserEmail());
                                        objRequest.setRejectComments(new Text(rejeccomments));
                                        objRequest.setLastUpdatedBy(UserUtil.getCurrentUser());
                                        objRequest.setLastUpdatedOn(new Date().getTime());
                                        objRequestDAO.saveRequest(objRequest);
                                        responseMap.put("STATUS", "SUCCESS");
                                        responseMap.put("MESSAGE", "Request Status changed to REJECTED");
                                        responseMap.put("data", objRequest);
                                        Queue queue = QueueFactory.getQueue("readSheetQueue");
                                        TaskOptions options = TaskOptions.Builder.withUrl("/sendnotificationtonextapprover");
                                        options.param("REQUESTID", objRequest.getId().toString());
                                        queue.add(options);
                                    }

                                } else {
                                    responseMap.put("STATUS", "FAILURE");
                                    responseMap.put("MESSAGE", "Sorry, You do not have permission to create Request");
                                }

                            }

                        } else {
                            responseMap.put("STATUS", "FAILURE");
                            responseMap.put("MESSAGE", "Invalid State, Please contact Administrator");
                        }
                    }
                }
            } else {
                responseMap.put("STATUS", "FAILURE");
                responseMap.put("MESSAGE", "Sorry, You do not have permission to create Request");
            }
        } catch (Exception e) {

            e.printStackTrace();
            log.info("Error in RequestController while saving " + e.getMessage());
            responseMap.put("STATUS", "FAILURE");
            responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
        }
        return new ObjectMapper().writeValueAsString(responseMap);
    }

    public static String getNextStatus(String currentStatus) {
        String nextStatus = "";

        switch (currentStatus) {
            case "WAITING FOR APPROVAL1":
                nextStatus = "WAITING FOR APPROVAL2";
                break;
            case "WAITING FOR APPROVAL2":
                nextStatus = "WAITING FOR APPROVAL3";
                break;
            case "WAITING FOR APPROVAL3":
                nextStatus = "APPROVED";
        }
        return nextStatus;
    }

    public static String getApproverRole(String currentStatus) {
        String nextStatus = "";

        switch (currentStatus) {
            case "WAITING FOR APPROVAL1":
                nextStatus = "APPROVER1";
                break;
            case "WAITING FOR APPROVAL2":
                nextStatus = "APPROVER2";
                break;
            case "WAITING FOR APPROVAL3":
                nextStatus = "APPROVER3";
        }
        return nextStatus;
    }

    public static Request setApprovedBy(String currentStatus, Text comments, User user, Request request) {
        String nextStatus = "";

        switch (currentStatus) {
            case "WAITING FOR APPROVAL1":
                request.setApprover1(user.getUserEmail());
                if (!comments.equals("")) {
                    request.setApprover1Comments(comments);
                } else {
                    request.setApprover1Comments(new Text(""));
                }
                break;
            case "WAITING FOR APPROVAL2":
                request.setApprover2(user.getUserEmail());
                if (!comments.equals("")) {
                    request.setApprover2Comments(comments);
                } else {
                    request.setApprover2Comments(new Text(""));
                }
                break;
            case "WAITING FOR APPROVAL3":
                request.setApprover3(user.getUserEmail());
                if (!comments.equals("")) {
                    request.setApprover3Comments(comments);
                } else {
                    request.setApprover3Comments(new Text(""));
                }
        }
        return request;
    }


    /**
     * @param request
     * @param userObj
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/getrequest", method = RequestMethod.GET)
    public @ResponseBody
    String getrequest(HttpServletRequest request, @RequestParam("requestid") String requestID) throws JsonGenerationException, JsonMappingException, IOException {

        HashMap<String, Object> responseMap = new HashMap<String, Object>();
        UserDAO userDao = new UserDAOImpls();
        RequestDAO objRequestDAO = new RequestDAOImpl();

        log.info("requestID: " + requestID);
        //UserJDO userJdoObj = new UserJDO();
        try {

            User userJdoObj = userDao.getUserByEmail(UserUtil.getCurrentUser());
            if (requestID != null && requestID != "") {
                Request objRequest = objRequestDAO.fetchRequestById(Long.parseLong(requestID));
                if (userJdoObj != null) {
                    if (objRequest == null) {
                        responseMap.put("STATUS", "FAILURE");
                        responseMap.put("MESSAGE", "No Request found with this id");
                    } else {
                        User pillarLead = userDao.getPillarLeadForPlant(objRequest.getPrimaryPillar(), objRequest.getPlant());
                        if (pillarLead != null)
                            responseMap.put("PILLARLEAD", pillarLead);
                        if (userJdoObj.getRoles().contains("SUPERADMIN")) {
                            responseMap.put("STATUS", "SUCCESS");
                            responseMap.put("MESSAGE", "SUCCESSFULL");
                            responseMap.put("data", objRequest);
                        } else {
                            if ((userJdoObj.getRoles().contains("PLANTMANAGER") || userJdoObj.getRoles().contains("EDITOR"))
                                    && userJdoObj.getPlantName().equals(objRequest.getPlant())) {
                                responseMap.put("STATUS", "SUCCESS");
                                responseMap.put("MESSAGE", "SUCCESSFULL");

                                responseMap.put("data", objRequest);
                            } else if (objRequest.getCreatedBy().equals(userJdoObj.getUserEmail())) {
                                responseMap.put("STATUS", "SUCCESS");
                                responseMap.put("MESSAGE", "SUCCESSFULL");
                                responseMap.put("data", objRequest);
                            } else {
                                responseMap.put("STATUS", "SUCCESS");
                                responseMap.put("MESSAGE", "SUCCESSFULL");
                                responseMap.put("data", objRequest);
                            }
                        }
                    }

                } else {
                    responseMap.put("STATUS", "FAILURE");
                    responseMap.put("MESSAGE", "Sorry, Please login first");
                }

            } else {
                responseMap.put("STATUS", "FAILURE");
                responseMap.put("MESSAGE", "Request ID in the params is empty, please contact Administrator");
            }

        } catch (Exception e) {

            e.printStackTrace();
            log.info("Error in RequestController while saving " + e.getMessage());
            responseMap.put("STATUS", "FAILURE");
            responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
        }


        //user
        User userJdoObj = userDao.getUserByEmail(UserUtil.getCurrentUser());
        Request objRequest = objRequestDAO.fetchRequestById(Long.parseLong(requestID));

        if (Objects.isNull(userJdoObj) || Objects.isNull(objRequest)) {
            responseMap.put("STATUS", "FAILURE");
            responseMap.put("MESSAGE", "Something Went Wrong , Please Contact Administrator");
        }
        boolean canApprove = false;
        String userPlantName = userJdoObj.getPlantName();
        String requestedPlanName = objRequest.getPlant();

        boolean isUserRequestHaveSamePlant = userPlantName.equals(requestedPlanName);
        boolean isUSerRequestHaveSamePillar = userJdoObj.getPillarName().equals(objRequest.getPrimaryPillar());

        log.warning("USER CONTAINS APPROVER1"+userJdoObj.getRoles().contains("APPROVER1"));
        log.warning("USER CONTAINS APPROVER1"+objRequest.getStatus().contains("APPROVAL1"));
        log.warning("having same plant "+isUserRequestHaveSamePlant);

        log.warning("USER CONTAINS APPROVER1"+userJdoObj.getRoles());
        log.warning("USER CONTAINS APPROVER1"+objRequest.getStatus());

        if (userJdoObj.getRoles().contains("SUPERADMIN")) {
            canApprove = true;
        } else if (isUserRequestHaveSamePlant && isUSerRequestHaveSamePillar && userJdoObj.getRoles().contains("APPROVER1") && objRequest.getStatus().contains("APPROVAL1")) {
            canApprove = true;
        } else if (isUserRequestHaveSamePlant && isUSerRequestHaveSamePillar && userJdoObj.getRoles().contains("APPROVER2") && objRequest.getStatus().contains("APPROVAL2")) {
            canApprove = true;
        } else if (isUserRequestHaveSamePlant && isUSerRequestHaveSamePillar && userJdoObj.getRoles().contains("APPROVER3") && objRequest.getStatus().contains("APPROVAL3")) {
            canApprove = true;
        }else if(isUSerRequestHaveSamePillar && isUserRequestHaveSamePlant && userJdoObj.getRoles().contains("PLANTMANAGER") ){
            canApprove = true;
        }
        else {
            canApprove = false;
        }


        if (objRequest.getStatus().contains("DRAFT")) {
            canApprove = false;
        }
        if (objRequest.getStatus().contains("APPROVED")) {
            canApprove = false;
        }

        log.warning("USER CONTAINS APPROVER1"+userJdoObj.getRoles());
        log.warning("USER CANAPPROVE "+canApprove);

        responseMap.put("canApprove", canApprove);

        return new ObjectMapper().writeValueAsString(responseMap);
    }


    @RequestMapping(value = "/createpdfanddownload2", method = RequestMethod.GET)
    public @ResponseBody
    void createPDFandDownload2(@RequestParam("filename") String reqid, HttpServletResponse resp) {
        try {
            log.info("FileDownload filename : " + reqid);
            RequestDAO objRequestDAO = new RequestDAOImpl();
            Request objRequest = objRequestDAO.fetchRequestById(Long.parseLong(reqid));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, stream);
            Rectangle rectangle = new Rectangle(30, 30, 550, 800);
            writer.setBoxSize("rectangle", rectangle);
            //HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            //writer.setPageEvent(event);

            document.open();
            Document pdffile = buildPdfDocument2(document, objRequest);
            document.close();
            ByteArrayInputStream inStream = new ByteArrayInputStream(stream.toByteArray());
            resp.setHeader("Content-disposition", "attachments;filename=" + objRequest.getFormId() + ".pdf");
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            String mimeType = mimeTypesMap.getContentType(objRequest.getFormId() + ".pdf");
            resp.setContentType(mimeType);
            System.out.println(mimeType);
            BufferedOutputStream outs = new BufferedOutputStream(resp.getOutputStream());
            int len;
            byte[] buf = new byte[1024];
            while ((len = inStream.read(buf)) > 0) {
                outs.write(buf, 0, len);
            }
            outs.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception occured during FileDownload : " + e);
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public Document buildPdfDocument2(Document doc, Request objRequest) throws Exception {
        TemplateBuilder buildTemplate = new TemplateBuilder();
        System.out.println(objRequest.getFormType() + "   objRequest.getFormType()");
        //Header Table Starts here
        String[][] formDetails = null;
        if (objRequest.getFormType().equals("Form-1"))
            formDetails = prepareArrayforA();
        else if (objRequest.getFormType().equals("Form-2"))
            formDetails = prepareArrayforB();
        else if (objRequest.getFormType().equals("FORM-C"))
            formDetails = prepareArrayforC();
        else if (objRequest.getFormType().equals("FORM-D"))
            formDetails = prepareArrayforD();
        System.out.println(formDetails.length);
        float colWidthSize = 0f;
        int numberOfColumns = 0;
        List<Float> colwidtharray = new ArrayList<Float>();
        List<List<String>> coldata = new ArrayList<List<String>>();
        for (String[] str : formDetails) {
            colWidthSize += Float.parseFloat(str[0]);
            colwidtharray.add(Float.parseFloat(str[0]) * 0.83f);
            numberOfColumns += 1;
            coldata.add(Arrays.asList(str));
            System.out.println("colWidth  --> " + Float.parseFloat(str[0]));
            System.out.println("colWidthSize  --> " + colWidthSize);
            if (colWidthSize == 12) {
                PdfPTable table = new PdfPTable(numberOfColumns);
                table.setWidthPercentage(100.0f);
                float[] array = new float[colwidtharray.size()];
                for (int i = 0; i < colwidtharray.size(); i++) {
                    array[i] = colwidtharray.get(i);
                }
                table.setWidths(array);
                //System.out.println(coldata.size());
                for (int i = 0; i < coldata.size(); i++) {
                    List<String> data = coldata.get(i);
                    table.addCell(getValueList(data, objRequest));
                }
                table.completeRow();
                doc.add(table);
                colwidtharray = new ArrayList<Float>();
                colWidthSize = 0;
                numberOfColumns = 0;
                coldata = new ArrayList<List<String>>();
            } else {
                //	coldata.add(Arrays.asList(str));
                //System.out.println(Integer.parseInt(str[0])*0.8f);

            }
        }

        return doc;
    }

    public PdfPCell getValueList(List<String> data, Request req) throws JsonParseException, JsonMappingException, IOException, DocumentException {

        List<String> valuesList = new ArrayList<String>();
        String str = data.get(2);
		/*HashMap<String,Object> variableData=null;
		if(str.contains("variableFieldData.")) {
			variableData = new ObjectMapper().readValue(req.getVariableFieldData().getValue(), HashMap.class);
		}*/
        HashMap<String, Object> variabledata = new ObjectMapper().readValue(req.getVariableFieldData().getValue(), new TypeReference<HashMap<String, Object>>() {
        });
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        Font fontText = FontFactory.getFont(FontFactory.HELVETICA, 9);
        Font bold = new Font(FontFamily.HELVETICA, 9, Font.BOLD);
        switch (str) {
            case "Start Date":
                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100.0f);
                //table.
                PdfPCell cell1 = new PdfPCell();


                String logoUrl = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "" + getLogo(req.getFormType());
                Image logoimage = Image.getInstance(new URL(logoUrl));
                logoimage.scaleToFit(100, 100);
                logoimage.setBorder(Rectangle.BOX);
                // logoimage.setBorderColor(BaseColor.BLUE);
                logoimage.setBorderWidth(1.5f);


					/*p.add(Chunk.NEWLINE);
					p.add(Chunk.NEWLINE);
					p.add(Chunk.NEWLINE);*/
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell1.addElement(logoimage);
                table.addCell(cell1);
                table.completeRow();
                PdfPCell cell2 = new PdfPCell();
                PdfPTable table1 = new PdfPTable(2);
                table1.setWidthPercentage(100.0f);
                cell1 = new PdfPCell();
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Start Date ");
                cell1.addElement(p);
                table1.addCell(cell1);
                cell1 = new PdfPCell();
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                DateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
                p.add(f.format(new Date(req.getStartDate())));
                cell1.addElement(p);
                table1.addCell(cell1);
                cell2.addElement(table1);
                table.addCell(cell2);
                cell.addElement(table);
                break;
            case "Details":
                Font smallFont = new Font(FontFamily.HELVETICA, 8);

                PdfPTable Detailstable = new PdfPTable(2);
                Detailstable.setWidthPercentage(100.0f);
                float[] array = new float[2];
                array[0] = 30.0f;
                array[1] = 70.0f;
                Detailstable.setWidths(array);
                PdfPCell noCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add("No ");
                p.setSpacingAfter(5.0f);
                noCell.addElement(p);
                Detailstable.addCell(noCell);
                PdfPCell novalueCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add(req.getFormId());
                novalueCell.addElement(p);
                Detailstable.addCell(novalueCell);
                Detailstable.completeRow();

                PdfPCell areaCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add("Area ");
                p.setSpacingAfter(5.0f);
                areaCell.addElement(p);
                Detailstable.addCell(areaCell);
                PdfPCell areavalueCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add(req.getArea());
                areavalueCell.addElement(p);
                Detailstable.addCell(areavalueCell);
                Detailstable.completeRow();

                PdfPCell lineCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add("Line ");
                p.setSpacingAfter(5.0f);
                lineCell.addElement(p);
                Detailstable.addCell(lineCell);
                PdfPCell linevalueCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add(req.getLine());
                linevalueCell.addElement(p);
                Detailstable.addCell(linevalueCell);
                Detailstable.completeRow();

                PdfPCell plCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add("Project Lead ");
                p.setSpacingAfter(5.0f);
                plCell.addElement(p);
                Detailstable.addCell(plCell);
                PdfPCell plvalueCell = new PdfPCell();
                p = new Paragraph(10);
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(smallFont);
                p.add(req.getProjectLead());
                plvalueCell.addElement(p);
                Detailstable.addCell(plvalueCell);
                Detailstable.completeRow();

                cell.addElement(Detailstable);
                break;
            case "formType":

                p.setAlignment(Element.ALIGN_CENTER);
                p.setFont(fontText);
                p.add(req.getFormType());
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "TEST":
                p.setAlignment(Element.ALIGN_CENTER);
                p.setFont(fontText);
                p.add(req.getFormType());
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Problem Statement":
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Problem Statement :");
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "problemStatement":
                System.out.println(req.getProblemStatement().getValue() + "tetsestes");
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                p.add(req.getProblemStatement().getValue());
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "primaryPillar":
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                p.add(req.getPrimaryPillar());
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "secondaryPillar":
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(fontText);
                p.add(req.getSecondaryPillar());
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "Secondary Pillar":
                p.setAlignment(Element.ALIGN_LEFT);
                p.setAlignment(Element.ALIGN_MIDDLE);
                p.setFont(fontText);
                p.add("Secondary Pillar");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Primary Pillar":
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setAlignment(Element.ALIGN_MIDDLE);
                p.setFont(fontText);
                p.add("Primary Pillar");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "variableFieldData.what":
                p = new Paragraph(10);
                if (variabledata.get("what") != null) {
                    p.add(new Chunk("What :", bold));
                    p.add(new Chunk(variabledata.get("what").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }
                if (variabledata.get("when") != null) {
                    p.add(new Chunk("When :", bold));
                    p.add(new Chunk(variabledata.get("when").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }
                if (variabledata.get("where") != null) {
                    p.add(new Chunk("Where :", bold));
                    p.add(new Chunk(variabledata.get("where").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }
                if (variabledata.get("who") != null) {
                    p.add(new Chunk("Who :", bold));
                    p.add(new Chunk(variabledata.get("who").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }
                if (variabledata.get("which") != null) {
                    p.add(new Chunk("Which :", bold));
                    p.add(new Chunk(variabledata.get("which").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }

                if (variabledata.get("how") != null) {
                    p.add(new Chunk("How :", bold));
                    p.add(new Chunk(variabledata.get("how").toString(), fontText));
                    p.add(Chunk.NEWLINE);
                    p.add(Chunk.NEWLINE);
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.do":
                if (variabledata.get("do") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("do").toString());
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.check":
                if (variabledata.get("check") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("check").toString());
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.phenomenonDescription":
                if (variabledata.get("phenomenonDescription") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("phenomenonDescription").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.systemAndProcess":
                if (variabledata.get("systemAndProcess") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("systemAndProcess").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.target":
                if (variabledata.get("target") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("target").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.rootCauseAnalysis":
                if (variabledata.get("rootCauseAnalysis") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("rootCauseAnalysis").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.actionsAndCountermeasures":
                if (variabledata.get("actionsAndCountermeasures") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("actionsAndCountermeasures").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.results":
                if (variabledata.get("results") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("results").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.standardizationAndFutureActions":
                if (variabledata.get("standardizationAndFutureActions") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("standardizationAndFutureActions").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.planlabel":
                if (variabledata.get("plan") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("plan").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.argument":
                if (variabledata.get("argument") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("argument").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "Plan":
                p.setAlignment(cell.ALIGN_CENTER);
                p.setFont(fontText);
                p.add("Plan");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Do":
                p.setAlignment(cell.ALIGN_CENTER);
                p.setFont(fontText);
                p.add("Do");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Check":
                p.setAlignment(cell.ALIGN_CENTER);
                p.setFont(fontText);
                p.add("Check");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Act":
                p.setAlignment(cell.ALIGN_CENTER);
                p.setFont(fontText);
                p.add("Act");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Tool Used":
                p.setAlignment(cell.ALIGN_CENTER);
                p.setFont(fontText);
                //  p.setSpacingAfter(10f);
                p.add("Tools Used");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "teamMembers":

                if (req.getTeamMembers() != null) {
                    List<HashMap<String, Object>> teammembers = new ObjectMapper().readValue(req.getTeamMembers().getValue(), new TypeReference<List<HashMap<String, Object>>>() {
                    });
                    p.setAlignment(cell.ALIGN_LEFT);
                    p.setFont(fontText);
                    p.add("Team Members : ");
                    List<String> sbMembers = new ArrayList();
                    for (HashMap<String, Object> members : teammembers) {
                        sbMembers.add(members.get("name").toString());
                    }
                    p.add(sbMembers.stream().collect(Collectors.joining(", ")));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "tools":
                System.out.println(req.getTools().size());
                System.out.println("TOOOOLSSSSS " + req.getTools().stream().collect(Collectors.joining(",")));
                if (req.getTools() != null) {
                    p.setAlignment(cell.ALIGN_CENTER);
                    p.setFont(fontText);

                    p.add(String.join(",", req.getTools()));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.planUploads":
                if (variabledata.get("planUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("planUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.doUploads":
                if (variabledata.get("doUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("doUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.checkUploads":
                if (variabledata.get("checkUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("checkUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.actUploads":
                if (variabledata.get("actUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("actUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                /**/
                break;
            case "targetDate":
                p.setAlignment(cell.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Target Date:                       ");
                if (req.getTargetDate() != 0) {

                    DateFormat f1 = new SimpleDateFormat("dd-MMM-yyyy");
                    System.out.println();
                    p.add(f1.format(new Date(req.getTargetDate())));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "actualCompletionDate":
                p.setAlignment(cell.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Completion Date: ");
                if (req.getActualCompletionDate() != 0) {

                    DateFormat f1 = new SimpleDateFormat("dd-MMM-yyyy");
                    System.out.println();
                    p.add(f1.format(new Date(req.getActualCompletionDate())));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "cost":
                p.setAlignment(cell.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Cost [INR] :                    ");
                if (req.getCost() != 0) {
                    p.add(Float.toString(req.getCost()));
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "benefitValue":
                p.setAlignment(cell.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Benefit [INR]:                      ");
                if (req.getBenefitValue() != 0) {

                    p.add(Float.toString(req.getBenefitValue()));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5) + " [INR]"));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "benefitType":
                p.setAlignment(cell.ALIGN_LEFT);
                p.setFont(fontText);
                p.add("Benefit/cost:            ");
                if (req.getCost() != 0) {
                    double calcValue = req.getBenefitValue() / req.getCost();
                    System.out.println("calcvlaue ________>" + Math.round(calcValue));
                    p.add(Double.toString(Math.round(calcValue)));
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "projectLeadName":
                if (req.getProjectLead() != null) {
                    p.setAlignment(cell.ALIGN_LEFT);
                    p.setFont(fontText);
                    p.add("Project Lead : ");
                    p.add(req.getProjectLeadName());
                }

                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;//
            case "Phenomenon Description":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Phenomenon Description :");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;//Root Cause Analysis
            case "Root Cause Analysis":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Root Cause Analysis : ");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Target":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Target : ");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
				        /* {"6","LABEL","Result","TEXT","","#e36c09"},
							{"6","LABEL","Standardization And Future Actions","TEXT","","#ffff00"},*/
            case "Standardization And Future Actions":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Standardization And Future Actions :");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Result":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Result :");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "System And Process":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("System And Process :");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;//Actions And Counter Measures
            case "Actions And Counter Measures":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Actions And Counter Measures :");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "Argument":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Argument");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "PlanLabel":
                if (req.getProjectLead() != null) {
                    p.setFont(fontText);
                    p.add("Plan");
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case "variableFieldData.act":
                if (variabledata.get("act") != null) {
                    p.setFont(fontText);
                    p.add(variabledata.get("act").toString());
                }
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "variableFieldData.phenomenonDescriptionUploads":
                if (variabledata.get("phenomenonDescriptionUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("phenomenonDescriptionUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.systemAndProcessUploads":
                if (variabledata.get("systemAndProcessUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("systemAndProcessUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
							/*{"6","LABEL","Result","TEXT","","#e36c09"},
							{"6","LABEL","Standardization And Future Actions","TEXT","","#ffff00"},
							{"6","VALUE","variableFieldData.resultsUploads","Image"},
							{"6","VALUE","variableFieldData.standardizationAndFutureActionsUploads","Image"},*/
            case "variableFieldData.standardizationAndFutureActionsUploads":
                if (variabledata.get("standardizationAndFutureActionsUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("standardizationAndFutureActionsUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.resultsUploads":
                if (variabledata.get("resultsUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("resultsUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.actionsAndCountermeasuresUploads":
                if (variabledata.get("actionsAndCountermeasuresUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("actionsAndCountermeasuresUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.rootCauseAnalysisUploads":
                if (variabledata.get("rootCauseAnalysisUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("rootCauseAnalysisUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            case "variableFieldData.targetUploads":
                if (variabledata.get("targetUploads") != null) {
                    List<HashMap<String, Object>> uploadslist = (List<HashMap<String, Object>>) variabledata.get("targetUploads");
                    Paragraph planuploads = new Paragraph();
                    int i = 1;
                    for (HashMap<String, Object> attachment : uploadslist) {
                        String url1 = "https://storage.googleapis.com/" + AppHelper.getDefaultBucketName() + "/RequestAttachments/" + attachment.get("attachmentId") + "_" + attachment.get("fileName");
                        Image image = Image.getInstance(new URL(url1));
                        image.scaleToFit(120, 250);
                        image.setBorder(Rectangle.BOX);
                        image.setBorderColor(BaseColor.BLUE);
                        image.setBorderWidth(1.5f);
                        if (i % 2 == 0)
                            planuploads.add(new Chunk(image, 8, 5, true));
                        else
                            planuploads.add(new Chunk(image, 5, 5, true));

                        i++;
                    }
                    cell.addElement(planuploads);
                }
                break;
            default:
                p.setAlignment(Element.ALIGN_CENTER);
                p.setFont(fontText);
                p.add("");
                if (data.size() > 5 && data.get(5) != "") {
                    cell.setBackgroundColor(WebColors.getRGBColor(data.get(5)));
                }
                cell.setBorderColor(WebColors.getRGBColor("#333"));
                cell.addElement(p);
                ;
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                valuesList.add("");

        }
		/*StringBuffer sb= new StringBuffer();
		for(String str1:valuesList)
			sb.append(str1);*/
        return cell;
    }

    public static String[][] prepareArrayforB() {
        String[][] formA =
                {{"4", "VALUE", "Start Date", "TABLE"},
                        {"3", "VALUE", "formType", "TEXT"},
                        {"5", "VALUE", "Details", "TABLE"},
                        {"3", "LABEL", "Problem Statement", "TEXT"},
                        {"9", "VALUE", "problemStatement", "TEXT1"},
                        {"2", "LABEL", "Primary Pillar", "TEXT"},
                        {"4", "VALUE", "primaryPillar", "TEXT"},
                        {"2", "LABEL", "Secondary Pillar", "TEXT"},
                        {"4", "VALUE", "secondaryPillar", "TEXT"},
                        {"3", "LABEL", "Phenomenon Description", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.phenomenonDescription", "TEXT", "", ""},
                        {"3", "LABEL", "System And Process", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.systemAndProcess", "TEXT", "", ""},
                        {"6", "VALUE", "variableFieldData.phenomenonDescriptionUploads", "Image"},
                        {"6", "VALUE", "variableFieldData.systemAndProcessUploads", "Image"},
                        {"2", "LABEL", "Target", "TEXT", "", "#92d050"},
                        {"2", "VALUE", "variableFieldData.target", "TEXT", "", ""},
                        {"2", "LABEL", "Root Cause Analysis", "TEXT", "", "#92d050"},
                        {"2", "VALUE", "variableFieldData.rootCauseAnalysis", "TEXT", "", ""},
                        {"2", "LABEL", "Actions And Counter Measures", "TEXT", "", "#548dd4"},
                        {"2", "VALUE", "variableFieldData.actionsAndCountermeasures", "TEXT", "", ""},
                        {"4", "VALUE", "variableFieldData.targetUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.rootCauseAnalysisUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.actionsAndCountermeasuresUploads", "Image"},
                        {"3", "LABEL", "Result", "TEXT", "", "#e36c09"},
                        {"3", "VALUE", "variableFieldData.results", "TEXT", "", ""},
                        {"3", "LABEL", "Standardization And Future Actions", "TEXT", "", "#ffff00"},
                        {"3", "VALUE", "variableFieldData.standardizationAndFutureActions", "TEXT", "", ""},
                        {"6", "VALUE", "variableFieldData.resultsUploads", "Image"},
                        {"6", "VALUE", "variableFieldData.standardizationAndFutureActionsUploads", "Image"},
                        {"2", "LABEL", "Tool Used", "LIST"},
                        {"10", "VALUE", "tools", "LIST"},
                        {"2", "VALUE", "teamMembers", "LIST1", "Team Members"},
                        {"1.5", "VALUE", "targetDate", "DATE", "Target Date"},
                        {"2", "VALUE", "actualCompletionDate", "DATE", "Completion Date"},
                        {"1.5", "VALUE", "cost", "TEXT", "Cost"},
                        {"1.5", "VALUE", "benefitValue", "TEXT", "Benefit Value"},
                        {"1.5", "VALUE", "benefitType", "LIST", "Benefit Type"},
                        {"2", "VALUE", "projectLeadName", "TEXT", "ProjectLead Name"}};
        return formA;
    }

    public static String[][] prepareArrayforA() {
        String[][] formA = {{"4", "VALUE", "Start Date", "TABLE"},
                {"3", "VALUE", "formType", "TEXT"},
                {"5", "VALUE", "Details", "TABLE"},
                {"3", "LABEL", "Problem Statement", "TEXT"},
                {"9", "VALUE", "problemStatement", "TEXT1"},
                {"2", "LABEL", "Primary Pillar", "TEXT"},
                {"4", "VALUE", "primaryPillar", "TEXT"},
                {"2", "LABEL", "Secondary Pillar", "TEXT"},
                {"4", "VALUE", "secondaryPillar", "TEXT"},
                {"2", "LABEL", "Plan", "TEXT", "", "#92d050"},
                {"4", "VALUE", "variableFieldData.what", "TEXT"},
                {"2", "LABEL", "Do", "TEXT", "", "#548dd4"},
                {"4", "VALUE", "variableFieldData.do", "TEXT"},
                {"6", "VALUE", "variableFieldData.planUploads", "Image"},
                {"6", "VALUE", "variableFieldData.doUploads", "Image"},
                {"2", "LABEL", "Check", "TEXT", "", "#e36c09"},
                {"4", "VALUE", "variableFieldData.check", "TEXT"},
                {"2", "LABEL", "Act", "TEXT", "", "#ffff00"},
                {"4", "VALUE", "variableFieldData.act", "TEXT"},
                {"6", "VALUE", "variableFieldData.checkUploads", "Image"},
                {"6", "VALUE", "variableFieldData.actUploads", "Image"},
                {"2", "LABEL", "Tool Used", "LIST"},
                {"10", "VALUE", "tools", "LIST"},
                {"2", "VALUE", "teamMembers", "LIST1", "Team Members"},
                {"1.5", "VALUE", "targetDate", "DATE", "Target Date"},
                {"2", "VALUE", "actualCompletionDate", "DATE", "Completion Date"},
                {"1.5", "VALUE", "cost", "TEXT", "Cost"},
                {"1.5", "VALUE", "benefitValue", "TEXT", "Benefit Value"},
                {"1.5", "VALUE", "benefitType", "LIST", "Benefit Type"},
                {"2", "VALUE", "projectLeadName", "TEXT", "ProjectLead Name"}};
        return formA;
    }

    public static String[][] prepareArrayforC() {
        String[][] formA =
                {{"4", "VALUE", "Start Date", "TABLE"},
                        {"3", "VALUE", "formType", "TEXT"},
                        {"5", "VALUE", "Details", "TABLE"},
                        {"3", "LABEL", "Problem Statement", "TEXT"},
                        {"9", "VALUE", "problemStatement", "TEXT1"},
                        {"2", "LABEL", "Primary Pillar", "TEXT"},
                        {"4", "VALUE", "primaryPillar", "TEXT"},
                        {"2", "LABEL", "Secondary Pillar", "TEXT"},
                        {"4", "VALUE", "secondaryPillar", "TEXT"},

                        {"2", "LABEL", "Argument", "TEXT", "", ""},
                        {"2", "LABEL", "Phenomenon Description", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.phenomenonDescription", "TEXT", "", ""},
                        {"2", "LABEL", "System And Process", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.systemAndProcess", "TEXT", "", ""},
                        {"2", "VALUE", "variableFieldData.argument", "TEXT", "", ""},
                        {"5", "VALUE", "variableFieldData.phenomenonDescriptionUploads", "Image"},
                        {"5", "VALUE", "variableFieldData.systemAndProcessUploads", "Image"},
                        {"2", "LABEL", "PlanLabel", "TEXT", "", ""},
                        {"2", "LABEL", "Target", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.target", "TEXT", "", ""},
                        {"2", "LABEL", "Actions And Counter Measures", "TEXT", "", "#548dd4"},
                        {"3", "VALUE", "variableFieldData.actionsAndCountermeasures", "TEXT", "", ""},
                        {"2", "VALUE", "variableFieldData.planlabel", "TEXT", "", ""},
                        {"5", "VALUE", "variableFieldData.targetUploads", "Image"},
                        {"5", "VALUE", "variableFieldData.actionsAndCountermeasuresUploads", "Image"},
                        {"2", "LABEL", "Root Cause Analysis", "TEXT", "", "#92d050"},
                        {"2", "VALUE", "variableFieldData.rootCauseAnalysis", "TEXT", "", ""},
                        {"2", "LABEL", "Result", "TEXT", "", "#e36c09"},
                        {"2", "VALUE", "variableFieldData.results", "TEXT", "", ""},
                        {"2", "LABEL", "Standardization And Future Actions", "TEXT", "", "#ffff00"},
                        {"2", "VALUE", "variableFieldData.standardizationAndFutureActions", "TEXT", "", ""},
                        {"4", "VALUE", "variableFieldData.rootCauseAnalysisUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.resultsUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.standardizationAndFutureActionsUploads", "Image"},
                        /*{"3","LABEL","Phenomenon Description","TEXT","","#92d050"},
					{"3","VALUE","variableFieldData.phenomenonDescription","TEXT","",""},
					{"3","LABEL","System And Process","TEXT","","#92d050"},
					{"3","VALUE","variableFieldData.systemAndProcess","TEXT","",""},
					{"6","VALUE","variableFieldData.phenomenonDescriptionUploads","Image"},
					{"6","VALUE","variableFieldData.systemAndProcessUploads","Image"},
					{"2","LABEL","Target","TEXT","","#92d050"},
					{"2","VALUE","variableFieldData.target","TEXT","",""},
					{"2","LABEL","Root Cause Analysis","TEXT","","#92d050"},
					{"2","VALUE","variableFieldData.rootCauseAnalysis","TEXT","",""},
					{"2","LABEL","Actions And Counter Measures","TEXT","","#548dd4"},
					{"2","VALUE","variableFieldData.actionsAndCountermeasures","TEXT","",""},
					{"4","VALUE","variableFieldData.targetUploads","Image"},
					{"4","VALUE","variableFieldData.rootCauseAnalysisUploads","Image"},
					{"4","VALUE","variableFieldData.actionsAndCountermeasuresUploads","Image"},
					{"3","LABEL","Result","TEXT","","#e36c09"},
					{"3","VALUE","variableFieldData.results","TEXT","",""},
					{"3","LABEL","Standardization And Future Actions","TEXT","","#ffff00"},
					{"3","VALUE","variableFieldData.standardizationAndFutureActions","TEXT","",""},
					{"6","VALUE","variableFieldData.resultsUploads","Image"},
					{"6","VALUE","variableFieldData.standardizationAndFutureActionsUploads","Image"},*/


                        {"2", "LABEL", "Tool Used", "LIST"},
                        {"10", "VALUE", "tools", "LIST"},
                        {"2", "VALUE", "teamMembers", "LIST1", "Team Members"},
                        {"1.5", "VALUE", "targetDate", "DATE", "Target Date"},
                        {"2", "VALUE", "actualCompletionDate", "DATE", "Completion Date"},
                        {"1.5", "VALUE", "cost", "TEXT", "Cost"},
                        {"1.5", "VALUE", "benefitValue", "TEXT", "Benefit Value"},
                        {"1.5", "VALUE", "benefitType", "LIST", "Benefit Type"},
                        {"2", "VALUE", "projectLeadName", "TEXT", "ProjectLead Name"}};
        return formA;
    }

    public static String[][] prepareArrayforD() {
        String[][] formA =
                {{"4", "VALUE", "Start Date", "TABLE"},
                        {"3", "VALUE", "formType", "TEXT"},
                        {"5", "VALUE", "Details", "TABLE"},
                        {"3", "LABEL", "Problem Statement", "TEXT"},
                        {"9", "VALUE", "problemStatement", "TEXT1"},
                        {"2", "LABEL", "Primary Pillar", "TEXT"},
                        {"4", "VALUE", "primaryPillar", "TEXT"},
                        {"2", "LABEL", "Secondary Pillar", "TEXT"},
                        {"4", "VALUE", "secondaryPillar", "TEXT"},
                        {"3", "LABEL", "Phenomenon Description", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.phenomenonDescription", "TEXT", "", ""},
                        {"3", "LABEL", "System And Process", "TEXT", "", "#92d050"},
                        {"3", "VALUE", "variableFieldData.systemAndProcess", "TEXT", "", ""},
                        {"6", "VALUE", "variableFieldData.phenomenonDescriptionUploads", "Image"},
                        {"6", "VALUE", "variableFieldData.systemAndProcessUploads", "Image"},
                        {"2", "LABEL", "Target", "TEXT", "", "#92d050"},
                        {"2", "VALUE", "variableFieldData.target", "TEXT", "", ""},
                        {"2", "LABEL", "Root Cause Analysis", "TEXT", "", "#92d050"},
                        {"2", "VALUE", "variableFieldData.rootCauseAnalysis", "TEXT", "", ""},
                        {"2", "LABEL", "Actions And Counter Measures", "TEXT", "", "#548dd4"},
                        {"2", "VALUE", "variableFieldData.actionsAndCountermeasures", "TEXT", "", ""},
                        {"4", "VALUE", "variableFieldData.targetUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.rootCauseAnalysisUploads", "Image"},
                        {"4", "VALUE", "variableFieldData.actionsAndCountermeasuresUploads", "Image"},
                        {"3", "LABEL", "Result", "TEXT", "", "#e36c09"},
                        {"3", "VALUE", "variableFieldData.results", "TEXT", "", ""},
                        {"3", "LABEL", "Standardization And Future Actions", "TEXT", "", "#ffff00"},
                        {"3", "VALUE", "variableFieldData.standardizationAndFutureActions", "TEXT", "", ""},
                        {"6", "VALUE", "variableFieldData.resultsUploads", "Image"},
                        {"6", "VALUE", "variableFieldData.standardizationAndFutureActionsUploads", "Image"},
                        {"2", "LABEL", "Tool Used", "LIST"},
                        {"10", "VALUE", "tools", "LIST"},
                        {"2", "VALUE", "teamMembers", "LIST1", "Team Members"},
                        {"1.5", "VALUE", "targetDate", "DATE", "Target Date"},
                        {"2", "VALUE", "actualCompletionDate", "DATE", "Completion Date"},
                        {"1.5", "VALUE", "cost", "TEXT", "Cost"},
                        {"1.5", "VALUE", "benefitValue", "TEXT", "Benefit Value"},
                        {"1.5", "VALUE", "benefitType", "LIST", "Benefit Type"},
                        {"2", "VALUE", "projectLeadName", "TEXT", "ProjectLead Name"}};
        return formA;
    }

    @RequestMapping(value = "/getPillarLead", method = RequestMethod.GET)
    public @ResponseBody
    String getPillarLead(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
        UserDAO userDao = new UserDAOImpls();
        User objUser = null;
        HashMap<String, Object> resultMap = null;
        try {

            resultMap = new HashMap<>();
            objUser = userDao.getPillarLeadForPlant(request.getParameter("PILLARNAME"), request.getParameter("PLANTNAME"));
            if (objUser != null) {

                resultMap.put("data", objUser);
                resultMap.put("MESSAGE", "FOUND");
                resultMap.put("STATUS", "SUCCESS");
            } else {
                resultMap.put("data", "");
                resultMap.put("MESSAGE", "NO PILLAR LEAD FOUND");
                resultMap.put("STATUS", "FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectMapper().writeValueAsString(resultMap);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendnotificationtonextapprover", method = RequestMethod.POST)
    public @ResponseBody
    void sendnotificationtonextapprover(HttpServletRequest req) {
        HashMap<String, Object> responseMap = null;
        MailHelper mailService = new MailHelper();
        try {
            Long requestID = Long.parseLong(req.getParameter("REQUESTID"));
            RequestDAO objRequestDAO = new RequestDAOImpl();
            UserDAO objUserDAO = new UserDAOImpls();
            Request objRequest = objRequestDAO.fetchRequestById(requestID);
            String approverRole = getApproverRole(objRequest.getStatus().trim()).trim();
            String plant = objRequest.getPlant().trim();
            //objUserDAO.g.
            List<String> approversList = new ArrayList<String>();
            if (approverRole != null && !approverRole.isEmpty())
                approversList = objUserDAO.getuserWithRoleandPlant(approverRole, objRequest.getPlant());
            System.out.println("approversList size" + approversList.size());
            if (approversList.size() > 0) {
                StringBuffer mailContent = new StringBuffer("Dear All,\r\n Request \\" + objRequest.getFormId() + " is Wating for your Approval \r\n Please clik on below link to view details and Validate, \r\n  " + AppHelper.getApplicationURL() + "/#pdca/" + objRequest.getFormType() + "/" + objRequest.getId());
                Set<String> toList = new HashSet<String>();
                Set<String> ccList = new HashSet<String>();
                Set<String> bccList = new HashSet<String>();
                switch (objRequest.getStatus()) {
                    case "WAITING FOR APPROVAL1":
                        for (String objUser : approversList) {
                            toList.add(objUser);
                        }
                        ccList.add(objRequest.getCreatedBy());
                        bccList.add("nickjosegcp@gmail.com");
                        mailService.sendMail(toList, ccList, bccList, "Request " + objRequest.getFormId() + " is Wating for your Approval", mailContent);
                        break;
                    case "WAITING FOR APPROVAL2":
                        for (String objUser : approversList) {
                            toList.add(objUser);
                        }
                        ccList.add(objRequest.getCreatedBy());
                        bccList.add("nickjosegcp@gmail.com");
                        mailService.sendMail(toList, ccList, bccList, "Request " + objRequest.getFormId() + " is Wating for your Approval", mailContent);
                        break;
                    case "WAITING FOR APPROVAL3":
                        for (String objUser : approversList) {
                            toList.add(objUser);
                        }
                        ccList.add(objRequest.getCreatedBy());
                        bccList.add("nickjosegcp@gmail.com");
                        mailService.sendMail(toList, ccList, bccList, "Request " + objRequest.getFormId() + " is Wating for your Approval", mailContent);
                        break;
                    case "APPROVED":
                        mailContent = new StringBuffer("Dear All,\r\n Request \\" + objRequest.getFormId() + " is Approved. \r\n Please clik on below link to view details, \r\n  " + AppHelper.getApplicationURL() + "/#pdca/" + objRequest.getFormType() + "/" + objRequest.getId());
                        toList.add(objRequest.getCreatedBy());
                        mailService.sendMail(toList, ccList, bccList, "Request " + objRequest.getFormId() + " is Approved", mailContent);
                        break;
                    case "REJECTED":
                        mailContent = new StringBuffer("Dear All,\r\n Request \\" + objRequest.getFormId() + " is REJECTED. \r\n Please clik on below link to view details, \r\n  " + AppHelper.getApplicationURL() + "/#pdca/" + objRequest.getFormType() + "/" + objRequest.getId());
                        toList.add(objRequest.getCreatedBy());
                        mailService.sendMail(toList, ccList, bccList, "Request " + objRequest.getFormId() + " is REJECTED", mailContent);
                        break;
                    default:
                        log.log(Level.INFO, "Status Not Appropriate.");
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

    public String getLogo(String formType) {
        String loglURL = "";
        switch (formType.trim()) {
            case "Form-1":
                loglURL = "/images/pdca-method-deming-wheel.jpg";
                break;
            case "Form-2":
                loglURL = "/images/pdca-plan-management-process-lean.jpg";
                break;
            case "FORM-C":
                loglURL = "/images/pdcalogo.jpg";
                break;
            case "FORM-D":
                loglURL = "/images/pdcalogo.jpg";
                break;
            default:
                loglURL = "images/error-icon.png";
                // code block
        }

        return loglURL;
    }
}
