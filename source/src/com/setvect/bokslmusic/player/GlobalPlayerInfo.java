package com.setvect.bokslmusic.player;

import java.util.ArrayList;
import java.util.List;

import com.setvect.bokslmusic.vo.music.MusicArticle;

/**
 * 전역적인 재생 정보<br>
 * 단일 사용자를 대상으로 하기 때문에 모든 속성 값은 static이다.
 * 
 * @version $Id$
 */
public class GlobalPlayerInfo {
	private static List<MusicArticle> currentList = new ArrayList<MusicArticle>();

	/** 재생 중인 음악 */
	private static MusicArticle playMusic;

	/** 재생기 */
	private static AudioPlayer player;
}
