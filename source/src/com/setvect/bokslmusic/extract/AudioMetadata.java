package com.setvect.bokslmusic.extract;

/**
 * @version $Id$ ����� ��Ÿ ����
 */
public interface AudioMetadata {
	/**
	 * @return ���ø� ����
	 */
	public int getSamplingRate();

	/**
	 * @return ��Ʈ����Ʈ
	 */
	public int getBatRate();

	/**
	 * @return ����ð�(�ʴ���)
	 */
	public int getRunningTime();

}
