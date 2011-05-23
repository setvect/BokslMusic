package com.setvect.bokslmusic.ui.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * 앨범에 등록된 음악 항목
 */
public class AlbumArticleModel extends BaseModel implements Comparable<AlbumArticleModel> {

	/** */
	private static final long serialVersionUID = -6422740976331298816L;

	// Serialize를 하기 위해 기본 생성자 필요
	public AlbumArticleModel() {
	}

	/**
	 * @param name
	 *            음원 이름
	 * @param runningTime
	 *            재생 시간
	 * @param id
	 *            앨범이름
	 */
	public AlbumArticleModel(String name, int runningTime, String id, int orderNo) {
		set("name", name);
		set("runningTime", runningTime);
		set("id", id);
		set("orderNo", orderNo);
	}

	public String getId() {
		return get("id");
	}

	public void setId(String id) {
		set("id", id);
	}

	public String getName() {
		return (String) get("name");
	}

	public int getRunningTime() {
		int runningTime = (Integer) get("runningTime");
		return runningTime;
	}

	public int getOrderNo() {
		return (Integer) get("orderNo");
	}

	public String toString() {
		return getName();
	}

	public int compareTo(AlbumArticleModel o) {
		int order = getOrderNo();
		if (order > o.getOrderNo()) {
			return 1;
		}
		else if (order < o.getOrderNo()) {
			return -1;
		}
		else {
			return getName().compareTo(o.getName());
		}
	}
}
