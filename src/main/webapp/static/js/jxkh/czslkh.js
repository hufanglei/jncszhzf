var issueRadio_page_url = "/jxkh/issueRadio.action";
var boot_table_id = "boot-table-dbrw";
//根据流程id获取流程图
var speedOfProgressByprocessId = '/task/listActionByProcessId.action?processId=';
//根据流程id获取批注信息
var commentDetailByprocessId = '/task/listHistoryCommentDetailByProcessId.action?processId=';
//根据普通案件标号查询案件信息
var query_issue_by_issuenum = '/issues/getIssueAndProcessInfoByIssueNumber.action';


// jsp 映射表 已移动到 公共js
var jsp_include_mapper = {
    // 'key15' : "shequchuli",
    // "key2" : 'sqzcz',
    // 'key3' : "jiedaofenfa",
    // 'key4' : "qujizhongxin",
    // 'key5' : "wbjjs",
    // 'key6' : "wbjcl",
    // 'key7' : "wbjla",
    // 'key9' : "comment",
    // 'key13' : "jdxxdc",
    // 'key14' : "qjzxxxdc",
    // 'key8' : "qjzxxgclsx",
    // //'key10' : "zgbmlb",
    // 'key10' : "wbjcl", //需要联办的案件也是需要派遣的
    // 'key11' : "ynajcl",
    // 'key12' : "jdbmcl",
    'key18': "dbpf" //督办派发
};
var dcdbPageNum = 1;
/**
 * Li  标签块映射
 */
var LiHeaderTagMap = [
    {
        imgUrl: "dla.svg"
    }, {
        imgUrl: "bmdqs.svg"
    }, {
        imgUrl: "dcf.svg"
    }, {
        imgUrl: "dja.svg"
    }];


//切换搜索条 index:下标
// var tagArr = ['jdjc', 'xcjz', 'dcdbTag', 'xcbd','ajbz']
var tagArr = ['xcbd', 'ajbz']

function switchTag(index) {
    for (var i = 0; i < tagArr.length; i++) {
        if (i != index && tagArr[i] != "") {
            $("." + tagArr[i]).css("display", "none");
        }
    }
    if (tagArr[index] != "") {
        $("." + tagArr[index]).css("display", "block");
    }
}

// 待办任务标签表
var todo_flags = [
    {
        id: "1",
        desc: "网格员",
        flags: [{
            show_flag: "街道派遣",
            query_page_param: "案件录入",
            query_count_param: "subDistriceDistributeCount",
            query_flag: "街道派遣数量"
        }, {
            show_flag: "自处置",
            query_page_param: "自处置",
            query_count_param: "waitHandleSelfCount",
            query_flag: "自处置数量"
        }, {
            show_flag: "待评价",
            query_page_param: "评价",
            query_count_param: "waitEvalutateCount",
            query_flag: "待评价数量"
        }]
    }, {
        id: "2",
        desc: "街道中心",
        flags: [{
            show_flag: "待派发",
            query_page_param: "街道派发",
            query_count_param: "subDistriceWaitDistributeCount",
            query_flag: "待派发数量"
        }]
    }, {
        id: "3",
        desc: "区级中心",
        flags: [
            {
                //     show_flag : "监督检查",
                //     query_page_param : "监督检查",
                //     query_count_param : "districeCenterWaitDistributeCount",
                //     query_flag : "待派发数量"
                // }, {
                //     show_flag : "巡查机制",
                //     query_page_param : "巡查机制",
                //     query_count_param : "districeCenterWaitDistributeCount",
                //     query_flag : "待派发数量"
                // }, {
                //     show_flag : "督察督办",
                //     query_page_param : "督察督办",
                //     query_count_param : "districeCenterWaitModTimeLimitCount",
                //     query_flag : "待修改时限数量"
                //
                // }, {
                show_flag: "宣传报道",
                query_page_param: "宣传报道",
                query_count_param: "districeCenterWaitDistributeCount",
                query_flag: "待派发数量"
            }, {
                show_flag: "案件比重",
                query_page_param: "案件比重",
                query_count_param: "waitEvalutateCount",
                query_flag: "待评价数量"
            }]
    }, {
        id: "4",
        desc: "领导小组办公室，区主管领导，区分管领导",
        flags: [{
            show_flag: "疑难问题待处理",
            query_page_param: "疑难案件处理",
            query_count_param: "districeCenterDifficultCaseWaitHandleCount",
            query_flag: "疑难问题待处理数量"
        }]
    }, {
        id: "5",
        desc: "委办局",
        flags: [{
            show_flag: "待接收",
            query_page_param: "主管部门接收",
            query_count_param: "waitCompetentDepartmentReceiveCount",
            query_flag: "待主管部门接收数量"
        }, {
            show_flag: "待立案",
            query_page_param: "主管部门立案",
            query_count_param: "waitCompetentDepartmentFilingCount",
            query_flag: "待主管部门立案数量"
        }, {
            show_flag: "待处理",
            query_page_param: "主管部门处理",
            query_count_param: "waitCompetentDepartmentHandleCount",
            query_flag: "待主管部门处理数量"
        }, {
            show_flag: "待联办",
            query_page_param: "问题联办",
            query_count_param: "waitCompetentDepartmentCooperationCount",
            query_flag: "待主管部门联办数量"
        }]
    }, {
        id: "6",
        desc: "街道内设科室",
        flags: [{
            show_flag: "待处理",
            query_page_param: "街道处理",
            query_count_param: "waitSubDistriceHandleCount",
            query_flag: "待处理任务数量"
        }]
    }, {
        id: "7",
        desc: "区级中心督察组",
        flags: [{
            show_flag: "待线下督查",
            query_page_param: "线下督查",
            query_count_param: "waitDistriceCenterOfflineSuperVisorCount",
            query_flag: "待线下督查数量"
        }]
    }, {
        id: "8",
        desc: "街道督察组",
        flags: [{
            show_flag: "待线下督查",
            query_page_param: "督察组线下督查",
            query_count_param: "waitSubDistriceSuperVisorCount",
            query_flag: "待线下督查数量"
        }]
    }];

var columns = [
   /* {
        checkbox: true
    },*/ {
        title: '机构代码',
        field: 'qhzdId',
        align: 'center',
        valign: 'middle',
        visible: false
    }, {
        title: '机构名称',
        field: 'qhzdName',
        align: 'center',
        valign: 'middle'/*,
        formatter : function(value, row, index) {
            return '<span class="link-col">' + value + '</span>'
        }*/
    }, {
        title: '发生案件数量',
        field: 'issuenum',
        align: 'center',
        valign: 'middle'
    }, {
        title: '12345网站年工单量',
        field: 'avg12345num',
        align: 'center',
        valign: 'middle'
    }, {
        title: '统一系数',
        field: 'coefficient',
        align: 'center',
        valign: 'middle',
    }, {
        title: '案件比重',
        field: 'proportion',
        align: 'center',
        valign: 'middle',
        visible: false,

    }, {
        title: '月份',
        field: 'khyf',
        align: 'center',
        valign: 'middle'
    }, {
        title: '得分',
        field: 'score',
        align: 'center',
        valign: 'middle'
    }];

/*
 * 获取 待办任务标签 html 元素 并 绑定事件
 */
