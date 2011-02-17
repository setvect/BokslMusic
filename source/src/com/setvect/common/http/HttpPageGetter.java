package com.setvect.common.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.setvect.common.util.FileUtil;
import com.setvect.common.util.StringUtilAd;

/**
 * 웹페이지 주도에 대한 내용을 가져옴
 * 
 * @version $Id: HttpPageGetter.java 131 2010-11-02 06:21:57Z setvect@naver.com
 *          $
 */
public class HttpPageGetter {

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	/** 기본 캐릭터 셋(데이터 받는 상황에서) */
	private static final String DEFAULT_BODY_CHARSET = "utf-8";

	/** URL 주소 */
	private String url;

	/** 파라미터 정보 */
	private Map<String, String> parameter = new HashMap<String, String>();

	/** request property */
	private Map<String, String> requestProperty = new HashMap<String, String>();

	/** 본문 인코드 캐릭터 셋 */
	private String bodyCharset;

	/** url 파라미터 인코드 캐릭터 셋 */
	private String urlCharset = "euc-kr";

	/** URL 커넥션 */
	private URL urlinfo;

	/** URL 오픈 커넥션 */
	private HttpURLConnection conn;

	/** 연결 제한 시간, 기본값 0, 단위 밀리세컨드 */
	private int conntionTimeout = 0;

	/**
	 * @see #METHOD_GET
	 * @see #METHOD_POST
	 */
	private String method = METHOD_GET;
	/** method가 POST인 경우에만 사용 가능 */
	private String sendBody;

	/**
	 * @param url
	 *            URL 주소 <br>
	 *            ex)http://www.naver.com/index.html
	 */
	public HttpPageGetter(String url) {
		this.url = url;

	}

	/**
	 * @param url
	 *            URL 주소 <br>
	 *            ex)http://www.naver.com/index.html
	 * @param urlCharset
	 *            url 파라미터 캐릭터 셋
	 */
	public HttpPageGetter(String url, String urlCharset) {
		this.url = url;
		this.urlCharset = urlCharset;

	}

	/**
	 * 일반적으로 UrlPageInfo(String, String)를 사용<br>
	 * 상식적으로 데이터를 받을때 캐릭터넷을 정의할 필요는 없다.<br>
	 * http 해더를 분석해서 받는 url에 charset를 알아야 된다. <br>
	 * 그러나 getContentType()에서 charset정보를 주지 않는 경우가 있음.<br>
	 * 이때는 호출 하는 쪽에서 처리
	 * 
	 * @param url
	 *            URL 주소 <br>
	 *            ex)http://www.google.com/page/main.asp
	 * @param bodyCharset
	 *            본문 캐릭터 셋
	 * @param urlCharset
	 *            url 파라미터 캐릭터 셋
	 * 
	 */
	public HttpPageGetter(String url, String bodyCharset, String urlCharset) {
		this.url = url;
		this.bodyCharset = bodyCharset;
		this.urlCharset = urlCharset;

	}

