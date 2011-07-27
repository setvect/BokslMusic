package com.setvect.bokslmusic.player;

import java.io.File;
import java.util.Map;

import com.setvect.common.log.LogPrinter;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * 음원 재생기
 */
public class AudioPlayer {
	private static BasicPlayer player = new BasicPlayer();
	private static PlayerListener playerListener = new PlayerListener();
	private static Map audioInfo;

	static {
		player.addBasicPlayerListener(playerListener);
	}

	/**
	 * 오디오 자원은 하나 밖에 없기 때문에 인스턴스를 만들지 않음
	 */
	private AudioPlayer() {
	}

	/**
	 * @param paramFile
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#open(java.io.File)
	 */
	public static void open(File paramFile) {
		try {
			player.open(paramFile);
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
		}
	}

	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#play()
	 */
	public static void play() {
		try {
			player.play();
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
		}
	}
	
	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#pause()
	 */
	public void pause() throws BasicPlayerException {
		player.pause();
	}

	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#resume()
	 */
	public void resume() throws BasicPlayerException {
		player.resume();
	}

	/**
	 * MP3, WAV만 지원
	 * 
	 * @param rate
	 *            이동 비율 0 ~ 1
	 */
	public static void seek(double rate) {
		int audioLength = ((Integer) audioInfo.get("audio.length.bytes")).intValue();
		long skipBytes = (long) Math.round(audioLength * rate);
		try {
			player.seek(skipBytes);
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
		}
	}

	static class PlayerListener implements BasicPlayerListener {
		public void opened(Object audio, Map properties) {
			AudioPlayer.audioInfo = properties;
		}

		public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
		}

		public void stateUpdated(BasicPlayerEvent event) {
		}

		public void setController(BasicController controller) {
		}
	}
}
