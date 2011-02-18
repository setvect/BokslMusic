package com.zuch.music.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.setvect.common.http.HttpPageGetter;

/**
 * �˽� ���� ������ �����Ͽ� ���� ���縦 ������
 * 
 * @version $Id$
 */
public final class MusicInfoGetter {
	private static final int HEADER_LENGTH = 160 * 1024;
	private String md5Str;
	/** ���縦 �м��� XML */
	private String resultXml;

	/** ���� �м� */
	private LyricParseHandler lyricParse = new LyricParseHandler();

	/**
	 * @param audioFile
	 *            ���� ����
	 */
	public MusicInfoGetter(File audioFile) {
		this.md5Str = Md5Util.getMD5Checksum(audioFile, 0, HEADER_LENGTH);
		downloadLyric();
	}

	/**
	 * @param md5
	 *            ���� ���� MD5
	 */
	public MusicInfoGetter(String md5) {
		this.md5Str = md5;
		downloadLyric();
	}

	/**
	 * @return ���� ���� Ű MD5
	 */
	public String getMd5() {
		return md5Str;
	}

	/**
	 * @return ����, ������ null
	 */
	public String getTitle() {
		return lyricParse.getTitle();
	}

	/**
	 * @return ��Ʈ����Ʈ, ������ null
	 */
	public String getArtist() {
		return lyricParse.getArtist();
	}

	/**
	 * @return ����, ������ null
	 */
	public String getLyric() {
		return lyricParse.getLyric().replace("<br>", "\n");
	}

	/**
	 * Download lyric from Alsong lyric server
	 */
	private void downloadLyric() {
		// Create request content
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\"");
		sb.append("	xmlns:SOAP-ENC=\"http://www.w3.org/2003/05/soap-encoding\"");
		sb.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		sb.append("	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append("	xmlns:ns2=\"ALSongWebServer/Service1Soap\"");
		sb.append("	xmlns:ns1=\"ALSongWebServer\" xmlns:ns3=\"ALSongWebServer/Service1Soap12\">");
		sb.append("<SOAP-ENV:Body>");
		sb.append("<ns1:GetLyric5>");
		sb.append("<ns1:stQuery>");
		sb.append("<ns1:strChecksum>").append(md5Str).append("</ns1:strChecksum>");
		sb.append("<ns1:strVersion>1.93</ns1:strVersion>");
		sb.append("<ns1:strMACAddress>005056C00001</ns1:strMACAddress>");
		sb.append("<ns1:strIPAddress>192.168.1.2</ns1:strIPAddress>");
		sb.append("</ns1:stQuery>");
		sb.append("</ns1:GetLyric5>");
		sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");
		String retValue = null;

		HttpPageGetter httpGetter = new HttpPageGetter("http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx");
		httpGetter.setMethod(HttpPageGetter.METHOD_POST);
		httpGetter.addRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		httpGetter.addRequestProperty("User-Agent", "gSOAP/2.7");
		httpGetter.addRequestProperty("Cache-Control", "no-cache");

		httpGetter.setSendBody(sb.toString());
		try {
			retValue = httpGetter.getBody();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		resultXml = retValue;

		try {
			parsing();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ������ ��Ÿ ������ �Ľ�
	 * 
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private void parsing() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		// ByteArrayInputStream�� close()���ص� ��.
		InputStream is = new ByteArrayInputStream(resultXml.getBytes("UTF-8"));
		saxParser.parse(is, lyricParse);
	}

	/**
	 * ������ ���� ��Ÿ XML�� �̿��Ͽ� �ʿ��� ������ �Ľ�
	 * 
	 * @version $Id$
	 */
	static class LyricParseHandler extends DefaultHandler {
		enum NodeName {
			strTitle, strArtist, strLyric
		}

		Map<NodeName, StringBuffer> parsedData = new HashMap<NodeName, StringBuffer>();

		LyricParseHandler() {
			NodeName[] nodes = NodeName.values();
			for (NodeName n : nodes) {
				parsedData.put(n, new StringBuffer());
			}
		}

		// ���� Ž�� �ε� �̸�
		private String currentExplorerNodeName;

		public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
			currentExplorerNodeName = qName;
		}

		public void characters(char buf[], int offset, int len) throws SAXException {
			NodeName node;
			try {
				node = NodeName.valueOf(currentExplorerNodeName);
			} catch (IllegalArgumentException e) {
				// ���� �ε�� ���εǴ� ���� MapŰ ����
				return;
			}

			String s = new String(buf, offset, len);
			StringBuffer value = parsedData.get(node);
			value.append(s);
		}

		public String getTitle() {
			return parsedData.get(NodeName.strTitle).toString();
		}

		public String getArtist() {
			return parsedData.get(NodeName.strArtist).toString();
		}

		public String getLyric() {
			return parsedData.get(NodeName.strLyric).toString();
		}
	}
}
