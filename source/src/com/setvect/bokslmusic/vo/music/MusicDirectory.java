package com.setvect.bokslmusic.vo.music;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 음원 저장 경로
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBAA_MUSIC_DIRECTORY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MusicDirectory {
	/** 음원 파일 검색 시작경로 경로 - OS 경로 */
	@Id
	@Column(name = "BASE_PATH")
	private String basePath;
	/** 마지막동기화 시간 */
	@Column(name = "SYNC_DATE")
	private Date syncDate;

	/**
	 * 음원 파일 검색 시작경로 경로 - OS 경로
	 * 
	 * @return 경로
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 음원 파일 검색 시작경로 경로 - OS 경로
	 * 
	 * @param basePath
	 *            경로
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * 음악 최상위 경로
	 * 
	 * @return 음악 최상위 경로
	 */
	public File getPath() {
		return new File(basePath);
	}

	/**
	 * 마지막동기화 시간
	 * 
	 * @return 마지막동기화 시간
	 */
	public Date getSyncDate() {
		return syncDate;
	}

	/**
	 * 마지막동기화 시간
	 * 
	 * @param syncDate
	 *            마지막동기화 시간
	 */
	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}
}
