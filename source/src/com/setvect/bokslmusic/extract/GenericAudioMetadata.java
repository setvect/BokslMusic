package com.setvect.bokslmusic.extract;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

/**
 * Flac 파일 메타데이터 분석<br/>
 * 
 * @version $Id$
 */
public class GenericAudioMetadata implements AudioMetadata {
	private File sourceFile;
	private AudioHeader audioHeader;

	public GenericAudioMetadata(File source) {
		sourceFile = source;
		AudioFile aFile;
		try {
			aFile = AudioFileIO.read(sourceFile);
		} catch (Exception e) {
			throw new RuntimeException("파일 : " + sourceFile.getAbsolutePath(), e);
		}
		audioHeader = aFile.getAudioHeader();
	}

	public int getSamplingRate() {
		if (audioHeader == null) {
			return 0;
		}
		return audioHeader.getSampleRateAsNumber();
	}

	public int getBatRate() {
		if (audioHeader == null) {
			return 0;
		}
		return (int) audioHeader.getBitRateAsNumber();
	}

	public int getRunningTime() {
		return audioHeader.getTrackLength();
	}

}
