<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色管理</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/groupManage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
</head>
<body style="margin: 1px">
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-sys"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-sys-role"/>
<!-- 导航辅助 结束 -->

<div class="row" style="margin: 0;">
    <div class="minheight">
        <div class="col-sm-3">
            <div id="treeview" class=""></div>
        </div>
        <div class="col-sm-9">
            <div class="panel panel-default">
                <div class="panel-heading">角色管理</div>

                <!-- 新增 -->
                <div id="fade" class="black_overlay"></div>
                <div id="MyDiv" class="white_content">
                    <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')"><i class="glyphicon glyphicon-remove"></i></span>
                    </div>
                    <form class='form-horizontal clearfix' id="addPostForm" method="post" enctype="multipart/form-data">
                        <table class="table" style="height: 300px;text-align: left;">
                            <tr>
                            <th>角色ID</th>
                            <th><input type="text" name="groupId" class="form-control" placeholder="请输入角色名" aria-describedby="basic-addon1"></th>
                            </tr>
                            <tr>
                            <th>角色名称</th>
                            <th><input type="text" name="groupName" class="form-control" aria-describedby="basic-addon1"></th>
                            </tr>
                            <tr>
                                <th>角色类型</th>
                                <th>
                                    <select name="groupTag" id="addRoleId" class='form-control'>
                                        <option value="-1">请选择</option>
                                    </select>
                                </th>
                            </tr>
                            <tr>
                                <th>所属用户组</th>
                                <th>
                                    <select name="groupTypeId" id="addGroupId" class='form-control'>
                                        <option value="-1">请选择</option>
                                    </select>
                                </th>
                            </tr>
                            <tr>
                                <th></th>
                                <th>
                                    <select name="groupPid" id="addSubGroupId" class='form-control'>
                                        <option value='-1'>请选择(可选)</option>
                                    </select>
                                </th>
                            </tr>
                        </table>
                        <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                            <button type="button" class="btn btn-default" onclick="submitAddTicket();">确定</button>
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
                    <form class='form-horizontal clearfix' id="editPostForm" method="post" enctype="multipart/form-data">
                        <table class="table" style="height: 300px;text-align: left;">
                            <tr>
                                <th>角色ID</th>
                                <th><input type="text" name="groupId" class="form-control" placeholder="请输入角色名" aria-describedby="basic-addon1"
                                           readonly="readonly"></th>
                            </tr>
                            <tr>
                                <th>角色名称</th>
                                <th><input type="text" name="groupName" class="form-control" aria-describedby="basic-addon1"></th>
                            </tr>
                            <tr>
                                <th>角色类型</th>
                                <th>
                                    <select name="groupTag" id="editGroupTag" class='form-control'>
                                        <option value="">请选择</option>
                                    </select>
                                </th>
                            </tr>
                            <tr>
                                <th>所属用户组</th>
                                <th>
                                    <select name="groupTypeId" id="editGroupId" class='form-control'>
                                        <option value="-1">请选择</option>
                                    </select>
                                </th>
                            </tr>
                            <tr>
                                <th></th>
                                <th>
                                    <select id="editGroupPId" class='form-control' style="display: none">
                                        <option value='-1'>请选择(可选)</option>
                                    </select>
                                    <input type="hidden" name="groupPid">
                                </th>
                            </tr>
                        </table>
                        <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                            <button type="button" class="btn btn-default" onclick="submitEditTicket();">确定</button>
                            <button type="button" class="btn btn-default" id="cancel">取消</button>
                        </div>
                    </form>
                </div>
                <!-- 权限 -->
                <div id="AllotFade" class="black_overlay"></div>
                <div id="AllotDiv" class="allot_content">
                    <div style="text-align: right; cursor: default;margin-bottom: 10px;">
                        <span style="font-size: 16px;" onclick="CloseDiv('AllotDiv','AllotFade')"><i class="glyphicon glyphicon-remove"></i></span>
                    </div>
                    <div id="powertree" class=""></div>
                    <div class="btn-group" role="group" aria-label="" style="margin-left: 40%;">
                        <button type="button" class="btn btn-default" onclick="applyPermissions();">确定</button>
                        <button type="button" class="btn btn-default" onclick="CloseDiv('AllotDiv','AllotFade');">取消</button>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button id="btn_allot" type="button" class="btn btn-default" onclick="showApplyPermissions();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>分配权限
                    </button>
                    <button id="btn_add" type="button" class="btn btn-default" onclick="ShowDiv('MyDiv','fade')">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                    </button>
                    <button id="btn_edit" type="button" class="btn btn-default" onclick="ShowDiv('EditDiv','EditFade')">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button>
                    <button id="btn_delete" type="button" class="btn btn-default" onclick="deleteGroup();">
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
    function ShowDiv(show_div, bg_div) {

        if (show_div == "EditDiv") {
            var selectedRow = $('#table').bootstrapTable('getSelections', null);

            if (selectedRow == null || selectedRow.length == 0) {
                layer.msg("请选择要修改的记录");
                return false;
            } else {
                selectedRow = selectedRow[0];
                console.log(selectedRow);
                $("#EditDiv input[name='groupId']").val(selectedRow.groupId);
                $("#EditDiv input[name='groupName']").val(selectedRow.groupName);
                $("#editGroupTag").val(selectedRow.groupTag);
                $("#editGroupId").val(selectedRow.groupTypeId);

                if(selectedRow.groupPid != null && selectedRow.groupPid.length != 0) {
                    $.ajax({
                        method: 'post',
                        url: '../../groups/' + selectedRow.groupTypeId + '/nodes',
                        dataType: 'json',
                        success: function (result) {
                            var data = result.result;
                            var $sonDom = $("#editGroupPId");
                            $sonDom.empty();
                            $sonDom.append("<option value='-1'>请选择(可选)</option>");
                            if (data.length == 0) {
                                $sonDom.css("display", "none");
                            } else {
                                $sonDom.css("display", "block");
                                for (var i in data) {
                                    $sonDom.append("<option value='" + data[i].groupId + "'>" + data[i].groupName + "</option>");
                                }
                            }
                            $("#editGroupPId").val(selectedRow.groupPid);
                        }
                    });
                }else{
                    $("#editGroupPId").val("-1");
                }
            }

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
    });

</script>
</body>
</html>