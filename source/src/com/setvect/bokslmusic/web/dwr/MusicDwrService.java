package com.setvect.bokslmusic.web.dwr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.player.GlobalPlayerInfo;
import com.setvect.bokslmusic.player.PlayerStat;
import com.setvect.bokslmusic.service.music.MusicService;
import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 프로젝트 목록 제공
 * 
 * @version $Id$
 */
@Service
public class MusicDwrService {
	// TODO 스프링 시작시점에서 Bind가 되지 않는다.
	private MusicService musicService = (MusicService) EnvirmentInit.getConfigSpring().getBean("MusicService");

	/**
	 * 음악 파일 목록을 조회 함
	 * 
	 * @param folder
	 *            폴더명
	 * @return 음악 파일 목록
	 */
	public List<MusicArticle> getPlayListFolder(String folder) {
		List<MusicArticle> result = musicService.getPlayListFolder(folder);
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
		List<MusicArticle> items = musicService.getPlayListFolder(folder);
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
	public static void clearPlayList() {
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
	public static PlayerStat getPlayerStat() {
//		LogPrinter.out.debug("요청");
		return GlobalPlayerInfo.getPlayerStat();
	}
}
