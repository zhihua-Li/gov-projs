$(function(){
  $(".navers .naver").eq(0).show().siblings().hide();	
  $('.nav li a').click(function(){
	 $(this).addClass('hovers').parent().siblings().children().removeClass('hovers');
	 var index=$('.nav li a').index(this);
	 $(".navers .naver").eq(index).show().siblings().hide();
	 $('.kkl').eq(index).children().first().children().addClass('ahovers').parent().siblings().children().removeClass('ahovers');
	 $('.kkl').eq(0).children().first().children().removeClass('ahovers');
  })
  $('.nav_side li a').css({background:'none',color:'#fff',padding:'0',fontWeight:'600'});
  $('.naver a').click(function(){
	 $(this).addClass('ahovers').parent().siblings().children().removeClass('ahovers');
  })
  $(".sidebar_ul_ul li a").click(function(){
	 $(this).addClass('hoversas').parent().siblings().children().removeClass('hoversas');
  })
  $('.titles_a a').click(function(){
	 $(this).addClass('hovers').siblings().removeClass('hovers');
  })
})