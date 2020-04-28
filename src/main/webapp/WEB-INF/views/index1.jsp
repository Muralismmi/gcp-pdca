<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page
        import="com.entity.User"
        import="org.codehaus.jackson.map.ObjectMapper"
%>
<%
    User objUser = (User) request.getAttribute("user");
    ObjectMapper mapper = new ObjectMapper();
    String userDetails = mapper.writeValueAsString(objUser);

    String logouturl = (String) request.getAttribute("signouturl");
    String logegedInUserEmailId = (String) request.getAttribute("loggedinusersmailid");
    String userName = objUser.getFirstName() + " " + objUser.getLastName();
    System.out.println("the is logegedInUserEmailId is this :: " + logegedInUserEmailId);
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>MASTER DATA MANAGEMENT</title>

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"
          media="screen,projection"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
    <link type="text/css" rel="stylesheet" href="css/mdb.min.css"
          media="screen,projection"/>
    <link rel="stylesheet" type="text/css" href="css/datatables.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/select2.min.css">
    <link rel="stylesheet" type="text/css" href="css/select2-bootstrap.css">
    <link rel="stylesheet" type="text/css" href="css/pmd-select2.css">
    <link type="text/css" rel="stylesheet" href="css/style.css"
          media="screen,projection"/>
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet"
          href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/pnotify.custom.min.css">
    <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="css/bootstrap-material-datetimepicker.css" rel="stylesheet">
</head>
<script>
    var logegedInUserEmailId = '<%=logegedInUserEmailId%>';
    window.useremailid = '<%=logegedInUserEmailId%>';
    var userDetails =<%=userDetails%>;
    var signouturl = '<%=logouturl%>';

</script>
<body id="page-top">
<div class="loader-wrapper" id="pleasewait" style="display: none;">
    <div class="loader-content">
        <div class="global-spinner"></div>
    </div>
</div>
<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <a class="navbar-brand header-logo" href=""> <img src="\images\pdcalogo.png" alt="pdca logo" height="39px"
                                                      width="89px">

        <strong class="applicationName ml-2">MDM PORTAL</strong></a>

    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">

    </form>
    <ul class="navbar-nav ml-auto ml-md-0">
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-user-circle fa-fw"></i><span><%=userName%></span>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">

                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="<%=logouturl%>">Logout</a>
            </div>
        </li>
    </ul>
    <!--  <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
       <i class="fas fa-bars"></i>
     </button> -->

    <!-- Navbar Search -->
    <!--  <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
       <div class="input-group">
         <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
         <div class="input-group-append">
           <button class="btn btn-primary" type="button">
             <i class="fas fa-search"></i>
           </button>
         </div>
       </div>
     </form> -->

    <%-- <div class="nav d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0" id="userDropdown">
                   Welcome <%=userName%> </div> --%>

    <!-- Navbar -->
    <!-- <div class="dropdown-menu dropdown-menu-right show" aria-labelledby="userDropdown">
          <a class="dropdown-item" href="#">Settings</a>
          <a class="dropdown-item" href="#">Activity Log</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Logout</a>
        </div> --%> -->

</nav>

