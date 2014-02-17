$(function(){
	//header nav,sidebar ul
	$('.nav li').eq(0).addClass("click");
	$('.sidebar ul li').eq(0).addClass("click");
	$('.nav li,.sidebar ul li').click(function(){
		$(this).addClass("click").siblings().removeClass("click")
	});
	//fieldset 显示隐藏
	$('.fieldset_one legend').click(function(){
		if($(this).nextAll().css('display') == 'none'){
			$(this).nextAll().show();
			$(this).removeClass('show');
		}else{
			$(this).nextAll().hide();
			$(this).addClass('show');
		};
	});
	$('.dishow').hide();
	$('.fids input').click(function(){
		$('.dishow').show();
	});
});
function autoIframeHeight(){ 
	var bodyHeight = $(document.body).height();
	var bodyWidth = $(document.body).width();
	$('#mainerif').height(bodyHeight - 95);
	$('.box_two').width(bodyWidth - 200);
	$('.box_three').height(bodyHeight - 32);
} 
$(function(){ 
	autoIframeHeight(); 
}); 
$(window).resize(function(){ 
	autoIframeHeight(); 
});
