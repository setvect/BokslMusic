package com.setvect.bokslmusic.db.h2;

import java.net.URL;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.DBInitializer;
import com.setvect.common.db.TableCreateInfo;
import com.setvect.common.log.LogPrinter;

/**
 * H2 DB 초기화 하는 것과 같음.
 * 
 * @version $Id: H2DBInitializer.java 112 2010-09-23 20:08:52Z setvect@naver.com $
 */
@Service(value = "db.initializer")
public class H2DBInitializer extends DBInitializer {

	@Autowired
	private SessionFactory sessionFactory;

	/** 사용자 테이블을 의미하는 타입 값 */
	private static final String DEFAULT_TABLE_TYPE = "TABLE";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ipms.sfj.db.DBInitializer#init(java.io.File)
	 */
	@Override
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ipms.sfj.db.DBInitializer#makeTable()
	 */
	public void makeTable() {

		URL script = this.getClass().getResource("/schma/db-script.xml");
		List<TableCreateInfo> tableCreate = tableScript(script);

		for (TableCreateInfo t : tableCreate) {
			createTable(t);
		}
	}

	/**
	 * 테이블과 테이블에 관련된 데이터 및 제약조건 등록<br>
	 * 만약 이전에 테이블이 등록되어 있으면 등록 하지 않음
	 * 
	 * @param tableInfo
	 *            테이블 생성 정보
	 */
	private void createTable(TableCreateInfo tableInfo) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session
				.createSQLQuery("SELECT table_name FROM INFORMATION_SCHEMA.TABLES where table_name = ? and table_type = ?");
		query.setParameter(0, tableInfo.getTableName());
		query.setParameter(1, DEFAULT_TABLE_TYPE);

		@SuppressWarnings("rawtypes")
		List resultTable = query.list();

		if (resultTable.size() != 0) {
			// 테이블 존재
			return;
		}

		query = session.createSQLQuery(tableInfo.getScript());
		query.executeUpdate();

		// 기본데이터 및 제약조건
		String[] qs = tableInfo.getQuerise();
		for (String q : qs) {
			query = session.createSQLQuery(q);
			query.executeUpdate();
		}
		LogPrinter.out.info(tableInfo.getTableName() + " table created");
	}
}
