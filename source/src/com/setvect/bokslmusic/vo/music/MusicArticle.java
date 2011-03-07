package com.setvect.bokslmusic.vo.music;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 음악 정보<br>
 * 음원파일 일부(0 ~163,840byte)에 대한 MD5구함 128bit(32자리)로 구성된 MD5<br>
 * 다른 파일에 동일한 MD5 충돌의 가능성은 무시
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAB_MUSIC_ARTICLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MusicArticle {
	/** 파일 MD5 */
	@Id
	@Column(name = "MUSIC_ID")
	private String musicId;
	/** 파일 이름 */
	@Column(name = "NAME")
	private String name;
	/** OS 경로 상에 파일 경로, 파일 이름 포함 하지 않음*/
	@Column(name = "PATH")
	private String path;
	/** 가사 */
	@Column(name = "LYRICS")
	private String lyrics;
	/** 파일 사이즈 */
	@Column(name = "FILE_SIZE")
	private int fileSize;
	/** 샘플링 비율 */
	@Column(name = "SAMPLING_RATE")
	private int samplingRate;
	/** 비트레이트 */
	@Column(name = "BIT_RATE")
	private int bitRate;
	/** 재생시간(초단위) */
	@Column(name = "RUNNING_TIME")
	private int runningTime;
	/** 메타정보 추출 제목 */
	@Column(name = "TITLE_TAG")
	private String titleTag;
	/** 메타정보 추출 가수/연주자 */
	@Column(name = "ARTIST_TAG")
	private String artistTag;
	/** 메타정보 추출 앨범 */
	@Column(name = "ALBUM_TAG")
	private String albumTag;
	/** 메타정보 추출 년도 */
	@Column(name = "YEAR_TAG")
	private String yearTag;
	/** 메타정보 추출 장르 */
	@Column(name = "GENRE_TAG")
	private String genreTag;
	/** 메타정보 추출 트랙 */
	@Column(name = "TRACK_TAG")
	private String trackTag;
	/** 외부수집 제목 */
	@Column(name = "TITLE_EXT")
	private String titleExt;
	/** 외부수집 가수/연주자 */
	@Column(name = "ARTIST_EXT")
	private String artistExt;

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getSamplingRate() {
		return samplingRate;
	}

	public void setSamplingRate(int samplingRate) {
		this.samplingRate = samplingRate;
	}

	public int getBitRate() {
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public String getTitleTag() {
		return titleTag;
	}

	public void setTitleTag(String titleTag) {
		this.titleTag = titleTag;
	}

	public String getArtistTag() {
		return artistTag;
	}

	public void setArtistTag(String artistTag) {
		this.artistTag = artistTag;
	}

	public String getAlbumTag() {
		return albumTag;
	}

	public void setAlbumTag(String albumTag) {
		this.albumTag = albumTag;
	}

	public String getYearTag() {
		return yearTag;
	}

	public void setYearTag(String yearTag) {
		this.yearTag = yearTag;
	}

	public String getGenreTag() {
		return genreTag;
	}

	public void setGenreTag(String genreTag) {
		this.genreTag = genreTag;
	}

	public String getTrackTag() {
		return trackTag;
	}

	public void setTrackTag(String trackTag) {
		this.trackTag = trackTag;
	}

	public String getTitleExt() {
		return titleExt;
	}

	public void setTitleExt(String titleExt) {
		this.titleExt = titleExt;
	}

	public String getArtistExt() {
		return artistExt;
	}

	public void setArtistExt(String artistExt) {
		this.artistExt = artistExt;
	}

	public File getFile() {
		return new File(path, name);
	}

	@Override
	public String toString() {
		return "MusicArticle [musicId=" + musicId + ", name=" + name + ", path=" + path + ", lyrics=" + lyrics
				+ ", fileSize=" + fileSize + ", samplingRate=" + samplingRate + ", bitRate=" + bitRate
				+ ", runningTime=" + runningTime + ", titleTag=" + titleTag + ", artistTag=" + artistTag
				+ ", albumTag=" + albumTag + ", yearTag=" + yearTag + ", genreTag=" + genreTag + ", trackTag="
				+ trackTag + ", titleExt=" + titleExt + ", artistExt=" + artistExt + "]";
	}
}
