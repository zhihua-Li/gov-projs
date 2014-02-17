<%@ page language="java" pageEncoding="UTF-8"%>
<%@	taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文件管理</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript">
		function downloadFileUploader(){
			document.location.href="<%=path%>/pages/file/downloadFileUploader.action";
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
  <div class="posittions"> 当前位置：文件管理  &gt;&gt; 文件上传工具下载</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>文件上传工具信息</legend>
      <div class="height_one"></div>
	      <table class="table_two width3 floatL marginL1">
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

    <div class="height_one"></div>

</div>

</div>
</body>
</html>
