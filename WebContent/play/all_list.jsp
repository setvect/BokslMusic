<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<link rel="stylesheet" type="text/css" href="tree/default/style.css"/>
<script type="text/javascript" src="tree/jquery.jstree.js"/>
<script type="text/javascript">
	$(function () {
		$("#demo1").jstree({ 
			"plugins" : [ "themes", "html_data" ]
		});
	});
</script>
<div id="demo1" class="demo">
	<ul>
		<li id="phtml_1">
			<a href="#">Root node 1</a>
			<ul>
				<li id="phtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="phtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="phtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>