	/**
	 * @param method
	 *            the method to set
	 * @see #METHOD_GET
	 * @see #METHOD_POST
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 
	 */
	private void conn() {
		// 이미 한번 초기화 되어 있으면 다시 하지 않음
		if (urlinfo != null) {
			return;
		}
		try {
			urlinfo = new URL(getUrl());
			conn = (HttpURLConnection) urlinfo.openConnection();
			conn.setDoOutput(true);

			Set<String> property = requestProperty.keySet();
			for (String key : property) {
				conn.addRequestProperty(key, requestProperty.get(key));
			}

			conn.setConnectTimeout(conntionTimeout);
			conn.setRequestMethod(method);

			if (method.equals(METHOD_POST)) {
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				String params = getParameterString();
				OutputStream os = null;
				try {
					os = conn.getOutputStream();
					os.write(params.getBytes());
					if (StringUtilAd.isNotEmpty(sendBody)) {
						os.write(sendBody.getBytes());
					}
					os.flush();
					os.close();
				}
				finally {
					if (os != null) {
						os.close();
					}
				}
			}

			// 본문 캐릭터 셋이 없을 경우 해더 분석
			if (bodyCharset == null) {
				// application/x-msdownload; charset=utf-8 이런식으로 옮
				String t = conn.getContentType();
				if (t != null && t.contains("charset=")) {
					int st = t.indexOf("charset=") + "charset=".length();
					int ed = t.indexOf(";", st);
					if (ed == -1) {
						ed = t.length();
					}
					bodyCharset = t.substring(st, ed);
				}
				else {
					bodyCharset = DEFAULT_BODY_CHARSET;
				}

			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param charset
	 *            캐릭터 셋
	 * @return 본문 페이지 내용
	 * @throws IOException
	 */
	public String getBody() throws IOException {
		conn();
		InputStream in = null;
		String body = null;
		try {
			in = conn.getInputStream();
			byte buf[] = new byte[1024];
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while (true) {
				int n = in.read(buf);
				if (n == -1)
					break;
				if (n != 0) {
					bout.write(buf, 0, n);
				}
			}
			bout.flush();
			body = new String(bout.toByteArray(), bodyCharset);
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
		return body;
	}

	/**
	 * 본문 내용을 지정된 이름으로 저장 한다. <br>
	 * 만약 지정된 곳에 이전 파일이 있으면 덮어 쒸운다.
	 * 
	 * @param saveFileName
	 *            파일 경로를 포함한 이름
	 * @throws IOException
	 */
	public void saveBody(String saveFileName) throws IOException {
		conn();
		InputStream in = conn.getInputStream();

		// 파일이 존재 하면 삭제한다.
		if (FileUtil.isExists(saveFileName)) {
			FileUtil.delFile(saveFileName);
		}
		File saveFile = new File(saveFileName);
		byte buf[] = new byte[1024];
		FileOutputStream ofp = new FileOutputStream(saveFile);

		while (true) {
			int n = in.read(buf);
			if (n == -1)
				break;
			if (n != 0) {
				ofp.write(buf, 0, n);
			}
		}
		ofp.flush();
		ofp.close();
		in.close();
	}

	/**
	 * 파일 저장. 중복 파일이 있을 경우 덮어 쒸우지 않고 새로운 이름으로 저장
	 * 
	 * @param savePath
	 *            파일 저장 경로
	 * @return 저장된 파일, 파일 저장을 실패하면 null
	 * @throws IOException
	 */
	public File saveFileDownload(String savePath) throws IOException {
		return saveFileDownload(savePath, null);
	}

	/**
	 * 파일 저장. 중복 파일이 있을 경우 덮어 쒸우지 않고 새로운 이름으로 저장
	 * 
	 * @param savePath
	 *            파일 저장 경로
	 * @param fileEnd
	 *            저장된 파일 뒤에 붙는 구분자,
	 * @return 파일 저장을 실패하면 null
	 * @throws IOException
	 */
	public File saveFileDownload(String savePath, String fileEnd) throws IOException {
		conn();
		String fileName = getFilename();

		int dotPos = fileName.lastIndexOf(".");
		String name = dotPos == -1 ? fileName : fileName.substring(0, dotPos);
		name += StringUtilAd.null2str(fileEnd, "");
		String ext = FileUtil.getExt(fileName);
		fileName = name + ext;
		File saveFile = new File(savePath, fileName);
		int c = 1;
		while (saveFile.exists()) {
			fileName = name + "_" + c + ext;
			saveFile = new File(savePath, fileName);
			c++;
		}
		InputStream in = conn.getInputStream();
		byte buf[] = new byte[1024];
		FileOutputStream ofp = new FileOutputStream(saveFile);
		while (true) {
			int n = in.read(buf);
			if (n == -1)
				break;
			if (n != 0) {
				ofp.write(buf, 0, n);
			}
		}
		ofp.flush();
		ofp.close();
		in.close();
		return saveFile;
	}

	/**
	 * @return 저장할 첨부파일 이름
	 * @throws IOException
	 */
	public String getFilename() throws IOException {
		conn();
		URLConnection conn = urlinfo.openConnection();

		// Content-Disposition: attachment; filename=%5bI10-01%5dPRM.pdf;
		String fi = conn.getHeaderField("Content-Disposition");
		String fileName;
		if (fi == null) {
			fileName = urlFileName(urlinfo.getPath());
		}
		else {
			fileName = dispositionFileName(fi);
		}
		return fileName;
	}

	/**
	 * url 마지막 정보를 파일명으로 사용
	 * 
	 * @param url
	 *            경로를 이용하여 파일명 추출
	 * @return url 경로 상에 파일명
	 */
	private String urlFileName(String url) {
		int stp = url.lastIndexOf("/");
		int enp = url.indexOf("?", stp);
		if (enp == -1) {
			enp = url.length();
		}
		return url.substring(stp, enp);
	}

	/**
	 * Content-Disposition 형태로 파일 다운로드 할 경우 파일 명 추출
	 * 
	 * @param fi
	 *            Content-Disposition 해더의 값
	 * @return 파일명
	 * @throws UnsupportedEncodingException
	 */
	private String dispositionFileName(String fi) throws UnsupportedEncodingException {
		// 파일 이름 시작~종료 시점 구하기
		int fileStartPos = fi.indexOf("filename=");
		int fileEndPos = fi.indexOf(";", fileStartPos);

		// Content-Disposition=[attachment;filename=SW stack
		// %ED%86%B5%ED%95%A9%EB%B8%8C%EB%A1%9C%EC%85%94.pdf],
		// 이런식으로 저장 되는 경우도 있다. 그래서 ;세미콜론이 없을 경우도 발생
		if (fileEndPos == -1) {
			fileEndPos = fi.length();
		}

		String fileName;
		if (fileStartPos != -1) {
			fileStartPos += "filename=".length();
			fileName = URLDecoder.decode(fi.substring(fileStartPos, fileEndPos), bodyCharset);
		}
		else {
			fileName = "fileDownloadAutoName";
		}
		return fileName;
	}

	/**
	 * @param ilePathschemaFilePath
	 *            XML 스키마 정보
	 * @return 문서 내용이 XML 파일 경우 최상위 Document로드를 리턴
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Document getBodyXml(String ilePathschemaFilePath) throws IOException, SAXException,
			ParserConfigurationException {

		File schemaFile = new File(ilePathschemaFilePath);
		Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaFile);

		return getBodyXml(schema);

	}

	/**
	 * @param schemaUrl
	 *            URL 상의 스키마 정보
	 * @return 문서 내용이 XML 파일 경우 최상위 Document로드를 리턴
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Document getBodyXml(URL schemaUrl) throws IOException, SAXException, ParserConfigurationException {

		Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl);
		return getBodyXml(schema);
	}

	/**
	 * @param schema
	 *            XML 스키마
	 * @return 문서 내용이 XML 파일 경우 최상위 Document로드를 리턴
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private Document getBodyXml(Schema schema) throws ParserConfigurationException, IOException, SAXException {

		BufferedInputStream bis = null;
		Document document = null;
		InputStream in = null;
		try {

			// DOM 파서 생성
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setSchema(schema);
			DocumentBuilder builder = factory.newDocumentBuilder();
			String body = getBody().trim();
			in = new ByteArrayInputStream(body.getBytes(bodyCharset));
			bis = new BufferedInputStream(in);

			// XML 문서 파싱하기
			document = builder.parse(bis);
		}
		finally {
			if (bis != null) {
				bis.close();
			}
			if (in != null) {
				in.close();
			}
		}

		return document;
	}

	/**
	 * URL에 넘길 파라미터 정보를 설정
	 * 
	 * @param key
	 *            키 정보
	 * @param value
	 *            값
	 * @deprecated 네이밍이 맞지 않음 addParameter()사용
	 */
	public void setParameter(String key, String value) {
		parameter.put(key, value);
	}

	/**
	 * URL에 넘길 파라미터 정보를 설정
	 * 
	 * @param key
	 *            키 정보
	 * @param value
	 *            값
	 */
	public void addParameter(String key, String value) {
		parameter.put(key, value);
	}

	/**
	 * request property
	 * 
	 * @param key
	 * @param value
	 */
	public void addRequestProperty(String key, String value) {
		requestProperty.put(key, value);
	}

	/**
	 * request property
	 * 
	 * @param key
	 * @param value
	 */
	public void setRequestProperty(Map<String, String> reqProperty) {
		requestProperty = reqProperty;
	}

	/**
	 * @return 주소+파라미터
	 */
	public String getUrl() {
		if (method.equals(METHOD_POST)) {
			return url;
		}
		String parameterString = getParameterString();
		if (StringUtilAd.isEmpty(parameterString)) {
			return url;
		}
		else {
			return url + "?" + parameterString;
		}

	}

	/**
	 * @return 파라미터 정보를 문자열로 변경
	 */
	private String getParameterString() {
		Set<String> enum1 = parameter.keySet();
		StringBuffer buf = new StringBuffer();
		for (String key : enum1) {
			if (buf.length() != 0) {
				buf.append('&');
			}
			String value = (String) parameter.get(key);
			try {
				buf.append(URLEncoder.encode(key, urlCharset));
				buf.append('=');
				buf.append(URLEncoder.encode(value, urlCharset));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return buf.toString();
	}

	/**
	 * 기존에 설정된 파라미터 정보가 있다면 해당 메소드를 실행하면 없어진다.
	 * 
	 * @param parameter
	 *            URL 파라미터 정보
	 */
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return Returns the conntionTimeout.
	 */
	public int getConntionTimeout() {
		return conntionTimeout;
	}

	/**
	 * method가 POST인 경우에만 사용 가능
	 * 
	 * @param sendBody
	 *            서버에 전달할 데이터
	 */
	public void setSendBody(String sendBody) {
		this.sendBody = sendBody;
	}

	/**
	 * @param conntionTimeout
	 *            The conntionTimeout to set.
	 */
	public void setConntionTimeout(int conntionTimeout) {
		this.conntionTimeout = conntionTimeout;
	}

}
