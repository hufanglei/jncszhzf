/**
 * 待办任务
 */
// 待办任务列表查询
var task_page_url = "/taskDb/taskPage.action";
// 待办任务数量查询
var task_count_url = "/task/getTaskCount.action";
// 根据taskId 查询案件信息
var query_issue_by_taskId = '/taskDb/getDbIssueByTaskId.action';
// 根据taskId获得点击的批注详情
var commentDetail='/task/listHistoryComment.action?taskId=';
//根据taskId获取流程图
var speedOfProgress='/task/listAction.action?taskId=';

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

/**
 * Li 待办任务标签块映射
 */
var LiHeaderTagMap = [ {
	imgUrl : "dla.svg"
}, {
	imgUrl : "bmdqs.svg"
}, {
	imgUrl : "dcf.svg"
}, {
	imgUrl : "dja.svg"
} ];
// 待办任务标签表
var todo_flags = [{
    id: "1",
    desc: "网格员",
    flags: [{
        show_flag: "街道派遣",
        query_page_param: "案件录入",
        query_count_param: "subDistriceDistributeCount",
        query_flag: "街道派遣数量"
    }, {
        show_flag: "自处置",
        query_page_param: "自处置",
        query_count_param: "waitHandleSelfCount",
        query_flag: "自处置数量"
    }
        // , {
        //     show_flag: "待评价",
        //     query_page_param: "评价",
        //     query_count_param: "waitEvalutateCount",
        //     query_flag: "待评价数量"
        // }
    ]
}, {
	id : "2",
	desc : "街道中心",
	flags : [ {
		show_flag : "待派发",
		query_page_param : "街道派发",
		query_count_param : "subDistriceWaitDistributeCount",
		query_flag : "待派发数量"
	} ]
}, {
	id : "3",
	desc : "区级中心",
	flags : [ {
		show_flag : "待派发",
		query_page_param : "区级中心派发",
		query_count_param : "districeCenterWaitDistributeCount",
		query_flag : "待派发数量"
	}, {
		show_flag : "待修改时限",
		query_page_param : "修改处理时限",
		query_count_param : "districeCenterWaitModTimeLimitCount",
		query_flag : "待修改时限数量"
	}, {
		show_flag : "待评价",
		query_page_param : "评价",
		query_count_param : "waitEvalutateCount",
		query_flag : "待评价数量"
	} ]
}, {
	id : "4",
	desc : "领导小组办公室，区主管领导，区分管领导",
	flags : [ {
		show_flag : "疑难问题待处理",
		query_page_param : "疑难案件处理",
		query_count_param : "districeCenterDifficultCaseWaitHandleCount",
		query_flag : "疑难问题待处理数量"
	} ]
}, {
    id: "5",
    desc: "委办局",
    flags: [
    // 	{
    //       show_flag: "全网格",
    //     // show_flag: "待接收",
    //     query_page_param: "",
    //     query_count_param: "",
    //     query_flag: "待主管部门接收数量"
    // },
		{
        show_flag: "待派遣",
        query_page_param: "主管部门派遣",
        query_count_param: "waitCompetentDepartmentHandleCount",
        query_flag: "待主管部门处理数量"
    }, {
        show_flag: "待联办",
        query_page_param: "问题联办",
        query_count_param: "waitCompetentDepartmentCooperationCount",
        query_flag: "待主管部门联办数量"
    }, {
        show_flag: "待立案",
        query_page_param: "主管部门立案",
        query_count_param: "waitCompetentDepartmentFilingCount",
        query_flag: "待主管部门立案数量"
    }]
}, {
    id: "6",
    desc: "街道内设科室",
    flags: [{
        show_flag: "待处理",
        query_page_param: "街道处理",
        query_count_param: "waitSubDistriceHandleCount",
        query_flag: "待处理任务数量"
    }]
}, {
    id: "7",
    desc: "区级中心督察组",
    flags: [{
        show_flag: "待线下督查",
        query_page_param: "线下督查",
        query_count_param: "waitDistriceCenterOfflineSuperVisorCount",
        query_flag: "待线下督查数量"
    }]
}, {
    id : "8",
    desc : "执法中队",
    flags : [ {
        show_flag : "部门派遣",
        query_page_param : "案件录入",
        query_count_param : "", // TODO 待开发
        query_flag : "部门派遣数量"
    }]
}
// , {
// 	id : "10",
// 	desc : "街道督察组",
// 	flags : [ {
// 		show_flag : "待线下督查",
// 		query_page_param : "督察组线下督查",
// 		query_count_param : "waitSubDistriceSuperVisorCount",
// 		query_flag : "待线下督查数量"
// 	} ]
// }
];

