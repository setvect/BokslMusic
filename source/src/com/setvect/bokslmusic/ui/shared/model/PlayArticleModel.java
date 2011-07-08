package com.setvect.bokslmusic.ui.shared.model;

/**
 * 재생 정보
 */
public class PlayArticleModel extends MusicArticleModel {
	/** */
	private static final long serialVersionUID = 8029538003649310292L;

	// Serialize를 하기 위해 기본 생성자 필요
	public PlayArticleModel() {
	}

	public String getLyrics() {
		return get("lyrics");
	}

	public void setLyrics(String lyrics) {
		set("lyrics", lyrics);
	}

	public int getSamplingRate() {
		int samplingRate = (Integer) get("samplingRate");
		return samplingRate;
	}

	public void setSamplingRate(int samplingRate) {
		set("samplingRate", samplingRate);
	}

	public int getBitRate() {
		int bitRate = (Integer) get("bitRate");
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		set("bitRate", bitRate);
	}
}
