$('table').click(function(ev){
	var e = e || window.event;                    //处理兼容性  
	var td = e.target || e.srcElement;
	console.log(td);
	$(td).parent().addClass('currentTr').siblings().removeClass('currentTr');
})

