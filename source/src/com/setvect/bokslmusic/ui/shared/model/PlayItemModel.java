package com.setvect.bokslmusic.ui.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class PlayItemModel extends BaseModel {

	/** */
	private static final long serialVersionUID = 1981226970305339196L;

	// Serialize를 하기 위해 기본 생성자 필요
	public PlayItemModel() {

	}

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
