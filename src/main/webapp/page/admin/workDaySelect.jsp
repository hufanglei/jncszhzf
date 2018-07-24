<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>工作日选择</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/kalendae-master/kalendae.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/kalendae-master/kalendae.standalone.js"></script>
<script type="text/javascript" charset="utf-8">
	var K_date;
	var date=new Date;
	var year=date.getFullYear(); 

	$(function() {
		K_date = new Kalendae("k_date", {
			viewStartDate : Kalendae.moment(year + "-01-01"),
			months : 12,
			mode : 'multiple'
		});
		
		getWorkDay(year);
	});

	function setWorkDay() {
		if(!confirm("确认提交？")){
			return;
		}
		
		var datas = K_date.getSelected();
		 $.post("${pageContext.request.contextPath}/workday/setWorkDay", {
			data : datas,
			year : year
		}, function(data) {
			var data=eval('('+data+')');
			if(data.success == true){
				alert("修改成功！");
			}else{
				alert("修改失败，请联系管理员！");
			}
				
		}); 
		
	}
	
	function getWorkDay(year) {
		 $.post("${pageContext.request.contextPath}/workday/selectWorkDay.action?year=" + year, {
			}, function(data) {
				var datas = data.rows.join();
				K_date.setSelected(datas);
			},"json"); 
	}
</script>

</head>

<body>
		<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-sys" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-sys-workday" />
	<!-- 导航辅助 结束 -->
	<!-- <div style="width: 100%; text-align: left; margin-left: 30px;";>
		请选择休息日
	</div> -->
	<h3 style="margin: 20px 0 20px 35px;">请选择休息日</h3>
	<div id="k_date" style="text-align: left;padding: 0 30px">
	
	</div>
	<button class="btn btn-primary" onclick="setWorkDay()" style="float: right;margin: 10px 35px 35px 0;">提交修改</button>
	<input type="hidden" id="node_id" value="106" />
</body>
</html>
