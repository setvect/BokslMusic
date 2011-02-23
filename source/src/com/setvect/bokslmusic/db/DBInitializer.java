package com.setvect.bokslmusic.db;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.setvect.common.db.TableCreateInfo;
import com.setvect.common.xml.XMLParser;

/**
 * ������ ���̽� �ʱ�ȭ �� Ŀ�ؼ� ��ü�� ���� �� ȸ���� ����� $Id: DBInitializer.java 33 2010-07-30 10:14:49Z setvect@naver.com $
 */
public abstract class DBInitializer {
	/**
	 * DBMS �ʱ�ȭ<br>
	 * 
	 * DB ���� �ʱ�ȭ. �����ͺ��̽� ������ ��ġ ���� ��
	 * 
	 */
	public abstract void init();

	/**
	 * ���̺� ��Ű���� �˻��Ͽ� ��Ű���� ������ <br>
	 * xml ���Ͽ� ����� ������ �̿��Ͽ� ��Ű���� �⺻ ������ �� ���� ������ �����
	 */
	public abstract void makeTable();

	/**
	 * @param dbScript
	 *            ���̺� ���� ��ũ��Ʈ�� ������ �ִ� ����
	 * @return ���̺� ���� ��ũ��Ʈ
	 */
	public List<TableCreateInfo> tableScript(URL dbScript) {

		// XML ���Ͽ��� ���� ���̺� ���� ��ũ��Ʈ�� ������
		URL schema = this.getClass().getResource("/config/db-script.xsd");

		Document d = null;
		List<TableCreateInfo> tableCreate = new ArrayList<TableCreateInfo>();

		try {
			d = XMLParser.parsing(dbScript, schema);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			// ���� Ÿ��ũ ����
			NodeList tableList = (NodeList) xpath.evaluate("/dbScript/table", d, XPathConstants.NODESET);
			for (int tableIdx = 0; tableIdx < tableList.getLength(); tableIdx++) {
				Element table = (Element) tableList.item(tableIdx);

				TableCreateInfo tableInfo = new TableCreateInfo(table.getAttribute("name"));

				Element eleDescription = (Element) xpath.evaluate("creation", table, XPathConstants.NODE);
				tableInfo.setScript(eleDescription.getTextContent());

				// ���� Task ���� ���� Task
				NodeList queryList = (NodeList) xpath.evaluate("defaultValue/query", table, XPathConstants.NODESET);

				for (int queryIdx = 0; queryIdx < queryList.getLength(); queryIdx++) {
					Element query = (Element) queryList.item(queryIdx);
					tableInfo.addQuery(query.getTextContent());
				}
				tableCreate.add(tableInfo);
			}
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return tableCreate;

	}

}