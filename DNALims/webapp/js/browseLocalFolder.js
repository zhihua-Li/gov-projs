
var openDialogContent = $("<div id='browerFolderDiv' style='width:360px;height:200px;text-align:center;'><br/><input type='text' class='text_one' style='width:240px;' id='savePath' readonly='readonly' value=''/>&nbsp;&nbsp;<input type='button' class='button_one' onclick='browseLocalFolder();' value='浏览...' /></div>");

function openBrowserFolderDialog(){
	openDialogContent.dialog({
		autoOpen:true,
		height:220,
		width:400,
		modal:true,
		async:false,
		title:"选择保存目录",
		buttons:{
			"确定":function(){
				var savePathTxt = $("#savePath", $(this)).val();
				if(savePathTxt != ''){
					$(this).dialog("close");
					downloadFiles(savePathTxt);
				}else{
					alert("请先选择保存目录！");
				}
			},
			"取消":function(){
				$(this).dialog("close");
			}
		}
	});
}

function browseLocalFolder(){
	try {
		var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
		var Shell = new ActiveXObject("Shell.Application");
		var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
		//var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
		if (Folder != null) {
			Folder = Folder.items(); // 返回 FolderItems 对象
			Folder = Folder.item(); // 返回 Folderitem 对象
			Folder = Folder.Path; // 返回路径

			if(Folder.charAt(Folder.length - 1) != "") {
				Folder = Folder + "";
			}

			$("#savePath", "#browerFolderDiv").val(Folder);
			return Folder;
		}

	} catch(e) {
		alert(e.message);
	}
}
