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
<title>委托查询</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>

	<script type="text/javascript">
		$(document).ready(function(){
			$("#scanButton").click(function(){

				var scanedBarcodeTxt = $("#inputBarcode").val();
				if(scanedBarcodeTxt == ""){
					alert("请输入条码号！");
					return;
				}

				var consignmentId = $("#consignmentId").val();

				$.ajax({
    				type: "post",
    				url:"<%=path %>/pages/sample/saveScanedSample.action",
    				data: {"sampleInfo.consignmentId": consignmentId, "sampleInfo.sampleNo": scanedBarcodeTxt},
    				dataType:"json",
    				success:function(jsonData){
    					if(jsonData.success){
    						if(jsonData.hasRepeat){
    							alert("该样本已扫描受理！");
	    					}else{
	    						$("#scanedCountSpan").text(jsonData.sampleCnt);
	    						$("#hiddenCount").val(jsonData.sampleCnt);
	    						var newTR = "<tr><td>"
	    									+ jsonData.sampleNo + "</td><td>"
	    									+ jsonData.acceptDate + "</td><td>"
	    									+ jsonData.acceptUserName + "</td><td>"
	    									+ "<a href='javascript:void(0);' onclick=\"updSample(this, '" + jsonData.id + "', '" + jsonData.sampleNo + "');\" class='red'>修改条码</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"delSample(this, '" + jsonData.id + "');\" class='red'>删除</a>"
	    									+ "</tr>";
	    						var trArr = $("tr", "#scanedSamplesTbody");
	    						if(trArr.length > 0 && trArr.length < 20){
	    							$("tr", "#scanedSamplesTbody").eq(0).before(newTR);
	    						}else{
	    							if(trArr.length == 0){
	    								$("#scanedSamplesTbody").append(newTR);
	    							}else if(trArr.length == 20){
	    								$("tr", "#scanedSamplesTbody").eq(19).remove();
	    								$("tr", "#scanedSamplesTbody").eq(0).before(newTR);
	    							}

	    						}
	    					}
    					}else{
							alert("该样本已扫描并受理！");
    					}

						$("#inputBarcode").val("");
    				}
    			});

			});

			$("#inputBarcode").keydown(function(){
		    	if(event.keyCode == 13) {
		    		$("#scanButton").trigger("click");
		    		return false;
		    	}
		    });
		});

		function updSample(obj, sampleId, sampleNo){
			var dialogDiv = $("<div style='text-align:center;'><br/>样本条码号：<input type='text' class='text_one' name='sampleNoTxt' value='" + sampleNo + "' /></div>");
			dialogDiv.dialog({
				autoOpen:true,
				height:160,
				width:400,
				modal:true,
				title:"修改条码",
				buttons:{
					"确定":function(){
						var sampleNoTxt = $("input[name='sampleNoTxt']", $(this)).val();
						if(sampleNoTxt != ''){
							$.ajax({
								url:"<%=path%>/pages/sample/updateScanedSampleNo.action",
								type:"post",
								data:{"sampleInfo.id":sampleId, "sampleInfo.sampleNo":sampleNoTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										alert("条码号已修改！");
										$("td", $(obj).parent().parent()).eq(0).text(sampleNoTxt);
										$(dialogDiv).dialog("close");
									}
								}
							});
						}else{
							alert("条码号不能为空！");
						}
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}

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
							$("#scanedCountSpan").text($("#hiddenCount").val() - 1);
						}
					}
				});
			}
		}

			function viewScanedBarcode(){
				$("#scanedSampleDiv").dialog({
					autoOpen: true,
					height:	450,
					width: 360,
					modal: true,
					title: "已扫描样本列表",
					buttons: {
						"关闭" : function(){
							$(this).dialog("close");
						}
					},
					close: function(){
						$(this).dialog("close");
					}
				});

			}

		function backBtn(){
			document.location.href="<%=path %>/pages/sample/queryConsignment.action";
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 委托查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>样本条码号扫描</legend>
      <div class="height_one"></div>
      <div class="wf_left width2">
			样本条码扫描：
          	<input type="text" class="text_one" id="inputBarcode" />
          	&nbsp;&nbsp;
          	<input type="button" class="button_one" id="scanButton" value="扫  描" />
			&nbsp;&nbsp;
      		<input type="hidden" id="consignmentId" value="<s:property value="sampleInfoQuery.consignmentId"/>" />

          	&nbsp;&nbsp;
          	<input type="hidden" id="hiddenCount" value="<s:property value="recordCount"/>"/>
			已扫描  <span id="scanedCountSpan" style="color:blue;"><s:property value="recordCount"/></span> 个样本

			<div style="float:right; margin-right:50px;">
				<input type="button" class="button_one" value="返  回" onclick="backBtn();"/>
			</div>
<%--
			<input type="hidden" id="hiddenScanedCount" value="0" />
          	&nbsp;
          	<s:submit cssClass="button_one" id="submitBtn" value="保存并生成受理表"/>
 --%>
      </div>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>已扫描样本列表</legend>
      		<div class="wf_left">
				<span>列表仅显示最近扫描的20条样本</span>
      		</div>
	      <table class="table_one">
	      <thead>
	        <tr>
	          <th>样本条码号</th>
	          <th>受理时间</th>
	          <th>受理人</th>
	          <th>操作</th>
	        </tr>
	      </thead>
	      <tbody id="scanedSamplesTbody">
			<s:iterator value="sampleInfoList">
				<tr>
					<td><s:property value="sampleNo"/></td>
					<td><s:date name="acceptDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="acceptUserName"/></td>
					<td>-- --</td>
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
