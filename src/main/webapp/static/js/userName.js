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

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: '../../users/table', //请求后台的URL（*）
            method: 'post', //请求方式（*）
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false, //是否显示所有的列
            showRefresh: true, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            singleSelect: true,
            height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID", //每一行的唯一标识，一般为主键列
            showToggle: false, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            formatLoadingMessage: function () {
                return "正在努力地加载数据中，请稍候...";
            },
            formatNoMatches: function () {  //没有匹配的结果
                return '无符合条件的记录';
            },
            responseHandler: function (ds) {
                return ds.result;
            },
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: '登陆名称'
            }, {
                field: 'userName',
                title: '姓名'
            }, {
                field: 'tel',
                title: '电话'
            }, {
                field: 'email',
                title: '邮箱'
            }, {
                field: 'orgNumber',
                title: '行政区划编码',
                visible:false
            }, {
                field: 'userTypeID',
                title: '角色类型',
                formatter: function (value, row, index) {
                    switch (value){
                        case 0:
                            return "系统管理员";
                        case 1:
                            return "社区网格员";
                        case 2:
                            return "街道中心";
                        case 3:
                            return "区级中心";
                        case 4:
                            return "区级领导";
                        case 5:
                            return "委办局";
                        case 6:
                            return "街道科室";
                        case 7:
                            return "区级中心督察组";
                        case 8:
                            return "执法中队";
                        case 9:
                            return "考核小组";
                    }
                }
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            userId: userId,
            groupId: groupId,
            paginationParam: {
                page: params.offset / params.limit, //页码
                pageSize: params.limit   //页面大小
            }
        };
        return temp;
    };

    oTableInit.refresh = function () {
        $("#table").bootstrapTable('refresh', {
            url: '../../users/table'
        });
    }

    oTableInit.refreshByGrouptag = function (groupTag) {
        groupId = groupTag;
        $("#table").bootstrapTable('refresh', {
            url: '../../users/table',
            query: {
                userId: userId,
                groupId: groupTag
            }
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


//树结构
function createTree(jsons, pid) {
    if (jsons != null) {
        var node = null;
        for (var i = 0; i < jsons.length; i++) {
            if (jsons[i].pid == pid) {
                var map = {text: jsons[i].groupName, attrs: jsons[i], nodes: createTree(jsons, jsons[i].id)};
                if (map.nodes == null && pid == 0) {
                    map.nodes = [];
                }
                if (node == null) node = [];
                node.push(map);
            }
        }
    }
    return node;
}

$(function () {
    var defaultData;
    $.ajax({
        method: 'post',
        url: '../../groups2tree',
        data:{
            usrId:userId
        },
        dataType: 'json',
        success: function (results) {
            defaultData = createTree(results.result, 0);

            $('#treeview').treeview({
                color: "#428bca",
                expandIcon: 'glyphicon glyphicon-chevron-right',
                collapseIcon: 'glyphicon glyphicon-chevron-down',
                data: defaultData,
                onNodeSelected: function (event, data) {
                    console.log(JSON.stringify(data));
                    if (data.nodes == null || data.nodes.length == 0) {
                        oTable.refreshByGrouptag(data.attrs.groupId);
                    }
                    if (data.nodes != null) {
                        var select_node = $('#treeview').treeview('getSelected', null);
                        if (select_node[0].state.expanded) {
                            $('#treeview').treeview('collapseNode', select_node);
//    			            select_node[0].state.selected=false;
                        }
                        else {
                            $('#treeview').treeview('expandNode', select_node);
//    			            select_node[0].state.selected=false;
                        }
                    }
                    // if(data.attrs.pid==0){
                    // 	oTable.refreshByGrouptag(data.attrs.groupId);
                    // }
                }
            });
            $('#treeview').treeview('collapseAll', {silent: true});
        }
    });
});


//新增、修改、删除
function addUser() {
    var selectedNode = $('#treeview').treeview('getSelected', null);
    if (selectedNode == null || selectedNode.length == 0 || (selectedNode[0].nodes != null && selectedNode[0].nodes.length != 0)) {
        layer.msg("请选择表单左侧具体的用户组");
        return false;
    }
    $("#userTypeAdd").val(selectedNode[0].attrs.roleId);
    $("#addMemGid").val(selectedNode[0].attrs.groupId);

    var options = {
        beforeSubmit: beforePostForm_Add,
        success: addCallBack,
        dataType: 'json',
        url: '../../user/save'
    };
    if (!options.beforeSubmit()) {
        layer.msg("用户名已存在");
        return false;
    }

    $("#addPostForm").ajaxForm(options);
    $('#addPostForm').submit();
}


function beforePostForm_Add() {
    var addResult = true;

    //	判断用户名是否已经存在
    $.ajax({
        url: '../../user/exists',
        dataType: 'json',
        data: {
            userId: $("#addPostForm input[name='user.id']").val()
        },
        async: false,
        success: function (ds) {
            if (ds.respCode == 200 && ds.result.oper) {
                addResult = false;
            }
        }
    });

    return addResult;
}

function addCallBack(responseObject, statusText) {
    if (responseObject.respCode == 200 && responseObject.result.oper) {
        showDialog('提示', '保存成功！', callBack);
        $("#addPostForm")[0].reset();
        var selectedNode = $('#treeview').treeview('getSelected', null);
        if (selectedNode == null || selectedNode.length == 0 || (selectedNode[0].nodes != null && selectedNode[0].nodes.length != 0)) {
            oTable.refresh();
        } else {
            oTable.refreshByGrouptag(selectedNode[0].attrs.groupId);
        }
    } else {
        showDialog('提示', '保存失败！', callBack);
    }

    function callBack() {
        $(".modal").modal('hide');
        $("#MyDiv").hide();
        $("#fade").hide();
    }
}


function updateUser() {
    var options = {
        beforeSubmit: beforePostForm_edit,
        success: editCallBack,
        dataType: 'json',
        url: '../../user/update'
    };

    $("#editPostForm").ajaxForm(options);
    $('#editPostForm').submit();
}

function beforePostForm_edit() {
    return true;
}

function editCallBack(responseObject, statusText) {
    if (responseObject.respCode == 200 && responseObject.result.oper) {
        showDialog('提示', '保存成功！', callBack);
        $("#editPostForm")[0].reset();
        var selectedNode = $('#treeview').treeview('getSelected', null);
        if (selectedNode == null || selectedNode.length == 0 || (selectedNode[0].nodes != null && selectedNode[0].nodes.length != 0)) {
            oTable.refresh();
        } else {
            oTable.refreshByGrouptag(selectedNode[0].attrs.groupId);
        }
    } else {
        showDialog('提示', '保存失败！', callBack);
    }

    function callBack() {
        $(".modal").modal('hide');
        $("#EditDiv").hide();
        $("#EditFade").hide();
        $("#fade").hide();
    }
}

function deleteUser() {
    var selectedRow = $('#table').bootstrapTable('getSelections', null);
    if (selectedRow == null || selectedRow.length == 0) {
        layer.msg("请选择要删除的记录");
        return false;
    }

    $.ajax({
        type: 'post',
        url: '../../users/' + selectedRow[0].id +'/deletion',
        dataType: 'json',
        success: function (ds) {
            if (ds.respCode == 200 && ds.result.oper) {
                layer.msg("删除成功");
                var selectedNode = $('#treeview').treeview('getSelected', null);
                if (selectedNode == null || selectedNode.length == 0 || (selectedNode[0].nodes != null && selectedNode[0].nodes.length != 0)) {
                    oTable.refresh();
                } else {
                    oTable.refreshByGrouptag(selectedNode[0].attrs.groupId);
                }
            } else {
                layer.msg("删除失败");
            }
        }
    });
}

function reSetpwd() {
    var selectedRow = $('#table').bootstrapTable('getSelections', null);
    if (selectedRow == null || selectedRow.length == 0) {
        layer.msg("请选择要重置密码的记录");
        return false;
    }

    $.ajax({
        type: 'post',
        url: '../../users/' + selectedRow[0].id + '/resetPwd',
        dataType: 'json',
        success: function (ds) {
            if (ds.respCode == 200 && ds.result.oper) {
                layer.msg("重置密码成功");
                var selectedNode = $('#treeview').treeview('getSelected', null);
                if (selectedNode == null || selectedNode.length == 0 || (selectedNode[0].nodes != null && selectedNode[0].nodes.length != 0)) {
                    oTable.refresh();
                } else {
                    oTable.refreshByGrouptag(selectedNode[0].attrs.groupId);
                }
            } else {
                layer.msg("重置密码失败");
            }
        }
    });
}