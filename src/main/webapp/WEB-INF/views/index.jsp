<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page
	import = "com.entity.User"
	import ="org.codehaus.jackson.map.JsonMappingException"
	import = "org.codehaus.jackson.map.ObjectMapper"
	 %>
    	<%
    	  User objUser = (User)request.getAttribute("user");
    	 ObjectMapper 	 mapper 	= 		new ObjectMapper();
  	  	String userDetails = mapper.writeValueAsString(objUser);
	    String logegedInUserEmailId = (String)request.getAttribute("loggedinusersmailid");
	    System.out.println("the is logegedInUserEmailId is this :: "+logegedInUserEmailId);
	   
	 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MASTER DATA MANAGEMENT</title>

<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico">

<!-- Add to home screen for Safari on iOS -->


<!--Google Icon Font-->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"
	media="screen,projection" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<link type="text/css" rel="stylesheet" href="css/mdb.min.css"
	media="screen,projection" />
<link rel="stylesheet" type="text/css" href="css/datatables.min.css" />
<link rel="stylesheet" type="text/css" href="css/select2.min.css">
<link rel="stylesheet" type="text/css" href="css/select2-bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/pmd-select2.css">
<link type="text/css" rel="stylesheet" href="css/style.css"
	media="screen,projection" />
	
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	  <link rel="stylesheet" type="text/css" href="css/pnotify.custom.min.css">
	   <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
    <link href="css/bootstrap-material-datetimepicker.css" rel="stylesheet">
<script>
		var logegedInUserEmailId ='<%=logegedInUserEmailId%>';
		window.useremailid='<%=logegedInUserEmailId%>';
		var userDetails =<%=userDetails%>;
	
</script>
<body>

	<div class="loader-wrapper" id="pleasewait" style="display: none;">
		<div class="loader-content">
			<div class="global-spinner"></div>
		</div>
	</div>

	<div class="overlay"></div>
	<!-- Sidebar  -->
	<nav id="sidebar">
		<div id="dismiss">
			<i class="material-icons"> close </i>
		</div>

		<div class="sidebar-header">
			<h3>Bootstrap Sidebar</h3>
		</div>

		<ul class="list-unstyled components">
			<p>Dummy Heading</p>
			<li class="active"><a href="#homeSubmenu" data-toggle="collapse"
				aria-expanded="false">Home</a>
				<ul class="collapse list-unstyled" id="homeSubmenu">
					<li><a href="#">Home 1</a></li>
					<li><a href="#">Home 2</a></li>
					<li><a href="#">Home 3</a></li>
				</ul></li>
			<li><a href="#">About</a> <a href="#pageSubmenu"
				data-toggle="collapse" aria-expanded="false">Pages</a>
				<ul class="collapse list-unstyled" id="pageSubmenu">
					<li><a href="#">Page 1</a></li>
					<li><a href="#">Page 2</a></li>
					<li><a href="#">Page 3</a></li>
				</ul></li>
			<li><a href="#">Portfolio</a></li>
			<li><a href="#">Contact</a></li>
		</ul>

		<ul class="list-unstyled CTAs">
			<li><a
				href="https://bootstrapious.com/tutorial/files/sidebar.zip"
				class="download">Download source</a></li>
			<li><a href="https://bootstrapious.com/p/bootstrap-sidebar"
				class="article">Back to article</a></li>
		</ul>
	</nav>
	<!-- Header -->
	<nav id="header-nav" class="navbar navbar-toggleable-md navbar-dark"
		style="z-index: 1;">

		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarNav1"
			aria-controls="navbarNav1" aria-expanded="false"
			aria-label="Toggle navigation" style="display: none;">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div>
			<a id="sidebarCollapse" style="display: none;"> <i
				class="material-icons" style="font-size: 30px;"> menu </i>
			</a> <a class="navbar-brand header-logo" href=""> <img
				src="\images\pdcalogo.png" alt="pdca logo" height="39px"
				width="89px"> <strong class="applicationName ml-2">MASTER
					DATA MANAGEMENT</strong>
			</a>
		</div>

		<div class="collapse navbar-collapse" id="navbarNav1">
			<ul class="navbar-nav mr-auto"></ul>





			<div class="nav-link">
				Welcome
				<%=logegedInUserEmailId%></div>
			<div class="btn-group align-items-center">
				<div id="admingear" class="nav-link" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false" style="cursor: pointer;">
					<i class="material-icons"> settings </i>
				</div>

				<div class="dropdown-menu dropdown-menu-right">
					<h6 class="dropdown-header" id="settingsmenuheading">Admin Setting</h6>
					<!-- <a class="dropdown-item" href="#adminroles">User Management</a>
                	<a class="dropdown-item" href="#adminbghsemanager">BG HSE Manager</a>
                	<a class="dropdown-item" href="#managesiteowner">Site Owner</a> -->
					<a class="dropdown-item" id="userRole-Item" style="display:none" href="#userrole">User Role</a>
					 <a class="dropdown-item" id="plant-Item" style="display:none" href="#plant">Plant</a>
					  <a class="dropdown-item" id="masterConfiguration-Item" style="display:none" href="#masterConfiguration">Master Configuration</a>
					<!-- <div class="dropdown-divider"></div>
					<h6 class="dropdown-header">Master Data</h6>
					<a class="dropdown-item" href="#adminworkpermittype">Permit
						Type</a> <a class="dropdown-item" href="#adminprotection">Personal
						Protection</a> <a class="dropdown-item" href="#adminworkflowcongig">Workflow
						Configuration</a> <a class="dropdown-item"
						href="#variableconfigforsite">Variable Configuration for Site</a>
					<a class="dropdown-item" href="#adminhelpinstruction">Help
						Instruction</a> -->
					<!-- <div class="dropdown-divider"></div>
					<a class="dropdown-item" href="#">Help portal</a> <a
						class="dropdown-item" href="#">Help center</a>
				</div> -->
			</div>
		</div>
	</nav>

	<!-- Menu -->
	<nav class="navbarMenu" id="main-hmenu" style="display: none;">
		<ul class="menu">
			<li class="nav-item"><a href="/" class="nav-link colorMenu"><i
					class="fa fa-home" aria-hidden="true" style="font-size: 20px;"></i></a>
			</li>
			<li class="nav-item"><a data-target="#chooseworktype-modal"
				data-toggle="modal" id="" style="display: none;" class="nav-link colorMenu createPDCA">Create PDCA</a></li>
			
			<li class="nav-item"><a href="#viewPDCA"
			id="" style="display: none;" class="nav-link colorMenu viewPDCA">View PDCA</a></li>

			<li class="nav-item"><a href="#myAction" id="" style="display: none;" 	class="nav-link colorMenu myAction">My Action</a></li>

		</ul>
	</nav>
	<main class="content-wrapper" style="min-height: calc(100vh - 131px);">
	<section id="home-section" style="padding-top: 50px; display: none">
		<div class="homeBanner-container">
			<div class="homeBanner-image"></div>
			<div class="homeBanner-overlay"></div>
		</div>
		<div class="col text-center">
			<h1 class="homeText" style="color: #fff;">
				Welcome to <span class="h1HomeColor">Master Data Management</span>
			</h1>
		</div>
		<div class="allCards">
			<a data-target="#chooseworktype-modal" data-toggle="modal"
				class="card card-cascade narrower align myCard createPDCA" style="display:none">
				<div class="roundCardHome">
					<i class="material-icons">add</i>
				</div>
				<div class="card-block">
					<h4>Create PDCA</h4>
				</div>
			</a> <a href="#viewPDCA" class="card card-cascade narrower align myCard viewPDCA" style="display:none">
				<div class="roundCardHome">
					<i class="material-icons"> visibility </i>
				</div>
				<div class="card-block">
					<h4>View PDCA</h4>
				</div>
			</a> <a href="#myAction" class="card card-cascade narrower align myCard myAction" style="display:none">
				<div class="roundCardHome">
					<i class="material-icons"> person </i>
				</div>
				<div class="card-block">
					<h4>My Action</h4>
				</div>
			</a>

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
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Plant Code</th>
							<th>Plant Name</th>
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
								<input data-required="yes" data-type="text" type="text" class="form-control"name="plant-plantCode" id="plant-plantCode">
							</div>
							<div class="form-group col-md-6 cus-formgroup-sm" id="">
								<label class="">Plant Name <span class="text-danger">*</span></label>
								<input  data-required="yes" data-type="text" type="text" class="form-control"name="plant-plantName" id="plant-plantName">
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
	
	<section id="masterConfiguration" style="padding-top: 50px; display: none">
		<div class="container-fluid">
			<h2 class="main-title">Master Configuration</h2>
			<div>
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
								<input data-required="yes" data-type="text" type="text" class="form-control"name="masterConfiguration-value" id="masterConfiguration-value">
							</div>
							
								<!-- <input data-required="yes" data-type="text" type="text" class="form-control"name="masterConfiguration-type" id="masterConfiguration-type"> -->
								<div class="form-group col-md-6 cus-formgroup-sm">
								<label class="">Type</label>
								<div>
									<select data-required="yes" theme="bootstrap" data-type="text" type="text" class="form-control"name="masterConfiguration-type" id="masterConfiguration-type" class="js-data-example-ajax"
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
						    <label >Plant Name</label>
						      <select  data-required="yes" data-type="text" id="masterConfiguration-plantName" name="masterConfiguration-plantName" class="js-data-example-ajax autocompleteforxcnreportssearch"  style="width: 100%">
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
		
		
	</section>
