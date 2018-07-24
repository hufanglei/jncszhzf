/**
 * 综合执法工作考核
 */
// 综合执法工作考核列表查询
var issue_page_url = "/jxkh/zhzfgzkhPage.action";
var issue_url = "/jxkh/zhzfPage.action";
var issue_jdpage_url="/jxkh/zhzfJdPage.action";
var showZxclDetailsUrl = "/jxkh/selectZxclDetails.action";
var showJsgsUrl = "/jxkh/selectJsgs.action";
var refreshZxclDetailsUrl="/jxkh/refreshZxclDetails.action";
var showdcdbDetailsUrl="/jxkh/showdcdbDetails.action";
var showdcdbJsgssUrl="/jxkh/selectDcdbJsgs.action";
//显示宣传报道列表页
var showXcbdDetailsUrl = "/jxkh/showXcbdDetails.action";
var refreshDcdbDetailsUrl="/jxkh/refreshDcdbDetails.action";
var query_issue_by_processId = '/issues/getIssueByProcessId.action';
// 根据taskId获得点击的批注详情
var commentDetailUrl = '/task/listHistoryCommentDetailByProcessId.action?processId=';
//根据processid 查询 附件
var attachMentUrl = '/gdgx/selecAttachList.action?processId=';
//获取上报人历史批注
var ptDepartment = '/task/getDepartment.action?issueNumber=';
var dbquery_dbissue_by_processId = "/jxkh/getDbIssueByProcess.action";
var dbcommentDetailUrl = "/task/listHistorydbDetailByProcessId.action?processId=";
var dbdepartment = "/task/getDbDepartment.action?taskId=";
var dbattachMentUrl = "/jxkh/selecAttachList.action?processId=";

// 表格是否全网格案件数据
var isQWGing = false;

