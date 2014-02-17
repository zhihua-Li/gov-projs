/*
******生成js分页脚******
****没剑(2008-03-05)****
修改日期：2008-3-12
添加两个参数：displaynum，displaylastNum可以自由定制显示的页码数量

参数：  pagesize:10  //每页显示的页码数
        ,count:0                //数据条数
        ,css:"mj_pagefoot"      //分页脚css样式类
        ,current:1              //当前页码
		,displaynum:7			//中间显示页码数
		,displaylastNum:5		//最后显示的页码数
        ,previous:"上一页"      //上一页显示样式
        ,next:"下一页"          //下一页显示样式
        ,paging:null            //分页事件触发时callback函数

使用：
	$("div").pagefoot({
	    pagesize:10,
	    count:500,
	    css:"mj_pagefoot",
	    previous:"<",
	    next:">",
	    paging:function(page){
			    alert("当前第"+page+"页");
		    }
	});
	以上代码为所有div加上分页脚代码
*/
jQuery.pagefoot =
{
    //生成分页脚
    create:function(_this,s){
        var pageCount=0;
        //计算总页码
        pageCount=(s.count/s.pagesize<=0)?1:(parseInt(s.count/s.pagesize)+((s.count%s.pagesize>0)?1:0));
        s.current=(s.current>pageCount)?pageCount:s.current;
        //循环生成页码
        var strPage="";
        //创建上一页
        if(s.current<=1){
        	strPage+="<span style=\"float:left;margin-left:20px;FONT-SIZE: 9pt;\">共 <b style=\"color:#0061DE\">"+s.count+" </b>条  		</span><span style=\"float:left; margin-left:10px; font-size:9pt;\">每页显示</span><select style=\"float:left; margin-left:10px; FONT-SIZE: 9pt;\"><option value=\"15\">15</option><option value=\"30\">30</option><option value=\"50\">50</option><option value=\"100\">100</option></select> <span style=\"float:left; margin-left:10px; font-size:9pt;\">  条</span></span><br><div align='center'>";
//            strPage+="<span class=\"disabled\"'>"+s.previous+"</span>";
        }else{
        	strPage+="<span style=\"float:left;margin-left:20px;FONT-SIZE: 9pt;\">共 <b style=\"color:#0061DE\">"+s.count+" </b>条 	 	</span><span style=\"float:left; margin-left:10px; font-size:9pt;\">每页显示</span><select style=\"float:left; margin-left:10px; FONT-SIZE: 9pt;\"><option value=\"15\">15</option><option value=\"30\">30</option><option value=\"50\">50</option><option value=\"100\">100</option></select> <span style=\"float:left; margin-left:10px; font-size:9pt;\">  条</span></span><br><div align='center'>";
            strPage+="<a href=\""+(s.current-1)+"\">"+s.previous+"</a>";
        }
        //开始的页码
        var startP=1;
        startP=1;
		var anyMore;//页码左右显示最大页码数
		anyMore=parseInt(s.displaynum/2);
        //结束的页码
        var endP=(s.current+anyMore)>pageCount?pageCount:s.current+anyMore;

        //可显示的码码数(剩N个用来显示最后N页的页码)
        var pCount=s.pagesize-s.displaylastNum;
        if(s.current>s.displaynum){//页码数太多时，则隐藏多余的页码
            startP=s.current-anyMore;
            for(i=1;i<=s.displaylastNum;i++){
                    strPage+="<a href=\""+i+"\" style='margin-left:5px;'>"+i+"</a>";
            }
            strPage+="...";
        }
        if(s.current+s.displaynum<=pageCount){//页码数太多时，则隐藏前面多余的页码
            endP=s.current+anyMore;
        }else{
            endP=pageCount;
        }
        for(i=startP;i<=endP;i++){
            if(s.current==i){
                strPage+="<span class=\"current\" style='margin-left:5px;'>"+i+"</span>";
            }else{
                strPage+="<a href=\""+i+"\" style='margin-left:5px;'>"+i+"</a>";
            }
        }
        if(s.current+s.displaynum<=pageCount){//页码数太多时，则隐藏后面多余的页码
            strPage+="...";
            for(i=pageCount-s.displaylastNum+1;i<=pageCount;i++){
                    strPage+="<a href=\""+i+"\" style='margin-left:5px;'>"+i+"</a>";
            }
        }
        //创建下一页
        if(s.current>=pageCount){
//            strPage+="<span class=\"disabled\" style='margin-left:5px; margin-right:5px;'>"+s.next+"</span>";
//            strPage+="<font style=\"FONT-SIZE: 9pt;color:#0061DE\">跳转至第 <input type='text' class='table_texts' id='pageRedirect' size='1' /> 页&nbsp;&nbsp;<button class='table_buttons center_buttons' id='go' onclick='pageRedirect()'>Go</button></font>";
        }else{
            strPage+="<a href=\""+(s.current+1)+"\" style='margin-left:5px; margin-right:5px;'>"+s.next+"</a>";
            strPage+="<font style=\"FONT-SIZE: 9pt;color:#0061DE\">跳转至第 <input type='text' class='table_texts' id='pageRedirect' size='1' /> 页&nbsp;&nbsp;<button class='table_buttons center_buttons' id='go' onclick='pageRedirect()'>Go</button></font>";
        }

        strPage += "</div>";

        $(_this).empty().append(strPage).find("a").click(function(){
            //得到翻页的页码
            var ln=this.href.lastIndexOf("/");
            var href=this.href;
            var page=parseInt(href.substring(ln+1,href.length));
            s.current=page;
            //外部取消翻页时...
            if(!$.pagefoot.paging(page, s.pagesize, s.paging))
            return false;

            $.pagefoot.create(_this,s);
            return false;
        });

        $(_this).find("select").eq(0).children("option").each(function(idx){
        	if($(this).val() == s.pagesize){
        		$(this).attr("selected", true);
        	}
        });

        $(_this).find("select").change(function(){
        	var selectVal = $("option:selected", $(this)).val();
        	s.pagesize = selectVal;
        	//外部取消翻页时...
            if(!$.pagefoot.paging(s.current, s.pagesize, s.paging))
            return false;

            $.pagefoot.create(_this,s);
            return false;
        });
        return this;
    },
    paging:function(page, pagesize, callback){
        if(callback){
            if(callback(page, pagesize)==false)
            return false;
        }
        return true;
    }
}

