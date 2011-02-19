package com.setvect.bokslmusic.service.code;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.CodeDao;
import com.setvect.bokslmusic.vo.code.Code;
import com.setvect.common.util.GenericPage;

/**
 * �ڵ� ���� ���
 * 
 * @version $Id$
 */
@Service(value = "CodeService")
public class CodeService {

	@Autowired
	private CodeDao codeDao;

	/**
	 * @param mainCode
	 * @param subCode
	 * @return
	 * @see com.saltlux.pms.db.CodeDao#getCode(java.lang.String, java.lang.String)
	 */
	public Code getCode(String mainCode, String subCode) {
		return codeDao.getCode(mainCode, subCode);
	}

	/**
	 * @param mainCode
	 * @param subCode
	 * @return �ڵ尪
	 */
	public String getCodeValue(String mainCode, String subCode) {
		Code c = codeDao.getCode(mainCode, subCode);
		if (c == null) {
			return null;
		}
		return c.getValue();
	}

	/**
	 * @param pageCondition
	 * @return
	 * @see com.saltlux.pms.db.CodeDao#getCodePagingList(com.saltlux.pms.service.code.CodeSearch)
	 */
	public GenericPage<Code> getCodePagingList(CodeSearch pageCondition) {
		return codeDao.getCodePagingList(pageCondition);
	}

	/**
	 * @param mainCode
	 *            ���� �ڵ�
	 * @return �ش� �����ڵ忡 ���� ���
	 */
	public GenericPage<Code> getCodePagingList(String mainCode) {
		CodeSearch codeSearch = new CodeSearch(1);
		codeSearch.setPagePerItemCount(Integer.MAX_VALUE);
		codeSearch.setSearchMainCode(mainCode);
		return getCodePagingList(codeSearch);
	}

	/**
	 * @param mainCode
	 *            ����
	 * @return �ش� �����ڵ忡 ���� ���
	 */
	public Collection<Code> getCodeList(String mainCode) {
		GenericPage<Code> l = getCodePagingList(mainCode);
		return l.getList();
	}

	/**
	 * ���� �ڵ�� ���� map���� ����� ��
	 * 
	 * @param mainCode
	 * @return
	 */
	public Map<String, String> getCodeMap(String mainCode) {
		Collection<Code> list = getCodeList(mainCode);
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Code c : list) {
			map.put(c.getSubCode(), c.getValue());
		}
		return map;
	}

	/**
	 * @param code
	 * @see com.saltlux.pms.db.CodeDao#createCode(com.saltlux.pms.vo.code.Code)
	 */
	public void createCode(Code code) {
		codeDao.createCode(code);
	}

	/**
	 * @param code
	 * @see com.saltlux.pms.db.CodeDao#updateCode(com.saltlux.pms.vo.code.Code)
	 */
	public void updateCode(Code code) {
		codeDao.updateCode(code);
	}

	/**
	 * @param mainCode
	 * @param subCode
	 * @see com.saltlux.pms.db.CodeDao#removeCode(java.lang.String, java.lang.String)
	 */
	public void removeCode(String mainCode, String subCode) {
		codeDao.removeCode(mainCode, subCode);
	}

}
