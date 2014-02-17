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
<title>比对详情查看</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		var hiddenAllLocusNameStr = $("#hiddenAllLocusNameStr").val();
		var hiddenQualityGeneInfo = $("#hiddenQualityGeneInfo").val();
		var hiddenMatchGeneInfo = $("#hiddenMatchGeneInfo").val();

		var locusNameArr = hiddenAllLocusNameStr.split(";");
		var srcGeneInfoArr = hiddenQualityGeneInfo.split(";");
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
		$("#geneInfoDetailTbl").append(innerHtmlStr);

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
<div class="main" style="overflow:hidden;">
  <div class="box_three">
		<fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>

	      <input type="hidden" id="hiddenQualityGeneInfo" value="<s:property value="qualitySampleGeneStr"/>"/>
	      <input type="hidden" id="hiddenMatchGeneInfo" value="<s:property value="matchSampleGeneStr"/>"/>
	      <input type="hidden" id="hiddenAllLocusNameStr" value="<s:property value="allLocusNameStr"/>"/>

			<table id="geneInfoDetailTbl" class="table_one width2">
				<tr>
					<th>&nbsp;</th>
					<th>质检样本</th>
					<th>比对样本</th>
				</tr>
			</table>
		    <div class="height_one"></div>
	    </fieldset>
  </div>
</div>
</body>
</html>
