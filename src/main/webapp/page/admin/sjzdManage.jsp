<%--
  Created by IntelliJ IDEA.
  User: guoguo
  Date: 2018/1/15
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>数据字典管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sjzdManage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
</head>

<body>

<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-sys"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-sys-zd"/>
<!-- 导航辅助 结束 -->

<div class="row" style="margin: 0;">
    <div class="minheight">

        <div class="col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="" style="height: 30px;">
                        <div class="filter">
                            <form action="">
                                <div class="form-group bs_date">
                                    <label class='control-label' style='font-weight: normal; float: left; padding-right: 10px;'>所属部门</label>
                                    <div class="">
                                        <select id="ssbm" class='form-control' name='ssWeibanju'>
                                            <option value="">请选择</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="toolbuton" class="">
                                    <button id="btn_delete" type="button" class="" onclick="deleteUser();">删除</button>
                                    <button id="btn_edit" type="button" class=""
                                            onclick="ShowDiv('EditDiv','EditFade')">修改
                                    </button>
                                    <button id="btn_add" type="button" class="" onclick="ShowDiv('MyDiv','fade')">添加
                                    </button>
                                    <button type="button" onclick="refreshData()">查询</button>
                                </div>
                            </form>
                        </div>
                    </div>


                    <!-- 新增 -->
                    <div id="fade" class="black_overlay"></div>
                    <div id="MyDiv" class="white_content">
                        <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')"><i
                                class="glyphicon glyphicon-remove"></i></span>
                        </div>
                        <form class='form-horizontal clearfix' id="addPostForm" method="post"
                              enctype="multipart/form-data">
                            <table class="table" style="height: 300px;text-align: left;">
                                <tr>
                                    <th height="34px;">权利编码</th>
                                    <th><input type="text" name="powerCode" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入权利编码">
                                    </th>
                                    <th height="34px;">所属部门</th>
                                    <th><input type="text" name="ssWeibanju" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入所属部门"></th>
                                </tr>
                                <tr>
                                    <th height="34px;">检查事项名称</th>
                                    <th><textarea name="powerDesc" class="form-control" aria-describedby="basic-addon1"
                                                  placeholder="请输入问责内容"></textarea>
                                    </th>
                                    <th>行驶阶层</th>
                                    <th><input type="text" name="execLevel" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入行驶阶层"></th>
                                </tr>
                                <tr>
                                    <th height="34px;">工作日时限</th>
                                    <th><input type="text" name="limitedWorkdays" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入工作日时限"/>
                                    </th>
                                    <th></th>
                                    <th>
                                    </th>
                                </tr>
                                <%-- <input type="hidden" name="" id="id"/>--%>

                            </table>
                            <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                                <button type="button" class="btn btn-default" onclick="addList();">确定</button>
                                <button type="button" class="btn btn-default" onclick="CloseDiv('MyDiv','fade')">取消
                                </button>
                            </div>
                        </form>
                    </div>

                    <!-- 编辑 -->
                    <div id="EditFade" class="black_overlay"></div>
                    <div id="EditDiv" class="white_content">
                        <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('EditDiv','EditFade')"><i
                                class="glyphicon glyphicon-remove"></i></span>
                        </div>
                        <form class='form-horizontal clearfix' id="editPostForm" method="post"
                              enctype="multipart/form-data">
                            <table class="table" style="height: 300px;text-align: left;">
                                <tr>
                                    <th height="34px;">权利编码</th>
                                    <th><input type="text" name="powerCode" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入权利编码">
                                    </th>
                                    <th>所属部门</th>
                                    <th><input type="text" name="ssWeibanju" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入所属部门"></th>
                                </tr>
                                <tr>
                                    <th height="34px;">检查事项名称</th>
                                    <th><textarea name="powerDesc" class="form-control" aria-describedby="basic-addon1"
                                                  placeholder="请输入问责内容"></textarea>
                                    </th>
                                    <th>行驶阶层</th>
                                    <th><input type="text" name="execLevel" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入行驶阶层"></th>
                                </tr>
                                <tr>
                                    <th height="34px;">工作日时限</th>
                                    <th><input type="text" name="limitedWorkdays" class="form-control"
                                               aria-describedby="basic-addon1" placeholder="请输入工作日时限"/>
                                    </th>
                                    <th></th>
                                    <th>
                                    </th>

                                </tr>
                                <input type="hidden" name="id" id="id"/>
                            </table>
                            <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                                <button type="button" class="btn btn-default" onclick="updateList();">确定</button>
                                <button type="button" class="btn btn-default" onclick="CloseDiv('EditDiv','EditFade')">
                                    取消
                                </button>
                            </div>
                        </form>
                    </div>

                    <%--<div id="toolbar" class="btn-group">
                        <button id="btn_add" type="button" class="btn btn-default" onclick="ShowDiv('MyDiv','fade')">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                        </button>
                        <button id="btn_edit" type="button" class="btn btn-default" onclick="ShowDiv('EditDiv','EditFade')">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                        </button>
                        <button id="btn_delete" type="button" class="btn btn-default" onclick="deleteUser();">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                        </button>
                    </div>--%>
                    <table id="table">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出隐藏层
    function ShowDiv(show_div, bg_div) {

        if (show_div == "EditDiv") {
            var selectedRow = $('#table').bootstrapTable('getSelections', null);

            if (selectedRow == null || selectedRow.length == 0) {
                layer.msg("请选择要修改的记录");
                return false;
            }
            selectedRow = selectedRow[0];
            $("#EditDiv input[name='id']").val(selectedRow.id);
            $("#EditDiv input[name='powerCode']").val(selectedRow.powerCode);
            $("#EditDiv input[name='ssWeibanju']").val(selectedRow.ssWeibanju);
            $("#EditDiv textarea[name='powerDesc']").val(selectedRow.powerDesc);
            $("#EditDiv input[name='execLevel']").val(selectedRow.execLevel);
            $("#EditDiv input[name='limitedWorkdays']").val(selectedRow.limitedWorkdays);
        }
        document.getElementById(show_div).style.display = 'block';
        document.getElementById(bg_div).style.display = 'block';

        var bgdiv = document.getElementById(bg_div);
        bgdiv.style.width = document.body.scrollWidth;
    };

    //关闭弹出层
    function CloseDiv(show_div, bg_div) {
        document.getElementById(show_div).style.display = 'none';
        document.getElementById(bg_div).style.display = 'none';
    };
    $("#cancel").click(function () {
        $("#EditDiv").hide();
        $("#EditFade").hide();
    })

        $(document).ready(function(){
            $.ajax({
                url:'../../frame/selectSsWeibanju',
                async: false,
                success:function (results){
                    var objs = JSON.parse(results);
                    var datas = eval(objs.rows);
                    for(var b in datas){
                        $("#ssbm").append("<option value='"+datas[b].ssWeibanju+"'>"+datas[b].ssWeibanju+"</option>");
                    }
                }
            });
        });
</script>
</body>
</html>