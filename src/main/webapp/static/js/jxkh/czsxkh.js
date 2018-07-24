var issueRadio_page_url = "/jxkh/issueRadio.action";
var boot_table_id = "boot-table-dbrw";
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
var tagArr = ['', 'xcbd', '', 'dcdbTag', 'ajbz']

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
    id : "1",
    desc : "网格员",
    flags : [ {
        show_flag : "街道派遣",
        query_page_param : "案件录入",
        query_count_param : "subDistriceDistributeCount",
        query_flag : "街道派遣数量"
    }, {
        show_flag : "自处置",
        query_page_param : "自处置",
        query_count_param : "waitHandleSelfCount",
        query_flag : "自处置数量"
    }, {
        show_flag : "待评价",
        query_page_param : "评价",
        query_count_param : "waitEvalutateCount",
        query_flag : "待评价数量"
    } ]
}, {
    id : "2",
    desc : "街道中心",
    flags : [ {
        show_flag : "待派发",
        query_page_param : "街道派发",
        query_count_param : "subDistriceWaitDistributeCount",
        query_flag : "待派发数量"
    } ]
}, {
    id : "3",
    desc : "区级中心",
    flags : [
        {
            show_flag : "监督检查",
            query_page_param : "监督检查",
            query_count_param : "districeCenterWaitDistributeCount",
            query_flag : "待派发数量"
        },
        {
            show_flag : "宣传报道",
            query_page_param : "宣传报道",
            query_count_param : "districeCenterWaitDistributeCount",
            query_flag : "待派发数量"
        }, {
            show_flag : "巡查机制",
            query_page_param : "巡查机制",
            query_count_param : "districeCenterWaitDistributeCount",
            query_flag : "待派发数量"
        }, {
            show_flag : "督察督办",
            query_page_param : "督察督办",
            query_count_param : "districeCenterWaitModTimeLimitCount",
            query_flag : "待修改时限数量"
        }, {
            show_flag : "案件比重",
            query_page_param : "案件比重",
            query_count_param : "waitEvalutateCount",
            query_flag : "待评价数量"
        } ]
}, {
    id : "4",
    desc : "领导小组办公室，区主管领导，区分管领导",
    flags : [ {
        show_flag : "疑难问题待处理",
        query_page_param : "疑难案件处理",
        query_count_param : "districeCenterDifficultCaseWaitHandleCount",
        query_flag : "疑难问题待处理数量"
    } ]
}, {
    id : "5",
    desc : "委办局",
    flags : [ {
        show_flag : "待接收",
        query_page_param : "主管部门接收",
        query_count_param : "waitCompetentDepartmentReceiveCount",
        query_flag : "待主管部门接收数量"
    }, {
        show_flag : "待立案",
        query_page_param : "主管部门立案",
        query_count_param : "waitCompetentDepartmentFilingCount",
        query_flag : "待主管部门立案数量"
    }, {
        show_flag : "待处理",
        query_page_param : "主管部门处理",
        query_count_param : "waitCompetentDepartmentHandleCount",
        query_flag : "待主管部门处理数量"
    }, {
        show_flag : "待联办",
        query_page_param : "问题联办",
        query_count_param : "waitCompetentDepartmentCooperationCount",
        query_flag : "待主管部门联办数量"
    } ]
}, {
    id : "6",
    desc : "街道内设科室",
    flags : [ {
        show_flag : "待处理",
        query_page_param : "街道处理",
        query_count_param : "waitSubDistriceHandleCount",
        query_flag : "待处理任务数量"
    } ]
}, {
    id : "7",
    desc : "区级中心督察组",
    flags : [ {
        show_flag : "待线下督查",
        query_page_param : "线下督查",
        query_count_param : "waitDistriceCenterOfflineSuperVisorCount",
        query_flag : "待线下督查数量"
    } ]
}, {
    id : "8",
    desc : "街道督察组",
    flags : [ {
        show_flag : "待线下督查",
        query_page_param : "督察组线下督查",
        query_count_param : "waitSubDistriceSuperVisorCount",
        query_flag : "待线下督查数量"
    } ]
} ];

