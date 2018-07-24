// gisapi 部署方案（以  gisapi321 作为根目录为例）
// - 在gisapi项目中的 init.js  和   dojo/dojo.js 中分别搜索【async:0,baseUrl:】 ，将其后的值修改为 【 _gis_base_location + "dojo" 】
// - 与业务项目独立部署在相同主机（host:port）；
// - 在业务项目中需要引用gisapi的页面中引用此js文件（需放在调用gisapi的js之前）;

// gis api webapp 基路径
var _gis_api_webapp = '/gisapi321/';
// gis api webapp 基路径完整URL
// 如业务项目与gisapi部署在不同域上，可修_gis_base_location 为gisapi的部署域，如 "http://10.0.13.48:8080"
//var _gis_api_domain = 'http://218.94.120.225:15347';

var _gis_api_domain = 'http://58.213.148.61:8888';
if(window.location.host.startsWith("10.")){
	_gis_api_domain = 'http://10.10.0.215:8888';
}else if(window.location.host.startsWith("172.")){
	_gis_api_domain = 'http://172.16.225.37:8888';
}

var _gis_base_location = ((_gis_api_domain == null)?(window.location.protocol + "//" + window.location.host):_gis_api_domain) + _gis_api_webapp;
function _gis_load_func(){
	
	// gis api css 样式引入
	var css_inc = "esri/css/esri.css";
	var style_path = '<' + 'link type="text/css" rel="stylesheet" href="'+ _gis_base_location + css_inc + '"' + ' />';
	document.writeln(style_path);

	// gis api init.js 引入（api加载）
	var js_inc = "init.js";
	var js_path = '<' + 'script type="text/javascript" src="' + _gis_base_location + js_inc + '"' + '><' + '/script>';
	document.writeln(js_path);
};

_gis_load_func();