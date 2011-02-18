package test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Echo01 extends DefaultHandler {
	public static void main(String argv[]) {

		// Use an instance of ourselves as the SAX event handler
		DefaultHandler handler = new Echo01();
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Set up output stream
			out = new OutputStreamWriter(System.out, "UTF8");

			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File("etc/a.xml"), handler);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(0);
	}

	static private Writer out;

	// ===========================================================
	// SAX DocumentHandler methods
	// ===========================================================

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
		try {
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	public void startElement(String namespaceURI, String lName, // local name
			String qName, // qualified name
			Attributes attrs) throws SAXException {
		System.out.printf("namespaceURI: %s, lName: %s, qName: %s, attrs: %s %n", namespaceURI, lName, qName,
				attrs.toString());
	}

	public void endElement(String namespaceURI, String sName, // simple name
			String qName // qualified name
	) throws SAXException {
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);

	}

}
