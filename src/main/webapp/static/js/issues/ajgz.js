/**
 * 案件跟踪
 */
// 案件跟踪列表查询
var issue_page_url = "/issues/ajgzPage.action";
// 根据taskId 查询案件信息
var query_issue_by_processId = '/issues/getIssueByProcessId.action';
// 根据taskId获得点击的批注详情
var commentDetail='/task/listHistoryCommentDetailByProcessId.action?processId=';
//根据taskId获取流程图
var speedOfProgress='/task/listActionByProcessId.action?processId=';


// 全网格 - 更新数据
var qwgRefresh = '/frame/refreshTQwgData.action';
// 全网格 - 完成案件
var qwgComplete = '/frame/completeTQwgData.action';
// 全网格 - 退回案件
var qwgReject = '/frame/rejectTQwgData.action';
// 全网格 - 数据列表
var qwgList = '/frame/listTQwgData.action';
// 表格是否全网格案件数据
var isQWGing = false;

var columns = [ {
	title : '案件id',
	field : 'id',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '案件编号',
	field : 'issueNumber',
	align : 'center',
	valign : 'middle',
	formatter : function(value, row, index) {
		return '<span class="link-col">' + value + '</span>'
	}
}, {
	title : '案件主题',
	field : 'issueSubject',
	align : 'center',
	valign : 'middle'
}, {
	title : '创建时间',
	field : 'addTime',
	align : 'center',
	valign : 'middle'
}, {
	title : '结束日期',
	field : 'endTime',
	align : 'center',
	valign : 'middle'
}, {
	title : '紧急程度',
	field : 'emrgencyLevel_ms',
	align : 'center',
	valign : 'middle'
}, {
	title : '案件状态',
	field : 'status_ms',
	align : 'center',
	valign : 'middle'
} ];

// 全网格案件数据列
var columns_qwg = [ {
	title : '案件来源ID',
	field : 'sourceid',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '案件编号',
	field : 'orderno',
	align : 'center',
	valign : 'middle',
	formatter : function(value, row, index) {
		return '<span class="link-col">' + value + '</span>'
	}
}, {
	title : '案件主题',
	field : 'title',
	align : 'center',
	valign : 'middle'
}, {
	title : '创建时间',
	field : 'occurdate',
	align : 'center',
	valign : 'middle'
}, {
	title : '结束日期',
	field : 'completedata',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '案件来源人',
	field : 'sourceperson',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '案件来源人手机',
	field : 'sourcemobile',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '案发地点',
	field : 'occurlocation',
	align : 'center',
	visible : false
}, {
	title : '案件内容',
	field : 'content',
	align : 'center',
	visible : false
}, {
	title : '案件类型编码',
	field : 'issuetypeid',
	align : 'center',
	visible : false
}, {
	title : '案发网格编码',
	field : 'occurorg',
	align : 'center',
	visible : false
}, {
	title : '案件来源',
	field : 'sourcename',
	align : 'center',
	visible : false
}, {
	title : '案件状态',
	field : '',
	align : 'center',
	valign : 'middle',
	formatter : function(value, row, index) {
		return '待处理';
	}
} ];

var boot_table_id = "boot-table-dbrw";

/*
 * 获取 已办业务标签 html 元素 并 绑定事件
 */
function getLiHeaderTag(flag, tagIssueCount, imgUrl) {
	var itemId = 'tag-li-' + flag.show_flag;
	var itemTemplate = "<li id='"
			+ itemId
			+ "'><a href='##' >"
			+ flag.show_flag
			+ "<embed src='"
			+ url_prefix
			+ "/static/images/"
			+ imgUrl
			+ "' type='image/svg+xml' pluginspage='http://www.adobe.com/svg/viewer/install/' />"
			+ "</a><i>" + tagIssueCount + "</i></li>";
	$("#ul-header-tag").append(itemTemplate);

	$("#" + itemId).click(function() {
		refreshDataTableByParams();
		return false;
	});
};

/*
 * 获取 全网格- 已办业务标签 html 元素 并 绑定事件
 */
