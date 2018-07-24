<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>受理交办管理</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css">

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sdkh.js"></script>

</head>
<body>

<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-jxkh" />
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-sljbkh" />
<!-- 导航辅助 结束 -->

<div class="row" style="margin: 0;">
    <div class="minheight">
        <%--<div class="col-sm-3">--%>
        <%--<div id="treeview" class=""></div>--%>
        <%--</div>--%>
        <div class="col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading">受理交办</div>

                <!-- 新增 -->
                <div id="fade" class="black_overlay"></div>
                <div id="MyDiv" class="white_content">
                    <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')"><i class="glyphicon glyphicon-remove"></i></span>
                    </div>
                    <form  class='form-horizontal clearfix' id="addPostForm" method="post" enctype="multipart/form-data">
                        <table class="table" style="height: 300px;text-align: left;">

                            <tr>
                                <th>交办问题来源</th>
                                <th><input type="text" name="source"  class="form-control" aria-describedby="basic-addon1" placeholder="交办问题来源"></th>
                                <th height="34px;">交办问题内容</th>
                                <th><textarea type="text" name="content"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入交办问题内容"></textarea>
                                </th>
                            </tr>
                            <tr>
                                <th>交办问题机构</th>
                                <th>
                                    <select name="qhzdName" id="qhzdName" class='form-control' readonly="readonly">
                                        <option value="-1">请选择</option>
                                    </select>
                                    <input type="hidden" name="qhzdId" id="qhzdId"/>
                                </th>
                                <th>交办问题扣分</th>
                                <th><input type="number" name="score"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入投诉扣分"></th>
                            </tr>
                            <input type="hidden" name="type" value="04"> <%--问责类型 --%>

                        </table>
                        <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                            <button type="button" class="btn btn-default" onclick="addUser();">确定</button>
                            <button type="button" class="btn btn-default" onclick="CloseDiv('MyDiv','fade')">取消</button>
                        </div>
                    </form>
                </div>

                <!-- 编辑 -->
                <div id="EditFade" class="black_overlay"></div>
                <div id="EditDiv" class="white_content">
                    <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('EditDiv','EditFade')"><i class="glyphicon glyphicon-remove"></i></span>
                    </div>
                    <form  class='form-horizontal clearfix' id="editPostForm" method="post" enctype="multipart/form-data">
                        <table class="table" style="height: 300px;text-align: left;">
                            <tr>
                                <th height="34px;">交办问题来源</th>
                                <th><input type="text" name="source"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入交办问题来源" >
                                </th>
                                <th height="34px;">交办问题内容</th>
                                <th><textarea  name="content"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入交办问题内容"></textarea>
                                </th>
                            </tr>
                            <tr>
                                <th>交办问题机构</th>
                                <th>
                                    <select name="qhzdName" id="EditqhzdName" class='form-control'>
                                        <option value="-1">请选择</option>
                                    </select>
                                    <input type="hidden" name="qhzdId" id="EditqhzdId"/>
                                </th>
                                <th>交办问题扣分</th>
                                <th><input type="number" name="score"  class="form-control" aria-describedby="basic-addon1" placeholder="请输入交办问题扣分"></th>
                            </tr>
                        </table>
                        <input type="hidden" name="id">
                        <%--<input type="hidden" name="type" value="04"> &lt;%&ndash;类型&ndash;%&gt;--%>
                        <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                            <button type="button" class="btn btn-default" onclick="updateUser();">确定</button>
                            <button type="button" class="btn btn-default" onclick="CloseDiv('EditDiv','EditFade')">取消</button>
                        </div>
                    </form>
                </div>

                <div id="toolbar" class="btn-group">
                    <button id="btn_add" type="button" class="btn btn-default" onclick="ShowDiv('MyDiv','fade')">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default" onclick="ShowDiv('EditDiv','EditFade')">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default" onclick = "deleteUser();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>

                </div>
                <table id="table">

                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //弹出隐藏层
    function ShowDiv(show_div,bg_div){

        if(show_div == "EditDiv"){
            var selectedRow = $('#table').bootstrapTable('getSelections', null);
            if(selectedRow == null || selectedRow.length==0){
                alert("请选择要修改的记录");
                return false;
            }else{
                selectedRow = selectedRow[0];
                $("#EditDiv input[name='id']").val(selectedRow.id);
                $("#EditDiv input[name='source']").val(selectedRow.source);
                console.log(selectedRow.content);
                $("#EditDiv textarea[name='content']").val(selectedRow.content);
                console.log("3==="+  $("#EditDiv textarea[name='content']").val());
                $("#EditDiv input[name='score']").val(selectedRow.score);


                // 是否有网格编码
                if(selectedRow.qhzdName==null || selectedRow.qhzdName == ""){
                    // 无网格编码的，则将网格编码区域置空
                    $("#EditqhzdId").val(-1);
                    $("#EditqhzdName").val(-1);
                }

                $("#EditDiv input[name='qhzdId']").val(selectedRow.qhzdId);
                $("#EditDiv select[name='qhzdName']").val(selectedRow.qhzdName);

//                $.ajax({
//                    url:"../../group/findGroupByUserId.action",
//                    async:false,
//                    dataType:'json',
//                    data:{
//                        userId : selectedRow.id
//                    },
//                    success:function(result){
//                        if(result.rows.length>0){
//                            var groupId = result.rows[0].groupId;
//                            $("#EditDiv input[name='group.groupId']").val(groupId);
//                            $("#userTypeEdit").find("option[tag='"+groupId+"']").attr("selected",true);
//                        }
//                    }
//                });
            }

        }


        document.getElementById(show_div).style.display='block';
        document.getElementById(bg_div).style.display='block' ;

        var bgdiv = document.getElementById(bg_div);
        bgdiv.style.width = document.body.scrollWidth;
    };
    //关闭弹出层
    function CloseDiv(show_div,bg_div){
        document.getElementById(show_div).style.display='none';
        document.getElementById(bg_div).style.display='none';
    };
    $("#cancel").click(function(){
        $("#EditDiv").hide();
        $("#EditFade").hide();
    })
</script>
</body>
</html>