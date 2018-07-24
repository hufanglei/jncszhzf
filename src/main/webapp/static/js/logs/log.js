/**
 * 日志查询
 */
// 日志列表查询
var issue_page_url = "/logs/logPage.action";

var columns = [ {
    title : '日志类型',
    field : 'type',
    align : 'center',
    valign : 'middle'
}, {
    title : '日志创建时间',
    field : 'timeStamp',
    align : 'center',
    valign : 'middle',
    visible : false
}, {
    title : '用户ID',
    field : 'userId',
    align : 'center',
    valign : 'middle'
}, {
    title : '日志内容',
    field : 'data',
    align : 'center',
    valign : 'middle'
} ];

var boot_table_id = "boot-table-dbrw";

function initDataTable() {
    var $table = $("#" + boot_table_id);
    $table.bootstrapTable({
        height : ($('.content').height() - $('.funShow').height()),
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pagination : true,
        striped : true, // 是否显示行间隔色
        columns : columns,
        method : 'post',
        contentType : "application/x-www-form-urlencoded",
        dataType : 'json',
        url : url_prefix + issue_page_url,
        queryParams : function(params) {
            return {
                rows: params.limit, // 页面大小
                page: params.offset/params.limit + 1 // 页码
            }
        }
    });

    $table.on("click-cell.bs.table", function(event, column_name, value, row) {
       // showQwgDetails(row);
    });
};

function refreshDataTableByParams() {
    $("#" + boot_table_id).bootstrapTable('refreshOptions', {
        columns : columns,
        url : url_prefix + issue_page_url,
        queryParams : function(params){
            return {
                rows: params.limit, // 页面大小
                page: params.offset/params.limit + 1 // 页码
            }
        }
    });
};

// 显示全网格案件处理页面
function showQwgDetails(row){
    myapp.qwgDetail = row;
    $('#myModal').modal('show');
}

function tab_animate() {
    $(".tab-content>.tab-pane").addClass('fade');
    $(".tab-content>.tab-pane").eq(0).addClass('in');
    $(".tab-content .tab-pane .btn").attr("type","button");
}

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

$(function() {

    initDataTable();

    initDialog();

    $('#myModal.close').click(function(){
        $('#myModal').modal('hide');
    });

});