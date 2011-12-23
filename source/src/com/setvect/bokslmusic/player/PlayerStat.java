package com.setvect.bokslmusic.player;

import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 재생 컨트롤 상태 값
 * 
 * @version $Id$
 */
public class PlayerStat {
	/** 재생중인 재생 리스트 순번  */
	private int playIndex;

	/** 반복 재생 여부 */
	private boolean repeat;

	/** 현재 재생 중인 음악 */
	private MusicArticle playArticle;

	/**
	 * @return 재생중이 곡 인덱스
	 */
	public int getPlayIndex() {
		return playIndex;
	}

	/**
	 * @param playIndex
	 *            재생중이 곡 인덱스
	 */
	public void setPlayIndex(int playIndex) {
		this.playIndex = playIndex;
	}

	/**
	 * @return 반복 재생 여부
	 */
	public boolean isRepeat() {
		return repeat;
	}

	/**
	 * @param repeat
	 *            반복 재생 여부
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	/**
	 * @return 현재 재생 중인 음악
	 */
	public MusicArticle getPlayArticle() {
		return playArticle;
	}

	/**
	 * @param playArticle
	 *            현재 재생 중인 음악
	 */
	public void setPlayArticle(MusicArticle playArticle) {
		this.playArticle = playArticle;
	}

}