var columns = [ {
	title : '任务id',
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
	field : 'subject',
	align : 'center',
	valign : 'middle'
}, {
	title : '创建时间',
	field : 'createTime',
	align : 'center',
	valign : 'middle'
}, {
	title : '结束日期',
	field : 'endTime',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '办理时限判断逻辑',
	field : 'lightStatus',
	align : 'center',
	valign : 'middle',
	visible : false
}, {
	title : '截止时间',
	field : 'limitTime',
	align : 'center',
	valign : 'middle'
}, {
	title : '案件状态',
	field : 'name',
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
 * 获取 待办任务标签 html 元素 并 绑定事件
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
		refreshDataTableByParams(flag.query_page_param);
		return false;
	});
};

/*
 * 获取 全网格- 待办任务标签 html 元素 并 绑定事件
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

/**
 * 拼接 待办任务标签 html 元素
 */

function appendLiHeaderTag() {
	var temp_item = null;
	for (var i = todo_flags.length - 1; i >= 0; i--) {
		itemFlag = todo_flags[i];
		if (itemFlag.id == user_group_type) {
			temp_item = itemFlag;

            var img_index = 0;
			var img_counts = LiHeaderTagMap.length;
			var ulObj = $("#ul-header-tag");
			ulObj.empty();
			for (var j = temp_item.flags.length - 1; j >= 0; j--) {
				flag = temp_item.flags[j];
				getLiHeaderTag(flag, '--', LiHeaderTagMap[img_index].imgUrl);
				img_index = (img_index + 1) % img_counts;
			};
			// if(user_group_type == 6){	// 委办局用户添加全网格事件
			// 	getQwgLiHeaderTag("--",LiHeaderTagMap[0].imgUrl);
			// }

			var colorArr = [ 'e5bb31', "2dd896", 'df7c3b', '3b97e0', 'e5bb31',
					'2dd896' ]
			$('.funShow li>a').each(function(index, item) {
				$(item).css('background-color', '#' + colorArr[index]);
			});

            $.ajax({
                method : 'post',
                dataType : 'json',
                url : url_prefix + task_count_url,
                success : function(data) {
                	// console.log(typeof data);
                	// console.log(data);
                    for (var j = temp_item.flags.length - 1; j >= 0; j--) {
                        flag = temp_item.flags[j];
                        $("#tag-li-"+flag.show_flag+" > i").html(data.res[flag.query_count_param]);
                    }
                }
            });


			if(user_group_type == 6){	// 委办局用户添加全网格事件
				$.ajax({	// 刷新全网格事件
					method : 'post',
					dataType : 'json',
					url : url_prefix + qwgRefresh,
					success : function(data) {
						$.ajax({	// 获取全网格事件列表
							method : 'post',
							dataType : 'json',
							url : url_prefix + qwgList,
							data : {
								rows : 10,
								page : 1,
								issueTypeId : issueTypeId
							},
							success : function(data){
								updateQwgCounts(data.total);
							}
						})
					}
				});
			}
		};
	};
};

// 更新全网格标签数字
function updateQwgCounts(counts){
	$("#tag-li-全网格 > i").html(counts);
}

