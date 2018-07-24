<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <title>督察督办上报</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/bootstrap-table.min.css"/>
    <link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/css/anjianshangbao.css"/>
    <link rel="stylesheet"
    href="${pageContext.request.contextPath}/static/css/map/map.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/elementui/theme-default/index.css">
    <link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css'/>


    <style>
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

        /* 督察督办 start */
        .filter {
            height: 50px;
            background-color: #eff0f4;
            line-height: 50px;
        }

        #container, #container * {
            box-sizing: content-box !important;
        }

        .breadcrumb > li + li:before {
            color: #CCCCCC;
            content: "> ";
            padding: 0 5px;
        }

        .breadcrumb {
            padding: 2px 15px;
            margin-bottom: 0;
            list-style: none;
            background-color: #f5f5f5;
            border-radius: 4px;
        }

        .fill {
            padding-top: 30px;
            background: #fff;
            padding-bottom: 30px;
        }

        .fill .col-sm-3 {
            padding-left: 0;
            padding-right: 0;
        }

        .fill .col-sm-6 {
            padding-left: 15px;
            padding-right: 15px;
        }

        .fill .form-control {
            border-radius: 0;
            padding-top: 0;
            padding-bottom: 0;
            line-height: 28px;
        }

        .fill .form-horizontal .control-label {
            padding-top: 4px;
            font-weight: normal;
        }

        .fill .form-group {
            margin-bottom: 8px;
        }

        .fill .form_date2, .fill .form_date {
            padding-left: 15px;
            padding-right: 15px;
        }

        .fill .input-group-addon {
            height: 28px;
        }

        /*.fill .hasarea{height: 70px !important;}*/
        .fill .hasarea .form-control {
            height: 65px !important;
            position: relative;
        }

        .col-sm-4 {
            padding-right: 0;
        }

        .col-sm-7 {
            position: relative
        }

        input {
            outline: none;
        }

        .fill .select {
            position: absolute;
            width: 50px;
            height: 33px;
            line-height: 33px;
            text-align: center;
            /*background-color: #66afe9;*/
            border: none;
            z-index: 20;
            right: 15px;
            top: 0;
            /*border: 2px solid #66afe9*/
        }

        .fill .select:hover, .fill .select:active {
            border: 0px;
        }

        .has-tanchuang {
            padding-right: 60px;
        }

        .propbox {
            width: 600px;
            height: 400px;
            background: red;
            display: none;
        }

        .show {
            display: block;
        }

        .tbody {
            height: auto !important;
        }

        .tbody thead tr th {
            padding: 10px 0;
        }
        .glyphicon-ok{
            color: #0f8b31;
        }
        .glyphicon-remove{
            color: #8b0b1e;
        }

        /*.filter .form-group{float: right;}*/
        .pagination {
            margin: 0
        }

        /* 督察督办 end */
    </style>
