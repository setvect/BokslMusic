package com.zuch.music.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.zuch.music.buffer.ByteBufferPool;

public final class Md5Util {
	/**
	 * Get MD5 Checksum value from file
	 * 
	 * @param file
	 *            File object to get MD5 Checksum
	 * @return MD5 Checksum value
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Checksum(File file) throws NoSuchAlgorithmException, IOException {
		return getMD5Checksum(file, 0, file.length());
	}

	/**
	 * Get MD5 Checksum value from file
	 * 
	 * @param file
	 *            File object to get MD5 Checksum
	 * @param startPos
	 *            Start position
	 * @param length
	 *            Byte length from start position
	 * @return MD5 Checksum value
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Checksum(File file, long startPos, long length) {
		if (!file.exists())
			return null;

		if (!file.isFile())
			return null;

		if (length <= 0)
			return null;

		long sPos = startPos < 0 ? 0 : startPos;

		int bufCount = ((int) (length / ByteBufferPool.FILE_BLOCKSIZE)) + 1;
		if (bufCount > 11)
			bufCount = 11;

		// Prepare ByteBufferArray
		ByteBuffer[] bufferArray = new ByteBuffer[bufCount];

		ByteBufferPool bufferMgr = ByteBufferPool.getInstance();

		for (int i = 0; i < bufferArray.length; ++i)
			bufferArray[i] = bufferMgr.getFileBuffer();

		FileChannel fc = null;
		FileInputStream fis = null;
		MessageDigest clsMd5 = null;
		try {
			fis = new FileInputStream(file);
			fc = fis.getChannel();

			// Move file pointer to start position
			fc = fc.position(sPos);

			// Get MD5 Checksum
			clsMd5 = MessageDigest.getInstance("MD5");
			clsMd5.reset();

			long read = 0;
			while (read < length) {
				long remain = length - read;

				// Clear buffer & adjust capacity
				for (ByteBuffer buf : bufferArray) {
					buf.clear();
					if (remain == 0) {
						buf.limit(0);
						continue;
					}

					int capacity = buf.capacity();
					if (remain - capacity < 0)
						buf.limit((int) remain);

					int remaining = buf.remaining();
					remain -= remaining;
				}

				// Read content
				long rCount = fc.read(bufferArray);
				if (rCount <= 0)
					break; // Error of too short

				read += rCount;

				// Update MD5
				for (ByteBuffer buf : bufferArray) {
					buf.flip();
					clsMd5.update(buf);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException ignor) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ignor) {
				}
			}
		}

		// Put buffers to pool
		for (ByteBuffer buf : bufferArray) {
			bufferMgr.putBuffer(buf);
			buf = null;
		}

		// Get MD5 Checksum value
		byte[] arrBuf = clsMd5.digest();
		StringBuffer sb = new StringBuffer();

		for (byte b : arrBuf)
			sb.append(String.format("%02x", b));

		return sb.toString();
	}

	/**
	 * Get MD5 Checksum value from inputStream
	 * 
	 * @param stream
	 *            InputStream
	 * @return MD5 Checksum value
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Checksum(InputStream stream) throws NoSuchAlgorithmException, IOException {
		if (stream == null)
			return null;

		ReadableByteChannel rbc = Channels.newChannel(stream);

		// Prepare ByteBufferArray
		ByteBufferPool bufferMgr = ByteBufferPool.getInstance();
		ByteBuffer bBuffer = bufferMgr.getFileBuffer();

		// Get MD5 Checksum
		MessageDigest clsMd5 = MessageDigest.getInstance("MD5");
		clsMd5.reset();

		while (rbc.read(bBuffer) > 0) {
			bBuffer.flip();
			clsMd5.update(bBuffer);
			bBuffer.clear();
		}
		rbc.close();

		// Put buffers to pool
		bufferMgr.putBuffer(bBuffer);
		bBuffer = null;

		// Get MD5 Checksum value
		byte[] arrBuf = clsMd5.digest();
		StringBuffer sb = new StringBuffer();

		for (byte b : arrBuf)
			sb.append(String.format("%02x", b));

		return sb.toString();
	}
}