var columns = [ {
    title : '考核id',
    field : 'id',
    align : 'center',
    valign : 'middle',
    visible : false
}, {
    title: '序号',//标题  可不加
    align : 'center',
    formatter: function (value, row, index) {
        return index+1;
    }
}, {
    title : '机构编码',
    field : 'qhzdId',
    visible : false,
    align : 'center',
    valign : 'middle',
    formatter : function(value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '机构名称',
    field : 'qhzdName',
    align : 'center',
    valign : 'middle'
}, {
    title : '按时处理(150分)',
    field : 'ascl',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }

}, {
    title : '快速处理(150分)',
    field : 'kscl',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '反复投诉(50分)',
    field : 'ffts',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '完整性、规范性(150分)',
    field : 'wzxgfx',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '督办问责(50分)',
    field : 'dbwz',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '工作协调(50分)',
    field : 'gzxt',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '监督检查(40分)',
    field : 'jdjc',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title : '宣传报道(10分)',
    field : 'xcbd',
    align : 'center',
    valign : 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }

}, {
    title : '总分(650分)',
    field : 'sumNum',
    align : 'center',
    valign : 'middle',

},{
    title : '考核月份',
    field : 'khyf',
    align : 'center',
    valign : 'middle',
    visible:false
}, {
    title : '考核时间',
    field : 'khsj',
    align : 'center',
    valign : 'middle',
    visible:false
} ];
var column_ = [ {
    title: '序号',//标题  可不加
    align: 'center',
    formatter: function (value, row, index) {
        return index + 1;
    }
}, {
    title: '案件编号',
    field: 'issueNumber',
    align: 'center',
    valign: 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title: '案件主题',
    field: 'issueSubject',
    align: 'center',
    valign: 'middle'
}, {
    title: '创建时间',
    field: 'addTime',
    align: 'center',
    valign: 'middle'

}, {
    title: '结束日期',
    field: 'endTime',
    align: 'center',
    valign: 'middle'
}, {
    title: '时间限制',
    field: 'timeLimit',
    align: 'center',
    valign: 'middle'
}, {
    title: '办理单位',
    field: 'isOpen',
    align: 'center',
    valign: 'middle'
}];
var column_dcdb = [ {
    title: '序号',//标题  可不加
    align: 'center',
    formatter: function (value, row, index) {
        return index + 1;
    }
}, {
    title: '督办案件编号',
    field: 'dcdbnum',
    align: 'center',
    valign: 'middle',
    formatter: function (value, row, index) {
        return '<span class="link-col">' + value + '</span>'
    }
}, {
    title: '案件编号',
    field: 'issuenum',
    align: 'center',
    valign: 'middle'
}, {
    title: '案件来源',
    field: 'sourceId',
    align: 'center',
    valign: 'middle'

}, {
    title: '分数',
    field: 'score',
    align: 'center',
    valign: 'middle'
}, {
    title: '办理单位',
    field: 'addUserid',
    align: 'center',
    valign: 'middle'
}];
var boot_table_id = "boot-table-dbrw";
var boot_table_panel_id = "boot-table-ptaj-panel";
var boot_table_panel_dbaj = "boot-table-dbaj-panel";
// 更新全网格标签数字
function updateQwgCounts(counts){
    $("#tag-li-全网格 > i").html(counts);
}
var rows='';
var page='';
var num=1;
var params;
function initDataTable() {
    var num=1;
    var date = new Date();
    var month = date.getMonth()+1;
    month = month<10 ? ('0'+month) : month;
    var beginDate = (date.getFullYear()+"-"+ month);
    $("#khsj").val(beginDate)
    var $table = $("#" + boot_table_id);
    $table.bootstrapTable({
        height : ($('.content').height() - $('.funShow').height()),
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination : false,
        striped : true, // 是否显示行间隔色
        columns : columns,
        method : 'post',
        contentType : "application/x-www-form-urlencoded",
        dataType : 'json',
        url : url_prefix + issue_page_url,
        queryParams : function(params){
            return {
                rows: params.limit, //页面大小
                page: params.offset/params.limit + 1, //页码,
                khyf:$('#khsj').val(),
                qhzdName:$('#qhzdName').val(),
                bmType:'5'
            }
        },
        responseHandler:function(res){
            console.log(res)
            var objs = JSON.parse(res);
            $.each(objs.rows, function (idx, item) {
                nums =parseFloat(item.ascl)
                    +parseFloat(item.kscl)+parseFloat(item.ffts)+parseFloat(item.wzxgfx)+parseFloat(item.dbwz)+parseFloat(item.gzxt)+parseFloat(item.xcbd)+parseFloat(item.jdjc);
                item.sumNum=nums.toFixed(2);
            })
            return objs;
        }
    });

    $table.on("click-cell.bs.table", function (event, column_name, value, row) {
           if (column_name == "ascl") {
                showZxclDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "kscl") {
                showZxclDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "wzxgfx") {
                showZxclDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "ffts") {
                showDcdbDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "dbwz") {
                showDcdbDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "xcbd") {
                showXcbdDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "jdjc") {
                showDcdbDetails(row.khyf,row.qhzdId, column_name,num);
            } else if (column_name == "gzxt") {
                showDcdbDetails(row.khyf,row.qhzdId, column_name,num);
            }

        }
    );

    $table.on("load-success.bs.table", function(event, data) {
        if (isQWGing){
            var options = $("#" + boot_table_id).bootstrapTable('getOptions');
            updateQwgCounts(options.totalRows);
        }
    });
};

/*
function refreshDataTableByParams() {
    isQWGing = false;
    $("#" + boot_table_id).bootstrapTable('refreshOptions', {
        columns : columns,
        url : url_prefix + issue_page_url,
        queryParams : function(params){
            return {
                rows: params.limit, //页面大小
                page: params.offset/params.limit + 1, //页码,
                khyf:$('#khyf').val(),
                qhzdName:$('#qhzdName').val(),
                bmType:'5'
            }
        }
    });
};
*/

