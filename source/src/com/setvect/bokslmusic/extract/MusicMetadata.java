package com.setvect.bokslmusic.extract;

import java.io.File;

import com.setvect.bokslmusic.vo.music.MusicArticle;
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
	private AlSongMetadata alSongMetadata;

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
	public AlSongMetadata getAlSongMetadata() {
		if (alSongMetadata == null) {
			alSongMetadata = new AlSongMetadata(sourceFile);
		}
		return alSongMetadata;
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

	/**
	 * @return DB�� ����� ���� ���� VO
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
	 * ���� ���� �ε�
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
