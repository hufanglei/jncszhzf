<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/permission.js"></script>

</head>
<body>
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-sys" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-sys-permission" />
	<!-- 导航辅助 结束 -->
	<!-- <table id="permission_list_table"></table> -->
	<div class="row" style="margin: 0;">
		<div class="minheight">
			<div class="col-sm-3">
				<div id="treeview" class=""></div>
		    </div>
		    <div class="col-sm-9">
		    	<div class="panel panel-default">
					<div class="panel-heading">权限列表</div>
					
					<!-- 新增 -->
					<div id="fade" class="black_overlay"></div>
					<div id="MyDiv" class="white_content">
						<div style="text-align: right; cursor: default;margin-bottom: 10px;">
							<span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')"><i class="glyphicon glyphicon-remove"></i></span>
						</div>
						<form  class='form-horizontal clearfix' id="addPostForm" method="post" enctype="multipart/form-data">
						<table class="table" style="height: 300px;text-align: left;">
								<tr>
									<th>显示名称</th>
									<th><input type="text" name="name" class="form-control" placeholder="" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>描述ID</th>
									<th><input type="text" name="description" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>URL地址</th>
									<th><input type="text" name="tag" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>上级元素编号</th>
									<th><input type="text" name="fatherid" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>排序编号</th>
									<th><input type="text" name="order" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
						</table>
						<div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
							<button type="button" class="btn btn-default" onclick = "submitAddTicket();">确定</button>
							<button type="button" class="btn btn-default" onclick="CloseDiv('MyDiv','fade')">取消</button>
						</div>
						</form>
					</div>
					
					<!-- 编辑 -->
					<div id="EditFade" class="black_overlay"></div>
					<div id="EditDiv" class="white_content">
						<div style="text-align: right; cursor: default;margin-bottom: 10px;">
							<span style="font-size: 16px;" onclick="CloseDiv('EditDiv','EditFade')"><i class="glyphicon glyphicon-remove"></i></span>
						</div>
						<form  class='form-horizontal clearfix' id="editPostForm" method="post" enctype="multipart/form-data">
							<table class="table" style="height: 300px;text-align: left;">
								<tr>
									<th>元素编号</th>
									<th><input type="text" name="id" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>显示名称</th>
									<th><input type="text" name="name" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>描述ID</th>
									<th><input type="text" name="description" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>URL地址</th>
									<th><input type="text" name="tag" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>上级元素编号</th>
									<th><input type="text" name="fatherid" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
								<tr>
									<th>排序编号</th>
									<th><input type="text" name="order" class="form-control" aria-describedby="basic-addon1"></th>
								</tr>
							</table>
							<div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
								<button type="button" class="btn btn-default" onclick = "submitEditTicket();">确定</button>
								<button type="button" class="btn btn-default" id="cancel">取消</button>
							</div>
						</form>
					</div>
			        <div id="toolbar" class="btn-group">
						 <button id="btn_add" type="button" class="btn btn-default" onclick="ShowDiv('MyDiv','fade')">
						 	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
						 </button>
						 <button id="btn_edit" type="button" class="btn btn-default" onclick="ShowDiv('EditDiv','EditFade')">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
						 </button>
						 <button id="btn_delete" type="button" class="btn btn-default" onclick = "deletePermission();">
						 	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
						 </button>
					</div>
					<table id="table">
					
					</table>
				</div>
		    </div>
	    </div>
    </div>
<script type="text/javascript">
//弹出隐藏层
function ShowDiv(show_div,bg_div){
	
	if(show_div == "EditDiv"){
		var selectedRow = $('#table').bootstrapTable('getSelections', null);
		
		if(selectedRow == null || selectedRow.length==0){
			alert("请选择要修改的记录");
			return false;
		}else{
			selectedRow = selectedRow[0];
			$("#EditDiv input[name='id']").val(selectedRow.id);
			$("#EditDiv input[name='name']").val(selectedRow.name);
			$("#EditDiv input[name='description']").val(selectedRow.description);
			$("#EditDiv input[name='tag']").val(selectedRow.tag);
			$("#EditDiv input[name='fatherid']").val(selectedRow.fatherid);
			$("#EditDiv input[name='order']").val(selectedRow.order);
		}
	}
	
	document.getElementById(show_div).style.display='block';
	document.getElementById(bg_div).style.display='block' ;
	
	var bgdiv = document.getElementById(bg_div);
	bgdiv.style.width = document.body.scrollWidth;
};
//关闭弹出层
function CloseDiv(show_div,bg_div){	
	document.getElementById(show_div).style.display='none';
	document.getElementById(bg_div).style.display='none';
};
$("#cancel").click(function(){
	  $("#EditDiv").hide();
	  $("#EditFade").hide();
	});

</script>

</body>
</html>