var columns = [
    {
    //     checkbox: true
    // },{
        title : '机构代码',
        field : 'qhzdId',
        align : 'center',
        valign : 'middle',
        visible : false
    }, {
        title : '机构名称',
        field : 'qhzdName',
        align : 'center',
        valign : 'middle',
        formatter : function(value, row, index) {
            return '<span class="link-col">' + value + '</span>'
        }
    }, {
        title : '发生案件数量',
        field : 'issuenum',
        align : 'center',
        valign : 'middle'
    }, {
        title : '12345月份平均工单量',
        field : 'avg12345num',
        align : 'center',
        valign : 'middle'
    }, {
        title : '统一系数',
        field : 'coefficient',
        align : 'center',
        valign : 'middle',
    }, {
        title : '案件比重',
        field : 'proportion',
        align : 'center',
        valign : 'middle',
    }, {
        title : '月份',
        field : 'khyf',
        align : 'center',
        valign : 'middle'
    }, {
        title : '得分',
        field : 'score',
        align : 'center',
        valign : 'middle'
    }];

/*
 * 获取 待办任务标签 html 元素 并 绑定事件
 */
function getLiHeaderTag(flag, tagIssueCount, imgUrl,index) {
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
        + "</a><i>" + tagIssueCount + "</i></li>";
    $("#ul-header-tag").append(itemTemplate);

    $("#" + itemId).click(function() {

        switchTag(index);//切换搜索栏
        switch(index){
            case 4:
                refreshDataTableByParams(flag.query_page_param);break;
            case 3:
                dcdb_obj.refreshDcDbTableByParams(flag.query_page_param); break;
            case 1:
                xcbd_obj.refreshTableByParams();break;
        }

        // alert(index+" = "+flag.query_page_param);


    });
};

//==============================================  案件比重 start ===============================================

var ajbz = {
    //显示设置弹窗
    ajbzset: function () {
        var khyf = $('#khyf_ajbz').val();
        if(khyf==""){
            alert("请选择考核月份!");
            return ;
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
    query: function () {
        $("#avg_12345num").val("");
        $("#coe_fficient").val("");
        $("#avg12345num").val("");
        $("#coefficient").val("");
        var khyf_ajbz = $("#khyf_ajbz").val();
        if (khyf_ajbz == null || khyf_ajbz == "") {
            showDialog('提示', '请选择考核月份！', function () {
                $(".modal").modal('hide');
            });
            return false;
        }

        //计算当前的 年和月
        var now = new Date();
        var thisYear = now.getFullYear();
        var thisMonth = now.getMonth() + 1;
        var queryAarr = khyf_ajbz.split('-');
        if (thisYear < queryAarr[0] || (thisYear == queryAarr[0] && thisMonth <= queryAarr[1])) {
            showDialog('提示', '请选择当前月份之前的月份！', function () {
                $(".modal").modal('hide');
            });
            return false;
        }
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: columns,
            url: url_prefix + issueRadio_page_url,
            responseHandler: function (res) {
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
            queryParams: {
                rows: $("#" + boot_table_id).bootstrapTable('getOptions').pageSize, //页面大小
                page: 1, //页码,
                khyf: khyf_ajbz, //考核月份,
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
                    showDialog('提示', '保存成功！', function(){
                        $(".modal").modal('hide');
                    });
                } else {
                    showDialog('提示', '保存失败！', hideDialog);
                }
                //window.location.href = window.location.href;
            }
        });

    },
    checkDate: function () {
        if ($('#khyf_ajbz').val() == "") {
            alert("请选择考核月份!");
            return false;
        }

        if ($('#avg_12345num').val() == "" || $("#coe_fficient").val()== "") {
            alert("请设置保存参数!");
            return false;
        }
        return ;

    }
};


//==============================================  案件比重 end =================================================


//==============================================  督查督办 start ===============================================

