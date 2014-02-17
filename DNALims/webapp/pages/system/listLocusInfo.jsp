<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>基因座列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>

	<script type="text/javascript">
		$(document).ready(function(){

			$("#addLocusInfoBtn").live("click", function(){
				$("#operateType").val("1");
				$("#locusId").val("");
				$("#locusNameText").val("");
				$("#locusAliasText").val("");
				$("#locusDescTxt").val("");

				$("#locusInfoDetailDiv").dialog({
					autoOpen: true,
					height:	300,
					width: 540,
					modal: true,
					title: "添加基因座",
					buttons: {
						"保存": function(){
							submitLocusInfo();
						},
						"取消": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
						$(this).dialog("close");
					}
				});
			});

		});

		function editLocusInfoFtn(obj) {
			$("#operateType").val("2");
			$("#locusId").val($("input[name='hiddenId']", $(obj).parent()).val());
			$("#locusNameText").val($("input[name='hiddenName']", $(obj).parent()).val());
			$("#locusAliasText").val($("input[name='hiddenAlias']", $(obj).parent()).val());
			$("#locusDescTxt").val($("input[name='hiddenDesc']", $(obj).parent()).val());

			$("#locusInfoDetailDiv").dialog({
				autoOpen: true,
				height:	300,
				width: 550,
				modal: true,
				title: "修改基因座",
				buttons: {
					"保存": function(){
						submitLocusInfo();
					},
					"取消": function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					$(this).dialog("close");
				}
			});
		}

		function submitLocusInfo(){
			var operateType = $("#operateType").val();
			var locusId = $("#locusId").val();
			var locusName = $("#locusNameText").val();
			var locusAlias = $("#locusAliasText").val();
			var locusDesc = $("#locusDescTxt").val();

			$.ajax({
				type: "post",
				url: "<%=path %>/pages/system/submitLocusInfo.action",
				data: {"operateType": operateType, "locusInfo.id": locusId,
						"locusInfo.genoName":locusName, "locusInfo.genoAlias": locusAlias,
						"locusInfo.genoDesc":locusDesc},
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						alert("保存成功！");
						$("#sysUserDetailDiv").dialog("close");
						location.href="<%=path %>/pages/system/locusInfoManage.action";
					}else{
						alert(jsonData.errorMsg);
					}
				}
			});

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
  <div class="posittions"> 当前位置：系统管理 &gt;&gt; 基因座管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>基因座列表</legend>
      <div class="wf5"><input type="button" id="addLocusInfoBtn" class="button_one" value="添 加"/></div>
      <table class="table_one">
	      <thead>
	        <tr>
	          <th>编号</th>
	          <th>基因座名称</th>
	          <th>基因座别名</th>
	          <th>备注</th>
	          <th>操作</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="locusInfoList">
				<tr>
					<td><s:property value="genoNo" /></td>
					<td><s:property value="genoName" /></td>
					<td><s:property value="genoAlias" /></td>
					<td><s:property value="genoDesc" /></td>
					<td>
						<input type="hidden" name="hiddenId" value="<s:property value="id"/>" />
						<input type="hidden" name="hiddenName" value="<s:property value="genoName"/>" />
						<input type="hidden" name="hiddenAlias" value="<s:property value="genoAlias"/>" />
						<input type="hidden" name="hiddenDesc" value="<s:property value="genoDesc"/>" />
						<a href="#" onclick="editLocusInfoFtn(this);"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>&nbsp;
					</td>
				</tr>
	      	</s:iterator>
	      </tbody>
    </table>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    </fieldset>
  </div>
</div>

<!-- 基因座详情Dialog -->
<div id="locusInfoDetailDiv">
	<div class="main">
	    <input type="hidden" id="locusId" />
	    <input type="hidden" id="operateType" />
		<table class="table_two floatL marginL1">
	        <tr>
	          <td>基因座名称<span style="color:red;">*</span>：</td>
	          <td>
	          	<input type="text" name="locusName" id="locusNameText" class="text_one"/>
	          </td>
	        </tr>
	        <tr>
	          <td>基因座别名：</td>
	          <td>
	          	<input type="text" name="locusAlias" id="locusAliasText" class="text_one" />
	          </td>
	        </tr>
	       	<tr>
	          <td>备 注：</td>
	          <td>
	          	<textarea class="textarea_width300" id="locusDescTxt" rows="3"></textarea>
	          </td>
	        </tr>
	      </table>
      </div>
</div>

</body>
</html>
