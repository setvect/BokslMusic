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
class AudioPlayer {
	public static final PlayerListener playerListener = new PlayerListener();
	private static final BasicPlayer player = new BasicPlayer();
	@SuppressWarnings("rawtypes")
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
	 * 다시 시작
	 * 
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
			LogPrinter.out.info(e);
			// 예외 무시
		}
	}

	/**
	 * MP3, WAV만 지원
	 * 
	 * @param rate
	 *            이동 비율 0 ~ 1
	 */
	public static void seek(double rate) {
		int audioLength = getCurrentAudioLength();
		long skipBytes = (long) Math.round(audioLength * rate);
		try {
			player.seek(skipBytes);
		} catch (BasicPlayerException e) {
			LogPrinter.out.warn(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return 현재 플레이 되는 음원의 사이즈
	 */
	private static int getCurrentAudioLength() {
		int audioLength = ((Integer) audioInfo.get("audio.length.bytes")).intValue();
		return audioLength;
	}

	/**
	 * @return 재생기 상태값
	 */
	public static PlayerStatus getStatus() {
		return status;
	}

	public static class PlayerListener implements BasicPlayerListener {
		private ProgressEventListener progress;
		private StateEventListener state;

		public void opened(Object audio, @SuppressWarnings("rawtypes") Map properties) {
			AudioPlayer.audioInfo = properties;
		}

		public void progress(int bytesread, long microseconds, byte[] pcmdata,
				@SuppressWarnings("rawtypes") Map properties) {
			if (progress != null) {
				progress.event(bytesread, getCurrentAudioLength());
			}
		}

		public void stateUpdated(BasicPlayerEvent event) {
			LogPrinter.out.debug("stateUpdated() - " + event);
			if (state != null) {
				state.event(event);
			}
		}

		public void setController(BasicController controller) {
			LogPrinter.out.debug("setController() - " + controller);

		}

		/**
		 * @param progress
		 *            the progress to set
		 */
		public void setProgressListener(ProgressEventListener progress) {
			this.progress = progress;
		}

		/**
		 */
		public void setStateListener(StateEventListener state) {
			this.state = state;
		}
	}
}
