var oTable;
var parentId = null;
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
			 url: '../../permissions/table',
			 method: 'post',
			 toolbar: '#toolbar', //工具按钮用哪个容器
			 striped: true, //是否显示行间隔色
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
				 field: 'id',
				 title: '编号'
 			 }, {
				 field: 'name',
				 title: '显示名称'
			 }, {
				 field: 'description',
				 title: '描述ID'
			 }, {
				 field: 'tag',
				 title: 'URL地址'
			 }, {
				 field: 'fatherid',
				 title: '上级元素编号'
			 }, {
				 field: 'order',
				 title: '排序编号'
			 }]
		 });
	 };
	 
	 //得到查询的参数
	 oTableInit.queryParams = function (params) {
		 var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
             parentId: parentId,
             paginationParam: {
                 page: params.offset / params.limit, //页码
                 pageSize: params.limit   //页面大小
             }
		 };
		 return temp;
	 };
	 
	 oTableInit.refreshByParentId = function (parentId) {
		 $("#table").bootstrapTable('refresh', {
			url : '../../permissions/table',
			query : {
                parentId : parentId
			}
	 	});
	 };
	 
	 oTableInit.refresh = function () {
		 $("#table").bootstrapTable('refresh', {
			 url: '../../permissions/table'
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
function createTree(jsons,pid){
    if(jsons != null){
    	var node = null;
        for(var i=0;i<jsons.length;i++){
            if(jsons[i].fatherid == pid){
            	var map={text:jsons[i].name,attrs:jsons[i],nodes:createTree(jsons,jsons[i].id)};
            	if(node==null) node = [];
            	node.push(map);
            }
        }
   }
    return node ;
}

$(function() {
	var defaultData;
	$.ajax({
		url : '../../permissions',
		dataType : 'json',
    	success: function(ds) {
            defaultData = createTree(ds.result, 0);
            console.log(JSON.stringify(ds.result));
            $('#treeview').treeview({
                color: "#428bca",
                expandIcon: 'glyphicon glyphicon-chevron-right',
                collapseIcon: 'glyphicon glyphicon-chevron-down',
                data: defaultData,
                onNodeSelected: function (event, data) {
                    if (data.attrs.fatherid == 0) {
                    	parentId = data.attrs.id;
                        oTable.refreshByParentId(parentId);
                    }
                    if (data.nodes != null) {
                        var select_node = $('#treeview').treeview('getSelected');
                        if (select_node[0].state.expanded) {
                            $('#treeview').treeview('collapseNode', select_node);
                            select_node[0].state.selected = false;
                        }
                        else {
                            $('#treeview').treeview('expandNode', select_node);
                            select_node[0].state.selected = false;
                        }
                    }
                }
            });

            $('#treeview').treeview('collapseAll', {silent: true});
            //$('#treeview').treeview('toggleNodeSelected', [1, {silent: false}]);
        }
    });
    
});




function submitAddTicket() {
    var options = {
        beforeSubmit: beforPostForm_Add,
        success: addCallBack,
        dataType: 'json',
        url: '../../permission/addPermission.action'
    };

    $("#addPostForm").ajaxForm(options);
    $('#addPostForm').submit();
}


function submitEditTicket() {
	var options = {
		beforeSubmit : beforPostForm_Edit,
		success : editCallBack,
		dataType : 'json',
		url : '../../permission/updatePermission.action'
	};

	$("#editPostForm").ajaxForm(options);
	$('#editPostForm').submit();

}

function beforPostForm_Add() {  
	return true;
}

function addCallBack(responseObject, statusText) {
	if (responseObject.result == 1 || responseObject.result == "success" ) {
		$("#addPostForm")[0].reset();
		oTable.refresh();
		showDialog('提示','保存成功！',callBack);
	} else {
		showDialog('提示','保存失败！',callBack);
	};
	
	function callBack(){
		$(".modal").modal('hide');
		$("#MyDiv").hide();
		$("#fade").hide();
	}
}

function beforPostForm_Edit() {  
	return true;
}

function editCallBack(responseObject, statusText) {
	if (responseObject.result == 1 || responseObject.result == "success" ) {
		showDialog('提示','保存成功！',callBack);
		oTable.refresh();
	} else {
		showDialog('提示','保存失败！',callBack);
	};
	
	function callBack(){
		$(".modal").modal('hide');
		$("#EditDiv").hide();
		$("#EditFade").hide();
	}
}

function deletePermission(){
	var selectedRow = $('#table').bootstrapTable('getSelections', null);
	
	if(selectedRow == null){
		alert("请选择要删除的记录");
	}
	
	
	$.ajax({
		method : 'post',
		url : '../../permission/deletePermission.do',
		dataType : 'json',
		data : {
			id: selectedRow[0].id
		},
    	success: function(results){
    		if(results.result == "success"){
    			alert("删除成功！");
    		}
    	}
	});
};