function getLiHeaderTag(flag, tagIssueCount, imgUrl, index) {
    var itemId = 'tag-li-' + flag.show_flag;
    var itemTemplate = "<li id='"
        + itemId
        + "'><a href='##' >"
        + flag.show_flag
        + "<embed src='"
        + url_prefix
        + "/static/images/"
        + imgUrl
        + "' type='image/svg+xml' pluginspage='http://www.adobe.com/svg/viewer/install/' />"
        /*+ "</a><i>" + tagIssueCount + "</i></li>";*/
        + "</a></li>";
    $("#ul-header-tag").append(itemTemplate);

    $("#" + itemId).click(function () {
        switchTag(index);//切换搜索栏
        switch (index) {
            // case 4:
            //     jdjc_obj.refreshTableByParams(1);break;
            // case 3:
            //     dcdb_obj.refreshDcDbTableByParams(1); break;
            // case 2:
            //     xcjz_obj.refreshTableByParams(1);break;
            case 1:
                ajbz.refreshDataTableByParams(1);
                break;
            case 0:
                xcbd_obj.refreshTableByParams(1);
                break;
        }

        // alert(index+" = "+flag.query_page_param);


    });
};

//==============================================  案件比重 start ===============================================

var ajbz = {
    resultRows:"",
    rowCount:0,
    beginDate:0,
    //显示设置弹窗
    ajbzset: function () {
        var date = new Date();
        var month = date.getMonth()+1;
        month = month<10 ? ('0'+month) : month;
         beginDate = (date.getFullYear()+"-"+ month);
    /*    $("#khyf_ajbz").val(beginDate)*/
       /* var khyf = $('#khyf_ajbz').val();
        if (khyf == "") {
            layerAlert("请选择考核月份！")
            return false;
        }*/ /*else {
            var now = new Date();
            var thisYear = now.getFullYear();
            var thisMonth = now.getMonth() + 2;
            var queryAarr = khyf.split('-');
            if (thisYear < queryAarr[0] || (thisYear == queryAarr[0] && thisMonth <= queryAarr[1])) {
                layerAlert('请选择当前月份之前的月份！');
                return false;
            }
        }*/
     /*   if(ajbz.resultRows!="") {*/
            var str ="";
            var num=0;
           /* $.each(ajbz.resultRows, function (idx, item) {

                str +=" <div class=\"form-group\">\n" +
                    "    <label for=\"avg12345num"+num+"\" class=\"col-sm-3 control-label\">"+item.qhzdName+"12345网站总工单量</label>\n" +
                    "    <div class=\"col-sm-9\">\n" +
                    "        <input type=\"number\" name=\"avg12345num"+num+"\" class=\"form-control\" id=\"avg12345num"+num+"\" value=\""+item.avg12345num+"\" placeholder=\""+item.qhzdName+"总自处置工单量\">\n" +
                    "        <input type=\"hidden\" name=\"jd"+num+"\" id=\"jd"+num+"\" value='"+item.qhzdId+"'>\n" +
                    "    </div>\n" +
                    "</div>"

                num++;
            })
            ajbz.rowCount=num;
            if(str!=""){
                str+="<div class=\"modal-footer\">\n" +
                    "    <div class=\"text-center\">\n" +
                    "        <button type=\"button\" onclick=\"ajbz.saveRadioSet()\" class=\"btn btn-primary\">保存\n" +
                    "        </button>\n" +
                    "        <button type=\"reset\" class=\"btn btn-default\">重置</button>\n" +
                    "    </div>\n" +
                    "</div>";
                $("#ajbzRadioSet").empty();
                $("#ajbzRadioSet").prepend(str);

            }*/
            $('#ajbzsetModal').modal('show');
        /*}else{
            layerAlert('请先查询！');
        }*/
    },
    //保存参数设置
    saveRadioSet: function () {
        var _this = this;
/*var num ="";
var code ="";
        for(var i=0;i<ajbz.rowCount;i++){
            num+=$("#avg12345num"+i+"").val()+",";
            code+=$("#jd"+i+"").val()+",";
        }*/

        var num1 = $("#avg12345num1").val();
        var code1 = $("#jd1").val();
        var num2 = $("#avg12345num2").val();
        var code2 = $("#jd2").val();
        var num3 = $("#avg12345num3").val();
        var code3 = $("#jd3").val();
        var num4 = $("#avg12345num4").val();
        var code4 = $("#jd4").val();
        var num5 = $("#avg12345num5").val();
        var code5 = $("#jd5").val();
        var num6 = $("#avg12345num6").val();
        var code6 = $("#jd6").val();
        var num7 = $("#avg12345num7").val();
        var code7 = $("#jd7").val();
        var num8 = $("#avg12345num8").val();
        var code8 = $("#jd8").val();
        var num9 = $("#avg12345num9").val();
        var code9 = $("#jd9").val();
        var num10 = $("#avg12345num10").val();
        var code10 = $("#jd10").val();
        var num11 = $("#avg12345num11").val();
        var code11 = $("#jd11").val();
        var num12 = $("#avg12345num12").val();
        var code12 = $("#jd12").val();
        var num13 = $("#avg12345num13").val();
        var code13 = $("#jd13").val();

      /*  var khyf = $("#khyf_ajbz").val();
        $("#khyf_ajbz").val(khyf);*/
        $.ajax({
            //  url: url_prefix + "/jxkh/saveRadioSet.action",
            url: url_prefix + "/jxkh/saveRadioKh.action",
            data: $("#ajbzRadioSet").serialize(),
            type: "POST",
            data: {

                num1: num1,
                code1: code1,
                num2: num2,
                code2: code2,
                num3: num3,
                code3: code3,
                num4: num4,
                code4: code4,
                num5: num5,
                code5: code5,
                num6: num6,
                code6: code6,
                num7: num7,
                code7: code7,
                num8: num8,
                code8: code8,
                num9: num9,
                code9: code9,
                num10: num10,
                code10: code10,
                num11: num11,
                code11: code11,
                num12: num12,
                code12: code12,
                num13: num13,
                code13: code13,
                khyf: beginDate
            },
            success: function (result) {
                if (result == 1) {
                    $('#ajbzsetModal').modal('hide');
                    layerAlert('保存成功！')
                } else {
                    layerAlert('保存失败！')
                }
                ajbz.query(1);
            }
        });
    },
    showAjbzDetails: function () {
        var _this = this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length != 1) {
            layerAlert("请选择一条修改的记录！");
            return false;
        } else {
            selectedRow = selectedRow[0];
            $("#ajbzsetModal input[name='qhzdId']").val(selectedRow.qhzdId);
            $("#ajbzsetModal input[name='avg12345num']").val(selectedRow.avg12345num);
        }
        $('#ajbzsetModal').modal('show');
    },
    //按月查询案件比重
    query: function (pageNum) {
      /*  $("#avg_12345num").val("");
        $("#avg12345num").val("");*/
        var khyf_ajbz = $("#khyf_ajbz").val();
        if (khyf_ajbz == null || khyf_ajbz == "") {
            layerAlert("请选择考核月份！")
            return false;
        }

        //计算当前的 年和月
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 2;
        var queryAarr = khyf_ajbz.split('-');
        if (thisYear < queryAarr[0] || (thisYear == queryAarr[0] && thisMonth <= queryAarr[1])) {
            layerAlert('请选择当前月份之前的月份！');
            return false;
        }
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            pagination : false,
            height : ($('.content').height() - $('.funShow').height()),
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
            pagination : false,
            striped : true, // 是否显示行间隔色
            columns : columns,
            method : 'post',
            contentType : "application/x-www-form-urlencoded",
            dataType : 'json',
            columns: columns,
            url: url_prefix + issueRadio_page_url,
           /* pageNumber: pageNum || pageNumber,*/
            responseHandler: function (res) {
                /*pageNum = "";*/
                var objs = JSON.parse(res);
                ajbz.resultRows=objs.rows;
                var khyf = $('#khyf_ajbz').val();
        /*        if (avgNum != "" && coe != "" && khyf != "") {*/
                    $.each(objs.rows, function (idx, item) {
                       /* var happenCount = item.issuenum;
                        item.avg12345num = avgNum;
                        item.coefficient = coe;
                        item.proportion = (happenCount * 100 / coe).toFixed(2) + "%";
                        //（发生案件数量/街道（园区）上年度12345月平均工单量）×150分，若比值大于1，将适情乘以统一系数。
                        var score = happenCount * 150 / avgNum;
                        item.score = score > 1 ? (score * coe).toFixed(2) : score.toFixed(2);*/
                    })
            /*    }*/
                return objs;
            },
            queryParams: function (params) {
                return {
                  /*  rows: $("#" + boot_table_id).bootstrapTable('getOptions').pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1), //页码,*/
                    khyf: khyf_ajbz //考核月份,
                }
            }
        });

    },
    checkDate: function () {
        if ($('#khyf_ajbz').val() == "") {
            layerAlert("请选择考核月份!");
            return false;
        }

        if ($('#avg_12345num').val() == "" || $("#coe_fficient").val() == "") {
            layerAlert("请设置保存参数!");
            return false;
        }
        return;

    },
    refreshDataTableByParams: function (pageNum) {
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: columns,
            url: url_prefix + issueRadio_page_url,
            pageNumber: pageNum || pageNumber,
            queryParams: function (params) {
                return {
                    rows: options.pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1) //页码,
                    // s_name: query_page_param
                }
            },
            responseHandler: function (res) {
                pageNum = "";
                return JSON.parse(res);
            }
        });
    }
};

