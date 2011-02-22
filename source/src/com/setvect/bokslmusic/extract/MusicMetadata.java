package com.setvect.bokslmusic.extract;

import java.io.File;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.common.util.FileUtil;
import com.zuch.music.util.AlSongMetadata;
import com.zuch.music.util.Md5Util;

/**
 * 음원에 대한 metadata 분석<br>
 * 추출정보는 늦은(lazy)로딩 형태로 데이터를 가져옴
 * 
 * @version $Id$
 */
public class MusicMetadata {
	/** 음원 첫 번째 프레임 */
	private static final int HEADER_LENGTH = 160 * 1024;

	/** */
	private final File sourceFile;

	/** */
	private AlSongMetadata alSongMetadata;

	private AudioMetadata audioMetadata;

	/** 음원 파일를 구별 하기위한 Key값 */
	private String headerMd5;

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
	 * @return MD5 코드
	 */
	public String getHeaderCode() {
		if (headerMd5 == null) {
			headerMd5 = getHeaderMd5(sourceFile);
		}
		return headerMd5;
	}

	/**
	 * @param audioFile
	 *            음원파일
	 * @return MD5 코드
	 */
	public static String getHeaderMd5(File audioFile) {
		long audioStartBytePosition = getAudioStartBytePosition(audioFile);
		String s = Md5Util.getMD5Checksum(audioFile, audioStartBytePosition, HEADER_LENGTH);
		return s;
	}

	/**
	 * @param audioFile
	 *            음원파일
	 * @return 오디오 시작 위치
	 */
	private static long getAudioStartBytePosition(File audioFile) {
		String ext = FileUtil.getExt(audioFile.getName());
		if (ext.equalsIgnoreCase(".mp3")) {
			try {
				MP3File f = (MP3File) AudioFileIO.read(audioFile);
				MP3AudioHeader header = (MP3AudioHeader) f.getAudioHeader();
				long mp3Start = header.getMp3StartByte();
				return mp3Start;
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/**
	 * 외부 서버에서 받아온 자료 <br>
	 */
	public AlSongMetadata getAlSongMetadata() {
		if (alSongMetadata == null) {
			alSongMetadata = new AlSongMetadata(getHeaderCode());
		}
		return alSongMetadata;
	}

	/**
	 * @return 음원파일 종류에 따른 AudioMetadata
	 */
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

	/**
	 * @return DB에 저장될 음원 정보 VO
	 */
	public MusicArticle getMusicArticle() {
		load();

		MusicArticle article = new MusicArticle();
		article.setMusicId(alSongMetadata.getMd5());
		article.setName(sourceFile.getName());
		article.setPath(sourceFile.getPath());
		article.setLyrics(alSongMetadata.getLyric());
		article.setFileSize((int) sourceFile.length());
		article.setSamplingRate(audioMetadata.getSamplingRate());
		article.setBitRate(audioMetadata.getBatRate());
		article.setRunningTime(audioMetadata.getRunningTime());
		article.setTitleTag(audioMetadata.getTitle());
		article.setArtistTag(audioMetadata.getArtist());
		article.setAlbumTag(audioMetadata.getAlbum());
		article.setYearTag(audioMetadata.getYear());
		article.setGenreTag(audioMetadata.getGenre());
		article.setTrackTag(audioMetadata.getTrack());
		article.setTitleExt(alSongMetadata.getTitle());
		article.setArtistExt(alSongMetadata.getArtist());
		return article;
	}

	/**
	 * 음원 정보 로드
	 */
	private void load() {
		if (alSongMetadata == null) {
			alSongMetadata = new AlSongMetadata(sourceFile);
		}
		if (audioMetadata == null) {
			audioMetadata = newAudioInstance();
		}
	}
}
