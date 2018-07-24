//	地图服务地址判断
//	0. 默认用58 互联网地图服务；
//	1. 如果是10段访问，则用10段的地图服务；
//	2. 如果是172段访问，则用172段的地图服务；

// 172段映射的外网IP
var hostPort = "58.213.148.61:8083";
var map_dt_url = "http://" + hostPort + "/GZ_DT_9_20/wmts";
var map_dt_lyr_name = 'gz_dt_9_20';
var map_zj_url = "http://" + hostPort + "/GZ_ZJ_9_20/wmts";
var map_zj_lyr_name = 'gz_zj_9_20';

if(window.location.host.startsWith("10.")){
	hostPort = "10.10.0.166:7010";
	map_dt_url = "http://" + hostPort + "/GZ_DT_10_20/wmts";
	map_dt_lyr_name = 'gz_dt_10_20';
	map_zj_url = "http://" + hostPort + "/GZ_ZJ_10_20/wmts";
	map_zj_lyr_name = 'gz_zj_10_20';
}else if(window.location.host.startsWith("172.")){
	hostPort = "172.16.225.38:7003";
}

//var hostPort = "58.213.23.212:7003";
//var map_dt_url = "http://" + hostPort + "/NJDLG_DT/wmts";
//var map_dt_lyr_name = 'NJDLG_DT_10_20';
//var map_zj_url = "http://" + hostPort + "/NJDLG_ZJ/wmts";
//var map_zj_lyr_name = 'NJDLG_ZJ_10_20';


