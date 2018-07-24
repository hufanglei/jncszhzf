<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>江宁综合执法管理平台</title>
	<script type="text/javascript">
        var groupTag = '${sessionScope.currentMemberShip.group.groupTag}';

	</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/iconfont.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/reset.css" />
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js"></script>


<style type="text/css">
*{
	margin: 0;
	padding: 0;
}

html, body {
	height: 100%;
}

.inner {
	width: 1050px;
	margin: 0 auto
}

.top {
	height: 75px;
	line-height: 75px;
	background: #fff;
}

.top .inner span {
	display: block;
	float: left;
	height: 75px;
	color: #40c6c3;
	text-shadow: 2px 2px 6px #40c6c3;
	font-size: 22px;
}

.top .inner i {
	font-style: normal;
	color: #999999;
	font-size: 20px;
	width: 450px;
	float: right
}

.bottom {
	color: #999;
	height: 75px;
	line-height: 75px;
	text-align: center;
	font-size: 14px;
}

section {
	background-color: #40c6c3;
}

section .inner {
	background: url(${pageContext.request.contextPath}/static/images/login-bg.png) center left no-repeat;
	height: 100%;
	background-size: 50%;
	display: flex;
	align-items: center;
	justify-content: flex-end
}

.login {
	width: 450px;
	float: right;
	width: 410px;
	background: #f2f7ff;
	padding: 0 48px;
	margin-right: 40px;
}

.login h2 {
	margin: 0;
	height: 80px;
	line-height: 80px;
	font-size: 20px;
}

section .inner form input {
	width: 100%;
	height: 38px;
	line-height: 38px;
	padding-left: 49px;
	padding-right: 30px !important;
	border: 1px solid #c5c5c5;
	padding-right: 5px;
	margin-bottom: 7px;
}

section .inner form .user {
	position: relative;
}

section .inner form .user span {
	position: absolute;
	left: 1px;
	top: 1px;
	font-size: 22px;
	background-color: #e8ebf0;
	color: #FFFFFF;
	width: 40px;
	text-align: center;
	height: 36px;
}

section .inner form .user i {
	position: absolute;
	right: 10px;
	top: 6px;
	font-size: 18px;
	cursor: pointer
}

section .inner form .yzm input {
	padding: 0 24px;
	font-size: 14px;
	margin-bottom: 0;
}

.yzBox {
	display: flex;
	align-items: center;
	height: 42px;
	justify-content: space-between;
	margin-bottom: 15px;
}

.yzpic {
	height: 38px;
	width: 50%;
}

/* .again {
	color: #999;
	display: block;
} */

.btns {
	display: flex;
	justify-content: space-between;
}

.btns button {
	width: 135px;
	height: 40px;
	background-color: #1194fc;
	color: #FFFFFF;
	border: none;
	outline: none;
}

.localpsw {
	display: flex;
	justify-content: space-between;
	height: 35px;
	line-height: 35px;
	color: #333;
	margin-top: 10px;
	margin-bottom: 20px;
}

.localpsw .jzpsw {
	position: relative;
	padding-left: 25px;
	cursor: pointer;
}

.localpsw .jzpsw span {
	display: block;
	position: absolute;
	top: 11px;
	height: 12px;
	width: 12px;
	border: 2px solid #ccc;
	left: 5px;
}

.localpsw .jzpsw .active {
	background-color: #40c6c3;
}

