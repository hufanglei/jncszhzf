<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待办任务管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	function searchTask(){
		$("#dg").datagrid('load',{
			"s_name":$("#s_name").val()
		});
	}
	function formatAction(val,row){
		return "<a href='javascript:openFinishTaskTab("+row.id+")'>办理任务</a>&nbsp;<a href='javascript:openCurrentViewTab("+row.id+")'>查看当前流程图</a>"
		//return "<a href='javascript:openFinishTaskTab("+row.id+")'>办理任务</a>&nbsp;<a target='_blank' href='${pageContext.request.contextPath}/task/showCurrentView.action?taskId="+row.id+"'>查看当前流程图</a>"
	}
	
	function openCurrentViewTab(taskId){
		window.parent.openTab('查看流程图',"../task/showCurrentView.action?taskId="+taskId,'icon-flow');
	}
	
	function openFinishTaskTab(taskId){
		$.post("${pageContext.request.contextPath}/task/redirectPage.action",{taskId:taskId},function(result){
			window.parent.openTab('办理任务',result.url+"?taskId="+taskId,'icon-check');
		},"json");
	}

</script>
</head>
<body style="margin: 1px">
<table id="dg" 
	title="待办任务管理" 
	class="easyui-datagrid"
  	fitColumns="true" 
  	nowrap="false"
  	pagination="true" 
  	rownumbers="true"
  	url="${pageContext.request.contextPath}/task/taskPage.action?userId=${currentMemberShip.user.id }" 
  	fit="true"
  	singleSelect="true"
  	toolbar="#tb">
 <thead>
 	<tr>
 		
 		<th field="issueNumber" width="105" align="center">上报编号</th>
 		<th field="subject" width="100" align="center">诉求主题</th>
 		<th field="name" width="100" align="center">业务类别</th>
 		<th field="createTime" width="100" align="center">上报时间</th>
 		<th field=limitTime width="100" align="center">处理到期时间</th>
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

</body>
</html>