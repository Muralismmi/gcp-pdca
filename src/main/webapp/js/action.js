   var loggedinuserEmailURI="";
   var authDomain	=	"";
   var projectId	=	"";
   var apiKey	=	"";
   var loader = $('.loader-wrapper');
   
   var requestPillarLead;
   
   function changeName($selector,arr,addText){

       var formA=addText+"TEST-A";
       var formB=addText+"TEST-B";
       var formC=addText+"TEST-C";

       if(arr){
           $selector.each(function(){
               if($(this).html().includes("FORM-A")){
                   $(this).html($(this).html().replace("FORM-A",formA));
               }else if($(this).html().includes("Form-2")){
                   $(this).html($(this).html().replace("Form-2",formB));
               }else if($(this).html().includes("FORM-C")){
                   $(this).html($(this).html().replace("FORM-C",formC));
               }
           });
       }else{
           if($selector.html().includes("Form-1")){
               $selector.html($selector.html().replace("Form-1",formA));
           }else if($selector.html().includes("Form-2")){
               $selector.html($selector.html().replace("Form-2",formB));
           }else if($selector.html().includes("FORM-C")){
               $selector.html($selector.html().replace("FORM-C",formC));
           }
       }
   }
  
   if(userDetails != undefined && userDetails.roles.indexOf("SUPERADMIN") > -1) {
	   $('#userRole-Item').css("display", "block");
	   $('#plant-Item').css("display", "block");
	   $('#masterConfiguration-Item').css("display", "block");
	   $('#linemasterdata-Item').css("display", "block");
	   $('.createPDCA').css("display", "block");
	   $('.viewPDCA').css("display", "block");
	   $('.myAction').css("display", "block");
	   $('.editPDCA').css("display", "block");
   } if(userDetails != undefined && userDetails.roles.indexOf("CREATOR") > -1) {
	   $('.createPDCA').css("display", "block");
	   $('.editPDCA').css("display", "block");
	   $('#userrole-superAdmin').css("display", "none");
	   $('.viewPDCA').css("display", "block");
	   $('.myAction').css("display", "block");
   } if(userDetails != undefined && userDetails.roles.indexOf("PLANTMANAGER") > -1) {
	   $('.createPDCA').css("display", "block");
	   $('.editPDCA').css("display", "block");
	   $('.viewPDCA').css("display", "block");
	   $('.myAction').css("display", "block");
	   $('#userRole-Item').css("display", "block");
   } if(userDetails != undefined && userDetails.roles.indexOf("APPROVER") > -1) {
	   $('.viewPDCA').css("display", "block");
	   $('.myAction').css("display", "block");
   }
   
var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera());
    }
};

/*var errorPage = function(){
	$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
		'<div class="Aligner-item Aligner-item--fixed">'+
			'<div class="Demo" style="text-align: center;">'+
				'<img src="/images/error-icon.png">'+
				'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
					'<b>Oops! </b>'+
				'</p>'+
				'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">You Dont have access to the application </p>'+
				'<div style="margin: 20px 0px;">'+
        		'<a href="/">'+
        			'<img src="/images/logo-valeo-2.png" style="width: 50px;">'+
        		'</a>'+
	        '</div>'+
       '</div>'+
    '</div>');
}*/


var slider	=	function(){
	//rotation speed and timer
    var speed = 5000;
    
    console.log("into slider");
    window.runslider = setInterval(rotate, speed);
    var slides = $('.slide');
    var container = $('#slides ul');
    var elm = container.find(':first-child').prop("tagName");
    var item_width = container.width();
    var previous = 'prev'; //id of previous button
    var next = 'next'; //id of next button
    slides.width(item_width); //set the slides to the correct pixel width
    container.parent().width(item_width);
    container.width(slides.length * item_width); //set the slides container to the correct total width
    container.find(elm + ':first').before(container.find(elm + ':last'));
    resetSlides();
    console.log("into slider");
    
    //if user clicked on prev button
    
    $('#buttons a').click(function (e) {
        //slide the item
        
        if (container.is(':animated')) {
            return false;
        }
        if (e.target.id == previous) {
            container.stop().animate({
                'left': 0
            }, 1500, function () {
                container.find(elm + ':first').before(container.find(elm + ':last'));
                resetSlides();
            });
        }
        
        if (e.target.id == next) {
            container.stop().animate({
                'left': item_width * -2
            }, 1500, function () {
                container.find(elm + ':last').after(container.find(elm + ':first'));
                resetSlides();
            });
        }
        
        //cancel the link behavior
        return false;
        
    });
    
    //if mouse hover, pause the auto rotation, otherwise rotate it
    container.parent().mouseenter(function () {
        clearInterval(runslider);
    }).mouseleave(function () {
    	runslider = setInterval(rotate, speed);
    });
    
    
    function resetSlides() {
    	 console.log("reset slider");
        //and adjust the container so current is in the frame
        container.css({
            'left': -1 * item_width
        });
    }
}
var rotate	=	function() {
	 console.log("into rotate");
    $('#next').click();
}
function checkfile(sender) {
    var validExts = new Array(".xlsx", ".xls");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      return false;
    }
    else return true;
}

