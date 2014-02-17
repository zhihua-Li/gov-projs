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
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#geneInfoTbl").append('<s:property escape="0" value="sampleSourceGeneView.geneInfoHtmlForPage"/>');

	//	$("input[name='alleleValue1']").attr("readOnly", "true");
	//	$("input[name='alleleValue2']").attr("readOnly", "true");

		$("#saveUpdatedCodisSampleGeneForm").submit(function(){
			$(this).ajaxSubmit({
				target: "#saveUpdatedCodisSampleGeneForm",
				url: "<%=path %>/pages/analysis/submitReview.action",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						window.parent.$("#resultDetailDiv").dialog("close");
					}else{
						alert(jsonData.errorMsg);
					}

				},
				error: function(e){
				}

			});

			return false;
		});

		$("#clostBtn").live("click", function(){
			window.parent.$("#resultDetailDiv").dialog("close");
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
    <s:form action="saveUpdatedCodisSampleGene" id="saveUpdatedCodisSampleGeneForm" method="post">
    	<s:hidden name="operateType"/>
    	<input type="hidden" name="sampleSourceGene.id" id="geneId" value="<s:property value="sampleSourceGeneView.id"/>" />
    	<input type="hidden" name="sampleSourceGene.sampleId" value="<s:property value="sampleSourceGeneView.sampleId"/>" />
    	<input type="hidden" name="sampleSourceGene.geneType" value="<s:property value="sampleSourceGeneView.geneType"/>" />
    	<input type="hidden" name="sampleSourceGene.boardId" value="<s:property value="sampleSourceGeneView.boardId"/>" />
    	<input type="hidden" name="sampleSourceGene.alignDbFlag" value="<s:property value="sampleSourceGeneView.alignDbFlag"/>" />
    	<input type="hidden" name="sampleSourceGene.reviewStatus" value="<s:property value="sampleSourceGeneView.reviewStatus"/>" />
    	<input type="hidden" name="sampleSourceGene.reviewDate" value="<s:property value="sampleSourceGeneView.reviewDate"/>" />
    	<input type="hidden" name="sampleSourceGene.reviewUser" value="<s:property value="sampleSourceGeneView.reviewUser"/>" />

	    <fieldset class="fieldset_one">
	      <legend>样本信息</legend>
	      <div class="height_one"></div>
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>委托号(板号)：</td>
	          <td>
	          	<input type="text" value="<s:property value="sampleSourceGeneView.consignmentNo"/>" readonly="readonly" class="text_one"/>
	          </td>
	          <td>样本编号：</td>
	          <td>
	          	<input type="text" value="<s:property value="sampleSourceGeneView.sampleNo"/>" readonly="readonly" class="text_one"/>
	          </td>
	        </tr>
	      </table>
	    </fieldset>
	    <fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>
	      	<table class="table_two width2" id="geneInfoTbl">
		        <tr>
		          <td>试剂盒:</td>
		          <td>
	          		<s:select name="sampleSourceGene.reagentKitId" id="reagentKitId" list="reagentKitList" listKey="id" listValue="reagentName"
		          		headerKey="" headerValue="" cssClass="select_one">
		          		<s:param name="value">
		          			<s:property value="sampleSourceGeneView.reagentKitId"/>
		          		</s:param>
		          	</s:select>
		          </td>
		          <td colspan="2">
		          	<s:if test="alignDbFlag == 1">
						<input type="button" class="button_one" id="clostBtn" value="关   闭" />
		          	</s:if>
					<s:else>
						<s:submit cssClass="button_one" value="保   存"/>
						&nbsp;
						<input type="button" class="button_one" id="clostBtn" value="关   闭" />
					</s:else>
		          </td>
		        </tr>
	      	</table>
		    <div class="height_one"></div>
	    </fieldset>
    </s:form>
  </div>
</div>
</body>
</html>
