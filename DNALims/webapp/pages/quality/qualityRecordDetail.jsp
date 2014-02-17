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
<title>比中详情查看</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#backBtn").live("click", function(){

			location.href="<%=path %>/pages/quality/queryPolluteRecord.action";

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
<div class="main" style="overflow:hidden;">
  <div class="box_three">
    	<s:hidden name="polluteRecordView.id"/>" />
	    <fieldset class="fieldset_one">
	      <legend>比中信息</legend>
	      <div class="height_one"></div>
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>比中时间：</td>
	          <td colspan="3">
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:date name="polluteRecordView.matchDate" format="yyyy-MM-dd HH:mm"/>" />
	          </td>
	        </tr>
	        <tr>
	          <td>比中基因座个数：</td>
	          <td>
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:property value="polluteRecordView.sameNum"/>" />
	          </td>
	          <td>差异基因座个数：</td>
	          <td>
	          	<input type="text" readonly="readonly" class="text_one"
	          		value="<s:property value="polluteRecordView.diffNum"/>" />
	          </td>
	        </tr>
	        <tr>
	        	<td>备     注：</td>
	        	<td colspan="2">
					<textarea class="textarea_width300" rows="3"><s:property value="polluteRecordView.remark"/></textarea>
	        	</td>
	        	<td align="right">
					<input type="button" class="button_one" id="backBtn" value="返   回" />
	        	</td>
	        </tr>
	      </table>
	    </fieldset>
		<fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>
			<div style="width:600px; text-align:left;">
				<div id="sample1Div" style="width:400px; text-align:center; float:left;display:inline;">
					<table class="table_one">
						<tr>
							<th>&nbsp;</th>
							<th style="width:200px;">质控人员样本</td>
						</tr>
						<tr>
							<th>样本编号</th>
							<td><s:property value="qualityControlSampleView.sampleNo"/></td>
						</tr>
						<s:iterator value="%{allLocusInfoList}" status="stu">
							<tr>
								<th>
									<s:property value="genoName" />
								</th>
								<td>
									<s:property value="%{sourceGenotypeInfoList[#stu.index]}" />
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<div id="sample2Div" style="width:200px; text-align:left; float:right;display:inline; display:inline; margin-left:-10px;">
					<table class="table_one">
						<tr>
							<th>比中样本</td>
						</tr>
						<tr>
							<td><s:property value="sampleSourceGeneView.sampleNo"/></td>
						</tr>
						<s:iterator value="matchGenotypeInfoList">
							<tr>
								<td>
									<s:property />
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
		    <div class="height_one"></div>
	    </fieldset>
  </div>
</div>
</body>
</html>
