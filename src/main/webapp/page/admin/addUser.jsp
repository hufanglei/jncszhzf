<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户新增</title>
<!-- // 街道中心添加用户 ： 添加本街道网格员 或 街道中心用户 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/addUser.js"></script>

</head>
<body>
	
		<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-sys" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-sys-user-add" />
	<!-- 导航辅助 结束 -->

<div class="row" style="margin: 0;">
	<div class="minheight">
		<div class="col-sm-3">
			<div id="treeview" class=""></div>
	    </div>
	    <div class="col-sm-9">
	    	<div class="panel panel-default">
				<div class="panel-heading">用户管理</div>
				
				<!-- 新增 -->
				<div id="fade" class="black_overlay"></div>
				<div id="MyDiv" class="white_content">
					<div style="text-align: right; cursor: default;margin-bottom: 10px;">
						<span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')"><i class="glyphicon glyphicon-remove"></i></span>
					</div>
					<form  class='form-horizontal clearfix' id="addPostForm" method="post" enctype="multipart/form-data">
						<table class="table" style="height: 300px;text-align: left;">
							<tr>
								<th height="34px;">登陆名称</th>
								<th><input type="text" name="user.id"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入用户名">
								</th>
								<th>密码</th>
								<th><input type="password" name="user.password"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入密码"></th>
							</tr>
							<tr>
								<th height="34px;">姓名</th>
								<th><input type="text" name="user.userName"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入姓名"></th>
								<th>电话</th>
								<th><input type="text" name="user.tel"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入电话号码"></th>
							</tr>
							<tr>
								<th height="34px;">邮箱</th>
								<th><input type="text" name="user.email"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入邮箱"/></th>
								<th>账号类别</th>
								<th>
									<select name="user.userTypeID" id="userTypeAdd" class='form-control'>
										
									</select>
									<input type="hidden" name="group.groupId" id="addMemGid"/>
								</th>
							</tr>
							<tr id = 'regionTr'>
								<th height="34px;">所属社区</th><!-- readonly="readonly"  -->
								<th>
									<select id="streetAdd" class='form-control'>
										<option value="-1">请选择</option>
									</select>
									<input type="hidden" name="user.orgNumber" id="addOrgNumber"/>
								</th>
							</tr>
						</table>
						<div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
							<button type="button" class="btn btn-default" onclick="addUser();">确定</button>
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
							<th height="34px;">登陆名称</th>
							<th><input type="text" name="id"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入用户名" readonly="readonly">
							</th>
							<th>密码</th>
							<th><input type="password" name="password"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入密码"></th>
						</tr>
						<tr>
							<th height="34px;">姓名</th>
							<th><input type="text" name="userName"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入姓名"></th>
							<th>电话</th>
							<th><input type="text" name="tel"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入电话号码"></th>
						</tr>
						<tr>
							<th height="34px;">邮箱</th>
							<th><input type="text" name="email"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入邮箱"/></th>
						</tr>
						<tr>
							<th height="34px;">所属机构</th><!-- readonly="readonly"  -->
							<th>
								<select id="areaEdit" class='form-control'>
									<option value="-1">请选择</option>
								</select>
								<select id="streetEdit" class='form-control'>
									<option value="-1">请选择</option>
								</select>
								<input type="hidden" name="orgNumber" id="editOrgNumber"/>
							</th>
							<th>账号类别</th>
							<th>
								<select name="userTypeID" id="userTypeEdit" class='form-control'>
									<option value="-1">请选择</option>
								</select>
								<input type="hidden" name="group.groupId" id="editMemGid"/>
							</th>
						</tr>
					</table>
					<div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
						<button type="button" class="btn btn-default" onclick="updateUser();">确定</button>
						<button type="button" class="btn btn-default" onclick="CloseDiv('EditDiv','EditFade')">取消</button>
					</div>
					</form>
				</div>
				
				 <div id="toolbar" class="btn-group">
					 <button id="btn_add" type="button" class="btn btn-default" onclick="ShowDiv('MyDiv','fade')">
					 	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
					 </button>
<!-- 					 <button id="btn_edit" type="button" class="btn btn-default" onclick="ShowDiv('EditDiv','EditFade')">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
					 </button> -->
					 <button id="btn_delete" type="button" class="btn btn-default" onclick = "deleteUser();">
					 	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					 </button>
<!-- 					 <button id="btn_password" type="button" class="btn btn-default"">
					 	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>重置密码
					 </button> -->
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
			$("#EditDiv input[name='userName']").val(selectedRow.userName);
			$("#EditDiv input[name='tel']").val(selectedRow.tel);
			$("#EditDiv input[name='email']").val(selectedRow.email);
			
			// 是否有网格编码
			if(selectedRow.orgNumber==null || selectedRow.orgNumber == ""){
				// 无网格编码的，则将网格编码区域置空
				$("#areaEdit").val(-1);
				$("#streetEdit").val(-1);
			}else{
				// 街道编码
				var leftPart = selectedRow.orgNumber.substr(0,9);
				$("#areaEdit").val(leftPart+"000");
				
				// 社区编码
				if(selectedRow.orgNumber.substr(9,3)=="000"){
					$("#streetEdit").val(-1);
				}else{
					$("#streetEdit").val(selectedRow.orgNumber);
				}
			}
			
			$("#EditDiv input[name='orgNumber']").val(selectedRow.orgNumber);
			$("#EditDiv input[name='userTypeID']").val(selectedRow.userTypeID);
			
			$.ajax({
				url:"../../group/findGroupByUserId.action",
				async:false,
				dataType:'json',
				data:{
					userId : selectedRow.id
				},
				success:function(result){
					if(result.rows.length>0){
						var groupId = result.rows[0].groupId;
						$("#EditDiv input[name='group.groupId']").val(groupId);
						$("#userTypeEdit").find("option[tag='"+groupId+"']").attr("selected",true);
					}
				}
			});
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
})
</script>
</body>
</html>