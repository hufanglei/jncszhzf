<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

		<meta charset="utf-8" />
		<title><decorator:title default="江宁综合执法管理平台" /></title>
		<script type="text/javascript">
			var userId = '${sessionScope.currentMemberShip.userId}';
			var session_group_id = '${sessionScope.currentMemberShip.groupId}';
			var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';
			var orgId = '${sessionScope.currentMemberShip.user.orgNumber}';
			var issueTypeId = '${sessionScope.issueTypeId}';
            var url_prefix = '${pageContext.request.contextPath}';
            console.log(url_prefix);

            if(null == userId || userId == ""){
				window.location.href='${pageContext.request.contextPath}/login.jsp';
			}
		</script>

		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/iconfont.css"/>
		<link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/static/css/bootstrap.min.css'/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/template.css" />
		<link rel="stylesheet" href='${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css' />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/select.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css" />

		
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/menu/initMenu.js"></script>
		

		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/template.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.min.js"></script>
		<script src='${pageContext.request.contextPath}/static/js/locales/bootstrap-datetimepicker.zh-CN.js'></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/map/mapaddrs.js"></script>


<decorator:head />
</head>

<body style='overflow-x: hidden;'>
<!-- <body style="min-width:1024px;"> -->
	<!-------------------  登录信息、导航栏 开始  ----------------------- -->
		<aside>
			<div class="logo">
				江宁综合执法管理平台
			</div>
			<ul class="nav-list" id="nav-list">
				
			</ul>
			
		</aside>
	<!-------------------  登录信息、导航栏 结束  ----------------------- -->	
	
	<main>
		<div class="top">
			<form action="">
				<div class="anjian"><decorator:title/></div>
				<div class="search">
					<input type="text" />
					<embed src="${pageContext.request.contextPath}/static/images/jnqcszhzfpt.svg" type="image/svg+xml" pluginspage="http://www.adobe.com/svg/viewer/install/" />
					</div>
					<a class='user' href='${pageContext.request.contextPath}/login.jsp'> 
						<div class="pic">
							<span class='glyphicon glyphicon-off'></span>
						</div>
						<div class="name" style=''>退出登录</div>
					</a>
					&nbsp;&nbsp;
					<a href="#changepass" role="button" class="user btn btn-default" data-toggle="modal"> 
						<div class="pic">
							<span class='glyphicon glyphicon-cog'></span>
						</div>
						<div class="name" style=''>修改密码</div>
					</a>
					<div id="changepass" class="modal" data-easein="slideDownBigIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			        <div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h4 class="modal-title">修改密码</h4>
							</div>
							<div class="modal-body">
								<ul>
	                                <li class="form-field">
	                                    <div class='form-label'>请输入旧密码 :</div>
	                                    <div class='form-input'>
	                                        <input type='password' id="oldPassword" name="oldPassword"/><span style="display: inline" id="tip1"></span>
	                                    </div>                                
	                                </li>
	                                <li class="form-field">
	                                    <div class='form-label'>请输入新密码 :</div>
	                                    <div class='form-input'>
	                                        <input type='password' id="newPassword" name="newPassword" /><span style="display: inline" id="tip2"></span>
	                                    </div>
	                                </li>                            
	                                <li class="form-field">
	                                    <div class='form-label'>请确认新密码 :</div>
	                                    <div class='form-input'>
	                                        <input type='password' id="newPassword2" name="newPassword2" /><span style="display: inline" id="tip3"></span>                                    
	                                    </div>
	                                </li>
	                            </ul>
							</div>
							<div class="modal-footer">
								<button id="btn" class="btn btn-default" data-dismiss="modal" aria-hidden="true">提交</button>
							  	<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消</button>
							</div>
						</div>
			        </div>
			      </div>
					&nbsp;&nbsp;
					<a class="user" href='##'>
						<div class="pic">
							<span class='icon iconfont icon-icon10'></span>
						</div>
						<div class="name">${sessionScope.currentMemberShip.user.userName}</div>
					</a>
				</form>
			</div>
			<decorator:body />
	</main>
	
	<!-- 操作确认提示模态框   开始-->
	<div class="modal fade" tabindex="-1" role="dialog" id="modal-tips">
		<div class="modal-dialog" role="document">
			<div class="modal-content"  style='box-shadow:none'>
				<div class="modal-header" style='padding:9px 15px;border-radius:6px 6px 0 0;background-color:#337ab7'>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"  id="modal-tips-title"></h4>
				</div>
				<div class="modal-body" id="modal-tips-body" style='text-align:center;font-size:19px'>
					
				</div>
				<div class="modal-footer" style='padding:0px 15px 8px;text-align:center;border-top:none'>
					<button type="button" class="btn btn-primary" id="modal-tips-btn-ok" style='margin:0 30px'>确认</button>
					<button type='button' class='btn btn-primary' id='modal-tips-btn-false' style='margin:0 30px'>取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<!-- 操作确认提示模态框   结束-->

<script type="text/javascript">
$(document).ready(function(){                
      $("#newPassword").blur(function(){
          var num=$("#newPassword").val().length;
          if(num<6){
               $("#tip2").html("<font color=\"red\" size=\"2\">  请输入6位及以上密码</font>");                
          }else{
              $("#tip2").html("<font color=\"green\" size=\"2\"> 正确</font>");                
          }
      }) ;
      $("#newPassword2").blur(function(){
          var tmp=$("#newPassword").val();
          var num=$("#newPassword2").val().length;
          if($("#newPassword2").val()!=tmp){
              $("#tip3").html("<font color=\"red\" size=\"2\">  两次密码输入不同请重新输入</font>");                 
          }else{
              if(num>=6&&num<=18){
                  $("#tip3").html("<font color=\"green\" size=\"2\">  正确</font>");                    
              }else{
                  $("#tip3").html("<font color=\"red\" size=\"2\">  请输入密码</font>");                           
              }                
          }
      });
          $("#btn").click(function(){
              var flag=true;
              var old=$("#oldPassword").val();
              var pass=$("#newPassword").val();
              var pass2=$("#newPassword2").val();
              var num1=$("#newPassword").val().length;
              var num2=$("#newPassword2").val().length;
              if(num1!=num2||num1<6||num2<6||num1>18||num2>18||pass!=pass2){
                  flag=false;
              }else{
                  flag=true;
              }
              if(flag){                   
              $.ajax({
            	  type: 'post',
                  url:'${pageContext.request.contextPath}/user/modifyPassword',
                  data:{"userId":userId,"oldPassword":old,"newPassword":pass,"newPassword2":pass2},
                  success:function(e){                         
                      if(e.code==1){
                    	  $("#userId").val("");
                          $("#oldPassword").val("");
                          $("#newPassword").val("");
                          $("#newPassword2").val("");
                          $("#tip1").empty();
                          $("#tip2").empty();
                          $("#tip3").empty();
                      }else{
                    	  alert("修改成功");
                    	  window.location.reload();
                      }
				}
              });
          }
	});                  
});
</script>
</body>
</html>
