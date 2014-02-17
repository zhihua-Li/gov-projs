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
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#acceptDateStartTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});

			$("#acceptDateEndTxt").datepicker({
				changeMonth: true,
				changeYear: true,
				autoSize:true,
				yearRange:"2010:"
			});
		});

		function sortByTh(obj, sortColumn){
			var sortOrder = $("input[type='hidden']", $(obj)).val();
			$("#hiddenSortColumn").val(sortColumn);
			$("#hiddenSortOrder").val(sortOrder);

			$("#sampleStatisticsForm").submit();
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
  <div class="posittions"> 当前位置：查询统计 &gt;&gt; 样本统计</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>统计条件</legend>
      <div class="height_one"></div>
      <s:form action="sampleStatistics" method="post" id="sampleStatisticsForm" name="sampleStatisticsForm">
	      <table class="table_two width7 floatL marginL1">
	        <tr>
	          <td>受理时间：</td>
	          <td>
	          	<s:textfield name="sampleInfoQuery.acceptDateStart" id="acceptDateStartTxt" cssClass="text_nowidth width5" />
	          	&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;
	          	<s:textfield name="sampleInfoQuery.acceptDateEnd" id="acceptDateEndTxt" cssClass="text_nowidth width5" />
	          </td>
	          <td>
	          	<s:submit id="pageSubmit" cssClass="button_one" value="统    计"/>
		  		<s:hidden name="sampleInfoQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="sampleInfoQuery.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>样本总数列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	委托单位编号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'ORGANIZATIONCODE');">
          		<s:if test="sampleInfoQuery.sortColumn == 'ORGANIZATIONCODE'">
          			<s:if test="sampleInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="sampleInfoQuery.sortOrder == 'DESC'">
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
          	委托单位名称 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'ORGANIZATIONNAME');">
          		<s:if test="sampleInfoQuery.sortColumn == 'ORGANIZATIONNAME'">
          			<s:if test="sampleInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="sampleInfoQuery.sortOrder == 'DESC'">
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
          	受理样本总数&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'SAMPLECOUNT');">
          		<s:if test="sampleInfoQuery.sortColumn == 'SAMPLECOUNT'">
          			<s:if test="sampleInfoQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="sampleInfoQuery.sortOrder == 'DESC'">
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
        </tr>
      </thead>
      <tbody>
        <s:iterator value="resultList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="ORGANIZATIONCODE" /></td>
			<td><s:property value="ORGANIZATIONNAME" /></td>
			<td><s:property value="SAMPLECOUNT" /></td>
		</tr>
     	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    <div class="height_one"></div>
    </fieldset>
  </div>
</div>
</body>
</html>
