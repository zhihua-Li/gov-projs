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
	<script type="text/javascript">

	function checkAllFtn(){
		if($("#checkAll").attr("checked") && $("#checkAll").attr("checked") == "checked"){
			$("input[type='checkbox']", "tbody").attr("checked", "checked");
		}else{
			$("input[type='checkbox']:checked", "tbody").removeAttr("checked");
		}
	}

	function deleteImportedSample(){
		var checkedArr = $("input[type='checkbox']:checked", "tbody");
		if(checkedArr.length == 0){
			alert("未勾选或没有可删除样本！");
			return;
		}

		if(confirm("确认删除选中样本吗？")){
			var checkedSampleNo = "";
			for(var i = 0; i < checkedArr.length; i++){
				checkedSampleNo += $(checkedArr.get(i)).val() + ",";
			}

			checkedSampleNo = checkedSampleNo.substring(0, checkedSampleNo.length-1);

			$.ajax({
				url:"<%=path %>/pages/compare/deleteCodisSample.action",
				type:"post",
				data:{"checkedSampleNo": checkedSampleNo},
				cache:false,
				dataType:"json",
				success:function(data){
					if(data.success){
						$("#importedCodisSampleQueryForm").submit();
					}
				}
			});
		}
	}

		function updateSampleNo(obj, sampleId, sampleNo){
			var dialogDiv = $("<div style='text-align:center;'><br/>样本条码号：<input type='text' class='text_one' name='sampleNoTxt' value='" + sampleNo + "' /></div>");
			dialogDiv.dialog({
				autoOpen:true,
				height:160,
				width:400,
				modal:true,
				title:"修改条码",
				buttons:{
					"确定":function(){
						var sampleNoTxt = $("input[name='sampleNoTxt']", $(this)).val();
						if(sampleNoTxt != ''){
							$.ajax({
								url:"<%=path%>/pages/compare/updateCodisSampleNo.action",
								type:"post",
								data:{"codisSample.id":sampleId, "codisSample.sampleNo":sampleNoTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										alert("条码号已修改！");
										$("td", $(obj).parent().parent()).eq(3).text(sampleNoTxt);
										$(dialogDiv).dialog("close");
									}
								}
							});
						}else{
							alert("条码号不能为空！");
						}
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		}

		function viewCodisSampleGene(sampleId){

			$("#codisSampleGeneDetailDivIframe").attr("src", "<%=path %>/pages/compare/viewGeneInfo.action?codisSample.id=" + sampleId);
			$("#codisSampleGeneDetailDiv").dialog({
				autoOpen: true,
				height:	520,
				width: 840,
				modal: true,
				title: "查看基因型",
				close: function(){
					$(this).dialog("close");
				}
			});

		}

		function sortByTh(obj, sortColumn){
			var sortOrder = $("input[type='hidden']", $(obj)).val();
			$("#hiddenSortColumn").val(sortColumn);
			$("#hiddenSortOrder").val(sortOrder);

			$("#querySamplesForm").submit();
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
  <div class="posittions"> 当前位置：入库比对管理 &gt;&gt; 已导入CODIS样本查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="importedCodisSampleQuery" method="post" id="importedCodisSampleQueryForm" name="importedCodisSampleQueryForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	          <td align="right">委托单位：</td>
	          <td>
		      	<s:select name="consignOrganizationId" id="consignOrganizationId" cssClass="select_one" list="consignOrganizationList" listKey="id" listValue="organizationName" headerKey="" headerValue="请选择..."/>
	          </td>
	          <td align="right">板号：</td>
	          <td>
	          	<s:textfield name="codisSample.boardInfoNo" cssClass="text_one" />
	          </td>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
        	  <td align="right">样本条码号：</td>
	          <td>
	          	<s:textfield name="codisSample.sampleNo" cssClass="text_one" />
	          </td>
	          <td align="right">CODIS导入人：</td>
	          <td>
	          	<s:select name="codisSample.importUser" id="importUser" cssClass="select_one" list="sysUserList" listKey="id" listValue="fullName" headerKey="" headerValue="请选择..."/>
	          </td>
	          <td>
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="codisSample.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="codisSample.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>样本列表</legend>
      <div class="wf5" style="margin-right:20px;">
      	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
    	<input type="button" class="button_one" onclick="deleteImportedSample();" value="删除选中样本"/>
    	</div>
      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	委托单位&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignOrganizationId');">
          		<s:if test="codisSample.sortColumn == 'consignOrganizationId'">
          			<s:if test="codisSample.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="codisSample.sortOrder == 'DESC'">
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
          	板号&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'boardInfoNo');">
          		<s:if test="codisSample.sortColumn == 'boardInfoNo'">
          			<s:if test="codisSample.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="codisSample.sortOrder == 'DESC'">
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
          	样本编号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sampleNo');">
          		<s:if test="codisSample.sortColumn == 'sampleNo'">
          			<s:if test="codisSample.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="codisSample.sortOrder == 'DESC'">
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
          	导入时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'importDate');">
          		<s:if test="codisSample.sortColumn == 'importDate'">
          			<s:if test="codisSample.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="codisSample.sortOrder == 'DESC'">
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
          	导入人&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'importUser');">
          		<s:if test="codisSample.sortColumn == 'importUser'">
          			<s:if test="codisSample.sortOrder == 'ASC'">
          				<img src="<%=path %>/images/sort(-1).png"/>
          				<input type="hidden" value="DESC"/>
          			</s:if>
          			<s:if test="codisSample.sortOrder == 'DESC'">
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
          <th>删除</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="codisSampleList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="consignOrganizationName" /></td>
			<td><s:property value="boardInfoNo" /></td>
			<td><s:property value="sampleNo" /></td>
			<td><s:date name="importDate" format="yyyy-MM-dd HH:mm" /></td>
			<td><s:property value="importUserName" /></td>
			<td>
				<input type="hidden" value="<s:property value="id"/>"/>
				<a href="#" onclick="viewCodisSampleGene('<s:property value="id"/>')" class="red"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查看基因型" title="查看基因型"/></a>
				&nbsp;
				<a href="javascript:void(0);" onclick="updateSampleNo(this, '<s:property value="id"/>','<s:property value="sampleNo" />');"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>
			</td>
			<td>
				<input type="checkbox" value='<s:property value="sampleNo"/>'/>
			</td>
		</tr>
     	</s:iterator>
      </tbody>
    </table>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    </fieldset>
  </div>

  <div style="display:none;" id="codisSampleGeneDetailDiv">
	<iframe id="codisSampleGeneDetailDivIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>
</div>
</body>
</html>
