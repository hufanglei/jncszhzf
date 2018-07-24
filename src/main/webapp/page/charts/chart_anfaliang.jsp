<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

	<title>案发量统计</title>

	<script type="text/javascript">
		var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
		var url_prefix = '${pageContext.request.contextPath}';
	</script>

	<style>
		.filter{height: 50px;background-color: #eff0f4;line-height: 50px;}
		#container,#container *{box-sizing: content-box !important;}
		.breadcrumb > li + li:before {color: #CCCCCC;content: "> "; padding: 0 5px;}
		.breadcrumb {padding: 2px 15px;margin-bottom:0;list-style:none;background-color: #f5f5f5;border-radius: 4px;}
	</style>
</head>
<body>
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-tjfx" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-tjfx-afl" />
	<!-- 导航辅助 结束 -->

	<section>
		<div class="content">
			<div id="container" style="width:100%;height:100%"
				data-highcharts-chart="0"></div>
		</div>
	</section>
	<script src="${pageContext.request.contextPath}/static/js/highstock.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/exporting.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/highcharts-zh_CN.js"></script>
	
	<script type="text/javascript">
	$(function () {
		$('.form_date').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  0,
			autoclose: 1,
			todayHighlight: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
   		 });
    	$('.form_date2').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  0,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
   		 });
    
	//初始化图标
    	var data=[];
	    var chart1 = new Highcharts.StockChart('container',{
	    	 rangeSelector : {
	                selected : 1
	            },
	            title : {
	                text : '案发量统计'
	            },
	            series : [{
	            	id:'exSeries',
	                name : 'AAPL',
	                data : data,
	                tooltip: {
	                    valueDecimals: 2
	                }
	            }]
	    })
			    //发起ajax请求更改数据
    	$.getJSON(url_prefix+'/statistics/issueCount', function (response){
    		var data=[];
    		var jsonObj = $.parseJSON(response)
    		jsonObj.forEach(function(item,index){
    			var arr=[];
    			arr.push(item.days);
    			arr.push(item.counts);
    			data.push(arr);
    		})
	       chart1.series[0].setData(data);
	       console.log(data);
    	});
	})	
	</script>

</body>
</html>