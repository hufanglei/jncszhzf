<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<title>案件热力地图</title>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/static/css/map/map.css" />
		<style type="text/css">
            #map {
                width:100%;
                height:100%;
                margin: 0;
                padding: 0;
            }
			#heatmapArea {
			    position: absolute;
			    /*float: left;*/
			    width: 100%;
			    top: 60px;
			    bottom: 0;
			}
		</style>

</head>
<body>
	<!-- 导航辅助 开始 -->
		<!-- 一级标签选中 -->
		<input id="nav-flag-top" type="hidden" value="nav-flag-top-dtfx" />
		<!-- 二级标签选中 -->
		<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-dtfx-heat" />
	<!-- 导航辅助 结束 -->
	<div id="heatmapArea">
        <div id="heatLayer"></div>
        <div id="map"></div>
	</div>

	<script type="text/javascript">
		var url_prefix = '${pageContext.request.contextPath}';
	</script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/gis_api_utils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WFSLayer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/heatmap/heatmap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/heatmap/heatmap-arcgis.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/heat.js"></script>
	</body>
</html>
