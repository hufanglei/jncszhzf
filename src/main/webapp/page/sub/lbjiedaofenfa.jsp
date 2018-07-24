<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var orgRegionId = orgId.substr(0,12);
		$.ajax({
			url:'../../qhzd/selectQhzd.action?qhjb=3&ssqhdm='+orgRegionId,
			async: false,
			success: function(result){
				var obj = JSON.parse(result);
				var data = eval(obj.rows);
				for(var i in data){
					$("#shequ").append("<option value='"+data[i].qhdm+"'>"+data[i].qhmc+"</option>");
				}
			}
		});
		
		/*$("#shequ").bind("change",function(){
			$.ajax({
				url:'../../user/selectUserByClause.action?orgNumber='+$("#shequ").val()+'&userTypeID=1',
				async: false,
				success: function(result){
					var obj = JSON.parse(result);
					var data = eval(obj.rows);
					$("#wgy").empty();
					console.log();
					for(var i in data){
						$("#wgy").append("<option value='"+data[i].id+"'>"+data[i].userName+"</option>");
					}
				}
			});
		});*/

        $.ajax({
            url:'../../user/selectUserByMenberShip.action?userTypeID=1',
            async: false,
            success: function(result){
                $.each(result,function(idx,item){
					$("#wgy").append("<option value='"+item.id+"'>"+item.userName+"</option>");
				})
            }
        });

		$.ajax({
			url:'../../group/findGroupByType.action?userTypeID=6',
			async: false,
			success:function (results){
				var objs = JSON.parse(results); 
				var datas = eval(objs.rows);
				for(var b in datas){ 
					$("#nsbm").append("<option value='"+datas[b].groupId+"'>"+datas[b].groupName+"</option>");
				} 
			}
		});
	});
</script>
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#paiqian"
			aria-controls="home" role="tab" data-toggle="tab">派遣</a></li>
		<li role="presentation"><a href="#paifa" aria-controls="profile"
			role="tab" data-toggle="tab">派发</a></li>
		<%--<li role="presentation" id="sb" style="display: none"><a href="#shangbao"--%>
			<%--aria-controls="profile" role="tab" data-toggle="tab">上报</a></li>--%>
		<%--<li role="presentation"><a href="#xianxiaducha"--%>
			<%--aria-controls="profile" role="tab" data-toggle="tab">线下督查</a></li>--%>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content" style='background: #fff;'>
		<div role="tabpanel" class="tab-pane active" id="paiqian">
			<form class='form-horizontal clearfix' id="frm1" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<%--<div class="form-group col-sm-6">
						<div class="biaoti">选择社区</div>
						<select name="" id="shequ" class='form-control'
							v-model='shequSelect'>
							<option value="">请选择</option>
						</select>
					</div>--%>
					<div class="form-group col-sm-8">
						<div class="biaoti">选择网格员</div>
						<select name="gridOperatorName" id="wgy" class='form-control'
							v-model="wanggeSelect">
							<option value="">请选择</option>
						</select>
					</div>
					<div class="form-group col-sm-4">
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj " rows="3" v-model='area1'
							name='comment'  ></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc;">

						</div>
					</div>
				</div>
				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab1' type="button">提交</button>
					</div>
				</div>
				<input id="taskId" class="taskId" type="hidden" name='taskId' /> <input
					id="status" type="hidden" name='state' value="4" />
			</form>
		</div>
		<div role="tabpanel" class="tab-pane" id="paifa">
			<form class='form-horizontal clearfix' id="frm2" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-6">
						<div class="biaoti">选择内设部门</div>
						<select id="nsbm" class='form-control' v-model="neisheSelect"
							name='subdistrictDepartGroupID'>
							<option value="">请选择</option>
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
				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab2' type="button">提交</button>
					</div>
				</div>
				<input id="taskId" class="taskId" type="hidden" name='taskId' /> <input
					id="status" type="hidden" name='state' value="1" />
			</form>
		</div>
		<div role="tabpanel" class="tab-pane" id="shangbao">
			<form class='form-horizontal clearfix' id="frm3" method="post"
				enctype="multipart/form-data">
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
				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab3'>提交</button>
					</div>
				</div>
				<input id="taskId" class="taskId" type="hidden" name='taskId' /> <input
					id="status" type="hidden" name='state' value="2" />
			</form>
		</div>
		<div role="tabpanel" class="tab-pane" id="xianxiaducha">
			<form class='form-horizontal clearfix' id="frm4" method="post"
				enctype="multipart/form-data">
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" v-model='area4'
							name='comment'></textarea>
					</div>
				</div>
                <div class="clearfix">
                    <div class="form-group col-sm-4">
                        <div class="biaoti">上报时上传的附件：</div>
                        <div class="report-list" style="height:80px; border: 1px solid #ccc">

                        </div>
                    </div>
                    <div class="form-group col-sm-4">
                        <div class="biaoti">处理时上传的附件：</div>
                        <div class="handle-list" style="height:80px; border: 1px solid #ccc">
                        </div>
                    </div>
                    <div class="form-group col-sm-4">
                        <div class="biaoti">评价时上传的附件：</div>
                        <div class="evaluation-list" style="height:80px; border: 1px solid #ccc">
                        </div>
                    </div>
                </div>
				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab4'>提交</button>
					</div>
				</div>
				<input id="taskId" class="taskId" type="hidden" name='taskId' /> <input
					id="status" type="hidden" name='state' value="3" />
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils.js"></script>
<script>
    tab_animate();
	$(".taskId").val(taskId);
	console.log(taskName);
	$(function(){
		for(var index=0;index<$(".tab-pane").length;index++){
			var _index=index+1;
			$('#tab'+_index).attr("tab_num",_index);
			$('#tab'+_index).click(function(){
			    var flag = true;
				 if($(this).attr("tab_num")==1){
					 if($("#wgy").val()==""){
                         flag = false;
					 }
				 }
				 if($(this).attr("tab_num")==2){
                     if($("#nsbm").val()==""){
                         flag = false;
                     }
				 }
              /*  if($(".blyj").val()==""||$(".blyj").val()==null){
                    layerAlert('办理意见不能为空!');
                    flag = false;
            }*/
                if (flag) {
				     //alert(flag)
                    submitForm('frm' + $(this).attr("tab_num"), '/task/lbSubdistrictOfficeDistribute');
                }
	    	})
		}
	})
</script>

</html>