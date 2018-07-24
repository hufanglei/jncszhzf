<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已办任务管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">


	function searchTask(){
		$("#dg").datagrid('load',{
			"s_name":$("#s_name").val()
		});
	}
	
	function openCurrentViewTab(taskId){
		window.parent.openTab('查看流程图',"../task/showCurrentView.action?taskId="+taskId,'icon-flow');
	}

	function formatAction(val,row){
		return "<a href='javascript:openListActionDialog("+row.id+")'>流程执行过程</a>&nbsp;<a href='javascript:openListCommentDialog("+row.id+")'>历史批注</a>&nbsp;"
	}
	
	function openListCommentDialog(taskId){
		$("#dg2").datagrid("load",{
			taskId:taskId
		});
		$("#dlg2").dialog("open").dialog("setTitle","查看历史批注");
	}
	
	function openListActionDialog(taskId){
		$("#dg3").datagrid("load",{
			taskId:taskId
		});
		$("#dlg3").dialog("open").dialog("setTitle","流程执行过程");
	}
	
	//处理亮灯状态
	function formatLightAction(val, row){
		var str = "";
		if (row.lightStatus == 0){
			str += '<img src="${pageContext.request.contextPath}/static/images/d_gray.png" style="width:20px;height:20px"/>';
		}
		if (row.lightStatus == 1){
			str += '<img src="${pageContext.request.contextPath}/static/images/d_red.png" style="width:20px;height:20px"/>';
		}
		if (row.lightStatus == 2){
			str += '<img src="${pageContext.request.contextPath}/static/images/d_green.png" style="width:20px;height:20px"/>';
		}
		return str;
	}

</script>
</head>
<body style="margin: 1px">
<table id="dg" 
	title="已办任务管理" 
	class="easyui-datagrid"
  	fitColumns="true" 
  	nowrap="false"
  	pagination="true" 
  	rownumbers="true"
  	url="${pageContext.request.contextPath}/task/finishedList.action?groupId=${currentMemberShip.group.groupId}&userId=${currentMemberShip.user.id }" 
  	fit="true" 
  	singleSelect="true"
  	toolbar="#tb">
 <thead>
 	<tr>
 		<th field="issueNumber" width="105" align="center">上报编号</th>
 		<th field="subject" width="100" align="center">诉求主题</th>
 		<th field="name" width="80" align="center">业务类别</th>
 		<th field="createTime" width="100" align="center">受理时间</th>
 		<th field="endTime" width="100" align="center">结束时间</th>
 		<th field="light" width="20" align="center" formatter="formatLightAction">状态</th>
 		<th field="action" width="100" align="center" formatter="formatAction">操作</th>
 	</tr>
 </thead>
</table>
<div id="tb">
 <div>
 	&nbsp;任务名称&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchTask()"/>
 	<a href="javascript:searchTask()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 </div>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true" >
 
 	<table id="dg2" title="批注列表" class="easyui-datagrid" data-options="nowrap:false"
  fitColumns="true"  
  url="${pageContext.request.contextPath}/task/listHistoryComment.do" style="width: 700px;height: 200px;">
 <thead>
 	<tr>
 		<th field="time" width="120" align="center">批注时间</th>
 		<th field="userId" width="100" align="center">批注人</th>
 		<th field="fullMessage" width="200" align="center">批注信息</th>
 	</tr>
 </thead>
</table>
 
</div>


<div id="dlg3" class="easyui-dialog" style="width: 750px;height: 350px;padding: 10px 20px" closed="true" >
 
 	<table id="dg3" title="流程执行过程列表" class="easyui-datagrid"
  fitColumns="true"  
  url="${pageContext.request.contextPath}/task/listAction.do" style="width: 700px;height: 250px;">
 <thead>
 	<tr>
 		<th field="activityName" width="150" align="center">任务节点名称</th>
 		<th field="startTime" width="100" align="center">开始时间</th>
 		<th field="endTime" width="100" align="center">结束时间</th>
 	</tr>
 </thead>
</table>
 
</div>

</body>
</html>