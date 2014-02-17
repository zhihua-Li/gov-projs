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
<title>高通量DNA检验平台 - 登录</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#submitBtn").removeAttr("disabled");

		//系统过期判定
		var expiredDayCount = $("#expiredDayCount").val();
		var expireWarnMsg = $("#expireWarnMsg").val();
		if(expireWarnMsg != ""){
			alert(expireWarnMsg);
		}
		if(parseInt(expiredDayCount) <= 0){
			$("#submitBtn").attr("disabled", "disabled");
			return;
		}else{
			$("#submitBtn").removeAttr("disabled");
		}

		//检查登录错误信息
		var hiddenErrorMessage = $("#hiddenErrorMessage").val();
		if(hiddenErrorMessage != ""){
			$("#errorMessageTxt").val(hiddenErrorMessage);
			$("table.login_message").css("display", "");
		}else{
			$("#errorMessageTxt").val();
			$("table.login_message").css("display", "none");
		}

		$("#submitLogin").submit(function(){
			var expireWarnMsg = $("#expireWarnMsg").val();
			var expiredDayCount = $("#expiredDayCount").val();

			if(parseInt(expiredDayCount) <= 0){
				alert(expireWarnMsg);

				$("#submitBtn").attr("disabled", "disabled");
				return false;
			}else{
				$("#submitBtn").removeAttr("disabled");
			}

			var userName = $("#userNameText").val();
			var userPassword = $("#userPasswordText").val();

			if(userName == ""){
				alert("请输入登录名！");
				$("#userNameText").focus();
				return false;
			}

			if(userPassword == ""){
				alert("请输入登录密码！");
				$("#userPasswordText").focus();
				return false;
			}

			return true;
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

<body style="position:relative;">
<div class="titles"></div>
<div class="login_box">
  <div class="box_login">
  	<input type="hidden" id="expiredDayCount" value="<s:property value="expiredDayCount"/>"/>
  	<input type="hidden" id="expireWarnMsg" value="<s:property value="expireWarnMsg"/>"/>
  	<s:form id="submitLogin" name="submitLogin" action="submitLogin" method="post">
	    <table class="table_login">
	      <tr class="tr_one">
	        <td colspan="2">
	        	<s:textfield id="userNameText" name="sysUser.userName" cssClass="text_login" />
	        </td>
	      </tr>
	      <tr class="tr_one">
	        <td colspan="2">
	        	<s:password id="userPasswordText" name="sysUser.userPassword" cssClass="text_login" />
	        </td>
	      </tr>
	      <tr class="tr_two">
	        <td>
	        	<s:submit id="submitBtn" cssClass="button_login_one" value=""></s:submit>
	        </td>
	        <td>
	        	<s:reset id="resetBtn" cssClass="button_login_two" value=""/>
	        </td>
	      </tr>
	    </table>
	    <s:hidden name="errorMessage" id="hiddenErrorMessage" />
    </s:form>

    <table class="login_message" style="display:none;">
      <tr class="tr_one">
        <td colspan="2">
        	<input type="text" id="errorMessageTxt" value="" style="color:red;"/>
        </td>
      </tr>
    </table>

    <table class="login_downloadIE">
      <tr class="tr_one">
        <td colspan="2">
        	温馨提示：为保证系统正常使用，建议您使用IE8版本以上浏览器。点击下载：<a href="javascript:void(0);" style="color:darkred ; text-decoration:underline; cursor:pointer;" onclick="document.location='<%=path %>/pages/system/downloadIEBrower.action?ietype=1'; return false;">IE8-WindowsXP-x86</a>
	  	&nbsp;<a href="javascript:void(0);" style="color:darkred ; text-decoration:underline; cursor:pointer;" onclick="document.location='<%=path%>/pages/system/downloadIEBrower.action?ietype=2'; return false;">IE8-WindowsVista-x86(Windows7)</a>
        </td>
      </tr>
    </table>

  </div>
</div>
</body>
</html>

