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
<title>取样任务管理</title>
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
			$("#importDateStartTxt").datetimepicker();

			$("#importDateEndTxt").datetimepicker();

		});
/*
		function generatePunchExtractionRecord(obj, boardId){
			$.ajax({
				type: "get",
*/
//				url: "<%=path %>/pages/sample/generatePunchExtractionRecord.action?boardInfo.id=" + boardId,
/*				cache: false,
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						alert("上样表生成成功！");
						$(obj).attr("alt","重新生成上样表");
						$(obj).attr("title","重新生成上样表");
						$(obj).next("a").css("display", "");
					}
				}
			});
		}
*/
		function checkAllFtn(){
			if($("#checkAll").attr("checked") && $("#checkAll").attr("checked") == "checked"){
				$("input[type='checkbox']", ".table_one").attr("checked", "checked");
			}else{
				$("input[type='checkbox']:checked", ".table_one").removeAttr("checked");
			}
		}

		function downloadSelectedSYTable(){
			var checkedArr = $("input[type='checkbox']:checked", ".table_one");
			if(checkedArr.length == 0){
				alert("请先勾选您要下载的记录！");
				return;
			}

			var checkedBoardId = "";
			for(var i = 0; i < checkedArr.length; i++){
				checkedBoardId += $(checkedArr.get(i)).val() + ",";
			}

			checkedBoardId = checkedBoardId.substring(0, checkedBoardId.length-1);

			$.ajax({
				url:"<%=path %>/pages/sample/zipDownSyTable.action",
				type:"post",
				cache:false,
				data:{"checkedBoardId":checkedBoardId},
				beforeSend: function (){
	                $.blockUI({
	                    fadeIn : 0,
	                    fadeOut : 0,
	                    showOverlay : false,
	                    message:"<h1>正在下载，请稍候...</h1>"
	                });
	            },
				dataType:"json",
				success:function(data){
					$("#hiddenZipedFilePath", "#downloadFileForm").val(data.zipedFilePath);
					$("#hiddenCheckedBoardId", "#downloadFileForm").val(data.checkedBoardId);
					$("#downloadFileForm").attr("action", "<%=path %>/pages/sample/downSyTable.action");
					$("#downloadFileForm").submit();

					for(var i = 0; i < checkedArr.length; i++){
						$(checkedArr.get(i)).next("span").text(parseInt($(checkedArr.get(i)).next("span").text())+1);
						$(checkedArr.get(i)).removeAttr("checked");
					}

					$("#checkAll").removeAttr("checked");
				},
				complete:function(){
					$.unblockUI();
				}
			});
		}

		function deleteBoardInfo(id) {
			if(confirm("删除该上样板时，其对应的样本及相关检验信息都将被删除！ 确定继续吗？")){
				if(confirm("确认删除该上样板及关联数据？")){
					document.location.href="<%=path %>/pages/sample/deleteBoardInfo.action?boardInfo.id=" + id;
				}
			}
		}

		function viewBoardInfo(id){
			$("#queryExtractionTasksForm").attr("action", "<%=path %>/pages/sample/viewBoardInfo.action?boardInfo.id=" + id + "&operateType=2");
			$("#queryExtractionTasksForm").submit();
		}

		function sortByTh(obj, sortColumn){
			var sortOrder = $("input[type='hidden']", $(obj)).val();
			$("#hiddenSortColumn").val(sortColumn);
			$("#hiddenSortOrder").val(sortOrder);

			$("#queryExtractionTasksForm").submit();
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 取样任务管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="queryExtractionTasks" method="post" id="queryExtractionTasksForm" name="queryExtractionTasksForm">
	      <table class="table_two width7 floatL marginL1">
	        <tr>
	          <td>委托号:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.consignmentNo"/>
	          </td>
	          <td>板号:</td>
	          <td colspan="2">
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.boardNo"/>
	      		<span style="float:right;display:inline;color:red;">
	      			<s:checkbox name="boardInfoQuery.fuzzyFlag"></s:checkbox>	模糊查询
	      		</span>
	          </td>
	        </tr>
	        <tr>
	          <td>取样时间:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.importDateStart" id="importDateStartTxt"/>
	          	&nbsp;&nbsp;到&nbsp;&nbsp;
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.importDateEnd" id="importDateEndTxt"/>
	          </td>
	          <td>取样人:</td>
	          <td>
	          	<s:select cssClass="select_one" name="boardInfoQuery.importUser" list="importUserList" listKey="id" listValue="fullName" headerKey="" headerValue="" />
	          </td>
			  <td>
			  	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="boardInfoQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="boardInfoQuery.sortOrder" id="hiddenSortOrder"/>
			  </td>
	        </tr>
	      </table>
	     </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>导入列表</legend>
      <div class="wf5" style="margin-right:20px;">
     	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
     	<button class="button_one" onclick="downloadSelectedSYTable();"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="22" height="22" style="vertical-align:middle;" alt="下载上样表" title="下载上样表"/>下载上样表</button>
  	  </div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	委托号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignmentNo');">
          		<s:if test="boardInfoQuery.sortColumn == 'consignmentNo'">
          			<s:if test="boardInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="boardInfoQuery.sortOrder == 'DESC'">
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
          	板号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'boardNo');">
          		<s:if test="boardInfoQuery.sortColumn == 'boardNo'">
          			<s:if test="boardInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="boardInfoQuery.sortOrder == 'DESC'">
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
          	取样时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'importDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'importDate'">
          			<s:if test="boardInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="boardInfoQuery.sortOrder == 'DESC'">
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
          	取样人&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'importUser');">
          		<s:if test="boardInfoQuery.sortColumn == 'importUser'">
          			<s:if test="boardInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="boardInfoQuery.sortOrder == 'DESC'">
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
          <th>操作</th>
          <th>下载上样表 [下载次数]</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="boardInfoList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="consignmentNo" /></td>
			<td><s:property value="boardNo" /></td>
			<td><s:date name="importDate" format="yyyy-MM-dd HH:mm" /></td>
			<td><s:property value="importUserName" /></td>
			<td>
				<input type="hidden" value="<s:property value="id"/> "/>
				<a href="javascript:void(0);" class="red" onclick="viewBoardInfo('<s:property value="id"/>');"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
				<s:if test="#session.hasDeleteRole">
					&nbsp;
					<a href="javascript:void(0);" onclick="deleteBoardInfo('<s:property value="id"/>');"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
				</s:if>

			</td>
			<td>
				<%--
				<s:if test="generateSyTableFlag == 1">
					<a href="javascript:void(0);" onclick="generatePunchExtractionRecord(this, '<s:property value="id"/>');"><img src="<%=path %>/images/word_32.png" width="24" height="24" style="vertical-align:middle;" alt="重新生成上样表" title="重新生成上样表"/></a>
					&nbsp;&nbsp;

				</s:if>
				<s:else>
					<a href="javascript:void(0);" onclick="generatePunchExtractionRecord(this, '<s:property value="id"/>');"><img src="<%=path %>/images/word_32.png" width="24" height="24" style="vertical-align:middle;" alt="生成上样表" title="生成上样表"/></a>
					&nbsp;&nbsp;
					<a style="display:none;" href="javascript:void(0);" onclick="downloadPunchExtractionRecord('<s:property value="id"/>');"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载上样表" title="下载上样表"/></a>
				</s:else>
				<a href="javascript:void(0);" onclick="downloadPunchExtractionRecord('<s:property value="id"/>');"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载上样表" title="下载上样表"/></a>
				 --%>
				<input type="checkbox" value="<s:property value="id"/>" />&nbsp;&nbsp;&nbsp; [ <span><s:property value="syrecordDownloadCnt"/></span> ]

			</td>
		</tr>
     	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    <form id="downloadFileForm" method="post">
    	<input type="hidden" name="zipedFilePath" id="hiddenZipedFilePath"/>
    	<input type="hidden" name="checkedBoardId" id="hiddenCheckedBoardId"/>
    </form>
    </fieldset>
  </div>
</div>
</body>
</html>
