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
<title>CODIS导入</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#importFileBtn").live("click", function(){

			var boardNoTxt = $("#boardNoTxt").val();
			if(boardNoTxt == ""){
				alert("请输入板号！");
				return;
			}

			var codisFileTxt = $("#codisFile").val();
			if(codisFileTxt == ""){
				alert("请选择CODIS文件！");
				return;
			}
			
			var checkResult = true;
			$.ajax({
				url:"<%=path %>/pages/analysis/checkBoardExists.action?boardNo=" + boardNoTxt,
				type:"get",
				dataType:"json",
				async:false,
				cache:false,
				success:function(jsonData){
					if(jsonData.boardExistsFlag){
						if(!jsonData.examineFlag){
							alert("该板尚未完成检验，暂不能导入结果！");
							checkResult = false;
						}
					}else{
						alert("不存在此板号，请重新输入！");
						checkResult = false;
					}
				}
			});
			
			if(checkResult){
				$("#submitCodisImportForm").submit();
			}

		});

		//TODO -
		$("#importFolderBtn").live("click", function(){
			return false;
		});

	});
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
  <div class="posittions"> 当前位置：检验结果管理 &gt;&gt; 结果导入</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>结果导入</legend>
      <div class="height_one"></div>
      <s:form action="submitCodisImport" name="submitCodisImportForm" id="submitCodisImportForm"
      	method="post" enctype="multipart/form-data">
	      <table class="table_two floatL marginL1">
	      	<tr>
	      		<td align="right">
		          	输入板号<span style="color:red;">*</span>：
		        </td>
		        <td align="left">
		      		<s:textfield cssClass="text_one" name="boardNo" id="boardNoTxt" />
	      		</td>
	      		<td rowspan="3" align="center" style="width:404px;">
	      			<div style="width:100%;border:1px #050000 solid;">
	      				<span style="color:red; font-size:15px;">
	      				说明：点击“导入”按钮时，系统自动判断输入的板号的检验类型，如果为质检，则对CODIS中质检的样本进行同型比对，并展示比对结果；非质检，则直接执行CODIS结果导入。
	      				</span>
	      			</div>
	      			
	      		</td>
	      	</tr>
			<tr>
				<td align="right">
					试剂盒<span style="color:red;">*</span>：
				</td>
				<td align="left">
					<s:select list="reagentKitList" name="reagentKitId" listKey="id" listValue="reagentName" cssClass="width5"></s:select>
				</td>
			</tr>
	        <tr>
	          <td align="right">
	          	CODIS文件：
	          </td>
	          <td align="left">
	          	<s:file name="codisFile" id="codisFile" cssClass="file_one width4" />
	          	&nbsp;&nbsp;
	          	<input type="button" value="导  入" class="button_one" id="importFileBtn"/></td>
	        </tr>

	      </table>
      </s:form>
    </fieldset>
  </div>
</div>
</body>
</html>
