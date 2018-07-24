<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

	<title>街道办结案件统计</title>

	<script type="text/javascript">
		var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
		var url_prefix = '${pageContext.request.contextPath}';
	</script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css" />

</head>
<body>
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-tjfx" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-tjfx-jdbjatj" />
	<!-- 导航辅助 结束 -->
	
	<section>
				<div class="content">
					<div class="filter">
						<form action="">
							<div class="form-group bs_date">
				                <label for="dtp_input1" class=" control-label">时间范围</label>
				                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1" data-link-format="yyyy-mm-dd">
				                    <input class="form-control" size="16" type="text" value="" readonly>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
								<input type="hidden" id="dtp_input1" value="" /><br/>
				        	</div>
							<div class="form-group bs_date">
					                <label for="dtp_input2" class=" control-label">至</label>
					                <div class="input-group date form_date2" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
					                    <input class="form-control" size="16" type="text" value="" readonly>
										<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					                </div>
									<input type="hidden" id="dtp_input2" value="" /><br/>
					        </div>
						<button id='findDate' type='button'>查询</button>
						</form>
					</div>
					<div id="container" style="width:100%;height:100%" data-highcharts-chart="0">
						
					</div>
				</div>
			</section>
	

	<script src="${pageContext.request.contextPath}/static/js/highstock.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/exporting.js"></script>
<!-- 	<script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script> -->
	<script src='${pageContext.request.contextPath}/static/js/new.js'></script>
	<script>
	$(function () {
		var categories=[];
    	//初始化图标
    	var data=[];
	    var chart1 = new Highcharts.Chart('container',{
		     chart: {
	            type: 'column'
	        },
	        title: {
	            text: '街道办结案统计'
	        },
	        xAxis: {
	            categories: categories,
	            crosshair: true
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '案件数 (件)'
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f} 件</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: [{
	            name: '案件总数',
	            data: []
	        }, {
	            name: '已办结数',
	            data: []
	        }]
   		});
	    function chartRefresh(){
	    	$.ajax({
				url:url_prefix+'/statistics/streetFinishedIssue',
				async: false,
				method: 'post',
				dataType : 'json',
				data:{
					startDay:$('#dtp_input1').val(),
					endDay:$('#dtp_input2').val()
				},
				success: function(result){
					var data=[];
                    var jsonObj = $.parseJSON(result);
					jsonObj.forEach(function(item,index){
						data.push(item.counts);
						categories.push(item.qhmc);
					});
					console.log(data)
					chart1.series[1].setData(data);
				}
			});
	    	$.getJSON(url_prefix+'/statistics/streetIssue',
	    			{startDay:$('#dtp_input1').val(),endDay:$('#dtp_input2').val()}, 
	    			function(response){
	    				var data=[];
	    				var jsonObj = $.parseJSON(response)
						jsonObj.forEach(function(item,index){
							data.push(item.counts);
						});
						console.log(data)
						chart1.series[0].setData(data);
	    			});
	    };	
	    chartRefresh();
	    $('#findDate').click(chartRefresh);
	    
});
	
	</script>
</body>
</html>