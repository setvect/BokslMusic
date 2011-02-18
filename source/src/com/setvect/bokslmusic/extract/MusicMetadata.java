package com.setvect.bokslmusic.extract;

import java.io.File;

import com.setvect.common.util.FileUtil;
import com.zuch.music.util.AlSongMetadata;

/**
 * 음원에 대한 metadata 분석<br>
 * 추출정보는 늦은(lazy)로딩 형태로 데이터를 가져옴
 * 
 * @version $Id$
 */
public class MusicMetadata {
	/** */
	private final File sourceFile;

	/** */
	private AlSongMetadata getter;

	private AudioMetadata audioMetadata;

	/**
	 * @param f
	 */
	public MusicMetadata(File f) {
		sourceFile = f;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	/**
	 * 외부 서버에서 받아온 자료 <br>
	 * 스레드에 안전하지 못함
	 */
	public AlSongMetadata getExtInfor() {
		if (getter == null) {
			getter = new AlSongMetadata(sourceFile);
		}
		return getter;
	}

	public AudioMetadata getAudioMetadata() {
		if (audioMetadata == null) {
			audioMetadata = newAudioInstance();
		}
		return audioMetadata;
	}

	/**
	 * @return 음원파일 종류에 따른 AudioMetadata 인스턴스
	 */
	private AudioMetadata newAudioInstance() {
		String ext = FileUtil.getExt(sourceFile.getName());
		ext = ext.toLowerCase();
		if (ext.equals(".mp3")) {
			return new Mp3AudioMetadata(sourceFile);
		}
		return null;
	}
}
