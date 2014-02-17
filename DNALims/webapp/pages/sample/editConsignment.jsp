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
<title>样本受理</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#consignmentDateTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});

			$("#expenseBalanceDateTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});

			$("#dataUploadDateTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});

			$("#sampleRebackDateTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});

			$("input[type='radio']", "#consignmentModeTD").eq(0).attr("checked", "checked");
			$("input[type='radio']", "#checkContentTD").eq(0).attr("checked", "checked");

			$("#submitAcceptInfoForm").live("submit", function(){

				var organizationId = $("#organizationId").val();
				var organizationId = $("#organizationId").val();
				if(organizationId == ""){
					alert("请选择委托单位！");
					return false;
				}

				var consignmentDateTxt = $("#consignmentDateTxt").val();
				if(consignmentDateTxt == ""){
					alert("请输入送检时间！");
					return false;
				}

				var consignmentorTxt = $("#consignmentorTxt").val();
				if(consignmentorTxt == ""){
					alert("请输入委托联系人！");
					return false;
				}
/*
				if($("#bloodSampleCheck").attr("checked")){
					var bloodSampleCount = $("#cosignBloodCountTxt").val();
					if(bloodSampleCount == ""){
						alert("请输入送检血斑样本份数！");
						return false;
					}
				}

				if($("#salivaSampleCheck").attr("checked")){
					var salivaSampleCount = $("#cosignSalivaCountTxt").val();
					if(salivaSampleCount == ""){
						alert("请输入送检唾液斑样本份数！");
						return false;
					}
				}
*/
				return true;
			});

		});

		function backBtn(){
			$("#queryConsignmentForm").submit();
			//document.location.href = "<%=path %>/pages/sample/queryConsignment.action";
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
  <div class="posittions"> 当前位置：样本管理  &gt;&gt; 查看委托</div>
  <div class="box_three">
    <div class="height_one"></div>
    <s:form action="submitConsignment" name="submitConsignmentForm" id="submitConsignmentForm" method="post">
    <s:hidden name="consignment.id" id="hiddenConsignmentId"/>
    <fieldset class="fieldset_one">
      <legend>委托信息</legend>
      <div class="height_one"></div>
	      <table class="table_two width2 floatL marginL1">
	        <tr id="consignmentNoTr">
	          <td>委托号：</td>
	          <td colspan="3">
	          	<s:textfield name="consignment.consignmentNo" id="consignmentNoTxt" cssClass="text_nowidth width4" readonly="true" />
	          </td>
	        </tr>
	        <tr>
	          <td>委托单位<span style="color:red;">*</span>：</td>
	          <td colspan="3">
	          	<s:select name="consignment.organizationId" id="organizationId" cssClass="select_one" list="consignOrganizationList" listKey="id" listValue="organizationName" headerKey="" headerValue="请选择..."/>
	          	&nbsp;&nbsp;<s:textfield name="consignment.organizationSubName" cssClass="text_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>委托联系人<span style="color:red;">*</span>：</td>
	          <td colspan="3">
	          	<s:textfield name="consignment.consignmentor" id="consignmentorTxt" cssClass="text_nowidth width4" />
	          </td>
	        </tr>
	        <tr>
	          <td>送检时间<span style="color:red;">*</span>：</td>
	          <td colspan="3">
	          	<s:textfield name="consignment.consignDate" id="consignmentDateTxt" cssClass="text_nowidth width4" />
	          </td>
	        </tr>
	        <tr>
	          <td>联系电话：</td>
	          <td>
	          	<s:textfield name="consignment.phonenum" cssClass="text_nowidth width4" />
	          </td>
	        </tr>
	        <tr>
	          <td>送检方式：</td>
	          <td id="consignmentModeTD" colspan="3">
	          	<s:radio cssClass="radio_one" name="consignment.consignMethod" list="consignModeList" listKey="dictKey" listValue="dictValue" />
	          </td>
	        </tr>
	        <tr>
	          <td>送检样本：</td><%--<span style="color:red;">*</span> --%>
	          <td colspan="3">
	          	<s:if test="consignment.consignBloodCount == null || consignment.consignBloodCount == 0">
	          		<input type="checkbox" name="bloodSampleCheck" id="bloodSampleCheck"/>血斑
	          	</s:if>
	          	<s:else>
	          		<input type="checkbox" name="bloodSampleCheck" id="bloodSampleCheck" checked/>血斑
	          	</s:else>
	          	&nbsp;&nbsp;份数&nbsp;<s:textfield name="consignment.consignBloodCount" id="cosignBloodCountTxt" cssClass="text_nowidth width6"/>&nbsp;&nbsp;&nbsp;&nbsp;
	          	<s:if test="consignment.consignSalivaCount == null || consignment.consignSalivaCount == 0">
	          		<input type="checkbox" name="salivaSampleCheck" id="salivaSampleCheck"/>唾液斑
	          	</s:if>
	          	<s:else>
	          		<input type="checkbox" name="salivaSampleCheck" id="salivaSampleCheck" checked/>唾液斑
	          	</s:else>
	          	&nbsp;&nbsp;份数&nbsp;<s:textfield name="consignment.consignSalivaCount" id="cosignSalivaCountTxt" cssClass="text_nowidth width6"/>&nbsp;&nbsp;
			  </td>
	        </tr>
	        <tr>
	          <td>检验内容：</td>
	          <td id="checkContentTD">
				<s:radio name="consignment.checkContentType" list="checkContentList" listKey="dictKey" listValue="dictValue" cssClass="radio_one"/>
	          </td>
	          <td>样本载体：</td>
	          <td>
	          	<s:select name="consignment.sampleCarrier" cssClass="select_one" list="sampleCarrierList" listKey="dictKey" listValue="dictValue" />
	          </td>
	        </tr>
	        <tr>
	          <td>合同金额：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="consignment.agreementAmount"/>
	          </td>
	          <td>费用结清日期：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="consignment.expenseBalanceDate" id="expenseBalanceDateTxt"/>
	          </td>
	        </tr>
	        <tr>
	          <td>数据上传日期：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="consignment.dataUploadDate" id="dataUploadDateTxt"/>
	          </td>
	          <td>样本返还日期：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="consignment.sampleRebackDate" id="sampleRebackDateTxt"/>
	          </td>
	        </tr>
	        <tr>
	          <td>备注：</td>
	          <td colspan="3">
	          	<s:textarea name="consignment.remark" rows="4" cssClass="textarea_one"></s:textarea>
	          </td>
	        </tr>
	        <tr>
	        	<td colspan="4">
	        		<input type="submit" class="button_one" id="submitBtn" value="保存变更" />
	        		&nbsp;&nbsp;&nbsp;
	        		<input type="button" class="button_one" onclick="backBtn();" value="返  回" />
	        	</td>
	        </tr>
	      </table>
    </fieldset>
    <div class="height_one"></div>
  </s:form>
<div style="display:none;">
  <s:form action="queryConsignment" method="post" id="queryConsignmentForm" name="queryConsignmentForm">
  	<s:hidden id="pageSize" name="pageSize" />
	<s:hidden id="pageIndex" name="pageIndex" />
	<s:hidden name="consignmentQuery.sortColumn" id="hiddenSortColumn"/>
	<s:hidden name="consignmentQuery.sortOrder" id="hiddenSortOrder"/>
  </s:form>
</div>
</div>
</div>
</body>
</html>
