/**
 * 待办任务 
 */

/**
 * Li 待办任务标签块映射
 */
var LiHeaderTagMap = {
		"部门待签收":{
			imgUrl:"bmdqs.svg"
		},
		"待立案":{
			imgUrl:"dla.svg"
		},
		"待处罚":{
			imgUrl:"dcf.svg"
		},
		"待结案":{
			imgUrl:"dja.svg"
		}
};

/*
 * 获取 待办任务标签 html　元素 并 绑定事件
 */
function getLiHeaderTag(tagName,tagIssueCount,callBackFunc){
	var itemId = 'tag-li-'+tagName;
	var itemTemplate = "<li><a href='javascript：void(0);' id='"+itemId+"'>"+ tagName
					+"<embed src='"+url_prefix+"/static/images/"+LiHeaderTagMap[tagName].imgUrl+"' type='image/svg+xml' pluginspage='http://www.adobe.com/svg/viewer/install/' />"
					+"</a><i>"+tagIssueCount+"</i></li>";
	$("#"+itemId).click(callBackFunc);
	
	return itemTemplate;
};

/**
 * 拼接 待办任务标签 html　元素
 */
function appendLiHeaderTag(){
	var ulObj = $("#ul-header-tag");
	ulObj.empty();
	
	for ( var item in LiHeaderTagMap) {
		ulObj.append(getLiHeaderTag(item,10,function(){}));
	}
};

var columns =[
              {
                  title: '日期',
                    field: 'days',
                    align: 'center',
                    valign: 'middle'
                }, 
                {
                	title: 'counts',
                	field: 'counts',
                	align: 'center',
                	valign: 'middle'
                }
              ];

var boot_table_id = "boot-table";
function initDataTableByUrl(url){
	
	var $table = $("#"+boot_table_id);
	$table.bootstrapTable({
		classes:"table",
        method:'POST',
        dataType:'json',
        striped: true,    //是否显示行间隔色
        height:($('.content').height()-$('.funShow').height()-20),
       // sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        url:url_prefix+"/statistics/issueCount",
        pagination:true,
//        pageNumber:1,                       //初始化加载第一页，默认第一页
//               pageSize: 20,                       //每页的记录行数（*）
//              pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
//              uniqueId: "id",                     //每一行的唯一标识，一般为主键列
//        showExport: true,                    
//        exportDataType: 'all',
        columns:columns
    });
}


var task_query_url = "/task/taskPage?row=10&page=1&userId=zqsq01"

$(function(){
	appendLiHeaderTag();
	
	var colorArr=['e5bb31',"2dd896",'df7c3b','3b97e0','e5bb31','2dd896']
	$('.funShow li>a').each(function(index,item){
		$(item).css('background-color','#'+colorArr[index]);
	});
	initDataTableByUrl();
});


// 网格员
// ——街道派遣数量 <- 
// ——自处置数量
// ——待评价数量


// 街道中心
// ——待派发数量


// 街道内设科室1，街道内设科室2
// ——待处理任务数量


// 街道督察组
// ——待线下督查数量


// 区级中心
// ——待派发数量
// ——待修改时限数量
// ——待评价数量


// 领导小组办公室，区主管领导，去分管领导
// ——疑难问题待处理数量


// 主管部门
// ——待主管部门接收数量
// ——待主管部门立案数量
// ——待主管部门处理数量
// ——待主管部门联办数量


// 区级中心督察组
// ——待线下督查数量