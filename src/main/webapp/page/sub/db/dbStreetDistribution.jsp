 <%--
    督办案件： 街道派发
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script
            src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){

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

        <li role="presentation" class="active"><a href="#paifa" aria-controls="profile"
                                   role="tab" data-toggle="tab">派发</a></li>

    </ul>

    <!-- Tab panes -->
    <div class="tab-content" style='background: #fff;'>
        <div role="tabpanel" class="tab-pane active" id="paifa">
            <form class='form-horizontal clearfix' id="frm1" method="post"
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
                        <div class="biaoti">上报时上传的附件：</div>
                        <div class="report-list" style="height:80px; border: 1px solid #ccc">

                        </div>
                    </div>
                </div>
                <div class="clearfix upload-container">
                </div>
                <div class="clearfix"  align="center" style="margin-top: 20px">
                    <div class="form-group col-sm-12">
                        <button class='btn btn-success' id='tab1' type="button">提交</button>
                    </div>
                </div>
                <input id="taskId" class="taskId" type="hidden" name='taskId' /> <input
                    id="status" type="hidden" name='state' value="1" />
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
                var flag=true;
                var blyj_values = $('#frm' + $(this).attr("tab_num")).find('.blyj').val();
                console.log(blyj_values);
                if($("#nsbm").val()==""||blyj_values==null){
                    layerAlert("请选择内设部门!");
                    flag= false;
                }
                if (!blyj_values) {
                    layerAlert('办理意见不能为空!');
                    flag = false;
                }
                if(flag){
                    submitForm('frm'+$(this).attr("tab_num"),'/dbissueTask/dbStreetDistribution');
                }
            })
        }
    })
</script>
</html>