<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>待办任务</title>

    <link href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/issues/dbrw.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/star-rating.min.css"/>
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
            display: none;
        }

        .report-list, .handle-list, .evaluation-list, .register-list {
            overflow-y: auto;
            overflow-x: hidden;
            text-overflow: ellipsis;
            width: 95%;

        }
    </style>
</head>
<body style='overflow-x: hidden;'>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-dbrw"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-dcdbrw"/>
<!-- 导航辅助 结束 -->
<div class='wrap'>
    <section>
        <div class="content">
            <ul class="funShow clearfix" id="ul-header-tag">
            </ul>
            <table id="boot-table-dbrw">
            </table>
        </div>
    </section>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:100%;">
        <div class="modal-dialog modal-lg" style="width:75%;">
            <div class="modal-content" style="width:100%;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <%--<h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>案件主题：<span>{{ajDetail.issueSubject}}</span></div>
                        <div class='changePage' style='float: right;'>
                            <a href="##"></a>
                            <a href="##">上报人:{{addUserName}}</a>
                        </div>
                    </h4>
--%>
                </div>
                <div class="modal-body" id="dbrw-body">
                    <div class="shang clearfix">
                        <div class="infomation" style="width: 100%;">
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{bianhao.gdxx}}</span>
                            </div>
                            <div class="specific" style='overflow-y:hidden'>
                                <table class='table text-center'>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">案件编号</th>
                                    <th  style="background-color:#fff">{{ajDetail.dcdbnum}}</th>
                                    <th  style="background-color:#e9f3f4">信息来源</th>
                                    <th  style="background-color:#fff">{{ajDetail.sourceId}}</th>
                                    <th  style="background-color:#e9f3f4">涉嫌机构</th>
                                    <th  style="background-color:#fff">{{ajDetail.qhzdName}}</th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">情况描述</th>
                                    <th colspan="5" style="background-color:#fff">{{ajDetail.descript}}</th>
                                    </tbody>
                                    <%--<tr>
                                        <td>备注</td>
                                        <td>{{ajDetail.comment}}</td>
                                        <td style="background-color: #fff;"></td>
                                        <td></td>
                                    </tr>--%>
                                </table>


                                <%-- <table class="table">
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
                                     &lt;%&ndash;<tr>
                                         <td>涉事企业组织机构代码</td>
                                         <td>{{ajDetail.orgId}}</td>
                                     </tr>&ndash;%&gt;
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
                                 </table>--%>
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

                    <%--附件上传组件--%>
                    <div class="fileUpload" style="display: none">
                        <label class='col-sm-12 control-label'>附件上传:</label>
                        <div class="col-sm-12">
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
                    <div id='dbrw-cl'>

                    </div>

                    <!-- /.modal-content -->
                </div><!-- /.modal -->
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
                            <td class='col-sm-2' v-for='detail in department'>{{ajDetail.addUserid}}[{{detail.roleName}}]</td>
                            <td class='col-sm-2' v-for='detail in department' >{{detail.departmentName}}</td>
                            <td class='col-sm-2'>{{ajDetail.isOpen}}</td>
                            <td class='col-sm-2'>--</td>
                            <td class='col-sm-2'>{{ajDetail.issueTime}}</td>
                            <td class='col-sm-2'>--</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="ptmyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" style="width:100%;">
        <div class=" modal-dialog modal-lg" style="width: 75%">
            <div class="modal-content" style="width: 100%">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{ptajDetail.issueSubject}}</span></div>
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
                                    <th  style="background-color:#fff">{{ptajDetail.issueSource}}</th>
                                    <th  style="background-color:#e9f3f4">当事人类型</th>
                                    <th  style="background-color:#fff">{{ptajDetail.requestorType}}</th>
                                    <th  style="background-color:#e9f3f4">当事人姓名</th>
                                    <th  style="background-color:#fff">{{ptajDetail.requestorName}}</th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">当事人电话</th>
                                    <th  style="background-color:#fff">{{ptajDetail.requestorTel}}</th>
                                    <th  style="background-color:#e9f3f4">当事人地址</th>
                                    <th  style="background-color:#fff">{{ptajDetail.requestorAddres}}</th>
                                    <th  style="background-color:#e9f3f4">证件号码</th>
                                    <th  style="background-color:#fff">{{ptajDetail.requestorIdcard}}</th>
                                    </tbody>
                                    <tbody>

                                    <th  style="background-color:#e9f3f4">涉事企业名称</th>
                                    <th  style="background-color:#fff">{{ptajDetail.refCompanyName}}</th>
                                    <th  style="background-color:#e9f3f4">涉事企业组织机构代码</th>
                                    <th  style="background-color:#fff">{{ptajDetail.orgId}}</th>
                                    <th  style="background-color:#e9f3f4">紧急程度</th>
                                    <th  style="background-color:#fff">{{ptajDetail.emrgencyLevel_ms}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">处置时限</th>
                                    <th  style="background-color:#fff">{{ptajDetail.timeLimit}}天</th>
                                    <th  style="background-color:#e9f3f4">权利编码</th>
                                    <th  style="background-color:#fff">{{ptajDetail.issueType}}</th>
                                    <th  style="background-color:#e9f3f4">地图坐标</th>
                                    <th  style="background-color:#fff">{{ptajDetail.x}},{{ptajDetail.y}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">事发时间</th>
                                    <th  style="background-color:#fff">{{ptajDetail.issueTime}}</th>
                                    <th  style="background-color:#e9f3f4">地址描述</th>
                                    <th  style="background-color:#fff">{{ptajDetail.addrDesc}}</th>
                                    <th  style="background-color:#e9f3f4">事件主题</th>
                                    <th  style="background-color:#fff">{{ptajDetail.issueSubject}}</th>
                                    </tbody>
                                    <tbody>
                                    <th  style="background-color:#e9f3f4">事件描述</th>
                                    <th  colspan="5" style="background-color:#fff">{{ptajDetail.issueDesc}}</th>

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
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment'>{{ptajDetail.addUserid}}[{{ptDetail.roleName}}]</td>
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment' >{{ptDetail.departmentName}}</td>
                            <td class='col-sm-2'></td>
                            <td class='col-sm-2'>--</td>
                            <td class='col-sm-2'>{{ptajDetail.addTime}}</td>
                            <td class='col-sm-2'>--</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal_2" tabindex="-2" role="dialog" aria-labelledby="myModalLabel2"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <a href='##' class="close">&times;</a>
                    <h4 class="modal-title" id="myModalLabel2">
                        <div class="bianhao" style='float: left;'>案件处理：<span>{{qwgDetail.title}}</span></div>
                        <div class='changePage' style='float: right;'>
                            <a href="##"></a>
                            <a href="##"></a>
                        </div>
                    </h4>
                </div>
                <div class="modal-body" id="dbrw-body" v-loading="loading" element-loading-text="处理中,请稍候">
                    <div class="shang clearfix">
                        <div class="infomation" style='width:100%;border-bottom:1px solid #ccc'>
                            <div class="tittle">
                                <i class="glyphicon glyphicon-briefcase" aria-hidden="true"></i>工单信息：<span>{{qwgDetail.orderno}}</span>
                            </div>
                            <div class="specific" style='overflow-y:auto ;height: 155px;'>
                                <table class="table">
                                    <tr>
                                        <td>案件编号</td>
                                        <td>{{qwgDetail.orderno}}</td>
                                    </tr>
                                    <tr>
                                        <td>案件来源</td>
                                        <td>{{qwgDetail.sourcename}}</td>
                                    </tr>
                                    <tr>
                                        <td>案件标题</td>
                                        <td>{{qwgDetail.title}}</td>
                                    </tr>
                                    <tr>
                                        <td>案发时间</td>
                                        <td>{{qwgDetail.occurdate}}</td>
                                    </tr>
                                    <tr>
                                        <td>案发地点</td>
                                        <td>{{qwgDetail.occurlocation}}</td>
                                    </tr>
                                    <tr>
                                        <td>案件上报人</td>
                                        <td>{{qwgDetail.sourceperson}}</td>
                                    </tr>
                                    <tr>
                                        <td>上报人联系方式</td>
                                        <td>{{qwgDetail.sourcemobile}}</td>
                                    </tr>
                                    <tr>
                                        <td>案件内容</td>
                                        <td>{{qwgDetail.content}}</td>
                                    </tr>
                                    <tr>
                                        <td>案件附件</td>
                                        <td>
                                            <a target="_blank" v-for="file in qwgDetail.fileAttachments"
                                               :href="'${pageContext.request.contextPath}/fileupload/'+file">{{file}}、</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <form class='form-horizontal clearfix' id="new_form" style='background-color:#fff;margin-top:20px'
                          method="post" enctype="multipart/form-data">
                        <div class="clearfix">
                            <div class="form-group col-sm-8">
                                <div class="biaoti">批注：</div>
                                <textarea id="qwg-comments" class="form-control" rows="3" name='comment'></textarea>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="form-group col-sm-8">
                                <div class="biaoti">附件：</div>

                                <el-upload
                                        class="upload-demo"
                                        action="${pageContext.request.contextPath}/frame/uploadAttachment"
                                        multiple
                                        :limit="5"
                                        :on-success="uploadSuccessHandler"
                                        :file-list="uploadFileList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                </el-upload>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="form-group col-sm-12">
                                <button class='btn btn-primary' type='button' @click="rejectQwgIssue"
                                        id="btn-qwg-reject">退回
                                </button>
                                <button class='btn btn-success' id="btn-qwg-commit" type='button'
                                        style='margin-right:40px' @click="completeQwgIssue">提交
                                </button>

                            </div>
                        </div>
                    </form>
                </div>

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
        <img v-bind:src="url" style="height: 100%;width:100%"/>
        <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
              </span>
    </el-dialog>
</div>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>

<script type="text/javascript">
    var url_prefix = '${pageContext.request.contextPath}';
    var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';


    function timeDifference(date1, date2) {
        var date1 = '2015/05/01 00:00:00';  //开始时间
        var date2 = new Date();    //结束时间
        var date3 = new Date(date2).getTime() - new Date(date1).getTime();   //时间差的毫秒数

        var leave1 = date3 % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
        var leave2 = leave1 % (3600 * 1000)        //计算小时数后剩余的毫秒数
        var minutes = Math.floor(leave2 / (60 * 1000))

        alert(" 相差 " + minutes + " 分钟")
    }

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

        if (responseObject.result == 1 || responseObject.success == true || responseObject.success == "true") {
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils.js"></script>
<script src="${pageContext.request.contextPath}/static/js/issues/dcdbrw.js"></script>
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
        dialog.url = '${pageContext.request.contextPath}/issues/showAttachment?path=' + url;
        dialog.dialogVisible = true;
    };

</script>


</html>
