/**
 * 地图分析-案件分布
 */

/**
 * 地图选取点位控制
 */
var map, wmtsDtLayer, wmtsZjLayer;
var markerUrl = url_prefix + "/static/images/map/marker-gold.png";
var markerPoint = null;
var marker_wid = 36;
var marker_hei = 36;
var curPoint={x:null,y:null};
var issuse_url = url_prefix + '/issues/listTMainIssueForMap.action';
var groupId = "";
var groupTypeId = "";

// if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
// 	// marker_wid = 18; marker_hei = 18;
// }

require([ "esri/map", "esri/layers/WMTSLayer","esri/layers/WFSLayer", "esri/layers/WMTSLayerInfo",
		"esri/geometry/Extent", "esri/layers/TileInfo",
		"esri/SpatialReference", "dojo/parser",
		"dijit/layout/BorderContainer", "dijit/layout/ContentPane",
		"esri/InfoTemplate","esri/dijit/InfoWindow",
		"dojo/domReady!"
	], function(Map, WMTSLayer,WFSLayer, WMTSLayerInfo,Extent, TileInfo, SpatialReference, parser
	) {

	parser.parse();
	
	var bounds = new Extent({
		"xmin" : 118.799602812075,
		"ymin" : 31.932281322199998,
		"xmax" : 118.881270057075,
		"ymax" : 31.972192272199997,
		"spatialReference" : {
			"wkid" : 4490
		}
	});
	
	map = new Map("map", {
		extent : bounds
	});

	wmtsDtLayerOption = getWMTSLayerOptionByURL(map_dt_lyr_name,'Matrix_2');
	wmtsDtLayer = new WMTSLayer(map_dt_url,wmtsDtLayerOption);
	map.addLayer(wmtsDtLayer);

	wmtsZjLayerOption = getWMTSLayerOptionByURL(map_zj_lyr_name,'Matrix_2');
	wmtsZjLayer = new WMTSLayer(map_zj_url,wmtsZjLayerOption);
	map.addLayer(wmtsZjLayer);

	/**
	 * 根据identifier获取WMTS图层的 option 参数
	 */
	function getWMTSLayerOptionByURL(identifier,tileMatrixSet){
		var tileInfo = new TileInfo({
			"dpi" : 96,
			"format" : "tiles",
			"compressionQuality" : 0,
			"spatialReference" : new SpatialReference({
				"wkid" : 4490
			}),
			"rows" : 256,
			"cols" : 256,
			"origin" : {
				"x" : -180-0.000598766096,
				"y" : 90+0.00011182483968
			},
			"lods" : [ {'level':9,'resolution':0.00274966563497472,'scale':1155583.41974439},
				  { "level": 10, "resolution": 0.0013732937596622429, "scale": 577791.7098721985 },
                  { "level": 11, "resolution": 0.00068664687983112144, "scale": 288895.85493609926 },
                  { "level": 12, "resolution": 0.00034332343991556072, "scale": 144447.92746804963 },
                  { "level": 13, "resolution": 0.00017166171995778036, "scale": 72223.96373402482 },
                  { "level": 14, "resolution": 0.00008583085997889018, "scale": 36111.98186701241 },
                  { "level": 15, "resolution": 0.00004291542998944509, "scale": 18055.990933506204 },
                  { "level": 16, "resolution": 0.000021457714994722545, "scale": 9027.995466753102 },
                  { "level": 17, "resolution": 0.000010728857497361272, "scale": 4513.997733376551 },
                  { "level": 18, "resolution": 0.0000053644287486806362, "scale": 2256.9988666882755 },
                  { "level": 19, "resolution": 0.0000026822143743403181, "scale": 1128.4994333441377 },
                  { "level": 20, "resolution": 0.0000013411071871701591, "scale": 564.2497166720689 } ]
		});
		
		var tileExtent = new Extent(118.125000, 30.937500, 119.531250, 33.046875,
				new SpatialReference({
					wkid : 4490
				}));
		
		var layerInfo = new WMTSLayerInfo({
			tileInfo : tileInfo,
			fullExtent : tileExtent,
			initialExtent : tileExtent,
			identifier : identifier, // "NJDLG_DT_10_20",
			tileMatrixSet : tileMatrixSet, // "Matrix_0",
			format : "image/png",
			style : "default"
		});

		var resourceInfo = {
			version : "1.0.0",
			layerInfos : [ layerInfo ],
			copyright : "天地图"
		};

		var options = {
			serviceMode : "KVP",
			resourceInfo : resourceInfo,
			layerInfo : layerInfo
		};
		
		return options;
	}


    initMap();

    dojo.connect(map.graphics, "onClick", function (obj) {
        showGdgxPage(obj.graphic.attributes);
    });
    esriConfig.defaults.io.proxyUrl = "/proxy.jsp";
    var url = "http://58.213.148.61:8083/FCS_JN/wfs";
    var opts = {
        "url": url,
        "name": "全要素网格",
        "version":'1.0.0',
        "wkid":4490,
        "maxFeatures": 20000,
    };
    var layer = new WFSLayer();

    layer.fromJson(opts);

    map.addLayer(layer);

});



