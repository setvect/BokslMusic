package com.setvect.bokslmusic.vo.music;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * À½¾Ç Á¤º¸
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAB_MUSIC_ARTICLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MusicArticle {
	/** */
	@Id
	@Column(name = "MUSIC_ID")
	private String musicId;
	/** */
	@Column(name = "NAME")
	private String name;
	/** */
	@Column(name = "PATH")
	private String path;
	/** */
	@Column(name = "LYRICS")
	private String lyrics;
	/** */
	@Column(name = "FILE_SIZE")
	private int fileSize;
	/** */
	@Column(name = "SAMPLING_RATE")
	private int samplingRate;
	/** */
	@Column(name = "BIT_RATE")
	private int bitRate;
	/** */
	@Column(name = "RUNNING_TIME")
	private int runningTime;
	/** */
	@Column(name = "TITLE_TAG")
	private String titleTag;
	/** */
	@Column(name = "ARTIST_TAG")
	private String artistTag;
	/** */
	@Column(name = "ALBUM_TAG")
	private String albumTag;
	/** */
	@Column(name = "YEAR_TAG")
	private String yearTag;
	/** */
	@Column(name = "GENRE_TAG")
	private String genreTag;
	/** */
	@Column(name = "TRACK_TAG")
	private String trackTag;
	/** */
	@Column(name = "TITLE_EXT")
	private String titleExt;
	/** */
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

}