function initDataTable(height) {
	var $table = $("#" + boot_table_id);
	$table.bootstrapTable({
		height: (height),
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pagination : true,
		striped : true, // 是否显示行间隔色
		columns : columns,
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		dataType : 'json',
		url : url_prefix + task_page_url,
		queryParams : function(params) {
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                s_name: ""
            }
        }
        // },
        // responseHandler:function (res) {
			// console.log(res);
			// return JSON.parse(res);
        // }

	});

	$table.on("click-cell.bs.table", function(event, column_name, value, row) {
		if (column_name == "issueNumber") {
			console.log(row);
			showIssueDealPage(row.id,row.name);
		}else if(column_name == "orderno"){
            console.log(row);
            showQwgDetails(row);
		}
	});	

	$table.on("load-success.bs.table", function(event, data) {
		//console.log(event);
		console.log(data);
		if (isQWGing){
			var options = $("#" + boot_table_id).bootstrapTable('getOptions');
			updateQwgCounts(options.totalRows);
		}
	});
};

function refreshDataTableByParams(query_page_param) {
	isQWGing = false;
	var options = $("#" + boot_table_id).bootstrapTable('getOptions');

	$("#" + boot_table_id).bootstrapTable('refreshOptions', {
		columns : columns,
		url : url_prefix + task_page_url,
		queryParams : {
            rows: options.pageSize, //页面大小
            page: 1, //页码,
            s_name: query_page_param
        }
        // },
        // responseHandler:function (res) {
        //     return JSON.parse(res);
        // }
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
        // ,
        // responseHandler:function (res) {
        //     return JSON.parse(res);
        // }
	});
};

/**
 * 显示案件处理页面
 */
var page_suffix;
// jsp 映射表
var jsp_include_mapper = {
	'key15' : "dbshequchuli",
	"key2" : 'sqzcz',
	'key3' : "dbjiedaofenfa",
	'key4' : "qujizhongxin",
	'key5' : "wbjjs",
	'key6' : "dbwbjcl",
    'key7' : "dbwbjla",
    'key9' : "comment",
	'key13' : "jdxxdc",
	'key14' : "qjzxxxdc",
	'key8' : "qjzxxgclsx",
	//'key10' : "zgbmlb",
	'key10' : "wbjcl", //需要联办的案件也是需要派遣的
	'key11' : "ynajcl",
	'key12' : "dbjdbmcl"
};