//==============================================  案件比重 end =================================================
/*
//==============================================  案件比重 start ===============================================

var ajbz = {
    //显示设置弹窗
    ajbzset: function () {
        var khyf = $('#khyf_ajbz').val();
        if(khyf==""){
            layerAlert("请选择考核月份！")
            return false;
        }
        $('#ajbzsetModal').modal('show');
    },
    //保存参数设置
    saveRadioSet: function () {
        var _this = this;
        var avg = $("#avg12345num").val();
        var con = $("#coefficient").val();
        var yf = $("#khyf_ajbz").val();
        $("#avg_12345num").val(avg);
        $("#coe_fficient").val(con);
        $.ajax({
            url: url_prefix + "/jxkh/saveRadioSet.action",
            data: $("#ajbzRadioSet").serialize(),
            type: "POST",
            success: function (res) {
                if (res == "1") {
                    $('#ajbzsetModal').modal('hide');
                }
            }
        });
        _this.computeTableDate();
    },//计算表格数据
    computeTableDate:function(){
        var avgNum = $('#avg_12345num').val();
        var coe = $('#coe_fficient').val();
        var khyf = $('#khyf_ajbz').val();

        if(avgNum!="" && coe!="" && khyf!=""){
            $('#boot-table-dbrw tr').each(function(i){
                var happenCount = $(this).children('td').eq(1).text();
                $(this).children('td').eq(2).text(avgNum);
                $(this).children('td').eq(3).text(coe);
                $(this).children('td').eq(4).text((happenCount*100/avgNum).toFixed(2)+"%");
                //（发生案件数量/街道（园区）上年度12345月平均工单量）×150分，若比值大于1，将适情乘以统一系数。
                var score = happenCount*150/avgNum;
                score=score>1?(score*coe).toFixed(2):score.toFixed(2);
                $(this).children('td').eq(6).text(score);
            })
        }
    },
    //按月查询案件比重
    query: function (pageNum) {
        $("#avg_12345num").val("");
        $("#coe_fficient").val("");
        $("#avg12345num").val("");
        $("#coefficient").val("");
        var khyf_ajbz = $("#khyf_ajbz").val();
        if (khyf_ajbz == null || khyf_ajbz == "") {
            layerAlert("请选择考核月份！")
            return false;
        }

            //计算当前的 年和月
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 1;
        var queryAarr = khyf_ajbz.split('-');
     /!*   if (thisYear < queryAarr[0] || (thisYear == queryAarr[0] && thisMonth <= queryAarr[1])) {
            layerAlert('请选择当前月份之前的月份！');
            return false;
        }*!/
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: columns,
            url: url_prefix + issueRadio_page_url,
            pageNumber: pageNum||pageNumber,
            responseHandler: function (res) {
                pageNum="";
                var objs = JSON.parse(res);

                var avgNum = $('#avg_12345num').val();
                var coe = $('#coe_fficient').val();
                var khyf = $('#khyf_ajbz').val();
                if(avgNum!="" && coe!="" && khyf!=""){
                    $.each(objs.rows,function(idx,item){
                        var happenCount = item.issuenum;
                        item.avg12345num = avgNum;
                        item.coefficient=coe;
                        item.proportion=(happenCount*100/coe).toFixed(2)+"%";
                        //（发生案件数量/街道（园区）上年度12345月平均工单量）×150分，若比值大于1，将适情乘以统一系数。
                        var score = happenCount*150/avgNum;
                        item.score=score>1?(score*coe).toFixed(2):score.toFixed(2);
                    })
                }
                return objs;
            },
            queryParams: function(params){
               return {
                   rows: $("#" + boot_table_id).bootstrapTable('getOptions').pageSize, //页面大小
                   page: pageNum || (params.offset / params.limit + 1), //页码,
                   khyf: khyf_ajbz //考核月份,
               }
            }
        });

    },
    //案件比重提交
    submit: function () {
        var _this = this;
        _this.checkDate();
        var avgNum = $('#avg_12345num').val();
        var cone = $('#coe_fficient').val();
        var khyf = $('#khyf_ajbz').val();
        $.ajax({
            method: 'post',
            url: '../../jxkh/updateAjbz.do',
            dataType: 'json',
            data: {
                avg12345num: avgNum,
                coefficient: cone,
                khyf: khyf
            },
            success: function (result) {
                if (result == 1) {
                    $("#avg_12345num").val("");
                    $("#coe_fficient").val("");
                    $("#avg12345num").val("");
                    $("#coefficient").val("");
                    layerAlert('保存成功！')
                    // showDialog('提示', '保存成功！', function(){
                     $(".modal").modal('hide');
                    // });
                } else {
                    // showDialog('提示', '保存失败！', hideDialog);
                    layerAlert('保存失败！')
                }

                //window.location.href = window.location.href;
            }
        });

    },
    checkDate: function () {
        if ($('#khyf_ajbz').val() == "") {
            layerAlert("请选择考核月份!");
            return false;
        }

        if ($('#avg_12345num').val() == "" || $("#coe_fficient").val()== "") {
            layerAlert("请设置保存参数!");
            return false;
        }
        return ;

    },
    refreshDataTableByParams:function(pageNum) {
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns : columns,
            url : url_prefix + issueRadio_page_url,
            pageNumber: pageNum||pageNumber,
            queryParams : function(params){
                return {
                    rows: options.pageSize, //页面大小
                    page: pageNum||(params.offset / params.limit + 1) //页码,
                    // s_name: query_page_param
                }
            },
            responseHandler:function (res) {
                pageNum="";
                return JSON.parse(res);
            }
        });
    }
};

//==============================================  案件比重 end =================================================
*/

