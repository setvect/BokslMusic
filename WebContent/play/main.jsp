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
<script type="text/javascript">
	var BASE_URL = "<%= CommonUtil.getContextUrl(request)%>";	
</script>
<base href="<%= CommonUtil.getContextUrl(request)%>/"/>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.16.custom.css"/>
<link rel="stylesheet" type="text/css" href="css/play.css"/>
<link rel="stylesheet" type="text/css" href="tree/jquery.treeview.css"/>

<jsp:include page="/play/common/script.inc.jsp"/>
<script type="text/javascript" src="tree/jquery.treeview.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/musicDwr.js'></script>
<script type="text/javascript" src="play/music_control.js"></script>

<style>
	.file:hover{
		color: red;; 
	}
	.toolbar {
		padding: 5px;
		overflow: hidden;
		clear: both;
		text-align: center;
	}	
	.playListRemove .ui-button-text{
		display: inline;
		line-height: 1.1;
	}	

	
</style>

<script type="text/javascript">
	$(function() {
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " +
						"If this wouldn't be a demo." );
				}
			}
		});
	});
</script>
</head>
<body>
<div id="wrap">
	<div id="tabs">
		<ul>
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.PLAY_LIST_FORM%>">Play List</a></li>
			<li><a href="play/music_list.do?mode=<%=MusicListController.Mode.ALL_LIST_FORM%>">All List</a></li>			
			<li><a href="ajax/content2.html">Setting</a></li>
		</ul>
	</div>
</div>
</body>
</html>