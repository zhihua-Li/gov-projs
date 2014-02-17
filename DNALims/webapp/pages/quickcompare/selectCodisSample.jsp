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
<title>选择CODIS样本导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){

		$("#selectCodisSampleForm").live("submit", function(){

			if($("#codisFile").val() == ""){
				alert("请选择CODIS文件！");
				return false;
			}

			$(this).ajaxSubmit({
				target: "#selectCodisSampleForm",
				success: function(jsonData){
					if(jsonData.success){

						var sampleList = jsonData.sampleList;
						var htmlStr = "";
						for(var i = 0; i < sampleList.length; i++){
							htmlStr += "<tr><td><input type='radio' name='sampleNoRadio'/><input type='hidden' name='strInfo' value='"
										+ sampleList[i].strInfo + "'/><input type='hidden' name='sampleNo' value='"
										+ sampleList[i].sampleNo + "'/></td><td>" + sampleList[i].sampleNo + "</td></tr>";
						}

						$("tbody", "#sampleListTbl").html(htmlStr);
					}else{
						alert(jsonData.errorMsg);
					}
				},
				error: function(e){
				},
				url: "<%=path %>/pages/quickcompare/selectCodisSample.action",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType: "json"
			});

			return false;
		});

		$("#submitSampleBtn").live("click", function(){
			var checkedRadio = $("input[type='radio']:checked", "#sampleListTbl");
			if(checkedRadio && checkedRadio.length > 0){
				var strInfo = $("input[type='hidden'][name='strInfo']", checkedRadio.parent()).val();
				alert(strInfo);
				window.parent.initCodisSampleStrTbl(strInfo);
			}else{
				alert("请选择要提交比对的样本！");
			}
		});

		$("#cancelBtn").live("click", function(){
			window.parent.$("#dialog-div").dialog("close");
		});

	});
</script>
<style type="text/css">
  html,body{
	  overflow:hidden;
	  height:100%;
	  width:100%;
  }
</style>
</head>

  <body>
    <div class="box_three" style="height:100%;width:100%;">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>选择CODIS文件</legend>
      <div class="height_one"></div>
      <s:form action="selectCodisSample" name="selectCodisSampleForm" id="selectCodisSampleForm"
      	method="post" enctype="multipart/form-data">
	      <table class="table_two floatL">
	        <tr>
	          <td align="left">
	          	选择CODIS文件：&nbsp;
	          	<s:file name="codisFile" id="codisFile" cssClass="file_one" />
	          	<br/>
	          	<input type="submit" value="确 定" class="button_one"/>
	          	&nbsp;&nbsp;
	          	<input type="button" value="取 消" class="button_one" id="cancelBtn"/>
	          </td>
	        </tr>

	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
    	<legend>CODIS样本列表</legend>
    	<div class="wf_left">
			<input type="button" class="button_one" value="提交选中样本" id="submitSampleBtn"/>
    	</div>
    	<table class="table_one" id="sampleListTbl">
			<thead>
				<tr>
					<th>选择</th>
					<th>样本条码号</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
    </fieldset>
  </div>
  </body>
</html>
