package com.setvect.bokslmusic.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 전역적인 재생 정보<br>
 * 단일 사용자를 대상으로 하기 때문에 모든 속성 값은 static이다.
 * 
 * @version $Id$
 */
public class GlobalPlayerInfo {
	/** 재생 리스트 */
	private static List<MusicArticle> playList = new ArrayList<MusicArticle>();

	/** 재생중인 재생 리스트 순번 */
	private static int playIndex;

	/** 재생 중인 음악 */
	private static MusicArticle playMusic;

	/** 재생기 */
	private static AudioPlayer player;

	/**
	 * 재상 항목 추가
	 * 
	 * @param items
	 *            재생 항목
	 */
	public static void addPlayArticle(List<MusicArticle> items) {
		playList.addAll(items);
	}

	/**
	 * 재생 목록 조회
	 * 
	 * @return 재생 목록
	 */
	public static List<MusicArticle> getPlayArticle() {
		return Collections.unmodifiableList(playList);
	}

	/**
	 * 재생 항목 삭제
	 * 
	 * @param idx
	 *            삭재 할 목록 인덱스
	 */
	public static void removePlayList(int idx) {
		playList.remove(idx);
	}

	/**
	 * 재생 목록 추가
	 * 
	 * @param article
	 *            재생 목록 추가
	 */
	public static void addPlayArticle(MusicArticle article) {
		playList.add(article);
	}

	/**
	 * 재생 전체 항목 제거
	 */
	public static void clearPlayList() {
		playList.clear();
	}

	/**
	 * 중복 제거
	 */
	public static void deduplication() {
		List<MusicArticle> temp = new ArrayList<MusicArticle>();
		for (MusicArticle a : playList) {
			if (temp.contains(a)) {
				continue;
			}
			temp.add(a);
		}
		playList = temp;
	}

	/**
	 * 재생 목록 섞이
	 */
	public static void shuffle() {
		Collections.shuffle(playList);
	}

}
