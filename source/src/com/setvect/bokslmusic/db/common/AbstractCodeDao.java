package com.setvect.bokslmusic.db.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.setvect.bokslmusic.db.CodeDao;
import com.setvect.bokslmusic.service.code.CodeSearch;
import com.setvect.bokslmusic.vo.code.Code;
import com.setvect.bokslmusic.vo.code.CodeKey;
import com.setvect.common.util.GenericPage;
import com.setvect.common.util.StringUtilAd;

/**
 * 코드 DAO
 * 
 * @version $Id$
 */
public abstract class AbstractCodeDao implements CodeDao {
	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saltlux.pms.db.CodeDao#getCode(java.lang.String,
	 * java.lang.String)
	 */
	public Code getCode(String mainCode, String subCode) {
		Session session = sessionFactory.getCurrentSession();
		return (Code) session.get(Code.class, new CodeKey(mainCode, subCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saltlux.pms.db.CodeDao#getBoardPagingList(com.saltlux.pms.service
	 * .board.CodeSearch)
	 */
	public GenericPage<Code> getCodePagingList(CodeSearch pageCondition) {
		Session session = sessionFactory.getCurrentSession();
		String whereClause = getWhereClause(pageCondition);

		String q = "select count(*) from Code " + whereClause;
		Query query = session.createQuery(q);
		int totalCount = ((Long) query.uniqueResult()).intValue();

		q = " from Code " + whereClause + " order by orderNo ";
		query = session.createQuery(q);
		query.setFirstResult(pageCondition.getStartNumber());
		query.setMaxResults(pageCondition.getPagePerItemCount());

		@SuppressWarnings("unchecked")
		List<Code> resultList = query.list();

		GenericPage<Code> resultPage = new GenericPage<Code>(resultList, pageCondition.getCurrentPage(), totalCount,
				pageCondition.getPageUnit(), pageCondition.getPagePerItemCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 * @return where 절
	 */
	private String getWhereClause(CodeSearch pageCondition) {
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("where 1 = 1");
		if (!StringUtilAd.isEmpty(pageCondition.getSearchMainCode())) {
			String mainCode = StringUtilAd.getSqlString(pageCondition.getSearchMainCode());
			whereClause.append(" and key.mainCode = " + mainCode);
		}

		if (!StringUtilAd.isEmpty(pageCondition.getSearchValue())) {
			String value = StringUtilAd.getSqlStringLike(pageCondition.getSearchValue());
			whereClause.append(" and value like " + value);
		}
		return whereClause.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saltlux.pms.db.CodeDao#createCode(com.saltlux.pms.vo.code.Code)
	 */
	public void createCode(Code code) {
		Session session = sessionFactory.getCurrentSession();
		session.save(code);
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saltlux.pms.db.CodeDao#updateCode(com.saltlux.pms.vo.code.Code)
	 */
	public void updateCode(Code code) {
		Session session = sessionFactory.getCurrentSession();
		session.update(code);
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saltlux.pms.db.CodeDao#removeCode(java.lang.String,
	 * java.lang.String)
	 */
	public void removeCode(String mainCode, String subCode) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getCode(mainCode, subCode));
		session.flush();
	}
}
