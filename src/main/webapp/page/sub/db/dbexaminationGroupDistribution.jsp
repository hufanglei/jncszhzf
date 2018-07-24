<%--考核组派发--%>
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
		<li role="presentation" class="active" id="presentation1"><a href="#paiqian"
			aria-controls="home" role="tab" data-toggle="tab">派遣</a></li>
		<li role="presentation" id="presentation2"><a href="#paifa" aria-controls="profile"
			role="tab" data-toggle="tab">派发</a></li>

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
						<div class="biaoti">附件：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>

				</div>
				<div class="pic"></div>
				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab1'>提交</button>
					</div>
				</div>

				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="1" />
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
						<div class="biaoti">附件：</div>
						<div class="report-list" style="height:80px; border: 1px solid #ccc">

						</div>
					</div>
				</div>
				<div class="pic"></div>

				<div class="clearfix"  align="center" style="margin-top: 20px">
					<div class="form-group col-sm-12">
						<button class='btn btn-success' id='tab2'>提交</button>
					</div>
				</div>
				<input id="taskId" type="hidden" name='taskId' class="taskId" /> <input
					id="state" type="hidden" name='state' value="2" />
			</form>
		</div>

	</div>
</div>

<div class="clearfix upload-container"  style="display: none">
</div>

<script type="text/javascript">

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

		for (var index = 0; index < $(".tab-pane").length; index++) {
			var _index = index + 1;
            $('#presentation' + _index).attr("tab_num", _index);
            $('#presentation' + _index).click(function () {
                $(".upload-container").show();
                $('#frm' + $(this).attr("tab_num")).find(".pic").append($(".upload-container"));
            });

			$('#tab' + _index).attr("tab_num", _index);
			$('#tab' + _index).click(
				function() {
				     //alert(1);
                    var flag=true;
                    var blyj_values = $('#frm' + $(this).attr("tab_num")).find('.blyj').val();

                    console.log(blyj_values);
                    if (!blyj_values) {
                        layerAlert('办理意见不能为空!');
                        flag = false;
                    }
                    if(flag) {
                        submitForm('frm' + $(this).attr("tab_num"), '/dbissueTask/dbexaminationGroupDistribution');

                    }
				}
			);
		};
        $('#presentation1').click();
		loadGroupData(2,function(result){
			var data = result.rows;
			for(var i in data){
				$("#street").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
			}
		});

		loadGroupData(5,function(result){
			var data = result.rows;
			for(var i in data){
				$("#bureau").append("<option value='"+data[i].groupId+"'>"+data[i].groupName+"</option>");
				data[i].oid = i;				
			}

		});


	});

	



</script>



</html>