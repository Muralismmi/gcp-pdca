<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	 	
	    String contractdetailstoken = (String)request.getAttribute("token");
	    System.out.println("contractdetailstoken is  "+contractdetailstoken);
	   
	 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- <link rel="manifest" href="/manifest.json" /> -->
<title>ExternalPage</title>
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/bootstrap.min.css" media="screen,projection" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
    <link type="text/css" rel="stylesheet" href="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/mdb.min.css" media="screen,projection" />
    <link type="text/css" rel="stylesheet" href="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/custom.css" media="screen,projection" />
       <link rel="stylesheet" type="text/css" href=https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/pnotify.custom.min.css>
    <style type="text/css">
    /* Loader changes */
.loader-wrapper { position: fixed; background: rgba(0, 0, 0, 0.4); top: 0; bottom: 0; right: 0; left: 0; z-index: 1200; } .loader-content { position: absolute; top: 50%; left: 50%; margin-top: -25px; margin-left: -25px; padding: 12px 12px; background: #fff; border-radius: 100%; -webkit-box-shadow: 0 5px 15px rgba(0,0,0,.5); box-shadow: 0 5px 15px rgba(0,0,0,.5); } .global-spinner { width: 26px; height: 26px; border: solid 2px transparent; border-top-color: #82e600 ; border-left-color: #82e600 ; border-radius: 50%; -webkit-animation: pace-spinner 500ms linear infinite; -moz-animation: pace-spinner 500ms linear infinite; -ms-animation: pace-spinner 500ms linear infinite; -o-animation: pace-spinner 500ms linear infinite; animation: pace-spinner 500ms linear infinite; } @-webkit-keyframes pace-spinner { 0% { -webkit-transform: rotate(0deg); transform: rotate(0deg); } 100% { -webkit-transform: rotate(360deg); transform: rotate(360deg); } } @-moz-keyframes pace-spinner { 0% { -moz-transform: rotate(0deg); transform: rotate(0deg); } 100% { -moz-transform: rotate(360deg); transform: rotate(360deg); } } @-o-keyframes pace-spinner { 0% { -o-transform: rotate(0deg); transform: rotate(0deg); } 100% { -o-transform: rotate(360deg); transform: rotate(360deg); } } @-ms-keyframes pace-spinner { 0% { -ms-transform: rotate(0deg); transform: rotate(0deg); } 100% { -ms-transform: rotate(360deg); transform: rotate(360deg); } } @keyframes pace-spinner { 0% { transform: rotate(0deg); transform: rotate(0deg); } 100% { transform: rotate(360deg); transform: rotate(360deg); } }
    </style>
</head>
<body class="body-custom">
<!-- Loader -->
<div class="loader-wrapper" id="pleasewait"  style="display:none;">
        <div class="loader-content">
          <div class="global-spinner">
          </div>
      </div>
  </div>
<!--  -->
<section id="loginform">
<div class="d-flex" style="height: 100vh;    padding: 0 10px;" ><div class="logincard">
  	<div class="pmd-card card-default pmd-z-depth">
		<div class="login-card">
			<form>	
				<div class="pmd-card-title card-header-border text-center">
					<h1 class="loginlogo">
						<a><img src="\images\pdcalogo.png" alt="Propeller Logo" style="
    height: 40px;
"></a>
					</h1>
					<h3><span><strong>HSE Work Permit</strong></span></h3>
				</div>
				
				<div class="pmd-card-body">
					<div class="alert alert-success" role="alert"> Oh snap! Change a few things up and try submitting again. </div>
                    
                    
                    <div class="form-group pmd-textfield pmd-textfield-floating-label">
                        <label for="inputError1" class="control-label pmd-input-group-label">Password</label>
                        <div class="input-group">
                            <div class="input-group-addon"><i class="material-icons md-dark pmd-sm">lock_outline</i></div>
                            <input type="password" class="form-control" id="passwordinput"><span class="pmd-textfield-focused"></span>
                        </div>
                    </div>
                </div>
				<div class="pmd-card-footer card-footer-no-border card-footer-p16 text-center mb-3">
					<a class="btn pmd-ripple-effect btn-success btn-block waves-effect waves-light" id="loginbtn">Login</a>
				</div>
				
			</form>
		</div>
	</div>
    </div></div>
    </section>
    
    <div id="tableform" style="display:none;">
    		<div class="text-center" style="background: #fff;">
					<h1 class="loginlogo" style="padding: 10px 15px;display: flex;align-items: center;justify-content: center;flex-wrap: wrap;">
						<a><img src="\images\pdcalogo.png" alt="Propeller Logo" style="height: 30px;"></a>
					<span style="font-size: 25px;color: #888;margin-left: 10px;margin-right: 10px;"><strong>Master Data Management</strong></span></h1>
					
				</div>
				<div>
					<div class="wrapper text-center">
        <div class="webkit container" style="border: 1px solid #ddd;background: #fff;margin: 30px auto;padding: 15px;">
            
			<div style="background-color: #f7f7f7;">
                        <div class="three-column">
                            <h3 style="font-size: 20px;margin: 0;font-weight: 400;">HSE Work Permit Request</h3>
                        </div>
                    </div>
			<div id="detailsdiv" class="inner one-column">
						<!-- <div  class="d-flex columncustome" style="">
                                        <div class="inner" style="/* width: 100%; */">
                                            <p class="h1" style="margin-bottom: 0;align-self: center;">Alert Level </p>
                                        </div>
                                        <div class="inner">
                                            <p style="margin-bottom: 0;">$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level$alert_level</p>
                                        </div>
                      </div> -->
            </div>
			<div id="butns">
				<a id="reject"  class="btn pmd-ripple-effect btn-danger waves-effect waves-light">Reject</a>
				<a id="approve"  class="btn pmd-ripple-effect btn-success waves-effect waves-light">Approve</a>
				<a id="viewpdf-contractor"  class="btn pmd-ripple-effect btn-success waves-effect waves-light">viewPdf</a>
			</div>
        </div>
    </div>
				</div>
    </div>
    
    <!-- Reject Permit - Contractor Modal -->
	<div class="modal fade" id="rejectpermitexternal-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-notify modal-info" role="document">
	        <!--Content-->
	        <div class="modal-content">
	            <!--Header-->
	            <div class="modal-header">
	                <p class="heading lead">Comments</p>
	
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true" class="white-text">&times;</span>
	                </button>
	            </div>
	
	            <!--Body-->
	            <div class="modal-body">
	                <form id = "">
						<div class="form-group">
						    <label class="">Reject Comment</label>
						    <textarea class="form-control" id="rejectpermitComments-external"></textarea>
						</div>
	                </form>
	            </div>
	
	            <!--Footer-->
	            <div class="modal-footer justify-content-center">
	                <button type="submit" class="btn btn-success btn-sm" id="rejectpermitmodalbtn-external">Reject</button>
	                <button class="btn btn-default btn-sm" data-dismiss="modal">Cancel</button>
	            </div>
	        </div>
	        <!--/.Content-->
	    </div>
	</div>

<!--  -->
<script type="text/javascript" src="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/tether.min.js"></script>
        <script type="text/javascript" src="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/bootstrap.min.js"></script>
        <script type="text/javascript" src="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/mdb.min.js"></script>
         <script type="text/javascript" src="https://storage.googleapis.com/valeo-cp0877-dev.appspot.com/stylescripts/pnotify.custom.min.js"></script>

     
     
        <script type="text/javascript">
      
		var contractdetailstoken ='<%=contractdetailstoken%>';
		window.contractdetailstoken='<%=contractdetailstoken%>';
  
		var loader 		= (function()
				  {
					return {
						 show: function()
						 {
							 $('#pleasewait').show();
						 },
						 hide: function()
						 {
							 $('#pleasewait').hide();
						 },
					};
				  })();
        
        var hiddentoken ="";
        $('#loginbtn').click(function(){
        	loader.show();
        	hiddentoken = $('#passwordinput').val();
        	validatepasswordandgetworkpermitdata();
        	
        	});
        $('#rejectpermitmodalbtn-external').click(function(){
        	loader.show();
        	if(hiddentoken && hiddentoken!="" && contractdetailstoken && contractdetailstoken !=""){
        		var comments	=	$('#rejectpermitComments-external').val();
        		if(comments!=undefined&&comments!=null&&comments!=""){
        			rejectHseWorkpermit(comments);
        		}
        		else{
        			loader.hide();
        			showNotification("Error","Please provide comments and reject","error");
        		}
        	}else{
        		loader.hide();
        		showNotification("Error","Tokens not Found , Please refresh page and try again","error");
        	}
        	
        	});
        
        $('#reject').click(function(e) 
        		{
        			loader.show();
        			$('#rejectpermitexternal-modal').modal('show');
        			$('#rejectpermitComments-external').removeClass('error');
        			$('#rejectpermitComments-external').val('');
        			loader.hide();
        		});	
        
        $('#approve').click(function(){
        	loader.show();
			if(hiddentoken && hiddentoken!="" && contractdetailstoken && contractdetailstoken !=""){
				approveHseWorkpermit();
        	}else{
        		loader.hide();
        		showNotification("Error","Tokens not Found , Please refresh page and try again","error");
        	}
        	
        	});
        
        $(document).on("click","#viewpdf-contractor",function()
   		{
   			var workPermitId = $('#workpermitId-contractor').text();
   			if(workPermitId!=null && workPermitId!=undefined && workPermitId!="")
   			{
   				window.location.href='/contractor/downloadPDFFromContractorPage?workPermitId='+workPermitId;
   			}
   			else
   			{
   				showNotification("Info","Could not find the requested work permit Details.Please contact administrator","info");
   			}	
   		});
        
        var res={};
        function validatepasswordandgetworkpermitdata(){
        	var datatobeSent ={};
        	datatobeSent["hiddentoken"] = hiddentoken;
        	datatobeSent["contractdetailstoken"] = contractdetailstoken;
        	
        	$.ajax({
                type: 'POST',
                url: '/contractor/getdata',
                async:false,
                datatype:'JSON',
                data:{'object':JSON.stringify(datatobeSent)},
                success: function (data) 
                {
                	res=data;
                	var response = JSON.parse(data);
                	 console.log("response "+response);
                     if(response.Status == "Failure"){
                   	  console.log("response from here "+response);
                   		showNotification("Error",response.Message,"error");
                   		loader.hide();
                  		 return;
                     }
                     else if(response.Status == "Success"){
                    	 console.log("Contractor details"+response);
                    	 //detailsdiv
                    	
                    	 var detailshtml="";
                    	 detailshtml+='<div  class="d-flex columncustome" style=""> <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Work Permit ID</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;" id="workpermitId-contractor">'+response.data.F001_ReqID+'</p></div> </div>';
                    	 
                    	 detailshtml+=' <div  class="d-flex columncustome" style=""><div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Reference</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+response.data.F003_Reference+'</p></div></div>';
                    	 
                    	 detailshtml+='<div  class="d-flex columncustome" style=""> <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Requester</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+response.data.F005_Requester+'</p></div></div>';
                    	 
                    	 detailshtml+=' <div  class="d-flex columncustome" style=""><div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Site Location</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+response.data.F014_SiteLocation+'</p></div></div>';
                    	 
                    	 /* detailshtml+='<div  class="d-flex columncustome" style=""> <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Safety instruction to comply with</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+response.data.F027_Safetyinstructionstocomplywith+'</p></div></div>'; */
                    	 
                    	 detailshtml+=' <div  class="d-flex columncustome" style=""><div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Work Permit Type</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+response.data.workPermitType+'</p></div></div>';
                    	 
                    	 detailshtml+=' <div  class="d-flex columncustome" style=""><div  class="d-flex columncustome" style=""><div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">Date of Creation</p></div>';
                    	 detailshtml+=' <div class="inner"> <p class="h1" style="margin-bottom: 0;align-self: center;">'+new Date(response.data.F004_Date)+'</p></div></div>';
                     	
                    	 $('#detailsdiv').html(detailshtml);
                    	 $('#loginform').hide();
                     	$('#tableform').show();
                     	loader.hide();
                     }
                   
                }
        	});
        }
        function showNotification(title,text,type){
        	new PNotify({
        	    title: title,
        	    text: text,
        	    type: type
        	});
        }
        
        function approveHseWorkpermit(){
        	var datatobeSent ={};
        	datatobeSent["hiddentoken"] = hiddentoken;
        	datatobeSent["contractdetailstoken"] = contractdetailstoken;
        	datatobeSent["ACTION"]="APPROVED";
        	$.ajax({
                type: 'POST',
                url: '/contractor/movetoapprover',
                async:false,
                datatype:'JSON',
                data:{'object':JSON.stringify(datatobeSent)},
                success: function (data) 
                {
                	res=data;
                	var response = JSON.parse(data);
                	 console.log("response "+response);
                     if(response.Status == "Failure"){
                   	  console.log("response from here "+response);
                   		showNotification("Error",response.Message,"error");
                   		loader.hide();
                  		 return;
                     }
                     else if(response.Status == "Success"){
                    	 console.log("Contractor details"+response);
                    	 //detailsdiv
                    	 $('#detailsdiv').html("<div>Work Permit Agreement Approved</div>");
                    	 res=null;
                    	 hiddentoken="";
                    	 contractdetailstoken="";
                    	 $('#loginform').hide();
                     	$('#tableform').show();
                     	$('#butns').hide();
                     	loader.hide();
                     }
                   
                }
        	});
        }
        function rejectHseWorkpermit(comments){
				var datatobeSent ={};
	        	datatobeSent["hiddentoken"] = hiddentoken;
	        	datatobeSent["contractdetailstoken"] = contractdetailstoken;
	        	datatobeSent["contractorRejectComments"]=comments;
	        	datatobeSent["ACTION"]="REJECTED";
	        	$.ajax({
	                type: 'POST',
	                url: '/contractor/movetoapprover',
	                async:false,
	                datatype:'JSON',
	                data:{'object':JSON.stringify(datatobeSent)},
	                success: function (data) 
	                {
	                	res=data;
	                	var response = JSON.parse(data);
	                	 console.log("response "+response);
	                     if(response.Status == "Failure"){
	                   	  console.log("response from here "+response);
	                   		showNotification("Error",response.Message,"error");
	                  		 return;
	                     }
	                     else if(response.Status == "Success"){
	                    	 console.log("Contractor details"+response);
	                    	 //detailsdiv
	                     	
	                    	 $('#detailsdiv').html("<div>Work Permit Agreement Rejected</div>");
	                    	 res=null;
	                    	 hiddentoken="";
	                    	 contractdetailstoken="";
	                    	 $('#loginform').hide();
	                     	$('#tableform').show();
	                     	$('#butns').hide();
	                     	$('#rejectpermitexternal-modal').modal('hide');
	                     	showNotification("Info!","Work Permit has been rejected","success");
	                     	loader.hide();
	                     }
	                   
	                }
	        	});
        }
        </script>
</body>
</html>