package com.setvect.bokslmusic.vo.code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 코드 정보
 * 
 * @version $Id$
 */
@Entity
@Table(name = "TBZA_CODE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Code {
	/** 최상단 코드 항목 */
	public static final String MAIN_CODE = "ROOT";

	@Id
	private CodeKey key = new CodeKey();

	/** 코드 값 */
	@Column(name = "VALUE")
	private String value;

	/** 상위코드내 순서 */
	@Column(name = "ORDER_NO")
	private int orderNo;

	/**
	 * @return the mainCode
	 */
	public String getMainCode() {
		return key.getMainCode();
	}

	/**
	 * @param mainCode
	 *            the mainCode to set
	 */
	public void setMainCode(String mainCode) {
		key.setMainCode(mainCode);
	}

	/**
	 * @return the subCode
	 */
	public String getSubCode() {
		return key.getSubCode();
	}

	/**
	 * @param subCode
	 *            the subCode to set
	 */
	public void setSubCode(String subCode) {
		key.setSubCode(subCode);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the orderNo
	 */
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the key
	 */
	public CodeKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(CodeKey key) {
		this.key = key;
	}
}
