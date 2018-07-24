<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--<script src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>--%>
</head>

<div style='margin-top: 25px;'>
				  <!-- Nav tabs -->
  <div class="tab-content" style='background: #fff;'>
    <div role="tabpanel" class="tab-pane active" id="zichuzhi">
    	<form  class='form-horizontal clearfix' id="frm1" method="post" enctype="multipart/form-data">

			<div class="clearfix">

				<div class="form-group col-sm-8">
					<div class="biaoti">扣分：</div>
					<div  style="border: 1px solid #ccc">
						<select name="score" >
							<option value="1">1分</option>
							<option value="2">2分</option>
						</select>
					</div>
				</div>
				<div class="form-group col-sm-2">
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group col-sm-8">
					<div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
					<textarea class="form-control blyj" rows="10"  id="comment" readonly></textarea>
				</div>
			</div>

			<div class="clearfix">
				<div class="form-group col-sm-4">
					<div class="biaoti">监督检查附件：</div>
					<div class="report-list" style="height:80px; border: 1px solid #ccc">

					</div>
				</div>
			</div>
	  		<div class="clearfix">
	  			<div class="form-group col-sm-12">
					<button class='btn btn-success' id="tab1" status="0">审核通过</button>
					<button class='btn btn-danger' id="tab2" status="1">审核不通过</button>
	  			</div>
	  		</div>
	  		<input id="id" type="hidden" name='id' />
	  		<input id="status" type="hidden" name='status' />
	  	</form>
	</div>
 </div>
</div>
<script>
    $(document).ready(function(){
        $("#comment").val(jdjc_comment);
        $("#id").val(jdjc_id);
		for(var index=0;index < $(".btn").length;index++){
            var _index = index+1;
            $('#tab'+_index).click(function(){
                var flag=true;
                $("#status").val($(this).attr('status'));
                var blyj_values=$('#frm'+ $(this).attr("tab_num")).find('.blyj').val();
                if(!blyj_values){
                    layerAlert('办理意见不能为空!');
                    flag = false;
                }
                if (flag) {
                    submitForm('frm1', '/taskDb/jdjcsh');
                }
            })
        }
    })

</script>
</html>