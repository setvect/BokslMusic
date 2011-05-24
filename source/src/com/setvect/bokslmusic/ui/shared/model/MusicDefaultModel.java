package com.setvect.bokslmusic.ui.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * 등록된 음악 항목
 */
public class MusicDefaultModel extends BaseModel {

	/** */
	private static final long serialVersionUID = 55581369798985370L;

	// Serialize를 하기 위해 기본 생성자 필요
	public MusicDefaultModel() {
	}

	public MusicDefaultModel(String id, String name, int runningTime, String path) {
		set("name", name);
		set("runningTime", runningTime);
		set("path", path);
		set("id", id);
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

	public void setName(String name) {
		set("name", name);
	}

	public int getRunningTime() {
		int runningTime = (Integer) get("runningTime");
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		set("runningTime", runningTime);
	}

	public String getId() {
		return (String) get("id");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String toString() {
		return getName();
	}

	public int hashCode() {
		return getId().hashCode();
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof MusicDefaultModel)) {
			return false;
		}

		MusicDefaultModel t = (MusicDefaultModel) o;
		return getId().equals(t.getId());
	}
}
