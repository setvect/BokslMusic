package com.setvect.bokslmusic.ui.client;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

public class MusicDirectoryModel extends BaseModel {

	/** */
	private static final long serialVersionUID = -5059698192047179613L;

	public MusicDirectoryModel(String path, Date syncDate) {
		set("basePath", path);
		set("syncDate", syncDate);
	}

	public String getBasePath() {
		return (String) get("basePath");
	}

	public void setBasePath(String basePath) {
		set("basePath", basePath);
	}

	public String getSyncDate() {
		return (String) get("syncDate");
	}

	public void setSyncDate(String basePath) {
		set("syncDate", basePath);
	}

	public String toString() {
		return getBasePath();
	}
}
