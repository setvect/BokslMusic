package com.setvect.bokslmusic.player;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;

import org.junit.Test;

public class MP3PlayerTestCase {
	@Test
	public void test() throws BasicPlayerException, InterruptedException {
		File file = new File("sample_data/a.mp3");
		AudioPlayer.open(file);
		AudioPlayer.play();
		Thread.sleep(5000);

		AudioPlayer.seek(.90);
		Thread.sleep(5000);

	}
}
