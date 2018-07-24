/**
 * Created by Administrator on 2017/12/12 012.
 */
/**
 * 综合执法工作考核
 */
// 综合执法工作考核列表查询
var issue_page_url = "/jxkh/zhzfgzkhPage.action";

// 表格是否全网格案件数据
var isQWGing = false;

var columns = [ {
    title : '考核id',
    field : 'id',
    align : 'center',
    valign : 'middle',
    visible : false
}, {
    title : '机构编码',
    field : 'qhzdId',
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
    title : '自行处理',
    field : 'zxcl',
    align : 'center',
    valign : 'middle'
}, {
    title : '案件比重',
    field : 'ajbz',
    align : 'center',
    valign : 'middle'
}, {
    title : '按时处理',
    field : 'ascl',
    align : 'center',
    valign : 'middle'
}, {
    title : '快速处理',
    field : 'kscl',
    align : 'center',
    valign : 'middle'
}, {
    title : '考核月份',
    field : 'khyf',
    align : 'center',
    valign : 'middle'
}, {
    title : '考核时间',
    field : 'khsj',
    align : 'center',
    valign : 'middle'
} ];

var boot_table_id = "boot-table-dbrw";

// 更新全网格标签数字
function updateQwgCounts(counts){
    $("#tag-li-全网格 > i").html(counts);
}

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
        queryParams : function(params){
            return {
                rows: params.limit, //页面大小
                page: params.offset/params.limit + 1, //页码,
                khyf:$('#khyf').val(),
                qhzdName:$('#qhzdName').val()
            }
        }
    });

    $table.on("click-cell.bs.table", function(event, column_name, value, row) {
        showQwgDetails(row);
    });

    $table.on("load-success.bs.table", function(event, data) {
        if (isQWGing){
            var options = $("#" + boot_table_id).bootstrapTable('getOptions');
            updateQwgCounts(options.totalRows);
        }
    });
};

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
                qhzdName:$('#qhzdName').val()
            }
        }
    });
};

// 显示全网格案件处理页面
function showQwgDetails(row){
    myapp.qwgDetail = row;
    // myapp.uploadFileList = [];
    $('#myModal').modal('show');
}

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
});

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

    ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#deal-dept"),"id","wbjName",null);
    ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#issue-belong"),"id","wbjName",'局');

    initDialog();

    $('#myModal.close').click(function(){
        $('#myModal').modal('hide');
    });

});