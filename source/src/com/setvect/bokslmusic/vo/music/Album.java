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
@Table(name = "TBAC_ALBUM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Album {
	/** �ٹ��Ϸù�ȣ */
	@Id
	@Column(name = "ALBUM_SEQ")
	private int albumSeq;

	/** �ٹ� �̸� */
	@Column(name = "NAME")
	private String name;

	public int getAlbumSeq() {
		return albumSeq;
	}

	public void setAlbumSeq(int albumSeq) {
		this.albumSeq = albumSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
