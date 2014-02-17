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
<title>分析复核</title>
<link rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">

	function inputRecord(id, consignmentNo){
		$("#listAnalysisCheckForm").attr("action", "<%=path %>/pages/examine/intoAnalysisRecord.action?boardInfo.id=" + id + "&boardInfo.consignmentNo=" + consignmentNo);
		$("#listAnalysisCheckForm").submit();
	}

	function sortByTh(obj, sortColumn){
		var sortOrder = $("input[type='hidden']", $(obj)).val();
		$("#hiddenSortColumn").val(sortColumn);
		$("#hiddenSortOrder").val(sortOrder);

		$("#listAnalysisCheckForm").submit();
	}
	$(function(){

		$("#examineDateStart").datetimepicker();

		$("#examineDateEnd").datetimepicker();


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
<div class="main">
  <div class="posittions"> 当前位置：检验过程管理  &gt;&gt; 分析</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="listWaitAnalysis" name="listAnalysisCheckForm" id="listAnalysisCheckForm" method="post">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>委托号：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.consignmentNo"/>
	          </td>
	          <td>板号：&nbsp;</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.boardNo"/>
	      		<span style="float:right;display:inline;color:red;">
	      			<s:checkbox name="boardInfoQuery.fuzzyFlag"></s:checkbox>	模糊查询
	      		</span>
	          </td>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>检测时间：&nbsp;</td>
	          <td colspan="3">
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.examineDateStart" id="examineDateStart"/>
	          	&nbsp;至&nbsp;
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.examineDateEnd" id="examineDateEnd"/>
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
      <legend>上样板列表</legend>
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
          	委托单位&nbsp;
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
          	检测时间&nbsp;
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
          	检测人&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'examineUserName');">
          		<s:if test="boardInfoQuery.sortColumn == 'examineUserName'">
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
        </tr>
      </thead>
      <tbody>
        <s:iterator value="boardInfoList" status="su">
			<tr>
				<td><s:property value="#su.index+1" /></td>
				<td><s:property value="consignmentNo" /></td>
				<td><s:property value="boardNo" /></td>
				<td><s:property value="organizationName" /><s:property value="organizationSubName" /></td>
				<td><s:date name="examineDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:property value="examineUserName" /></td>
				<td>
					<input type="hidden" value="<s:property value="id"/> "/>
					<a href="javascript:void(0);" onclick="inputRecord('<s:property value="id"/>', '<s:property value="consignmentNo"/>');" class="red">填写分析记录</a>
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
