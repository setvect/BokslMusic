package com.zuch.music.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.valuepair.TextEncoding;

import com.zuch.music.buffer.ByteBufferPool;

public class MP3Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File sourceFile = new File("E:\\10.멀티미디어\\03.음악\\01_가요\\대학가요제\\1988년 9회 강변 대상 - 이상은 - 담다디 (LP).mp3");
		try {
			MP3File f = (MP3File) AudioFileIO.read(sourceFile);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();

			printSummeryInformation(audioHeader);
			String md5 = printChecksumForLyric(f);

			printLyric(md5);

			ID3v1Tag v1Tag = f.getID3v1Tag();
			AbstractID3v2Tag v2tag = f.getID3v2Tag();
			ID3v24Tag v24tag = f.getID3v2TagAsv24();

			printID3v1Tag(v1Tag);
			printID3v2Tag(v2tag);
			printID3v24Tag(v24tag);

		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printSummeryInformation(MP3AudioHeader header) {
		StringBuffer sb = new StringBuffer();

		sb.append("======== Summery Information ========\n");
		sb.append("TrackLength: ").append(header.getTrackLength()).append("\n");
		sb.append("SampleRateAsNumber: ").append(header.getSampleRateAsNumber()).append("\n");
		sb.append("Channels: ").append(header.getChannels()).append("\n");
		sb.append("isVariableBitRate: ").append(header.isVariableBitRate()).append("\n\n");
		sb.append("TrackLengthAsString: ").append(header.getTrackLengthAsString()).append("\n");
		sb.append("MpegVersion: ").append(header.getMpegVersion()).append("\n");
		sb.append("MpegLayer: ").append(header.getMpegLayer()).append("\n");
		sb.append("Original: ").append(header.isOriginal()).append("\n");
		sb.append("Copyrighted: ").append(header.isCopyrighted()).append("\n");
		sb.append("Private: ").append(header.isPrivate()).append("\n");
		sb.append("Protected: ").append(header.isProtected()).append("\n");
		sb.append("BitRate: ").append(header.getBitRate()).append("\n");
		sb.append("EncodingType: ").append(header.getEncodingType()).append("\n");
		sb.append("Mp3StartByte: ").append(header.getMp3StartByte()).append("\n");

		System.out.println(sb.toString());
	}

	private static String printChecksumForLyric(MP3File mp3) throws IOException, NoSuchAlgorithmException {
		System.out.println("======== MP3 checksum for Lyric ========");

		MP3AudioHeader header = (MP3AudioHeader) mp3.getAudioHeader();
		long mp3Start = header.getMp3StartByte();

		ByteBufferPool byteBufferPool = ByteBufferPool.getInstance();

		FileInputStream fis = new FileInputStream(mp3.getFile());
		FileChannel fc = fis.getChannel();

		// Read audio frame 160*1024 byte
		int count = 40;
		ByteBuffer[] bufferArray = new ByteBuffer[count];

		for (int i = 0; i < count; ++i)
			bufferArray[i] = byteBufferPool.getMemoryBuffer();

		fc = fc.position(mp3Start);
		long read = fc.read(bufferArray);

		System.out.printf("read: [%d]\n", read);

		MessageDigest clsMd5 = MessageDigest.getInstance("MD5");
		clsMd5.reset();
		for (int i = 0; i < count; ++i) {
			bufferArray[i].flip();
			clsMd5.update(bufferArray[i]);
			byteBufferPool.putBuffer(bufferArray[i]);
		}

		byte[] arrBuf = clsMd5.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : arrBuf) {
			sb.append(String.format("%02x", b));
		}

		System.out.printf("MD5 Value : [%s]\n", sb.toString());
		System.out.println(sb.toString().length());
		fc.close();

		return sb.toString();
	}

	public static void printLyric(String mp5) throws IOException {
		System.out.printf("============== printLyric ==================\n");
		URL alsongServer = new URL("http://lyrics.alsong.co.kr/alsongwebservice/service1.asmx");
		HttpURLConnection httpCon = (HttpURLConnection) alsongServer.openConnection();

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
		sb.append("<ns1:strChecksum>").append(mp5).append("</ns1:strChecksum>");
		sb.append("<ns1:strVersion>1.93</ns1:strVersion>");
		sb.append("<ns1:strMACAddress>005056C00001</ns1:strMACAddress>");
		sb.append("<ns1:strIPAddress>192.168.1.2</ns1:strIPAddress>");
		sb.append("</ns1:stQuery>");
		sb.append("</ns1:GetLyric5>");
		sb.append("</SOAP-ENV:Body>");
		sb.append("</SOAP-ENV:Envelope>");

		String content = sb.toString();

		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("POST");
		httpCon.addRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		httpCon.addRequestProperty("User-Agent", "gSOAP/2.7");
		httpCon.addRequestProperty("Cache-Control", "no-cache");

		OutputStreamWriter osw = new OutputStreamWriter(httpCon.getOutputStream());
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(content);
		bw.flush();
		bw.close();

		httpCon.connect();

		int status = httpCon.getResponseCode();
		String responseMessage = httpCon.getResponseMessage();
		System.out.printf("Response Message: %s\n", responseMessage);

		InputStreamReader isr = null;
		if (status == 200)
			isr = new InputStreamReader(httpCon.getInputStream(), "utf-8");
		else
			isr = new InputStreamReader(httpCon.getErrorStream());

		BufferedReader br = new BufferedReader(isr);

		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}

	private static void printID3v1Tag(ID3v1Tag tag) throws UnsupportedEncodingException {
		if (tag == null) {
			System.out.println("ID3v1Tag 태그 없음!");
			return;
		}
		StringBuffer sb = new StringBuffer();

		sb.append("======== ID3v1Tag Information ========\n");
		String encode = tag.getEncoding();
		System.out.println("encode: " + encode);
		sb.append("FirstTitle: ").append(new String(tag.getFirstTitle().getBytes(encode), "euc-kr")).append("\n");
		// sb.append("FirstTrack: ").append(tag.getFirstTrack()).append("\n");
		sb.append("FirstYear: ").append(new String(tag.getFirstYear().getBytes(encode), "euc-kr")).append("\n");
		sb.append("FirstGenre: ").append(new String(tag.getFirstGenre().getBytes(encode), "euc-kr")).append("\n");
		List<TagField> artList = tag.getArtist();
		for (TagField f : artList) {
			sb.append("ArtList: ").append(new String(f.toString().getBytes(encode), "euc-kr")).append("\n");
		}

		sb.append("FirstComment: ").append(new String(tag.getFirstComment().getBytes(encode), "euc-kr")).append("\n");

		System.out.println(sb.toString());
	}

	private static void printID3v2Tag(AbstractID3v2Tag tag) {
		if (tag == null) {
			System.out.println("ID3v2Tag 태그 없음!");
			return;
		}
		System.out.printf("\ngetIdentifier: [%s]\n", tag.getIdentifier());
		System.out.printf("class name: [%s]\n", tag.getClass());
	}

	private static void printID3v24Tag(ID3v24Tag tag) throws UnsupportedEncodingException {
		if (tag == null) {
			System.out.println("ID3v24 태그 없음!");
			return;
		}
		System.out.printf("\ngetIdentifier: [%s]\n", tag.getIdentifier());
		System.out.printf("class name: [%s]\n", tag.getClass());

		Iterator<TagField> fIter = tag.getFields();

		while (fIter.hasNext()) {
			TagField field = fIter.next();
			System.out.printf("ID: [%s]\n", field.getId());

			AbstractID3v2Frame frame = (AbstractID3v2Frame) field;
			if (frame.getBody() instanceof AbstractFrameBodyTextInfo) {
				AbstractFrameBodyTextInfo textBody = (AbstractFrameBodyTextInfo) frame.getBody();
				byte enc = textBody.getTextEncoding();
				TextEncoding textEncoding = TextEncoding.getInstanceOf();
				String endcode = textEncoding.getValueForId(enc);
				String orgText = textBody.getText();

				System.out.printf("Encoding: [%s]\n", endcode);

				String text = new String(orgText.getBytes(endcode), "euc-kr");

				System.out.printf("String: [%s]\n\n", text);
			}
		}
	}
}
