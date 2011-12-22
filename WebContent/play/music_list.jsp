<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript" src="tree/jquery.treeview.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/musicDwr.js'></script>
<link rel="stylesheet" type="text/css" href="tree/jquery.treeview.css"/>
<script type="text/javascript">
	
	var musicList = new Object();
	
	// 재생목록 표시 
	musicList.playListPrint = function(){
		musicDwr.getPlayArticle(function(rtnValue){
			var tableObj = $( "#playlist-dialog table").get(0);
			var n = tableObj.rows.length
			for(var i=n-1; i>=0; i--){
				tableObj.deleteRow(i);
			}
			
			for(var i=0; i<rtnValue.length; i++){
				var article = rtnValue[i];
				
				var row = tableObj.insertRow(i);
				
				var cell = row.insertCell(0);
				cell.innerHTML= i + 1;
				
				cell = row.insertCell(1);
				cell.innerHTML= article.name;
				
				cell = row.insertCell(2);
				cell.innerHTML= parseInt(article.runningTime/60) +":" + $u.STR.paddingLeftChar(article.runningTime % 60 + "", 2, "0");
				
				cell = row.insertCell(3);
				cell.innerHTML= "<nobr><button class='playListRemove' value='"+ i +"'>제거</button></nobr>";
			}
			$("button").button();
			
			$(".playListRemove").bind("click",function(e){
				musicDwr.removePlayList(e.target.value, function(){
					// 삭제된 내용 반영하기 위해, 다시 목록 표시
					musicList.playListPrint();
				});
			});
		});
	};
	
	// 서브 폴더 확장
	musicList.expandFolder = function(i,object) {
		musicDwr.getPlayListFolder(object.title, function(data){
			$(object).html("");
			for(var i=0;i<data.length;i++){
				var m = data[i];
				$(object).append("<li><span class='file' id='"+m.musicId+"'>"+m.name+"</span></li>");
			}
			$(".file").bind("click", function(obj){
				musicDwr.addPlayList(obj.target.id);
			});
		});
	};			
	
	// 하위 폴더 음악 추가 
	musicList.addFolder =	function(event){
		musicDwr.addPlayListFolder(event.target.value, function(rtnValue){
			alert("재생목록에 " + rtnValue + "개 추가");
		});
	};
	
	
	// 재생 목록창 표시 
	musicList.openPlayList = function(){
		$( "#playlist-dialog" ).dialog( "open" );
		musicList.playListPrint();
	};
	
	// 전체 삭제 
	musicList.clearPlayList = function(event){
		musicDwr.clearPlayList(function(){
			musicList.playListPrint();
		});
	};
	
	// 중복 제거 
	musicList.deduplication = function(event){
		musicDwr.deduplication(function(){
			musicList.playListPrint();
		});
	};
	
	// 섞이
	musicList.shuffle = function(event){
		musicDwr.shufflePlayList(function(){
			musicList.playListPrint();
		});
	};
	
	$(function () {
		$("#musicList").treeview({
			persist: "cookie",
			cookieId: "treeview-black",			
			toggle: musicList.expandFolder
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
		
		$("button").button();
		$("#musicList button").bind("click", musicList.addFolder);
		$("#openPlayListBtn").bind("click", musicList.openPlayList);
		$("#clearPlayListBtn").bind("click", musicList.clearPlayList);
		$("#deduplicationBtn").bind("click", musicList.deduplication);
		$("#shuffleBtn").bind("click", musicList.shuffle);
		
	});
	
</script>
<style>
	.ui-button .ui-button-text{
		display: inline;
		line-height: 1.1;
	}
	.file:hover{
		color: red;; 
	}
	table{
		width:100%;
		padding:0; border-spacing:0px; border:0; border-collapse:collapse; 
	}
	td{
		border:2px solid #c7dafa;
	}

	.toolbar {
		width: 100px;
		padding: 5px;
		overflow: hidden;
		clear: both;
		text-align: center;
	}	
</style>
<div class="toolbar ui-widget-header ui-corner-all">
	<button id="openPlayListBtn">재상목록</button>
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
	<div class="toolbar ui-widget-header ui-corner-all" style="width: auto;">
		<button id="clearPlayListBtn">전체제거</button>
		<button id="deduplicationBtn">중복제거</button>
		<button id="shuffleBtn">뒤죽박죽</button>
	</div>	
	<table>
	</table>
</div>
