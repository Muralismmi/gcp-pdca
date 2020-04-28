(function()
{
	checkWorkPermitTypeExists	=	function(id,callback)
	{
		var snapshotKey	=	"WorkPermitType_uniqueId";
		var query	=	db.collection("WorkPermitType").doc(id);
			query.get().then(function(response)
					{
						if(response!=null && response!=undefined && response.data()!=undefined && response.data()!=null)
				    	{
				    		var data	=	response.data();
				    		if(data.uploadStatus=="Success")
				    		{
				    			$('#workpermit-form').attr('data-idPrefix',data.workPermitIdPrefix);
				    			$('#workpermit-form').attr('data-workpermitname',data.workPermitType);
				    			$('#workpermit-form').attr('data-id',id);
				    			Do.validateAndDoCallback(callback);
				    		}
				    		else
				    		{
				    			loader.hide();
				    			showNotification("Error!","Something went wrong. Please contact Administrator","error");
				    		}
				    	}
				    	else
				    	{
			    			loader.hide();
							showNotification("Error!","Something went wrong. Please contact Administrator","error");
				    	}
					}) .catch(function (err) {
				        console.log(err);
				        if(!navigator.onLine && err!=null && err!=undefined && err.message!=undefined && err.message!=null && err.message.indexOf("offline")!=-1){
				        	 loader.hide();
								showNotification("Error!","You are offline.Please connect to network to proceed further","error");
				        }
				        else{
				        	 loader.hide();
								showNotification("Error!","Something went wrong. Please contact Administrator","error");
				        }
				    });
		if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
		{
			console.log("already triggered");
		}
		else
		{
			initializeRealTimeUpdates(snapshotKey,query);
		}
	}
	
	
	
	
	validateAccessibleSiteForWorkPermit = function(siteCode,callback)
	{
		var flag= true;
		if(siteCode!=null && siteCode!=undefined && siteCode!="")
		{
			if(window.access["Admin"] || window.access["Business Owner"])
			{
				flag = false;
			}
			if( flag&& window.access["BGHSEManager"]!=undefined && window.access["BGHSEManager"]!="" && window.access["BGHSEManager"]!=null)
			{
				var siteCodeArr	=	new Array();
				$.each( window.access["BGHSEManager"],function(index,data){
					siteCodeArr.push(data.split("-")[0]);
				});
				 if(siteCodeArr.indexOf(siteCode)!=-1)
				 {
					 flag = false;
				 }
			}
			if(flag && window.access["SiteOwner"]!=undefined && window.access["SiteOwner"]!="" && window.access["SiteOwner"]!=null)
			{
				if(siteCode==window.access["SiteOwner"])
				{
					flag = false;
				}
			}
			if((flag && window.access["Approver"]!=undefined && window.access["Approver"]!="" && window.access["Approver"]!=null))
			{
				if(window.access["Approver"]==siteCode)
				{
					flag = false;
				}
			}
			if((flag && window.access["Requester"]!=undefined && window.access["Requester"]!="" && window.access["Requester"]!=null))
			{
				if(window.access["Requester"]==siteCode)
				{
					flag = false;
				}
			}
			if(flag){
				loader.hide();
				showNotification("Error!","You do not have privileges to create work permit for this site","error");
			}
			else{
				Do.validateAndDoCallback(callback);
			}
		}
		else
		{
			Do.validateAndDoCallback(callback);
		}	
	}
	
	checkUserAccessToWorkPermit	=	function(workpermitdata,callback){
		if(window.access["Admin"] || window.access["Business Owner"]){
			Do.validateAndDoCallback(callback);
		}
		else if(window.access["BGHSEManager"]){
			if(window.access["BGHSEManager"].indexOf(workpermitdata.F014_SiteLocation_siteCode)!=-1 
				|| workpermitdata.F006_EmailRequester_emailUri.indexOf(loggedinuserEmailURI)!=-1
				|| workpermitdata.F009_ApproverEmail.indexOf(useremailid)!=-1){
				Do.validateAndDoCallback(callback);
			}
			else{
				loader.hide();
				showNotification("Error!","You do not have privileges to access this page","error");
			}
		}
		else if(window.access["SiteOwner"]){
			if(window.access["SiteOwner"].indexOf(workpermitdata.F014_SiteLocation_siteCode)!=-1
					|| workpermitdata.F006_EmailRequester_emailUri.indexOf(loggedinuserEmailURI)!=-1
					|| workpermitdata.F009_ApproverEmail.indexOf(useremailid)!=-1){
				Do.validateAndDoCallback(callback);
			}
			else{
				loader.hide();
				showNotification("Error!","You do not have privileges to access this page","error");
			}
		}
		else if(window.access["Requester"]){
			if(workpermitdata.F006_EmailRequester_emailUri.indexOf(loggedinuserEmailURI)!=-1
					|| workpermitdata.F009_ApproverEmail.indexOf(useremailid)!=-1){
				Do.validateAndDoCallback(callback);
			}
			else{
				loader.hide();
				showNotification("Error!","You do not have privileges to access this page","error");
			}
		}else if(window.access["Approver"]){
			if(workpermitdata.F006_EmailRequester_emailUri.indexOf(loggedinuserEmailURI)!=-1
					|| workpermitdata.F009_ApproverEmail.indexOf(useremailid)!=-1){
				Do.validateAndDoCallback(callback);
			}
			else{
				loader.hide();
				showNotification("Error!","You do not have privileges to access this page","error");
			}
		}
	}
	
	fetchWorkPermitTypeFieldConfigBySection	=	function(workPermittypeRefId,callback)
	{
		var responseMap        =        {};
		$.ajax({
		        type: 'GET',
		        url: '/getFieldDetailsSectionWise',
		        async:false, data:{'WorkPermitTypeID':workPermittypeRefId},
		        datatype:'JSON',
		        success: function (data) 
		        {
		                var response = JSON.parse(data);
		                 console.log("response from here pushDatatoSearchIndex "+response.length);
		                                 var sectionWiseMap        =        {};
		                            var sectionArr        =        new Array();
		             for(var i in response){
		                         
		                                        console.log(response[i].headerName)
		                                        var id        =        response[i].uniqueId;
		                                    var data        =        response[i];
		                                    var fieldConfigArr        =        new Array();
		                                    if(sectionWiseMap[data.headerName]!=null && sectionWiseMap[data.headerName]!=undefined && sectionWiseMap[data.headerName]!="")
		                                    {
		                                            fieldConfigArr        =        sectionWiseMap[data.headerName];
		                                    }
		                                    fieldConfigArr.push(data);
		                                    sectionWiseMap[data.headerName]=fieldConfigArr;
		                                    if(sectionArr.indexOf(data.headerName)==-1)
		                                    {
		                                            sectionArr.push(data.headerName);
		                                    }
		                         
		                         }
		             responseMap["sectionArr"] = sectionArr;
		                            responseMap["sectionWiseMap"] = sectionWiseMap;
		        }
		        });
	    		Do.validateAndDoCallback(callback(responseMap));
	}
	
	fetchWorkPermitTypeFieldConfigBySectionEdit	=	function(workPermittypeRefId,callback)
	{
		var responseMap        =        {};
		$.ajax({
		        type: 'GET',
		        url: '/getFieldDetailsSectionWise1',
		        async:false, data:{'WorkPermitTypeID':workPermittypeRefId},
		        datatype:'JSON',
		        success: function (data) 
		        {
		                var response = JSON.parse(data);
		                 console.log("response from here pushDatatoSearchIndex "+response.length);
		                                 var sectionWiseMap        =        {};
		                            var sectionArr        =        new Array();
		             for(var i in response){
		                         
		                                        console.log(response[i].headerName)
		                                        var id        =        response[i].uniqueId;
		                                    var data        =        response[i];
		                                    var fieldConfigArr        =        new Array();
		                                    if(sectionWiseMap[data.headerName]!=null && sectionWiseMap[data.headerName]!=undefined && sectionWiseMap[data.headerName]!="")
		                                    {
		                                            fieldConfigArr        =        sectionWiseMap[data.headerName];
		                                    }
		                                    fieldConfigArr.push(data);
		                                    sectionWiseMap[data.headerName]=fieldConfigArr;
		                                    if(sectionArr.indexOf(data.headerName)==-1)
		                                    {
		                                            sectionArr.push(data.headerName);
		                                    }
		                         
		                         }
		             responseMap["sectionArr"] = sectionArr;
		                            responseMap["sectionWiseMap"] = sectionWiseMap;
		        }
		        });
	    		Do.validateAndDoCallback(callback(responseMap));
	}
	
	fetchWorkPermitById	=	function(workPermitId,callback)
	{
		var snapshotKey	=	"WorkPermit_F001_REQID";
		var query	=	db.collection("WorkPermit").doc(workPermitId);
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.exists)
			{
				Do.validateAndDoCallback(callback(response));
			}
			else
			{
				if(!navigator.onLine){
					loader.hide();
			        showNotification("Error!","You are offline.Please connect to network to proceed further","error");
				}
				else{
					Do.validateAndDoCallback(callback(null));
				}
			}
			if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
			{
				console.log("already triggered");
			}
			else
			{
				initializeRealTimeUpdates(snapshotKey,query);
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	    });
	}
	
	
	fetchWorkPermitAndFieldConfig	=	function(workPermitId,callback)
	{						
		var snapshotKey	=	"WorkPermit_F001_REQID";
		var query	=	db.collection("WorkPermit").doc(workPermitId);
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.exists)
			{
				var responseMap	=	{};
				var fieldConfigList	=	response.data().fieldConfigurations;
				var variableFieldConfigList=response.data().variableFieldConfigurations;
				if(fieldConfigList!=null && fieldConfigList!=undefined && fieldConfigList!="")
				{
					var sectionWiseMap	=	{};
		    		var sectionArr	=	new Array();
		    		$.each(fieldConfigList,function(index,data)
		    		{
		    			var fieldConfigArr	=	new Array();
		    			if(sectionWiseMap[data.HeaderName]!=null && sectionWiseMap[data.HeaderName]!=undefined && sectionWiseMap[data.HeaderName]!="")
		    			{
		    				fieldConfigArr	=	sectionWiseMap[data.HeaderName];
		    			}
		    			fieldConfigArr.push(data);
		    			sectionWiseMap[data.HeaderName]=fieldConfigArr;
		    			if(sectionArr.indexOf(data.HeaderName)==-1)
		    			{
		    				sectionArr.push(data.HeaderName);
		    			}
		    		});
		    		responseMap["sectionArr"] = sectionArr;
		    		responseMap["sectionWiseMap"] = sectionWiseMap;
		    		responseMap["variableFieldConfigurations"] = variableFieldConfigList;
		    		responseMap["data"]=response.data();
		    		if(response.data()!=null && response.data()!=undefined)
		    		{
		    			$('#workpermit-form').attr('data-requesterBG',response.data().requesterBG);
		    			$('#workpermit-form').attr('data-requesterTimezone',response.data().requesterTimezone);
		    		}	
		    		Do.validateAndDoCallback(callback(responseMap));
				}
				else
				{
					loader.hide();
					showNotification("Error!","Something went wrong. Please contact Administrator","error");
				}
			}
			else
			{
				loader.hide();
				showNotification("Error!","Something went wrong. Please contact Administrator","error");
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	    });
		if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
		{
			console.log("already triggered");
		}
		else
		{
			initializeRealTimeUpdates(snapshotKey,query);
		}
	}
	
	
	buildSectionWiseWorkPermitObject = function(workpermitdata,callback){
		if(workpermitdata!=null && workpermitdata!=undefined && workpermitdata!=""){
			var responseMap	=	{};
			var fieldConfigList	=	workpermitdata.data().fieldConfigurations;
			var variableFieldConfigList=workpermitdata.data().variableFieldConfigurations;
			if(fieldConfigList!=null && fieldConfigList!=undefined && fieldConfigList!="")
			{
				var sectionWiseMap	=	{};
	    		var sectionArr	=	new Array();
	    		$.each(fieldConfigList,function(index,data)
	    		{
	    			var fieldConfigArr	=	new Array();
	    			if(sectionWiseMap[data.HeaderName]!=null && sectionWiseMap[data.HeaderName]!=undefined && sectionWiseMap[data.HeaderName]!="")
	    			{
	    				fieldConfigArr	=	sectionWiseMap[data.HeaderName];
	    			}
	    			fieldConfigArr.push(data);
	    			sectionWiseMap[data.HeaderName]=fieldConfigArr;
	    			if(sectionArr.indexOf(data.HeaderName)==-1)
	    			{
	    				sectionArr.push(data.HeaderName);
	    			}
	    		});
	    		responseMap["sectionArr"] = sectionArr;
	    		responseMap["sectionWiseMap"] = sectionWiseMap;
	    		responseMap["variableFieldConfigurations"] = variableFieldConfigList;
	    		responseMap["data"]=workpermitdata.data();
	    		Do.validateAndDoCallback(callback(responseMap));
			}
			else
			{
				loader.hide();
				showNotification("Error!","Something went wrong. Please contact Administrator","error");
			}
		}
		else
		{
			loader.hide();
			showNotification("Error!","Something went wrong. Please contact Administrator","error");
		}
	}
	
