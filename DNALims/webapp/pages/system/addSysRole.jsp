<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色详情</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jstree/jquery.ztree.core-3.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jstree/jquery.ztree.excheck-3.4.min.js"></script>
	<script type="text/javascript">
	<!--
	var setting = {
		view: {
			expandSpeed: "fast",
			showIcon: true
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pId",
				rootPId: null
			}
		},
		check: {
			enable: true
		},
		callback: {
			beforeCheck:beforeModuleCheck,
			onCheck:onModuleCheck
		}
	};

	function beforeModuleCheck(treeId, treeNode) {
		return true;
	}

	function onModuleCheck(treeId, treeNode) {
	}

	function beforeAsync(treeId, treeNode) {
		return true;
	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
	}

	$(document).ready(function(){
		//加载所有模块的信息
		$.ajax({
			type: "get",
			url: "<%=path %>/pages/system/querySysMenus.action?operateType=1",
			dataType: "json",
			success: function(jsonSysMenuList){
				//var zNodes = eval("(" + jsonSysMenuList + ")");
				$.fn.zTree.init($("#moduleTree"), setting, jsonSysMenuList);
		}});

		$("#submitSysRoleForm").submit(function(){
			if($("#roleNameText").val() == ""){
				alert("请输入角色名称！");
				return false;
			}

			var zTree = $.fn.zTree.getZTreeObj("moduleTree");
			var nodes = zTree.getCheckedNodes(true);
			var $nodes = $(nodes);
			if($nodes.length < 1){
				alert("该角色尚未授权！");
				return false;
			}

			var checkedIds = new Array();
			$nodes.each(function(){
				var id = $(this).attr("id");
				checkedIds.push(id);
			});

			$("#sysMenuIdListStr").val(checkedIds.join(","));

			return true;
		});

		$("#backBtn").live("click", function(){
			location.href="<%=path%>/pages/system/querySysRole.action";
		});

	});
	//-->
	</script>
  </head>

  <body>
	<div class="main">
	  	<div class="posittions"> 当前位置：首页 &gt;&gt; 角色管理 </div>
		  <div class="box_three">
		    <div class="height_one"></div>
		    <s:form action="submitSysRole" id="submitSysRoleForm" name="submitSysRoleForm" method="post">
				<s:hidden name="operateType" id="hiddenOperateType"/>
				<s:hidden name="sysMenuIdListStr" id="sysMenuIdListStr" />

				 <fieldset class="fieldset_one">
			      <legend>角色信息</legend>
			      <div class="height_one"></div>
					<table class="table_two width2 floatL marginL1">
				        <tr>
				          <td>角色名称<span style="color:red;">*</span>：</td>
				          <td>
				          	<s:textfield name="sysRole.roleName" id="roleNameText" cssClass="text_one"/>
				          </td>
				        </tr>
				        <tr>
				          <td>角色代码：</td>
				          <td>
				          	<s:textfield name="sysRole.roleType" id="roleTypeText" cssClass="text_one" />
				          </td>
				        </tr>
				        <tr>
				          <td>描 述：</td>
				          <td>
				          	<s:textarea name="sysRole.roleDesc" id="roleDescText" cssClass="textarea_one" rows="4" />
				          </td>
				        </tr>
				      </table>
				</fieldset>

				<fieldset class="fieldset_one">
			      <legend>授权信息</legend>
			      <div class="height_one"></div>
					<div>
				  		<ul id="moduleTree" class="ztree"></ul>
				    </div>
				</fieldset>

				<div style="float:left; display:inline; margin-left:200px; margin-bottom:20px;">
					<s:submit value="保   存" cssClass="button_one"></s:submit>&nbsp;&nbsp;
					<button id="backBtn" class="button_one">返   回</button>
				</div>

			</s:form>
		</div>
	</div>
  </body>
</html>
