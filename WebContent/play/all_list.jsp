<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	function init(){
		musicDwr.getPath("", function(folder){
			var tree = $("#musicList");
			tree.html("");
			for(var i=0;i<folder.length;i++){
				var d = folder[i];
				var t="<li class='closed'>";
				t+="<span class='folder close'>"+d+"<button style='float: right' value='"+d+"'>모두추가</button></span>";
				t+="<ul title='"+d+"'></ul>";
				t+="</li>";
				tree.append(t);
			}
			$("#musicList").treeview({
				persist: "cookie",
				cookieId: "treeview-black",			
				toggle: FolderControl.expandFolder
			});
			$("button").button();
			$("#musicList .ui-button-text").css("display", "inline").css("line-height", "1.1");
		});
	}

	$(function () {
		init();		
		$( "#playlist-dialog" ).dialog({
			modal: true,
			autoOpen: false,
			resizable: false,
			height:350,
			width:350,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
		PlayListControl.playListTableId = "playListTable1";
		
		$("#musicList button").bind("click", PlayListControl.addFolder);
		$("#openPlayListBtn").bind("click", PlayListControl.openPlayList);
		$("#clearPlayListBtn").bind("click", PlayListControl.clearPlayList);
		$("#deduplicationBtn").bind("click", PlayListControl.deduplication);
		$("#shuffleBtn").bind("click", PlayListControl.shuffle);
	});
</script>

<div class="toolbar ui-widget-header ui-corner-all">
	<button id="openPlayListBtn">재생목록</button>
</div>	
<div>
	<ul id="musicList" class="filetree">
	</ul>
</div>
<div id="playlist-dialog" title="재생 목록">
	<div class="toolbar ui-widget-header ui-corner-all">
		<button id="clearPlayListBtn">전체제거</button>
		<button id="deduplicationBtn">중복제거</button>
		<button id="shuffleBtn">뒤죽박죽</button>
	</div>
	<table id="playListTable1" class="playListTable">
		<tbody></tbody>
	</table>
</div>
