<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>委托单位列表</title>
	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/css/pagefoot.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageFoot.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.pageList.js"></script>

	<script type="text/javascript">
		function deleteConsignOrganizationFtn(obj){
			if(confirm("确认删除该委托单位吗？")){
				var deleteOrganizationId = $("input[type='hidden']", $(obj).parent()).val();
				document.location.href = "<%=path%>/pages/system/submitOrganization.action?consignOrganization.id=" + deleteOrganizationId  + "&operateType=3";
			}
		}

		function editConsignOrganizationFtn(obj) {
			var editOrganizationId = $("input[type='hidden']", $(obj).parent()).val();
			document.location.href="<%=path%>/pages/system/editOrganization.action?consignOrganization.id=" + editOrganizationId + "&operateType=2";
		}

		function addOrganizationFtn() {
			document.location.href="<%=path%>/pages/system/editOrganization.action?operateType=1";
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
  <div class="posittions"> 当前位置：系统管理 &gt;&gt; 委托单位管理  </div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>委托单位查询</legend>
      <div class="height_one"></div>
      <s:form action="queryOrganization" method="post" id="queryOrganizationForm" name="queryOrganizationForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td>委托单位代码：</td>
	          <td>
	          	<s:textfield name="consignOrganization.organizationCode" cssClass="text_one" />
	          </td>
	          <td>委托单位名称：</td>
	          <td>
	          	<s:textfield name="consignOrganization.organizationName" cssClass="text_one" />
	          </td>
	          <td colspan="2">
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>委托单位列表</legend>
      <div class="wf5"><input type="button" onclick="addOrganizationFtn();" class="button_one" value="添 加"/></div>
      <table class="table_one">
	      <thead>
	        <tr>
	          <th>序号</th>
	          <th>委托单位代码</th>
	          <th>委托单位名称</th>
	          <th>委托单位电话</th>
	          <th>委托单位地址</th>
	          <th>操 作</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="consignOrganizationList" status="co">
				<tr>
					<td><s:property value="#co.index+1" /></td>
					<td><s:property value="organizationCode" /></td>
					<td><s:property value="organizationName" /></td>
					<td><s:property value="phonenum" /></td>
					<td><s:property value="organizationAddress" /></td>
					<td>
						<input type="hidden" value="<s:property value="id"/> "/>
						<a href="#" onclick="editConsignOrganizationFtn(this);"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>&nbsp;
						&nbsp;
						<a href="#" onclick="deleteConsignOrganizationFtn(this);"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
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
