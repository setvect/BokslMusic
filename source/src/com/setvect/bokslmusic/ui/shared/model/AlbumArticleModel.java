package com.setvect.bokslmusic.ui.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * 앨범에 등록된 음악 항목
 */
public class AlbumArticleModel extends BaseModel {
	/** */
	private static final long serialVersionUID = -5059698192047179613L;

	// Serialize를 하기 위해 기본 생성자 필요
	public AlbumArticleModel() {
	}

	public AlbumArticleModel(String name, int runningTime, String albumTitle) {
		set("name", name);
		set("runningTime", runningTime);
		set("albumTitle", albumTitle);
	}

	public String getAlbumTitle() {
		return get("albumTitle");
	}

	public void setAlbumTitle(String albumTitle) {
		set("albumTitle", albumTitle);
	}

	public String getName() {
		return (String) get("name");
	}

	public int getRunningTime() {
		int runningTime = (Integer) get("runningTime");
		return runningTime;
	}

	public String toString() {
		return getName();
	}
}
