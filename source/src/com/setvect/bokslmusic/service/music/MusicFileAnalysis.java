package com.setvect.bokslmusic.service.music;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.flac.FlacStreamReader;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import com.setvect.bokslmusic.extract.AudioMetadata;
import com.setvect.bokslmusic.extract.GenericAudioMetadata;
import com.setvect.bokslmusic.extract.Mp3AudioMetadata;
import com.setvect.common.log.LogPrinter;
import com.zuch.music.util.Md5Util;

/**
 * 음원 파일에 따른 분석 인스턴스 제공
 * 
 * @version $Id$
 */
public class MusicFileAnalysis {
	/** 음원 첫 번째 프레임 */
	private static final int HEADER_LENGTH = 160 * 1024;

	/** 확장자에 따른 음원 타입 맴핑 */
	public static final Map<String, MusicFileKind> EXT_MAPPING = new HashMap<String, MusicFileAnalysis.MusicFileKind>();
	static {
		EXT_MAPPING.put(".mp3", MusicFileKind.MP3);
		EXT_MAPPING.put(".ogg", MusicFileKind.OGG);
		EXT_MAPPING.put(".wma", MusicFileKind.WMA);
		EXT_MAPPING.put(".flac", MusicFileKind.FLAC);
	}

	/**
	 * 음원 파일에 따른 분석 인스턴스 제공
	 */
	public enum MusicFileKind {
		MP3, OGG, WMA, FLAC;
		/**
		 * @param source
		 * @return
		 */
		public AudioMetadata newAudioMetadataInstance(File source) {
			switch (this) {
			case MP3:
				return new Mp3AudioMetadata(source);
			case OGG:
				return new GenericAudioMetadata(source);
			case WMA:
				return new GenericAudioMetadata(source);
			case FLAC:
				return new GenericAudioMetadata(source);
			}
			return null;
		}

		/**
		 * @param audioFile
		 *            음원파일
		 * @return 음원 파일의 해더 부분 영역의 md5 코드
		 */
		public String getHeaderMd5(File audioFile) {
			switch (this) {
			case MP3:
				return getMp3Md5(audioFile);
			case OGG:
				return getOggMd5(audioFile);
			case WMA:
				return getWmaMd5(audioFile);
			case FLAC:
				return getFlacMd5(audioFile);
			}
			return null;

		}
	}

	/**
	 * @param audioFile
	 * @return MP3 Header 값
	 */
	private static String getMp3Md5(File audioFile) {
		// 오디오 시작 위치
		long audioStartBytePosition = 0;
		try {
			MP3File f = (MP3File) AudioFileIO.read(audioFile);
			MP3AudioHeader header = (MP3AudioHeader) f.getAudioHeader();
			long mp3Start = header.getMp3StartByte();
			audioStartBytePosition = mp3Start;
		}
		catch (Exception e) {
			LogPrinter.out.warn(e);
		}
		String s = Md5Util.getMD5Checksum(audioFile, audioStartBytePosition, HEADER_LENGTH);
		return s;
	}

	/**
	 * @param audioFile
	 * @return Ogg Header 값
	 */
	private static String getOggMd5(File audioFile) {
		// 오디오 시작 위치
		long audioStartBytePosition = 0;
		String s = Md5Util.getMD5Checksum(audioFile, audioStartBytePosition, HEADER_LENGTH);
		return s;
	}

	/**
	 * @param audioFile
	 * @return Wma Header 값
	 */
	private static String getWmaMd5(File audioFile) {
		// 오디오 시작 위치
		long audioStartBytePosition = 0;
		String s = Md5Util.getMD5Checksum(audioFile, audioStartBytePosition, HEADER_LENGTH);
		return s;
	}

	/**
	 * @param audioFile
	 * @return Flac Header 값
	 */
	private static String getFlacMd5(File audioFile) {
		// 오디오 시작 위치
		long audioStartBytePosition = 0;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(audioFile, "r");
			FlacStreamReader stramReader = new FlacStreamReader(raf);
			stramReader.findStream();
			audioStartBytePosition = stramReader.getStartOfFlacInFile();
		}
		catch (Exception e) {
			LogPrinter.out.warn(e);
		}
		finally {
			if (raf != null) {
				try {
					raf.close();
				}
				catch (IOException e) {
				}
			}
		}

		String s = Md5Util.getMD5Checksum(audioFile, audioStartBytePosition, HEADER_LENGTH);
		return s;
	}
}
