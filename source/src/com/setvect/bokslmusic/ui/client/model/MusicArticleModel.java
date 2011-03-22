package com.setvect.bokslmusic.ui.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * 등록된 음악 항목
 */
public class MusicArticleModel extends BaseModel {

	/** */
	private static final long serialVersionUID = -1991638193138041465L;

	public MusicArticleModel() {
	}

	public MusicArticleModel(String name, int runningTime, String path) {
		set("name", name);
		set("runningTime", runningTime);
		set("path", path);
	}

	public String getPath() {
		return get("path");
	}

	public void setPath(String path) {
		set("path", path);
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