buildWorkPermitForm	=	function(sectionArr,sectionWiseMap,variablePartConfig,isCreation,callback)
{
	window.fieldConfigList	=	new Array();
	window.variableFieldConfigurations =new Array();
			var workpermitTypeName	=	"";
			var finalHtml	=	"";
			$.each(sectionArr,function(index,sectionName)
			{
				var dataList	=	sectionWiseMap[sectionName];
				var html="";
				$.each(dataList,function(index,data)
    			{
    				var belongsToPrevField	=	false;
    				var prevFieldConfigObj	=	null;
    				if(index==0) // get work permit name
    				{
    					workpermitTypeName = data.workpermitTypeName;
    				}
    				else // checks if the field belongs to previous object
    				{
    					var prevObj	=	dataList[index-1];
    					if(data.fieldName==prevObj.fieldName)
    					{
    						belongsToPrevField	=	true;
    					}
    				}
    				// keep record of sections defined and also checks if it
					// comes first
    				if(sectionArr.indexOf(data.headerName)==-1)
    				{
    					
    					sectionArr.push(data.headerName);
    				}
    				if(sectionName.toLowerCase().indexOf("variable part")!=-1){
    					buildField(data,function(contentHtml)
        				{
        					// convert field html to complete in a map which
							// keeps content based on section name
    						html+=buildFieldDiv(data,contentHtml,index);
        				});
    				}
    				else
    				{
    				buildField(data,function(contentHtml)
    				{
    					// convert field html to complete in a map which keeps
						// content based on section name
	    				if(belongsToPrevField)
	    				{
	    					html+=contentHtml;
	    				}
	    				else
	    				{
	    					html+=buildFieldDiv(data,contentHtml,index);
	    				}
    				});
    				}
    				fieldConfigList.push(data);
    			});
				// variable part section
				if(sectionName.toLowerCase().indexOf("variable part")!=-1)
				{
					if(variablePartConfig!=null && variablePartConfig!=undefined &&variablePartConfig!=""&&variablePartConfig.length>0)
					{
						var variablePartHtml	=	"";
						$.each(variablePartConfig,function(index,configObj){
							buildField(configObj,function(contentHtml)
		    				{
		    					// convert field html to complete in a map which
								// keeps content based on section name
			    				variablePartHtml+=buildFieldDiv(configObj,contentHtml,index);
		    				});
							variableFieldConfigurations.push(configObj);
						});
						html+='<div class="customcol-4 variablefields-div">'+variablePartHtml+'</div>';
					}
					finalHtml+=buildSection(sectionName,html);
				}
				else
				{
					finalHtml+=buildSection(sectionName,html);
				}
			});
			if(isCreation)
			{
				$('#workpermithead').text("Create "+workpermitTypeName);
				$('#workpermit-form').attr("data-workpermitname",workpermitTypeName);
			}
			else
			{
				$('#workpermithead').text("Edit/Validate "+workpermitTypeName);
			}
			$('#workpermit-form').html(finalHtml);
			$('#workpermitform .customstatus-design').find('i').text('');
			$('#workpermit-form').show();
			bindAutosuggestField(function()
			{
				$('.masterdataref').each(function(){
					fetchAndbuildMasterData($(this),function(){});
				});
				bindListBoxField('#workpermit-form',function(){
				bindApproverList(function()
    				{
    					bindDateTimePickerField('#workpermit-form',function()
    					{
	    						$('.setdefaultval').each(function(){
	    							setDefaultValueToFields($(this),function(){});
	    						});
    							bindVisibilityCondition(function()
    							{
    								Do.validateAndDoCallback(callback);
    							});
    						});
    					});
					});
				});
    	}


	fetchAuthorizedFieldTypes	=	function(siteCode,callback){
		var snapshotKey	=	"VariablePartConfiguration_Site";
		var query	=	db.collection("VariablePartConfiguration").where("siteCode","==",siteCode).where("activeStatus","==",true);
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.size>0)
			{
				Do.validateAndDoCallback(callback(response));
			}
			else
			{
				if(!navigator.onLine){
					 loader.hide();
				        showNotification("Error!","You are offline.Please connect to network to proceed further","error");
				}
				else{
					Do.validateAndDoCallback(callback(null));
				}
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	    });
		if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
		{
			console.log("already triggered");
		}
		else
		{
			initializeRealTimeUpdates(snapshotKey,query);
		}
	}
	
	fetchExistingWorkPermitBySiteAndWorkType	=	function(workpermitrefId,siteCodeProp,siteCode,callback){
		var snapshotKey	=	"WorkPermit_workpermitTypeRefIdSite";
		var query	=	db.collection("WorkPermit").where("workPermitTypeRefId","==",workpermitrefId).where(siteCodeProp,"==",siteCode).where("isActive","==",true);
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.size>0)
			{
				Do.validateAndDoCallback(callback(response));
			}
			else
			{
				if(!navigator.onLine){
					 loader.hide();
				        showNotification("Error!","You are offline.Please connect to network to proceed further","error");
				}
				else{
					Do.validateAndDoCallback(callback(null));
				}
				
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	    });
		if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
		{
			console.log("already triggered");
		}
		else
		{
			initializeRealTimeUpdates(snapshotKey,query);
		}
	}

	duplicateWorkPermit	=	function(){
		
		 getWorkPermitObject('duplicate',function(response)
		{
			var textFields	=	new Array();
			var dateFields	=	new Array();
			var data = response.data;
			textFields	=	response.textFields;
			dateFields	= response.dateFields;
			data.F002_Status="Draft";
			getNewId(function(id)
			{
				data.F001_ReqID=id;
				workpermitId = id;
				db.collection("WorkPermit").doc(workpermitId).set(data).then(function(){
					pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
						updateCountCollection("WorkPermit","save",function(){
							setValuesToFields(data,function(){
								setButtonVisibility(data,function(){
									setFormFieldValidationByStatus(data,function(){
										loader.hide();
										window.location="#editworkpermitform/"+workpermitId;
									});
								});
							});
						});
					});
				});
			});
		});
	}
	
	
	$(document).on('click','.chooseworktype',function()
	{
		
		loader.show();
		fetchAndbuildWorkPermitTypeOption("workpermittype-option","radio",function(){
			$('#chooseworktype-modal').modal('show');
			loader.hide();
		});
	});
	
	/*$(document).on('click','#rejectpermitmodalbtn,#approvewokpermit-btn,#demotepermitmodalbtn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn',function()
			{
				loader.show();
				var btnid=$(this).attr('id');
				var permitid = $('#F001').val();
				if(permitid !=undefined && permitid.trim()!=""){
					var query = db.collection("WorkPermit").where("F001_ReqID","==",permitid);
					fetchDataFromFireStore(query,function (dataList){

						if(dataList!=null && dataList!=undefined && dataList.length>0)
						{
							var data	=	dataList[0].data();
							if(data!=null && data!=undefined)
							{
								if(data.F002_Status!=undefined && data.F002_Status.indexOf("Waiting for Site Approver Validation")!=-1){
									if(window.access["Admin"] || window.access["Business Owner"] || (data.F009_ApproverEmail != undefined && data.F009_ApproverEmail!="" && data.F009_ApproverEmail == useremailid )){
										if(btnid == "rejectpermitmodalbtn")
										{
											   var rejectcomments = $('#rejectpermitComments').val();
											   if(rejectcomments != "" && rejectcomments != null)
											   {
											            getWorkPermitObject('update',function(response)
														{
															var textFields	=	new Array();
															var dateFields	=	new Array();
															textFields	=	response.textFields;
															dateFields	= response.dateFields;
															dateFields[dateFields.length]= "approverStageRejectedOn";
															data.F002_Status="REJECTED";
															data.approverStageRejectComments = rejectcomments;
															data.approverStageRejectedOn=new Date().getTime();
															data.approverStageRejectedBy=useremailid;
															textFields[textFields.length] = "approverStageRejectComments";
															db.collection("WorkPermit").doc(permitid).update(data).then(function(){
																pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
																	fetchWorkPermitAndFieldConfig(permitid,function(response)
																	{
																		if(response!=null && response!=undefined &&response!="")
															           {
																			var sectionArr	=	response.sectionArr;
																			var sectionWiseMap=response.sectionWiseMap;
																			var data=response.data;
																			var variableFieldConfigurations = response.variableFieldConfigurations;
																			buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																	setValuesToFields(data,function(){
																		setButtonVisibility(data,function(){
																			setFormFieldValidationByStatus(data,function(){
																	loader.hide();
																	showNotification("Info!","Work Permit Rejected","success");
																	$('#rejectpermit-modal').modal('hide');
																	sendNotificationOnWorkflowValidation(data,"WorkPermitRejected");
																			});
																});
															});
														});
															            }
															});
														});
															}).catch(function (err) {
														        console.log(err);
														        loader.hide();
														        showNotification("Error!","Something went wrong. Please contact Administrator","error");
														    });
														});
											   }
											   else
											   {
												   loader.hide();
												   $('#rejectpermitComments').addClass('error');
												   showNotification("Error!","Reject Comment is mandatory","error");
											   }	    
										}
										else if(btnid == "approvewokpermit-btn")
										{
											loader.hide();
											bootbox.confirm({
											    message: "Are you sure to Approve this WorkPermit?",
											    buttons: {
											        confirm: {
											            label: 'Yes',
											            className: 'btn-success btn-sm'
											        },
											        cancel: {
											            label: 'No',
											            className: 'btn-default btn-sm'
											        }
											    },
											    callback: function (result) {
											        console.log('This was logged in the callback: ' + result);
											        if(result)
											        {
											        	loader.show();
											        	validateField('workpermit-form',function(){
										        	        getWorkPermitObject('update',function(response)
															{
																var textFields	=	new Array();
																var dateFields	=	new Array();
																var data= response.data;
																textFields	=	response.textFields;
																dateFields	= response.dateFields;
																dateFields[dateFields.length]= "approverStageApprovedOn";
																data.F002_Status="APPROVED";
																data.approverStageApprovedOn=new Date().getTime();
																data.approverStageApprovedBy=useremailid;
																db.collection("WorkPermit").doc(permitid).update(data).then(function(){
																	pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
																		fetchWorkPermitAndFieldConfig(permitid,function(response)
																		{
																			if(response!=null && response!=undefined &&response!="")
																           {
																				var sectionArr	=	response.sectionArr;
																				var sectionWiseMap=response.sectionWiseMap;
																				var data=response.data;
																				var variableFieldConfigurations = response.variableFieldConfigurations;
																				buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																					setValuesToFields(data,function(){
																			setButtonVisibility(data,function(){
																				setFormFieldValidationByStatus(data,function(){
																		loader.hide();
																				showNotification("Info!","Work Permit Approved","success");
																				sendNotificationOnWorkflowValidation(data,"WorkPermitApproved");
																				});
																			});
																		});
																	});
																            }
																		});
																	});
																}).catch(function (err) {
															        console.log(err);
															        loader.hide();
															        showNotification("Error!","Something went wrong. Please contact Administrator","error");
															    });
															});
											        	});
											        }
											    }
											});
										}
										else
										{
											var demoteComments = $('#demotepermitComments').val();
											if(demoteComments!=null && demoteComments!=undefined && demoteComments!="")
											{
											        getWorkPermitObject('update',function(response)
													{
														var textFields	=	new Array();
														var dateFields	=	new Array();
														textFields	=	response.textFields;
														dateFields	= response.dateFields;
														dateFields[dateFields.length]= "approverStageDemotedOn";
														data.F002_Status="Demoted by Approver";
														data.approverStageDemotedOn=new Date().getTime();
														data.approverStageDemotedBy=useremailid;
														data.approverStageDemoteComments = demoteComments;
														db.collection("WorkPermit").doc(permitid).update(data).then(function(){
															pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
																fetchWorkPermitAndFieldConfig(permitid,function(response)
																{
																	if(response!=null && response!=undefined &&response!="")
														           {
																		var sectionArr	=	response.sectionArr;
																		var sectionWiseMap=response.sectionWiseMap;
																		var data=response.data;
																		var variableFieldConfigurations = response.variableFieldConfigurations;
																		buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																setValuesToFields(data,function(){
																	setButtonVisibility(data,function(){
																		setFormFieldValidationByStatus(data,function(){
																loader.hide();
																		showNotification("Info!","Work Permit Demoted to requester","success");
																		$('#demotepermit-modal').modal('hide');
																		sendNotificationOnWorkflowValidation(data,"WorkPermitDemoted");
																		});
																	});
																});
															});
														            }
																});
															});
														}).catch(function (err) {
													        console.log(err);
													        loader.hide();
													        showNotification("Error!","Something went wrong. Please contact Administrator","error");
													    });
													});
											}
											else
											{
												$('#demotepermitComments').addClass('error');
												showNotification("Error!","Demote Comment is mandatory","error");
											}	
										}
									}
									else
									{
										loader.hide();
										showNotification("Error!","You do not approver previliges to approve this workpermit, please Contact your Administrator. ","error");
									}
								}
								else if(data.F002_Status!=undefined && data.F002_Status.indexOf("APPROVED")!=-1){
									if(window.access["Admin"] || window.access["Business Owner"] || window.access["SiteOwner"] || window.access["BGHSEManager"]||(data.F009_ApproverEmail!=undefined && data.F009_ApproverEmail!=null && data.F009_ApproverEmail!="" && data.F009_ApproverEmail == useremailid) || (data.F006_EmailRequester_emailUri!=null && data.F006_EmailRequester_emailUri!=undefined && data.F006_EmailRequester_emailUri!="" && data.F006_EmailRequester_emailUri == loggedinuserEmailURI)){
										if(btnid == "completeworkmonitor-btn"){
						        	        getWorkPermitObject('update',function(response)
											{
												var textFields	=	new Array();
												var dateFields	=	new Array();
												var data= response.data;
												textFields	=	response.textFields;
												dateFields	= response.dateFields;
												dateFields[dateFields.length]= "completionUpdatedOn";
												data.F002_Status="COMPLETED";
												data.completionUpdatedOn=new Date().getTime();
												data.completionUpdatedBy=useremailid;
												db.collection("WorkPermit").doc(permitid).update(data).then(function(){
													pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
														fetchWorkPermitAndFieldConfig(permitid,function(response)
														{
															if(response!=null && response!=undefined &&response!="")
												           {
																var sectionArr	=	response.sectionArr;
																var sectionWiseMap=response.sectionWiseMap;
																var data=response.data;
																var variableFieldConfigurations = response.variableFieldConfigurations;
																buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
														setValuesToFields(data,function(){
															setButtonVisibility(data,function(){
																setFormFieldValidationByStatus(data,function(){
																loader.hide();
																showNotification("Info!","Work Permit Updated Successfully","success");
																sendNotificationOnWorkflowValidation(data,"WorkPermitCompleted");
																});
															});
														});
													});
												            }
														});
													});
												}).catch(function (err) {
											        console.log(err);
											        loader.hide();
											        showNotification("Error!","Something went wrong. Please contact Administrator","error");
											    });
											});
									}
									else if(btnid == "notcompleteworkmonitor-btn"){
										loader.hide();
										bootbox.confirm({
										    message: "Work Permit will be closed and duplicate of permit will be created. Are you sure to perform this action?",
										    buttons: {
										        confirm: {
										            label: 'Yes',
										            className: 'btn-success btn-sm'
										        },
										        cancel: {
										            label: 'No',
										            className: 'btn-default btn-sm'
										        }
										    },
										    callback: function (result) {
										        console.log('This was logged in the callback: ' + result);
										        if(result)
										        {
										        	loader.show();
								        	        getWorkPermitObject('update',function(response)
													{
														var textFields	=	new Array();
														var dateFields	=	new Array();
														var data= response.data;
														textFields	=	response.textFields;
														dateFields	= response.dateFields;
														dateFields[dateFields.length]= "completionUpdatedOn";
														data.F002_Status="CLOSED";
														data.completionUpdatedOn=new Date().getTime();
														data.completionUpdatedBy=useremailid;
														db.collection("WorkPermit").doc(permitid).update(data).then(function(){
														pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
															loader.hide();
															showNotification("Info!","Work Permit has been closed.You will be redirected once duplicate of this Work Permit is created","success");
															duplicateWorkPermit();
														});
														}).catch(function (err) {
													        console.log(err);
													        loader.hide();
													        showNotification("Error!","Something went wrong. Please contact Administrator","error");
													    });
													});
										        }
										    }
										});
								}
									}
									else
									{
										loader.hide();
										showNotification("Error!","You are not authorized to update this workpermit, please Contact your Administrator. ","error");
									}
								}
								else
								{
									loader.hide();
									showNotification("Error!","Status of Work Permit should be 'APPROVED' to perform this action.","error");
								}
							}
							else
							{
								loader.hide();
								showNotification("Error!","WorkPermit is not available in database. , please Contact your Administrator. ","error");
							}
						}
						else
						{
							loader.hide();
							showNotification("Error!","WorkPermit is not available in database. , please Contact your Administrator. ","error");
						}
					
					});
				}
				else{
					loader.hide();
				}
			});printview
	*/
	
	
	$(document).on('click','#savepdca-btn',function()
			{
		debugger;
				var id	=	$(this).attr('id');
				var workpermitId = "";
				var siteCode = $('#F014').attr('data-code');
				
					
				loader.show();
			//	validateField('workpermit-form',function(){
					
					getWorkPermitObject('update',function(response)
							{
						debugger;
						var pdcaId = $('#workpermit-form').attr('data-id');
						if(pdcaId != undefined && pdcaId != null && pdcaId != '') {
							response.data.datatobesen.id = pdcaId;
						}
						response.data.datatobesen.status = "DRAFT";
						if(id=="savepdca-btn")
						{
							var primaryPillarValue=$('SELECT[data-fieldname="PrimaryPillar"]').val();
							if( primaryPillarValue ==null || primaryPillarValue==""){
								$('SELECT[data-fieldname="PrimaryPillar"]').closest('div').find('.select2-container--bootstrap').addClass('error');
								loader.hide();
								return;
							}
							$.ajax({ 
								type: 'POST',
								url: '/saverequest',
								async:false,
								datatype:'JSON', 
								data:{'requestobj':JSON.stringify(response.data.datatobesen),
									'variableconfigdata':JSON.stringify(response.data.variableFieldData)},
								success: function (data) { 
									var response = JSON.parse(data); 
									console.log("response from here pushDatatoSearchIndex "+response); 
									if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
										showNotification("Error!",response.MESSAGE,"Error");
										return;
									} else if(response.STATUS == "SUCCESS"){
										console.log("succefully pushDatatoSearchIndex "+response);
										showNotification("Info!","PDCA Saved Successfully","success");
										if(location.hash.indexOf('#pdca') >  -1){
											window.location.reload();
										}else {
											window.location = "/#pdca/"+response.data.formType+"/"+response.data.id;
										}
									} 
								}
								});
						}else if(id=="submitpdca-btn") {
							$.ajax({ 
								type: 'POST',
								url: '/submitRequest',
								async:false,
								datatype:'JSON', 
								data:{'requestobj':JSON.stringify(response.data.datatobesen),
									'variableconfigdata':JSON.stringify(response.data.variableFieldData)},
								success: function (data) { 
									var response = JSON.parse(data); 
									console.log("response from here pushDatatoSearchIndex "+response); 
									if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
										showNotification("Error!",response.MESSAGE,"Error");
										return;
									} else if(response.STATUS == "SUCCESS"){
										console.log("succefully pushDatatoSearchIndex "+response);
										showNotification("Info!","PDCA Saved Successfully","success");
										window.location.reload();
									} 
								}
								});
						}
						
						
						
							})
					
				//})
				
			});
	$(document).on('click','#submitpdca-btn',function()
			{
		debugger;
				var id	=	$(this).attr('id');
				var workpermitId = "";
				var siteCode = $('#F014').attr('data-code');
				
					
				loader.show();
				validateField('workpermit-form',function(){
					
					getWorkPermitObject('update',function(response)
							{
						debugger;
						var pdcaId = $('#workpermit-form').attr('data-id');
						if(pdcaId != undefined && pdcaId != null && pdcaId != '') {
							response.data.datatobesen.id = pdcaId;
						}
						response.data.datatobesen.status = "DRAFT";
						if(id=="savepdca-btn")
						{
							
							$.ajax({ 
								type: 'POST',
								url: '/saverequest',
								async:false,
								datatype:'JSON', 
								data:{'requestobj':JSON.stringify(response.data.datatobesen),
									'variableconfigdata':JSON.stringify(response.data.variableFieldData)},
								success: function (data) { 
									var response = JSON.parse(data); 
									console.log("response from here pushDatatoSearchIndex "+response); 
									if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
										showNotification("Error!",response.MESSAGE,"Error");
										return;
									} else if(response.STATUS == "SUCCESS"){
										console.log("succefully pushDatatoSearchIndex "+response);
										showNotification("Info!","PDCA Saved Successfully","success");
										window.location.reload;
									} 
								}
								});
						}else if(id=="submitpdca-btn") {
							$.ajax({ 
								type: 'POST',
								url: '/submitRequest',
								async:false,
								datatype:'JSON', 
								data:{'requestobj':JSON.stringify(response.data.datatobesen),
									'variableconfigdata':JSON.stringify(response.data.variableFieldData)},
								success: function (data) { 
									var response = JSON.parse(data); 
									console.log("response from here pushDatatoSearchIndex "+response); 
									if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
										showNotification("Error!",response.MESSAGE,"Error");
										return;
									} else if(response.STATUS == "SUCCESS"){
										console.log("succefully pushDatatoSearchIndex "+response);
										showNotification("Info!","PDCA Saved Successfully","success");
										window.location.reload();
									} 
								}
								});
						}
						
						
						
							})
					
				})
				
			});//,#approvewokpermit-btn
	$(document).on('click','#rejectpermitmodalbtn',function()
			{
				var id	=	$(this).attr('id');
				var workpermitId = "";
				
				var rejectComments =$('#rejectpermitComments').val();
				if(rejectComments.trim()==""){
					showNotification("Error!","Please enter reject comments","Error");
					return;
				}
				var variableData=JSON.parse(request.data.variableFieldData.value);
					loader.show();
					
						
							$.ajax({ 
								type: 'POST',
								url: '/validaterequest',
								async:false,
								datatype:'JSON', 
								data:{'requestobj':JSON.stringify(request.data),
									'variableconfigdata':JSON.stringify(variableData),
									'actionperformed':'REJECT','rejectcomments':rejectComments,'approvecomments':''},
								success: function (data) { 
									var response = JSON.parse(data); 
									console.log("response from here pushDatatoSearchIndex "+response); 
									if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
										showNotification("Error!",response.MESSAGE,"Error");
										return;
									} else if(response.STATUS == "SUCCESS"){
										console.log("succefully pushDatatoSearchIndex "+response);
										showNotification("Info!",response.MESSAGE,"success");
										window.location.reload();
									} 
								}
								});
				
			});
	$(document).on('click','#approvepermitmodalbtn',function()
			{
		var id	=	$(this).attr('id');
		var workpermitId = "";
		var approveComments =$('#approvepermitComments').val();
		
		var variableData=JSON.parse(request.data.variableFieldData.value);
			loader.show();
					$.ajax({ 
						type: 'POST',
						url: '/validaterequest',
						async:false,
						datatype:'JSON', 
						data:{'requestobj':JSON.stringify(request.data),
							'variableconfigdata':JSON.stringify(variableData),
							'actionperformed':'APPROVE','approvecomments':approveComments,'rejectcomments':'',},
						success: function (data) { 
							var response = JSON.parse(data); 
							console.log("response from here pushDatatoSearchIndex "+response); 
							if(response.STATUS == "FAILURE"){ console.log("response from here "+response); 
								showNotification("Error!",response.MESSAGE,"Error");
								return;
							} else if(response.STATUS == "SUCCESS"){
								console.log("succefully pushDatatoSearchIndex "+response);
								showNotification("Info!",response.MESSAGE,"success");
								window.location.reload();
							} 
						}
						});
		
	});
	
	/*$(document).on('click','#saveworkpermit-btn,#submitworkpermit-btn',function()
	{
		var id	=	$(this).attr('id');
		var workpermitId = "";
		var siteCode = $('#F014').attr('data-code');
		if(id=="saveworkpermit-btn")
	{
		loader.show();
			validateAccessibleSiteForWorkPermit(siteCode,function(){
				getWorkPermitObject('update',function(response)
						{
							var textFields	=	new Array();
							var dateFields	=	new Array();
							var object	=	{};
							object	=	response.data;
							textFields	=	response.textFields;
							dateFields	= response.dateFields;
							if(object["F002_Status"]==null || object["F002_Status"]==undefined || object["F002_Status"]==""){
								object["F002_Status"]="Draft";
							}
							
							checkAndValidateMaxVariableFields(response,function()
							{
								if(object["F001_ReqID"]!=undefined && object["F001_ReqID"]!=null && object["F001_ReqID"]!="")
								{
									var id	=	object["F001_ReqID"];
									workpermitId = id;
									db.collection("WorkPermit").doc(id).update(object)
									.then(function(){
										$('#F001').val(id);
										pushDatatoSearchIndex(object,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
												loader.hide();
												showNotification("Info!","Work Permit Saved Successfully","success");
												// window.location =
												// "#workpermitlist";
												// fetch and set details to the
												// permit after save or submit
												fetchWorkPermitAndFieldConfig(workpermitId,function(response)
												{
													if(response!=null && response!=undefined &&response!="")
										           {
														var sectionArr	=	response.sectionArr;
														var sectionWiseMap=response.sectionWiseMap;
														var data=response.data;
														var variableFieldConfigurations = response.variableFieldConfigurations;
														buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
															setValuesToFields(data,function(){
																setButtonVisibility(data,function(){
																	setFormFieldValidationByStatus(data,function(){
																$('.content-wrapper section').hide();
																$('#workpermitform').show();
																$('.navbarMenu ul > li').removeClass('active');
																$('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
																$('#workmonitoring-maindiv').hide();
																loader.hide();
															});
															});
															});
															});
										            }
												});
										});
									},function(rejectResponse){
										console.log(rejectResponse);
									}).catch(function (err) {
								        console.log(err);
								        loader.hide();
								        showNotification("Error!","Something went wrong. Please contact Administrator","error");
								    });
								}
								else
								{
									getNewId(function(id){
												object["F001_ReqID"]=id;
										workpermitId = id;
										object["requesterTimezone"]=moment.tz.guess();
										if(window.edResponse!=null && window.edResponse!=undefined && window.edResponse!="" && window.edResponse.bg!=null &&window.edResponse.bg!=undefined){
											object["requesterBG"] = window.edResponse.bg;
										}
										else
										{
											object["requesterBG"] = "";
										}	
										db.collection("WorkPermit").doc(id).set(object)
										.then(function(){
											$('#F001').val(id);
											object["uniqueId"]=id;
											// object["requesterTimezone"]=moment.tz.guess();
													pushDatatoSearchIndex(object,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function()
												    {
														updateCountCollection("WorkPermit","save",function(){
															loader.hide();
															showNotification("Info!","New Work Permit - "+id+" Created Successfully","success");
															// window.location =
															// "#workpermitlist";
															// fetch and set
															// details to the
															// permit after save
															// or submit
															fetchWorkPermitAndFieldConfig(workpermitId,function(response)
															{
																if(response!=null && response!=undefined &&response!="")
													           {
																	var sectionArr	=	response.sectionArr;
																	var sectionWiseMap=response.sectionWiseMap;
																	var data=response.data;
																	var variableFieldConfigurations = response.variableFieldConfigurations;
																	buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																		setValuesToFields(data,function(){
																			setButtonVisibility(data,function(){
																				setFormFieldValidationByStatus(data,function(){
																			$('.content-wrapper section').hide();
																			$('#workpermitform').show();
																			$('.navbarMenu ul > li').removeClass('active');
																			$('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
																			$('#workmonitoring-maindiv').hide();
																			loader.hide();
																		
																		});
																		});
																	});
																	});
													            }
															});
														});
												    });
												},function(rejectResponse){
													console.log(rejectResponse);
												}).catch(function (err) {
											        console.log(err);
											        loader.hide();
											        showNotification("Error!","Something went wrong. Please contact Administrator","error");
											    });
										});
								}
							});
						});
			});
		}
		else
		{
			loader.show();
			validateAccessibleSiteForWorkPermit(siteCode,function(){
				validateField('workpermit-form',function(){
					var plannedStartDate	=	$('#F018').val()!=undefined && $('#F018').val()!=null && $('#F018').val()!=""?localTimezone.formatDate($('#F018').val()):0;
					var plannedEndDate	=	$('#F019').val()!=undefined && $('#F019').val()!=null && $('#F019').val()!=""?localTimezone.formatDate($('#F019').val()):0;
					var today = new Date();
					today.setHours(0);
					today.setMinutes(0);
					today.setSeconds(0);
					today.setMilliseconds(0);
					var added7daysToStartDate = plannedStartDate+(7*(82800000+3540000));
					var validPlanDates = true;
					if(plannedStartDate>plannedEndDate)
					{
						loader.hide();
						showNotification("Error!","Planned start date should not be greater than or equal to planned end date","error");
						validPlanDates = false;
					}
					else if(plannedStartDate<today.getTime()){
						loader.hide();
						showNotification("Error!","Planned Start Date should not be less than today date","error");
						validPlanDates = false;
					}
					else if(plannedEndDate > added7daysToStartDate)
					{
						loader.hide();
						showNotification("Error!","Planned End Date should not be more than one week from Planned Start Date","error");
						validPlanDates = false;
					}	
					if(validPlanDates)
					{
						validateEmailFields(function(){
							getWorkPermitObject('update',function(response)
									{
										var textFields	=	new Array();
										var dateFields	=	new Array();
										var object	=	{};
										object	=	response.data;
										textFields	=	response.textFields;
										dateFields	= response.dateFields;
										checkAndValidateMaxVariableFields(response,function()
										{
											if(object["F001_ReqID"]!=undefined && object["F001_ReqID"]!=null && object["F001_ReqID"]!="")
											{
												var id	=	object["F001_ReqID"];
												workpermitId = id;
												object["F002_Status"]="Waiting for Contractor Validation";
												db.collection("WorkPermit").doc(id).update(object)
												.then(function(){
													$('#F001').val(id);
													pushDatatoSearchIndex(object,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
															loader.hide();
															showNotification("Info!","Work Permit Submitted Successfully","success");
															// fetch and set
															// details to the
															// permit after save
															// or submit
															fetchWorkPermitAndFieldConfig(workpermitId,function(response){
																if(response!=null && response!=undefined &&response!="")
													        {
																	var sectionArr	=	response.sectionArr;
																	var sectionWiseMap=response.sectionWiseMap;
																	var data=response.data;
																	var variableFieldConfigurations = response.variableFieldConfigurations;
																	buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																		setValuesToFields(data,function(){
																			setButtonVisibility(data,function(){
																				setFormFieldValidationByStatus(data,function(){
																			$('.content-wrapper section').hide();
																			$('#workpermitform').show();
																			$('.navbarMenu ul > li').removeClass('active');
																			$('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
																			$('#workmonitoring-maindiv').hide();
																			loader.hide();
																			notifyContractoronSubmit(object);
																		});
																		});
																	});
																	});
													        }
															});
													});
												},function(rejectResponse){
													console.log(rejectResponse);
												}).catch(function (err) {
											        console.log(err);
											        loader.hide();
											        showNotification("Error!","Something went wrong. Please contact Administrator","error");
											    });
											}
											else
											{
												getNewId(function(id){
															object["F001_ReqID"]=id;
													workpermitId = id;
													object["F002_Status"]="Waiting for Contractor Validation";
													db.collection("WorkPermit").doc(id).set(object)
													.then(function(){
														$('#F001').val(id);
																pushDatatoSearchIndex(object,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
																	updateCountCollection("WorkPermit","save",function(){
																		loader.hide();
																		showNotification("Info!","New Work Permit - "+id+" Submitted Successfully","success");
																		// fetch
																		// and
																		// set
																		// details
																		// to
																		// the
																		// permit
																		// after
																		// save
																		// or
																		// submit
																		fetchWorkPermitAndFieldConfig(workpermitId,function(response){
																			if(response!=null && response!=undefined &&response!="")
																        {
																				var sectionArr	=	response.sectionArr;
																				var sectionWiseMap=response.sectionWiseMap;
																				var data=response.data;
																				var variableFieldConfigurations = response.variableFieldConfigurations;
																				buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function(){
																					setValuesToFields(data,function(){
																						setButtonVisibility(data,function(){
																							setFormFieldValidationByStatus(data,function(){
																						$('.content-wrapper section').hide();
																						$('#workpermitform').show();
																						$('.navbarMenu ul > li').removeClass('active');
																						$('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
																						$('#workmonitoring-maindiv').hide();
																						loader.hide();
																						notifyContractoronSubmit(object);
																					});
																					});
																					});
																					});
																        }
																		});
																	});
																});
															},function(rejectResponse){
																console.log(rejectResponse);
															}).catch(function (err) {
														        console.log(err);
														        loader.hide();
														        showNotification("Error!","Something went wrong. Please contact Administrator","error");
														    });
													});
											}
										});
									});
						});
					}
				});
			});
		}
	});
	*/
	/*
	 * validateFromToDateField = function(callback) {
	 * $('#workpermit-form').find('[data-fieldtype="DATE"]').each(function(){
	 * var id = $(this).attr('id'); if(id!=undefined && id!=null && id!="" &&
	 * (id.indexOf("F")!=-1 || id.indexOf("VF")!=-1)){
	 *  } }); Do.validateAndDoCallback(callback); }
	 */
	
	getNewId	=	function(callback)
	{
		var id	=	"";
		var prefix = $('#workpermit-form').attr('data-idPrefix');
		var snapshotKey	=	"WorkPermitId_UniqueId";
		var query	=	db.collection("WorkPermitId").doc(prefix);
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.exists)
			{
				var doc	=	response.data();
					idNo	=	doc.nextId;
					createNextId(prefix,idNo,function(){
						constructWorkPermitId(prefix,idNo,function(id){
							Do.validateAndDoCallback(callback(id));
						});
					});
			}
			else
			{
				idNo	=	1;
				createNextId(prefix,idNo,function(){
					constructWorkPermitId(prefix,idNo,function(id){
						Do.validateAndDoCallback(callback(id));
					});
				});
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	    });
		if(window.snapshotQuery!=undefined && window.snapshotQuery[snapshotKey]!=undefined && window.snapshotQuery[snapshotKey]!=""&& window.snapshotQuery[snapshotKey]!=null)
		{
			console.log("already triggered");
		}
		else
		{
			initializeRealTimeUpdates(snapshotKey,query);
		}
	}
	
	constructWorkPermitId	=	function(prefix,id,callback)
	{
		id	=	id.toString();
		var zeros	=	"0000000";
		var finalId	=	prefix+"-";
		zeros = zeros.substring(id.length);
		finalId	=	finalId+zeros+id;
		Do.validateAndDoCallback(callback(finalId));
	}
	
	createNextId	=	function(prefix,currentid,callback)
	{
		var obj		=	{};
		obj.nextId	=	currentid+1;
		db.collection("WorkPermitId").doc(prefix).set(obj)
		.then(function(response){
			Do.validateAndDoCallback(callback);
		});
	}
	
	getWorkPermitObject =	function(action,callback)
	{
		var response	=	{};
		var textFields	=	new Array();
		var dateFields	=	new Array();
		var obj	=	{
				datatobesen: {},
				variableFieldData : {}
		};
		var workpermittypeid = $('#workpermit-form').attr('data-id');
		var workPermitType	=	$('#workpermit-form').attr('data-workpermitname');
		var workPermitTypePrefix	=	$('#workpermit-form').attr('data-idPrefix');
		var requesterBG = $('#workpermit-form').attr('data-requesterBG');
		var  requesterTimezone = $('#workpermit-form').attr('data-requesterTimezone');
		// var requesterBG = $('#workpermit-form').attr('data-bg');
		$('#workpermit-form [id^=F]').each(function()
		{
			
			
		
			var id	=	$(this).attr('id');
			var fieldDataType  = "";;
			for(var i in fieldConfigList){ 
				if(id == fieldConfigList[i].iD) {
					fieldDataType = fieldConfigList[i].type;
				}
			}
			var fieldType	=	$(this).attr('data-fieldtype');
			if(id.indexOf("_")==-1 && fieldType!=undefined &&fieldType.indexOf("VARIABLE")==-1)
		    	{
				
				
				// var data = response.docs[0].data();
	    		var fieldname	=	$(this).attr('data-fieldname');
	    		
// var fieldType = data.FieldType;
// var fieldname = data.FieldName.replace(/[^a-zA-Z 0-9]+/g,'');
					
	    		if(fieldDataType == 'FIXED') {
	    			var val	=	"";
					if(fieldType!=undefined && (fieldType=="BIG TEXT" || fieldType=="TEXT" || fieldType=="AUTOSUGGEST" || fieldType=="NUMBER"))
					{
						if(fieldType=="BIG TEXT")
						{
							textFields.push(id+"_"+fieldname);
						}
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType=="DATE")
					{
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?localTimezone.formatDate($(this).val()):0;
						dateFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType=="TIME"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType=="RADIO")
					{
						val	=	$(this).find('input:checked').val()!=undefined && $(this).find('input:checked').val()!=null && $(this).find('input:checked').val()!=""?$(this).find('input:checked').val():"";
					}
					else if(fieldType!=undefined &&fieldType=="CHECKBOX")
					{
						var arr=new Array();
						$(this).find('input:checked').each(function(){
							arr.push($(this).val());
						});
						val = arr;
					}
					else if(fieldType!=undefined && fieldType=="FILE")
					{
						var attachIds	=	new Array();
						var attachLinks	=	new Array();
						var attachObj	=	{};
						$(this).find('.attachdesign .workpermitfiledownload').each(function(){
							if($(this).attr('data-filetype').indexOf("file")!=-1)
							{
								var obj=	{};
								obj["fileName"]=$(this).attr('data-file');
								obj["attachmentId"]	=$(this).attr('data-attachmentid');
								attachIds.push(obj);
							}
							else
							{
								var obj=	{};
								obj["fileName"]	=	$(this).attr('data-file');
								obj["fileLink"]	=	$(this).attr('data-link');
								attachLinks.push(obj);
							}
						});
						if(attachIds!=null && attachIds!=undefined && attachIds!=""&&attachIds.length>0)
						{
							attachObj=attachIds;
						}
						if(attachLinks!=null && attachLinks!=undefined && attachLinks!=""&&attachLinks.length>0)
						{
							attachObj["link"]=attachLinks;
						}
						val	=	attachObj;
							textFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType=="SIGNATURE")
					{
						val	=	$(this).attr('data-url')!=undefined && $(this).attr('data-url')!=null && $(this).attr('data-url')!=""?$(this).attr('data-url'):"";
							textFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="CAPTURE")
					{
						var annotateImgUrls	=	new Array();
						$("#"+id).find('.annotateImage-wrapper').find('img').each(function()
						{
							console.log($(this));
							if($(this).attr('data-url')!=undefined && $(this).attr('data-url')!=null && $(this).attr('data-url')!="")
						    {
								annotateImgUrls.push($(this).attr('data-url'));
							}
							else
							{
								console.log("annotateImgUrls -- inside Else");
							}	
						});
						textFields.push(id+"_"+fieldname);
						val = annotateImgUrls;
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="LISTBOX"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="LISTBOX1"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					if(val!=undefined && val!=null && val!="" && JSON.stringify(val)!="{}" && JSON.stringify(val)!="[]")
					{
						if($(this).hasClass('autosuggest-site') && $(this).attr('data-code')!=undefined && $(this).attr('data-code')!=""){
							obj[id+"_"+fieldname+"_siteCode"]=$(this).attr('data-code');
						}
						else if($(this).hasClass('autosuggest-contact') && $(this).attr('data-emailUri')!=undefined && $(this).attr('data-emailUri')!=""){
							obj[id+"_"+fieldname+"_emailUri"]=$(this).attr('data-emailUri');
						}
						if(action.indexOf("update")!=-1){
							obj.datatobesen[camelize(fieldname)]=val;
						if(camelize(fieldname)=="projectLead"){
							console.log($(this).select2('data'));
							obj.datatobesen["projectLeadId"]=val.split("&&&&")[0];
							obj.datatobesen["projectLead"]=val.split("&&&&")[1];
							obj.datatobesen["projectLeadName"]=$(this).select2('data')[0].text;
						}
						if(camelize(fieldname)=="mentor"){
							console.log($(this).select2('data'));
							obj.datatobesen["mentorId"]=val.split("&&&&")[0];
							obj.datatobesen["mentor"]=val.split("&&&&")[1];
							obj.datatobesen["mentorName"]=$(this).select2('data')[0].text;
						}
						if(camelize(fieldname)=="teamMembers"){
						//	var idlist=
							var list=$(this).select2('data');
							var finalList=new Array();
							
							for(index in list){
								var map={};
								map["name"]=list[index].text;
								map["email"]=list[index].id.split("&&&&")[1];
								map["id"]=list[index].id.split("&&&&")[0];
								finalList.push(map);
							}
							obj.datatobesen["teamMembers"]=JSON.stringify(finalList);
						}
						}
						else if(action.indexOf("duplicate")!=-1 && fieldType.indexOf("SIGNATURE")==-1){
						obj[id+"_"+fieldname]=val;
					}
		    	}
	    			
	    		}else if(fieldDataType == 'VARIABLE'){
	    			var val	=	"";
					if(fieldType!=undefined && (fieldType=="BIG TEXT" || fieldType=="TEXT" || fieldType=="AUTOSUGGEST" || fieldType=="NUMBER"))
					{
						if(fieldType=="BIG TEXT")
						{
							textFields.push(id+"_"+fieldname);
						}
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType=="DATE")
					{
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?localTimezone.formatDate($(this).val()):0;
						dateFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType=="TIME"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType=="RADIO")
					{
						val	=	$(this).find('input:checked').val()!=undefined && $(this).find('input:checked').val()!=null && $(this).find('input:checked').val()!=""?$(this).find('input:checked').val():"";
					}
					else if(fieldType!=undefined &&fieldType=="CHECKBOX")
					{
						var arr=new Array();
						$(this).find('input:checked').each(function(){
							arr.push($(this).val());
						});
						val = arr;
					}
					else if(fieldType!=undefined && fieldType=="FILE")
					{
						var attachIds	=	new Array();
						var attachLinks	=	new Array();
						var attachObj	=	{};
						$(this).find('.attachdesign .workpermitfiledownload').each(function(){
							if($(this).attr('data-filetype').indexOf("file")!=-1)
							{
								var obj=	{};
								obj["fileName"]=$(this).attr('data-file');
								obj["attachmentId"]	=$(this).attr('data-attachmentid');
								attachIds.push(obj);
							}
							else
							{
								var obj=	{};
								obj["fileName"]	=	$(this).attr('data-file');
								obj["fileLink"]	=	$(this).attr('data-link');
								attachLinks.push(obj);
							}
						});
						if(attachIds!=null && attachIds!=undefined && attachIds!=""&&attachIds.length>0)
						{
							attachObj=attachIds;
						}
						if(attachLinks!=null && attachLinks!=undefined && attachLinks!=""&&attachLinks.length>0)
						{
							attachObj["link"]=attachLinks;
						}
						val	=	attachObj;
							textFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType=="SIGNATURE")
					{
						val	=	$(this).attr('data-url')!=undefined && $(this).attr('data-url')!=null && $(this).attr('data-url')!=""?$(this).attr('data-url'):"";
							textFields.push(id+"_"+fieldname);
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="CAPTURE")
					{
						var annotateImgUrls	=	new Array();
						$("#"+id).find('.annotateImage-wrapper').find('img').each(function()
						{
							console.log($(this));
							if($(this).attr('data-url')!=undefined && $(this).attr('data-url')!=null && $(this).attr('data-url')!="")
						    {
								annotateImgUrls.push($(this).attr('data-url'));
							}
							else
							{
								console.log("annotateImgUrls -- inside Else");
							}	
						});
						textFields.push(id+"_"+fieldname);
						val = annotateImgUrls;
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="LISTBOX"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					else if(fieldType!=undefined && fieldType!= "" && fieldType=="LISTBOX1"){
						val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?$(this).val():"";
					}
					if(val!=undefined && val!=null && val!="" && JSON.stringify(val)!="{}" && JSON.stringify(val)!="[]")
					{
						if($(this).hasClass('autosuggest-site') && $(this).attr('data-code')!=undefined && $(this).attr('data-code')!=""){
							obj[id+"_"+fieldname+"_siteCode"]=$(this).attr('data-code');
						}
						else if($(this).hasClass('autosuggest-contact') && $(this).attr('data-emailUri')!=undefined && $(this).attr('data-emailUri')!=""){
							obj[id+"_"+fieldname+"_emailUri"]=$(this).attr('data-emailUri');
						}
						if(action.indexOf("update")!=-1){
							obj.variableFieldData[camelize(fieldname)]=val;
							
						}
						else if(action.indexOf("duplicate")!=-1 && fieldType.indexOf("SIGNATURE")==-1){
						obj[id+"_"+fieldname]=val;
					}
		    	}
	    			
	    		}
	    		
	    			
		    	}
		    });
		
		
		function camelize(str) { 
			return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) { return index == 0 ? word.toLowerCase() : word.toUpperCase(); }).replace(/\s+/g, ''); 
		}
		
		
		$('#workpermit-form [id^=VF]').each(function(){
			var id	=	$(this).attr('id');
			var fieldType	=	$(this).attr('data-fieldtype');
			var fieldname	=	$(this).attr('data-fieldname');
			if(fieldType!=undefined && fieldType=="RADIO")
			{
				val	=	$(this).find('input:checked').val()!=undefined && $(this).find('input:checked').val()!=null && $(this).find('input:checked').val()!=""?$(this).find('input:checked').val():"";
			}
			else if(fieldType!=undefined &&fieldType=="CHECKBOX")
			{
				var arr=new Array();
				$(this).find('input:checked').each(function(){
					arr.push($(this).val());
				});
				val = arr;
			}
			else if(fieldType!=undefined && fieldType=="DATE")
			{
				val	=	$(this).val()!=undefined && $(this).val()!=null && $(this).val()!=""?localTimezone.formatDate($(this).val()):0;
				dateFields.push(id+"_"+fieldname);
			}
			else
			{
				val = $('#'+id).val(); 
			}	
			if(val!=undefined && val!=null && val!=""  && JSON.stringify(val)!="{}" && JSON.stringify(val)!="[]")
			{
				obj[id+"_"+fieldname]=val;
			}
		});
		if(obj["status"]==undefined || obj["status"]==null || obj["status"]=="")
		{
			obj.datatobesen["status"] = "Draft";
		}
		
		if(workPermitType != undefined &&  workPermitType != null && workPermitType != "")
		{
			obj.datatobesen["formType"] = workPermitType;
		}
		
		workPermitType
		response.data	=	obj;
		Do.validateAndDoCallback(callback(response));
	}
	
	fetchAttachmentDetailById	=	function(id,callback){
		 doFetch('POST', '/attachments/fetchAttachObjById/'+id, '')
	        .then(function (response) {
				console.log(JSON.stringify(response));
				if(response!=null && response.Status=="Success")
				{
					Do.validateAndDoCallback(callback(response.data));
				}
				else
				{
					Do.validateAndDoCallback(callback(null));
				}
	        })
	        .catch(function (err) {
	            console.log(err);
	            Do.validateAndDoCallback(callback(null));
	        });
	}
	
	
	
	
	
	$(document).on('click','.editworkpermit-btn',function(){
		loader.show();
		var workPermitId	=	$(this).attr('data-uniqueId');
		if(workPermitId!=null && workPermitId!=undefined && workPermitId!="")
		{
			window.location	=	"#editworkpermitform/"+workPermitId;
		}
		else
		{
			loader.hide();
			showNotification("Error!","Something went wrong. Please contact Administrator","error");
		}
	});
	
	
	$('#selectwrktype-createbtn').click(function () 
	{
		loader.show();
		var val	=	$('input[name="selectWorkPermitType"]:checked').val();
		var id	=	$('input[name="selectWorkPermitType"]:checked').attr('data-id');
		$('#chooseworktype-modal').modal('hide');
		location.hash = "#workpermitform/"+encodeURIComponent(val)+"/"+encodeURIComponent(id);
	});
	
	$(document).on('click','.workpermit-attachbtn',function(){
		
		$('#workpermitattach-uploadbtn').prop('disabled',true);
		$('#attach-modal').find('input').val('');
		$('#attach-modal').attr('data-fieldId',$(this).closest('.attachment').attr('id'));
		$('#attach-modal').modal('show');
	});
	
