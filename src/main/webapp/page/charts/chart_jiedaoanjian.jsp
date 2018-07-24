<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

	<title>街道案件统计</title>

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
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-tjfx-jdajtj" />
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
		    	//初始化图标
		    	var data=[];
			    var chart1 = new Highcharts.Chart('container',{
						chart: {
		            		type: 'column'
				        },
				        title: {
				            text: '街道案件统计'
				        },
				        subtitle: {
				            text: ''
				        },
				        xAxis: {
				            type: 'category',
				            labels: {
				                rotation: -45,
				                style: {
				                    fontSize: '13px',
				                    fontFamily: 'Verdana, sans-serif'
				                }
				            }
				        },
				        yAxis: {
				            min: 0,
				            title: {
				                text: '数量(件)'
				            }
				        },
				        legend: {
				            enabled: false
				        },
				        tooltip: {
				            pointFormat: '案发量: <b>{point.y:.1f} 件</b>'
				        },
				        series: [{
				            name: '总案件',
				            data: [
				            ],
				            dataLabels: {
				                enabled: true,
				                rotation: -90,
				                color: '#FFFFFF',
				                align: 'right',
				                format: '{point.y:.1f}', // one decimal
				                y: 10, // 10 pixels down from the top
				                style: {
				                    fontSize: '13px',
				                    fontFamily: 'Verdana, sans-serif'
				                }
				            }
				        }]
				
				});
			    function chartRefresh(){
			    	$.ajax({
						url:url_prefix+'/statistics/streetIssue',
						async: false,
						method: 'post',
						dataType : 'json',
						data:{
							startDay:$('#dtp_input1').val(),
							endDay:$('#dtp_input2').val()
						},
						success: function(result){
							console.log(result);
							var data=[];
                            var jsonObj = $.parseJSON(result);
							jsonObj.forEach(function(item,index){
								var obj=[];
								obj[1]=item.counts;
								obj[0]=item.qhmc;
								data.push(obj);
							});
							console.log(data)
							chart1.series[0].setData(data);
						}
					});
			    };	
			    chartRefresh();
			    $('#findDate').click(chartRefresh);
		});
	</script>
</body>
</html>