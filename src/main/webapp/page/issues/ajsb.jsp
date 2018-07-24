<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <title>案件上报</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/anjianshangbao.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/map/map.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/elementui/theme-default/index.css">


    <style type="text/css">
        .fill_error {
            color: red;
            border-color: red
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
            /*margin-left: -1000px;*/
            display: none;
        }

        .required {
            color: red;
        }
        .cols-padding{
            padding:0px;
        }
    </style>
</head>
<body>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-ajsb"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-ajsb"/>
<!-- 导航辅助 结束 -->
<main>
    <section>
        <div class="content">
            <div class="filter">
                <ol class="breadcrumb">
                    <li><a href="#">首页</a></li>
                    <li class="active">案件上报</li>
                </ol>
            </div>
            <div class="fill clearfix">
                <div class="col-sm-10">
                    <form class='form-horizontal clearfix' id="postForm" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>案件归口:</label>
                            <div class="col-sm-7" style='height: 100%;'>
                                <div class="col-sm-4 cols-padding" >
                                    <select id='lei' name="name" class='form-control' required></select>
                                </div>
                                <div class="col-sm-4 cols-padding">
                                    <select id='xiang' name="name" class='form-control' required></select>
                                </div>
                                <div class="col-sm-4 cols-padding" >
                                    <select id='issueSubject' name="issueSubject" class='form-control' required></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>案件来源:</label>
                            <div class="col-sm-7 ajly">
                                <select id="issueSource" name="issueSource" class='form-control' required>

                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>当事人类型:</label>
                            <div class="col-sm-7">
                                <select id="dsrlx" name="requestorType"
                                        class='form-control'>
                                    <code:options codeName="dsrlx" codeId="${param.dsrlx}"/>
                                </select>
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label class='col-sm-4 control-label'>涉事企业名称:</label>
                            <div class="col-sm-7">
                                <input id="refCompanyName" name="refCompanyName" type="text" placeholder="输入涉事企业名称" class='form-control has-tanchuang'/>
                                <button class="select" type='button' data-toggle="modal" data-target="myModal2">查询</button>
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'>当事人姓名:</label>
                            <div class="col-sm-7">
                                <input id="requestorName" name="requestorName" type="text" placeholder="输入当事人姓名"
                                       class='form-control'/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'>当事人电话:</label>
                            <div class="col-sm-7">
                                <input id="requestorTel" name="requestorTel" type="text" placeholder="输入当事人电话"
                                       class='form-control'/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'>当事人地址:</label>
                            <div class="col-sm-7">
                                <input id="requestorAddress" name="requestorAddress" type="text" placeholder="输入当事人地址"
                                       class='form-control'/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'>证件号码:</label>
                            <div class="col-sm-7">
                                <input id="requestorIdcard" name="requestorIdcard" type="text" placeholder="输入证件号码"
                                       class='form-control' required/>
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label class='col-sm-4 control-label'>涉事企业组织机构代码:</label>
                            <div class="col-sm-7">
                                <input id="orgId" name="orgId" type="text" placeholder="输入组织机构代码" class='form-control'/>
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label for="issueTime" class=" control-label col-sm-4"><span class="required">*</span>案发时间:</label>
                            <div class="input-group date form_date2 col-sm-7" data-date=""
                                 data-date-format="yyyy-MM-dd hh:ii:ss" data-link-field="dtp_input2"
                                 data-link-format="yyyy-mm-dd">
                                <input class="form-control" size="16" type="text" value="" id="issueTime"
                                       name="issueTime" readonly>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>处置时限:</label>
                            <div class="col-sm-7">
                                <select id="timeLimit" name="timeLimit" class='form-control'>
                                    <code:options codeName="czsx" codeId="${param.czsx}" />
                                </select>
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>权力编码:</label>
                            <div class="col-sm-7" data-toggle="modal" data-target="#power-code-modal">
                                <input id="issueType" name="issueType" type="text"
                                       class='form-control has-tanchuang' readonly/>
                                <button class="select" type='button'>查询
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>地图坐标:</label>
                            <div class='col-sm-7'>
                                (&nbsp;<label id="xLabel">--.----</label>&nbsp;,&nbsp;<label id="yLabel">--.----</label>&nbsp;)
                                <input id="x" type="hidden" name='x' value=""/>
                                <input id="y" type="hidden" name='y' value=""/>
                                <button class="select view-map-btn" type='button' onclick="showMap();">查看地图</button>
                            </div>
                            <!-- 									<div class="col-sm-2">
                                                                    <input id="x" name="x" type="text" placeholder="" class='form-control'/>
                                                                </div>
                                                                <label class='col-sm-2 control-label'>Y轴坐标:</label>
                                                                <div class="col-sm-2">
                                                                    <input id="y" name="y" type="text" placeholder="" class='form-control'/>
                                                                </div> -->
                        </div>
                        <%--<div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>地址描述:</label>
                            <div class="col-sm-7">
                                <input id="addrDesc" name="addrDesc" type="text" placeholder="" class='form-control'/>
                            </div>
                        </div>--%>
                        <div class="form-group hasarea">
                            <label class='col-sm-4 control-label'><span class="required">*</span>案件描述:</label>
                            <div class="col-sm-7" style='height: 100%;'>
                                <textarea id="issueDesc" name="issueDesc" rows="10" cols="12" class="form-control"
                                          required="true"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>紧急程度:</label>
                            <div class="col-sm-7">
                                <select id="emrgencyLevel" name="emrgencyLevel" class='form-control'>
                                    <code:options codeName="jjcd" codeId="${param.jjcd}"/>
                                </select>
                            </div>
                        </div>
                        <div class="form-group hasarea">
                            <label class='col-sm-4 control-label'>备注:</label>
                            <div class="col-sm-7" style='height: 100%;'>
                                <textarea id="comment" name="comment" rows="10" cols="12"
                                          class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-group" id="uploadContainer">
                            <label class='col-sm-4 control-label'>附件上传:</label>
                            <div class="col-sm-7">
                                <el-upload ref="upload"
                                           class="upload-demo"
                                           action="${pageContext.request.contextPath}/frame/uploadAttachment"
                                           multiple
                                           accept="image/*"
                                           :on-preview="handlePreview"
                                           :on-success="handleSuccess"
                                           :file-list="fileList"
                                           list-type="picture">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传图片</div>
                                </el-upload>
                                <input type="hidden" value="" name="myfileData" id="uploadFile">
                            </div>
                        </div>
                        <div>
                            <div class='col-sm-4'></div>
                            <div class='col-sm-7' id="ansb_btns">

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal -->
</main>


