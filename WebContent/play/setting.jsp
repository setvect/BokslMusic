<%@page import="com.setvect.bokslmusic.web.play.MusicListController"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<script type="text/javascript">
	function loadSyncList(){
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
	
	$(function () {
		loadSyncList();
		$("#syncBtn").bind("click",function(){
			musicDwr.syncAll(function(result){
				if(result){
					alert("동기화 수행 종료");
				}
				else{
					alert("동기화 수행중...");
				}
			});
		});

		$("#syncRegBtn").bind("click", function(){
			var textForm = $("#directoryInput").get(0);
			if($u.FORM.isEmptyRtnMsg(textForm, "경로를 입력하세요")){
				return;
			}
			
			musicDwr.addSyncDirectory(textForm.value, function(result){
				if(result){
					loadSyncList();
				}	
				else{
					alert("동기화 디렉토리가 존재 하지 않습니다.");
				}
			});
		});
	});
</script>
<table class="playListTable" >
	<thead>
		<tr>
			<th>디렉토리명</th>
			<th>마지막 동기화</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody id="syncListTable">
	</tbody>
</table>
<div style="padding-top: 10px;">
	<button id="syncBtn">동기화하기</button>
	<span style="padding-left: 20px;">
		<label for="directoryInput">디렉토리:</label>
		<input type="text" value="" id="directoryInput" width="300px;">
		<button id="syncRegBtn">동기화 디렉토리 추가</button>
	</span>
</div>