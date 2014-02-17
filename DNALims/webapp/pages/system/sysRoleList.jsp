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
		function deleteSysRoleFtn(obj){
			if(confirm("确认删除该角色吗？")){
				var deleteRoleId = $("input[type='hidden']", $(obj).parent()).val();
				document.location.href = "<%=path%>/pages/system/deleteSysRole.action?sysRole.id=" + deleteRoleId;
			}
		}

		function editSysRoleFtn(obj) {
			var editRoleId = $("input[type='hidden']", $(obj).parent()).val();
			document.location.href="<%=path%>/pages/system/editSysRole.action?sysRole.id=" + editRoleId;
		}

		function addSysRoleFtn() {
			document.location.href="<%=path%>/pages/system/addSysRole.action";
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
  <div class="posittions"> 当前位置：系统管理 &gt;&gt; 角色管理 </div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>角色查询</legend>
      <div class="height_one"></div>
      <s:form action="querySysRole" method="post" id="querySysRoleForm" name="querySysRoleForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>角色名:</td>
	          <td>
	          	<s:textfield name="sysRole.roleName" cssClass="text_one" />
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
      <legend>角色列表</legend>
      <div class="wf5"><input type="button" onclick="addSysRoleFtn();" class="button_one" value="添 加"/></div>
      <table class="table_one">
	      <thead>
	        <tr>
	          <th>序号</th>
	          <th>角色代码</th>
	          <th>角色名称</th>
	          <th>描	 述</th>
	          <th>操 作</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="sysRoleList" status="su">
				<tr>
					<td><s:property value="#su.index+1" /></td>
					<td><s:property value="roleType" /></td>
					<td><s:property value="roleName" /></td>
					<td><s:property value="roleDesc" /></td>
					<td>
						<input type="hidden" value="<s:property value="id"/> "/>
						<s:if test="editableFlag == 0">
							-- --
						</s:if>
						<s:else>
							<a href="#" class="red" onclick="editSysRoleFtn(this);"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>&nbsp;
							&nbsp;
							<a href="#" class="red" onclick="deleteSysRoleFtn(this);"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
						</s:else>

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
</body>
</html>