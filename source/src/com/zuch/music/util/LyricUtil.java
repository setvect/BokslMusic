package com.zuch.music.util;

import java.io.IOException;

import com.setvect.common.http.HttpPageGetter;

public final class LyricUtil {
	/**
	 * Download lyric from Alsong lyric server
	 * 
	 * @param firstFrmMD5
	 *            MD5 Checksum value of the 1st music frame
	 * @return Lyric content(Maybe XML document format)
	 */
	public static String downloadLyric(String firstFrmMD5) {
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
		sb.append("<ns1:strChecksum>").append(firstFrmMD5).append("</ns1:strChecksum>");
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
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retValue;
	}

}
