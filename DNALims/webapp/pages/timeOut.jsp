<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<html>
<head>
<title>高通量DNA检验平台</title>
<script language="javascript">
	function timeOut(){
		alert('没有登录或登录超时，请您重新登录！');
		top.location.href="<%=path%>/pages/intoLogin.action";
		return;
	}
</script>
</head>

<body onload="timeOut();">
</body>
</html>
