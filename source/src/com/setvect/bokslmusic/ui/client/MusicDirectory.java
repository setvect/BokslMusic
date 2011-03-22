package com.setvect.bokslmusic.ui.client;

import java.io.Serializable;

/**
 * 음원 저장 경로
 * 
 * @version $Id$
 */
public class MusicDirectory implements Serializable {
	/** */
	private static final long serialVersionUID = 1006774236144835352L;
	private String basePath;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
