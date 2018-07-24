<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>处置时效</title>

    <link href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"rel="stylesheet"/>
    <link rel="stylesheet"href="${pageContext.request.contextPath}/static/css/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet"href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet"href="${pageContext.request.contextPath}/static/css/star-rating.min.css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/elementui/theme-default/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/iconfont.css"/>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/template.css"/>--%>
    <link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/select.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/chart/chart.css"/>

    <style>
        #myDcdbModal textarea.form-control {
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

        /* 督察督办 start */
        .filter{height: 50px;background-color: #eff0f4;line-height: 50px;}
        #container,#container *{box-sizing: content-box !important;}
        .breadcrumb > li + li:before {color: #CCCCCC;content: "> "; padding: 0 5px;}
        .breadcrumb {padding: 2px 15px;margin-bottom:0;list-style:none;background-color: #f5f5f5;border-radius: 4px;}
        .fill {padding-top: 30px;background: #fff;padding-bottom: 30px;}
        .fill .col-sm-3{padding-left: 0;padding-right: 0;}
        .fill .col-sm-6{padding-left: 15px;padding-right: 15px;}
        .fill .form-control{border-radius: 0;height: 28px !important;padding-top:0 ;padding-bottom: 0;line-height: 28px;}
        .fill .form-horizontal .control-label{padding-top: 4px;font-weight: normal;}
        .fill .form-group{margin-bottom: 8px;}
        .fill .form_date2,.fill .form_date{padding-left:15px ;padding-right: 15px;}
        .fill .input-group-addon{height: 28px;}
        /*.fill .hasarea{height: 70px !important;}*/
        .fill .hasarea .form-control{height: 65px !important;position: relative;}
        .col-sm-4{padding-right: 0;}
        .col-sm-7{position:relative}
        input{outline: none;}
        .fill .select{position: absolute;width: 50px;height: 28px;line-height: 28px;text-align: center;color: #fff;background-color: #66afe9;border:none;z-index:20;right:15px;top:0;border:2px solid #66afe9}
        .fill .select:hover,.fill .select:active{border:2px solid #2178f3}
        .has-tanchuang{padding-right: 60px;}
        .propbox{width:600px;height: 400px;background: red;display: none;}
        .show{display: block;}
        .tbody{height: auto !important;}
        .tbody thead tr th{padding: 10px 0;}
        /*.filter .form-group{float: right;}*/
        .pagination{margin:0}

        /* 督察督办 end */

        @media screen and (max-width: 1000px){
            html,body{background: #fff;}
            .top{display: none;}
            .filter{display: none;}
            section{background: #fff;}
            .fill{padding-top: 0;}
            .content{border: none;}
        }

        .upload-demo input {
            margin-left: -1000px;
        }

        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none !important;
            margin: 0;
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
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-czsxkh" />

<!-- 导航辅助 结束 -->
<div class='wrap'>
    <section>
        <div class="content">
            <ul class="funShow clearfix" id="ul-header-tag"></ul>

            <!-- 案件比重 start -->
            <div class="filter ajbz" >
                <form action="">
                    <input type="hidden" name="coefficient" class="form-control" id="coe_fficient"  >
                    <input type="hidden" name="avg12345num" class="form-control" id="avg_12345num">
                    <div class="form-group bs_date">
                        <label for="khyf_ajbz" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf_ajbz" data-link-format="yyyy-mm">
                            <input id="khyf_ajbz" name="khyf" onchange="ajbz.query()" class="form-control" size="16" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <div class="form-group bs_date" >
                        <button type="button" onclick="ajbz.ajbzset()">设置</button>
                    </div>
                    <button type="button" onclick="ajbz.submit()">全部提交</button>
                    <button type="button" onclick="ajbz.query()">查询</button>
                </form>
            </div>


            <div class="modal fade" id="ajbzsetModal" tabindex="-1" role="dialog" aria-labelledby="ajbzsetModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" <%--id="myModalLabel"--%>>
                                <div class="bianhao" style='float: left;'>案件比重参数设置：<span></span></div>

                            </h4>
                        </div>
                        <div class="modal-body" <%--id="dbrw-body"--%>>
                            <form class="form-horizontal" id="ajbzRadioSet">
                                <div class="form-group">
                                    <label for="avg12345num" class="col-sm-2 control-label">12345月平均工单量</label>
                                    <div class="col-sm-10">
                                        <input type="number" name="avg12345num" class="form-control" id="avg12345num" value="" placeholder="请输入12345月平均工单量">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="coefficient" class="col-sm-2 control-label">统一系数</label>
                                    <div class="col-sm-10">
                                        <input type="number" name="coefficient" class="form-control" id="coefficient" value="" placeholder="请输入统一系数">
                                    </div>
                                </div>
                                <div class="modal-footer" >
                                    <div class="text-center">
                                        <button type="button" onclick="ajbz.saveRadioSet()" class="btn btn-primary">保存</button>
                                        <button type="reset"  class="btn btn-default">重置</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 案件比重 end -->

            <!-- 督察督办 start -->
            <div class="filter dcdbTag" style="display: none" >
                <form action="">
                    <div class="form-group bs_date">
                        <label for="dcdb_khyf" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf" data-link-format="yyyy-mm">
                            <input id="dcdb_khyf" class="form-control" size="16" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>

                        <label for="dcdb_jgmc" class=" control-label" style="margin-left:20px;">机构名称</label>
                        <div class="input-group">
                            <input id="dcdb_jgmc" class="form-control" sstyle="width:160px;" type="text" >
                        </div>
                    </div>
                    <div class="form-group" >
                        <button type="button" onclick="dcdb_obj.delDcdbInfo()">删除</button>
                        <button type="button" onclick="dcdb_obj.showDcDbDetails('update')">修改</button>
                        <button type="button" onclick="dcdb_obj.showDcDbDetails('add')">添加</button>
                        <button type="button" onclick="dcdb_obj.refreshDcDbTableByParams()">查询</button>
                    </div>
                </form>
            </div>

            <div class="wrap modal fade" id="myDcdbModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class=" modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">
                                <div class="bianhao" style='float: left;'><blod>督察督办</blod></div>
                            </h4>
                        </div>
                        <div class="modal-body" id="dbrw-body">
                            <div class="shang clearfix">
                                <div class="fill clearfix" >
                                    <div class="col-sm-10">
                                        <form  class='form-horizontal clearfix' id="addPostForm_dcdb" method="post" enctype="text/plain">
                                            <input type="hidden" name="id" />
                                            <input type="hidden" id="dcdb_qhzdName" name="qhzdName" /><%--机构名称--%>
                                            <input type="hidden" id="dcdb_typeName" name="typeName" /><%--违规类型--%>
                                            <input type="hidden" id="dcdb_sourceName" name="sourceName" /><%--信息来源--%>
                                            <input type="hidden" id="dcdbNum" name="dcdbNum" /><%--督察督办编号--%>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>案件编号:</label>
                                                <div class="col-sm-7">
                                                    <input id="issueType" name="issuenum" type="text" placeholder="输入案件编号" autocomplete="off" class='form-control has-tanchuang'/>
                                                    <button class="select" type='button' data-toggle="modal" data-target="#power-code-modal">查询</button>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="khyf_panel" class="col-sm-4 control-label"><span class="required">*</span>考核月份:</label>
                                                <div class="col-sm-7 input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf_panel" data-link-format="yyyy-mm">
                                                    <input id ="khyf_panel" name="khyf" class="form-control" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>信息来源:</label>
                                                <div class="col-sm-7">
                                                    <select id="issueSource" name="sourceId" class='form-control' required>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>涉嫌机构:</label>
                                                <div class="col-sm-7">
                                                    <select id="qhzdGroup" name="qhzdId" class='form-control' required>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group hasarea">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>情况描述:</label>
                                                <div class="col-sm-7">
                                                    <textarea id="descript" name="descript" rows="4" cols="12" class="form-control" required="true"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>涉嫌违规类型:</label>
                                                <div class="col-sm-7" style="margin-top:8px;">
                                                    <select id="violationtype" name="typeId" class='form-control' required>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>扣分:</label>
                                                <div class="col-sm-7" style="margin-top:8px;">
                                                    <select id="deductScore" name="score" class='form-control' required>
                                                        <option value="">请选择</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>案件信息:</label>
                                                <div class="col-sm-7" style="margin-top:8px;">
                                                    <textarea id="info" name="info" rows="4" cols="12" class="form-control" required="true"></textarea>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                            <!-- /.modal-content -->
                        </div><!-- /.modal -->
                        <div class="modal-footer" style='max-height:150px;overflow-y:auto'>
                            <div class="filter">
                                <button type="button" onclick="dcdb_obj.saveDcdbInfo()" >提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 督察督办 end -->


            <!-- 宣传报道 start -->
            <div class="filter xcbd" style="display: none">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="month" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" id="month" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <button type="button" onclick="xcbd_obj.delXcbdInfo()">删除</button>
                    <button type="button" onclick="xcbd_obj.showXcbdDetails('update')">修改</button>
                    <button type="button" onclick="xcbd_obj.showXcbdDetails('add')">新增</button>
                    <button type="button" onclick="xcbd_obj.refreshTableByParams()">查询</button>
                </form>
            </div>

            <div class="wrap modal fade" id="myXcbdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class=" modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                <div class="bianhao" style='float: left;'><blod>宣传报道</blod></div>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="shang clearfix">
                                <div class="fill clearfix" >
                                    <div class="col-sm-10">
                                        <form  class='form-horizontal clearfix' id="addPostForm_xcbd" method="post" enctype="text/plain">
                                            <input type="hidden" name="id" />
                                            <input type="hidden" id="qhzdMc" name="qhzdMc" />
                                            <div class="row" style="margin-bottom:8px;">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>机构名称:</label>
                                                <div class="col-sm-10">
                                                    <select id="qhzdDm" name="qhzdDm" class='form-control' required>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group ">
                                                    <label for="khyf_xcbd" class="col-sm-2 control-label"><span class="required">*</span>考核月份:</label>
                                                    <div class="col-sm-10 input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="khyf_panel" data-link-format="yyyy-mm">
                                                        <input id="khyf_xcbd" name="khyf" class="form-control" type="text" value="" readonly=""> <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <span class='col-sm-12 control-label' style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">国家级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="countrywlmt" type="number" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="countryfwlmt" type="number" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label' style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">省级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="provincewlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="provincefwlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label' style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">市级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="citywlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="cityfwlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label' style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">区级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="districtwlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="districtfwlmt" type="text" autocomplete="off" class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div><!-- /.modal -->
                        <div class="modal-footer" style='max-height:150px;overflow-y:auto'>
                            <div class="filter">
                                <button type="button" onclick="xcbd_obj.saveXcbdInfo()" >提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 宣传报道 end -->



            <table id="boot-table-dbrw">
            </table>

        </div>
    </section>
</div>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
    <script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jxkh/czsxkh.js"></script>
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

        var url_prefix = '${pageContext.request.contextPath}';
        var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';
    </script>

</body>
</html>