//==============================================  督查督办 start ===============================================
var dbtaskId = null; //督办任务id
var dbqhzdName = null; //被督办机构
var dbqhzdId = null; //被督办机构代码
var dcdb_obj = {
    issueDbdc_page_url: "/jxkh/listDcdbKh.action",
    // 根据taskId 查询案件信息
    query_issue_by_taskId: '/taskDb/getDbIssueByTaskId.action',
    // 根据taskId获得点击的批注详情
    commentDetail: '/task/listHistoryComment.action?taskId=',
    //根据taskId获取流程图
    speedOfProgress: '/task/listAction.action?taskId=',
    columns_dcdb: [
        /*{
            checkbox: true
        },*/ {
            title: '督查督办编号',
            field: 'dcdbnum',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title: '任务id',
            field: 'taskId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '机构名称',
            field: 'qhzdName',
            align: 'center',
            valign: 'middle'/*,
            formatter : function(value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }*/
        }, {
            title: '案件状态',
            field: 'status',
            align: 'center',
            valign: 'middle'
        }, {
            title: '案件状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '案件编号',
            field: 'issuenum',
            align: 'center',
            valign: 'middle'
        }, {
            title: '类型',
            field: 'typeId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '类型',
            field: 'sourceName',
            align: 'center',
            valign: 'middle'
        }, {
            title: '扣分',
            field: 'score',
            align: 'center',
            valign: 'middle',
        }, {
            title: '&nbsp;&nbsp;&nbsp;&nbsp;日期&nbsp;&nbsp;&nbsp;&nbsp;',
            field: 'khyf',
            align: 'center',
            valign: 'middle'
        }],
    scoreType: {
        "反复投诉": "<option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option>",
        "工作协调": "<option value='2'>2</option><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option><option value='8'>8</option><option value='9'>9</option><option value='10'>10</option>",
        "受理交办": "<option value='1'>1</option><option value='2'>2</option>"
    },
    saveDcdbInfo: function () {
        var _this = this;
        var result = _this.checkDcdbDate();
        if (result) {
            var options = {
                url: url_prefix + '/jxkh/saveDcdbInfo.action',
                dataType: 'json',
                success: function (responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        layerAlert('保存成功！')
                        // showDialog('提示', '保存成功！', function(){
                        $(".modal").modal('hide');
                        // });

                        _this.refreshDcDbTableByParams(1);
                    } else {
                        layerAlert('保存失败！')
                        // showDialog('提示', '保存失败！', hideDialog);
                    }
                }
            };

            $("#addPostForm_dcdb").ajaxForm(options);
            $('#addPostForm_dcdb').submit();
        }
    },
    //查询
    refreshDcDbTableByParams: function (pageNum) {
        var _this = this;
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: _this.columns_dcdb,
            url: url_prefix + _this.issueDbdc_page_url,
            pageNumber: pageNum || (pageNumber),
            responseHandler: function (res) {
                pageNum = "";
                dcdbPageNum = pageNum || this.pageNumber;
                return JSON.parse(res);
            },
            queryParams: function (params) {
                return {
                    rows: $("#" + boot_table_id).bootstrapTable('getOptions').pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1), //页码,
                    khyf: $("#dcdb_khyf").val(), //考核月份,
                    jgmc: $("#dcdb_jgmc").val() //页码
                }
            }
        });
    },
    // 显示全网格案件处理页面
    showDcDbDetails: function (type) {
        var _this = this;
        initUserGroup($("#qhzdGroup"), "2,5")
        initAddOptions($("#violationtype"), url_prefix + '/qhzd/listDictionary.aciton', "violationtype");
        initAddOptions($("#issueSource"), url_prefix + '/qhzd/listDictionary.aciton', "ajly");
        setTimeout(function () {
            if (type == "update") {
                var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
                if (selectedRow == null || selectedRow.length != 1) {
                    layerAlert("请选择一条修改的记录！");
                    return false;
                } else {
                    selectedRow = selectedRow[0];
                    _this.initDeductScore(selectedRow.typeName)
                    $("#myDcdbModal input[name='id']").val(selectedRow.id);
                    $("#myDcdbModal input[name='dcdbNum']").val(selectedRow.dcdbNum);
                    $("#myDcdbModal input[name='issuenum']").val(selectedRow.issuenum);
                    $("#myDcdbModal input[name='sourceName']").val(selectedRow.sourceName);
                    $("#myDcdbModal input[name='qhzdName']").val(selectedRow.qhzdName);
                    $("#myDcdbModal input[name='typeName']").val(selectedRow.typeName);
                    $("#myDcdbModal textarea[name='descript']").val(selectedRow.descript);
                    $("#myDcdbModal textarea[name='info']").val(selectedRow.info);
                    $("#myDcdbModal input[name='khyf']").val(selectedRow.khyf);

                    $("#qhzdGroup").val(selectedRow.qhzdId);
                    $("#issueSource").val(selectedRow.sourceId);
                    $("#violationtype").val(selectedRow.typeId);
                    $("#deductScore").val(selectedRow.score);

                    $("#dcdb_qhzdName").val(selectedRow.qhzdName);
                    $("#dcdb_typeName").val(selectedRow.typeName);
                    $("#dcdbNum").val(selectedRow.dcdbNum);
                    $("#dcdb_sourceName").val(selectedRow.sourceName);
                }
            } else {
                $("#myDcdbModal input[name='id']").val("");
                $("#myDcdbModal input[name='dcdbNum']").val("");
                $("#myDcdbModal input[name='issuenum']").val("");
                $("#myDcdbModal input[name='sourceName']").val("");
                $("#myDcdbModal input[name='qhzdName']").val("");
                $("#qhzdGroup").val("");
                $("#violationtype").val("");
                $("#issueSource").val("");
                $("#deductScore").val("");
                $("#myDcdbModal input[name='typeName']").val("");
                $("#myDcdbModal textarea[name='descript']").val("");
                $("#myDcdbModal textarea[name='info']").val("");
                $("#myDcdbModal input[name='khyf']").val("");
                $("#dcdb_qhzdName").val("");
                $("#dcdb_typeName").val("");
                $("#dcdbNum").val("");
                $("#dcdb_sourceName").val("");
            }
            $('#myDcdbModal').modal('show');
        }, 300)

    },
    //删除督察督办信息
    delDcdbInfo: function () {
        var _this = this;

        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            layerAlert("请选择要删除的记录！");
            s
            return false;
        } else {
            var ids = "";
            var status = "";
            $.each(selectedRow, function (idx, obj) {
                ids += obj.id + ",";
                status += obj.status;
            });
            if (ids.length > 0) {
                var flag = confirm("确认删除这条记录？");
                if (status != 17) {
                    layerAlert("案件未完结,不能删除!");
                    return;
                } else {
                    if (flag == true)
                        $.ajax({
                            type: "GET",
                            url: url_prefix + '/jxkh/delDcdbInfo.action',
                            data: {ids: ids},
                            dataType: "json",
                            success: function (data) {
                                if (data.success == true) {
                                    layerAlert('删除成功！')
                                    // showDialog('提示', '删除成功！', hideDialog);
                                    _this.refreshDcDbTableByParams(1);
                                } else {
                                    layerAlert('删除失败！')
                                    // showDialog('提示', '删除失败！', hideDialog);
                                }
                            }
                        });
                }
            }
        }
    },
    //初始化扣分
    initDeductScore: function (type) {
        var _this = this;
        $("#deductScore").empty();
        var htmlStr = "<option value = '' > 请选择 </option>";
        if (type != "" && type != "请选择") {
            htmlStr += eval("_this.scoreType." + type);
        }
        $("#deductScore").append(htmlStr);
    },
    //页面提交校验
    checkDcdbDate: function () {
        if ($("#myDcdbModal input[name='issuenum']").val() == "") {
            layerAlert("案件编号不能为空!");
            return false;
        }
        if ($("#myDcdbModal input[name='khyf']").val() == "") {
            layerAlert("考核月份不能为空!");
            return false;
        }
        if ($("#myDcdbModal input[name='sourceName']").val() == "") {
            layerAlert("信息来源不能为空!");
            return false;
        }
        if ($("#myDcdbModal input[name='qhzdName']").val() == "") {
            layerAlert("涉嫌机构不能为空!");
            return false;
        }
        if ($("#myDcdbModal input[name='typeName']").val() == "") {
            layerAlert("涉嫌违规类型不能为空!");
            return false;
        }
        if ($("#deductScore").val() == "") {
            layerAlert("扣分不能为空!");
            return false;
        }
        // if($("#myDcdbModal textarea[name='descript']").val()==""){
        //     layerAlert("案件信息不能为空!");
        //     return false;
        // }
        // if($("#myDcdbModal textarea[name='info']").val()==""){
        //     layerAlert("情况描述不能为空!");
        //     return false;
        // }

        return true;
    },
    init: function () {
        var _this = this;
        //涉嫌违规类型
        $("#violationtype").change(function () {
            var violationtype = $.trim($("#violationtype").find("option:selected").text());
            _this.initDeductScore(violationtype);
            $("#dcdb_typeName").val(violationtype)
        });

        //涉嫌机构
        $("#qhzdGroup").change(function () {
            $("#dcdb_qhzdName").val($.trim($("#qhzdGroup").find("option:selected").text()))
        });

        //信息来源
        $("#issueSource").change(function () {
            $("#dcdb_sourceName").val($.trim($("#issueSource").find("option:selected").text()))
        });

        //扣分
        $("#deductScore").click(function () {
            var violationtype = $.trim($("#violationtype").find("option:selected").text());
            if (violationtype == "" || violationtype == "请选择") {
                layerAlert("请先选择涉嫌违规类型！")
                return;
            }
        });


        $('#myDcdbModal.close').click(function () {
            $('#myDcdbModal').modal('hide');
        });

        $("#" + boot_table_id).on("click-cell.bs.table", function (event, column_name, value, row) {
            var issueType = row.status;//待考核组派发18
            var typeId = row.typeId;//反复投诉69

            //如果是需要走流程 ，弹出流程页面
            if (column_name == "dcdbnum") {
                if (typeId == 69 && issueType == 18) {//row.taskId != "0"
                    _this.showIssueDealPage(row, true);
                } else if (typeId == 69 && issueType != 18) {//2.	反复投诉+非考核小组：显示上部
                    _this.showIssueDealPage(row, false);
                } else {//其他
                    myappWin.qwgDetail = row;
                    $('#myDcdbModal2').modal('show');
                }
            }
            // else if(column_name == "orderno"){
            //     console.log(row);
            //     showQwgDetails(row);
            // }
        });


    },
    //显示督办信息+流程进度+批注内容
    showIssueDealPage: function (row, flag) {
        var _this = this;
        //督办信息
        myappDb.ajDetail = row;
        var processId = row.processId;
        console.log(row);
        dbtaskId = row.taskId;
        dbqhzdName = myappDb.ajDetail.qhzdName;
        dbqhzdId = myappDb.ajDetail.qhzdId;
        $("#myModal").modal({backdrop: 'static'}).show();
        // //显示内嵌页面
        if (flag) {
            page_suffix = eval("jsp_include_mapper." + 'key18');
            // page_suffix = jsp_include_mapper[status];
            $('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp?type=dcdb', function () {
            })
        }

        //流程进度
        myappDb.$http.get(url_prefix + speedOfProgressByprocessId + processId).then(
            function (response) {
                var data = JSON.parse(response.data);
                console.log(data.rows);
                myappDb.speedOfProgress = data.rows;
                console.log(myapp.speedOfProgress);
                var startTime = new Date(myappDb.speedOfProgress[0].startTime);
                var endTime;
                if (myappDb.speedOfProgress[myappDb.speedOfProgress.length - 1].endTime) {
                    endTime = new Date(myappDb.speedOfProgress[myappDb.speedOfProgress.length - 1].endTime);
                }
                else endTime = new Date();
                myappDb.timeTaken = endTime - startTime;
                Vue.nextTick(function () {

                    $('.dec').each(function (index, dom) {
                        var shart = setInterval(function () {
                            if ($('.shuju').height() > 0) {
                                //console.log($(dom).parent().children('.text').innerHeight(),$(dom).parent().parent().innerHeight());
                                $(dom).height($(dom).parent().parent().innerHeight() - $(dom).parent().children('.text').innerHeight() + 'px');
                                $(dom).css('bottom', '-' + $(dom).css('height'));
                                clearInterval(shart);
                            }
                        }, 50)
                    })
                })
            });
        //批注内容
        $.ajax({
            method: 'get',
            url: url_prefix + commentDetailByprocessId + processId,
            dataType: 'json',
            success: function (data) {
                data = (typeof data == "object") ? data : $.parseJSON(data)
                myappDb.commentDetail = data.rows;
                console.log(data);
                //初始化提示框
                $('[data-toggle="tooltip"]').tooltip();
            }
        });

    }
}
dcdb_obj.init();
//==============================================  督查督办 end ===============================================

