function showNotification(title,text,type,delay){
	if(delay==0||isNaN(delay))
	{
		delay	=	3000;
	}
	new PNotify({
	    title: title,
	    text: text,
	    type: type,
	    delay: delay
	});
}

var Do 				=   (function()
		{
			return {
				setLanguageButton : function(languageChanged,callback)
				{
					   window.language = languageChanged;
					   if (languageChanged == "EN")
					   {
					      $("#lang-btn").html('<img src="../statics/images/en.png"> EN <span class="caret"></span>');
					      window.datatablebtn_language	=	window.databutton_englishLabelList;
					   }
					   else
					   {
					      $("#lang-btn").html('<img src="../statics/images/fr.png"> FR <span class="caret"></span>');
					      window.datatablebtn_language	=	window.databutton_frenchLabelList;
					   }
					   $('#lang-btn').parent('div').find('ul').css('display','none');
					   Do.validateAndDoCallback(callback);
				},
				validateAndDoCallback : function(callback)
				{
					   if (typeof callback === "function")
				       {
						   callback();
				       }
				},
				BackboneEncode	:   function(formValue)
				{
					  return $.trim(formValue);
				},
			};
		})();

var convertEmailToUserName	=	function(userEmail)
{
	var userName	=	"";
		if(userEmail!=undefined && userEmail!=null && userEmail!="" && userEmail.indexOf("@")!=-1)
		{
			userName	=	userEmail.split("@")[0];
			userName = userEmail.replace("."," ");
			firstName	=	userName.split(' ')[0];
			lastName	=	userName.split(' ')[1].toUpperCase();
			if(firstName.indexOf("-")!=-1)
			{
				var arr	=	firstName.split("-");
				for(var index in arr)
				{
					arr[index] = arr[index].substring(0,1).toUpperCase()+arr[index].substring(1).toLowerCase();
				}
				firstName = arr.join("-");
			}
			else
			{
				firstName	=	firstName.substring(0,1).toUpperCase()+firstName.substring(1).toLowerCase();
			}
			userName = firstName+" "+lastName;
		}
		return userName;
}
var getUsername = (function()
		{
				    return {
				        get: function(usermail)
				        {
				        	if(usermail!=null && usermail!="")
				        	{
				        		var splitedValue = usermail.split('@')[0];
				        		var firstName 	 = splitedValue.split('.')[0];
				        		var lastName 	 = splitedValue.split('.')[1].toUpperCase();
				        		var userName     = firstName.charAt(0).toUpperCase()+ firstName.substr(1) +" "+lastName;
				        		console.log("userName in getusername --"+userName);
					            return userName;
				        	}
				        	else
				        	{
				        		return "";
				        	}	
				        },
				    };
		})();
var getfirstName = (function()
		{
				    return {
				        get: function(usermail)
				        {
				        	if(usermail!=null && usermail!="")
				        	{
				        		var splitedValue = usermail.split('@')[0];
				        		var firstName 	 = splitedValue.split('.')[0];
					            return firstName;
				        	}
				        	else
				        	{
				        		return "";
				        	}	
				        },
				    };
		})();
function addWorkDays(startDate, days) 
{
    // Get the day of the week as a number (0 = Sunday, 1 = Monday, .... 6 = Saturday)
    var dow = startDate.getDay();
    var daysToAdd = days;
    // If the current day is Sunday add one day
    if (dow == 0)
        daysToAdd++;
    // If the start date plus the additional days falls on or after the closest Saturday calculate weekends
    if (dow + daysToAdd >= 6) {
        //Subtract days in current working week from work days
        var remainingWorkDays = daysToAdd - (5 - dow);
        //Add current working week's weekend
        daysToAdd += 2;
        if (remainingWorkDays > 5) {
            //Add two days for each working week by calculating how many weeks are included
            daysToAdd += 2 * Math.floor(remainingWorkDays / 5);
            //Exclude final weekend if remainingWorkDays resolves to an exact number of weeks
            if (remainingWorkDays % 5 == 0)
                daysToAdd -= 2;
        }
    }
    startDate.setDate(startDate.getDate() + daysToAdd);
    return startDate;
}

addDays = function(startDate,days) {
	  var dat = new Date(startDate);
	  dat.setDate(dat.getDate() + days);
	  return dat;
	}


