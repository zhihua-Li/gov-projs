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
<title>检验记录查看</title>
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

		$("input[type='radio']", "#loopCountTD").eq(0).attr("checked", "checked");
		$("input[type='radio']", "#pcrSystemTD").eq(0).attr("checked", "checked");

		$("#backBtn").live("click", function(){
			location.href="<%=path %>/pages/examine/listWaitPcr.action";
		});
	});

	function backFtn(){
		location.href="<%=path %>/pages/examine/listExamineRecords.action";
	}

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
  <div class="posittions"> 当前位置：检验过程管理&gt;&gt; 检验记录管理 </div>
  <div class="box_three">
    <div class="height_one"></div>
	    <fieldset class="fieldset_one">
	      <legend>扩增记录</legend>
	      <div class="height_one"></div>
	      <table class="table_two width8 floatL marginL1">
	        <tr>
	          <td>扩增时间:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.pcrDate" id="pcrDateTxt" readonly="true">
	          		<s:param name="value">
	          			<s:date name="pcrRecord.pcrDate" format="yyyy-MM-dd HH:mm:ss" />
	          		</s:param>
	          	</s:textfield>
	          </td>
	          <td>扩增仪编号:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.pcrInstrument" id="pcrInstrumentTxt" readonly="true"/>
	          </td>
	        </tr>
	        <tr>
	          <td>温度:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.temperature" id="temperatureTxt" readonly="true"/>
	          </td>
	          <td>湿度:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.humidity" id="humidityTxt" readonly="true"/>
	          </td>
	        </tr>
	        <tr>
	          <td>循环数:</td>
	          <td colspan="3" id="loopCountTD">
				<s:radio cssClass="radio_one" name="pcrRecord.loopCount" list="pcrLoopCountList" listKey="dictKey" listValue="dictValue" />
	          </td>
	        </tr>
	        <tr>
	          <td>扩增体系:</td>
	          <td id="pcrSystemTD">
	          	<s:radio cssClass="radio_one" name="pcrRecord.pcrSystem" list="pcrSystemList" listKey="dictKey" listValue="dictValue" />
	          </td>
	          <td>试剂编号:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="pcrRecord.reagentNo" id="reagentNoTxt" readonly="true"/>
	          </td>
	        </tr>
<%--
	        <tr>
        		<td>检测仪编号:</td>
	          	<td colspan="3" id="examineInstrumentTD">
	          		<s:radio cssClass="radio_one" name="boardInfo.examineInstrumentNo" list="examineInstrumentList" listKey="dictKey" listValue="dictValue" />
	          	</td>
	    	</tr>
 --%>
	        <tr>
	          <td>检测人:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfo.examineUser" id="examineUserTxt" readonly="true"/>
	          </td>
	          <td>检测时间:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfo.examineDate" id="examineDateTxt" readonly="true">
	          		<s:param name="value">
	          			<s:date name="boardInfo.examineDate" format="yyyy-MM-dd HH:mm:ss" />
	          		</s:param>
	          	</s:textfield>
	          </td>
	        </tr>
	      </table>
	    </fieldset>
	    <fieldset class="fieldset_one">
	      <legend>分析记录</legend>
	      <div class="height_one"></div>
	      <table class="table_two width1 floatL marginL1">
	        <tr>
	          <td>委托号:</td>
	          <td>
		      	<s:textfield cssClass="text_one" name="boardInfo.consignmentNo" readonly="true"/>
			  </td>
	        </tr>
	        <tr>
	           <td>分析时间:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="analysisRecord.analysisDate" id="analysisDateTxt" readonly="true">
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
          			listKey="dictKey" listValue="dictValue" headerKey="" headerValue="请选择..."  readonly="true"/>
          	</td>
	        </tr>
	    </table>
	    </fieldset>
	    <div class="height_one"></div>
	    <div class="wf">
			<input type="button" class="button_one" onclick="backFtn();" value=" 返     回  " />
	    </div>
  </div>
</div>
</body>
</html>