function addMarker(mapPoint,attr,infoTemplate){
	var symbol = new esri.symbol.PictureMarkerSymbol(markerUrl, marker_wid, marker_hei);
	var pt = new esri.geometry.Point(mapPoint.x,mapPoint.y, map.spatialReference);
	var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);
	map.graphics.add(graphic);
};

var result ={"total":0,"rows":[{"addTime":"2017-12-26 11:47:04","addUserTel":"15148249317","addUserid":"dssq02","addrDesc":"","belongWeibanjuId":"bureau_LY,bureau_NY","comment":"东方时空l","d1":"","dealWay":"","emrgencyLevel":"01","emrgencyLevel_ms":"一般","endTime":"","evaluationLevel":"","evaluationStatus":"","hasModLimit":"","id":379,"isArchived":"","isMultiExector":"","isOpen":"","isPassDistrictcenter":"1","issueDesc":"电风扇jioafio","issueExecDept":"bureau_LY,bureau_NY","issueNumber":"320115001002171226114704","issueSource":"01","issueSource_ms":"12345","issueSubject":"新流程测试02","issueTime":"2017-12-26 11:46:44","issueType":"JS011500HB-CF-0003","orgId":"","processId":"845001","refCompanyName":"","requestorAddress":"第三方","requestorAge":"","requestorIdcard":"12415","requestorName":"小王","requestorSex":"","requestorTel":"1215","requestorType":"01","requestorType_ms":"个人","status":"6","status_ms":"待主管部门派遣","timeLimit":"","timeLimit_ms":"","whoReported":"1","x":"118.8317447","y":"31.9565713"},{"addTime":"2017-12-26 11:47:04","addUserTel":"15148249317","addUserid":"dssq02","addrDesc":"","belongWeibanjuId":"bureau_LY,bureau_NY","comment":"东方时空l","d1":"","dealWay":"","emrgencyLevel":"01","emrgencyLevel_ms":"一般","endTime":"","evaluationLevel":"","evaluationStatus":"","hasModLimit":"","id":379,"isArchived":"","isMultiExector":"","isOpen":"","isPassDistrictcenter":"1","issueDesc":"电风扇jioafio","issueExecDept":"bureau_LY,bureau_NY","issueNumber":"320115001002171226114704","issueSource":"01","issueSource_ms":"12345","issueSubject":"新流程测试02","issueTime":"2017-12-26 11:46:44","issueType":"JS011500HB-CF-0003","orgId":"","processId":"845001","refCompanyName":"","requestorAddress":"第三方","requestorAge":"","requestorIdcard":"12415","requestorName":"小王","requestorSex":"","requestorTel":"1215","requestorType":"01","requestorType_ms":"个人","status":"6","status_ms":"待主管部门派遣","timeLimit":"","timeLimit_ms":"","whoReported":"1","x":"118.83378910458943","y":"31.92358986716634"}]}

function initMap(){
    let date = new Date();
    let month = date.getMonth()+1;
     month = month<10 ? ('0'+month) : month;
    let beginDate = (date.getFullYear()+"-"+ month +"-01");
    let lastDate = new Date(date.getFullYear(), month, 0)
    let endDate = date.getFullYear()+"-"+month+"-"+lastDate.getDate();
    $("#beginDate").val(beginDate)
    $("#endDate").val(endDate)

    $("#map-wrapper").css('visibility','visible');
    map.graphics.clear();

    queryMap(beginDate,endDate)

}
$("#btLoadTrace").on("click",function(){
    map.graphics.clear();
    queryMap($("#beginDate").val(),$("#endDate").val())
});

function queryMap(beginDate,endDate){
    $.ajax({
        type : "post",
        url : url_prefix+"/issues/queryIssueMap.action",
        data : {
            'beginDate':beginDate,
            'endDate':endDate,
            'groupId':groupId,
            'groupTypeId':groupTypeId
        },
        dataType : "json",
        async : false,
        success : function(data) {
            console.log(data);
            if (null != data) {
                if(data.length==0) {
                    layerAlert("暂无数据");
                }else{
                    for ( var item in data) {
                        addMarker({x:data[item].x,y:data[item].y},data[item].pid,null);
                    }
                }
            }
        }
    });
}


