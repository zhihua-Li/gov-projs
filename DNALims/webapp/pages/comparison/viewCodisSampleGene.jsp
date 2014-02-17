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
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function(){

		$("#geneInfoTbl").append('<s:property escape="0" value="geneInfo.geneInfoHtmlForPage"/>');

		$("#saveUpdatedCodisSampleGeneForm").submit(function(){
			$(this).ajaxSubmit({
				target: "#saveUpdatedCodisSampleGeneForm",
				url: "<%=path %>/pages/compare/saveUpdatedCodisSampleGene.action",
				type: "post",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						window.parent.$("#codisSampleGeneDetailDiv").dialog("close");
						window.parent.$("#pageSubmit").click();
					}else{
						alert(jsonData.errorMsg);
					}

				},
				error: function(e){
				}

			});

			return false;
		});

		$("#clostBtn").live("click", function(){
			window.parent.$("#resultDetailDiv").dialog("close");
		});

		$("#reagentKitId").change(function(){
			var checkedReagent = $("option:selected", $(this)).val();
			var id = $("#geneId").val();
			$.ajax({
				type: "get",
				url: "<%=path %>/pages/compare/changeCodisSampleGeneReagentKit.action?geneInfo.id=" + id + "&geneInfo.reagentKit=" + checkedReagent ,
				cache: false,
				dataType: "html",
				success: function(data){
					$("tr:gt(0)", "#geneInfoTbl").remove();
					$("#geneInfoTbl").append(data);
				},
				error: function(){
					alert("获取基因座列表失败！请重试或联系技术人员！");
				}
			});


		});
	});

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
<div class="main" style="overflow:hidden;">
  <div class="box_three">
    <s:form action="saveUpdatedCodisSampleGene" id="saveUpdatedCodisSampleGeneForm" method="post">
    	<s:hidden name="operateType"/>
    	<s:hidden name="geneInfo.id" id="geneId"/>
    	<s:hidden name="geneInfo.sampleId" />
    	<s:hidden name="geneInfo.geneType" />
    	<s:hidden name="geneInfo.sampleType" />

	    <fieldset class="fieldset_one">
	      <legend>样本基因型信息</legend>
	      	<table class="table_two width2" id="geneInfoTbl">
		        <tr>
		          <td>试剂盒:</td>
		          <td>
		          	<s:select name="geneInfo.reagentKit" id="reagentKitId" list="reagentKitList" listKey="id" listValue="reagentName"
		          		headerKey="" headerValue="" cssClass="select_one">
		          		<s:param name="value">
		          			<s:property value="geneInfo.reagentKit"/>
		          		</s:param>
		          	</s:select>
		          </td>
		          <td colspan="2">
		          	<s:submit cssClass="button_one" value="保   存"/>
						&nbsp;
						<input type="button" class="button_one" id="clostBtn" value="关   闭" />
				  </td>
		        </tr>
	      	</table>
		    <div class="height_one"></div>
	    </fieldset>
    </s:form>
  </div>
</div>
</body>
</html>
