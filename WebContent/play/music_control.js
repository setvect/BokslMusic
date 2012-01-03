var MusicControl = new Object();

// 재생 목록 표시 테이블 아이디 값
MusicControl.playListTableId = "";

// 재상 목록 뿌여 지고 수행할 작업
MusicControl.playListPrintAfter = null;

// 재생목록 표시
MusicControl.playListPrint = function() {
	musicDwr.getPlayArticle(function(rtnValue) {
		var tableObj = $("#" + MusicControl.playListTableId).get(0);
		var n = tableObj.rows.length;
		for ( var i = n - 1; i >= 0; i--) {
			tableObj.deleteRow(i);
		}

		for ( var i = 0; i < rtnValue.length; i++) {
			var article = rtnValue[i];
			var row = tableObj.insertRow(i);
			var cell = row.insertCell(0);
			cell.innerHTML = i + 1;

			cell = row.insertCell(1);
			cell.innerHTML = "<span class='musicTitle'>" + article.name + 
					"<input type='hidden' value='" + article.musicId + "'></span>";

			cell = row.insertCell(2);
			cell.innerHTML = $u.DATE.toMinSec(article.runningTime);

			cell = row.insertCell(3);
			cell.innerHTML = "<nobr><button class='playListRemove' value='" + i
					+ "'>제거</button></nobr>";
		}
		$("button").button();

		$(".playListRemove").bind("click", function(e) {
			musicDwr.removePlayList(e.delegateTarget.value, function() {
				// 삭제된 내용 반영하기 위해, 다시 목록 표시
				MusicControl.playListPrint();
			});
		});

		if (MusicControl.playListPrintAfter != null) {
			MusicControl.playListPrintAfter();
		}
	});
};

// 서브 폴더 확장
MusicControl.expandFolder = function(i, object) {
	musicDwr.getPlayListFolder(object.title, function(data) {
		$(object).html("");
		for ( var i = 0; i < data.length; i++) {
			var m = data[i];
			$(object).append(
					"<li><span class='file' id='" + m.musicId + "'>" + m.name
							+ "</span></li>");
		}
		$(".file").bind("click", function(obj) {
			musicDwr.addPlayList(obj.delegateTarget.id);
		});
	});
};

// 하위 폴더 음악 추가
MusicControl.addFolder = function(event) {
	musicDwr.addPlayListFolder(event.delegateTarget.value, function(rtnValue) {
		alert("재생목록에 " + rtnValue + "개 추가");
	});
};

// 재생 목록창 표시
MusicControl.openPlayList = function() {
	$("#playlist-dialog").dialog("open");
	MusicControl.playListPrint();
};

// 전체 삭제
MusicControl.clearPlayList = function(event) {
	musicDwr.clearPlayList(function() {
		MusicControl.playListPrint();
	});
};

// 중복 제거
MusicControl.deduplication = function(event) {
	musicDwr.deduplication(function() {
		MusicControl.playListPrint();
	});
};

// 섞이
MusicControl.shuffle = function(event) {
	musicDwr.shufflePlayList(function() {
		MusicControl.playListPrint();
	});
};

// 앨범 목록 가져옴
MusicControl.loadAlbumList = function(callback) {
	musicDwr.getAlbumList(callback);
};
