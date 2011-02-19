package com.setvect.bokslmusic.vo.code;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ���� ��� ��ƼƼ, �⺻Ű ����
 * 
 * @version $Id$
 */
@Embeddable
public class CodeKey implements Serializable {

	/** */
	private static final long serialVersionUID = -7619550780176822366L;

	/** �����ڵ� */
	@Column(name = "MAIN_CODE")
	private String mainCode;

	/** �����ڵ� */
	@Column(name = "SUB_CODE")
	private String subCode;

	public CodeKey() {
	}

	public CodeKey(String mainCode, String subCode) {
		this.mainCode = mainCode;
		this.subCode = subCode;
	}

	/**
	 * @return the mainCode
	 */
	public String getMainCode() {
		return mainCode;
	}

	/**
	 * @param mainCode
	 *            the mainCode to set
	 */
	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}

	/**
	 * @return the subCode
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * @param subCode
	 *            the subCode to set
	 */
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (o == this) {
			return true;
		}
		else if (o.getClass() == CodeKey.class) {
			CodeKey other = (CodeKey) o;
			if (other.mainCode.equals(mainCode) && other.subCode.equals(subCode)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
