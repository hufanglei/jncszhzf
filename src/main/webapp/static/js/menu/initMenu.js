// 导航条初始化相关

// 导航元素id映射表
var top_menu_id_mapper = {
		'系统管理':'nav-flag-top-sys',
		'案件上报':'nav-flag-top-ajsb',
		'待办任务':'nav-flag-top-dbrw',
		'批管同步':'nav-flag-top-pgtb',
		'案件查询':'nav-flag-top-ajcx',
		'统计分析':'nav-flag-top-tjfx',
		// '日志查询':'nav-flag-top-log',
		// '归档共享':'nav-flag-top-gdgx',
		'绩效考核':'nav-flag-top-jxkh',
		'地图分析':'nav-flag-top-dtfx'
};
var sub_menu_id_mapper = {
		'用户管理':'nav-flag-sub-sys-user',
		'角色管理':'nav-flag-sub-sys-role',
		'权限管理':'nav-flag-sub-sys-permission',
		'流程管理':'nav-flag-sub-sys-process',
		'工作日管理':'nav-flag-sub-sys-workday',
		'数据字典管理':'nav-flag-sub-sys-zd',
		'用户新增':'nav-flag-sub-sys-user-add',

		'案件上报':'nav-flag-sub-ajsb',

		'待办任务':'nav-flag-sub-dbrw',

		'批管派发':'nav-flag-sub-pgtb-pgpf',
		'批管处理':'nav-flag-sub-pgtb-pgcl',
		'批管查询':'nav-flag-sub-pgtb-pgcx',

		'案件跟踪':'nav-flag-sub-ajcx-ajgz',
		'案件筛选':'nav-flag-sub-ajcx-ajsx',
		'追踪案件':'nav-flag-sub-ajcx-zzaj',

		'案发量统计':'nav-flag-sub-tjfx-afl',
		'案件状态占比统计':'nav-flag-sub-tjfx-ajzt',
		'街道案件统计':'nav-flag-sub-tjfx-jdajtj',
		'街道办结案件统计':'nav-flag-sub-tjfx-jdbjatj',
		'处置部门案件统计':'nav-flag-sub-tjfx-czbmaj',
		'处置部门办结案件统计':'nav-flag-sub-tjfx-czbmbja',
		'事项归口统计':'nav-flag-sub-tjfx-sxgk',

		// '日志查询':'nav-flag-sub-log',

	    '归档查询':'nav-flag-sub-gdgx-gdgxcx',
	    '督办归档案件':'nav-flag-sub-gdgx-dbgdcx',
		'督察督办':'nav-flag-sub-dbsb',//案件上报的 考核小组用的
		'督办任务':'nav-flag-sub-dcdbrw',//待办中的 街道和委办局用的

		'工作量统计':'nav-flag-sub-jxkh-gzltj',
		'案件处理统计':'nav-flag-sub-gdgx-ajcltj',
		'街道考核记分':'nav-flag-sub-gdgx-zhzfgzkh',
		'委办局考核记分':'nav-flag-sub-gdgx-wbjzhzfkh',
		'反复投诉管理':'nav-flag-sub-gdgx-fftskh',
		'督办问责管理':'nav-flag-sub-gdgx-dbwzkh',
		'工作协调管理':'nav-flag-sub-gdgx-gzxtkh',
		'受理交办管理':'nav-flag-sub-gdgx-sljbkh',
		'巡查机制管理':'nav-flag-sub-gdgx-xcjzkh',
		'宣传报道管理':'nav-flag-sub-gdgx-xcbdkh',
		'监督检查管理':'nav-flag-sub-gdgx-jdjckh',

		'手动考核':'nav-flag-sub-gdgx-czslkh',
		'处置时效':'nav-flag-sub-gdgx-czsxkh',
		'执法痕迹':'nav-flag-sub-gdgx-zfhjkh',
		'结果评估':'nav-flag-sub-gdgx-jgpgkh',
		'作风评估':'nav-flag-sub-gdgx-zfpgkh',
		'附加项':'nav-flag-sub-gdgx-fjxkh',

		'案件分布':'nav-flag-sub-dtfx-ajfb',
		'人员定位':'nav-flag-sub-dtfx-trace',
		'热力地图':'nav-flag-sub-dtfx-heat'
};


function isNullOrEmpty(obj){
	if(obj==null||obj==""||obj==undefined){
		return true;
	}else{
		return false;
	}

};

// 初始化导航条 
function init_JNCSZHZF_Menu(){
	var topMenuMapper = {};	//存储一级导航id与名称对应关系；
	
	$.ajax({
		url: url_prefix + '/permission/getpermission_by_grouptag.action',
		method : 'post',
		data:{
			groupTag: groupTag
		},
		dataType:'json',
		success:function(result){
			// console.log(result);
			// console.log("========");
            for (var i = 0; i < result.length; i++) {
				item = result[i];
				if(item != null){
					if(item.fatherid == 0){	// 一级导航栏目
						$("#nav-list").append(generateTopMenu(item));
						topMenuMapper[item.id]=item.name;
					}else{	// 二级导航栏目
						var fatherDomId = top_menu_id_mapper[topMenuMapper[item.fatherid]];
						$("#" + fatherDomId + " > ul").append(generateSubMenu(item));
					}
				}
			};
			
 			var navFlagTop=$("#nav-flag-top").val();
 			var navFlagSub=$("#nav-flag-sub").val();
			if(!isNullOrEmpty(navFlagTop)&&!isNullOrEmpty(navFlagSub)){
				$("#"+navFlagTop).addClass("active");
				$("#"+navFlagSub).addClass("current");
			};
			
			//点击下滑收缩 toggle
			$('.nav-list>li>a').click(function(){
				$(this).parent().children('.drop-toggle').slideToggle(300);
				if(!$(this).parent().hasClass('active')){
					$(this).parent().addClass('active');
				}
				else{
					$(this).parent().removeClass('active')
				}
				$(this).parent().siblings().removeClass('active').children('.drop-toggle').slideUp(300);
				
				setTimeout(function(){
					aside_h=parseFloat($('.nav-list').css('height'));
					if( aside_h+60>$('aside').height()){
						$('.nav-list').css('box-shadow','2px -6px 6px #aaa')
						$('aside').css('box-shadow','none')
					}else{
						$('.nav-list').css('box-shadow','none')
						$('aside').css('box-shadow','2px -6px 6px #aaa')
					}
				},350)
			})
		}
	});
	
	// 拼接一级导航栏目
	function generateTopMenu(item){
		return '<li id="'+top_menu_id_mapper[item.name]+'"><a href="##">'+item.name+'<span></span></a><ul class="drop-toggle" ></ul></li>';
	}
	
	// 拼接二级导航栏目
	function generateSubMenu(item){
		return '<li id="'+sub_menu_id_mapper[item.name]+'"><a href="'+item.tag+'">'+item.name+'</a></li>';
	}
};

init_JNCSZHZF_Menu();