var myapp = new Vue({
	el:'#myModal',
	data:{
        bianhao : {},
        ajDetail : [],
        commentDetail:[],
        speedOfProgress:[],
        timeTaken:0
	}
})


function showGdgxPage(processId) {
    // 根据taskId 查询案件信息
    var query_issue_by_processId = '/issues/getIssueByProcessId.action';
    // 根据taskId获得点击的批注详情
    var commentDetail='/task/listHistoryCommentDetailByProcessId.action?processId=';
    //根据taskId获取流程图
    var speedOfProgress='/task/listActionByProcessId.action?processId=';
    $.ajax({
        method : 'post',
        url : url_prefix + query_issue_by_processId,
        dataType : 'json',
        data : {
            processId : processId
        },
        success : function(data) {
            // 填充案件详情数据
            data =(typeof data=="object")?data:$.parseJSON(data);
            myapp.ajDetail = data.tMainIssueBean;
            myapp.ajstatus = myapp.ajDetail.status;
            $("#myModal").modal({backdrop: 'static'}).show();
        }
    });
    $.ajax({
        method: 'get',
        url: url_prefix + commentDetail + processId,
        dataType: 'json',
        success: function (data) {
            //初始化提示框
            data = (typeof data=="object")?data:$.parseJSON(data)
            myapp.commentDetail = data.rows;
            $('[data-toggle="tooltip"]').tooltip( "destroy" );
            $('[data-toggle="tooltip"]').tooltip();
        }
    });
    myapp.$http.get(url_prefix + speedOfProgress + processId).then(
        function(response) {
        	var data = (typeof response.data=="object")?response.data:$.parseJSON(response.data)
            myapp.speedOfProgress=data.rows;
            var startTime=new Date(myapp.speedOfProgress[0].startTime);
            var endTime;
            if(myapp.speedOfProgress[myapp.speedOfProgress.length-1].endTime){
                endTime=new Date(myapp.speedOfProgress[myapp.speedOfProgress.length-1].endTime);
            }
            else endTime=new Date();
            myapp.timeTaken=endTime-startTime;
            Vue.nextTick(function(){
                $('.dec').each(function(index,dom){
                    var shart=setInterval(function(){
                        if($('.shuju').height()>0){
                            $(dom).height($(dom).parent().parent().innerHeight()-$(dom).parent().children('.text').innerHeight()+'px');
                            $(dom).css('bottom','-'+$(dom).css('height'));
                            clearInterval(shart);
                        }
                    },50)
                })
            })
        });
};

var ajfb = {
    initTree: function () {
        var _this = this;
        $.ajax({
            method: 'post',
            url: url_prefix + '/archiveOfGroup',
            data: {
                usrId: userId
            },
            dataType: 'json',
            success: function (results) {
                console.log(results);
                var defaultData = _this.createTree(results.result, 0);
                $('#treeview').treeview({
                    color: "#428bca",
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    data: defaultData,
                    onNodeSelected: function (event, data) {
                        console.log(111);
                        console.log(data);
                        if (data.nodes != null) {
                            var select_node = $('#treeview').treeview('getSelected', null);
                            if (select_node[0].state.expanded) {
                                $('#treeview').treeview('collapseNode', select_node);
                            } else {
                                $('#treeview').treeview('expandNode', select_node);
                            }
                        }
                        if (data.nodes == null && data.attrs.groupId != null) {
                            groupId = data.attrs.groupId;
                            groupTypeId = data.attrs.groupType;

                            // _this.findGdgxDate();
                        } else {
                            _this.groupId = null;
                        }
                    }
                });
                $('#treeview').treeview('collapseAll', {silent: true});
            }
        });
    },
    //树结构
    createTree: function (jsons, pid) {
        if (jsons != null) {
            var node = null;
            for (var i = 0; i < jsons.length; i++) {
                if (jsons[i].pid == pid) {
                    var map = {
                        text: jsons[i].groupName,
                        attrs: jsons[i],
                        nodes: this.createTree(jsons, jsons[i].id)
                    };
                    if (map.nodes == null && pid == 0) {
                        map.nodes = [];
                    }
                    if (node == null) node = [];
                    node.push(map);
                }
            }
        }
        return node;
    }
};

$(function () {
    ajfb.initTree();
});
