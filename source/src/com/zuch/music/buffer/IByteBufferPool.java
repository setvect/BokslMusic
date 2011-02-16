package com.zuch.music.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer pool interface
 * 
 * @author zuch
 * 
 */
public interface IByteBufferPool {
	public ByteBuffer getMemoryBuffer();

	public ByteBuffer getFileBuffer();

	public void putBuffer(ByteBuffer buffer);

	public void setWait(boolean wait);

	public boolean isWait();
}
