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
<title>样本Excel导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#importFileBtn").click(function(){

			var codisFileTxt = $("#sampleExcelFile").val();
			if(codisFileTxt == ""){
				alert("请选择Excel文件！");
				return;
			}

			$("#submitSampleExcelImportForm").submit();
		});

	});

	function delSample(obj, sampleId){
		if(confirm("确认删除该样本？")){
			$.ajax({
				url:"<%=path%>/pages/sample/deleteScanedSampleNo.action",
				type:"post",
				data:{"sampleInfo.id":sampleId},
				dataType:"json",
				success:function(data){
					if(data.success){
						$(obj).parent().parent().remove();
					}
				}
			});
		}
	}

	function backFtn(){
		document.location.href="<%=path%>/pages/sample/queryConsignment.action";
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 样本Excel导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>样本Excel导入</legend>
      <div class="height_one"></div>
      <s:form action="submitSampleExcelImport" name="submitSampleExcelImportForm" id="submitSampleExcelImportForm"
      	method="post" enctype="multipart/form-data">
      		<s:hidden name="consignment.id"/>
	      <table class="table_two width2 floatL marginL1">

	        <tr>
	          <td align="right">
	          	选择样本Excel文件：
	          </td>
	          <td align="left">
	          	<s:file name="sampleExcelFile" id="sampleExcelFile" cssClass="file_one width4" />
	          	&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/>
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          	<input type="button" value="返   回" class="button_one" onclick="backFtn();"/>
	          </td>
	        </tr>

	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>导入结果信息</legend>
      <div class="height_one"></div>
      <table class="table_two width2 floatL marginL1">
        <tr>
          	<td>导入文件名称:<span><s:property value="resultDetail['fileName']"/></span></td>
          	<s:if test="resultDetail['success']">
        		<td>样本总数：<span><s:property value="resultDetail['sampleCountInExcel']"/></span></td>
        		<td>导入成功样本数：<span><s:property value="resultDetail['importedSampleCount']"/></span></td>
          		<td>导入失败样本数：<span><s:property value="resultDetail['importFailedSampleCount']"/></span></td>
       	 	</s:if>
	        <s:else>
	          	<td colspan="3">
	          		Excel导入失败，原因：<s:property value="resultDetail['errorMessage']"/>
	          	</td>
	        </s:else>
        </tr>
      </table>
    </fieldset>

    <fieldset class="fieldset_one">
      <legend>导入失败样本列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>样本编号</th>
          <th>失败原因</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="importFailureList" status="importFailure">
      		<tr>
      			<td><s:property value="#importFailure.index+1"/></td>
      			<td><s:property value="sampleNo"/></td>
      			<td><s:property value="failureMsg"/></td>
      		</tr>
      	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    </fieldset>

    <fieldset class="fieldset_one">
      <legend>导入成功样本列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>样本编号</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="importSucceedList" status="importSucceed">
      		<tr>
      			<td><s:property value="#importSucceed.index+1"/></td>
      			<td><s:property value="sampleNo"/></td>
      			<td>
      				<a href="javascript:void(0);" onclick="delSample(this, '<s:property value="id"/>');" class="red">删除</a>
      			</td>
      		</tr>
      	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    </fieldset>

  </div>
</div>
</body>
</html>
