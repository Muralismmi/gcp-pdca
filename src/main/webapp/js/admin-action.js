$(document).on(
		"click",
		"#masterConfigurationSaveBtn",
		function() {
			
			validateField('masterConfiguration-form', function() {
				
			var id = $('#masterConfigurationSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var configurationValue = $('#masterConfiguration-value').val()
			if (configurationValue != undefined && configurationValue != '') {
				inputData.value = configurationValue;
			} else {

			}

			var configurationType = $('#masterConfiguration-type').val()
			if (configurationType != undefined && configurationType != '') {
				inputData.type = configurationType;
			} else {

			}
			var plantName = $('#masterConfiguration-plantName').val()
			if (plantName != undefined && plantName != '') {
				inputData.plant = plantName;
			} else {

			}

			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/saveConfiguration',
				async : false,
				datatype : 'JSON',
				data : {
					'configurationObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					debugger;
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!", response.MESSAGE,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#masterconfiguration-modal').hide();
						showNotification("Success!", "Updated Succesfully",
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});

		});


		});




$(document).on("click", ".editConfiguration-btn", function(e) {
	debugger;
	$('#masterconfiguration-modal').modal('show');
	bindAutoSearchEvent("masterConfiguration-value");
	$('#masterconfiguration-modal').find('h5').html('Edit Configuration');
	var data = $('#masterconfigurationtable').DataTable().row($(this).parents('tr')).data();
	$('#masterConfiguration-value').val(data.value);
	
	$('#masterConfiguration-plantName').html('<option value="'+data.plant+'" >' + data.plant+ '</option>').select2();
	$('#masterConfiguration-plantName').val( data.plant).trigger('change');
	$('#masterConfiguration-type').val(data.type);
	$('#masterConfiguration-modal').find('.error').removeClass('error');
	$('#masterConfigurationSaveBtn').attr("data-uniqueId", data.id);
});

$(document).on(
		"click",
		".deleteConfiguration-btn",
		function(e) {
			var data = $('#masterconfigurationtable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deleteconfiguration/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							
							window.location.reload();
							
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});


$(document).on(
		"click",
		"#plantSaveBtn",
		function() {
			
			
			validateField('plant-form', function() {
				
			var id = $('#plantSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var plantCode = $('#plant-plantCode').val()
			if (plantCode != undefined && plantCode != '') {
				inputData.code = plantCode;
			} else {

			}

			var plantName = $('#plant-plantName').val()
			if (plantName != undefined && plantName != '') {
				inputData.name = plantName;
			} else {

			}
			var plantLocation = $('#plant-location').val()
			if (plantLocation != undefined && plantLocation != '') {
				inputData.location = plantLocation;
			} else {

			}
			var plantregion = $('#plant-region').val()
			if (plantregion != undefined && plantregion != '') {
				inputData.region = plantregion;
			} else {

			}
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savePlant',
				async : false,
				datatype : 'JSON',
				data : {
					'plantObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					debugger;
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
$(document).on(
		"click",
		"#pillarSaveBtn",
		function() {
			
			
			validateField('pillar-form', function() {
				
			var id = $('#pillarSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var pillarType = $('#pillar-pillarType').val()
			if (pillarType != undefined && pillarType != '') {
				inputData.pillarType = pillarType;
			} else {

			}

			var pillarName = $('#pillar-pillarName').val()
			if (pillarName != undefined && pillarName != '') {
				inputData.name = pillarName;
			} else {

			}
			/*var plantLocation = $('#plant-location').val()
			if (plantLocation != undefined && plantLocation != '') {
				inputData.location = plantLocation;
			} else {

			}
			var plantregion = $('#plant-region').val()
			if (plantregion != undefined && plantregion != '') {
				inputData.region = plantregion;
			} else {

			}*/
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savePillar',
				async : false,
				datatype : 'JSON',
				data : {
					'pillarObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					debugger;
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
/*$(document).on(
		"click",
		"#areaSaveBtn",
		function() {
			validateField('area-form', function() {
			var id = $('#areaSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var areaPlant = $('#area-plantName').val();
			if (areaPlant != undefined && areaPlant != '') {
				inputData.plant = areaPlant;
				inputData.plantId = $('#area-plantId').val();
			} else {

			}

			var areaname = $('#area-areaName').val()
			if (areaname != undefined && areaname != '') {
				inputData.value = areaname;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savearea',
				async : false,
				datatype : 'JSON',
				data : {
					'AreaObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					debugger;
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", "Updated Succesfully",
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});*/
$(document).on("click", ".editPlant-btn", function(e) {
	debugger;
	$('#plant-modal').modal('show');
	//bindAutoSearchEvent("plant-plantCode");
	$('#plant-modal').find('h5').html('Edit Plant');
	var data = $('#plantable').DataTable().row($(this).parents('tr')).data();
	$('#plant-plantCode').val(data.code);
	$('#plant-plantName').val(data.name);
	$('#plant-location').val(data.location);
	$('#plant-region').val(data.region);
	$('#plant-modal').find('.error').removeClass('error');
	$('#plantSaveBtn').attr("data-uniqueId", data.id);
});
$(document).on("click", ".editPillar-btn", function(e) {
	debugger;
	$('#pillar-modal').modal('show');
	//bindAutoSearchEvent("plant-plantCode");
	$('#pillar-modal').find('h5').html('Edit Pillar');
	var data = $('#pillartable').DataTable().row($(this).parents('tr')).data();
	$('#pillar-pillarType').val(data.pillarType);
	$('#pillar-pillarName').val(data.name);
	$('#pillar-pillarName').val(data.name);
	/*$('#plant-location').val(data.location);
	$('#plant-region').val(data.region);*/
	$('#pillar-modal').find('.error').removeClass('error');
	$('#pillarSaveBtn').attr("data-uniqueId", data.id);
});
$(document).on(
		"click",
		"#areaSaveBtn",
		function() {
			console.log("save area")
			validateField('area-form', function() {
			var id = $('#areaSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var areaPlant = $('#area-plantName').val();
			if (areaPlant != undefined && areaPlant != '') {
				inputData.plant = areaPlant;
				inputData.plantId = $('#area-plantId').val();
			} else {

			}

			var areaname = $('#area-areaName').val()
			if (areaname != undefined && areaname != '') {
				inputData.value = areaname;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savearea',
				async : false,
				datatype : 'JSON',
				data : {
					'AreaObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});

$(document).on("click", ".editArea-btn", function(e) {
	debugger;
	
	$("#area-plantName").select2({
	    ajax: {
	    	url: function (params) {
	    		var partneridsearch = "";
	    		if(params.term && params.term!="")
	    			partneridsearch=params.term;
				elementid = $(this).prop("id").split("_")[0];
			      return '/fetchdataforautosuggest?fieldName=name&qstr='+partneridsearch+'&indexName=PLANT';
			    },
	        dataType: 'json',
	        type: "GET",
	        processResults: function (data,params) {
	        	globalData = data;
	        	$.unique(globalData);
	        	//var resultData = removeDuplicateObjects(globalData,elementid);
	            return {
	            	
	                results: $.map(globalData, function (item) {
	                	$('#area-plantId').val(item.split("&&&&")[1]);
	                    return {
	                        text: item.split("&&&&")[0],
	                        id: item.split("&&&&")[0]
	                    }
	                })
	            };
	        },
	        cache:false,
	    },
	    createTag: function (tag) {
	        if(globalData.length!=0)
	        return {};
	  },
	  tags:true,
	  escapeMarkup: function (markup) { return markup; } // let our custom formatter work
	});
	$('#area-modal').modal('show');
	//bindAutoSearchEvent("plant-plantCode");
	$('#area-modal').find('h5').html('Edit Area');
	var data = $('#areatable').DataTable().row($(this).parents('tr')).data();
	$('#area-areaName').val(data.value);
	 $('#area-plantId').val(data.plantId);
	//$('#area-plantName').html('<option value="'+data.plant+'" >' + data.plant+ '</option>').select2();
	$('#area-plantName').val( data.plant+"&&&&"+data.plantId).trigger('change');
	//$('#area-plantName').val(data.plant);
	/*$('#plant-location').val(data.location);
	$('#plant-region').val(data.region);*/
	$('#area-modal').find('.error').removeClass('error');
	$('#areaSaveBtn').attr("data-uniqueId", data.id);
});
$(document).on(
		"click",
		"#lineSaveBtn",
		function() {
			console.log("save area")
			validateField('line-form', function() {
			var id = $('#lineSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var areaPlant = $('#line-plantName').val();
			if (areaPlant != undefined && areaPlant != '') {
				inputData.plant = areaPlant;
				inputData.plantId = $('#line-plantId').val();
			} else {

			}

			var areaname = $('#line-lineName').val()
			if (areaname != undefined && areaname != '') {
				inputData.value = areaname;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/saveline',
				async : false,
				datatype : 'JSON',
				data : {
					'LineObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
$(document).on(
		"click",
		".deleteLine-btn",
		function(e) {
			var data = $('#linetable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deleteline/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		"#benefitSaveBtn",
		function() {
			validateField('benefit-form', function() {
			var id = $('#benefitSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var benefitname = $('#benefit-benefitName').val()
			if (benefitname != undefined && benefitname != '') {
				inputData.value = benefitname;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savebenefit',
				async : false,
				datatype : 'JSON',
				data : {
					'BenefitObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
$(document).on(
		"click",
		".deleteBenefit-btn",
		function(e) {
			var data = $('#benefittable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deletebenefit/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		"#toolSaveBtn",
		function() {
			validateField('tool-form', function() {
			var id = $('#toolSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var toolname = $('#tool-toolName').val()
			if (toolname != undefined && toolname != '') {
				inputData.value = toolname;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savetool',
				async : false,
				datatype : 'JSON',
				data : {
					'ToolObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
$(document).on(
		"click",
		".deleteTool-btn",
		function(e) {
			var data = $('#tooltable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deletetool/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		"#losstypeSaveBtn",
		function(e) {
			validateField('losstype-form', function() {
			var id = $('#losstypeSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};

			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var losstypename = $('#losstype-losstypeName').val()
			if (losstypename != undefined && losstypename != '') {
				inputData.value = losstypename;
			} else {

			}
		
			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;

			$.ajax({
				type : 'POST',
				url : '/savelosstype',
				async : false,
				datatype : 'JSON',
				data : {
					'LossTypeObj' : JSON.stringify(inputData)
				},
				success : function(data) {
					if (data == "error") {
						showNotification("ERROR!", "Please Try Again Later",
								"failure");
						return;
					}
					var response = JSON.parse(data);
					if (response.STATUS == "FAILURE") {
						console.log("response from here " + response);
						showNotification("ERROR!",response.MESSAGE ,
								"failure");
						return;
					} else if (response.STATUS == "SUCCESS") {
						$('#plant').hide();
						showNotification("Success!", response.MESSAGE,
								"success");
						setTimeout(function(){
							window.location.reload();
						}, 1000);
						console.log("succefully pushDatatoSearchIndex "
								+ response);
					}

				}
			});
			});
			

		});
$(document).on(
		"click",
		".deleteLosstype-btn",
		function(e) {
			var data = $('#losstypetable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deletelosstype/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		".deleteArea-btn",
		function(e) {
			var data = $('#areatable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deletearea/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		".deletePillar-btn",
		function(e) {
			var data = $('#pillartable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deletepillar/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});
$(document).on(
		"click",
		".deletePlant-btn",
		function(e) {
			var data = $('#plantable').DataTable().row($(this).parents('tr'))
					.data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/deleteplant/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							window.location.reload();
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}
		});

$(document).on(
		"click",
		"#reqRoleSaveBtn",
		function() {
			
			validateField('userrole-form', function() {
				 debugger;
			
			var id = $('#reqRoleSaveBtn').attr('data-uniqueId');
			var isSave = true;

			var inputData = {};


			if (id != undefined && id != '') {
				isSave = false;
				inputData.id = id;
			}

			var roles = [];
			var superAdmin = $('input[name="userrole-superAdmin"]:checked')
					.val();
			var creator = $('input[name="userrole-creator"]:checked').val();
			//var editor = $('input[name="userrole-editor"]:checked').val();
			var approver1 = $('input[name="userrole-approver1"]:checked').val();
			var approver2= $('input[name="userrole-approver2"]:checked').val();
			var approver3= $('input[name="userrole-approver3"]:checked').val();
			var plantManager = $('input[name="userrole-plantManager"]:checked')
					.val();
			if (superAdmin != undefined) {
				roles.push(superAdmin)
			}
			if (creator) {
				roles.push(creator)
			}
			/*if (editor) {
				roles.push(editor)
			}*/
			if (approver1) {
				roles.push(approver1)
			}
			if (approver2) {
				roles.push(approver2)
			}
			if (approver3) {
				roles.push(approver3)
			}
			if (plantManager) {
				roles.push(plantManager)
			}
			inputData.roles = roles;

			var userEmail = $('#userrole-userEmail').val()
			if (userEmail != undefined && userEmail != '') {
				inputData.userEmail = userEmail;
			} else {

			}

			var title = $('#userrole-title').val()
			if (title != undefined && title != '') {
				inputData.title = title;
			} else {

			}

			var firstName = $('#userrole-firstName').val()
			if (firstName != undefined && firstName != '') {
				inputData.firstName = firstName;
			} else {

			}

			var lastName = $('#userrole-lastName').val()
			if (lastName != undefined && lastName != '') {
				inputData.lastName = lastName;
			} else {

			}

			var userName = $('#userrole-userName').val()
			if (userName != undefined && userName != '') {
				inputData.userName = userName;
			} else {

			}

			/*var userName = $('#userName').val()
			if (userEmail != undefined && userEmail != '') {
				inputData.userEmail = userEmail;
			} else {

			}*/

			var plantId = $('#userrole-plantId').val()
			if (plantId != undefined && plantId != '') {
				inputData.plantId = plantId;
			} else {

			}
			var plantName = $('#userrole-plantName').val()
			if (plantName != undefined && plantName != '') {
				inputData.plantName = plantName;
			} else {

			}

			var plantId = $('#userrole-plantId').val()
			if (plantId != undefined && plantId != '') {
				inputData.plantId = plantId;
			} else {

			}
			var plantName = $('#userrole-plantName').val()
			if (plantName != undefined && plantName != '') {
				inputData.plantName = plantName;
			} else {

			}

			var pillarId = $('#userrole-pillarId').val()
			if (pillarId != undefined && pillarId != '') {
				inputData.pillarId = pillarId;
			} else {

			}

			var pillarName = $('#userrole-pillarName').val()
			if (pillarName != undefined && pillarName != '') {
				inputData.pillarName = pillarName;
			} else {

			}

			var plantName = $('#userrole-plantName').val()
			if (plantName != undefined && plantName != '') {
				inputData.plantName = plantName;
			} else {

			}
			
			var isMentor = $('input[name="userrole-mentor"]:checked').val()
			if (isMentor != undefined && isMentor != '') {
				inputData.mentor = true;
			} else {
				inputData.mentor = false;
			}
			
			var isEditor = $('input[name="userrole-editor"]:checked').val()
			if (isEditor != undefined && isEditor != '') {
				inputData.editor = true;
			} else {
				inputData.editor = false;
			}
			
			var isProjectLead = $('input[name="userrole-projectLead"]:checked').val()
			if (isProjectLead != undefined && isProjectLead != '') {
				inputData.projectLead = true;
			} else {
				inputData.projectLead = false;
			}
			
			var isPillarLead = $('input[name="userrole-pillarLead"]:checked').val()
			if (isPillarLead != undefined && isPillarLead != '') {
				inputData.pillarLead = true;
			} else {
				inputData.pillarLead = false;
			}

			if (isSave) {
				inputData.createdBy = userDetails.userEmail;
				// inputData.createdOn = new Date();
			}
			inputData.createdBy = userDetails.userEmail;
			inputData.lastUpdatedBy = userDetails.userEmail;
			// inputData.lastUpdatedOn = new Date();
			inputData.active = true;
			
			debugger;
				
				$.ajax({
					type : 'POST',
					url : '/saveUser',
					async : false,
					datatype : 'JSON',
					data : {
						'userObj' : JSON.stringify(inputData)
					},
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!", "Please Try Again Later",
									"failure");
							return;
						}
						debugger;
						cleanModal();
						var response = JSON.parse(data);
						if (response.STATUS == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!", "Please Try Again Later",
									"failure");
							return;
						} else if (response.STATUS == "SUCCESS") {
							$('#userrole').hide();
							showNotification("Success!", response.MESSAGE,
									"success");
							setTimeout(function(){
								window.location.reload();
							}, 1000);
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});
			});
			
		});


$(document).on(
		"click",
		".editUserRoleType-btn",
		function(e) {
			debugger;
			$('#userrole-modal').modal('show');
			//bindAutoSearchEvent("userrole-userEmail");
			$('#userrole-modal').find('input:not([type="checkbox"]),select')
					.val('');
			$('#userrole-modal').find('h5').html('Edit User Role');
			var data = $('#userrole-table').DataTable().row(
					$(this).parents('tr')).data();
			$('#userrole-userEmail').val(data.userEmail);
			$('#userrole-firstName').val(data.firstName);
			$('#userrole-lastName').val(data.lastName);
			$('#userrole-userName').val(data.userName);
			$('#userrole-pillarId').val(data.pillarId);
			//$('#userrole-pillarName').val(data.pillarName);
			$('#userrole-plantId').val(data.plantId);
			
			
		  	$('#userrole-pillarName').html('<option value="'+data.pillarName+'" >' + data.pillarName+ '</option>').select2();
			$('#userrole-pillarName').val( data.pillarName).trigger('change');
			

		  	$('#userrole-plantName').html('<option value="'+data.plantName+'" >' + data.plantName+ '</option>').select2();
			$('#userrole-plantName').val( data.plantName).trigger('change');
			
			
			//$('#userrole-plantName').val(data.plantName);
			if (data.roles.search("SUPERADMIN") > -1) {
				$('input[name="userrole-superAdmin"][value="SUPERADMIN"]')
						.prop('checked', true);
			}
			if (data.roles.search("CREATOR") > -1) {
				$('input[name="userrole-creator"][value="CREATOR"]').prop(
						'checked', true);
			}
			if (data.roles.search("EDITOR") > -1) {
				$('input[name="userrole-editor"][value="EDITOR"]').prop(
						'checked', true);
			}
			if (data.roles.search("PLANTMANAGER") > -1) {
				$('input[name="userrole-plantManager"][value="PLANTMANAGER"]')
						.prop('checked', true);
			}
			if (data.roles.search("APPROVER1") > -1) {
				$('input[name="userrole-approver1"][value="APPROVER1"]').prop(
						'checked', true);
			}
			if (data.roles.search("APPROVER2") > -1) {
				$('input[name="userrole-approver2"][value="APPROVER2"]').prop(
						'checked', true);
			}
			if (data.roles.search("APPROVER3") > -1) {
				$('input[name="userrole-approver3"][value="APPROVER3"]').prop(
						'checked', true);
			}
			if (data.editor=="true") {
				$('input[name="userrole-editor"][value="Editor"]').prop(
						'checked', true);
			}

			if (data.mentor=="true") {
				$('input[name="userrole-mentor"][value="Mentor"]').prop(
						'checked', true);
			}

			if (data.projectLead=="true") {
				$('input[name="userrole-projectLead"][value="Project Lead"]')
						.prop('checked', true);
			}

			if (data.pillarLead=="true") {
				$('input[name="userrole-pillarLead"][value="Pillar Lead"]').prop(
						'checked', true);
			}

			$("option[value='" + data.title + "']")
					.attr('selected', 'selected');

			$('#userrole-modal').find('.error').removeClass('error');
			$('#reqRoleSaveBtn').attr("data-uniqueId", data.id);
		});

$(document).on(
		"click",
		".deleteUserRoleType-btn",
		function(e) {
			var data = $('#userrole-table').DataTable().row(
					$(this).parents('tr')).data();
			if (data.id != undefined) {
				$.ajax({
					type : 'POST',
					url : '/delete/' + data.id,
					async : false,
					success : function(data) {
						if (data == "error") {
							showNotification("ERROR!",
									"Please Try Again Later", "failure");
							return;
						}
						debugger;
						var response = JSON.parse(data);
						if (response.Status == "FAILURE") {
							console.log("response from here " + response);
							showNotification("ERROR!",
									"User Already Exist", "failure");
							return;
						} else if (response.Status == "SUCCESS") {
							$('#userrole').hide();
							window.location.reload();
							showNotification("Deleted!", "Deleted Succesfully",
									"success");
							console.log("succefully pushDatatoSearchIndex "
									+ response);
						}

					}
				});

			}

		});
