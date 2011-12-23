package com.setvect.bokslmusic.player;

import com.setvect.bokslmusic.player.AudioPlayer.PlayerStatus;
import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 재생 컨트롤 상태 값
 * 
 * @version $Id$
 */
public class PlayerStat {
	/** 재생중인 재생 리스트 순번 */
	private int playIndex;

	/** 반복 재생 여부 */
	private boolean repeat;

	/** 현재 재생 중인 음악 */
	private MusicArticle playArticle;

	/**
	 * 재생 상태
	 * 
	 * @see PlayerStatus
	 */
	private String playStatus;

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

	/**
	 * 재생 상태
	 * 
	 * @see PlayerStatus
	 * @return the playStatus
	 */
	public String getPlayStatus() {
		return playStatus;
	}

	/**
	 * 재생 상태
	 * 
	 * @see PlayerStatus
	 * @param playStatus
	 *            the playStatus to set
	 */
	public void setPlayStatus(String playStatus) {
		this.playStatus = playStatus;
	}

}
