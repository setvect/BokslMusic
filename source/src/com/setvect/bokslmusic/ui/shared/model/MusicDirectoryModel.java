package com.setvect.bokslmusic.ui.shared.model;

public class MusicDirectoryModel extends MusicArticleModel {

	/** */
	private static final long serialVersionUID = -4546026824498210580L;

	protected MusicDirectoryModel() {
	}

	public MusicDirectoryModel(String name, String path) {
		set("name", name);
		set("path", path);
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
}
