<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>日志查询</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/static/css/bootstrap.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/template.css"/>
    <link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/select.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css"/>

</head>
<body style='overflow-x: hidden;'>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-log"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-log"/>
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
                    <button type="button" onclick="refreshDataTableByParams()">查询</button>
                </form>
            </div>
            <div class="content">

                <table id="boot-table-dbrw">
                </table>
            </div>
        </div>
    </section>

    <div class="wrap modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body" id="dbrw-body">
                    <div class="shang clearfix">
                        <%--<div class="infomation">--%>
                        <div class="specific" style='overflow-y:auto ;height: 355px;'>
                            <table class="table">
                                <tr>
                                    <td>机构名称</td>
                                    <td>{{qwgDetail.qhzdName}}</td>
                                </tr>
                                <tr>
                                    <td>自行处理</td>
                                    <td>{{qwgDetail.zxcl}}</td>
                                </tr>
                                <tr>
                                    <td>案件比重</td>
                                    <td>{{qwgDetail.ajbz}}</td>
                                </tr>
                                <tr>
                                    <td>按时处理</td>
                                    <td>{{qwgDetail.ascl}}</td>
                                </tr>
                                <tr>
                                    <td>快速处理</td>
                                    <td>{{qwgDetail.kscl}}</td>
                                </tr>
                                <tr>
                                    <td>考核月份</td>
                                    <td>{{qwgDetail.khyf}}</td>
                                </tr>
                                <tr>
                                    <td>考核时间</td>
                                    <td>{{qwgDetail.khsj}}</td>
                                </tr>
                            </table>
                        </div>
                        <%--</div>--%>
                        <!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
            </div>
        </div>

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

        <script src="${pageContext.request.contextPath}/static/js/logs/log.js"></script>


    </div>
</body>
</html>
