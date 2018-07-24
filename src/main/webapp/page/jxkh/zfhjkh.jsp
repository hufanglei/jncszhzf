<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>执法痕迹</title>

    <link
            href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"
            rel="stylesheet"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/star-rating.min.css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/elementui/theme-default/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/iconfont.css"/>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/template.css"/>--%>
    <link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/select.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css"/>


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


        .webuploader-pick {
            position: relative;
            display: inline-block;
            cursor: pointer;
            background: #00b7ee;
            padding: 4px 8px;
            color: #fff;
            text-align: center;
            border-radius: 3px;
            overflow: hidden;
        }

        .upload-demo input {
            margin-left: -1000px;
        }
    </style>
</head>
<body style='overflow-x: hidden;'>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-jxkh" />
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-zfhjkh" />

<!-- 导航辅助 结束 -->
<div class='wrap'>
    <section>
        <div class="content">
            <ul class="funShow clearfix" id="ul-header-tag">
            </ul>

            <div class="filter ajbz">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="khyf" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                        <input type="hidden" id="khyf" name="khyf" value=""><br>
                    </div>
                    <div class="form-group bs_date" >
                        <button type="button" onclick="refreshDataTableByParams()">设置</button>
                    </div>
                    <%--<div class="form-group bs_date">--%>
                    <%--<label class="control-label" style="font-weight: normal; float: left; padding-right: 10px;">SHZ</label>--%>
                    <%--<div class="input-group ">--%>
                    <%--<input type="text" class="form-control" size="16" id="qhzdName" name="qhzdName">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <button type="button" onclick="refreshDataTableByParams()">提交</button>
                    <button type="button" onclick="refreshDataTableByParams()">查询</button>
                </form>
            </div>

            <div class="filter xcbd" style="display: none">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="month" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" id="month" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                        <%--<input type="hidden" id="month" name="month" value=""><br>--%>
                    </div>
                    <button type="button" onclick="javascript:;">删除</button>
                    <button type="button" onclick="javascript:;">新增</button>
                    <button type="button" onclick="zfhj.buildXcbdTable()">查询</button>
                </form>
            </div>

            <table id="boot-table-dbrw">
            </table>
        </div>
    </section>



    <script
            src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
    <script>
        $('.form_date').datetimepicker({
            format: 'yyyy-mm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN'
        });
    </script>
    <script type="text/javascript">

        var url_prefix = '${pageContext.request.contextPath}';
        var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';


        // 提交表单
        function submitForm(domId, url) {
            var options = {
                dataType: 'json',
                success: dbrwclCallBack,
                url: url_prefix + url
            };

            $("#" + domId).ajaxForm(options);
            $('#' + domId).submit();
            $('#myModal').modal('hide');
        };


        function dbrwclCallBack(responseObject, statusText) {
            if (responseObject.result == 1 || responseObject.success == true) {
                showDialog('提示', '处理成功！', callBack);
            } else {
                showDialog('提示', '处理失败！', callBack);
            }

            function callBack() {
                $(".modal").modal('hide');
            }

            refreshDataTableByParams("");
            appendLiHeaderTag();
        }
    </script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
    <script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>

    <%--<script src="${pageContext.request.contextPath}/static/js/jxkh/czslkh.js"></script>--%>


</body>
</html>
