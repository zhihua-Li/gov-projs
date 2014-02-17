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
<title>高通量DNA检验平台</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>

<script type="text/javascript">
	function updatePasswordFtn(){
		window.showModalDialog("<%=path%>/pages/system/intoUpdatePassword.action", null, 'dialogWidth=420px;dialogHeight=240px;status=0;scroll=yes;help:no;dialogLeft=700px;dialogTop=300px;');
	}

	function logoutFtn(){
		parent.location.href = "<%=path %>/pages/logout.action";
	}

	function downloadManual(){
		document.location.href="<%=path%>/pages/system/downloadManual.action";
	}
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
<div id="box">
  <div class="header">
    <div class="logo"></div>
    <div class="title_h1"></div>
    <ul class="nav">
		<s:iterator var="sysMenu" value="rootSysMenuList">
			<li>
				<a href="<%=path %>/pages/intoFrameContent.action?menuId=<s:property value="#sysMenu.id"/>" target="mainerif"><s:property value="#sysMenu.menuName"/></a>
			</li>
		</s:iterator>
    </ul>

    <div style="position:absolute; top:35px; right:25px; width:100px; font-size:14px; font-color:red;">
		<a style="color:rgb(255, 0, 92); text-decoration:underline;" href="javascript:void(0);" onclick="downloadManual();">下载使用手册</a>
    </div>

    <div class="admin">
      <ul>
       <li class="none">当前用户：</li>
       <li class="none_two"><s:property value="#session.currentUser.fullName" /></li>
<%--   <li>|<a href="<%=path %>/pages/intoFrameContent.action?menuId=<s:property value="defaultSysMenu.id"/>" target="mainerif">返回首页</a></li>	 --%>
       <li>|<a href="<%=path %>/pages/help.jsp" target="_blank">帮 助</a></li>
       <li>|<a href="javascript:void(0);" onclick="updatePasswordFtn();">修改密码</a></li>
       <li>|<a href="javascript:void(0);" onclick="logoutFtn();">注 销</a></li>
      </ul>
    </div>
  </div>
<iframe id="mainerif" src="<%=path %>/pages/intoFrameContent.action?menuId=<s:property value="defaultSysMenu.id"/>" name="mainerif" frameborder="0" scrolling="no" width="100%" ></iframe>
</div>
</body>
</html>

