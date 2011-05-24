package com.setvect.bokslmusic.ui.shared.model;

public class FolderModel extends AlbumArticleModel {

	/** */
	private static final long serialVersionUID = -1541811484537364972L;

	protected FolderModel() {
	}

	public FolderModel(String name, int albumSeq) {
		super("", name, 0, "", 0, albumSeq);
	}

	public boolean equals(Object o) {
		return (this == o);
	}
}
