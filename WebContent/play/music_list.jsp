<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript" src="tree/jquery.treeview.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/music.js'></script>
<link rel="stylesheet" type="text/css" href="tree/jquery.treeview.css"/>
<script type="text/javascript">
	
	var musicList = new Object();
	
	musicList.playListBg = function(){
		$(".playList li:odd").addClass("line_odd");
		$(".playList li:even").addClass("line_even");
	};
	
	$(function () {
		$("#musicList").treeview({
			persist: "cookie",
			cookieId: "treeview-black",			
			toggle: function(i,object) {
				music.getMusicArticle(object.title, function(data){
					$(object).html("");
					for(var i=0;i<data.length;i++){
						var m = data[i];
						$(object).append("<li><span class='file' id='"+m.musicId+"'>"+m.name+"</span></li>");
					}
					$(".file").bind("click", function(obj){
						alert(obj.target.id);	
					});
					
				});
			}			
		});
		
		$( "button").button();
		
		musicList.playListBg();
	});
	
	
</script>
<style>
	.panel{
		height: 400px; width:360px; overflow: auto; float: left; 
	}
	.ui-button .ui-button-text{
		display: inline;
		line-height: 1.1;
	}
	.line_odd{
		background-color: #ffe;;		
	}
	.line_even{
		background-color: #fef;		
	}
	.playList{
		list-style: none outside none;
		margin: 0;
    padding: 0;
    padding-top: 4px;
	}
	.playList li{
		clear: both;
		height: 22px;
	}
	.playList div{
		padding-top:3px;
		float: left;
	}
	.playList .title{
		width: 250px;
	}
	.playList .time{
		width: 100px;
	}	
	.playList .function{
	}	
	
	#toolbar {
		padding: 5px;
		overflow: hidden;
		clear: both;
	}
</style>
<div style="overflow: hidden;">
	<div class="panel">
		<ul id="musicList" class="filetree">
<%
	List<String> folder = (List<String>)request.getAttribute(MusicListController.AttributeKey.FOLDER.name());
	for(String f : folder){
%>
			<li class="closed">
				<span class="folder close" ><%=f%><button style="float: right" value="<%=f%>">=></button></span>
				<ul title="<%=f%>"></ul>
			</li>
<%
	}
%>	
		</ul>
	</div>
	<div class="panel" style="padding-left: 20px; width: auto;">
		<div id="toolbar" class="ui-widget-header ui-corner-all">
			<button>중복제거</button>
			<button>전체저거</button>
			<button>뒤쭉박쭉</button>
		</div>	
		<ul class="playList">
			<li>
				<div class="title">노래를.mp3</div>
				<div class="time">3:40</div>
				<div class="function"><button>제거</button> </div>
			</li>
		</ul>
	</div>
</div>	