//
$(document).on('click','#masterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadmasterform')[0];
     var fileName = $('#bulkuploadmasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","BULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadmasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#masterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});

$(document).on('click','#pillarmasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadpillarmasterform')[0];
     var fileName = $('#bulkuploadpillarmasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","PILLARBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadpillarmasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#pillarmasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});

$(document).on('click','#linemasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadlinemasterform')[0];
     var fileName = $('#bulkuploadlinemasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","LINEBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadlinemasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#linemasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});
$(document).on('click','#areamasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadareamasterform')[0];
     var fileName = $('#bulkuploadareamasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","AREABULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadareamasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#areamasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});
$(document).on('click','#benefitmasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadbenefitmasterform')[0];
     var fileName = $('#bulkuploadbenefitmasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","BENEFITBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadbenefitmasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#benefitmasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});
$(document).on('click','#toolmasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadtoolmasterform')[0];
     var fileName = $('#bulkuploadtoolmasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","TOOLBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadtoolmasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#toolmasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});
$(document).on('click','#losstypemasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadlosstypemasterform')[0];
     var fileName = $('#bulkuploadlosstypemasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","LOSSTYPEBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadlosstypemasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#losstypemasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});
$(document).on('click','#usermasterbulkSaveBtn',function() 
		{	
			
	 event.preventDefault();
     // Get form
     var form = $('#bulkuploadusermasterform')[0];
     var fileName = $('#bulkuploadusermasterinput').val();
		// Create an FormData object
     var data = new FormData(form);
     data.append("RefId","");
     data.append("typeofFile","USERBULKUPLOADCONFIG");
     data.append("fileName",fileName);
     $.ajax({
         type: "POST",
         url:  $('#bulkuploadusermasterform').attr('action'),
         data: data,
         processData: false,
         contentType: false,
         cache: false,
         timeout: 600000,
         success: function (data) {

         		var response = JSON.parse(data);
         		if(response.Status=="Success"){
         			if(response.Message!=null && response.Message!=undefined && response.Message!="")
         			{
         				$('#usermasterbulk-modal')
						.modal('hide');
         				showNotification("Success",response.Message,"success");
         			}
         		}else{
         			showNotification("Error","Something went wrong please contact administrator","error");
         		}
         },
         error: function (e) {
             console.log("ERROR : Please Contact Administrator", e);
         }
     });
	
		});


$(document).on('click','#annotate-add-btn',function() 
{	
	$("#myCanvas").annotate("export", {
		type: "image/jpeg",	// ex: "image/png"
		quality: 0.75		// Available only for "image/jpeg"
	});
	loader.show();
	var annotateImgDivId = $('#annotate-modal').attr('data-id');
    uploadEvidenceImg(annotateImgDivId,function()
    {
    	
    	 loader.hide();
    });
});

$(document).on('click','.annotatedimage-clear',function()
{
	$(this).parent().remove();
});

$(document).on('click','.perviewImage',function()
{
	var imgURl = $(this).parent().find('img').attr('src');
	$('#annotatePerview-modal').modal('show');
	$('#annotatePerview-image').attr('src',imgURl);
});

$('#rejectpermitComments').keyup(function()
		{
	var rejectcomments = $(this).val();
	if(rejectcomments.trim()==""){
		$('#rejectpermitmodalbtn').attr('disabled',true);
	}else{
		$('#rejectpermitmodalbtn').attr('disabled',false);
	}
	
});

/*$(document).on('keyUp','#rejectpermitComments',function()
		{
			var rejectcomments = $(this).val();
			if(rejectcomments.trim()==""){
				$('#rejectpermitmodalbtn').attr('disabled',true);
			}else{
				$('#rejectpermitmodalbtn').attr('disabled',false);
			}
			
		});*/

function generateuploadurl(id){
	$.ajax({
        url: '/attachments/getuploadurlforattachment',
        type: 'POST'
    })
    .done(function(data)
    {
    	$(id).attr('action',data);
    })
    .fail(function()
    {
   	 bootbox.alert({
          		title:'<b>Information</b>',
          		message:'Error occured while saving contact Admin.'
          });
    })
}
function generateuploadurl1(id){
	$.ajax({
        url: '/attachments/getuploadurlforattachment1',
        type: 'POST'
    })
    .done(function(data)
    {
    	$(id).attr('action',data);
    })
    .fail(function()
    {
   	 bootbox.alert({
          		title:'<b>Information</b>',
          		message:'Error occured while saving contact Admin.'
          });
    })
}
$(document).ajaxStart(function() 
{
	 loader.show();
});

$(document).ajaxStop(function() 
{
	 loader.hide();
});




$(document).ready(function(){
	  
	buildformselectpopup();
	
	
	window.access	=	{};
	
	/*$(document).on('keyup','.number',function(){
		var val	=	$(this).val();
		if(val!=undefined && val!=null &&val!=""){
			this.value = this.value.replace(/[^0-9+]/g, '');
		}
	});*/
	$('.number').keypress(function(event) {
	    var $this = $(this);
	    if ((event.which != 46 || $this.val().indexOf('.') != -1) &&
	       ((event.which < 48 || event.which > 57) &&
	       (event.which != 0 && event.which != 8))) {
	           event.preventDefault();
	    }

	    var text = $(this).val();
	    if ((event.which == 46) && (text.indexOf('.') == -1)) {
	        setTimeout(function() {
	            if ($this.val().substring($this.val().indexOf('.')).length > 3) {
	                $this.val($this.val().substring(0, $this.val().indexOf('.') + 3));
	            }
	        }, 1);
	    }

	    if ((text.indexOf('.') != -1) &&
	        (text.substring(text.indexOf('.')).length > 2) &&
	        (event.which != 0 && event.which != 8) &&
	        ($(this)[0].selectionStart >= text.length - 2)) {
	            event.preventDefault();
	    }      
	});

	$('.number').bind("paste", function(e) {
	var text = e.originalEvent.clipboardData.getData('Text');
	if ($.isNumeric(text)) {
	    if ((text.substring(text.indexOf('.')).length > 3) && (text.indexOf('.') > -1)) {
	        e.preventDefault();
	        $(this).val(text.substring(0, text.indexOf('.') + 3));
	   }
	}
	else {
	        e.preventDefault();
	     }
	});
	
	$('.clearmodalbtn').click(function() 
   {
		if($(this).closest('.modal').attr('id')!=undefined && $(this).closest('.modal').attr('id')!=null && $(this).closest('.modal').attr('id')!="" && $(this).closest('.modal').attr('id')=="bghsemanager-modal")
		{
			$(this).closest('.modal').find('input:not([type="radio"],[type="checkbox"],:disabled),select,textarea').val("");
			$(this).closest('.modal').find('input[type="radio"],input[type="checkbox"],:disabled').prop('checked',false);
			$(this).closest('.modal').find('select').val("").trigger('change');
			//attachdesign
			$(this).closest('.modal').find('.attachdesign').html('');
		}
		else
		{
			$(this).closest('.modal').find('input:not([type="radio"],[type="checkbox"]),select,textarea').val("");
			$(this).closest('.modal').find('input[type="radio"],input[type="checkbox"]').prop('checked',false);
			$(this).closest('.modal').find('select').val("").trigger('change');
			//attachdesign
			$(this).closest('.modal').find('.attachdesign').html('');
		}
	});
	
	
	$(document).on('focus','input,textarea',function(){
		$(this).removeClass('error');
	});
	
	$(document).on('change','input[type="checkbox"],input[type="radio"]',function(){
		$(this).closest('.form-inline').find('fieldset').removeClass('error');
	});
	

	$(document).on('change','select',function(){
		$(this).closest('div').find('.select2-container--bootstrap').removeClass('error');
	});
	if(location.hash == "")
		location.hash = "#home"
			
			// Panel toolbox
			$('body').on('click', '.collapse-link',function() {
		        var $BOX_PANEL = $(this).parent().closest('.x_panel'),
		            $ICON = $(this),
		            $BOX_CONTENT = $BOX_PANEL.find('.x_content');
		        
		        // fix for some div with hardcoded fix class
		        if ($BOX_PANEL.attr('style')) {
		            /*$BOX_CONTENT.slideToggle(200, function(){
		                $BOX_PANEL.removeAttr('style');
		            });*/
		        	$(this).parent().next('blockquote').slideToggle(200, function(){
		                $BOX_PANEL.removeAttr('style');
		            });
		        } else {
		            /*$BOX_CONTENT.slideToggle(200); */
		        	$(this).parent().next('blockquote').slideToggle(200); 
		            $BOX_PANEL.css('height', 'auto');  
		        }

		       $ICON.toggleClass('fa-chevron-down fa-chevron-up');
		    });
	$('body').popover({
	       /*placement: 'top',*/
	       container: 'body',
	       trigger: 'hover',
	       html: true,
	       animation: false,
	       selector: '[data-toggle="popover"]'
	   });
	$('#selectWkType-saveBtn').click(function () 
			{
				loader.show();
				 $('#workpermit-form').attr('data-id','');
				var val	=	$('input[name="selectWorkPermitType"]:checked').val();
				var id	=	$('input[name="selectWorkPermitType"]:checked').attr('id');
				$('#chooseworktype-modal').modal('hide');
				location.hash = "#workpermitform/"+encodeURIComponent(val)+"/"+encodeURIComponent(id);
			});
	$(document).on('click','.closesign',function(){	
		$(this).next('img').attr('data-url',"");
		$(this).next('img').attr('src',"");
		$(this).parent().hide();
		$(this).parent().parent().find('button').show();
		
	});
	
	if($(window).width() < '500'){
		var fixmeTop = $('.fixme').offset().top;
		$(window).scroll(function() {
		    var currentScroll = $(window).scrollTop();
		    if (currentScroll >= 63) {
		        $('.fixme').css({
		            position: 'fixed',
		            top: '0',
		            left: '0',
		            zIndex : '2',
		            padding : '5px 10px',
		            background : '#fff',
		            width:'100%'
		        });
		    } else {
		        $('.fixme').css({
		            position: 'static',
		            padding : '0'
		        });
		    }
		});
		}
});

//a simple function to click next link
//a timer will call this function, and the rotation will begin


$(window).on('hashchange',function(){
	hashchange();
});
$(window).on('load',function(){
	hashchange();
});
function getLogo(formType){
	var loglURL="";
	console.log("*************************** "+formType)
	switch(formType.trim()) {
	  case "Form-1":
		  loglURL = "/images/pdca-method-deming-wheel.jpg"; 
	    break;
	  case "Form-2":
		  loglURL ="/images/pdca-plan-management-process-lean.jpg";
	    break;
	  case "FORM-C":
		  loglURL = "/images/pdcalogo.jpg";
	    break;
	  case "FORM-D":
		  loglURL = "/images/pdcalogo.jpg";
	    break;
	  default:
		  loglURL="images/error-icon.png";
	    // code block
	}
	return loglURL;
}
function buildformselectpopup(){
	var fromselectpopuphtml ="";
	
	
	$.ajax({
        type: 'GET',
        url: '/getFormTypes',
        async:false,
        datatype:'JSON',
        success: function (data) 
        {
                var response = JSON.parse(data);
                 if(response!=null && response.length>0){
                	 
                	 for(var i in response){
                		 console.log(response[i].uniqueId)
                		 fromselectpopuphtml+='<fieldset class="form-group"><input name="selectWorkPermitType" id="'+response[i].uniqueId+'"type="radio" value="'+response[i].workPermitType+'"> <label for="'+response[i].uniqueId+'">'+response[i].workPermitType+'</label></fieldset>';
                		 }
                	 
                	 $('#formselectpopup').html(fromselectpopuphtml);
//                     
//                     var formA="TEST-A";
//                     var formB="TEST-B";
//                     var formC="TEST-C";
//                     $("#chooseworktype-modal").find("label").each(function(){
//                      if($(this).html() == "FORM-A"){
//                         $(this).html(formA);
//                       }else if($(this).html() == "Form-2"){
//                       $(this).html(formB)
//                     }else if($(this).html("FORM-C")){
//                       $(this).html(formC)
//                     }
//                     });
                 }
                 
        }
        });
	
	
}


function hashchange(){
	$('.content-wrapper section').hide();
	var hashurl=location.hash;
	var accessValid = validateUserAccess(hashurl.substring(1, hashurl.length));
	if(accessValid){
		clearInterval(window.runslider);
		$('#demoteCommentstext').text("");
		$('#demotecommentsdiv').hide();
		$('#rejectcommentsdiv').hide();
		$('#rejectcommentsdiv').text("");
		
		
		if(location.hash == '#home'){
			$('footer').removeClass('blue');
			$('#main-hmenu').hide();
			$('.content-wrapper').css('min-height','calc(100vh - 131px)');
			$('#header-nav').removeClass('navbar-light').addClass('navbar-dark');
			$('#sidebarCollapse').css('color','#fff');
			
		}
		else{
			$('footer').addClass('blue');
			$('#main-hmenu').show();
			$('.content-wrapper').css('min-height','calc(100vh - 188px)');
			$('#header-nav').removeClass('navbar-dark').addClass('navbar-light');
			$('#sidebarCollapse').css('color','#000000e6');
			$('#slides ul').stop();
		}
		
		if(location.hash == '#home' || location.hash.trim() ==""){
			$('#home-section').show();
		}
		else if(location.hash=="#PDCAprintView")
		{
			$('#PDCAprintView').show();
		}
		else if(location.hash=="#error")
		{
			$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
	  		'<div class="Aligner-item Aligner-item--fixed">'+
	  			'<div class="Demo" style="text-align: center;">'+
	  				'<img src="/images/error-icon.png">'+
	  				'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
	  					'<b>Oops! </b>'+
	  				'</p>'+
	  				'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">You Dont have access to the application </p>'+
	  				'<div style="margin: 20px 0px;">'+
		        		'<a href="/">'+
		        			'<img src="/images/pdcalogo.png" style="width: 50px;">'+
		        		'</a>'+
			        '</div>'+
		       '</div>'+
		    '</div>');
		}
		else if(location.hash == '#userrole'){
			$('.content-wrapper section').hide();
			$('#userrole').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildUserRoleList();
			$("#userrole-plantName").select2({
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
			                	$('#userrole-plantId').val(item.split("&&&&")[1]);
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
			  tabIndex:0,
			  tags:true,
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			});
			
			$("#userrole-pillarName").select2({
			    ajax: {
			    	url: function (params) {
			    		var partneridsearch = "";
			    		if(params.term && params.term!="")
			    			partneridsearch=params.term;
						elementid = $(this).prop("id").split("_")[0];
					      return '/fetchdataforautosuggestfromConfiguration?fieldName=name&qstr='+partneridsearch+'&indexName=PILLAR&active=true';
					    },
			        dataType: 'json',
			        type: "GET",
			        processResults: function (data,params) {
			        	globalData = data;
			        	$.unique(globalData);
			        	//var resultData = removeDuplicateObjects(globalData,elementid);
			            return {
			            	
			                results: $.map(globalData, function (item) {
			                	$('#userrole-pillarId').val(item.split("&&&&")[1]);
			                	console.log("item  from here"+item);
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
			  tabIndex:0,
			  tags:true,
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			});
			
			}else if(location.hash.indexOf('#workpermitform')!=-1)
			{
				window.fieldConfigList=new Array();
				 window.variableFieldConfigurations = new Array();
				 $('#submitpdca-btn').hide();
				 $('#savepdca-btn').show();
				 $('#approvewokpermit-btn').hide();
				 $('#rejectwokpermit-btn').hide();
				 $('#viewpdf-btn').hide();
				 //approvewokpermit-btn
				 // rejectwokpermit-btn
				 //submitpdca-btn
				 //savepdca-btn
				if(document.URL.indexOf("/")!=-1)
				{
					var workPermittype =	document.URL.split("/")[4];
					var workPermittypeId =	document.URL.split("/")[5];
					if(workPermittype!=null && workPermittype!=undefined && workPermittype!="" && workPermittypeId!=null && workPermittypeId!=undefined && workPermittypeId!="")
					{
						
						loader.show();
						
								fetchWorkPermitTypeFieldConfigBySection(workPermittypeId,function(response){
									if(response!=null && response!=undefined &&response!="")
									{
										var sectionArr	=	response.sectionArr;
										var sectionWiseMap=response.sectionWiseMap;
										buildWorkPermitForm(sectionArr,sectionWiseMap,null,true,function()
										{
											setButtonVisibility({status:""},function()
											{
												setFormFieldValidationByStatus({status:"Draft"},function()
												{
														$('.content-wrapper section').hide();
														$('#workpermitform').show();
														$('.navbarMenu ul > li').removeClass('active');
													    $('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
													    $('#workmonitoring-maindiv').hide();
													    loader.hide();
													    var companyElements =$("input[data-fieldtype='NUMBER']");
													    for( i in companyElements){
													    if(companyElements[i].id && companyElements[i].id!=""){
													    $('#'+companyElements[i].id).parent().find('label').after('<span style="font-size: 12px;">[INR]</span>');
													    }
													    }
												});
											});
									    });
//										changeName($("#workpermithead"),false,"");
									}
							});
						
						
					}
					else
					{
						showNotification("Error!","Something went wrong. Please contact Administrator","error");
					}
				}
			
				}
		else if(location.hash == '#plant'){
			$('.content-wrapper section').hide();
			$('#plant').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildPlantList();
			}
		else if(location.hash == '#PILLAR'){
			$('.content-wrapper section').hide();
			$('#pillar').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildPillarList();
			}
		else if(location.hash == '#AREA'){
			$('.content-wrapper section').hide();
			$('#area').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildAreaList();
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
			}
		else if(location.hash == '#LINE'){
			$('.content-wrapper section').hide();
			$('#line').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildLineList();
			$("#line-plantName").select2({
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
			}	else if(location.hash == '#BENEFITTYPE'){
				$('.content-wrapper section').hide();
				$('#benefit').show();
				$('.navbarMenu ul > li').removeClass('active');
				fetchAndbuildBenefitList();
				}
		else if(location.hash == '#TOOL'){
		$('.content-wrapper section').hide();
		$('#tool').show();
		$('.navbarMenu ul > li').removeClass('active');
		fetchAndbuildToolList();
		}
		else if(location.hash == '#LOSSTYPE'){
			$('.content-wrapper section').hide();
			$('#losstype').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildlosstypeList();
			}
	/*	else if(location.hash == '#LOSSTYPE' ||location.hash == '#LINE' ||location.hash == '#AREA' ||location.hash == '#TOOL' ||location.hash == '#BENEFITTYPE'){
			$('.content-wrapper section').hide();
			$('#masterConfiguration').show();
			$('.navbarMenu ul > li').removeClass('active');
			var configtype=location.hash.split("#")[1];
			$('#masterConfiguration-type').val(configtype);
			console.log(configtype);
			fetchAndbuildMasterConfigurationList(configtype);
			
			$("#masterConfiguration-plantName").select2({
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
			                	$('#userrole-plantId').val(item.split("&&&&")[1]);
			                	console.log("item  from here"+item);
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
			}*/
		
		else if(location.hash == '#viewPDCA'){
			$('.content-wrapper section').hide();
			$('#viewPDCA').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildViewPDCAList();
			}
		else if(location.hash == '#myAction') {
			$('.content-wrapper section').hide();
			$('#myAction').show();
			$('.navbarMenu ul > li').removeClass('active');
			fetchAndbuildMyActionList();
			}
		else if(location.hash.indexOf('#pdca') >  -1){
			$('.content-wrapper section').hide();
			var pdcatype =	document.URL.split("/")[4];
			var pdcaId =	document.URL.split("/")[5];
			$.ajax({
			       type: 'GET',
			       url: '/getrequest',
			       async:false, data:{'requestid':pdcaId},
			       datatype:'JSON',
			       success: function (data) 
			       {
			              var dataResponse = JSON.parse(data);
			              request=dataResponse;
			              if(dataResponse.PILLARLEAD && dataResponse.PILLARLEAD!=null){
			            	  requestPillarLead=dataResponse.PILLARLEAD;
			              }
			              console.log("response from here pushDatatoSearchIndex "+dataResponse.length);
			                if(document.URL.indexOf("/")!=-1)
							{
								if(dataResponse.data.status=="DRAFT"){
									$('#submitpdca-btn').show();
									 $('#savepdca-btn').show();
									 $('#approvewokpermit-btn').hide();
									 $('#rejectwokpermit-btn').hide();
									 $('#viewpdf-btn').hide();
								}
								 if(dataResponse.data.status.indexOf("WAITING FOR APPROVAL") !=-1){
										$('#submitpdca-btn').hide();
										 $('#savepdca-btn').hide();
										 $('#approvewokpermit-btn').show();
										 $('#rejectwokpermit-btn').show();
										 $('#viewpdf-btn').show();
									}
								 if(dataResponse.data.status == "APPROVED" || dataResponse.data.status == "REJECTED"){
										$('#submitpdca-btn').hide();
										 $('#savepdca-btn').hide();
										 $('#approvewokpermit-btn').hide();
										 $('#rejectwokpermit-btn').hide();
										 $('#viewpdf-btn').show();
									}
								
								var statuslist = ["DRAFT","APPROVED","REJECTED","DELETED","WAITING FOR APPROVAL1","WAITING FOR APPROVAL2","WAITING FOR APPROVAL3"];
								if(statuslist.indexOf(dataResponse.data.status) == -1){
										$('#submitpdca-btn').hide();
										 $('#savepdca-btn').hide();
										 $('#approvewokpermit-btn').hide();
										 $('#rejectwokpermit-btn').hide();
										 $('#viewpdf-btn').hide();
										 showNotification("Error","INVALID STAGE OF REQUEST, Please contact Administrator","error");
									}
								if(pdcatype!=null && pdcatype!=undefined && pdcatype!="" && pdcaId!=null && pdcaId!=undefined && pdcaId!="")
								{
									
									loader.show();
									if(dataResponse.data.status=="DRAFT"){

													fetchWorkPermitTypeFieldConfigBySectionEdit(pdcaId,function(response){
														if(response!=null && response!=undefined &&response!="")
														{
															var sectionArr	=	response.sectionArr;
															var sectionWiseMap=response.sectionWiseMap;
															var data=dataResponse.data;
															var variableFieldConfigurations = JSON.parse(dataResponse.data.variableFieldData.value)
															delete dataResponse.data.variableFieldData;
															
															buildWorkPermitForm(sectionArr,sectionWiseMap,variableFieldConfigurations,false,function()
																		{
																setValuesToFields(data,sectionWiseMap,function(){
																	setValuesToFields(variableFieldConfigurations,sectionWiseMap,function(){
																	setButtonVisibility(data,function(){
																			setFormFieldValidationByStatus(data,function()
																			{
																				
																					$('.content-wrapper section').hide();
																					$('#workpermitform').show();
																					$('.navbarMenu ul > li').removeClass('active');
																				    $('a.nav-link[id="#workpermitform-nav"]').parent().addClass('active');
																				    $('#workmonitoring-maindiv').hide();
																				    var companyElements =$("input[data-fieldtype='NUMBER']");
																				    for( i in companyElements){
																				    if(companyElements[i].id && companyElements[i].id!=""){
																				    $('#'+companyElements[i].id).parent().find('label').after('<span style="font-size: 12px;">[INR]</span>');
																				    }
																				    }
																				    loader.hide();
																			});
																		});
																	 });  }); 
																});
														}
												});
													 $("input").attr("maxlength", 50);
													 $("textarea").attr("maxlength", 1000);
									}else{
										
										
										if(dataResponse.data.formType =="Form-1"){
											var formA = prepareArrayforA();
										}
										else if(dataResponse.data.formType =="Form-2"){
											var formA = prepareArrayforB();
										}else if(dataResponse.data.formType =="FORM-C"){
											var formA = prepareArrayforC();
										}else{
											var formA = prepareArrayforD();
										}
										var tableContent =generatetableforprintview(dataResponse.data);
										var tableContent1 =generateTableForStartDateView(dataResponse.data);
										prepareFormView(formA,dataResponse.data,tableContent,tableContent1);
										$('#PDCAprintView').show();
										
									}
								}
								else
								{
									showNotification("Error!","Something went wrong. Please contact Administrator","error");
								}
							}
			                               
			       }
			       });
			
			$('.navbarMenu ul > li').removeClass('active');
			//fetchAndbuildViewPDCAList();
			}
		else{
			$('.navbarMenu ul > li').removeClass('active');
			$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
			  		'<div class="Aligner-item Aligner-item--fixed">'+
			  			'<div class="Demo" style="text-align: center;">'+
			  				'<img src="/images/error-icon.png">'+
			  				'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
			  					'<b>Oops! </b>'+
			  				'</p>'+
			  				'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">Page Not found </p>'+
			  				'<div style="margin: 20px 0px;">'+
				        		'<a href="/">'+
				        			'<img src="/images/pdcalogo.png" style="width: 50px;">'+
				        		'</a>'+
					        '</div>'+
				       '</div>'+
				    '</div>');
		}
	}else{
		$('.navbarMenu ul > li').removeClass('active');
		$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
		  		'<div class="Aligner-item Aligner-item--fixed">'+
		  			'<div class="Demo" style="text-align: center;">'+
		  				'<img src="/images/error-icon.png">'+
		  				'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
		  					'<b>Oops! </b>'+
		  				'</p>'+
		  				'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">You do not have Access.</p>'+
		  				'<div style="margin: 20px 0px;">'+
			        		'<a href="/">'+
			        			'<img src="/images/pdcalogo.png" style="width: 50px;">'+
			        		'</a>'+
				        '</div>'+
			       '</div>'+
			    '</div>');
	}
	
	}
var request;
var adminObjects = new Array();
function pushDatatoSearchIndex(inputObj,indexName,textFields,dateFields,idFieldName,callback){
	var datatobeSent ={};
	datatobeSent["inputObj"] = inputObj;
	datatobeSent["indexName"] = indexName;
	datatobeSent["textFields"] = textFields;
	datatobeSent["dateFields"] = dateFields;
	datatobeSent["idFieldName"] = idFieldName;
	
	$.ajax({
        type: 'POST',
        url: '/createEntryInSearchIndex',
        async:false,
        datatype:'JSON',
        data:{'inputObj':JSON.stringify(inputObj),'indexName':indexName,'textFields':JSON.stringify(textFields),'dateFields':JSON.stringify(dateFields),'idFieldName':idFieldName},
        success: function (data) 
        {
        	var response = JSON.parse(data);
        	 console.log("response from here pushDatatoSearchIndex "+response);
             if(response.Status == "Failure"){
           	  console.log("response from here "+response);
          		 return;
             }
             else if(response.Status == "Success"){
            	 console.log("succefully pushDatatoSearchIndex "+response);
             }
             Do.validateAndDoCallback(callback);
        }
	});
}
function notifyContractoronSubmit(inputObj){
	var datatobeSent ={};
	datatobeSent["object"] = inputObj;
	
	
	$.ajax({
        type: 'POST',
        url: '/workpermit/notifycontractoronSubmit',
        async:false,
        datatype:'JSON',
        data:{'object':JSON.stringify(inputObj)},
        success: function (data) 
        {
        	var response = JSON.parse(data);
        	 console.log("response "+response);
             if(response.Status == "Failure"){
           	  console.log("response from here "+response);
          		 return;
             }
             else if(response.Status == "Success"){
            	 console.log("Notified to Contractor "+response);
             }
           
        }
	});
}
function Generator() {};

Generator.prototype.rand =  Math.floor(Math.random() * 26) + Date.now();

Generator.prototype.getId = function() {
return this.rand++;
};
	function getDateFromMillisec (millisec)
    {
    	if(millisec!=null && millisec!="" && millisec!=0)
    	{
    		var dates=new Date(millisec);
        	dates = dates.toLocaleDateString();
            return dates;
    	}
    	else
    	{
    		return "";
    	}	
    } 
	function truncateDate(date) {
		  return new Date(date.getFullYear(), date.getMonth(), date.getDate());
		}
	
	function getAppUrl() 
	{
		var urlType ;
		var url = document.URL;
		if( url.indexOf("valeo-cp0877-acp2.appspot.com")!=-1 || url.indexOf("valeo-cp0877-dev.appspot.com")!=-1 || url.indexOf("localhost")!=-1)
		{
			urlType = "Test_Domain";
		}
		else
		{
			urlType = "Prod_Domain";
		}	
		return urlType;
	}

	$(document).on("click",".signpopup-btn",function()
	{
		$('#signature-modal').modal('show');
		
		var permitTypeMode = $(this).attr('data-permity-mode');
		if(permitTypeMode == 'monitoring'){
			initializeSignature("signature-pad", $(this).attr('id'), permitTypeMode);
		}else {
			generateuploadurl('#'+$(this).next('.sign-contentimage').attr('id'));
			initializeSignature("signature-pad", $(this).attr('id'), undefined);
		}
	    
	});
	
	$(document).on("change","#bulkuploadmasterinput",function(){
		generateuploadurl1("#bulkuploadmasterform");
	});
	$(document).on("change","#bulkuploadusermasterinput",function(){
		generateuploadurl1("#bulkuploadusermasterform");
	});
	$(document).on("change","#bulkuploadpillarmasterinput",function(){
		generateuploadurl1("#bulkuploadpillarmasterform");
	});
	$(document).on("change","#bulkuploadlinemasterinput",function(){
		generateuploadurl1("#bulkuploadlinemasterform");
	});
	$(document).on("change","#bulkuploadareamasterinput",function(){
		generateuploadurl1("#bulkuploadareamasterform");
	});
	$(document).on("change","#bulkuploadbenefitmasterinput",function(){
		generateuploadurl1("#bulkuploadbenefitmasterform");
	});
	$(document).on("change","#bulkuploadtoolmasterinput",function(){
		generateuploadurl1("#bulkuploadtoolmasterform");
	});
	$(document).on("change","#bulkuploadlosstypemasterinput",function(){
		generateuploadurl1("#bulkuploadlosstypemasterform");
	});
/*	add variable field*/
	/*$(document).on("click","[data-variablefieldtype='Text']",function()
			{
		$('.placeholder-variablefield').remove();
		 $(".fieldWrapper").append('<div class="form-group ">'
				 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
				 +'<div class="dottedborder">'
				 +'<input type="text" class="form-control labelinput" placeholder="Label Name">'
				 +'<input type="text" class="form-control" disabled="" placeholder="Short Text">'
				 +'</div></div>');
			});*/
	/*$(document).on("click","[data-variablefieldtype='BigText']",function()
			{
		$('.placeholder-variablefield').remove();
		 $(".fieldWrapper").append('<div class="form-group ">'
				 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
				 +'<div class="dottedborder">'
				 +'<input type="text" class="form-control labelinput" placeholder="Label Name">'
				 +'<textarea rows="5" class="form-control" placeholder="Long Text" disabled=""></textarea>'
				 +'</div></div>');
			});*/
	/*$(document).on("click","[data-variablefieldtype='CheckBox']",function()
			{
		$('.placeholder-variablefield').remove();
		 $(".fieldWrapper").append('<div class="form-group ">'
				 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
				 +'<div class="dottedborder">'
				 		+'<input type="text" class="form-control labelinput" placeholder="Label Name">'
				 		+'<div class="varfield-holder">'
				 			+'<div class="checkboxWrapper">'
				 				+'<div class="d-flex varfield-content">'
				 					+'	<i class="fa fa-square-o active"></i>'
				 					+'<input type="text" class="form-control" placeholder="Option">'
				 					+'<span><i class="material-icons">close</i></span>'
				 				+'</div>'
				 			+'</div>'
				 			+'<div class="addoption checkboxaddoption">Add Option</div>'
				 		+'</div>'
				 +'</div></div>');
			});*/
	/*$(document).on("click","[data-variablefieldtype='Radio']",function()
			{
		$('.placeholder-variablefield').remove();
		 $(".fieldWrapper").append('<div class="form-group ">'
				 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
				 +'<div class="dottedborder">'
				 		+'<input type="text" class="form-control labelinput" placeholder="Label Name">'
				 		+'<div class="varfield-holder">'
				 			+'<div class="radioWrapper">'
				 				+'<div class="d-flex varfield-content">'
				 					+'	<i class="fa fa fa-circle-o"></i>'
				 					+'<input type="text" class="form-control" placeholder="Option">'
				 					+'<span><i class="material-icons">close</i></span>'
				 				+'</div>'
				 			+'</div>'
				 			+'<div class="addoption radioaddoption">Add Option</div>'
				 		+'</div>'
				 +'</div></div>');
			});*/
	/*$(document).on("click","[data-variablefieldtype='ListBox']",function()
			{
		$('.placeholder-variablefield').remove();
		 $(".fieldWrapper").append('<div class="form-group ">'
				 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
				 +'<div class="dottedborder">'
				 		+'<input type="text" class="form-control labelinput" placeholder="Label Name">'
				 		+'<div class="varfield-holder">'
				 			+'<div class="listboxWrapper">'
				 				+'<div class="d-flex varfield-content">'
				 					+'<input type="text" class="form-control" placeholder="Option">'
				 					+'<span><i class="material-icons">close</i></span>'
				 				+'</div>'
				 			+'</div>'
				 			+'<div class="addoption listboxaddoption">Add Option</div>'
				 		+'</div>'
				 +'</div></div>');
			});*/
	/*	add variable field end*/
$('body').on('click','.components li a',function(){
	$('.components li').removeClass('active');
	$(this).parent().addClass('active');
	$('#sidebar').removeClass('active');
    $('.overlay').removeClass('active');
});
function validateUserAccess(pageView){
	pageView=pageView.split("/")[0];
	console.log("hash --->"+pageView)
	if(pageView.trim()=="")
		pageView="home";
	
	var status=true;
	if(userDetails==undefined || userDetails.userEmail.trim() == ""){
		$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
				'<div class="Aligner-item Aligner-item--fixed">'+
					'<div class="Demo" style="text-align: center;">'+
						'<img src="/images/error-icon.png">'+
						'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
							'<b>Oops! </b>'+
						'</p>'+
						'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">You Dont have access to the application </p>'+
						'<div style="margin: 20px 0px;">'+
		        		'<a href="/">'+
		        			'<img src="/images/logo-valeo-2.png" style="width: 50px;">'+
		        		'</a>'+
			        '</div>'+
		       '</div>'+
		    '</div>');
		status = false;
	}else{
		var rolemap={};
		rolemap.userrole=["SUPERADMIN","PLANTMANAGER"];
		rolemap.home=["SUPERADMIN","PLANTMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
		rolemap.viewPDCA=["SUPERADMIN","PLANTMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
		rolemap.myAction=["SUPERADMIN","PLANTMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
		rolemap.PDCAprintView=["SUPERADMIN","PLANTdataMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
		rolemap.pdca=["SUPERADMIN","PLANTMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
		rolemap.plant=["SUPERADMIN"];
		rolemap.PILLAR=["SUPERADMIN"];
		rolemap.LINE=["SUPERADMIN"];
		rolemap.AREA=["SUPERADMIN"];
		rolemap.BENEFITTYPE=["SUPERADMIN"];
		rolemap.LOSSTYPE=["SUPERADMIN"];
		rolemap.TOOL=["SUPERADMIN"];
		rolemap.masterConfiguration=["SUPERADMIN"];
		rolemap.workpermitform=["SUPERADMIN","PLANTMANAGER","CREATOR","APPROVER1","APPROVER2","APPROVER3"];
	if(rolemap[pageView] !=undefined){
		console.log("test test "+rolemap[pageView]);
		for(var role in rolemap[pageView]){
			console.log("TEST --> "+rolemap[pageView][role]+"  "+userDetails.roles +"  "+userDetails.roles.indexOf(rolemap[pageView][role]));
			if(userDetails.roles.indexOf(rolemap[pageView][role]) == -1){
				status=false;
			}else{
				return true;
			}
		}
		console.log("status "+status);
		return status;
	}
	else
		{
		$(document).find('body') .html('<div class="Aligner" style="height: 100vh;">'+
				'<div class="Aligner-item Aligner-item--fixed">'+
					'<div class="Demo" style="text-align: center;">'+
						'<img src="/images/error-icon.png">'+
						'<p style="margin: 0px 0px;font-size: 36px;color: #e85f71;line-height: 1;">'+
							'<b>Oops! </b>'+
						'</p>'+
						'<p style="margin: 25px 0px;font-size: 24px;color: #e85f71;">Page Not Found.Contact Admin</p>'+
						'<div style="margin: 20px 0px;">'+
		        		'<a href="/">'+
		        			'<img src="/images/pdcalogo.png" style="width: 50px;">'+
		        		'</a>'+
			        '</div>'+
		       '</div>'+
		    '</div>');
		status = false;
		}
	}
}

function bindautosuggestforpillar(id,masterdataType){
	var filter="";
	var indexName="";
	var fieldName="";
	switch (masterdataType) {
	  case "LINE":
		  filter = "active%3Dtrue";
		  indexName = "LINE";
		  fieldName = "value";
	    break;
	  case  "AREA":
		  filter = "active%3Dtrue";
		  indexName = "AREA";
		  fieldName = "value";
	    break;
	  case "PROJECTLEAD":
		  filter = "projectLead%3D%22true%22";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "USER":
		  filter = "active%3Dtrue";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "MENTOR":
		  filter = "mentor%3D%22true%22";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "PILLAR":
		  filter = "active%3Dtrue";
		  indexName = "PILLAR";
		  fieldName = "name";
	    break;
	  case "TOOL":
		  filter = "active%3Dtrue";
		  indexName = "TOOL";
		  fieldName = "value";
	    break;
	  case "BENEFITTYPE":
		  filter = "active%3Dtrue";
		  indexName = "BENEFIT";
		  fieldName = "value";
	    break;
	  case  "LOSSTYPE":
		  filter = "active%3Dtrue";
		  indexName = "LOSSTYPE";
		  fieldName = "value";
	}
	
	$('#'+id).select2({
	    ajax: {
	    	async:false,
	    	url:function (params) {
	    		if(masterdataType=="PILLAR"){
	    			var datafield = $('#'+id).attr("data-fieldname");
	    			var existingVal="";
	    			var primarypillarid="";
	    			var secondarypillarid="";
	    			if(datafield=="PrimaryPillar"){
	    				var intval= parseInt(id.split("F")[1]);
	    				intval=intval+1;
	    				secondarypillarid = pad(intval,3)
	    				 existingVal=$('#F'+secondarypillarid).val();
	    			}
	    			if(datafield=="SecondaryPillar"){
	    				var intval= parseInt(id.split("F")[1]);
	    				intval=intval-1;
	    				primarypillarid = pad(intval,3)
	    				 existingVal=$('#F'+primarypillarid).val();
	    			}
	    			filter = filter.split('AND')[0];
	    			if(existingVal!=null && existingVal!="" ){
	    				filter+=encodeURI(' AND NOT name = "'+existingVal+'"');
	    			}
	    			//alert(existingVal+"   "+primarypillarid+"   "+secondarypillarid);
	    		}
	    		console.log("filter "+filter);
	    		var partneridsearch = "";
	    		if(params.term && params.term!="")
	    			partneridsearch=params.term;
				elementid = $(this).prop("id").split("_")[0];
					console.log("partneridsearch :: "+partneridsearch+" "+elementid);
			      return '/fetchdataforautosuggestfromConfigurationwithQuery?fieldName='+fieldName+'&qstr='+partneridsearch+'&indexName='+indexName+'&configuration='+filter;
			    },
	        dataType: 'json',
	        type: "GET",
	        processResults: function (data) {
	        	globalData = data;
	        	$.unique(globalData);
	        	//var resultData = removeDuplicateObjects(globalData,elementid);
	            return {
	            	
	                results: $.map(globalData, function (item) {
	                	
	                	if(indexName =="USER"){
	                		$('#'+id).val(item.ID);
	                		  return {
	                			  text: item.firstName+item.lastName,
	                    		  id: item.ID+"&&&&"+item.EMAIL,
	                    }
	                	}else{
	                		$('#'+id).val(item.split("&&&&")[1]);
	                		  return {
	                    		  text: item.split("&&&&")[0],
	                    		  id: item.split("&&&&")[0]
	                    }
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
	  tabIndex:0,
	  tags:false,
	  placeholder: "Select",
	  allowClear: true,
	  theme: "bootstrap",
	  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
	});
	
	
	/*$('#'+id).select2({
	    ajax: {
	    	url: function (params) {
	    		var partneridsearch = params.term;
				elementid = $(this).prop("id").split("_")[0];
					console.log("partneridsearch :: "+partneridsearch+" "+elementid);
			      return '/fetchdataforautosuggestfromConfigurationwithQuery?fieldName='+fieldName+'&qstr='+partneridsearch+'&indexName='+indexName+'&configuration='+filter;
			    },
	        dataType: 'json',
	        type: "GET",
	        processResults: function (data,params) {
	        	globalData = data;
	        	$.unique(globalData);
	        	//var resultData = removeDuplicateObjects(globalData,elementid);
	            return {
	            	
	                results: $.map(globalData, function (item) {
	                	$('#'+id).val(item.split("&&&&")[1]);
	                	console.log("item  from here"+item);
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
	  theme: "bootstrap",
	  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
	  minimumInputLength: 2
	});*/
	
	

}
function bindautosuggestforpillar1(id,masterdataType){
	var filter="";
	var indexName="";
	var fieldName="";
	switch (masterdataType) {
	 case "LINE":
		  filter = "active%3Dtrue";
		  indexName = "LINE";
		  fieldName = "value";
	    break;
	  case  "AREA":
		  filter = "active%3Dtrue";
		  indexName = "AREA";
		  fieldName = "value";
	    break;
	  case "PROJECTLEAD":
		  filter = "projectLead%3D%22true%22";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "USER":
		  filter = "active%3Dtrue";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "MENTOR":
		  filter = "mentor%3D%22true%22";
		  indexName = "USER";
		  fieldName = "userEmail";
	    break;
	  case "PILLAR":
		  filter = "active%3Dtrue";
		  indexName = "PILLAR";
		  fieldName = "name";
	    break;
	  case "TOOL":
		  filter = "active%3Dtrue";
		  indexName = "TOOL";
		  fieldName = "value";
	    break;
	  case "BENEFITTYPE":
		  filter = "active%3Dtrue";
		  indexName = "BENEFIT";
		  fieldName = "value";
	    break;
	  case  "LOSSTYPE":
		  filter = "active%3Dtrue";
		  indexName = "LOSSTYPE";
		  fieldName = "value";
	}
	$('#'+id).select2({
	    ajax: {
	    	async:false,
	    	url: function (params) {
	    		var partneridsearch = "";
	    		if(params.term && params.term!="")
	    			partneridsearch=params.term;
				elementid = $(this).prop("id").split("_")[0];
					console.log("partneridsearch :: "+partneridsearch+" "+elementid);
			      return '/fetchdataforautosuggestfromConfigurationwithQuery?fieldName='+fieldName+'&qstr='+partneridsearch+'&indexName='+indexName+'&configuration='+filter;
			    },
	        dataType: 'json',
	        type: "GET",
	        processResults: function (data,params) {
	        	globalData = data;
	        	//var resultData = removeDuplicateObjects(globalData,elementid);
	            return {
	                results: $.map(globalData, function (item) {
	                	if(indexName =="USER"){
	                		  return {
	                    		  text: item.firstName+item.lastName,
	                    		  id: item.ID+"&&&&"+item.EMAIL,
	                    }
	                	}else{
	                		  return {
	                    		  text: item.split("&&&&")[0],
	                    		  id: item.split("&&&&")[0]
	                    }
	                	}
	                })
	            };
	        },
	        cache:false,
	    },
	    tabIndex:0,
		  tags:false,
		  closeOnSelect: false,
		  placeholder: "Select",
		  allowClear: true,
       theme: "bootstrap",
		  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
		});
	
	

}
function generateExport( type,configType){
	$.ajax({
        type: 'GET',
        url: '/reports/generateReport?type='+type+"&configType="+configType,
        async:true,
        datatype:'JSON',
        success: function (data) 
        {
        	console.log("data "+data);
                var response = JSON.parse(data);
                console.log("response "+response);
                
                	 console.log("response.STATUS  "+response.STATUS+"   "+(response.STATUS=="SUCCESS"));
                	if(response.STATUS =="SUCCESS"){
                		showNotification("Success!", response.MESSAGE,
						"success");
                	}else{
                		showNotification("ERROR!", "Please Contact Administrator",
						"failure");
                	}
                 }
        
        });
}
function pad (str, max) {
	  str = str.toString();
	  return str.length < max ? pad("0" + str, max) : str;
	}
function generateuploadurlandassignForfileupload(callback){
	$.ajax({
        url: '/attachments/getuploadurlforattachment',
        type: 'POST'
    })
    .done(function(data) 
    {
    	Do.validateAndDoCallback(callback(data));
    })
    .fail(function() 
    {
   	 bootbox.alert({
          		title:'<b>Information</b>',
          		message:'Error occured while saving contact Admin.'
          });
    })
}
function prepareArrayforA(){
	var formA =new Array();
	/*formA[0]=["col-sm-3","LABEL","Start Date","TEXT"];
	formA[1]=["col-sm-3","VALUE","startDate","DATE"];*/
	formA[0]=["col-sm-4","VALUE","Start Date","TABLE"];
	formA[1]=["col-sm-4","VALUE","formType","TEXT"];
	formA[2]=["col-sm-4","VALUE","","TABLE"];
	formA[3]=["col-sm-3","LABEL","Problem Statement","TEXT"];
	formA[4]=["col-sm-9","VALUE","problemStatement","TEXT1"];
	formA[5]=["col-sm-2","LABEL","Primary Pillar","TEXT"];
	formA[6]=["col-sm-4","VALUE","primaryPillar","TEXT"];
	formA[7]=["col-sm-2","LABEL","Secondary Pillar","TEXT"];
	formA[8]=["col-sm-4","VALUE","secondaryPillar","TEXT"];
	formA[9]=["col-sm-2","LABEL","Plan","TEXT","","#92d050"];
	formA[10]=["col-sm-4","VALUE","variableFieldData.what","TEXT"];
	formA[11]=["col-sm-2","LABEL","Do","TEXT","","#548dd4"];
	formA[12]=["col-sm-4","VALUE","variableFieldData.do","TEXT"];
	formA[13]=["col-sm-6","VALUE","variableFieldData.planUploads","Image"];
	formA[14]=["col-sm-6","VALUE","variableFieldData.doUploads","Image"];
	formA[15]=["col-sm-2","LABEL","Check","TEXT","","#e36c09"];
	formA[16]=["col-sm-4","VALUE","variableFieldData.check","TEXT"];
	formA[17]=["col-sm-2","LABEL","Act","TEXT","","#ffff00"];
	formA[18]=["col-sm-4","VALUE","variableFieldData.act","TEXT"];
	formA[19]=["col-sm-6","VALUE","variableFieldData.checkUploads","Image"];
	formA[20]=["col-sm-6","VALUE","variableFieldData.actUploads","Image"];
	formA[21]=["col-sm-2","LABEL","Tool Used","LIST"];
	formA[22]=["col-sm-10","VALUE","tools","LIST"];
	formA[23]=["col-sm-4","VALUE","teamMembers","LIST1","Team Members"];
	formA[24]=["col-sm-1","VALUE","targetDate","DATE","Target Date"];
	formA[25]=["col-sm-1","VALUE","actualCompletionDate","DATE","Completion Date"];
	formA[26]=["col-sm-1","VALUE","cost","TEXT","Cost[INR]"];
	formA[27]=["col-sm-1","VALUE","benefitValue","TEXT","Benefit Value[INR]"];
	formA[28]=["col-sm-1","VALUE","benefitType","LIST","Benefit/Cost"];
	formA[29]=["col-sm-3","VALUE","projectLeadName","TEXT","ProjectLead Name"];
	return formA;
}
function prepareArrayforB(){
	var formA =new Array();
	formA[0]=["col-sm-4","VALUE","Start Date","TABLE"];
	formA[1]=["col-sm-4","VALUE","formType","TEXT"];
	formA[2]=["col-sm-4","VALUE","","TABLE"];
	formA[3]=["col-sm-3","LABEL","Problem Statement","TEXT"];
	formA[4]=["col-sm-9","VALUE","problemStatement","TEXT1"];
	formA[5]=["col-sm-2","LABEL","Primary Pillar","TEXT"];
	formA[6]=["col-sm-4","VALUE","primaryPillar","TEXT"];
	formA[7]=["col-sm-2","LABEL","Secondary Pillar","TEXT"];
	formA[8]=["col-sm-4","VALUE","secondaryPillar","TEXT"];
	formA[9]=["col-sm-3","LABEL","Phenomenon Description","TEXT","","#92d050"];
	formA[10]=["col-sm-3","VALUE","variableFieldData.phenomenonDescription","TEXT","",""];
	formA[11]=["col-sm-3","LABEL","System And Process","TEXT","","#92d050"];
	formA[12]=["col-sm-3","VALUE","variableFieldData.systemAndProcess","TEXT","",""];
	formA[13]=["col-sm-6","VALUE","variableFieldData.phenomenonDescriptionUploads","Image"];
	formA[14]=["col-sm-6","VALUE","variableFieldData.systemAndProcessUploads","Image"];
	formA[15]=["col-sm-2","LABEL","Target","TEXT","","#92d050"];
	formA[16]=["col-sm-2","VALUE","variableFieldData.target","TEXT","",""];
	formA[17]=["col-sm-2","LABEL","Root Cause Analysis","TEXT","","#92d050"];
	formA[18]=["col-sm-2","VALUE","variableFieldData.rootCauseAnalysis","TEXT","",""];
	formA[19]=["col-sm-2","LABEL","Actions And Counter Measures","TEXT","","#548dd4"];
	formA[20]=["col-sm-2","VALUE","variableFieldData.actionsAndCountermeasures","TEXT","",""];
	formA[21]=["col-sm-4","VALUE","variableFieldData.targetUploads","Image"];
	formA[22]=["col-sm-4","VALUE","variableFieldData.rootCauseAnalysisUploads","Image"];
	formA[23]=["col-sm-4","VALUE","variableFieldData.actionsAndCountermeasuresUploads","Image"];
	formA[24]=["col-sm-3","LABEL","Result","TEXT","","#e36c09"];
	formA[25]=["col-sm-3","VALUE","variableFieldData.results","TEXT","",""];
	formA[26]=["col-sm-3","LABEL","Standardization And Future Actions","TEXT","","#ffff00"];
	formA[27]=["col-sm-3","VALUE","variableFieldData.standardizationAndFutureActions","TEXT","",""];
	formA[28]=["col-sm-6","VALUE","variableFieldData.resultsUploads","Image"];
	formA[29]=["col-sm-6","VALUE","variableFieldData.standardizationAndFutureActionsUploads","Image"];
	formA[30]=["col-sm-2","LABEL","Tool Used","LIST"];
	formA[31]=["col-sm-10","VALUE","tools","LIST"];
	formA[32]=["col-sm-4","VALUE","teamMembers","LIST1","Team Members"];
	formA[33]=["col-sm-1","VALUE","targetDate","DATE","Target Date"];
	formA[34]=["col-sm-1","VALUE","actualCompletionDate","DATE","Completion Date"];
	formA[35]=["col-sm-1","VALUE","cost","TEXT","Cost[INR]"];
	formA[36]=["col-sm-1","VALUE","benefitValue","TEXT","Benefit Value[INR]"];
	formA[37]=["col-sm-1","VALUE","benefitType","LIST","Benefit/Cost"];
	formA[38]=["col-sm-3","VALUE","projectLeadName","TEXT","ProjectLead Name"];
	return formA;
}
function prepareArrayforC(){
	var formA =new Array();
	formA[0]=["col-sm-4","VALUE","Start Date","TABLE"];
	formA[1]=["col-sm-4","VALUE","formType","TEXT"];
	formA[2]=["col-sm-4","VALUE","","TABLE"];
	formA[3]=["col-sm-3","LABEL","Problem Statement","TEXT"];
	formA[4]=["col-sm-9","VALUE","problemStatement","TEXT1"];
	formA[5]=["col-sm-2","LABEL","Primary Pillar","TEXT"];
	formA[6]=["col-sm-4","VALUE","primaryPillar","TEXT"];
	formA[7]=["col-sm-2","LABEL","Secondary Pillar","TEXT"];
	formA[8]=["col-sm-4","VALUE","secondaryPillar","TEXT"];
	
	formA[9]=["col-sm-2","LABEL","Argument","TEXT","",""];
	formA[10]=["col-sm-2","LABEL","Phenomenon Description","TEXT","","#92d050"];
	formA[11]=["col-sm-3","VALUE","variableFieldData.phenomenonDescription","TEXT","",""];
	formA[12]=["col-sm-2","LABEL","System And Process","TEXT","","#92d050"];
	formA[13]=["col-sm-3","VALUE","variableFieldData.systemAndProcess","TEXT","",""];
	formA[14]=["col-sm-2","VALUE","variableFieldData.argument","TEXT","",""];
	formA[15]=["col-sm-5","VALUE","variableFieldData.phenomenonDescriptionUploads","Image"];
	formA[16]=["col-sm-5","VALUE","variableFieldData.systemAndProcessUploads","Image"];
	formA[17]=["col-sm-2","LABEL","Plan","TEXT","",""];
	formA[18]=["col-sm-2","LABEL","Target","TEXT","","#92d050"];
	formA[19]=["col-sm-3","VALUE","variableFieldData.target","TEXT","",""];
	formA[20]=["col-sm-2","LABEL","Actions And Counter Measures","TEXT","","#548dd4"];
	formA[21]=["col-sm-3","VALUE","variableFieldData.actionsAndCountermeasures","TEXT","",""];
	formA[22]=["col-sm-2","VALUE","variableFieldData.plan","TEXT","",""];
	formA[23]=["col-sm-5","VALUE","variableFieldData.targetUploads","Image"];
	formA[24]=["col-sm-5","VALUE","variableFieldData.actionsAndCountermeasuresUploads","Image"];
	formA[25]=["col-sm-2","LABEL","Root Cause Analysis","TEXT","","#92d050"];
	formA[26]=["col-sm-2","VALUE","variableFieldData.rootCauseAnalysis","TEXT","",""];
	formA[27]=["col-sm-2","LABEL","Result","TEXT","","#e36c09"];
	formA[28]=["col-sm-2","VALUE","variableFieldData.results","TEXT","",""];
	formA[29]=["col-sm-2","LABEL","Standardization And Future Actions","TEXT","","#ffff00"];
	formA[30]=["col-sm-2","VALUE","variableFieldData.standardizationAndFutureActions","TEXT","",""];
	formA[31]=["col-sm-4","VALUE","variableFieldData.rootCauseAnalysisUploads","Image"];
	formA[32]=["col-sm-4","VALUE","variableFieldData.resultsUploads","Image"];
	formA[33]=["col-sm-4","VALUE","variableFieldData.standardizationAndFutureActionsUploads","Image"];
	 
	formA[34]=["col-sm-2","LABEL","Tool Used","LIST"];
	formA[35]=["col-sm-10","VALUE","tools","LIST"];
	formA[36]=["col-sm-4","VALUE","teamMembers","LIST1","Team Members"];
	formA[37]=["col-sm-1","VALUE","targetDate","DATE","Target Date"];
	formA[38]=["col-sm-1","VALUE","actualCompletionDate","DATE","Completion Date"];
	formA[39]=["col-sm-1","VALUE","cost","TEXT","Cost[INR]"];
	formA[40]=["col-sm-1","VALUE","benefitValue","TEXT","Benefit Value[INR]"];
	formA[41]=["col-sm-1","VALUE","benefitType","LIST","Benefit/Cost"];
	formA[42]=["col-sm-3","VALUE","projectLeadName","TEXT","ProjectLead Name"];
	return formA;
}
function prepareArrayforD(){
	var formA =new Array();
	formA[0]=["col-sm-4","VALUE","Start Date","TABLE"];
	formA[1]=["col-sm-4","VALUE","formType","TEXT"];
	formA[2]=["col-sm-4","VALUE","","TABLE"];
	formA[3]=["col-sm-3","LABEL","Problem Statement","TEXT"];
	formA[4]=["col-sm-9","VALUE","problemStatement","TEXT1"];
	formA[5]=["col-sm-2","LABEL","Primary Pillar","TEXT"];
	formA[6]=["col-sm-4","VALUE","primaryPillar","TEXT"];
	formA[7]=["col-sm-2","LABEL","Secondary Pillar","TEXT"];
	formA[8]=["col-sm-4","VALUE","secondaryPillar","TEXT"];
	formA[9]=["col-sm-3","LABEL","Phenomenon Description","TEXT","","#92d050"];
	formA[10]=["col-sm-3","VALUE","variableFieldData.phenomenonDescription","TEXT","",""];
	formA[11]=["col-sm-3","LABEL","System And Process","TEXT","","#92d050"];
	formA[12]=["col-sm-3","VALUE","variableFieldData.systemAndProcess","TEXT","",""];
	formA[13]=["col-sm-6","VALUE","variableFieldData.phenomenonDescriptionUploads","Image"];
	formA[14]=["col-sm-6","VALUE","variableFieldData.systemAndProcessUploads","Image"];
	formA[15]=["col-sm-2","LABEL","Target","TEXT","","#92d050"];
	formA[16]=["col-sm-2","VALUE","variableFieldData.target","TEXT","",""];
	formA[17]=["col-sm-2","LABEL","Root Cause Analysis","TEXT","","#92d050"];
	formA[18]=["col-sm-2","VALUE","variableFieldData.rootCauseAnalysis","TEXT","",""];
	formA[19]=["col-sm-2","LABEL","Actions And Counter Measures","TEXT","","#548dd4"];
	formA[20]=["col-sm-2","VALUE","variableFieldData.actionsAndCountermeasures","TEXT","",""];
	formA[21]=["col-sm-4","VALUE","variableFieldData.targetUploads","Image"];
	formA[22]=["col-sm-4","VALUE","variableFieldData.rootCauseAnalysisUploads","Image"];
	formA[23]=["col-sm-4","VALUE","variableFieldData.actionsAndCountermeasuresUploads","Image"];
	formA[24]=["col-sm-3","LABEL","Result","TEXT","","#e36c09"];
	formA[25]=["col-sm-3","VALUE","variableFieldData.results","TEXT","",""];
	formA[26]=["col-sm-3","LABEL","Standardization And Future Actions","TEXT","","#ffff00"];
	formA[27]=["col-sm-3","VALUE","variableFieldData.standardizationAndFutureActions","TEXT","",""];
	formA[28]=["col-sm-6","VALUE","variableFieldData.resultsUploads","Image"];
	formA[29]=["col-sm-6","VALUE","variableFieldData.standardizationAndFutureActionsUploads","Image"];
	formA[30]=["col-sm-2","LABEL","Tool Used","LIST"];
	formA[31]=["col-sm-10","VALUE","tools","LIST"];
	formA[32]=["col-sm-4","VALUE","teamMembers","LIST1","Team Members"];
	formA[33]=["col-sm-1","VALUE","targetDate","DATE","Target Date"];
	formA[34]=["col-sm-1","VALUE","actualCompletionDate","DATE","Completion Date"];
	formA[35]=["col-sm-1","VALUE","cost","TEXT","Cost[INR]"];
	formA[36]=["col-sm-1","VALUE","benefitValue","TEXT","Benefit Value[INR]"];
	formA[37]=["col-sm-1","VALUE","benefitType","LIST","Benefit/Cost"];
	formA[38]=["col-sm-3","VALUE","projectLeadName","TEXT","ProjectLead Name"];
	return formA;
}
function prepareFormView(layoutArray,request,tableContent,tableContent1){
	var divContent = "";
	var classColor="";
	for(var i in layoutArray){
		if(layoutArray[i].length>5 && layoutArray[i][5] !=""){
			classColor=layoutArray[i][5];
		}
		else{
			classColor="";
		}
		if(layoutArray[i][1]=="LABEL"){
			if(layoutArray[i][2]=="Start Date"){
				divContent+='<div style="padding:0px; " class="'+layoutArray[i][0]+'">';
				divContent+='<div style= "height: 50%; width:100%; border: 1px solid black !important; text-align: center; background:'+classColor+'" ><span><b>'+layoutArray[i][2]+'</b></span></div>';
				divContent+='<div style= " height: 50%; width:100%; border: 1px solid black !important; text-align: center; background:'+classColor+'" ><span><b>'+layoutArray[i][2]+'</b></span></div>';
				divContent+='</div>';
			}else
			divContent+='<div style= "display: flex;justify-content: center;    align-items: center; background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+layoutArray[i][2]+'</b></span></div>';
		}else{
			var value 	= "";
			if(layoutArray[i][2].indexOf("variableFieldData")==-1){
				if(layoutArray[i][2]=="projectLeadName")
					{
					if(requestPillarLead && requestPillarLead!=undefined && requestPillarLead!=null)
					value=requestPillarLead.firstName+" "+requestPillarLead.lastName;
					}
				else
				value=request[layoutArray[i][2]];
			}else{
				var key = layoutArray[i][2].split(".")[1];
				console.log(key);
				var jsonvalue = JSON.parse(request["variableFieldData"].value);
				console.log(jsonvalue);
				console.log(jsonvalue[key]);
				value=jsonvalue[key];
				/*if(layoutArray[i][4]!=null && layoutArray[i][4]!=="Team Members"){
					value=layoutArray[i][4]+ ":"+ value;
				}*/
				if(value==null || value==undefined){value="";}
						//JSON.parse(request["variableFieldData"].value).act
			}
			
			if(layoutArray[i][3]=="TEXT"){
				if(layoutArray[i][4]!=null && layoutArray[i][4]!="Team Members" && layoutArray[i][2].indexOf("variableFieldData")==-1){
					value=layoutArray[i][4]+ ":<br/>"+ value;
				}
				if(layoutArray[i][2]!=null && layoutArray[i][2]=="formType"){
					divContent+='<div style=" display: flex;justify-content: center;    align-items: center; background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+value+'</b></span></div>';
				}
				else if(layoutArray[i][2]!=null && layoutArray[i][2]=="variableFieldData.what"){
					var jsonvalue = JSON.parse(request["variableFieldData"].value);
					value ='<b >What: </b><b style="font-weight:500">'+jsonvalue.what+'</b><br/>';
					value +='<b >When:</b> <b style="font-weight:500">'+jsonvalue.when+'</b><br/>';
					value +='<b >Where: </b><b style="font-weight:500">'+jsonvalue.where+'</b><br/>';
					value +='<b >Who: </b> <b style="font-weight:500">'+jsonvalue.who+'</b><br/>';
					value +='<b >Which: </b><b style="font-weight:500">'+jsonvalue.which+'</b><br/>';
					value +='<b >How: </b><b style="font-weight:500">'+jsonvalue.how+'</b><br/>';
					divContent+='<div style=" background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+value.replace(/\n/g, "<br />")+'</b></span></div>';
				}else{
					console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$4 "+value +"  "+layoutArray[i][2])
					if(value!= null)
					divContent+='<div style=" background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+value.replace(/\n/g, "<br />")+'</b></span></div>';
					else
					divContent+='<div style=" background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b></b></span></div>';	
				}
			}
			if(layoutArray[i][3]=="TEXT1"){
				if(layoutArray[i][4]!=null && layoutArray[i][4]!="Team Members"){
					value=layoutArray[i][4]+ ":<br/> 	"+ value.value;
				}else
					value=value.value;
				divContent+='<div style="background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+value.replace(/\n/g, "<br />")+'</b></span></div>';
			}
			 if(layoutArray[i][3]=="DATE"){
				 var datestring = new Date(value).toLocaleDateString('en-GB', {
					    day : 'numeric',
					    month : 'short',
					    year : 'numeric'
					}).split(' ').join('-');
				 if(datestring == "Invalid Date"){
					 datestring="";
				 }
				 if(layoutArray[i][4]!=null && layoutArray[i][4]!="Team Members"){
					 datestring=layoutArray[i][4]+ ":<br/> "+ datestring;
					}
				divContent+='<div  style="background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+datestring+'</b></span></div>';
			}
			 if(layoutArray[i][3]=="TABLE"){
				 if(layoutArray[i][2]=="Start Date"){
					 
					 
					 	divContent+='<div style="padding:0px; " class="'+layoutArray[i][0]+'">';
						divContent+='<div style= "height: 75%;  border: 1px solid black !important; text-align: center; background:'+classColor+'" ><img style="object-fit:cover;height:90px;width:100px;" src="'+getLogo(request["formType"])+'"></img></div>';
						 divContent+='<div  style=" height: 25%; border: 1px solid black !important; padding:0px;background:'+classColor+'">'+tableContent1+'</div>';
					//	divContent+='<div style= " height: 50%; width:100%; border: 1px solid black !important; text-align: center; background:'+classColor+'" ><span><b>'+layoutArray[i][2]+'</b></span></div>';
						divContent+='</div>';
						
					
				 }else
					divContent+='<div  style=" padding:0px;background:'+classColor+'" class="'+layoutArray[i][0]+'">'+tableContent+'</div>';
				}
			 if(layoutArray[i][3]=="LIST"){
				 if(layoutArray[i][4]!=null && layoutArray[i][4]!="Team Members"){
					 value=layoutArray[i][4]+ ":<br/>"+ value.toString();
					}
				 if(layoutArray[i][4]!=null && layoutArray[i][4]=="Benefit/Cost"){
					 value=layoutArray[i][4]+ ": <br/>"+( request.benefitValue/request.cost).toFixed(2);;
					}
				 divContent+='<div  style="background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+value+'</b></span></div>';
			 }
			 if(layoutArray[i][3]=="LIST1"){
				 if(layoutArray[i][4]=="Team Members"){
					 var jsonval = JSON.parse(value.value);
					 var nameArray = new Array();
					 for(q in jsonval) {
							nameArray.push(jsonval[q].name);
						}
					 divContent+='<div  style="background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+layoutArray[i][4]+" : "+nameArray.toString()+'</b></span></div>';
				 }else{
				 divContent+='<div  style="background:'+classColor+'" class="'+layoutArray[i][0]+'"><span><b>'+layoutArray[i][4]+" : "+value.toString()+'</b></span></div>';
				 }
			 }
			 if(layoutArray[i][3]=="Image"){
				 console.log("inside image" );
				 divContent+='<div  style="background:'+classColor+'" class="'+layoutArray[i][0]+'"> <ul class="list-unstyled attachdesign imageattach">';
				 for(var image in value){
					 var downloadlink	=	'/attachments/FileDownload?type=WorkPermitAttachments&attchemnetID='+value[image].attachmentId+'&filename='+value[image].fileName+'&refID=';
					 divContent += 	'<li id="'+value[image].attachmentId+'">'
					 		+'<div class="cloneimage"><img src="'+downloadlink+'"></div>'
							+'<a class="filedownld workpermitfiledownload" data-link="'+downloadlink+'" data-filetype="file" data-file ="'+value[image].fileName+'" data-attachmentid="'+value[image].attachmentId+'">'+value[image].fileName+'</a>'
						+'</li>';
				 	}
				 divContent +='</ul></div>';
			 }
			
		}
	}
	 /*<li id="1562668493079">
		<div class="cloneimage">
			<img src="https://www.w3schools.com/w3images/lights.jpg">
			</div>
			<a class="filedownld workpermitfiledownload" data-link="/attachments/FileDownload?type=WorkPermitAttachments&amp;attchemnetID=1562668493079&amp;filename=hero-thankyou.jpg&amp;refID=" data-filetype="file" data-file="hero-thankyou.jpg" data-attachmentid="1562668493079">hero-thankyou.jpg</a>
			<span class="attchmentdelete">
			<i class="fa fa-times"></i>
			</span>
	</li>*/
	$('#reqid_label1').text(request.id);
	$('#reqname_pdfview_label1').text(request.formId);
	$('#status_label1').text(request.status);
	$('#plant_label1').text(request.plant);
	$('#created_by_label1').text(request.createdBy);
	if(request.createdOn != undefined) {
		$('#created_on_label1').text(localTimezone.get(request.createdOn));
	}
	$('#printviewpdcarow').html(divContent);
}
function generatetableforprintview(request){
	
	var tablecontent ='<table class="table table-bordered">'
						+'<tr><td >No  </td><td >'+request.formId+'</td> </tr>'
						+'<tr><td >Area  </td><td >'+request.area+'</td></tr>'
						+'<tr><td>Line </td> <td>'+request.line+'</td></tr>'
						+'<tr><td >Project Lead   </td><td >'+request.projectLeadName+'</td></tr></table>';
				return tablecontent;
} 
function generateTableForStartDateView(request){
	var tablecontent ='<table class="table table-bordered">'
		+'<tr><td >Start Date  </td>';
		if(request.startDate && request.startDate!=null && request.startDate!=0){
			tablecontent+='<td >'+new Date(request.startDate).toLocaleDateString('en-GB', {
			    day : 'numeric',
			    month : 'short',
			    year : 'numeric'
			}).split(' ').join('-')+'</td>';
		}
	tablecontent+='</tr></table>';
return tablecontent;
}
var dateFormat = function () {
    var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
        timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
        timezoneClip = /[^-+\dA-Z]/g,
        pad = function (val, len) {
            val = String(val);
            len = len || 2;
            while (val.length < len) val = "0" + val;
            return val;
        };

    // Regexes and supporting functions are cached through closure
    return function (date, mask, utc) {
        var dF = dateFormat;

        // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
        if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
            mask = date;
            date = undefined;
        }

        // Passing date through Date applies Date.parse, if necessary
        date = date ? new Date(date) : new Date;
        if (isNaN(date)) throw SyntaxError("invalid date");

        mask = String(dF.masks[mask] || mask || dF.masks["default"]);

        // Allow setting the utc argument via the mask
        if (mask.slice(0, 4) == "UTC:") {
            mask = mask.slice(4);
            utc = true;
        }

        var _ = utc ? "getUTC" : "get",
            d = date[_ + "Date"](),
            D = date[_ + "Day"](),
            m = date[_ + "Month"](),
            y = date[_ + "FullYear"](),
            H = date[_ + "Hours"](),
            M = date[_ + "Minutes"](),
            s = date[_ + "Seconds"](),
            L = date[_ + "Milliseconds"](),
            o = utc ? 0 : date.getTimezoneOffset(),
            flags = {
                d:    d,
                dd:   pad(d),
                ddd:  dF.i18n.dayNames[D],
                dddd: dF.i18n.dayNames[D + 7],
                m:    m + 1,
                mm:   pad(m + 1),
                mmm:  dF.i18n.monthNames[m],
                mmmm: dF.i18n.monthNames[m + 12],
                yy:   String(y).slice(2),
                yyyy: y,
                h:    H % 12 || 12,
                hh:   pad(H % 12 || 12),
                H:    H,
                HH:   pad(H),
                M:    M,
                MM:   pad(M),
                s:    s,
                ss:   pad(s),
                l:    pad(L, 3),
                L:    pad(L > 99 ? Math.round(L / 10) : L),
                t:    H < 12 ? "a"  : "p",
                tt:   H < 12 ? "am" : "pm",
                T:    H < 12 ? "A"  : "P",
                TT:   H < 12 ? "AM" : "PM",
                Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
            };

        return mask.replace(token, function ($0) {
            return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
        });
    };
}();

// Some common format strings
dateFormat.masks = {
    "default":      "ddd mmm dd yyyy HH:MM:ss",
    shortDate:      "m/d/yy",
    mediumDate:     "mmm d, yyyy",
    longDate:       "mmmm d, yyyy",
    fullDate:       "dddd, mmmm d, yyyy",
    shortTime:      "h:MM TT",
    mediumTime:     "h:MM:ss TT",
    longTime:       "h:MM:ss TT Z",
    isoDate:        "yyyy-mm-dd",
    isoTime:        "HH:MM:ss",
    isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
    isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
    dayNames: [
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    ],
    monthNames: [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    ]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
    return dateFormat(this, mask, utc);
};


jQuery(document).ready(function($) {
    var docBody = $(document.body);
    var shiftPressed = false;
    var clickedOutside = false;
    //var keyPressed = 0;
    $(".select2-selection").on("focus", function () {
        $(this).parent().parent().prev().select2("open");
    });
    docBody.on('keydown', function(e) {
        var keyCaptured = (e.keyCode ? e.keyCode : e.which);
        //shiftPressed = keyCaptured == 16 ? true : false;
        if (keyCaptured == 16) { shiftPressed = true; }
    });
    docBody.on('keyup', function(e) {
        var keyCaptured = (e.keyCode ? e.keyCode : e.which);
        //shiftPressed = keyCaptured == 16 ? true : false;
        if (keyCaptured == 16) { shiftPressed = false; }
    });

    docBody.on('mousedown', function(e){
        // remove other focused references
        clickedOutside = false;
        // record focus
        if ($(e.target).is('[class*="select2"]')!=true) {
            clickedOutside = true;
        }
    });

    docBody.on('select2:opening', function(e) {
        // this element has focus, remove other flags
        clickedOutside = false;
        // flag this Select2 as open
        $(e.target).attr('data-s2open', 1);
    });
    docBody.on('select2:closing', function(e) {
        // remove flag as Select2 is now closed
        $(e.target).removeAttr('data-s2open');
    });

    docBody.on('select2:close', function(e) {
        var elSelect = $(e.target);
        elSelect.removeAttr('data-s2open');
        var currentForm = $('#createpermit-content');
        var othersOpen = currentForm.has('[data-s2open]').length;
        if (othersOpen == 0 && clickedOutside==false) {
            /* Find all inputs on the current form that would normally not be focus`able:
             *  - includes hidden <select> elements whose parents are visible (Select2)
             *  - EXCLUDES hidden <input>, hidden <button>, and hidden <textarea> elements
             *  - EXCLUDES disabled inputs
             *  - EXCLUDES read-only inputs
             */
            var inputs = currentForm.find(':input:enabled:not([readonly], input:hidden, button:hidden, textarea:hidden)')
                .not(function () {   // do not include inputs with hidden parents
                    return $(this).parent().is(':hidden');
                });
            var elFocus = null;
            $.each(inputs, function (index) {
                var elInput = $(this);
                if (elInput.attr('id') == elSelect.attr('id')) {
                    if ( shiftPressed) { // Shift+Tab
                        elFocus = inputs.eq(index - 1);
                    } else {
                        elFocus = inputs.eq(index + 1);
                    }
                    return false;
                }
            });
            if (elFocus !== null) {
                // automatically move focus to the next field on the form
                var isSelect2 = elFocus.siblings('.select2').length > 0;
                if (isSelect2) {
                    elFocus.select2('open');
                } else {
                    elFocus.focus();
                }
            }
        }
    });

    /**
     * Capture event where the user entered a Select2 control using the keyboard.
     * http://stackoverflow.com/questions/20989458
     * http://stackoverflow.com/questions/1318076
     */
    docBody.on('focus', '.select2', function(e) {
        var elSelect = $(this).siblings('select');
        if (elSelect.is('[disabled]')==false && elSelect.is('[data-s2open]')==false
            && $(this).has('.select2-selection--single').length>0) {
            elSelect.attr('data-s2open', 1);
            elSelect.select2('open');
        }
    });
   
    
});