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
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
		$(document).ready(function(){

			$("#closeLeftImg", parent.document.body).click();

			var boardInfoJson = eval("(" + $("#boardInfoJsonStr").val() + ")");
			var resultList = boardInfoJson.sampleList;
			var htmlStr1 = "";
			//var htmlStr2 = "";
			for(var i = 0; i < resultList.length; i++){
				htmlStr1 += "<tr><td class=\"td1\">" + resultList[i].prefix + "</td>";
				//htmlStr2 += "";
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
								htmlStr1 += " id=\"ckText\" readonly=\"readonly\" style=\"display:none;\"></textarea>";
								htmlStr1 += "<span id=\"ckSpan\">CK</span>";
							}else{
								htmlStr1 += ">" + samplesLine[j].sampleNo + "</textarea>";
							}
						}else{
							htmlStr1 += ">" + samplesLine[j].sampleNo + "</textarea>";
						}
					}

					htmlStr1 += "<input type=\"hidden\" name=\"positionNo\" value=\"" + samplesLine[j].positionNo + "\"/></td>";
					//if(samplesLine[j].sampleNo != ""){
					//	htmlStr2 += "<tr><td><a href=\"#" + samplesLine[j].positionNo + "\" onclick=\"sampleChecked('" + samplesLine[j].positionNo +"');\">" + samplesLine[j].sampleNo + "</a></td><td>" + samplesLine[j].positionNo + "</td></tr>";
					//}
				}

				htmlStr1 += "</tr>";
			}

			$("tr:gt(0)", "#samplePositionTbl").remove();
			$("#samplePositionTbl").append(htmlStr1);
			//$("tbody", "#listSampleTbl").append(htmlStr2);

		});


		function sampleChecked(checkedSample) {
			$("input", "#" + checkedSample).focus();
			$("input", "#" + checkedSample).select();
		}

		function backFtn(){
			location.href="<%=path%>/pages/sample/querySamples.action";
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
  <div class="posittions"> 当前位置：样本管理  &gt;&gt; 取样任务详情</div>
  <div class="box_three">
    <div class="height_one"></div>
    <img id="loading" src="<%=path %>/images/loading.gif" style="display:none;">
    <input type="hidden" id="boardInfoJsonStr" value="<s:property value="jsonString"/>"/>

	    <table class="table_two table_two_left width2">
	      <tr>
	    		<td>输入委托号<span style="color:red;">*</span>：</td>
		        <td>
		        	<s:textfield name="boardInfo.consignmentNo" id="consignmentNoTxt" cssClass="text_one" readonly="true"/>
		        </td>
	    		<td>输入板号<span style="color:red;">*</span>：</td>
	    		<td>
	    			<s:textfield name="boardInfo.boardNo" id="boardNoTxt" cssClass="text_one" readonly="true"/>
	    		</td>
			</tr>
			<tr>
				<td>工作站文件：</td>
	        	<td colspan="3">
	        		<input type="text" value="<s:property value="boardInfo.workstationFilename"/>" class="text_nowidth width4"/>
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
	        <td>取样孔径：</td>
	        <td id="holeDiameterTD" colspan="4">
	        	<s:checkboxlist name="boardInfo.holeDiameterArr" list="holeDiameterList" listKey="dictKey" listValue="dictValue" />
	        	<s:if test="boardInfo.holeDiameterOther != null">
	        		<input type="checkbox" id="otherHoleDiameterRadio" checked="checked" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_one"/>
	        	</s:if>
				<s:else>
					<input type="checkbox" id="otherHoleDiameterRadio" />&nbsp;其他
					&nbsp;<s:textfield name="boardInfo.holeDiameterOther" id="holeDiameterOtherTxt" cssClass="text_one"/>
				</s:else>
	        </td>
	      </tr>
	      <tr>
	        <td>检验类型：</td>
	        <td id="examineTypeTD" colspan="3">
	        	<s:radio cssClass="radio_one" name="boardInfo.examineType" list="examineTypeList" listKey="dictKey" listValue="dictValue"></s:radio>
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
	    <div class="height_one"/>
	    <table class="table_two table_two_left width8">
	    	<tr>
		      	<td>
		      		&nbsp;
		   			<input type="button" class="button_one" value="返   回" onclick="backFtn();"/>
		      	</td>
		      </tr>
	    </table>
<%--
    <fieldset class="fieldset_one">
    <legend>样本板位列表</legend>
      <table class="table_one" id="listSampleTbl">
      <thead>
        <tr>
          <th>样本编号</th>
          <th>位置</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
      <div class="height_one"></div>
    </fieldset>
 --%>
  </div>
</div>
</body>
</html>