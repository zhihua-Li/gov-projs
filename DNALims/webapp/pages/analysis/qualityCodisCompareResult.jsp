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
<title>CODIS导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript">
	
	function viewQuailtyResult(obj){
		var qualitySampleGeneStr = $("input[name='qualitySampleGeneStr']", $(obj).parent().parent()).val();
		var matchSampleGeneStr = $("input[name='matchSampleGeneStr']", $(obj).parent().parent()).val();
		$("#resultDetailDivIframe").attr("src", "<%=path %>/pages/analysis/viewQualityCompareGene.action?qualitySampleGeneStr=" + qualitySampleGeneStr + "&matchSampleGeneStr=" + matchSampleGeneStr);
		$("#resultDetailDiv").dialog({
			autoOpen: true,
			height:	540,
			width: 840,
			modal: true,
			title: "查看比对信息",
			close: function(){
				$(this).dialog("close");
			}
		});
	}
	
	function exportExcel(){
		
	}
	
	function backFtn(){
		location.href="<%=path %>/pages/analysis/intoCodisImport.action";
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
  <div class="posittions"> 当前位置：检验结果管理 &gt;&gt; 结果导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>CODIS文件信息</legend>
      <div class="height_one"></div>
      <table class="table_two width2 floatL marginL1">
        <tr>
          <td>板     号：<span><s:property value="boardNo"/></span></td>
          <td>CODIS文件名称:<span><s:property value="resultDetail['fileName']"/></span></td>
          <td>CODIS样本总数:<span><s:property value="resultDetail['codisSampleCount']"/></span></td>
          <td><input type="button" value="返回" class="button_one" onclick="backFtn();"/></td>
        </tr>
        <s:if test="importSucceedFlag == 0">
        	<tr>
	          	<td colspan="3">
	          		CODIS导入失败，原因：<s:property value="resultDetail['errorMessage']"/>
	          	</td>
        	</tr>
        </s:if>
      </table>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>质检样本比对结果列表</legend>
      <%--
      <div class="wf5" style="margin-right:20px;">
     	<button class="button_one" onclick="exportExcel();"><img src="<%=path %>/images/excel_32.png" width="22" height="22" style="vertical-align:middle;" alt="导出Excel" title="导出Excel"/>导出Excel</button>
  	  </div>
  	   --%>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>样本编号</th>
          <th>比对板号</th>
          <th>比对样本编号</th>
          <th>比对状态</th>
          <th>异常信息</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="qualityCompareResultList" status="qualityCompareResult">
      		<tr>
      			<td><s:property value="#qualityCompareResult.index+1"/></td>
      			<td>
      				<s:property value="sampleNo"/>
      				<input type="hidden" name="qualitySampleGeneStr" value='<s:property value="qualitySampleGeneStr"/>' />
      			</td>
      			<s:if test="compareFlag == 1">
	      			<td><s:property value="matchBoardNo"/></td>
	      			<td>
	      				<s:property value="matchSampleNo"/>
      					<input type="hidden" name="matchSampleGeneStr" value='<s:property value="matchSampleGeneStr"/>' />
	      			</td>
	      			<td>
	      				<s:if test="matchFlag == 1">
	      					比中
	      				</s:if>
	      				<s:else>
	      					未比中
	      				</s:else>
	      			</td>
	      			<td>-- --</td>
      			</s:if>
      			<s:else>
      				<td>-- --</td>
      				<td>-- --</td>
      				<td>-- --</td>
      				<td><s:property value="hintMessage"/></td>
      			</s:else>
      			<td>
      				<a href="javascript:void(0);" onclick="viewQuailtyResult(this);" class="red">查看</a>
      			</td>
      		</tr>
      	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    </fieldset>
  </div>
</div>

<div style="display:none;" id="resultDetailDiv">
	<iframe id="resultDetailDivIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>
</body>
</html>
