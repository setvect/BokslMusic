package com.setvect.bokslmusic.db;

import com.setvect.bokslmusic.service.code.CodeSearch;
import com.setvect.bokslmusic.vo.code.Code;
import com.setvect.common.util.GenericPage;

/**
 * 코드 관리 DAO
 * 
 * @version $Id$
 */
public interface CodeDao {
	/**
	 * @param mainCode
	 *            메인 코드
	 * @param subCode
	 *            서브 코드
	 * @return 해당 코드 정보
	 */
	public Code getCode(String mainCode, String subCode);

	/**
	 * @param pageCondition
	 * @return 코드 검색
	 */
	public GenericPage<Code> getCodePagingList(CodeSearch pageCondition);

	/**
	 * 코드 등록
	 * 
	 * @param code
	 */
	public void createCode(Code code);

	/**
	 * 코드 수정
	 * 
	 * @param code
	 */
	public void updateCode(Code code);

	/**
	 * 코드 삭제
	 * 
	 * @param mainCode
	 *            메인 코드
	 * @param subCode
	 *            서브 코드
	 */
	public void removeCode(String mainCode, String subCode);

}
