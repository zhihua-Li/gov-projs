(function($){

	function findExactPageData(page, pagesize){
		$("#pageIndex").val(page);
		$("#pageSize").val(pagesize);
		$("#pageSubmit").trigger("click");
	}

	$(document).ready(function(){
		var pagesize = null;
		if($("#pageSize").val()!=null){
			pagesize = Number($("#pageSize").val().replace(",",""));
		}else{
			pagesize = "";
		}
		var maxCount = 0;
		if($("#recordCount").val()!=null && $("#recordCount").val()!='null'){
			maxCount = Number($("#recordCount").val().replace(",",""));
		}
		var pageIndex = null;
		if($("#pageIndex").val()!=null){
			pageIndex = Number($("#pageIndex").val().replace(",",""));
		}else{
			pageIndex = "";
		}

		if(maxCount == 0){
			$("#pageFooterDiv").css("display", "none");
		}else{
			$("#pageFooterDiv").pagefoot({
				pagesize:pagesize,
				count:maxCount,
				current:pageIndex,
				displaynum:3,
				displaylastNum:2,
				css:"mj_pagefoot_green",
				paging:function(page, pagesize){
					findExactPageData(page, pagesize);
				}
			});

			$("#pageFooterDiv").attr("style","display:''");
	    }
	});
})(jQuery)