$(document).on('click','.workpermit-linkbtn',function(){
		
		$('#link-modal').find('input').val('');
		$('#link-modal').attr('data-fieldId',$(this).closest('.attachment').attr('id'));
		$('#link-modal').modal('show');
	});

$(document).on('click','#workpermit-addlinkbtn',function(){
	validateField('workpermit-linkform',function(){
		var fileName	=	$('#workpermit-linktxt').val();
		var fileLink	=	$('#workpermit-link').val();
		if(fileLink!=undefined && fileLink!=null && fileLink!=""){
			var fieldId	=	$('#link-modal').attr('data-fieldId');
			var htmlcontent = 	'<li data-link="'+fileLink+'">'
			+'<a class="filedownld workpermitfiledownload" data-filetype="link" data-file ="'+fileName+'" data-link="'+fileLink+'">'+fileName+'</a>'
			+'<span class="attchmentdelete">'
			+'<i class="fa fa-times"></i>'
			+'</span>'
		+'</li>';
		$('#'+fieldId+' .attachdesign').append(htmlcontent);
		$('#'+fieldId).removeClass('error');
		$('#link-modal').modal('hide');
		}
		else{
			loader.hide();
			showNotification("Error","Please Enter Valid Link","error");
		}
	});
});

$(document).on('click','.attchmentdelete',function(){
	$(this).parent().remove();
});
	
	$("#workpermit-attachfile").change(function()
    {
		if($('#workpermit-attachfile').val().trim()!=""){
			var path = $('#workpermit-attachfile').val();
			var fileName = path.match(/[^\/\\]+$/);
			$('#workpermit-filename').val(fileName);
			generateuploadurlandassignForfileupload(function(data){
				$('#workpermitattach-form').attr('action',data);
				$('#workpermitattach-uploadbtn').prop('disabled',false);
			});
		}
    });
	
	$('#workpermitattach-uploadbtn').click(function(){
        event.preventDefault();
        // Get form
        var form = $('#workpermitattach-form')[0];
        var fileName = $('#workpermit-filename').val();
		// Create an FormData object
        var data = new FormData(form);
        data.append("RefId","");
        data.append("typeofFile","WorkPermit");
		data.append("fileName",fileName);
		// If you want to add an extra field for the FormData
        // data.append("CustomField", "This is some extra data, testing");
		// disabled the submit button
        // $("#btnSubmit").prop("disabled", true);
        $.ajax({
            type: "POST",
            url:  $('#workpermitattach-form').attr('action'),
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

            		var response = JSON.parse(data);
            		if(response.Status=="Success"){
            			if(response.data!=null && response.data!=undefined && response.data!="")
            			{
            				var attachmentObj=response.data;
            				var fieldId	=	$('#attach-modal').attr('data-fieldId');
                			$('#attach-modal').attr('data-fieldId',"");
            				$('#attach-modal').modal('hide');
            				var downloadlink	=	'/attachments/FileDownload?type=WorkPermitAttachments&attchemnetID='+attachmentObj.attachmentId+'&filename='+attachmentObj.fileName+'&refID=';
            				var htmlcontent = 	'<li id="'+attachmentObj.attachmentId+'">'
    							+'<a class="filedownld workpermitfiledownload" data-link="'+downloadlink+'" data-filetype="file" data-file ="'+attachmentObj.fileName+'" data-attachmentid="'+attachmentObj.attachmentId+'">'+attachmentObj.fileName+'</a>'
    							+'<span class="attchmentdelete">'
    							+'<i class="fa fa-times"></i>'
    							+'</span>'
    						+'</li>';
            				$('#'+fieldId+' .attachdesign').append(htmlcontent);
            				$('#'+fieldId).removeClass('error');
            			}
            		}else{
            			showNotification("Error","Something went wrong please contact administrator","error");
            		}
            },
            error: function (e) {
                console.log("ERROR : in  createhelpinstructionupload action", e);
            }
        });
    
	});
	
	$(document).on('click','.workpermitfiledownload',function(){
		var fileType	=	$(this).attr('data-filetype');
		var downloadlink	=	$(this).attr('data-link')
		if(fileType=="file")
		{
			 var link = document.createElement("a");
				link.href = downloadlink;
  	       	link.click();
		}
		else if(fileType=="link")
		{
			 var link = document.createElement("a");
			 $(link).attr('target','_blank');
	  	       	link.href = downloadlink;
	  	       	link.click();
		}
	});
	
	uploadEvidenceImg	=	function(annotateDivId,callback)
	{
		var fieldName	=	$("#"+annotateDivId).attr('data-fieldname');
		var folderName	=	"WP"+fieldName.toUpperCase();
		$("#"+annotateDivId).find('.annotateImage-wrapper').find('img').each(function()
		{
			var message = $(this).attr('src');
			var imgElement = $(this);
			var storageRef = storage.ref();
			if(message!=undefined && message!=null && message!="" && message.indexOf("data:image")!=-1 && ($(this).attr('data-url')==undefined || $(this).attr('data-url')==null || $(this).attr('data-url')==""))
			{
				var fileName	=	new Date().getTime()+"_"+loggedinuserEmailURI+"_evidenceImg.jpg";
				// NAME HERE TO BE CHANGED TO DESIRED NAME PREFERABLY
				// 'WORKPERMITID-SIG-APP-UNIQUEID' AND SAVE THIS IN WORKPERMIT
				// OBJECT
				var WPSIGNATURES = storageRef.child(folderName+'/'+fileName);
					WPSIGNATURES.putString(message, 'data_url').then(function(snapshot) {
							snapshot.ref.getDownloadURL().then(function(downloadURL) {
						    console.log('Image File available at', downloadURL);
						    imgElement.attr('data-url',downloadURL);
						    $('#'+annotateDivId).siblings('div').find('input').removeClass('error');
						    Do.validateAndDoCallback(callback);
						  });
				}) .catch(function (err) {
			        console.log(err);
			        showNotification("Error!","Something went wrong. Please contact Administrator","error");
					Do.validateAndDoCallback(callback);
			    });
			}
			else
			{
				Do.validateAndDoCallback(callback);
			}
		});
	}

	buildVariableFieldConfigurationModal	=	function(variableFieldConfigurations,callback)
	{
		if(variableFieldConfigurations!=null && variableFieldConfigurations!=undefined && variableFieldConfigurations!="")
		{
			$.each(variableFieldConfigurations,function(index,data){
				if(data.FieldType.indexOf("CHECKBOX")!=-1)
				{
					var html	=	buildCheckboxVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
				else if(data.FieldType.indexOf("RADIO")!=-1)
				{
					var html	=	buildRadioVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
				else if(data.FieldType.indexOf("TEXT")!=-1)
				{
					var html	=	buildTextVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
				else if(data.FieldType.indexOf("BIG TEXT")!=-1)
				{
					var html	=	buildTextAreaVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
				else if(data.FieldType.indexOf("LISTBOX")!=-1)
				{
					var html	=	buildListBoxVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
				else if(data.FieldType.indexOf("DATE")!=-1)
				{
					var html	=	buildDateVariableFieldConfig(data);
					$('#variablepartpopup-modal ').find('#variablefieldmodal-div').append(html);
				}
			});
		}
		 Do.validateAndDoCallback(callback);
	}

	/*setButtonVisibility = function(workpermitobject,callback){
	console.log("setButtonVisibility  is invoked");	
	}*/
	setButtonVisibility = function(workpermitobject,callback){
		if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("Draft")!=-1 ){
			if((window.access!=null && window.access!=undefined && (window.access["Admin"] || window.access["Business Owner"])) || (workpermitobject.F006_EmailRequester == useremailid))
			{
				$('#backfromworkpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').show();
			}
			else
			{
				$('#backfromworkpermit-btn').show();
				$('#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			}	
			$('#rejectwokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			$('#viewpdf-btn').show();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
			
		}else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("Demoted by Approver")!=-1 ){
			$('#backfromworkpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn').show();
			$('#rejectwokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			if(workpermitobject.F002_Status.indexOf("Demoted by Approver")!=-1){
				$('#deletewokpermit-btn').hide();
				$('#demotecommentsdiv').show();
				$('#demoteCommentstext').text(workpermitobject.approverStageDemoteComments);
			}
			else{
				$('#demoteCommentstext').text("");
				$('#demotecommentsdiv').hide();
				$('#deletewokpermit-btn').show();
			}
			$('#viewpdf-btn').show();
		}
		else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("Waiting for Contractor Validation")!=-1){
			$('#backfromworkpermit-btn').show();
			$('#rejectwokpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
		    $('#viewpdf-btn').show();
		    $('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}
		else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("Waiting for Site Approver Validation")!=-1){
			// check if APPROVER(OF REQUEST),BO,ADMIN
			
			if(window.access["Admin"] || window.access["Business Owner"] || (workpermitobject.F009_ApproverEmail!=undefined && workpermitobject.F009_ApproverEmail == useremailid) )
			{
				$('#backfromworkpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn').hide();
				$('#rejectwokpermit-btn').show();
				$('#approvewokpermit-btn').show();
				$('#demotewokpermit-btn').show();
				$('#deletewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			}
			else{
				$('#backfromworkpermit-btn').show();
				$('#rejectwokpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
				$('#approvewokpermit-btn').hide();
				$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			}
			$('#viewpdf-btn').show();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("CONTRACTOR REJECTED")!=-1){
			$('#backfromworkpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			$('#rejectwokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
		    $('#viewpdf-btn').show();
		    $('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}
		else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null &&( workpermitobject.F002_Status.indexOf("APPROVED")!=-1 || workpermitobject.F002_Status.indexOf("REJECTED")!=-1) ){
			$('#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			$('#rejectwokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn').hide();
			$('#backfromworkpermit-btn').show();
			if(workpermitobject.F002_Status.indexOf("REJECTED")!=-1)
			{
				$('#rejectcommentsdiv').show();
				$('#rejectCommentstext').text(workpermitobject.approverStageRejectComments);
			}
			else
			{
				$('#rejectcommentsdiv').hide();
				$('#rejectcommentsdiv').text("");
			}	
			if(workpermitobject.F002_Status.indexOf("APPROVED")!=-1 && (window.access["Admin"] || window.access["Business Owner"] || window.access["SiteOwner"] || window.access["BGHSEManager"]||(workpermitobject.F009_ApproverEmail!=undefined && workpermitobject.F009_ApproverEmail == useremailid) || (workpermitobject.F006_EmailRequester_emailUri!=null && workpermitobject.F006_EmailRequester_emailUri!="" && workpermitobject.F006_EmailRequester_emailUri == loggedinuserEmailURI)))
			{
				$('#completeworkmonitor-btn,#notcompleteworkmonitor-btn').show();
			}
			$('#viewpdf-btn').show();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}
		else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && (workpermitobject.F002_Status.indexOf("COMPLETED")!=-1 || workpermitobject.F002_Status.indexOf("CLOSED")!=-1 || workpermitobject.F002_Status.indexOf("NOT COMPLETED")!=-1))
		{
		    $('#viewpdf-btn').show();
			$('#backfromworkpermit-btn').show();
			$('#rejectwokpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}
		else if(workpermitobject.F002_Status!=undefined && workpermitobject.F002_Status!="" &&workpermitobject.F002_Status!=null && workpermitobject.F002_Status.indexOf("Archived")!=-1)
		{
		    $('#viewpdf-btn').show();
			$('#backfromworkpermit-btn').show();
			$('#rejectwokpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn,#deletewokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");
		}
		else{
		   /* $('#viewpdf-btn').hide(); 
			$('#backfromworkpermit-btn,#saveworkpermit-btn,#submitworkpermit-btn').show();
			$('#rejectwokpermit-btn').hide();
			$('#approvewokpermit-btn').hide();
			$('#demotewokpermit-btn').hide();
			$('#deletewokpermit-btn,#demotetoapprover-btn,#completeworkmonitor-btn,#notcompleteworkmonitor-btn').hide();
			$('#demotecommentsdiv').hide();
			$('#demoteCommentstext').text("");*/
			console.log("hi");
		}
		$('#backfromworkmonitor-btn').hide();
		Do.validateAndDoCallback(callback);
	}
	
	setFormFieldValidationByStatus	=	function(workpermitobject,callback){
		if(workpermitobject!=null&&workpermitobject!=undefined && workpermitobject!=""){
			if(workpermitobject.status!=undefined && workpermitobject.status!="" &&workpermitobject.status!=null && (workpermitobject.status.indexOf("Draft")!=-1 ||workpermitobject.status.indexOf("Demoted by Approver")!=-1 ) ){
				$('#workpermit-form').find('.approversign .signpopup-btn').prop('disabled',true);
				// $('#workpermit-form').find('.annotatedimage-clear,.closesign,.attchmentdelete').remove();
				$('#nav-tab').hide();
			}
			else if(workpermitobject.status!=undefined && workpermitobject.status!="" &&workpermitobject.status!=null && (workpermitobject.status.indexOf("WAITING FOR APPROVAL1")!=-1 || workpermitobject.status.indexOf("WAITING FOR APPROVAL2")!=-1 || workpermitobject.status.indexOf("WAITING FOR APPROVAL2")!=-1 || workpermitobject.status.indexOf("APPROVED")!=-1 || workpermitobject.status.indexOf("REJECTED")!=-1)){
				$('#workpermit-form').find('input,textarea,button,select').prop('disabled',true);
				$('#workpermit-form').find('.annotatedimage-clear,.closesign,.attchmentdelete').remove();
				$('#nav-tab').show();
			}
			
		}
		$('#workpermitform #createpermit-container').show();
		$('#workpermitform #workmonitoring-wrapper').hide();
		$('#workpermit-tab').addClass('active');
		$('#workpermit-tab').siblings().removeClass('active');
		Do.validateAndDoCallback(callback);
	}
	
	
	checkForMaxVariableFieldsReached	=	function(callback){

		var variableFieldLength	=	0;
		var maxNoFields	=	0;
		maxNoFields	=	$('#variablepartpopup-modal').attr('data-maxFields')!=undefined&&$('#variablepartpopup-modal').attr('data-maxFields')!=null&&$('#variablepartpopup-modal').attr('data-maxFields')!=""?parseInt($('#variablepartpopup-modal').attr('data-maxFields')):0;
		if(maxNoFields==0){
			Do.validateAndDoCallback(callback(false));
		}	
		else{
			if($('#variablepartpopup-modal').attr('data-action')==undefined || $('#variablepartpopup-modal').attr('data-action')!="existing"){
				var variablefieldsConfig = $('#variablepartpopup-modal #variablefieldmodal-div').find('.choosefieldtypediv');
				if(variablefieldsConfig!=undefined && variablefieldsConfig!=""&&variablefieldsConfig!=null){
					variableFieldLength+=variablefieldsConfig.length;
				}
			}
			var variableFieldsinform	=	$('.variablefields-div').find('div.form-group');
			if(variableFieldsinform!=undefined && variableFieldsinform!=""&&variableFieldsinform!=null){
				variableFieldLength+=variableFieldsinform.length;
			}
			if( variableFieldLength>=maxNoFields){
				Do.validateAndDoCallback(callback(true));
			}
			else{
				Do.validateAndDoCallback(callback(false));
			}
		}
	}
	
	validateVariablePart	=	function(callback){
		loader.show();
		var isValid	=	false;
		var maxNoFields	=	0;
		var fieldId	=	$('#variablepartpopup-modal').attr('data-id');
		var action	=	$('#variablepartpopup-modal').attr('data-action');
		maxNoFields = $('#variablepartpopup-modal').attr('data-maxFields')!=undefined&&$('#variablepartpopup-modal').attr('data-maxFields')!=null&&$('#variablepartpopup-modal').attr('data-maxFields')!=""?parseInt($('#variablepartpopup-modal').attr('data-maxFields')):0;
		if($('#variablepartpopup-modal').find('.choosefieldtypediv')!=undefined &&$('#variablepartpopup-modal').find('.choosefieldtypediv')!=null &&$('#variablepartpopup-modal').find('.choosefieldtypediv').length>0)
		{
			if(maxNoFields!=0 && maxNoFields<$('#variablepartpopup-modal').find('.choosefieldtypediv').length){
				loader.hide();
				showNotification("Error","You have configured fields more than maximum limit("+maxNoFields+")","error");
			}
			else
			{
				$('#variablepartpopup-modal').find('.choosefieldtypediv').each(function(){
					var addField	=	true;
					var fieldType	=	$(this).attr('data-fieldtype');
					var fieldName = $(this).find('.labelname-variablefield').val();
						if(fieldName!=null&&fieldName!=undefined&&fieldName!=""){
					if(fieldType.indexOf("CHECKBOX")!=-1)
					{
						var options	=	new Array();
						if($(this).find('.variablecheckbox-option')!=undefined &&$(this).find('.variablecheckbox-option')!=""&&$(this).find('.variablecheckbox-option')!=null&&$(this).find('.variablecheckbox-option').length>0)
						{
							$(this).find('.variablecheckbox-option').each(function(index,elem){
								var optionVal = $(this).val();
								if(optionVal==undefined || optionVal==null || optionVal=="")
								{
									loader.hide();
									showNotification("Error","Please enter the options for checkbox","error");
									addField	=	false;
									return false;
								}
							});
						}
						else
						{
							loader.hide();
							showNotification("Error","Please enter the options for checkbox","error");
							addField	=	false;
						}
					}
					else if(fieldType.indexOf("RADIO")!=-1)
					{
						var options	=	new Array();
						if($(this).find('.variableradio-option')!=undefined &&$(this).find('.variableradio-option')!=""&&$(this).find('.variableradio-option')!=null&&$(this).find('.variableradio-option').length>0)
						{
							$(this).find('.variableradio-option').each(function(index,elem){
								var optionVal = $(this).val();
								if(optionVal==undefined|| optionVal==null || optionVal=="")
								{
									loader.hide();
									showNotification("Error","Please enter the options for radio","error");
									addField	=	false;
									return false;
								}
							});
						}
						else
						{
							loader.hide();
							showNotification("Error","Please enter the options for radio","error");
							addField	=	false;
						}
					}
					else if(fieldType.indexOf("LISTBOX")!=-1)
					{
						var options	=	new Array();
						if($(this).find('.variablelist-option')!=undefined &&$(this).find('.variablelist-option')!=""&&$(this).find('.variablelist-option')!=null&&$(this).find('.variablelist-option').length>0)
						{
							$(this).find('.variablelist-option').each(function(index,elem){
								var optionVal = $(this).val();
								if(optionVal==undefined || optionVal==null || optionVal=="")
								{
									loader.hide();
									showNotification("Error","Please enter the options for listbox","error");
									addField	=	false;
									return false;
								}
							});
						}
						else
						{
							loader.hide();
							showNotification("Error","Please enter the options for listbox","error");
							addField	=	false;
						}
					}
					if(!addField)
					{
						isValid	=	false;
						return false; // do not iterate to the configured
										// fields if current configuration is
										// not valid
					}
					else{
						isValid = true;
					}
				}
				else
				{
					loader.hide();
					showNotification("Error","Please enter label name","error");
					isValid	=	false;
					return false; // do not iterate to the configured fields
									// if current configuration is not valid
				}
				});
			}
		}
		else if(action!=undefined &&action!=null &&action!=""&&action.indexOf('edit')!=-1){
			isValid = true;
		}
		else
		{
			loader.hide();
			showNotification("Error","Please choose variable fields","error");
		}
		if(isValid){
			Do.validateAndDoCallback(callback);
		}
	}
	
	
	$(document).on('click','.workpermit-variableduplicatebtn',function(){
		loader.show();
		var workPermitTypeId = $('#workpermit-form').attr('data-id');
		var siteCode = $('#F014').attr('data-code');
		var fieldId	=	$(this).siblings('.workpermit-variablebtn').attr('id');
		var maxNoFields	=	0;
		if(workPermitTypeId!=null && workPermitTypeId!=undefined && workPermitTypeId!="")
		{
		if(siteCode!=null && siteCode!=undefined && siteCode!="")
		{
			fetchAndbuildFieldTypeDropdwn('list','variablefielddropdown-div',function(){
				fetchAuthorizedFieldTypes(siteCode,function(response){
				if(response!=null)
				{
					maxNoFields=response.docs[0].data().maxNoFields;
					fetchExistingWorkPermitBySiteAndWorkType(workPermitTypeId,"F014_"+$('#F014').attr('data-fieldname')+"_siteCode",siteCode,function(response){
						if(response!=null && response!=undefined && response.size>0)
						{
							var fieldshtml	=	"";
							var docs	=	response.docs;
							$.each(docs,function(index,doc){
								var data=doc.data();
								if(data.variableFieldConfigurations!=undefined && data.variableFieldConfigurations!=null &&data.variableFieldConfigurations!="")
								{
									fieldshtml+="<option value='"+doc.id+"'>"+doc.id+"</option>";
								}
							});
							if(fieldshtml!="")
							{
								loader.hide();
									$('#variablepart-existingdropdown').html(fieldshtml).select2({
										placeholder: "Select",
						                allowClear: true,
						                theme: "bootstrap",
									});
									$('#variablepart-existingdropdown').val("").trigger('change');
								$('#variablepartpopup-modal #variablepart-existingdropdowndiv').show();
								$('#variablepartpopup-modal #variablefieldmodal-div').hide();
								$('#variablepartpopup-modal').attr('data-id',fieldId);
								$('#variablepartpopup-modal').attr('data-maxFields',maxNoFields);
								$('#variablepartpopup-modal').attr('data-action','existing');
								$('#variablepartpopup-modal').modal('show');
							}
							else
							{
								loader.hide();
								showNotification("Error","No Existing Variable Part Found","error");	
							}
						}
						else
						{
							loader.hide();
							showNotification("Error","No Existing Variable Part Found","error");	
						}
					});
				}
				else
				{
					loader.hide();
					showNotification("Error","No Existing Variable Part Found","error");	
				}
			});
			});
		}
		else
		{
			loader.hide();
			showNotification("Error","Please choose site location and add variable fields","error");
		}
		}
		else
		{
			loader.hide();
			showNotification("Error","Something went wrong. Please contact Administrator","error");
		}
	});
	
	$(document).on('change','#variablepart-existingdropdown',function(){
		loader.show();
		var val	=	$(this).val();
		var maxNoFields=0;
		var fieldId = $('#variablepartpopup-modal').attr('data-id');
		maxNoFields = $('#variablepartpopup-modal').attr('data-maxFields')!=undefined&&$('#variablepartpopup-modal').attr('data-maxFields')!=null&&$('#variablepartpopup-modal').attr('data-maxFields')!=""?parseInt($('#variablepartpopup-modal').attr('data-maxFields')):0;
		if(val!=null && val!=undefined &&val!=""){
			$('#variablepartpopup-modal').find('#variablefieldmodal-div').html("");
			fetchWorkPermitById(val,function(response){
				if(response!=null && response!=undefined && response!="" &&response.data().variableFieldConfigurations!=null &&response.data().variableFieldConfigurations!=undefined  &&response.data().variableFieldConfigurations.length>0){
					buildVariableFieldConfigurationModal(response.data().variableFieldConfigurations,function(){
						checkForMaxVariableFieldsReached(function(isReached){
							if(isReached){
								$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
								$('#'+fieldId).prop('disabled',true);
							}
							else{
								$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
								$('#'+fieldId).prop('disabled',false);
							}
							loader.hide();
						});
						/*
						 * var variableFieldLength = $('#variablepartpopup-modal
						 * #variablefieldmodal-div').find('.choosefieldtypediv').length;
						 * if(response.data().variableFieldConfigurations!=undefined
						 * &&response.data().variableFieldConfigurations!=null
						 * &&response.data().variableFieldConfigurations!=""){
						 * variableFieldLength+=response.data().variableFieldConfigurations.length; }
						 * if( variableFieldLength>=maxNoFields){
						 * showNotification("Info","Maximum limit for adding
						 * variable fields reached.","info");
						 * $('#variablepartpopup-modal
						 * #addmore-variablefieldbtn').prop('disabled',true); }
						 */
						$('#variablepartpopup-modal').find('#variablefieldmodal-div').show();
						loader.hide();
					});
				}
				else
				{
					loader.hide();
					showNotification("Error","Existing Variable Part not found","error");
				}
			});
		}
		else{
			loader.hide();
			$('#variablepartpopup-modal').find('#variablefieldmodal-div').html("");
			$("#variablepartpopup-modal #variablefieldmodal-div").append('<div class="form-group placeholder-variablefield"><div class="dottedborder"><div style="font-size: 12px;text-align: center;color: #888;">Click the "Add More Field" button to add Variable field  </div></div></div>');
		}
	});
	
	$(document).on('click','.workpermit-variableeditbtn',function(){
		loader.show();
		var fieldId	=	$(this).siblings('.workpermit-variablebtn').attr('id');
		if(window.variableFieldConfigurations!=null &&window.variableFieldConfigurations!=undefined &&window.variableFieldConfigurations!=""&&window.variableFieldConfigurations.length>0)
		{
		$('#variablepartpopup-modal').find('#variablefieldmodal-div').html("");
		$('#variablepart-existingdropdown').hide();
		buildVariableFieldConfigurationModal(window.variableFieldConfigurations,function(){
			$('#variablepartpopup-modal').attr('data-id',fieldId);
			$('#variablepartpopup-modal #addmore-variablefieldbtn').hide();
			$('#variablepartpopup-modal #variablepart-existingdropdowndiv').hide();
			$('#variablepartpopup-modal').attr('data-action','edit');
			$('#variablepartpopup-modal #variablefield-addbtn').text("Update");
			$('#variablepartpopup-modal').find('#variablefieldmodal-div').show();
			$('#variablepartpopup-modal').modal('show');
				loader.hide();
		});
		}
		else
		{
			loader.hide();
			showNotification("Error","No variable Fields configured to Edit","error");
		}
	});
	
	$(document).on('click','.workpermit-variablebtn',function(){
		loader.show();
		var fieldId	=	$(this).attr('id');
		var siteCode	=	$('#F014').attr('data-code');
		$('#variablepartpopup-modal').find('#variablefieldmodal-div').html('');
		$("#variablepartpopup-modal #variablefieldmodal-div").append('<div class="form-group placeholder-variablefield"><div class="dottedborder"><div style="font-size: 12px;text-align: center;color: #888;">Click the "Add More Field" button to add Variable field  </div></div></div>');
		$('#variablepartpopup-modal #variablepart-existingdropdowndiv').hide();
		if(siteCode!=null && siteCode!=undefined && siteCode!="")
		{
			fetchAndbuildFieldTypeDropdwn('list','variablefielddropdown-div',function(){
			fetchAuthorizedFieldTypes(siteCode,function(response){
				if(response!=null)
				{
					var docList	=	response.docs;
					var doc	=	docList[0];
					var data	=	doc.data();
					$('#variablepartpopup-modal').attr('data-maxFields',data.maxNoFields);
				}
				$('#variablepartpopup-modal').attr('data-id',fieldId);
				$('#variablepartpopup-modal #addmore-variablefieldbtn').show();
				$('#variablepartpopup-modal').attr('data-action','add');
				$('#variablepartpopup-modal #variablefield-addbtn').text("Add");
				$('#variablepartpopup-modal').find('#variablefieldmodal-div').show();
				$('#variablepartpopup-modal').modal('show');
				checkForMaxVariableFieldsReached(function(isReached){
					if(isReached){
						$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
						$('#'+fieldId).prop('disabled',true);
					}
					else{
						$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
						$('#'+fieldId).prop('disabled',false);
					}
					loader.hide();
				});
				/*
				 * else {
				 * $('#workpermitvariablepart-fieldtype').val("").trigger("change");
				 * loader.hide(); showNotification("Error","No Fields configured
				 * for your site. Please contact your site owner","error"); }
				 */
			});
			});
		}
		else
		{
			loader.hide();
			showNotification("Error","Please choose site location and add variable fields","error");
		}
	});
	
	$(document).on('click','.variablefieldtype-list',function(){
		var maxFields = 0;
		maxFields = $('#variablepartpopup-modal').attr('data-maxFields')!=undefined&& $('#variablepartpopup-modal').attr('data-maxFields')!=null && $('#variablepartpopup-modal').attr('data-maxFields')!=""? parseInt($('#variablepartpopup-modal').attr('data-maxFields')):0;
		var fieldId	=	$('#variablepartpopup-modal').attr('data-id');
		var fieldHtml	=	"";
		if($(this).attr('data-variablefieldtype').indexOf("Checkbox")!=-1){
			fieldHtml=buildCheckboxVariableField();
		}
		else if($(this).attr('data-variablefieldtype').indexOf("Radio")!=-1){
			fieldHtml=buildRadioVariableField();
		}
		else if($(this).attr('data-variablefieldtype').indexOf("Text")!=-1){
			fieldHtml=buildTextVariableField();
		}
		else if($(this).attr('data-variablefieldtype').indexOf("Comment")!=-1){
			fieldHtml=buildTextAreaVariableField();
		}
		else if($(this).attr('data-variablefieldtype').indexOf("ListBox")!=-1){
			fieldHtml=buildListBoxVariableField();
		}
		else if($(this).attr('data-variablefieldtype').indexOf("Date")!=-1){
			fieldHtml=buildDateVariableField();
		}
		$('.placeholder-variablefield').remove();
		$('#variablepartpopup-modal').find('#variablefieldmodal-div').append(fieldHtml);
		checkForMaxVariableFieldsReached(function(isReached){
			if(isReached){
				$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
				$('#'+fieldId).prop('disabled',true);
		}
			else{
				$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
				$('#'+fieldId).prop('disabled',false);
		}
	});
	});
	
	$(document).on("click",".fieldtrash",function()
			{
		var fieldId = $('#variablepartpopup-modal').attr('data-id');
		$(this).parent().remove();
		var formgrouplength = $('.fieldWrapper > div.form-group').length;
		if(formgrouplength == 0){
			$("#variablepartpopup-modal #variablefieldmodal-div").append('<div class="form-group placeholder-variablefield"><div class="dottedborder"><div style="font-size: 12px;text-align: center;color: #888;">Click the "Add More Field" button to add Variable field  </div></div></div>');
		}
		checkForMaxVariableFieldsReached(function(isReached){
			if(isReached){
				$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
				$('#'+fieldId).prop('disabled',true);
		}
			else{
				$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
				$('#'+fieldId).prop('disabled',false);
			}
		});
			});
	
	$(document).on("click",".checkboxaddoption,.radioaddoption,.listboxaddoption",function()
			{
		if($(this).hasClass('checkboxaddoption'))
		{
			$(this).parent().find('.checkboxWrapper').append('<div class="d-flex varfield-content">'
					+'	<i class="fa fa-square-o"></i>'
						+'<input type="text" class="form-control variablecheckbox-option" data-required="yes" data-type="text" placeholder="Option">'
						+'<span class="variablepart-checkboxvaloptionremove"><i class="material-icons">close</i></span>'
					+'</div>');
		}
		if($(this).hasClass('radioaddoption'))
		{
			$(this).parent().find('.radioWrapper').append('<div class="d-flex varfield-content">'
					+'	<i class="fa fa-circle-o"></i>'
						+'<input type="text" class="form-control variableradio-option" data-required="yes" data-type="text" placeholder="Option">'
						+'<span class="variablepart-radiovaloptionremove"><i class="material-icons">close</i></span>'
					+'</div>');
		}
		if($(this).hasClass('listboxaddoption'))
		{
			$(this).parent().find('.listboxWrapper').append('<div class="d-flex varfield-content">'
					+'<input type="text" class="form-control variablelist-option" data-required="yes" data-type="text" placeholder="Option">'
					+'<span class="variablepart-listvaloptionremove"><i class="material-icons">close</i></span>'
				+'</div>');
		}
			});
	$(document).on('click','.variablepart-checkboxvaloptionremove,.variablepart-radiovaloptionremove,.variablepart-listvaloptionremove',function(){

		$(this).parent().remove();
		});

	$(document).on('click','#variablepartpopup-modal #variablefield-addbtn',function(){
		validateVariablePart(function(){
		var fieldId	=	$('#variablepartpopup-modal').attr('data-id');
		var action	=	$('#variablepartpopup-modal').attr('data-action');
			if(action!=undefined &&action!=null &&action!=""&&(action.indexOf('edit')!=-1||action.indexOf('existing')!=-1)){
				if(action.indexOf('existing')!=-1){
					var maxNoFields	=	$('#variablepartpopup-modal').attr('data-maxFields')!=undefined&&$('#variablepartpopup-modal').attr('data-maxFields')!=null&&$('#variablepartpopup-modal').attr('data-maxFields')!=""?parseInt($('#variablepartpopup-modal').attr('data-maxFields')):0;
					var variablefieldsConfig = $('#variablepartpopup-modal #variablefieldmodal-div').find('.choosefieldtypediv')!=undefined &&$('#variablepartpopup-modal #variablefieldmodal-div').find('.choosefieldtypediv')!=null && $('#variablepartpopup-modal #variablefieldmodal-div').find('.choosefieldtypediv')!=""?$('#variablepartpopup-modal #variablefieldmodal-div').find('.choosefieldtypediv').length:0;
					if(maxNoFields==0||(maxNoFields<variablefieldsConfig)){
						loader.hide();
						showNotification("Error","You have reached the maximum limit of fields.Please delete some fields and proceed","error");
						return false;
					}
				}
				window.variableFieldConfigurations=new Array();
				$('#'+fieldId).closest('blockquote').find('.variablefields-div').html('');
			}
				if($('#variablepartpopup-modal').find('.choosefieldtypediv')!=undefined &&$('#variablepartpopup-modal').find('.choosefieldtypediv')!=null &&$('#variablepartpopup-modal').find('.choosefieldtypediv').length>0){
		$('#variablepartpopup-modal').find('.choosefieldtypediv').each(function(){
			var fieldType	=	$(this).attr('data-fieldtype');
			var fieldName = $(this).find('.labelname-variablefield').val();
					var obj = {};
			if(fieldType.indexOf("CHECKBOX")!=-1)
			{
				var options	=	new Array();
						if($(this).find('.variablecheckbox-option')!=undefined &&$(this).find('.variablecheckbox-option')!=""&&$(this).find('.variablecheckbox-option')!=null&&$(this).find('.variablecheckbox-option').length>0)
						{
				$(this).find('.variablecheckbox-option').each(function(index,elem){
						var optionVal = $(this).val();
					if(optionVal!=undefined&&optionVal!=null &&optionVal!="")
					{
						options.push(optionVal.trim());
					}
				});
				obj.FieldType = fieldType;
				obj.Options = options.join(",");
			}
					}
			else if(fieldType.indexOf("RADIO")!=-1)
			{
				var options	=	new Array();
						if($(this).find('.variableradio-option')!=undefined &&$(this).find('.variableradio-option')!=""&&$(this).find('.variableradio-option')!=null&&$(this).find('.variableradio-option').length>0)
						{
				$(this).find('.variableradio-option').each(function(index,elem){
						var optionVal = $(this).val();
					if(optionVal!=undefined&&optionVal!=null &&optionVal!="")
					{
						options.push(optionVal.trim());
					}
				});
				obj.FieldType = fieldType;
				obj.Options = options.join(",");
			}
					}
			else if(fieldType.indexOf("TEXT")!=-1)
			{
				var options	=	new Array();
				obj.FieldType = fieldType;
			}
			else if(fieldType.indexOf("BIG TEXT")!=-1)
			{
				var options	=	new Array();
				obj.FieldType = fieldType;
			}
			else if(fieldType.indexOf("LISTBOX")!=-1)
			{
				var options	=	new Array();
						if($(this).find('.variablelist-option')!=undefined &&$(this).find('.variablelist-option')!=""&&$(this).find('.variablelist-option')!=null&&$(this).find('.variablelist-option').length>0)
						{
				$(this).find('.variablelist-option').each(function(index,elem){
						var optionVal = $(this).val();
					if(optionVal!=undefined&&optionVal!=null &&optionVal!="")
					{
						options.push(optionVal.trim());
					}
				});
				obj.FieldType = fieldType;
				obj.Options = options.join(",");
			}
			}
			else if(fieldType.indexOf("DATE")!=-1)
			{
				var options	=	new Array();
				obj.FieldType = fieldType;
			}
					$('#'+fieldId).removeClass('error');
				obj.FieldName = fieldName;
				obj.HeaderName ="Variable Part";
				obj.IsMandatory ="YES";
					if(action!=undefined &&action!=null &&action!=""&&(action.indexOf('edit')!=-1||action.indexOf('existing')!=-1)){
					var id = $(this).attr('data-id');
					obj.ID=id;
					window.variableFieldConfigurations.push(obj);
					buildField(obj,function(fieldHtml){
						var fieldDivHtml = buildFieldDiv(obj,fieldHtml,window.variableFieldConfigurations.indexOf(obj));
						if($('#'+fieldId).closest('blockquote').find('.variablefields-div')=="" || $('#'+fieldId).closest('blockquote').find('.variablefields-div')==undefined || $('#'+fieldId).closest('blockquote').find('.variablefields-div').length<=0)
						{
							$('#'+fieldId).closest('blockquote').append('<div class="customcol-4 variablefields-div">'+fieldDivHtml+"</div>");
						}
						else
						{
							$('#'+fieldId).closest('blockquote').find('.variablefields-div').append(fieldDivHtml);
						}
							bindListBoxField('.variablefields-div',function(){
								bindDateTimePickerField('#workpermit-form',function(){
								checkForMaxVariableFieldsReached(function(isReached){
									if(isReached){
										$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
										$('#'+fieldId).prop('disabled',true);
									}
									else{
										$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
										$('#'+fieldId).prop('disabled',false);
									}
						loader.hide();
					});
						});
					});
						});
				}
				else{
					constructVariableFieldId("VF",(window.variableFieldConfigurations.length+1),function(id){
						obj.ID=id;
						window.variableFieldConfigurations.push(obj);
						buildField(obj,function(fieldHtml){
							var fieldDivHtml = buildFieldDiv(obj,fieldHtml,window.variableFieldConfigurations.indexOf(obj));
							if($('#'+fieldId).closest('blockquote').find('.variablefields-div')=="" || $('#'+fieldId).closest('blockquote').find('.variablefields-div')==undefined || $('#'+fieldId).closest('blockquote').find('.variablefields-div').length<=0)
							{
								$('#'+fieldId).closest('blockquote').append('<div class="customcol-4 variablefields-div">'+fieldDivHtml+"</div>");
							}
							else
							{
								$('#'+fieldId).closest('blockquote').find('.variablefields-div').append(fieldDivHtml);
							}
								bindListBoxField('.variablefields-div',function(){
									bindDateTimePickerField('#workpermit-form',function(){
										$('#variablepartpopup-modal').find('.choosefieldtypediv').remove();
										checkForMaxVariableFieldsReached(function(isReached){
											if(isReached){
												$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
												$('#'+fieldId).prop('disabled',true);
											}
											else{
												$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
												$('#'+fieldId).prop('disabled',false);
											}
								loader.hide();
							});
									});
					});
							});
						});
				}
				});
			}
			else{
				checkForMaxVariableFieldsReached(function(isReached){
						if(isReached){
							$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',true);
							$('#'+fieldId).prop('disabled',true);
						}
						else{
							$('#variablepartpopup-modal #addmore-variablefieldbtn').prop('disabled',false);
							$('#'+fieldId).prop('disabled',false);
						}
					loader.hide();
				});
			}
				$('#variablepartpopup-modal').modal('hide');
		});
	});
	
	constructVariableFieldId	=	function(prefix,id,callback)
	{
		id	=	id.toString();
		var zeros	=	"000";
		var finalId	=	prefix;
		zeros = zeros.substring(id.length);
		finalId	=	finalId+zeros+id;
		Do.validateAndDoCallback(callback(finalId));
	}
	
	deleteWorkpermit	=	function(ids,callback){
		if(ids!=undefined && ids!=null && ids!=""){
			var idList = ids.split(",");
			$.each(idList,function(index,id){
				db.collection("WorkPermit").doc(id).get()
				.then(function(response){
					if(response!=undefined && response!=null && response!="" && response.exists){
						var data = response.data();
						data["isActive"]=false;
						db.collection("WorkPermit").doc(id).set(data)
						.then(function(response){

							var textFields	=	new Array();
							var dateFields	=	new Array();
							if(data.fieldConfigurations!=null) {
								$.each(data.fieldConfigurations,function(index,map) {
									if(map.FieldType=="BIG TEXT"
									   || map.FieldType=="FILE"
									   || map.FieldType=="SIGNATURE"
									   || map.FieldType=="CAPTURE") {
										textFields.push(map.ID+"_"+map.FieldName.replace(/[^a-zA-Z0-9]+/g,''));
									}
									else if(map.FieldType=="DATE") {
										dateFields.push(map.ID+"_"+map.FieldName.replace(/[^a-zA-Z0-9]+/g,''));
									}
								});
							}
							if(data.variableFieldsConfigurations!=null) {
								$.each(data.variableFieldsConfigurations,function(index,map) {
									if(map.FieldType=="BIG TEXT"
									   || map.FieldType=="FILE"
									   || map.FieldType=="SIGNATURE"
									   || map.FieldType=="CAPTURE") {
										textFields.push(map.ID+"_"+map.FieldName.replace(/[^a-zA-Z0-9]+/g,''));
									}
									else if(map.FieldType=="DATE") {
										dateFields.push(map.ID+"_"+map.FieldName.replace(/[^a-zA-Z0-9]+/g,''));
									}
								});
							}
							pushDatatoSearchIndex(data,"WORKPERMIT_INDEX",textFields,dateFields,"F001_ReqID",function(){
								updateCountCollection("WorkPermit","delete",function(){});
							});
							console.log(response);
						
						}).catch(function (err) {
					        console.log(err);
					        showNotification("Error!","Something went wrong. Please contact Administrator","error");
					    });
					}
				});
			});
			Do.validateAndDoCallback(callback);
		}
		else{
			Do.validateAndDoCallback(callback);
		}
	}
	
	$(document).on('click','#backfromworkpermit-btn',function(){
		bootbox.confirm({
			message:"Are you sure you want to leave from this page?",
			buttons:{
				confirm:{
					label:'Yes',
					className:'btn-success btn-sm',
				},
				cancel:{
					label:'No',
					className:'btn-default btn-sm',
				}
			},
			callback:function(result) 
				{
			if(result)
			{
				window.location="#workpermitlist";
			}
		}
		});
	});
	
	$(document).on('click','#deletewokpermit-btn,.deleteworkpermit-btn',function(){
		var id = $("#F001").val();
		var creator	=	$('#F006').attr('data-emailUri');
		if($(this).hasClass('deleteworkpermit-btn')){
			var data = $("#workpermitlist-table").DataTable().row($(this).closest('tr')).data();
			 id = data.F001_ReqID;
			 creator = data.F006_EmailRequester_emailUri;
		}
		if(window.access["Admin"] || window.access["Business Owner"] || creator==loggedinuserEmailURI){
			bootbox.confirm({
				message:"Are you sure you want to delete the work permit?",
				buttons:{
					confirm:{
						label:'Yes',
						className:'btn-success btn-sm',
					},
					cancel:{
						label:'No',
						className:'btn-default btn-sm',
					}
				},
				callback:function(result) 
					{
				if(result)
				{
					loader.show();
					deleteWorkpermit(id,function(){
						loader.hide();
						showNotification("Info!","Work Permit deleted Successfully","success");
						if($(this).hasClass('deleteworkpermit-btn')){
							var queryObj = {};
							fetchAndBuildWorkPermit(queryObj);
						}
						else{
							location.hash="#workpermitlist";
						}
						
					});
				}
			}
			});
		}
		else{
			showNotification("Error!","You do not have privileges to delete this work permit","error");
		}
	});
	
	
	
	$(document).on("click","#rejectwokpermit-btn",function(e) 
	{
		$('#rejectpermit-modal').modal('show');
		$('#rejectpermitComments').removeClass('error');
		$('#rejectpermitComments').val('');
		$('#rejectpermitmodalbtn').attr('disabled',true);
	});	
	
	$(document).on("click","#approvewokpermit-btn",function(e) 
			{
				$('#approvepermit-modal').modal('show');
				$('#approvepermitComments').removeClass('error');
				$('#approvepermitComments').val('');
			});	
	
	$(document).on("click","#demotewokpermit-btn,#demotetoapprover-btn",function(e) 
	{
		var action ="";
		var id = $(this).attr('id');
		if(id=="demotewokpermit-btn"){
			action = "demotetorequester";
		}
		else if(id=="demotetoapprover-btn"){
			action = "demotetoapprover";
		}
		$('#demotepermit-modal').modal('show');
		$('#demotepermit-modal').attr('data-action',action);
		$('#demotepermitComments').removeClass('error');
		$('#demotepermitComments').val('');
	});	
	
	function sendNotificationOnWorkflowValidation(inputObj,action)
    {
		var datatobeSent ={};
		datatobeSent["object"] = inputObj;
		$.ajax({
	        type: 'POST',
	        url: '/workpermit/notifyActorsOnWorkflowValidation?action='+action,
	        async:false,
	        datatype:'JSON',
	        data:{'object':JSON.stringify(inputObj)},
	        success: function (data) 
	        {
	        	var response = JSON.parse(data);
	        	 console.log("response "+response);
	             if(response.Status == "Failure")
	             {
	           	  console.log("response from here "+response);
	          		 return;
	             }
	             else if(response.Status == "Success"){
	            	 console.log("Notified to Contractor "+response);
	             }
	           
	        }
		});
	}
	
	

	/*$(document).on("click","#viewpdf-btn",function()
	{
		var workPermitId = $('#reqid_label').html();
		loader.show();
		if(workPermitId!=null && workPermitId!=undefined && workPermitId!="")
		{
			window.location.href='/reports/generateReportPDF?workPermitId='+workPermitId;
			loader.hide();
		}
		else
		{
			loader.hide();
			showNotification("Info","Please save the work permit details","info");
			// showNotification("Info","Could not find the requested work permit
			// Details.Please contact administrator","info");
		}	
	});*/
	 $("#viewpdf-btn").click(function() { 
		 
		 
		 window.location = '/createpdfanddownload2?filename='+request.data.id;
		  /*var pdf;
		  pdf = new jsPDF('landscape');
		  pdf.addHTML($('#printviewpdcarow'), function() {
		    pdf.save('stacking-plan.pdf');
		  });*/
		 /* html2canvas($("#printviewpdcarow"), {
	            onrendered: function(canvas) {   
	            	
	            	var imgData = canvas.toDataURL('image/png');
	            	var imgWidth = 210; 
	            	var pageHeight = 295;  
	            	var imgHeight = canvas.height * imgWidth / canvas.width;
	            	var heightLeft = imgHeight;
	            	var doc = new jsPDF('p', 'mm');
	            	var position = 0;

	            	doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
	            	heightLeft -= pageHeight;

	            	while (heightLeft >= 0) {
	            	  position = heightLeft - imgHeight;
	            	  doc.addPage();
	            	  doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
	            	  heightLeft -= pageHeight;
	            	}
	            	doc.save(request.data.formId+".pdf");
	                             
	                var doc = new jsPDF('l', 'mm','a4');
	                doc.addImage(canvas, 'PNG',0,0);
	                doc.save('sample-file.pdf');
	            }
	        });*/
	            
	        
	    });
	
	
	$(document).on("click",".helpinstruction-link",function(){
		loader.show();
		var workpermitTypeID = $('#workpermit-form').attr('data-id');
		fetchAndbuildHelpInstructionsByWorkPermitType('#helpinstructions-workpermit-table tbody',workpermitTypeID,function(){
			loader.hide();
		});
	});
	 
	checkAndValidateMaxVariableFields = function(workpermitObj,callback)
	{
		var siteCode = $('#F014').attr('data-code');
		if(siteCode!=null && siteCode!=undefined && siteCode!="")
		{
			if(workpermitObj.data.variableFieldConfigurations!=null && workpermitObj.data.variableFieldConfigurations!=undefined && workpermitObj.data.variableFieldConfigurations!="" && workpermitObj.data.variableFieldConfigurations.length >0)
			{
				fetchAuthorizedFieldTypes(siteCode,function(response){
					if(response!=null)
					{
						var docList	=	response.docs;
						var doc	=	docList[0];
						var data	=	doc.data();
						var maxFieldNo = data.maxNoFields;
						if(workpermitObj.data.variableFieldConfigurations.length > maxFieldNo)
						{
							loader.hide();
							showNotification("Info","Variable Field Configured is greater than the Max Number of fields for this site","info");
						}
						else
						{
							Do.validateAndDoCallback(callback);
						}	
					}
					else
					{
						Do.validateAndDoCallback(callback);
					}	
				});
			}
			else
			{
				Do.validateAndDoCallback(callback);
			}	
		}
		else
		{
			Do.validateAndDoCallback(callback);
		}	
	}
	
	validateEmailFields	=	function(callback){
		var siteResponsible = $('#F008').val();
		var contractorEmail = $('#F012').val();
		if(!isEmail(siteResponsible)){
			loader.hide();
			showNotification("Error!",siteResponsible+" is not a valid email ID","error");
		}
		else if(!isEmail(contractorEmail)){
			loader.hide();
			showNotification("Error!",contractorEmail+" is not a valid email ID","error");
		}
		else{
			Do.validateAndDoCallback(callback);
		}
	}
	
	buildWorkPermitListQuerybyAccess	=	function(limit,orderColumn,orderOption,callback)
	{
		var queryArray	=	new Array();
		var query = db.collection("WorkPermit").orderBy(orderColumn,orderOption);
		if(limit!=null && limit!=undefined && limit!="" && limit>0){
			query.limit(limit);
		}
		if(window.access["Business Owner"] || window.access["Admin"]){
			queryArray.push(query);
		}
		else if(window.access["BGHSEManager"]){
			queryArray.push(query.where("F006_EmailRequester_emailUri","==",window.loggedinuserEmailURI));
			queryArray.push(query.where("F009_ApproverEmail","==",window.useremailid));
			$.each(window.access["BGHSEManager"],function(index,site){
				queryArray.push(query.where("F014_SiteLocation_siteCode","==",site));
			});
		}
		else if(window.access["SiteOwner"]){
			queryArray.push(query.where("F006_EmailRequester_emailUri","==",window.loggedinuserEmailURI));
			queryArray.push(query.where("F009_ApproverEmail","==",window.useremailid));
			queryArray.push(query.where("F014_SiteLocation_siteCode","==",window.access["SiteOwner"]));
		}
		else if(window.access["Requester"]){
			queryArray.push(query.where("F006_EmailRequester_emailUri","==",window.loggedinuserEmailURI));
			queryArray.push(query.where("F009_ApproverEmail","==",window.useremailid));
		}
		else if(window.access["Approver"]){
			queryArray.push(query.where("F006_EmailRequester_emailUri","==",window.loggedinuserEmailURI));
			queryArray.push(query.where("F009_ApproverEmail","==",window.useremailid));
		}
		Do.validateAndDoCallback(callback(queryArray));
	}
	
	fetchWorkPermitList	=	function(query,callback){
		query.get().then(function(response){
			if(response!=null && response!=undefined && response!="" && response.size>0)
			{
				var docList	=	response.docs;
				Do.validateAndDoCallback(callback(docList));
			}
			else
			{
				Do.validateAndDoCallback(callback(null));
			}
		}).catch(function (err) {
	        console.log(err);
	        loader.hide();
	        showNotification("Error!","Something went wrong. Please contact Administrator","error");
	        Do.validateAndDoCallback(callback(null));
	    });
	}
})();