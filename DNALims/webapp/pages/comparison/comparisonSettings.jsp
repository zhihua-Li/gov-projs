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
<title>比对参数设置</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
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
  <div class="posittions"> 当前位置：入库比对管理  &gt;&gt; 比对参数设置</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>比对参数</legend>
      <div class="height_one"></div>
      <s:form action="submitComparisonSettings" name="submitComparisonSettingsForm" id="submitComparisonSettingsForm" method="post">
      	<s:hidden name="comparisonSettings.id"/>
      	<s:hidden name="comparisonSettings.defaultSameLowerLimitNum"/>
      	<s:hidden name="comparisonSettings.defaultUpperLimitNum"/>

	      <table class="table_two width3 floatL marginL1">
	        <tr>
	          <td>最少匹配基因座个数：</td>
	          <td>
	          	<s:select name="comparisonSettings.sameLowerLimitNum" cssClass="select_one"
	          		list="#{'6':'6', '7':'7', '8':'8','9':'9','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16'}" />
	          </td>
	        </tr>
	        <tr>
	          <td>最多差异基因座个数：</td>
	          <td>
	          	<s:select name="comparisonSettings.diffUpperLimitNum" cssClass="select_one"
	          		list="#{'0':'0', '1':'1', '2':'2','3':'3','4':'4'}" />
	          </td>
	        </tr>
	        <tr>
	          <td>备 注：</td>
	          <td>
	          	<s:textarea name="comparisonSettings.remark" rows="3" cssClass="textarea_width300" />
	          </td>
	        </tr>
	        <tr>
	          <td colspan="4" align="left">
	          	<s:submit cssClass="button_one" value="保    存"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
  </div>
</div>
</body>
</html>
