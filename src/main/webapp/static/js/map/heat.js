/**
 * 地图分析-热力图
 */

var map, wmtsDtLayer, wmtsZjLayer;
var heatLayer;
var featureSets = [];


// if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
// 	// marker_wid = 18; marker_hei = 18;
// }

require([ "esri/map", "esri/layers/WMTSLayer", "esri/layers/WFSLayer","esri/layers/WMTSLayerInfo",
		"esri/geometry/Extent", "esri/layers/TileInfo",
		"esri/SpatialReference","esri/geometry/Point","esri/graphic"], function(Map, WMTSLayer,WFSLayer, WMTSLayerInfo, Extent,
		TileInfo, SpatialReference,Point,Graphic) {	
	
	var bounds = new Extent({
		// "xmin" : 118.799602812075,
		// "ymin" : 31.932281322199998,
		// "xmax" : 118.881270057075,
		// "ymax" : 31.972192272199997,
		"xmin" : 118.775108824825,
		"ymin" : 31.8854177507,
		"xmax" : 118.967369144825,
		"ymax" : 31.984466147699997,
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

	getFeatureSets();

	// 获取案件坐标数据
	function getFeatureSets(){
		$.ajax({
			url : url_prefix + "/issues/listTMainIssueForHeatmap.action",
			method : "post",
			dataType : "json",
			success : function(result){
				var data = result.rows;
				if (data){

                    for (var i = data.length - 1; i >= 0; i--) {
                        var geometry = new Point(data[i].x,data[i].y,map.spatialReference);
                        var feature = new Graphic(geometry,null,data[i],null);
                        featureSets.push(feature);
                    };
				}


				addHeatLayer();

			}
		});
	}

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

function addHeatLayer(){
	    //resize the map when the browser resizes
    dojo.connect(dijit.byId('map'), 'resize', map, map.resize);
    // create heat layer
    heatLayer = new HeatmapLayer({
        config: {
            "useLocalMaximum": true,
            "radius": 40,
            "gradient": {
                0.45: "rgb(000,000,255)",
                0.55: "rgb(000,255,255)",
                0.65: "rgb(000,255,000)",
                0.95: "rgb(255,255,000)",
                1.00: "rgb(255,000,000)"
            }
        },
        "map": map,
        "domNodeId": "heatLayer",
        "opacity": 0.85
    });


	heatLayer.setData(featureSets);
    // add heat layer to map
    map.addLayer(heatLayer);
    // resize map
    map.resize();
}