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
	$(document).ready(function(){

		$("#importFileBtn").live("click", function(){

			var codisFileTxt = $("#codisFile").val();
			if(codisFileTxt == ""){
				alert("请选择CODIS文件！");
				return;
			}

			$("#submitCodisImportForm").submit();
		});

		//TODO -
		$("#importFolderBtn").live("click", function(){
			return false;
		});

	});

	function viewImportedSample(boardNo, sampleNo){

		$("#resultDetailDivIframe").attr("src", "<%=path %>/pages/analysis/viewImportedSampleGene.action?boardNo=" + boardNo + "&sampleNo=" + sampleNo);
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
      <legend>结果导入</legend>
      <div class="height_one"></div>
      <s:form action="submitCodisImport" name="submitCodisImportForm" id="submitCodisImportForm"
      	method="post" enctype="multipart/form-data">
	      <table class="table_two width2 floatL marginL1">
	      	<tr>
	      		<td align="right">
		          	输入板号<span style="color:red;">*</span>：
		        </td>
		        <td align="left">
		      		<s:textfield cssClass="text_one" name="boardNo" id="boardNoTxt" />
	      		</td>
	      	</tr>
			<tr>
			<tr>
				<td align="right">
					试剂盒<span style="color:red;">*</span>：
				</td>
				<td align="left">
					<s:select list="reagentKitList" name="reagentKitId" listKey="id" listValue="reagentName" cssClass="width5"></s:select>
				</td>
			</tr>
	          <td align="right">
	          	CODIS文件：
	          </td>
	          <td align="left">
	          	<s:file name="codisFile" id="codisFile" cssClass="file_one width4" />&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>导入结果信息</legend>
      <div class="height_one"></div>
      <table class="table_two width1 floatL marginL1">
        <tr>
          <td>导入文件名称:<span><s:property value="resultDetail['fileName']"/></span></td>
          <td>导入成功样本数:<span><s:property value="resultDetail['importedSampleCount']"/></span></td>
          <td>导入失败样本数:<span><s:property value="resultDetail['importFailedSampleCount']"/></span></td>
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
      <legend>导入结果列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>委托号</th>
          <th>样本编号</th>
          <th>导入说明</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="importResultList" status="importResult">
      		<tr>
      			<td><s:property value="#importResult.index+1"/></td>
      			<td><s:property value="consignmentNo"/></td>
      			<td><s:property value="sampleNo"/></td>
      			<td><s:property value="message"/></td>
      			<td>
      				<s:if test="flag == 1">
      					<a href="javascript:void(0);" onclick="viewImportedSample('<s:property value="boardNo"/>', '<s:property value="sampleNo"/>')"
      						class="red">查看</a>
      				</s:if>
      				<s:else>
      					-- --
      				</s:else>
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
