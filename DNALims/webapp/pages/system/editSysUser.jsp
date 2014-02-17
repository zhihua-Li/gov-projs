<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户详情</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){

			$("#submitSysUserForm").live("submit", function(){

				$(this).ajaxSubmit({
					target: "#submitSysUserForm",
					success: function(jsonData){
						if(jsonData.success){
							window.parent.$("#sysUserDetailDiv").dialog("close");
							window.parent.location.href="<%=path %>/pages/system/querySysUser.action";
						}else{
							alert(jsonData.errorMsg);
						}

					},
					error: function(e){
					},
					url: "<%=path %>/pages/system/submitSysUser.action",
					type: "post",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "json"
				});

				return false;
			});

		});

		function submitForm() {

			var operateType = $("#hiddenOperateType").val();
			var userNameText = $("#userNameText").val();
			if(userNameText == ""){
				alert("请输入登录名！");
				return;
			}

			if(operateType == '1'){
				var userPasswordText = $("#userPasswordText").val();
				if(userPasswordText == ""){
					alert("请输入登录密码！");
					return;
				}
			}

			var fullNameText = $("#fullNameText").val();
			if(fullNameText == ""){
				alert("请输入姓名！");
				return;
			}

			var checkboxArr = $("input[type='checkbox'][name='roleIdList']");
			var checked = 0;
			$(checkboxArr).each(function(){
				if($(this).attr("checked")){
					checked++;
				}
			});
			if(checked == 0) {
				alert("请指定用户角色！");
				return;
			}

			$("#submitSysUserForm").submit();
		}
	</script>
  </head>

  <body>
		<div class="main">
	  	<s:form action="submitSysUser" id="submitSysUserForm" name="submitSysUserForm" method="post">
	  		<s:hidden name="operateType" id="hiddenOperateType"/>
	  		<s:hidden name="sysUser.id"/>

			<table class="table_two floatL marginL1">
		        <tr>
		          <td>登录名<span style="color:red;">*</span>：</td>
		          <td>
		          	<s:if test="operateType == 2">
		          		<s:textfield name="sysUser.userName" id="userNameText" cssClass="text_one" readonly="true"/>
		          	</s:if>
		          	<s:else>
		          		<s:textfield name="sysUser.userName" id="userNameText" cssClass="text_one"/>
		          	</s:else>
		          </td>
		        </tr>
		        <s:if test="operateType == 1">
			        <tr>
			          <td>登录密码<span style="color:red;">*</span>：</td>
			          <td>
			          	<s:password name="sysUser.userPassword" id="userPasswordText" cssClass="text_one" />
			          </td>
			        </tr>
		        </s:if>
		        <tr>
		          <td>姓  名<span style="color:red;">*</span>：</td>
		          <td>
		          	<s:textfield name="sysUser.fullName" id="fullNameText" cssClass="text_one" />
		          </td>
		        </tr>
		       	<tr>
		          <td>身份证号：</td>
		          <td>
		          	<s:textfield name="sysUser.idCardNo" cssClass="text_one" />
		          </td>
		        </tr>
		        <tr>
		          <td>联系电话：</td>
		          <td>
		          	<s:textfield name="sysUser.phonenum" cssClass="text_one" />
		          </td>
		        </tr>
		        <tr>
		          <td>Email：</td>
		          <td>
		          	<s:textfield name="sysUser.email" cssClass="text_one" />
		          </td>
		        </tr>
		        <tr>
		          <td>用户角色<span style="color:red;">*</span>：</td>
		          <td>
		          		<s:checkboxlist name="roleIdList" list="sysRoleList" listKey="id" listValue="roleName"></s:checkboxlist>
				  </td>
		        </tr>
		        <tr>
		          <td>是否启用：</td>
		          <td>
		          	<s:radio list="#{'1':'启用', '0':'禁用'}" name="sysUser.activeFlag" value="1"/>
				  </td>
		        </tr>
		      </table>
		</s:form>
	</div>
  </body>
</html>
