package com.setvect.bokslmusic.vo.music;

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
@Table(name = "TBAD_PLAY_LIST")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayList {
	/** �ٹ��Ϸù�ȣ */
	@Id
	@Column(name = "PLAY_LIST_SEQ")
	private int playListSeq;

	/** ���� ���̵� */
	@Column(name = "MUSIC_ID")
	private String musicId;

	/** �ٹ��Ϸù�ȣ */
	@Column(name = "ALBUM_SEQ")
	private int albumSeq;

	/** ���� ���� */
	@Column(name = "ORDER_NO")
	private int orderNo;

	public int getPlayListSeq() {
		return playListSeq;
	}

	public void setPlayListSeq(int playListSeq) {
		this.playListSeq = playListSeq;
	}

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public int getAlbumSeq() {
		return albumSeq;
	}

	public void setAlbumSeq(int albumSeq) {
		this.albumSeq = albumSeq;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

}