//==============================================  宣传报道 start =============================================
var xcbd_obj = {
    columns: [
        {
            checkbox: true
        }, {
            title: '机构代码',
            field: 'qhzdDm',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '机构名称',
            field: 'qhzdMc',
            align: 'center',
            valign: 'middle'
        }, {
            title: '国家级',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index, field) {
                return row.countrywlmt + row.countryfwlmt;
            }
        }, {
            title: '省级',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index, field) {
                return row.provincewlmt + row.provincefwlmt;
            }
        }, {
            title: '市级',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index, field) {
                return row.citywlmt + row.cityfwlmt;
            }
        }, {
            title: '区级',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index, field) {
                return row.districtwlmt + row.districtfwlmt;
            }
        }, {
            title: '总分',
            field: 'score',
            align: 'center',
            valign: 'middle'
        }, {
            title: '考核月份',
            field: 'khyf',
            align: 'center',
            valign: 'middle'
        }],
    // 显示全网格案件处理页面
    showXcbdDetails: function (type) {
        var _this = this;
        if (type == "update") {
            var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
            if (selectedRow == null || selectedRow.length != 1) {
                layerAlert("请选择一条修改的记录！");
                return false;
            } else {
                selectedRow = selectedRow[0];
                $("#myXcbdModal input[name='id']").val(selectedRow.id);
                $("#myXcbdModal input[name='countrywlmt']").val(selectedRow.countrywlmt);
                $("#myXcbdModal input[name='countryfwlmt']").val(selectedRow.countryfwlmt);
                $("#myXcbdModal input[name='provincewlmt']").val(selectedRow.provincewlmt);
                $("#myXcbdModal input[name='provincefwlmt']").val(selectedRow.provincefwlmt);
                $("#myXcbdModal input[name='citywlmt']").val(selectedRow.citywlmt);
                $("#myXcbdModal input[name='cityfwlmt']").val(selectedRow.cityfwlmt);
                $("#myXcbdModal input[name='districtwlmt']").val(selectedRow.districtwlmt);
                $("#myXcbdModal input[name='districtfwlmt']").val(selectedRow.districtfwlmt);
                $("#myXcbdModal input[name='khyf']").val(selectedRow.khyf);
                $("#qhzdDm").val(selectedRow.qhzdDm);
                $("#qhzdMc").val(selectedRow.qhzdMc);
            }
        } else {
            $("#myXcbdModal input[name='id']").val("");
            $("#myXcbdModal input[name='countrywlmt']").val("");
            $("#myXcbdModal input[name='countryfwlmt']").val("");
            $("#myXcbdModal input[name='provincewlmt']").val("");
            $("#myXcbdModal input[name='provincefwlmt']").val("");
            $("#myXcbdModal input[name='citywlmt']").val("");
            $("#myXcbdModal input[name='cityfwlmt']").val("");
            $("#myXcbdModal input[name='districtwlmt']").val("");
            $("#myXcbdModal input[name='districtfwlmt']").val("");
            $("#myXcbdModal input[name='khyf']").val("");
            $("#qhzdDm").val("");
            $("#qhzdMc").val("");
        }
        $('#myXcbdModal').modal('show');
    },
    saveXcbdInfo: function () {
        var _this = this;
        var result = _this.checkDcdbDate();
        if (result) {
            var options = {
                url: url_prefix + '/jxkh/saveOrUpdateXcbdKh.action',
                dataType: 'json',
                success: function (responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        layerAlert('保存成功！')
                        $(".modal").modal('hide');
                        _this.refreshTableByParams(1);
                    } else {
                        layerAlert('保存失败！')
                    }
                }
            };
            $("#addPostForm_xcbd").ajaxForm(options);
            $('#addPostForm_xcbd').submit();
        }
    },
    delXcbdInfo: function (id) {
        var _this = this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            layerAlert("请选择要删除的记录！");
            return false;
        } else {
            var ids = "";
            $.each(selectedRow, function (idx, obj) {
                ids += obj.id + ",";
            });
            if (ids.length > 0) {
                $.ajax({
                    type: "GET",
                    url: url_prefix + '/jxkh/deleteXcbdKh.action',
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if (data.success == true) {
                            layerAlert('删除成功！')
                            _this.refreshTableByParams(1);
                        } else {
                            layerAlert('删除失败！')
                        }
                    }
                });
            }
        }
    },
    refreshTableByParams: function (pageNum) {
        var _this = this;
        initUserGroup($("#qhzdDm"), "2,5")
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        var url = url_prefix + '/jxkh/findTissueXcbdKh';
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: _this.columns,
            url: url,
            pageNumber: pageNum || pageNumber,
            queryParams: function (params) {
                return {
                    rows: options.pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1), //页码,
                    month: $('#month').val()
                }
            },
            responseHandler: function (res) {
                pageNum = "";
                return JSON.parse(res);
            }
        });
    },
    //页面提交校验
    checkDcdbDate: function () {
        if ($("#qhzdDm").val() == "") {
            layerAlert("机构名称不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='khyf']").val() == "") {
            layerAlert("考核月份不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countrywlmt']").val() == "") {
            layerAlert("国家级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countryfwlmt']").val() == "") {
            layerAlert("国家级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincewlmt']").val() == "") {
            layerAlert("省级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincefwlmt']").val() == "") {
            layerAlert("省级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='citywlmt']").val() == "") {
            layerAlert("市级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='cityfwlmt']").val() == "") {
            layerAlert("市级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtwlmt']").val() == "") {
            layerAlert("区级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtfwlmt']").val() == "") {
            layerAlert("区级非网络媒体不能为空!");
            return false;
        }

        return true;
    },
    //校验街道and月份事件的唯一性
    cbddCheck: function () {
        //获取文文本框的值
        var qhzdDm = $("#qhzdDm").val();
        var khyfXcbd = $("#khyf_xcbd").val();
        //$.ajax
        $.ajax({
            url: url_prefix + "/jxkh/checkXcbd.action",
            data: {
                qhzdDm: qhzdDm,
                khyf: khyfXcbd
            },
            type: "POST",
            success: function (result) {
                console.log("1")
                if (result == 1) {
                    layerAlert("此机构信息已存在,可返回修改,不能新增")
                    $("#qhzdDm").val("");
                    return false;
                } else {
                }
            }
        });

    },
    init: function () {
        //机构名称
        $("#qhzdDm").change(function () {
            $("#qhzdMc").val($.trim($("#qhzdDm").find("option:selected").text()))
        });
    }
};

