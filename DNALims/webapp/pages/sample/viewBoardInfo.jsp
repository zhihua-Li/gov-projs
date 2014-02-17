<%@ page language="java" pageEncoding="UTF-8"%>
<%@	taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript">
	var pathStr = "<%=path %>";
</script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/punchExtractionInfo.js"></script>

<script type="text/javascript">
		$(document).ready(function(){
			$("#closeLeftImg", parent.document.body).click();

			var boardInfoJson = eval("(" + $("#boardInfoJsonStr").val() + ")");
			var resultList = boardInfoJson.sampleList;
			var htmlStr1 = "";
			for(var i = 0; i < resultList.length; i++){
				htmlStr1 += "<tr><td class=\"td1\">" + resultList[i].prefix + "</td>";
				var samplesLine = resultList[i].samples;
				for(var j = 0; j < samplesLine.length; j++){
					htmlStr1 += "<td id=\"" + samplesLine[j].positionNo + "\"><a name=\"#" + samplesLine[j].positionNo + "\"></a><textarea name=\"sampleNo\" class=\"textarea_te\" title=\"" + samplesLine[j].sampleNo + "\"";
					if(samplesLine[j].positionNo == "B01"
							|| samplesLine[j].positionNo == "H03"
							|| samplesLine[j].positionNo == "H10"){
						htmlStr1 += " readonly=\"readonly\" style=\"display:none;\"></textarea>";

						if(samplesLine[j].positionNo == "B01"){
							htmlStr1 += "9947";
						}

						if(samplesLine[j].positionNo == "H03"){
							htmlStr1 += "Ladder";
						}

						if(samplesLine[j].positionNo == "H10"){
							htmlStr1 += "Ladder";
						}
					}else{
						if(samplesLine[j].positionNo == "A01"){
							if(samplesLine[j].sampleNo == "" || samplesLine[j].sampleNo == "CK"){
								htmlStr1 += " id=\"ckText\" style=\"display:none;\"></textarea>";
								htmlStr1 += "<span id=\"ckSpan\">CK</span>";
							}else{
								htmlStr1 += " id=\"ckText\">" + samplesLine[j].sampleNo + "</textarea><span id=\"ckSpan\" style=\"display:none;\">CK</span>";
								$("#unlockCK").attr("checked","checked");
							}
						}else{
							htmlStr1 += ">" + samplesLine[j].sampleNo + "</textarea>";
						}
					}

					htmlStr1 += "<input type=\"hidden\" name=\"positionNo\" value=\"" + samplesLine[j].positionNo + "\"/></td>";
				}

				htmlStr1 += "</tr>";
			}

			$("tr:gt(0)", "#samplePositionTbl").remove();
			$("#samplePositionTbl").append(htmlStr1);

			$("#workstationFile").change(function(){
				var filepath = $(this).val();
				var filename = filepath.substring(filepath.lastIndexOf("\\") + 1);

				$("#hiddenWorkstationFilename").val(filename);
			});

			$("#submitPunchExtractionForm").submit(function(){

				$(this).ajaxSubmit({
					target: "#submitPunchExtractionForm",
					success: function(jsonData){
						if(jsonData.success){
							$("#hiddenBoardId").val(jsonData.boardInfoId);
							$("#hiddenOperateType").val(jsonData.operateType);
							alert("保存成功！");

							$(".text_te").removeAttr("disabled");

							var firstPosition = $("input[name='positionNo'][value='C01']");
							$("textarea[name='sampleNo']", firstPosition.parent()).focus();
						}else{
							alert(jsonData.errorMsg);
						}
					},
					error: function(e){
					},
					url: "<%=path %>/pages/sample/submitPunchExtraction.action",
					type: "post",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType: "json"
				});

				return false;
			});

			$("textarea", "#samplePositionTbl").keydown(function(){
		    	if(event.keyCode == 13) {
		    		scaneSampleBarcodeAndSave($(this));
		    		return false;
		    	}
		    });

			$("textarea", "#samplePositionTbl").click(function(){
				if($(this).val() != ""){
					$(this).select();
				}
		    });

			$("#unlockCK").live("click", function(){
				if($(this).attr("checked") && $(this).attr("checked") == "checked"){
					$("#ckText").css("display","");
					$("#ckSpan").css("display","none");
				}else{
					if($("#ckText").val() != ""){
						var hiddenBoardId = $("#hiddenBoardId").val();
		    			$.ajax({
		    				type: "post",
		    				url:"<%=path %>/pages/sample/deleteCKSample.action",
		    				data:{"boardInfo.id":hiddenBoardId},
		    				dataType:"json",
		    				success:function(jsonData){
		   						if(jsonData.success){
		   							$("#ckText").val("");
		   							$("#ckText").css("display","none");
		   							$("#ckSpan").css("display","");
		   						}
		    				}
		    			});
					}
				}
			});
		});

		function backFtn(){
			$("#queryExtractionTasksForm").submit();
		}

	</script>
<style>
html, body {
	overflow:hidden;
	width:100%;
	height:100%;
}
</style>
</head>

<body>
<div class="main">
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 取样任务详情</div>
  <div class="box_three">
    <div class="height_one"></div>
