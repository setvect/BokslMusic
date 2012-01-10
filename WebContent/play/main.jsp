<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="com.setvect.bokslmusic.util.CommonUtil"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<!-- <%=request.getRequestURI()%> -->
<title>Boksl Music</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<script type="text/javascript">
	var BASE_URL = "<%= CommonUtil.getContextUrl(request)%>";	
</script>
<base href="<%= CommonUtil.getContextUrl(request)%>/"/>
<link rel="stylesheet" type="text/css" href="css/smoothness/jquery-ui-1.8.16.custom.css"/>
<link rel="stylesheet" type="text/css" href="css/play.css"/>
<link rel="stylesheet" type="text/css" href="tree/jquery.treeview.css"/>

<jsp:include page="/play/common/script.inc.jsp"/>
<script type="text/javascript" src="tree/jquery.treeview.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/musicDwr.js'></script>

<style type="text/css">
	.file:hover{
		color: red; 
	}
	.toolbar {
		padding: 5px;
		overflow: hidden;
		clear: both;
		text-align: center;
	}	
	.toolbar .ui-button-text{
		line-height:1;
	}	
	.playListRemove .ui-button-text{
		display: inline;
		line-height: 1.1;
	}	
	
	#lyricsLayer{
		height: 80px; 
		overflow: auto; 
		background-color: #fea;
		padding-left: 10px;
		line-height: 150%;
	}
	#lyricsLayer strong{
		background-color: #ff9;
	}
</style>

