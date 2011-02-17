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
 * �������� �ֵ��� ���� ������ ������
 * 
 * @version $Id: HttpPageGetter.java 131 2010-11-02 06:21:57Z setvect@naver.com
 *          $
 */
public class HttpPageGetter {

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	/** �⺻ ĳ���� ��(������ �޴� ��Ȳ����) */
	private static final String DEFAULT_BODY_CHARSET = "utf-8";

	/** URL �ּ� */
	private String url;

	/** �Ķ���� ���� */
	private Map<String, String> parameter = new HashMap<String, String>();

	/** request property */
	private Map<String, String> requestProperty = new HashMap<String, String>();

	/** ���� ���ڵ� ĳ���� �� */
	private String bodyCharset;

	/** url �Ķ���� ���ڵ� ĳ���� �� */
	private String urlCharset = "euc-kr";

	/** URL Ŀ�ؼ� */
	private URL urlinfo;

	/** URL ���� Ŀ�ؼ� */
	private HttpURLConnection conn;

	/** ���� ���� �ð�, �⺻�� 0, ���� �и������� */
	private int conntionTimeout = 0;

	/**
	 * @see #METHOD_GET
	 * @see #METHOD_POST
	 */
	private String method = METHOD_GET;
	/** method�� POST�� ��쿡�� ��� ���� */
	private String sendBody;

	/**
	 * @param url
	 *            URL �ּ� <br>
	 *            ex)http://www.naver.com/index.html
	 */
	public HttpPageGetter(String url) {
		this.url = url;

	}

	/**
	 * @param url
	 *            URL �ּ� <br>
	 *            ex)http://www.naver.com/index.html
	 * @param urlCharset
	 *            url �Ķ���� ĳ���� ��
	 */
	public HttpPageGetter(String url, String urlCharset) {
		this.url = url;
		this.urlCharset = urlCharset;

	}

	/**
	 * �Ϲ������� UrlPageInfo(String, String)�� ���<br>
	 * ��������� �����͸� ������ ĳ���ͳ��� ������ �ʿ�� ����.<br>
	 * http �ش��� �м��ؼ� �޴� url�� charset�� �˾ƾ� �ȴ�. <br>
	 * �׷��� getContentType()���� charset������ ���� �ʴ� ��찡 ����.<br>
	 * �̶��� ȣ�� �ϴ� �ʿ��� ó��
	 * 
	 * @param url
	 *            URL �ּ� <br>
	 *            ex)http://www.google.com/page/main.asp
	 * @param bodyCharset
	 *            ���� ĳ���� ��
	 * @param urlCharset
	 *            url �Ķ���� ĳ���� ��
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
		// �̹� �ѹ� �ʱ�ȭ �Ǿ� ������ �ٽ� ���� ����
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

			// ���� ĳ���� ���� ���� ��� �ش� �м�
			if (bodyCharset == null) {
				// application/x-msdownload; charset=utf-8 �̷������� ��
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
	 *            ĳ���� ��
	 * @return ���� ������ ����
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
	 * ���� ������ ������ �̸����� ���� �Ѵ�. <br>
	 * ���� ������ ���� ���� ������ ������ ���� �����.
	 * 
	 * @param saveFileName
	 *            ���� ��θ� ������ �̸�
	 * @throws IOException
	 */
	public void saveBody(String saveFileName) throws IOException {
		conn();
		InputStream in = conn.getInputStream();

		// ������ ���� �ϸ� �����Ѵ�.
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
	 * ���� ����. �ߺ� ������ ���� ��� ���� ������ �ʰ� ���ο� �̸����� ����
	 * 
	 * @param savePath
	 *            ���� ���� ���
	 * @return ����� ����, ���� ������ �����ϸ� null
	 * @throws IOException
	 */
	public File saveFileDownload(String savePath) throws IOException {
		return saveFileDownload(savePath, null);
	}

	/**
	 * ���� ����. �ߺ� ������ ���� ��� ���� ������ �ʰ� ���ο� �̸����� ����
	 * 
	 * @param savePath
	 *            ���� ���� ���
	 * @param fileEnd
	 *            ����� ���� �ڿ� �ٴ� ������,
	 * @return ���� ������ �����ϸ� null
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
	 * @return ������ ÷������ �̸�
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
	 * url ������ ������ ���ϸ����� ���
	 * 
	 * @param url
	 *            ��θ� �̿��Ͽ� ���ϸ� ����
	 * @return url ��� �� ���ϸ�
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
	 * Content-Disposition ���·� ���� �ٿ�ε� �� ��� ���� �� ����
	 * 
	 * @param fi
	 *            Content-Disposition �ش��� ��
	 * @return ���ϸ�
	 * @throws UnsupportedEncodingException
	 */
	private String dispositionFileName(String fi) throws UnsupportedEncodingException {
		// ���� �̸� ����~���� ���� ���ϱ�
		int fileStartPos = fi.indexOf("filename=");
		int fileEndPos = fi.indexOf(";", fileStartPos);

		// Content-Disposition=[attachment;filename=SW stack
		// %ED%86%B5%ED%95%A9%EB%B8%8C%EB%A1%9C%EC%85%94.pdf],
		// �̷������� ���� �Ǵ� ��쵵 �ִ�. �׷��� ;�����ݷ��� ���� ��쵵 �߻�
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
	 *            XML ��Ű�� ����
	 * @return ���� ������ XML ���� ��� �ֻ��� Document�ε带 ����
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
	 *            URL ���� ��Ű�� ����
	 * @return ���� ������ XML ���� ��� �ֻ��� Document�ε带 ����
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
	 *            XML ��Ű��
	 * @return ���� ������ XML ���� ��� �ֻ��� Document�ε带 ����
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private Document getBodyXml(Schema schema) throws ParserConfigurationException, IOException, SAXException {

		BufferedInputStream bis = null;
		Document document = null;
		InputStream in = null;
		try {

			// DOM �ļ� ����
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setSchema(schema);
			DocumentBuilder builder = factory.newDocumentBuilder();
			String body = getBody().trim();
			in = new ByteArrayInputStream(body.getBytes(bodyCharset));
			bis = new BufferedInputStream(in);

			// XML ���� �Ľ��ϱ�
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
	 * URL�� �ѱ� �Ķ���� ������ ����
	 * 
	 * @param key
	 *            Ű ����
	 * @param value
	 *            ��
	 * @deprecated ���̹��� ���� ���� addParameter()���
	 */
	public void setParameter(String key, String value) {
		parameter.put(key, value);
	}

	/**
	 * URL�� �ѱ� �Ķ���� ������ ����
	 * 
	 * @param key
	 *            Ű ����
	 * @param value
	 *            ��
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
	 * @return �ּ�+�Ķ����
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
	 * @return �Ķ���� ������ ���ڿ��� ����
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
	 * ������ ������ �Ķ���� ������ �ִٸ� �ش� �޼ҵ带 �����ϸ� ��������.
	 * 
	 * @param parameter
	 *            URL �Ķ���� ����
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
	 * method�� POST�� ��쿡�� ��� ����
	 * 
	 * @param sendBody
	 *            ������ ������ ������
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
