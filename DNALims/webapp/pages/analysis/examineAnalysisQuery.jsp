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
<script type="text/javascript">

	function viewSampleGenes(boardId){
		location.href="<%=path %>/pages/analysis/viewSampleSourceGeneList.action?sampleSourceGeneQuery.boardId=" + boardId;
	}

	function sortByTh(obj, sortColumn){
		var sortOrder = $("input[type='hidden']", $(obj)).val();
		$("#hiddenSortColumn").val(sortColumn);
		$("#hiddenSortOrder").val(sortOrder);

		$("#resultCheckForm").submit();
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
  <div class="posittions"> 当前位置：检验结果管理  &gt;&gt; 结果查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="resultCheck" method="post" id="resultCheckForm" name="resultCheckForm">
	      <table class="table_two width7 floatL marginL1">
	        <tr>
	          <td>委托号：</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="boardInfoQuery.consignmentNo"/>
	          </td>
	          <td>样本编号：</td>
	          <td>
	      		<s:textfield cssClass="text_one" name="boardInfoQuery.sampleNo"/>
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
      <s:form action="" method="post" id="batchReviewForm">
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
		          	检验类型 &nbsp;
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
		          <th>操作</th>
		        </tr>
		      </thead>
		      <tbody id="listTbody">
		        <s:iterator value="boardInfoList" status="su">
					<tr>
						<td>
							<s:property value="#su.index+1"/>
						</td>
						<td><s:property value="consignmentNo" /></td>
						<td><s:property value="boardNo" /></td>
						<td><s:property value="examineTypeName" /></td>
						<td><s:date name="pcrDate" format="yyyy-MM-dd HH:mm" /></td>
						<td><s:date name="analysisDate" format="yyyy-MM-dd HH:mm" /></td>
						<td>
							<a href="javascript:void(0)" onclick="viewSampleGenes('<s:property value="id"/>');" class="red"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
						</td>
					</tr>
		     	</s:iterator>
		      </tbody>
	    </table>
	    <div class="height_one"></div>
	    <div id="pageFooterDiv"></div>
    </s:form>
    </fieldset>
  </div>
</div>

<div style="display:none;" id="resultDetailDiv">
	<iframe id="resultDetailDivIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>
</body>
</html>
