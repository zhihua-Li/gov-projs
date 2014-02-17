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
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#importFileBtn").live("click", function(){

			var consignOrganizationIdVal = $("#consignOrganizationId").val();
			if(consignOrganizationIdVal == ""){
				alert("请选择委托单位！");
				return;
			}

			var codisFileTxt = $("#codisFile").val();
			if(codisFileTxt == ""){
				alert("请选择CODIS文件！");
				return;
			}

			$("#submitBatchCodisImportForm").submit();
		});

	});

	function checkAllFtn(){
		if($("#checkAll").attr("checked") && $("#checkAll").attr("checked") == "checked"){
			$("input[type='checkbox']", "tbody").attr("checked", "checked");
		}else{
			$("input[type='checkbox']:checked", "tbody").removeAttr("checked");
		}
	}

	function deleteImportedSample(){
		var checkedArr = $("input[type='checkbox']:checked", "tbody");
		if(checkedArr.length == 0){
			alert("未勾选或没有可删除样本！");
			return;
		}

		if(confirm("确认删除选中样本吗？")){
			var checkedSampleNo = "";
			for(var i = 0; i < checkedArr.length; i++){
				checkedSampleNo += $(checkedArr.get(i)).val() + ",";
			}

			checkedSampleNo = checkedSampleNo.substring(0, checkedSampleNo.length-1);

			$.ajax({
				url:"<%=path %>/pages/compare/deleteCodisSample.action",
				type:"post",
				data:{"checkedSampleNo": checkedSampleNo},
				cache:false,
				dataType:"json",
				success:function(data){
					if(data.success){
						for(var i = 0; i<checkedArr.length; i++){
							$(checkedArr.get(i)).parent().parent().remove();
						}
					}
				}
			});
		}
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
  <div class="posittions"> 当前位置：入库比对管理 &gt;&gt; CODIS样本批量导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>CODIS样本批量导入</legend>
      <div class="height_one"></div>
      <s:form action="submitBatchCodisImport" name="submitBatchCodisImportForm" id="submitBatchCodisImportForm"
      	method="post" enctype="multipart/form-data">
	      <table class="table_two width2 floatL marginL1">
	      	<tr>
	      		<td align="right">
		          	委托单位<span style="color:red;">*</span>：
		        </td>
		        <td align="left">
		      		<s:select name="consignOrganizationId" id="consignOrganizationId" cssClass="select_one" list="consignOrganizationList" listKey="id" listValue="organizationName" headerKey="" headerValue="请选择..."/>
	      		</td>
	      	</tr>
			<tr>
				<td align="right">
					试剂盒<span style="color:red;">*</span>：
				</td>
				<td align="left">
					<s:select list="reagentKitList" name="reagentKitId" listKey="id" listValue="reagentName" cssClass="width5"></s:select>
				</td>
			</tr>
	        <tr>
	          <td align="right">
	          	CODIS文件：
	          </td>
	          <td align="left">
	          	<s:file name="codisFile" id="codisFile" cssClass="file_one width4" />
	          	&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/></td>
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
      <div class="wf5" style="margin-right:20px;">
      	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
    	<input type="button" class="button_one" onclick="deleteImportedSample();" value="删除选中项"/>
    	</div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>委托单位</th>
          <th>板号</th>
          <th>样本编号</th>
          <th>导入结果</th>
          <th>
          	删 除
          </th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="importResultList" status="importResult">
      		<tr>
      			<td><s:property value="#importResult.index+1"/></td>
      			<td><s:property value="consignOrganizationName"/></td>
      			<td><s:property value="boardNo"/></td>
      			<td><s:property value="sampleNo"/></td>
      			<td>
      				<s:if test="flag == 1">
      					<s:property value="message"/>
      				</s:if>
      				<s:else>
      					<span style="color:red;"><s:property value="message"/></span>
      				</s:else>
      			</td>
      			<td>
      				<s:if test="flag == 1">
      					<input type="checkbox" value='<s:property value="sampleNo"/>'/>
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
</body>
</html>
