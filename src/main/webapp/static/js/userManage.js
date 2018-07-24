$(function() {
	initQhzdSelect("jiedao", "", "1", "");
});

	function searchUser(){
		$("#dg").datagrid('load',{
			"id":$("#s_id").val()
		});
	}
	
	function resetPassword(){
		var selectRows=$("#dg").datagrid("getSelections");
		if(selectRows.length!=1){
			$.messager.alert("系统提示","请选择一条要重置密码的数据！");
			return;
		}
		var row=selectRows[0];alert(row.id);
		$("#dlgresetpasswd").dialog("open").dialog("setTitle","重置密码");
		$("#resetPwdId").val(row.id);
		$("#resetPwdId").attr("readonly",true);
	}
	
	function closeResetPwdDialog(){
		$("#dlgresetpasswd").dialog("close");
		$("#resetPwdId").val("");
		$("#reResetPassword").val("");
		$("#resetPassword").val("");
	}
	
	function checkResetPwdData(){
		var id = $("#resetPwdId").val();
		var reResetPassword = $("#reResetPassword").val();
		var resetPassword = $("#resetPassword").val();
		if (id == "" || id == null){
			alert("要修改的用户错误，请重试");
			return;
		}
		if (resetPassword == null || resetPassword == ""){
			alert("新密码不能为空");
			return;
		}
		if (reResetPassword == null || reResetPassword == ""){
			alert("重复密码不能为空");
			return;
		}
		if (reResetPassword != resetPassword){
			alert("两次输入的密码不一致，请重新输入");
			return;
		}
		$("#resetpwdfm").form("submit",{
			url:'../../user/userResetPassword.action',
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统系统","保存成功！");
					resetValue();
					$("#dlgresetpasswd").dialog("close");
					$("#resetPwdId").val("");
					$("#reResetPassword").val("");
					$("#resetPassword").val("");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统系统","保存失败！");
					return;
				}
			}
		});
	}
	
	function deleteUser(){
		var selectRows=$("#dg").datagrid("getSelections");
		if(selectRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectRows.length;i++){
			strIds.push(selectRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectRows.length+"</font>条数据吗?",function(r){
			if(r){
				$.post("../../user/deleteUser.action",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已经成功删除！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","数据删除失败，请联系管理员！");
					}
				},"json");
			}
		});
	}
	
	
	function openUserAddDiglog(){
		$("#dlg").dialog("open").dialog("setTitle","添加用户信息");
		$("#flag").val(1);
		$("#id").attr("readonly",false);
	}
	
	function openUserModifyDiglog(){
		var selectRows=$("#dg").datagrid("getSelections");
		if(selectRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectRows[0];
		var orgNumber = row.orgNumber;
		$("#dlg").dialog("open").dialog("setTitle","编辑用户信息");
		$("#fm").form("load",row);
		$("#flag").val(2);
		$("#password").val("");
		$("#passwordTd").css('display','none'); 
		$("#passwordTdInput").css('display', 'none');
		$("#id").attr("readonly",true);
		var ssqhdm = "";
		$.ajax({
			type : "post",
			url : "../../qhzd/selectQhzd",
			data : {
				qhdm : orgNumber,
				qhjb : 2,
				ssqhdm : ""
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (null == data) {
					alert("服务器异常，请重试！");
				} else {
					var data1 = data.rows;
					//获取上级区划代码
					ssqhdm = data1[0].ssqhdm;
				}
			}
		});
		$("#jiedao").val(ssqhdm);
		selectNext("shequ", ssqhdm);
		$("#shequ").val(orgNumber);
	}
	
	
	function checkData(){
		if($("#id").val()==''){
			$.messager.alert("系统提示","请输入用户名！");
			$("#id").focus();
			return;
		}
		var flag=$("#flag").val();
		if($("#password").val()=='' && flag == 1){
			$.messager.alert("系统提示","请输入密码！");
			$("#password").focus();
			return;
		}
		
		//如果是街道或者社区领导，需要判断地址选择框是否为空
		var userTypeID = $("#userTypeID").val();
		if (userTypeID == 5){
			//街道领导
			var jiedao = $("#jiedao").val();
			if (jiedao == null || jiedao == ""){
				alert("请选择所属街办");
				return;
			}
		}
		
		
		if (userTypeID == 4 || userTypeID == 1){
			//社区领导
			var jiedao = $("#jiedao").val();
			if (jiedao == null || jiedao == ""){
				alert("请选择所属街办");
				return;
			}
			
			var shequ = $("#shequ").val();
			if (shequ == null || shequ == ""){
				alert("请选择所属社区");
				return;
			}
		}

		if(flag==1){
			$.post("../../user/existUserName.do",{userid:$("#id").val()},function(result){
				if(result.exist){
					$.messager.alert("系统提示","该用户名已存在，请更换下！");
					$("#id").focus();
				}else{
					saveUser();
				}
			},"json");
		}else{
			updateUser();
		}
	}
	
	function updateUser(){
		$("#fm").form("submit",{
			url:'../../user/userUpdate.action',
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统系统","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统系统","保存失败！");
					return;
				}
			}
		});
	}
	
	function saveUser(){
		$("#fm").form("submit",{
			url:'../../user/userSave.action',
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统系统","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统系统","保存失败！");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#id").val("");
		$("#password").val("");
		$("#userName").val("");
		$("#tel").val("");
		$("#email").val("");
	}
	
	function closeUserDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}

	
	function initQhzdSelect(selectid, qhdm, qhjb,ssqhdm) {
		if (qhdm == '' && qhjb == '') return false;
		$.ajax({
			type : "post",
			url : "../../qhzd/selectQhzd",
			data : {
				qhdm : qhdm,
				qhjb : qhjb,
				ssqhdm : ssqhdm
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (null == data) {
				} else {
					var data1 = data.rows;
					$("#" + selectid).empty();
					$("#" + selectid).append("<option value=''>--请选择--</option>");
					for (var i = 0; i < data1.length; i++) {
						$("#" + selectid).append("<option value='"+data1[i].qhdm+"'>"+ data1[i].qhmc + "</option>");
					}
				}
			}
		});
	}

	function selectNext(selectid, qhdm) {
		if (qhdm == '') return false;
		initQhzdSelect(selectid, "", 2, qhdm);
	}