<section id="workpermitform" style="display:none;">
		<div class="d-flex justify-content-end title-header" style="margin-top:5px;margin-bottom: 5px;background: #dddddd;padding: 5px 10px;">
					<button class="btn btn-link btn-sm" id="backfromworkpermit-btn" style="margin: 0;box-shadow: none;cursor: pointer;">
						<span><i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Return"></i> </span>
					</button>
					<h2 class="custom-title mr-auto align-self-center" id="workpermithead">
	              
					</h2>
					<div class="pull-left polivalance-count customstatus-design" style="font-size:12px;font-weight:bold;background: #fff;padding: 4px;">
						<span>Request ID :<i id="reqid_label"></i></span>
						<span>Status : <i id="status_label"></i></span>
						<span>Plant : <i id="plant_label"></i></span>
						<span>Created On: <i id="created_on_label"></i></span>
						<span>Created By: <i id="created_by_label"></i></span>
					</div>
		</div>
		<div class="container-fluid" id="createpermit-container">
			<!-- <h2 class="main-title">Create Work At Height</h2> -->
			<div class="d-flex justify-content-end fixme"  id="workpermit-btndiv" style="margin-bottom: 5px;">
				<div class="mr-auto align-self-center custom-actionbtn">
					<button class="btn btn-default btn-sm waves-effect waves-light" id="savepdca-btn" style="margin: 0 ;margin-right:6px;">
						<span><i class="fa fa-save" aria-hidden="true"></i> Save</span>
					</button>
					<button class="btn btn-default btn-sm waves-effect waves-light " id="submitpdca-btn" style="margin: 0 ;">
						<span><i class="fa fa-send-o" aria-hidden="true"></i> Submit</span>
					</button>
					
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
					<%-- <button class="btn btn-warning btn-sm waves-effect waves-light" id="demotetoapprover-btn" style="margin: 0 ;">
						<span><i class="fa fa-info-circle" aria-hidden="true"></i> <spring:message code="4_Demote"/></span>
					</button> --%>
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
				</div>
			</div>
			<div class="alert alert-danger" role="alert" style="padding: 8px 15px;display:none;" id="demotecommentsdiv">
			    <h6 class="alert-heading">Demote Comments</h6>
			    <p class="mb-0" id="demoteCommentstext">A simple danger alert—check it out!</p>
			</div>
			<div class="alert alert-danger" role="alert" style="padding: 8px 15px;display:none;" id="rejectcommentsdiv">
			    <h6 class="alert-heading">Reject Comments</h6>
			    <p class="mb-0" id="rejectCommentstext">A simple danger alert—check it out!</p>
			</div>
			<div id="createpermit-content" style="margin-top: 7px;">
				<div class="workatheight-form" id="workpermit-form" style="display:none;">
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-file-text-o" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Basic_Detail"/></h5>
						<blockquote class="message x_content customcol-4">
							<div class="form-group">
						        <label class="control-label"><spring:message code="4_ReqId"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Status"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Reference"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Date"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						</blockquote>
					</div>
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-user" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Identification"/></h5>
						<blockquote class="message x_content customcol-4">
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Requester"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Email_Requester"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Phone_number"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Responsible_of_work_site"/></label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						 </blockquote>
					</div>
				    
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-street-view" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Location_and_dates"/></h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label"><spring:message code="4_Site_Location"/></label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Plan_attached"/></label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="workatheightattach" id="workatheightattachYes-radioBtnYes" type="radio" value="Yes"> <label for="workatheightattachYes-radioBtnYes"><spring:message code="4_Yes"/></label>
									</fieldset>
									<fieldset class="form-group">
										<input name="workatheightattach" id="workatheightattachYes-radioBtnNo" value="No" type="radio"> <label for="workatheightattachYes-radioBtnNo"><spring:message code="4_No"/> </label>
									</fieldset>
								</div>
						    </div>
						    <div class="form-group" id="workpermittypeattachement" style="display:none">
						    	<button class="btn btn-default btn-sm" style="margin: 0 0 10px ;" data-toggle="modal" data-target="#attach-modal">
									<span><i class="fa fa-paperclip" aria-hidden="true"></i> <spring:message code="4_Add_Attachment"/></span>
								</button>
								<button class="btn btn-default btn-sm" style="margin: 0 0 10px ;" data-toggle="modal" data-target="#link-modal">
									<span><i class="fa fa-link" aria-hidden="true"></i> <spring:message code="4_Add_Link"/></span>
								</button>
								<div style="min-height:57px;max-height: 76px;border-bottom: 1px solid #ccc;overflow: auto;">
									<ul class="list-unstyled attachdesign">
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
									</ul>
								</div>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Area</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="customcol-4">
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_Planned_start_date"/><span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="<spring:message code="4_Planned_start_date_Info"/>">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_Provisional_end_of_work"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="<spring:message code="4_Provisional_end_of_work_Info"/>">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_From"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="<spring:message code="4_From_Info"/>">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_To"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="<spring:message code="4_To_Info"/>">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
						    </div>
						</blockquote>
					</div>
				    
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-universal-access" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Prevention_and_consigments"/></h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label"><spring:message code="4_Associated_previention_plan"/></label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="workatheightplan" id="workatheightplanYes" type="radio" value="Yes"> <label for="workatheightplanYes"><spring:message code="4_Yes"/></label>
									</fieldset>
									<fieldset class="form-group">
										<input name="workatheightplan" id="workatheightplanNo" value="No" type="radio"> <label for="workatheightplanNo"><spring:message code="4_No"/></label>
									</fieldset>
								</div>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Reference"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Associated_consignements"/></label>
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigElectric" class="filled-in" value="Electric">
							        	<label for="consigElectric" style="padding-left: 30px;"><spring:message code="4_Electric"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigGas" class="filled-in" value="Gas">
							        	<label for="consigGas" style="padding-left: 30px;"><spring:message code="4_Gas"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigDrifge" class="filled-in" value="Drifge">
							        	<label for="consigDrifge" style="padding-left: 30px;"><spring:message code="4_Drifge"/></label>
							        </fieldset>
						        </div>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Reference"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						</blockquote>
					</div>
				    <div class="x_panel">
					    <h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-briefcase" aria-hidden="true" style="margin-right:5px;"></span><spring:message code="4_Work_description"/></h5>
					    <blockquote class="message x_content">
					    	<div class="form-group">
						        <label class="control-label"><spring:message code="4_Nature_of_work"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Company"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_List_of_workers_involved"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_List_of_workers_involved_Info_1"/>)
