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
<title>CODIS导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>

<script type="text/javascript">
function downloadFileUploader(){
	document.location.href="<%=path%>/pages/file/downloadFileUploader.action";
}
</script>
<%--
<script type="text/javascript">
	//$(document).ready(function(){
		//$("#importFileBtn").live("click", function(){
			//var consignOrganizationIdVal = $("#consignOrganizationId").val();
			//if(consignOrganizationIdVal == ""){
			//	alert("请选择委托单位！");
			//	return;
			//}
			//var codisFileTxt = $("#codisFile").val();
			//if(codisFileTxt == ""){
			//	alert("请选择CODIS文件！");
			//	return;
			//}
			//$("#submitBatchCodisImportForm").submit();
		//});
	//});
</script>
 --%>
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
  <div class="posittions"> 当前位置：入库比对管理 &gt;&gt; CODIS样本批量导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>CODIS样本批量导入</legend>
      <div class="height_one"></div>
	      <table class="table_two width3 floatL marginL1">
	      	<tr>
	          <td>说明：</td>
	          <td>CODIS样本批量导入功能已转移至客户端工具中实现。</td>
	        </tr>
	        <tr>
	          <td>工具名称：</td>
	          <td>文件上传及CODIS导入工具</td>
	        </tr>
	        <tr>
	          <td>工具描述：</td>
	          <td>提供文件批量上传及CODIS批量导入功能。</td>
	        </tr>
	        <tr>
	          <td>工具启动文件：</td>
	          <td>双击“启动文件上传工具.bat”启动该工具。</td>
	        </tr>
	        <tr>
	        	<td>版本：</td>
	        	<td>v2014.02.17</td>
	        </tr>
	        <tr>
	          <td>下载地址：</td>
	          <td colspan="3">
				<button class="button_one" onclick="downloadFileUploader();"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="22" height="22" style="vertical-align:middle;" alt="点此下载" title="点此下载"/>点此下载</button>
	          </td>
	        </tr>
	      </table>
    </fieldset>

<%--
    <fieldset class="fieldset_one">
      <legend>CODIS样本批量导入</legend>
      <div class="height_one"></div>
      <s:form action="submitBatchCodisImport" name="submitBatchCodisImportForm" id="submitBatchCodisImportForm"
      	method="post" enctype="multipart/form-data">
	      <table class="table_two width2 floatL marginL1">
	      	<tr>
	      		<td align="right">
		          	委托单位<span style="color:red;">*</span>：
		        </td>
		        <td align="left">
		      		<s:select name="consignOrganizationId" id="consignOrganizationId" cssClass="select_one" list="consignOrganizationList" listKey="id" listValue="organizationName" headerKey="" headerValue="请选择..."/>
	      		</td>
	      	</tr>
			<tr>
				<td align="right">
					试剂盒<span style="color:red;">*</span>：
				</td>
				<td align="left">
					<s:select list="reagentKitList" name="reagentKitId" listKey="id" listValue="reagentName" cssClass="width5"></s:select>
				</td>
			</tr>
	        <tr>
	          <td align="right">
	          	CODIS文件：
	          </td>
	          <td align="left">
	          	<s:file name="codisFile" id="codisFile" cssClass="file_one width4" />
	          	&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/></td>
	        </tr>

	      </table>
      </s:form>
    </fieldset>
     --%>
  </div>
</div>
</body>
</html>
