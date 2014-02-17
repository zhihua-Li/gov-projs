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
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#geneInfoTbl").append('<s:property escape="0" value="sampleSourceGeneView.geneInfoHtmlForPage"/>');

		$("#clostBtn").live("click", function(){
			window.parent.$("#resultDetailDiv").dialog("close");
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
	    <fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>
	      	<table class="table_two width2" id="geneInfoTbl">
		        <tr>
		          <td>试剂盒:</td>
		          <td>
	          		<s:select name="sampleSourceGene.reagentKitId" id="reagentKitId" list="reagentKitList" listKey="id" listValue="reagentName"
		          		headerKey="" headerValue="" cssClass="select_one" disabled="true">
		          		<s:param name="value">
		          			<s:property value="sampleSourceGeneView.reagentKitId"/>
		          		</s:param>
		          	</s:select>
		          </td>
		          <td colspan="2">
					<input type="button" class="button_one" id="clostBtn" value="关   闭" />
		          </td>
		        </tr>
	      	</table>
		    <div class="height_one"></div>
	    </fieldset>
  </div>
</div>
</body>
</html>
