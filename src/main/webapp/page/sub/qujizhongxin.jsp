<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#paiqian"
			aria-controls="home" role="tab" data-toggle="tab">派遣</a></li>
		<li role="presentation"><a href="#paifa" aria-controls="profile"
			role="tab" data-toggle="tab">派发</a></li>
		<li role="presentation"><a href="#lianban"
			aria-controls="profile" role="tab" data-toggle="tab">联办</a></li>
		<li role="presentation"><a href="#yinananjian"
			aria-controls="profile" role="tab" data-toggle="tab">疑难案件</a></li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content" id='qujizhongxin' style='background: #fff;'>
		<div role="tabpanel" class="tab-pane active" id="paiqian">
			<form class='form-horizontal clearfix' id="frm1" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-6">
						<div class="biaoti">选择街道</div>
						<select id="street" class='form-control' name='subdistrictGroupID'>
						</select>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" v-model='area1'
							name='comment'></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>

				</div>
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab1'>提交</button>
					</div>
				</div>
				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="3" />
			</form>
		</div>


		<div role="tabpanel" class="tab-pane" id="paifa">
			<form class='form-horizontal clearfix' id="frm2" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-6">
						<div class="biaoti">选择委办局</div>
						<select id="bureau" class='form-control' name='districtCenterDepartGroup'>
						</select>
					</div>
				</div>

				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" v-model='area2'
							name='comment'></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab2'>提交</button>
					</div>
				</div>
				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="1" />
			</form>
		</div>


		<div role="tabpanel" class="tab-pane" id="lianban">
			<form class='form-horizontal clearfix' id="frm3" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-6">
						<label  class="biaoti">联办委办局:</label>
						<input id="cooperationDeparts" name="cooperationDeparts" type="hidden" value=""/>
						<input id="showDepts" name="showDepts" type="text" class='form-control' placeholder="选择联办委办局" readonly/>
		                <div class="popover fade bottom in" id="panel" style="max-width:700px; margin-left:80px; display:block;visibility: hidden;">
		                    <div class="arrow" style="margin-left: -200px"></div>
		                    <div class="popover-title" style="height:35px;">
		                        <div class="checkbox" style="margin-top:0;margin-bottom:0px;"></div><label><input type="checkbox" onchange="CheckAll(this)" />全选</label>
		                        <button type="button" class="close" onclick="hideMultiSel()">
		                            <span aria-hidden="true">&times;</span>
		                            <span class="sr-only">Close</span>
		                        </button>
		                    </div>
							<div class="popover-content" style="width:700px;">
		                        <ul class="list-inline" id="multi-sel-ul" >
		                            <!--<li><div class="checkbox"></div><label><input type="checkbox" value="0" onclick="Choose(this)"/>5M</label></li>-->
		                        </ul>
		                    </div>
		                </div>
	                </div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" v-model='area3'
							name='comment'></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab3'>提交</button>
					</div>
				</div>
				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="4" />
			</form>
		</div>

		<div role="tabpanel" class="tab-pane" id="yinananjian">
			<form class='form-horizontal clearfix' id="frm4" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-6">
						<div class="biaoti">选择处理部门</div>
						<select id="district-dept" name="difficultCaseDealDepartgroupID" class="form-control">
						</select>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea v-model='area4' class="form-control blyj" rows="3"
							name='comment'></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab4'>提交</button>
					</div>
				</div>
				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="2" />
			</form>
		</div>
	</div>
</div>



