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
<title>样本查询</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript">

		function updateFileType(obj, sampleId, sampleNo){
			var dialogDiv = $("<div style='text-align:center;'><br/>文件类型：<input type='text' class='text_one' name='sampleNoTxt' value='" + sampleNo + "' /></div>");
			dialogDiv.dialog({
				autoOpen:true,
				height:160,
				width:400,
				modal:true,
				title:"修改文件类型",
				buttons:{
					"确定":function(){
						var sampleNoTxt = $.trim($("input[name='sampleNoTxt']", $(this)).val());
						if(sampleNoTxt != ''){
							$.ajax({
								url:"<%=path%>/pages/file/updateFileType.action",
								type:"post",
								data:{"dictItem.id":sampleId, "dictItem.dictValue":sampleNoTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										alert("文件类型已修改！");
										$("td", $(obj).parent().parent()).eq(1).text(sampleNoTxt);
										$(dialogDiv).dialog("close");
									}
								}
							});
						}else{
							alert("文件类型不能为空！");
						}
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}

		function addFileTypeFtn(){
			var dialogDiv = $("<div style='text-align:center;'><br/>文件类型：<input type='text' class='text_one' name='sampleNoTxt' value='' /></div>");
			dialogDiv.dialog({
				autoOpen:true,
				height:160,
				width:400,
				modal:true,
				title:"添加文件类型",
				buttons:{
					"确定":function(){
						var sampleNoTxt = $.trim($("input[name='sampleNoTxt']", $(this)).val());
						if(sampleNoTxt != ''){
							$.ajax({
								url:"<%=path%>/pages/file/addFileType.action",
								type:"post",
								data:{"dictItem.dictValue":sampleNoTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										alert("文件类型已添加！");
										$(dialogDiv).dialog("close");
										document.location.href="<%=path %>/pages/file/fileTypeManage.action";
									}
								}
							});
						}else{
							alert("文件类型不能为空！");
						}
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}

		function deleteFileType(id){
			if(confirm("确认删除该文件类型吗?")){
				document.location.href="<%=path%>/pages/file/deleteFileType.action?dictItem.id=" + id;
			}
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
  <div class="posittions"> 当前位置：文件管理 &gt;&gt; 文件类型管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>文件类型列表</legend>
      <div class="wf5"><input type="button" onclick="addFileTypeFtn();" class="button_one" value="添 加"/></div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>文件类型</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="fileTypeList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="dictValue" /></td>
			<td>
				<input type="hidden" value='<s:property value="id"/>'/>
				<a href="javascript:void(0);" onclick="updateFileType(this, '<s:property value="id"/>','<s:property value="dictValue" />');" class="red"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>
				&nbsp;
				<a href="javascript:void(0);" onclick="deleteFileType('<s:property value="id"/>');"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>

			</td>
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