xcbd_obj.init();
//==============================================  宣传报道 end ===============================================


//==============================================  巡查机制 start =============================================
var xcjz_obj = {
    name: '巡查机制',
    columns: [
        {
            checkbox: true
        }, {
            title: '案件编号',
            field: 'issuenum',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title: '信息来源ID',
            field: 'sourceId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '机构名称',
            field: 'qhzdName',
            align: 'center',
            valign: 'middle'
        }, {
            title: '涉嫌机构id',
            field: 'qhzdId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '扣分',
            field: 'score',
            align: 'center',
            valign: 'middle'
        }, {
            title: '考核月份',
            field: 'khyf',
            align: 'center',
            valign: 'middle'
        }, {
            title: '审核状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var result = "";
                switch (value) {
                    case '0' :
                        result = "通过";
                        break;
                    case '1' :
                        result = "不通过";
                        break;
                    case '2' :
                        result = "未审核";
                        break;
                    default:
                        result = "- -"
                }
                return result;
            }
        }, {
            title: '情况描述',
            field: 'descript',
            align: 'center',
            valign: 'middle'
        }, {
            title: '批注',
            field: 'comment',
            align: 'center',
            valign: 'middle',
            visible: false
        }],
    saveXcjzInfo: function () {// TODO
        var _this = this;
        var result = _this.checkDcdbDate();
        if (result) {
            var options = {
                url: url_prefix + '/jxkh/saveOrUpdateXcjzKh.action',
                dataType: 'json',
                success: function (responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        layerAlert('保存成功！')
                        $(".modal").modal('hide');
                        _this.refreshTableByParams(1);
                    } else {
                        layerAlert('保存失败！')
                    }
                }
            };
            $("#addPostForm_xcbd").ajaxForm(options);
            $('#addPostForm_xcbd').submit();
        }
    },
    delXcjzInfo: function (id) {
        var _this = this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            layerAlert("请选择要删除的记录！");
            return false;
        } else {
            var ids = "";
            $.each(selectedRow, function (idx, obj) {
                ids += obj.id + ",";
            });
            if (ids.length > 0) {
                $.ajax({
                    type: "GET",
                    url: url_prefix + '/jxkh/deleteXcjzKh.action',
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if (data.success == true) {
                            layerAlert('删除成功！')
                            _this.refreshTableByParams(1);
                        } else {
                            layerAlert('删除失败！')
                        }
                    }
                });
            }
        }
    },
    refreshTableByParams: function (pageNum) {
        var _this = this;
        // initUserGroup($("#qhzdDm"),"2,5")
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        var url = url_prefix + '/jxkh/findTissueXcjzKh';
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: _this.columns,
            url: url,
            pageNumber: pageNum || pageNumber,
            queryParams: function (params) {
                return {
                    rows: options.pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1), //页码,
                    month: $('#month_xcjz').val()
                }
            },
            responseHandler: function (res) {
                pageNum = "";
                return JSON.parse(res);
            }
        });
    },
    //页面提交校验
    checkDcdbDate: function () { // TODO
        if ($("#qhzdDm").val() == "") {
            layerAlert("机构名称不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='khyf']").val() == "") {
            layerAlert("考核月份不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countrywlmt']").val() == "") {
            layerAlert("国家级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countryfwlmt']").val() == "") {
            layerAlert("国家级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincewlmt']").val() == "") {
            layerAlert("省级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincefwlmt']").val() == "") {
            layerAlert("省级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='citywlmt']").val() == "") {
            layerAlert("市级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='cityfwlmt']").val() == "") {
            layerAlert("市级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtwlmt']").val() == "") {
            layerAlert("区级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtfwlmt']").val() == "") {
            layerAlert("区级非网络媒体不能为空!");
            return false;
        }

        return true;
    },
    page_suffix: 'xcjzcl',

    showIssueDealPage: function (row) {
        console.log(row);
        var _this = this;
        //下面的页面展示
        // ③巡查机制的表单信息，直接通过row获取即可
        // ①案件显示: 根据普通案件编号异步查询 赋值给 vue的 myapp.ajDetail
        // ②流程进度显示: 根据案件编号获取 流程id，获取历史任务信息 赋值给 myapp.speedOfProgress
        var issueNumber = row.issuenum;//案件编号 根据案件编号查询案件 赋值给 vue的 myapp.ajDetail
        xcjz_comment = row.comment; //巡查机制id
        xcjz_id = row.id; //巡查机制id
        var processId = "";//流程id

        $.ajax({
            method: 'post',
            url: url_prefix + query_issue_by_issuenum,
            dataType: 'json',
            data: {
                issueNumber: issueNumber
            },
            success: function (data) {
                data = (typeof data == "object") ? data : $.parseJSON(data);
                myapp.ajDetail = data.tMainIssueBean;
                processId = myapp.ajDetail.processId;
                console.log(myapp.ajDetail);
                $("#myModal3").modal({backdrop: 'static'}).show();
                $('#dbrw-cl3').load(url_prefix + '/page/sub/' + _this.page_suffix + '.jsp', function () {
                })
            }
        }).then(function () {
            console.log(processId);
            //流程进度
            myapp.$http.get(url_prefix + speedOfProgressByprocessId + processId).then(
                function (response) {
                    var data = JSON.parse(response.data);
                    myapp.speedOfProgress = data.rows;
                    console.log(myapp.speedOfProgress);
                    var startTime = new Date(myapp.speedOfProgress[0].startTime);
                    var endTime;
                    if (myapp.speedOfProgress[myapp.speedOfProgress.length - 1].endTime) {
                        endTime = new Date(myapp.speedOfProgress[myapp.speedOfProgress.length - 1].endTime);
                    }
                    else endTime = new Date();
                    myapp.timeTaken = endTime - startTime;
                    Vue.nextTick(function () {
                        $('.dec').each(function (index, dom) {
                            var shart = setInterval(function () {
                                if ($('.shuju').height() > 0) {
                                    //console.log($(dom).parent().children('.text').innerHeight(),$(dom).parent().parent().innerHeight());
                                    $(dom).height($(dom).parent().parent().innerHeight() - $(dom).parent().children('.text').innerHeight() + 'px');
                                    $(dom).css('bottom', '-' + $(dom).css('height'));
                                    clearInterval(shart);
                                }
                            }, 50)
                        })
                    })
                });
            //批注信息
            $.ajax({
                method: 'get',
                url: url_prefix + commentDetailByprocessId + processId,
                dataType: 'json',
                success: function (data) {
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    myapp.commentDetail = data.rows;
                    //初始化提示框
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });
        });

    },
    init: function () {
        var _this = this;
        $("#" + boot_table_id).on("click-cell.bs.table", function (event, column_name, value, row) {
            if (column_name == 'issuenum') {
                _this.showIssueDealPage(row);
            }
        });

    }
};

