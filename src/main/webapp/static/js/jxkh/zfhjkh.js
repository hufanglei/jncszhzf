

var issueRadio_page_url = "/jxkh/issueRadio.action";

/**
 * Li  标签块映射
 */
var LiHeaderTagMap = [
    {
    imgUrl : "dla.svg"
}, {
    imgUrl : "bmdqs.svg"
}, {
    imgUrl : "dcf.svg"
}, {
    imgUrl : "dja.svg"
} ];

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
        checkbox: true
    },{
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

var boot_table_id = "boot-table-dbrw";

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
        //alert(index);
        if(index==4){
            refreshDataTableByParams(flag.query_page_param);
            return false;
        }

    });
};

function refreshDataTableByParams(query_page_param) {
    isQWGing = false;
    var options = $("#" + boot_table_id).bootstrapTable('getOptions');

    $("#" + boot_table_id).bootstrapTable('refreshOptions', {
        columns : columns,
        url : url_prefix + issueRadio_page_url,
        queryParams : {
            rows: options.pageSize, //页面大小
            page: 1, //页码,
            s_name : query_page_param
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
        }
    });

};

function appendLiHeaderTag() {
    var temp_item = null;
    for (var i = todo_flags.length - 1; i >= 0; i--) {
        itemFlag = todo_flags[i];
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

