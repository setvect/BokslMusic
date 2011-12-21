<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript" src="tree/jquery.treeview.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<link rel="stylesheet" type="text/css" href="tree/jquery.treeview.css"/>
<script type="text/javascript">
	$(function () {
		$("#musicList").treeview({
			persist: "cookie",
			cookieId: "treeview-black",			
			toggle: function(i,object) {
				$(object).html("<li><span class='folder'>Subfolder 2.1</span></li><li><span class='file'>File 2.2</span></li>");
			}			
		});
	});
</script>
<ul id="musicList" class="filetree">
<%
	List<String> folder = (List<String>)request.getAttribute(MusicListController.AttributeKey.FOLDER.name());
	for(String f : folder){
%>
	<li class="closed">
		<span class="folder close" ><%=f%></span>
		<ul title="<%=f%>"></ul>
	</li>
<%
	}
%>	
</ul>




			