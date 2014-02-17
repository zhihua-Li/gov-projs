<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>系统用户列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>

	<script type="text/javascript">
		$(document).ready(function(){

			$("#addSysUserBtn").live("click", function(){
				$("#sysUserDetailIframe").attr("src", "<%=path%>/pages/system/editSysUser.action?operateType=1");;
				$("#sysUserDetailDiv").dialog({
					autoOpen: true,
					height:	460,
					width: 780,
					modal: true,
					title: "新增用户",
					buttons: {
						"保存": function(){
							var obj = document.getElementById("sysUserDetailIframe").contentWindow;
							obj.submitForm();
						},
						"取消": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
						$(this).dialog("close");
					}
				});
			});

		});

		function editSysUserFtn(obj) {
			var editUserId = $("input[type='hidden']", $(obj).parent()).val();

			$("#sysUserDetailIframe").attr("src", "<%=path%>/pages/system/editSysUser.action?operateType=2&sysUser.id=" + editUserId);
			$("#sysUserDetailDiv").dialog({
				autoOpen: true,
				height:	460,
				width: 780,
				modal: true,
				title: "编辑用户",
				buttons: {
					"保存": function(){
						var obj = document.getElementById("sysUserDetailIframe").contentWindow;
						obj.submitForm();
					},
					"取消": function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					$(this).dialog("close");
				}
			});
		}

		function deleteSysUserFtn(obj){
			if(confirm("确认删除该用户吗？")){
				var deleteUserId = $("input[type='hidden']", $(obj).parent()).val();
				document.location.href = "<%=path%>/pages/system/deleteSysUser.action?sysUser.id=" + deleteUserId;
			}
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
  <div class="posittions"> 当前位置：系统管理  &gt;&gt; 用户管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>用户查询</legend>
      <div class="height_one"></div>
      <s:form action="querySysUser" method="post" id="querySysUserForm" name="querySysUserForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>登录名:</td>
	          <td>
	          	<s:textfield name="sysUser.userName" cssClass="text_one" />
	          </td>
	          <td class="width6">姓 名:</td>
	          <td>
	          	<s:textfield name="sysUser.fullName" cssClass="text_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>身份证号:</td>
	          <td>
	          	<s:textfield name="sysUser.idCardNo" cssClass="text_one" />
	          </td>
	          <td colspan="2">
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>用户列表</legend>
      <div class="wf5"><input type="button" id="addSysUserBtn" class="button_one" value="添 加"/></div>
      <table class="table_one">
	      <thead>
	        <tr>
	          <th>序号</th>
	          <th>用户名</th>
	          <th>姓	 名</th>
	          <th>身份证号</th>
	          <th>联系电话</th>
	          <th>操作</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="sysUserList" status="su">
				<tr>
					<td><s:property value="#su.index+1" /></td>
					<td><s:property value="userName" /></td>
					<td><s:property value="fullName" /></td>
					<td><s:property value="idCardNo" /></td>
					<td><s:property value="phonenum" /></td>
					<td>
						<input type="hidden" value="<s:property value="id"/> "/>
						<a href="#" class="red" onclick="editSysUserFtn(this);"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>&nbsp;
						&nbsp;
						<a href="#" class="red" onclick="deleteSysUserFtn(this);"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
					</td>
				</tr>
	      	</s:iterator>
	      </tbody>
    </table>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    </fieldset>
  </div>
</div>

<!-- 用户详情Dialog -->
<div id="sysUserDetailDiv">
	<iframe id="sysUserDetailIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>

</body>
</html>
