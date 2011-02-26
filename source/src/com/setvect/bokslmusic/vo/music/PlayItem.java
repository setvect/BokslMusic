package com.setvect.bokslmusic.vo.music;

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
 * �ٹ� ����
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAD_PLAY_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayItem {
	/** �ٹ��Ϸù�ȣ */
	@Id
	@Column(name = "PLAY_ITEM_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int playItemSeq;

	/** ���� ���̵� */
	@Column(name = "MUSIC_ID")
	private String musicId;

	/** �ٹ��Ϸù�ȣ */
	@Column(name = "ALBUM_SEQ")
	private int albumSeq;

	/** ���� ���� */
	@Column(name = "ORDER_NO")
	private int orderNo;

	public int getPlayItemSeq() {
		return playItemSeq;
	}

	public void setPlayItemSeq(int playListSeq) {
		this.playItemSeq = playListSeq;
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