<%--     <img id="loading" src="<%=path %>/images/loading.gif" style="display:none;"> --%>
    <input type="hidden" id="boardInfoJsonStr" value="<s:property value="jsonString"/>"/>
    <s:form action="submitPunchExtraction" id="submitPunchExtractionForm" name="submitPunchExtractionForm" method="post">
    	<s:hidden name="boardInfo.id" id="hiddenBoardId"></s:hidden>
	    <s:hidden name="boardInfo.workstationFilename" id="hiddenWorkstationFilename" />
	    <s:hidden name="boardInfo.importUser"/>
	    <s:hidden name="boardInfo.importDate"/>
	    <s:hidden name="operateType"/>
	    <s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	    <s:hidden name="boardInfoQuery.importDateStart"/>
	    <s:hidden name="boardInfoQuery.importDateEnd"/>
	    <s:hidden name="boardInfoQuery.importUser"/>
	    <table class="table_two table_two_left width2">
	    	<tr>
	    		<td>输入委托号<span style="color:red;">*</span>：</td>
		        <td>
		        	<s:textfield name="boardInfo.consignmentNo" id="consignmentNoTxt" cssClass="text_one" readonly="true"/>
		        </td>
	    		<td>输入板号<span style="color:red;">*</span>：</td>
	    		<td>
	    			<s:textfield name="boardInfo.boardNo" id="boardNoTxt" cssClass="text_one" />
	    		</td>
			</tr>
			<tr>
				<td>工作站文件：</td>
	        	<td colspan="3">
					<s:file name="workstationFile" id="workstationFile" cssClass="text_nowidth width4" disabled="true"></s:file>
		        	&nbsp;
		        	<input name="button" type="button" class="button_one" onclick="importWorkstationFile();" value="导   入" disabled="disabled"/>
		        </td>
		    </tr>
	      <tr>
	        <td>无条码数量：</td>
	        <td>
	        	<s:if test="boardInfo.barcodelessNum == 0">
					<s:textfield name="boardInfo.barcodelessNum" cssClass="text_one" value="" />
	        	</s:if>
	        	<s:else>
	        		<s:textfield name="boardInfo.barcodelessNum" cssClass="text_one" />
	        	</s:else>
	        </td>
	        <td>无样本数量：</td>
	        <td>
	        	<s:if test="boardInfo.samplelessNum == 0">
					<s:textfield name="boardInfo.samplelessNum" cssClass="text_one" value="" />
	        	</s:if>
	        	<s:else>
	        		<s:textfield name="boardInfo.samplelessNum" cssClass="text_one" />
	        	</s:else>
	        </td>
	      </tr>
	      <tr>
	        <td>取样孔径<span style="color:red;">*</span>：</td>
	        <td id="holeDiameterTD" colspan="3">
	        	<s:checkboxlist name="boardInfo.holeDiameterArr" list="holeDiameterList" listKey="dictKey" listValue="dictValue" />
	        	<s:if test="boardInfo.holeDiameterOther != null">
	        		<input type="checkbox" id="otherHoleDiameterRadio" checked="checked" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_one"/>
	        	</s:if>
				<s:else>
					<input type="checkbox" id="otherHoleDiameterRadio" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_one" />
				</s:else>
	        </td>
	      </tr>
	      <tr>
	        <td>检验类型：</td>
	        <td id="examineTypeTD" colspan="3">
	        	<s:radio cssClass="radio_one" name="boardInfo.examineType" list="examineTypeList" listKey="dictKey" listValue="dictValue"></s:radio>
	        </td>
	      </tr>
	      <tr>
	      	<td colspan="4">
	      		<input type="checkbox" id="unlockCK" />&nbsp;&nbsp;<span style="color:red;">是否解除CK锁定</span>
	      		<input type="hidden" id="importFlag" value="0"/>
	      	</td>
	      </tr>
	    </table>
	    <div class="height_one"></div>
	    <table class="table_te fids" id="samplePositionTbl">
	    	<tr>
		        <td class="td2">位置</td>
		        <td class="td1">1</td>
		        <td class="td1">2</td>
		        <td class="td1">3</td>
		        <td class="td1">4</td>
		        <td class="td1">5</td>
		        <td class="td1">6</td>
		        <td class="td1">7</td>
		        <td class="td1">8</td>
		        <td class="td1">9</td>
		        <td class="td1">10</td>
		        <td class="td1">11</td>
		        <td class="td1">12</td>
		      </tr>
	    </table>
	    <div class="height_one"></div>
	   	<table class="table_two table_two_left width8">
	   		<tr>
	   			<td>
	   				&nbsp;&nbsp;
	   				<input type="button" class="button_one" onclick="finishPunchFtn(false);" value="完成暂不生成上样表"/>
	   				&nbsp;&nbsp;&nbsp;
	   				<input type="button" class="button_one" onclick="finishPunchFtn(true);" value="完成并生成上样表"/>
	   				&nbsp;&nbsp;&nbsp;
	   				<input type="button" class="button_one" value="返   回" onclick="backFtn();"/>
	   			</td>
	   		</tr>
	   	</table>
    <div class="height_one"></div>
    <div class="height_one"></div>
    </s:form>
  </div>

  <div style="display:none">
      <s:form action="queryExtractionTasks" method="post" id="queryExtractionTasksForm" name="queryExtractionTasksForm">
        <s:hidden id="pageSize" name="pageSize" />
	  	<s:hidden id="pageIndex" name="pageIndex" />
	  	<s:hidden name="boardInfoQuery.sortColumn"/>
	  	<s:hidden name="boardInfoQuery.sortOrder"/>
		<s:hidden name="boardInfoQuery.consignmentNo"/>
        <s:hidden name="boardInfoQuery.boardNo"/>
	    <s:hidden name="boardInfoQuery.importDateStart"/>
	    <s:hidden name="boardInfoQuery.importDateEnd"/>
	    <s:hidden name="boardInfoQuery.importUser"/>
  	</s:form>
  </div>
</div>
</body>
</html>