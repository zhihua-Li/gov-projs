<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>试剂盒列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>

	<script type="text/javascript">

		function addReagentFtn() {
			location.href = "<%=path %>/pages/system/intoReagentKit.action?operateType=1";
		}

		function editReagentFtn(obj) {
			var reagentKitId = $("input[type='hidden']", $(obj).parent()).val();
			location.href = "<%=path %>/pages/system/intoReagentKit.action?operateType=2&reagentKit.id=" + reagentKitId;
		}

		function deleteReagentFtn(obj){
			if(confirm("确定删除该试剂盒？")){
				var reagentKitId = $("input[type='hidden']", $(obj).parent()).val();
				location.href = "<%=path %>/pages/system/intoReagentKit.action?operateType=3&reagentKit.id=" + reagentKitId;
			}
		}

		function submitLocusInfo(){
			var operateType = $("#operateType").val();
			var locusId = $("#locusId").val();
			var locusName = $("#locusNameText").val();
			var locusAlias = $("#locusAliasText").val();
			var locusDesc = $("#locusDescTxt").val();

			$.ajax({
				type: "post",
				url: "<%=path %>/pages/system/submitLocusInfo.action",
				data: {"operateType": operateType, "locusInfo.id": locusId,
						"locusInfo.genoName":locusName, "locusInfo.genoAlias": locusAlias,
						"locusInfo.genoDesc":locusDesc},
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						alert("保存成功！");
						$("#sysUserDetailDiv").dialog("close");
						location.href="<%=path %>/pages/system/locusInfoManage.action";
					}else{
						alert(jsonData.errorMsg);
					}
				}
			});
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
  <div class="posittions"> 当前位置：系统管理 &gt;&gt; 试剂盒管理</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>试剂盒列表</legend>
      <div class="wf5"><input type="button" onclick="addReagentFtn();" class="button_one" value="添 加"/></div>
      <table class="table_one">
	      <thead>
	        <tr>
	          <th>序号</th>
	          <th>试剂盒名称</th>
	          <th>试剂盒提供商</th>
	          <th>入库时间</th>
	          <th>备注</th>
	          <th>操作</th>
	        </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="reagentKitList" status="rk">
				<tr>
					<td><s:property value="#rk.index+1" /></td>
					<td><s:property value="reagentName" /></td>
					<td><s:property value="reagentProducer" /></td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm" /></td>
					<td><s:property value="reagentDesc" /></td>
					<td>
						<input type="hidden" value="<s:property value="id"/>" />
						<a href="#" onclick="editReagentFtn(this);"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>&nbsp;
						&nbsp;
						<a href="#" onclick="deleteReagentFtn(this);"><img src="<%=path %>/images/symbols_Delete_32.png" width="24" height="24" style="vertical-align:middle;" alt="删除" title="删除"/></a>
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

<!-- 用户详情Dialog -->
<div id="locusInfoDetailDiv">
	<div class="main">
	    <input type="hidden" id="locusId" />
	    <input type="hidden" id="operateType" />
		<table class="table_two floatL marginL1">
	        <tr>
	          <td>基因座名称<span style="color:red;">*</span>：</td>
	          <td>
	          	<input type="text" name="locusName" id="locusNameText" class="text_one"/>
	          </td>
	        </tr>
	        <tr>
	          <td>基因座别名：</td>
	          <td>
	          	<input type="text" name="locusAlias" id="locusAliasText" class="text_one" />
	          </td>
	        </tr>
	       	<tr>
	          <td>备 注：</td>
	          <td>
	          	<textarea class="textarea_width300" id="locusDescTxt" rows="3"></textarea>
	          </td>
	        </tr>
	      </table>
      </div>
</div>

</body>
</html>
