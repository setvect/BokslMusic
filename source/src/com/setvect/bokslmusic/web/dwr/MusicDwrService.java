package com.setvect.bokslmusic.web.dwr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.setvect.bokslmusic.player.GlobalPlayerInfo;
import com.setvect.bokslmusic.player.PlayerStat;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.service.music.MusicSyncService;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;

/**
 * 프로젝트 목록 제공
 * 
 * @version $Id$
 */
@Controller
public class MusicDwrService {
	@Autowired
	private MusicService musicService;
	@Autowired
	private MusicSyncService syncService;

	/** 동기화 중 */
	private boolean syncing = false;

	/**
	 * 디렉토리 목록
	 * 
	 * @param searchWord
	 *            디렉토리 또는 파일명 검색어
	 * @return 디렉토리 목록
	 */
	public List<String> getPath(String searchWord) {
		List<String> allPath = musicService.getMusicArticlePath(searchWord);
		return allPath;
	}

	/**
	 * 음악 파일 목록을 조회 함
	 * 
	 * @param folder
	 *            폴더명
	 * @param searchWord
	 *            디렉토리 또는 파일명 검색어
	 * @return 음악 파일 목록
	 */
	public List<MusicArticle> getPlayListFolder(String folder, String searchWord) {
		List<MusicArticle> result = musicService.getPlayListFolder(folder, searchWord);
		return result;
	}

	/**
	 * 재생 항목 추가
	 * 
	 * @param musicId
	 *            음악 아이디
	 */
	public void addPlayList(String musicId) {
		MusicArticle article = musicService.getMusicArticle(musicId);
		GlobalPlayerInfo.addPlayArticle(article);
	}

	/**
	 * 폴더 경로에 있는 모든 음악 파일 추가
	 * 
	 * @param folder
	 *            폴더명
	 * @return 등록한 음악 갯수
	 */
	public int addPlayListFolder(String folder) {
		List<MusicArticle> items = musicService.getPlayListFolder(folder, null);
		GlobalPlayerInfo.addPlayArticle(items);
		return items.size();
	}

	/**
	 * 재생 목록 조회
	 * 
	 * @return 재생 목록
	 */
	public List<MusicArticle> getPlayArticle() {
		return GlobalPlayerInfo.getPlayArticle();
	}

	/**
	 * 재생 항목 삭제
	 * 
	 * @param idx
	 *            삭재 할 목록 인덱스
	 */
	public void removePlayList(int idx) {
		GlobalPlayerInfo.removePlayList(idx);
	}

	/**
	 * 재생 항목 전체 삭제
	 */
	public void clearPlayList() {
		GlobalPlayerInfo.clearPlayList();
	}

	/**
	 * 재생 목록 섞이
	 */
	public void shufflePlayList() {
		GlobalPlayerInfo.shuffle();
	}

	/**
	 * 중복 제거
	 */
	public void deduplication() {
		GlobalPlayerInfo.deduplication();
	}

	/**
	 * 현재 커서의 음악 재생, 또는 다시 시작
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public int play() {
		return GlobalPlayerInfo.play();
	}

	/**
	 * 현재 재생 목록에 해당 하는 음악 아이디가 없으면 작업 요청을 무시한다.
	 * 
	 * @param musicId
	 *            음악 아이디
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public int playId(String musicId) {
		return GlobalPlayerInfo.play(musicId);
	}

	/**
	 * 일시멈춤
	 */
	public void pause() {
		GlobalPlayerInfo.pause();
	}

	/**
	 * 멈춤
	 */
	public void stop() {
		GlobalPlayerInfo.stop();
	}

	/**
	 * 이전 곡 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public int previous() {
		return GlobalPlayerInfo.previous();
	}

	/**
	 * 다음 곡 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public int next() {
		return GlobalPlayerInfo.next();
	}

	/**
	 * 반복 재생 여부
	 * 
	 * @param repeat
	 *            true면 반복 재생
	 */
	public void repeat(boolean repeat) {
		GlobalPlayerInfo.repeat(repeat);
	}

	/**
	 * 볼륨 조정
	 * 
	 * @param volume
	 *            볼륨 : 범위 100~0
	 */
	public void setVolume(int volume) {
		GlobalPlayerInfo.setVolume(volume);
	}

	/**
	 * 재생 Progress 이동
	 * 
	 * @param seek
	 *            이동 값 0~1 사이
	 */
	public void setProgressRate(double seek) {
		GlobalPlayerInfo.setProgressRate(seek);
	}

