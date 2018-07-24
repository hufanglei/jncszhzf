/**
 * 地图选取点位控制
 */

var map, wmtsDtLayer, wmtsZjLayer;
var markerUrl = url_prefix + "/static/images/map/marker-gold.png";
var markerPoint = null;
var marker_wid = 36;
var marker_hei = 36;
var curPoint={x:null,y:null};
// if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
// 	// marker_wid = 18; marker_hei = 18;
// }

require([ "esri/map", "esri/layers/WMTSLayer", "esri/layers/WMTSLayerInfo",
		"esri/geometry/Extent", "esri/layers/TileInfo",
		"esri/SpatialReference", "dojo/parser",

		"dijit/layout/BorderContainer", "dijit/layout/ContentPane",
		"dojo/domReady!" ], function(Map, WMTSLayer, WMTSLayerInfo, Extent,
		TileInfo, SpatialReference, parser) {
	
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

	if(curPoint.x!=null&&curPoint.y!=null){
		addMarker(curPoint);
	}
	
	map.on("click", function(event){
		addMarker(event.mapPoint);
	});
	

	
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
});


function getXY(){
	return curPoint;
}

function setXYtoIssue(){
	$('#x').val(curPoint.x.toFixed(7));
	$('#y').val(curPoint.y.toFixed(7));
	$('#xLabel').html(curPoint.x.toFixed(7));
	$('#yLabel').html(curPoint.y.toFixed(7));
	hideMap();
};

function addMarker(mapPoint){
	map.graphics.clear();

	var symbol = new esri.symbol.PictureMarkerSymbol(markerUrl, marker_wid, marker_hei);        
	var pt = new esri.geometry.Point(mapPoint.x,mapPoint.y, map.spatialReference);
	var attr={},infoTemplate=null;
	var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);        
	map.graphics.add(graphic);  
	curPoint.x = mapPoint.x;
	curPoint.y = mapPoint.y;
	document.getElementById("xVal").innerHTML= curPoint.x.toFixed(5);
	document.getElementById("yVal").innerHTML= curPoint.y.toFixed(5);
};
