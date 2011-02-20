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

	/**
	 * @return ����
	 */
	public String getTitle();

	/**
	 * @return ����/������
	 */
	public String getArtist();

	/**
	 * @return �ٹ�
	 */
	public String getAlbum();

	/**
	 * @return �⵵
	 */
	public String getYear();

	/**
	 * @return �帣
	 */
	public String getGenre();

	/**
	 * @return Ʈ��
	 */
	public String getTrack();

}
