package com.setvect.bokslmusic.vo.music;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 앨범 정보
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAE_PLAY_TIME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayTime {
	/** 일련번호 */
	@Id
	@Column(name = "PLAY_TIME_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int playTimeSeq;

	/** 파일 아이디 */
	@Column(name = "MUSIC_ID")
	private String musicId;

	/** 앨범일련번호 */
	@Column(name = "PLAY_DATE")
	private Date playDate;

	public int getPlayTimeSeq() {
		return playTimeSeq;
	}

	public void setPlayTimeSeq(int playTimeSeq) {
		this.playTimeSeq = playTimeSeq;
	}

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}
}
