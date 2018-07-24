<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div style='margin-top: 25px;'>
	<!-- Nav tabs -->
	<div class="tab-content" style='background: #fff;'>
    	<div role="tabpanel" class="tab-pane active" id="zichuzhi">
    	 	<form  class='form-horizontal clearfix' id="frm1" method="post" enctype="multipart/form-data">
	   			<div class="clearfix">
	  				<div class="form-group col-sm-8">
						<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					    <textarea class="form-control" rows="3" name='comment'></textarea>
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
						<button class='btn btn-success' id="tab1">提交</button>
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
	    		submitForm('frm'+$(this).attr("tab_num"),'/task/supervisionOffLine');
	    	})
		}
	})
</script>
</html>