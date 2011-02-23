package com.setvect.bokslmusic.extract;

/**
 * @version $Id$ 오디오 메타 정보
 */
public interface AudioMetadata {
	/**
	 * @return 샘플링 비율
	 */
	public int getSamplingRate();

	/**
	 * @return 비트레이트
	 */
	public int getBatRate();

	/**
	 * @return 재생시간(초단위)
	 */
	public int getRunningTime();

}
