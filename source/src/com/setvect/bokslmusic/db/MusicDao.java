package com.setvect.bokslmusic.db;

import java.util.List;

import com.setvect.bokslmusic.service.music.MusicArticleSearch;
import com.setvect.bokslmusic.vo.music.MusicArticle;
import com.setvect.bokslmusic.vo.music.MusicDirectory;
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
	 * @param item
	 *            ���� ���� ���
	 */
	public void createMusicPath(MusicDirectory item);

	/**
	 * ����
	 * @param item
	 *            ���� ���� ���
	 */
	public void updateMusicPath(MusicDirectory item);

	/**
	 * ����
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
}
