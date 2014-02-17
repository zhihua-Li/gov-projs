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
<title>检验记录管理</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript">
	function viewResult(obj){
		var boardId = $("#hiddenBoardId").val();
		var sampleId = $("input[type='hidden']", $(obj).parent()).val();

		$("#resultDetailDivIframe").attr("src", "<%=path %>/pages/analysis/intoSampleSourceGeneDetail.action?sampleSourceGeneQuery.sampleId=" + sampleId + "&sampleSourceGeneQuery.boardId=" + boardId);
		$("#resultDetailDiv").dialog({
			autoOpen: true,
			height:	540,
			width: 840,
			modal: true,
			title: "结果复核",
			close: function(){
				$(this).dialog("close");
			}
		});
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
  <div class="posittions"> 当前位置：检验结果管理  &gt;&gt; 结果复核</div>
  <div class="box_three">
    <div class="height_one"></div>

    <fieldset class="fieldset_one">
      <legend>样本质检情况列表</legend>
      <s:form action="" method="post" id="batchReviewForm">
	      <table class="table_one">
		      <thead>
		        <tr>
		          <th>序号</th>
		          <th>样本编号</th>
		          <th>受理状态</th>
		          <th>受理人</th>
		          <th>受理时间</th>
		          <th>板号</th>
		          <th>检验类型</th>
		          <th>质检结果</th>
		          <th>操作</th>
		        </tr>
		      </thead>
		      <tbody id="listTbody">
		        <s:iterator value="qualityCheckNotPassList" var="notPassSampleList" status="su">
		        	<s:iterator value="#notPassSampleList" status="innerSta">
		        		<tr>
		        			<s:if test="#innerSta.index == 0">
		        				<td rowspan="3"><s:property value="#su.index + 1"/></td>
		        				<td rowspan="3"><s:property value="sampleNo" /></td>
			        			<td rowspan="3">
									<s:if test="acceptStatus == 0">未受理</s:if>
									<s:if test="acceptStatus == 1">已受理</s:if>
								</td>
								<td rowspan="3">
									<s:if test="acceptStatus == 0">-- --</s:if>
									<s:if test="acceptStatus == 1"><s:property value="acceptUserName"/></s:if>
								</td>
								<td rowspan="3">
									<s:if test="acceptStatus == 0">-- --</s:if>
									<s:if test="acceptStatus == 1"><s:date name="acceptDate" format="yyyy-MM-dd HH:mm:ss"/></s:if>
								</td>
		        			</s:if>
							<td><s:property value="boardNo" /></td>
							<td><s:property value="examineTypeName" /></td>
							<s:if test="#innerSta.index == 0">
								<td rowspan="3"><span style="color:red;">质检未通过</span></td>
								<td rowspan="3">
									<input type="hidden" value="<s:property value="id"/>"/>
									<a href="javascript:void(0)" onclick="viewResult(this);" class="red">查 看</a>
								</td>
							</s:if>
		        		</tr>
		        	</s:iterator>
		     	</s:iterator>

				<s:set var="notpassListSize" value="qualityCheckNotPassList.size()"></s:set>
		     	<s:iterator value="qualityCheckPassedList" var="passedSampleList" status="su1">
		        	<s:iterator value="#passedSampleList" status="innerSta2">
		        		<tr>
							<s:if test="#innerSta2.index == 0">
		        				<td rowspan="3"><s:property value="#notpassListSize + #su1.index + 1"/></td>
		        				<td rowspan="3"><s:property value="sampleNo" /></td>
			        			<td rowspan="3">
									<s:if test="acceptStatus == 0">未受理</s:if>
									<s:if test="acceptStatus == 1">已受理</s:if>
								</td>
								<td rowspan="3">
									<s:if test="acceptStatus == 0">-- --</s:if>
									<s:if test="acceptStatus == 1"><s:property value="acceptUserName"/></s:if>
								</td>
								<td rowspan="3">
									<s:if test="acceptStatus == 0">-- --</s:if>
									<s:if test="acceptStatus == 1"><s:date name="acceptDate" format="yyyy-MM-dd HH:mm:ss"/></s:if>
								</td>
		        			</s:if>
							<td><s:property value="boardNo" /></td>
							<td><s:property value="examineTypeName" /></td>
							<s:if test="#innerSta2.index == 0">
								<td rowspan="3">质检通过</td>
								<td rowspan="3">
									<input type="hidden" value="<s:property value="id"/>"/>
									<a href="javascript:void(0)" onclick="viewResult(this);" class="red">查 看</a>
								</td>
							</s:if>

		        		</tr>
		        	</s:iterator>
		     	</s:iterator>
		      </tbody>
	    </table>
	    <div class="height_one"></div>
    </s:form>
    </fieldset>
  </div>
</div>

<div style="display:none;" id="resultDetailDiv">
	<iframe id="resultDetailDivIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>
</body>
</html>
