	function showMapLocate(){
		window.open("../ditu/ditu.jsp?x="+x+"&y="+y ,"_blank" ,"height=600,width=960,scrollbars=no,location=no,titlebar=no,menubar=no,status=no,toolbar=no" ) ;
	}
	
	function initSelectList(selectid, mc){
		$.ajax({
			type : "post",
			url : "../../issuetypezd/selectZd",
			data : {
				mc : mc,
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
						$("#" + selectid).append("<option value='"+data1[i].bh+"'>"+ data1[i].ms + "</option>");
					}
				}
			}
		});
	}
	
	function selectSonList(selectid, sssjbh, mc){
		$.ajax({
			type : "post",
			url : "../../issuetypezd/selectSonZd",
			data : {
				mc : mc,
				sssjbh : sssjbh
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
						$("#" + selectid).append("<option value='"+data1[i].bh+"'>"+ data1[i].ms + "</option>");
					}
				}
			}
		});
	}
	
	function selectNext(selectid, bh, mc) {
		if (bh == '' || mc == '') return false;
		var mingcheng = "";
		if(issueClass == 1){
			mingcheng = "q" + mc;
		}else if(issueClass==2){
			mingcheng = "c" + mc;
		}
		selectSonList(selectid, bh, mingcheng);
	}
	/**
	function openTab(text,url,iconCls){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/page/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				iconCls:iconCls,
				closable:true,
				content:content
			});
		}
	} */