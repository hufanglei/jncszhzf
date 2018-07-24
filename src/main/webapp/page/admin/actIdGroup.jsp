<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/json2.js"></script>

<script type="text/javascript">
$(function() {

	$("#act_id_group_list_table").datagrid({
		striped : true,
		iconCls : 'icon-tip',
		singleSelect : true,
		rownumbers : true,
		title : "角色列表",
		pagination : true,
		pageList : [ 10,20,50 ],
		pageSize: 20,
		url : '../../actidgroup/listActIdGroup',
		queryParams : {

		},
		rowStyler : function(index, row) {
			return 'cursor:pointer;';
		},
		loadMsg : "加载数据中，请稍候....",
		nowrap : true,
		onDblClickRow : clickrow,
		columns : [ [ 
			{
				title : 'ID_',
				field : 'id',
				width : "120",
				sortable : false,
				formatter : function(value, rowData) {
					return " " + value;
			
				}
			},      
		             
			{
			title : 'REV_',
			field : 'rev',
			width : "120",
			sortable : false,
			formatter : function(value, rowData) {
				return " " + value;

			}
		},
			{
			title : 'NAME_',
			field : 'name',
			width : "120",
			sortable : false,
			formatter : function(value, rowData) {
				return " " + value;

			}
		},
			{
			title : 'TYPE_',
			field : 'type',
			width : "120",
			sortable : false,
			formatter : function(value, rowData) {
				return " " + value;

			}
		}
		 ] ],
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				$('#postForm').resetForm();
				$("#is_add").val(null);
				$("#id").removeAttr("readonly");
				$('#edit_act_id_group_form').window('open');
			}

		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				$("#is_add").val("no");
				$("#id").attr("readonly","readonly");
				var rows = $("#act_id_group_list_table").datagrid('getSelections');
				if (rows == 0) {
					alert("请选择要编辑的行！");
					return;
				}
				if (rows.length > 1) {
					alert("系统提示", "对不起，该操作只能选择一行！");
					return;
				}
				showEditActIdGroup(rows[0].id);
			}
		}, '-', {
			id : 'btndel',
			text : '删除',
			iconCls : 'icon-no',
			handler : function() {
				var rows = $("#act_id_group_list_table").datagrid('getSelections');
				if (rows == 0) {
					alert("请选择要删除的行！");
					return;
				}

				if (rows.length > 1) {
					alert("对不起，该操作只能选择一行！");
					return;
				}

				var flag = confirm("确认删除这条记录？");
				if (flag == false)
					return;
				$.post("../../actidgroup/deleteActIdGroup", {
					id : rows[0].id
				}, function(responseObject, statusText) {
					if (responseObject.result == 1) {
						alert("删除成功！");
						$('#act_id_group_list_table').datagrid('reload');
					} else {
						alert("删除失败！原因：\n"+responseObject.errMessage);
					}
					$("#act_id_group_list_table").datagrid("load");
				},"json");
			}
		} ]
	});
});

function addActIdGroupCallBack(responseObject, statusText) {
	if (responseObject.result == 1) {
		alert("保存成功！");
		$('#edit_act_id_group_form').window('close');
		$('#act_id_group_list_table').datagrid('reload');
	} else {
		alert("保存失败！原因：\n"+responseObject.errMessage);
	}
}

function clickrow(rowIndex, rowData) {
	
	showEditActIdGroup(rowData.id);
}

function submitAddTicket() {
	var is_add = $("#is_add").val();

	if (is_add == null || $.trim(is_add) == '') {
		var options = {
			beforeSubmit : beforPostForm_actIdGroup,
			success : addActIdGroupCallBack,
			dataType : 'json',
			url : "../../actidgroup/addActIdGroup"

		};

		$("#postForm").ajaxForm(options);
		$('#postForm').submit();
	} else {
		var options = {
			beforeSubmit : beforPostForm_actIdGroup,
			success : addActIdGroupCallBack,
			dataType : 'json',
			url : "../../actidgroup/updateActIdGroup"

		};

		$("#postForm").ajaxForm(options);
		$('#postForm').submit();
	}

}

function beforPostForm_actIdGroup() {
    var dateReg = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    var numberReg = /^[0-9]+$/;
    var doubleReg = /^-?[0-9]+(\.[0-9]+)*$/;
			
				var rev = $('#rev').val();
					   if(($.trim(rev)!=""&&!numberReg.test($.trim(rev))))
				 	   {
					   		alert("REV_格式不正确！");
				 			return false;
					   }
			
				var name = $('#name').val();
			
				var type = $('#type').val();
	return true;
}


function showEditActIdGroup(id){
	
	
	
	$.post("../../actidgroup/toEditActIdGroup",{id:id},function(data){
		var data1 = JSON.parse(data);
		
		$("#id").val(data1.id);
		$("#rev").val(data1.rev);
		$("#name").val(data1.name);
		$("#type").val(data1.type);
		$("#id").removeClass("hidden");
		$('#edit_act_id_group_form').window('open');
	});
	
	
	
		
}

</script>
<style>
.minheight {
	margin: 5px;
	padding: 10px;
	background: #ddd;
	height: auto !important;
	_height: 550px;
	min-height: 550px;
}
</style>
</head>
<body>
	<div class="minheight">
		<table id="act_id_group_list_table"></table>
	</div>

	<div id="edit_act_id_group_form" class="easyui-window" title="角色配置" data-options="modal:true,closed:true,iconCls:'icon-edit',collapsible:false,minimizable:false,maximizable:false,shadow:true,top:1"
		style="padding: 0px; text-align: left; width: 600px; ">
		<div style="margin: 0px; border: 1px solid #fff; background: #eee;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="submitAddTicket();">确定</a> <a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel',plain:true" onclick="$('#edit_act_id_group_form').window('close');">取消</a>
		</div>
		<div style="height:400px; overflow:scroll;">

		<form action="" id="postForm" method="post" style="padding: 10px;">
				<label>ID_：</label> <input type="text" id="id" name="id" style="width: 150px;"> 
				<br /><br /> 
				<label>REV_：</label> <input type="text" id="rev" name="rev" style="width: 150px;">  [
				 数字（10位）
				]<br /><br /> 
				<label>NAME_：</label> <input type="text" id="name" name="name" style="width: 150px;">  [
				字符串（255位）
				
				]<br /><br /> 
				<label>TYPE_：</label> <input type="text" id="type" name="type" style="width: 150px;">  [
				字符串（255位）
				

				]<br /><br /> 
			
	
		</form>
		<input id="is_add" type="hidden" name="is_add" />
		</div>
	</div>


</body>
</html>