package com.setvect.bokslmusic.ui.shared.model;

/**
 * 앨범에 등록된 음악 항목
 */
public class AlbumArticleModel extends MusicArticleModel implements Comparable<AlbumArticleModel> {

	/** */
	private static final long serialVersionUID = 7754536465135395260L;

	// Serialize를 하기 위해 기본 생성자 필요
	public AlbumArticleModel() {
	}

	/**
	 * @param id
	 *            앨범이름
	 * @param name
	 *            음원 이름
	 * @param runningTime
	 *            재생 시간
	 * @param albumNo
	 *            일련번호
	 */
	public AlbumArticleModel(String id, String name, int runningTime, int orderNo, int albumNo) {
		super(id, name, runningTime, "");
		set("orderNo", orderNo);
		set("albumNo", albumNo);
	}

	public int getOrderNo() {
		return (Integer) get("orderNo");
	}

	public int getAlbumNo() {
		return (Integer) get("albumNo");
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
