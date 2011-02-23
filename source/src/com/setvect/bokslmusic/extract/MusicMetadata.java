package com.setvect.bokslmusic.extract;

import java.io.File;

import com.setvect.bokslmusic.service.music.MusicFileAnalysis;
import com.setvect.bokslmusic.service.music.MusicFileAnalysis.MusicFileKind;
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

	private MusicFileKind fileKind;

	/**
	 * @param f
	 */
	public MusicMetadata(File f) {
		sourceFile = f;
		String ext = FileUtil.getExt(sourceFile.getName());
		fileKind = MusicFileAnalysis.EXT_MAPPING.get(ext);
	}

	public File getSourceFile() {
		return sourceFile;
	}

	/**
	 * @return MD5 �ڵ�
	 */
	public String getHeaderCode() {
		return fileKind.getHeaderMd5(sourceFile);
	}

	/**
	 * @param audioFile
	 *            ��������
	 * @return MD5 �ڵ�
	 */
	public static String getHeaderMd5(File audioFile) {
		String ext = FileUtil.getExt(audioFile.getName());
		MusicFileKind fileKind = MusicFileAnalysis.EXT_MAPPING.get(ext);
		return fileKind.getHeaderMd5(audioFile);
	}

	/**
	 * �ܺ� �������� �޾ƿ� �ڷ� <br>
	 */
	public AlSongMetadata getAlSongMetadata() {
		if (alSongMetadata == null) {
			alSongMetadata = new AlSongMetadata(getHeaderCode());
		}
		return alSongMetadata;
	}

	/**
	 * @return �������� ������ ���� AudioMetadata
	 */
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
		MusicFileKind kind = MusicFileAnalysis.EXT_MAPPING.get(ext);
		if (kind == null) {
			return null;
		}

		AudioMetadata newAudioMetadataInstance = kind.newAudioMetadataInstance(sourceFile);
		return newAudioMetadataInstance;
	}

	/**
	 * @return DB�� ����� ���� ���� VO
	 */
	public MusicArticle getMusicArticle() {
		load();

		MusicArticle article = new MusicArticle();
		article.setMusicId(alSongMetadata.getMd5());
		article.setName(sourceFile.getName());
		article.setPath(sourceFile.getParent());
		article.setLyrics(alSongMetadata.getLyric());
		article.setFileSize((int) sourceFile.length());
		article.setSamplingRate(audioMetadata.getSamplingRate());
		article.setBitRate(audioMetadata.getBatRate());
		article.setRunningTime(audioMetadata.getRunningTime());
		article.setTitleExt(alSongMetadata.getTitle());
		article.setArtistExt(alSongMetadata.getArtist());

		if (audioMetadata instanceof Mp3AudioMetadata) {
			Mp3AudioMetadata mp3Meta = (Mp3AudioMetadata) audioMetadata;
			article.setTitleTag(mp3Meta.getTitle());
			article.setArtistTag(mp3Meta.getArtist());
			article.setAlbumTag(mp3Meta.getAlbum());
			article.setYearTag(mp3Meta.getYear());
			article.setGenreTag(mp3Meta.getGenre());
			article.setTrackTag(mp3Meta.getTrack());
		}
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
