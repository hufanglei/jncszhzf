<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/code.tld" prefix="code" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>街道中心工作考核</title>

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
    <style type="text/css">
        .filter, .filter form {
            height: 100px;
        }
         .page-bar{

         }
        ul,li{
            margin: 0px;
            padding: 0px;
        }
        li{
            list-style: none
        }
        .page-bar li:first-child>a {
            margin-left: 0px
        }
        .page-bar a{
            border: 1px solid #ddd;
            text-decoration: none;
            position: relative;
            float: left;
            padding: 6px 12px;
            margin-left: -1px;
            line-height: 1.42857143;
            color: #337ab7;
            cursor: pointer
        }
        .page-bar a:hover{
            background-color: #eee;
        }
        .page-bar a.banclick{
            cursor:not-allowed;
        }
        .page-bar .active a{
            color: #fff;
            cursor: default;
            background-color: #337ab7;
            border-color: #337ab7;
        }
        .page-bar i{
            font-style:normal;
            color: #d44950;
            margin: 0px 4px;
            font-size: 12px;
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
<input id="nav-flag-top" type="hidden" value="nav-flag-top-jxkh"/>
<!-- 二级标签选中 -->
<input id="nav-flag-sub" type="hidden" value="nav-flag-sub-gdgx-zhzfgzkh"/>
<!-- 导航辅助 结束 -->
<div>
    <section>
        <div class="content" style="height: 462px;">
            <div class="filter">
                <form action="">
                    <div class="form-group bs_date">
                        <label class="control-label" style="width:90px;">统计方式:</label>
                        <label class="control-label" style="width:90px;"> <input type="radio" name="refresh"
                                                                                 onclick="refreshYear(1)">年度统计</label>
                        <label class="control-label" style="width:90px;"> <input type="radio" name="refresh"
                                                                                 onclick="refreshYear(2)">季度统计</label>
                        <label class="control-label" style="width:90px;"> <input type="radio" name="refresh" checked
                                                                                 onclick="refreshYear(3)">月度统计</label>
                    </div>
                    <br>
                    <div id="yearCount">
                        <div class="form-group bs_date">
                            <label for="khyf" class=" control-label">考核时间</label>
                            <div class="input-group date form_date_year" data-date="" data-date-format="yyyy-mm"
                                 data-link-field="khyf" data-link-format="yyyy">
                                <input class="form-control" size="16" type="text" value="" readonly=""> <span
                                    class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <button type="button" onclick="refreshDataTableByParams(1)">查询</button>
                    </div>

                    <div id="quarterCount">
                        <div class="form-group bs_date">
                            <label for="khyf" class=" control-label">考核时间</label>
                            <div class="input-group date form_date_year" data-date="" data-date-format="yyyy-mm"
                                 data-link-field="khyf" data-link-format="yyyy">
                                <input class="form-control" size="16" type="text" value="" readonly=""> <span
                                    class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                            <input type="hidden" id="khyf" name="khyf" value=""><br>
                        </div>
                        <div id="jdselect">
                            <label class=" control-label" style="width:90px;"> <input type="radio" name="jdfresh"
                                                                                      value="1" onclick="">第一季度</label>
                            <label class=" control-label" style="width:90px;"> <input type="radio" name="jdfresh"
                                                                                      value="2" onclick="">第二季度</label>
                            <label class=" control-label" style="width:90px;"> <input type="radio" name="jdfresh"
                                                                                      value="3" onclick="">第三季度</label>
                            <label class=" control-label" style="width:90px;"> <input type="radio" name="jdfresh"
                                                                                      value="4" onclick="">第四季度</label>
                            <button type="button" onclick="refreshDataTableByParams(2)">查询</button>
                        </div>
                    </div>
                    <div id="mouthCount">
                        <div class="form-group bs_date">
                            <label class=" control-label">考核时间</label>
                            <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm"
                                 data-link-field="dtp_input2" data-link-format="yyyy-mm">
                                <input class="form-control" size="10" type="text" id="khsj">
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                        </div>
                        <button type="button" onclick="refreshDataTableByParams(3)">查询</button>
                    </div>

                </form>
            </div>
            <div class="content">

                <table id="boot-table-dbrw">
                </table>
            </div>
        </div>
    </section>
    <%--  <div class="wrap modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
           aria-hidden="true">
          <div class=" modal-dialog modal-lg">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                      <h4 class="modal-title" id="myModalLabel">
                          <div class="bianhao" style='float: left;'>绩效考核：</div>
                      </h4>
                  </div>
                  <div class="modal-body" id="dbrw-body">
                      <div class="shang clearfix">
                          &lt;%&ndash;<div class="infomation">&ndash;%&gt;
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
                                      <td>完整性、规范性</td>
                                      <td>{{qwgDetail.wzxgfx}}</td>
                                  </tr>
                                  <tr>
                                      <td>反复投诉</td>
                                      <td>{{qwgDetail.ffts}}</td>
                                  </tr>
                                  <tr>
                                      <td>督办问责</td>
                                      <td>{{qwgDetail.dbwz}}</td>
                                  </tr>
                                  <tr>
                                      <td>工作协调</td>
                                      <td>{{qwgDetail.gzxt}}</td>
                                  </tr>
                                  <tr>
                                      <td>受理交办</td>
                                      <td>{{qwgDetail.sljb}}</td>
                                  </tr>
                                  <tr>
                                      <td>巡查机制</td>
                                      <td>{{qwgDetail.xcjz}}</td>
                                  </tr>
                                  <tr>
                                      <td>宣传报道</td>
                                      <td>{{qwgDetail.xcbd}}</td>
                                  </tr>
                                  <tr>
                                      <td>监督检查</td>
                                      <td>{{qwgDetail.jdjc}}</td>
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
                          &lt;%&ndash;</div>&ndash;%&gt;
                          <!-- /.modal-content -->
                      </div><!-- /.modal -->
                  </div>
              </div>
          </div>
      </div>--%>
    <%--普通案件的案件信息--%>
    <div class="wrap modal fade" id="zxclModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" style="margin-top:100px;">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content" style="margin-top:100px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>考评明细：</div>
                    </h4>
                </div>
                <div class=" modal-footer" role="dialog" id="gsx_mx" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <tr>
                            <th colspan="1">考评规则:</th>
                            <th colspan="5" id="khgz">
                        </tr>
                        <tr>
                            <th colspan="1">考评得分:</th>
                            <th colspan="5" id="kpdf">
                        </tr>
                        <tr>
                            <th colspan="1">案件编号:</th>
                            <th colspan="5">
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="1" checked
                                                                      onclick="refreshKfx(1)">全部</label>
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="2"
                                                                      onclick="refreshKfx(2)">正常项</label>
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="3"
                                                                      onclick="refreshKfx(3)">扣分项</label>
                            </th>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer" style='max-height:400px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <thead>
                        <tr style="position:fixed;top:195px;background: #ffffff;text-align: center  ">
                            <th style="width:120px;">序号</th>
                            <th style="width:120px;">案件编号</th>
                            <th style="width:120px;">案件主题</th>
                            <th style="width:122px;">创建时间</th>
                            <th style="width:122px;">结束日期</th>
                            <th style="width:122px;">时间限制</th>
                            <th style="width:122px;">办理单位</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in ajkhDetail" style="margin-top:290px;">
                            <td>{{index+1}}</td>
                            <td @click="showPage(item.issueNumber,item.processId)" style="color: #2D93CA">
                                {{item.issueNumber}}
                            </td>
                            <td>{{item.issueSubject}}</td>
                            <td>{{item.addTime}}</td>
                            <td>{{item.endTime}}</td>
                            <td>{{item.timeLimit}}天</td>
                            <td>{{item.isOpen}}</td>
                        </tr>
                        </tbody>

                    </table>
                    <div class="page-bar">
                        <ul>
                            <li v-if="cur>1"><a v-on:click="cur--,pageClick()">上一页</a></li>
                            <li v-if="cur==1"><a class="banclick">上一页</a></li>
                            <li v-for="index in indexs"  v-bind:class="{ 'active': cur == index}">
                                <a v-on:click="btnClick(index)">{{ index }}</a>
                            </li>
                            <li v-if="cur!=all"><a v-on:click="cur++,pageClick()">下一页</a></li>
                            <li v-if="cur == all"><a class="banclick">下一页</a></li>
                            <li><a>共<i>{{all}}</i>页</a></li>
                        </ul>
                    </div>

                </div>
            </div>
                </div>


    </div>
    <%--督办案件的案件信息--%>
    <div class="wrap2 modal fade" id="dbajModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content" style="margin-top: 160px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>考评明细：</div>
                    </h4>
                </div>
                <div class=" modal-footer" role="dialog" id="gsx_mx" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <tr>
                            <th colspan="1">考评规则:</th>
                            <th colspan="5" id="dckhgz">
                        </tr>
                        <tr>
                            <th colspan="1">考评得分:</th>
                            <th colspan="5" id="dckpdf">
                        </tr>
                        <tr>
                            <th colspan="1">案件编号:</th>
                            <th colspan="5">
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="1" checked
                                                                      onclick="refreshDcdb(1)">全部</label>
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="2"
                                                                      onclick="refreshDcdb(2)">正常项</label>
                                <label class=" control-label"> <input type="radio" name="jdfresh" value="3"
                                                                      onclick="refreshDcdb(3)">扣分项</label>
                            </th>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer" style='max-height:300px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <thead>
                        <tr style="position:fixed;top:195px;background: #ffffff;text-align: center  ">
                            <th style="width:143px;">序号</th>
                            <th style="width:143px;">督办案件编号</th>
                            <th style="width:143px;">案件编号</th>
                            <th style="width:145px;">案件来源</th>
                            <th style="width:145px;">分数</th>
                            <th style="width:145px;">办理单位</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(items, index) in dcdbDetail">
                            <td>{{index+1}}</td>
                            <td @click="showDbPage(items.dcdbnum,items.processId)" style="color: #2D93CA">
                                {{items.dcdbnum}}
                            </td>
                            <td>{{items.issuenum}}</td>
                            <td>{{items.sourceId}}</td>
                            <td>{{items.score}}</td>
                            <td>{{items.addUserid}}</td>
                        </tr>
                        </tbody>

                    </table>
                </div>

            </div>
        </div>
    </div>
    <%--宣传报道列表页面--%>
    <div class="wrap5 modal fade" id="xcbdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class=" modal-dialog modal-lg">
            <div class="modal-content" style="margin-top: 160px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">
                        <div class="bianhao" style='float: left;'>考评明细：</div>
                    </h4>
                </div>
                <div class=" modal-footer" role="dialog" id="gsx_mx" style='max-height:150px;overflow-y:auto'>
                    <table class='table text-center' style='border-radius:4px'>
                        <tr>
                            <th colspan="1">考评规则:</th>
                            <th colspan="5" id="xcbdgz">
                        </tr>
                        <tr>
                            <th colspan="1">考评得分:</th>
                            <th colspan="5" id="xcbddf">
                        </tr>
                        <%--<tr>
                            <th colspan="1">案件编号:</th>
                            <th colspan="5">
                                    <label  class=" control-label"> <input type="radio" name="jdfresh" value="1" checked onclick="refreshDcdb(1)" >全部</label>
                                    <label  class=" control-label"> <input type="radio" name="jdfresh" value="2"  onclick="refreshDcdb(2)" >正常项</label>
                                    <label  class=" control-label"> <input type="radio" name="jdfresh" value="3" onclick="refreshDcdb(3)">扣分项</label>
                            </th>
                        </tr>--%>
                    </table>
                </div>
                <div class="modal-footer" style='max-height:300px;overflow-y:auto'>
                    <table class='table text-center' style="border-radius:4px;" >
                        <thead >
                            <tr  style="border-style: solid solid solid solid;">
                            <td rowspan="2" style="border-style: solid solid solid solid;">序号</td>
                            <td colspan="2" style="border-style: solid solid solid solid;">国家级</td>
                            <td colspan="2" style="border-style: solid solid solid solid;">省级</td>
                            <td colspan="2" style="border-style: solid solid solid solid;">市级</td>
                            <td colspan="2" style="border-style: solid solid solid solid;">区级</td>
                            <td rowspan="2" style="border-style: solid solid solid solid;">办理单位</td>

                        </tr>
                        <tr style="border-style: solid solid solid solid;">
                            <td >网络媒体</td>
                            <td >非网络媒体</td>
                            <td >网络媒体</td>
                            <td >非网络媒体</td>
                            <td >网络媒体</td>
                            <td >非网络媒体</td>
                            <td >网络媒体</td>
                            <td >非网络媒体</td>
                        </tr>
                        </thead>
                        <tbody >
                        <tr v-for="(items, index) in xcbdDetail" style="border-style: solid solid solid solid;">
                            <td style="border-style: solid solid solid solid;">{{index+1}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.countrywlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.countryfwlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.provincewlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.provincefwlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.citywlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.cityfwlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.districtwlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.districtfwlmt}}</td>
                            <td style="border-style: solid solid solid solid;">{{items.qhzdMc}}</td>
                        </tr>
                        </tbody>

                    </table>
                </div>

            </div>
        </div>
    </div>
    <%--普通案件详情--%>
    <div class="wrap3 modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                                    <th style="background-color:#e9f3f4">案件来源</th>
                                    <th style="background-color:#fff">{{ajDetail.issueSource}}</th>
                                    <th style="background-color:#e9f3f4">当事人类型</th>
                                    <th style="background-color:#fff">{{ajDetail.requestorType}}</th>
                                    <th style="background-color:#e9f3f4">当事人姓名</th>
                                    <th style="background-color:#fff">{{ajDetail.requestorName}}</th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">当事人电话</th>
                                    <th style="background-color:#fff">{{ajDetail.requestorTel}}</th>
                                    <th style="background-color:#e9f3f4">当事人地址</th>
                                    <th style="background-color:#fff">{{ajDetail.requestorAddres}}</th>
                                    <th style="background-color:#e9f3f4">证件号码</th>
                                    <th style="background-color:#fff">{{ajDetail.requestorIdcard}}</th>
                                    </tbody>
                                    <tbody>

                                    <th style="background-color:#e9f3f4">涉事企业名称</th>
                                    <th style="background-color:#fff">{{ajDetail.refCompanyName}}</th>
                                    <th style="background-color:#e9f3f4">涉事企业组织机构代码</th>
                                    <th style="background-color:#fff">{{ajDetail.orgId}}</th>
                                    <th style="background-color:#e9f3f4">紧急程度</th>
                                    <th style="background-color:#fff">{{ajDetail.emrgencyLevel_ms}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">处置时限</th>
                                    <th style="background-color:#fff">{{ajDetail.timeLimit}}天</th>
                                    <th style="background-color:#e9f3f4">权利编码</th>
                                    <th style="background-color:#fff">{{ajDetail.issueType}}</th>
                                    <th style="background-color:#e9f3f4">地图坐标</th>
                                    <th style="background-color:#fff">{{ajDetail.x}},{{ajDetail.y}}
                                    </th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">事发时间</th>
                                    <th style="background-color:#fff">{{ajDetail.issueTime}}</th>
                                    <th style="background-color:#e9f3f4">地址描述</th>
                                    <th style="background-color:#fff">{{ajDetail.addrDesc}}</th>
                                    <th style="background-color:#e9f3f4">事件主题</th>
                                    <th style="background-color:#fff">{{ajDetail.issueSubject}}</th>
                                    </tbody>
                                    <tbody>
                                    <th style="background-color:#e9f3f4">事件描述</th>
                                    <th colspan="5" style="background-color:#fff">{{ajDetail.issueDesc}}</th>

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
                        <tr>
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment'>
                                {{ajDetail.addUserid}}[{{ptDetail.roleName}}]
                            </td>
                            <td class='col-sm-2' v-for='ptDetail in ptDepartment'>{{ptDetail.departmentName}}</td>
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
    <%--督办案件详情--%>
    <div class="wrap4 modal fade" id="dbModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                        <%-- <tr >
                             <td class='col-sm-2' v-for='detail in department'>{{dbajDetail.addUserid}}[{{detail.roleName}}]</td>
                             <td class='col-sm-2' v-for='detail in department' >{{detail.departmentName}}</td>
                             <td class='col-sm-2'>{{dbajDetail.info}}</td>
                             <td class='col-sm-2'>--</td>
                             <td class='col-sm-2'>{{dbajDetail.startTime}}</td>
                             <td class='col-sm-2'>--</td>
                         </tr>--%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
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
        $('.form_date_year').datetimepicker({
            format: 'yyyy',
            weekStart: 1,
            autoclose: true,
            startView: 4,
            minView: 4,
            forceParse: false,
            language: 'zh-CN'
        });
    </script>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap-table.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/locales/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/issues/star-rating.min.js"></script>
    <script type="text/javascript">
        var url_prefix = '${pageContext.request.contextPath}';
        var user_group_type = '${sessionScope.currentMemberShip.group.groupTag}';
    </script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue.min.js'></script>
    <script type="text/javascript">
        var pageBar = new Vue({
            el: '.page-bar',
            data: {
                all: 10, //总页数
                cur: 1//当前页码
            },
            watch: {
                cur: function(oldValue , newValue){

                }
            },
            methods: {
                btnClick: function(data){//页码点击事件
                    if(data != this.cur){
                        this.cur = data
                    }
                },
                pageClick: function(){
                    console.log('现在在'+this.cur+'页');
                }
            },

            computed: {
                indexs: function(){
                    var left = 1;
                    var right = this.all;
                    var ar = [];
                    if(this.all>= 5){
                        if(this.cur > 3 && this.cur < this.all-2){
                            left = this.cur - 2
                            right = this.cur + 2
                        }else{
                            if(this.cur<=3){
                                left = 1
                                right = 5
                            }else{
                                right = this.all
                                left = this.all -4
                            }
                        }
                    }
                    while (left <= right){
                        ar.push(left)
                        left ++
                    }
                    return ar
                }

            }
        })
    </script>
    <script src='${pageContext.request.contextPath}/static/js/vue/vue-resource.min.js'></script>
    <script src="${pageContext.request.contextPath}/static/js/elementui/index.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jxkh/zhzfgzkh.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/1.9.3/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils.js"></script>
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
