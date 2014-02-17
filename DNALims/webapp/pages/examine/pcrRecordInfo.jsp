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
		$("#pcrDateTxt").datetimepicker();

		$("#examineDateTxt").datetimepicker();

		//$("input[type='radio']", "#loopCountTD").eq(0).attr("checked", "checked");
		$("input[type='radio']", "#pcrSystemTD").eq(0).attr("checked", "checked");
		$("input[type='radio']", "#examineInstrumentTD").eq(0).attr("checked", "checked");

		$("#backBtn").live("click", function(){
			//location.href="<%=path %>/pages/examine/listWaitPcr.action";
			$("#listWaitPcrForm").submit();
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
  <div class="posittions"> 当前位置：检验过程管理&gt;&gt; 扩增检测 </div>
  <div class="box_three">
    <div class="height_one"></div>
	<s:form action="submitPcrRecord" name="submitPcrRecordForm" id="submitPcrRecordForm" method="post">
		<s:hidden id="pageSize" name="pageSize" />
  		<s:hidden id="pageIndex" name="pageIndex" />
  		<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
  		<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
  		<s:hidden name="boardInfoQuery.consignmentNo"/>
  		<s:hidden name="boardInfoQuery.boardNo"/>
	    <s:hidden name="boardInfoQuery.importDateStart"/>
	    <s:hidden name="boardInfoQuery.importDateEnd"/>
		<s:hidden name="pcrRecord.boardInfoId" />
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
		<s:hidden name="boardInfo.id" />
	    <fieldset class="fieldset_one">
	      <legend>扩增记录</legend>
	      <div class="height_one"></div>
	      <table class="table_two width8 floatL marginL1">
	      	<tr>
	      		  <td>扩增仪编号：</td>
		          <td colspan="3">
		          	<s:textfield cssClass="text_one" name="pcrRecord.pcrInstrument" id="pcrInstrumentTxt"/></td>
	      	</tr>
	        <tr>
	          <td>温度：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.temperature" id="temperatureTxt"/>
	          </td>
	          <td>湿度：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.humidity" id="humidityTxt"/>
	          </td>
	        </tr>
	        <tr>
	          <td>循环数：</td>
	          <td colspan="3" id="loopCountTD">
	          	<input type="hidden" id="hiddenDefaultLoopCount" value="<s:property value="pcrRecord.loopCount"/>"/>
				<s:radio cssClass="radio_one" name="pcrRecord.loopCount" list="pcrLoopCountList" listKey="dictKey" listValue="dictValue">
					<s:param name="value"><s:property value="pcrRecord.loopCount"/></s:param>
				</s:radio>
	          </td>
	        </tr>
	        <tr>
	          <td>扩增体系：</td>
	          <td id="pcrSystemTD">
	          	<s:radio cssClass="radio_one" name="pcrRecord.pcrSystem" list="pcrSystemList" listKey="dictKey" listValue="dictValue" />
	          </td>
	          <td>试剂编号：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.reagentNo" id="reagentNoTxt"/>
	          </td>
	        </tr>
	        <tr>
	          <td>扩增人：</td>
	          <td>
	          	<s:select name="pcrRecord.pcrUser" list="sysUserList" listKey="id" listValue="fullName" cssClass="width5">
	          		<s:param name="value"><s:property value="#session.currentUser.id"/></s:param>
	          	</s:select>
	          </td>
	          <td>扩增时间：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.pcrDate" id="pcrDateTxt">
	          		<s:param name="value">
	          			<s:date name="pcrRecord.pcrDate" format="yyyy-MM-dd HH:mm:ss" />
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
    <s:form action="listWaitPcr" method="post" id="listWaitPcrForm" name="listWaitPcrForm">
        <s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	    <s:hidden name="boardInfoQuery.importDateStart"/>
	    <s:hidden name="boardInfoQuery.importDateEnd"/>
	          	<s:hidden name="boardInfoQuery.fuzzyFlag"/>
  	</s:form>
  </div>
</div>
</body>
</html>
