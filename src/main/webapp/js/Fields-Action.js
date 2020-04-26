(function(){
	// common html 
	var mandatoryFieldHtml	=	" <span class='text-danger'>*</span>";
	var mandatoryAttr	=	"data-required='yes' data-type='text'";
	var visibilityContent	=	"style='display:none;'";
	var disabledContent = "disabled";
	
	
	buildSection	=	function(sectionName,contentHtml)
	{
		var html	=	"";
		if(sectionName.toLowerCase().indexOf('variable part')!=-1){
			html	=	"<div class='x_panel'>"+
			"<h5 class='subtitle-bg'><i class='fa fa-chevron-down arrow-updown collapse-link'></i><span class='fa fa-file-text-o' aria-hidden='true' style='margin-right:5px;'></span> "+sectionName+"</h5>"+
			"<blockquote class='message x_content'>"+
			contentHtml+
			"</div></blockquote></div>";
			return html;
		}
		else{
			html	=	"<div class='x_panel'>"+
			"<h5 class='subtitle-bg'><i class='fa fa-chevron-down arrow-updown collapse-link'></i><span class='fa fa-file-text-o' aria-hidden='true' style='margin-right:5px;'></span> "+sectionName+"</h5>"+
			"<blockquote class='message x_content customcol-4'>"+
			contentHtml+
			"</div></blockquote></div>";
		}
		return html;
	}
	
	buildFieldDiv	=	function(configObj,contentHtml,index)			
	{
		var html = "";
		var defaultVisibilityStyle	="";
		var visibilityCondtClass	=	"";
		var visibilityCondtAttr	=	"";
		var mandatory	= "";
		var approversignFieldClass	=	"";
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
		{ 
			 mandatory	=mandatoryFieldHtml;
		}
		 if(configObj.showWhen!=null &&configObj.showWhen!=undefined && configObj.showWhen!="")
		{
			 defaultVisibilityStyle	=visibilityContent;
			 visibilityCondtClass = "conditionexist";
			 visibilityCondtAttr = "data-condition='"+encodeURIComponent(configObj.showWhen)+"'";
		}
		 if(configObj.fieldName.toLowerCase().indexOf("approver")!=-1 && configObj.fieldType.toLowerCase().indexOf("signature")!=-1){
			 approversignFieldClass	=	"approversign";
		 }
		 if(configObj.fieldType.indexOf("VARIABLE")!=-1)
			{
				html+="<div "+visibilityCondtAttr+" class='form-group "+approversignFieldClass+" "+visibilityCondtClass+"' "+defaultVisibilityStyle+">"+
				contentHtml+"</div>";
			}
		 else
		{
		if(index>0)
		{
			html+="</div><div "+visibilityCondtAttr+" class='form-group "+approversignFieldClass+" "+visibilityCondtClass+"' "+defaultVisibilityStyle+">"+
			"<label class='control-label active'>"+configObj.fieldName+""+mandatory+""+buildHelpText(configObj.helpText)+"</label>"+
				contentHtml;
		}
		else
		{
			html+="<div "+visibilityCondtAttr+" class='form-group "+approversignFieldClass+" "+visibilityCondtClass+"' "+defaultVisibilityStyle+">"+
			"<label class='control-label active'>"+configObj.fieldName+""+mandatory+""+buildHelpText(configObj.helpText)+"</label>"+
				contentHtml;
		}
		}
		return html;
	}
	
	buildHelpText	=	function(content)
	{
		var html	=	"";
		if(content!=null && content!=undefined && content!="")
		{
			html	=	"<span class='helpicon' data-container='body' data-toggle='popover' data-placement='top' data-content='"+content+"' data-original-title='' title=''>"+
			"<i class='fa fa-info-circle' style='color: #ffc107;'></i>"+
			"</span>";
		}
		return html;
	}
	/* buildBasicDetailSection	=	function(callback)
	{
		var html	=	"";
		html	=	"<div class='x_panel'>"+
						"<h5 class='subtitle-bg collapse-link'><i class='fa fa-chevron-down arrow-updown'></i><span class='fa fa-file-text-o' aria-hidden='true' style='margin-right:5px;'></span> Basic Detail</h5>"+
						"<blockquote class='message x_content customcol-4'>"+
							"<div class='form-group'>"+
						      "  <label class='control-label active'>ReqId</label>"+
						       " <input type='text' name='' placeholder='' class='form-control'>"+
						   " </div>"+
						   " <div class='form-group'>"+
						       " <label class='control-label active'>Status</label>"+
						        "<input type='text' name='' placeholder='' class='form-control'>"+
						   " </div>"+
						   " <div class='form-group'>"+
						       " <label class='control-label active'>Reference</label>"+
						       " <input type='text' name='' placeholder='' class='form-control'>"+
						   " </div>"+
						   " <div class='form-group'>"+
						       " <label class='control-label active'>Date</label>"+
						       " <input type='text' name='' placeholder='' class='form-control'>"+
						    "</div>"+
						"</blockquote>"+
				"	</div>";
		Do.validateAndDoCallback(callback(html));
	}*/
	 buildField	=	function(configObj,callback)
	{
		if(configObj!=null && configObj!=undefined && configObj!="")
		{
			 var fieldTypeHtmlMethod	=	"";
			 if(configObj.fieldType=="AUTOSUGGEST")
			 {
				 fieldTypeHtmlMethod="buildAutoSuggestField";
			 }
			 else if(configObj.fieldType=="NUMBER")
			 {
				 fieldTypeHtmlMethod	=	"buildNumberField";
			 }
			 else if(configObj.fieldType=="RADIO")
			 {
				 fieldTypeHtmlMethod	=	"buildRadioButtonField";
			 }
			 else if(configObj.fieldType=="FILE")
			 {
				 fieldTypeHtmlMethod	=	"buildAttachmentField";
			 }
			 else if(configObj.fieldType=="BIG TEXT")
			 {
				 fieldTypeHtmlMethod	=	"buildTextAreaField";
			 }
			 else if(configObj.fieldType=="DATE")
			 {
				 fieldTypeHtmlMethod	=	"buildDateField";
			 }
			 else if(configObj.fieldType=="TIME")
			 {
				 fieldTypeHtmlMethod	=	"buildTimeField";
			 }
			 else if(configObj.fieldType=="TEXT")
			 {
				 fieldTypeHtmlMethod	=	"buildTextField";
			 }
			 else if(configObj.fieldType=="CHECKBOX")
			 {
				 fieldTypeHtmlMethod	=	"buildCheckBoxField";
			 }
			 else if(configObj.fieldType=="CAPTURE")
			 {
				 fieldTypeHtmlMethod	=	"buildCaptureField";
			 }
			 else if(configObj.fieldType=="SIGNATURE")
			 {
				 fieldTypeHtmlMethod	=	"buildSignatureField";
			 }
			 else if(configObj.fieldType=="LISTBOX")
			 {
				 fieldTypeHtmlMethod	=	"buildDropdownField";
			 }
			 else if(configObj.fieldType=="LISTBOX1")
			 {
				 fieldTypeHtmlMethod	=	"buildDropdownField";
			 }
			 else if(configObj.fieldType=="VARIABLE")
			 {
				 fieldTypeHtmlMethod	=	"buildVariableField";
			 }
			 
			window[fieldTypeHtmlMethod](configObj,function(html)
			{
					Do.validateAndDoCallback(callback(html));
			});
		}
	}
	 
	 buildVariableField	=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	= mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			 html = 	"<button data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" id='"+configObj.iD+"' "+mandatory+" class='workpermit-variablebtn btn btn-default btn-sm waves-effect waves-light "+defaultValClass+"' tabindex='-1' aria-hidden='true'>" +
			 			configObj.fieldName+
			 			"</button>" +
			 			"<button class='workpermit-variableduplicatebtn btn btn-default btn-sm waves-effect waves-light' tabindex='-1' aria-hidden='true'>" +
			 			'Add Existing Variable Part'+
			 			"</button>" +
			 			"</button><button class='workpermit-variableeditbtn btn btn-default btn-sm waves-effect waves-light' tabindex='-1' aria-hidden='true'>" +
			 			'Edit'+
			 			"</button>";
			 Do.validateAndDoCallback(callback(html));
		}
	 
	 
	 buildDropdownField	=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	= mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			 if(configObj.options!=undefined && configObj.options!=null && configObj.options!="")
				{
				 var optionHtml="";
					$.each(configObj.options,function(index,val)
					{
						optionHtml+="<option value='"+val+"'>"+val+"</option>";
					});
					html = 	"<select data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" id='"+configObj.iD+"' "+mandatory+" class='select-simple form-control pmd-select2 select2picker "+defaultValClass+"' style='width:100%;' tabindex='-1' aria-hidden='true'>" +
					optionHtml+"</select>";
				}
			if(configObj.masterDataReference!=undefined && configObj.masterDataReference!=null && configObj.masterDataReference!="")
			{
				if(configObj.fieldType=="LISTBOX1"){
					html = "<select multiple data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" id='"+configObj.iD+"' "+mandatory+" class='masterdataref js-data-example-ajax autocompleteforxcnreportssearch select-tags form-control pmd-select2-tags"+defaultValClass+"' style='width:100%;' tabindex='-1' aria-hidden='true' data-masterdata='"+configObj.masterDataReference+"'>" +
					"</select>";
				}else{
					html = "<select  data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" id='"+configObj.iD+"' "+mandatory+" class='masterdataref js-data-example-ajax autocompleteforxcnreportssearch select-tags form-control pmd-select2-tags"+defaultValClass+"' style='width:100%;' tabindex='-1' aria-hidden='true' data-masterdata='"+configObj.masterDataReference+"'>" +
					"</select>";
				}
		    	
		    	console.log(html);
			}
			 Do.validateAndDoCallback(callback(html));
		}
	 
	 buildSignatureField	=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			var allowedUsertoSignField = "";
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			 if(fieldName.toLowerCase().indexOf("approver")!=-1){
				 allowedUsertoSignField="F009";
			 }
			 else if(fieldName.toLowerCase().indexOf("contractor")!=-1){
				 allowedUsertoSignField = "F012";
			 }
		 html= "<div class='signdiv'>"+
			"<button data-userfield='"+allowedUsertoSignField+"' "+defaultValAttr+" "+editable+" data-permity-mode='normal' class='signpopup-btn btn btn-default btn-sm waves-effect waves-light "+defaultValClass+"' id='"+configObj.iD+"_signpopup-btn"+"'>"+
			"	<span><i class='fa fa-pencil-square-o' aria-hidden='true'></i> Add Signature</span>"+
			"</button>"+
			"<div class='sign-contentimage' style='display:none;' id='"+configObj.iD+"_signcontentimg"+"'>"+
			"<span class='closesign'>"+
			"<i class='fa fa-trash'></i>"+
			"</span>"+
			"<img data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+mandatory+" src='' id='"+configObj.iD+"'>"+
			"</div>"+
			"</div>";
			Do.validateAndDoCallback(callback(html));
		}
	 
	 buildCaptureField	=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			 var fieldId = "\'"+configObj.iD+"\'";
			html =  '<div style="position:relative;">'
	          	+'<input class="imagecapture-txt" type="text" '+editable+' class="form-control" placeholder="Click here to Browse...">'
	          	/*+'<input type="file" name="files" '+editable+' id="" accept="image/*;capture=camera" onchange="previewFile(\'safetyMeasuresAnnotate\')">'*/
	          	+'<input type="file" name="files" '+editable+' id="" accept="image/*;capture=camera" onchange="previewFile('+fieldId+', this)">'
	          	+'</div>'
	          	+'<div class="imagecapture" '+mandatory+' id="'+configObj.iD+'" data-fieldtype="'+configObj.fieldType+'" data-fieldname="'+fieldName+'" data-fieldId="'+configObj.iD+'"></div>';
			Do.validateAndDoCallback(callback(html));
		}
	 
	 buildCheckBoxField	=	function(configObj,callback)
		{
			var html	=	"";
			var optionsHtml	=	"";
			var mandatory = "";
			var editable	=	"";
			var isMandatory	=	false;
			var isEditable	=	true;
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 isMandatory	=	true;
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 isEditable	=	false;
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			if(configObj.options!=undefined && configObj.options!=null && configObj.options!="")
			{
				$.each(configObj.options,function(index,val)
				{
					optionsHtml+="<fieldset class='form-check'>"+
					"<input class='filled-in' "+defaultValAttr+" name='"+configObj.iD+"' "+editable+" "+mandatory+"  type='checkbox' value='"+val+"'> " +
							"<label for='"+configObj.iD+"' style='padding-left: 30px;'>"+val+"</label>"+
					"</fieldset>";
				});
				html	=	"<div class='form-inline' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' id='"+configObj.iD+"'>"+
				optionsHtml+
			       " </div>";
				Do.validateAndDoCallback(callback(html));
			}
			else if(configObj.masterDataReference!=undefined && configObj.masterDataReference!=null && configObj.masterDataReference!="")
			{
				html	=	"<div "+defaultValAttr+" data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' data-mandatory="+isMandatory+" data-editable="+isEditable+" data-masterdata='"+configObj.masterDataReference+"' data-dependentfieldvalue='"+configObj.workpermitTypeName+"' class='form-inline masterdataref "+defaultValClass+"' id='"+configObj.iD+"'>"+
							" </div>";
				Do.validateAndDoCallback(callback(html));
			}
		}
	 
	 buildTextField	=	function(configObj,callback)
	 {
		var html	=	"";
		var mandatory = "";
		var editable	=	"";
		var defaultValAttr	=	"";
		var defaultValClass	=	"";
		var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
		{
			 mandatory	=mandatoryAttr;
		}
		 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
		 {
			 editable= disabledContent;
		 }
		 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
		 {
			 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
			 defaultValClass="setdefaultval";
		 }
		html= "<input type='text' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" "+mandatory+" class='form-control "+defaultValClass+"' id='"+configObj.iD+"'>";
		Do.validateAndDoCallback(callback(html));
	 }
	 
	 buildTimeField=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			html =  "<input type='text' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" "+mandatory+" class='form-control timepicker "+defaultValClass+"'id='"+configObj.iD+"'>";
			Do.validateAndDoCallback(callback(html));
		}
	 
	 buildDateField=	function(configObj,callback)
		{
			var html	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			html =  "<input type='text' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" "+mandatory+" class='form-control datepicker "+defaultValClass+"'id='"+configObj.iD+"'>";
			Do.validateAndDoCallback(callback(html));
		}
	 
	 buildAutoSuggestField	=	function(configObj,callback)
	{
		var html	=	"";
		var mandatory = "";
		var autosuggestClass	=	"";
		var editable	=	"";
		var defaultValAttr	=	"";
		var defaultValClass	=	"";
		var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
		{
			 mandatory	=mandatoryAttr;
		}
		 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
		 {
			 editable= disabledContent;
		 }
		 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
		 {
			 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
			 defaultValClass="setdefaultval";
		 }
		if(configObj.masterDataReference!=undefined && configObj.masterDataReference!=null && configObj.masterDataReference!="")
		{
			if(configObj.masterDataReference=="LINE")
			{
				autosuggestClass	=	"autosuggest-LINE";
			}
			else if(configObj.masterDataReference=="AREA")
			{
				autosuggestClass	=	"autosuggest-AREA";
			}
			else if(configObj.masterDataReference=="PROJECTLEAD")
			{
				autosuggestClass	=	"autosuggest-PROJECTLEAD";
			}
			else if(configObj.masterDataReference=="USER")
			{
				autosuggestClass	=	"autosuggest-USER";
			}
			else if(configObj.masterDataReference=="MENTOR")
			{
				autosuggestClass	=	"autosuggest-MENTOR";
			}
			else if(configObj.masterDataReference=="PILLAR")
			{
				autosuggestClass	=	"autosuggest-PILLAR";
			}
			else if(configObj.masterDataReference=="LOSSTYPE")
			{
				autosuggestClass	=	"autosuggest-LOSSTYPE";
			}
			else if(configObj.masterDataReference=="TOOL")
			{
				autosuggestClass	=	"autosuggest-TOOL";
			}
			else if(configObj.masterDataReference=="BENEFITTYPE")
			{
				autosuggestClass	=	"autosuggest-BENEFITTYPE";
			}
			else 
			{
				autosuggestClass	=	"autosuggest-USER";
			}
		}
		else
		{
			autosuggestClass	=	"autosuggest-USER";
		}
		html	=	"<input type='text' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' "+defaultValAttr+" "+editable+" "+mandatory+" class='form-control "+autosuggestClass+" "+defaultValClass+"' id='"+configObj.iD+"'>";
		Do.validateAndDoCallback(callback(html));
	}
	
	 buildNumberField	=	function(configObj,callback)
	{
		var html	=	"";
		var mandatory = "";
		var editable	=	"";
		var defaultValAttr	=	"";
		var defaultValClass	=	"";
		var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
			 mandatory	=mandatoryAttr;
			}
		 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
		 {
			 editable= disabledContent;
		 }
		 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
		 {
			 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
			 defaultValClass="setdefaultval";
		 }
		html	=	"<input type='text' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' class='number form-control "+defaultValClass+"' "+defaultValAttr+" "+editable+" "+mandatory+" id='"+configObj.iD+"'>";
		Do.validateAndDoCallback(callback(html));
	}
	
	
	 buildRadioButtonField	=	function(configObj,callback)
	{
			var html	=	"";
			var optionsHtml	=	"";
			var mandatory = "";
			var editable	=	"";
			var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
			 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
				 mandatory	=mandatoryAttr;
			}
			 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
			 {
				 editable= disabledContent;
			 }
			 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
			 {
				 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
				 defaultValClass="setdefaultval";
			 }
			if(configObj.Options!=undefined && configObj.Options!=null && configObj.Options!="")
			{
				$.each(configObj.Options.split(","),function(index,val)
				{
					optionsHtml+="<fieldset class='form-group'>"+
					"<input "+defaultValAttr+" class='"+defaultValClass+"' name='"+configObj.iD+"' id='"+configObj.iD+"_"+val+"' "+editable+" "+mandatory+"  type='radio' value='"+val+"'> <label for='"+configObj.iD+"_"+val+"'>"+val+"</label>"+
					"</fieldset>";
				});
			}
			/*else if(configObj.masterDataReference!=undefined && configObj.masterDataReference!=null && configObj.masterDataReference!="")
			{
				if(configObj.masterDataReference=="COLLECTIVES")
				var data	=	null;
					fetchAndbuildCollectiveProtectionOption(configObj.workpermitTypeName,function(){
						data	=	"";
				});
			}*/
			else
			{
				optionsHtml	=	"<fieldset class='form-group'>"+
									"<input "+defaultValAttr+" class='"+defaultValClass+"' name='"+configObj.iD+"' "+editable+" id='"+configObj.iD+"_YES' type='radio' value='Yes'> <label for='"+configObj.iD+"_YES'>Yes</label>"+
								"</fieldset>"+
								"<fieldset class='form-group'>"+
									"<input "+defaultValAttr+" class='"+defaultValClass+"' name='"+configObj.iD+"' "+editable+" id='"+configObj.iD+"_NO' value='No' type='radio'> <label for=id='"+configObj.iD+"_NO'>No </label>"+
								"</fieldset>";
			}
			html	=	"<div class='form-inline' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' id='"+configObj.iD+"'>"+
						optionsHtml+
					       " </div>";
			Do.validateAndDoCallback(callback(html));
		}
	
	 buildAttachmentField	=	function(configObj,callback)
	{
		 var mandatory = "";
		 var editable	=	"";
		 var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
		 {
			 mandatory	=mandatoryAttr; 
		 }
		 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
		 {
			 editable= disabledContent;
		 }
		 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
		 {
			 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
			 defaultValClass="setdefaultval";
		 }
	 	html =  "<div class='attachment' "+editable+" "+mandatory+" data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' id='"+configObj.iD+"' style=''>" +
	 	"<button class='btn btn-default btn-sm waves-effect waves-light workpermit-attachbtn'>"+
								"<span><i class='fa fa-paperclip' aria-hidden='true'></i> Add Attachment</span>"+
							"</button>"+
							
							"<div style='min-height:60px;max-height: 250px;border-bottom: 1px solid #ccc;overflow: auto;'>"+
							"<ul class='list-unstyled attachdesign imageattach'>"+
							"</ul>"+
							"</div>"+
	 			"</div>";
	 	Do.validateAndDoCallback(callback(html));
	}
	 
	 buildTextAreaField	=	function(configObj,callback)
		{
		 var mandatory = "";
		 var editable	=	"";
		 var defaultValAttr	=	"";
			var defaultValClass	=	"";
			var fieldName	=	configObj.fieldName.replace(/[^a-zA-Z0-9]+/g,'');
		 if(configObj.isMandatory!=null &&configObj.isMandatory!=undefined && configObj.isMandatory!="" && configObj.isMandatory=="YES")
			{
			 mandatory	=mandatoryAttr;
			}
		 if(configObj.byDefaultEditable!=null &&configObj.byDefaultEditable!=undefined && configObj.byDefaultEditable!="" && configObj.byDefaultEditable=="NO")
		 {
			 editable= disabledContent;
		 }
		 if(configObj.defaultValue!=undefined && configObj.defaultValue!=null && configObj.defaultValue!="")
		 {
			 defaultValAttr	=	"data-defaultval='"+configObj.defaultValue+"'";
			 defaultValClass="setdefaultval";
		 }
		 	html =  "<textarea rows='5' data-fieldtype='"+configObj.fieldType+"' data-fieldname='"+fieldName+"' class='form-control "+defaultValClass+"' "+defaultValAttr+" "+editable+" "+mandatory+" id='"+configObj.iD+"'></textarea>";
		 	Do.validateAndDoCallback(callback(html));
		}
	 
	 bindAutosuggestField	=	function(callback)
		{
			/*$('input.autosuggest-LINE').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'));
			});
			$('input.autosuggest-AREA').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});
			$('input.autosuggest-PROJECTLEAD').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});
			$('input.autosuggest-USER').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'));
			});
			$('input.autosuggest-MENTOR').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});
			$('input.autosuggest-PILLAR').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});
			$('input.autosuggest-LOSSTYPE').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'));
			});
			$('input.autosuggest-TOOL').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});
			$('input.autosuggest-BENEFITTYPE').each(function()
			{
				bindautosuggestforpillar($(this).attr('id'),'');
			});*/
			Do.validateAndDoCallback(callback);
		}
		
			bindApproverList	=	function(callback) // yet to configure for dropdown
		{
			$('#F014').on('change',function(){
				loader.show();
			//	var siteName	=	$(this).val();
				var siteCode	=	$(this).attr('data-code');
				if(siteCode!=null && siteCode!=undefined && siteCode!=""){

					fetchAndbuildApproverDropdown(siteCode,function(response)
					{
						if(response!=null && response!=undefined && response!="")
						{
						var optionsHtml="";
						$.each(response,function(index,doc)
						{
						obj = doc.data();
						obj.ID	=	doc.id;
						optionsHtml+='<option value="'+obj.email+'" data-emailUri="'+obj.emailUri+'" id="'+obj.ID+'">'+obj.email+'</option>';
						});
						$('#F009').html(optionsHtml).select2({
							placeholder: "Select",
			                allowClear: true,
			                theme: "bootstrap",
						});
						$('#F009').val("").trigger('change');
						loader.hide();
						}
						else
						{
							$('#F009').html("").select2({
								placeholder: "Select",
				                allowClear: true,
				                theme: "bootstrap",
							});
							$('#F009').val("").trigger('change');
							loader.hide();
						}
					});
			
				}
				else{
					loader.hide();
				}
			});
			Do.validateAndDoCallback(callback);
		}
		
			bindListBoxField	=	function(formId,callback){
				$(formId).find('.select2picker').each(function(){
					$(this).select2({
						placeholder: "Select",
		                allowClear: true,
		                theme: "bootstrap",
					});
					$(this).val('').trigger('change');
				});
				Do.validateAndDoCallback(callback);
			}
		
		bindDateTimePickerField	=	function(formId,callback)
		{
			$(formId).find('.datepicker').each(function()
			{
				if($(this).attr('data-fieldname')=='ActualCompletionDate'){
					$(this).bootstrapMaterialDatePicker({ format : 'DD/MM/YYYY', time: false, maxDate : new Date() });
				}
				else {
					$(this).bootstrapMaterialDatePicker({ format : 'DD/MM/YYYY', time: false });
				}
			});
			
			$(formId).find('.timepicker').each(function()
					{
				$(this).bootstrapMaterialDatePicker({ date: false,format: 'HH:mm', });
					});
			Do.validateAndDoCallback(callback);
		}
		
		fetchAndbuildMasterData	=	function(currentElem,callback)
		{
			var id	=	$(currentElem).attr('id');
			var masterdataType	=	$(currentElem).attr('data-masterdata');
			var defaultValue	=	$(currentElem).attr('data-defaultval');
			var type  = $(currentElem).attr('data-fieldtype');
			if(type=="CHECKBOX"){
				type = 'checkbox';
			}
			else if(type=="RADIO"){
				type="radio";
			}
			if(masterdataType!=undefined && masterdataType.indexOf("CollectiveAndPersonalProtection")!=-1)
			{
				var dependentfieldValue	=	$(currentElem).attr('data-dependentfieldvalue');
				var mandatory	=	"";
				var editable	=	"";
				if($(currentElem).attr('data-mandatory')!=undefined && $(currentElem).attr('data-mandatory')!=null && $(currentElem).attr('data-mandatory').indexOf("true")!=-1)
				{
					mandatory	=	mandatoryAttr;
				}
				if($(currentElem).attr('data-editable')!=undefined &&$(currentElem).attr('data-editable')!=null && $(currentElem).attr('data-editable').indexOf("false")!=-1)
				{
					editable	=	disabledContent;
				}
				
			}
			else
			{
				if(type=="LISTBOX1"){
					bindautosuggestforpillar1(id,masterdataType);
					Do.validateAndDoCallback(callback);
				}else{
					bindautosuggestforpillar(id,masterdataType);
					Do.validateAndDoCallback(callback);
				}
				
			}
		}
		
		
		setDefaultValueToFields	=	function(currentElem,callback)
		{
			var val	=	$(currentElem).attr('data-defaultval');
			if(val!=null && val!=undefined && val!="" && val!=0)
			{
				if(val.indexOf("CURRENTDATE")!=-1)
				{
					val	=	localTimezone.get(new Date().getTime());
					if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
					{
						$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
					}
					else
					{
						$(currentElem).val(val);
					}
					$(currentElem).removeClass('setdefaultval');
					$(currentElem).removeAttr('data-defaultval');
					Do.validateAndDoCallback(callback);
				}
				else if(val.indexOf("LOGGEDINUSERNAME")!=-1)
				{
					val	=	window.userName;
					if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
					{
						$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
					}
					else
					{
						$(currentElem).val(val);
					}
					$(currentElem).removeClass('setdefaultval');
					$(currentElem).removeAttr('data-defaultval');
					Do.validateAndDoCallback(callback);
				}
				else if(val.indexOf("LOGGEDINUSEREMAIL")!=-1)
				{
					val	=	window.useremailid;
					if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
					{
						$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
						$(currentElem).find('input[value="'+val+'"]').attr('data-emailUri',loggedinuserEmailURI);
					}
					else
					{
						$(currentElem).val(val);
						$(currentElem).attr('data-emailUri',loggedinuserEmailURI);
					}
					$(currentElem).removeClass('setdefaultval');
					$(currentElem).removeAttr('data-defaultval');
					Do.validateAndDoCallback(callback);
				}
				else if(val.indexOf("LOGGEDINUSERSITE")!=-1)
				{
					var alreadyExistingVal = $(currentElem).val();
					if(alreadyExistingVal==undefined || alreadyExistingVal==null ||alreadyExistingVal=="")
					{
						val	=	"";
						/*fetchEDDetails(loggedinuserEmailURI,function(response)
						{*/
							alreadyExistingVal = $(currentElem).val();
							if(alreadyExistingVal==undefined || alreadyExistingVal==null ||alreadyExistingVal==""){
								if(window.edResponse!=null && window.edResponse!=undefined && window.edResponse!="")
								{
									//var data	=	response.data;
									val	=	window.edResponse.siteName;
									if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
									{
										$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
										$(currentElem).find('input[value="'+val+'"]').attr('data-code',window.edResponse.siteCode);
									}
									else
									{
										$(currentElem).attr('data-code',window.edResponse.siteCode);
										$(currentElem).val(val).trigger('change');
									}
								}
								$(currentElem).removeClass('setdefaultval');
								$(currentElem).removeAttr('data-defaultval');
								Do.validateAndDoCallback(callback);
							}
							else
							{
								Do.validateAndDoCallback(callback);
							}
						//});
					}
				}
				else if(val.indexOf("LOGGEDINUSERBG")!=-1)
				{
					var alreadyExistingVal = $(currentElem).val();
					if(alreadyExistingVal==undefined || alreadyExistingVal==null ||alreadyExistingVal=="")
					{
						val	=	"";
						/*fetchEDDetails(loggedinuserEmailURI,function(response)
						{
							*/alreadyExistingVal = $(currentElem).val();
							if(alreadyExistingVal==undefined || alreadyExistingVal==null ||alreadyExistingVal==""){
								if(window.edResponse!=null && window.edResponse!=undefined && window.edResponse!="")
								{
								/*if(response!=null && response.Status=="Success")
								{*/
									//var data	=	response.data;
									val	=	edResponse.bg;
									if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
									{
										$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
										$(currentElem).find('input[value="'+val+'"]').attr('data-code',edResponse.bgShortName);
									}
									else
									{
										$(currentElem).attr('data-code',edResponse.bgShortName);
										$(currentElem).val(val).trigger('change');
									}
								}
								$(currentElem).removeClass('setdefaultval');
								$(currentElem).removeAttr('data-defaultval');
								Do.validateAndDoCallback(callback);
							}
							else
							{
								Do.validateAndDoCallback(callback);
							}
						//});
					}
				}
				else if(val.indexOf("LOGGEDINUSERPHONE")!=-1)
				{
					if(window.edResponse!=null && window.edResponse!=undefined && window.edResponse!="")
					{
						val	=	edResponse.phoneNumber;
						if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
						{
							$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
						}
						else
						{
							$(currentElem).val(val);
						}
					}
					$(currentElem).removeClass('setdefaultval');
					$(currentElem).removeAttr('data-defaultval');
					Do.validateAndDoCallback(callback);
				}
				else
				{
					if($(currentElem).find('input')!=undefined && $(currentElem).find('input')!=undefined!=null && $(currentElem).find('input')!="" && $(currentElem).find('input').length>0)
					{
						$(currentElem).find('input[value="'+val+'"]').prop('checked',true);
					}
					else
					{
						$(currentElem).val(val);
					}
					$(currentElem).removeClass('setdefaultval');
					$(currentElem).removeAttr('data-defaultval');
					Do.validateAndDoCallback(callback);
				}
			}
			else
			{
				Do.validateAndDoCallback(callback);
			}
		}
		
		
		bindVisibilityCondition	=	function(callback)
		{
			$('.conditionexist').each(function(){
				var condition	=	$(this).attr('data-condition');
				if(condition!=undefined && condition!=null && condition!="")
				{
					var conditionArr	=	decodeURIComponent(condition).split(",");
					var elem	=$(this);
					if(conditionArr.length==3)
					{
						var event	="change";
						var selector	="";
						if($("#"+conditionArr[0])!=undefined && $("#"+conditionArr[0])[0].nodeName=="DIV")
						{
							selector = '#'+conditionArr[0]+' input,#'+conditionArr[0]+' select';
						}
						else
						{
							selector='#'+conditionArr[0]	;
						}
						$(document).on('change',selector,function()
						{
							var val	=	"";
							if($(this).attr('type')!=undefined && $(this).attr('type').indexOf("radio")!=-1)
							{
								val	=	$(this).closest('div').find('input:checked').val();
							}
							else if($(this).attr('type')!=undefined && $(this).attr('type').indexOf("checkbox")!=-1 )
							{
								var arr=new Array();
								$(this).closest('div').find('input:checked').each(function(){
									arr.push($(this).val());
								});
								val = arr;
							}
							else
							{
								val	=	$(this).val();
							}
							if(val!=null && val!=undefined && val!="")
							{
								if(conditionArr[1]=="=")
								{
									if(val.indexOf(conditionArr[2].trim())!=-1)
									{
										$(elem).show();
									}
									else
									{
										$(elem).hide();
									}
								}
								else if(conditionArr[1]=="!=")
								{
									if(val.indexOf(conditionArr[2].trim())==-1)
									{
										$(elem).show();
									}
									else
									{
										$(elem).hide();
									}
								}
								else
								{
									$(elem).hide();
								}
							}
							else
							{
								$(elem).hide();
							}
						});
			
					}
				}
				$(this).removeAttr('data-condition');
				$(this).removeClass('conditionexist');
			});
			Do.validateAndDoCallback(callback);
		}
		function camelize(str) { 
			return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) { return index == 0 ? word.toLowerCase() : word.toUpperCase(); }).replace(/\s+/g, ''); 
		}
		
		setValuesToFields	=	function(workpermitdata,fieldConfigData, callback)
		{
			
			
			
			console.log("workpermitdata  "+JSON.stringify(workpermitdata))
			$.each(workpermitdata,function(prop,value){
				
				if(true && value!=null && value!=undefined && value!="")
				{
					
					var fieldId	=	"";
					var fieldArr	=	[];
					
					for(s  in fieldConfigData) {
						
						for(t in fieldConfigData[s]) {
							if(prop == camelize(fieldConfigData[s][t].fieldName)) {
								fieldArr.push(fieldConfigData[s][t])
								fieldId = fieldConfigData[s][t].iD;
							}
						}
						
						
					}
					
					
					
					if(fieldArr!=null && fieldArr!=undefined && fieldArr!="" && fieldArr.length>0){
						if(fieldArr[0].fieldType=="TEXT" || fieldArr[0].fieldType=="AUTOSUGGEST" || fieldArr[0].fieldType=="NUMBER")
						{
							$('#'+fieldId).val(value);
						}else if(fieldArr[0].fieldType=="BIG TEXT") {
							if(typeof value == 'object') {
								$('#'+fieldId).val(value.value);
							}else {
								$('#'+fieldId).val(value);
							}
						}else if(fieldArr[0].fieldType=="DATE")
						{
							if(value!=0){
							$('#'+fieldId).val(localTimezone.get(value));
							}
						}
						else if(fieldArr[0].fieldType=="TIME")
						{
							if(value!=0){
								$('#'+fieldId).val(value);
							}
						}
						else if(fieldArr[0].fieldType=="RADIO")
						{
							$('#'+fieldId).find('input[value="'+value+'"]').prop('checked',true);
						}
						else if(fieldArr[0].fieldType=="CHECKBOX")
						{
							var arr = new Array();
							$.each(value,function(index,val){
								if($('#'+fieldId).find('input[value="'+val+'"]')!=undefined&&$('#'+fieldId).find('input[value="'+val+'"]')!=null&&$('#'+fieldId).find('input[value="'+val+'"]')!=""&&$('#'+fieldId).find('input[value="'+val+'"]').length>0){
									$('#'+fieldId).find('input[value="'+val+'"]').prop('checked',true);
								}
								else{
									arr.push(val);
									$('#'+fieldId).find('input[value="'+val+'"]').prop('checked',true);
								}
							});
							$('#'+fieldId).attr('data-setvalue',JSON.stringify(arr));
						}
						else if(fieldArr[0].fieldType=="FILE")
						{
							var attachObj	=	value;
							if(attachObj!=null && attachObj!=undefined && attachObj!="")
							{
								if(attachObj!=undefined && attachObj!=null && attachObj!="")
								{
									$.each(attachObj,function(index,obj){
										loader.show();
										var attachmentObj	=	fetchAttachmentDetailById(obj.attachmentId,function(attachmentObj){
										if(attachmentObj!=null && attachmentObj!=undefined && attachmentObj!="")
										{
											var downloadlink	=	'/attachments/FileDownload?type=WorkPermitAttachments&attchemnetID='+attachmentObj.attachmentId+'&filename='+attachmentObj.fileName+'&refID=';
											var htmlcontent = 	'<li id="'+attachmentObj.attachmentId+'">'
											+'<div class="cloneimage">'
											+'<img src="'+downloadlink+'" />'
											+'</div>'
											+'<a class="filedownld workpermitfiledownload" data-link="'+downloadlink+'" data-filetype="file" data-file ="'+attachmentObj.fileName+'" data-attachmentid="'+attachmentObj.attachmentId+'">'+attachmentObj.fileName+'</a>'
											+'<span class="attchmentdelete">'
											+'<i class="fa fa-times"></i>'
											+'</span>'
											+'</li>';
											$('#'+fieldId+' .attachdesign').append(htmlcontent);
												loader.hide();
										}
									});
									});
								}
								if(attachObj["link"]!=undefined && attachObj["link"]!=null && attachObj["link"]!="")
								{
									$.each(attachObj["link"],function(index,obj){
										var htmlcontent = 	'<li data-link="'+obj.fileLink+'">'
										+'<a class="filedownld workpermitfiledownload" data-filetype="link" data-file ="'+obj.fileName+'" data-link="'+obj.fileLink+'">'+obj.fileName+'</a>'
										+'<span class="attchmentdelete">'
										+'<i class="fa fa-times"></i>'
										+'</span>'
										+'</li>';
										$('#'+fieldId+' .attachdesign').append(htmlcontent);
									});
								}
							}
						}
				else if(fieldArr[0].fieldType=="LISTBOX"){
					
						//bindautosuggestforpillar(fieldId,fieldArr[0].masterDataReference);
						console.log("fieldArr[0].fieldName "+ fieldArr[0].fieldName)
						if(fieldArr[0].fieldName=="Mentor"){
							bindautosuggestforpillar(fieldId,fieldArr[0].masterDataReference);
							
							var newOption = new Option(request.data.mentorName,request.data.mentorId+"&&&&"+request.data.mentor,request.data.mentor, false, false);
							$('#'+fieldId).append(newOption).trigger('change');
							/*var htmlOption = ""	
									htmlOption += '<option EMAIL="'+request.data.mentor+'" value='+request.data.mentorId+'>' +request.data.mentorName+ '</option>'
								$('#'+fieldId).append(htmlOption).trigger('change');*/
						
						//	$('#'+fieldId).val(request.data.mentorId).trigger('change');
						}
						else if(fieldArr[0].fieldName=="Project Lead"){
							bindautosuggestforpillar(fieldId,fieldArr[0].masterDataReference);
							/*var htmlOption = ""	
								htmlOption += '<option EMAIL="'+request.data.projectLead+'" value='+request.data.projectLeadId+' >' +request.data.projectLeadName+ '</option>'
							$('#'+fieldId).append(htmlOption).trigger('change');*/
							var newOption = new Option(request.data.projectLeadName,request.data.projectLeadId+"&&&&"+request.data.projectLead, false, false);
							$('#'+fieldId).append(newOption).trigger('change');
						//	$('#'+fieldId).val(request.data.mentorId).trigger('change');
						}else{
							bindautosuggestforpillar(fieldId,fieldArr[0].masterDataReference);
							//$('#'+fieldId).html('<option value="'+value+'" >' + value+ '</option>').select2();
							var newOption = new Option(value,value, false, false);
							$('#'+fieldId).append(newOption).trigger('change');
						//	$('#'+fieldId).val(value).trigger('change');
						}
						
						
				}else if(fieldArr[0].fieldType=="LISTBOX1"){
					
					bindautosuggestforpillar1(fieldId,fieldArr[0].masterDataReference);
					
					if(fieldArr[0].fieldName=="Team Members"){
						console.log("value.value---- "+value.value);
						var jsonval=JSON.parse(value.value);
						var arrayofoptions = "";
						var idlist=new Array();
							for(i in jsonval) {
								console.log("test test --> "+jsonval[i].name+"   "+jsonval[i].email+"  "+jsonval[i].id)
								 arrayofoptions += '<option value="'+jsonval[i].id+"&&&&"+jsonval[i].email+'" >' +jsonval[i].name+ '</option>';
								idlist.push(jsonval[i].id+"&&&&"+jsonval[i].email);
							//arrayofoptions.push( new Option(jsonval[i].name,jsonval[i].id+"&&&&"+jsonval[i].email, false, false));
							}
							
							$('#'+fieldId).append(arrayofoptions);
							$('#'+fieldId).val(idlist).trigger('change');
							//$('#'+fieldId).append(arrayofoptions).trigger('change');
					}else{
						var arrayofoptions = "";
							for(i in value) {
								 arrayofoptions += '<option value="'+value[i]+'" >' +value[i]+ '</option>';
								//idlist.push(jsonval[i].id+"&&&&"+jsonval[i].email);
							//arrayofoptions.push( new Option(jsonval[i].name,jsonval[i].id+"&&&&"+jsonval[i].email, false, false));
							}
							
							$('#'+fieldId).append(arrayofoptions);
							$('#'+fieldId).val(value).trigger('change');
						//$('#'+fieldId).val(value).trigger('change');
					}	
					
					
			}
			}
		}
	});
			$('#reqid_label').text(workpermitdata.id);
			$('#reqname_label1').text(workpermitdata.formId);
			$('#status_label').text(workpermitdata.status);
			$('#plant_label').text(workpermitdata.plant);
			$('#created_by_label').text(workpermitdata.createdBy);
			if(workpermitdata.createdOn != undefined) {
				$('#created_on_label').text(localTimezone.get(workpermitdata.createdOn));
			}
			window.variableFieldConfigurations =	workpermitdata.variableFieldConfigurations;
//			$('#workpermit-form').attr('data-idPrefix',workpermitdata.workPermitIdPrefix);
			$('#workpermit-form').attr('data-workpermitname',workpermitdata.formType);
			$('#workpermit-form').attr('data-id',workpermitdata.id);
			//$('#workpermit-form').attr('data-bg',workpermitdata.requesterBG);
			Do.validateAndDoCallback(callback);
		}
	
		buildCheckboxVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="CHECKBOX">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 		+'<div class="varfield-holder">'
			 			+'<div class="checkboxWrapper">'
			 				+'<div class="d-flex varfield-content">'
			 					+'	<i class="fa fa-square-o active"></i>'
			 					+'<input type="text" class="form-control variablecheckbox-option" data-required="yes" data-type="text" placeholder="Option">'
			 					+'<span class="variablepart-checkboxvaloptionremove"><i class="material-icons">close</i></span>'
			 				+'</div>'
			 			+'</div>'
			 			+'<div class="addoption checkboxaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		
		buildRadioVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="RADIO">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 		+'<div class="varfield-holder">'
			 			+'<div class="radioWrapper">'
			 				+'<div class="d-flex varfield-content">'
			 					+'	<i class="fa fa fa-circle-o"></i>'
			 					+'<input type="text" class="form-control variableradio-option" data-required="yes" data-type="text" placeholder="Option">'
			 					+'<span class="variablepart-radiovaloptionremove"><i class="material-icons">close</i></span>'
			 				+'</div>'
			 			+'</div>'
			 			+'<div class="addoption radioaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		
		buildTextVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="TEXT">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 +'<input type="text" class="form-control variablepart-textinput" disabled="" placeholder="Short Text">'
			 +'</div></div>';
		}
		
		buildTextAreaVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="BIG TEXT">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 +'<textarea rows="5" class="form-control variablepart-textareainput" placeholder="Long Text" disabled=""></textarea>'
			 +'</div></div>';
		}
		
		buildListBoxVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="LISTBOX">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 		+'<div class="varfield-holder">'
			 			+'<div class="listboxWrapper">'
			 				+'<div class="d-flex varfield-content">'
			 					+'<input type="text" class="form-control variablelist-option" data-required="yes" data-type="text" placeholder="Option">'
			 					+'<span class="variablepart-listvaloptionremove"><i class="material-icons">close</i></span>'
			 				+'</div>'
			 			+'</div>'
			 			+'<div class="addoption listboxaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		buildDateVariableField	=	function(){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="DATE">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name">'
			 +'<input type="text" class="form-control variablepart-dateinput" disabled="" placeholder="Choose Date">'
			 +'</div></div>';
		}
		
		
		buildCheckboxVariableFieldConfig	=	function(configObj){
			var optionHtml	=	"";
			if(configObj.Options!=undefined && configObj.Options!=null &&configObj.Options!="")
			{
				$.each(configObj.Options.split(","),function(index,val){
					optionHtml+='<div class="d-flex varfield-content">'
						+'	<i class="fa fa-square-o active"></i>'
						+'<input type="text" class="form-control variablecheckbox-option" data-required="yes" data-type="text" placeholder="Option" value="'+val+'">'
						+'<span class="variablepart-checkboxvaloptionremove"><i class="material-icons">close</i></span>'
					+'</div>';
				});
			}
			return '<div class="form-group choosefieldtypediv" data-fieldtype="CHECKBOX" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 		+'<div class="varfield-holder"><div class="checkboxWrapper">'
			 		+optionHtml
			 			+'</div><div class="addoption checkboxaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		
		buildRadioVariableFieldConfig	=	function(configObj){
			var optionHtml	=	"";
			if(configObj.Options!=undefined && configObj.Options!=null &&configObj.Options!="")
			{
				$.each(configObj.Options.split(","),function(index,val){
					optionHtml+='<div class="d-flex varfield-content">'
 					+'	<i class="fa fa fa-circle-o"></i>'
 					+'<input type="text" class="form-control variableradio-option" data-required="yes" data-type="text" placeholder="Option" value="'+val+'">'
 					+'<span class="variablepart-radiovaloptionremove"><i class="material-icons">close</i></span>'
 				+'</div>';
				});
			}
			return '<div class="form-group choosefieldtypediv" data-fieldtype="RADIO" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 		+'<div class="varfield-holder"><div class="radioWrapper">'
			 		+optionHtml
			 			+'</div><div class="addoption radioaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		
		buildTextVariableFieldConfig	=	function(configObj){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="TEXT" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 +'<input type="text" class="form-control variablepart-textinput" disabled="" placeholder="Short Text">'
			 +'</div></div>';
		}
		
		buildTextAreaVariableFieldConfig	=	function(configObj){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="BIG TEXT" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 +'<textarea rows="5" class="form-control variablepart-textareainput" placeholder="Long Text" disabled=""></textarea>'
			 +'</div></div>';
		}
		
		buildListBoxVariableFieldConfig	=	function(configObj){
			var optionHtml	=	"";
			if(configObj.Options!=undefined && configObj.Options!=null &&configObj.Options!="")
			{
				$.each(configObj.Options.split(","),function(index,val){
					optionHtml+='<div class="d-flex varfield-content">'
 					+'<input type="text" class="form-control variablelist-option" data-required="yes" data-type="text" placeholder="Option" value="'+val+'">'
 					+'<span class="variablepart-listvaloptionremove"><i class="material-icons">close</i></span>'
 				+'</div>';
				});
			}
			return '<div class="form-group choosefieldtypediv" data-fieldtype="LISTBOX" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 		+'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 		+'<div class="varfield-holder">'+
			 		'<div class="listboxWrapper">'
			 		+optionHtml
			 			+'</div><div class="addoption listboxaddoption">Add Option</div>'
			 		+'</div>'
			 +'</div></div>';
		}
		
		buildDateVariableFieldConfig	=	function(configObj){
			return '<div class="form-group choosefieldtypediv" data-fieldtype="DATE" data-id="'+configObj.iD+'">'
			 +'<span class="fieldtrash"><i class="fa fa-trash"></i></span>'
			 +'<div class="dottedborder">'
			 +'<input type="text" class="form-control labelinput labelname-variablefield" data-required="yes" data-type="text" placeholder="Label Name" value="'+configObj.fieldName+'">'
			 +'<input type="text" class="form-control variablepart-dateinput" disabled="" placeholder="Choose Date">'
			 +'</div></div>';
		}
		
		
		buildDetailFieldsForWorkMonitoring	=	function(data,workpermitdata,callback){
			var html ="";
			if(data!=null && data!=undefined && data!=""){
				var val = workpermitdata[data.ID+"_"+data.fieldName.replace(/[^a-zA-Z0-9]+/g,'')];
				if(data.fieldType!=undefined && (data.fieldType=="TEXT" || data.fieldType=="BIG TEXT" || data.fieldType=="TIME" || data.fieldType=="AUTOSUGGEST"  || data.fieldType=="NUMBER" || data.fieldType=="RADIO" || data.fieldType=="LISTBOX")){
					html='<p>'+val+'</p>';
				}
				else if(data.fieldType!=undefined && (data.fieldType=="CHECKBOX")){
					var ulHtml = '';
					$.each(val,function(index,checkboxVal){
						ulHtml='<li>'+checkboxVal+'</li>';
					});
					html='<p><ul class="list-unstyled">'+ulHtml+'</ul></p>';
				}
				else if(data.fieldType!=undefined && data.fieldType=="FILE"){
					var attachObj	=	val;
					if(attachObj!=null && attachObj!=undefined && attachObj!="")
					{
						if(attachObj!=undefined && attachObj!=null && attachObj!="")
						{
							$.each(attachObj,function(index,fileObj){
								var downloadlink	=	'/attachments/FileDownload?type=WorkPermitAttachments&attchemnetID='+fileObj.attachmentId+'&filename='+fileObj.fileName+'&refID=';
								html = html+'<li id="'+fileObj.attachmentId+'">'
								+'<div class="cloneimage">'
								+'<img src="'+downloadlink+'" />'
								+'</div>'
								+'<a class="filedownld workpermitfiledownload" data-link="'+downloadlink+'" data-filetype="file" data-file ="'+fileObj.fileName+'" data-attachmentid="'+fileObj.attachmentId+'">'+fileObj.fileName+'</a>'
								+'</li>';
							});
						}
						if(attachObj["link"]!=undefined && attachObj["link"]!=null && attachObj["link"]!="")
						{
							$.each(attachObj["link"],function(index,obj){
								html = 	html+'<li data-link="'+obj.fileLink+'">'
								+'<a class="filedownld workpermitfiledownload" data-filetype="link" data-file ="'+obj.fileName+'" data-link="'+obj.fileLink+'">'+obj.fileName+'</a>'
								+'</li>';
							});
						}
					}
					html="<p><ul class='list-unstyled attachdesign'>"+html+"</ul></p>";
				}
				else if(data.fieldType!=undefined && data.fieldType=="DATE"){
					html='<p>'+localTimezone.get(val)+'</p>';
				}
				else if(data.fieldType!=undefined && data.fieldType=="CAPTURE"){
					$.each(val,function(index,capturedata){
						html+='<div class="annotateImage-wrapper"><a class="perviewImage"></a><img src="'+val+'"></div>';
					});
					html="<p>"+html+"</p>";
				}
				else if(data.fieldType!=undefined && data.fieldType=="SIGNATURE"){
					$.each(val,function(index,capturedata){
						html+='<p><img src="'+val+'"/></p>';
					});
				}
			}
			Do.validateAndDoCallback(callback(html));
		}
})();