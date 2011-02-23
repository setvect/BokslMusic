package com.setvect.bokslmusic.vo.music;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * �ٹ� ����
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAE_PLAY_TIME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayTime {
	/** �Ϸù�ȣ */
	@Id
	@Column(name = "PLAY_TIME_SEQ")
	private int playTimeSeq;

	/** ���� ���̵� */
	@Column(name = "MUSIC_ID")
	private String musicId;

	/** �ٹ��Ϸù�ȣ */
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