//======================================================handle check box=============================================================================
handleCheckboxinDatatable				  =	  function(table,tableId)
{
		$('#'+tableId +' tbody').on('click', 'input[type="checkbox"]', function(e)
				{
					var $row = $(this).closest('tr');
					console.log($row);
					if(this.checked)
					{
						$row.addClass('selected');
					} 
					else 
					{
						$row.removeClass('selected');
					}
					e.stopPropagation();
				});

		$('thead input[name="select_all"]', table.table().container()).on('click', function(e)
		{
					if(this.checked)
					{
						$('#'+tableId +' tbody input[type="checkbox"]:not(:checked)').trigger('click');
					} 
					else 
					{
						$('#'+tableId +' tbody input[type="checkbox"]:checked').trigger('click');
					}
					e.stopPropagation();
	 });
};
  
//==============================================  get selected row ======================================================
  getSelectedRows	=	function(tableId,confirmMsg,noItemMsg,callback)
  {
				window.rows_selected = [];
				$('#'+tableId+ ' tbody .selected').each(function() 
				{
					if($(this).hasClass('selected'))
					{
						var id=$(this).find('input[type="checkbox"]').attr('data-link');
						if(id==undefined)
						{
							rows_selected.push($(this).find('a')[0].dataset.link);
						}
						else
						{
							rows_selected.push(id);
						}
						
						
					} 
				});
				if(rows_selected.length == 0)
				{
					showNotification("Error",noItemMsg,"error");
				}	 
				else
				{
					bootbox.confirm({
						message:confirmMsg,
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
							Do.validateAndDoCallback(callback);
						}
					}
					});
				}	  		
	};


function showNotification(title,text,type,delay){
	if(delay==0||isNaN(delay))
	{
		delay	=	3000;
	}
	new PNotify({
	    title: title,
	    text: text,
	    type: type,
	    delay: delay
	});
}

var SortMySelectPicker 		=   (function()
								{
									return {
										now	:   function(id)
										{
											$( ".selectpicker" ).each(function( index ) 
											{
												  var id=$(this).attr('id');
												  var my_options = $('#'+id+ ' option');
													my_options.sort(function(a,b) 
													{
													    if (a.text > b.text) return 1;
													    else if (a.text < b.text) return -1;
													    else return 0;
													});
													$('#'+id).empty().append(my_options).selectpicker("refresh");
											});
										},
									};
								})();

var currentPage =  (function()
					{
					    return {
					        get: function()
					        {
					            return document.URL.split("#")[1];
					        },
					    };
					})();

var localTimezone = (function()
					{
					    return {
					        get: function(currenttimeinMilliseconds)
					        {
					        	if(currenttimeinMilliseconds!=null && currenttimeinMilliseconds!="" && currenttimeinMilliseconds!=0)
					        	{
					        		var date=new Date(parseInt(currenttimeinMilliseconds));
					        		var year=date.getFullYear();
					        		var month=date.getMonth()+1;
					        		var date1=date.getDate();
					        		date1=date1<10?'0'+date1:date1+'';
					        		month=month<10?'0'+month:month+'';
					        		var fullDate=date1+'/'+month+'/'+year;
					        		return fullDate;
					        		/*var dates=new Date(currenttimeinMilliseconds);
							        //dates=dates.toString().split("GMT")[0];
						        	dates = dates.toLocaleDateString();
						            return dates;*/
					        	}
					        	else
					        	{
					        		return "";
					        	}	
					        },
					        formatDate:function(date)
					        {
					        	var millisec	=	0;
					        	if(date!=""&&date!=null&&date!=0)
					        	{
					        		var day	=	date.split('/')[0];
					        		var mon	=	date.split('/')[1];
					        		var year	=	date.split('/')[2];
					        		millisec	=	new Date(mon+'/'+day+'/'+year).getTime();
					        	}
					        	return millisec;
					        	
					        }
					        
					    };
					})();
function startTimer(startTime,id,rowlength) 
{
	var count=0;
   setInterval(function() {
	
	//var startTime = new Date("Aug 19, 2017 15:37:25").getTime();
    var now = new Date().getTime();
   
   // for(var i=0;i<rowlength;i++){
   
   /* if(count!=0)
    {
    	id=id+"1";
    	startTime=$('#id').attr("data");
    }*/
    var distance = startTime - now;
    
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    $('.'+id).html(days +"d:" +hours +"h:" +minutes +"m:" +seconds +"s");
   /* document.getElementsByClassName(id).innerHTML = days + "d: " + hours + "h: "
    + minutes + "m:" + seconds + "s ";*/
    
    count++;
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("testdue").innerHTML = "EXPIRED";
    }
  //  }
}, 1000);
}