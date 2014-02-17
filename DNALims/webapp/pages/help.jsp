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
<title>高通量DNA检验平台--帮 助</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
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
  <div class="posittions"> 当前位置：帮 助</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>下载列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>类型</th>
          <th>名称</th>
          <th>下载</th>
        </tr>
      </thead>
      <tbody>
			<tr>
				<td>1</td>
				<td>IE8浏览器(WindowsXP中文版)</td>
				<td>IE8-WindowsXP-x86-CHS.exe</td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="document.location.href='<%=path %>/pages/system/downloadIEBrower.action?ietype=1'; return false;"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载" title="下载"/></a>
				</td>
			</tr>
			<tr>
				<td>2</td>
				<td>IE8浏览器(WindowsVista中文版)</td>
				<td>IE8-WindowsVista-x86-CHS.exe</td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="document.location.href='<%=path%>/pages/system/downloadIEBrower.action?ietype=2'; return false;"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载" title="下载"/></a>
				</td>
			</tr>
			<tr>
				<td>3</td>
				<td>IE8浏览器(WindowsXP英文版)</td>
				<td>IE8-WindowsXP-x86-ENU.exe</td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="document.location.href='<%=path %>/pages/system/downloadIEBrower.action?ietype=3'; return false;"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载" title="下载"/></a>
				</td>
			</tr>
			<tr>
				<td>4</td>
				<td>IE8浏览器(WindowsVista英文版)</td>
				<td>IE8-WindowsVista-x86-ENU.exe</td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="document.location.href='<%=path%>/pages/system/downloadIEBrower.action?ietype=4'; return false;"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载" title="下载"/></a>
				</td>
			</tr>
			<tr>
				<td>3</td>
				<td>解压缩工具</td>
				<td>wrar501sc.exe</td>
				<td>
					<a href="javascript:void(0);" class="red" onclick="document.location.href='<%=path%>/pages/system/downloadWinrar.action'; return false;"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载" title="下载"/></a>
				</td>
			</tr>
      </tbody>
    </table>
    <div class="height_one"></div>
    </fieldset>
  </div>
</div>
</body>
</html>