	/**
	 * 컨트롤러와 관련된 상태 정보
	 * 
	 * @return 상태 정보
	 */
	public PlayerStat getPlayerStat() {
		return GlobalPlayerInfo.getPlayerStat();
	}

	/**
	 * @return 동기화 디렉토리 목록
	 */
	public List<MusicDirectory> getSyncDiretory() {
		return musicService.getMusicDirectory();
	}

	/**
	 * 포함되어 있는 디렉토리 및 DB 기준 동기화를 수행<br>
	 * 가능성 없는 두개 이상이 스레드 동기성 문제는 무시한다. 일어나도 별 문제 없다<br>
	 * 
	 * @return true 동기화 정상, false: 이미 동기화 진행중
	 */
	public boolean syncAll() {
		if (!syncing) {
			try {
				syncing = true;
				List<MusicDirectory> dirs = musicService.getMusicDirectory();
				for (MusicDirectory dir : dirs) {
					syncService.syncDirectory(dir.getPath());
					syncService.syncDb();
					dir.setSyncDate(new Date());
					musicService.updateMusicDirectory(dir);
				}
				return true;
			} finally {
				syncing = false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * 동기화 디렉토리 추가
	 * 
	 * @param path
	 *            동기화할 디렉토리
	 * @return 성공 여부
	 */
	public boolean addSyncDirectory(String path) {
		File f = new File(path);

		if (!f.exists() || !f.isDirectory()) {
			return false;
		}

		MusicDirectory item = new MusicDirectory();
		item.setBasePath(path);
		musicService.createMusicDirectory(item);

		return true;
	}

	/**
	 * 동기화 디렉토리 삭제
	 * 
	 * @param path
	 *            동기화 디렉토리
	 */
	public void removeSyncDirectory(String path) {
		musicService.removeMusicDirectory(path);
	}

	/**
	 * 앨범 목록
	 * 
	 * @return 앨범 목록
	 */
	public List<Album> getAlbumList() {
		Collection<Album> a = musicService.getAlbumListAll();
		// TODO 임시 저장은 보내지 않음
		List<Album> result = new ArrayList<Album>();
		result.addAll(a);
		return result;
	}

	/**
	 * 음악 앨범 선택. 선택한 앨범의 음악이 재생 목록에 추가
	 * 
	 * @param albumSeq
	 *            앨범 번호
	 */
	public void useAlbum(int albumSeq) {
		Album album = musicService.getAlbum(albumSeq);
		List<MusicArticle> musicList = album.getMusicArticleList();
		GlobalPlayerInfo.clearPlayList();
		GlobalPlayerInfo.addPlayArticle(musicList);
	}

	/**
	 * 앨범 불러 오기
	 * 
	 * @param albumSeq
	 *            앨범 아이디
	 * @return 앨범
	 */
	public Album getAlbum(int albumSeq) {
		Album album = musicService.getAlbum(albumSeq);
		return album;
	}

	/**
	 * 앨범 이름 수정
	 * 
	 * @param albumSeq
	 *            앨범 아이디
	 * @param name
	 *            앨범 이름
	 */
	public void updateAlbum(int albumSeq, String name) {
		Album album = musicService.getAlbum(albumSeq);
		album.setName(name);
		musicService.updateAlbum(album);
	}

	/**
	 * 현재 플레이 음악 앨범 저장
	 * 
	 * @param name
	 *            앨범 이름
	 */
	public void saveAlbum(String name) {
		Album saveAlbum = new Album();
		saveAlbum.setName(name);
		musicService.createAlbum(saveAlbum);
		List<MusicArticle> list = GlobalPlayerInfo.getPlayArticle();
		int order = 0;
		for (MusicArticle aa : list) {
			PlayItem item = new PlayItem();
			item.setAlbumSeq(saveAlbum.getAlbumSeq());
			item.setMusicId(aa.getMusicId());
			item.setOrderNo(order++);
			musicService.createPlayItem(item);
		}
	}

	/**
	 * 앨범 삭제
	 * 
	 * @param albumSeq
	 *            앨범 아이디
	 */
	public void removeAlbum(int albumSeq) {
		musicService.removeAlbum(albumSeq);
	}

	/**
	 * 선택된 노래 가사 보기
	 * 
	 * @return 노래 가사
	 */
	public String getLyrics() {
		PlayerStat t = GlobalPlayerInfo.getPlayerStat();
		MusicArticle a = t.getPlayArticle();
		if (a == null) {
			return null;
		}
		return a.getLyricsWithOutTime();
	}
}
