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
		for (double d = 0.1; d <= 1; d += .1) {
			System.out.println("Volume: " + d);
			AudioPlayer.setVolume(d);
			Thread.sleep(3000);
		}

	}
}
