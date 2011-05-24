package com.setvect.bokslmusic.ui.shared.model;

/**
 * 앨범 전체 정보
 */
public class MusicArticleModel extends MusicDefaultModel {
	/** */
	private static final long serialVersionUID = 8029538003649310292L;

	// Serialize를 하기 위해 기본 생성자 필요
	public MusicArticleModel() {
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