<script type="text/javascript">
   console.log("9090909");

	var wbj_table_id = "tb_wbj_list";
   function loadGroupData(groupType,successCallBack){
       $.ajax({
           url:url_prefix+'/group/findGroupByType2.action',
           method: 'post',
           dataType : 'json',
           data:{
               typeId:groupType || '2'
           },
           success: function(result){
               successCallBack(result);
           }
       });
   };
	tab_animate();
	$(".taskId").val(taskId);
	$(function() {

		
		$("#tab3").click(function(){
			
			if($("#cooperationDeparts").val().indexOf(",")==-1){
				showDialog('提示','请选择至少两个联办委办局!',function(){
					hideDialog();
				});
				return false;
			}
		});
		
		$("#showDepts").click(function(){
			showMultiSel(document.getElementById("showDepts"));
		});
		
		for (var index = 0; index < $(".tab-pane").length; index++) {
			var _index = index + 1;
			$('#tab' + _index).attr("tab_num", _index);
			$('#tab' + _index).click(
				function() {
                    var flag=true;
                    var blyj_values = $('#frm' + $(this).attr("tab_num")).find('.blyj').val();
                    console.log(blyj_values)
                    if (!blyj_values) {
                        layerAlert('办理意见不能为空!');
                        flag = false;
                    }
                    if(flag) {
                        submitForm('frm' + $(this).attr("tab_num"), '/task/districtCenterDistribute');

                    }
				}
			);
		};
		
		loadGroupData(2,function(result){
			var data = result.rows;
			for(var i in data){
				$("#street").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
			}
		});
		
		loadGroupData(4,function(result){
			var data = result.rows;
			for(var i in data){
				$("#district-dept").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
			}
		});

		loadGroupData(5,function(result){
			var data = result.rows;
			for(var i in data){
				$("#bureau").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
				data[i].oid = i;				
			}

		});
		loadGroupData(1,function(result){
			var data = result.rows;
			for(var i in data){
				data[i].oid = i;
			}
            loadMultiSelData(data);
		});

	});

	
	//加载多选框数据
	function loadMultiSelData(data){
		var ul = $("#multi-sel-ul");
		for (var i = 0; i < data.length; i++) {
			ul.append("<li><div class='checkbox'></div><label><input type='checkbox' value="
							+ data[i].groupId
							+ " onclick='ChooseMultiSel(this)'/>"
							+ data[i].groupName+ "</label></li>");
		}
	}
	
	//显示多选框
	function showMultiSel(t) {
		//设置多选框显示的位置，在选择框的中间
		var left = t.offsetLeft + t.clientWidth / 2
				- $("#panel")[0].clientWidth / 2
		var top = t.offsetTop + t.clientHeight + document.body.scrollTop;
		$("#panel").css("visibility", "visible");
		//$("#panel").css("margin-left", left);
		$("#panel").css("margin-top", top + 5);
	}
	//隐藏多选框
	function hideMultiSel() {
		$("#panel").css("visibility", "hidden");
	}
	//全选操作
	function CheckAll(t) {
		var name = "";
		var ids = "";
		var popoverContent = $($(t).parent().parent().parent().children()[2]);
		popoverContent.find("input[type=checkbox]").each(function(i, th) {
			th.checked = t.checked;
			if (t.checked) {
				name += $(th).parent().text() + ",";
				ids += $(th).val() + ",";
			}
		});
		name = name.substr(0, name.length - 1);
		ids = ids.substr(0, ids.length - 1);
		$("#showDepts").val(name);
		$("#cooperationDeparts").val(ids);
	}

	//勾选某一个操作
	function ChooseMultiSel(t) {
		var oldName = $("#showDepts").val();
		var name = oldName == "" ? "," + $("#showDepts").val() : ","
				+ $("#showDepts").val() + ",";
		var ids = oldName == "" ? "," + $("#cooperationDeparts").val() : "," + $("#cooperationDeparts").val()
				+ ",";
		var newName = $(t).parent().text();
		var newid = $(t).val();

		if (t.checked) {//选中的操作
			$("#showDepts").val(name += newName + ",");
			$("#cooperationDeparts").val(ids += newid + ",");
		} else {//去掉选中的操作
			var index = name.indexOf("," + newName + ",");
			var len = newName.length;
			name = name.substring(0, index)
					+ name.substring(index + len + 1, name.length);

			var index = ids.indexOf("," + newid + ",");
			var len = newid.length;
			ids = ids.substring(0, index)
					+ ids.substring(index + len + 1, ids.length);
		}
		name = name.substr(1, name.length - 2);
		ids = ids.substr(1, ids.length - 2);
		$("#showDepts").val(name);
		$("#cooperationDeparts").val(ids);
	}
</script>



</html>