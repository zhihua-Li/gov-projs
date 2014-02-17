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
<title>检测记录表</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#examineDateTxt").datetimepicker();

		$("#backBtn").live("click", function(){
			$("#listWaitCheckForm").submit();
			//location.href="<%=path %>/pages/examine/listWaitCheck.action";
		});
	});
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
<div class="main">
  <div class="posittions"> 当前位置：检验过程管理&gt;&gt; 检测 </div>
  <div class="box_three">
    <div class="height_one"></div>
	<s:form action="submitExamineCheckRecord" name="submitExamineCheckRecordForm" id="submitExamineCheckRecordForm" method="post">
		<s:hidden name="examineRecord.boardId" />
	    <s:hidden id="pageSize" name="pageSize" />
  		<s:hidden id="pageIndex" name="pageIndex" />
		<s:hidden name="boardInfoQuery.consignmentNo"/>
	    <s:hidden name="boardInfoQuery.boardNo"/>
  		<s:hidden name="boardInfoQuery.sortColumn"/>
  		<s:hidden name="boardInfoQuery.sortOrder"/>
	          	<s:hidden name="boardInfoQuery.pcrDateStart"/>
	          	<s:hidden name="boardInfoQuery.pcrDateEnd"/>
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
		<s:hidden name="boardInfo.id" />
	    <fieldset class="fieldset_one">
	      <legend>检测记录</legend>
	      <div class="height_one"></div>
	      <table class="table_two width8 floatL marginL1">
	      	<tr>
	      		  <td>检测仪编号：</td>
		          <td>
		          	<s:radio cssClass="radio_one" name="examineRecord.examineInstrumentNo" list="examineInstrumentList" listKey="dictKey" listValue="dictValue">
		          		<s:param name="value">2</s:param>
		          	</s:radio>
		          </td>
	      	</tr>
	        <tr>
	          <td>检测人：</td>
	          <td>
	          	<s:select name="examineRecord.examineUserId" list="sysUserList" listKey="id" listValue="fullName" cssClass="width5">
	          		<s:param name="value"><s:property value="#session.currentUser.id"/></s:param>
	          	</s:select>
	          </td>
	         </tr>
	        <tr>
	          <td>检测时间：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="examineRecord.examineDate" id="examineDateTxt">
	          		<s:param name="value">
	          			<s:date name="examineRecord.examineDate" format="yyyy-MM-dd HH:mm:ss" />
	          		</s:param>
	          	</s:textfield>
	          </td>
	        </tr>
	      </table>
	    </fieldset>
	    <div class="height_one"></div>
	    <div style="width:200px;float:left;text-align:center;margin:10px auto;">
	    	<s:submit cssClass="button_one" value="保  存" />
          	&nbsp;
          	<input type="button" class="button_one" id="backBtn" value="返  回"/>
	    </div>
    </s:form>
  </div>

  <div style="display:none">
      <s:form action="listWaitCheck" method="post" id="listWaitCheckForm" name="listWaitCheckForm">
        <s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	          	<s:hidden name="boardInfoQuery.pcrDateStart"/>
	          	<s:hidden name="boardInfoQuery.pcrDateEnd"/>
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
  	</s:form>
  </div>
</div>
</body>
</html>
