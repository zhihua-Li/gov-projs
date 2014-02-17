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
	<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="<%=path %>/js/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){

			$("#uploadDateStartTxt").datetimepicker();

			$("#uploadDateEndTxt").datetimepicker();

		});

		function deleteSelectedFiles(){
			var checkedArr = $("input[type='checkbox']:checked", "tbody");
			if(checkedArr.length == 0){
				alert("请先勾选您要删除的文件！");
				return;
			}

			if(confirm("确认删除选中的文件吗？")){
				var checkedFileId = "";
				for(var i = 0; i < checkedArr.length; i++){
					checkedFileId += $(checkedArr.get(i)).val() + ",";
				}

				checkedFileId = checkedFileId.substring(0, checkedFileId.length-1);

				$.ajax({
					url:"<%=path %>/pages/file/deleteFileRecord.action",
					type:"post",
					data:{"checkedFileId": checkedFileId},
					cache:false,
					dataType:"json",
					success:function(data){
						if(data.success){
							$("#fileManageForm").submit();
						}
					}
				});
			}
		}
/*
		function browseFolder(path) {
			try {
				var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
				var Shell = new ActiveXObject("Shell.Application");
				var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
				if (Folder != null) {
					Folder = Folder.items(); // 返回 FolderItems 对象
					Folder = Folder.item(); // 返回 Folderitem 对象
					Folder = Folder.Path; // 返回路径
					if (Folder.charAt(Folder.length - 1) != "") {
						Folder = Folder + "";
					}
					document.getElementById(path).value = Folder;
					return Folder;
				}
			} catch (e) {
				alert(e.message);
			}
		}
*/

		function sortByTh(obj, sortColumn){
			var sortOrder = $("input[type='hidden']", $(obj)).val();
			$("#hiddenSortColumn").val(sortColumn);
			$("#hiddenSortOrder").val(sortOrder);

			$("#fileManageForm").submit();
		}

		function checkAllFtn(){
			if($("#checkAll").attr("checked") && $("#checkAll").attr("checked") == "checked"){
				$("input[type='checkbox']", "tbody").attr("checked", "checked");
			}else{
				$("input[type='checkbox']:checked", "tbody").removeAttr("checked");
			}
		}

		function downloadSelectedFiles(){

			var checkedArr = $("input[type='checkbox']:checked", "tbody");
			if(checkedArr.length == 0){
				alert("请先勾选您要下载的文件！");
				return;
			}

			var checkedFileId = "";
			for(var i = 0; i < checkedArr.length; i++){
				checkedFileId += $(checkedArr.get(i)).val() + ",";
			}

			$.blockUI({
                fadeIn : 0,
                fadeOut : 0,
                showOverlay : false,
                message:"<h1>正在下载，请稍候...</h1>"
            });

			checkedFileId = checkedFileId.substring(0, checkedFileId.length-1);
			document.location.href="<%=path %>/pages/file/downUploadedFile.action?checkedFileId=" + checkedFileId;

			setTimeout(function(){
				$.unblockUI();
			}, 1000);

	 }
/*
			var dialogDiv = $("<div style='text-align:center;'><br/>文件保存目录：<input type='text' class='text_one' id='savePath' value='' /> &nbsp; <input type='button' class='button_one' value='浏 览' onclick='browseFolder(\"savePath\");'/></div>");
			dialogDiv.dialog({
				autoOpen:true,
				height:160,
				width:400,
				modal:true,
				title:"指定文件保存目录",
				buttons:{
					"确定":function(){
						var savePathTxt = $("#savePath", $(this)).val();
						if(savePathTxt != ''){
							$.ajax({
								url:"pages/file/downUploadedFile.action",
*/
/*								type:"post",
								data:{"checkedFileId":checkedFileId, "downloadFileSavePath": savePathTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										dialogDiv.dialog("close");
									}
								}
							});
						}else{
							alert("请先指定文件保存目录！");
						}
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}
*/

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
  <div class="posittions"> 当前位置：文件管理 &gt;&gt; 文件查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="fileManage" method="post" id="fileManageForm" name="fileManageForm">
	      <table class="table_two width9 floatL marginL1">
	        <tr>
	          <td>文件名称：</td>
	          <td>
	          	<s:textfield name="fileUploadRecordQuery.fileName" cssClass="text_one" />
	          </td>
	          <td>文件类型：</td>
	          <td>
	          	<s:select name="fileUploadRecordQuery.fileType" list="fileTypeList" listKey="dictKey" listValue="dictValue" headerKey="" headerValue="请选择..." cssClass="select_one" />
	          </td>
	          <td>上传人：</td>
	          <td>
	          	<s:textfield name="fileUploadRecordQuery.uploadUser" cssClass="text_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>上传时间：</td>
	          <td>
	          	<s:textfield name="fileUploadRecordQuery.uploadDateStart" id="uploadDateStartTxt" cssClass="text_one" />
	          </td>
	          <td>至</td>
	          <td>
	          	<s:textfield name="fileUploadRecordQuery.uploadDateEnd" id="uploadDateEndTxt" cssClass="text_one" />
	          </td>
	          <td colspan="3">
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="fileUploadRecordQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="fileUploadRecordQuery.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>文件列表</legend>
      <div class="wf5" style="margin-right:20px;">
      	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
    	<input type="button" class="button_one" onclick="downloadSelectedFiles();" value="下载选中文件"/>
    	<s:if test="#session.hasDeleteRole">
	    	&nbsp;
	    	<input type="button" class="button_one" onclick="deleteSelectedFiles();" value="删除选中文件"/>
	    </s:if>
    	</div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	文件类型 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'fileType');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'fileType'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>
          	文件名称 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'fileName');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'fileName'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>
          	文件大小（bytes） &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'fileSize');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'fileSize'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>
          	文件存放路径 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'fileFullPath');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'fileFullPath'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>
          	上传人&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'uploadUser');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'uploadUser'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>
          	上传时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'uploadDate');">
          		<s:if test="fileUploadRecordQuery.sortColumn == 'uploadDate'">
          			<s:if test="fileUploadRecordQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="fileUploadRecordQuery.sortOrder == 'DESC'">
          				<img src="<%=path %>/images/sort(1).png"/>
          				<input type="hidden" value="ASC"/>
          			</s:if>
          		</s:if>
          		<s:else>
          			<img src="<%=path %>/images/sort(null).png"/>
          			<input type="hidden" value="DESC"/>
          		</s:else>
          	</a>
          </th>
          <th>选择</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="fileUploadRecordList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="fileTypeName" /></td>
			<td><s:property value="fileName" /></td>
			<td><s:property value="fileSize" /></td>
			<td><s:property value="fileFullPath" /></td>
			<td><s:property value="uploadUser" /></td>
			<td><s:date name="uploadDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td>
				<input type="checkbox" value='<s:property value="id"/>'/>
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
</body>
</html>
