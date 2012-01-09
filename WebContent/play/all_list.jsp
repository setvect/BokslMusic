<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	$(function () {
		var searchValue = $u.COOKIE.getCookie("searchValue");
		$("#search_field").get(0).value = searchValue ;
		
		AllListControl.init();
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
		$("#searchBtn").bind("click", AllListControl.init);
		$("#openPlayListBtn").bind("click", PlayListControl.openPlayList);
		$("#clearPlayListBtn").bind("click", PlayListControl.clearPlayList);
		$("#deduplicationBtn").bind("click", PlayListControl.deduplication);
		$("#shuffleBtn").bind("click", PlayListControl.shuffle);
	});
</script>

<div class="toolbar ui-widget-header ui-corner-all">
	<input type="text" name="search" id="search_field" value="" class="ui-widget-content ui-corner-all" />
	<button id="searchBtn">검색</button>
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
