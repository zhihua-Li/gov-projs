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
<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/blockui/jquery.blockUI.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#consignDateStart").datepicker({
			changeMonth: true,
			changeYear: true,
			autoSize:true,
			yearRange:"2010:"
		});

		$("#consignDateEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			autoSize:true,
			yearRange:"2010:"
		});
	});

//	function downloadExamineInfo(obj){
//		var boardId = $("input[type='hidden']", $(obj).parent()).val();
//
//		document.location.href = "<%=path %>/pages/analysis/downloadExamineInfo.action?sampleSourceGeneQuery.boardId=" + boardId;
//	}

//	function downloadPcrExaminInfo(obj){
//		var boardId = $("input[type='hidden']", $(obj).parent()).val();

//		document.location.href = "<%=path %>/pages/analysis/downloadPcrExamineInfo.action?sampleSourceGeneQuery.boardId=" + boardId;
//	}

	function checkAllFtn(){
		if($("#checkAll").attr("checked") && $("#checkAll").attr("checked") == "checked"){
			$("input[type='checkbox']", ".table_one").attr("checked", "checked");
		}else{
			$("input[type='checkbox']:checked", ".table_one").removeAttr("checked");
		}
	}

	function downloadSelectedExamineRecord(){
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
			url:"<%=path %>/pages/analysis/zipDownloadPcrExamineInfo.action",
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
				$("#downloadFileForm").attr("action", "<%=path %>/pages/analysis/downloadPcrExamineInfo.action");
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

	function sortByTh(obj, sortColumn){
		var sortOrder = $("input[type='hidden']", $(obj)).val();
		$("#hiddenSortColumn").val(sortColumn);
		$("#hiddenSortOrder").val(sortOrder);

		$("#examineRecordForm").submit();
	}
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
<div class="main">
  <div class="posittions"> 当前位置：检验结果管理 &gt;&gt; 检验记录下载</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="examineRecord" name="examineRecordForm" id="examineRecordForm" method="post">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>委托号：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.consignmentNo"/>
	          </td>
	          <td>委托单位：</td>
	          <td>
	      		<s:select name="boardInfoQuery.organizationId" list="organizationList"
	      			listKey="id" listValue="organizationName" headerKey="" headerValue="" cssClass="select_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>委托时间:</td>
	          <td colspan="3">
	          	<s:textfield name="boardInfoQuery.consignDateStart" id="consignDateStart" cssClass="text_one"/>
	          	&nbsp;到&nbsp;
	          	<s:textfield name="boardInfoQuery.consignDateEnd" id="consignDateEnd" cssClass="text_one"/>
	          </td>
	        </tr>
	        <tr>
	          <td>检验类型:</td>
	          <td>
	          	<s:select name="boardInfoQuery.examineType" list="examineTypeList" listKey="dictKey" listValue="dictValue"
	          		headerKey="" headerValue="" cssClass="select_one" />
	          </td>
	          <td colspan="2" align="right">
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
      <legend>检验列表</legend>
      <div class="wf5" style="margin-right:20px;">
     	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
     	<button class="button_one" onclick="downloadSelectedExamineRecord();"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="22" height="22" style="vertical-align:middle;" alt="下载检验记录表" title="下载检验记录表"/>下载检验记录表</button>
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
          	委托单位 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'organizationId');">
          		<s:if test="boardInfoQuery.sortColumn == 'organizationId'">
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
          	送检时间 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'consignDate'">
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
          	检验类型&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'examineType');">
          		<s:if test="boardInfoQuery.sortColumn == 'examineType'">
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
          <th>检验记录表下载 [下载次数]</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="boardInfoList" status="bi">
			<tr>
				<td><s:property value="#bi.index+1"/></td>
				<td><s:property value="consignmentNo"/></td>
				<td><s:property value="organizationName"/><s:property value="organizationSubName"/></td>
				<td><s:date name="consignDate" format="yyyy-MM-dd HH:mm"/></td>
				<td><s:property value="boardNo"/></td>
				<td><s:date name="importDate" format="yyyy-MM-dd HH:mm"/></td>
				<td><s:property value="examineTypeName"/></td>
				<td>
					<input type="hidden" value="<s:property value="id"/>"/>
					<%--
					<a href="javascript:void(0);" onclick="downloadExamineInfo(this);" class="red">样本检验信息表下载</a>
					&nbsp;&nbsp;&nbsp;

					<a href="javascript:void(0);" onclick="downloadPcrExaminInfo(this);" class="red"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="直扩检验记录表下载" title="直扩检验记录表下载"/></a>
					--%>
					<input type="checkbox" value="<s:property value="id"/>" />&nbsp;&nbsp;&nbsp; [ <span><s:property value="examineRecordDownloadCnt"/></span> ]
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