</head>
<body>
<!-- 导航辅助 开始 -->
<!-- 一级标签选中 -->
<input id="nav-flag-top" type="hidden" value="nav-flag-top-ajsb"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-dbsb"/>
<!-- 导航辅助 结束 -->
<main>
    <section>
        <div class="content">
            <div class="filter">
                <ol class="breadcrumb">
                    <li><a href="#">首页</a></li>
                    <li class="active">督察督办上报</li>
                </ol>
            </div>
            <div class="fill clearfix myDcdbModal">
                <div class="col-sm-10 ">
                    <form class='form-horizontal clearfix' id="postForm" method="post" enctype="multipart/form-data">
                        <input type="hidden" id="dcdb_qhzdName" name="qhzdName"/><%--机构名称--%>
                        <input type="hidden" id="dcdb_typeName" name="typeName"/><%--违规类型--%>
                        <input type="hidden" id="dcdb_sourceName" name="sourceName"/><%--信息来源--%>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>案件编号:</label>
                            <div class="col-sm-7">
                                <input id="issueType" name="issuenum" type="text" placeholder="输入案件编号"
                                       autocomplete="off" class='form-control has-tanchuang' onblur="dcdb_obj.numCheck()"/>
                               <div id="gly-iconOk" > <i class="select glyphicon glyphicon-ok" style="background-color: green" ></i></div>
                                <div id="icon-Remove"> <i class="select glyphicon glyphicon-remove" style="background-color: #d00808" ></i></div>

                            </div>

                        </div>
                        <%--<div class="form-group ">--%>
                            <%--<label for="khyf_panel" class="col-sm-4 control-label"><span class="required">*</span>考核月份:</label>--%>
                            <%--<div class="col-sm-7 input-group date form_date" data-date="" data-date-format="yyyy-mm"--%>
                                 <%--data-link-field="khyf_panel" data-link-format="yyyy-mm">--%>
                                <%--<input id="khyf_panel" name="khyf" class="form-control" type="text" value=""--%>
                                       <%--readonly=""> <span class="input-group-addon"><span--%>
                                    <%--class="glyphicon glyphicon-calendar"></span></span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <label class='col-sm-4 control-label'><span class="required">*</span>信息来源:</label>
                            <div class="col-sm-7">
                                <select id="issueSource" name="sourceId" class='form-control' onblur="dcdb_obj.dcNoify()" required>
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
                                <textarea id="descript" name="descript" rows="4" cols="12" class="form-control"
                                          required="true"></textarea>
                            </div>
                        </div>



                        <%-- ====================================== 附件上传 =================================--%>
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
                                           :on-remove="handleRemove"
                                           :file-list="fileList"
                                           list-type="picture">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传图片</div>
                                </el-upload>
                                <input type="hidden" value="" name="myfileData" id="uploadFile">
                            </div>
                        </div>
                        <%-- ==============================================================================--%>

                        <%-- ====================================== 督察通知单 =================================--%>
                        <div class="form-group" id="uploadContainer2">
                            <label class='col-sm-4 control-label danger'>督察通知单:</label>
                            <div class="col-sm-7">
                                <el-upload ref="upload"
                                           class="upload-demo"
                                           action="${pageContext.request.contextPath}/frame/uploadAttachment"
                                           multiple
                                           accept="image/*"
                                           :on-preview="handlePreview"
                                           :on-success="handleSuccess"
                                           :on-remove="handleRemove"
                                           :file-list="fileList"
                                           list-type="picture">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传图片</div>
                                </el-upload>
                               <%-- <input type="hidden" value="" name="myfileData" id="uploadFile">--%>
                            </div>
                        </div>
                        <%-- ==============================================================================--%>

                        <div class="form-group">
                            <div class='col-sm-12 text-center'>
                                <button type='button' onclick="dcdb_obj.saveDcdbInfo(this)"
                                        class='btn  btn-primary btn-common'>提交
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal -->
</main>



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

<div id="dialog2">
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

<script src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
<script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
<script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>

<script type="text/javascript">
    $('.form_date').datetimepicker({
        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        startView: 3,
        minView: 3,
        forceParse: false,
        language: 'zh-CN'
    });
    var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
    var url_prefix = '${pageContext.request.contextPath}';
    console.log(groupTag);
</script>


<script>
    $('.form_date2').datetimepicker({
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    });


</script>

<script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>