<br>(<spring:message code="4_List_of_workers_involved_Info_2"/>)">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Means_of_collective_and_perosnal_protection_required"/><span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_Means_of_collective_Info"/>)">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
								<input type="text" name="" placeholder="" class="form-control" style="margin-bottom:10px;">
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionSafetyshoes" class="filled-in" value="Safetyshoes">
							        	<label for="protectionSafetyshoes" style="padding-left: 30px;"><spring:message code="4_Safety_shoes"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionHelmet" class="filled-in" value="Helmetwithchinstrap">
							        	<label for="protectionHelmet" style="padding-left: 30px;"><spring:message code="4_Helmet_with_chin_strap"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionWorkoveralls" class="filled-in" value="Workoveralls">
							        	<label for="protectionWorkoveralls" style="padding-left: 30px;"><spring:message code="4_Work_overalls"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionSafetyharness" class="filled-in" value="Safetyharness">
							        	<label for="protectionSafetyharness" style="padding-left: 30px;"><spring:message code="4_Safety_harness"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionReflectivevest" class="filled-in" value="Reflectivevest">
							        	<label for="protectionReflectivevest" style="padding-left: 30px;"><spring:message code="4_Reflective_vest"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionEquipmentformarkingout" class="filled-in" value="Equipmentformarkingout">
							        	<label for="protectionEquipmentformarkingout" style="padding-left: 30px;"><spring:message code="4_Equipment_for_marking_out"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionLadderorstepladder" class="filled-in" value="Ladderorstepladder">
							        	<label for="protectionLadderorstepladder" style="padding-left: 30px;"><spring:message code="4_Ladder_or_stepladder"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionAeriallift" class="filled-in" value="Aeriallift">
							        	<label for="protectionAeriallift" style="padding-left: 30px;"><spring:message code="4_Aerial_lift"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protection" id="protectionLifeline" class="filled-in" value="Lifeline">
							        	<label for="protectionLifeline" style="padding-left: 30px;"><spring:message code="4_Lifeline"/></label>
							        </fieldset>
							     </div>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Safety_instructions_to_comply_with"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Specific_measure"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Emergency_numbers"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Other_documents_associated_with_this_permit"/></label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label"><spring:message code="4_Evidence_of_the_safety_measures"/></label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
				    	</blockquote>
				    </div>
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-pencil-square-o" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Date_and_visa"/></h5>
				    	<blockquote class="message x_content">
				    		<div class="customcol-3">
					    		<div class="form-group">
							        <label class="control-label"><spring:message code="4_Date"/></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_Signature_of_Valeo_approver"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_Signature_of_Valeo_approver_Info"/>)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light signpopup-btn" style="margin: 0 0 10px ;"  id="workatheight-approver-signpopup-btn">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="4_Add_Signature"/></span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="workatheight-approver-sign-contentimage">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id=""/>
										</div>
									</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
							    </div>
							    <div class="form-group">
							        <label class="control-label"><spring:message code="4_Signature_of_the_contractor"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_Signature_of_the_contractor_Info"/>)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light signpopup-btn" style="margin: 0 0 10px ;"  id="workatheight-contractor-signpopup-btn">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="4_Add_Signature"/></span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="workatheight-contractor-sign-contentimage">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id=""/>
										</div>
									</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
							    </div>
						    </div>
				    	</blockquote>
				    </div>
				    
				    <div class="x_panel" style="display:none;">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-clock-o" aria-hidden="true" style="margin-right:5px;"></span> Monitoring of work</h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label">Carried out by</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">On</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">Observation</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Signature</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						</blockquote>
					</div>
				    
				    
				</div>
				<!-- workonenergizedunits -->
				<div class="workonenergizedunits" style="display:none">
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-file-text-o" aria-hidden="true" style="margin-right:5px;"></span> Basic Detail</h5>
						<blockquote class="message x_content customcol-4">
							<div class="form-group">
						        <label class="control-label">ReqId</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">Status</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">Reference</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">Date</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						</blockquote>
					</div>
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-user" aria-hidden="true" style="margin-right:5px;"></span> Identification</h5>
						<blockquote class="message x_content customcol-4">
						    <div class="form-group">
						        <label class="control-label">Requester</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">Email Requester</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">phone number</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						    <div class="form-group">
						        <label class="control-label">Responsible of work (site)</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						 </blockquote>
					</div>
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-street-view" aria-hidden="true" style="margin-right:5px;"></span> Location and dates</h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label">site location</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">Plan attached</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="energizedunitsattach" id="energizedunitsattachYes" type="radio" value="Yes"> <label for="energizedunitsattachYes">Yes</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="energizedunitsattach" id="energizedunitsattachNo" value="No" type="radio"> <label for="energizedunitsattachNo">No </label>
									</fieldset>
								</div>
						    </div>
						    <div class="form-group">
						    	<button class="btn btn-default btn-sm" style="margin: 0 0 10px ;" data-toggle="modal" data-target="#attach-modal">
									<span><i class="fa fa-paperclip" aria-hidden="true"></i> Add Attachment</span>
								</button>
								
								<div style="min-height:57px;max-height: 76px;border-bottom: 1px solid #ccc;overflow: auto;">
									<ul class="list-unstyled attachdesign">
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
									</ul>
								</div>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Area</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="customcol-4">
							    <div class="form-group">
							        <label class="control-label">Planned start date <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label">Provisional end of work <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label">From <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
							    <div class="form-group">
							        <label class="control-label">To <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							        <input type="text" name="" placeholder="" class="form-control">
							    </div>
						    </div>
						</blockquote>
					</div>
					<div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-universal-access" aria-hidden="true" style="margin-right:5px;"></span> Prevention and consigments</h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label">Associated previention plan</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="workatheightplan" id="workatheightplanYes" type="radio" value="Yes"> <label for="workatheightplanYes">Yes</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="workatheightplan" id="workatheightplanNo" value="No" type="radio"> <label for="workatheightplanNo">No</label>
									</fieldset>
								</div>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Reference</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Associated consignements</label>
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigElectric" class="filled-in" value="Electric">
							        	<label for="consigElectric" style="padding-left: 30px;">Electric</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigGas" class="filled-in" value="Gas">
							        	<label for="consigGas" style="padding-left: 30px;">Gas</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="consignements" id="consigDrifge" class="filled-in" value="Drifge">
							        	<label for="consigDrifge" style="padding-left: 30px;">Drifge</label>
							        </fieldset>
						        </div>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Reference</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						</blockquote>
					</div>
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-briefcase" aria-hidden="true" style="margin-right:5px;"></span> Work description</h5>
						<blockquote class="message x_content">
							<div class="form-group">
							    <label class="control-label">Type of work to be performed</label>
							    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
							</div>
							<div class="form-group">
							    <label class="control-label">Company</label>
							    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
							</div>
							<div class="form-group">
							    <label class="control-label">List of interveners <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="where appropriate name of <br>suncontracting companies">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
							</div>
							<div class="form-group">
							    <label class="control-label">Means of collective and personal protection required <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="specify who is the responsible for the provision, in the absence of a particular mention it is the responsibility of the employer of each worker for PPE and of the site for other mean">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
							    <input type="text" name="" placeholder="" class="form-control" style="margin-bottom:10px;">
							    <div class="customcol-4">
							    	<fieldset class="form-check">
							        	<input type="checkbox" name="protectionergizedunits" id="protectionInsulatingsafetyshoes" class="filled-in" value="Insulatingsafetyshoes">
							        	<label for="protectionInsulatingsafetyshoes" style="padding-left: 30px;">Insulating safety shoes</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionergizedunits" id="protectionnometalparts" class="filled-in" value="Overallswithnometalparts">
							        	<label for="protectionnometalparts" style="padding-left: 30px;">Overalls with no metal parts</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionergizedunits" id="protectiontrapandeyeshade" class="filled-in" value="Helmetwithchinstrapandeyeshade">
							        	<label for="protectiontrapandeyeshade" style="padding-left: 30px;">Helmet with chin strap and eyeshade</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionergizedunits" id="protectionmarkuypmeans" class="filled-in" value="markuypmeans">
							        	<label for="protectionmarkuypmeans" style="padding-left: 30px;">Markuyp means</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionergizedunits" id="protectioninsulatingmatorstool" class="filled-in" value="insulatingmatorstool">
							        	<label for="protectioninsulatingmatorstool" style="padding-left: 30px;">Insulating mat or stool</label>
							        </fieldset>
							    </div>
							</div>
							<div class="form-group">
							    <label class="control-label">Safety rules to comply with</label>
							    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
							</div>
							<div class="form-group">
							    <label class="control-label">Intervention procedure</label>
							    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
							</div>
							<div class="form-group">
							    <label class="control-label">Evidence of the safety measures (taken photos)</label>
							    <input type="text" name="" placeholder="" class="form-control">
							</div>
							
						</blockquote>
					</div>
					<div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-pencil-square-o" aria-hidden="true" style="margin-right:5px;"></span> Date and visa</h5>
				    	<blockquote class="message x_content">
				    		<div class="form-group">
						        <label class="control-label">Date</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">Signature of Valeo approver <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(person who authorize the work)">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">Signature of the contractor <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(Responsable of the operator)">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
				    	</blockquote>
				    </div>
				    
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-clock-o" aria-hidden="true" style="margin-right:5px;"></span> Monitoring of work</h5>
						<blockquote class="message x_content">
							<div class="form-group">
						        <label class="control-label">Carried out by</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">On</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						    <div class="form-group">
						        <label class="control-label">Observation</label>
						        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
						    </div>
						    <div class="form-group">
						        <label class="control-label">Signature</label>
						        <input type="text" name="" placeholder="" class="form-control">
						    </div>
						</blockquote>
					</div>
				</div>
				<!--  -->
				<div class="workfire-form" style="display:none">
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-file-text-o" aria-hidden="true" style="margin-right:5px;"></span> Basic Detail</h5>
						<blockquote class="message x_content customcol-4">
				    <div class="form-group">
						        <label class="control-label">ReqId</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">Status</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">Reference</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">Date</label>
						        <input type="text" name="" placeholder="" class="form-control" />
						    </div>
						</blockquote>
				    </div>
				    <div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-user" aria-hidden="true" style="margin-right:5px;"></span> Identification</h5>
						<blockquote class="message x_content customcol-4">
				    <div class="form-group">
				        <label class="control-label">Requester</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">Email Requester</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">phone number</label>
						        <input type="text" name="" placeholder="" class="form-control" />
				    </div>
				    <div class="form-group">
				        <label class="control-label">Person in charge of site supervision</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Email person responsible for site supervision</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
						 </blockquote>
					</div>
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-street-view" aria-hidden="true" style="margin-right:5px;"></span> Location and dates</h5>
						<blockquote class="message x_content customcol-4">
				    <div class="form-group">
				        <label class="control-label">site location</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
						        <label class="control-label">Area</label>
				        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
				    </div>
				    <div class="form-group">
				        <label class="control-label">Site map</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">When</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">from</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">to</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
						</blockquote>
					</div>
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-user" aria-hidden="true" style="margin-right:5px;"></span> Contact Identification</h5>
				    	<blockquote class="message x_content customcol-4">
				    <div class="form-group">
				        <label class="control-label">Contractor</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Person to inform</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Email person to inform</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Person in charge of site supervision</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Email person responsible for site supervision</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    	</blockquote>
				    </div>
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-briefcase" aria-hidden="true" style="margin-right:5px;"></span> Work description</h5>
				    	<blockquote class="message x_content">
				    <div class="form-group">
				        <label class="control-label">Kind of work</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="workfirekind" id="workfirekind-radioBtnYes" type="radio" value="Yes"> <label for="workfirekind-radioBtnYes">Yes</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="workfirekind" id="workfirekind-radioBtnNo" value="No" type="radio"> <label for="workfirekind-radioBtnNo">No </label>
									</fieldset>
				        </div>
				    </div>
				    <div class="form-group">
				        <label class="control-label">Energy used</label>
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="energyused" id="energyair" class="filled-in" value="Air">
							        	<label for="energyair" style="padding-left: 30px;">Air</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="energyused" id="energygas" class="filled-in" value="Gas">
							        	<label for="energygas" style="padding-left: 30px;">Gas</label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="energyused" id="energyelectric" class="filled-in" value="Electricity">
							        	<label for="energyelectric" style="padding-left: 30px;">Electricity</label>
							        </fieldset>
				        </div>
				        </div>
				    <div class="form-group">
				        <label class="control-label">Work leading to a risk</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="workrisk" id="workrisk-radioBtnYes" type="radio" value="Yes"> <label for="workrisk-radioBtnYes">Yes</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="workrisk" id="workrisk-radioBtnNo" value="No" type="radio"> <label for="workrisk-radioBtnNo">No </label>
									</fieldset>
				        </div>
				    </div>
				    <div class="form-group">
				        <label class="control-label">Other</label>
				        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
				    </div>
				    <div class="form-group">
				        <label class="control-label">realized by</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="realized" id="realizedvaleo" type="radio" value="Valeo"> <label for="realizedvaleo">Valeo</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="realized" id="realizedexternal" value="External Company" type="radio"> <label for="realizedexternal">External Company</label>
									</fieldset>
				        </div>
				    </div>
				    <div class="form-group">
				        <label class="control-label">company</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">name</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    	</blockquote>
				    </div>
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-info-circle" aria-hidden="true" style="margin-right:5px;"></span> Performance by:</h5>
				    	<blockquote class="message x_content customcol-4">
				    <div class="form-group">
				        <label class="control-label">Required precaution check list</label>
				        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
				    </div>
				    <div class="form-group">
				        <label class="control-label">Risk area</label>
				        <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
				    </div>
				    <div class="form-group">
				        <label class="control-label">Factory map</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Control (site)</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Control (name)</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
				        <label class="control-label">Control (signature)</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    	</blockquote>
				    </div>
				    
				    <div class="x_panel">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-pencil-square-o" aria-hidden="true" style="margin-right:5px;"></span> Date and visa</h5>
				    	<blockquote class="message x_content">
				    		<div class="customcol-3">
				    <div class="form-group">
				        <label class="control-label active">Date</label>
				        <input type="text" name="" placeholder="" class="form-control">
				    </div>
				    <div class="form-group">
							        <label class="control-label">Signature of Valeo approver <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(person who authorize the work)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light signpopup-btn" style="margin: 0 0 10px ;" id="signpopup-btn">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Add Signature</span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id="teste">
										</div>
									</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
				    </div>
				    <div class="form-group">
							        <label class="control-label">Signature of the contractor <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(Responsable of the operator)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light signpopup-btn" style="margin: 0 0 10px ;" id="signpopup-btn2">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Add Signature</span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id="teste2">
				    </div>
				</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
							    </div>
						    </div>
				    	</blockquote>
				    </div>
				</div>
				<!--  -->
				<div class="excavationspermit" style="display:none;">
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-file-text-o" aria-hidden="true" style="margin-right:5px;"></span> Basic Detail</h5>
						<blockquote class="message x_content customcol-4">
					<div class="form-group">
						        <label class="control-label active">ReqId</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">Status</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">Reference</label>
						        <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">Date</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
						</blockquote>
					</div>
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-user" aria-hidden="true" style="margin-right:5px;"></span> Identification</h5>
						<blockquote class="message x_content customcol-4">
					<div class="form-group">
						        <label class="control-label active">Requester</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">Email Requester</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">phone number</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
						        <label class="control-label active">Responsible of work (site)</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
						 </blockquote>
					</div>
					<div class="x_panel">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-street-view" aria-hidden="true" style="margin-right:5px;"></span> Location and dates</h5>
						<blockquote class="message x_content">
					<div class="form-group">
						        <label class="control-label active">site location</label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
					    <label class="control-label">Plan attached</label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="excavationattach" id="excavation-radioBtnYes" type="radio" value="Yes"> <label for="excavation-radioBtnYes">Yes</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="excavationattach" id="excavation-radioBtnNo" value="No" type="radio"> <label for="excavation-radioBtnNo">No </label>
									</fieldset>
					    </div>
					    </div>
						    <div class="form-group" id="workpermittypeattachement" style="display:none">
						    	<button class="btn btn-default btn-sm waves-effect waves-light" style="margin: 0 0 10px ;" data-toggle="modal" data-target="#attach-modal">
									<span><i class="fa fa-paperclip" aria-hidden="true"></i> Add Attachment</span>
								</button>
							
								<div style="min-height:57px;max-height: 76px;border-bottom: 1px solid #ccc;overflow: auto;">
									<ul class="list-unstyled attachdesign">
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
										<li>
											<a class="filedownld">Excavations Permit.txt</a>
											<span class="attchmentdelete">
												<i class="fa fa-times"></i>
											</span>
										</li>
									</ul>
					    </div>
					</div>
					<div class="form-group">
						        <label class="control-label">Area</label>
					  <textarea rows="5" name="" placeholder="" class="form-control"> </textarea>
					</div>
					<div class="form-group">
							        <label class="control-label">Planned start date <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
							        <label class="control-label">Provisional end of work <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
							        <label class="control-label">From <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="Info text">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
							        <label class="control-label"><spring:message code="4_To"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="<spring:message code="4_To_Info"/>">
							<i class="fa fa-info-circle" style="color: #ffc107;"></i>
						</span></label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
						</blockquote>
					</div>
					<div class="x_panel" style="display:none;">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa arrow-updown fa-chevron-down"></i><span class="fa fa-universal-access" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Prevention_and_consigments"/></h5>
						<blockquote class="message x_content" style="">
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Associated_previention_plan"/></label>
						        <div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="excavationplan" id="excavationplanYes" type="radio" value="Yes"> <label for="excavationplanYes"><spring:message code="4_Yes"/></label>
									</fieldset>
									<fieldset class="form-group">
										<input name="excavationplan" id="excavationplanNo" value="No" type="radio"> <label for="excavationplanNo"><spring:message code="4_No"/></label>
									</fieldset>
					    </div>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Reference"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Associated_consignements"/></label>
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="excavationconsignements" id="excavationconsigElectric" class="filled-in" value="Electric">
							        	<label for="excavationconsigElectric" style="padding-left: 30px;"><spring:message code="4_Electric"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="excavationconsignements" id="excavationconsigGas" class="filled-in" value="Gas">
							        	<label for="excavationconsigGas" style="padding-left: 30px;"><spring:message code="4_Gas"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="excavationconsignements" id="excavationconsigDrifge" class="filled-in" value="Drifge">
							        	<label for="excavationconsigDrifge" style="padding-left: 30px;"><spring:message code="4_Drifge"/></label>
							        </fieldset>
					    </div>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Reference"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
						</blockquote>
					</div>
					<div class="x_panel" style="display:none;">
						<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-briefcase" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Work_description"/></h5>
						<blockquote class="message x_content">
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Location_of_excavation"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Details_of_work"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Tool_to_be_used"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_List_of_interveners_workers"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Supervisor_name"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Means_of_collective_and_perosnal_protection_required"/></label>
							    <input type="text" name="" placeholder="" class="form-control" style="margin-bottom:10px;">
							    <div class="customcol-4">
							    	<fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconsafetyshoes" class="filled-in" value="Insulatingsafetyshoes">
							        	<label for="protectionexcavationconsafetyshoes" style="padding-left: 30px;"><spring:message code="4_Insulating_safety_shoes"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconnometalparts" class="filled-in" value="Overallswithnometalparts">
							        	<label for="protectionexcavationconnometalparts" style="padding-left: 30px;"><spring:message code="4_Overalls_with_no_metal_parts"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationcontrapandeyeshade" class="filled-in" value="Helmetwithchinstrapandeyeshade">
							        	<label for="protectionexcavationcontrapandeyeshade" style="padding-left: 30px;"><spring:message code="4_Helmet_with_chin_strap_and_eyeshade"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconmarkuypmeans" class="filled-in" value="markuypmeans">
							        	<label for="protectionexcavationconmarkuypmeans" style="padding-left: 30px;"><spring:message code="4_Markuyp_means"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconsulatingmatorstool" class="filled-in" value="insulatingmatorstool">
							        	<label for="protectionexcavationconsulatingmatorstool" style="padding-left: 30px;"><spring:message code="4_Insulating_mat_or_stool"/></label>
							        </fieldset>
					    </div>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Safety_rules_to_comply_with"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Intervention_procedure"/></label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Precaution_to_be_taken"/></label>
						        <div class="customcol-4">
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationdamage" class="filled-in" value="">
							        	<label for="protectionexcavationdamage" style="padding-left: 30px;"><spring:message code="4_Risk_of_damage_to_underground_services"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationtrees" class="filled-in" value="">
							        	<label for="protectionexcavationtrees" style="padding-left: 30px;"><spring:message code="4_Damage_to_trees"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconsafetyshoes" class="filled-in" value="">
							        	<label for="protectionexcavation" style="padding-left: 30px;"><spring:message code="4_Collapse_of_trench"/></label>
							        </fieldset>
								   <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconsafetyshoes" class="filled-in" value="">
							        	<label for="protectionexcavation" style="padding-left: 30px;"><spring:message code="4_Persons_objects_falling_into_trench"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationconsafetyshoes" class="filled-in" value="">
							        	<label for="protectionexcavation" style="padding-left: 30px;"><spring:message code="4_Underground_Electrical_wire"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="protectionexcavationcon" id="protectionexcavationcable" class="filled-in" value="">
							        	<label for="protectionexcavationcable" style="padding-left: 30px;"><spring:message code="4_Underground_IT_Cable"/></label>
							        </fieldset>
					    </div>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Evidence_of_the_safety_measures"/></label>
					    <input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Requirements_within"/></label>
							    <input type="text" name="" placeholder="" class="form-control">
							    	<fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Ignitable_liquid"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Explosive_atmosphere_in_area_eliminated"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Floors_swept_clean"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Combustible_floors"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Remove_other_combustible_material"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_All_wall_and_floor_openings_covered"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_FM_Approved_welding_pads"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Protect_or_shut_down"/></label>
							        </fieldset>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Hot_work_on_walls"/></label>
							    <input type="text" name="" placeholder="" class="form-control">
							    <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Enclosed_equipment_cleaned_of_all_combustible_material"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Containers_purged_of_ignitable_liquid_vapor"/></label>
							        </fieldset>
							        <fieldset class="form-check">
							        	<input type="checkbox" name="" id="" class="filled-in" value="">
							        	<label for="" style="padding-left: 30px;"><spring:message code="4_Pressurized_vessels_piping"/></label>
							        </fieldset>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Fire_watch_hot_work_area_monitoring"/></label>
							    <input type="text" name="" placeholder="" class="form-control">
							    <fieldset class="form-check">
						        	<input type="checkbox" name="" id="" class="filled-in" value="">
						        	<label for="" style="padding-left: 30px;"><spring:message code="4_Fire_watch_will_be_provided"/></label>
						        </fieldset>
						        <fieldset class="form-check">
						        	<input type="checkbox" name="" id="" class="filled-in" value="">
						        	<label for="" style="padding-left: 30px;"><spring:message code="4_Fire_watch_is_supplied"/></label>
						        </fieldset>
						        <fieldset class="form-check">
						        	<input type="checkbox" name="" id="" class="filled-in" value="">
						        	<label for="" style="padding-left: 30px;"><spring:message code="4_Fire_watch_is_trained"/></label>
						        </fieldset>
						        <fieldset class="form-check">
						        	<input type="checkbox" name="" id="" class="filled-in" value="">
						        	<label for="" style="padding-left: 30px;"><spring:message code="4_Fire_watch_may_be_required"/></label>
						        </fieldset>
						        <fieldset class="form-check">
						        	<input type="checkbox" name="" id="" class="filled-in" value="">
						        	<label for="" style="padding-left: 30px;"><spring:message code="4_Monitor_hot_work_area"/></label>
						        </fieldset>
					    </div>
					<div class="form-group">
					    <label class="control-label"><spring:message code="4_Other_precautions_taken"/>:</label>
					    <textarea rows="5" name="" placeholder="" class="form-control"></textarea>
					</div>
						</blockquote>
					</div>
					
					<div class="x_panel" style="display:none;">
				    	<h5 class="subtitle-bg collapse-link"><i class="fa fa-chevron-down arrow-updown"></i><span class="fa fa-pencil-square-o" aria-hidden="true" style="margin-right:5px;"></span> <spring:message code="4_Date_and_visa"/></h5>
				    	<blockquote class="message x_content">
				    		<div class="customcol-3">
					<div class="form-group">
						<label class="control-label"><spring:message code="4_Date"/></label>
						<input type="text" name="" placeholder="" class="form-control">
					</div>
					<div class="form-group">
							        <label class="control-label"><spring:message code="4_Signature_of_Valeo_approver"/><span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_Signature_of_Valeo_approver_Info"/>)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light" style="margin: 0 0 10px ;" id="signpopup-btn">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="4_Add_Signature"/></span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id="teste">
					</div>
									</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
					</div>
					<div class="form-group">
							        <label class="control-label"><spring:message code="4_Signature_of_the_contractor"/> <span class="helpicon" data-container="body" data-toggle="popover" data-placement="top" data-content="(<spring:message code="4_Signature_of_the_contractor_Info"/>)">
								<i class="fa fa-info-circle" style="color: #ffc107;"></i>
							</span></label>
									<div>
										<button class="btn btn-default btn-sm waves-effect waves-light" style="margin: 0 0 10px ;" id="signpopup-btn2">
											<span><i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="4_Add_Signature"/></span>
										</button>
										<div class="sign-contentimage" style="display:none;" id="">
											<span class="closesign">
												<i class="fa fa-times"></i>
											</span>
											<img src="" id="teste2">
					</div>
					</div>
							       <!--  <input type="text" name="" placeholder="" class="form-control"> -->
			</div>
		  </div>
				    	</blockquote>
					</div>
					</div>
			</div>
		</div>
		
	<!-- work monitoring section -->	
	<div class="container-fluid" id="workmonitoring-wrapper">
		<div class="d-flex monitoringcol-wrapper">
		    <div class="monitoringcol-left">
		    	<h5 class="subtitle-bg"><span class="fa fa-file-text-o" aria-hidden="true" style="margin-right:5px;"></span> Work description</h5>
				<div style="padding: 10px;" class="monitoring-workdescription">
					<div class="form-group">
				    	<label class="control-label">Nature of work to be carried out</label>
				    	<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Company</label>
				    	<p>valeo india private limited</p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">List of workers involved </label>
				    	<p></p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Means of collective and perosnal protection required.</label>
				    	<p>Safety shoes ; Helmet with chin strap; Work overalls; Safety harness; Reflective vest; Equipment for marking out; Ladder or stepladder; Aerial lift ; Lifeline.</p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Safety instructions to comply with</label>
				    	<p>
				    		here the default text : General measures: In all cases of working at height you must:
							- mark out of area located under the work area
							- Prohibit all activitty under the area (unless otherwise granted by the safety department)
							- Check there is no risk of interaction 
							- Deny access to unauthorized persons,
							- In case of work interruption, secure and prevent access (locking access)
				    	</p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Specific measure</label>
				    	<p></p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Emergency numbers</label>
				    	<p>7989464132</p>    
					</div>
					<div class="form-group">
				    	<label class="control-label">Other documents associated with this permit</label>
				    	<p></p>    
					</div>
				</div>
		    </div>
		    <div class="monitoringcol-right">
		    	<h5 class="subtitle-bg collapse-link"><span class="fa fa-clock-o" aria-hidden="true" style="margin-right:5px;"></span> Monitoring of work</h5>
		    	<div style="margin-top: 10px;">
		    		<table class="table table-striped table-bordered nowrap" cellspacing="0" width="100%" id="monitorlist-table">
						<thead>
							<tr>
								<th style="width: 40px">
									<fieldset class="form-group">
										<input type="checkbox" name="select_all" class="filled-in"> <label for=""></label>
									</fieldset>
								</th>
								<th>Carried By</th>
								<th>Carried On</th>
								<th class="text-center action-col" style="width: 100px"><spring:message code="5_Actions"/></th>
							</tr>
						</thead>
						<tbody>
							 <tr>
								<td>
									<fieldset class="form-group">
										<input type="checkbox" name="select_all" class="filled-in"> <label for=""></label>
									</fieldset>
								</td>
								<td>nickjosegcp@gmail.com</td>
								<td>13/02/2019</td>
								<td class="action-col">
									<a class="z-depth-1"><i class="fa fa-pencil"></i></a>
									<a class="z-depth-1 "><i class="fa fa-trash"></i></a>
								</td>
							</tr> 
						</tbody>
					</table>
		    	</div>
		    	<div style="padding: 10px;border: 2px solid #eafbd1;margin: 15px;" id="workmontoring-form">
		    		<div class="row">
		    		<div class="form-group col-md-6">
					    <label class="control-label">Carried out by</label>
					    <input type="text" data-required="yes" data-type="text" class="form-control" id="carriedby_workmonitor" disabled>
					</div>
					<div class="form-group col-md-6">
					    <label class="control-label">Carried on</label>
					    <input type="text" data-required="yes" data-type="text" class="form-control" id="carriedon_workmonitor">
					</div>
		    		<div class="form-group col-md-12">
						<%-- <label class=""><spring:message code="5_Role_type"/> <span class="text-danger">*</span></label> --%>
						<div class="form-inline" id="workmonitor_comply">
							<fieldset class="form-group">
								<input name="comply" data-required="yes" data-type="text" id="radiocomply" type="radio" value="Compliant"> <label for="radiocomply"><spring:message code="20_Compliant"/></label>
							</fieldset>
							<fieldset class="form-group">
								<input name="comply" data-required="yes" data-type="text" id="radiononcomply" value="Not Compliant"
									type="radio"> <label for="radiononcomply"><spring:message code="20_Not_Compliant"/> </label>
							</fieldset>
						</div>
					</div>
		    		<div class="form-group col-md-12">
		    			<label class="control-label">Evidence of the safety measures (taken photos) <span class="text-danger">*</span></label>
		    			<div style="position:relative;">
		    				<input class="imagecapture-txt" type="text" placeholder="Click here to Browse...">
		    				<input type="file" name="files" id="" accept="image/*;capture=camera" onchange="previewFile('monitorEvidence', this)">
		    			</div>
		    			<div class="imagecapture" data-required="yes" data-type="text" id="monitorEvidence" data-fieldtype="CAPTURE" data-fieldname="Evidenceofthesafetymeasurestakenphotos" data-fieldid="monitorEvidence"></div>
		    		</div>
		    		<div class="form-group col-md-12">
					    <label class="control-label">Observation</label>
					    <textarea rows="5" name="" placeholder="" id="observation_workmonitor" class="form-control"></textarea>
					</div>
					<div class="form-group col-md-12">
					    <button id="save-workmonitoringbtn" class="btn btn-success btn-sm waves-effect waves-light" id="" style="margin: 0 ;">
							<span> Save</span>
						</button>
						<button class="btn btn-default btn-sm waves-effect waves-light workmonitoring-clear" id="" style="margin: 0 ;">
							<span> Clear</span>
						</button>
					</div>
					</div>
		    	</div>
		    </div>
		</div>
	
	</div>	
	
	
	
	<div class="modal fade" id="rejectpermit-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
	                <form id = "">
						<div class="form-group">
						    <label class="">Reject Comment</label>
						    <textarea class="form-control" id="rejectpermitComments"></textarea>
						</div>
	                </form>
	            </div>
	
	            <!--Footer-->
	            <div class="modal-footer justify-content-center">
	                <button type="submit" class="btn btn-success btn-sm" id="rejectpermitmodalbtn">Reject</button>
	                <button class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
	            </div>
	        </div>
	        <!--/.Content-->
	    </div>
	</div>
	</section>
	<section id="adminroles" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Admin Role</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="adminroles-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Mail Id</th>
							<th>Roles</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
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
		<!-- Reject WorkPermit Modal -->
	
	
		<div class="modal fade" id="adminroles-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Users</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="adminroles-form">
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label class="">Role Type <span class="text-danger">*</span></label>
								<div class="form-inline" id="">
									<fieldset class="form-group">
										<input name="role" id="radioadminrole" type="radio"
											value="Admin"> <label for="radioadminrole">Admin</label>
									</fieldset>
									<fieldset class="form-group">
										<input name="role" id="radioborole" value="BO" type="radio">
										<label for="radioborole">BO </label>
									</fieldset>
								</div>
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">User Mail Id <span class="text-danger">*</span></label>
								<input type="text" class="form-control" id="adminRolesMailId">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="adminRoleSaveBtn">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="workpermitform" style="display:none;">
		<div class="d-flex justify-content-end title-header" style="margin-top:5px;margin-bottom: 5px;background: #dddddd;padding: 5px 10px;">
					<button class="btn btn-link btn-sm" id="backfromworkpermit-btn" style="margin: 0;box-shadow: none;cursor: pointer;">
						<span><i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="top" title="Return"></i> </span>
					</button>
					<h2 class="custom-title mr-auto align-self-center" id="workpermithead">
	              
					</h2>
					<div class="pull-left polivalance-count customstatus-design" style="font-size:12px;font-weight:bold;background: #fff;padding: 4px;">
						<span><spring:message code="4_ReqId"/> :<i id="reqid_label"></i></span>
						<span><spring:message code="4_Status"/> : <i id="status_label"></i></span>
						<span><spring:message code="4_Reference"/> : <i id="ref_label"></i></span>
						<span><spring:message code="4_Date"/> : <i id="date_label"></i></span>
					</div>
		</div>
