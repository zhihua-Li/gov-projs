/**
 * 
 */
function vialidInputSampleNo(sampleNo){
	var invalidCharArr = new Array();
	invalidCharArr.push(".");
	invalidCharArr.push("!");
	invalidCharArr.push("@");
	invalidCharArr.push("#");
	invalidCharArr.push("$");
	invalidCharArr.push("^");
	invalidCharArr.push("&");
	invalidCharArr.push("*");
	invalidCharArr.push("`");
	invalidCharArr.push("~");
	invalidCharArr.push("/");
	invalidCharArr.push("\\");
	invalidCharArr.push("。");
	invalidCharArr.push("、");
	invalidCharArr.push("；");
	invalidCharArr.push("：");
	invalidCharArr.push("“");
	invalidCharArr.push("”");
	invalidCharArr.push("‘");
	invalidCharArr.push("’");
	invalidCharArr.push("》");
	invalidCharArr.push("《");
	invalidCharArr.push("【");
	invalidCharArr.push("】");

	var sampleNoLen = sampleNo.length;
	for(var i = 0; i < sampleNoLen; i++){
		for(var j = 0; j < invalidCharArr.length; j++){
			if(sampleNo.charAt(i) == invalidCharArr[j]){
				return false;
			}
		}
	}

	return true;
}

//扫描条码并保存
function scaneSampleBarcodeAndSave(obj){
	var currentTxt = $(obj);
	var inputSampleNo = currentTxt.val();
	var samplePosition = $("input[name='positionNo']", $(obj).parent()).val();
	var hiddenBoardId = $("#hiddenBoardId").val();
	//所有TR数组
	var trArr = $("tr", "#samplePositionTbl");
	//当前行
	var currentTr = currentTxt.parent().parent();
	//当前行索引
	var currentTrIdx = trArr.index(currentTr);
	//当前行的text数组
	var textArrInCurrentTr = $("textarea", currentTr);
	//当前输入框在TR中的索引
	var currentTxtIdx = textArrInCurrentTr.index(currentTxt);
	//每一行的text个数
	var txtSizeInTr = textArrInCurrentTr.length;

	//下一行
	var nextTr;
	//下一个text
	var nextText;
	while(true){
		if(currentTrIdx == (trArr.length - 1)){
			nextTr = trArr.get(1);
			if(currentTxtIdx < (txtSizeInTr - 1)){
				nextText = $("textarea", $(nextTr)).get(currentTxtIdx + 1);
			}else{
				break;
			}
		}else{
			nextTr = trArr.get(currentTrIdx + 1);
			nextText = $("textarea", $(nextTr)).get(currentTxtIdx);
		}

		if($(nextText).attr("readonly") && $(nextText).attr("readonly") == "readonly"){
			currentTrIdx = trArr.index(nextTr);
		}else{
			break;
		}
	}

	if(inputSampleNo == "" || $.trim(inputSampleNo) == ""){
		if(confirm("当前孔位无样本，确认跳过并继续吗？")){
			//置空数据库中当前孔位的样本
			$.ajax({
				type: "post",
				url: pathStr + "/pages/sample/deleteSampleOfBoardPosition.action",
				data: {"boardInfo.id":hiddenBoardId, "inputSamplePosition": samplePosition},
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						$(nextText).focus();
						$(nextText).select();
					}else{
						alert(jsonData.errorMsg);
					}
				}
			});
		}else{
			$(obj).css("border","1px solid #FF3333");
		}
	}else{
		$(obj).css("border","0px");
		inputSampleNo = $.trim(inputSampleNo);
		if(!vialidInputSampleNo(inputSampleNo)){
			alert("样本条码号中不可以包含以下字符：\n" + ".!@#$%^&*`~/\。、；：“”‘’》《【】");
			currentTxt.val("");
			return;
		}

		$(obj).attr("title", inputSampleNo);
		$.ajax({
			type: "post",
			url:pathStr + "/pages/sample/saveScanedSampleInBoard.action",
			data:{"boardInfo.id":hiddenBoardId, "inputSampleNo": inputSampleNo, "inputSamplePosition": samplePosition},
			dataType:"json",
			success:function(jsonData){
					if(jsonData.success){
						$(nextText).focus();
						$(nextText).select();
					}else{
						if(jsonData.otherConsignmentSample){
							alert("该样本不属于当前委托！ 所属委托编号为：" + jsonData.consignmentNo);
							currentTxt.val("");
						}
					}
			}
		});
	}
}

