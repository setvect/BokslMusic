package com.setvect.bokslmusic.vo.music;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ���� ���� ��� 
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAA_MUSIC_DIRECTORY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MusicDirectory {
	/** ���� ���� �˻� ���۰�� ��� - OS ��� */
	@Id
	@Column(name = "BASE_PATH")
	private String basePath;
	/** ����������ȭ �ð� */
	@Column(name = "SYNC_DATE")
	private Date syncDate;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}
}
