package com.setvect.bokslmusic.web.dwr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.boot.EnvirmentInit;
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
@Service
public class MusicDwrService {
	// TODO 스프링 시작시점에서 Bind가 되지 않는다.
	private static MusicService musicService = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");

	/** 동기화 중 */
	private static boolean syncing = false;

	/**
	 * 음악 파일 목록을 조회 함
	 * 
	 * @param folder
	 *            폴더명
	 * @return 음악 파일 목록
	 */
	public static List<MusicArticle> getPlayListFolder(String folder) {
		List<MusicArticle> result = musicService.getPlayListFolder(folder);
		return result;
	}

	/**
	 * 재생 항목 추가
	 * 
	 * @param musicId
	 *            음악 아이디
	 */
	public static void addPlayList(String musicId) {
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
	public static int addPlayListFolder(String folder) {
		List<MusicArticle> items = musicService.getPlayListFolder(folder);
		GlobalPlayerInfo.addPlayArticle(items);
		return items.size();
	}

	/**
	 * 재생 목록 조회
	 * 
	 * @return 재생 목록
	 */
	public static List<MusicArticle> getPlayArticle() {
		return GlobalPlayerInfo.getPlayArticle();
	}

	/**
	 * 재생 항목 삭제
	 * 
	 * @param idx
	 *            삭재 할 목록 인덱스
	 */
	public static void removePlayList(int idx) {
		GlobalPlayerInfo.removePlayList(idx);
	}

	/**
	 * 재생 항목 전체 삭제
	 */
	public static void clearPlayList() {
		GlobalPlayerInfo.clearPlayList();
	}

	/**
	 * 재생 목록 섞이
	 */
	public static void shufflePlayList() {
		GlobalPlayerInfo.shuffle();
	}

	/**
	 * 중복 제거
	 */
	public static void deduplication() {
		GlobalPlayerInfo.deduplication();
	}

	/**
	 * 현재 커서의 음악 재생, 또는 다시 시작
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int play() {
		return GlobalPlayerInfo.play();
	}

	/**
	 * 현재 재생 목록에 해당 하는 음악 아이디가 없으면 작업 요청을 무시한다.
	 * 
	 * @param musicId
	 *            음악 아이디
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int play(String musicId) {
		return GlobalPlayerInfo.play(musicId);
	}

	/**
	 * 일시멈춤
	 */
	public static void pause() {
		GlobalPlayerInfo.pause();
	}

	/**
	 * 멈춤
	 */
	public static void stop() {
		GlobalPlayerInfo.stop();
	}

	/**
	 * 이전 곡 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int previous() {
		return GlobalPlayerInfo.previous();
	}

	/**
	 * 다음 곡 재생
	 * 
	 * @return 재생되는 음악 Index 번호, 플레이 할 수 없으면 -1
	 */
	public static int next() {
		return GlobalPlayerInfo.next();
	}

	/**
	 * 반복 재생 여부
	 * 
	 * @param repeat
	 *            true면 반복 재생
	 */
	public static void repeat(boolean repeat) {
		GlobalPlayerInfo.repeat(repeat);
	}

	/**
	 * 볼륨 조정
	 * 
	 * @param volume
	 *            볼륨 : 범위 100~0
	 */
	public static void setVolume(int volume) {
		GlobalPlayerInfo.setVolume(volume);
	}

	/**
	 * 재생 Progress 이동
	 * 
	 * @param seek
	 *            이동 값 0~1 사이
	 */
	public static void setProgressRate(double seek) {
		GlobalPlayerInfo.setProgressRate(seek);
	}

	/**
	 * 컨트롤러와 관련된 상태 정보
	 * 
	 * @return 상태 정보
	 */
	public static PlayerStat getPlayerStat() {
		// LogPrinter.out.debug("요청");
		return GlobalPlayerInfo.getPlayerStat();
	}

	/**
	 * @return 동기화 디렉토리 목록
	 */
	public static List<MusicDirectory> getSyncDiretory() {
		return musicService.getMusicDirectory();
	}

	/**
	 * 포함되어 있는 디렉토리 및 DB 기준 동기화를 수행<br>
	 * 가능성 없는 두개 이상이 스레드 동기성 문제는 무시한다. 일어나도 별 문제 없다<br>
	 * 
	 * @return true 동기화 정상, false: 이미 동기화 진행중
	 */
	public static boolean syncAll() {
		if (!syncing) {
			try {
				syncing = true;
				MusicSyncService syncService = (MusicSyncService) EnvirmentInit.getConfigSpring().getBean(
						"MusicSyncService");
				List<MusicDirectory> dirs = musicService.getMusicDirectory();
				for (MusicDirectory dir : dirs) {
					syncService.syncDirectory(dir.getPath());
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
	public static boolean addSyncDirectory(String path) {
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
	public static void removeSyncDirectory(String path) {
		musicService.removeMusicDirectory(path);
	}

	/**
	 * 앨범 목록
	 * 
	 * @return 앨범 목록
	 */
	public static List<Album> getAlbumList() {
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
	public static void useAlbum(int albumSeq) {
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
	public static Album getAlbum(int albumSeq) {
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
	public static void updateAlbum(int albumSeq, String name) {
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
	public static void saveAlbum(String name) {
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
	public static void removeAlbum(int albumSeq) {
		musicService.removeAlbum(albumSeq);
	}
}
