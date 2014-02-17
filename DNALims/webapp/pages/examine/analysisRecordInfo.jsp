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
<title>扩增记录表</title>
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
		$("#analysisDateTxt").datetimepicker();

		$("#backBtn").live("click", function(){
			$("#listAnalysisCheckForm").submit();
			//location.href="<%=path %>/pages/examine/listAnalysisCheck.action";
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
  <div class="posittions"> 当前位置：检验过程管理 &gt;&gt; 分析 </div>
  <div class="box_three">
    <div class="height_one"></div>
	<s:form action="submitAnalysisRecord" name="submitAnalysisRecordForm" id="submitAnalysisRecordForm" method="post">
		<s:hidden name="analysisRecord.id" />
		<s:hidden name="analysisRecord.boardInfoId" />
		<s:hidden name="operateType" />
		<s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	          	<s:hidden name="boardInfoQuery.examineDateStart"/>
	          	<s:hidden name="boardInfoQuery.examineDateEnd"/>
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
	    <fieldset class="fieldset_one">
	      <legend>分析记录</legend>
	      <div class="height_one"></div>
	      <table class="table_two width1 floatL marginL1">
		      	<s:hidden cssClass="text_one" name="boardInfo.consignmentNo" readonly="true"/>
			<tr>
	           <td>分析人:</td>
	          <td>
	          	<s:select name="analysisRecord.createUser" list="operateUserList" listKey="id" listValue="fullName" cssClass="width5">
	          		<s:param name="value"><s:property value="#session.currentUser.id"/></s:param>
	          	</s:select>
	          </td>
	        </tr>
	        <tr>
	           <td>分析时间:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="analysisRecord.analysisDate" id="analysisDateTxt">
	          		<s:param name="value">
	          			<s:date name="analysisRecord.analysisDate" format="yyyy-MM-dd HH:mm:ss" />
	          		</s:param>
	          	</s:textfield>
	          </td>
	        </tr>
	        <tr>
	          <td>分析软件类型:</td>
	          <td>
				<s:select cssClass="width5" name="analysisRecord.analysisInstrument" id="analysisInstrumentTxt" list="analysisInstrumentList"
          			listKey="dictKey" listValue="dictValue" headerKey="" headerValue="请选择..."/>	          </td>
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
      <s:form action="listWaitAnalysis" name="listAnalysisCheckForm" id="listAnalysisCheckForm" method="post">
        <s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	          	<s:hidden name="boardInfoQuery.examineDateStart"/>
	          	<s:hidden name="boardInfoQuery.examineDateEnd"/>
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
  	</s:form>
  </div>
</div>
</body>
</html>
