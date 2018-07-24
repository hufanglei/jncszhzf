<%--
  Created by IntelliJ IDEA.
  User: guoguo
  Date: 2018/3/29
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>督办案件归档查询</title>

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
        #myModal textarea {
            height: auto !important
        }

        @media (min-width: 768px) {
            .form-horizontal .control-label {
                text-align: left
            }
        }

        .upload-demo input {
            margin-left: -1000px;
        }

        /* .filter,.filter form {
             height: 90px;
         }*/

        .cols-padding select {
            width: 150px;
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
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-dbgdcx"/>
<!-- 导航辅助 结束 -->
<div>
    <section>
        <div class="col-sm-2" style="padding:0px;">
            <div id="treeview" class=""></div>
        </div>
        <div class="col-sm-10" style="padding-right:0px;">
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
                            <label for="caseType" class=" control-label" style="width:90px;">案件来源</label>
                            <select id="caseType" name="caseType" class='form-control'>
                                <option value="">—— 全部 ——</option>
                                <option value="反复投诉">反复投诉</option>
                                <option value="巡查机制">巡查机制</option>
                                <option value="监督检查">监督检查</option>
                                <option value="督办问责">督办问责</option>
                                <option value="工作协调">工作协调</option>
                            </select>
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
        <div class=" modal-dialog modal-lg" style="width:75%;">
            <div class="modal-content" style="width:100%;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{dbajDetail.sourceName}}</span></div>
                    </h4>
                </div>
                <div class="modal-body" id="dbrw-body">
                    <div class="shang clearfix">
                        <div class="infomation" style="width: 100%">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:hidden'>
                                <table class='table text-center'>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">案件编号</th>
                                    <th style="background-color:#fff">{{dbajDetail.dcdbnum}}</th>
                                    <th style="background-color:#e9f3f4">信息来源</th>
                                    <th style="background-color:#fff">{{dbajDetail.sourceId}}</th>
                                    <th style="background-color:#e9f3f4">涉嫌机构</th>
                                    <th style="background-color:#fff">{{dbajDetail.qhzdName}}</th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">情况描述</th>
                                    <th colspan="5" style="background-color:#fff">{{dbajDetail.descript}}</th>

                                    </tbody>

                                    </tbody>
                                </table>
                                </table>
                            </div>
                        </div>
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
                        <tr v-for='items in commentDetail' v-if='commentDetail[0]!=null'>
                            <td class='col-sm-2'>{{items.userName}}</td>
                            <td class='col-sm-2'>{{items.department}}</td>
                            <td class='col-sm-2'>{{items.handleWay}}</td>
                            <td class='col-sm-2' data-toggle="tooltip" data-placement="top"
                                onMouseOver="$(this).tooltip('show')" :title="item.fullMessage">{{items.comment}}
                            </td>
                            <td class='col-sm-2'>{{items.endTime}}</td>
                            <td class='col-sm-2'>{{items.consumTime}}</td>
                        </tr>
                        <tr >
                            <td class='col-sm-2' v-for='detail in department'>{{dbajDetail.addUserid}}[{{detail.roleName}}]</td>
                            <td class='col-sm-2' v-for='detail in department' >{{detail.departmentName}}</td>
                            <td class='col-sm-2'>{{dbajDetail.info}}</td>
                            <td class='col-sm-2'>--</td>
                            <td class='col-sm-2'>{{dbajDetail.startTime}}</td>
                            <td class='col-sm-2'>--</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 案件详情modal start -->
    <div class="wrap2 modal fade" id="ptmyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：</span>
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
                                </table>
                            </div>
                        </div>
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
                        <tr v-for='item in ptcommentDetail' v-if='ptcommentDetail[0]!=null'>
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
                            <td class='col-sm-2'>{{ajDetail.isOpen}}</td>
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
</div>
<script src='${pageContext.request.contextPath}/static/js/new.js'></script>
<script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-treeview.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
<script src="${pageContext.request.contextPath}/static/js/gdgx/dbgdcx.js"></script>


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
</body>
</html>
