<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>已办业务</title>

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

</head>
<body style='overflow-x: hidden;'>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-ajcx"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-ajcx-ybyw"/>
<!-- 导航辅助 结束 -->
<div>
    <section>
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
                    <%--<div class="form-group select first">
                        <label class='control-label'
                               style='font-weight: normal; float: left; padding-right: 10px;'>案件来源</label>
                        <div class="" style='float: left;'>
                            <select name="issueSource" id="issueSource" class='form-control'>
                                <code:options codeName="ajly" codeId="${param.ajly}" />
                            </select>
                        </div>
                    </div>--%>
                    <%--<div class="form-group select">
                        <label class='control-label'
                               style='font-weight: normal; float: left; padding-right: 10px;'>处置部门</label>
                        <div class="" style='float: left;'>
                            <select name="deal-dept" id="deal-dept" class='form-control'>
                            </select>
                        </div>
                    </div>--%>
                    <%--<div class="form-group select">
                        <label class='control-label'
                               style='font-weight: normal; float: left; padding-right: 10px;'>事项归口</label>
                        <div class="" style='float: left;'>
                            <select name="issue-belong" id="issue-belong" class='form-control'>
                            </select>
                        </div>
                    </div>--%>
                    <button id="findDate" type="button">查询</button>
                </form>
            </div>
            <div class="content">

                <table id="boot-table-dbrw">
                </table>
            </div>
        </div>
    </section>

    <div class="wrap modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{ajDetail.issueSubject}}</span></div>
                    </h4>
                </div>
                <div class="modal-body" id="dbrw-body">
                    <div class="shang clearfix">
                        <div class="infomation">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:auto ;height: 155px;'>
                                <table class="table">
                                    <tr>
                                        <td>案件来源</td>
                                        <td>{{ajDetail.issueSource}}</td>
                                    </tr>
                                    <tr>
                                        <td>当事人类型</td>
                                        <td>{{ajDetail.requestorType}}</td>
                                    </tr>
                                    <tr>
                                        <td>涉事企业名称</td>
                                        <td>{{ajDetail.refCompanyName}}</td>
                                    </tr>
                                    <tr>
                                        <td>当事人姓名</td>
                                        <td>{{ajDetail.requestorName}}</td>
                                    </tr>
                                    <tr>
                                        <td>当事人电话</td>
                                        <td>{{ajDetail.requestorTel}}</td>
                                    </tr>
                                    <tr>
                                        <td>当事人地址</td>
                                        <td>{{ajDetail.requestorAddress}}</td>
                                    </tr>
                                    <tr>
                                        <td>证件号码</td>
                                        <td>{{ajDetail.requestorIdcard}}</td>
                                    </tr>
                                    <tr>
                                        <td>涉事企业组织机构代码</td>
                                        <td>{{ajDetail.orgId}}</td>
                                    </tr>
                                    <tr>
                                        <td>事发时间</td>
                                        <td>{{ajDetail.issueTime}}</td>
                                    </tr>
                                    <tr>
                                        <td>处置时限</td>
                                        <td>{{ajDetail.timeLimit}}</td>
                                    </tr>
                                    <tr>
                                        <td>权利编码</td>
                                        <td>{{ajDetail.issueType}}</td>
                                    </tr>
                                    <tr>
                                        <td>地图坐标</td>
                                        <td>{{ajDetail.x}},{{ajDetail.y}}</td>
                                    </tr>
                                    <tr>
                                        <td>地址描述</td>
                                        <td>{{ajDetail.addrDesc}}</td>
                                    </tr>
                                    <tr>
                                        <td>事件主题</td>
                                        <td>{{ajDetail.issueSubject}}</td>
                                    </tr>
                                    <tr>
                                        <td>事件描述</td>
                                        <td>{{ajDetail.issueDesc}}</td>
                                    </tr>
                                    <tr>
                                        <td>紧急程度</td>
                                        <td>{{ajDetail.emrgencyLevel_ms}}</td>
                                    </tr>
                                    <tr>
                                        <td>备注</td>
                                        <td>{{ajDetail.comment}}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div class="jindu">
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
                        </div>
                    </div>
                    <div id='dbrw-cl'>

                    </div>
                    <!-- /.modal-content -->
                </div><!-- /.modal -->
                <div class="modal-footer" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <thead>
                        <tr>
                            <th class='col-sm-3'>操作人</th>
                            <th class='col-sm-3'>操作时间</th>
                            <th class='col-sm-6'>办理意见</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for='item in commentDetail' v-if='commentDetail[0]!=null'>
                            <td class='col-sm-3'>{{item.userId}}</td>
                            <td class='col-sm-3'>{{item.time}}
                            </th>
                            <td class='col-sm-6' data-toggle="tooltip" data-placement="top"
                                onMouseOver="$(this).tooltip('show')" :title="item.fullMessage">{{item.message}}
                            </th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src='${pageContext.request.contextPath}/static/js/new.js'></script>

    <script
            src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
    <script type="text/javascript">
        var url_prefix = '${pageContext.request.contextPath}';
        var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';

    </script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
    <script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/issues/ybyw.js"></script>


</div>
</body>
</html>