<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="ssqyLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="ssqyLabel">请选择涉事企业</h4>
            </div>
            <div class="modal-body">
                <div class="filter">
                    <div class='btn_group '>
                        <button class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-refresh icon-refresh"></i>
                        </button>
                        <button class="btn btn-primary btn-lg"><i
                                class="glyphicon glyphicon-list-alt icon-list-alt"></i></button>
                        <div class="shaixuan">

                        </div>
                    </div>
                    <div class="form-group bs_date right">
                        <div class="input-group " style='margin-top: 12px;margin-left: 100px;'>
                            <input class="form-control" type="text" placeholder="搜索">
                        </div>
                    </div>
                </div>
                <div class="tbody" style='overflow-y: auto;height: auto; width: 100%;'>
                    <table class='table table-striped'>
                        <thead>
                        <tr>
                            <th>企业名称</th>
                            <th>当事人名称</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>2017-08-29 15:16:48
                            </td>
                            <td>操作日志</td>
                        </tr>
                        <tr>
                            <td>2017-08-29 15:16:48
                            </td>
                            <td>删除日志</td>
                        </tr>
                        <tr>
                            <td>2017-08-29 15:16:48
                            </td>
                            <td>观看日志</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <nav id="fenye" style="text-align: center;" pagination="pagination_new" pagenumber="1" totalpage="10">
                </nav>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade bs-example-modal-lg" id="power-code-modal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">请选择权力事项</h4>
            </div>
            <div class="modal-body">
                <!-- <div class="panel-body" style="padding-bottom: 0px;"> -->
                <table id="tb_powerlist"></table>
                <!-- </div> -->

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id='bt-power-list-commit'>确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" id='bt-power-list-cancel'>取消</button>
            </div>
        </div>
    </div>
</div>

<div id='map-wrapper'>
    <div data-dojo-type="dijit/layout/BorderContainer"
         data-dojo-props="design:'headline', gutters:false"
         style="width: 100%; height: 100%; margin: 0;">

        <div id="map" data-dojo-type="dijit/layout/ContentPane"
             data-dojo-props="region:'center'">
            <div id='map-click-info'>
                <div id="location">
                    <span>X :</span><span class="val" id='xVal'> --.---- </span>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <span>Y :</span><span class="val" id='yVal'> --.---- </span>
                </div>

                <button class='btn btn-success' id='map-submit' onclick="setXYtoIssue();"> 确定</button>
                <button class='btn btn-danger' id='map-cancel' onclick='hideMap();'> 取消</button>
            </div>
        </div>

    </div>
</div>
<%-- 附件预览--%>
<div id="dialog">
    <el-dialog
            title=""
            :visible.sync="dialogVisible"
            center
            @close="dialogVisible = false"
            width="60%">
        <img v-bind:src="url"/>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
    </el-dialog>
</div>


<script type="text/javascript">
    var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
    var url_prefix = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
<script src="${pageContext.request.contextPath}/static/js/map/gis_api_utils.js"></script>
<script src="${pageContext.request.contextPath}/static/js/map/layers/WMTSLayer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/map/map.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/ajsb.js"></script>


</body>
</html>
