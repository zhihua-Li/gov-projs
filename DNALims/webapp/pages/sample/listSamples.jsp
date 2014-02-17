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

		function deleteSelectedSample(){
			var checkedArr = $("input[type='checkbox']:checked", "tbody");
			if(checkedArr.length == 0){
				alert("请先勾选您要删除的样本！");
				return;
			}

			if(confirm("删除该样本时，其对应的检验信息及基因型数据都将被删除！ 确定继续吗？")){
				if(confirm("确认删除该样本及关联数据？")){
					var checkedSampleNo = "";
					for(var i = 0; i < checkedArr.length; i++){
						checkedSampleNo += $(checkedArr.get(i)).val() + ",";
					}

					checkedSampleNo = checkedSampleNo.substring(0, checkedSampleNo.length-1);

					$.ajax({
						url:"<%=path %>/pages/sample/deleteSample.action",
						type:"post",
						data:{"checkedId": checkedSampleNo},
						cache:false,
						dataType:"json",
						success:function(data){
							if(data.success){
								$("#querySamplesForm").submit();
							}
						}
					});
				}
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
								url:"<%=path%>/pages/sample/updateScanedSampleNo.action",
								type:"post",
								data:{"sampleInfo.id":sampleId, "sampleInfo.sampleNo":sampleNoTxt},
								dataType:"json",
								success:function(data){
									if(data.success){
										alert("条码号已修改！");
										$("td", $(obj).parent().parent()).eq(1).text(sampleNoTxt);
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
  <div class="posittions"> 当前位置：样本管理 &gt;&gt; 样本查询</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="querySamples" method="post" id="querySamplesForm" name="querySamplesForm">
	      <table class="table_two width2 floatL marginL1">
	        <tr>
	        	<td>委托编号：</td>
	          <td>
	          	<s:textfield name="sampleInfoQuery.consignmentNo" cssClass="text_one" />
	          </td>
	          <td>样本编号：</td>
	          <td>
	          	<s:textfield name="sampleInfoQuery.sampleNo" cssClass="text_one" />
	      		<span style="float:right;display:inline;color:red;">
	      			<s:checkbox name="sampleInfoQuery.fuzzyFlag"></s:checkbox>	模糊查询 &nbsp;&nbsp;&nbsp;&nbsp;
	      		</span>
	          </td>
	          <td>
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="sampleInfoQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="sampleInfoQuery.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
      </s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>样本列表</legend>
      <s:if test="#session.hasDeleteRole">
	      <div class="wf5" style="margin-right:20px;">
	     	<input type="checkbox" id="checkAll" onclick="checkAllFtn();"/> 全选 &nbsp;
	   		<input type="button" class="button_one" onclick="deleteSelectedSample();" value="删除选中样本"/>
	  	  </div>
	  </s:if>

      <table class="table_one">
      <thead>
        <tr>
          <th>序号</th>
          <th>
          	样本编号 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sampleNo');">
          		<s:if test="sampleInfoQuery.sortColumn == 'sampleNo'">
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
          	位置 &nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'samplePosition');">
          		<s:if test="sampleInfoQuery.sortColumn == 'samplePosition'">
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
          	委托号&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignmentNo');">
          		<s:if test="sampleInfoQuery.sortColumn == 'consignmentNo'">
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
          	委托单位&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'organizationId');">
          		<s:if test="sampleInfoQuery.sortColumn == 'organizationId'">
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
          	受理时间&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'acceptDate');">
          		<s:if test="sampleInfoQuery.sortColumn == 'acceptDate'">
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
          	受理人&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'acceptUser');">
          		<s:if test="sampleInfoQuery.sortColumn == 'acceptUser'">
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
          	受理状态&nbsp;
          	<a href="javascript:void(0);" onclick="sortByTh(this, 'acceptStatus');">
          		<s:if test="sampleInfoQuery.sortColumn == 'acceptStatus'">
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
          <th>操作</th>
          <th>删除</th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="sampleInfoViewList" status="su">
		<tr>
			<td><s:property value="#su.index+1" /></td>
			<td><s:property value="sampleNo" /></td>
			<td><s:property value="samplePosition" /></td>
			<td><s:property value="consignmentNo" /></td>
			<td><s:property value="organizationName" /><s:property value="organizationSubName" /></td>
			<td><s:date name="acceptDate" format="yyyy-MM-dd HH:mm" /></td>
			<td><s:property value="acceptUserName" /></td>
			<td>
				<s:if test="acceptStatus == 0">
					未受理
				</s:if>
				<s:if test="acceptStatus == 1">
					已受理
				</s:if>
			</td>
			<td>
				<input type="hidden" value="<s:property value="id"/>"/>
				<s:if test="boardId != null">
					<a href="<%=path %>/pages/sample/viewSampleBoard.action?boardInfo.id=<s:property value="boardId"/>"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
				</s:if>
				<s:else>
					-- --
				</s:else>
				&nbsp;
				<a href="javascript:void(0);" onclick="updateSampleNo(this, '<s:property value="id"/>','<s:property value="sampleNo" />');" class="red"><img src="<%=path %>/images/edit_32.png" width="24" height="24" style="vertical-align:middle;" alt="修改" title="修改"/></a>
			</td>

			<s:if test="#session.hasDeleteRole">
				<td>
					<input type="checkbox" value='<s:property value="id"/>'/>
				</td>
			</s:if>
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
