package com.setvect.bokslmusic.ui.shared.model;


public class MusicDirectoryModel extends MusicArticleModel {
	/**
	 * 디렉토리 유형
	 */
	public static final String TYPE_BASE_DIR = "BASE_DIR";
	public static final String TYPE_DIR = "DIR";

	/** */
	private static final long serialVersionUID = -4546026824498210580L;

	public MusicDirectoryModel() {
	}

	/**
	 * @param name
	 *            디렉토리 이름
	 * @param path
	 *            디렉토리 경로
	 * @param type
	 *            디렉토리 유형
	 */
	public MusicDirectoryModel(String name, String path, String type) {
		set("name", name);
		set("path", path);
		setId(path);
		set("type", type);
	}

	public String getName() {
		return get("name");
	}

	public String getPath() {
		return get("path");
	}

	public boolean equals(Object o) {
		return (this == o);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return get("type");
	}
}
