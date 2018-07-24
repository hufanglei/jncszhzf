/**
 * 工具类
 */
function isNullOrEmpty(obj){
	if(obj==null||obj==""||obj==undefined){
		return true;
	}else{
		return false;
	}
};



function ajaxAddOptions(url, target, id, text,trimStr){
    target.empty();
    target.append("<option value = 'null' > 请选择 </option>");
    $.getJSON(url, function(json){
    	var jsonObj = (typeof json=="string")?$.parseJSON(json):json;
        $(jsonObj).each(function(i){
            var x = jsonObj[i];
            var htmlStr = "<option value='" + eval("x." + id) + "'>" + eval("x." + text) + "</option>";
            if(trimStr){
            	htmlStr.replace(trimStr,"");
            }
            target.append( htmlStr );  
        })  
    });  
};


function initAddOptions(target,url, type){
    target.empty();
    var htmlStr ="<option value = '' > 请选择 </option>";
    $.getJSON(url+"?dmmc="+type, function(json){
        var jsonObj = $.parseJSON(json)
        $.each(jsonObj,function(index,obj){
            htmlStr += "<option value='" + obj.id + "'>" + obj.text + "</option>";
        })
        target.append( htmlStr );
    });
};

function initAddOptionsDb(target,url, type){
    target.empty();
    var htmlStr ="<option value = '' > 请选择 </option>";
    if('5'==type){
        htmlStr += "<option value='巡查机制'>巡查机制</option>";
	}else if('4'==type){
        htmlStr += "<option value='监督检查'>监督检查</option>";
    }else if('3'==type){
        htmlStr += "<option value='反复投诉'>反复投诉</option><option value='督办问责'>督办问责</option><option value='工作协调'>工作协调</option>";
	}
     target.append( htmlStr );

};

//初始化涉嫌机构
function initUserGroup(target,type){
    target.empty();
    var url = url_prefix +  '/actidgroup/listGroupInfo.aciton'+"?groupPid="+type;
    var htmlStr ="<option value = '' > 请选择 </option>";
    $.getJSON(url, function(json){
        var objs =json;
        if(typeof json!="object"){
            objs = $.parseJSON(json)
        }
        $.each(objs,function(index,obj){
            htmlStr += "<option value='" + obj.id + "'>" + obj.text + "</option>";
        })
        target.append( htmlStr );
    });
}


//初始化涉嫌机构
function initUserGroupMenu(target,type){
    target.empty();
    var url = url_prefix +  '/actidgroup/listGroupInfoMenu.aciton'+"?groupPid="+type;
    var htmlStr ="<option value = '' > 请选择 </option>";
     // var htmlStr ="";
    $.getJSON(url, function(json){
        var objs =json;
        if(typeof json!="object"){
            objs = $.parseJSON(json)
        }
        $.each(objs,function(index,obj){
            htmlStr += "<option value='" + obj.id + "'>" + obj.text + "</option>";
        })
        target.append( htmlStr );
    });
}

/**
 * 坐标转换，WGS84转为南京天地图坐标
 */
function locationParser(orgLocation,parserFromWGS84){
	var url = 'http://www.njmap.gov.cn:8280/ResourcesProxy/Gateway/skwsmzenrk/coorddojson.asmx/CoordDecryptJson';
	$.ajax({
		url:url,
		method : 'post',
		dataType : 'json',
		data:{
			intputInfo:[orgLocation.x,orgLocation.y],		// [108,32,108.3,32,108.4,32] 单组或多组
			decryption:'WGS84toWGS84P',
			GetUserName:'admin'
		},
		success : function(data) {
			console.log(data);
		}
	});
};

// 初始化操作提示对话框
function initDialog(callBackFunc){
	$("#modal-tips-btn-ok").unbind("click");
	$("#modal-tips-btn-ok").click(callBackFunc);
}

// 弹出操作提示对话框，设置回调函数
function showDialog(title,content,callBackFunc,operation){
	$("#modal-tips-title").html(title);
	$("#modal-tips-body").html(content);
	if(callBackFunc){
		$("#modal-tips-btn-ok").unbind("click");
		$("#modal-tips-btn-ok").click(callBackFunc);
		console.log(operation);
		if(operation){
			$("#modal-tips-btn-ok").click(operation);
			$("#modal-tips-btn-false").show();
			$("#modal-tips-btn-false").unbind("click");
			$("#modal-tips-btn-false").click(callBackFunc);
		}
	};
	if(!operation){
		$("#modal-tips-btn-false").hide();
	}
	$("#modal-tips").modal("show");
	
};

function hideDialog(){
	$("#modal-tips").modal("hide");
};

// 模态框剧中
function centerModals() {   
	$('.modal.fade').each(function(i) {   
		var $clone = $(this).clone().css('display','block').appendTo('body');
		var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
		top = top > 0 ? top : 0;   
		$clone.remove();   
		$(this).find('.modal-content').css("margin-top", top);   
	});   
};

$('.modal.fade').on('show.bs.modal', centerModals);
$(window).on('resize', centerModals);


function layerAlert(msg){
    layer.alert(msg, {
        skin: 'layui-layer-lan'
        ,closeBtn: 0
    });
}
