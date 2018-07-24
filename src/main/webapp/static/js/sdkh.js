/**
 *  Created by hfl on 2017/11/30.
 *  手动考核
 */
var oTable =null;
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
            url: '../../jxkh/fftskhPage.action', //请求后台的URL（*）
            method: 'get', //请求方式（*）
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1, //初始化加载第一页，默认第一页
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
            showToggle:false, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            columns: [{
                checkbox: true
            },{
                field: 'id',
                title: '主键id',
                visible:false
             },{
                field: 'type',
                title: '类型',
                visible:false
            },{
                field: 'qhzdId',
                title: '机构编码'
            }, {
                field: 'qhzdName',
                title: '机构名称'
            }, {
                field: 'source',
                title: '问责来源'
            }, {
                field: 'content',
                title: '问责内容'
            },{
                field: 'score',
                title: '问责扣分'
            },{
                field: 'createTime',
                title: '创建时间'
            }, ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            rows: params.limit, //页面大小
            page: params.offset/params.limit + 1,//页码
            type:GetRequest()
        };
        return temp;
    };

    oTableInit.refresh = function () {
        $("#table").bootstrapTable('refresh', {
            url : '../../jxkh/fftskhPage.action'
        });
    }

    oTableInit.refreshByGrouptag = function (groupTag) {
        $("#table").bootstrapTable('refresh', {
            url : '../../jxkh/jxkh.action',
            query : {
                group_type : groupTag
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



// //树结构
// function createTree(jsons,pid){
//     if(jsons != null){
//     	var node = null;
//         for(var i=0;i<jsons.length;i++){
//             if(jsons[i].pid == pid){
//             	var map={text:jsons[i].groupName,attrs:jsons[i],nodes:createTree(jsons,jsons[i].id)};
//             	if(node==null) node=[];
//             	node.push(map);
//             }
//         }
//    }
//     return node ;
// }

// $(function() {
// 	var defaultData;
// 	$.ajax({
// 		method : 'post',
//     	url:'../../group/listAllGroupsForTree.action',
//     	dataType : 'json',
//     	success: function(results){
//     		defaultData= createTree(results.rows,0);
//
//     		$('#treeview').treeview({
//     		    color: "#428bca",
//     		    expandIcon: 'glyphicon glyphicon-chevron-right',
//     		    collapseIcon: 'glyphicon glyphicon-chevron-down',
//     		    data: defaultData,
//     		    onNodeSelected: function(event, data) {
//     			    if(data.nodes!=null){
//     			        var select_node = $('#treeview').treeview('getSelected');
//     			        if(select_node[0].state.expanded){
//     			            $('#treeview').treeview('collapseNode',select_node);
// //    			            select_node[0].state.selected=false;
//     			        }
//     			        else{
//     			            $('#treeview').treeview('expandNode',select_node);
// //    			            select_node[0].state.selected=false;
//     			        }
//     			    }
//     			    if(data.attrs.pid==0){
//     			    	oTable.refreshByGrouptag(data.attrs.groupTag);
//     			    }
//     			}
//     	    });
//     		$('#treeview').treeview('collapseAll', {silent: true });
//     	}
//     });
//
// })


//新增、修改、删除
$(document).ready(function(){
    /**
     * 下来框加载数据
     */
    $.ajax({
        url:'../../jxkh/listAllGroups.action',
        dataType : 'json',
        success: function(result){
            var data = result.groupList;
            for(var i in data){
                $("select[name='qhzdName']").append("<option value='"+data[i].qhmc+"' tag='"+data[i].qhdm+"'>"+data[i].qhdm+"  "+data[i].qhmc+"</option>");
            };

        }
    });
    /**
     * 新增 给隐藏域组织机构代码赋值
     */
    $("#qhzdName").bind("change",function(){
        if($(this).val()!=-1){
            $("#qhzdId").val($(this).find("option:selected").attr("tag"));
        }else{
            $("#qhzdId").val("");
        }
    });
    /**
     * 编辑 给隐藏域组织机构代码赋值
     */
    $("#EditqhzdName").bind("change",function(){
        if($(this).val()!=-1){
            $("#EditqhzdId").val($(this).find("option:selected").attr("tag"));
        }else{
            $("#EditqhzdId").val("");
        }
    });
});

// function bindChange(parentId,sonId){
//     var $parentDom = $("#"+parentId);
//     $parentDom.bind("change",function(){
//         var parentVal = $parentDom.val();
//         if(parentVal == -1){
//             var $sonDom = $("#"+sonId);
//             $sonDom.empty();
//             $sonDom.append("<option value='-1'>请选择</option>");
//             return;
//         }
//         $.ajax({
//             url:'../../qhzd/selectQhzd.action?qhjb=3&ssqhdm='+parentVal,
//             dataType : 'json',
//             success: function(result){
//                 var data = result.rows;
//                 var $sonDom = $("#"+sonId);
//
//                 $sonDom.empty();
//                 for(var i in data){
//                     $sonDom.append("<option value='"+data[i].qhdm+"'>"+data[i].qhmc+"</option>");
//                 }
//             }
//         });
//     });
// };

function updateUser() {
    var options = {
        // beforeSubmit : beforPostForm_edit,
        success : editCallBack,
        dataType : 'json',
        url : '../../jxkh/EditComplaint.action'
    };

    $("#editPostForm").ajaxForm(options);
    $('#editPostForm').submit();
};

// function beforPostForm_edit() {
//     if($("#streetEdit").val()!=-1){
//         $("#editOrgNumber").val($("#streetAdd").val());
//     }else if($("#areaEdit").val()!=-1){
//         $("#editOrgNumber").val($("#areaAdd").val());
//     }
//     return true;
// }

function editCallBack(responseObject, statusText) {
    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
        showDialog('提示','保存成功！',callBack);
        $("#editPostForm")[0].reset();
        oTable.refresh();
    } else {
        showDialog('提示','保存失败！',callBack);
    };

    function callBack(){
        $(".modal").modal('hide');
        $("#EditDiv").hide();
        $("#EditFade").hide();
        $("#fade").hide();
        window.location.reload();
    }
}
/**
 * 截取第一个type参数
 * @constructor
 */
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var arr  = url.split("?type=");
    return arr[1];
}

/**
 * 保存方法
 */
function addUser() {
    // var usrTypeId = $("#userTypeAdd").val();
    // if(usrTypeId == 1 || usrTypeId ==2){
    //     if($("#streetAdd").val()!=-1){
    //         $("#addOrgNumber").val($("#streetAdd").val());
    //     }else if($("#areaAdd").val()!=-1){
    //         $("#addOrgNumber").val($("#areaAdd").val());
    //     }
    // }else{
    //     $("#addOrgNumber").val("");
    // }

    var options = {
        // beforeSubmit : beforPostForm_Add,
        success : addCallBack,
        dataType : 'json',
        url : '../../jxkh/saveComplaint.action'
    };

    $("#addPostForm").ajaxForm(options);
    $('#addPostForm').submit();
};


// function beforPostForm_Add() {
//     var addResult = false;
//
//     //	判断用户名是否已经存在
//     $.ajax({
//         async: false,
//         url:'../../user/existUserName.action',
//         dataType : 'json',
//         data : {
//             userid: $("#addPostForm input[name='user.id']").val()
//         },
//         success:function(result){
//             if(!result.exist){
//                 addResult = true;
//             }
//         }
//     });
//
//     return addResult;
// }

function addCallBack(responseObject, statusText) {
    if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
        showDialog('提示','保存成功！',callBack);
        $("#addPostForm")[0].reset();
        oTable.refresh();
    } else {
        showDialog('提示','保存失败！',callBack);
    };

    function callBack(){
        $(".modal").modal('hide');
        $("#MyDiv").hide();
        $("#fade").hide();
        // oTable.refresh();
        window.location.reload();
    }
}


function deleteUser(){
    var selectedRow = $('#table').bootstrapTable('getSelections', null);

    if(selectedRow == null  || selectedRow.length==0){
        alert("请选择要删除的记录");
        return false;
    }

    $.ajax({
        method : 'post',
        url : '../../jxkh/deleteComplaint.action',
        dataType : 'json',
        data : {
            ids: selectedRow[0].id
        },
        success: function(results){
            if(results.success){
                alert("删除成功！");
                oTable.refresh();
            }else{
                alert("删除失败");
            }
        }
    });
}