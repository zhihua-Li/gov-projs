$(function(){
	$('.sidebar_ul_ul li a').hover(function(){
	   $(this).animate({paddingLeft: '+12px'},200);
    },function(){
	   $(this).animate({paddingLeft:'5px'},200);
    });	
	$('.titles_a a').first().addClass('hovers_a');
	$('.titles_a a').click(function(){
	   $(this).addClass('hovers_a').siblings().removeClass('hovers_a');
    });
	$('.disnone').hide();
	$('.wins a').click(function(){
	   $('.disnone').show();
	   $('.footer').css({right:'0'});
	});
	$('.close').click(function(){
		$('.eject').hide();
		$('.windows').hide();	
	});
	$('.table_texts').focus(function(){
		$(this).addClass('boxSha');
	});
	$('.table_texts').blur(function(){
		$(this).removeClass('boxSha');	
	});
	$('.table_two .table_buttons').hover(function(){
		$(this).addClass('blue');
	},function(){
		$(this).removeClass('blue');
	});
	$('.pages_ul li').first().hover(function(){
		$(this).children().addClass('blues');
	},function(){
		$(this).children().removeClass('blues');
	});
	$('.pages_ul li').last().hover(function(){
		$(this).children().addClass('blues');
	},function(){
		$(this).children().removeClass('blues');
	});
	$('.table_bg tr').hover(function(){
		$(this).addClass('tr_bgs');
	},function(){
		$(this).removeClass('tr_bgs');
	});
});

