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
<title>样本Excel导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#importFileBtn").click(function(){

			var codisFileTxt = $("#sampleExcelFile").val();
			if(codisFileTxt == ""){
				alert("请选择Excel文件！");
				return;
			}

			$("#submitSampleExcelImportForm").submit();
		});

	});


	function backFtn(){
		document.location.href="<%=path%>/pages/sample/queryConsignment.action";
	}
</script>
<style>
  html,body{
	  overflow:hidden;
	  width:100%;
	  height:100%;
  }
</style>

</head>

<body>
<div class="main">
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 样本Excel导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>样本Excel导入</legend>
      <div class="height_one"></div>
      <s:form action="submitSampleExcelImport" name="submitSampleExcelImportForm" id="submitSampleExcelImportForm"
      	method="post" enctype="multipart/form-data">
      		<s:hidden name="consignment.id"/>
	      <table class="table_two width2 floatL marginL1">

	        <tr>
	          <td align="right">
	          	选择样本Excel文件：
	          </td>
	          <td align="left">
	          	<s:file name="sampleExcelFile" id="sampleExcelFile" cssClass="file_one width4" />
	          	&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/>
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          	<input type="button" value="返   回" class="button_one" onclick="backFtn();"/>
	          </td>
	        </tr>

	      </table>
      </s:form>
    </fieldset>
  </div>
</div>
</body>
</html>
