<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<title>案件地图分布</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/map/map.css" />
	<link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>

<style type="text/css">
	.info-window-col-title {
		width: 40%;
		background: #e9f3f4;
		font-weight: bolder;
		text-align: right;
		margin: 10px 5px;
		padding: 5px 8px;
	}

	.info-window-col-content {
		text-align: left;
		padding-left: 10px;
		background-color: #fff;
	}

	#tab-sel {
		color: #000;
		position: absolute;
		text-align: center;
		top: 51px;
		z-index: 999;
		width: 90%;
		left:50%;
		-webkit-transform: translateX(-50%) translateY(-50%);
		-moz-transform: translateX(-50%) translateY(-50%);
		-ms-transform: translateX(-50%) translateY(-50%);
		transform: translateX(-50%) translateY(-50%);
		padding: 5px 20px;
		background: rgba(158, 158, 158, .6);
		border-radius:6px;
	}


	@media (min-width: 768px) {
		.biaoti{
			height: 34px !important;
			color:#2d2e2f !important;
			line-height: 34px;
			font-weight:bolder;
			font-size:15px;
			padding:0px;
		}
		.form-group{
			margin-bottom:0;margin:10px 0;
		}
		#btLoadTrace{margin-left:60px;width:99px;}
	}
</style>

</head>
<body>
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-dtfx" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-dtfx-ajfb" />

	<!-- 导航辅助 结束 -->
	<!--左侧树-->
	<div id="treeview" class="" style="width: 20%;"></div>
	<!--右侧地图-->
	<div id='map-wrapper' style=" width:80%;left: 22%;">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'headline', gutters:false"
			style="width: 100%; height: 100%; margin: 0;float: right">

			<div id="map" data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'">

				<div class="tab-content" id='tab-sel'>
					<div role="tabpanel" class="tab-pane active">
						<form class='form-horizontal clearfix' id="frmSel" method="post" enctype="multipart/form-data">
							<div class="col clearfix">
								<div class="form-group col-sm-5" style="padding-right:60px;">
									<div class="biaoti" style="float: left">日期起</div>
									<div  style="float: left;width:80%;margin-left: 15px;" class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
										<input class="form-control" size="10" type="text" id="beginDate">
										<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									</div>
								</div>
								<div class="form-group col-sm-5" style="padding-right:60px;">
									<div class="biaoti" style="float: left">日期止</div>
									<div style="float: left;width:80%;margin-left: 15px;" class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1" data-link-format="yyyy-mm-dd">
										<input class="form-control" size="10" type="text" id="endDate">
										<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									</div>
								</div>
								<div class="form-group col-sm-2" style="padding-top:3rem">
									<button type="button" id="btLoadTrace" class='btn btn-success'>查询</button>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div class="wrap modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class=" modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						<div class="bianhao" style='float: left;'>案件处理：<span>{{ajDetail.issueSubject}}</span></div>
					</h4>
				</div>
				<div class="modal-body" id="dbrw-body">
					<div class="shang clearfix">
						<div class="infomation" style="width: 100%">
							<div class="tittle">
								<i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
							</div>
							<div class="specific" style='overflow-y:auto ;height: 155px;'>
								<table class="table">
									<tr>
										<td>案件来源</td>
										<td>{{ajDetail.issueSource}}</td>
										<td>当事人类型</td>
										<td>{{ajDetail.requestorType}}</td>
									</tr>
									<tr>
										<td>涉事企业名称</td>
										<td>{{ajDetail.refCompanyName}}</td>
										<td>当事人姓名</td>
										<td>{{ajDetail.requestorName}}</td>
									</tr>
									<tr>
										<td>当事人电话</td>
										<td>{{ajDetail.requestorTel}}</td>
										<td>当事人地址</td>
										<td>{{ajDetail.requestorAddress}}</td>
									</tr>
									<tr>
										<td>证件号码</td>
										<td>{{ajDetail.requestorIdcard}}</td>
										<td>涉事企业组织机构代码</td>
										<td>{{ajDetail.orgId}}</td>
									</tr>
									<tr>
										<td>事发时间</td>
										<td>{{ajDetail.issueTime}}</td>
										<td>处置时限</td>
										<td>{{ajDetail.timeLimit}}</td>
									</tr>
									<tr>
										<td>权利编码</td>
										<td>{{ajDetail.issueType}}</td>
										<td>地图坐标</td>
										<td>{{ajDetail.x}},{{ajDetail.y}}</td>
									</tr>
									<tr>
										<td>地址描述</td>
										<td>{{ajDetail.addrDesc}}</td>
										<td>事件主题</td>
										<td>{{ajDetail.issueSubject}}</td>
									</tr>
									<tr>
										<td>事件描述</td>
										<td>{{ajDetail.issueDesc}}</td>
										<td>紧急程度</td>
										<td>{{ajDetail.emrgencyLevel_ms}}</td>
									</tr>
									<tr>
										<td>备注</td>
										<td>{{ajDetail.comment}}</td>
										<td style="background-color: #fff;"></td>
										<td></td>
									</tr>
								</table>
							</div>
						</div>

						<%--<div class="jindu">
							<div class="tittle">
								<i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>处理流程
								<div class='date'>总耗时
									<time>{{(timeTaken/1000/60/60).toFixed(2)}}</time>
									小时 约<span>{{(timeTaken/1000/60/60/24).toFixed(1)}}</span>工作日
								</div>
							</div>
							<div class="specific" style='overflow-y:auto ;height: 155px;'>
								<table class="table">
									<tr v-for="(item,index) in speedOfProgress">

										<td>
											<div class="left">
												<div class="text">{{item.activityName}}</div>
												<i>{{item.assignee}}</i>
												<span v-if='index!=(speedOfProgress.length-1)' class='dec'></span>
											</div>
										</td>
										<td>
											<div class="shuju">
												<div v-if='item.startTime' class="bumen"><span>开始时间：</span>{{item.startTime}}
												</div>
												<div v-if='item.durationInMillis && parseInt(item.durationInMillis/1000/60) > 0'
													 class="haoshi"><span>耗时</span>：<s>
													{{parseInt(item.durationInMillis/1000/60)}} </s> 分钟 约 <u>{{(item.durationInMillis
													/ (1000 * 60 * 60 * 24)).toFixed(1)}}</u> 工作日
												</div>
												<div v-if='item.endTime' class="zhuban"><span>结束时间：</span>{{item.endTime}}
												</div>
												<div v-if='item.xieban' class="xieban"><span>协办部门：</span>{{item.xieban}}
												</div>
												<div v-if='item.shixian' class="shixian"><span>处置时限：</span>{{item.shixian}}
												</div>
												<div v-if='item.dasima' class="shixian"><span>处置时限：</span>{{item.dasima}}
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>--%>
					</div>
					<div id='dbrw-cl'>

					</div>
				</div>
				<div class="modal-footer" style='max-height:150px;overflow-y:auto'>
					<table class='table text-center' style='border-radius:4px'>
						<thead>
						<tr>
							<th class='col-sm-2'>办理人</th>
							<th class='col-sm-2'>办理部门</th>
							<th class='col-sm-2'>办理方式</th>
							<th class='col-sm-2'>办理意见</th>
							<th class='col-sm-2'>办理时间</th>
							<th class='col-sm-2'>耗费时间</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for='item in commentDetail' v-if='commentDetail[0]!=null'>
							<td class='col-sm-2'>{{item.userName}}</td>
							<td class='col-sm-2'>{{item.department}}</td>
							<td class='col-sm-2'>{{item.handleWay}}</td>
							<td class='col-sm-2' data-toggle="tooltip" data-placement="top"
								onMouseOver="$(this).tooltip('show')" :title="item.fullMessage">{{item.comment}}
							</td>
							<td class='col-sm-2'>{{item.endTime}}</td>
							<td class='col-sm-2'>{{item.consumTime}}</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var url_prefix = '${pageContext.request.contextPath}';
        $('.form_date').datetimepicker({
            // weekStart: 1,
            autoclose: true,
            // startView: 3,
            minView: 2,
            // forceParse: false,
            language: 'zh-CN'
        });
	</script>
	<script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>

	<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
	<script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
	<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/gis_api_utils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WFSLayer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/ajfb.js"></script>
</body>
</html>
