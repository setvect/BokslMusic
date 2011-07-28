package com.setvect.bokslmusic.player;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import com.setvect.common.log.LogPrinter;

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
	 * 현재 진행 상태
	 */
	public enum PlayerStatus {
		STOP, PLAY, PAUSE
	}

	/**
	 * 오디오 자원은 하나 밖에 없기 때문에 인스턴스를 만들지 않음
	 */
	private AudioPlayer() {
	}

	/** 재생기 상태 값 */
	private static PlayerStatus status = PlayerStatus.STOP;

	/**
	 * @param audioFile
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#open(java.io.File)
	 */
	public static void open(File audioFile) {
		try {
			LogPrinter.out.debug("Play:" + audioFile);
			player.open(audioFile);
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#play()
	 */
	public static void play() {
		try {
			player.play();
			status = PlayerStatus.PLAY;
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#pause()
	 */
	public static void pause() {
		try {
			player.pause();
			status = PlayerStatus.PAUSE;
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 정지
	 */
	public static void stop() {
		try {
			player.stop();
			status = PlayerStatus.STOP;
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#resume()
	 */
	public static void resume() {
		try {
			player.resume();
			status = PlayerStatus.PLAY;
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 볼륨 조정
	 * 
	 * @param volume
	 *            0~1 값
	 * @throws BasicPlayerException
	 * @see javazoom.jlgui.basicplayer.BasicPlayer#setGain(double)
	 */
	public static void setVolume(double volume) {
		try {
			player.setGain(volume);
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
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
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return 재생기 상태값
	 */
	public static PlayerStatus getStatus() {
		return status;
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
