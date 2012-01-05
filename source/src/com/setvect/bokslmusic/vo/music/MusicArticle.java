package com.setvect.bokslmusic.vo.music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.setvect.bokslmusic.config.BokslMusicConstant;
import com.setvect.common.log.LogPrinter;

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
	/** OS 경로 상에 파일 경로, 파일 이름 포함 하지 않음 */
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

	/** 노래 가사 및 시간 정보 */
	@Transient
	private List<LyricsTime> lyricsTime;

	/** 노래 진행시간(초) */
	@Transient
	private int currentTime;

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

	/**
	 * 노래 시간에 따른 강조 태그를 넣어서 리턴
	 * 
	 * @return 강조 태그가 들어가 노래 가사
	 */
	public String getLyricsHighlight() {
		if (lyricsTime == null) {
			lyricsTime = loadLyricsTime();
		}

		int highlightIdx = -1;
		for (int i = 0; i < lyricsTime.size(); i++) {
			if (currentTime - 1 < lyricsTime.get(i).time) {
				break;
			}
			highlightIdx = i;
		}

		StringBuffer lyr = new StringBuffer();
		lyr.append(this.getTitleExt() + "\n\n");
		for (int i = 0; i < lyricsTime.size(); i++) {
			LyricsTime lt = lyricsTime.get(i);
			if (i == highlightIdx) {
				lyr.append("<" + BokslMusicConstant.LYRICS_HIGHLIGHT_TAG + ">");
				lyr.append(lt.lyrics);
				lyr.append("</" + BokslMusicConstant.LYRICS_HIGHLIGHT_TAG + ">");
			}
			else {
				lyr.append(lt.lyrics);
			}
			lyr.append("\n");
		}
		return lyr.toString();
	}

	/**
	 * 시간별 노래 가사
	 * 
	 * @return 시간별 노래 가사
	 */
	private List<LyricsTime> loadLyricsTime() {
		List<LyricsTime> l = new ArrayList<LyricsTime>();
		String[] lines = lyrics.split("\n");
		for (String line : lines) {
			if (line.length() < 10 || !line.startsWith("[") || line.startsWith("[00:00.00]")) {
				continue;
			}
			int second = second(line);
			String substring = line.substring(10);
			LyricsTime time = new LyricsTime(second, substring);
			l.add(time);
		}
		return l;
	}

	/**
	 * 음악 파일
	 * 
	 * @return 음악 파일
	 */
	public File getFile() {
		return new File(path, name);
	}

	/**
	 * 가사 라인에 대한 시간(초)<br>
	 * 형식 예) [03:13.56]헤어진 모습 이대로 <br>
	 * <br>
	 * 99분 이상의 노래는 고려 하지 않았음 아마 애러 날 것임
	 * 
	 * @param lyricsLine
	 *            가사 한 라인
	 * @return 시간 초
	 */
	private int second(String lyricsLine) {

		int minute = Integer.parseInt(lyricsLine.substring(1, 3));
		int second = Integer.parseInt(lyricsLine.substring(4, 6));

		return minute * 60 + second;
	}

	/**
	 * 현재 노개 가사 진행 초
	 * 
	 * @param currentTime
	 *            현재 노개 가사 진행 초
	 */
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	public String toString() {
		return "MusicArticle [musicId=" + musicId + ", name=" + name + ", path=" + path + ", lyrics=" + lyrics
				+ ", fileSize=" + fileSize + ", samplingRate=" + samplingRate + ", bitRate=" + bitRate
				+ ", runningTime=" + runningTime + ", titleTag=" + titleTag + ", artistTag=" + artistTag
				+ ", albumTag=" + albumTag + ", yearTag=" + yearTag + ", genreTag=" + genreTag + ", trackTag="
				+ trackTag + ", titleExt=" + titleExt + ", artistExt=" + artistExt + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((musicId == null) ? 0 : musicId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MusicArticle)) {
			return false;
		}
		MusicArticle other = (MusicArticle) obj;
		if (musicId == null) {
			if (other.musicId != null) {
				return false;
			}
		}
		else if (!musicId.equals(other.musicId)) {
			return false;
		}
		return true;
	}

	/**
	 * 가사 라인에 대한 시간
	 */
	private class LyricsTime {
		/** 시간 */
		private int time;
		/** 노래 가사 */
		private String lyrics;

		public LyricsTime(int t, String l) {
			time = t;
			lyrics = l;
		}

	}

}
