<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

	<title>地图</title>

	<script type="text/javascript">
		var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
		var url_prefix = '${pageContext.request.contextPath}';
	</script>
	<link rel="stylesheet" href="http://218.94.120.225:15347/gisapi321/esri/css/esri.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/static/css/map/map.css" />
	<link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/static/css/bootstrap.min.css'/>
	
	<script type="text/javascript">
		var url_prefix = '${pageContext.request.contextPath}';
	</script>
	<script type="text/javascript" src="http://218.94.120.225:15347/gisapi321/init.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/map.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<style type="text/css">

	</style>
</head>
<body>
	<div data-dojo-type="dijit/layout/BorderContainer"
		data-dojo-props="design:'headline', gutters:false"
		style="width: 100%; height: 100%; margin: 0;">

		<div id="map" data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'center'">
			<div id='map-click-info' >
				<div id="location">
					<span>X :</span><span class="val" id='xVal'> --.---- </span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span>Y :</span><span class="val" id='yVal'> --.---- </span>
				</div>

				<button class='btn btn-success'> 确定 </button>
				<button class='btn btn-danger'> 取消 </button>
			</div>
		</div>

	</div>

</body>
</html>