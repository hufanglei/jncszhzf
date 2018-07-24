<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>天地图南京底图加载Demo</title>

	<link href="${pageContext.request.contextPath}/static/css/inc-style.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/enterprise-style.css" rel="stylesheet" type="text/css">
	<style type="text/css">


		/* 地图网格全局样式 */
		*{ margin:0; padding:0; list-style:none; font-family:"微软雅黑","Microsoft YaHei";}
		body{ padding:0; margin:0; font-size:12px;background:#fff; }
		ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,form,p,input{ padding:0; margin:0;}
		ul{list-style:none;}
		img{border:0px;}
		.clearfloat{clear:both; height:0px; font-size:1px; line-height:0px;}
		input,button{font-family: 微软雅黑, "Microsoft YaHei";}
		a,a:hover,a:focus{text-decoration:none}
		ul,dl{height:100%}

	</style>
</head>

<body>
<div class="wrapper">
	<div class="newmap" id="map"></div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/gis_api_utils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/OpenLayers/OpenLayers-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/GeoGlobeSDK/GeoGlobeJS.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/NewForSDK/OpenLayerMap.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/ajfb.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/enterprise-script.js"></script>
</body>
</html>
