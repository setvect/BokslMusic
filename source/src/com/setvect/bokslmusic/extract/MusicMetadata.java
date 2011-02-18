package com.setvect.bokslmusic.extract;

import java.io.File;

import com.setvect.common.util.FileUtil;
import com.zuch.music.util.AlSongMetadata;

/**
 * ������ ���� metadata �м�<br>
 * ���������� ����(lazy)�ε� ���·� �����͸� ������
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
	 * �ܺ� �������� �޾ƿ� �ڷ� <br>
	 * �����忡 �������� ����
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
	 * @return �������� ������ ���� AudioMetadata �ν��Ͻ�
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
