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
 * H2 DB �ʱ�ȭ �ϴ� �Ͱ� ����.
 * 
 * @version $Id: H2DBInitializer.java 112 2010-09-23 20:08:52Z setvect@naver.com $
 */
@Service(value = "db.initializer")
public class H2DBInitializer extends DBInitializer {

	@Autowired
	private SessionFactory sessionFactory;

	/** ����� ���̺��� �ǹ��ϴ� Ÿ�� �� */
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
	 * ���̺�� ���̺� ���õ� ������ �� �������� ���<br>
	 * ���� ������ ���̺��� ��ϵǾ� ������ ��� ���� ����
	 * 
	 * @param tableInfo
	 *            ���̺� ���� ����
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
			// ���̺� ����
			return;
		}

		query = session.createSQLQuery(tableInfo.getScript());
		query.executeUpdate();

		// �⺻������ �� ��������
		String[] qs = tableInfo.getQuerise();
		for (String q : qs) {
			query = session.createSQLQuery(q);
			query.executeUpdate();
		}
		LogPrinter.out.info(tableInfo.getTableName() + " table created");
	}
}
