(function()
{
	
	isEmail =function(mailVal){
		var regex=/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		result=regex.test(mailVal);
		console.log(result);
		return result;
		}
	
	validateField			=		function(formdivid,done)
	{
		loader.hide();
		var setFlag = false;
		flag=!(validate.getInstance().formordiv(formdivid)) // adds error class to fields if no value is present
		// double checks if form is hidden and disabled.. no need to check the mandatory
		$('#'+formdivid).find('.form-control').each(function()
        {
            if ($(this).hasClass('error') && $(this).closest('.form-group').css('display').indexOf("none")==-1)
            {
                if ($(this).closest('.form-group').css('display').indexOf("none")==-1 && !$(this).is(':disabled'))
                {
                	$(this).addClass('error');
                	console.log("coming here 1---> ");
                    setFlag = true;
                }
                else
                {
                    $(this).removeClass('error');
                }
            }
        });
		// check select2
		$('#'+formdivid).find('select').each(function()
        {
            if ($(this).hasClass('error') && $(this).closest('.form-group').css('display').indexOf("none")==-1)
            {
                if ($(this).closest('.form-group').css('display').indexOf("none")==-1 && !$(this).is(':disabled'))
                {
                	$(this).closest('div').find('.select2-container--bootstrap').addClass('error');
                	console.log("coming here ---> ");
                    setFlag = true;
                }
                else
                {
                	$(this).closest('div').find('.select2-container--bootstrap').removeClass('error');
                }
                $(this).removeClass('error');
            }
        });
		// to check mandatory for radio button fields
            var radioFlag	=	false;
            var radionameArr	=	new Array();
            $('#'+formdivid).find('input[type="radio"]').each(function()
            {
            	if($(this).closest('.form-group').css('display').indexOf("none")==-1 && radionameArr.indexOf($(this).attr('name'))==-1 
            			&& $(this).attr('data-required')!=null 
            			&& $(this).attr('data-required')!=undefined 
            			&& $(this).attr('data-required').indexOf("yes")!=-1)
            	{
            		radionameArr.push($(this).attr('name'));
            	}
            });
            for(var index in radionameArr)
            {
            	var checkedlist = $('#'+formdivid).find('input[name="'+radionameArr[index]+'"]:checked');
            	if(checkedlist!=null && checkedlist!=undefined&&checkedlist.length!=0)
            	{
            		console.log("checked");
            		$('#'+formdivid).find('input[name="'+radionameArr[index]+'"]').closest('fieldset').removeClass('error');
            	}
            	else
            	{
            		$('#'+formdivid).find('input[name="'+radionameArr[index]+'"]').closest('fieldset').addClass('error');
            		radioFlag = true;
            	}
            }
         // to check mandatory for checkbox fields
            var checkBoxFlag	=	false;
            var checkBoxnameArr	=	new Array();
            $('#'+formdivid).find('input[type="checkbox"]').each(function()
		     {
            	if($(this).closest('.form-group').css('display').indexOf("none")==-1 && checkBoxnameArr.indexOf($(this).attr('name'))==-1
            			&& $(this).attr('data-required')!=null 
            			&& $(this).attr('data-required')!=undefined 
            			&& $(this).attr('data-required').indexOf("yes")!=-1)
            	{
            		checkBoxnameArr.push($(this).attr('name'));
            	}
		     });
            for(var index in checkBoxnameArr)
            {
            	var checkedlist = $('#'+formdivid).find('input[name="'+checkBoxnameArr[index]+'"]:checked');
            	if(checkedlist!=null && checkedlist!=undefined&&checkedlist.length!=0)
            	{
            		console.log("checked");
            		$('#'+formdivid).find('input[name="'+checkBoxnameArr[index]+'"]').closest('fieldset').removeClass('error');
            	}
            	else
            	{
            		$('#'+formdivid).find('input[name="'+checkBoxnameArr[index]+'"]').closest('fieldset').addClass('error');
            		checkBoxFlag = true;
            	}
            }
            // multiple attachment mandatory check
            var attachmentFlag	=	false;
            $('#'+formdivid).find('.attachment').each(function(){
            	if($(this).hasClass('error')){
            		if($(this).closest('.form-group').css('display').indexOf("none")==-1 && ($(this).find('.attachdesign .filedownld')==undefined || $(this).find('.attachdesign .filedownld').length==0))
                	{
                		attachmentFlag	= true;
                	}
                	else
                	{
                		$(this).removeClass('error');
                	}
            	}
            });
            // signature field mandatory check
            var signatureFlag	=	false;
            $('#'+formdivid).find('.signdiv').each(function(){
            	if($(this).closest('.form-group').css('display').indexOf("none")==-1 && $(this).find('img').hasClass('error'))
            	{
            		if($(this).find('img').attr('src')!=undefined && $(this).find('img').attr('src')!=null && $(this).find('img').attr('src')!="")
            		{
            			$(this).find('img').removeClass('error');
            		}
            		else
            		{
            			$(this).find('img').removeClass('error');
            			$(this).find('.signpopup-btn').addClass('error');
            			signatureFlag=true;
            		}
            	}
            });
            
            // capture field mandatory check
            var captureFlag	=	false;
            $('#'+formdivid).find('.imagecapture').each(function(){
            	if($(this).hasClass('error') && $(this).closest('.form-group').css('display').indexOf("none")==-1)
            	{
            		var imgSrc	=	$(this).find('.annotateImage-wrapper img').attr('src');
            		if(imgSrc!=undefined && imgSrc!=null && imgSrc!="")
            		{
            			$(this).removeClass('error');
            		}
            		else
            		{
            			$(this).removeClass('error');
            			$(this).closest('.form-group').find('.imagecapture-txt').addClass('error');
            			captureFlag=true;
            		}
            	}
            });
            // variable part fields mandatory check
            var variablePartFlag	=	false;
            $('#'+formdivid).find('.workpermit-variablebtn').each(function(){
            	if($(this).hasClass('error')){
            		if($(this).closest('.form-group').css('display').indexOf("none")==-1){
                		if($(this).closest('div').next('.variablefields-div')!=undefined 
                				&& $(this).closest('div').next('.variablefields-div')!=null 
                				&& $(this).closest('div').next('.variablefields-div')!=""
                				&& $(this).closest('div').next('.variablefields-div').length>0){
                			$(this).removeClass('error');
                		}
                		else{
                			variablePartFlag = true;
                		}
                	}
            	}
            });
            if (!setFlag && !radioFlag && !checkBoxFlag && !attachmentFlag && !signatureFlag && !captureFlag && !variablePartFlag)
	        {
            	Do.validateAndDoCallback(done);
	        }
            else
            {
            	loader.hide();
            	showNotification("Error","Mandatory Field(s) Missing","error");
            }
	};	
	
})();