var dcdb_obj = {
    issueDbdc_page_url:"/jxkh/listDcdbKh.action",
    columns_dcdb:[
        {
            checkbox: true
        },{
            title : '机构代码',
            field : 'qhzdId',
            align : 'center',
            valign : 'middle'
        }, {
            title : '机构名称',
            field : 'qhzdName',
            align : 'center',
            valign : 'middle',
            formatter : function(value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title : '督查督办编号',
            field : 'dcdbNum',
            align : 'center',
            valign : 'middle'
        }, {
            title : '案件编号',
            field : 'issuenum',
            align : 'center',
            valign : 'middle'
        }, {
            title : '类型',
            field : 'typeId',
            align : 'center',
            valign : 'middle',
            visible : false
        }, {
            title : '类型',
            field : 'typeName',
            align : 'center',
            valign : 'middle'
        }, {
            title : '扣分',
            field : 'score',
            align : 'center',
            valign : 'middle',
        }, {
            title : '&nbsp;&nbsp;&nbsp;&nbsp;日期&nbsp;&nbsp;&nbsp;&nbsp;',
            field : 'khyf',
            align : 'center',
            valign : 'middle'
        }],
    scoreType : {
        "反复投诉":"<option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option>",
        "工作协调":"<option value='2'>2</option><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option><option value='8'>8</option><option value='9'>9</option><option value='10'>10</option>",
        "受理交办":"<option value='1'>1</option><option value='2'>2</option>"
    },
    saveDcdbInfo:function() {
        var _this=this;
        var result = _this.checkDcdbDate();
        if(result){
            var options = {
                url : url_prefix + '/jxkh/saveDcdbInfo.action',
                dataType : 'json',
                success : function(responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        showDialog('提示', '保存成功！', function(){
                            $(".modal").modal('hide');
                        });
                        _this.refreshDcDbTableByParams();
                    } else {
                        showDialog('提示', '保存失败！', hideDialog);
                    }
                }
            };

            $("#addPostForm_dcdb").ajaxForm(options);
            $('#addPostForm_dcdb').submit();
        }
    },
    //查询
    refreshDcDbTableByParams:function(query_page_param) {
        var _this=this;
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns : _this.columns_dcdb,
            url : url_prefix + _this.issueDbdc_page_url,
            responseHandler:function (res) {
                return JSON.parse(res);
            },
            queryParams : {
                rows: $("#" + boot_table_id).bootstrapTable('getOptions').pageSize, //页面大小
                page: 1, //页码,
                khyf: $("#dcdb_khyf").val(), //考核月份,
                jgmc: $("#dcdb_jgmc").val() //页码
                // s_name : query_page_param
            }
        });
    },
    // 显示全网格案件处理页面
    showDcDbDetails:function(type){
        var _this=this;
        initUserGroup($("#qhzdGroup"),"2,5")
        initAddOptions($("#violationtype"),url_prefix +  '/qhzd/listDictionary.aciton',"violationtype");
        initAddOptions($("#issueSource"),url_prefix +  '/qhzd/listDictionary.aciton',"ajly");
        setTimeout(function(){
            if(type == "update") {
                var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
                if (selectedRow == null || selectedRow.length != 1) {
                    alert("请选择一条修改的记录！");
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
            }else{
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
        },300)

    },
    //删除督察督办信息
    delDcdbInfo:function(){
        var _this=this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            alert("请选择要删除的记录！");
            return false;
        } else {
            var ids = "";
            $.each(selectedRow, function(idx, obj) {
                ids+=obj.id+",";
            });
            if(ids.length>0) {
                $.ajax({
                    type: "GET",
                    url: url_prefix + '/jxkh/delDcdbInfo.action',
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if(data.success==true){
                            showDialog('提示', '删除成功！', hideDialog);
                            _this.refreshDcDbTableByParams();
                        }else{
                            showDialog('提示', '删除失败！', hideDialog);
                        }
                    }
                });
            }
        }
    },
    //初始化扣分
    initDeductScore:function(type) {
        var _this=this;
        $("#deductScore").empty();
        var htmlStr = "<option value = '' > 请选择 </option>";
        if (type != "" && type!="请选择") {
            htmlStr += eval("_this.scoreType." + type);
        }
        $("#deductScore").append( htmlStr );
    },
    //页面提交校验
    checkDcdbDate:function(){
            if($("#myDcdbModal input[name='issuenum']").val()==""){
                alert("案件编号不能为空!");
                return false;
            }
            if($("#myDcdbModal input[name='khyf']").val()==""){
                alert("考核月份不能为空!");
                return false;
            }
            if($("#myDcdbModal input[name='sourceName']").val()==""){
                alert("信息来源不能为空!");
                return false;
            }
            if($("#myDcdbModal input[name='qhzdName']").val()==""){
                alert("涉嫌机构不能为空!");
                return false;
            }
            if($("#myDcdbModal input[name='typeName']").val()==""){
                alert("涉嫌违规类型不能为空!");
                return false;
            }
            if($("#deductScore").val()==""){
                alert("扣分不能为空!");
                return false;
            }
            // if($("#myDcdbModal textarea[name='descript']").val()==""){
            //     alert("案件信息不能为空!");
            //     return false;
            // }
            // if($("#myDcdbModal textarea[name='info']").val()==""){
            //     alert("情况描述不能为空!");
            //     return false;
            // }

            return true;
        },
    init:function(){
        var _this=this;
        //涉嫌违规类型
        $("#violationtype").change(function(){
            var violationtype = $.trim($("#violationtype").find("option:selected").text());
            _this.initDeductScore(violationtype);
            $("#dcdb_typeName").val(violationtype)
        });

        //涉嫌机构
        $("#qhzdGroup").change(function(){
            $("#dcdb_qhzdName").val($.trim($("#qhzdGroup").find("option:selected").text()))
        });

        //信息来源
        $("#issueSource").change(function(){
            $("#dcdb_sourceName").val($.trim($("#issueSource").find("option:selected").text()))
        });

        //扣分
        $("#deductScore").click(function(){
            var violationtype = $.trim($("#violationtype").find("option:selected").text());
            if(violationtype=="" || violationtype=="请选择"){
                alert("前先选择涉嫌违规类型！")
                return ;
            }
        });


        $('#myDcdbModal.close').click(function(){
            $('#myDcdbModal').modal('hide');
        });
    }
}
dcdb_obj.init();
//==============================================  督查督办 end ===============================================

