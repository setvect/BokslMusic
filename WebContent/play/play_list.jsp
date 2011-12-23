<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	
	// 현재 재생 중인 음악 하일라이트
	function tableHighlight(idx){
		$("#playListTable2 tr").css("background-color", "");
		if(idx >=0){
			$("#playListTable2 tr").eq(idx).css("background-color", "#ffeedd");
		}
	}

	$(function () {
		$( "#rewind" ).button({
			text: false,
			icons: {
				primary: "ui-icon-seek-prev"
			}
		})
		.click(function() {
			musicDwr.previous(function(idx){
				tableHighlight(idx);
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
					tableHighlight(idx);
				});
			} else {
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
				tableHighlight(idx);
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
		
		MusicControl.playListTableId = "playListTable2";
		MusicControl.playListPrint();
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
			<input type="radio" id="repeat0" name="repeat" value="no" checked="checked" /><label for="repeat0">No Repeat</label>
			<input type="radio" id="repeatall" name="repeat" value="all"/><label for="repeatall">All</label>
		</span>
	</div>
</div>
<table id="playListTable2" class="playListTable">
</table>
