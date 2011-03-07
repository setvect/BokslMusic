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
 * 데이터 베이스 초기화 및 커넥션 객체를 제공 및 회수를 담당함 $Id: DBInitializer.java 33 2010-07-30 10:14:49Z setvect@naver.com $
 */
public abstract class DBInitializer {
	/**
	 * DBMS 초기화<br>
	 * 
	 * DB 설정 초기화. 데이터베이스 생성될 위치 지정 등
	 * 
	 */
	public abstract void init();

	/**
	 * 테이블 스키마를 검사하여 스키마가 없으면 <br>
	 * xml 파일에 저장된 정보를 이용하여 스키마와 기본 데이터 및 제약 조건을 등록함
	 */
	public abstract void makeTable();

	/**
	 * @param dbScript
	 *            테이블 생성 스크립트을 가지고 있는 파일
	 * @return 테이블 생성 스크립트
	 */
	public List<TableCreateInfo> tableScript(URL dbScript) {

		// XML 파일에서 만들 테이블 생성 스크립트를 가저옴
		URL schema = this.getClass().getResource("/config/db-script.xsd");

		Document d = null;
		List<TableCreateInfo> tableCreate = new ArrayList<TableCreateInfo>();

		try {
			d = XMLParser.parsing(dbScript, schema);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			// 메인 타스크 정보
			NodeList tableList = (NodeList) xpath.evaluate("/dbScript/table", d, XPathConstants.NODESET);
			for (int tableIdx = 0; tableIdx < tableList.getLength(); tableIdx++) {
				Element table = (Element) tableList.item(tableIdx);

				TableCreateInfo tableInfo = new TableCreateInfo(table.getAttribute("name"));

				Element eleDescription = (Element) xpath.evaluate("creation", table, XPathConstants.NODE);
				tableInfo.setScript(eleDescription.getTextContent());

				// 메인 Task 하위 서브 Task
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
