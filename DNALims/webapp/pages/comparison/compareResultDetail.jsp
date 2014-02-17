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
<title>比中详情查看</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		var hiddenAllLocusNameStr = $("#hiddenAllLocusNameStr").val();
		var hiddenSrcGeneInfo = $("#hiddenSrcGeneInfo").val();
		var hiddenMatchGeneInfo = $("#hiddenMatchGeneInfo").val();

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
		$("#geneInfoDetailTbl").append(innerHtmlStr);

		$("#backBtn").live("click", function(){
			location.href="<%=path %>/pages/compare/queryCompareResult.action";
		});

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
    	<s:hidden name="compareResult.id"/>" />
	    <fieldset class="fieldset_one">
	      <legend>比中信息</legend>
	      <div class="height_one"></div>
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>比中时间：</td>
	          <td colspan="3">
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:date name="compareResult.matchDate" format="yyyy-MM-dd HH:mm"/>" />
	          </td>
	        </tr>
	        <tr>
	          <td>比中基因座个数：</td>
	          <td>
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:property value="compareResult.sameNum"/>" />
	          </td>
	          <td>差异基因座个数：</td>
	          <td>
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:property value="compareResult.diffNum"/>" />
	          </td>
	        </tr>
	        <tr>
	        	<td>备     注：</td>
	        	<td colspan="2">
					<textarea class="textarea_width300" rows="3"><s:property value="compareResult.matchDesc"/></textarea>
	        	</td>
	        	<td align="right">
					<input type="button" class="button_one" id="backBtn" value="返   回" />
	        	</td>
	        </tr>
	      </table>
	    </fieldset>
		<fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>


	      <input type="hidden" id="hiddenSrcGeneInfo" value="<s:property value="sourceGeneInfoView.genotypeInfo"/>"/>
	      <input type="hidden" id="hiddenMatchGeneInfo" value="<s:property value="matchGeneInfoView.genotypeInfo"/>"/>
	      <input type="hidden" id="hiddenAllLocusNameStr" value="<s:property value="allLocusNameStr"/>"/>

			<table id="geneInfoDetailTbl" class="table_one width2">
				<tr>
					<th>&nbsp;</th>
					<th>样本1</td>
					<th>样本2</td>
				</tr>
				<tr>
					<th>样本编号</th>
					<td><s:property value="sourceGeneInfoView.sampleNo"/></td>
					<td><s:property value="matchGeneInfoView.sampleNo"/></td>
				</tr>
				<tr>
					<th>试剂盒</th>
					<td><s:property value="sourceGeneInfoView.reagentKitName"/></td>
					<td><s:property value="matchGeneInfoView.reagentKitName"/></td>
				</tr>
			</table>
		    <div class="height_one"></div>
	    </fieldset>
  </div>
</div>
</body>
</html>