var taskId = null;
function showIssueDealPage(taskId_,task_name) {
	taskId = taskId_;
	$.ajax({
		method : 'post',
		url : url_prefix + query_issue_by_taskId,
		dataType : 'json',
		data : {
			taskId : taskId
		},
		success : function(data) {
            data = (typeof data=="object")?data:$.parseJSON(data)

			// 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
			myapp.ajDetail = data.tDbMainIssueBean;
			myapp.ajstatus = myapp.ajDetail.status;
            console.log(myapp.ajstatus);
			if(myapp.ajstatus == 10 && task_name=="联办执法中队处理"){
                myapp.ajstatus = 7; //跳转到执法中队处理页面
			}
			var status = "key" + myapp.ajstatus;
			$("#myModal").modal().show();
			page_suffix = eval("jsp_include_mapper." + status);
			// 关联处理页面 根据 issueObj.tMainIssueBean.status 字段
			$('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp',{},()=>{
                $.get(url_prefix+"/issues/getAttachments/"+taskId,{},(data)=>{
                    var $rep = $('.report-list');
                	var $han = $('.handle-list');
                	var $eva =  $('.evaluation-list');
                	//清空附件列表里面的附件
					if($rep){
                        $rep.children().remove();
                        //案件上报时上传的附件
                        var ra =  data.reportAttachments;
                        if (ra && ra.length>0){
                            $.each(ra,(i,o)=>{
                                console.log(o);
                                var attach = $('<a class="link-col" href="#" onclick="showAttachment(\''+o.savePath+'\')">'+o.name+'</a><br>');
                                $('.report-list').append(attach);
                            });
                        }
					}

					if($han){
						$han.children().remove()
                        //处理时上传的附件
                        var ha = data.handleAttachments;
                        if (ha && ha.length>0){
                            $.each(ha,(i,o)=>{
                                var attach = $('<a class="link-col" href="#" onclick="showAttachment(\''+o.savePath+'\')">'+o.name+'</a><br>');
                                $('.handle-list').append(attach);
                            });
                        }
					}

                    if ($eva){
						$eva.children().remove();
                        //评价时上传的附件
                        var ea = data.evaluationAttachments;
                        if (ea && ea.length>0){
                            $.each(ea,(i,o)=>{
                                var attach = $('<a class="link-col" href="#" onclick="showAttachment(\''+o.savePath+'\')">'+o.name+'</a><br>');
                                $('.evaluation-list').append(attach);
                            });
                        }
					}

                });
			});
			// console.log("path:"+url_prefix + '/page/sub/' + page_suffix + '.jsp');
		}
	});
	$.ajax({
		method : 'get',
		url : url_prefix+commentDetail+taskId,
		dataType : 'json',
		success : function(data) {
            data = (typeof data=="object")?data:$.parseJSON(data)
            myapp.commentDetail=data.rows;
			//初始化提示框
			 $('[data-toggle="tooltip"]').tooltip();
		}
	});
	myapp.$http.get(url_prefix + speedOfProgress + taskId).then(
		function(response) {
			var data = JSON.parse(response.data);
			myapp.speedOfProgress=data.rows;
			//console.log(myapp.speedOfProgress);
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
							//console.log($(dom).parent().children('.text').innerHeight(),$(dom).parent().parent().innerHeight());
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
        uploadSuccessHandler(response, file, list){
        	file.name = response.name;
        	file.url = response.url;
        	this.uploadFileList = list;
        },
        rejectQwgIssue(){
        	this.loading = true;
            var self = this;
            var files = [];
            for(var i=0;i<this.uploadFileList.length;i++){
                files.push(this.uploadFileList[i].url);
            }
            $.ajax({
                method : 'post',
                url : url_prefix+qwgReject,
                dataType : 'json',
                data : {
                    orderNo : self.qwgDetail.orderno,
                    comments : $("#qwg-comments").val(),
                    files:files.join(",")
                },
                success : function(data) {
                    self.loading = false;
                    showDialog('提示','案件['+ myapp.qwgDetail.orderno +']退回成功!',function(){
                        $(".modal").modal('hide');
                    });
                    refreshDataTableForQwg();
                    $('#myModal_2').modal('hide');
                }
            })
        },
        completeQwgIssue(){
            var self = this;
            var files = [];
            for(var i=0;i<this.uploadFileList.length;i++){
                files.push(this.uploadFileList[i].url);
            }
            this.loading = true;
            $.ajax({
                method : 'post',
                url : url_prefix + qwgComplete,
                dataType : 'json',
                data : {
                    orderNo : self.qwgDetail.orderno,
                    comments : $("#qwg-comments").val(),
                    files:files.join(",")
                },
                success : function(data) {
                    self.loading = false;
                    if(data.errMsg){
                        showDialog('提示','案件['+ myapp.qwgDetail.orderno +']处理失败!',function(){
                            $(".modal").modal('hide');
                        });
					} else {
                        showDialog('提示','案件['+ myapp.qwgDetail.orderno +']处理成功!',function(){
                            $(".modal").modal('hide');
                        });
                        refreshDataTableForQwg();
                        $('#myModal_2').modal('hide');
					}
                }
            })
        }
    }

});

$(function() {
	appendLiHeaderTag();

	var height = $('.content').height() - $('.funShow').height();

	if(document.body.clientWidth < 800){
		height = document.body.clientHeight - 200;
	}

	initDataTable(height);
	
	initDialog();

	$('#myModal_2 .close').click(function(){
		$('#myModal_2').modal('hide');
	});
});