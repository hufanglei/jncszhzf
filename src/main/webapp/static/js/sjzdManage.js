/*
    Created by IntelliJ IDEA.
    User: guoguo
    Date: 2018/1/15
    Time: 10:47
*/
var oTable = null;
var groupId = "";
$(function () {
    //1.初始化Table
    oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

});

var columns = [{
    checkbox: true
}, {
    field: 'id',
    title: '主键id',
    visible: false
}, {
    field: 'powerCode',
    title: '权利编码',
    /* visible:false*/
}, {
    field: 'ssWeibanju',
    title: '所属部门',
    width: '90px',
}, {
    field: 'powerDesc',
    title: '检查事项名称',
}, {
    field: 'execLevel',
    title: '行驶层级',
}, {
    field: 'limitedWorkdays',
    title: '工作日时限',
}];
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: '../../frame/listAllPower', //请求后台的URL（*）
            method: 'post', //请求方式（*）
            /* toolbar: '#toolbuton', //工具按钮用哪个容器*/
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序S
            sortOrder: "asc", //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            contentType: "application/x-www-form-urlencoded",
            dataType: 'json',
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false, //是否显示所有的列
            showRefresh: false, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            singleSelect: true,
            height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID", //每一行的唯一标识，一般为主键列
            showToggle: false, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            columns: columns, /*[{
                checkbox: true
            }, {
                field: 'id',
                title: '主键id',
                visible: false
            }, {
                field: 'powerCode',
                title: '权利编码',
                /!* visible:false*!/
            }, {
                field: 'ssWeibanju',
                title: '类  别',
                width: '90px',
            }, {
                field: 'powerDesc',
                title: '检查事项名称',
            }, {
                field: 'execLevel',
                title: '行驶层级',
            }, {
                field: 'limitedWorkdays',
                title: '工作日时限',
            }]*/
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit, //页面大小
            page: params.offset / params.limit + 1,//页码
            ssWeibanju: params.ssWeibanju
        };
        return temp;
    };

    oTableInit.refresh = function () {
        $("#table").bootstrapTable('refresh', {
            url: '../../frame/listAllPower'
        });
    }
    return oTableInit;
};
var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};
    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

function refreshData() {
    $("#table").bootstrapTable('refreshOptions', {
        columns: columns,
        url: '../../frame/listAllPower',
        pageNumber: 1,
        queryParams: function (params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1,
                ssWeibanju: $('#ssbm').val()
            }
        }
    });
};

/**
 * 添加
 */
function addList() {
    var options = {
        success: addCallBack,
        dataType: 'json',
        url: '../../frame/addPower.action'
    };

    $("#addPostForm").ajaxForm(options);
    $('#addPostForm').submit();
};

function addCallBack(result, statusText) {
    if (result == 1) {
        showDialog('提示', '保存成功！', callBack);
        $("#addPostForm")[0].reset();
        oTable.refresh();
    } else {
        showDialog('提示', '保存失败！', callBack);
    }
    ;

    function callBack() {
        $(".modal").modal('hide');
        $("#MyDiv").hide();
        $("#fade").hide();
        // oTable.refresh();
        window.location.reload();
    }
}

/**
 * 编辑
 */
function updateList() {
    var options = {
        success: editCallBack,
        dataType: 'json',
        url: '../../frame/updatePower.action'
    };

    $("#editPostForm").ajaxForm(options);
    $('#editPostForm').submit();
};

function editCallBack(result, statusText) {
    if (result == 1) {
        showDialog('提示', '保存成功！', callBack);
        $("#editPostForm")[0].reset();
        oTable.refresh();
    } else {
        showDialog('提示', '保存失败！', callBack);
    }
    ;

    function callBack() {
        $(".modal").modal('hide');
        $("#EditDiv").hide();
        $("#EditFade").hide();
        $("#fade").hide();
        window.location.reload();
    }
}

//删除
function deleteUser() {
    var selectedRow = $('#table').bootstrapTable('getSelections', null);

    if (selectedRow == null || selectedRow.length == 0) {
        alert("请选择要删除的记录");
        return false;
    }
    var flag = confirm("确认删除这条记录？");
    if (flag == true) {
        $.ajax({
            method: 'post',
            url: '../../frame/deletePower.action',
            dataType: 'json',
            data: {
                id: selectedRow[0].id
            },
            success: function (result) {
                if (result > 0) {
                    alert("删除成功！");
                    oTable.refresh();
                } else {
                    alert("删除失败");
                    oTable.refresh();

                }
            }
        });
    }
}