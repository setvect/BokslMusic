package com.setvect.bokslmusic.web.dwr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.boot.EnvirmentInit;
import com.setvect.bokslmusic.player.GlobalPlayerInfo;
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
	public static void deduplication() {
		GlobalPlayerInfo.deduplication();
	}
}
