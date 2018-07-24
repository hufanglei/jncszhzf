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
var curPoint = {x: null, y: null};
var issuse_url = url_prefix + '/issues/listTMainIssueForMap.action';
var groupId = "";
var groupTypeId = "";

// if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
// 	// marker_wid = 18; marker_hei = 18;
// }

require(["esri/map", "esri/layers/WMTSLayer", "esri/layers/WMTSLayerInfo",
    "esri/geometry/Extent", "esri/layers/TileInfo",
    "esri/SpatialReference", "dojo/parser",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
    "esri/InfoTemplate", "esri/dijit/InfoWindow",
    "dojo/domReady!"
], function (Map, WMTSLayer, WMTSLayerInfo, Extent, TileInfo, SpatialReference, parser) {

    parser.parse();

    var bounds = new Extent({
        "xmin": 118.799602812075,
        "ymin": 31.932281322199998,
        "xmax": 118.881270057075,
        "ymax": 31.972192272199997,
        "spatialReference": {
            "wkid": 4490
        }
    });

    map = new Map("map", {
        extent: bounds
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





//定义矢量要素图层

var context = {
    getLineColor: function (feature) {
        var value = feature.attributes.COLOR1;
        if (typeof(value) == "undefined")
            return "red";
        else if (value == "#00000000")
            return "rgba(0, 0, 0, 0)";
        else
            return value;
    },
    getFillColor: function (feature) {
        var value = feature.attributes.COLOR;
        if (typeof(value) == "undefined")
            return "rgba(255, 255, 255, 1)";
        else if (value == "#00000000")
            return "rgba(0, 0, 0, 0)";
        else
            return value;
    }
};
var styleMap = new OpenLayers.StyleMap({
    "default": new OpenLayers.Style({
        fillColor: "${getFillColor}",
        strokeColor: "${getLineColor}",
        strokeWidth: 2
    }, {context: context})
});
var vectorLayer = new Geo.View2D.Layer.Vector("Layer", {
    styleMap: styleMap
});

map.addLayers([vectorLayer]);


var path = "/proxy?url=http://58.213.148.61:8083/qyswg/wfs";
var layname = "全要素网格"
var wfsQueryObj = new Geo.Query.WFSQuery(path, layname,
    {
        geometryName: "GEOMETRY",
        format: new Geo.Format.GML.v2({xy: true}),
        version: "1.0.0",
        maxFeatures: 20000
    });


//查询所有街道
wfsQueryObj.query(new Geo.Filter.Spatial({
    type: Geo.Filter.Spatial.INTERSECTS,
    value: Geo.Bounds.fromString("-180,-90,180,90")
}), function (features) {
    if (features.length > 0) {
        vectorLayer.removeAllFeatures();
        vectorLayer.addFeatures(features);
    }
}, function () {
    alert("查询失败");
});







    initMap();

    dojo.connect(map.graphics, "onClick", function (obj) {
        showGdgxPage(obj.graphic.attributes);
    });

});


function addMarker(mapPoint,attr,infoTemplate){
    var symbol = new esri.symbol.PictureMarkerSymbol(markerUrl, marker_wid, marker_hei);
    var pt = new esri.geometry.Point(mapPoint.x,mapPoint.y, map.spatialReference);
    var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);
    map.graphics.add(graphic);
};



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