<script type="text/javascript">
    var dcdb_obj = {
        submit_flag : true,
        checkNumFlag:false,
        //案件编号验证
        numCheck:function () {
            //获取文文本框的值
            dcdb_obj.checkNumFlag=false;
            var issueNum = $("#issueType").val();
            //$.ajax
            $.ajax({
                url: url_prefix + "/jxkh/selectIssueNum.action",
                data: {
                    issueNum:issueNum
                },
                type: "POST",
                success: function (result) {
                    if (result == "1") {
                        $('#icon-Remove').hide();
                        $('#gly-iconOk').show();
                        dcdb_obj.checkNumFlag=true;
                    }else if(result==0) {

                        $('#icon-Remove').show();
                        $('#gly-iconOk').hide();
                        dcdb_obj.checkNumFlag=false
                    }
                }
            });

        },
       dcNoify:function () {
            //获取文文本框的值
            var issueSource = $("#issueSource").val();
         if (issueSource==("督办问责")){
             $('#uploadContainer2').show();
         }else{
             $('#uploadContainer2').hide();
         }

        },
        saveDcdbInfo: function (obj) {

            var _this = this;

            if(!_this.checkNumFlag){
                layerAlert("案件编号不正确,请重新输入!")
                return;
            }
            if (_this.submit_flag) {
                var files = [];
                var result = _this.checkDcdbDate();
                if (result) {
                    _this.submit_flag = false;
                    //将上传的附件放入隐藏域
                    $.each(uploader.fileList, (i, o) => {
                        if (o && o.response) {
                            files.push({
                                "fileName": o.response.url,
                                "originalFileName": o.response.name,
                                "size": o.response.size,
                                "type":1
                            });
                        }
                    });
                    //督办通知单附件上传  保存到fileLIst 中
                    $.each(uploader2.fileList, (i, o) => {
                        if (o && o.response) {
                            files.push({
                                "fileName": o.response.url,
                                "originalFileName": o.response.name,
                                "size": o.response.size,
                                "type":2
                            });
                        }
                    });
                    $('#uploadFile').val(JSON.stringify(files));


                    var options = {
                        url: url_prefix + '/jxkh/saveDcdbInfo.action',
                        dataType: 'json',
                        success: function (responseObject, statusText) {
                            if (responseObject.result == 1 || responseObject.result == "success" || responseObject.success) {
                                layerAlert('保存成功！');
                                document.getElementById("postForm").reset();
                                uploader.$refs.upload.clearFiles();
                                _this.submit_flag = true;
                            } else {
                                layerAlert('保存失败！');
                                _this.submit_flag = true;
                            }
                        }
                    };
                    $("#postForm").ajaxForm(options);
                    $('#postForm').submit();
                }

            }
        },
        //页面提交校验
        checkDcdbDate: function () {
            var flag = true;
            $('#issueType,#qhzdGroup,#descript,#issueSource').removeClass('fill_error').each(function (index, item) {
                // console.log(index);
                if ($(item).val() == '') {
                    $(item).addClass('fill_error');
                    showDialog('错误提示', '红色选中项为必填！', callBack);

                    function callBack() {
                        $(".modal").modal('hide');
                    };
                    flag = false;
                    submit_flag = true;
                }
            });

            return flag;
        },

        init: function () {
            var _this = this;
            initUserGroupMenu($("#qhzdGroup"), groupTag);
            initAddOptionsDb($("#issueSource"), url_prefix + '/qhzd/listDictionary.aciton', groupTag);
            $('#uploadContainer2').hide();
            $('#icon-Remove').hide();
            $('#gly-iconOk').hide();
            //涉嫌机构
            $("#qhzdGroup").change(function () {
                $("#dcdb_qhzdName").val($.trim($("#qhzdGroup").find("option:selected").text()))
            });

            //信息来源
            $("#issueSource").change(function () {
                $("#dcdb_sourceName").val($.trim($("#issueSource").find("option:selected").text()))
            });


            $('#myDcdbModal.close').click(function () {
                $('#myDcdbModal').modal('hide');
            });
        }
    }
    dcdb_obj.init();
</script>

<script>
    var uploader = new Vue({
        el: '#uploadContainer',
        data: {
            fileList: []
        },
        methods: {
            handlePreview(file) {
                dialog.url = file.url;
                dialog.dialogVisible = true;
            },
            handleSuccess(response, file, list) {
                this.fileList = list;
            },
            handleRemove(file, list) {
                this.fileList = list;
            }
        }
    });
    var uploader2 = new Vue({
        el: '#uploadContainer2',
        data: {
            fileList: []
        },
        methods: {
            handlePreview(file) {
                dialog2.url = file.url;
                dialog2.dialogVisible = true;
            },
            handleSuccess(response, file, list) {
                this.fileList = list;
            },
            handleRemove(file, list) {
                this.fileList = list;
            }
        }
    });
    var dialog = new Vue({
        el: '#dialog',
        data() {
            return {
                url: '',
                dialogVisible: false
            }
        }
    });

    var dialog2 = new Vue({
        el: '#dialog2',
        data() {
            return {
                url: '',
                dialogVisible: false
            }
        }
    });
</script>

</body>
</html>
