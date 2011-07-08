package com.setvect.bokslmusic.ui.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class AlbumModel extends BaseModel {

	/** */
	private static final long serialVersionUID = -1541811484537364972L;

	protected AlbumModel() {
	}

	public AlbumModel(String name, int albumSeq) {
		set("name", name);
		set("albumSeq", albumSeq);
	}

	public String getName() {
		return get("name");
	}

	public int getAlbumNo() {
		return (Integer) get("albumSeq");
	}

	public boolean equals(Object o) {
		return (this == o);
	}
}
