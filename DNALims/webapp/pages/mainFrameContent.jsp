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
	$(function(){

		$("#closeLeftImg").live("click", function(){
			$(this).css("display", "none");
			$("#openLeftImg").css("display", "");
			$("div.sidebar").hide();
			$("div.box_two").width($(document.body).width());
		});

		$("#openLeftImg").live("click", function(){
			$(this).css("display", "none");
			$("#closeLeftImg").css("display", "");
			$("div.sidebar").show();
			$("div.box_two").width($(document.body).width()-200);
		});

	});
</script>
<style>
html, body {
	overflow:hidden;
	width:100%;
	height:100%;
}
</style>
</head>

<body>
	<div class="sidebar">
		<ul>
			<s:iterator var="sysMenu" value="childSysMenuList">
				<li>
					<a href="<%=path %>/<s:property value="#sysMenu.menuLink"/>" target="mainif"><s:property value="#sysMenu.menuName"/></a>
				</li>
			</s:iterator>
		</ul>
	</div>

	<img src="<%=path %>/images/close_left.gif" id="closeLeftImg" alt="关闭菜单栏" style="position:absolute;top:210px;left:200px;cursor:pointer;" title="关闭菜单栏"/>
	<img src="<%=path %>/images/open_left.gif" id="openLeftImg" alt="打开菜单栏" style="display:none;position:absolute;top:210px;left:0px;cursor:pointer;" title="打开菜单栏"/>

	<div class="box_two">
	  <iframe id="mainif" src="<%=path %>/<s:property value="defaultSysMenu.menuLink"/>" name="mainif" frameborder="0" scrolling="no" height="100%" width="100%"></iframe>
	</div>
</body>
</html>