function refreshDataTableByParams(num) {
    isQWGing = false;
    var khyf = $('#khyf').val();
    var ykhyf= $("#khsj").val();
    if (khyf == null || khyf == ""&&ykhyf == null || ykhyf == "") {
        layerAlert("请选择考核时间！")
        return false;
    }

    if (num==1){
        //年度统计
        //月度统计
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 1;
        var queryAarr = khyf.split('-');
        if (thisYear < queryAarr[0]){
            layerAlert('请选择当前年份之前的年份！');
            return false;
        }
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns : columns,
            url : url_prefix + issue_url,
            queryParams : function(params){
                return {
                    rows: params.limit, //页面大小
                    page: params.offset/params.limit + 1, //页码,
                    khyf:$('#khyf').val(),
                    bmType:'5'
                }
            }
        });
    }else if (num==2){
        //季度统计
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 2;
        var queryAarr = khyf.split('-');
        var mouth =$("#jdselect input[name='jdfresh']:checked").val();


        if(mouth==1){
            if (thisMonth<3){
                layerAlert("请选择当前月份之前的季度");
                return false;
            }
        }else if (mouth==2){
            if (thisMonth<6) {
                layerAlert("请选择当前月份之前的季度");
                return false;
            }
        }else if (mouth==3){
            if (thisMonth<9) {
                layerAlert("请选择当前月份之前的季度");
                return false;
            }
        }else if (mouth==4){
            if (thisMonth<12) {
                layerAlert("请选择当前月份之前的季度");
                return false;
            }
        }


        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns : columns,
            url : url_prefix + issue_jdpage_url,
            queryParams : function(params){
                return {
                    rows: params.limit, //页面大小
                    page: params.offset/params.limit + 1, //页码,
                    khyf:$('#khyf').val(),
                    jdfreshNum:$("#jdselect input[name='jdfresh']:checked").val(),
                    bmType:'5'
                }
            }
        });
    }else if(num==3){
        //月度统计
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 2;
        var queryAarr = ykhyf.split('-');

        if (thisYear < queryAarr[0] || (thisYear == queryAarr[0] && thisMonth <= queryAarr[1])) {
            layerAlert('请选择当前月份之前的月份！');
            return false;
        }
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns : columns,
            url : url_prefix + issue_page_url,
            queryParams : function(params){
                return {
                    rows: params.limit, //页面大小
                    page: params.offset/params.limit + 1, //页码,
                    khyf:$('#khsj').val(),
                    bmType:'5'
                }
            }
        });

    }

};
//统计方式:年度统计 季度统计 月度统计
function refreshYear(number) {
    if (number==1){
        $('#yearCount').show();
        $('#quarterCount').hide();
        $('#mouthCount').hide();
    }else if(number==2){
        $('#quarterCount').show();
        $('#yearCount').hide();
        $('#mouthCount').hide();
    }else if(number==3){
        $('#mouthCount').show();
        $('#yearCount').hide();
        $('#quarterCount').hide();
    }

};
var columName;
var jgmc;
var khyf;

/**
 * 显示案件列表详情
 * @param row_
 * @param column_name_
 */
