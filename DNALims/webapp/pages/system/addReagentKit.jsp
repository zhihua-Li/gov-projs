<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>试剂盒信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link rel="stylesheet" href="<%=path %>/css/style.css" />
	<link rel="stylesheet" href="<%=path %>/js/jquery-ui/jquery.ui.all.css" />
	<script type="text/javascript" src="<%=path %>/js/lib/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=path %>/js/autoIframeHeight.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lib/jquery.bgiframe-2.1.2.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript">
		function upLineFtn(obj){
			var currentTR = $(obj).parent().parent();
			if(currentTR.index() == 0){
				return;
			}else{
				var prevTR = currentTR.prev("tr");
				var locusNameTdInPrevTR = $("td", prevTR).eq(0).html();
				var locusNameTdInCurrentTR = $("td", currentTR).eq(0).html();

				$("td", prevTR).eq(0).html(locusNameTdInCurrentTR);
				$("td", currentTR).eq(0).html(locusNameTdInPrevTR)
			}
		}

		function downLineFtn(obj){
			var locusCount = $("tr", "#tbody").length;
			var currentTR = $(obj).parent().parent();
			if(currentTR.index() == (locusCount - 1)){
				return;
			}else{
				var nextTR = currentTR.next("tr");
				var locusNameTdInNextTR = $("td", nextTR).eq(0).html();
				var locusNameTdInCurrentTR = $("td", currentTR).eq(0).html();

				$("td", nextTR).eq(0).html(locusNameTdInCurrentTR);
				$("td", currentTR).eq(0).html(locusNameTdInNextTR)
			}
		}

		function removeLineFtn(obj){
			var locusCount = $("tr", "#tbody").length;
			if(locusCount == 1){
				alert("基因座列表不能为空！");
				return;
			}else{
				$(obj).parent().parent().remove();
			}
		}

		$(document).ready(function(){

			$("#submitReagentKitForm").submit(function(){
				var reagentNameVal = $("#reagentNameTxt").val();
				if(reagentNameVal == ""){
					alert("请输入试剂盒名称！");
					return false;
				}
			});

			$("#addNewLocus").live("click", function(){
				var locusCount = $("tr", "#tbody").length;
				var allLocusCount = $("#allLocusCount").val();
				if(locusCount == allLocusCount){
					alert("已达到最大基因座个数！如果是新基因座，请先在“基因座管理”页面添加！");
					return;
				}

				var lastSn;
				if(locusCount == 0){
					lastSn = 0;
				}else{
					lastSn = parseInt($("tr", "#tbody").eq(locusCount-1).text());
				}


				$("#addLocusDiv").dialog({
					autoOpen: true,
					height:	200,
					width: 460,
					modal: true,
					title: "添加基因座",
					buttons: {
						"确定": function(){
							var newLocusName = $("option:selected", "#newLocusNo").text();
							var newLocusNo = $("option:selected", "#newLocusNo").val();
							var locusTR = $("tr", "#tbody");
							var hasLocus = false;
							for(var i = 0; i < locusTR.length; i++){
								var locusTD = $("td", $(locusTR.get(i))).eq(0)
								var existLocus = $("input[type='hidden']", locusTD).val();
								if(existLocus == newLocusNo){
									hasLocus = true;
									break;
								}
							}

							if(hasLocus){
								alert("该基因座已存在，请重新选择！");
							}else{
								var newLine = "<tr>"
											+ "<td>" + (lastSn+1) + "</td>"
											+ "<td>" + newLocusName + "<input type='hidden' name='genoNo' value='" + newLocusNo + "' /></td>"
										//	+ "<td>" + (locusCount + 1) + "</td>"
										//	+ "<td><a href='javascript:void(0);' onclick='upLineFtn(this);'>上移</a>"
				        				//	+ "&nbsp;"
				        				//	+ "<a href='javascript:void(0);' onclick='downLineFtn(this);'>下移</a>"
				        					+ "<td>&nbsp;"
				        					+ "<a href='javascript:void(0);' onclick='removeLineFtn(this);'>删除</a>"
				        					+ "</td></tr>";

				        		$("#tbody").append(newLine);
								alert("添加成功！");
								$(this).dialog("close");
							}
						},
						"取消": function(){
							$(this).dialog("close");
						}
					},
					close: function(){
						$(this).dialog("close");
					}
				});

			});

		});

		function importPanel(){
			$("#importPanelDiv").dialog({
				autoOpen: true,
				height:	200,
				width: 460,
				modal: true,
				title: "导入Panel文件",
				buttons: {
					"确定": function(){
						$.ajaxFileUpload({
								url:'<%=path %>/pages/system/importPanelFile.action',
								secureuri:false,
								fileElementId:'panelFile',
								dataType: 'json',
								success: function (data, status) {
									var resultList = data;
									var htmlStr1 = "";
									for(var i = 0; i < resultList.length; i++){
										htmlStr1 += "<tr>"
											+ "<td>" + (i+1) + "</td>"
											+ "<td>" + resultList[i].genoName + "<input type='hidden' name='genoNo' value='" + resultList[i].genoNo + "' /></td>"
											+ "<td>&nbsp;"
				        					+ "<a href='javascript:void(0);' onclick='removeLineFtn(this);'>删除</a>"
				        					+ "</td></tr>";
									}

									$("tr", "#tbody").remove();
									$("#tbody").append(htmlStr1);

									$("#importPanelDiv").dialog("close");
								},
								error: function (data, status, e) {
									alert(e);
								}
							});
					},
					"取消": function(){
						$(this).dialog("close");
					}
				},
				close: function(){
					$(this).dialog("close");
				}
			});
		}

		function backFtn(){
			document.location.href="<%=path %>/pages/system/reagentKitManage.action";
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
  <div class="posittions"> 当前位置：首页&gt;&gt; 试剂盒信息</div>
  <div class="box_three">
    <div class="height_one"></div>
	<s:form action="submitReagentKit" name="submitReagentKitForm" id="submitReagentKitForm" method="post">
		<s:hidden name="reagentKit.id" />
	    <s:hidden name="operateType" />

	    <fieldset class="fieldset_one">
	      <legend>试剂盒信息</legend>
	      <div class="height_one"></div>

		      <table class="table_two width2 floatL marginL1">
		        <tr>
		          <td>试剂盒名称<span style="color:red;">*</span>：</td>
		          <td>
		          	<s:textfield cssClass="text_one" name="reagentKit.reagentName" id="reagentNameTxt" />
		          </td>
		          <td>版本：</td>
		          <td>
		          	<s:textfield cssClass="text_one" name="reagentKit.reagentVersion"/>
		          </td>
		        </tr>
<%--
		        <tr>
		          <td>试剂盒提供商：</td>
		          <td>
		          	<s:textfield cssClass="text_one" name="reagentKit.reagentProducer"/>
		          </td>
		          <td>版本：</td>
		          <td>
		          	<s:textfield cssClass="text_one" name="reagentKit.reagentVersion"/>
		          </td>
		        </tr>
 --%>
		        <tr>
		          <td>备注：</td>
		          <td colspan="3">
		          	<s:textarea cssClass="textarea_width300" rows="3" name="reagentKit.reagentDesc" />
		          </td>
		        </tr>
		      </table>
	    </fieldset>
	    <fieldset class="fieldset_one">
	      <legend>基因座列表</legend>
	      <div class="wf_right width500">
		  	<input type="button" class="button_one" id="addNewLocus" value="添加基因座"/>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<input type="button" class="button_one" onclick="importPanel();" value="导入Panel文件"/>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<s:submit value="保 存" cssClass="button_one"/>
          	&nbsp;&nbsp;&nbsp;
          	<input type="button" class="button_one" onclick="backFtn();" value="返 回"/>
		  </div>
	      <table class="table_one">
		      <thead>
		        <tr>
		          <th>序号</th>
		          <th>基因座名称</th>
		          <th>操作</th>
		        </tr>
		      </thead>
		      <tbody id="tbody">
		      <%--
		        <s:iterator value="allLocusInfoList" status="locus">
		        	<tr>
			        	<td>
			        		<s:property value="genoName"/>
			        		<input type="hidden" name="genoNo" value="<s:property value="genoNo"/>"/>
			        	</td>
			        	<td><s:property value="#locus.index+1"/></td>
			        	<td>
			        	<%--
			        		<a href="javascript:void(0);" onclick="upLineFtn(this);">上移</a>
			        		&nbsp;
			        		<a href="javascript:void(0);" onclick="downLineFtn(this);">下移</a>
			        		&nbsp;

			        		<a href="javascript:void(0);" onclick="removeLineFtn(this);">删除</a>
			        	</td>
			        </tr>
			     </s:iterator>
			      --%>
		      </tbody>
	    </table>
	    <div class="height_one"></div>
	    </fieldset>

	</s:form>

	<div id="addLocusDiv" style="display:none;overflow:hidden;">
	  <div class="main" style="overflow:hidden;">
	    <input type="hidden" id="allLocusCount" value="<s:property value="allLocusCount"/>" />
	    <br/>
		<table class="table_two floatL marginL1">
	        <tr>
	          <td>选择基因座：</td>
	          <td>
	          	<s:select cssClass="select_one" id="newLocusNo" list="allLocusInfoList" listKey="genoNo" listValue="genoName"/>
	          </td>
	        </tr>
	      </table>
      </div>
    </div>

    <div id="importPanelDiv" style="display:none;overflow:hidden;">
	  <div class="main" style="overflow:hidden;">
	    <br/>
		<table class="table_two floatL marginL1">
	        <tr>
	          <td>选择Panel文件：</td>
	          <td>
	          	<input type="file" name="panelFile" id="panelFile" class="file_one width4"/>
	          </td>
	        </tr>
	      </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>