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
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#geneInfoTbl").append('<s:property escape="0" value="geneInfoHtmlStr"/>');

		$("#selectCodisSampleBtn").live("click", function(){
			$("iframe", "#dialog-div").attr("src", "<%=path%>/pages/quickcompare/intoSelectCodisSample.action");
			$("#dialog-div").dialog({
				autoOpen: true,
				height:	480,
				width: 420,
				modal: true,
				title: "检索CODIS并选择样本",
				close: function(){
					$(this).dialog("close");
				}
			});
		});

	});

	function initCodisSampleStrTbl(strInfo){
		var splitedStrInfoArr = strInfo.split(";");
		var alleleValue1Arr = $("input[name='alleleValue1']", "#geneInfoTbl");
		var alleleValue2Arr = $("input[name='alleleValue2']", "#geneInfoTbl");
		var alleleValue3Arr = $("input[name='alleleValue3']", "#geneInfoTbl");
		for(var i = 0; i < splitedStrInfoArr.length; i++){
			var locusGeneStrArr = splitedStrInfoArr[i].split("/");
			$(alleleValue1Arr.get(i)).val(locusGeneStrArr[0]);
			if(i == 0 && locusGeneStrArr[1] == ""){
				$(alleleValue2Arr.get(i)).val(locusGeneStrArr[0]);
			}else{
				$(alleleValue2Arr.get(i)).val(locusGeneStrArr[1]);
			}

			$(alleleValue3Arr.get(i)).val(locusGeneStrArr[2]);
		}

		$("#dialog-div").dialog("close");
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
  <div class="posittions"> 当前位置：快速比对  &gt;&gt; 单个样本比对</div>
  <div class="box_three">
    <div class="height_one"></div>
    <s:form action="submitSingleSampleCompare" id="submitSingleSampleCompareForm" method="post">
	    <fieldset class="fieldset_one">
			<legend>基因分型信息</legend>
				<table class="table_two marginL1" id="codisTbl">
					<tr>
						<td>选择CODIS样本：
							<input type="button" class="button_one" value="检索CODIS并选择样本" id="selectCodisSampleBtn"/>
						</td>
					</tr>
				</table>
				<table class="table_two width2 marginL1" id="geneInfoTbl">
				</table>
				<div class="height_one"></div>
		</fieldset>
		<fieldset class="fieldset_one">
			<legend>比对条件</legend>
				<table class="table_two width2 marginL1 marginBottom10" id="paramsTbl">
					<tr>
						<td>
							最少比中基因座个数：
							<s:select cssClass="select_one" name="sameLimit"
								list="#{6:6,7:7,8:8,9:9,10:10,11:11,12:12,13:13,14:14,15:15,16:16,17:17,18:18,19:19,20:20}">
								<s:param name="value">9</s:param>
							</s:select>
						</td>
						<td>
							最多差异基因座个数：
							<s:select cssClass="select_one" name="diffLimit"
								list="#{0:0,1:1,2:2,3:3,4:4,5:5}">
								<s:param name="value">1</s:param>
							</s:select>
						</td>
						<td>
							<input type="submit" value="提交比对" class="button_one"/>
						</td>
					</tr>
				</table>
			  	<div class="height_one"></div>
			  	<div class="height_one"></div>
		</fieldset>
	</s:form>
	<div class="height_one"></div>
  </div>

  <div id="dialog-div" style="display:none;">
  		<iframe src="" width="100%" frameborder="0" scrolling="auto" height="100%"></iframe>
  </div>
</div>
</body>
</html>
