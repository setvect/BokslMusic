package com.setvect.bokslmusic.boot;

/**
 * �� ���ø����̼ǿ��� ���ǵ ���� �ڵ�󿡼� �߻� ��Ų ����
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
