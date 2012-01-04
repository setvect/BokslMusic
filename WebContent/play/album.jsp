<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	function listLoad(){
		MusicControl.loadAlbumList(function(albumList){
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
					listLoad();					
				});
			});
		});
	}
	
	
	$(function () {
		listLoad();

		// 앨범이름 수정 다이얼로그
		$( "#album-update-dialog" ).dialog({
			autoOpen: false,
			height: 150,
			width: 300,
			modal: true,
			buttons: {
				"등록": function() {
					var name = $("#album_update_name").get(0);
					var id = $("#album_update_id").get(0);
					if(name.value == ""){
						alert("이름 입력!");
						name.focus();
						name.select();
						return;
					}
					musicDwr.updateAlbum(id.value, name.value, function(){
						$( "#album-update-dialog" ).dialog( "close" );
					});
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				listLoad();		
			}					
		});
	});
</script>
<div>
	<table >
		<thead style="background-color: #fee">
			<tr>
				<th>번호</th>
				<th>앨범이름</th>
				<th>노래곡수</th>
				<th>재생시간</th>
				<th>이름변경</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody id="albumTableList">
		</tbody>
	</table>
</div>

<div id="album-update-dialog" title="앨범 수정">
	<fieldset>
		<label for="name">이름</label>
		<input type="text" name="name" id="album_update_name" class="ui-widget-content ui-corner-all" />
		<input type="hidden" name="albumSeq" id="album_update_id" class="ui-widget-content ui-corner-all" />
	</fieldset>
</div>