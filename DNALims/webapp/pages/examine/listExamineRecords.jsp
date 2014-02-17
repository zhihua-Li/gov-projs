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
$(function(){
	$("#importDateStart").datetimepicker();
	$("#importDateEnd").datetimepicker();

	$("#pcrDateStart").datetimepicker();
	$("#pcrDateEnd").datetimepicker();

	$("#examineDateStart").datetimepicker();
	$("#examineDateEnd").datetimepicker();

	$("#analysisDateStart").datetimepicker();
	$("#analysisDateEnd").datetimepicker();

	$("#examineReviewDateStart").datetimepicker();
	$("#examineReviewDateEnd").datetimepicker();

});

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

		$("#listExamineRecordsForm").submit();
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
  <div class="posittions"> 当前位置：检验过程管理 &gt;&gt; 检验记录管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="listExamineRecords" method="post" id="listExamineRecordsForm" name="listExamineRecordsForm">
	      <table class="table_two width7 floatL marginL1">
	        <tr>
	          <td>委托号:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.consignmentNo"/>
	          </td>
	          <td>板号:</td>
	          <td>
	      		<s:textfield cssClass="text_one" name="boardInfoQuery.boardNo"/>
	      		<span style="float:right;display:inline;color:red;">
	      			<s:checkbox name="boardInfoQuery.fuzzyFlag"></s:checkbox>	模糊查询
	      		</span>
	          </td>
	        </tr>
	         <tr>
	          <td>取样时间：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.importDateStart" id="importDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.importDateEnd" id="importDateEnd"/>
	          </td>
	          <td>扩增时间：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.pcrDateStart" id="pcrDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.pcrDateEnd" id="pcrDateEnd"/>
	          </td>
	        </tr>
	        <tr>
	          <td>检测时间：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.examineDateStart" id="examineDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.examineDateEnd" id="examineDateEnd"/>
	          </td>
	          <td>分析时间：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.analysisDateStart" id="analysisDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.analysisDateEnd" id="analysisDateEnd"/>
	          </td>
	        </tr>
	        <tr>
	          <td>复核时间：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.examineReviewDateStart" id="examineReviewDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_nowidth width150" name="boardInfoQuery.examineReviewDateEnd" id="examineReviewDateEnd"/>
	          </td>
	          <td>状态：</td>
	          <td>
	          	<s:select name="boardInfoQuery.examineStatus" list="examineStatusList" listKey="dictKey" listValue="dictValue"
	          		headerKey="" headerValue="--全部--" cssClass="select_one"/>
	          </td>
	        </tr>
	        <tr>
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
      <legend>检验记录列表</legend>
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
          <th>
          	状态&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'examineStatus');">
          		<s:if test="boardInfoQuery.sortColumn == 'examineStatus'">
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
          	扩增时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'pcrDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'pcrDate'">
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
          	检验时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'examineDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'examineDate'">
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
          	分析时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'analysisDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'analysisDate'">
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
          	复核时间<a href="javascript:void(0);" onclick="sortByTh(this, 'examineReviewDate');">
          		<s:if test="boardInfoQuery.sortColumn == 'examineReviewDate'">
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
        <s:iterator value="boardInfoList" status="su">
			<tr>
				<td><s:property value="#su.index+1" /></td>
				<td><s:property value="consignmentNo" /></td>
				<td><s:property value="boardNo" /></td>
				<td><s:property value="examineTypeName"/></td>
				<td><s:property value="examineStatusName" /></td>
				<td><s:date name="importDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:date name="pcrDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:date name="examineDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:date name="analysisDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:date name="examineReviewDate" format="yyyy-MM-dd HH:mm" /></td>
				<td>
					<%--
					<a href="<%=path %>/pages/examine/viewExamineRecord.action?boardInfoQuery.id=<s:property value="id"/>"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
					<s:if test="pcrFlag == 1">
						&nbsp;
						<s:if test="generateRecordFlag == 1">
							<a href="javascript:void(0);" onclick="generateExamineRecord(this, '<s:property value="id"/>');"><img src="<%=path %>/images/word_32.png" width="24" height="24" style="vertical-align:middle;" alt="重新生成检验记录表" title="重新生成检验记录表"/></a>
							&nbsp;&nbsp;
							<a href="javascript:void(0);" onclick="downloadExamineRecord('<s:property value="id"/>');"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载检验记录表" title="下载检验记录表"/></a>
						</s:if>
						<s:else>
							<a href="javascript:void(0);" onclick="generateExamineRecord(this, '<s:property value="id"/>');"><img src="<%=path %>/images/word_32.png" width="24" height="24" style="vertical-align:middle;" alt="生成检验记录表" title="生成检验记录表"/></a>
							&nbsp;&nbsp;
							<a style="display:none;" href="javascript:void(0);" onclick="downloadExamineRecord('<s:property value="id"/>');"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载检验记录表" title="下载检验记录表"/></a>
						</s:else>
					</s:if>
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
