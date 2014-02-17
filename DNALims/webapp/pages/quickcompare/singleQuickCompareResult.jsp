<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>检验记录管理</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	function viewMatchedDetails(obj){
		var hiddenAllLocusNameStr = $("#hiddenAllLocusNameStr").val();
		var hiddenSrcGeneInfo = $("#hiddenSrcGeneInfo").val();
		var hiddenMatchGeneInfo = $("input[name='hiddenMatchGeneInfo']", $(obj).parent()).val();

		var locusNameArr = hiddenAllLocusNameStr.split(";");
		var srcGeneInfoArr = hiddenSrcGeneInfo.split(";");
		var matchGeneInfoArr = hiddenMatchGeneInfo.split(";");

		var innerHtmlStr = "";
		for(var i = 0; i < locusNameArr.length; i++){
			innerHtmlStr += "<tr>";
			innerHtmlStr += "<th>" + locusNameArr[i] + "</th>";

			if(srcGeneInfoArr[i] != matchGeneInfoArr[i]
				&& (srcGeneInfoArr[i] != "//" && matchGeneInfoArr[i] != "//")){
				innerHtmlStr += "<td><span style='color:red;'>" + srcGeneInfoArr[i] + "</span></td>";
				innerHtmlStr += "<td><span style='color:red;'>" + matchGeneInfoArr[i] + "</span></td>";
			}else{
				innerHtmlStr += "<td>" + srcGeneInfoArr[i] + "</td>";
				innerHtmlStr += "<td>" + matchGeneInfoArr[i] + "</td>";
			}

			innerHtmlStr += "</tr>";
		}

		$("table.table_one", "#dialogDiv").append(innerHtmlStr);
		$("#dialogDiv").dialog({
			autoOpen:true,
			height:480,
			width:600,
			modal:true,
			title:"查看比中详情",
			buttons:{
				"关  闭":function(){
					$(this).dialog("close");
				}
			},
			close: function(){
				$(this).dialog("close");
			}
		});
	}

	$(function(){
		if($("tr", "#tbody").length == 0){
			var htmlStr = "<tr><td colspan='7' style='color:red;'>无比中！</td></tr>"
				$("#tbody").html(htmlStr);
		}
	});

</script>
<style type="text/css">
  html,body{
	  overflow:hidden;
	  width:100%;
	  height:100%;
  }
</style>

</head>

<body>
<div class="main">
  <div class="posittions"> 当前位置：快速比对  &gt;&gt; 单个样本比对</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>比中样本列表</legend>
      <input type="hidden" id="hiddenSrcGeneInfo" value="<s:property value="inputGeneInfoStr"/>"/>
      <input type="hidden" id="hiddenAllLocusNameStr" value="<s:property value="allLocusNameStr"/>"/>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>委托号</th>
          <th>委托单位</th>
          <th>样本编号</th>
          <th>比中基因座个数</th>
          <th>差异基因座个数</th>
          <th>查看详情</th>
        </tr>
      </thead>
      <tbody id="tbody">
        <s:iterator value="compareResultList" status="su">
			<tr>
				<td><s:property value="#su.index+1" /></td>
				<td><s:property value="matchConsignmentNo" /></td>
				<td><s:property value="matchConsignOrgName" /></td>
				<td><s:property value="matchSampleNo" /></td>
				<td><s:property value="sameNum" /></td>
				<td><s:property value="diffNum" /></td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="viewMatchedDetails(this);"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
					<input type="hidden" name="hiddenMatchGeneInfo" value="<s:property value="matchGeneInfo"/>"/>
				</td>
			</tr>
     	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>

    <div id="dialogDiv" style='display:none;'>
		<table class="table_one">
			<tr>
				<th>基因座</th>
				<th>排查样本基因分型</th>
				<th>比中样本基因分型</th>
			</tr>
		</table>
    </div>

    </fieldset>
  </div>
</div>
</body>
</html>
