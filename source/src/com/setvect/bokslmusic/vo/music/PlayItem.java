package com.setvect.bokslmusic.vo.music;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 앨범 정보
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAD_PLAY_ITEM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayItem {
	/** 앨범일련번호 */
	@Id
	@Column(name = "PLAY_ITEM_SEQ")
	private int playItemSeq;

	/** 파일 아이디 */
	@Column(name = "MUSIC_ID")
	private String musicId;

	/** 앨범일련번호 */
	@Column(name = "ALBUM_SEQ")
	private int albumSeq;

	/** 정렬 순서 */
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
