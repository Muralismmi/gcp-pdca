(function () {
    //#userrole-table  //masterconfigurationtable
    fetchAndbuildMasterConfigurationList = function (configType) {
        $('#masterconfigurationtable')
            .DataTable(
                {
                    "ordering": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "bDestroy": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetailslistadmindetails/CONFIGURATION/checkbox,value,type,plant,createdBy,actions/active/true?configuration="
                        + configType,
                    dom: 'Bfrtip',
                    "columns": [
                        {
                            "data": "id",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<fieldset class="form-group"> <input id="'
                                    + data
                                    + '" type="checkbox" class="filled-in selectedcheckbox"><label for=""></label></fieldset>';
                                return htmlcontent;
                            }
                        },
                        {
                            "data": "value"
                        },
                        {
                            "data": "type"
                        },
                        {
                            "data": "plant"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "Id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i class="fa fa-edit editConfiguration-btn" id="'
                                    + data
                                    + '"></i></a><span></span><a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteConfiguration-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#masterconfiguration-modal')
                                    .modal('show');
                                $('#masterconfiguration-modal')
                                    .attr('data-id', "");
                                $('#masterconfiguration-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#masterconfiguration-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#masterconfiguration-modal')
                                    .find('.error')
                                    .removeClass('error');
                                $('#masterConfiguration-type').val(
                                    configType);
                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#masterbulk-modal')
                                    .modal('show');

                                $('#masterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        },
                        {
                            className: 'btn-default btn-sm ',
                            text: '<i class="fa fa-trash" aria-hidden="true"></i> Delete',
                            action: function (e, dt, node, config) {
                                ///alert( 'Button activated' );
                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("CONFIGURATION", configType);

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    var team;
    fetchAndbuildViewPDCAList = function () {
        $('#viewPDCAtable')
            .DataTable(
                {
                    "autoWidth": false,
                    "ordering": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "bDestroy": true,
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/REQUEST/id,formId,formType,line,station,projectLeadName,teamMembers,mentorName,primaryPillar,lossType,problemStatement,startDate,targetDate,benefitType,benefitValue,cost,tools,actualCompletionDate,status,createdBy,createdOn,createdOn/plant/"
                        + userDetails.plantName,
                    dom: 'Bfrtip',
                    "columns": [
                        {
                            "data": "formId"
                        },
                        {
                            "data": "formType"
                        },
                        {
                            "data": "line",
                            "defaultContent": ""
                        },
                        {
                            "data": "station",
                            "defaultContent": ""
                        },
                        {
                            "data": "projectLeadName",
                            "defaultContent": ""
                        },
                        {
                            "data": "teamMembers",
                            "orderable": false,
                            "render": function (data, type, full,
                                                meta) {
                                team = data;
                                debugger;
                                var jsonval = eval(data);
                                var nameArray = new Array();
                                console.log("data" + jsonval);
                                for (q in jsonval) {
                                    console.log("name from json "
                                        + jsonval[q].name);
                                    nameArray.push(" " + jsonval[q].name);
                                }
                                return nameArray;
                            }
                        },
                        {
                            "data": "mentorName",
                            "defaultContent": ""
                        },
                        {
                            "data": "primaryPillar",
                            "defaultContent": ""
                        },
                        {
                            "data": "lossType",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null)
                                    return data.toString();
                            }
                        },
                        {
                            "data": "problemStatement",
                            "defaultContent": "",
                            "render": function (data, type, full, meta) {
                                if (data)
                                    return "<div style='white-space:normal;width:200px;'>" + data + "</div>";
                            },
                        },
                        {
                            "data": "startDate",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null) {
                                    var datestring = new Date(data).toLocaleDateString('en-GB', {
                                        day: 'numeric',
                                        month: 'short',
                                        year: 'numeric'
                                    }).split(' ').join('-');
                                    if (datestring == "Invalid Date") {
                                        datestring = "";
                                    }
                                    return datestring;
                                }

                            }
                        },
                        {
                            "data": "targetDate",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null) {
                                    var datestring = new Date(data).toLocaleDateString('en-GB', {
                                        day: 'numeric',
                                        month: 'short',
                                        year: 'numeric'
                                    }).split(' ').join('-');
                                    if (datestring == "Invalid Date") {
                                        datestring = "";
                                    }
                                    return datestring;
                                }

                            }
                        },
                        {
                            "data": "benefitType",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null)
                                    return data.toString();
                            }
                        },
                        {
                            "data": "benefitValue",
                            "defaultContent": "",
                        },
                        {
                            "data": "cost",
                            "defaultContent": ""
                        }, {
                            "data": "id",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (full["benefitValue"] != 0.0 && full["benefitValue"] != null && full["cost"] != 0.0 && full["cost"] != null) {
                                    return (full["benefitValue"] / full["cost"]).toFixed(2);
                                }

                            }
                        },
                        {
                            "data": "tools",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null)
                                    return data.toString();
                            }
                        },
                        {
                            "data": "actualCompletionDate",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null) {
                                    var datestring = new Date(data).toLocaleDateString('en-GB', {
                                        day: 'numeric',
                                        month: 'short',
                                        year: 'numeric'
                                    }).split(' ').join('-');
                                    if (datestring == "Invalid Date") {
                                        datestring = "";
                                    }
                                    return datestring;
                                }

                            }
                        }, {
                            "data": "id",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (full["targetDate"] != 0 && full["targetDate"] != null && full["startDate"] != 0 && full["startDate"] != null) {
                                    const date1 = new Date(full["targetDate"]);
                                    const date2 = new Date(full["startDate"]);
                                    const diffTime = Math.abs(date2.getTime() - date1.getTime());
                                    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
                                    console.log(diffDays);
                                    return diffDays;
                                }

                            }
                        },
                        {
                            "data": "status",
                            "defaultContent": "",

                        },
                        {
                            "data": "createdBy",
                            "defaultContent": ""
                        },
                        {
                            "data": "createdOn",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null) {
                                    var datestring = new Date(data).toLocaleDateString('en-GB', {
                                        day: 'numeric',
                                        month: 'short',
                                        year: 'numeric'
                                    }).split(' ').join('-');
                                    if (datestring == "Invalid Date") {
                                        datestring = "";
                                    }
                                    return datestring;
                                }

                            }
                        },
                        {
                            "data": "createdOn",
                            "defaultContent": "",
                            "render": function (data, type, full,
                                                meta) {
                                if (data && data != null) {
                                }
                                return dateFormat(new Date(data), "hh:MM:ss");
                            }
                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, full,
                                                meta) {
                                var htmlcontent = '<a class="" href="#pdca/'
                                    + full.formType
                                    + '/'
                                    + data
                                    + '"><i class="fa fa-edit " ></i></a><span></span>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [{
                        className: 'btn-default btn-sm',
                        text: '<i class="fa fa-plus "  aria-hidden="true"></i> Add',
                        action: function (e, dt, node, config) {
                            $('#chooseworktype-modal').modal('show');

                        }
                    }, {
                        className: 'btn-default btn-sm',
                        text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                        action: function (e, dt, node, config) {
                            generateExport("REQUEST", "");

                        }
                    }],
                    "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
        changeName($("#viewPDCAtable").find("td"), true, "");
    }
    /*	<th>Title</th>
        <th>Name</th>
        <th>Mail Id</th>
        <th>Plant</th>
        <th>Pillar</th>
        <th>Is Pillar Leader</th>
        <th>Is Editor</th>
        <th>Is Pillar Approver</th>
        <th>Is CD for Financial Approver</th>
        <th>Is WCM/FI Approver</th>
        <th>Is Active</th>*/
    fetchAndbuildUserRoleList = function () {
        USER_INFO = [];
        $('#userrole-table')
            .DataTable(
                {
                    "autoWidth": true,
                    "ordering": true,
                    "paging": true,
                    "info": true,
                    "bDestroy": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/USER/id,title,firstName,userEmail,plantName,pillarName,pillarLead,editor,roles,roles,roles,active,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        {
                            "data": "title"
                        },
                        {
                            "data": "firstName",
                            "orderable": false,
                            "render": function (data, type, full,
                                                meta) {
                                USER_INFO.push(full);

                                return full.firstName + " " + full.lastName;
                            }
                        },
                        {
                            "data": "userEmail"
                        },
                        {
                            "data": "plantName"
                        },
                        {
                            "data": "pillarName"
                        },
                        {
                            "data": "pillarLead"
                        },
                        {
                            "data": "editor"
                        },
                        {
                            "data": "roles",
                            "orderable": false,
                            "render": function (data, type, full,
                                                meta) {
                                //if(data.conta)
                                return data.includes("APPROVER1");
                            }

                        },
                        {
                            "data": "roles",
                            "orderable": false,
                            "render": function (data, type, full,
                                                meta) {
                                //if(data.conta)
                                return data.includes("APPROVER2");
                            }

                        },
                        {
                            "data": "roles",
                            "orderable": false,
                            "render": function (data, type, full,
                                                meta) {
                                //if(data.conta)
                                return data.includes("APPROVER3");
                            }

                        },
                        {
                            "data": "active"
                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, full,
                                                meta) {
                                var htmlcontent = '<div class="user-id-edit" user-id=' + data + ' onClick=editUser('+data+')><i class="fa fa-edit " ></i></div>';
                                return htmlcontent;
                            }
                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, full,
                                                meta) {
                                var htmlcontent = '<div class="user-id-delete" user-id=' + data + ' onClick=deleteUser('+data+')><i class="fa fa-trash " ></i></div>';
                                return htmlcontent;
                            }
                        }



                        /*,
                        {
                            "data" : "id",
                            "class" : "action-col",
                            'orderable' : false,
                            "render" : function(data, type, row,
                                    meta) {
                                var htmlcontent = '<a class=""><i class="fa fa-pencil editUserRoleType-btn" id="'
                                        + data
                                        + '"></i></a><span></span><a class=""><i id="'
                                        + data
                                        + '" class="fa fa-trash deleteUserRoleType-btn"></i></a>';
                                return htmlcontent;
                            }
                        } */],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#userrole-modal').modal('show');
                                $('#userrole-modal').attr(
                                    'data-id', "");
                                $('#userrole-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#userrole-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#userrole-modal').find('.error')
                                    .removeClass('error');
                                $('#reqRoleSaveBtn').attr("data-uniqueId", "");
                                $('#userrole-modal').find('h5').html('Add User');
                            }
                        },
                        /*{
                            className : 'btn-default btn-sm ',
                            text : '<i class="fa fa-trash" aria-hidden="true"></i> Delete',
                            action : function(e, dt, node, config) {
                                ///alert( 'Button activated' );
                            }
                        }  ,*/{
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#usermasterbulk-modal')
                                    .modal('show');

                                $('#usermasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("USER", "");

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildPlantList = function () {
        $('#plantable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/PLANT/id,code,name,location,region,createdBy,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [

                        {
                            "data": "code"
                        },
                        {
                            "data": "name"
                        },
                        {
                            "data": "location"
                        },
                        {
                            "data": "region"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i class="fa fa-pencil editPlant-btn" id="'
                                    + data
                                    + '"></i></a><span></span><a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deletePlant-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#plant-modal').modal('show');
                                $('#plant-modal').attr('data-id',
                                    "");
                                $('#plant-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#plant-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#plant-modal').find('.error')
                                    .removeClass('error');
                                $('#plantSaveBtn').attr("data-uniqueId", "");
                                $('#plant-modal').find('h5').html('Add Loss type');
                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("PLANT", "");

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildPillarList = function () {
        $('#pillartable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/PILLAR/id,name,pillarType,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        {
                            "data": "name"
                        },
                        {
                            "data": "pillarType"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i class="fa fa-pencil editPillar-btn" id="'
                                    + data
                                    + '"></i></a><span></span><a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deletePillar-btn"></i></a>';
                                changeName($("#viewPDCAtable").find("td"), true, "");
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#pillar-modal').modal('show');
                                $('#pillar-modal').attr('data-id',
                                    "");
                                $('#pillar-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#pillar-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#pillar-modal').find('.error').removeClass('error');
                                $('#pillarSaveBtn').attr("data-uniqueId", "");
                                $('#pillar-modal').find('h5').html('Add Pillar');
                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("PILLAR", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#pillarmasterbulk-modal')
                                    .modal('show');


                                $('#pillarmasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildlosstypeList = function () {
        $('#losstypetable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/LOSSTYPE/id,value,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        /*{
                            "data" : "id",
                            'orderable' : false,
                            "render" : function(data, type, row,
                                    meta) {
                                console.log("Inside create chekbo");
                                var htmlcontent = '<fieldset class="form-group"> <input id="'
                                        + data
                                        + '" type="checkbox" class="filled-in selectedcheckbox"><label for=""></label></fieldset>';
                                return htmlcontent;
                            }
                        },*/
                        {
                            "data": "value"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteLosstype-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#losstype-modal').modal('show');
                                $('#losstype-modal').attr('data-id',
                                    "");
                                $('#losstype-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#losstype-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#losstype-modal').find('.error')
                                    .removeClass('error');
                                $('#losstypeSaveBtn').attr("data-uniqueId", "");
                                $('#losstype-modal').find('h5').html('Add Loss type');
                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("LOSSTYPE", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#losstypemasterbulk-modal')
                                    .modal('show');

                                $('#losstypemasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildToolList = function () {
        $('#tooltable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/TOOL/id,value,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        /*{
                            "data" : "id",
                            'orderable' : false,
                            "render" : function(data, type, row,
                                    meta) {
                                console.log("Inside create chekbo");
                                var htmlcontent = '<fieldset class="form-group"> <input id="'
                                        + data
                                        + '" type="checkbox" class="filled-in selectedcheckbox"><label for=""></label></fieldset>';
                                return htmlcontent;
                            }
                        },*/
                        {
                            "data": "value"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteTool-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#tool-modal').modal('show');
                                $('#tool-modal').attr('data-id',
                                    "");
                                $('#tool-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#tool-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#tool-modal').find('.error')
                                    .removeClass('error');
                                $('#toolSaveBtn').attr("data-uniqueId", "");
                                $('#tool-modal').find('h5').html('Add Tool');

                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("TOOL", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#toolmasterbulk-modal')
                                    .modal('show');

                                $('#toolmasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildBenefitList = function () {
        console.log("benefittable");
        $('#benefittable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/BENEFIT/id,value,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        /*{
                            "data" : "id",
                            'orderable' : false,
                            "render" : function(data, type, row,
                                    meta) {
                                console.log("Inside create chekbo");
                                var htmlcontent = '<fieldset class="form-group"> <input id="'
                                        + data
                                        + '" type="checkbox" class="filled-in selectedcheckbox"><label for=""></label></fieldset>';
                                return htmlcontent;
                            }
                        },*/
                        {
                            "data": "value"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteBenefit-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#benefit-modal').modal('show');
                                $('#benefit-modal').attr('data-id',
                                    "");
                                $('#benefit-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#benefit-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#benefit-modal').find('.error')
                                    .removeClass('error');
                                $('#benefitSaveBtn').attr("data-uniqueId", "");
                                $('#benefit-modal').find('h5').html('Add Benefit');

                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("BENEFIT", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#benefitmasterbulk-modal')
                                    .modal('show');

                                $('#benefitmasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildAreaList = function () {
        $('#areatable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/AREA/id,value,plant,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [

                        {
                            "data": "value"
                        },
                        {
                            "data": "plant"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteArea-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#area-modal').modal('show');
                                $('#area-modal').attr('data-id',
                                    "");
                                $('#area-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#area-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#area-modal').find('.error')
                                    .removeClass('error');
                                $('#areaSaveBtn').attr("data-uniqueId", "");
                                $('#area-modal').find('h5').html('Add Loss type');

                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("AREA", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#areamasterbulk-modal')
                                    .modal('show');

                                $('#areamasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }
    fetchAndbuildLineList = function () {
        $('#linetable')
            .DataTable(
                {
                    "ordering": true,
                    "bDestroy": true,
                    "paging": true,
                    "info": true,
                    "language": {
                        "infoFiltered": "",
                    },
                    "serverSide": true,
                    "aaSorting": [[1, "asc"]],
                    "ajax": "/listadmindetails/LINE/id,value,plant,createdBy,createdOn,actions/active/true",
                    dom: 'Bfrtip',
                    "columns": [
                        /*{
                            "data" : "id",
                            'orderable' : false,
                            "render" : function(data, type, row,
                                    meta) {
                                var htmlcontent = '<fieldset class="form-group"> <input id="'
                                        + data
                                        + '" type="checkbox" class="filled-in selectedcheckbox"><label for=""></label></fieldset>';
                                return htmlcontent;
                            }
                        },*/
                        {
                            "data": "value"
                        },
                        {
                            "data": "plant"
                        },
                        {
                            "data": "createdBy"
                        },
                        {
                            "data": "createdOn",
                            "render": function (data, type, row, meta) {
                                return new Date(data).toLocaleDateString('en-GB', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                }).split(' ').join('-');
                            }

                        },
                        {
                            "data": "id",
                            "class": "action-col",
                            'orderable': false,
                            "render": function (data, type, row,
                                                meta) {
                                var htmlcontent = '<a class=""><i id="'
                                    + data
                                    + '" class="fa fa-trash deleteLine-btn"></i></a>';
                                return htmlcontent;
                            }
                        }],
                    buttons: [
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Add',
                            action: function (e, dt, node, config) {
                                $('#line-modal').modal('show');
                                $('#line-modal').attr('data-id',
                                    "");
                                $('#line-modal')
                                    .find(
                                        'input:not([type="radio"],[type="checkbox"]),select')
                                    .val('');
                                $('#line-modal')
                                    .find(
                                        'input[type="radio"],input[type="checkbox"]')
                                    .prop('checked', false);
                                $('#line-modal').find('.error')
                                    .removeClass('error');
                                //lineSaveBtn
                                $('#lineSaveBtn').attr("data-uniqueId", "");
                                $('#line-modal').find('h5').html('Add Line');
                            }
                        },
                        {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-download "  aria-hidden="true"></i> Export',
                            action: function (e, dt, node, config) {
                                generateExport("LINE", "");

                            }
                        }, {
                            className: 'btn-default btn-sm',
                            text: '<i class="fa fa-plus " aria-hidden="true"></i> Bulk Add',
                            action: function (e, dt, node, config) {
                                $('#linemasterbulk-modal')
                                    .modal('show');

                                $('#linemasterbulk-modal')
                                    .find('.error')
                                    .removeClass('error');

                            }
                        }], "sScrollX": '100%'
                });
        $('div.dt-buttons').children().removeClass('dt-button');
        $('.dt-buttons .btn').removeClass('btn-secondary');
    }


    /*fetchAndbuildWorkPermitFromFirestore	=	function(){
        loader.show();
                var triggerNextQuery = true;
                 fetchWorkPermitList(queryList[index].limit(queryLimit),function(response){
                     queryLimit = 10;
                    resultFromFirestore= response;
                    if(response!=null && response.length>0){
                        if(response.length==queryLimit){
                            triggerNextQuery=false;
                            window.lastDoc = response[response.length-1];
                        }
                        else{
                            queryLimit=(10-response.length);
                        }
                        $.each(response,function(index,doc){
                            workPermitList.push(doc.data());
                        });
                    }
                    if(triggerNextQuery){
                        index++;
                        fetchWorkPermitList();
                    }
                    buildWorkPermitFromFirestore(workPermitList);
                 });

    }*/


    bindFetchDetailsOnHighLight = function (tableId, callback) {
        $('#' + tableId + " tbody")
            .on(
                'click',
                'tr',
                function (event) {
                    console.log("clicked");
                    var data = $('#' + tableId).DataTable()
                        .row($(this)).data();
                    var rowindex = $('#' + tableId).DataTable().row(
                        $(this)).index();
                    $("#" + tableId + "_wrapper tr").removeClass(
                        "highlight-row");
                    var trIndex = rowindex + 1;
                    $("#" + tableId + "_wrapper table.dataTable").each(
                        function (index) {
                            $(this).find("tr:eq(" + trIndex + ")")
                                .addClass("highlight-row");
                        });
                    setFieldValues(data);
                    Do.validateAndDoCallback(callback);
                });
    }

})();