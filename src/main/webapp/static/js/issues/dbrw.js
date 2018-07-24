/**
 * 待办任务
 */
// 待办任务列表查询
var task_page_url = "/task/taskPage.action";
// 待办任务数量查询
var task_count_url = "/task/getTaskCount.action";
// 根据taskId 查询案件信息
var query_issue_by_taskId = '/issues/getIssueByTaskId.action';
// 根据taskId获得点击的批注详情
var commentDetail='/task/listHistoryCommentDetal.action?taskId=';
//获取上报人历史批注
var department='/task/getDepartment.action?issueNumber=';
//根据taskId获取流程图
var speedOfProgress='/task/listAction.action?taskId=';
//根据上报人用户id查询上报人用户名称
var showAddUserNameById = '/issues/showAddUserNameById.action?addUserId=';
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
        query_page_param: "街道派遣",
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
	}, /*{
		show_flag : "待修改时限",
		query_page_param : "修改处理时限",
		query_count_param : "districeCenterWaitModTimeLimitCount",
		query_flag : "待修改时限数量"
	},*/ {
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
		{
        show_flag: "待派遣",
        query_page_param: "主管部门派遣",
        query_count_param: "waitCompetentDepartmentHandleCount",
        query_flag: "待主管部门处理数量"
    }, {
        show_flag: "待联办",
        query_page_param: "联办",
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
        query_page_param: "街道科室处理",
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
        query_page_param : "执法中队处理",
        query_count_param : "waitExamination", // TODO 待开发
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
			if(user_group_type == 3){	// 委办局用户添加全网格事件
				getQwgLiHeaderTag("--",LiHeaderTagMap[0].imgUrl);
			}

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


			if(user_group_type == 3){	// 委办局用户添加全网格事件
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
			console.log(params)
            return {
                rows: params.limit, //页面大小
                page: params.offset / params.limit + 1, //页码,
                s_name: ''
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
			showIssueDealPage(row.id,row.name,row.issueNumber);
		}else if(column_name == "orderno"){
            showQwgDetails(row);
		}
	});	

	$table.on("load-success.bs.table", function(event, data) {
		//console.log(event);
		console.log(data);
		if (isQWGing){
			var options = $("#" + boot_table_id).bootstrapTable('getOptions');
			updateQwgCounts(options.totalRows);
			console.log(options.totalRows)
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
	"key2" : 'sqzcz',
	'key3' : "jiedaofenfa",
	'key4' : "qujizhongxin",
	'key5' : "wbjjs",
	'key6' : "wbjcl",
    'key7' : "wbjla",
	'key8' : "qjzxxgclsx",
    'key9' : "comment",
    //'key10' : "zgbmlb",
	'key10' : "wbjcl", //需要联办的案件也是需要派遣的
	'key11' : "ynajcl",
	'key12' : "jdbmcl",
    'key13' : "jdxxdc",
    'key14' : "qjzxxxdc",
    'key15' : "shequchuli",

    'key16' : "lbjiedaofenfa",//联办街道中心派发
    'key17' : "lbjdbmcl",//联办联办街道科室处理
    'key18' : "lbsqzcz",//联办网格员处理
};


var taskId = null;
var cooperateFlag  = false; //是否联办
var taskName = '';
var  issueNumber=null;
function showIssueDealPage(taskId_,task_name,issueNumber_) {
    taskName = task_name;
	//隐藏附件控件
    $('.fileUpload').hide();
	taskId = taskId_;
    issueNumber=issueNumber_;
	var addUserId; //上报人
	$.ajax({
		method : 'post',
		url : url_prefix + query_issue_by_taskId,
		dataType : 'json',
		data : {
			taskId : taskId
		},
		success : function(data) {
            data = (typeof data=="object")?data:$.parseJSON(data)
            console.log(data);
			// 填充案件详情数据 如 ： issueObj.tMainIssueBean.issueSubject
			myapp.ajDetail = data.tMainIssueBean;
            addUserId = myapp.ajDetail.addUserid;
            myapp.ajstatus = myapp.ajDetail.status;
            cooperateFlag = false;
			if(myapp.ajstatus == 10 ){
                cooperateFlag = true;
				//联办 委办局待处理页面
				if(groupTag==5 && (task_name=='联办主管部立案' || task_name=='联办') ){
					//跳转到
					myapp.ajstatus = 5; // 是否立案
				}
				//联办 街道中心待处理页面
                if(groupTag==2 && (task_name=='联办街道处理' || task_name=='联办') ){
                    //跳转到
                    myapp.ajstatus = 16; // 跳转到街道派发页面
                }
				//联办  街道科室处理
                if(groupTag==6 && (task_name=='联办街道科室处理' || task_name=='联办') ){
                    //跳转到
                    myapp.ajstatus = 17; // 跳转到街道科室处理页面
                }
                //联办 网格员处理
                if(groupTag==1 && task_name=='联办网格员处理' ){
                    //跳转到
                    myapp.ajstatus = 18; // 跳转到联办 网格员处理页面
                }
				if(task_name=="联办执法中队处理"){
                    myapp.ajstatus = 7; // 跳转到执法中队处理页面
				}
				console.log(myapp.ajstatus);
			}
			var status = "key" + myapp.ajstatus;
			$("#myModal").modal({backdrop:'static'}).show();
			page_suffix = eval("jsp_include_mapper." + status);
			// 关联处理页面 根据 issueObj.tMainIssueBean.status 字段
			$('#dbrw-cl').load(url_prefix + '/page/sub/' + page_suffix + '.jsp',{},()=>{
				console.log("path:"+url_prefix + '/page/sub/' + page_suffix + '.jsp');
                if($('.upload-container') && $('.upload-container').length >0 ){
                    $('.upload-container').children().remove();
                    $('.upload-container').append($('.fileUpload'));
                    $('.fileUpload').show();
                }

                // 显示处理前，处理后，立案，评价时上传的附件
				// 要显示附件列表的页面只需要加上相应的类名
				// 例如处理前的附件：
				// <div class="report-list" style="height:80px; border: 1px solid #ccc"></div>
                $.get(url_prefix+"/issues/getAttachments/"+taskId,{},(data)=>{
                	var containers = {
						'reportAttachments':$('.report-list'),        //处理前附件容器
						'handleAttachments':$('.handle-list'),        //处理后附件容器
						'evaluationAttachments':$('.evaluation-list'),//评价附件容器
						'registerAttachments':$('.register-list')     //立案附件容器
                	};

                	for(var key in containers){
                		var container = $(containers[key]);
                        if (container){
                			//清空附件列表
                			container.children().remove();
                			//获取对应容器的附件数据
                            var attachments = data[key];
                            if(attachments && attachments.length>0){
                                $.each(attachments,(i,o)=>{
                                 var attachment = $('<a class="link-col" href="#" onclick="showAttachment(\''+o.savePath+'\')">'+o.name+'</a><br>');
                                    container.append(attachment);
                                 });
							}else{
                            	container.parent().hide();
							}
						}
					}
                });
			});
		}
	}).then(function () {
		//异步显示上报人addUserId
        $.ajax({
            method : 'get',
            url : url_prefix + showAddUserNameById + addUserId,
            dataType : 'json',
            success : function(data) {
                data = (typeof data=="object")?data:$.parseJSON(data)
                myapp.addUserName = data.addUserName;
            }
		});
    });

	//原弹窗下侧批注内容
	/*$.ajax({
		method : 'get',
		url : url_prefix+commentDetail+taskId,
		dataType : 'json',
		success : function(data) {
            data = (typeof data=="object")?data:$.parseJSON(data)
            myapp.commentDetail=data.rows;
			//初始化提示框
			 $('[data-toggle="tooltip"]').tooltip();
		}
	});*/

	//办理信息
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
	//
	$.ajax({
		method : 'get',
		url : url_prefix+department+issueNumber,
		dataType : 'json',
		success : function(data) {
            data = (typeof data=="object")?data:$.parseJSON(data)
            myapp.department=data.rows;
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
        uploadFileList:[],
		fileList:[],
		files:[],
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
		detailComment:[],
		speedOfProgress:[],
		timeTaken:0,
        addUserName:""
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

        handlePreview(file) {
            console.log(file)
            dialog.url = file.url;
            dialog.dialogVisible = true;
        },
        handleSuccess(response, file, list) {
            var _this = this;
            _this.files.push({
                "fileName": response.url,
                "originalFileName": response.name,
                "size":response.size
            });
            $("#uploadFile").val(JSON.stringify(_this.files));
        },
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
                  //  refreshDataTableForQwg();
                    $('#myModal_2').modal('hide');
                }
            })
        },
        completeQwgIssue(){
            var self = this;
            var files = [];
            console.log(this.uploadFileList);
            for(var i=0;i<this.uploadFileList.length;i++){
                files.push(this.uploadFileList[i].url);
            }
            this.loading = true;
            console.log(files.join(","))
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


                    console.log(data.result[0].success)
                    if(data.result[0].success==true){
                        $(".modal-body").modal('hide');
                        showDialog('提示','案件['+ myapp.qwgDetail.orderno +']处理成功!',function(){
                            $(".modal").modal('hide');
                        });
                        refreshDataTableForQwg();
                        $('#myModal_2').modal('hide');

                    }else if (data.result[0].success==false) {
                        $(".modal-body").modal('hide');
                        showDialog('提示','案件处理失败!',function(){
                            $(".modal").modal('hide');
                        });
                        $('#myModal_2').modal('hide');
                    }
                /*    if(data.errMsg){
                        showDialog('提示','案件['+ myapp.qwgDetail.orderno +']处理失败!',function(){
                            $(".modal").modal('hide');
                        });
					} else {
                        showDialog('提示','案件['+ myapp.qwgDetail.orderno +']处理成功!',function(){
                            $(".modal").modal('hide');
                        });
                        refreshDataTableForQwg();
                        $('#myModal_2').modal('hide');
					}*/
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

	$('#myModal.close').click(function(){
		$('#myModal').modal('hide');
	});
    $('#myModal_2 .close').click(function(){
        $('#myModal_2').modal('hide');
    });
    $('#myModal').on('hidden.bs.modal', function () {
        window.location.href = window.location.href;
    });
    $('#myModal_2').on('hidden.bs.modal', function () {
        window.location.href = window.location.href;
    });
    $('.tab-content').on('hidden.bs.modal', function () {
        window.location.href = window.location.href;
    });

});