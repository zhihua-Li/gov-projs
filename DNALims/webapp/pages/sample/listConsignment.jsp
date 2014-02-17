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
<title>委托查询</title>
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

	<script type="text/javascript">
		$(document).ready(function(){
			$("#consignDateStartTxt").datetimepicker();

			$("#consignDateEndtTxt").datetimepicker();


		});

		function deleteConsignment(consignmentId){
			if(confirm("删除此委托时，其对应的样本、上样板及相关检验信息都将被删除！ 确定继续吗？")){
				if(confirm("确认删除该委托及关联数据？")){
					document.location.href="<%=path %>/pages/sample/deleteConsignment.action?consignment.id=" + consignmentId;
				}
			}
		}

		function intoScanSampleFtn(consignmentId){
			document.location.href="<%=path %>/pages/sample/intoScaneSamples.action?sampleInfoQuery.consignmentId=" + consignmentId;
		}

		function batchAcceptSampleFtn(consignmentId){
			document.location.href="<%=path %>/pages/sample/intoBatchAcceptSamples.action?consignment.id=" + consignmentId;
		}

		function downloadConsignmentRecord(consignmentId){
			document.location.href="<%=path %>/pages/sample/generateAcceptInfoRecord.action?consignment.id=" + consignmentId;
		}

		function editConsignmentFtn(consignmentId){
			$("#queryConsignmentForm").attr("action", "<%=path %>/pages/sample/editConsignment.action?consignment.id=" + consignmentId);
			$("#queryConsignmentForm").submit();
		}

		function intoSampleExcelImportFtn(consignmentId){
			document.location.href="<%=path %>/pages/sample/intoSampleExcelImoprt.action?consignment.id=" + consignmentId;
		}

		function viewAllSample(id){
			$("#queryConsignmentForm").attr("action", "<%=path %>/pages/sample/queryConsignmentSamples.action?sampleInfoQuery.consignmentId=" + id);
			$("#queryConsignmentForm").submit();
		}
		function viewAllAcceptedSample(id){
			$("#queryConsignmentForm").attr("action", "<%=path %>/pages/sample/queryConsignmentSamples.action?sampleInfoQuery.acceptStatus=1&sampleInfoQuery.consignmentId=" + id);
			$("#queryConsignmentForm").submit();
		}

		function sortByTh(obj, sortColumn){
			var sortOrder = $("input[type='hidden']", $(obj)).val();
			$("#hiddenSortColumn").val(sortColumn);
			$("#hiddenSortOrder").val(sortOrder);

			$("#queryConsignmentForm").submit();
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 委托查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="queryConsignment" method="post" id="queryConsignmentForm" name="queryConsignmentForm">
	      <table class="table_two width7 floatL marginL1">
	      	<tr>
	          <td>委托号:</td>
	          <td>
	          	<s:textfield cssClass="text_one" name="consignmentQuery.consignmentNo"/>
	          </td>
	          <td>委托单位:</td>
	          <td>
	          	<s:select cssClass="select_one" name="consignmentQuery.organizationId" list="organizationList" listKey="id" listValue="organizationName" headerKey="" headerValue="" />
	          </td>
	        </tr>
	        <tr>
	          <td>送检时间:</td>
	          <td colspan="2">
	          	<s:textfield cssClass="text_one" name="consignmentQuery.consignDateStart" id="consignDateStartTxt">
	          		<s:param name="value">
	          			<s:date name="consignmentQuery.consignDateStart" format="yyyy-MM-dd HH:mm"/>
	          		</s:param>
	          	</s:textfield>
	          	&nbsp;&nbsp;到&nbsp;&nbsp;
	          	<s:textfield cssClass="text_one" name="consignmentQuery.consignDateEnd" id="consignDateEndtTxt">
	          		<s:param name="value">
	          			<s:date name="consignmentQuery.consignDateEnd" format="yyyy-MM-dd HH:mm"/>
	          		</s:param>
	          	</s:textfield>
	          </td>
			  <td>
			  	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="consignmentQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="consignmentQuery.sortOrder" id="hiddenSortOrder"/>
			  </td>
	        </tr>
	      </table>
	     </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>委托列表</legend>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	委托号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignmentNo');">
          		<s:if test="consignmentQuery.sortColumn == 'consignmentNo'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          		<s:if test="consignmentQuery.sortColumn == 'organizationId'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          		<s:if test="consignmentQuery.sortColumn == 'consignDate'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          	受理人 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'createUser');">
          		<s:if test="consignmentQuery.sortColumn == 'createUser'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          	受理时间 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'createDate');">
          		<s:if test="consignmentQuery.sortColumn == 'createDate'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          	样本总数 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sampleCount');">
          		<s:if test="consignmentQuery.sortColumn == 'sampleCount'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
          	已受理样本数 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'acceptedSampleCount');">
          		<s:if test="consignmentQuery.sortColumn == 'acceptedSampleCount'">
          			<s:if test="consignmentQuery.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="consignmentQuery.sortOrder == 'DESC'">
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
      	<s:iterator value="consignmentList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td>
				<a href="javascript:void(0);" onclick="editConsignmentFtn('<s:property value="id"/>');" title="点击查看委托信息"><s:property value="consignmentNo" /></a>
			</td>
			<td><s:property value="organizationName" /><s:property value="organizationSubName" /></td>
			<td><s:date name="consignDate" format="yyyy-MM-dd HH:mm" /></td>
			<td><s:property value="createUserName" /></td>
			<td><s:date name="createDate" format="yyyy-MM-dd HH:mm" /></td>
			<td>
				<a href="javascript:void(0);" onclick="viewAllSample('<s:property value="id"/>');" title="查看当前委托的所有样本"><s:property value="sampleCount"/></a>
			</td>
			<td>
				<a href="javascript:void(0);" onclick="viewAllAcceptedSample('<s:property value="id"/>')" title="查看已受理的样本"><s:property value="acceptedSampleCount"/></a>
			</td>
			<td>
				<input type="hidden" value="<s:property value="id"/>"/>
				<s:if test="#session.hasDeleteRole">
					<a href="javascript:void(0);" onclick="deleteConsignment('<s:property value="id"/>');" class="red"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
					&nbsp;
				</s:if>
				<a href="javascript:void(0);" onclick="intoScanSampleFtn('<s:property value="id"/>')" class="red"><img src="<%=path %>/images/barcode_32.png" width="32" height="32" style="vertical-align:middle;" alt="扫描样本" title="扫描样本"/></a>
				&nbsp;
				<a href="javascript:void(0);" onclick="batchAcceptSampleFtn('<s:property value="id"/>')" class="red"><img src="<%=path %>/images/batch_accept.png" width="24" height="24" style="vertical-align:middle;" alt="批量受理样本" title="批量受理样本"/></a>
				&nbsp;
				<a href="javascript:void(0);" onclick="intoSampleExcelImportFtn('<s:property value="id"/>');" class="red"><img src="<%=path %>/images/excel_32.png" width="24" height="24" style="vertical-align:middle;" alt="导入样本Excel" title="导入样本Excel"/></a>
				&nbsp;
				<a href="javascript:void(0);" onclick="downloadConsignmentRecord('<s:property value="id"/>');" class="red"><img src="<%=path %>/images/folder_Blue_Downloads_32.png" width="24" height="24" style="vertical-align:middle;" alt="下载委托表" title="下载委托表"/></a>
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
