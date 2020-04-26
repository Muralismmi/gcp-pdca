$(function()
{
    var autocompletURL = "/1/contacts"

    split = function(val)
    {
        return val.split(/,\s*/);
    };

    extractLast = function(term)
    {
        return split(term).pop();
    };

    highlight = function(item, searchTerm)
    {
        searchTerm = searchTerm.trim();
        searchTerm = searchTerm.replace(" ", "|");

        var matcher = new RegExp("(" + searchTerm + ")", "ig");
        var result = item.firstname + ", " + item.lastname + "(" + item.email + ")";
        return result.replace(matcher, "<strong>$1</strong>");
    };

    bindAutoSearchEvent = function (idtoBind)
    {
    	$("#"+idtoBind).bind("keydown", function(event)
    	{
    		  if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active)
    		  {
    		      event.preventDefault();
    		  }
        }).autocomplete({
    		            minLength: 2,
    		            source: function(request, response)
    		            {
    		                $.ajax(
    		                {
    		                    url: autocompletURL,
    		                    dataType: "json",
    		                    data:
    		                    {
    		                        q: extractLast(request.term)
    		                    },
    		                    success: function(persons)
    		                    {
    		                    	 $('#'+idtoBind).removeAttr('data-emailid');
    		                        response($.map(persons, function(item)
    		                        {
    		                            return {
    		                                label: highlight(item, extractLast(request.term)),
    		                                value: item.firstname+" "+item.lastname,
    		                               // value: item.email,
    		                                email:item.email,
    		                                emailUri:item.emailUri
    		                            };
    		                        }));
    		                    },
    		                    error: function(jqXHR, textStatus, errorThrown)
    		                    {
    		                        console.error(textStatus);
    		                        console.error(errorThrown);
    		                        console.error(jqXHR.responseText);
    		                    }
    		                });
    		            },
    		            change:function(event,ui)
    		           {
    		            	if(navigator.onLine){
    		            		if(ui.item==null||ui.item=="")
        		            	{
        		            		$(this).val("");
        		            	}
    		            	}
    		            	
    		           },
    		            focus: function()
    		            {
    		                // prevent value inserted on focus
    		            		return false;
    		            },
    		            
    		            select: function(event, ui)
    		            {
    		                var terms = split(this.value);
    		                // remove the current input
    		                terms.pop();
    		                // add the selected item
    		                terms.push(ui.item.email);
    		                // add placeholder to get the comma-and-space at the end
    		                $('#'+idtoBind).attr('data-emailUri',ui.item.emailUri);
    		                $('#'+idtoBind).attr('data-username',ui.item.value);
    		                if(idtoBind=="usermailid" || idtoBind=="userEmailInGroup")
    		                {
    		                	
    		                	  //terms.push("");
    		                	  this.value = terms;
    		                	  $('#'+idtoBind).removeAttr('data-emailid');
    		                	  $('#'+idtoBind).attr('data-emailid',ui.item.value.replace(/,/g ,""));
    		                }
    		                else
    		                {
    		                	this.value = terms.join(", ");
    		                }
    		                return false;
    		            }
    		            /*select: function(event, ui)
    		            {
    		                var terms = split(this.value);
    		                // remove the current input
    		                terms.pop();
    		                // add the selected item
    		                terms.push(ui.item.email.replace(/,/g ,""));
    		                // add placeholder to get the comma-and-space at the end
    		                if(idtoBind=="usermailid")
    		                {
    		                	terms.push("");
    		                    this.value = terms.join(", ");
    		                    $('#'+idtoBind).attr('data-emailid',ui.item.value.replace(/,/g ,""));
    		                }
    		                else
    		                {
    		                	this.value = terms.join(", ");
    		                }
    		                return false;
    		            }*/
    		        }).data('ui-autocomplete')._renderItem = function(ul, item)
    		        {
    		            // MA : Hack to render result in HTML
    					$('.ui-autocomplete').css({'position': 'absolute', 'z-index':'1100','width':'570px','font-family':'sans-serif','font-size':'12px'});
    		            return $("<li></li>")
    		                .data("ui-autocomplete-item", item)
    		                .append('<a>' + item.label + '</a>')
    		                .appendTo(ul);
    		            
    		        };
    };
});