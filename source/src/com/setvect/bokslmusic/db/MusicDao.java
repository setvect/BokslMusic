package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.AlbumSearch;
import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.service.music.PlayItemSearch;
import com.setvect.bokslmusic.service.music.PlayTimeSearch;
import com.setvect.bokslmusic.vo.music.Album;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
import com.setvect.bokslmusic.vo.music.PlayItem;
import com.setvect.bokslmusic.vo.music.PlayTime;
import com.setvect.common.util.GenericPage;

/**
 * ���� ���� DAO
 * 
 * @version $Id$
 */
public interface MusicDao {
	// ------ ���� ��� ����
	/**
	 * @param basePath
	 *            ���(Ű)
	 * @return ���� ���� ���
	 */
	public MusicDirectory getMusicPath(String basePath);

	/**
	 * @return ��ϵ� ��� ���� ���� ���
	 */
	public List<MusicDirectory> getMusicPathList();

	/**
	 * ���
	 * 
	 * @param item
	 *            ���� ���� ���
	 */
	public void createMusicPath(MusicDirectory item);

	/**
	 * ����
	 * 
	 * @param item
	 *            ���� ���� ���
	 */
	public void updateMusicPath(MusicDirectory item);

	/**
	 * ����
	 * 
	 * @param basePath
	 *            ���� ���(Ű)
	 */
	public void removeMusicPath(String basePath);

	// ------ ���� ��� ����
	/**
	 * @param musicArticleSeq
	 * @return ��������
	 */
	public MusicArticle getMusicArticle(String musicArticleId);

	/**
	 * @param pageCondition
	 * @return ��������
	 */
	public GenericPage<MusicArticle> getMusicArticlePagingList(MusicArticleSearch pageCondition);

	/**
	 * ���� ���
	 * 
	 * @param item
	 */
	public void createMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param item
	 */
	public void updateMusicArticle(MusicArticle item);

	/**
	 * ���� ����
	 * 
	 * @param musicArticleId
	 */
	public void removeMusicArticle(String musicArticleId);

	// ------ �ٹ� ����
	/**
	 * @param albumSeq
	 *            �ٹ� �Ϸù�ȣ
	 * @return �ٹ� ����
	 */
	public Album getAlbum(int albumSeq);

	/**
	 * @param pageCondition
	 *            �˻� ����
	 * @return �ٹ� ���� ���
	 */
	public GenericPage<Album> getAlbumList(AlbumSearch pageCondition);

	/**
	 * ���
	 * 
	 * @param item
	 *            �ٹ� ����
	 */
	public void createAlbum(Album item);

	/**
	 * ����
	 * 
	 * @param item
	 *            �ٹ� ����
	 */
	public void updateAlbum(Album item);

	/**
	 * ����
	 * 
	 * @param albumSeq
	 *            �ٹ� �Ϸù�ȣ
	 */
	public void removeAlbum(int albumSeq);

	// ------ Play Item ����
	/**
	 * @param playItemSeq
	 *            ��� ���� �Ϸù�ȣ
	 * @return �ٹ� ����
	 */
	public PlayItem getPlayItem(int playItemSeq);

	/**
	 * @param pageCondition
	 *            �˻� ����
	 * @return �ٹ� ���� ���
	 */
	public GenericPage<PlayItem> getPlayItemList(PlayItemSearch pageCondition);

	/**
	 * ���
	 * 
	 * @param item
	 *            ��� ����
	 */
	public void createPlayItem(PlayItem item);

	/**
	 * ����
	 * 
	 * @param item
	 *            ��� ����
	 */
	public void updatePlayItem(PlayItem item);

	/**
	 * ����
	 * 
	 * @param playItemSeq
	 *            ��� ���� �Ϸù�ȣ
	 */
	public void removePlayItem(int playItemSeq);

	// ------ Play Time ����

	/**
	 * @param playTimeSeq
	 * 
	 * @return ��� �ð� ����
	 */
	public PlayTime getPlayTime(int playTimeSeq);

	/**
	 * @param pageCondition
	 *            �˻� ����
	 * @return �ٹ� ���� ���
	 */
	public GenericPage<PlayTime> getPlayTimeList(PlayTimeSearch pageCondition);

	/**
	 * ���
	 * 
	 * @param item
	 *            ��� ����
	 */
	public void createPlayTime(PlayTime item);

	/**
	 * ����
	 * 
	 * @param item
	 *            ��� ����
	 */
	public void updatePlayTime(PlayTime item);

	/**
	 * ����
	 * 
	 * @param playTimeSeq
	 *            ��� ���� �Ϸù�ȣ
	 */
	public void removePlayTime(int playTimeSeq);

}
