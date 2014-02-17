<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>修改密码</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){

			$("#submitBtn").live("click", function(){
				var oldPasswordText = $("#oldPasswordText").val();
				var newPasswordText = $("#newPasswordText").val();
				var confirmNewPasswordText = $("#confirmNewPasswordText").val();

				if(oldPasswordText == ""){
					alert("请输入旧密码");
					return;
				}

				if(newPasswordText == ""){
					alert("请输入新密码");
					return;
				}

				if(confirmNewPasswordText == ""){
					alert("请输入确认新密码");
					return;
				}

				if(newPasswordText != confirmNewPasswordText){
					alert("两次输入的新密码不一致，请重新输入！");
					return;
				}

				var userId = $("#hiddenUserId").val();

				$.ajax({
					type: "post",
					url: "<%=path %>/pages/submitUpdatePassword.action",
					data:{"sysUser.userPassword": oldPasswordText, "sysUser.newPassword": newPasswordText,
							"sysUser.id": userId},
					dataType:"json",
					success:function(jsonData){
						if(jsonData.success){
							alert("密码修改成功！");
							window.close();
						}else{
							if(jsonData.oldPasswordError){
								alert("旧密码错误，请重新输入！");
							}
						}
					}
				});

				return false;
			});

			$("#cancelBtn").live("click", function(){
				window.close();
			});
		});
	</script>
  </head>

  <body>
		<div class="main" style="overflow:hidden; height;100%;">
			<br/>
			<br/>
			<div class="box_three" style="overflow:hidden; height;100%;">
			<div class="height_one"></div>
			<div class="height_one"></div>
			<input type="hidden" id="hiddenUserId" value="<s:property value="#session.currentUser.id"/>" />
			<table class="table_two floatL marginL1" style="height:100%;">
		        <tr>
		          <td>旧密码<span style="color:red;">*</span>：</td>
		          <td>
		          	<input type="password" id="oldPasswordText" class="text_one"/>
		          </td>
		        </tr>
		        <tr>
		          <td>新密码<span style="color:red;">*</span>：</td>
		          <td>
		          	<input type="password" id="newPasswordText" class="text_one" />
		          </td>
		        </tr>
		       	<tr>
		          <td>确认新密码<span style="color:red;">*</span>：</td>
		          <td>
		          	<input type="password" id="confirmNewPasswordText" class="text_one" />
		          </td>
		        </tr>
		        <tr>
		        	<td>
		        		<input type="button" id="submitBtn" class="button_one" value="提  交"/>
		        		&nbsp;
		        		<input type="button" id="cancelBtn" class="button_one" value="取  消"/>
		        	</td>
		        </tr>
		      </table>
		      <br/>
		      			<div class="height_one"></div>

		      			<div class="height_one"></div>



		 </div>
		 				<br/>
		      			<br/>
		      			<br/>
	</div>
  </body>
</html>