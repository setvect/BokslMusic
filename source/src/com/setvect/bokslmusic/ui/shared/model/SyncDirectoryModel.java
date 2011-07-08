package com.setvect.bokslmusic.ui.shared.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * 동기화 대상 디렉토리 목록
 */
public class SyncDirectoryModel extends BaseModel {

	/** */
	private static final long serialVersionUID = 2540853721169782220L;

	// Serialize를 하기 위해 기본 생성자 필요
	public SyncDirectoryModel() {
	}

	public SyncDirectoryModel(String path, Date syncDate) {
		set("basePath", path);
		set("syncDate", syncDate);
	}

	public String getBasePath() {
		return (String) get("basePath");
	}

	public void setBasePath(String basePath) {
		set("basePath", basePath);
	}

	public Date getSyncDate() {
		return (Date) get("syncDate");
	}

	public void setSyncDate(Date basePath) {
		set("syncDate", basePath);
	}

	public String toString() {
		return getBasePath();
	}
}