function showZxclDetails(khyf_, qhzdId_,column_name_,num_) {
    khyf = khyf_;
    columName = column_name_;
    jgmc = qhzdId_;
    num =num_;
    var str = "";
    var str2 = "";
    $('#zxclModal').modal('show');

    var $table = $("#" + boot_table_panel_id);
    $table.bootstrapTable({
        height:'300px',//
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination: true,
        striped: true, // 是否显示行间隔色
        columns: column_, //
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        dataType: 'json',
        url: url_prefix + showZxclDetailsUrl,//
        responseHandler: function (res) {
            return (typeof res == "string") ? $.parseJSON(res) : res;
        },
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                khyf: khyf,
                columName: columName,
                jgmc: jgmc,
                num:num
            }
        }
    });
    $table.on("click-cell.bs.table", function (event, column_name, value, row) {
        showPage(row.issueNumber,row.processId);
    });

    /* $.ajax({
         method: 'get',
         url: url_prefix + showZxclDetailsUrl,
         dataType: 'json',
         data: {
             khyf: khyf,
             columName: columName,
             jgmc: jgmc
         },
         success: function (data) {
             console.log(data)
             myapp.ajkhDetail = data.rows;
             /!*   console.log(data.rows.isOpen)*!/
             $('#zxclModal').modal('show');
         }
     });*/
    $.ajax({
        method: 'get',
        url: url_prefix + showJsgsUrl,
        dataType: 'json',
        data: {
            khyf: khyf,
            columName: columName,
            jgmc: jgmc
        },
        success: function (data) {
            console.log(data)
            myapp.jsgsDetail = data.rows;
        }
    }).then(function (data) {
        var num1 = 0;
        var num2 = 0;
        var re = 0;

        if (columName == 'zxcl') {
            if (myapp.jsgsDetail.length != 0) {
                num1 = myapp.jsgsDetail[0].isOpen;
                num2 = myapp.jsgsDetail[0].whoReported;
                re = myapp.jsgsDetail[0].isArchived;
            }
            $("#kpdf").empty();
            str += "（自行处理案件数量/总案件数量）×150分，总案件数量=自行处理案件数量+上报且被部门受理并立案案件数量。</th>"
            str2 += "(" + num1 + "/" + num2 + ")" + "*150" + "=" + re + "</th>"
        } /*else if (columName == "ajbz") {
            $("#kpdf").empty();
            str += "A=13个街道园区的当月自处置案件总量    B=用户输入的12345网站去年一年13个街道园区的总自处置工单量 / 12  " +
                "统一系数P=1-（A-B）/A N=单个街道园区的当月自处置案件量     M=B/13   “案件比重”分值=（N/M）*P</th>"
            str2 += ""

        }*/ else if (columName == "ascl") {
            if (myapp.jsgsDetail.length != 0) {
                num1 = myapp.jsgsDetail[0].isOpen;
                num2 = myapp.jsgsDetail[0].whoReported;
                re = myapp.jsgsDetail[0].isArchived;
            }
            $("#kpdf").empty();
            str += "街道（园区）案件处理时限为2个工作日，按时办结率=（按时办结案件数量/办结案件数量）×150分；</th>"
            str2 += "(" + num1 + "/" + num2 + ")" + "*150" + "=" + re + "</th>"

        } else if (columName == "kscl") {
            $("#kpdf").empty();
            str += "街道（园区）：提前办结率=（1-案件处理实际耗时／案件处理规定时间）×150分；</th>"
            str2 += "</th>"

        } else if (columName == "wzxgfx") {
            if (myapp.jsgsDetail.length != 0) {
                num1 = myapp.jsgsDetail[0].isOpen;
                num2 = myapp.jsgsDetail[0].whoReported;
                re = myapp.jsgsDetail[0].isArchived;
            }
            $("#kpdf").empty();
            str += "街道（园区）：上传案件处理前后照片，需启动简易执法程序的上传执法文书，得分=（要素全案件数量／自处理案件数量）×150分；\n" +
                "部门：上传现场查看照片及立案所需文书、相关材料，</th>"
            str2 += "(" + num1 + "/" + num2 + ")" + "*150" + "=" + re + "</th>"
        }
        $("#khgz").empty();
        $("#khgz").prepend(str);
        $("#kpdf").prepend(str2);
    })
    ;
};

/**
 * 督查督办案件列表显示
 * @param row_
 * @param column_name_
 */
