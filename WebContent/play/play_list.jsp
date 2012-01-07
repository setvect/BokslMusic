<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	// 재생창 토글
	function playlistToggle(){
		var display = $("#playListTableLayer").css("display");
		var value = display == "none" ? "block" : "none";
		$("#playListTableLayer").css("display", value);
		$u.COOKIE.setCookieInstacne("playListTableLayer", value, "/");
	}
	
	// 가사창 토글
	function lyricsToggle(){
		var display = $("#lyricsLayer").css("display");
		var value = display == "none" ? "block" : "none";
		$("#lyricsLayer").css("display", value);
		$u.COOKIE.setCookieInstacne("lyricsLayer", value, "/");
	}

	$(function () {
		// ====== 컨트롤 버튼
		$( "#rewind" ).button({
			text: false,
			icons: {
				primary: "ui-icon-seek-prev"
			}
		})
		.click(function() {
			musicDwr.previous(function(idx){
				PlayerControl.tableHighlight(idx);
			});
		});
		
		$( "#play" ).button({
			text: false,
			icons: {
				primary: "ui-icon-play"
			}
		})
		.click(function() {
			var options;
			if ( $( this ).text() === "play" ) {
				options = {
					label: "pause",
					icons: {
						primary: "ui-icon-pause"
					}
				};
				musicDwr.play(function(idx){
					PlayerControl.tableHighlight(idx);
				});
			}
			else {
				options = {
					label: "play",
					icons: {
						primary: "ui-icon-play"
					}
				};
				musicDwr.pause(function(){
					// nothing
				});
			}
			
			$( this ).button( "option", options );
		});
		
		$( "#stop" ).button({
			text: false,
			icons: {
				primary: "ui-icon-stop"
			}
		})
		.click(function() {
			$( "#play" ).button( "option", {
				label: "play",
				icons: {
					primary: "ui-icon-play"
				}
			});
			musicDwr.stop(function(){
				// nothing
			});
		});
		
		$( "#forward" ).button({
			text: false,
			icons: {
				primary: "ui-icon-seek-next"
			}
		})
		.click(function() {
			musicDwr.next(function(idx){
				PlayerControl.tableHighlight(idx);
			});
		});

		$( "#repeat" ).buttonset();
		
		
		$( "#repeat0" ).click(function(){
			musicDwr.repeat(false);
		});
		$( "#repeatall" ).click(function(){
			musicDwr.repeat(true);
		});
		
		// ====== 일반설정 		
		PlayListControl.playListTableId = "playListTable2";
		PlayListControl.playListPrintAfter = PlayerControl.initViewPage;
		PlayListControl.playListPrint();
		
		// ====== 스라이더(볼륨, 진행)
		// 볼륨 조절 
		$( "#volumeSlider" ).slider({
			range: "min",
			value: 0,
			min: 0,
			max: 100,
			slide: function( event, ui ) {
				$( "#volume" ).val( ui.value );
			},
			change: function(event, ui) { 
				$( "#volume" ).val( ui.value );
			},
			start: function(event, ui) {
				PlayerControl.volumeOver = true;
			},
			stop: function(event, ui) { 
				musicDwr.setVolume(ui.value, function(){
					PlayerControl.volumeOver = false;
					PlayerControl.initViewPage();
				});
			}
		});

		// 진행 이동
		$( "#seekSlider" ).slider({
			range: "min",
			value: 0,
			min: 0,
			max: 1000,
			step: 1,
			slide: function( event, ui ) {
				var sec = parseInt(PlayerControl.playLength * (ui.value / 1000.0));
				$( "#time" ).val( $u.DATE.toMinSec(sec) );
			},
			change: function(event, ui) { 
				var sec = parseInt(PlayerControl.playLength * (ui.value / 1000.0));
				$( "#time" ).val( $u.DATE.toMinSec(sec) );
			},
			start: function(event, ui) {
				PlayerControl.seekOver = true;
			},
			stop: function(event, ui) { 
				musicDwr.setProgressRate(ui.value / 1000.0, function(){
					PlayerControl.seekOver  = false;
					PlayerControl.initViewPage();
				});
			}			
		});

		// 하단 버튼
		// 가사 보기
		$( "#lyricsView" ).button().click(function(){
			musicDwr.getLyrics(function(lyrics){
				if(lyrics != null){
					$( "#lyrics-dialog").dialog( "open" );
					$( "#lyrics-dialog").html(lyrics.replace(/\n/g,"<br/>"));
				}
			});
		});

		// 재생 항목 적기
		$( "#shuffle" ).button().click(function(){
			PlayListControl.shuffle();
		});

		// 앨범 저장
		$( "#albumSave" ).button().click(function(){
			$( "#album-dialog").dialog( "open" );
		});
		
		// 앨범 목록
		$( "#albumList" ).button().click(function(){
			var v = $("#albumChoiceTable").css("display");
			if(v=="none"){
				AlbumControl.loadAlbumList(function(albumList){
					var tableObj = $("#albumChoiceList").get(0);
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
						cell.innerHTML = "<button class='albumChoiceBtn' value='" + article.albumSeq + "'>선택</button>";
					}
					
					// 앨범 항목 선택
					$(".albumChoiceBtn").button();
					$(".albumChoiceBtn").bind("click", function(event){
						musicDwr.useAlbum(event.delegateTarget.value, function(){
							PlayListControl.playListPrint();
						});
					});
					$("#albumChoiceTable").css("display", "block");
				});
			}
			else{
				$("#albumChoiceTable").css("display", "none");
			}
		});
		
		// 앨범 저장 다이얼로그
		$( "#album-dialog" ).dialog({
			autoOpen: false,
			height: 150,
			width: 300,
			modal: true,
			buttons: {
				"등록": function() {
					var name = $("#album_name").get(0);
					if(name.value == ""){
						alert("이름 입력!");
						name.focus();
						name.select();
						return;
					}
					musicDwr.saveAlbum(name.value,function(){
						$( "#album-dialog" ).dialog( "close" );
					});
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
		$( "#lyrics-dialog" ).dialog({
			autoOpen: false,
			modal: true,
			resizable: false,
			height:250
		});
		
		// 재생창 토글
		var display = $u.COOKIE.getCookie("playListTableLayer");
		if(display != null){
			$("#playListTableLayer").css("display", display);
		}
		
		// 가사창 토글
		display = $u.COOKIE.getCookie("lyricsLayer");
		if(display != null){
			$("#lyricsLayer").css("display", display);
		}
		
		// 1초마다 설정 정보 갱신
		if(PlayerControl.polling == null){
			PlayerControl.polling = setInterval( PlayerControl.initViewPage, 1000 );
		}
	});
</script>
<div>
	<div class="toolbar ui-widget-header ui-corner-all">
		<button id="rewind">rewind</button>
		<button id="play">play</button>
		<button id="stop">stop</button>
		<button id="forward">fast forward</button>
		<span id="repeat">
			<input type="radio" id="repeat0" name="repeat" checked="checked" /><label for="repeat0">No Rep.</label>
			<input type="radio" id="repeatall" name="repeat"/><label for="repeatall">All</label>
		</span>
	</div>
	
	<ul style="list-style: none outside; padding-left: 0">
		<li>
			<span>
				<label for="amount">Volume:</label>
				<input type="text" id="volume" style="border:0; color:#f6931f; font-weight:bold;" />
			</span>		
			<div id="volumeSlider"></div>
		</li>
		<li>
			<span>
				<label for="amount">Time:</label>
				<input type="text" id="time" style="border:0; color:#f6931f; font-weight:bold;" />
			</span>				
			<div id="seekSlider"></div>
		</li>
	</ul>
	<div class="toolbar ui-widget-header ui-corner-all" style="padding: 2px; cursor: pointer;" onclick="playlistToggle()">재생 목록</div>
	<div id="playListTableLayer">
		<table id="playListTable2" class="playListTable">
			<tbody></tbody>
		</table>	
	</div>
	<div class="toolbar ui-widget-header ui-corner-all" style="padding: 2px; cursor: pointer;" onclick="lyricsToggle()">가사창</div>
	<div id="lyricsLayer" class="ui-widget-content ui-corner-all">
	</div>	
	
	<div class="toolbar ui-widget-header ui-corner-all" style="margin-top: 10px;">
		<button id="lyricsView">가사보기</button>
		<button id="shuffle">뒤죽박죽</button>
		<button id="albumSave">앨범저장</button>
		<button id="albumList">앨범목록</button>
	</div>
	<div id="albumChoiceTable" style="display: none;">
		<table class="playListTable">
			<thead style="background-color: #fee">
				<tr>
					<th>번호</th>
					<th>앨범이름</th>
					<th>노래곡수</th>
					<th>재생시간</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody id="albumChoiceList">
			</tbody>
		</table>
	</div>
</div>

<div id="album-dialog" title="앨범 등록">
	<fieldset>
		<label for="name">이름</label>
		<input type="text" name="name" id="album_name" class="ui-widget-content ui-corner-all" />
	</fieldset>
</div>

<div id="lyrics-dialog" title="노래가사">
</div>