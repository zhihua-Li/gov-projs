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
<title>批量受理样本</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#submitSampleBatchAcceptForm").submit(function(){

			var barcodePrefix = $("#barcodePrefix").val();
			if(barcodePrefix == ""){
				alert("请输入条码号前缀！");
				return false;
			}

			var barcodeValStart = $("#barcodeValStart").val();
			var barcodeValEnd = $("#barcodeValEnd").val();
			if(barcodeValStart == "" || barcodeValEnd == ""){
				alert("请输入流水号起止范围！");
				return false;
			}

			return true;
		});

	});


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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 样本受理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>样本批量受理</legend>
      <div class="height_one"></div>
      <s:form action="submitSampleBatchAccept" name="submitSampleBatchAcceptForm"
      	id="submitSampleBatchAcceptForm" method="post">
      		<s:hidden name="consignment.id"/>
	      <table class="table_two width2 floatL marginL1">

	        <tr>
	          <td align="right">
	          	条码号前缀<span style="color:red">*</span>：
	          </td>
	          <td>
	          	<s:textfield name="barcodePrefix" id="barcodePrefix" cssClass="text_one width4" />
	          </td>
	        </tr>
	        <tr>
	          <td align="right">
	          	流水号长度：
	          </td>
	          <td>
	          	<s:select name="serialNoLength" id="serialNoLength" cssClass="select_one width4" list="#{2:'2',3:'3',4:'4',5:'5'}" value="4"/>
	          </td>
	        </tr>
	        <tr>
	        	<td align="right">
	        		流水号范围<span style="color:red">*</span>：
	        	</td>
	        	<td>
	        		<s:textfield name="barcodeValStart" id="barcodeValStart" cssClass="text_one"/>
	        		&nbsp;&nbsp;&nbsp;&nbsp;
	        		至
	        		&nbsp;&nbsp;&nbsp;&nbsp;
	        		<s:textfield name="barcodeValEnd" id="barcodeValEnd" cssClass="text_one"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="right">
	        		<s:submit value="提   交" cssClass="button_one"/>
	        	</td>
	        	<td>&nbsp;</td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
  </div>
</div>
</body>
</html>