</section>
	<section id="bghsemanager" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">BG HSE Manager</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="bghsemanager-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Mail Id</th>
							<th>BG Code</th>
							<th>Sites</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
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
							<td>IS</td>
							<td></td>
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
		<div class="modal fade" id="bghsemanager-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create/Edit BG HSE Manager</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">User Mail Id <span class="text-danger">*</span></label>
								<input type="text" disabled id="useremail-bghseMgr"
									class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">BG Name <span class="text-danger">*</span></label>
								<input type="text" disabled id="bgname-bghseMgr"
									class="form-control" disabled>
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label class="">List of Sites</label>
								<div>
									<select id="adminlistofsites" class="js-data-example-ajax"
										multiple="multiple" style="width: 100%">
										<option value="one">List of Sites</option>
										<option value="one">Sites</option>
										<option value="one">Chennai</option>
										<option value="one">India</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id="bghsemgr-savebtn"
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="managesiteowner" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Site Owner</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="managesiteowner-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Site Owner Email</th>
							<th>Site Name</th>
							<th>Site Code</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
						</tr>
					</thead>
					<tbody>
						<!-- <tr>
								<td>
									<fieldset class="form-group">
										<input type="checkbox" name="select_all" class="filled-in"> <label for=""></label>
									</fieldset>
								</td>
								<td>nickjosegcp@gmail.com</td>
								<td>Chennai</td>
								<td>H22CHE7</td>
								<td>nickjosegcp@gmail.com</td>
								<td>13/02/2019</td>
								<td class="action-col">
									<a class="z-depth-1"><i class="fa fa-pencil"></i></a>
									<a class="z-depth-1 "><i class="fa fa-trash"></i></a>
								</td>
							</tr> -->
					</tbody>
				</table>
			</div>
		</div>
		<!-- adminroles-modal -->
		<div class="modal fade" id="managesiteowner-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Site Owner</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Site Owner EMail<span
									class="text-danger">*</span></label> <input type="text"
									class="form-control" id="siteowneremail">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Site Name <span class="text-danger">*</span></label>
								<input type="text" class="form-control" id="geositenametxt">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label class="">Site Code <span class="text-danger">*</span></label>
								<input type="text" class="form-control" disabled
									id="geositecodetxt">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="siteownersave-btn">Save</button>
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
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>ID</th>
							<th>Form Type</th>
							<th>Status</th>
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
	</section>
	<section id="myAction" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">PDCA List</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="myActiontable">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>ID</th>
							<th>Form Type</th>
							<th>Status</th>
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
	</section>
	<section id="userrole" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">User Role</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="userrole-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Mail Id</th>
							<th>Plant</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
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
										<input type="checkbox" name="userrole-superAdmin" id="userrole-superAdmin"
											class="filled-in" value="SUPERADMIN"> <label
											for="userrole-superAdmin" style="padding-left: 30px;">Super
											Admin</label>
									</div>
									<div class="form-group col-md-6">
										<input type="checkbox" name="userrole-approver1" id="userrole-approver"
											class="filled-in" value="APPROVER1"> <label
											for="userrole-approver" style="padding-left: 30px;">Approver1</label>
									</div>
									<div class="form-group col-md-6">
										<input type="checkbox" name="userrole-approver2" id="userrole-approver"
											class="filled-in" value="APPROVER2"> <label
											for="userrole-approver" style="padding-left: 30px;">Approver2</label>
									</div>
									<div class="form-group col-md-6">
										<input type="checkbox" name="userrole-approver3" id="userrole-approver"
											class="filled-in" value="APPROVER1"> <label
											for="userrole-approver" style="padding-left: 30px;">Approver3</label>
									</div>
								<div class="form-group col-md-6">
										<input type="checkbox" name="userrole-creator" id="userrole-creator"
											class="filled-in" value="CREATOR"> <label
											for="userrole-creator" style="padding-left: 30px;">Creator</label>
									</div>
								<div class="form-group col-md-6">
										<input type="checkbox" name="userrole-plantManager" id="userrole-plantManager"
											class="filled-in" value="PLANTMANAGER"> <label
											for="userrole-plantManager" style="padding-left: 30px;">Plant Manager</label>
									</div>
								</div>
							</div>

							<div class="form-group col-md-6 cus-formgroup-sm" id="">
								<label class="">User Mail Id <span class="text-danger">*</span></label>
								<input data-required="yes" data-type="email" type="text" class="form-control"name="userrole-userEmail" id="userrole-userEmail">
							</div>
							
							
							
							
							<div class="form-group col-md-6 cus-formgroup-sm">
								<label class="">Title</label>
								<div>
									<select data-required="yes" data-type="text" id="userrole-title" name="userrole-title" class="js-data-example-ajax"
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
								<input data-required="yes" data-type="text" id="userrole-firstName" name="userrole-firstName" type="text" class="form-control">
							</div>

							<div class="form-group col-md-6 cus-formgroup-sm" id="">
								<label class="">Last Name<span class="text-danger">*</span></label>
								<input data-required="yes" data-type="text" id="userrole-lastName" name="userrole-lastName" type="text" class="form-control">
							</div>

							<div class="form-group col-md-6 cus-formgroup-sm" id="">
								<label class="">User Name<span class="text-danger">*</span></label>
								<input data-required="yes" data-type="text" id="userrole-userName" name="userrole-userName" type="text" class="form-control">
							</div>

							 <div class="form-group col-md-6 cus-formgroup-sm" id="" style="display:none">
								<label class="">Pillar Id<span class="text-danger">*</span></label>
								<input data-required="yes" data-type="text" id="userrole-pillarId" name="userrole-pillarId" type="text" class="form-control">
							</div> 

							<!-- <div class="form-group col-md-6 cus-formgroup-sm" id="">
								<label class="">Pillar Name<span class="text-danger">*</span></label>
								<input id="userrole-pillarName" name="userrole-pillarName" type="text" class="form-control">
							</div> -->
							
							 <div class="form-group col-md-6">
						    <label >Pillar Name</label>
						      <select  data-required="yes" data-type="text" id="userrole-pillarName" name="userrole-pillarName" class="js-data-example-ajax autocompleteforxcnreportssearch"  style="width: 100%">
						     </select>
							</div>
								<div class="form-group col-md-6 cus-formgroup-sm" id="" style="display:none">
								<label class="">Plant Id<span class="text-danger">*</span></label>
								<input id="userrole-plantId" name="userrole-plantId" type="text" class="form-control">
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
						    <label >Plant Name</label>
						      <select data-required="yes" data-type="text" id="userrole-plantName" name="userrole-plantName" class="js-data-example-ajax autocompleteforxcnreportssearch"  style="width: 100%">
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
									for="userrole-pillarLead" style="padding-left: 30px;">Pillar Lead</label>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id="userrole-clear"
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="userrole-cancel">Cancel</a>
						<button class="btn btn-success btn-sm" id="reqRoleSaveBtn">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="adminpermittype" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Permit Type</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="adminpermittype-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Permit Name</th>
							<th>Permit Prefix</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
						</tr>
					</thead>
					<tbody>
						<!-- <tr>
								<td>
									<fieldset class="form-group">
										<input type="checkbox" name="select_all" class="filled-in"> <label for=""></label>
									</fieldset>
								</td>
								<td>Work at Height</td>
								<td>WH</td>
								<td>nickjosegcp@gmail.com</td>
								<td>13/02/2019</td>
								<td class="action-col">
									<a class="z-depth-1"><i class="fa fa-pencil"></i></a>
									<a class="z-depth-1 "><i class="fa fa-trash"></i></a>
								</td>
							</tr> -->
					</tbody>
				</table>
			</div>
		</div>
		<!-- adminroles-modal -->
		<div class="modal fade" id="adminpermittype-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Permit Type</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<div class="form-group col-md-12 cus-formgroup-sm"
								id="premitNameDiv">
								<label class="">Permit Name <span class="text-danger">*</span></label>
								<input type="text" class="form-control" id="permitNameTxt">
							</div>
						</form>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<div class="form-group col-md-12 cus-formgroup-sm"
								id="permitPrefixDiv">
								<label class="">Permit Prefix <span class="text-danger">*</span></label>
								<input type="text" class="form-control" id="permitPrefixTxt">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="adminpermittype-save">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section id="variableconfigforsite" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Variable Site Configuration</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="variableconfigforsite-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Site Name</th>
							<th>Site Code</th>
							<th>Minimum No Fields</th>
							<th>Authorize Field Types</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
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
							<td>{siteName}</td>
							<td>{siteCode}</td>
							<td>{minNoFields}</td>
							<td>{authorizedFieldType}</td>
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
		<div class="modal fade" id="variableconfigforsite-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Permit Type</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Site Name <span class="text-danger">*</span></label>
								<input id="siteName" type="text" class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Site Code<span class="text-danger">*</span></label>
								<input id="siteCode" type="text" class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">minNoFields<span class="text-danger">*</span></label>
								<input id="minNoFields" type="text" class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Authorized Field Types<span
									class="text-danger">*</span></label> <select id="authorizeFields"
									class="js-data-example-ajax" multiple="multiple"
									style="width: 100%" data-required="yes" data-type="text">
									<!--  <option value="one"> 1 </option>
										<option value="one">3 </option>
										<option value="one">6 </option>
										<option value="one">16</option> -->
								</select>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="">Cancel</a>
						<button class="btn btn-success btn-sm"
							id="variableConfigForSiteSaveBtn">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="adminprotection" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Personal Protection</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="adminprotection-table">
					<thead>
						<tr>
							<th style="width: 40px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Permit Type</th>
							<th>Value</th>
							<th>Created By</th>
							<th>Created On</th>
							<th class="text-center action-col" style="width: 100px">Actions</th>
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
							<td>Work at Height</td>
							<td>IS</td>
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
		<div class="modal fade" id="adminprotection-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Personal Protection</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<!--Simple select -->
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label>Permit type <span class="text-danger">*</span></label> <select
									id="permittype-protection"
									class="select-simple form-control pmd-select2"
									style="width: 100%;">
									<option></option>
									<option>Work at Height</option>
								</select>
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Value <span class="text-danger">*</span></label>
								<input type="text" id="protection" class="form-control">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id="protection-savebtn"
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="adminworkflowconfig" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Workflow Configuration</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="adminworkflowconfig-table">
					<thead>
						<tr>
							<th style="width: 50px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Sequence No</th>
							<th>WorkFlow Type</th>
							<th>Step Name</th>
							<th>Created By</th>
							<th>Created On</th>
							<th>Action</th>
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
							<td>2</td>
							<td>Invoice Booking</td>
							<td>Buyer SSC AP Invoice Validation</td>
							<td>srimathi.ravichandran@apps-test.valeo.com</td>
							<td>16/11/2018</td>
							<td class=" action-col"><a class="z-depth-1"><i
									class="fa fa-pencil"></i></a> <a class="z-depth-1"><i
									class="fa fa-trash"></i></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- adminroles-modal -->
		<div class="modal fade" id="adminworkflowconfig-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Personal Protection</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<!--Simple select -->
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label>WorkFlow type <span class="text-danger">*</span></label>
								<select class="select-simple form-control pmd-select2"
									style="width: 100%;">
									<option></option>
									<option>Work at Height</option>
								</select>
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">Step Name <span class="text-danger">*</span></label>
								<input type="text" class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">No <span class="text-danger">*</span></label> <input
									type="text" class="form-control">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section id="adminhelpinstruction" style="display: none;">
		<div class="container-fluid">
			<h2 class="main-title">Help Instructions</h2>
			<div>
				<table class="table table-striped table-bordered nowrap"
					cellspacing="0" width="100%" id="adminhelpinstruction-table">
					<thead>
						<tr>
							<th style="width: 50px">
								<fieldset class="form-group">
									<input type="checkbox" name="select_all" class="filled-in">
									<label for=""></label>
								</fieldset>
							</th>
							<th>Permit type</th>
							<th>File Name</th>
							<th>link</th>
							<th>Created By</th>
							<th>Created On</th>
							<th>Action</th>
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
							<td>2</td>
							<td>Invoice Booking</td>
							<td>Buyer SSC AP Invoice Validation</td>
							<td>srimathi.ravichandran@apps-test.valeo.com</td>
							<td>16/11/2018</td>
							<td class=" action-col"><a class="z-depth-1"><i
									class="fa fa-pencil"></i></a> <a class="z-depth-1"><i
									class="fa fa-trash"></i></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- adminroles-modal -->
		<div class="modal fade" id="adminhelpinstruction-modal">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="">Create Help Instructions</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="">
						<form class="row selfalign-bottom" id="">
							<!--Simple select -->
							<div class="form-group col-md-12 cus-formgroup-sm">
								<label>Permit type <span class="text-danger">*</span></label> <select
									class="select-simple form-control pmd-select2"
									style="width: 100%;">
									<option></option>
									<option>Work at Height</option>
								</select>
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">File Name <span class="text-danger">*</span></label>
								<input type="text" class="form-control">
							</div>
							<div class="form-group col-md-12 cus-formgroup-sm" id="">
								<label class="">File Link <span class="text-danger">*</span></label>
								<input type="text" class="form-control">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<a class="btn btn-default btn-sm" id=""
							style="margin-right: auto;">Clear</a> <a
							class="btn btn-default btn-sm" data-dismiss="modal" id="">Cancel</a>
						<button class="btn btn-success btn-sm" id="">Save</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	</main>
	<div class="modal fade" id="chooseworktype-modal">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="">Select Work Type</h5>
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
						          	<input id="workpermit-filename"  type="text" class="form-control" placeholder="Click here to Browse...">
				                  	<input id="workpermit-attachfile" type="file" name="files">
						          </div>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default btn-sm">Cancel</button>
					    <button disabled type="button" id="workpermitattach-uploadbtn" class="btn btn-success btn-sm">Upload</button>
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
						          	<input id="workpermit-linktxt" type="text" class="form-control" data-required="yes" data-type="text">
						          </div>
						          <div class="form-group" style="position:relative;">
						          	<label><spring:message code="16_Link"/></label>
						          	<input id="workpermit-link" type="text" class="form-control" data-required="yes" data-type="text">
						          </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default btn-sm" data-dismiss="modal"> <spring:message code="16_Cancel"/></button>
					    <button type="button" class="btn btn-success btn-sm" id="workpermit-addlinkbtn"><spring:message code="16_Add"/></button>
					</div>
			</form>
  	  </div>
	</div>
	</div>
	<div class="modal fade" id="signature-modal" style="display: none;"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Signature</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body" style="height: calc(100vh - 201px);">
					<div id="signature-pad" class="signature-pad">
						<div class="signature-pad--body">
							<canvas></canvas>
						</div>
						<div class="signature-pad--footer">
							<div class="description">Sign above</div>

							<div class="signature-pad--actions">
								<div>
									<button type="button" class="button clear" data-action="clear">Clear</button>
									<!-- <button type="button" class="button" data-action="change-color">Change color</button>
          <button type="button" class="button" data-action="undo">Undo</button> -->

								</div>
								<div>
									<button type="button" class="button save"
										data-action="save-png">Save</button>
									<!-- <button type="button" class="button save" data-action="save-jpg">Save as JPG</button>
          <button type="button" class="button save" data-action="save-svg">Save as SVG</button> -->
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- <div class="modal-footer">
						<button type="button" class="btn btn-default btn-sm">Cancel</button>
					    <button type="button" class="btn btn-success btn-sm">Add</button>
					</div> -->
			</div>
		</div>
	</div>
	<div class="modal fade" id="signature-modal2" style="display: none;"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Signature</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body" style="height: calc(100vh - 201px);">
					<div id="signature-pad1" class="signature-pad">
						<div class="signature-pad--body">
							<canvas></canvas>
						</div>
						<div class="signature-pad--footer">
							<div class="description">Sign above</div>

							<div class="signature-pad--actions">
								<div>
									<button type="button" class="button clear" data-action="clear">Clear</button>
									<!--  <button type="button" class="button" data-action="change-color">Change color</button>
          <button type="button" class="button" data-action="undo">Undo</button> -->

								</div>
								<div>
									<button type="button" class="button save"
										data-action="save-png1">Save</button>
									<!--     <button type="button" class="button save" data-action="save-jpg">Save as JPG</button>
          <button type="button" class="button save" data-action="save-svg">Save as SVG</button> -->
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- <div class="modal-footer">
						<button type="button" class="btn btn-default btn-sm">Cancel</button>
					    <button type="button" class="btn btn-success btn-sm">Add</button>
					</div> -->
			</div>
		</div>
	</div>
	<footer class="page-footer sticky-bottom"
		style="z-index: 1; position: relative;">
		<a href="" target="blank"> <img src="\images\pdcalogo.png"
			alt="MDM" class="logoFooter">
		</a>
		<div class="copyrights-text">© 2019</div>
	</footer>
	<!--Import jQuery before js files-->
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript"
		src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script type="text/javascript" src="js/tether.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/mdb.min.js"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
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

	<script type="text/javascript">
		var loggedinuserEmailURI = "";

		$(document).ready(function() {

			$("#sidebar").mCustomScrollbar({
				theme : "minimal"
			});

			$('#dismiss, .overlay').on('click', function() {
				$('#sidebar').removeClass('active');
				$('.overlay').removeClass('active');
			});

			$('#sidebarCollapse').on('click', function() {
				$('#sidebar').addClass('active');
				$('.overlay').addClass('active');
				$('.collapse.in').toggleClass('in');
				$('a[aria-expanded=true]').attr('aria-expanded', 'false');
			});

			$(".select-simple").select2({
				placeholder : "Select",
				allowClear : true,
				theme : "bootstrap",
				minimumResultsForSearch : Infinity,
			});
		});
		if (window.useremailid != null && window.useremailid != "") {
		} else {
			console.log("UserEmailid is null here :: " + window.useremailid);
		}
	</script>

</body>
</html>