function getQwgLiHeaderTag(tagIssueCount, imgUrl) {
	var flag = {
		show_flag : '全网格',
	};
	var itemId = 'tag-li-' + flag.show_flag;
	var itemTemplate = "<li id='"
			+ itemId
			+ "'><a href='##' >"
			+ flag.show_flag
			+ "<embed src='"
			+ url_prefix
			+ "/static/images/"
			+ imgUrl
			+ "' type='image/svg+xml' pluginspage='http://www.adobe.com/svg/viewer/install/' />"
			+ "</a><i>" + tagIssueCount + "</i></li>";
	$("#ul-header-tag").append(itemTemplate);


	$("#" + itemId).click(function() {
		refreshDataTableForQwg();
		return false;
	});
};

// 更新全网格标签数字
function updateQwgCounts(counts){
	$("#tag-li-全网格 > i").html(counts);
}

function initDataTable() {
	var $table = $("#" + boot_table_id);
	$table.bootstrapTable({
		height : ($('.content').height() - $('.funShow').height()),
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : true,
		striped : true, // 是否显示行间隔色
		columns : columns,
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		dataType : 'json',
		url : url_prefix + issue_page_url,
        responseHandler:function(res){
            return (typeof res=="object")?res:$.parseJSON(res)
        },
		queryParams : function(params){
			return {
				rows: params.limit, //页面大小
				page: params.offset/params.limit + 1, //页码,
                s_name : "",
                issueSource:$('#issueSource').val(),
                dealdept:$('#deal-dept').val(),
                issuebelong:$('#issue-belong').val(),
                startDay:$('#dtp_input1').val(),
                endDay:$('#dtp_input2').val()
			}
		}
	});

	$table.on("click-cell.bs.table", function(event, column_name, value, row) {
		if (column_name == "issueNumber") {
			showIssueDealPage(row.processId);
		}else if(column_name == "orderno"){
			showQwgDetails(row);
		}
	});

	$table.on("load-success.bs.table", function(event, data) {
		if (isQWGing){
			var options = $("#" + boot_table_id).bootstrapTable('getOptions');
			updateQwgCounts(options.totalRows);
		}
	});
};

function refreshDataTableByParams() {
	isQWGing = false;
	var options = $("#" + boot_table_id).bootstrapTable('getOptions');

	$("#" + boot_table_id).bootstrapTable('refreshOptions', {
		columns : columns,
		url : url_prefix + issue_page_url,
		responseHandler:function(res){
			return (typeof res=="object")?res:$.parseJSON(res)
		},
        queryParams : function(params){
            return {
                rows: params.limit, //页面大小
                page: params.offset/params.limit + 1, //页码,
                s_name : "",
                issueSource:$('#issueSource').val(),
                dealdept:$('#deal-dept').val(),
                issuebelong:$('#issue-belong').val(),
                startDay:$('#dtp_input1').val(),
                endDay:$('#dtp_input2').val()
            }
        }
	});
};


// 根据全网格数据更新数据表
function refreshDataTableForQwg() {
	isQWGing = true;
	var options = $("#" + boot_table_id).bootstrapTable('getOptions');

	$("#" + boot_table_id).bootstrapTable('refreshOptions', {
		columns : columns_qwg,
		url : url_prefix + qwgList,
		queryParams : {
			rows: options.pageSize, //页面大小
			page: 1, //页码,
			issueTypeId : issueTypeId
		}
	});
};

/**
 * 显示案件处理页面
 */
var page_suffix;
// jsp 映射表
var jsp_include_mapper = {
	'key15' : "shequchuli",
	"key2" : 'sqzcz',
	'key3' : "jiedaofenfa",
	'key4' : "qujizhongxin",
	'key5' : "wbjjs",
	'key6' : "wbjla",
	'key7' : "wbjcl",
	'key9' : "comment",
	'key13' : "jdxxdc",
	'key14' : "qjzxxxdc",
	'key8' : "qjzxxgclsx",
	'key10' : "zgbmlb",
	'key11' : "ynajcl",
	'key12' : "jdbmcl"
};