jQuery.fn.pagefoot= function(opt)
{
	/*参数定义*/
    var setting = {pagesize:10  //每页显示的页码数
        ,count:0                //数据条数
        ,css:"mj_pagefoot"      //分页脚css样式类
        ,current:1              //当前页码
		,displaynum:7			//中间显示页码数
		,displaylastNum:5		//最后显示的页码数
        ,previous:"上一页"      //上一页显示样式
        ,next:"下一页"          //下一页显示样式
        ,paging:null            //分页事件触发时callback函数
	};
	opt= opt || {}
	$.extend(setting, opt);
    return this.each(function(){
        $(this).addClass(setting.css);
        $.pagefoot.create(this,setting);
    });
}

function pageRedirect() {
	var num = /^[0-9]\d*$/;
	var total = document.getElementById("recordCount").value.replace(',','');
	var redirect = document.getElementById("pageRedirect").value;
	//alert(redirect);
	var totalCount = document.getElementById("pageSize").value.replace(',','');
	if (num.test(redirect)){
		if (redirect == 0) {
			$("#pageIndex").val(1);
		} else {
			if (redirect < (total/totalCount)) {
				$("#pageIndex").val(redirect);
			} else {
				if ((total/totalCount) > Math.round(total/totalCount))
					$("#pageIndex").val(Math.round(total/totalCount)+1);
				else
					$("#pageIndex").val(Math.round(total/totalCount));
			}
		}
		$("#pageSubmit").trigger("click");
	}
}

//获取 url参数
  function getQueryString(queryStringName)
	{
	 var returnValue="";
	 var URLString=new String(location.href);
	 var serachLocation=-1;
	 var queryStringLength=queryStringName.length;
	 do
	 {
	  serachLocation=URLString.indexOf(queryStringName+"\=");

	  if (serachLocation!=-1)
	  {
	   if ((URLString.charAt(serachLocation-1)=='?') || (URLString.charAt(serachLocation-1)=='&'))
	   {
	    URLString=URLString.substr(serachLocation);
	    break;
	   }
	   URLString=URLString.substr(serachLocation+queryStringLength+1);
	  }

	 }
	 while (serachLocation!=-1)
	 if (serachLocation!=-1)
	 {
	  var seperatorLocation=URLString.indexOf("&");
	  if (seperatorLocation==-1)
	  {
	   returnValue=URLString.substr(queryStringLength+1);
	  }
	  else
	  {
	   returnValue=URLString.substring(queryStringLength+1,seperatorLocation);
	  }
	 }
	 return returnValue;
}