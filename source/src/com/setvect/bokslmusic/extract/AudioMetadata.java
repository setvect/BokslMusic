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

	/**
	 * @return 제목
	 */
	public String getTitle();

	/**
	 * @return 가수/연주자
	 */
	public String getArtist();

	/**
	 * @return 앨범
	 */
	public String getAlbum();

	/**
	 * @return 년도
	 */
	public String getYear();

	/**
	 * @return 장르
	 */
	public String getGenre();

	/**
	 * @return 트랙
	 */
	public String getTrack();

}
