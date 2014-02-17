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
<title>导入上样表</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#submitBoardSampleFileImport").submit(function(){

			var codisFileTxt = $("#boardSampleFile").val();
			if(codisFileTxt == ""){
				alert("请选择上样表文件！");
				return false;
			}

			$(this).ajaxSubmit({
				target: "#submitBoardSampleFileImport",
				url: "<%=path %>/pages/sample/submitBoardSampleFileImport.action",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType: "json",
				success: function(data){
					var jsonString = eval("(" + data + ")");
					if(jsonString.success || jsonString.success == "true"){
						parent.fillingImportedSample(jsonString);

						parent.$("#importBoardSample").dialog("close");
					}
				},
				error: function(e){
				}
			});

			return false;
		});
	});


	function backFtn(){
		parent.$("#importBoardSample").dialog("close");
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
<div class="main" style="overflow:hidden;">
    <div class="height_one"></div>
     <div class="height_one"></div>
     <br/>
     <br/>
      <s:form action="submitBoardSampleFileImport" name="submitBoardSampleFileImport" id="submitBoardSampleFileImport"
      	method="post" enctype="multipart/form-data">
      	<s:hidden name="boardInfo.id"/>
	      <table class="table_two floatL marginL1">
	        <tr>
	          <td>
	          	选择上样表文件：
	          	<s:file name="boardSampleFile" id="boardSampleFile" cssClass="file_one width4" />
	          </td>
	        </tr>
	        <tr>
	        	<td>
	        		&nbsp;&nbsp;
		          	<input type="submit" value="导  入" class="button_one" id="importFileBtn"/>
		          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		          	<input type="button" value="取   消" class="button_one" onclick="backFtn();"/>
	        	</td>
	        </tr>

	      </table>
      </s:form>
</div>
</body>
</html>