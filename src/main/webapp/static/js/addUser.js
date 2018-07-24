// 街道中心添加用户 ： 添加本街道网格员 或 街道中心用户
// orgId : 当前用户网格编号

// 当前街道的行政区划编码前缀
var parentOrgId = orgId.substr(0,9);
var streetOrgId = orgId.substr(0,12);

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
			 url: '../../user/userPage.action', //请求后台的URL（*）
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
//			 responseHandler:function(res){ //数据加载前的预处理
//				return {
//					rows : res.rows,
//					total : res.rows.length
//				};
//			 },
			 columns: [{
				 checkbox: true
			 },  {
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
			 },{
				 field: 'orgNumber',
				 title: '行政区划编码'
			 },{
				 field: 'userTypeID',
				 title: '账号类别'
			 }, ]
		 });
	 };

	 //得到查询的参数
	 oTableInit.queryParams = function (params) {
		 var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				 orgNumber:parentOrgId,
				 forStreet : "y",
				 rows: params.limit, //页面大小
				 page: params.offset/params.limit + 1 //页码
		 };
		 return temp;
	 };
	 
	 oTableInit.refresh = function () {
		 $("#table").bootstrapTable('refresh', {
			url : '../../user/userPage.action',
			query : {
				forStreet : "y",
				orgNumber:parentOrgId
			}
	 	});
	 }
	 
	 //	 根据行政区划编码刷新数据
	 oTableInit.refreshByOrgId = function (OrgId) {
		 $("#table").bootstrapTable('refresh', {
			url : '../../user/userPage.action',
			query : {
				forStreet : "y",
				orgNumber : OrgId
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
function createTree(jsons,pid){
    if(jsons != null){
    	var node = null;
        for(var i=0;i<jsons.length;i++){
            if(jsons[i].ssqhdm == pid){
            	var map={text:jsons[i].qhmc,attrs:jsons[i],nodes:createTree(jsons,jsons[i].qhdm)};
            	if(node==null) node=[];
            	node.push(map);
            }
        }
   }
    return node ;
}

var qhzd_sub_arr = []; 	// 存储本街道的社区列表
$(function() {
	var defaultData;
	$.ajax({
		method : 'post',
    	url:'../../qhzd/selectQhzdByStreetPrefix.aciton',
    	data:{
    		qhdm : parentOrgId
    	},
    	dataType : 'json',
    	success: function(results){
    		qhzd_sub_arr = results.rows;
    		
    		// 构造treeview数据
    		defaultData= createTree(results.rows,orgId.substr(0,6)+'000000');
    		
    		$('#treeview').treeview({
    		    color: "#428bca",
    		    expandIcon: 'glyphicon glyphicon-chevron-right',
    		    collapseIcon: 'glyphicon glyphicon-chevron-down',
    		    data: defaultData,
    		    onNodeSelected: function(event, data) {
    			    if(data.nodes!=null){                               
    			        var select_node = $('#treeview').treeview('getSelected');
    			        if(select_node[0].state.expanded){
    			            $('#treeview').treeview('collapseNode',select_node);
    			        }
    			        else{
    			            $('#treeview').treeview('expandNode',select_node);
    			        }
    			    }
    			    if(data.attrs.qhdm.indexOf("000")==-1){	//如果选中的是社区
    			    	oTable.refreshByOrgId(data.attrs.qhdm);
    			    }else{  //如果选中的是街道
    			    	oTable.refresh();
    			    }
    			}
    	    });
    		
    		// 填充下属社区的下拉列表
    		$sonDom = $("#streetAdd");
			$sonDom.empty();
			for (var i = 0; i < qhzd_sub_arr.length; i++) {
				if(qhzd_sub_arr[i].qhjb==3)
					$sonDom.append("<option value='"+qhzd_sub_arr[i].qhdm+"'>"+qhzd_sub_arr[i].qhmc+"</option>");
			}
			$("#addOrgNumber").val($("#streetAdd").val());
			$("#streetAdd").bind("change",function(){
				$("#addOrgNumber").val($("#streetAdd").val());
			});
    	}
    });
	
})


//新增、修改、删除
$(document).ready(function(){
	$("select[name='user.userTypeID']").append("<option value='1' tag='community'>社区网格员用户</option>");
	$("select[name='user.userTypeID']").append("<option value='2' tag='"+session_group_id+"'>街道中心用户</option>");
	$("#addMemGid").val($(this).find("option:selected").attr("tag"));
	
	$("#userTypeAdd").bind("change",function(){ 
		$("#addMemGid").val($(this).find("option:selected").attr("tag"));
		
		if($(this).val()==1){
			$("#regionTr").show();
		}else{
			$("#regionTr").hide();
		}
		
	});
});

function updateUser() {
	var options = {
			beforeSubmit : beforPostForm_edit,
			success : editCallBack,
			dataType : 'json',
			url : '../../user/userUpdate.action'
	};
	
	$("#editPostForm").ajaxForm(options);
	$('#editPostForm').submit();
};

function beforPostForm_edit() {
	if($("#streetEdit").val()!=-1){
		$("#editOrgNumber").val($("#streetAdd").val());
	}else if($("#areaEdit").val()!=-1){
		$("#editOrgNumber").val($("#areaAdd").val());
	}
	return true;
}

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
	}
}

function addUser() {
	var options = {
			beforeSubmit : beforPostForm_Add,
			success : addCallBack,
			dataType : 'json',
			url : '../../user/userSave.action'
	};
	
	$("#addPostForm").ajaxForm(options);
	$('#addPostForm').submit();
};


function beforPostForm_Add() {
	var addResult = false;
	//	判断用户名是否已经存在
	$.ajax({
		async: false,
		url:'../../user/existUserName.action',
		dataType : 'json',
		data : {
			userid: $("#addPostForm input[name='user.id']").val()
		},
		success:function(result){
			function errCallBack(){
				$(".modal").modal('hide');
			}
			
			if(result.exist){
				showDialog('提示','用户名存在，请重试！',errCallBack);
			}else{
				addResult = true;
				userArrs = null;
			}
		}
	});
	
	return addResult;
}

function addCallBack(responseObject, statusText) {
	if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
		showDialog('提示','保存成功！',callBack);
		$("#addPostForm")[0].reset();
		oTable.refresh();
	} else {
		showDialog('提示','保存失败！',errCallBack);
	};
	
	function callBack(){
		$(".modal").modal('hide');
		$("#MyDiv").hide();
		$("#fade").hide();
	}
	
}

function errCallBack(){
	$(".modal").modal('hide');
}

function deleteUser(){
	var selectedRow = $('#table').bootstrapTable('getSelections', null);
	
	if(selectedRow == null  || selectedRow.length==0){
		alert("请选择要删除的记录");
		return false;
	}
	
	$.ajax({
		method : 'post',
		url : '../../user/deleteUser.do',
		dataType : 'json',
		data : {
			ids: selectedRow[0].id
		},
    	success: function(results){
    		if(results.success){
    			showDialog('提示','删除【' + selectedRow[0].id + '】成功！',errCallBack);
    			oTable.refresh();
    		}else{
    			showDialog('提示','删除【' + selectedRow[0].id + '】失败！',errCallBack);
    		}
    	}
	});
}