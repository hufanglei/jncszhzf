<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
		src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#chuzhi" aria-controls="home" role="tab" data-toggle="tab">处理</a></li>
		<li role="presentation"><a href="#zengjiashixian" aria-controls="profile" role="tab" data-toggle="tab">增加时限</a></li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content" style='background: #fff;'>

		<div role="tabpanel" class="tab-pane active" id="chuzhi">
			<form class='form-horizontal clearfix' id="frm1" method="post"
				enctype="multipart/form-data">
				<div class="col clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti">选择执法中队</div>
						<select name="zfzd" id="zfzd" class='form-control'>
							<option value="">请选择</option>
						</select>
					</div>
					<div class="form-group col-sm-2">
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" v-model="area1"
							name='comment'></textarea>
					</div>
				</div>
				<div class="clearfix">
					<div class="form-group col-sm-4">
						<div class="biaoti">处理前：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>
					<div class="form-group col-sm-4">
						<div class="biaoti">立案：</div>
						<div class="register-list" style="height:80px; border: 1px solid #ccc">
						</div>
					</div>
				</div>
				<%-- 附件上传容器，需要添加附件上传功能的页面只需添加这个容器就可以了 --%>
				<%--<div class="clearfix upload-container">--%>
				<%--</div>--%>
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab1'>提交</button>
					</div>
				</div>
				<input type="hidden" name='taskId' class="taskId" /> <input
					type="hidden" name='state' value='1' />
			</form>
		</div>

		<div role="tabpanel" class="tab-pane" id="zengjiashixian">
			<form class='form-horizontal clearfix' id="frm2" method="post"
				enctype="multipart/form-data">
				<div class="clearfix">
					<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
						<textarea class="form-control blyj" rows="3" name='comment'></textarea>
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
				<div class="clearfix">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab2'>提交</button>
					</div>
				</div>
				<input type="hidden" name='taskId' class="taskId" /> <input
					type="hidden" name='state' value='2' />
			</form>
		</div>
	</div>
</div>
</div>
<script>
	tab_animate();
	$(".taskId").val(taskId);
	$(function(){
		for(var index=0;index<$(".tab-pane").length;index++){
			var _index=index+1;
			$('#tab'+_index).attr("tab_num",_index);
				$('#tab'+_index).click(function(){
				    var flag=true;
                    if($("#zfzd").val()==""||$(".blyj").val()==null){
                        layerAlert("请选择执法中队!");
                        flag= false;
					}
                    var blyj_values=$('#frm'+ $(this).attr("tab_num")).find('.blyj').val();
                    console.log(blyj_values)
                    if(!blyj_values){
                        layerAlert('办理意见不能为空!');
                        flag = false;
                    }
					if(flag) {
                         submitForm('frm' + $(this).attr("tab_num"), '/task/competentDepartmentHandle');
                    }
	    	})
		}
	})

    //获取执法中队信息
    $.ajax({
        url:'../../user/selectUserByMenberShip.action?userTypeID=8',
        async: false,
        success: function(result){
            $.each(result,function(idx,item){
                $("#zfzd").append("<option value='"+item.id+"'>"+item.userName+"</option>");
            })
        }
    });



</script>
</html>