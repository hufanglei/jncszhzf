\
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>归档共享查询</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/static/css/bootstrap.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/template.css"/>
    <link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/select.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/elementui/theme-default/index.css">
    <style>
        #myModal textarea.form-control {
            height: auto !important
        }

        .form-horizontal .form-group {
            margin-right: 0;
            margin-left: 0
        }

        @media (min-width: 768px) {
            .form-horizontal .control-label {
                text-align: left
            }
        }

        .upload-demo input {
            margin-left: -1000px;
        }

        .filter, .filter form {
            height: 90px;
        }

        .select_group {
            margin-top: -10px;
        }

        .cols-padding select {
            width: 150px;
        }

        .cols-padding {
            float: left;
            border: 5px;
            margin-right: 5px;
        }

        .filter button {
            margin-top: -2px;
        }

        section {
            height: 542px !important;
        }
    </style>
</head>
<body style='overflow-x: hidden;'>

<%-- 附件预览--%>
<div id="dialog">
    <el-dialog
            title=""
            :visible.sync="dialogVisible"
            center
            @close="dialogVisible = false"
            width="60%">
        <img v-bind:src="url" style="height: 100%;width: 100%"/>
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
              </span>
    </el-dialog>
</div>

<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-ajcx"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-gdgxcx"/>
<!-- 导航辅助 结束 -->
<div>
    <section>
        <div class="col-sm-3" style="padding:0px;">
            <div id="treeview" class=""></div>
        </div>
        <div class="col-sm-9" style="padding-right:0px;">
            <div class="content" style="height: 462px;">
                <div class="filter">
                    <form action="">
                        <div class="form-group bs_date">
                            <label for="dtp_input1" class=" control-label">时间范围</label>
                            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd"
                                 data-link-field="dtp_input1" data-link-format="yyyy-mm-dd">
                                <input class="form-control" size="16" type="text" value="" readonly=""> <span
                                    class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                            <input type="hidden" id="dtp_input1" value=""><br>
                        </div>
                        <div class="form-group bs_date">
                            <label for="dtp_input2" class=" control-label">至</label>
                            <div class="input-group date form_date2" data-date="" data-date-format="yyyy-mm-dd"
                                 data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                                <input class="form-control" size="16" type="text" value="" readonly=""> <span
                                    class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                            <input type="hidden" id="dtp_input2" value=""><br>
                        </div>
                        <div class="form-group bs_date">
                            <label for="caseType" class=" control-label" style="width:90px;">案件类型</label>
                            <select id="caseType" name="caseType" class='form-control'>
                                <option value="">—— 全部 ——</option>
                                <option value="XC">巡查案件</option>
                                <option value="JN">执法案件</option>
                                <%-- <option value="DC">督察督办</option>--%>
                            </select>
                        </div>
                        <br>
                        <div class="form-group bs_date select_group">
                            <label class="control-label">案件归口</label>
                            <div>
                                <div class="cols-padding">
                                    <select id='lei' name="name" class='form-control'></select>
                                </div>
                                <div class="cols-padding">
                                    <select id='xiang' name="name" class='form-control'></select>
                                </div>
                                <div class="cols-padding">
                                    <select id='issueSubject' name="issueSubject" class='form-control'></select>
                                </div>
                            </div>
                        </div>


                        <button id="findGdgxDate" type="button">查询</button>
                    </form>
                </div>
                <div class="content">
                    <table id="boot-table-dbrw">
                    </table>
                </div>
            </div>
        </div>
    </section>


    <!-- 案件详情modal start -->
    <div class="wrap modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" style="width:100%;">
        <div class=" modal-dialog modal-lg" style="width: 75%">
            <div class="modal-content" style="width: 100%">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{ajDetail.issueSubject}}</span></div>
                    </h4>
                </div>
                <div class="modal-body" id="dbrw-body">
                    <div class="shang clearfix">
                        <div class="infomation" style="width: 100%">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:hidden '>
                                <table class='table text-center'>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">案件来源</th>
                                    <th  style="background-color:#fff">{{ajDetail.issueSource}}</th>
                                    <th  style="background-color:#e9f3f4">当事人类型</th>
                                    <th  style="background-color:#fff">{{ajDetail.requestorType}}</th>
                                    <th  style="background-color:#e9f3f4">当事人姓名</th>
                                    <th  style="background-color:#fff">{{ajDetail.requestorName}}</th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">当事人电话</th>
                                    <th  style="background-color:#fff">{{ajDetail.requestorTel}}</th>
                                    <th  style="background-color:#e9f3f4">当事人地址</th>
                                    <th  style="background-color:#fff">{{ajDetail.requestorAddres}}</th>
                                    <th  style="background-color:#e9f3f4">证件号码</th>
                                    <th  style="background-color:#fff">{{ajDetail.requestorIdcard}}</th>
                                    </tbody>
                                    <tbody>

                                    <th  style="background-color:#e9f3f4">涉事企业名称</th>
                                    <th  style="background-color:#fff">{{ajDetail.refCompanyName}}</th>
                                    <th  style="background-color:#e9f3f4">涉事企业组织机构代码</th>
                                    <th  style="background-color:#fff">{{ajDetail.orgId}}</th>
                                    <th  style="background-color:#e9f3f4">紧急程度</th>
                                    <th  style="background-color:#fff">{{ajDetail.emrgencyLevel_ms}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">处置时限</th>
                                    <th  style="background-color:#fff">{{ajDetail.timeLimit}}天</th>
                                    <th  style="background-color:#e9f3f4">权利编码</th>
                                    <th  style="background-color:#fff">{{ajDetail.issueType}}</th>
                                    <th  style="background-color:#e9f3f4">地图坐标</th>
                                    <th  style="background-color:#fff">{{ajDetail.x}},{{ajDetail.y}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">事发时间</th>
                                    <th  style="background-color:#fff">{{ajDetail.issueTime}}</th>
                                    <th  style="background-color:#e9f3f4">地址描述</th>
                                    <th  style="background-color:#fff">{{ajDetail.addrDesc}}</th>
                                    <th  style="background-color:#e9f3f4">事件主题</th>
                                    <th  style="background-color:#fff">{{ajDetail.issueSubject}}</th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">事件描述</th>
                                    <th  colspan="5" style="background-color:#fff">{{ajDetail.issueDesc}}</th>

                                    </tbody>

                                    <%--<tr>
                                        <td>案件来源</td>
                                        <td>{{ajDetail.issueSource}}</td>
                                        <td>当事人类型</td>
                                        <td>{{ajDetail.requestorType}}</td>
                                    </tr>
                                    <tr>
                                        <td>涉事企业名称</td>
                                        <td>{{ajDetail.refCompanyName}}</td>
                                        <td>当事人姓名</td>
                                        <td>{{ajDetail.requestorName}}</td>
                                    </tr>
                                    <tr>
                                        <td>当事人电话</td>
                                        <td>{{ajDetail.requestorTel}}</td>
                                        <td>当事人地址</td>
                                        <td>{{ajDetail.requestorAddress}}</td>
                                    </tr>
                                    <tr>
                                        <td>证件号码</td>
                                        <td>{{ajDetail.requestorIdcard}}</td>
                                        <td>涉事企业组织机构代码</td>
                                        <td>{{ajDetail.orgId}}</td>
                                    </tr>
                                    <tr>
                                        <td>事发时间</td>
                                        <td>{{ajDetail.issueTime}}</td>
                                        <td>处置时限</td>
                                        <td>{{ajDetail.timeLimit}}</td>
                                    </tr>
                                    <tr>
                                        <td>权利编码</td>
                                        <td>{{ajDetail.issueType}}</td>
                                        <td>地图坐标</td>
                                        <td>{{ajDetail.x}},{{ajDetail.y}}</td>
                                    </tr>
                                    <tr>
                                        <td>地址描述</td>
                                        <td>{{ajDetail.addrDesc}}</td>
                                        <td>事件主题</td>
                                        <td>{{ajDetail.issueSubject}}</td>
                                    </tr>
                                    <tr>
                                        <td>事件描述</td>
                                        <td>{{ajDetail.issueDesc}}</td>
                                        <td>紧急程度</td>
                                        <td>{{ajDetail.emrgencyLevel_ms}}</td>
                                    </tr>
                                    <tr>
                                        <td>备注</td>
                                        <td>{{ajDetail.comment}}</td>
                                        <td style="background-color: #fff;"></td>
                                        <td></td>
                                    </tr>--%>
                                </table>
                            </div>
                        </div>

                        <%--<div class="jindu">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>处理流程
                                <div class='date'>总耗时
                                    <time>{{(timeTaken/1000/60/60).toFixed(2)}}</time>
                                    小时 约<span>{{(timeTaken/1000/60/60/24).toFixed(1)}}</span>工作日
                                </div>
                            </div>
                            <div class="specific" style='overflow-y:auto ;height: 155px;'>
                                <table class="table">
                                    <tr v-for="(item,index) in speedOfProgress">

                                        <td>
                                            <div class="left">
                                                <div class="text">{{item.activityName}}</div>
                                                <i>{{item.assignee}}</i>
                                                <span v-if='index!=(speedOfProgress.length-1)' class='dec'></span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="shuju">
                                                <div v-if='item.startTime' class="bumen"><span>开始时间：</span>{{item.startTime}}
                                                </div>
                                                <div v-if='item.durationInMillis && parseInt(item.durationInMillis/1000/60) > 0'
                                                     class="haoshi"><span>耗时</span>：<s>
                                                    {{parseInt(item.durationInMillis/1000/60)}} </s> 分钟 约 <u>{{(item.durationInMillis
                                                    / (1000 * 60 * 60 * 24)).toFixed(1)}}</u> 工作日
                                                </div>
                                                <div v-if='item.endTime' class="zhuban"><span>结束时间：</span>{{item.endTime}}
                                                </div>
                                                <div v-if='item.xieban' class="xieban"><span>协办部门：</span>{{item.xieban}}
                                                </div>
                                                <div v-if='item.shixian' class="shixian"><span>处置时限：</span>{{item.shixian}}
                                                </div>
                                                <div v-if='item.dasima' class="shixian"><span>处置时限：</span>{{item.dasima}}
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>--%>
                    </div>
                    <div id='dbrw-cl'>

                    </div>
                </div>

                <div class=" modal-footer" role="dialog" id="gsx_mx" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <thead>
                        <tr>
                            <th class='col-sm-3'>办理人</th>
                            <th class='col-sm-3'>附件类型</th>
                            <th class='col-sm-3'>附件</th>
                        </tr>
                        </thead>
                        <tbody class="attachment-list">

                        </tbody>
                    </table>
                </div>

                <div class="modal-footer" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <thead>
                        <tr>
                            <th class='col-sm-2'>办理人</th>
                            <th class='col-sm-2'>办理部门</th>
                            <th class='col-sm-2'>办理方式</th>
                            <th class='col-sm-2'>办理意见</th>
                            <th class='col-sm-2'>办理时间</th>
                            <th class='col-sm-2'>耗费时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for='item in commentDetail' v-if='commentDetail[0]!=null'>
                            <td class='col-sm-2'>{{item.userName}}</td>
                            <td class='col-sm-2'>{{item.department}}</td>
                            <td class='col-sm-2'>{{item.handleWay}}</td>
                            <td class='col-sm-2' data-toggle="tooltip" data-placement="top"
                                onMouseOver="$(this).tooltip('show')" :title="item.fullMessage">{{item.comment}}
                            </td>
                            <td class='col-sm-2'>{{item.endTime}}</td>
                            <td class='col-sm-2'>{{item.consumTime}}</td>
                        </tr>
                        <tr >
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment'>{{ajDetail.addUserid}}[{{ptDetail.roleName}}]</td>
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment' >{{ptDetail.departmentName}}</td>
                            <td class='col-sm-2'>{{ajDetail.dealWay}}</td>
                            <td class='col-sm-2'>--</td>
                            <td class='col-sm-2'>{{ajDetail.addTime}}</td>
                            <td class='col-sm-2'>--</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 案件详情modal start -->

</div>
<script type="text/javascript">
    var url_prefix = '${pageContext.request.contextPath}';
    var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';
    var issuenum = "";

    // 提交表单
    function submitForm(domId, url) {
        console.log("============QINGQ============");
        var options = {
            dataType: 'json',
            success: dbrwclCallBack,
            data: $('#' + domId).serialize(),
            url: url_prefix + url
        };

        $("#" + domId).ajaxForm(options);
        //$('#' + domId).submit();
        $('#myModal').modal('hide');
    };


    function dbrwclCallBack(responseObject, statusText) {

        if (responseObject.result == 1 || responseObject.success == true || responseObject.success == "true") {
            showDialog('提示', '处理成功！', callBack);
        } else {
            showDialog('提示', '处理失败！', callBack);
        }

        function callBack() {
            $(".modal").modal('hide');
        }

        // refreshDataTableByParams("");
        // appendLiHeaderTag();
    }
</script>
<script src='${pageContext.request.contextPath}/static/js/new.js'></script>
<script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
<script src="${pageContext.request.contextPath}/static/js/gdgx/gdgxcx.js"></script>


<script>

    /**
     * 显示附件的弹窗
     */
    var dialog = new Vue({
        el: '#dialog',
        data() {
            return {
                url: '',
                dialogVisible: false
            }
        }
    });

    /**
     * 设置显示附件的路径
     * @param url
     */
    var showAttachment = (url) => {
        console.log(url);
        dialog.url = '${pageContext.request.contextPath}/issues/showAttachment?path=' + url;
        dialog.dialogVisible = true;
    };

</script>
<script>
    //tab_animate();

    $(function () {
        console.log(issuenum);
        var qhzdId = window.parent.glob;
        var qhzdName = window.parent.globName;
        /*  console.log(qhzdId);
          console.log(qhzdName);*/
        $("#issuenum").val(issuenum);
        for (var index = 0; index < $(".tab-pane").length; index++) {
            var _index = index + 1;
            $('#tab' + _index).attr("tab_num", _index);
            $('#tab' + _index).click(function () {
                var flag = true;
                if ($(".blyj").val() == "") {
                    layerAlert("处理意见不能为空!");
                    // alert("处理意见不能为空!");
                    flag = false;
                }
                if (flag) {
                    submitForm('frm' + $(this).attr("tab_num"), "/jxkh/saveXcjzKh?groupId=" + qhzdId + "&groupName=" + qhzdName);
                }
            })
        }
    })


</script>
<script>
    // tab_animate();
    //监督检查页面
    $(function () {
        console.log(issuenum);
        var qhzdId = window.parent.glob;
        var qhzdName = window.parent.globName;
        /*  console.log(qhzdId);
          console.log(qhzdName);*/
        $("#issuenum").val(issuenum);
        for (var index = 0; index < $(".tab-pane").length; index++) {
            var _index = index + 1;
            $('#tab' + _index).attr("tab_num", _index);
            $('#tab' + _index).click(function () {
                var flag = true;
                if ($.trim($(".blyj").val()) == "") {
                    layerAlert('办理意见不能为空!');
                    flag = false;
                }
                if (flag) {
                    submitForm('frm' + $(this).attr("tab_num"), "/jxkh/saveJdjcKh?groupId=" + qhzdId + "&groupName=" + qhzdName);
                }
            })
        }
    });

</script>

<script type="text/javascript">

    //加载案件归口数据
    $(function () {
        buildSelect("lei", 0);
    });
    $("#lei").change(
        function () {
            if ("0" == $("#lei").val()) {
                $("#xiang").children().remove();
            } else {
                buildSelect("xiang", $("#lei").val());
            }
            $("#issueSubject").children().remove();
        }
    )
    $("#xiang").change(
        function () {
            if ("0" == $("#xiang").val()) {
                $("#issueSubject").children().remove();
            } else {
                buildSelect("issueSubject", $("#xiang").val());
            }
        }
    )

    //案件归口三级联动
    function buildSelect(selectId, parentId) {
        var $select = $("#" + selectId);
        var url = url_prefix + "/issues/getSxgkList";
        var param = {
            parentId: parentId
        };
        $select.children().remove();
        $select.append($('<option value="0">请选择</option>'));
        $.post(url, param, function (list) {
            if (list && list.length > 0) {
                $.each(list, function (i, o) {
                    var option = $('<option value="' + o.id + '">' + o.name + '</option>');
                    option.appendTo($select);
                });
            }
        });
    }

</script>
</body>
</html>
