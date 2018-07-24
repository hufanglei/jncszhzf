/**
 * 用户管理
 */
var qhzd_query_url = '/qhzd/selectQhzd.action';
var user_page_url = '/user/userPage.action';
var group_list_url = '/group/listAllGroups.action';
var zTreeObj;
var zTreeDomId = 'tree';
var page_size = 10;
var page_num = 1;
var zTree_root_pid = '320100000000';
var boot_table_id = 'boot-table-yhgl';

$(function(){
	$('.left').height($('.right').height());

	$('.content .tbody tbody').click(function(e){
		$(this).sureTr(e);
	});

	initLayerTree(zTreeDomId);
});


var columns =[
              {
                  title: 'id',
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    visible:false
                }, 
                {
                	title: '姓名',
                	field: 'userName',
                	align: 'center',
                	valign: 'middle',
                	formatter:function(value,row,index){
                		return '<a>'+value+'</a>';
                	}
                },
                {
                	title: '电话',
                	field: 'tel',
                	align: 'center',
                	valign: 'middle'
                }, 
                {
                	title: '邮箱',
                	field: 'email',
                	align: 'center',
                	valign: 'middle'
                },
                {
                	title: '角色',
                	field: 'groups',
                	align: 'center',
                	valign: 'middle'
                }, 
                {
                	title: '组织编码',
                	field: 'orgNumber',
                	align: 'center',
                	valign: 'middle',
                		visible:false
                },
                {
                	title: '所属街道',
                	field: 'jiedao',
                	align: 'center',
                	valign: 'middle'
                }, 
                {
                	visible:false,
                	title: '用户类型',
                	field: 'userTypeID',
                	align: 'center',
                	valign: 'middle'
                }
              ];

			


function initDataTable(){
	var $table = $("#"+boot_table_id);
	$table.bootstrapTable({
        height:($('.content').height()-$('.funShow').height()),
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pagination:true,
        striped: true,    //是否显示行间隔色
        columns:columns,
        method:'post',
        contentType : "application/x-www-form-urlencoded",
        dataType:'json',
        url:url_prefix+user_page_url,
        queryParams:{
        	rows:page_size,
        	page:page_num
        }
	});
};

function refreshDataTableByParams(group_type){
	var $table = $("#"+boot_table_id);
	$("#"+boot_table_id).bootstrapTable('refresh',{
		url:url_prefix+user_page_url,
		query:{
        	rows:page_size,
        	page:page_num,
        	group_type:group_type
    }}); 
}


/**
 * 初始化图层树
 * @param domId
 */
function initLayerTree(domId){
	var _url= url_prefix + group_list_url;
	var setting = {
			callback:{
				onClick: zTreeOnClick
			},
			data: {
				key: {
					name:"groupName",
					title: "groupName"
				},
				simpleData: {
					enable: true,
					idKey: "stid",
					pIdKey: "pid",
					rootPId: zTree_root_pid
				}
			},
			view: {
				selectedMulti: false
			}
	};
	
	$.ajax({
		url : _url,
		data:{sort:"id",order:"asc"},
		dataType : "json",
		method : "post",
		success : function(data){
			var result = data.groupList;
			var i = 2;
			for ( var item in result) {
				item.stid = i;
				item.pid = 0;
				i++ ;
			}
			result.push({'groupName':'江宁区','stid':1,'pid':0});
			
			zTreeObj = $.fn.zTree.init($("#"+domId), setting, data.groupList);
			// var node = zTreeObj.getNodeByTId(zTree_root_pid);
			// zTreeObj.expandNode(node,true,false,false);
		},
		error : function(err){
			console.log(err);
		}
	});

};

function zTreeOnClick(event, treeId, treeNode){

	console.log(treeNode);
	refreshDataTableByParams(treeNode.groupTag);
};