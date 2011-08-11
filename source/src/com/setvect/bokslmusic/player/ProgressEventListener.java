package com.setvect.bokslmusic.player;

/**
 * 재생 중 발 생 하는 이벤트
 */
public interface ProgressEventListener {

	/**
	 * 읽어들인 바이트 크키와 전체 바이트 크기를 이용해서 현재 진행률을 구할 수 있음
	 * 
	 * @param bytesread
	 *            읽어 드린 바이트 크기
	 * @param currentAudioLength
	 *            음원 전체 바이트 크기
	 */
	public void event(int bytesread, int currentAudioLength);

}
