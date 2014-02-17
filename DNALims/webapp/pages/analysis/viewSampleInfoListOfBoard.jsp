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
			title: "样本基因型",
			close: function(){
				$(this).dialog("close");
			}
		});
	}

	function viewReviewDesc(reviewDesc){
		var dialogDiv = $("<div><br/><div style='float:left; display:inline;'>复核不通过原因：</div><div style='float:right; display:inline;'><textarea style='width:320px;' rows='5' id='reviewDescTxt'>"
					+ reviewDesc + "</textarea></div></div>");
		dialogDiv.dialog({
			autoOpen:true,
			height:240,
			width:460,
			modal:true,
			title:"查看复核不通过原因",
			buttons:{
				"确定":function(){
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
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
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="intoSampleSourceGeneList" method="post" id="intoSampleSourceGeneListForm" name="intoSampleSourceGeneListForm">
	      <table class="table_two width7 floatL marginL1">
	        <tr>
	        	<td>委托号：</td>
	        	<td>
	        		<label><s:property value="boardInfo.consignmentNo"/></label>
	        	</td>
	        	<td>板号：</td>
	        	<td>
	        		<label><s:property value="boardInfo.boardNo"/></label>
	        	</td>
	         	<td>样本编号：</td>
	          	<td>
	      			<s:textfield cssClass="text_one" name="sampleSourceGeneQuery.sampleNo"/>
	          	</td>
	          	<td>
	          		<s:hidden name="sampleSourceGeneQuery.boardId" id="hiddenBoardId"/>
	          		<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	          	</td>
	        </tr>
	      </table>
	   </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>样本列表</legend>
      <s:form action="" method="post" id="batchReviewForm">

	      <table class="table_one">
		      <thead>
		        <tr>
		           <th>序号</th>
		          <th>样本编号</th>
		          <th>受理状态</th>
		          <th>受理人</th>
		          <th>受理时间</th>
		          <th>复核状态</th>
		          <th>查看结果</th>
		        </tr>
		      </thead>
		      <tbody id="listTbody">
		        <s:iterator value="sampleSourceGeneViewList" status="su">
					<tr>
						<td><s:property value="#su.index + 1"/></td>
						<td><s:property value="sampleNo" /></td>
						<td>
							<s:if test="acceptStatus == 0">
								未受理
							</s:if>
							<s:if test="acceptStatus == 1">
								已受理
							</s:if>
						</td>
						<td>
							<s:if test="acceptStatus == 0">
								-- --
							</s:if>
							<s:if test="acceptStatus == 1">
								<s:property value="acceptUserName"/>
							</s:if>
						</td>
						<td>
							<s:if test="acceptStatus == 0">
								-- --
							</s:if>
							<s:if test="acceptStatus == 1">
								<s:date name="acceptDate" format="yyyy-MM-dd HH:mm:ss"/>
							</s:if>
						</td>
						<td>
							<s:if test="reviewStatus == 0">
								未复核
							</s:if>
							<s:if test="reviewStatus == 1">
								复核通过
							</s:if>
							<s:if test="reviewStatus == 2">
								<span style="color:red;">复核未通过</span>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="viewReviewDesc('<s:property value="reviewDesc"/>');"><img src="<%=path %>/images/question_blue_32.png" width="24" height="24" style="vertical-align:middle;" alt="不通过原因" title="不通过原因"/></a>
							</s:if>
						</td>
						<td>
							<input type="hidden" value="<s:property value="id"/>"/>
							<a href="javascript:void(0)" onclick="viewResult(this);" class="red"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
						</td>
					</tr>
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
