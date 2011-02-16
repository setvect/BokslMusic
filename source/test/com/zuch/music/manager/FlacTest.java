package com.zuch.music.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacStreamReader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTagField;

import com.zuch.music.buffer.ByteBufferPool;

public class FlacTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		File sourceFile = new File("E:\\10.멀티미디어\\03.음악\\03_클래식\\네티즌이 뽑은 클래식 베스트36-We Get Classics Requests(Vol.1)\\[FLAC] Classics Requests Vol.1_CD1\\11. 엘가 - 위풍당당 행진곡 1번.flac");

		try
		{
			AudioFile aFile = AudioFileIO.read(sourceFile);
			AudioHeader aHeader = aFile.getAudioHeader();
			printSummeryInformation(aHeader);
			
			String md5 = printChecksumForLyric(aFile);
			MP3Test.printLyric(md5);
			
			Tag tag =  aFile.getTag();
			System.out.println(aFile.getClass());
			printTag(tag);
			
		} catch (CannotReadException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String printChecksumForLyric(AudioFile flacfile) throws IOException, NoSuchAlgorithmException, CannotReadException
	{
		System.out.println("======== MP3 checksum for Lyric ========");
		
		FlacStreamReader stramReader = new FlacStreamReader(new RandomAccessFile(flacfile.getFile(), "r"));			
		stramReader.findStream();
		
		System.out.printf("StartOfFlacInFile: [%d]\n", stramReader.getStartOfFlacInFile());
		
		ByteBufferPool byteBufferPool = ByteBufferPool.getInstance();
		
		FileInputStream fis = new FileInputStream(flacfile.getFile());
		FileChannel fc = fis.getChannel();
		
		//Read audio frame 160*1024 byte
		int count = 40;		
		ByteBuffer[] bufferArray = new ByteBuffer[count];
		
		for(int i=0; i<count; ++i)
			bufferArray[i] = byteBufferPool.getMemoryBuffer();
		
		fc = fc.position(stramReader.getStartOfFlacInFile());
		long read = fc.read(bufferArray);
		
		System.out.printf("read: [%d]\n", read);
		
		MessageDigest clsMd5 = MessageDigest.getInstance("MD5");
		clsMd5.reset();
		for(int i=0; i<count; ++i)
		{
			bufferArray[i].flip();
			clsMd5.update(bufferArray[i]);
			byteBufferPool.putBuffer(bufferArray[i]);
		}
		
		byte[] arrBuf = clsMd5.digest();
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("[len:%d] ", arrBuf.length));
		for(byte b : arrBuf)
		{
			sb.append(String.format("%02x", b));
		}
		
		System.out.printf("MD5 Value : [%s]\n", sb.toString());
		
		fc.close();
		
		return sb.toString();
	}
	
	private static void printSummeryInformation(AudioHeader header)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("======== Summery Information ========\n");
		sb.append("TrackLength: ").append(header.getTrackLength()).append("\n");
		sb.append("SampleRateAsNumber: ").append(header.getSampleRateAsNumber()).append("\n");
		sb.append("Channels: ").append(header.getChannels()).append("\n");
		sb.append("isVariableBitRate: ").append(header.isVariableBitRate()).append("\n");
		sb.append("BitRate: ").append(header.getBitRate()).append("\n");
		sb.append("EncodingType: ").append(header.getEncodingType()).append("\n");
		sb.append("Format: ").append(header.getFormat()).append("\n");
		System.out.println(sb.toString());
	}
	
	private static void printTag(Tag tag) throws UnsupportedEncodingException
	{
		System.out.printf("class name: [%s]\n", tag.getClass());
		
		Iterator<TagField> fIter = tag.getFields();
		
		while(fIter.hasNext())
		{
			TagField field = fIter.next();
			System.out.printf("ID: [%s]\n", field.getId());
			
			VorbisCommentTagField tagField = (VorbisCommentTagField)field;
			String endcode = tagField.getEncoding();
			String orgText = tagField.getContent();
			
			System.out.printf("Encoding: [%s]\n", endcode);
			System.out.printf("String: [%s]\n\n", orgText);
		}
	}

}