xcjz_obj.init();
//==============================================  巡查机制 end ===============================================

//==============================================  监督检查 start =============================================
var jdjc_obj = {
    columns: [
        {
            checkbox: true
        }, {
            title: '案件编号',
            field: 'issueNum',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title: '信息来源ID',
            field: 'sourceId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '机构名称',
            field: 'qhzdName',
            align: 'center',
            valign: 'middle'
        }, {
            title: '涉嫌机构id',
            field: 'qhzdId',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            title: '扣分',
            field: 'score',
            align: 'center',
            valign: 'middle'
        }, {
            title: '考核月份',
            field: 'khyf',
            align: 'center',
            valign: 'middle'
        }, {
            title: '审核状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var result = "";
                switch (value) {
                    case '0':
                        result = "通过";
                        break;
                    case '1':
                        result = "不通过";
                        break;
                    case '2':
                        result = "未审核";
                        break;
                    default:
                        result = "- -"
                }
                return result;
            }
        }, {
            title: '情况描述',
            field: 'descript',
            align: 'center',
            valign: 'middle'
        }, {
            title: '批注',
            field: 'comment',
            align: 'center',
            valign: 'middle',
            visible: false
        }],
    saveJdjcInfo: function () {// TODO
        var _this = this;
        var result = _this.checkDcdbDate();
        if (result) {
            var options = {
                url: url_prefix + '/jxkh/saveOrUpdateJdjcKh.action',
                dataType: 'json',
                success: function (responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        layerAlert('保存成功！')
                        $(".modal").modal('hide');
                        _this.refreshTableByParams(1);
                    } else {
                        layerAlert('保存失败！')
                    }
                }
            };
            $("#addPostForm_xcbd").ajaxForm(options);
            $('#addPostForm_xcbd').submit();
        }
    },
    delJdjcInfo: function (id) {
        var _this = this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            layerAlert("请选择要删除的记录！");
            return false;
        } else {
            var ids = "";
            $.each(selectedRow, function (idx, obj) {
                ids += obj.id + ",";
            });
            if (ids.length > 0) {
                $.ajax({
                    type: "GET",
                    url: url_prefix + '/jxkh/deleteJdjcKh.action',
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if (data.success == true) {
                            layerAlert('删除成功！')
                            _this.refreshTableByParams(1);
                        } else {
                            layerAlert('删除失败！')
                        }
                    }
                });
            }
        }
    },
    refreshTableByParams: function (pageNum) {
        var _this = this;
        // initUserGroup($("#qhzdDm"),"2,5")
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        var url = url_prefix + '/jxkh/findTissueJdjcKh';
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: _this.columns,
            url: url,
            pageNumber: pageNum || pageNumber,
            queryParams: function (params) {
                return {
                    rows: options.pageSize, //页面大小
                    page: pageNum || (params.offset / params.limit + 1), //页码,
                    month: $('#month_jdjc').val()
                }
            },
            responseHandler: function (res) {
                pageNum = "";
                return JSON.parse(res);
            }
        });
    },
    //页面提交校验 TODO
    checkDcdbDate: function () {
        if ($("#qhzdDm").val() == "") {
            layerAlert("机构名称不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='khyf']").val() == "") {
            layerAlert("考核月份不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countrywlmt']").val() == "") {
            layerAlert("国家级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='countryfwlmt']").val() == "") {
            layerAlert("国家级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincewlmt']").val() == "") {
            layerAlert("省级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='provincefwlmt']").val() == "") {
            layerAlert("省级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='citywlmt']").val() == "") {
            layerAlert("市级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='cityfwlmt']").val() == "") {
            layerAlert("市级非网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtwlmt']").val() == "") {
            layerAlert("区级网络媒体不能为空!");
            return false;
        }
        if ($("#myXcbdModal input[name='districtfwlmt']").val() == "") {
            layerAlert("区级非网络媒体不能为空!");
            return false;
        }

        return true;
    },
    page_suffix: 'jdjccl',
    showIssueDealPage: function (row) {
        console.log(row);
        var _this = this;
        //下面的页面展示
        // ③巡查机制的表单信息，直接通过row获取即可
        // ①案件显示: 根据普通案件编号异步查询 赋值给 vue的 myapp.ajDetail
        // ②流程进度显示: 根据案件编号获取 流程id，获取历史任务信息 赋值给 myapp.speedOfProgress
        var issueNumber = row.issueNum;//案件编号 根据案件编号查询案件 赋值给 vue的 myapp.ajDetail
        jdjc_comment = row.comment; //巡查机制id
        jdjc_id = row.id; //巡查机制id
        var processId = "";//流程id

        $.ajax({
            method: 'post',
            url: url_prefix + query_issue_by_issuenum,
            dataType: 'json',
            data: {
                issueNumber: issueNumber
            },
            success: function (data) {
                data = (typeof data == "object") ? data : $.parseJSON(data);
                myapp.ajDetail = data.tMainIssueBean;
                processId = myapp.ajDetail.processId;
                console.log(myapp.ajDetail);
                $("#myModal3").modal({backdrop: 'static'}).show();
                $('#dbrw-cl3').load(url_prefix + '/page/sub/' + _this.page_suffix + '.jsp', function () {
                })
            }
        }).then(function () {
            console.log(processId);
            //流程进度
            myapp.$http.get(url_prefix + speedOfProgressByprocessId + processId).then(
                function (response) {
                    var data = JSON.parse(response.data);
                    myapp.speedOfProgress = data.rows;
                    console.log(myapp.speedOfProgress);
                    var startTime = new Date(myapp.speedOfProgress[0].startTime);
                    var endTime;
                    if (myapp.speedOfProgress[myapp.speedOfProgress.length - 1].endTime) {
                        endTime = new Date(myapp.speedOfProgress[myapp.speedOfProgress.length - 1].endTime);
                    }
                    else endTime = new Date();
                    myapp.timeTaken = endTime - startTime;
                    Vue.nextTick(function () {
                        $('.dec').each(function (index, dom) {
                            var shart = setInterval(function () {
                                if ($('.shuju').height() > 0) {
                                    //console.log($(dom).parent().children('.text').innerHeight(),$(dom).parent().parent().innerHeight());
                                    $(dom).height($(dom).parent().parent().innerHeight() - $(dom).parent().children('.text').innerHeight() + 'px');
                                    $(dom).css('bottom', '-' + $(dom).css('height'));
                                    clearInterval(shart);
                                }
                            }, 50)
                        })
                    })
                });
            //批注信息
            $.ajax({
                method: 'get',
                url: url_prefix + commentDetailByprocessId + processId,
                dataType: 'json',
                success: function (data) {
                    data = (typeof data == "object") ? data : $.parseJSON(data)
                    myapp.commentDetail = data.rows;
                    //初始化提示框
                    $('[data-toggle="tooltip"]').tooltip();
                }
            });
        });
    },
    name: '监督检查',
    init: function () {
        var _this = this;
        $("#" + boot_table_id).on("click-cell.bs.table", function (event, column_name, value, row) {
            if (column_name == 'issueNum') {
                _this.showIssueDealPage(row);
            }

        });
    }
};

