package com.setvect.bokslmusic.db;

import com.setvect.bokslmusic.service.code.CodeSearch;
import com.setvect.bokslmusic.vo.code.Code;
import com.setvect.common.util.GenericPage;

/**
 * �ڵ� ���� DAO
 * 
 * @version $Id$
 */
public interface CodeDao {
	/**
	 * @param mainCode
	 *            ���� �ڵ�
	 * @param subCode
	 *            ���� �ڵ�
	 * @return �ش� �ڵ� ����
	 */
	public Code getCode(String mainCode, String subCode);

	/**
	 * @param pageCondition
	 * @return �ڵ� �˻�
	 */
	public GenericPage<Code> getCodePagingList(CodeSearch pageCondition);

	/**
	 * �ڵ� ���
	 * 
	 * @param code
	 */
	public void createCode(Code code);

	/**
	 * �ڵ� ����
	 * 
	 * @param code
	 */
	public void updateCode(Code code);

	/**
	 * �ڵ� ����
	 * 
	 * @param mainCode
	 *            ���� �ڵ�
	 * @param subCode
	 *            ���� �ڵ�
	 */
	public void removeCode(String mainCode, String subCode);

}