<div id="wrapper">

    <!-- Sidebar -->
    <ul class="sidebar navbar-nav">
        <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#"
                style="float: right;margin-left: auto;">
            <i class="fas fa-bars"></i>
        </button>
        <li class="nav-item active">
            <a class="nav-link" href="/">
                <i class="fas fa-fw fa-home" aria-hidden="true"></i>
                <span>Home</span>
            </a>
        </li>
        <!--
                <li class="nav-item ">
                <a data-target="#chooseworktype-modal" data-toggle="modal" class="nav-link createPDCA" >
                  <i class="fas fa-fw fa-file"  aria-hidden="true"></i>
                  <span>Create PDCA</span>
                </a>
              </li>	 -->
        <li class="nav-item ">
            <a data-target="#chooseworktype-modal" data-toggle="modal" class="nav-link  viewPDCA">
                <i class="fas fa-fw fa-file" aria-hidden="true"></i>
                <span>Create Form</span>
            </a>
        </li>

        <li class="nav-item ">
            <a href="#viewPDCA" id="" class="nav-link  viewPDCA">
                <i class="fas fa-fw fa-folder" aria-hidden="true"></i>
                <span>Form List</span>
            </a>
        </li>

        <!-- <li class="nav-item ">
           <a href="#PDCAprintView" id=""  class="nav-link colorMenu viewPDCA">
              <i class="fas fa-fw fa-server"  aria-hidden="true"></i>
              <span>View PDCA</span>
            </a>
          </li>  -->
        <!--  <li class="nav-item ">
          <a href="#myaction" id="" class="nav-link colorMenu myAction">
             <i class="fas fa-fw fa-home"  aria-hidden="true"></i>
             <span>My Action</span>
           </a>
         </li> -->
        <li class="nav-item ">
            <a href="#userrole" id="userRole-Item" class="nav-link  myAction">
                <i class="fas fa-fw fa-users" aria-hidden="true"></i>
                <span>User Management</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#plant" id="plant-Item" class="nav-link  myAction">
                <i class="fas fa-fw fa-building" aria-hidden="true"></i>
                <span>Plant Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#PILLAR" id="masterConfiguration-Item" data-configtype="PILLAR" class="nav-link  myAction">
                <i class="fas fa-fw fa-key" aria-hidden="true"></i>
                <span>Pillar Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#LINE" id="linemasterdata-Item" data-configtype="LINE" class="nav-link  myAction">
                <i class="fas fa-fw fa-layer-group" aria-hidden="true"></i>
                <span>Line Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#AREA" id="linemasterdata-Item" data-configtype="AREA" class="nav-link  myAction">
                <i class="fas fa-fw fa-cube" aria-hidden="true"></i>
                <span>Area Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#BENEFITTYPE" id="linemasterdata-Item" data-configtype="BENEFITTYPE" class="nav-link  myAction">
                <i class="fas fa-fw fa-gears" aria-hidden="true"></i>
                <span>Benefit Type Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#TOOL" id="linemasterdata-Item" data-configtype="TOOL" class="nav-link  myAction">
                <i class="fas fa-fw fa-tools" aria-hidden="true"></i>
                <span>Tool Master</span>
            </a>
        </li>
        <li class="nav-item ">
            <a href="#LOSSTYPE" id="linemasterdata-Item" data-configtype="LOSSTYPE" class="nav-link  myAction">
                <i class="fas fa-fw fa-level-down-alt" aria-hidden="true"></i>
                <span>Losstype Master</span>
            </a>
        </li>
        <!--  <a class="dropdown-item" id="userRole-Item" style="display:none" href="#userrole">User Role</a>
                        <a class="dropdown-item" id="plant-Item" style="display:none" href="#plant">Plant</a>
                         <a class="dropdown-item" id="masterConfiguration-Item" style="display:none" href="#masterConfiguration">Master Configuration</a> -->
        <!--  <li class="nav-item dropdown">
           <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
             <i class="fas fa-fw fa-folder"></i>
             <span>Pages</span>
           </a>
           <div class="dropdown-menu" aria-labelledby="pagesDropdown">
             <h6 class="dropdown-header">Login Screens:</h6>
             <a class="dropdown-item" href="login.html">Login</a>
             <a class="dropdown-item" href="register.html">Register</a>
             <a class="dropdown-item" href="forgot-password.html">Forgot Password</a>
             <div class="dropdown-divider"></div>
             <h6 class="dropdown-header">Other Pages:</h6>
             <a class="dropdown-item" href="404.html">404 Page</a>
             <a class="dropdown-item" href="blank.html">Blank Page</a>
           </div>
         </li>
         <li class="nav-item">
           <a class="nav-link" href="charts.html">
             <i class="fas fa-fw fa-chart-area"></i>
             <span>Charts</span></a>
         </li> -->
        <!--   <li class="nav-item">
            <a class="nav-link" href="tables.html">
              <i class="fas fa-fw fa-table"></i>
              <span>Tables</span></a>
          </li> -->
    </ul>

    <div id="content-wrapper">


        <main class="content-wrapper" style="min-height: calc(100vh - 131px);">

            <section id="home-section" style="display:none;">
                <div class="content-wrapper homeBanner-image d-flex justify-content-end title-header">

                </div>

            </section>


            <!-- //home-section -->
            <section id="workpermitform" style="display:none;">
                <!-- <div class="homeBanner-container">
                    <div class="homeBanner-image"></div>
                    <div class="homeBanner-overlay"></div>
                </div> -->
                <div class="d-flex justify-content-end title-header"
                     style="margin-top:5px;margin-bottom: 5px;background: #dddddd;padding: 5px 10px;">
                    <!-- <button class="btn btn-link btn-sm" id="backfromworkpermit-btn" style="margin: 0;box-shadow: none;cursor: pointer;">
                        <span><i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Return"></i> </span>
                    </button> -->
                    <h2 class="custom-title mr-auto align-self-center" id="workpermithead">

                    </h2>
                    <div class="pull-left polivalance-count customstatus-design"
                         style="font-size:12px;font-weight:bold;background: #fff;padding: 4px;">
                        <span style="display:none">Request ID :<i id="reqid_label"></i></span>
                        <span>Request ID : <i id="reqname_label1"></i></span>
                        <span>Status : <i id="status_label"></i></span>
                        <span>Plant : <i id="plant_label"></i></span>
                        <span>Created On: <i id="created_on_label"></i></span>
                        <span>Created By: <i id="created_by_label"></i></span>
                    </div>
                </div>
                <div class="vcontainer-fluid" id="createpermit-container">
                    <!-- <h2 class="main-title">Create Work At Height</h2> -->
                    <div class="d-flex justify-content-end fixme" id="workpermit-btndiv" style="margin-bottom: 5px;">
                        <div class="mr-auto align-self-center custom-actionbtn">
                            <button class="btn btn-default btn-sm waves-effect waves-light" id="savepdca-btn"
                                    style="margin: 0 ;margin-right:6px;">
                                <span><i class="fa fa-save" aria-hidden="true"></i> Save Draft</span>
                            </button>
                            <button class="btn btn-default btn-sm waves-effect waves-light " id="submitpdca-btn"
                                    style="margin: 0 ;">
                                <span><i class="fa fa-send-o" aria-hidden="true"></i> Submit for Approval</span>
                            </button>
                            <!-- </div> -->
                            <%--
                                <!-- <button class="btn btn-default btn-sm waves-effect waves-light " id="deletewokpermit-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-trash" aria-hidden="true"></i> <spring:message code="4_Delete"/></span>
                                </button> -->
                                <button class="btn btn-danger btn-sm waves-effect waves-light " id="rejectwokpermit-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-thumbs-down" aria-hidden="true"></i>REJECT</span>
                                </button>
                                <button class="btn btn-success btn-sm waves-effect waves-light " id="approvewokpermit-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-thumbs-up" aria-hidden="true"></i>APPROVE </span>
                                </button>
                                <!-- <button class="btn btn-warning btn-sm waves-effect waves-light " id="demotewokpermit-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-hand-o-left" aria-hidden="true"></i> Demote</span>
                                </button> -->
                                <button class="btn btn-warning btn-sm waves-effect waves-light" id="demotetoapprover-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-info-circle" aria-hidden="true"></i> <spring:message code="4_Demote"/></span>
                                </button>
                                <button class="btn btn-default btn-sm waves-effect waves-light " id="backfromworkmonitor-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Return"></i> </span>
                                </button>
                                <!-- <button class="btn btn-success btn-sm waves-effect waves-light " id="completeworkmonitor-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-check" aria-hidden="true"></i> Completed</span>
                                </button>
                                <button class="btn btn-danger btn-sm waves-effect waves-light " id="notcompleteworkmonitor-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-times" aria-hidden="true"></i> Not Completed</span>
                                </button> -->
                                <button class="btn btn-success btn-sm waves-effect waves-light " id="viewpdf-btn" style="margin: 0 ;">
                                    <span><i class="fa fa-download" aria-hidden="true"></i>Download Pdf</span>
                                </button>
                                <button class="btn btn-success btn-sm waves-effect waves-light " id="printview" style="margin: 0 ;">
                                    <span><i class="fa fa-download" aria-hidden="true"></i>Final View</span>
                                </button> --%>
                        </div>
                    </div>
                    <div class="alert alert-danger" role="alert" style="padding: 8px 15px;display:none;"
                         id="demotecommentsdiv">
                        <h6 class="alert-heading">Demote Comments</h6>
                        <p class="mb-0" id="demoteCommentstext">A simple danger alert—check it out!</p>
                    </div>
                    <div class="alert alert-danger" role="alert" style="padding: 8px 15px;display:none;"
                         id="rejectcommentsdiv">
                        <h6 class="alert-heading">Reject Comments</h6>
                        <p class="mb-0" id="rejectCommentstext">A simple danger alert—check it out!</p>
                    </div>
                    <div id="createpermit-content" style="margin-top: 7px;">
                        <div class="workatheight-form" id="workpermit-form" style="display:none;">


                            <!-- workonenergizedunits -->
                            <div class="workonenergizedunits" style="display:none">


                            </div>
                        </div>


                    </div>

            </section>
            <section id="plant" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Plant</h2>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="plantable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>Plant Code</th>
                                <th>Plant Name</th>
                                <th>Location</th>
                                <th>Region</th>
                                <th>Created By</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="plant-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Plant</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="plant-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Plant Code <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="plant-plantCode" id="plant-plantCode">
                                    </div>
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Plant Name <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="plant-plantName" id="plant-plantName">
                                    </div>
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Location <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="plant-location" id="plant-location">
                                    </div>
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Region <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="plant-region" id="plant-region">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="plantSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section id="pillar" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Pillar MasterData</h2>
                    <div>
                        <div>
                            <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Pillar%20Master-Bulk%20Upload.xlsx?src=ac"
                               download>Click here to download Template for Bulk upload</a></div>
                        <br/>
                        <div>

                            <div>
                                <table class="table table-striped table-bordered nowrap"
                                       cellspacing="0" width="100%" id="pillartable">
                                    <thead>
                                    <tr>
                                        <!-- <th style="width: 40px">
                                            <fieldset class="form-group">
                                                <input type="checkbox" name="select_all" class="filled-in">
                                                <label for=""></label>
                                            </fieldset>
                                        </th> -->
                                        <th>Pillar Name</th>
                                        <th>Pillar Type</th>
                                        <!-- <th>Location</th>
                                        <th>Region</th> -->
                                        <th>Created By</th>
                                        <th>Created On</th>
                                        <th class="text-center action-col" style="width: 100px">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- adminroles-modal -->
                        <div class="modal fade" id="pillar-modal">
                            <div class="modal-dialog " role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="">Create Pillar</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" id="">
                                        <form class="row selfalign-bottom" id="pillar-form">
                                            <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                                <label class="">Pillar Name <span class="text-danger">*</span></label>
                                                <input data-required="yes" data-type="text" type="text"
                                                       class="form-control" name="pillar-pillarName"
                                                       id="pillar-pillarName">
                                            </div>
                                            <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                                <label class="">Pillar Type <span class="text-danger">*</span></label>
                                                <input data-required="yes" data-type="text" type="text"
                                                       class="form-control" name="pillar-pillarType"
                                                       id="pillar-pillarType">
                                            </div>
                                            <!-- <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                                <label class="">Location <span class="text-danger">*</span></label>
                                                <input  data-required="yes" data-type="text" type="text" class="form-control"name="plant-location" id="plant-location">
                                            </div>
                                            <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                                <label class="">Region <span class="text-danger">*</span></label>
                                                <input  data-required="yes" data-type="text" type="text" class="form-control"name="plant-region" id="plant-region">
                                            </div> -->
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <a class="btn btn-default btn-sm" id=""
                                           style="margin-right: auto;">Clear</a> <a
                                            class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                        <button class="btn btn-success btn-sm" id="pillarSaveBtn">Save</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="pillarmasterbulk-modal">
                            <div class="modal-dialog " role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="">Bulk Upload Pillar Master data</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" id="">
                                        <form class="md-form" id="bulkuploadpillarmasterform">
                                            <div class="file-field">
                                                <div class="btn btn-primary btn-sm float-left">
                                                    <input type="file" id="bulkuploadpillarmasterinput" name="files"
                                                           onchange="checkfile(this);">
                                                </div>
                                                <div style="display:none" class="file-path-wrapper">
                                                    <input class="file-path validate" type="text"
                                                           placeholder="Upload your file">
                                                </div>
                                            </div>
                                        </form>

                                    </div>
                                    <div class="modal-footer">
                                        <a class="btn btn-default btn-sm" id=""
                                           style="margin-right: auto;">Clear</a> <a
                                            class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                        <button class="btn btn-success btn-sm" id="pillarmasterbulkSaveBtn">Save
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
            </section>
            <section id="area" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Area MasterData</h2>
                    <div>
                        <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Area%20Master-Bulk%20Upload.xlsx?src=ac"
                           download>Click here to download Template for Bulk upload</a></div>
                    <br/>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="areatable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>Area Name</th>
                                <th>Plant</th>
                                <!-- <th>Location</th>
                                <th>Region</th> -->
                                <th>Created By</th>
                                <th>Created On</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="area-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Area</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="area-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Area Name <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="area-areaName" id="area-areaName">
                                    </div>
                                    <div class="form-group col-md-6" style="display:none">
                                        <label>Plant Id</label>
                                        <select data-required="yes" data-type="text" id="area-plantId"
                                                name="area-plantId"
                                                class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                style="width: 100%">
                                        </select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Plant Name</label>
                                        <select data-required="yes" data-type="text" id="area-plantName"
                                                name="area-plantName"
                                                class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                style="width: 100%">
                                        </select>
                                    </div>

                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="areaSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="areamasterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload Area Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadareamasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadareamasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="areamasterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section id="line" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Line MasterData</h2>
                    <div>
                        <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Line%20Master-Bulk%20Upload.xlsx?src=ac"
                           download>Click here to download Template for Bulk upload</a></div>
                    <br/>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="linetable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>Line Name</th>
                                <th>Plant</th>
                                <!-- <th>Location</th>
                                <th>Region</th> -->
                                <th>Created By</th>
                                <th>Created On</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="line-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Line</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="area-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Line Name <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="line-lineName" id="line-lineName">
                                    </div>
                                    <div class="form-group col-md-6" style="display:none">
                                        <label>Plant Id</label>
                                        <select data-required="yes" data-type="text" id="line-plantId"
                                                name="line-plantId"
                                                class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                style="width: 100%">
                                        </select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Plant Name</label>
                                        <select data-required="yes" data-type="text" id="line-plantName"
                                                name="line-plantName"
                                                class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                style="width: 100%">
                                        </select>
                                    </div>

                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="lineSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="linemasterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload Line Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadlinemasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadlinemasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="linemasterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section id="losstype" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Loss Type MasterData</h2>
                    <div>
                        <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-LossType%20Master-Bulk%20Upload.xlsx?src=ac"
                           download>Click here to download Template for Bulk upload</a></div>
                    <br/>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="losstypetable">
                            <thead>
                            <tr>
                                <!-- 	<th style="width: 40px">
                                        <fieldset class="form-group">
                                            <input type="checkbox" name="select_all" class="filled-in">
                                            <label for=""></label>
                                        </fieldset>
                                    </th> -->
                                <th>Loss Type</th>
                                <th>Created By</th>
                                <th>Created On</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="losstype-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create LossType</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="losstype-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Loss Type <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="losstype-losstypeName" id="losstype-losstypeName">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="losstypeSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="losstypemasterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload LossType Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadlosstypemasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadlosstypemasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="losstypemasterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
            <section id="tool" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Tool MasterData</h2>
                    <div>
                        <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Tool%20Master-Bulk%20Upload.xlsx?src=ac"
                           download>Click here to download Template for Bulk upload</a></div>
                    <br/>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="tooltable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>Tool Name</th>
                                <th>Created By</th>
                                <th>Created On</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="tool-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Tool</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="tool-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Tool Name <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="tool-toolName" id="tool-toolName">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="toolSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="toolmasterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload Tool Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadtoolmasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadtoolmasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="toolmasterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>

            </section>

            <section id="benefit" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Benefit Type MasterData</h2>
                    <div>
                        <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Benefit%20Type%20Master-Bulk%20Upload.xlsx?src=ac"
                           download>Click here to download Template for Bulk upload</a></div>
                    <br/>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="benefittable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>Benefit Type</th>
                                <th>Created By</th>
                                <th>Created On</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- adminroles-modal -->
                <div class="modal fade" id="benefit-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Benefit</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="benefit-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Benefit Type <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="benefit-benefitName" id="benefit-benefitName">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="benefitSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="benefitmasterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload Benefit Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadbenefitmasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadbenefitmasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="benefitmasterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
            <section id="masterConfiguration" style="padding-top: 50px; display: none">
                <div class="container-fluid">
                    <h2 class="main-title">Master Configuration</h2>
                    <div>
                        <div>
                            <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-Master%20BulkUpload.xlsx?src=ac"
                               download>Click here to download Template for Bulk upload</a></div>
                        <br/>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="masterconfigurationtable">
                            <thead>
                            <tr>
                                <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th>
                                <th>Configuration Value</th>
                                <th>Configuration Type</th>
                                <th>Plant</th>
                                <th>Created By</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- masterconfiguration-modal -->
                <div class="modal fade" id="masterconfiguration-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Create Master Configuration</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="row selfalign-bottom" id="masterConfiguration-form">
                                    <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                        <label class="">Value <span class="text-danger">*</span></label>
                                        <input data-required="yes" data-type="text" type="text" class="form-control"
                                               name="masterConfiguration-value" id="masterConfiguration-value">
                                    </div>

                                    <!-- <input data-required="yes" data-type="text" type="text" class="form-control"name="masterConfiguration-type" id="masterConfiguration-type"> -->
                                    <div class="form-group col-md-6 cus-formgroup-sm">
                                        <label class="">Type</label>
                                        <div>
                                            <select disabled data-required="yes" theme="bootstrap" data-type="text"
                                                    type="text" class="form-control" name="masterConfiguration-type"
                                                    id="masterConfiguration-type" class="js-data-example-ajax"
                                                    style="width: 100%">
                                                <option value="PILLAR">Pillar</option>
                                                <option value="LINE">Line</option>
                                                <option value="LOSSTYPE">Loss Type</option>
                                                <option value="TOOL">Tool</option>
                                                <option value="AREA">Area</option>
                                                <option value="BENEFITTYPE">Benefit Type</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Plant Name</label>
                                        <select data-required="yes" data-type="text" id="masterConfiguration-plantName"
                                                name="masterConfiguration-plantName"
                                                class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                style="width: 100%">
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="masterConfigurationSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="masterbulk-modal">
                    <div class="modal-dialog " role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="">Bulk Upload Master data</h5>
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="">
                                <form class="md-form" id="bulkuploadmasterform">
                                    <div class="file-field">
                                        <div class="btn btn-primary btn-sm float-left">
                                            <input type="file" id="bulkuploadmasterinput" name="files"
                                                   onchange="checkfile(this);">
                                        </div>
                                        <div style="display:none" class="file-path-wrapper">
                                            <input class="file-path validate" type="text"
                                                   placeholder="Upload your file">
                                        </div>
                                    </div>
                                </form>

                            </div>
                            <div class="modal-footer">
                                <a class="btn btn-default btn-sm" id=""
                                   style="margin-right: auto;">Clear</a> <a
                                    class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                <button class="btn btn-success btn-sm" id="masterbulkSaveBtn">Save</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>


            <section id="userrole" style="display: none;">
                <div class="container-fluid">
                    <h2 class="main-title">User Role</h2>
                    <div>
                        <div>
                            <a href="https://storage.cloud.google.com/pdca-test-1.appspot.com/RequestAttachments/Template-User%20Bulk%20Upload.xlsx?src=ac"
                               download>Click here to download Template for Bulk upload</a></div>
                        <br/>
                        <div>
                            <table class="table table-striped table-bordered nowrap"
                                   cellspacing="0" width="100%" id="userrole-table">
                                <thead>
                                <tr>
                                    <!-- 	<th style="width: 40px">
                                            <fieldset class="form-group">
                                                <input type="checkbox" name="select_all" class="filled-in">
                                                <label for=""></label>
                                            </fieldset>
                                        </th> -->
                                    <th>Title</th>
                                    <th>Name</th>
                                    <th>Mail Id</th>
                                    <th>Plant</th>
                                    <th>Pillar</th>
                                    <th>Is Pillar Leader</th>
                                    <th>Is Editor</th>
                                    <th>Is Pillar Approver</th>
                                    <th>Is CD for Financial Approver</th>
                                    <th>Is WCM/FI Approver</th>
                                    <th>Is Active</th>
                                    <th>Edit</th>
                                    <th>Delete</th>
                                    <!-- <th>Created By</th>
                                    <th>Created On</th> -->
                                    <!-- <th class="text-center action-col" style="width: 100px">Actions</th> -->
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <fieldset class="form-group">
                                            <input type="checkbox" name="select_all" class="filled-in">
                                            <label for=""></label>
                                        </fieldset>
                                    </td>
                                    <td>nickjosegcp@gmail.com</td>
                                    <td>BO</td>
                                    <td>nickjosegcp@gmail.com</td>
                                    <td>13/02/2019</td>
                                    <td class="action-col"><a class="z-depth-1"><i
                                            class="fa fa-pencil"></i></a> <a class="z-depth-1 "><i
                                            class="fa fa-trash"></i></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- adminroles-modal -->
                    <div class="modal fade" id="userrole-modal">
                        <div class="modal-dialog " role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="">Create User Role</h5>
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="">
                                    <form class="row selfalign-bottom" id="userrole-form">
                                        <div class="form-group col-md-12 cus-formgroup-sm">
                                            <label class="">Role Type <span class="text-danger">*</span></label>
                                            <div class="form-inline" id="">
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-superAdmin"
                                                           id="userrole-superAdmin"
                                                           class="filled-in" value="SUPERADMIN"> <label
                                                        for="userrole-superAdmin" style="padding-left: 30px;">Super
                                                    Admin</label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-approver1"
                                                           id="userrole-approver1"
                                                           class="filled-in" value="APPROVER1"> <label
                                                        for="userrole-approver1" style="padding-left: 30px;">Pillar
                                                    Approver</label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-approver2"
                                                           id="userrole-approver2"
                                                           class="filled-in" value="APPROVER2"> <label
                                                        for="userrole-approver2" style="padding-left: 30px;">CD for
                                                    Financial Approver</label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-approver3"
                                                           id="userrole-approver3"
                                                           class="filled-in" value="APPROVER3"> <label
                                                        for="userrole-approver3" style="padding-left: 30px;">WCM/FI
                                                    Approver</label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-creator" id="userrole-creator"
                                                           class="filled-in" value="CREATOR"> <label
                                                        for="userrole-creator"
                                                        style="padding-left: 30px;">Creator</label>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <input type="checkbox" name="userrole-plantManager"
                                                           id="userrole-plantManager"
                                                           class="filled-in" value="PLANTMANAGER"> <label
                                                        for="userrole-plantManager" style="padding-left: 30px;">Plant
                                                    Manager</label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                            <label class="">User Mail Id <span class="text-danger">*</span></label>
                                            <input data-required="yes" data-type="email" type="text"
                                                   class="form-control" name="userrole-userEmail"
                                                   id="userrole-userEmail">
                                        </div>


                                        <div class="form-group col-md-6 cus-formgroup-sm">
                                            <label class="">Title</label>
                                            <div>
                                                <select data-required="yes" data-type="text" id="userrole-title"
                                                        name="userrole-title" class="js-data-example-ajax"
                                                        style="width: 100%">
                                                    <option value="">Select Title</option>
                                                    <option value="Mr.">Mr.</option>
                                                    <option value="Mrs.">Mrs.</option>
                                                    <option value="Ms.">Ms.</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                            <label class="">First Name<span class="text-danger">*</span></label>
                                            <input data-required="yes" data-type="text" id="userrole-firstName"
                                                   name="userrole-firstName" type="text" class="form-control">
                                        </div>

                                        <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                            <label class="">Last Name<span class="text-danger">*</span></label>
                                            <input data-required="yes" data-type="text" id="userrole-lastName"
                                                   name="userrole-lastName" type="text" class="form-control">
                                        </div>

                                        <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                            <label class="">User Name<span class="text-danger">*</span></label>
                                            <input data-required="yes" data-type="text" id="userrole-userName"
                                                   name="userrole-userName" type="text" class="form-control">
                                        </div>

                                        <div class="form-group col-md-6 cus-formgroup-sm" id="" style="display:none">
                                            <label class="">Pillar Id<span class="text-danger">*</span></label>
                                            <input data-required="yes" data-type="text" id="userrole-pillarId"
                                                   name="userrole-pillarId" type="text" class="form-control">
                                        </div>

                                        <!-- <div class="form-group col-md-6 cus-formgroup-sm" id="">
                                            <label class="">Pillar Name<span class="text-danger">*</span></label>
                                            <input id="userrole-pillarName" name="userrole-pillarName" type="text" class="form-control">
                                        </div> -->

                                        <div class="form-group col-md-6">
                                            <label>Pillar Name</label>
                                            <select data-required="yes" data-type="text" id="userrole-pillarName"
                                                    name="userrole-pillarName"
                                                    class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                    style="width: 100%">
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6 cus-formgroup-sm" id="" style="display:none">
                                            <label class="">Plant Id<span class="text-danger">*</span></label>
                                            <input id="userrole-plantId" name="userrole-plantId" type="text"
                                                   class="form-control">
                                        </div>
                                        <!-- 	<div class="form-group col-md-6 cus-formgroup-sm" id="" style="display:block">
                                                <label class="">Plant Id<span class="text-danger">*</span></label>
                                                <input id="userrole-plantId" name="userrole-plantId" type="text" class="form-control">
                                            </div>


                                         <div class="form-group col-md-6">
                                            <label >Plant Id</label>
                                              <select id="userrole-plantId" name="userrole-plantId" class="js-data-example-ajax autocompleteforxcnreportssearch"  style="width: 100%">
                                             </select>
                                        </div> -->
                                        <!-- <div class="form-group col-md-12 cus-formgroup-sm" id="">
                                            <label class="">Plant Name<span class="text-danger">*</span></label>
                                            <input id="userrole-plantName" name="userrole-plantName" type="text" class="form-control">
                                        </div> -->
                                        <div class="form-group col-md-6">
                                            <label>Plant Name</label>
                                            <select data-required="yes" data-type="text" id="userrole-plantName"
                                                    name="userrole-plantName"
                                                    class="js-data-example-ajax autocompleteforxcnreportssearch"
                                                    style="width: 100%">
                                            </select>
                                        </div>

                                        <div class="form-group col-md-12">
                                            <input type="checkbox" name="userrole-mentor" id="userrole-mentor"
                                                   class="filled-in" value="Mentor"> <label
                                                for="userrole-mentor" style="padding-left: 30px;">Mentor</label>
                                        </div>

                                        <div class="form-group col-md-12">
                                            <input type="checkbox" name="userrole-editor" id="userrole-editor"
                                                   class="filled-in" value="Editor"> <label
                                                for="userrole-editor" style="padding-left: 30px;">Editor</label>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <input type="checkbox" name="userrole-projectLead" id="userrole-projectLead"
                                                   class="filled-in" value="Project Lead"> <label
                                                for="userrole-projectLead" style="padding-left: 30px;">Project
                                            Lead</label>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <input type="checkbox" name="userrole-pillarLead" id="userrole-pillarLead"
                                                   class="filled-in" value="Pillar Lead"> <label
                                                for="userrole-pillarLead" style="padding-left: 30px;">Pillar
                                            Lead</label>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <a class="btn btn-default btn-sm" id="userrole-clear"
                                       style="margin-right: auto;">Clear</a> <a
                                        class="btn btn-default btn-sm" data-dismiss="modal"
                                        id="userrole-cancel">Cancel</a>
                                    <button class="btn btn-success btn-sm" id="reqRoleSaveBtn">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="usermasterbulk-modal">
                        <div class="modal-dialog " role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="">Bulk Upload User Master data</h5>
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="">
                                    <form class="md-form" id="bulkuploadusermasterform">
                                        <div class="file-field">
                                            <div class="btn btn-primary btn-sm float-left">
                                                <input type="file" id="bulkuploadusermasterinput" name="files"
                                                       onchange="checkfile(this);">
                                            </div>
                                            <div style="display:none" class="file-path-wrapper">
                                                <input class="file-path validate" type="text"
                                                       placeholder="Upload your file">
                                            </div>
                                        </div>
                                    </form>

                                </div>
                                <div class="modal-footer">
                                    <a class="btn btn-default btn-sm" id=""
                                       style="margin-right: auto;">Clear</a> <a
                                        class="btn btn-default btn-sm" data-dismiss="modal">Cancel</a>
                                    <button class="btn btn-success btn-sm" id="usermasterbulkSaveBtn">Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
            </section>
            <section id="viewPDCA" style="display: none;">
                <div class="container-fluid">
                    <h2 class="main-title">PDCA List</h2>
                    <div>
                        <table class="table table-striped table-bordered nowrap"
                               cellspacing="0" width="100%" id="viewPDCAtable">
                            <thead>
                            <tr>
                                <!-- <th style="width: 40px">
                                    <fieldset class="form-group">
                                        <input type="checkbox" name="select_all" class="filled-in">
                                        <label for=""></label>
                                    </fieldset>
                                </th> -->
                                <th>ID</th>
                                <th>Form Type</th>
                                <th>Line</th>
                                <th>Station</th>
                                <th>Project Lead</th>
                                <th>Team Members</th>
                                <th>Mentor</th>
                                <th>Pillar</th>
                                <th>Loss</th>
                                <th>Problem Statement</th>
                                <th>Start Date</th>
                                <th>Target Date</th>
                                <th>Benefit Type</th>
                                <th>Benefit [INR]</th>
                                <th>Cost [INR]</th>
                                <th>Benefit/Cost</th>
                                <th>Tools Used</th>
                                <th>Completion Date</th>
                                <th>Duration</th>
                                <th>Stage</th>
                                <th>created By</th>
                                <th>created Date</th>
                                <th>created Time</th>
                                <th class="text-center action-col" style="width: 100px">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
            <section id="PDCAprintView" style="display: none;margin:5px;">

                <div id="printviewpdca" class="container-fluid">

                    <div class="d-flex justify-content-end title-header"
                         style="margin-top:5px;margin-bottom: 5px;background: #dddddd;padding: 5px 10px;">
                        <!-- <button class="btn btn-link btn-sm" id="backfromworkpermit-btn" style="margin: 0;box-shadow: none;cursor: pointer;">
                            <span><i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Return"></i> </span>
                        </button> -->
                        <h2 class="custom-title mr-auto align-self-center" id="workpermithead">

                        </h2>
                        <div class="pull-left polivalance-count customstatus-design"
                             style="font-size:12px;font-weight:bold;background: #fff;padding: 4px;">
                            <span style="display:none">Request ID :<i id="reqid_label1"></i></span>
                            <span>Request ID : <i id="reqname_pdfview_label1"></i></span>
                            <span>Status : <i id="status_label1"></i></span>
                            <span>Plant : <i id="plant_label1"></i></span>
                            <span>Created On : <i id="created_on_label1"></i></span>
                            <span>Created By : <i id="created_by_label1"></i></span>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end fixme" id="workpermit-btndiv1" style="margin-bottom: 5px;">
                        <div class="mr-auto align-self-center custom-actionbtn">

                            <button class="btn btn-danger btn-sm waves-effect waves-light " id="rejectwokpermit-btn"
                                    style="margin: 0 ;">
                                <span><i class="fa fa-thumbs-down" aria-hidden="true"></i>REJECT</span>
                            </button>
                            <button class="btn btn-success btn-sm waves-effect waves-light " id="approvewokpermit-btn"
                                    style="margin: 0 ;">
                                <span><i class="fa fa-thumbs-up" aria-hidden="true"></i>APPROVE </span>
                            </button>
                            <button class="btn btn-success btn-sm waves-effect waves-light " id="viewpdf-btn"
                                    style="margin: 0 ;">
                                <span><i class="fa fa-download" aria-hidden="true"></i>Download Pdf</span>
                            </button>

                        </div>
                    </div>

                    <div id="printviewpdcarow" class="row withborder" style="font-size: 0.75rem;font-weight: 100;">
                        <div class="col-sm-3"><span><b>Start Date</b></span></div>
                        <div class="col-sm-3"><span><b>20-03-2019</b></span></div>
                        <div class="col-sm-2"><span><b>Form-A</b></span></div>
                        <div class="col-sm-4">
                            <table class="tg">
                                <tr>
                                    <td class="tg-kiyi">No</td>
                                    <td class="tg-0pky">PRO_QC_QK_000011</td>
                                </tr>
                                <tr>
                                    <td class="tg-0pky">Area</td>
                                    <td class="tg-0pky">TOOL ROOM</td>
                                </tr>
                                <tr>
                                    <td class="tg-fymr">Line</td>
                                    <td class="tg-0pky">Assembly Line</td>
                                </tr>
                                <tr>
                                    <td class="tg-fymr">Project Lead</td>
                                    <td class="tg-0pky">Nick Jose</td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-sm-3"><span><b>Project Name :</b></span></div>
                        <div class="col-sm-9"><span><b>Not yet decided .</b></span></div>
                        <div class="col-sm-2"><span><b>Primary Pillar :</b></span></div>
                        <div class="col-sm-4"><span><b>Quality Control</b></span></div>
                        <div class="col-sm-2"><span><b>Secondary Pillar :</b></span></div>
                        <div class="col-sm-4"><span><b>Quality Control</b></span></div>
                        <div class="col-sm-2" style="background-color:#92d050"><span><b>PLAN</b></span></div>
                        <div class="col-sm-4"><span><p>Users with Editor role will have the rights to
									1. edit/update only Masters created by him/her with stage - Draft/Rejected
									2. view PDF of all masters submitted by users of all plants</p></span></div>
                        <div class="col-sm-2" style="background-color:#548dd4"><span><b>DO</b></span></div>
                        <div class="col-sm-4"><span><p>Users with Editor role will have the rights to
							1. edit/update only Masters created by him/her with stage - Draft/Rejected
							2. view PDF of all masters submitted by users of all plants</p></span></div>
                        <div class="col-sm-6  ">
                            <ul class="list-unstyled attachdesign imageattach">
                                <li id="1562668493079">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-thankyou.jpg"
                                       data-attachmentid="1562668493079">hero-thankyou.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668477961">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668477961&amp;filename=hero-people.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-people.jpg"
                                       data-attachmentid="1562668477961">hero-people.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668493079">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-thankyou.jpg"
                                       data-attachmentid="1562668493079">hero-thankyou.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668477961">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668477961&amp;filename=hero-people.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-people.jpg"
                                       data-attachmentid="1562668477961">hero-people.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-6  ">
                            <ul class="list-unstyled attachdesign imageattach">
                                <li id="1562668493079">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-thankyou.jpg"
                                       data-attachmentid="1562668493079">hero-thankyou.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668477961">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668477961&amp;filename=hero-people.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-people.jpg"
                                       data-attachmentid="1562668477961">hero-people.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                            </ul>
                        </div>

                        <div class="col-sm-2" style="background-color:#e36c09"><span><b>CHECK</b></span></div>
                        <div class="col-sm-4"><span><p>Users with Editor role will have the rights to
									1. edit/update only Masters created by him/her with stage - Draft/Rejected
									2. view PDF of all masters submitted by users of all plants</p></span></div>
                        <div class="col-sm-2" style="background-color: #ffff00"><span><b>DO</b></span></div>
                        <div class="col-sm-4"><span><p>Users with Editor role will have the rights to
							1. edit/update only Masters created by him/her with stage - Draft/Rejected
							2. view PDF of all masters submitted by users of all plants</p></span></div>
                        <div class="col-sm-6  ">
                            <ul class="list-unstyled attachdesign imageattach">
                                <li id="1562668493079">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-thankyou.jpg"
                                       data-attachmentid="1562668493079">hero-thankyou.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668477961">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668477961&amp;filename=hero-people.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-people.jpg"
                                       data-attachmentid="1562668477961">hero-people.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-6  ">
                            <ul class="list-unstyled attachdesign imageattach">
                                <li id="1562668493079">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-thankyou.jpg"
                                       data-attachmentid="1562668493079">hero-thankyou.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                                <li id="1562668477961">
                                    <div class="cloneimage">
                                        <img src="https://www.w3schools.com/w3images/lights.jpg">
                                    </div>
                                    <a class="filedownld workpermitfiledownload"
                                       data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668477961&amp;filename=hero-people.jpg&amp;refID="
                                       data-filetype="file" data-file="hero-people.jpg"
                                       data-attachmentid="1562668477961">hero-people.jpg</a><span
                                        class="attchmentdelete"><i class="fa fa-times"></i></span>
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-2"><span><b>Tool Used</b></span></div>
                        <div class="col-sm-10"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-6">
                            <span><b>Team Menbers : Nick Jose, Rama, Dinesh Kumar, Matt Jose</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                        <div class="col-sm-1"><span><b>5W2h Tool</b></span></div>
                    </div>
                </div>


                <div class="modal fade" id="rejectpermit-modal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-notify modal-info" role="document">
                        <!--Content-->
                        <div class="modal-content">
                            <!--Header-->
                            <div class="modal-header">
                                <h5 class="modal-title">Comments</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <!--Body-->
                            <div class="modal-body">
                                <form id="">
                                    <div class="form-group">
                                        <label class="">Reject Comment</label>
                                        <textarea class="form-control" id="rejectpermitComments"></textarea>
                                    </div>
                                </form>
                            </div>

                            <!--Footer-->
                            <div class="modal-footer justify-content-center">
                                <button type="submit" class="btn btn-danger btn-sm" id="rejectpermitmodalbtn">Reject
                                </button>
                                <button class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                        <!--/.Content-->
                    </div>
                </div>


                <div class="modal fade" id="approvepermit-modal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-notify modal-info" role="document">
                        <!--Content-->
                        <div class="modal-content">
                            <!--Header-->
                            <div class="modal-header">
                                <h5 class="modal-title">Comments</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <!--Body-->
                            <div class="modal-body">
                                <form id="">
                                    <div class="form-group">
                                        <label class="">Approve Comment</label>
                                        <textarea class="form-control" id="approvepermitComments"></textarea>
                                    </div>
                                </form>
                            </div>

                            <!--Footer-->
                            <div class="modal-footer justify-content-center">
                                <button type="submit" class="btn btn-success btn-sm" id="approvepermitmodalbtn">
                                    Approve
                                </button>
                                <button class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                        <!--/.Content-->
                    </div>
                </div>
            </section>
        </main>

        <div class="modal fade" id="chooseworktype-modal">
            <div class="modal-dialog " role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="">Select Form Type</h5>
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="">
                        <form class="row selfalign-bottom" id="">
                            <!-- Simple radio with label -->
                            <div class="form-group col-md-12 cus-formgroup-sm">
                                <!-- <label class="">Role Type <span class="text-danger">*</span></label> -->
                                <div class="" id="formselectpopup">
                                    <fieldset class="form-group">
                                        <input name="selectWorkPermitType" id="selectworkatheight"
                                               type="radio" value="WorkAtHeight"> <label
                                            for="selectworkatheight">Work At Height</label>
                                    </fieldset>
                                    <fieldset class="form-group">
                                        <input name="selectWorkPermitType" id="selectworkfire"
                                               value="WorkFire" type="radio"> <label
                                            for="selectworkfire">Work Fire</label>
                                    </fieldset>
                                    </fieldset>
                                    <fieldset class="form-group">
                                        <input name="selectWorkPermitType" id="workOnEnergizedUnits"
                                               value="workOnEnergizedUnits" type="radio"> <label
                                            for="workOnEnergizedUnits">Work on Energizied Units</label>
                                    </fieldset>
                                    </fieldset>
                                    <fieldset class="form-group">
                                        <input name="selectWorkPermitType" id="excavationsPermit"
                                               value="excavationsPermit" type="radio"> <label
                                            for="excavationsPermit">Excavations Permit</label>
                                    </fieldset>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <!-- <a class="btn btn-default btn-sm" id="" style="margin-right: auto;">Clear</a>  -->
                        <a class="btn btn-default btn-sm" data-dismiss="modal"
                           id="selectWkType-cancelBtn">Cancel</a>
                        <button class="btn btn-success btn-sm" id="selectWkType-saveBtn">Select</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" id="attach-modal" style="display: none;" aria-hidden="true">
            <div class="modal-dialog " role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add Attachment</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <form id="workpermitattach-form">
                        <div class="modal-body">
                            <div class="form-group" style="position:relative;">
                                <input id="workpermit-filename" type="text" class="form-control"
                                       placeholder="Click here to Browse...">
                                <input id="workpermit-attachfile" type="file" name="files">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" data-dismiss="modal" class="btn btn-default btn-sm">Cancel</button>
                            <button disabled type="button" id="workpermitattach-uploadbtn"
                                    class="btn btn-success btn-sm">Upload
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal" id="link-modal" style="display: none;" aria-hidden="true">
            <div class="modal-dialog " role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"><spring:message code="16_Add_Link"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <form id="workpermit-linkform">
                        <div class="modal-body">
                            <div class="form-group" style="position:relative;">
                                <label><spring:message code="13_File_Name"/></label>
                                <input id="workpermit-linktxt" type="text" class="form-control" data-required="yes"
                                       data-type="text">
                            </div>
                            <div class="form-group" style="position:relative;">
                                <label><spring:message code="16_Link"/></label>
                                <input id="workpermit-link" type="text" class="form-control" data-required="yes"
                                       data-type="text">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><spring:message
                                    code="16_Cancel"/></button>
                            <button type="button" class="btn btn-success btn-sm" id="workpermit-addlinkbtn">
                                <spring:message code="16_Add"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
    <!-- /.container-fluid -->

    <!-- Sticky Footer -->
    <footer class="sticky-footer">
        <div class="container my-auto">
            <div class="copyright text-center my-auto">
                <span>Copyright ©  2019</span>
            </div>
        </div>
    </footer>

