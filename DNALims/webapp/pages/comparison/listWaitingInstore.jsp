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
<title>样本入库比对</title>
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
	$(document).ready(function(){

		$("input[type='checkbox']", "#listTbody").live("click", function(){
			var checkedSampleVal = $("input[type='hidden']", $(this).parent()).val();
			var checkedTRIdx = $(this).parent().parent().index();

			$("input[type='hidden']", "#listTbody").each(function(){
				var idx = $(this).parent().parent().index();
				var tempVal = $("input[type='hidden']", $(this).parent()).val();
				if(checkedTRIdx != idx && checkedSampleVal == tempVal){
					if($(this).attr("checked")){
						$(this).removeAttr("checked");
					}
				}
			});
		});


		$("#batchInstoreBtn").live("click", function(){
			var checkedSamples = $("input[type='checkbox']:checked", "#listTbody");
			if(checkedSamples && checkedSamples.length > 0){
				var checkedSampleIds = "";
				for(var i = 0; i<checkedSamples.length; i++){
					if(i==0){
						checkedSampleIds += $(checkedSamples.get(i)).val();
					}else{
						checkedSampleIds +=  "," + $(checkedSamples.get(i)).val();
					}
				}

				$.ajax({
					url:"<%=path%>/pages/compare/qualitySampleCompare.action",
					type:"post",
					data:{"checkedSampleIds": checkedSampleIds},
					dataType:"json",
					success:function(data){
						if(data.hasPolluteSample){
							if(confirm("样本" + data.pollutedSample + "与质控样本比中，\n是否跳过并继续入库比对？")){
								var pollutedIdList = data.pollutedIdList;
								for(var i = 0; i < pollutedIdList.length; i++){
									for(var j = 0; j<checkedSamples.length; j++){
										if($(checkedSamples.get(j)).val() == pollutedIdList[i]){
											$(checkedSamples.get(j)).removeAttr("checked");
											break;
										}
									}
								}

								$("#batchInstoreForm").submit();
							}
						}else{
							$("#batchInstoreForm").submit();
						}
					}
				});

			}else{
				alert("请先选择需要入库比对的记录！");
				return false;
			}
		});

		$("input[type='checkbox']", "#listTbody").live("click", function(){
			var checkedSampleVal = $("input[type='hidden']", $(this).parent()).val();
			var checkedTRIdx = $(this).parent().parent().index();

			$("input[type='checkbox']", "#listTbody").each(function(){
				var idx = $(this).parent().parent().index();
				var tempVal = $("input[type='hidden']", $(this).parent()).val();
				if(checkedTRIdx != idx && checkedSampleVal == tempVal){
					if($(this).attr("checked")){
						$(this).removeAttr("checked");
					}
				}
			});
		});

		$("#checkAll").live("click", function(){
			if($(this).attr("checked") && $(this).attr("checked") == "checked"){
				$("input[name='checkedId']").attr("checked", "checked");
			}else{
				$("input[name='checkedId']").removeAttr("checked");
			}
		});

	});

	function viewSampleDetail(geneId){

		$("#resultDetailDivIframe").attr("src", "<%=path %>/pages/compare/intoSampleGeneDetail.action?sampleSourceGeneQuery.id=" + geneId);
		$("#resultDetailDiv").dialog({
			autoOpen: true,
			height:	540,
			width: 840,
			modal: true,
			title: "查看样本信息",
			close: function(){
				$(this).dialog("close");
			}
		});

	}

	function sortByTh(obj, sortColumn){
		var sortOrder = $("input[type='hidden']", $(obj)).val();
		$("#hiddenSortColumn").val(sortColumn);
		$("#hiddenSortOrder").val(sortOrder);

		$("#queryWaitingInstoreComparisonForm").submit();
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
  <div class="posittions"> 当前位置：入库比对管理 &gt;&gt; 样本入库比对</div>
  <div class="box_three">
    <div class="height_one"></div>
    <fieldset class="fieldset_one">
      <legend>查询条件</legend>
      <div class="height_one"></div>
      <s:form action="queryWaitingInstoreComparison" method="post" id="queryWaitingInstoreComparisonForm" name="queryWaitingInstoreComparisonForm">
	      <table class="table_two floatL marginL1">
	        <tr>
	          <td>委托号:</td>
	          <td>
	          	<s:textfield name="sampleSourceGeneQuery.consignmentNo" cssClass="text_one" />
	          </td>
	          <td>样本编号:</td>
	          <td>
	          	<s:textfield name="sampleSourceGeneQuery.sampleNo" cssClass="text_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>复核人:</td>
	          <td>
	          	<s:select list="sysUserList" listKey="id" listValue="fullName" headerKey="" headerValue=""
	          		name="sampleSourceGeneQuery.reviewUser" cssClass="select_one" />
	          </td>
	          <td>复核时间:</td>
	          <td>
	          	<s:textfield name="sampleSourceGeneQuery.reviewDateStart" id="reviewDateStartTxt" cssClass="text_one" />
	          	&nbsp;&nbsp;到&nbsp;&nbsp;
	          	<s:textfield name="sampleSourceGeneQuery.reviewDateEnd" id="reviewDateEndTxt" cssClass="text_one" />
	          </td>
	        </tr>
	        <tr>
	          <td>入库状态：</td>
	          <td>
	          	<s:select name="sampleSourceGeneQuery.alignDbFlag" cssClass="select_one" list="#{0:'未入库',1:'已入库'}" headerKey="" headerValue="请选择..."></s:select>
	          </td>
	          <td colspan="2">
	          	<s:submit id="pageSubmit" cssClass="button_one" value="查	询"/>
	            <s:hidden id="pageSize" name="pageSize" />
				<s:hidden id="recordCount" name="recordCount" />
		  		<s:hidden id="pageIndex" name="pageIndex" />
		  		<s:hidden name="sampleSourceGeneQuery.sortColumn" id="hiddenSortColumn"/>
		  		<s:hidden name="sampleSourceGeneQuery.sortOrder" id="hiddenSortOrder"/>
	          </td>
	        </tr>
	      </table>
	   	</s:form>
    </fieldset>
    <fieldset class="fieldset_one">
      <legend>样本列表</legend>
	  	<div class="wf_right width500">
	  		<input type="checkbox" id="checkAll" title="全 选" />全选
	  		&nbsp;&nbsp;&nbsp;
			<input type="button" id="batchInstoreBtn" value="入库比对" class="button_one" />
			&nbsp;&nbsp;&nbsp;
	    </div>
	    <s:form action="batchInstore" method="post" id="batchInstoreForm">
	      	<table class="table_one">
		      <thead>
		        <tr>
		          <th>
		          	委托号 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'consignmentNo');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'consignmentNo'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'boardNo'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
	<%--
		          <th>
		          	取样时间 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'importDate');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'importDate'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
	 --%>
		          <th>
		          	样本编号 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'sampleNo');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'sampleNo'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
		          	复核人 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'reviewUser');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'reviewUser'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
		          	复核时间 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'reviewDate');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'reviewDate'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
		          <th>查看</th>
		          <th>
		          	入库操作 &nbsp;
		          	<a href="javascript:void(0);" onclick="sortByTh(this, 'alignDbFlag');">
		          		<s:if test="sampleSourceGeneQuery.sortColumn == 'alignDbFlag'">
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'ASC'">
		          				<img src="<%=path %>/images/sort(-1).png"/>
		          				<input type="hidden" value="DESC"/>
		          			</s:if>
		          			<s:if test="sampleSourceGeneQuery.sortOrder == 'DESC'">
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
		      <tbody id="listTbody">
		        <s:iterator value="sampleSourceGeneList" status="su">
					<tr>
						<td><s:property value="consignmentNo" /></td>
						<td><s:property value="boardNo" /></td>
				<%--		<td><s:property value="importDate"/></td>	 --%>
						<td><s:property value="sampleNo" /></td>
						<td><s:property value="reviewUserName" /></td>
						<td><s:date name="reviewDate" format="yyyy-MM-dd HH:mm" /></td>
						<td>
							<a href="javascript:void(0)" onclick="viewSampleDetail('<s:property value="id"/>');" class="red"><img src="<%=path %>/images/view_text_32.png" width="24" height="24" style="vertical-align:middle;" alt="查 看" title="查 看"/></a>
						</td>
						<td>
							<input type="hidden" value="<s:property value="sampleId"/>"/>
							<s:if test="alignDbFlag == 1">
								<span style="color:red;">已入库</span>
							</s:if>
							<s:else>
								<s:if test="hasInstorGeneFlag == 1">
									<input type="checkbox" name="checkedId" value="<s:property value="id"/>" disabled="disabled"/>
								</s:if>
								<s:else>
									<input type="checkbox" name="checkedId" value="<s:property value="id"/>"/>
								</s:else>
							</s:else>
						</td>
					</tr>
		     	</s:iterator>
		      </tbody>
	    	</table>
    	</s:form>
    <div class="height_one"></div>
    <div id="pageFooterDiv"></div>
    </fieldset>
  </div>
</div>

<div style="display:none;" id="resultDetailDiv">
	<iframe id="resultDetailDivIframe" src="" width="100%" frameborder="0" scrolling="auto" height="100%"/>
</div>
</body>
</html>