var processId = null;
function showIssueDealPage(processId_) {
    processId = processId_;
	$.ajax({
		method : 'post',
		url : url_prefix + query_issue_by_processId,
		dataType : 'json',
		data : {
            processId : processId
		},
		success : function(data) {
			// 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
            data = (typeof data =="object")?data:$.parseJSON(data)
			myapp.ajDetail = data.tMainIssueBean;
			myapp.ajstatus = myapp.ajDetail.status;
			var status = "key" + myapp.ajstatus;
            // console.log("status-->"+status);
			$("#myModal").modal({backdrop: 'static'}).show();
			// page_suffix = eval("jsp_include_mapper." + status);
			// // 关联处理页面 根据 issueObj.tMainIssueBean.status 字段
			// $('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp');

		}
	});
	$.ajax({
        method: 'get',
        url: url_prefix + commentDetail + processId,
        dataType: 'json',
        success: function (data) {
            data = (typeof data=="object")?data:$.parseJSON(data)
            myapp.commentDetail = data.rows;
            //初始化提示框
			$('[data-toggle="tooltip"]').tooltip("destroy");
            $('[data-toggle="tooltip"]').tooltip();
        }
    });
	myapp.$http.get(url_prefix + speedOfProgress + processId).then(
		function(response) {
            data = (typeof response.data =="object")?response.data:$.parseJSON(response.data);
            console.log(data.rows);
			myapp.speedOfProgress=data.rows;
            console.log(myapp.speedOfProgress);
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
							console.log($(dom).parent().children('.text').innerHeight(),$(dom).parent().parent().innerHeight());
							$(dom).height($(dom).parent().parent().innerHeight()-$(dom).parent().children('.text').innerHeight()+'px');
							$(dom).css('bottom','-'+$(dom).css('height'));
							clearInterval(shart);
						}
					},50)
				})
			})
	});
};

// 显示全网格案件处理页面
function showQwgDetails(row){
	myapp.qwgDetail = row;
	myapp.uploadFileList = [];
	$('#myModal_2').modal('show');
}


function testStatus(status) {
	var status = "key" + status;
	$("#myModal").modal().show();
	page_suffix = eval("jsp_include_mapper." + status);
	$('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp');
	return status;
}

function tab_animate() {
	$(".tab-content>.tab-pane").addClass('fade');
	$(".tab-content>.tab-pane").eq(0).addClass('in');
	$(".tab-content .tab-pane .btn").attr("type","button");
}



var tianshu = parseInt($('.shuzi').val());
$('.biaoti input').click(function() {
	var flag = $(this).is(':checked');
	if (flag) {
		$('.jia').removeClass('jjfocus');
		$('.jian').removeClass('jjfocus');
		$('.jian').bind('click', function() {
			tianshu--;
			$('.shuzi').val(tianshu);
			return false
		});
		$('.jia').bind('click', function() {
			tianshu++;
			$('.shuzi').val(tianshu);
			return false;
		});
	} else {
		$('.jian').unbind('click');
		$('.jia').unbind('click');
		$('.jia').addClass('jjfocus');
		$('.jian').addClass('jjfocus');
	}
})

$('#bumen').click(function() {
	$('#tree').slideToggle(300);
});

// 初始化VUE
var myapp = new Vue({
	el : '.wrap',
	data : {
        uploadFileList: [],
		fileList:[],
		setting : {
			check : {
				enable : true,
				nocheckInherit : false
			},
			view : {
				dblClickExpand : true,
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : ""
				}
			},
			callback : {
				beforeClick : function(treeId, treeNode) {
				}
			}
		},
		zNodes : [],
		t : {},
		liucheng : [],
        loading:false,
		bianhao : {},
		ajDetail : [],
		qwgDetail : [],
		commentDetail:[],
		speedOfProgress:[],
		timeTaken:0
	},
	mounted : function() {
		this.$http.get(url_prefix + '/page/json/chuliliucheng.json').then(
				function(data) {
					this.liucheng = data.body;
				});
		this.$http.get(url_prefix + '/page/json/function.json').then(
				function(data) {
					this.functionSr = data.body;
				});
	},
    methods:{
    }

});

$(function() {

	initDataTable();

    $('#findDate').click(refreshDataTableByParams);

    // ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#deal-dept"),"id","wbjName",null);
    // ajaxAddOptions(url_prefix +  '/frame/listAllWeibanjuzd.aciton',$("#issue-belong"),"id","wbjName",'局');

	initDialog();

	$('#myModal_2 .close').click(function(){
		$('#myModal_2').modal('hide');
	});

});