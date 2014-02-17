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
<title>高通量DNA检验平台</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />

<script type="text/javascript">
	var pathStr = "<%=path %>";
</script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/punchExtractionInfo.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#closeLeftImg", parent.document.body).click();

	$("input[type='radio']", "#holeDiameterTD").eq(0).attr("checked", "checked");
	$("input[type='radio']", "#examineTypeTD").eq(0).attr("checked", "checked");

	$("#workstationFile").change(function(){
		var filepath = $(this).val();
		var filename = filepath.substring(filepath.lastIndexOf("\\") + 1);

		$("#hiddenWorkstationFilename").val(filename);
	});

	$("textarea[name='sampleNo']", "#samplePositionTbl").keydown(function(){
    	if(event.keyCode == 13) {
    		scaneSampleBarcodeAndSave($(this));
    		return false;
    	}
    });

	$("textarea[name='sampleNo']", "#samplePositionTbl").click(function(){
		if($(this).val() != ""){
			$(this).select();
		}
    });

	$("#unlockCK").live("click", function(){
		var hiddenBoardId = $("#hiddenBoardId").val();
		if(hiddenBoardId == ""){
			alert("请保存基本信息后再进行该操作！");
			return false;
		}else{
			if($(this).attr("checked") && $(this).attr("checked") == "checked"){
				$("#ckText").css("display","");
				$("#ckSpan").css("display","none");
			}else{
				if($("#ckText").val() != ""){
	    			$.ajax({
	    				type: "post",
	    				url:pathStr + "/pages/sample/deleteCKSample.action",
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
		}
	});

	$("#submitPunchExtractionForm").submit(function(){
		var boardNoTxt = $.trim($("#boardNoTxt").val());
		if(boardNoTxt == ""){
			alert("请输入板号！");
			return false;
		}

		var consignmentNoTxt = $.trim($("#consignmentNoTxt").val());
		if(consignmentNoTxt == ""){
			alert("请输入委托号！");
			return false;
		}

		var isConsignmentNoExists = true;
		var isBoardNoExists = true;
		$.ajax({
			url:pathStr + "/pages/sample/checkConsignmentNoExists.action?boardInfo.consignmentNo=" + consignmentNoTxt + "&boardInfo.boardNo=" + boardNoTxt,
			type:"get",
			dataType:"json",
			async:false,
			cache:false,
			success:function(data){
				if(data.consignmentNoExists == "false"){
					isConsignmentNoExists = false;
				}

				if(data.boardNoExists == "false"){
					isBoardNoExists = false;
				}
			}
		});

		if(isBoardNoExists){
			alert("您输入的板号已存在，请重新输入！");
			$("#boardNoTxt").focus();
			$("#boardNoTxt").select();
			return false;
		}

		if(!isConsignmentNoExists){
			alert("委托号不存在，请重新输入！");
			$("#consignmentNoTxt").focus();
			$("#consignmentNoTxt").select();
			return false;
		}

		var holeDiameters = $("input[type='checkbox']:checked", "#holeDiameterTD");
		if(holeDiameters.length <= 0){
			alert("请选择取样孔径！");
			return false;
		}

		var importFlag = $("#importFlag").val();
		$(this).ajaxSubmit({
			target: "#submitPunchExtractionForm",
			url: pathStr + "/pages/sample/submitPunchExtraction.action",
			type: "post",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType: "json",
			success: function(jsonData){
				if(jsonData.success){
					$("#hiddenBoardId").val(jsonData.boardInfoId);
					$("#hiddenConsignmentId").val(jsonData.consignmentId);
					$("#hiddenOperateType").val(jsonData.operateType);
					alert("保存成功！");

					$(".textarea_te").removeAttr("disabled");

					if(importFlag == "1"){
						$("#boardSampleIframe").attr("src", pathStr + "/pages/sample/intoImportBoardSample.action?boardInfo.id=" + jsonData.boardInfoId);
						$("#importBoardSample").dialog({
							autoOpen: true,
							height:	300,
							width: 500,
							modal: true,
							title: "导入上样表",
							close: function(){
								$(this).dialog("close");
							}
						});
					}else{
						var isUnlockCK = false;
						if($("#unlockCK").attr("checked") && $("#unlockCK").attr("checked")=="checked"){
							isUnlockCK = true;
						}

						if(isUnlockCK){
							$("#ckText").css("display","");
							$("#ckSpan").css("display","none");

							var firstPosition = $("input[name='positionNo'][value='A01']");
							$("textarea[name='sampleNo']", firstPosition.parent()).focus();
						}else{
							$("#ckText").css("display","none");
							$("#ckSpan").css("display","");

							var firstPosition = $("input[name='positionNo'][value='C01']");
							$("textarea[name='sampleNo']", firstPosition.parent()).focus();
						}
					}
				}else{
					alert(jsonData.errorMsg);
				}
			},
			error: function(e){
			}
		});

		return false;
	});
});

function fillingImportedSample(boardInfoJson){
	var isUnlockCK = false;
	if($("#unlockCK").attr("checked") && $("#unlockCK").attr("checked")=="checked"){
		isUnlockCK = true;
	}

	if(isUnlockCK){
		$("#ckText").css("display","");
		$("#ckSpan").css("display","none");
	}else{
		$("#ckText").css("display","none");
		$("#ckSpan").css("display","");
	}

	var resultList = boardInfoJson.sampleList;
	var htmlStr1 = "";
	for(var i = 0; i < resultList.length; i++){
		htmlStr1 += "<tr><td class=\"td1\">" + resultList[i].prefix + "</td>";
		var samplesLine = resultList[i].samples;
		for(var j = 0; j < samplesLine.length; j++){
			htmlStr1 += "<td id=\"" + samplesLine[j].positionNo + "\"><a name=\"#" + samplesLine[j].positionNo + "\"></a><textarea name=\"sampleNo\" class=\"textarea_te\"";

			if(samplesLine[j].positionNo == "B01"
					|| samplesLine[j].positionNo == "H03"
					|| samplesLine[j].positionNo == "H10"){
				htmlStr1 += " title=\"" + samplesLine[j].sampleNo + "\" readonly=\"readonly\" style=\"display:none;\">" + samplesLine[j].sampleNo + "</textarea>";

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
					if(isUnlockCK){
						htmlStr1 += " title=\"" + samplesLine[j].sampleNo + "\" id=\"ckText\">" + samplesLine[j].sampleNo + "</textarea>";
						htmlStr1 += "<span id=\"ckSpan\" style=\"display:none;\">CK</span>";
					}else{
						htmlStr1 += " id=\"ckText\" readonly=\"readonly\" style=\"display:none;\" disabled=\"disabled\"></textarea>";
						htmlStr1 += "<span id=\"ckSpan\">CK</span>";
					}
				}else{
					htmlStr1 += " title=\"" + samplesLine[j].sampleNo + "\" >" + samplesLine[j].sampleNo + "</textarea>";
				}
			}

			htmlStr1 += "<input type=\"hidden\" name=\"positionNo\" value=\"" + samplesLine[j].positionNo + "\"/></td>";
		}

		htmlStr1 += "</tr>";
	}

	$("tr:gt(0)", "#samplePositionTbl").remove();
	$("#samplePositionTbl").append(htmlStr1);

	$("textarea[name='sampleNo']", "#samplePositionTbl").keydown(function(){
    	if(event.keyCode == 13) {
    		scaneSampleBarcodeAndSave($(this));
    		return false;
    	}
    });

	$("textarea[name='sampleNo']", "#samplePositionTbl").click(function(){
		if($(this).val() != ""){
			$(this).select();
		}
    });
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 打孔取样</div>
  <div class="box_three">
    <div class="height_one"></div>
    <%-- <img id="loading" src="<%=path %>/images/loading.gif" style="display:none;"> --%>
    <s:form action="submitPunchExtraction" id="submitPunchExtractionForm" name="submitPunchExtractionForm" method="post">
    	<s:hidden name="boardInfo.id" id="hiddenBoardId"></s:hidden>
    	<s:hidden name="boardInfo.consignmentId" id="hiddenConsignmentId"></s:hidden>
    	<s:hidden name="boardInfo.workstationFilename" id="hiddenWorkstationFilename" />
    	<s:hidden name="boardInfo.examineStatus" />
    	<s:hidden name="operateType" id="hiddenOperateType"/>
	    <table class="table_two table_two_left width8">
	    	<tr>
	    		<td>输入委托号<span style="color:red;">*</span>：</td>
		        <td>
		        	<s:textfield name="boardInfo.consignmentNo" id="consignmentNoTxt" cssClass="text_one"/>
		        </td>
	    		<td>输入板号<span style="color:red;">*</span>：</td>
	    		<td>
	    			<s:textfield name="boardInfo.boardNo" id="boardNoTxt" cssClass="text_one"/>
	    		</td>
			</tr>
			<tr>
				<td>工作站文件：</td>
	        	<td colspan="3">
					<s:file name="workstationFile" id="workstationFile" cssClass="text_nowidth width4" disabled="true"></s:file>
		        	&nbsp;
		        	<input name="button" type="button" class="button_one" onclick="importWorkstationFile();" value="导   入" onclick="return false;"/>
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
	        <td id="holeDiameterTD" colspan="4">
	        	<s:checkboxlist name="boardInfo.holeDiameterArr" list="holeDiameterList" listKey="dictKey" listValue="dictValue" />
	        	<s:if test="boardInfo.holeDiameterOther != null">
	        		<input type="checkbox" id="otherHoleDiameterRadio" checked="checked" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_nowidth width80"/>
	        	</s:if>
				<s:else>
					<input type="checkbox" id="otherHoleDiameterRadio" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_nowidth width80"/>
				</s:else>
	        </td>
	      </tr>
	      <tr>
	        <td>检验类型：</td>
	        <td id="examineTypeTD" colspan="4">
	        	<s:radio cssClass="radio_one" name="boardInfo.examineType" list="examineTypeList" listKey="dictKey" listValue="dictValue"></s:radio>
	        </td>
	      </tr>
	      <tr>
	      	<td colspan="4">
	      		<input type="checkbox" id="unlockCK" />&nbsp;&nbsp;<span style="color:red;">是否解除CK锁定</span>
	      		&nbsp;&nbsp;&nbsp;
	      		<input type="button" class="button_one" onclick="saveAndPunch();" value="保存并开始打孔"/>
	      		&nbsp;&nbsp;&nbsp;
	      		<input type="button" class="button_one" onclick="saveAndImport();" value="保存并导入上样表" />
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
	      <tr>
	        <td class="td1">A</td>
	        <td>
	        	<a name="A01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled" id="ckText" style="display:none;"></textarea>
				<span id="ckSpan">CK</span>
	        	<input type="hidden" name="positionNo" value="A01"/>
	        </td>
	        <td>
	        	<a name="A02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A02"/>
	        </td>
	        <td>
	        	<a name="A03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A03"/>
	        </td>
	        <td>
	        	<a name="A04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A04"/>
	        </td>
	        <td>
	        	<a name="A05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A05"/>
	        </td>
	        <td>
	        	<a name="A06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A06"/>
	        </td>
	        <td>
	        	<a name="A07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A07"/>
	        </td>
	        <td>
	        	<a name="A08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A08"/>
	        </td>
	        <td>
	        	<a name="A09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A09"/>
	        </td>
	        <td>
	        	<a name="A10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A10"/>
	        </td>
	        <td>
	        	<a name="A11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A11"/>
	        </td>
	        <td>
	        	<a name="A12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="A12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">B</td>
	        <td>
	        	<a name="B01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled" readonly="readonly" style="display:none;"></textarea>
	        	9947
	        	<input type="hidden" name="positionNo" value="B01"/>
	        </td>
	        <td>
	        	<a name="B02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B02"/>
	        </td>
	        <td>
	        	<a name="B03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B03"/>
	        </td>
	        <td>
	        	<a name="B04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B04"/>
	        </td>
	        <td>
	        	<a name="B05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B05"/>
	        </td>
	        <td>
	        	<a name="B06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B06"/>
	        </td>
	        <td>
	        	<a name="B07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B07"/>
	        </td>
	        <td>
	        	<a name="B08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B08"/>
	        </td>
	        <td>
	        	<a name="B09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B09"/>
	        </td>
	        <td>
	        	<a name="B10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B10"/>
	        </td>
	        <td>
	        	<a name="B11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B11"/>
	        </td>
	        <td>
	        	<a name="B12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="B12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">C</td>
	        <td>
	        	<a name="C01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C01"/>
	        </td>
	        <td>
	        	<a name="C02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C02"/>
	        </td>
	        <td>
	        	<a name="C03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C03"/>
	        </td>
	        <td>
	        	<a name="C04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C04"/>
	        </td>
	        <td>
	        	<a name="C05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C05"/>
	        </td>
	        <td>
	        	<a name="C06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C06"/>
	        </td>
	        <td>
	        	<a name="C07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C07"/>
	        </td>
	        <td>
	        	<a name="C08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C08"/>
	        </td>
	        <td>
	        	<a name="C09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C09"/>
	        </td>
	        <td>
	        	<a name="C10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C10"/>
	        </td>
	        <td>
	        	<a name="C11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C11"/>
	        </td>
	        <td>
	        	<a name="C12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="C12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">D</td>
	        <td>
	        	<a name="D01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D01"/>
	        </td>
	        <td>
	        	<a name="D02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D02"/>
	        </td>
	        <td>
	        	<a name="D03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D03"/>
	        </td>
	        <td>
	        	<a name="D04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D04"/>
	        </td>
	        <td>
	        	<a name="D05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D05"/>
	        </td>
	        <td>
	        	<a name="D06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D06"/>
	        </td>
	        <td>
	        	<a name="D07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D07"/>
	        </td>
	        <td>
	        	<a name="D08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D08"/>
	        </td>
	        <td>
	        	<a name="D09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D09"/>
	        </td>
	        <td>
	        	<a name="D10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D10"/>
	        </td>
	        <td>
	        	<a name="D11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D11"/>
	        </td>
	        <td>
	        	<a name="D12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="D12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">E</td>
	        <td>
	        	<a name="E01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E01"/>
	        </td>
	        <td>
	        	<a name="E02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E02"/>
	        </td>
	        <td>
	        	<a name="E03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E03"/>
	        </td>
	        <td>
	        	<a name="E04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E04"/>
	        </td>
	        <td>
	        	<a name="E05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E05"/>
	        </td>
	        <td>
	        	<a name="E06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E06"/>
	        </td>
	        <td>
	        	<a name="E07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E07"/>
	        </td>
	        <td>
	        	<a name="E08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E08"/>
	        </td>
	        <td>
	        	<a name="E09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E09"/>
	        </td>
	        <td>
	        	<a name="E10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E10"/>
	        </td>
	        <td>
	        	<a name="E11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E11"/>
	        </td>
	        <td>
	        	<a name="E12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="E12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">F</td>
	        <td>
	        	<a name="F01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F01"/>
	        </td>
	        <td>
	        	<a name="F02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F02"/>
	        </td>
	        <td>
	        	<a name="F03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F03"/>
	        </td>
	        <td>
	        	<a name="F04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F04"/>
	        </td>
	        <td>
	        	<a name="F05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F05"/>
	        </td>
	        <td>
	        	<a name="F06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F06"/>
	        </td>
	        <td>
	        	<a name="F07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F07"/>
	        </td>
	        <td>
	        	<a name="F08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F08"/>
	        </td>
	        <td>
	        	<a name="F09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F09"/>
	        </td>
	        <td>
	        	<a name="F10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F10"/>
	        </td>
	        <td>
	        	<a name="F11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F11"/>
	        </td>
	        <td>
	        	<a name="F12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="F12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">G</td>
	        <td>
	        	<a name="G01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G01"/>
	        </td>
	        <td>
	        	<a name="G02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G02"/>
	        </td>
	        <td>
	        	<a name="G03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G03"/>
	        </td>
	        <td>
	        	<a name="G04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G04"/>
	        </td>
	        <td>
	        	<a name="G05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G05"/>
	        </td>
	        <td>
	        	<a name="G06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G06"/>
	        </td>
	        <td>
	        	<a name="G07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G07"/>
	        </td>
	        <td>
	        	<a name="G08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G08"/>
	        </td>
	        <td>
	        	<a name="G09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G09"/>
	        </td>
	        <td>
	        	<a name="G10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G10"/>
	        </td>
	        <td>
	        	<a name="G11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G11"/>
	        </td>
	        <td>
	        	<a name="G12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="G12"/>
	        </td>
	      </tr>
	      <tr>
	        <td class="td1">H</td>
	        <td>
	        	<a name="H01"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H01"/>
	        </td>
	        <td>
	        	<a name="H02"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H02"/>
	        </td>
	        <td>
	        	<a name="H03"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled" readonly="readonly" style="display:none;"></textarea>
	        	Ladder
	        	<input type="hidden" name="positionNo" value="H03"/>
	        </td>
	        <td>
	        	<a name="H04"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H04"/>
	        </td>
	        <td>
	        	<a name="H05"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H05"/>
	        </td>
	        <td>
	        	<a name="H06"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H06"/>
	        </td>
	        <td>
	        	<a name="H07"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H07"/>
	        </td>
	        <td>
	        	<a name="H08"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H08"/>
	        </td>
	        <td>
	        	<a name="H09"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H09"/>
	        </td>
	        <td>
	        	<a name="H10"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled" readonly="readonly" style="display:none;"></textarea>
	        	Ladder
	        	<input type="hidden" name="positionNo" value="H10"/>
	        </td>
	        <td>
	        	<a name="H11"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H11"/>
	        </td>
	        <td>
	        	<a name="H12"></a>
	        	<textarea name="sampleNo" class="textarea_te" disabled="disabled"></textarea>
	        	<input type="hidden" name="positionNo" value="H12"/>
	        </td>
	      </tr>
	    </table>
	    <div class="height_one"></div>
	   	<table class="table_two table_two_left width9">
	   		<tr>
	   			<td>
	   				&nbsp;&nbsp;
	   				<input type="button" class="button_one" onclick="finishPunchFtn(false);" value="完成暂不生成上样表"/>
	   				&nbsp;&nbsp;&nbsp;
	   				<input type="button" class="button_one" onclick="finishPunchFtn(true);" value="完成并生成上样表"/>
	   			</td>
	   		</tr>
	   	</table>
    </s:form>

    <div class="height_one"></div>
    <div class="height_one"></div>

<!-- 上样板样本Dialog -->
<div id="importBoardSample">
	<iframe id="boardSampleIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>

  </div>
</div>
</body>
</html>

