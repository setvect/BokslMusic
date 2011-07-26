package com.setvect.bokslmusic;

import java.io.File;

import org.junit.Test;

import com.setvect.bokslmusic.play.MP3Player;

public class MP3PlayerTestCase {
	@Test
	public void test() {
		File file = new File("sample_data/a.mp3");
		MP3Player player = new MP3Player();
		player.setMusicFile(file);
		player.play();
	}
}