section .inner form .error {
	color: #d44950;
	border-color: #d44950;
}
#code{  
    font-family:Arial;  
    font-style:italic;  
    font-weight:bold;  
    border:0;  
    letter-spacing:2px;  
    color:blue;  
}
.rem_psw{background-color:#1194fc}
</style>

</head>
<body>
	<div class="top">
		<div class="inner">
			<span>江宁综合执法管理平台</span> <i>欢迎登录</i>
		</div>
	</div>
	<section>
	<div class="inner">
		<div class="login">
			<h2>账户登录</h2>
			<form method='post' id="loginFrm">
				<div class="userId user">
					<input type="text" placeholder="请输入登录名" id="txtUname" name="userName"/> 
						<span class='icon iconfont icon-icon09'></span> 
						<i class='icon iconfont icon-close' id="iClcUname" name="password"></i>
				</div>
				<div class="userPsw user">
					<input type="password" placeholder="请输入密码" id="txtPwd"/> <span
						class='icon iconfont icon-icon10'></span> <i
						class='icon iconfont icon-close' id="iClcPwd"></i>
				</div>
				<div class="yzBox">
					<div class="yzm" style="width: 50%;">
						<input type="text" id="input" value="" placeholder="验证码" />
					</div>
					<div class="yzpic">
						<input type = "button" id="code" onclick="createCode()"/>  
					</div>
					<!-- <a class="again" href="##"> 看不清<br /> 换一张 </a> -->
				</div>
				<div class="btns">
					<button type="button" id="btLogin">登录</button>
					<button type="button" id="btRest">重置</button>
				</div>
				<div class="localpsw">
					<div class="jzpsw">
						记住密码<span></span>
					</div>
					<div class="wzpsw">
						<a href='##'>忘记密码</a>?
					</div>
				</div>
			</form>

		</div>
	</div>
	</section>
	<div class="bottom">版权所有&copy;江宁综合执法管理平台</div>
</body>

<script>
	//确定整屏下 中间注册件的高度
	$('section').css('height', parseFloat($('body').height()) - 150);

	//登录验证
	function checkname() {
		var name = $("#txtUname").val();
		if (name == '') {
			$('#txtUname').val('账号不能为空');
			$('#txtUname').addClass('error')
			return false;
		}
		return true;
	}
	//验证密码
	function checkpwd() {
		var password = $("#txtPwd").val();
		if (password == '') {
			$('#txtPwd').val('密码不能为空');
			$('#txtPwd').addClass('error');
			return false;
		}
		return true;
	}
	//验证验证码
	function checkCode() {
		var myCode = $("#input").val();
		if (myCode == '') {
			$('#input').val('验证码不能为空!');
			$('#input').addClass('error');
			$('#input').focus(function(){
				$('#input').val('').removeClass("error").unbind('focus');
			});
			return false;
		}else if(myCode.toLowerCase()!=code.toLowerCase()){
			$('#input').val('验证码错误！');
			$('#input').addClass('error');
			$('#input').focus(function(){
				$('#input').val('').removeClass("error").unbind('focus');
			});
			return false;
		}
		return true;
	}
	
	function login(){
		if(checkname() && checkpwd() && checkCode()){
			$.ajax({
				url : "${pageContext.request.contextPath}/user/userLogin.action",
				data: {
					userName:$("#txtUname").val(),
					password:$("#txtPwd").val()
				},
				dataType : "json",
				method : "post",
				success : function(result){
					if(result.success){
					  if (groupTag=='9'){
                          window.location.href="${pageContext.request.contextPath}/page/issues/dcdbrw.jsp";
					  }else {
                          window.location.href="${pageContext.request.contextPath}/page/issues/dbrw.jsp";
					  }

					}else{
						alert("系统提示:"+result.errorInfo);
						return;
					}
				},
				error : function(err){
					console.log(err);
				}
			});
		}
	};

	function resetValue() {
		$("#txtUname").val("");
		$("#txtPwd").val("");
	}

	$(function() {
		$("#btRest").click(resetValue);
		$("#btLogin").click(login);

		//叉号清空input内容
		$('.user i').click(function() {
			$(this).parent().children('input').val("");
		});

		$('#txtUname').focus(function() {
			if ($(this).val() == '账号不能为空') {
				$(this).val("");
			};
			$('#txtUname').removeClass('error');
		});

		$('#txtPwd').focus(function() {
			if ($(this).val() == '账号不能为空') {
				$(this).val("");
			};
			$('#txtPwd').removeClass('error');
		});
	})
	
	//随机验证码
	var code;
    function createCode(){
        code = '';
        var codeLength = 4;
        var codeV = document.getElementById('code');
        var random = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R', 'S','T','U','V','W','X','Y','Z');
        for(var i = 0; i < codeLength; i++){
             var index = Math.floor(Math.random()*36);
             code += random[index]; 
        }
        codeV.value = code;
    }

    window.onload = function (){
        createCode();
    }
    
    //记住密码
    $('.jzpsw').click(function(){
    	$(this).children('span').toggleClass('rem_psw');
    });
    $('#btLogin').click(function(){
    	if($('.jzpsw').children('span').hasClass('rem_psw')){
    		console.log('csa');
    		if(localStorage.id){
    			console.log('更改');
    			localStorage.setItem('jncs_id',$("#txtUname").val());
    			localStorage.setItem('jncs_psw',$("#txtPwd").val());
    			localStorage.setItem('choose',true);
    		}
    		else{
    			console.log('创建');
    			console.log(localStorage);
    			localStorage.jncs_id =$("#txtUname").val();
    			localStorage.jncs_psw =$("#txtPwd").val();
    			localStorage.choose=true;
    		}
    	}
    	else{
    		if($("#txtUname").val()==localStorage.jncs_id){
    			localStorage.removeItem("jncs_id");
    			localStorage.removeItem("jncs_psw");
    			localStorage.removeItem("choose");
    		}
    	}
    })
    if(localStorage.jncs_id){
    	$("#txtUname").val(localStorage.jncs_id);
    	$("#txtPwd").val(localStorage.jncs_psw);
    };
    if(localStorage.choose){
    	$('.jzpsw').children('span').addClass('rem_psw');
    }
</script>
</html>