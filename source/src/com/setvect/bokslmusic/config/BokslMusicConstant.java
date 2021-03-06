package com.setvect.bokslmusic.config;

/**
 * 프로그램에서 사용되는 상수 값들의 모음 <br>
 * 속성값 요청 전에 EnvirmentProperty 클래스가 초기화 되어야됨 <br>
 * 
 * @version $Id$
 */
public class BokslMusicConstant {
	/** 등록한 음악 확장자 */
	public static final String[] MUSIC_EXT = EnvirmentProperty.getStringArray("com.setvect.bokslmusic.audio_ext");

	/** 임시저장용으로 사용되는 앨범 번호 */
	public static final int ALBUM_TEMP = 1;

	/** 노래 가사 하일라이트 부분 태그 */
	public static final String LYRICS_HIGHLIGHT_TAG = "strong";
}
