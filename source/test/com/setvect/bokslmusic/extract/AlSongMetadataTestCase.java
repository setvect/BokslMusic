package com.setvect.bokslmusic.extract;

import java.io.File;

import org.junit.Test;

import com.zuch.music.util.AlSongMetadata;

/**
 * 알쏭 가서 서버에 접속하여 음악 가사를 비롯한 메타정보를 가져옴
 */
public final class AlSongMetadataTestCase {
	@Test
	public void test() {
		File source = new File("sample_data/d.mp3");
		AlSongMetadata a = new AlSongMetadata(source);
		String s = a.getLyric();
		System.out.println(s);
		System.out.println("종료");
	}
}