function showDcdbDetails(khyf_, qhzdId_,column_name_,num_) {
    khyf = khyf_;
    columName = column_name_;
    jgmc = qhzdId_;
    var str = "";
    var str2 = "";
    var num=1;

    var $table = $("#" + boot_table_panel_dbaj);
    $("#dbajModal").bootstrapTable('refresh',$table );
    $('#dbajModal').modal('show');

    $table.bootstrapTable({
        height:'300px',//
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination: true,
        striped: true, // 是否显示行间隔色
        columns: column_dcdb,
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        dataType: 'json',
        url: url_prefix + showdcdbDetailsUrl,
        responseHandler: function (res) {
            return (typeof res == "string") ? $.parseJSON(res) : res;
        },
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                khyf: khyf,
                columName: columName,
                jgmc: jgmc,
                num:num
            }
        }
    });
    $table.on("click-cell.bs.table", function (event, column_name, value, row) {
        showDbPage(row.issueNumber,row.processId);
    });


    $.ajax({
        method: 'get',
        url: url_prefix + showdcdbJsgssUrl,
        dataType: 'json',
        data: {
            khyf: khyf,
            columName: columName,
            jgmc: jgmc
        },
        success: function (data) {
            console.log(data)
            myapp.jsgsDcdb = data.rows;
        }
    }).then(function (data) {
        var num1 = 0;
        var num2 = 0;
        var re = 50;
        if (myapp.jsgsDcdb.length != 0) {
            num1 = myapp.jsgsDcdb[0].score;
            /*num2 = myapp.jsgsDetail[0].whoReported;
            re = (num1 / num2) * 150;*/
            re=50-num1
        }
        if (columName == "ffts") {
            $("#dckpdf").empty();
            str += "因处置不妥当，不彻底，被12345热线、媒体等投诉、举报、曝光的，每发生一起扣2分</th>"
            str2 += 50 + "-" + num1  + "=" + re + "</th>"

        } else if (columName == "dbwz") {
            $("#dckpdf").empty();
            str += "因处置不妥当，不彻底或者不作为、慢作为，被区督查办下发《督查通知单》并要求限期整改仍不整改的，普通《督查通知单》每发生一起扣2分；《黄牌警告通知书》，每发生一起扣5分；《红牌警告通知书》，每发生一起扣10分。</th>"
            str2 += 50 + "-" + num1  + "=" + re + "</th>"

        } else if (columName == "xcjz") {
            $("#dckpdf").empty();
            str += "13个部门对本部门职能范围内案件处理情况进行动态巡查，发现街道（园区）应自处理而未处理案件或需上报立案而未上报案件，提供相关材料依据，按月报区综合行政执法平台，经考核小组核实确认后，每发生一起扣2分。\n</th>"
            str2 += 50 + "-" + num1  + "=" + re + "</th>"

        }else if (columName == "jdjc") {
            $("#dckpdf").empty();
            str += "法制办每月对案件处理情况进行抽查，发现弄虚作假，经核实后上报考核小组，每发生一起视情节严重扣2-10分。</th>"
            str2 += 50 + "-" + num1  + "=" + re + "</th>"

        } else if (columName == "gzxt") {
            $("#dckpdf").empty();
            str += "执法工作时加强沟通协调，杜绝推诿扯皮现象。因工作协调、沟通不到位、\n" +
                "推诿扯皮造成工作失误的，或完成上级、平台交办工作不及时的，每发生一起扣2分,造成严重后果的，每发生一起扣10分。</th>"
            str2 += 50 + "-" + num1  + "=" + re + "</th>"

        }
        $("#dckhgz").empty();
        $("#dckhgz").prepend(str);
        $("#dckpdf").prepend(str2);
    })
    ;
};

/**
 * 宣传报道列表页面显示
 * @param number
 */
function showXcbdDetails(row_, column_name_) {
    khyf = row_.khyf;
    columName = column_name_;
    jgmc = row_.qhzdId;
    var str = "国家级报刊每篇5分，省级每篇3分、市级每篇2分、区级每篇1分；网络媒体\n" +
        "分值减半。每月得分10分封顶。</th>"
    var str2 = "";

    $.ajax({
        method: 'get',
        url: url_prefix + showXcbdDetailsUrl,
        dataType: 'json',
        data: {
            khyf: khyf,
            columName: columName,
            jgmc: jgmc
        },
        success: function (data) {
            console.log(data)
            myapp5.xcbdDetail = data.rows;
            $('#xcbdModal').modal('show');
        }
    }).then(function (data) {
        var score=0;
        if ( myapp5.xcbdDetail.length != 0) {
         score=myapp5.xcbdDetail[0].score;
        }
        if (columName == "xcbd") {
            $("#xcbddf").empty();
            str += "国家级报刊每篇5分，省级每篇3分、市级每篇2分、区级每篇1分；网络媒体\n" +
                "分值减半。每月得分10分封顶。</th>"
            str2 += score + "</th>"

        }
        $("#xcbdgz").empty();
        $("#xcbdgz").prepend(str);
        $("#xcbddf").prepend(str2);
    })
    ;
};
function refreshKfx(number) {
    var num=number;
    refreshZxclDetails( khyf,jgmc, columName, num)
}

/**
 * 普通案件 全部 正常项   和扣分项数据
 * @param jgmc_
 * @param khyf_
 * @param column_name_
 * @param num_
 */
