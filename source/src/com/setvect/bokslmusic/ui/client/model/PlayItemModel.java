package com.setvect.bokslmusic.ui.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class PlayItemModel extends BaseModel {

	/** */
	private static final long serialVersionUID = -5059698192047179613L;

	public PlayItemModel(String name, int runningTime) {
		set("name", name);
		set("runningTime", runningTime);
	}

	public String getName() {
		return (String) get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getRunningTime() {
		return (String) get("runningTime");
	}

	public void setRunningTime(String runningTime) {
		set("runningTime", runningTime);
	}
}
