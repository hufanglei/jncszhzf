/* 案件上报 */
function addTMainIssueCallBack(responseObject, statusText) {
	console.log(responseObject);
	var res = JSON.parse(responseObject);
	if (res.result == 1) {
		showDialog('提示','保存成功！',()=>{
            document.getElementById("postForm").reset();
            $("#xLabel").html("--.----");
            $("#yLabel").html("--.----");
            $("#x").val("");
            $("#y").val("");
            uploader.$refs.upload.clearFiles();
            $(".modal").modal('hide');
        });

	} else {
		showDialog('提示','保存失败！',()=>{$(".modal").modal('hide');});
	};

}



// 提交案件上报表单
function submitAddTicket() {
	var status = $(this).attr("status");
    var files = [];
    //将上传的附件放入隐藏域
    $.each(uploader.fileList, (i, o) => {
        if (o && o.response) {
            files.push({
                "fileName": o.response.url,
                "originalFileName": o.response.name,
                "size": o.response.size
            });
        }
    });
    $('#uploadFile').val(JSON.stringify(files));

	var options = {
		beforeSubmit : beforPostForm_tMainIssue,
		success : addTMainIssueCallBack,
		dataType : 'json',
		url : url_prefix+"/issues/reportIssue.action?status="+status
	};

	$("#postForm").ajaxForm(options);
	$('#postForm').submit();

}

function beforPostForm_tMainIssue() {
	// console.log($('#sel_time').val());
	$('#lei,#xiang,#issueSource,#dsrlx,#issueTime,#issueType,#timeLimit,#yLabel,#xLabel,#issueSubject,#issueDesc,#emrgencyLevel').removeClass('fill_error')
	var flag=true;
	$('#lei,#xiang,#issueSource,#dsrlx,#issueTime,#issueType,#timeLimit,#x,#y,#issueSubject,#issueDesc,#emrgencyLevel').each(function(index,item){
		// console.log(index);
		if($(item).val()==''||$(item).val()==0){
			$(item).addClass('fill_error');
			showDialog('错误提示','红色选中项为必填！',callBack);
			if(item.id=='x'||item.id=='y'){
				$('#yLabel,#xLabel').addClass('fill_error');
			}
			function callBack(){
				$(".modal").modal('hide');
			};
			flag=false;
		}
	})
	return flag;
}

var map_wrapper_id = 'map-wrapper';
var tmp_scroll_top = 0;
function showMap(){
	$('#'+map_wrapper_id).css('visibility','visible');
	tmp_scroll_top = $('body').scrollTop();
	$('body').scrollTop(0);

	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i)!=null){
		var command = {
			'action':'locationEvent'
		};
		app.event(JSON.stringify(command));
	}else{
		window.scrollTo(0,0);
	}
};

function hideMap(){
	$('#'+map_wrapper_id).css('visibility','hidden');
	$('body').scrollTop(tmp_scroll_top);
};

var tb_powerlist_id = "tb_powerlist";
$(function() {
	//1.初始化Table
    buildSelect('lei', '0');
    ajlySelect('ajly');
	var oTable = new TableInit();
	oTable.Init();

	$("#bt-power-list-commit").click(function(){
		var row =  $('#'+tb_powerlist_id).bootstrapTable('getSelections',function(row){return row ;});
		if(row.length==0){
			alert('请选择权力事项编码!');
		}else{
			// console.log(row);
			$("#issueType").val(row[0].powerCode);
			$("#power-code-modal").modal('hide');
		}

	})
});

var TableInit = function() {
	var oTableInit = new Object();
	//初始化Table
	oTableInit.Init = function() {
		$('#'+tb_powerlist_id).bootstrapTable({
			url : url_prefix + '/frame/listAllPowerList', //请求后台的URL（*）
			method : 'get', //请求方式（*）
			striped : true, //是否显示行间隔色
			pagination : true, //是否显示分页（*）
			sidePagination : "client", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 10, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : false,
			clickToSelect : true, //是否启用点击选中行
			//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", //每一行的唯一标识，一般为主键列
			showToggle : true, //是否显示详细视图和列表视图的切换按钮
            singleSelect: true,
			responseHandler: function(data){
				return data.rows;
			},
			columns : [ {
				checkbox : true
			}, {
				field : 'ssWeibanju',
				align : 'left',
				valign:	'left',
				title : '所属部门'
			}, {
				field : 'powerDesc',
				valign:	'left',
				title : '权利事项内容'
			}, {
				field : 'powerCode',
				valign:	'left',
				visible:false,
				title : '权利事项编码'
			}, {
				field : 'id',
				valign:	'left',
				visible:false,
				title : 'ID'
			}, ]
		});
	};
	return oTableInit;
};

