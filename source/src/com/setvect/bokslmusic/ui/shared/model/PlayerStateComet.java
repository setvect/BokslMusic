package com.setvect.bokslmusic.ui.shared.model;

import java.io.Serializable;

import javazoom.jlgui.basicplayer.BasicPlayerEvent;

/**
 * 재생 플레이어 상태 정보 전달
 */
public class PlayerStateComet implements Serializable {
	/** */
	private static final long serialVersionUID = -1436579954532570966L;

	/**
	 * 상태 코드 정보
	 * 
	 * @see BasicPlayerEvent
	 */
	private int code;

	private int position;

	// deserialize 하기 위해 기본 생성자 필요
	public PlayerStateComet() {

	}

	public PlayerStateComet(int code, int position) {
		this.code = code;
		this.position = position;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PlayerStateComet [code=" + code + ", position=" + position + "]";
	}

}
