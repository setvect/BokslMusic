package com.setvect.bokslmusic.vo.music;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 음악 정보
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAA_MUSIC_PATH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MusicPath {
	/** 음원 파일 검색 시작경로 경로 - OS 경로 */
	@Id
	@Column(name = "BASE_PATH")
	private String basePath;
	/** 마지막동기화 시간 */
	@Column(name = "SYNC_DATE")
	private String syncDate;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}
}
