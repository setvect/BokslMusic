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

	/** 볼륨 : 범위 100~0 */
	private int volume;

	/** 플래이 진행률 0~1 */
	private double progressRate;

	/**
	 * 재생 상태
	 */
	private PlayerStatus playStatus;

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
	 * @return the playStatus
	 */
	public PlayerStatus getPlayStatus() {
		return playStatus;
	}

	/**
	 * 재생 상태
	 * 
	 * @param playStatus
	 *            the playStatus to set
	 */
	public void setPlayStatus(PlayerStatus playStatus) {
		this.playStatus = playStatus;
	}

	/**
	 * @return 볼륨 : 범위 100~0
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            볼륨 : 범위 100~0
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * 플래이 진행률
	 * 
	 * @return 0~1
	 */
	public double getProgressRate() {
		return progressRate;
	}

	/**
	 * 플래이 진행률
	 * 
	 * @param progressRate
	 *            0~1
	 */
	public void setProgressRate(double progressRate) {
		this.progressRate = progressRate;
	}
}
