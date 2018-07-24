<%--
    督办案件: 考核组评价页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--<script src="${pageContext.request.contextPath}/static/js/issues/modal-content.js"></script>--%>
</head>
<div style='margin-top:25px;'>
    <!-- Nav tabs -->
    <div class="tab-content" style='background: #fff;'>
        <div role="tabpanel" class="tab-pane active" id="zichuzhi">
            <form class='form-horizontal clearfix' id="frm1" method="post" enctype="multipart/form-data">
                <div class="clearfix">
                    <div class="form-group col-sm-8">
                        <div class="biaoti"><span style="color: red"> * </span>办理意见：</div>
                        <textarea class="form-control blyj" rows="3" v-modal='area1' name='comment'></textarea>
                    </div>
                </div>
                <div class="clearfix">
                    <div class="form-group col-sm-4">
                        <div class="biaoti">附件：</div>
                        <div class="report-list" style="height:80px; border: 1px solid #ccc">

                        </div>
                    </div>
                </div>

                <div class="clearfix">
                    <div class="form-group col-sm-8">
                        <div class="biaoti">扣分：</div>
                        <div  style="border: 1px solid #ccc">
                            <select name="score" class='form-control'>
                                <option value="1">1分</option>
                                <option value="2">2分</option>
                                <option value="3">3分</option>
                                <option value="4">4分</option>
                                <option value="5">5分</option>
                                <option value="6">6分</option>
                                <option value="7">7分</option>
                                <option value="8">8分</option>
                                <option value="9">9分</option>
                                <option value="10">10分</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-sm-2">
                    </div>
                </div>

                <div class="clearfix">
                    <div class="form-group col-sm-6">
                        <button class='btn btn-success evaluation' id="tab1" status="1">满意</button>
                        <button class='btn btn-primary evaluation' id="tab2" status="2">不满意</button>
                        <button class='btn btn-danger evaluation' id="tab3" status="3">发回重办</button>
                    </div>
                </div>
                <input id="taskId" type="hidden" name='taskId' class="taskId"/>
                <input id="status" type="hidden" name='status' />

            </form>
        </div>
    </div>
</div>
<script>

    $(function () {
        tab_animate();
        $(".taskId").val(taskId);

        for(var index=0;index < $(".evaluation").length;index++){
            var _index = index+1;
            console.log(_index);
            $('#tab'+_index).click(function(){
                $('#tab' + _index).attr("tab_num", _index);
                var flag=true;
                $("#status").val($(this).attr('status'));
                var blyj_values=$('.blyj').val();
                console.log(blyj_values);
                if(!blyj_values){
                    layerAlert('办理意见不能为空!');
                    flag = false;
                }
                if (flag) {
                    submitForm('frm1', '/dbissueTask/dbexaminationGroupEvaluation');
                }
            })
        }
    })
</script>
</html>