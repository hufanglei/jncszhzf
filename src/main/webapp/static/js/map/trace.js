/**
 * 地图分析-轨迹追踪
 */

/**
 * 地图选取点位控制
 */

var map, wmtsDtLayer, wmtsZjLayer,clickLayer;
var markerUrl = url_prefix + "/static/images/map/marker-gold.png";
var markerPoint = null;
var marker_wid = 36;
var marker_hei = 36;
var curPoint={x:null,y:null};
var issuse_url = url_prefix + '/issues/listTMainIssueForMap.action';

// if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
// 	// marker_wid = 18; marker_hei = 18;
// }

toastr.options = {
    "positionClass": "toast-bottom-right",//弹出窗的位置
    "showDuration": "500",//显示的动画时间
    "hideDuration": "500",//消失的动画时间
    "timeOut": "2000", //展现时间
    };

require([ "esri/map","esri/layers/GraphicsLayer", "esri/layers/WMTSLayer", "esri/layers/WFSLayer","esri/layers/WMTSLayerInfo",
		"esri/geometry/Extent", "esri/layers/TileInfo", "esri/geometry/Circle",
		"esri/SpatialReference", "dojo/parser","esri/symbols/SimpleLineSymbol",

		"dijit/layout/BorderContainer", "dijit/layout/ContentPane",
		"dojo/domReady!" ], function(Map, GraphicsLayer , WMTSLayer, WFSLayer,WMTSLayerInfo, Extent,
		TileInfo,Circle, SpatialReference, parser) {
	
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

	clickLayer = new GraphicsLayer({"id":"clickLayer"});  
    map.addLayer(clickLayer);  

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

    dojo.connect(map.graphics, "onMouseOver", function (obj) {
        toastr.info(obj.graphic.attributes);
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



$("#btLoadTrace").click(function(){
    var uid = $("#sel-wgy").val();
    var groupId = $("#sel-jiedao").val();
    if(groupId==""){
        layerAlert("请选择街道!");
    }else if(uid=="-1") {
        layerAlert("暂无人员!");
    }else{
        $.ajax({
            // url : url_prefix + '/frame/listTraceByUserId',
            url : url_prefix + '/frame/listTraceByUser',
            dataType : 'json',
            method : 'post',
            data: {
                groupId:groupId,
                userId:uid
            },
            success : function(result){
                var obj = (typeof result=="string"?JSON.parse(result):result)
                // showPath(obj.rows);
                showMarker(obj);
            }
        });
    }
});


/**
 * 显示标注
 * @param arr
 */
function showMarker(objs){
    map.graphics.clear()
    for(var i=0;i<objs.length;i++){
        obj=objs[i];
        addMarker({x:obj.x,y:obj.y},obj.username+"("+obj.x+","+obj.y+")",null);
    }
}

/**
 * 显示标注
 * @param mapPoint
 * @param attr
 * @param infoTemplate
 */
function addMarker(mapPoint,attr,infoTemplate){
    var symbol = new esri.symbol.PictureMarkerSymbol(markerUrl, marker_wid, marker_hei);
    var pt = new esri.geometry.Point(mapPoint.x,mapPoint.y, map.spatialReference);
    var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);
    map.graphics.add(graphic);
};


/**
 * 展示运动轨迹
 */
function showPath(path){
    var sr = map.spatialReference;
    var sms =  new esri.symbol.SimpleMarkerSymbol({
        "color": [255,255,0,255],
        "size": 8,
        "angle": -30,
        "xoffset": 0,
        "yoffset": 0,
        "type": "esriSMS",
        "style": "esriSMSCircle",
        "outline": {
            "color": [255,0,0,255],
            "width": 1,
            "type": "esriSLS",
            "style": "esriSLSSolid"
        }
    });
    var sls = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,
        new esri.Color([255, 0, 0]),
        2
    );
    var sfs = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,
        new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT,
            new esri.Color([0,0,255,0.2]), 2),new esri.Color([0,0,255,0.2])
    );

    if(path!=null) {
        for (var index = 0; index < path.length - 1; index++) {
            var ptStart = new esri.geometry.Point(path[index][0], path[index][1], sr);
            var ptEnd = new esri.geometry.Point(path[index + 1][0], path[index + 1][1], sr);
            var gPtStart = new esri.Graphic(
                ptStart,
                sms
            );
            var gCircleStart = new esri.Graphic(
                new Circle(ptStart, {"radius": 10}),
                sfs
            );
            var gPtEnd = new esri.Graphic(
                ptEnd,
                sms
            );
            var gCircleEnd = new esri.Graphic(
                new Circle(ptEnd, {"radius": 10}),
                sfs
            );
            var polylineJson = {
                "paths": [[[path[index][0], path[index][1]], [path[index + 1][0], path[index + 1][1]]]],
                "spatialReference": {"wkid": 4490}
            };
            var gLine = new esri.Graphic(
                new esri.geometry.Polyline(polylineJson),
                sls
            );

            clickLayer.add(gLine);
            if (index < path.length - 1) {
                clickLayer.add(gCircleEnd);
                clickLayer.add(gPtEnd);
            }
            ;
            if (index === 0) {
                clickLayer.add(gCircleStart);
                clickLayer.add(gPtStart);
            }
            ;
            //setTimeout(function(){},1000);
        };
    }
}