function refreshZxclDetails(khyf_,jgmc_,  column_name_, num_) {
    var khyf = khyf_;
    var jgmc = jgmc_;
    var columName = column_name_;
    var num = num_
    $("#" + boot_table_panel_id).bootstrapTable('refreshOptions', {
        columns: column_,
        url: url_prefix + showZxclDetailsUrl,
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                khyf: khyf,
                columName: columName,
                jgmc: jgmc,
                num:num
            }
        }
    });

}
function showPage (issueNumber_, processId_){
    var processId = processId_;
    var issueNumber = issueNumber_;
    $.ajax({
        method: 'post',
        url: url_prefix + query_issue_by_processId,
        dataType: 'json',
        data: {
            processId: processId
        },
        success: function (data) {
            // 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
            /* data = (typeof data == "object") ? data : $.parseJSON(data);*/
            myapp3.ajDetail = data.tMainIssueBean;
            $("#newModal").modal('show');
        }
    });
    $.ajax({
        method: 'get',
        url: url_prefix + commentDetailUrl + processId,
        dataType: 'json',
        success: function (data) {
            //初始化提示框
            data = (typeof data == "object") ? data : $.parseJSON(data)
            myapp3.commentDetail = data.rows;
            /*  $('[data-toggle="tooltip"]').tooltip("destroy");
              $('[data-toggle="tooltip"]').tooltip();*/
        }
    });
    $.ajax({
        method: 'get',
        url: url_prefix + ptDepartment + issueNumber,
        dataType: 'json',
        success: function (data) {
            //初始化提示框
            data = (typeof data == "object") ? data : $.parseJSON(data)
            myapp3.ptDepartment = data.rows;
            $('[data-toggle="tooltip"]').tooltip("destroy");
            $('[data-toggle="tooltip"]').tooltip();
        }
    });
    $.ajax({
        method: 'get',
        url: url_prefix + attachMentUrl + processId,
        dataType: 'json',
        success: function (data) {

            console.log(data);
            if (data && data.length > 0) {
                $(".attachment-list").empty();
                $.each(data, (i, o) => {
                    var $tr = $('<tr></tr>');
                    var $td = $('<td class="col-sm-3">' + o.USERNAME_ + '</td>');
                    var typeName = '';
                    switch (o.type) {
                        case 1:
                            typeName = '处理前';
                            break;
                        case 2:
                            typeName = '处理中';
                            break;
                        case 3:
                            typeName = '评价';
                            break;
                        case 4:
                            typeName = '立案';
                            break;
                        case 5:
                            typeName = '处理后';
                            break;
                        default:
                            break;
                    }
                    var $td2 = $('<td class="col-sm-3">' + typeName + '</td>');
                    var $td3 = $('<td class="col-sm-3 link-col" ">' + o.name + '</td>');
                    $td3.on('click', (e) => {
                        showAttachment(o.save_path);
                    });
                    $tr.append($td).append($td2).append($td3);
                    $tr.appendTo($('.attachment-list'));

                });
            }
        }
    });

}
function showDbPage(dcdbnum_, processId_){

    /**
     * 督办案件的案件详情
     */
    var dcdbnum = dcdbnum_;
    var Db_processId = processId_;
    $.ajax({
        method: 'post',
        url: url_prefix + dbquery_dbissue_by_processId,
        dataType: 'json',
        data: {
            processId: Db_processId
        },
        success: function (data) {
            console.log(data)
            // 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
            data = (typeof data == "object") ? data : $.parseJSON(data);
            myapp4.dbajDetail = data.tissueDcdbKhBean;
            $("#dbModal").modal('show');

        }
    });

    $.ajax({
        method: 'get',
        url: url_prefix + dbcommentDetailUrl + Db_processId,
        dataType: 'json',
        success: function (data) {
            //初始化提示框
            data = (typeof data == "object") ? data : $.parseJSON(data)
            myapp4.commentDetail = data.rows;
            $('[data-toggle="tooltip"]').tooltip("destroy");
            $('[data-toggle="tooltip"]').tooltip();
        }
    });
    //案件上报时的流程
    $.ajax({
        method: 'get',
        url: url_prefix + dbdepartment + Db_processId,
        dataType: 'json',
        success: function (data) {
            data = (typeof data == "object") ? data : $.parseJSON(data)
            myapp4.department = data.rows;
            //初始化提示框
            $('[data-toggle="tooltip"]').tooltip();
        }
    });

    $.ajax({
        method: 'get',
        url: url_prefix + dbattachMentUrl + Db_processId,
        dataType: 'json',
        success: function (data) {

            console.log(data);
            if (data && data.length > 0) {
                $(".attachment-list").empty();
                $.each(data, (i, o) => {
                    var $tr = $('<tr></tr>');
                    var $td = $('<td class="col-sm-3">' + o.USERNAME_ + '</td>');
                    var typeName = '';
                    switch (o.type) {
                        case 1:
                            typeName = '处理前';
                            break;
                        case 2:
                            typeName = '处理中';
                            break;
                        case 3:
                            typeName = '评价';
                            break;
                        case 4:
                            typeName = '立案';
                            break;
                        case 5:
                            typeName = '处理后';
                            break;
                        default:
                            break;
                    }
                    var $td2 = $('<td class="col-sm-3">' + typeName + '</td>');
                    var $td3 = $('<td class="col-sm-3 link-col" ">' + o.name + '</td>');
                    $td3.on('click', (e) => {
                        showAttachment(o.save_path);
                    });
                    $tr.append($td).append($td2).append($td3);
                    $tr.appendTo($('.attachment-list'));

                });
            }
        }
    });
}

