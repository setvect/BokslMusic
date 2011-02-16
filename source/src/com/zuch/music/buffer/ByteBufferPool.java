package com.zuch.music.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferPool implements IByteBufferPool {
	public static final int MEMORY_BLOCKSIZE = 4096;
	public static final int FILE_BLOCKSIZE = 16384;

	private final List<ByteBuffer> memoryQueue = new ArrayList<ByteBuffer>();
	private final List<ByteBuffer> fileQueue = new ArrayList<ByteBuffer>();

	private boolean isWait = false;

	/**
	 * Constructor
	 * 
	 * @param memorySize
	 *            Size of initial memory buffer
	 * @param fileSize
	 *            Size of initial file buffer
	 * @param file
	 *            Path of file buffer
	 * @throws IOException
	 */
	private ByteBufferPool(int memorySize, int fileSize, File file) throws IOException {
		if (memorySize > 0)
			initMemoryBuffer(memorySize);

		if (fileSize > 0)
			initFileBuffer(fileSize, file);
	}

	/**
	 * Initialize memory buffer
	 * 
	 * @param size
	 *            Size of initial memory buffer
	 */
	private void initMemoryBuffer(int size) {
		int bufferCount = size / MEMORY_BLOCKSIZE;
		size = bufferCount * MEMORY_BLOCKSIZE;
		ByteBuffer directBuf = ByteBuffer.allocateDirect(size);
		divideBuffer(directBuf, MEMORY_BLOCKSIZE, memoryQueue);
	}

	/**
	 * Initialize file buffer
	 * 
	 * @param size
	 *            Size of initial file buffer
	 * @param f
	 *            Buffer file
	 * @throws IOException
	 */
	private void initFileBuffer(int size, File f) throws IOException {
		int bufferCount = size / FILE_BLOCKSIZE;
		size = bufferCount * FILE_BLOCKSIZE;
		RandomAccessFile file = new RandomAccessFile(f, "rw");
		try {
			file.setLength(size);
			ByteBuffer fileBuffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0L, size);
			divideBuffer(fileBuffer, FILE_BLOCKSIZE, fileQueue);
		} finally {
			file.close();
		}
	}

	/**
	 * Divide buffer by block size
	 * 
	 * @param buf
	 *            ByteBuffer to divide
	 * @param blockSize
	 *            Size of unit block
	 * @param list
	 *            Container of unit buffer
	 */
	private void divideBuffer(ByteBuffer buf, int blockSize, List<ByteBuffer> list) {
		int bufferCount = buf.capacity() / blockSize;
		int position = 0;
		for (int i = 0; i < bufferCount; i++) {
			int max = position + blockSize;
			buf.limit(max);
			list.add(buf.slice());
			position = max;
			buf.position(position);
		}
	}

	/**
	 * Get memory buffer
	 * 
	 * @return ByteBuffer of memory buffer type
	 */
	public ByteBuffer getMemoryBuffer() {
		return getBuffer(memoryQueue, fileQueue);
	}

	/**
	 * Get file buffer
	 * 
	 * @return ByteBuffer of file buffer type
	 */
	public ByteBuffer getFileBuffer() {
		return getBuffer(fileQueue, memoryQueue);
	}

	/**
	 * Get buffer
	 * 
	 * @param firstQueue
	 *            Buffer queue to get buffer at first
	 * @param secondQueue
	 *            Buffer queue to get buffer at second times
	 * @return ByteBuffer of memory or file type
	 */
	private ByteBuffer getBuffer(List<ByteBuffer> firstQueue, List<ByteBuffer> secondQueue) {
		ByteBuffer buffer = getBuffer(firstQueue, false);
		if (buffer == null) {
			buffer = getBuffer(secondQueue, false);
			if (buffer == null) {
				if (isWait)
					buffer = getBuffer(firstQueue, true);
				else
					buffer = ByteBuffer.allocate(MEMORY_BLOCKSIZE);
			}
		}
		return buffer;
	}

	/**
	 * Get buffer
	 * 
	 * @param queue
	 *            Buffer queue to get buffer
	 * @param wait
	 *            Flag of wait status
	 * @return ByteBuffer
	 */
	private ByteBuffer getBuffer(List<ByteBuffer> queue, boolean wait) {
		synchronized (queue) {
			if (queue.isEmpty()) {
				if (wait) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						return null;
					}
				}
				else {
					return null;
				}
			}
			return (ByteBuffer) queue.remove(0);
		}
	}

	/**
	 * Put buffer in queue
	 * 
	 * @param buffer
	 *            ByteBuffer to put in queue
	 */
	public void putBuffer(ByteBuffer buffer) {
		if (buffer.isDirect()) {
			switch (buffer.capacity()) {
			case MEMORY_BLOCKSIZE:
				putBuffer(buffer, memoryQueue);
				break;
			case FILE_BLOCKSIZE:
				putBuffer(buffer, fileQueue);
				break;
			}
		}
	}

	/**
	 * Put buffer in queue
	 * 
	 * @param buffer
	 *            ByteBuffer to put in queue
	 * @param queue
	 *            ByteBuffer queue
	 */
	private void putBuffer(ByteBuffer buffer, List<ByteBuffer> queue) {
		buffer.clear();
		synchronized (queue) {
			queue.add(buffer);
			queue.notify();
		}
	}

	/**
	 * Set wait flag
	 */
	public synchronized void setWait(boolean wait) {
		this.isWait = wait;
	}

	/**
	 * Check wait flag
	 */
	public synchronized boolean isWait() {
		return isWait;
	}

	/**
	 * @return
	 */
	public static ByteBufferPool getInstance() {
		return ByteBufferPoolSingletone.instance;
	}

	/**
	 * syncrhonized를 사용하지 않고 싱글톤을 구현
	 */
	static class ByteBufferPoolSingletone {
		private static final ByteBufferPool instance;
		static {
			File bufferFile = new File("temp_buffer");
			try {
				instance = new ByteBufferPool(4096 << 6, 0, bufferFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
