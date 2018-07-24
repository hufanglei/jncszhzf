<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<div class="tab-content" style='background: #fff;'>
		<div role="tabpanel" class="tab-pane active" id="zichuzhi">
			<form  class='form-horizontal clearfix' id="frm1" method="post" enctype="multipart/form-data">
			 	<div class="col clearfix">
					<div class="form-group col-sm-6" style='margin-bottom: 0;margin-top: 20px;'>
						<div class="biaoti">请选择：</div>
				   	 	<div class="radio" style='margin-bottom: 0;margin-top: 0;'>
							<input id="input-star" value="10" type="number" min=0 max=10 step=1 data-size="sm" name='evaluationLevel' data-symbol="&#xe005;" data-default-caption="" data-star-captions="{}" >
						</div>
				   	</div>
		   		</div>
		   		<div class="clearfix">
		  			<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					    <textarea class="form-control blyj" rows="3" v-model='area1'  name='comment'></textarea>
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
					<div class="form-group col-sm-4">
						<div class="biaoti">处理后：</div>
						<div class="handle-list" style="height:80px; border: 1px solid #ccc">
						</div>
					</div>
				</div>
		  		<div class="clearfix">
		  			<div class="form-group col-sm-12">
						<button class='btn btn-success' id="tab1" type="button">提交</button>
		  			</div>	
		  		</div>
		  		<input id="taskId" type="hidden" name='taskId' class="taskId"/>
		  	</form>	
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
				console.log(_index);
                var flag=true;
                var blyj_values=$('#frm'+ $(this).attr("tab_num")).find('.blyj').val();
                if(!blyj_values){
                    layerAlert('办理意见不能为空!');
                    flag = false;
                }
                if (flag) {
                    submitForm('frm' + $(this).attr("tab_num"), '/task/evaluation');
                }
        	})
		}
	})
	$("#input-star").rating({
          hoverOnClear: false,
          showClear:false,
          starCaptions: function(val) {
                  return val;
          },
          starCaptions:'10.0'
    });
</script>
</html>