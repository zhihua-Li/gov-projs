<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>委托单位详情</title>
	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){

		$("#submitOrganizationForm").submit(function(){
			var organizationCodeVal = $("#organizationCodeText").val();
			var organizationNameVal = $("#organizationNameText").val();
			if(organizationCodeVal == ""){
				alert("请输入委托单位代码！");
				return false;
			}

			if(organizationNameVal == ""){
				alert("请输入委托单位名称！");
				return false;
			}
			
			var hasRepeat = false;
			$.ajax({
				url:"<%=path %>/pages/system/checkOrganizationRepeat.action",
				type:"post",
				data:{"consignOrganization.organizationName":organizationNameVal},
				dataType:"json",
				async:false,
				success:function(jsonData){
					if(jsonData.hasRepeat){
						hasRepeat = true;
					}
				}
			});
			
			if(hasRepeat){
				$("#errorHintSpan").html(" 委托单位名称已存在!");
				return false;
			}else{
				$("#errorHintSpan").html("");
			}

			return true;
		});

		$("#backBtn").live("click", function(){
			location.href="<%=path%>/pages/system/queryOrganization.action";
		});

	});
	//-->
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
	  	<div class="posittions"> 当前位置：首页 &gt;&gt; 委托单位管理 </div>
		  <div class="box_three">
		    <div class="height_one"></div>
		    <s:form action="submitOrganization" id="submitOrganizationForm" name="submitOrganizationForm" method="post">
		    	<s:hidden name="consignOrganization.id" id="consignOrganizationId"/>
				<s:hidden name="operateType" id="hiddenOperateType"/>

				 <fieldset class="fieldset_one">
			      <legend>委托单位信息</legend>
			      <div class="height_one"></div>
					<table class="table_two width2 floatL marginL1">
				        <tr>
				          <td>委托单位代码<span style="color:red;">*</span>：</td>
				          <td>
				          	<s:textfield name="consignOrganization.organizationCode" id="organizationCodeText" cssClass="text_one"/>
				          </td>
				        </tr>
				        <tr>
				          <td>委托单位名称<span style="color:red;">*</span>：</td>
				          <td>
				          	<s:textfield name="consignOrganization.organizationName" id="organizationNameText" cssClass="text_one" />
				          	<span style="color:red;font-size:16px;" id="errorHintSpan"></span>
				          </td>
				        </tr>
				        <tr>
				          <td>委托单位电话：</td>
				          <td>
				          	<s:textfield name="consignOrganization.phonenum" id="phonenumText" cssClass="text_one" />
				          </td>
				        </tr>
				        <tr>
				          <td>委托单位地址：</td>
				          <td>
				          	<s:textfield name="consignOrganization.organizationAddress" id="organizationAddressText" cssClass="text_one" />
				          </td>
				        </tr>
				        <tr>
				          <td>备注：</td>
				          <td>
				          	<s:textarea name="consignOrganization.remark" id="remarkText" cssClass="textarea_one" rows="4" />
				          </td>
				        </tr>
				        <tr>
				        	<td colspan="2">
				        		<s:submit value="保   存" cssClass="button_one"></s:submit>&nbsp;&nbsp;
								<button id="backBtn" class="button_one">返   回</button>
				        	</td>
				        </tr>
				      </table>
				</fieldset>
			</s:form>
		</div>
	</div>
  </body>
</html>
