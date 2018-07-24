<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<title>案件状态占比统计</title>

<script type="text/javascript">
		var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
		var url_prefix = '${pageContext.request.contextPath}';
	</script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/chart/chart.css" />

</head>
<body>
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-tjfx" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-tjfx-ajzt" />
	<!-- 导航辅助 结束 -->

	<section>
	<div class="content">
		<div class="filter">
			<form action="">
				<div class="form-group bs_date">
					<label for="dtp_input1" class=" control-label">时间范围</label>
					<div class="input-group date form_date" data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text" value=""
							readonly> <span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div>
					<input type="hidden" id="dtp_input1" value="" /><br />
				</div>
				<div class="form-group bs_date">
					<label for="dtp_input2" class=" control-label">至</label>
					<div class="input-group date form_date2" data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text" value=""
							readonly> <span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span></span>
					</div>
					<input type="hidden" id="dtp_input2" value="" /><br />
				</div>
				<div class="form-group select first">
					<label class='control-label'
						style='font-weight: normal; float: left; padding-right: 10px;'>案件来源</label>
					<div class="" style='float: left;'>
						<select name="" id="" class='form-control'>
							<code:options codeName="ajly" codeId="${param.ajly}" />
						</select>
					</div>
				</div>
				<div class="form-group select">
					<label class='control-label'
						style='font-weight: normal; float: left; padding-right: 10px;'>处置部门</label>
					<div class="" style='float: left;'>
						<select name="" id="deal-dept" class='form-control'>
						</select>
					</div>
				</div>
				<div class="form-group select">
					<label class='control-label'
						style='font-weight: normal; float: left; padding-right: 10px;'>事项归口</label>
					<div class="" style='float: left;'>
						<select name="" id="issue-belong" class='form-control'>
						</select>
					</div>
				</div>
				<button id='findDate' type='button'>查询</button>
			</form>
		</div>
		<div id="container" style="width: 100%; height: 100%"
			data-highcharts-chart="0"></div>
	</div>
	</section>

	<script src="${pageContext.request.contextPath}/static/js/highstock.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/exporting.js"></script>
	<script src='${pageContext.request.contextPath}/static/js/new.js'></script>
	<script type="text/javascript">
	$(function () {
		//初始化图标
    	var data=[];
	    var chart1 = new Highcharts.Chart('container',{
	    	chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
				        },
				    title: {
				        text: '案件状态占比统计'
				    },
				    tooltip: {
				        headerFormat: '{series.name}<br>',
				        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
				    },
				    plotOptions: {
				        pie: {
				            allowPointSelect: true,
				            cursor: 'pointer',
				            dataLabels: {
				                enabled: true,
				                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
				                style: {
				                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				               		}
				            	}
				            	,showInLegend: true
				                
				        	}
				    },
				    series: [{
				        type: 'pie',
				        name: '浏览器访问量占比',
				        data: [
				            ['未开始',   45.0],
				            ['已受理',       26.8],
				            {
				                name: '快完成',
				                y: 12.8,
				                sliced: true,
				                selected: true
				            },
				           	['待审批',    8.5],
				            ['已结案',     6.2],
				            ['未结案',   0.7]
				            ]
				        }]
		    	})
			    function chartRefresh(){
			    	$.ajax({
						url:url_prefix+'/statistics/issueStatusPercent',
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
								var arr=[];
								arr.push(item.issue_status);
								arr.push(item.percents);
								data.push(arr);
							});
							chart1.series[0].setData(data);
						}
				   });
			   };	
			   chartRefresh();
		       $('#findDate').click(chartRefresh);
			   ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#deal-dept"),"id","wbjName",null);
			   ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#issue-belong"),"id","wbjName",'局');
    });  
</script>
</body>
</html>