</div>
<!-- /.content-wrapper -->


<!-- Bootstrap core JavaScript-->
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript"
        src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/tether.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/mdb.min.js"></script>
<script src="js/sb-admin.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="js/datatables.min.js"></script>
<script type="text/javascript" src="js/pnotify.custom.min.js"></script>
<script type="text/javascript" src="js/select2.full.js"></script>
<script type="text/javascript" src="js/DataTable-Actions.js"></script>
<script type="text/javascript" src="js/moment-with-locales.min.js"></script>
<script type="text/javascript" src="js/moment-timezone.min.js"></script>
<script type="text/javascript" src="js/moment-timezone-with-data.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="js/bootstrap-material-datetimepicker.js"></script>
<script type="text/javascript" src="js/daterangepicker.min.js"></script>
<script type="text/javascript" src="js/action.js"></script>
<script type="text/javascript" src="js/admin-action.js"></script>
<script type="text/javascript" src="js/ajax-actions.js"></script>
<script type="text/javascript" src="js/initializer.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>
<script type="text/javascript" src="js/WorkPermit-Action.js"></script>
<script type="text/javascript" src="js/Fields-Action.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/Validation.js"></script>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/FileSaver.js"></script>
<script type="text/javascript" src="js/populatejs.min.js"></script>
populatejs.min
<script type="text/javascript">
    var loggedinuserEmailURI = "";

    $(document).ready(function () {
        $("input").attr("maxlength", 50);
        $("textarea").attr("maxlength", 1000);
        $("#sidebar").mCustomScrollbar({
            theme: "minimal"
        });

        $('#dismiss, .overlay').on('click', function () {
            $('#sidebar').removeClass('active');
            $('.overlay').removeClass('active');
        });

        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').addClass('active');
            $('.overlay').addClass('active');
            $('.collapse.in').toggleClass('in');
            $('a[aria-expanded=true]').attr('aria-expanded', 'false');
        });

        $(".select-simple").select2({
            placeholder: "Select",
            allowClear: true,
            theme: "bootstrap",
            minimumResultsForSearch: Infinity,
        });
    });
    if (window.useremailid != null && window.useremailid != "") {
    } else {
        console.log("UserEmailid is null here :: " + window.useremailid);
    }

    function editUser(userId) {
        var selecttedUser = null;
        USER_INFO.forEach(function(user){
            if(user.id == userId){
                selecttedUser =  user;
                return;
            }
        })
        //update the user
        updateModal(selecttedUser);
        $('#userrole-modal').find('h5').html('Update User');
        $('#userrole-modal').modal('show');
        //show the modal


    }

    function deleteUser(userId) {

        $("#pleasewait").show();
        $.ajax({
            type: 'POST',
            url: '/delete/' + userId,
            async: false, data: {'userId': userId},
            datatype: 'JSON',
            success: function (data) {
                console.log(data);
                $("#pleasewait").hide();
                showNotification("Success!", "Deleted Succesfully",
                    "success");
                setTimeout(function(){
                    window.location.reload();
                }, 1000);
            },
            error: function (data) {
                $("#pleasewait").hide();
                showNotification("ERROR!", "Please Try Again Later",
                    "failure");
                return;

            }
        })
    }

    function updateModal(user) {
		cleanModal();
        $('#reqRoleSaveBtn').attr('data-uniqueId',user.id);
        var roles = user.roles;
        user.roles.forEach(function (role) {
            if (role === "SUPERADMIN") {
                $("[name = userrole-superAdmin]").prop("checked", true)
            }
            if (role === "APPROVER1") {
                $("[name = userrole-approver1]").prop("checked", true)
            }

            if (role === "APPROVER2") {
                $("[name = userrole-approver2]").prop("checked", true)
            }

            if (role === "APPROVER3") {
                $("[name = userrole-approver3]").prop("checked", true)
            }

            if (role === "CREATOR") {
                $("[name = userrole-creator]").prop("checked", true)
            }

            if (role === "PLANTMANAGER") {
                $("[name = userrole-plantManager]").prop("checked", true)
            }

        })
        populatejs({
            'userrole-userEmail': user.userEmail,
            "userrole-title": user.title,
            "userrole-firstName": user.firstName,
            "userrole-lastName": user.lastName,
            "userrole-userName": user.userName,
        });

        if (user.mentor) {
            $("[name = userrole-mentor]").prop("checked", true)
        }
        if (user.editor) {
            $("[name = userrole-editor]").prop("checked", true)
        }
        if (user.projectLead) {
            $("[name = userrole-projectLead]").prop("checked", true)
        }
        if (user.pillarLead) {
            $("[name = userrole-pillarLead]").prop("checked", true)
        }

        setPlant(user.plantName, user.plantId);
        setPillar(user.pillarName, user.pillarId);
    }


    function setPlant(name, id) {
    	if(name ==null){
			$("#select2-userrole-plantName-container").html("");
			$('#userrole-plantId').val("")
			$("#userrole-plantName").empty();

			return;
		}

        $("#select2-userrole-plantName-container").html(name);
        var option = $('<option/>', {
            value: name,
            selected: true
        }).html(name)

        $('#userrole-plantId').val(id)

        $("#userrole-plantName").append(option)
    }

    function setPillar(name, id) {
		if(name ==null){
			$("#select2-userrole-pillarName-container").html("");
			$('#userrole-pillarId').val("")
			$("#userrole-pillarName").empty();

			return;
		}

    	$("#select2-userrole-pillarName-container").html(name);
        var option = $('<option/>', {
            value: name,
            selected: true
        }).html(name)

        $('#userrole-pillarId').val(id)

        $("#userrole-pillarName").append(option)
    }

    function cleanModal() {
        $('#reqRoleSaveBtn').attr('data-uniqueId', '');
        $("[name = userrole-superAdmin]").prop("checked", false);
        $("[name = userrole-approver1]").prop("checked", false)
        $("[name = userrole-approver2]").prop("checked", false)
        $("[name = userrole-approver3]").prop("checked", false)
        $("[name = userrole-creator]").prop("checked", false)

        $("[name = userrole-plantManager]").prop("checked", false)

        populatejs({
            'userrole-userEmail': "",
            "userrole-title": "",
            "userrole-firstName": "",
            "userrole-lastName": "",
            "userrole-userName": "",
        });
        $("[name = userrole-mentor]").prop("checked", false)
        $("[name = userrole-editor]").prop("checked", false)
        $("[name = userrole-projectLead]").prop("checked", false)
        $("[name = userrole-pillarLead]").prop("checked", false)

        setPlant(null, null);
        setPillar(null, null);
    }

</script>


</body>

</html>
