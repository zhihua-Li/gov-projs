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
<title>高通量DNA检验平台</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
		$(document).ready(function(){

			$("#geneInfoTbl").append('<s:property escape="0" value="qualityControlSampleView.geneInfoHtmlForPage"/>');


			$("#submitQualitySampleForm").submit(function(){
				var fullName = $("#fullNameTxt").val();
				if(fullName == ""){
					alert("请输入质控人员姓名！");
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

<body>
<div class="main">
  <div class="posittions"> 当前位置：质量控制管理 &gt;&gt; 质控人员信息</div>
  <div class="box_three">
    <div class="height_one"></div>
    <s:form action="submitQualitySample" id="submitQualitySampleForm" name="submitQualitySampleForm" method="post">
    	<s:hidden name="qualityControlSampleView.id" id="hiddenId" />
    	<s:hidden name="operateType" />

	    <fieldset class="fieldset_one">
	      <legend>质控人员信息</legend>
	      <div class="height_one"></div>
	      <table class="table_two width8 floatL marginL1">
	      	<s:if test="operateType == 2">
	      		<tr>
		    	<td class="width_table1">人员编号<span style="color:red;">*</span>：</td>
		        <td colspan="3">
		        	<s:textfield name="qualityControlSampleView.sampleNo" cssClass="text_one" id="sampleNoTxt" readonly="true"/>
		    	</td>
		    </tr>
	      	</s:if>
	      	<tr>
		    	<td class="width_table1">人员姓名<span style="color:red;">*</span>：</td>
		        <td>
		        	<s:textfield name="qualityControlSampleView.fullName" cssClass="text_one" id="fullNameTxt"/>
		    	</td>
		        <td class="width_table1">性别：</td>
		        <td>
		        	<s:select name="qualityControlSampleView.gender" list="genderList" listKey="dictKey" listValue="dictValue"
		        		cssClass="select_one" />
		        </td>
		    </tr>
		    <tr>
		        <td class="width_table1">身份证号：</td>
		        <td>
		        	<s:textfield name="qualityControlSampleView.idcardNo" cssClass="text_one"/>
		        </td>
		        <td class="width_table1">电话：</td>
		        <td>
		        	<s:textfield name="qualityControlSampleView.phonenum" cssClass="text_one"/>
		        </td>
		      </tr>
		    <tr>
		        <td class="width_table1">所属单位：</td>
		        <td colspan="2">
		        	<s:select name="qualityControlSampleView.organizationId" list="organizationList" listKey="id"
		        		listValue="organizationName" headerKey="" headerValue="" cssClass="select_one" />
		        	<s:textfield name="qualityControlSampleView.organizationSubName" cssClass="text_one" />
		        </td>
		      </tr>
		      <tr>
		        <td class="width_table1">职务：</td>
		        <td>
		        	<s:textfield name="qualityControlSampleView.duty" cssClass="text_one" />
		        </td>
		        <td class="width_table1">警号：</td>
		        <td>
		        	<s:textfield name="qualityControlSampleView.policeNo" cssClass="text_one" />
		        </td>
		      </tr>
		      <tr>
		        <td class="width_table1">备注:</td>
		        <td colspan="2">
		        	<s:textarea name="qualityControlSampleView.remark" rows="3" cssClass="textarea_one" />
		        </td>
		        <td align="right">
		        	<s:submit value="保  存" id="submitBtn" cssClass="button_one"/>
		        	&nbsp;&nbsp;
		        	<input type="button" value="返  回" id="backBtn" class="button_one" />
		        </td>
		      </tr>
	      </table>
	    </fieldset>
	    <fieldset class="fieldset_one">
	      <legend>基因型信息</legend>
	      	<table class="table_two width8 floatL marginL1" id="geneInfoTbl">

	      	</table>
		    <div class="height_one"></div>
	    </fieldset>
    </s:form>
  </div>
</div>
</body>
</html>

