$(function(){
			var aside_h=parseFloat($('.nav-list').css('height'));
				if( aside_h+60>$('aside').height()){
					$('.nav-list').css('box-shadow','2px -6px 6px #aaa')
					$('aside').css('box-shadow','none')
				}else{
					$('.nav-list').css('box-shadow','none')
					$('aside').css('box-shadow','2px -6px 6px #aaa')
				}

			
			//使右边的内容区与左面导航栏等高 ，都是一整屏
			function setFullpage(){
				$('section').css('height',parseFloat($('aside').css('height'))-60);
//				console.log(parseFloat($('aside').css('height'))-60);
				$('.content').css('height',parseFloat($('section').css('height'))-90);
				$('.tbody').css('height',parseFloat($('.content').css('height'))-170);
				$('.thead table').css('width',parseFloat($('.tbody table tr').css('width')))
			};
			setFullpage();
			
			
	    	//checkbox点击筛选功能
		    var checkboxNum=$('.thead thead tr th').length;
		    var text='';
		    for(var i=0;i<checkboxNum;i++){
				text=$('.thead thead tr th').eq(i).text();
				var checkHtml='<div class="checkbox">'+'<label>'+'<input type="checkbox">'+text+'</label>'+'</div>'
//				console.log(text);
				$('.shaixuan').append(checkHtml);
		    }
		    $('.shaixuan .checkbox input').attr('checked',true);
		    $('.btn_group button').eq(2).click(function(){
		    	$('.shaixuan').slideToggle(300);
		    })
		    $('.shaixuan .checkbox input').click(function(){
		    	var _this=this;
				if(!this.checked){
					$('.thead thead tr th').eq($(this).parent().parent().index()).css('display','none');
//					console.log($(this).parent().parent().index());
					$('.tbody tbody tr').each(function(){
						
						$(this).children('td').eq($(_this).parent().parent().index()).css('display','none');
					})
				}
				else{
					$('.thead thead tr th').eq($(this).parent().parent().index()).css('display','table-cell');
					$('.tbody tbody tr').each(function(){
						$(this).children('td').eq($(_this).parent().parent().index()).css('display','table-cell');
					})
				}	
		    })
		    
		    $('.thead thead tr th').each(function(index,dom){
		    	$(this).css('width',$('.tbody tbody tr').children().eq(index).css('width'));
		    })
		    
		$.fn.extend({
              // 设置 apDiv
            center:function () {
            //apDiv浮动层显示位置居中控制
            
	            $(this).css({
	            	'position':'absolute',
	            	'z-index':2000
	            })
	            $(this).toggleClass('show')
	            var wheight=$(window).height();
	            var wwidth=$(window).width();
	            console.log($(this).height());
	            var apHeight=wheight-$(this).height();
	            var apWidth=wwidth-$(this).width();
	            $(this).css("top",apHeight/2);
	            $(this).css("left",apWidth/2);
	        },
	        sureTr:function(e){
	        	var e = e || window.event;                    //处理兼容性  
	        	var td = e.target || e.srcElement;
				console.log(td);
				$(td).parent().addClass('currentTr').siblings().removeClass('currentTr');
	        }
        });       
			
})
		
		