//==============================================  宣传报道 start ===============================================
var xcbd_obj = {
    columns : [
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
            valign: 'middle',
            formatter: function (value, row, index) {
                return '<span class="link-col">' + value + '</span>'
            }
        }, {
            title: '国家级',
            align: 'center',
            valign: 'middle',
            formatter:function(value, row, index, field) {
                return row.countrywlmt+row.countryfwlmt;
            }
        }, {
            title: '省级',
            align: 'center',
            valign: 'middle',
            formatter:function(value, row, index, field) {
                return row.provincewlmt+row.provincefwlmt;
            }
        }, {
            title: '市级',
            align: 'center',
            valign: 'middle',
            formatter:function(value, row, index, field) {
                return row.citywlmt+row.cityfwlmt;
            }
        }, {
            title: '区级',
            align: 'center',
            valign: 'middle',
            formatter:function(value, row, index, field) {
                return row.districtwlmt+row.districtfwlmt;
            }
        }, {
            title: '总分',
            field: 'score',
            align: 'center',
            valign: 'middle'
        }],
    // 显示全网格案件处理页面
    showXcbdDetails:function(type){
        var _this=this;
        if(type == "update") {
            var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
            if (selectedRow == null || selectedRow.length != 1) {
                alert("请选择一条修改的记录！");
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
        }else{
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
    saveXcbdInfo:function() {
        var _this=this;
        var result = _this.checkDcdbDate();
        if(result) {
            var options = {
                url: url_prefix + '/jxkh/saveOrUpdateXcbdKh.action',
                dataType: 'json',
                success: function (responseObject, statusText) {
                    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                        showDialog('提示', '保存成功！', function () {
                            $(".modal").modal('hide');
                        });
                        _this.refreshTableByParams();
                    } else {
                        showDialog('提示', '保存失败！', hideDialog);
                    }
                }
            };
            $("#addPostForm_xcbd").ajaxForm(options);
            $('#addPostForm_xcbd').submit();
        }
    },
    delXcbdInfo:function(id){
        var _this=this;
        var selectedRow = $('#boot-table-dbrw').bootstrapTable('getSelections', null);
        if (selectedRow == null || selectedRow.length == 0) {
            alert("请选择要删除的记录！");
            return false;
        } else {
            var ids = "";
            $.each(selectedRow, function(idx, obj) {
                ids+=obj.id+",";
            });
            if(ids.length>0) {
                $.ajax({
                    type: "GET",
                    url: url_prefix + '/jxkh/deleteXcbdKh.action',
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if(data.success==true){
                            showDialog('提示', '删除成功！', hideDialog);
                            _this.refreshTableByParams();
                        }else{
                            showDialog('提示', '删除失败！', hideDialog);
                        }
                    }
                });
            }
        }
    },
    refreshTableByParams: function () {
        var _this = this;
        initUserGroup($("#qhzdDm"),"2,5")
        var options = $("#" + boot_table_id).bootstrapTable('getOptions');

        var url = url_prefix + '/jxkh/findTissueXcbdKh';
        $("#" + boot_table_id).bootstrapTable('refreshOptions', {
            columns: _this.columns,
            url: url,
            queryParams: {
                rows: options.pageSize, //页面大小
                page: 1, //页码,
                month: $('#month').val()
            },
            responseHandler:function (res) {
                return JSON.parse(res);
            }
        });
    },
    //页面提交校验
    checkDcdbDate:function(){
        if($("#qhzdDm").val()==""){
            alert("机构名称不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='khyf']").val()==""){
            alert("考核月份不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='countrywlmt']").val()==""){
            alert("国家级网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='countryfwlmt']").val()==""){
            alert("国家级非网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='provincewlmt']").val()==""){
            alert("省级网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='provincefwlmt']").val()==""){
            alert("省级非网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='citywlmt']").val()==""){
            alert("市级网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='cityfwlmt']").val()==""){
            alert("市级非网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='districtwlmt']").val()==""){
            alert("区级网络媒体不能为空!");
            return false;
        }
        if($("#myXcbdModal input[name='districtfwlmt']").val()==""){
            alert("区级非网络媒体不能为空!");
            return false;
        }

        return true;
    },
    init:function(){
        //机构名称
        $("#qhzdDm").change(function(){
            $("#qhzdMc").val($.trim($("#qhzdDm").find("option:selected").text()))
        });
    }
};