<script type="text/javascript">
	// =================================  플레이어 컨트로
	var PlayerControl = new Object();
	// 폴링 객체 
	PlayerControl.polling;
	
	// 현재 재생중인 음악 전체 길이(초)
	PlayerControl.playLength = 0;
	
	// 볼륨 슬라이더 조정 중 
	PlayerControl.volumeOver = false;
	// 재생 슬라이더 조정 중 
	PlayerControl.seekOver = false;
	
	// 현재 재생 중인 음악 하일라이트
	PlayerControl.tableHighlight = function(idx){
		$("#playListTable2 tr").css("background-color", "");
		if(idx >=0){
			$("#playListTable2 tr").eq(idx).css("background-color", "#ffeedd");
		}
	};
	
	// 현재 플레이어 설정되로 화면 
	PlayerControl.initViewPage = function(){
		musicDwr.getPlayerStat(function(statInfo){
			PlayerControl.tableHighlight(statInfo.playIndex);
			if(statInfo.repeat){
				$("#repeatall").attr("checked", true);
			}
			else{
				$("#repeat0").attr("checked", true);
			}
			$( "#repeat" ).buttonset();
			var options;
			if(statInfo.playStatus == "PLAY"){			
				$("#play").text("pause");
				options = {
					label: "pause",
					icons: {
						primary: "ui-icon-pause"
					}
				};
			}
			else{
				options = {
					label: "play",
					icons: {
						primary: "ui-icon-play"
					}
				};
			}
			
			$( "#play" ).button( "option", options );
			if(!PlayerControl.volumeOver){
				$( "#volumeSlider" ).slider("value", statInfo.volume);				
			}

			if(!PlayerControl.seekOver){
				if(statInfo.playArticle != null){
					PlayerControl.playLength = statInfo.playArticle.runningTime;
				}
				$( "#seekSlider" ).slider("value", statInfo.progressRate * 1000);				
			}
			if(statInfo.playArticle != null){
				$("#lyricsLayer").html("");
				$("#lyricsLayer").append(statInfo.playArticle.lyricsHighlight.replace(/\n/g,"<br/>"));

				// 스코롤 자동이동
				var div = $('#lyricsLayer').get(0);
				var htline = $('#lyricsLayer strong').get(0);
				if(htline!=null){
					div.scrollTop = htline.offsetTop - div.offsetTop - 30;
				}
				else{
					div.scrollTop = 0;
				}
			}
		});
		
		$(".musicTitle").css("cursor","pointer");
		$(".musicTitle").unbind("click");
		$(".musicTitle").bind("click", function(event){
			var obj = event.delegateTarget;
			var musicId = $("input", obj).get(0).value;
			musicDwr.playId(musicId);
		});
	};	
	
	$(function() {
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html("error" );
				}
			}
		});
	});
	
	
	// ================================= 전체목록 관리 
	var AllListControl = new Object();
	
	AllListControl.init = function(){
		var searchValue =  $("#search_field").get(0).value;
		$u.COOKIE.setCookieInstacne("searchValue", escape(searchValue), "/");
		musicDwr.getPath(searchValue, function(folder){
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
				toggle: AllListControl.expandFolder
			});
			$("button").button();
			$("#musicList .ui-button-text").css("display", "inline").css("line-height", "1.1");
			$("#musicList button").bind("click", PlayListControl.addFolder);
		});
	};

	
	// 서브 폴더 확장
	AllListControl.expandFolder = function(i, object) {
		var searchValue =  $("#search_field").get(0).value;
		musicDwr.getPlayListFolder(object.title, searchValue, function(data) {
			$(object).html("");
			for ( var i = 0; i < data.length; i++) {
				var m = data[i];
				$(object).append(
						"<li><span class='file' id='" + m.musicId + "'>" + m.name
								+ "</span></li>");
			}
			$(".file").bind("click", function(obj) {
				musicDwr.addPlayList(obj.delegateTarget.id);
			});
		});
	};
	
	
	// ================================= 재생 목록 관리
	var PlayListControl = new Object();
	// 재생 목록 표시 테이블 아이디 값
	PlayListControl.playListTableId = "";

	// 재생 목록 뿌여 지고 수행할 작업
	PlayListControl.playListPrintAfter = null;

	// 재생목록 표시
	PlayListControl.playListPrint = function() {
		musicDwr.getPlayArticle(function(rtnValue) {
			var tableObj = $("#" + PlayListControl.playListTableId).get(0);
			var n = tableObj.rows.length;
			for ( var i = n - 1; i >= 0; i--) {
				tableObj.deleteRow(i);
			}

			for ( var i = 0; i < rtnValue.length; i++) {
				var article = rtnValue[i];
				var row = tableObj.insertRow(i);
				var cell = row.insertCell(0);
				cell.innerHTML = i + 1;

				cell = row.insertCell(1);
				cell.innerHTML = "<span class='musicTitle'>" + article.name + 
						"<input type='hidden' value='" + article.musicId + "'></span>";

				cell = row.insertCell(2);
				cell.innerHTML = $u.DATE.toMinSec(article.runningTime);

				cell = row.insertCell(3);
				cell.innerHTML = "<nobr><button class='playListRemove' value='" + i
						+ "'>제거</button></nobr>";
			}
			$("button").button();

			$(".playListRemove").bind("click", function(e) {
				musicDwr.removePlayList(e.delegateTarget.value, function() {
					// 삭제된 내용 반영하기 위해, 다시 목록 표시
					PlayListControl.playListPrint();
				});
			});

			if (PlayListControl.playListPrintAfter != null) {
				PlayListControl.playListPrintAfter();
			}
		});
	};

	// 하위 폴더 음악 추가
	PlayListControl.addFolder = function(event) {
		musicDwr.addPlayListFolder(event.delegateTarget.value, function(rtnValue) {
			alert("재생목록에 " + rtnValue + "개 추가");
		});
	};

	// 재생 목록창 표시
	PlayListControl.openPlayList = function() {
		$("#playlist-dialog").dialog("open");
		PlayListControl.playListPrint();
	};

	// 전체 삭제
	PlayListControl.clearPlayList = function(event) {
		musicDwr.clearPlayList(function() {
			PlayListControl.playListPrint();
		});
	};

	// 중복 제거
	PlayListControl.deduplication = function(event) {
		musicDwr.deduplication(function() {
			PlayListControl.playListPrint();
		});
	};

	// 섞이
	PlayListControl.shuffle = function(event) {
		musicDwr.shufflePlayList(function() {
			PlayListControl.playListPrint();
		});
	};
	
	// ================================= 앨범관리 관리
	var AlbumControl = new Object(); 
	// 앨범 목록 가져옴
	AlbumControl.loadAlbumList = function(callback) {
		musicDwr.getAlbumList(callback);
	};		
	
	AlbumControl.listLoad = function(){
		AlbumControl.loadAlbumList(function(albumList){
			var tableObj = $("#albumTableList").get(0);
			var n = tableObj.rows.length;
			for ( var i = n - 1; i >= 0; i--) {
				tableObj.deleteRow(i);
			}

			for ( var i = 0; i < albumList.length; i++) {
				var article = albumList[i];
				var row = tableObj.insertRow(i);
				var cell = row.insertCell(0);
				cell.innerHTML = i + 1;

				cell = row.insertCell(1);
				cell.innerHTML = article.name;

				cell = row.insertCell(2);
				cell.innerHTML = article.musicArticleList.length;
				
				var time =0;
				for(var j =0; j < article.musicArticleList.length;j++){
					time += article.musicArticleList[i].runningTime;			
				}
				cell = row.insertCell(3);
				cell.innerHTML = $u.DATE.toMinSec(time);
				
				cell = row.insertCell(4);
				cell.innerHTML = "<button class='changeNameBtn' value='" + article.albumSeq + "'>변경</button>";
				
				cell = row.insertCell(5);
				cell.innerHTML = "<button class='removeBtn' value='" + article.albumSeq + "'>삭제</button>";
			}
			
			$(".changeNameBtn").button().click(function(event){
				musicDwr.getAlbum(event.delegateTarget.value, function(album){
					$("#album_update_name").get(0).value = album.name;;
					$("#album_update_id").get(0).value = album.albumSeq;;
					$("#album-update-dialog").dialog( "open" );
				});
			});
			$(".removeBtn").button().click(function(event){
				musicDwr.removeAlbum(event.delegateTarget.value, function(){
					AlbumControl.listLoad();					
				});
			});
		});
	};
	
	// ================================= 동기화 컨트롤 관리
	var SyncControl = new Object();
	
	SyncControl.loadSyncList = function(){
		musicDwr.getSyncDiretory(function(syncDiretory){
			var tableObj = $("#syncListTable").get(0);
			
			var n = tableObj.rows.length;
			for(var i=n-1; i>=0; i--){
				tableObj.deleteRow(i);
			}
			
			for(var i=0; i<syncDiretory.length; i++){
				var row = tableObj.insertRow(i);
				var cell = row.insertCell(0);
				cell.innerHTML= syncDiretory[i].basePath;
				cell = row.insertCell(1);
				if(syncDiretory[i].syncDate == null){
					cell.innerHTML= syncDiretory[i].syncDate="&nbsp;";
				}
				else{
					cell.innerHTML= syncDiretory[i].syncDate.format("yyyy-MM-dd HH:mm:ss");	
				}
				 
				cell = row.insertCell(2);
				cell.innerHTML= "<button class='deleteDirectoryBtn' value='"+ syncDiretory[i].basePath +"'>삭제</button>";
			}
			$("button").button();
			$(".deleteDirectoryBtn").bind("click", function(e){
				musicDwr.removeSyncDirectory(e.delegateTarget.value, function(){
					loadSyncList();
				});
			});
		});
	}
</script>
</head>
<body>
<div id="wrap">
	<div id="tabs">
		<ul>
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.PLAY_LIST_FORM%>">재생</a></li>
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.ALL_LIST_FORM%>">노래들</a></li>
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.ALBUM_FORM%>">앨범</a></li>			
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.SETTING_FORM%>">설정</a></li>
		</ul>
	</div>
</div>
</body>
</html>