function refreshDcdb(number) {
    var num=number;
    refreshDcdbDetails(jgmc,khyf, columName,num)
}
/**
 *督办案件    全部 正常项   和扣分项数据
 * @param jgmc_
 * @param khyf_
 * @param column_name_
 * @param num_
 */
function refreshDcdbDetails(jgmc_, khyf_, column_name_, num_) {
    var khyf = khyf_;
    var jgmc = jgmc_;
    var columName = column_name_;
    var num = num_
    $("#" + boot_table_panel_dbaj).bootstrapTable('refreshOptions', {
        columns: column_dcdb,
        url: url_prefix + showdcdbDetailsUrl,
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                khyf: khyf,
                columName: columName,
                jgmc: jgmc,
                num: num
            }
        }

    })
}
// 显示全网格案件处理页面
function showQwgDetails(row){
    myapp.qwgDetail = row;
    // myapp.uploadFileList = [];
    $('#myModal').modal('show');
}
/*
function tab_animate() {
    $(".tab-content>.tab-pane").addClass('fade');
    $(".tab-content>.tab-pane").eq(0).addClass('in');
    $(".tab-content .tab-pane .btn").attr("type","button");
}



var tianshu = parseInt($('.shuzi').val());
$('.biaoti input').click(function() {
    var flag = $(this).is(':checked');
    if (flag) {
        $('.jia').removeClass('jjfocus');
        $('.jian').removeClass('jjfocus');
        $('.jian').bind('click', function() {
            tianshu--;
            $('.shuzi').val(tianshu);
            return false
        });
        $('.jia').bind('click', function() {
            tianshu++;
            $('.shuzi').val(tianshu);
            return false;
        });
    } else {
        $('.jian').unbind('click');
        $('.jia').unbind('click');
        $('.jia').addClass('jjfocus');
        $('.jian').addClass('jjfocus');
    }
})

$('#bumen').click(function() {
    $('#tree').slideToggle(300);
});*/