xcbd_obj.init();
//==============================================  宣传报道 end ===============================================

function refreshDataTableByParams(query_page_param) {
    var options = $("#" + boot_table_id).bootstrapTable('getOptions');

    $("#" + boot_table_id).bootstrapTable('refreshOptions', {
        columns : columns,
        url : url_prefix + issueRadio_page_url,
        queryParams : {
            rows: options.pageSize, //页面大小
            page: 1, //页码,
            s_name : query_page_param
        },
        responseHandler:function (res) {
            return JSON.parse(res);
        }
    });
};

function initDataTable(height) {
    var $table = $("#" + boot_table_id);
    $table.bootstrapTable({
        height : (height),
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination : true,
        striped : true, // 是否显示行间隔色
        columns : columns,
        method : 'post',
        contentType : "application/x-www-form-urlencoded",
        dataType : 'json',
        url : url_prefix + issueRadio_page_url,
        queryParams : function(params){
            return {
                rows: params.limit, //页面大小
                page: params.offset/params.limit + 1, //页码,
                s_name : ""
            }
        },
        responseHandler:function (res) {
            return JSON.parse(res);
        }
    });

};

function appendLiHeaderTag() {
    var temp_item = null;
    for (var i = todo_flags.length - 1; i >= 0; i--) {
        itemFlag = todo_flags[i];
        user_group_type=3
        if (itemFlag.id == user_group_type) {
            temp_item = itemFlag;
            var img_index = 0;
            var img_counts = LiHeaderTagMap.length;
            var ulObj = $("#ul-header-tag");
            ulObj.empty();
            for (var j = temp_item.flags.length - 1; j >= 0; j--) {
                flag = temp_item.flags[j];
                getLiHeaderTag(flag, '--', LiHeaderTagMap[img_index].imgUrl,j);
                img_index = (img_index + 1) % img_counts;
            };
        };
        var colorArr = [ 'e5bb31', "2dd896", 'df7c3b', '3b97e0', 'e5bb31',
            '2dd896' ]
        $('.funShow li>a').each(function(index, item) {
            $(item).css('background-color', '#' + colorArr[index]);
        });
    };
};


$(function() {
    appendLiHeaderTag();

    var height = $('.content').height() - $('.funShow').height();

    if(document.body.clientWidth < 800){
        height = document.body.clientHeight - 200;
    }

    initDataTable(height);

    initDialog();

    $('#myModal_2 .close').click(function(){
        $('#myModal_2').modal('hide');
    });
});

