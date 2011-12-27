<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	var playerObj = new Object();
	// 현재 재생중인 음악 전체 길이(초)
	playerObj.playLength = 0;
	
	// 볼륨 슬라이더 조정 중 
	playerObj.volumeOver = false;
	// 재생 슬라이더 조정 중 
	playerObj.seekOver = false;
	
	// 폴링 객체 
	playerObj.polling;
	
	// 현재 재생 중인 음악 하일라이트
	playerObj.tableHighlight = function(idx){
		$("#playListTable2 tr").css("background-color", "");
		if(idx >=0){
			$("#playListTable2 tr").eq(idx).css("background-color", "#ffeedd");
		}
	};
	
	// 현재 플레이어 설정되로 화면 
	playerObj.initViewPage = function(){
		musicDwr.getPlayerStat(function(statInfo){
			playerObj.tableHighlight(statInfo.playIndex);
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
			if(!playerObj.volumeOver){
				$( "#volumeSlider" ).slider("value", statInfo.volume);				
			}

			if(!playerObj.seekOver){
				if(statInfo.playArticle != null){
					playerObj.playLength = statInfo.playArticle.runningTime;
				}
				$( "#seekSlider" ).slider("value", statInfo.progressRate * 1000);				
			}
		});
	};

	
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
				playerObj.tableHighlight(idx);
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
					playerObj.tableHighlight(idx);
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
				playerObj.tableHighlight(idx);
			});
		});

		$( "#shuffle" ).button().click(function(){
			MusicControl.shuffle();
		});
		$( "#repeat" ).buttonset();
		
		
		$( "#repeat0" ).click(function(){
			musicDwr.repeat(false);
		});
		$( "#repeatall" ).click(function(){
			musicDwr.repeat(true);
		});
		
		// ====== 일반설정 		
		MusicControl.playListTableId = "playListTable2";
		MusicControl.playListPrintAfter = playerObj.initViewPage;
		MusicControl.playListPrint();
		
		// ====== 스라이더(볼륨, 진행)
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
				playerObj.volumeOver = true;
			},
			stop: function(event, ui) { 
				musicDwr.setVolume(ui.value, function(){
					playerObj.volumeOver = false;
					playerObj.initViewPage();
				});
			}
		});

		$( "#seekSlider" ).slider({
			range: "min",
			value: 0,
			min: 0,
			max: 1000,
			step: 1,
			slide: function( event, ui ) {
				var sec = parseInt(playerObj.playLength * (ui.value / 1000.0));
				$( "#time" ).val( $u.DATE.toMinSec(sec) );
			},
			change: function(event, ui) { 
				var sec = parseInt(playerObj.playLength * (ui.value / 1000.0));
				$( "#time" ).val( $u.DATE.toMinSec(sec) );
			},
			start: function(event, ui) {
				playerObj.seekOver = true;
			},
			stop: function(event, ui) { 
				musicDwr.setProgressRate(ui.value / 1000.0, function(){
					playerObj.seekOver  = false;
					playerObj.initViewPage();
				});
			}			
		});
		
		// 1초마다 설정 정보 갱신
		if(playerObj.polling == null){
			playerObj.polling = setInterval( playerObj.initViewPage, 1000 );
		}
	});
</script>
<div>
	<div class="toolbar ui-widget-header ui-corner-all">
		<button id="rewind">rewind</button>
		<button id="play">play</button>
		<button id="stop">stop</button>
		<button id="forward">fast forward</button>
		<input type="checkbox" id="shuffle" /><label for="shuffle">Shuffle</label>
		<span id="repeat">
			<input type="radio" id="repeat0" name="repeat" checked="checked" /><label for="repeat0">No Repeat</label>
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
	
</div>
<table id="playListTable2" class="playListTable">
</table>
