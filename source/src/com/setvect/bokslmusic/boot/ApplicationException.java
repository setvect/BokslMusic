package com.setvect.bokslmusic.boot;

/**
 * 본 어플리케이션에서 조건등에 의해 코드상에서 발생 시킨 예외
 * 
 * @version $Id$
 */
public class ApplicationException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 1163703870431928769L;

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
