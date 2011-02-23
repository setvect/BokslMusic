package com.setvect.bokslmusic.extract;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 * MP3 파일 메타데이터 분석<br/>
 * 태그 버전 V1만 수정
 */
public class Mp3AudioMetadata extends GenericAudioMetadata {
	private final File sourceFile;
	private ID3v1Tag v1Tag;
	private ID3v24Tag v24tag;
	private String encode;

	public Mp3AudioMetadata(File source) {
		super(source);
		sourceFile = source;
		MP3File f;
		try {
			f = (MP3File) AudioFileIO.read(sourceFile);
		} catch (Exception e) {
			throw new RuntimeException("파일 : " + sourceFile.getAbsolutePath(), e);
		}
		v1Tag = f.getID3v1Tag();
		if (v1Tag != null) {
			encode = v1Tag.getEncoding();
		}
		v24tag = f.getID3v2TagAsv24();
	}

	/**
	 * @return 제목
	 */
	public String getTitle() {
		if (v1Tag == null) {
			return null;
		}

		try {
			return new String(v1Tag.getFirstTitle().getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return 가수/연주자
	 */
	public String getArtist() {
		if (v1Tag == null) {
			return null;
		}
		List<TagField> artList = v1Tag.getArtist();
		StringBuffer sb = new StringBuffer();
		boolean first = false;
		for (TagField f : artList) {
			if (first) {
				sb.append(",");
			}
			sb.append(f.toString());
			first = true;
		}
		try {
			return new String(sb.toString().getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return 앨범
	 */
	public String getAlbum() {
		return null;
	}

	/**
	 * @return 년도
	 */
	public String getYear() {
		if (v1Tag == null) {
			return null;
		}
		try {
			return new String(v1Tag.getFirstYear().getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return 장르
	 */
	public String getGenre() {
		if (v1Tag == null) {
			return null;
		}
		try {
			return new String(v1Tag.getFirstGenre().getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 항후 구현. 현재 까지지는 아무런 정보 없음
	 * 
	 * @return 트랙.
	 */
	public String getTrack() {
		return null;
	}
}
