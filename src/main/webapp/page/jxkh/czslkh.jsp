<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>手动考核</title>

    <link href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/star-rating.min.css"/>

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

        @media screen and (max-width: 1000px) {
            html, body {
                background: #fff;
            }

            .top {
                display: none;
            }

            .filter {
                display: none;
            }

            section {
                background: #fff;
            }

            .fill {
                padding-top: 0;
            }

            .content {
                border: none;
            }
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
<input id="nav-flag-top" type="hidden" value="nav-flag-top-dbrw"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-czslkh"/>

<!-- 导航辅助 结束 -->
<div class='wrap '>
    <section>
        <div class="content">
            <ul class="funShow clearfix" id="ul-header-tag"></ul>

            <!-- 案件比重 start -->
            <div class="filter ajbz">
                <form action="">
                    <%--<input type="hidden" name="coefficient" class="form-control" id="coe_fficient"  >--%>
                    <input type="hidden" name="avg12345num" class="form-control" id="avg_12345num">
                    <div class="form-group bs_date">
                        <label class=" control-label">考核月份</label>
                        <div   class="input-group date form_date" data-date="" data-date-format="yyyy-mm" data-link-field="dtp_input2" data-link-format="yyyy-mm">
                            <input class="form-control" size="10" type="text" id="khyf_ajbz">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                        <div class="form-group bs_date">
                    <%--   <button type="button" onclick="ajbz.submit()">全部提交</button>--%>
                    <button type="button" onclick="ajbz.query(1)">查询</button>
                        </div>
                            <button type="button" onclick="ajbz.ajbzset()">设置</button>

                </form>
            </div>

            <div class="modal fade" id="ajbzsetModal" tabindex="-1" role="dialog" aria-labelledby="ajbzsetModalLabel"
                 aria-hidden="true">
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
                                    <label for="avg12345num1" class="col-sm-3 control-label">江宁开发区12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num1" class="form-control" id="avg12345num1" value="" placeholder="江宁开发区的总自处置工单量">
                                        <input type="hidden" name="jd1" id="jd1" value="jnkfqp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num2" class="col-sm-3 control-label">东山街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num2" class="form-control" id="avg12345num2" value="" placeholder="东山街道中心的总自处置工单量">
                                        <input type="hidden" name="jd2" id="jd2" value="dsjdp" >
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="avg12345num3" class="col-sm-3 control-label">秣陵街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num3" class="form-control" id="avg12345num3" value="" placeholder="秣陵街道中心的总自处置工单量">
                                        <input type="hidden" name="jd3" id="jd3" value="mljdzxp" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num4" class="col-sm-3 control-label">汤山街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num4" class="form-control" id="avg12345num4" value="" placeholder="汤山街道中心的总自处置工单量">
                                        <input type="hidden" name="jd4" id="jd4" value="tsjdzxp" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num5" class="col-sm-3 control-label">淳化街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num5" class="form-control" id="avg12345num5" value="" placeholder="淳化街道中心的总自处置工单量">
                                        <input type="hidden" name="jd5" id="jd5" value="chjdzxp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num6" class="col-sm-3 control-label">禄口街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num6" class="form-control" id="avg12345num6" value="" placeholder="禄口街道中心的总自处置工单量">
                                        <input type="hidden" name="jd6" id="jd6" value="lkjdzxp" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num7" class="col-sm-3 control-label">江宁街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num7" class="form-control" id="avg12345num7" value="" placeholder="江宁街道中心的总自处置工单量">
                                        <input type="hidden" name="jd7" id="jd7" value="jnjdzxp" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num8" class="col-sm-3 control-label">谷里街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num8" class="form-control" id="avg12345num8" value="" placeholder="谷里街道中心的总自处置工单量">
                                        <input type="hidden" name="jd8" id="jd8" value="gljdzxp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num9" class="col-sm-3 control-label">湖熟街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num9" class="form-control" id="avg12345num9" value="" placeholder="湖熟街道中心的总自处置工单量">
                                        <input type="hidden" name="jd9" id="jd9" value="hsjdzxp" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num10" class="col-sm-3 control-label">麒麟街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num10" class="form-control" id="avg12345num10" value="" placeholder="麒麟街道中心的总自处置工单量">
                                        <input type="hidden" name="jd10" id="jd10" value="qljdzxp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num11" class="col-sm-3 control-label">横溪街道中心12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num11" class="form-control" id="avg12345num11" value="" placeholder="横溪街道中心的总自处置工单量">
                                        <input type="hidden" name="jd11" id="jd11" value="hxjdzhp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num12" class="col-sm-3 control-label">江宁高新园12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num12" class="form-control" id="avg12345num12" value="" placeholder="江宁高新园的总自处置工单量">
                                        <input type="hidden" name="jd12" id="jd12" value="jngxyp">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="avg12345num13" class="col-sm-3 control-label">滨江开发区12345网站总工单量</label>
                                    <div class="col-sm-9">
                                        <input type="number" name="avg12345num13" class="form-control" id="avg12345num13" value="" placeholder="滨江开发区的总自处置工单量">
                                        <input type="hidden" name="jd13" id="jd13" value="bjkfqp" >
                                    </div>
                                </div>

                                <%-- <div class="form-group">
                                     <label for="coefficient" class="col-sm-2 control-label">统一系数</label>
                                     <div class="col-sm-9">
                                         <input type="number" name="coefficient" class="form-control" id="coefficient" value="" placeholder="请输入统一系数">
                                     </div>
                                 </div>--%>
                                <div class="modal-footer">
                                    <div class="text-center">
                                        <button type="button" onclick="ajbz.saveRadioSet()" class="btn btn-primary">保存
                                        </button>
                                        <button type="reset" class="btn btn-default">重置</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 案件比重 end -->

            <!-- 督察督办 start -->
            <div class="filter dcdbTag" style="display: none">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="dcdb_khyf" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm"
                             data-link-field="khyf" data-link-format="yyyy-mm">
                            <input id="dcdb_khyf" class="form-control" size="16" type="text" value="" readonly=""> <span
                                class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>

                        <label for="dcdb_jgmc" class=" control-label" style="margin-left:20px;">机构名称</label>
                        <div class="input-group">
                            <input id="dcdb_jgmc" class="form-control" sstyle="width:160px;" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <%--<button type="button" onclick="dcdb_obj.delDcdbInfo()">删除</button>--%>
                        <%-- <button type="button" onclick="dcdb_obj.showDcDbDetails('update')">修改</button>
                         <button type="button" onclick="dcdb_obj.showDcDbDetails('add')">添加</button>--%>
                        <button type="button" onclick="dcdb_obj.refreshDcDbTableByParams(1)">查询</button>
                    </div>
                </form>
            </div>

            <div class="wrap modal fade" id="myDcdbModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class=" modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">
                                <div class="bianhao" style='float: left;'>
                                    <blod>督察督办</blod>
                                </div>
                            </h4>
                        </div>
                        <div class="modal-body" id="dbrw-body">
                            <div class="shang clearfix">
                                <div class="fill clearfix">
                                    <div class="col-sm-10">
                                        <form class='form-horizontal clearfix' id="addPostForm_dcdb" method="post"
                                              enctype="text/plain">
                                            <input type="hidden" name="id"/>
                                            <input type="hidden" id="dcdb_qhzdName" name="qhzdName"/><%--机构名称--%>
                                            <input type="hidden" id="dcdb_typeName" name="typeName"/><%--违规类型--%>
                                            <input type="hidden" id="dcdb_sourceName" name="sourceName"/><%--信息来源--%>
                                            <input type="hidden" id="dcdbNum" name="dcdbNum"/><%--督察督办编号--%>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>案件编号:</label>
                                                <div class="col-sm-7">
                                                    <input id="issueType" name="issuenum" type="text"
                                                           placeholder="输入案件编号" autocomplete="off"
                                                           class='form-control has-tanchuang'/>
                                                    <button class="select" type='button' data-toggle="modal"
                                                            data-target="#power-code-modal">查询
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="khyf_panel" class="col-sm-4 control-label"><span
                                                        class="required">*</span>考核月份:</label>
                                                <div class="col-sm-7 input-group date form_date" data-date=""
                                                     data-date-format="yyyy-mm" data-link-field="khyf_panel"
                                                     data-link-format="yyyy-mm">
                                                    <input id="khyf_panel" name="khyf" class="form-control" type="text"
                                                           value="" readonly=""> <span class="input-group-addon"><span
                                                        class="glyphicon glyphicon-calendar"></span></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>信息来源:</label>
                                                <div class="col-sm-7">
                                                    <select id="issueSource" name="sourceId" class='form-control'
                                                            required>
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
                                                    <textarea id="descript" name="descript" rows="4" cols="12"
                                                              class="form-control" required="true"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class='col-sm-4 control-label'><span class="required">*</span>涉嫌违规类型:</label>
                                                <div class="col-sm-7" style="margin-top:8px;">
                                                    <select id="violationtype" name="typeId" class='form-control'
                                                            required>
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
                                                    <textarea id="info" name="info" rows="4" cols="12"
                                                              class="form-control" required="true"></textarea>
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
                                <%--<button type="button" onclick="dcdb_obj.saveDcdbInfo()" >提交</button>--%>
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
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm"
                             data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" id="month" type="text" value="" readonly=""> <span
                                class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <%-- <button type="button" onclick="xcbd_obj.delXcbdInfo()">删除</button>
                     <button type="button" onclick="xcbd_obj.showXcbdDetails('update')">修改</button>--%>
                    <button type="button" onclick="xcbd_obj.showXcbdDetails('add')">新增</button>
                    <button type="button" onclick="xcbd_obj.refreshTableByParams(1)">查询</button>
                </form>
            </div>

            <div class="wrap modal fade" id="myXcbdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class=" modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                <div class="bianhao" style='float: left;'>
                                    <blod>宣传报道</blod>
                                </div>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="shang clearfix">
                                <div class="fill clearfix">
                                    <div class="col-sm-10">
                                        <form class='form-horizontal clearfix' id="addPostForm_xcbd" method="post"
                                              enctype="text/plain">
                                            <input type="hidden" name="id"/>
                                            <input type="hidden" id="qhzdMc" name="qhzdMc"/>
                                            <div class="row" style="margin-bottom:8px;">
                                                <div class="form-group">
                                                    <label for="khyf_xcbd" class="col-sm-2 control-label"><span
                                                            class="required">*</span>考核月份:</label>
                                                    <div class="col-sm-10 input-group date form_date" data-date=""
                                                         data-date-format="yyyy-mm" data-link-field="khyf_panel"
                                                         data-link-format="yyyy-mm">
                                                        <input id="khyf_xcbd" name="khyf" class="form-control"
                                                               type="text" value="" readonly=""> <span
                                                            class="input-group-addon"><span
                                                            class="glyphicon glyphicon-calendar"></span></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>机构名称:</label>
                                                <div class="col-sm-10">
                                                    <select id="qhzdDm" name="qhzdDm" class='form-control'
                                                            onBlur="xcbd_obj.cbddCheck()" required>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <span class='col-sm-12 control-label'
                                                      style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">国家级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="countrywlmt" type="number" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="countryfwlmt" type="number" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label'
                                                      style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">省级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="provincewlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="provincefwlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label'
                                                      style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">市级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="citywlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="cityfwlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>
                                            </div>
                                            <div class="row text-center ">
                                                <span class='col-sm-12 control-label'
                                                      style="text-align:center;font-weight: bold;font-size: 16px;margin:5px;">区级</span>
                                            </div>
                                            <div class="row">
                                                <label class='col-sm-2 control-label'><span class="required">*</span>网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="districtwlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
                                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                                </div>

                                                <label class='col-sm-2 control-label'><span class="required">*</span>非网络媒体</label>
                                                <div class="col-sm-4">
                                                    <input name="districtfwlmt" type="text" autocomplete="off"
                                                           class='form-control has-tanchuang'
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
                                <button type="button" onclick="xcbd_obj.saveXcbdInfo()">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 宣传报道 end -->

            <!-- 巡查机制 start -->
            <div class="filter xcjz" style="display: none">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="month_xcjz" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm"
                             data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" id="month_xcjz" type="text" value="" readonly="">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <button type="button" onclick="xcjz_obj.delXcjzInfo()">删除</button>
                    <%--<button type="button" onclick="xcbd_obj.showXcjzDetails('update')">修改</button>
                    <button type="button" onclick="xcbd_obj.showXcjzDetails('add')">新增</button>--%>
                    <button type="button" onclick="xcjz_obj.refreshTableByParams(1)">查询</button>
                </form>
            </div>

            <!-- 巡查机制 end -->

            <!-- 监督检查 start -->
            <div class="filter jdjc" style="display: none">
                <form action="">
                    <div class="form-group bs_date">
                        <label for="month_jdjc" class=" control-label">考核月份</label>
                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm"
                             data-link-field="khyf" data-link-format="yyyy-mm">
                            <input class="form-control" size="16" id="month_jdjc" type="text" value="" readonly="">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                    </div>
                    <button type="button" onclick="jdjc_obj.delJdjcInfo()">删除</button>
                    <%--<button type="button" onclick="xcbd_obj.showXcjzDetails('update')">修改</button>
                    <button type="button" onclick="xcbd_obj.showXcjzDetails('add')">新增</button>--%>
                    <button type="button" onclick="jdjc_obj.refreshTableByParams(1)">查询</button>
                </form>
            </div>

            <!-- 监督检查 end -->

            <div class="content">
                <table id="boot-table-dbrw">
                </table>
            </div>
        </div>
    </section>

    <!-- 督察督办弹窗1 start -->
    <div class="modal fade dbwrap" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel0"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel0">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{ajDetail.descript}}</span></div>
                        <div class='changePage' style='float: right;'>
                            <a href="##"></a>
                            <a href="##"></a>
                        </div>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="shang clearfix">
                        <div class="infomation" style="width:100%">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:auto ;height: 105px;'>
                                <table class="table sebox">
                                    <tr>
                                        <td>信息来源</td>
                                        <td>{{ajDetail.sourceName}}</td>
                                        <td>涉嫌机构</td>
                                        <td>{{ajDetail.qhzdName}}</td>
                                    </tr>
                                    <tr>
                                        <td>涉嫌违规类型</td>
                                        <td>{{ajDetail.typeName}}</td>
                                        <td>情况描述</td>
                                        <td>{{ajDetail.descript}}</td>
                                    </tr>
                                    <tr>
                                        <td>扣分</td>
                                        <td>{{ajDetail.score}}</td>
                                        <td>案件信息</td>
                                        <td>{{ajDetail.info}}</td>
                                    </tr>
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
                    <div id='dbrw-cl' style="margin-top:-10px;">

                    </div>
                    <!-- /.modal-content -->
                </div><!-- /.modal -->
                <div id="opeInfo" class="modal-footer" style='max-height:150px;overflow-y:auto'>
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
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 督察督办弹窗1 end -->
    <!-- 督察督办弹窗2 start -->
    <div class="wrap modal fade" id="myDcdbModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel2">
                        <div class="bianhao" style='float: left;'>详情信息：</div>
                    </h4>
                </div>
                <div id="dbrw-body2" class="modal-body" style="padding-top: 0px;">
                    <div class="shang clearfix">
                        <div class="specific" style='overflow-y:auto ;height: 355px;'>
                            <table class="table">
                                <tr>
                                    <td>督查督办编号</td>
                                    <td>{{qwgDetail.dcdbnum}}</td>
                                </tr>
                                <tr>
                                    <td>机构名称</td>
                                    <td>{{qwgDetail.qhzdName}}</td>
                                </tr>
                                <tr>
                                    <td>案件编号</td>
                                    <td>{{qwgDetail.issuenum}}</td>
                                </tr>
                                <tr>
                                    <td>案件状态</td>
                                    <td>{{qwgDetail.statusName}}</td>
                                </tr>
                                <tr>
                                    <td>违规类型名称</td>
                                    <td>{{qwgDetail.typeName}}</td>
                                </tr>
                                <tr>
                                    <td>扣分</td>
                                    <td>{{qwgDetail.score}}</td>
                                </tr>
                                <tr>
                                    <td>日期</td>
                                    <td>{{qwgDetail.khyf}}</td>
                                </tr>
                                <tr>
                                    <td>情况描述</td>
                                    <td>{{qwgDetail.descript}}</td>
                                </tr>
                                <tr>
                                    <td>案件信息</td>
                                    <td>{{qwgDetail.info}}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 督察督办弹窗2 end -->
    <!-- 巡查机制3 start -->
    <div class="modal fade dbwrap2" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel0"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel0">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{ajDetail.descript}}</span></div>
                        <div class='changePage' style='float: right;'>
                            <a href="##"></a>
                            <a href="##"></a>
                        </div>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="shang clearfix">
                        <div class="infomation" style="width: 100%">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:auto ;height: 155px;'>
                                <table class="table">
                                    <tr>
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
                                        <td style="background: #fff"></td>
                                        <td></td>
                                    </tr>
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
                    <div id='dbrw-cl3'>

                    </div>
                    <!-- /.modal-content -->
                </div><!-- /.modal -->
                <div id="opeInfo" class="modal-footer" style='max-height:150px;overflow-y:auto'>
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
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 巡查机制 end -->
</div>


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
        // $('#' + domId).submit();
        //dcdb_obj.refreshDcDbTableByParams(dcdbPageNum);
        //$('#myModal').modal('hide');
        $('#myModal3').modal('hide');

    };


    function dbrwclCallBack(responseObject, statusText) {

        if (responseObject.result == 1 || responseObject.success == true || responseObject.success == "true") {
            layerAlert('处理成功！')
        } else {
            layerAlert('处理失败！')
        }
        $(".modal").modal('hide');
        dcdb_obj.refreshDcDbTableByParams(dcdbPageNum);
        //ajbz.refreshDataTableByParams(1);
        // appendLiHeaderTag();
    }

</script>
<script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>

<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jxkh/czslkh.js"></script>


</body>
</html>
