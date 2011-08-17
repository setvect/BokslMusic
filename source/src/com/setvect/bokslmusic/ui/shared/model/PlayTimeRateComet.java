package com.setvect.bokslmusic.ui.shared.model;

import java.io.Serializable;

/**
 * 현재 재생 중인 비율을 전달 함
 */
public class PlayTimeRateComet implements Serializable {
	/** */
	private static final long serialVersionUID = -9201853683596403434L;
	private double rate;

	// deserialize 하기 위해 기본 생성자 필요
	public PlayTimeRateComet() {

	}

	public PlayTimeRateComet(double r) {
		rate = r;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

}