jdjc_obj.init();

//==============================================  监督检查 end ===============================================


function initDataTable(height) {
    var $table = $("#" + boot_table_id);
    $table.bootstrapTable({
        height: (height),
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination: true,
        striped: true, // 是否显示行间隔色
        columns: columns,
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        dataType: 'json',
        url: url_prefix + issueRadio_page_url,
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                s_name: ""
            }
        },
        responseHandler: function (res) {
            return JSON.parse(res);
        }
    });

};

function appendLiHeaderTag() {
    var temp_item = null;
    for (var i = todo_flags.length - 1; i >= 0; i--) {
        itemFlag = todo_flags[i];
        user_group_type = 3
        if (itemFlag.id == user_group_type) {
            temp_item = itemFlag;
            var img_index = 0;
            var img_counts = LiHeaderTagMap.length;
            var ulObj = $("#ul-header-tag");
            ulObj.empty();
            for (var j = temp_item.flags.length - 1; j >= 0; j--) {
                flag = temp_item.flags[j];
                getLiHeaderTag(flag, '--', LiHeaderTagMap[img_index].imgUrl, j);
                img_index = (img_index + 1) % img_counts;
            }
            ;
        }
        ;
        var colorArr = ['e5bb31', "2dd896", 'df7c3b', '3b97e0', 'e5bb31',
            '2dd896']
        $('.funShow li>a').each(function (index, item) {
            $(item).css('background-color', '#' + colorArr[index]);
        });
    }
    ;
};


// 初始化VUE
var myappDb = new Vue({
    el: '.dbwrap',
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
        uploadSuccessHandler(response, file, list) {
            file.name = response.name;
            file.url = response.url;
            this.uploadFileList = list;
        },
        rejectQwgIssue() {
            this.loading = true;
            var self = this;
            var files = [];
            for (var i = 0; i < this.uploadFileList.length; i++) {
                files.push(this.uploadFileList[i].url);
            }
            $.ajax({
                method: 'post',
                url: url_prefix + qwgReject,
                dataType: 'json',
                data: {
                    orderNo: self.qwgDetail.orderno,
                    comments: $("#qwg-comments").val(),
                    files: files.join(",")
                },
                success: function (data) {
                    self.loading = false;
                    showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']退回成功!', function () {
                        $(".modal").modal('hide');
                    });
                    refreshDataTableForQwg();
                    $('#myModal_2').modal('hide');
                }
            })
        },
        completeQwgIssue() {
            var self = this;
            var files = [];
            for (var i = 0; i < this.uploadFileList.length; i++) {
                files.push(this.uploadFileList[i].url);
            }
            this.loading = true;
            $.ajax({
                method: 'post',
                url: url_prefix + qwgComplete,
                dataType: 'json',
                data: {
                    orderNo: self.qwgDetail.orderno,
                    comments: $("#qwg-comments").val(),
                    files: files.join(",")
                },
                success: function (data) {
                    self.loading = false;
                    if (data.errMsg) {
                        showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']处理失败!', function () {
                            $(".modal").modal('hide');
                        });
                    } else {
                        showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']处理成功!', function () {
                            $(".modal").modal('hide');
                        });
                        refreshDataTableForQwg();
                        $('#myModal_2').modal('hide');
                    }
                }
            })
        }
    }

});

//巡查机制和督办督查页面弹出展示用
var myapp = new Vue({
    el: '.dbwrap2',
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
        uploadSuccessHandler(response, file, list) {
            file.name = response.name;
            file.url = response.url;
            this.uploadFileList = list;
        },
        rejectQwgIssue() {
            this.loading = true;
            var self = this;
            var files = [];
            for (var i = 0; i < this.uploadFileList.length; i++) {
                files.push(this.uploadFileList[i].url);
            }
            $.ajax({
                method: 'post',
                url: url_prefix + qwgReject,
                dataType: 'json',
                data: {
                    orderNo: self.qwgDetail.orderno,
                    comments: $("#qwg-comments").val(),
                    files: files.join(",")
                },
                success: function (data) {
                    self.loading = false;
                    showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']退回成功!', function () {
                        $(".modal").modal('hide');
                    });
                    refreshDataTableForQwg();
                    $('#myModal_2').modal('hide');
                }
            })
        },
        completeQwgIssue() {
            var self = this;
            var files = [];
            for (var i = 0; i < this.uploadFileList.length; i++) {
                files.push(this.uploadFileList[i].url);
            }
            this.loading = true;
            $.ajax({
                method: 'post',
                url: url_prefix + qwgComplete,
                dataType: 'json',
                data: {
                    orderNo: self.qwgDetail.orderno,
                    comments: $("#qwg-comments").val(),
                    files: files.join(",")
                },
                success: function (data) {
                    self.loading = false;
                    if (data.errMsg) {
                        showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']处理失败!', function () {
                            $(".modal").modal('hide');
                        });
                    } else {
                        showDialog('提示', '案件[' + myapp.qwgDetail.orderno + ']处理成功!', function () {
                            $(".modal").modal('hide');
                        });
                        refreshDataTableForQwg();
                        $('#myModal_2').modal('hide');
                    }
                }
            })
        }
    }

});
// 初始化VUE
var myappWin = new Vue({
    el: '#myDcdbModal2',
    data: {
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
        ajDetail: {},
        commentDetail: [],
        speedOfProgress: [],
        timeTaken: 0,
        qwgDetail: []
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

$(function () {
    appendLiHeaderTag();

    var height = $('.content').height() - $('.funShow').height();

    if (document.body.clientWidth < 800) {
        height = document.body.clientHeight - 200;
    }

    initDataTable(height);

    initDialog();

    $('#myModal_2 .close').click(function () {
        $('#myModal_2').modal('hide');
    });
});