// 初始化VUE
var myapp = new Vue({
    el : '.wrap',
    data : {
        uploadFileList: [],
        fileList:[],
        setting : {
            check : {
                enable : true,
                nocheckInherit : false
            },
            view : {
                dblClickExpand : true,
                showLine : true,
                selectedMulti : false
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPId : ""
                }
            },
            callback : {
                beforeClick : function(treeId, treeNode) {
                }
            }
        },
        zNodes : [],
        t : {},
        liucheng : [],
        loading:false,
        bianhao : {},
        ajDetail : [],
        qwgDetail : [],
        commentDetail:[],
        ajkhDetail: [],
        jsgsDetail: [],
        dcdbDetail: [],
        jsgsDcdb: [],
        speedOfProgress:[],
        timeTaken:0
    },
    mounted : function() {
        this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
            function(data) {
                this.liucheng = data.body;
            });
        this.$http.get(url_prefix + '/page/json/function.json').then(
            function(data) {
                this.functionSr = data.body;
            });
    },
    methods:{

    }

});
var myapp2 = new Vue({
    el: '.wrap2',
    data: {
        uploadFileList: [],
        fileList: [],
        setting: {
            check: {
                enable: true,
                nocheckInherit: false
            },
            view: {
                dblClickExpand: true,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                }
            }
        },
        zNodes: [],
        t: {},
        liucheng: [],
        loading: false,
        bianhao: {},
        ajDetail: [],
        qwgDetail: [],
        commentDetail: [],
        ajkhDetail: [],
        jsgsDetail: [],
        dcdbDetail: [],
        jsgsDcdb: [],
        speedOfProgress: [],
        timeTaken: 0
    },
    mounted: function () {
        this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
            function (data) {
                this.liucheng = data.body;
            });
        this.$http.get(url_prefix + '/page/json/function.json').then(
            function (data) {
                this.functionSr = data.body;
            });
    },
    methods: {
        /**
         * 督办案件的案件详情
         */
    }

});
var myapp3 = new Vue({
    el: '.wrap3',
    data: {
        uploadFileList: [],
        fileList: [],
        setting: {
            check: {
                enable: true,
                nocheckInherit: false
            },
            view: {
                dblClickExpand: true,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                }
            }
        },
        zNodes: [],
        t: {},
        liucheng: [],
        loading: false,
        bianhao: {},
        ajDetail: [],
        qwgDetail: [],
        commentDetail: [],
        ajkhDetail: [],
        jsgsDetail: [],
        dcdbDetail: [],
        jsgsDcdb: [],
        ptDepartment: [],
        speedOfProgress: [],
        timeTaken: 0
    },
    mounted: function () {
        this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
            function (data) {
                this.liucheng = data.body;
            });
        this.$http.get(url_prefix + '/page/json/function.json').then(
            function (data) {
                this.functionSr = data.body;
            });
    },
    methods: {}

});
var myapp4 = new Vue({
    el: '.wrap4',
    data: {
        uploadFileList: [],
        fileList: [],
        setting: {
            check: {
                enable: true,
                nocheckInherit: false
            },
            view: {
                dblClickExpand: true,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                }
            }
        },
        zNodes: [],
        t: {},
        liucheng: [],
        loading: false,
        bianhao: {},
        ajDetail: [],
        dbajDetail: [],
        qwgDetail: [],
        commentDetail: [],
        department: [],
        speedOfProgress: [],
        ptcommentDetail: [],
        ptDepartment: [],
        timeTaken: 0
    },
    mounted: function () {
        this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
            function (data) {
                this.liucheng = data.body;
            });
        this.$http.get(url_prefix + '/page/json/function.json').then(
            function (data) {
                this.functionSr = data.body;
            });
    },
    methods: {}
});
var myapp5 = new Vue({
    el: '.wrap5',
    data: {
        uploadFileList: [],
        fileList: [],
        setting: {
            check: {
                enable: true,
                nocheckInherit: false
            },
            view: {
                dblClickExpand: true,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                }
            }
        },
        zNodes: [],
        t: {},
        liucheng: [],
        loading: false,
        bianhao: {},
        ajDetail: [],
        dbajDetail: [],
        qwgDetail: [],
        commentDetail: [],
        department: [],
        speedOfProgress: [],
        ptcommentDetail: [],
        xcbdDetail: [],
        ptDepartment: [],
        timeTaken: 0
    },
    mounted: function () {
        this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
            function (data) {
                this.liucheng = data.body;
            });
        this.$http.get(url_prefix + '/page/json/function.json').then(
            function (data) {
                this.functionSr = data.body;
            });
    },
    methods: {}
});

$(function() {

    initDataTable();
    initDialog();

    $('#yearCount').hide();
    $('#quarterCount').hide();
    $('#mouthCount').show();
    $('#myModal.close').click(function(){
        $('#myModal').modal('hide');
    });
    $('#zxclModal').on('hidden.bs.modal', function () {
        window.location.href = window.location.href;
    });
    $('#dbajModal').on('hidden.bs.modal', function () {
        window.location.href = window.location.href;
    });
});