$('.form_date2').datetimepicker({
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1
});


// 设置案件上报按钮
// 网格员： 暂存、上报、自处置；
// 区级中心： 暂存、提交；
// status ： 1：暂存 2：自处置 3：网格员上报 4：区级中心提交
function setAjsbBtns() {
    var btnsHtml = "";
    if (groupTag == 3) {
        // 区级中心
        btnsHtml = ""
            // + "<button id='btn-save' type='button' status='1' class='btn btn-ajsb btn-warning btn-common'>暂存</button>"
            + "<button id='btn-submit' type='button' status='4' class='btn btn-ajsb btn-primary btn-common'>提交</button>";
    } else if (groupTag == 1) {
        // 网格员
        btnsHtml = ""
            // +"<button id='btn-save' type='button' status='1' class='btn btn-ajsb btn-warning btn-common'>暂存</button>"
            + "<button id='btn-submit' type='button' status='3' class='btn btn-ajsb btn-primary btn-common'>上报</button>"
            + "<button id='btn-selfhandle' type='button' status='2' class='btn btn-ajsb btn-success btn-common'>自处置</button>";
    }

    $("#ansb_btns").html(btnsHtml);

    $(".btn-ajsb").click(submitAddTicket);
}

setAjsbBtns();

function setXY(xVal, yVal) {
    curPoint.x = xVal;
    curPoint.y = yVal;
    addMarker(curPoint);
    document.getElementById("xVal").innerHTML = xVal.toFixed(5);
    document.getElementById("yVal").innerHTML = yVal.toFixed(5);
    map.centerAt(new esri.geometry.Point(curPoint.x, curPoint.y, map.spatialReference));
};

var uploader = new Vue({
    el: '#uploadContainer',
    data: {
        fileList: []
    },
    methods: {
        handlePreview(file) {
            dialog.url = file.url;
            dialog.dialogVisible = true;
        },
        handleSuccess(response, file, list) {
            this.fileList = list;
        },
        handleRemove(file, list) {
            this.fileList = list;
        }
    }
});

//
var dialog = new Vue({
    el: '#dialog',
    data() {
        return {
            url: '',
            dialogVisible: false
        }
    }
});
$("#lei").change(
    function(){
        buildSelect('xiang',$("#lei").val());
        buildSelect('issueSubject',$("#xiang").val());

    }
)
$("#xiang").change(
    function(){
        buildSelect('issueSubject',$("#xiang").val());
    }
)
//事项归口三级联动
function buildSelect(selectId, parentId) {
    var $select = $('#' + selectId);
    var url = url_prefix + '/issues/getSxgkList';
    var param = {
        parentId: parentId
    };
    $select.children().remove();
    $select.append($('<option value="0">请选择</option>'));
    $.post(url, param, function (list) {
        if (list && list.length > 0) {
            $.each(list, function (i, o) {
                var option = $('<option value="' + o.id + '">' + o.name + '</option>');
                option.appendTo($select);
            });
        }

    });
}

//网格员案件上报页面中的“案件来源”固定为“巡查上报”，不可更改
    function ajlySelect(dmmc) {
    var $select = $('#issueSource');
    var url = url_prefix + '/qhzd/getDictionary';
    var param = {
        dmmc: dmmc
    };

    $select.children().remove();
    if(groupTag == 3){
        // 区级中心
        $select.append($('<option value="0">请选择</option>'));
        $.post(url, param, function (list) {
            console.log(list)
            if (list && list.length > 0) {
                $.each(list, function (i, o) {
                    var option = $('<option value="' + o.id + '">' + o.dmms + '</option>');
                    option.appendTo($select);
                });
            }
        });
    }else if (groupTag == 1){
        // 网格员
        $select.append($('<option value="0">请选择</option>'));
        var option = $('<option value="58">巡查上报</option>');
        option.appendTo($select);
    }


}

