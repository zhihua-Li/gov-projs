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
<title>质控人员管理</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){

			$("#addBtn").live("click", function(){
				location.href = "<%=path %>/pages/quality/intoQualitySample.action?operateType=1";
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
  <div class="posittions"> 当前位置：质量控制管理 &gt;&gt; 质控人员信息</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="listQualitySample" method="post" id="listQualitySampleForm" name="listQualitySampleForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>人员编号:</td>
	          <td>
	          	<s:textfield name="qualityControlSample.sampleNo" cssClass="text_one"/>
	          </td>
	          <td>人员姓名:</td>
	          <td>
	          	<s:textfield name="qualityControlSample.fullName" cssClass="text_one"/>
	          </td>
	          <td>
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
	          </td>
	        </tr>
	      </table>
	  </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>质控人员列表</legend>
      <div class="wf_right width500">
			<input type="button" id="addBtn" value="添  加" class="button_one" />
	      </div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>人员编号</th>
          <th>人员姓名</th>
          <th>性别</th>
          <th>警号</th>
          <th>职位</th>
          <th>所属单位</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="qualityControlSampleList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="sampleNo" /></td>
			<td><s:property value="fullName" /></td>
			<td><s:property value="genderName" /></td>
			<td><s:property value="policeNo" /></td>
			<td><s:property value="duty" /></td>
			<td><s:property value="organizationName" /><s:property value="organizationSubName" /></td>
			<td>
				<input type="hidden" value="<s:property value="id"/> "/>
				<a href="<%=path %>/pages/quality/intoQualitySample.action?qualityControlSample.id=<s:property value="id"/>&operateType=2"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查看" title="查看"/></a>
			</td>
		</tr>
     	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    </fieldset>
  </div>
</div>
</body>
</html>
