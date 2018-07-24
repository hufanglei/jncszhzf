<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<title>轨迹追踪</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/map/map.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/toastr.css" />


<style type="text/css">
.info-window-col-title {
	width: 28%;
	background: #e9f3f4;
	text-align: right;
	padding-right: 25px;
}

.info-window-col-content {
	text-align: left;
	padding-left: 25px;
	background-color: #fff;
}

#map-wrapper {
	visibility: visible;
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
	.col-sm-4{ width: 32%;}
	.col-sm-4:nth-child(4){ width: 8%;}
	.form-horizontal .form-group {
	    display:flex;
	}
	.form-horizontal .form-group select{width:68%}
	.biaoti{   
			 height: 34px !important;
    		color:#2d2e2f !important;
    		width: 32%;
    		line-height: 34px;
    		font-weight:bolder;
    		font-size:15px;
    	}
    .form-group{
    	margin-bottom:0;margin:10px 0;
    }
	#btLoadTrace{margin-left:20px}
}
</style>

</head>
<body style="margin: 0;">
	<!-- 导航辅助 开始 -->
	<!-- 一级标签选中 -->
	<input id="nav-flag-top" type="hidden" value="nav-flag-top-dtfx" />
	<!-- 二级标签选中 -->
	<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-dtfx-trace" />
	<!-- 导航辅助 结束 -->
	<div id='map-wrapper'>
		<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'headline', gutters:false" style="width: 100%; height: 100%; margin: 0; padding: 0;">
			<div id="map" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'">
				<div class="tab-content" id='tab-sel'>
					<div role="tabpanel" class="tab-pane active">
						<form class='form-horizontal clearfix' id="frmSel" method="post"
							enctype="multipart/form-data">
							<div class="col clearfix">
								<div class="form-group col-sm-4">
									<div class="biaoti">选择街道</div>
									<select id="sel-jiedao" class='form-control'>
									</select>
								</div>
								<div class="form-group col-sm-4">
									<div class="biaoti">选择网格员</div>
									<select id="sel-wgy" class='form-control'>
									</select>
								</div>
								<div class="form-group col-sm-4">
									<button type="button" id="btLoadTrace" class='btn btn-success'>人员定位</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var url_prefix = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.8.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/toastr.js"></script>
    <script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/utils.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/map/gis_api_utils.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/map/layers/WFSLayer.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/map/trace.js"></script>
	<script type="text/javascript">
		$.ajax({
			url: url_prefix + '/qhzd/selectQhzdByAuthority.action',
			dataType : 'json',
			success:function(result){
				var data = result.rows;
                $("#sel-jiedao").empty();
                $("#sel-wgy").empty();
                $("#sel-jiedao").append("<option value=''>——请选择——</option>");
                $("#sel-wgy").append("<option value=''>——请选择——</option>");
				for(var i in data){
					$("#sel-jiedao").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
				}
			}
		});
		
		$("#sel-jiedao").on("change",function(){
            $("#sel-wgy").empty();
            map.graphics.clear()
			$.ajax({
				url:url_prefix + '/user/selectUserByMenberShipId.action?groupId='+$("#sel-jiedao").val(),
				dataType : 'json',
				success: function(result){
					$("#sel-wgy").empty();
					if(result !=null && result.length>0) {
                        $("#sel-wgy").append("<option value=''>——全部——</option>");
                        for (var i in result) {
                            $("#sel-wgy").append("<option value='" + result[i].id + "'>" + result[i].userName + "</option>");
                        }
                    }else{
                        $("#sel-wgy").append("<option value='-1'>——暂无网格员——</option>");
					}
				}
			});
		});
	</script>
</body>
</html>
