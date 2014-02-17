<%@ page language="java" pageEncoding="UTF-8"%>
<%@	taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>比对结果查看</title>
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
	function viewCompareResultDetail(obj){
		var compareResultId = $("input[type='hidden']", $(obj).parent()).val();

		location.href="<%=path %>/pages/compare/intoCompareResultDetail.action?compareResult.id=" + compareResultId;
	}

	function addComments(obj){

	}

	function sortByTh(obj, sortColumn){
		var sortOrder = $("input[type='hidden']", $(obj)).val();
		$("#hiddenSortColumn").val(sortColumn);
		$("#hiddenSortOrder").val(sortOrder);

		$("#queryCompareResultForm").submit();
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
  <div class="posittions"> 当前位置：入库比对管理 &gt;&gt; 比对结果查看</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="queryCompareResult" method="post" id="queryCompareResultForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>样本编号:</td>
	          <td>
	          	<s:textfield name="compareResultQuery.sampleNo" cssClass="text_one" />
	          </td>
	          <td>入库人:</td>
	          <td>
	          	<s:select list="sysUserList" listKey="id" listValue="fullName" headerKey="" headerValue=""
	          		name="compareResultQuery.instoreUser" cssClass="select_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>入库时间:</td>
	          <td colspan="2">
	          	<s:textfield name="compareResultQuery.instoreDateStart" id="instoreDateStartTxt" cssClass="text_one" />
	          	&nbsp;&nbsp;到&nbsp;&nbsp;
	          	<s:textfield name="compareResultQuery.instoreDateEnd" id="instoreDateEndTxt" cssClass="text_one" />
	          </td>
	          <td>
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="compareResultQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="compareResultQuery.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>比对结果列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>分组</th>
          <th>
          	样本编号 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sourceSampleNo');">
		          		<s:if test="compareResultQuery.sortColumn == 'sourceSampleNo'">
		          			<s:if test="compareResultQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="compareResultQuery.sortOrder == 'DESC'">
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
          	比中样本编号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'matchSampleNo');">
          		<s:if test="compareResultQuery.sortColumn == 'matchSampleNo'">
          			<s:if test="compareResultQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="compareResultQuery.sortOrder == 'DESC'">
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
          	匹配个数 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sameNum');">
          		<s:if test="compareResultQuery.sortColumn == 'sameNum'">
          			<s:if test="compareResultQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="compareResultQuery.sortOrder == 'DESC'">
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
          	差异个数 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'diffNum');">
          		<s:if test="compareResultQuery.sortColumn == 'diffNum'">
          			<s:if test="compareResultQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="compareResultQuery.sortOrder == 'DESC'">
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
          	比中时间 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'matchDate');">
          		<s:if test="compareResultQuery.sortColumn == 'matchDate'">
          			<s:if test="compareResultQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="compareResultQuery.sortOrder == 'DESC'">
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
          <th>比中详情</th>
        </tr>
      </thead>
      <tbody>
      	<s:iterator value="compareResultViewList" status="su">
			<tr>
				<td><s:property value="#su.index+1"/></td>
				<td><s:property value="sourceSampleNo" /></td>
				<td><s:property value="matchSampleNo" /></td>
				<td><s:property value="sameNum" /></td>
				<td><s:property value="diffNum" /></td>
				<td><s:date name="matchDate" format="yyyy-MM-dd HH:mm" /></td>
				<td>
					<input type="hidden" value="<s:property value="id"/>"/>
					<a href="javascript:void(0);" onclick="viewCompareResultDetail(this);"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
					<%--
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="addComments(this);" class="red"><img src="<%=path %>/images/comments_add_32.png" width="24" height="24" style="vertical-align:middle;" alt="添加备注" title="添加备注"/></a>
					 --%>
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