function saveAndPunch(){
	$("#importFlag").val("0");
	$("#submitPunchExtractionForm").submit();
}

function saveAndImport(){
	$("#importFlag").val("1");
	$("#submitPunchExtractionForm").submit();
}

function finishPunchFtn(generateFlag){
	var boardNoTxt = $.trim($("#boardNoTxt").val());
	if(boardNoTxt == ""){
		alert("请输入板号！");
		return false;
	}

	var holeDiameters = $("input[type='checkbox']:checked", "#holeDiameterTD");
	if(holeDiameters.length <= 0){
		alert("请选择取样孔径！");
		return false;
	}
	
	var sampleNoArr = $("textarea[name='sampleNo']", "#samplePositionTbl");
	var hasEmpty = false;
	for(var i = 0; i < sampleNoArr.length; i++){
		if($(sampleNoArr.get(i)).val() == ""){
			if($(sampleNoArr.get(i)).css("display") && $(sampleNoArr.get(i)).css("display") == "none"){
				continue;
			}

			hasEmpty = true;;
			break;
		}
	}

	if(hasEmpty){
		if(!confirm("上样板存在空的孔位，确定继续吗？")){
			return;
		}
	}

	if(generateFlag){
		var hiddenBoardId = $("#hiddenBoardId").val();

		$.ajax({
			type: "get",
			url: pathStr + "/pages/sample/generatePunchExtractionRecord.action?boardInfo.id=" + hiddenBoardId,
			cache: false,
			dataType: "json",
			success: function(jsonData){
				if(jsonData.success){
					alert("上样表生成成功！");
				}
			}
		});
	}

	document.location.href=pathStr + "/pages/sample/intoPunchExtraction.action";
}

function sampleChecked(checkedSample) {
	$("textarea", "#" + checkedSample).focus();
	$("textarea", "#" + checkedSample).select();
}

/*

function importWorkstationFile(){
	$.ajaxFileUpload
	(
		{
			url:'<%=path %>/pages/sample/importWorkstationFile.action',
			secureuri:false,
			fileElementId:'workstationFile',
			dataType: 'json',
			success: function (data, status)
			{
				var fileName = data.workstationFileName;
				var boardNo = data.boardNo;
				var resultList = data.sampleList;
				var htmlStr1 = "";
				//var htmlStr2 = "";
				for(var i = 0; i < resultList.length; i++){
					htmlStr1 += "<tr><td class=\"td1\">" + resultList[i].prefix + "</td>";
					//htmlStr2 += "";
					var samplesLine = resultList[i].samples;
					for(var j = 0; j < samplesLine.length; j++){
						htmlStr1 += "<td id=\"" + samplesLine[j].positionNo + "\"><a name=\"#" + samplesLine[j].positionNo + "\"></a><textarea name=\"sampleNo\" class=\"textarea_te\" ";
						if(samplesLine[j].positionNo == "A01"
								|| samplesLine[j].positionNo == "B01"
								|| samplesLine[j].positionNo == "H03"
								|| samplesLine[j].positionNo == "H10"){
							htmlStr1 += " readonly=\"readonly\" style=\"display:none;\"></textarea>";
							if(samplesLine[j].positionNo == "A01"){
								htmlStr1 += "CK";
							}

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
							htmlStr1 += ">" + samplesLine[j].sampleNo + "</textarea>";
						}

						htmlStr1 += "<input type=\"hidden\" name=\"positionNo\" value=\"" + samplesLine[j].positionNo + "\"/></td>";
					//	if(samplesLine[j].sampleNo != ""){
					//		htmlStr2 += "<tr><td><a href=\"#" + samplesLine[j].positionNo + "\" onclick=\"sampleChecked('" + samplesLine[j].positionNo +"');\">" + samplesLine[j].sampleNo + "</a></td><td>" + samplesLine[j].positionNo + "</td></tr>";
					//	}
					}

					htmlStr1 += "</tr>";
				}

				$("tr:gt(0)", "#samplePositionTbl").remove();
				$("#samplePositionTbl").append(htmlStr1);
				//$("tbody", "#listSampleTbl").append(htmlStr2);
			},
			error: function (data, status, e)
			{
				alert(e);
			}
		}
	)

	return false;
}
		

*/
