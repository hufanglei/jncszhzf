var oTable;
var groupTag = "";

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
            url: '../../groups/table',
            method: 'post',
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: 'limit',
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
            onClickRow: function (rows, tr) {
                // 进行你的操作，如弹出新窗口
            },
            columns: [{
                checkbox: true
            }, {
                field: 'groupId',
                title: '角色ID'
            }, {
                field: 'groupName',
                title: '角色名称'
            }, {
                field: 'roleTypeName',
                title: '角色类型'
            }, {
                field: 'groupTag',
                title: '角色类型ID',
                visible:false
            }, {
                field: 'groupTypeId',
                title: '用户组ID',
                visible: false
            }, {
                field: 'groupPid',
                title: '分组级别',
                formatter: function (value, row, index) {//赋予的参数
                    if(Boolean(value)){
                        return "二级";
                    }
                    return "一级";
                }
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            groupTag: groupTag,//角色类型
            paginationParam: {
                page: params.offset / params.limit, //页码
                pageSize: params.limit   //页面大小
            }
        };
        return temp;
    };

    oTableInit.refreshByGroupTag = function (groupTag) {
        $("#table").bootstrapTable('refresh', {
            url: '../../groups/table',
            query: {
                groupTag: groupTag
            }
        });
    }

    oTableInit.refresh = function () {
        $("#table").bootstrapTable('refresh', {
            url: '../../groups/table'
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
function createGroupTree(jsons) {
    if (jsons != null) {
        var node = null;
        for (var i = 0; i < jsons.length; i++) {
            var map = {text: jsons[i].dmms, attrs: jsons[i]};
            if (node == null) node = [];
            node.push(map);
        }
    }
    return node;
}

$(function () {
    var roles= {
        dmmc: 'rolename',
        sortParam: {
            sort: 'dmorder'
        }
    };
    $.ajax({
        dataType: 'json',
        type: 'POST',
        url: '../../zd/list',
        data: JSON.stringify(roles),
        contentType: "application/json",
        success: function (ds) {
            var data = ds.result;

            for (var i = 0; i < data.length; i++) {
                $("#addRoleId").append("<option value='" + data[i].dmbh + "'>" + data[i].dmms + "</option>");
                $("#editGroupTag").append("<option value='" + data[i].dmbh + "'>" + data[i].dmms + "</option>");
            }

            var defaultData = createGroupTree(data);
            $('#treeview').treeview({
                color: "#428bca",
                emptyIcon: '',    //没有子节点的节点图标
                expandIcon: 'glyphicon glyphicon-chevron-right',
                collapseIcon: 'glyphicon glyphicon-chevron-down',
                data: defaultData,
                onNodeSelected: function (event, data) {
                    console.log(JSON.stringify(data.attrs));
                     groupTag = data.attrs.dmbh;
                     oTable.refreshByGroupTag(data.attrs.dmbh);
                }
            });
            $('#treeview').treeview('collapseAll', {silent: true});
        }
    });
    var groups = {
        dmmc: 'groupname',
        sortParam: {
            sort: 'dmorder'
        }
    };
    $.ajax({
        method: 'post',
        url: '../../zd/list',
        dataType: 'json',
        data: JSON.stringify(groups),
        contentType: "application/json",
        success: function (ds) {
            var data = ds.result;
            for (var i = 0; i < data.length; i++) {
                $("#addGroupId").append("<option value='" + data[i].dmbh + "'>" + data[i].dmms + "</option>");
                $("#editGroupId").append("<option value='" + data[i].dmbh + "'>" + data[i].dmms + "</option>");
            }
        }
    });
    bindChange("addGroupId","addSubGroupId");
    bindChange("editGroupId", "editSubGroupId");
});

function bindChange(parentId, sonId) {
    var $parentDom = $("#" + parentId);
    $parentDom.bind("change", function () {
        var parentVal = $parentDom.val();
        if (parentVal == -1) {
            var $sonDom = $("#" + sonId);
            $sonDom.empty();
            $sonDom.css("display", "none");
            return;
        }
        $.ajax({
            type: 'post',
            url: '../../groups/' + parentVal + '/nodes',
            dataType: 'json',
            success: function (ds) {
                var data = ds.result;
                var $sonDom = $("#" + sonId);
                $sonDom.empty();
                $sonDom.append("<option value='-1'>请选择(可选)</option>");
                if (data.length == 0) {
                    $sonDom.css("display", "none");
                } else {
                    $sonDom.css("display", "block");
                    for (var i in data) {
                        $sonDom.append("<option value='" + data[i].groupId + "'>" + data[i].groupName + "</option>");
                    }
                }
            }
        });
    });
}

function submitAddTicket() {
    var options = {
        beforeSubmit: beforPostForm_Add,
        success: addCallBack,
        dataType: 'json',
        url: '../../group/save'
    };
    if(!options.beforeSubmit()){
        layer.msg("此用户组已存在");
        return false;
    }

    $("#addPostForm").ajaxForm(options);
    $('#addPostForm').submit();

}

function beforPostForm_Add() {
    var addResult = true;
    var data = {
        groupId: $("#MyDiv input[name='groupId']").val(),
        groupName: $("#MyDiv input[name='groupName']").val()
    };
//	判断角色是否已经存在
	$.ajax({
        type:'post',
		async: false,
		url:'../../group/exists',
		dataType : 'json',
		data : JSON.stringify(data),
        contentType : "application/json",
		success :function(ds){
			if(ds.respCode == 200 && ds.result.oper){
				addResult = false;
			}
		}
	});
    return addResult;
}

function addCallBack(responseObject, statusText) {
    if (responseObject.respCode == 200 && responseObject.result.oper) {
        $("#addPostForm")[0].reset();
        oTable.refreshByGroupTag(groupTag);
        showDialog('提示', '保存成功！', callBack);
    } else {
        showDialog('提示', '保存失败！', callBack);
    }

    function callBack() {
        $(".modal").modal('hide');
        $("#MyDiv").hide();
        $("#fade").hide();
    }
}

function submitEditTicket() {
    var options = {
        beforeSubmit: beforPostForm_Edit,
        success: editCallBack,
        dataType: 'json',
        url: '../../group/update'
    };
    if ($("#editGroupPId").val() == -1) {
        $("#EditDiv input[name='groupPid']").val("");
    } else {
        $("#EditDiv input[name='groupPid']").val($("#editGroupPId").val());
    }

    $("#editPostForm").ajaxForm(options);
    $('#editPostForm').submit();
}

function beforPostForm_Edit() {
    return true;
}

function editCallBack(responseObject, statusText) {
    if (responseObject.respCode == 200 && responseObject.result.oper) {
        showDialog('提示', '保存成功！', callBack);
        oTable.refreshByGroupTag(groupTag);
    } else {
        showDialog('提示', '保存失败！', callBack);
    }
    ;

    function callBack() {
        $(".modal").modal('hide');
        $("#EditDiv").hide();
        $("#EditFade").hide();
    }
}

function deleteGroup() {
    var selectedRow = $('#table').bootstrapTable('getSelections', null);

    if (selectedRow == null||selectedRow.length==0) {
        layer.msg("请选择要删除的记录");
        return false;
    }
    $.ajax({
        type: 'post',
        url:  url_prefix+'/groups/'+ selectedRow[0].groupId+'/deletion',
        dataType: 'json',
        success: function (ds) {
            if (ds.respCode == 200 && ds.result.oper) {
                layer.msg("删除成功");
                oTable.refreshByGroupTag(groupTag);
            } else {
                layer.msg("删除失败");
            }
        }
    });
};


/**
 * 权限分配处理
 */
// 存储权限树的所有结点
var allPermissions = null;

$.ajax({
    url: url_prefix + '/permissions',
    dataType: 'json',
    success: function (ds) {
        allPermissions = parserPermissionTreeData(ds.result, 0);
        $('#powertree').treeview({
            color: "#428bca",
            expandIcon: 'glyphicon glyphicon-chevron-right',
            collapseIcon: 'glyphicon glyphicon-chevron-down',
            data: allPermissions,
            showCheckbox: true,
            state: {
                checked: true,
                expanded: true
            },
            onNodeChecked: function (event, node) {
                nodeChecked(event, node, 'powertree');
            },
            onNodeUnchecked: function (event, node) {
                nodeUnchecked(event, node, 'powertree');
            }
        });

        $('#powertree').treeview('collapseAll', {silent: true});
    }
});

function parserPermissionTreeData(jsons, pid) {
    var node = [];
    if (jsons != null) {
        for (var i = 0; i < jsons.length; i++) {
            if (jsons[i].fatherid == pid) {
                var map = {
                    text: jsons[i].name,
                    attrs: jsons[i],
                    nodes: parserPermissionTreeData(jsons, jsons[i].id),
                    nodeId: jsons[i].id,
                    parentId: jsons[i].fatherid
                };
                node.push(map);
            }
        }
    }
    return node;
}

// 要分配权限的角色Id
var roleId = null;
var allNodes = null;
// 原始选中权限点
var srcSelectedPermissions = null;

// 显示分配权限窗体
function showApplyPermissions() {
    var selectedNode = $('#treeview').treeview('getSelected', null);

    if (selectedNode == null || selectedNode.length == 0) {
        layer.msg("请选择左侧的角色进行批量赋权");
        return false;
    }
    roleId = selectedNode[0].attrs.dmbh;

    $.ajax({
        method: 'post',
        dataType: 'json',
        url: url_prefix + "/roles/" + roleId + "/permissions",
        success: function (ds) {
            console.log(ds);
            srcSelectedPermissions = ds.result;
            $('#powertree').treeview('uncheckAll', {silent: true});
            if (allNodes == null) {
                allNodes = $('#powertree').treeview('getUnselected', {silent: true});
            }
            for (var i = 0; i < allNodes.length; i++) {
                if (srcSelectedPermissions.indexOf(allNodes[i].attrs.id) != -1) {
                    $('#powertree').treeview('checkNode', allNodes[i]);
                }
            }

            ShowDiv('AllotDiv', 'AllotFade');
        }
    });
}

function applyPermissions() {
    var curSelectedPermissions = "";
    var curSelectedPermissionsArr = [];

    var curSelectedNodes = $('#powertree').treeview('getChecked', null);

    for (var i = 0; i < curSelectedNodes.length; i++) {
        curSelectedPermissionsArr.push(curSelectedNodes[i].attrs.id);
        curSelectedPermissions += (curSelectedNodes[i].attrs.id + ",");
    }
    if (curSelectedPermissions.indexOf(",") > -1) {
        curSelectedPermissions = curSelectedPermissions.substr(0, curSelectedPermissions.length - 1);
    }

    if (JSON.stringify(curSelectedPermissionsArr.sort()) == JSON.stringify(srcSelectedPermissions.sort())) {
        return true;
    } else {
        $.ajax({
            method: 'post',
            dataType: 'json',
            url: url_prefix + "/permission/setgroup_permission",
            data: {
                group_tag: roleId,
                permission_ids: curSelectedPermissions
            },
            success: function (ds) {
                if (ds.result == "success") {
                    layer.msg("赋权成功",{shift:-1}, CloseDiv('AllotDiv', 'AllotFade'));
                } else {
                    layer.msg("赋权失败");
                }
            }
        });
    }
}


/**
 * 级联勾选 treeView -- 开始
 */

var nodeCheckedSilent = false;

function nodeChecked(event, node, treeDomId) {
    if (nodeCheckedSilent) {
        return;
    }
    nodeCheckedSilent = true;
    checkAllParent(node, treeDomId);
    checkAllSon(node, treeDomId);
    nodeCheckedSilent = false;
}

var nodeUncheckedSilent = false;

function nodeUnchecked(event, node, treeDomId) {
    if (nodeUncheckedSilent)
        return;
    nodeUncheckedSilent = true;
    uncheckAllParent(node, treeDomId);
    uncheckAllSon(node, treeDomId);
    nodeUncheckedSilent = false;
}

//选中全部父节点  
function checkAllParent(node, treeDomId) {
    $('#' + treeDomId).treeview('checkNode', node.nodeId, {silent: true});
    var parentNode = $('#' + treeDomId).treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    } else {
        checkAllParent(parentNode, treeDomId);
    }
}

//取消全部父节点  
function uncheckAllParent(node, treeDomId) {
    $('#' + treeDomId).treeview('uncheckNode', node.nodeId, {silent: true});
    var siblings = $('#' + treeDomId).treeview('getSiblings', node.nodeId);
    var parentNode = $('#' + treeDomId).treeview('getParent', node.nodeId);
    if (!("nodeId" in parentNode)) {
        return;
    }
    var isAllUnchecked = true;  //是否全部没选中  
    for (var i in siblings) {
        if (siblings[i].state.checked) {
            isAllUnchecked = false;
            break;
        }
    }
    if (isAllUnchecked) {
        uncheckAllParent(parentNode, treeDomId);
    }

}

//级联选中所有子节点  
function checkAllSon(node, treeDomId) {
    $('#' + treeDomId).treeview('checkNode', node.nodeId, {silent: true});
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            checkAllSon(node.nodes[i], treeDomId);
        }
    }
}

//级联取消所有子节点  
function uncheckAllSon(node, treeDomId) {
    $('#' + treeDomId).treeview('uncheckNode', node.nodeId, {silent: true});
    if (node.nodes != null && node.nodes.length > 0) {
        for (var i in node.nodes) {
            uncheckAllSon(node.nodes[i], treeDomId);
        }
    }
}


/**
 * 级联勾选 treeView -- 结束
 */
