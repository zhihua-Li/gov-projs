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
<title>污染记录查询</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript">
	//查看污染详情
	function viewDetial(polluteRecordId){
		location.href="<%=path %>/pages/quality/intoQualityRecordDetail.action?polluteRecordQuery.id=" + polluteRecordId;
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
  <div class="posittions"> 当前位置：质量控制管理 &gt;&gt; 污染记录查询</div>
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
	          	<s:textfield name="polluteRecordQuery.sampleNo" cssClass="text_one"/>
	          </td>
	          <td>人员姓名:</td>
	          <td>
	          	<s:select list="qualityControlSampleList" name="polluteRecordQuery.qcpId"
	          		listKey="id" listValue="fullName" headerKey="" headerValue="" cssClass="select_one"/>
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
      <legend>污染记录列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>样本编号</th>
          <th>人员姓名</th>
          <th>比中样本编号</th>
          <th>比中时间</th>
          <th>匹配个数</th>
          <th>差异个数</th>
          <th>比中详情</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="polluteRecordViewList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="qualitySampleNo" /></td>
			<td><s:property value="qualitySampleName" /></td>
			<td><s:property value="matchSampleNo" /></td>
			<td><s:date name="matchDate" format="yyyy-MM-dd HH:mm" /></td>
			<td><s:property value="sameNum" /></td>
			<td><s:property value="diffNum" /></td>
			<td>
				<input type="hidden" value="<s:property value="id"/> "/>
				<a href="javascript:void(0);" class="red" onclick="viewDetial('<s:property value="id"/>');"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查看" title="查看"/></a>
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
