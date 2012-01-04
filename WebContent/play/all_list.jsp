<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	$(function () {
		$("#musicList").treeview({
			persist: "cookie",
			cookieId: "treeview-black",			
			toggle: MusicControl.expandFolder
		});
		
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
		
		MusicControl.playListTableId = "playListTable1";
		
		$("button").button();
		$("#musicList button").bind("click", MusicControl.addFolder);
		$("#openPlayListBtn").bind("click", MusicControl.openPlayList);
		$("#clearPlayListBtn").bind("click", MusicControl.clearPlayList);
		$("#deduplicationBtn").bind("click", MusicControl.deduplication);
		$("#shuffleBtn").bind("click", MusicControl.shuffle);
		$("#musicList .ui-button-text").css("display", "inline").css("line-height", "1.1");
	});
</script>

<div class="toolbar ui-widget-header ui-corner-all">
	<button id="openPlayListBtn">재생목록</button>
</div>	
<div>
	<ul id="musicList" class="filetree">
<%
	List<String> folder = (List<String>)request.getAttribute(MusicListController.AttributeKey.FOLDER.name());
	for(String f : folder){
%>
		<li class="closed">
			<span class="folder close"><%=f%><button style="float: right" value="<%=f%>">모두추가</button></span>
			<ul title="<%=f%>"></ul>
		</li>
<%
	}
%>	
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
